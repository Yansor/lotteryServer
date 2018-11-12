package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.User;
import lottery.domains.content.vo.user.UserHighPrizeTimesVO;
import org.apache.commons.lang.StringUtils;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import lottery.domains.content.entity.SysPlatform;
import javautils.math.MathUtil;
import lottery.domains.content.entity.UserHighPrize;
import lottery.domains.content.vo.user.UserVO;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import java.util.Collection;
import java.util.LinkedList;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import javautils.redis.JedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserHighPrizeDao;
import lottery.domains.content.entity.GameBets;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserHighPrizeService;

@Service
public class UserHighPrizeServiceImpl implements UserHighPrizeService
{
    private static final Logger log;
    private static final String USER_HIGH_PRIZE_NOTICE_KEY = "USER:HIGH_PRIZE:NOTICE";
    private BlockingQueue<GameBets> gameBetsQueue;
    @Autowired
    private UserHighPrizeDao highPrizeDao;
    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    private static boolean isRunning;
    
    static {
        log = LoggerFactory.getLogger((Class)UserHighPrizeServiceImpl.class);
        UserHighPrizeServiceImpl.isRunning = false;
    }
    
    public UserHighPrizeServiceImpl() {
        this.gameBetsQueue = new LinkedBlockingDeque<GameBets>();
    }
    
    @Scheduled(cron = "0/3 * * * * *")
    public void run() {
        synchronized (UserHighPrizeServiceImpl.class) {
            if (UserHighPrizeServiceImpl.isRunning) {
                // monitorexit(UserHighPrizeServiceImpl.class)
                return;
            }
            UserHighPrizeServiceImpl.isRunning = true;
        }
        // monitorexit(UserHighPrizeServiceImpl.class)
        try {
            this.add();
        }
        finally {
            UserHighPrizeServiceImpl.isRunning = false;
        }
        UserHighPrizeServiceImpl.isRunning = false;
    }
    
    private void add() {
        if (this.gameBetsQueue != null && this.gameBetsQueue.size() > 0) {
            try {
                final List<GameBets> adds = new LinkedList<GameBets>();
                this.gameBetsQueue.drainTo(adds, 50);
                if (CollectionUtils.isNotEmpty((Collection)adds)) {
                    this.add(adds);
                }
            }
            catch (Exception e) {
                UserHighPrizeServiceImpl.log.error("添加用户大额中奖失败", (Throwable)e);
            }
        }
    }
    
    private void add(final List<GameBets> adds) {
        for (final GameBets add : adds) {
            final UserVO user = this.dataFactory.getUser(add.getUserId());
            if (user == null) {
                continue;
            }
            final UserHighPrize highPrize = this.convert(add, user);
            this.highPrizeDao.add(highPrize);
            this.addToRedis(highPrize, user);
        }
    }
    
    private UserHighPrize convert(final GameBets gameBets, final UserVO user) {
        final UserHighPrize highTimes = new UserHighPrize();
        highTimes.setUserId(user.getId());
        highTimes.setPlatform(gameBets.getPlatformId());
        final SysPlatform sysPlatform = this.dataFactory.getSysPlatform(gameBets.getPlatformId());
        highTimes.setName(sysPlatform.getName());
        highTimes.setNameId(new StringBuilder(String.valueOf(gameBets.getPlatformId())).toString());
        highTimes.setSubName(gameBets.getGameName());
        highTimes.setRefId(new StringBuilder(String.valueOf(gameBets.getId())).toString());
        highTimes.setMoney(gameBets.getMoney());
        highTimes.setPrizeMoney(gameBets.getPrizeMoney());
        double times = gameBets.getPrizeMoney();
        if (gameBets.getMoney() > 0.0) {
            times = gameBets.getPrizeMoney() / gameBets.getMoney();
        }
        times = MathUtil.doubleFormat(times, 3);
        highTimes.setTimes(times);
        highTimes.setTime(gameBets.getTime());
        highTimes.setStatus(0);
        return highTimes;
    }
    
    private void addToRedis(final UserHighPrize highPrize, final UserVO user) {
        final String field = new StringBuilder(String.valueOf(highPrize.getId())).toString();
        final Map<String, Object> value = new HashMap<String, Object>();
        value.put("platform", highPrize.getPlatform());
        value.put("username", user.getUsername());
        value.put("name", highPrize.getName());
        value.put("subName", highPrize.getSubName());
        value.put("refId", highPrize.getRefId());
        value.put("money", highPrize.getMoney());
        value.put("prizeMoney", highPrize.getPrizeMoney());
        value.put("times", highPrize.getTimes());
        value.put("type", 1);
        this.jedisTemplate.hset("USER:HIGH_PRIZE:NOTICE", field, JSON.toJSONString((Object)value));
    }
    
    @Override
    public PageList search(final Integer type, final String username, final Integer platform, final String nameId, final String subName, final String refId, final Double minMoney, final Double maxMoney, final Double minPrizeMoney, final Double maxPrizeMoney, final Double minTimes, final Double maxTimes, final String minTime, final String maxTime, final Integer status, final String confirmUsername, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        final StringBuilder sqlStr = new StringBuilder();
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                sqlStr.append(" and b.user_id = ").append(user.getId());
            }
        }
        if (platform != null) {
            sqlStr.append(" and b.platform = ").append(platform);
        }
        if (StringUtils.isNotEmpty(nameId)) {
            sqlStr.append(" and b.name_id = ").append("'" + nameId + "'");
        }
        if (StringUtil.isNotNull(subName)) {
            sqlStr.append(" and b.sub_name = ").append("'" + subName + "'");
        }
        if (StringUtil.isNotNull(refId)) {
            sqlStr.append(" and b.ref_id = ").append("'" + refId + "'");
        }
        if (minMoney != null) {
            sqlStr.append(" and b.money >= ").append(minMoney);
        }
        if (maxMoney != null) {
            sqlStr.append(" and b.money <= ").append(maxMoney);
        }
        if (minPrizeMoney != null) {
            sqlStr.append(" and b.prize_money >= ").append(minPrizeMoney);
        }
        if (maxPrizeMoney != null) {
            sqlStr.append(" and b.prize_money <= ").append(maxPrizeMoney);
        }
        if (minTimes != null) {
            sqlStr.append(" and b.times >= ").append("'" + minTimes + "'");
        }
        if (maxTimes != null) {
            sqlStr.append(" and b.times <= ").append("'" + maxTimes + "'");
        }
        if (StringUtil.isNotNull(minTime)) {
            sqlStr.append(" and b.time >= ").append("'" + minTime + "'");
        }
        if (StringUtil.isNotNull(maxTime)) {
            sqlStr.append(" and b.time <= ").append("'" + maxTime + "'");
        }
        if (status != null) {
            sqlStr.append(" and b.status = ").append(status);
        }
        if (StringUtil.isNotNull(confirmUsername)) {
            sqlStr.append(" and b.confirm_username = ").append("'" + confirmUsername + "'");
        }
        sqlStr.append(" and b.id > ").append(0);
        final String nickname = "试玩用户";
        if (type != null) {
            sqlStr.append("  and u.type = ").append(type);
        }
        else {
            sqlStr.append("  and u.upid != ").append(0);
        }
        sqlStr.append("  and u.upid != ").append(0);
        sqlStr.append(" order by b.id desc ");
        final List<UserHighPrizeTimesVO> list = new ArrayList<UserHighPrizeTimesVO>();
        final PageList pList = this.highPrizeDao.find(sqlStr.toString(), start, limit);
        for (final Object tmpBean : pList.getList()) {
            final UserHighPrizeTimesVO tmpVO = new UserHighPrizeTimesVO((UserHighPrize)tmpBean, this.dataFactory);
            list.add(tmpVO);
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public UserHighPrize getById(final int id) {
        return this.highPrizeDao.getById(id);
    }
    
    @Override
    public synchronized boolean lock(final int id, final String username) {
        final UserHighPrize highPrize = this.highPrizeDao.getById(id);
        if (highPrize == null) {
            return false;
        }
        if (highPrize.getStatus() == 0) {
            return this.highPrizeDao.updateStatusAndConfirmUsername(id, 1, username);
        }
        return highPrize.getStatus() == 1 && StringUtils.equals(username, highPrize.getConfirmUsername());
    }
    
    @Override
    public synchronized boolean unlock(final int id, final String username) {
        final UserHighPrize highPrize = this.highPrizeDao.getById(id);
        return highPrize != null && (highPrize.getStatus() == 1 && username.equals(highPrize.getConfirmUsername())) && this.highPrizeDao.updateStatusAndConfirmUsername(id, 0, null);
    }
    
    @Override
    public synchronized boolean confirm(final int id, final String username) {
        final UserHighPrize highPrize = this.highPrizeDao.getById(id);
        return highPrize != null && (highPrize.getStatus() == 1 && username.equals(highPrize.getConfirmUsername())) && this.highPrizeDao.updateStatus(id, 2);
    }
    
    @Override
    public void addIfNecessary(final GameBets gameBets) {
        if (gameBets.getPrizeMoney() <= 0.0) {
            return;
        }
        if (gameBets.getPrizeMoney() <= 100.0 && gameBets.getMoney() <= 100.0) {
            return;
        }
        double times = gameBets.getPrizeMoney();
        if (gameBets.getMoney() > 0.0) {
            times = gameBets.getPrizeMoney() / gameBets.getMoney();
        }
        if (gameBets.getPrizeMoney() >= 5000.0 || times >= 13.0) {
            this.gameBetsQueue.offer(gameBets);
        }
    }
    
    @Override
    public int getUnProcessCount() {
        return this.highPrizeDao.getUnProcessCount();
    }
    
    @Override
    public Map<String, String> getAllHighPrizeNotices() {
        return this.jedisTemplate.hgetAll("USER:HIGH_PRIZE:NOTICE");
    }
    
    @Override
    public void delHighPrizeNotice(final String field) {
        this.jedisTemplate.hdel("USER:HIGH_PRIZE:NOTICE", field);
    }
}
