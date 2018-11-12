package javautils.redis.pool;

import javautils.redis.JedisTemplate.JedisAction;
import javautils.redis.JedisTemplate;
import redis.clients.jedis.exceptions.JedisConnectionException;
import javautils.redis.JedisUtils;
import java.util.concurrent.atomic.AtomicBoolean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import org.apache.commons.pool2.impl.GenericObjectPool;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import redis.clients.jedis.HostAndPort;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CountDownLatch;
import redis.clients.jedis.JedisPoolConfig;
import java.util.List;
import org.slf4j.Logger;

public final class JedisSentinelPool extends JedisPool
{
    private static final String NO_ADDRESS_YET = "I dont know because no sentinel up";
    private static Logger logger;
    private List<JedisPool> sentinelPools;
    private MasterSwitchListener masterSwitchListener;
    private String masterName;
    private JedisPoolConfig masterPoolConfig;
    private ConnectionInfo masterConnectionInfo;
    private CountDownLatch poolInit;
    
    static {
        JedisSentinelPool.logger = LoggerFactory.getLogger((Class)JedisSentinelPool.class);
    }
    
    public JedisSentinelPool(final HostAndPort[] sentinelAddresses, final String masterName, final ConnectionInfo masterConnectionInfo, final JedisPoolConfig masterPoolConfig) {
        this.sentinelPools = new ArrayList<JedisPool>();
        this.poolInit = new CountDownLatch(1);
        assertArgument(sentinelAddresses == null || sentinelAddresses.length == 0, "seintinelInfos is not set");
        for (final HostAndPort sentinelInfo : sentinelAddresses) {
            final JedisPool sentinelPool = new JedisDirectPool(sentinelInfo, new JedisPoolConfig());
            this.sentinelPools.add(sentinelPool);
        }
        assertArgument(masterConnectionInfo == null, "masterConnectionInfo is not set");
        this.masterConnectionInfo = masterConnectionInfo;
        assertArgument(masterName == null || masterName.isEmpty(), "masterName is not set");
        this.masterName = masterName;
        assertArgument(masterPoolConfig == null, "masterPoolConfig is not set");
        this.masterPoolConfig = masterPoolConfig;
        (this.masterSwitchListener = new MasterSwitchListener()).start();
        try {
            if (!this.poolInit.await(5L, TimeUnit.SECONDS)) {
                JedisSentinelPool.logger.warn("the sentiel pool can't not init in 5 seconds");
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public JedisSentinelPool(final HostAndPort[] sentinelAddresses, final String masterName, final JedisPoolConfig masterPoolConfig) {
        this(sentinelAddresses, masterName, new ConnectionInfo(), masterPoolConfig);
    }
    
    public void destroy() {
        this.masterSwitchListener.shutdown();
        for (final JedisPool sentinel : this.sentinelPools) {
            sentinel.destroy();
        }
        this.destroyInternelPool();
        try {
            JedisSentinelPool.logger.info("Waiting for MasterSwitchListener thread finish");
            this.masterSwitchListener.join();
            JedisSentinelPool.logger.info("MasterSwitchListener thread finished");
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    protected void destroyInternelPool() {
        this.closeInternalPool();
        this.address = null;
        this.connectionInfo = null;
        this.internalPool = null;
    }
    
    private static void assertArgument(final boolean expression, final String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public MasterSwitchListener getMasterSwitchListener() {
        return this.masterSwitchListener;
    }
    
    public class MasterSwitchListener extends Thread
    {
        public static final String THREAD_NAME_PREFIX = "MasterSwitchListener-";
        private JedisPubSub subscriber;
        private JedisPool sentinelPool;
        private Jedis sentinelJedis;
        private AtomicBoolean running;
        private HostAndPort previousMasterAddress;
        
        public MasterSwitchListener() {
            super("MasterSwitchListener-" + JedisSentinelPool.this.masterName);
            this.running = new AtomicBoolean(true);
        }
        
        public void shutdown() {
            this.running.getAndSet(false);
            this.interrupt();
            try {
                if (this.subscriber != null) {
                    this.subscriber.unsubscribe();
                }
            }
            finally {
                JedisUtils.destroyJedis(this.sentinelJedis);
            }
            JedisUtils.destroyJedis(this.sentinelJedis);
        }
        
        @Override
        public void run() {
            while (this.running.get()) {
                try {
                    this.sentinelPool = this.pickupSentinel();
                    if (this.sentinelPool != null) {
                        final HostAndPort masterAddress = this.queryMasterAddress();
                        if (JedisSentinelPool.this.internalPool != null && this.isAddressChange(masterAddress)) {
                            JedisSentinelPool.logger.info("The internalPool {} had changed, destroy it now.", (Object)this.previousMasterAddress);
                            JedisSentinelPool.this.destroyInternelPool();
                        }
                        if (JedisSentinelPool.this.internalPool == null) {
                            JedisSentinelPool.logger.info("The internalPool {} is not init or the address had changed, init it now.", (Object)masterAddress);
                            JedisSentinelPool.this.initInternalPool(masterAddress, JedisSentinelPool.this.masterConnectionInfo, JedisSentinelPool.this.masterPoolConfig);
                            JedisSentinelPool.this.poolInit.countDown();
                        }
                        this.previousMasterAddress = masterAddress;
                        this.sentinelJedis = (Jedis)this.sentinelPool.getResource();
                        this.subscriber = new JedisSentinelPool.MasterSwitchListener.MasterSwitchSubscriber();
                        this.sentinelJedis.subscribe(this.subscriber, new String[] { "+switch-master", "+redirect-to-master" });
                    }
                    else {
                        JedisSentinelPool.logger.info("All sentinels down, sleep 2 seconds and try to connect again.");
                        if (JedisSentinelPool.this.internalPool == null) {
                            final HostAndPort masterAddress = new HostAndPort("I dont know because no sentinel up", 6379);
                            JedisSentinelPool.this.initInternalPool(masterAddress, JedisSentinelPool.this.masterConnectionInfo, JedisSentinelPool.this.masterPoolConfig);
                            this.previousMasterAddress = masterAddress;
                        }
                        this.sleep(2000);
                    }
                }
                catch (JedisConnectionException e2) {
                    if (this.sentinelJedis != null) {
                        this.sentinelPool.returnBrokenResource(this.sentinelJedis);
                    }
                    if (!this.running.get()) {
                        continue;
                    }
                    JedisSentinelPool.logger.error("Lost connection with Sentinel " + this.sentinelPool.getAddress() + ", sleep 1 seconds and try to connect other one. ");
                    this.sleep(1000);
                }
                catch (Exception e) {
                    JedisSentinelPool.logger.error(e.getMessage(), (Throwable)e);
                    this.sleep(1000);
                }
            }
        }
        
        public HostAndPort getCurrentMasterAddress() {
            return this.previousMasterAddress;
        }
        
        private JedisPool pickupSentinel() {
            for (final JedisPool pool : JedisSentinelPool.this.sentinelPools) {
                if (JedisUtils.ping(pool)) {
                    return pool;
                }
            }
            return null;
        }
        
        private boolean isAddressChange(final HostAndPort currentMasterAddress) {
            return this.previousMasterAddress == null || !this.previousMasterAddress.equals((Object)currentMasterAddress);
        }
        
        private HostAndPort queryMasterAddress() {
            final JedisTemplate sentinelTemplate = new JedisTemplate(this.sentinelPool);
            final List<String> address = sentinelTemplate.execute((JedisAction<List<String>>)new JedisAction<List<String>>() {
                @Override
                public List<String> action(final Jedis jedis) {
                    return (List<String>)jedis.sentinelGetMasterAddrByName(JedisSentinelPool.this.masterName);
                }
            });
            if (address == null || address.isEmpty()) {
                throw new IllegalArgumentException("Master name " + JedisSentinelPool.this.masterName + " is not in sentinel.conf");
            }
            return new HostAndPort((String)address.get(0), (int)Integer.valueOf(address.get(1)));
        }
        
        private void sleep(final int millseconds) {
            try {
                Thread.sleep(millseconds);
            }
            catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
            }
        }
        
        
        private class MasterSwitchSubscriber extends JedisPubSub
        {
            public void onMessage(final String channel, final String message) {
                JedisSentinelPool.logger.info("Sentinel " + MasterSwitchListener.this.sentinelPool.getAddress() + " published: " + message);
                final String[] switchMasterMsg = message.split(" ");
                if (JedisSentinelPool.this.masterName.equals(switchMasterMsg[0])) {
                    final HostAndPort masterAddress = new HostAndPort(switchMasterMsg[3], Integer.parseInt(switchMasterMsg[4]));
                    JedisSentinelPool.logger.info("Switch master to " + masterAddress);
                    JedisSentinelPool.this.destroyInternelPool();
                    JedisSentinelPool.this.initInternalPool(masterAddress, JedisSentinelPool.this.masterConnectionInfo, JedisSentinelPool.this.masterPoolConfig);
                   MasterSwitchListener.this.previousMasterAddress = masterAddress;
                }
            }
            
            public void onPMessage(final String pattern, final String channel, final String message) {
            }
            
            public void onSubscribe(final String channel, final int subscribedChannels) {
            }
            
            public void onUnsubscribe(final String channel, final int subscribedChannels) {
            }
            
            public void onPUnsubscribe(final String pattern, final int subscribedChannels) {
            }
            
            public void onPSubscribe(final String pattern, final int subscribedChannels) {
            }
        }
    }
}
