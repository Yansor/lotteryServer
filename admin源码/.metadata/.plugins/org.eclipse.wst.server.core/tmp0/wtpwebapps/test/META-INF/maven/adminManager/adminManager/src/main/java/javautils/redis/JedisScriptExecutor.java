package javautils.redis;

import java.util.List;
import java.util.Arrays;
import org.springframework.util.Assert;
import org.springframework.core.io.Resource;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.DefaultResourceLoader;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.Jedis;
import javautils.redis.pool.JedisPool;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class JedisScriptExecutor
{
    private static Logger logger;
    private JedisTemplate jedisTemplate;
    private String script;
    private String sha1;
    
    static {
        JedisScriptExecutor.logger = LoggerFactory.getLogger((Class)JedisScriptExecutor.class);
    }
    
    public JedisScriptExecutor(final JedisPool jedisPool) {
        this.jedisTemplate = new JedisTemplate(jedisPool);
    }
    
    public JedisScriptExecutor(final JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }
    
    public void load(final String scriptContent) throws JedisDataException {
        this.sha1 = this.jedisTemplate.execute(new JedisTemplate.JedisAction<String>() {
            @Override
            public String action(final Jedis jedis) {
                return jedis.scriptLoad(scriptContent);
            }
        });
        this.script = scriptContent;
        JedisScriptExecutor.logger.debug("Script \"{}\" had been loaded as {}", (Object)scriptContent, (Object)this.sha1);
    }
    
    public void loadFromFile(final String scriptPath) throws JedisDataException {
        String scriptContent;
        try {
            final Resource resource = new DefaultResourceLoader().getResource(scriptPath);
            scriptContent = FileUtils.readFileToString(resource.getFile(), "UTF-8");
        }
        catch (IOException e) {
            throw new IllegalArgumentException(String.valueOf(scriptPath) + " is not exist.", e);
        }
        this.load(scriptContent);
    }
    
    public Object execute(final String[] keys, final String[] args) throws IllegalArgumentException {
        Assert.notNull((Object)keys, "keys can't be null.");
        Assert.notNull((Object)args, "args can't be null.");
        return this.execute(Arrays.asList(keys), Arrays.asList(args));
    }
    
    public Object execute(final List<String> keys, final List<String> args) throws IllegalArgumentException {
        Assert.notNull((Object)keys, "keys can't be null.");
        Assert.notNull((Object)args, "args can't be null.");
        return this.jedisTemplate.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(final Jedis jedis) {
                try {
                    return jedis.evalsha(JedisScriptExecutor.this.sha1, keys, args);
                }
                catch (JedisDataException e) {
                    JedisScriptExecutor.logger.warn("Script {} is not loaded in server yet or the script is wrong, try to reload and run it again.", (Object)JedisScriptExecutor.this.script, (Object)e);
                    return jedis.eval(JedisScriptExecutor.this.script, keys, args);
                }
            }
        });
    }
}
