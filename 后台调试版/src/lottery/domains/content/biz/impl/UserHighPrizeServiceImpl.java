package lottery.domains.content.biz.impl;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import javautils.redis.JedisTemplate;
import lottery.domains.content.biz.UserHighPrizeService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserHighPrizeDao;
import lottery.domains.content.entity.GameBets;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserHighPrize;
import lottery.domains.content.vo.user.UserHighPrizeTimesVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHighPrizeServiceImpl
  implements UserHighPrizeService
{
  private static final Logger log = LoggerFactory.getLogger(UserHighPrizeServiceImpl.class);
  private static final String USER_HIGH_PRIZE_NOTICE_KEY = "USER:HIGH_PRIZE:NOTICE";
  private BlockingQueue<GameBets> gameBetsQueue = new LinkedBlockingDeque();
  @Autowired
  private UserHighPrizeDao highPrizeDao;
  @Autowired
  private JedisTemplate jedisTemplate;
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  private static boolean isRunning = false;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="0/3 * * * * *")
  public void run()
  {
    // Byte code:
    //   0: ldc 1
    //   2: dup
    //   3: astore_1
    //   4: monitorenter
    //   5: getstatic 41	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
    //   8: ifeq +6 -> 14
    //   11: aload_1
    //   12: monitorexit
    //   13: return
    //   14: iconst_1
    //   15: putstatic 41	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
    //   18: aload_1
    //   19: monitorexit
    //   20: goto +6 -> 26
    //   23: aload_1
    //   24: monitorexit
    //   25: athrow
    //   26: aload_0
    //   27: invokespecial 59	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:add	()V
    //   30: goto +10 -> 40
    //   33: astore_1
    //   34: iconst_0
    //   35: putstatic 41	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
    //   38: aload_1
    //   39: athrow
    //   40: iconst_0
    //   41: putstatic 41	lottery/domains/content/biz/impl/UserHighPrizeServiceImpl:isRunning	Z
    //   44: return
    // Line number table:
    //   Java source line #60	-> byte code offset #0
    //   Java source line #61	-> byte code offset #5
    //   Java source line #63	-> byte code offset #11
    //   Java source line #65	-> byte code offset #14
    //   Java source line #60	-> byte code offset #18
    //   Java source line #68	-> byte code offset #26
    //   Java source line #69	-> byte code offset #30
    //   Java source line #70	-> byte code offset #34
    //   Java source line #71	-> byte code offset #38
    //   Java source line #70	-> byte code offset #40
    //   Java source line #72	-> byte code offset #44
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	45	0	this	UserHighPrizeServiceImpl
    //   3	36	1	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   5	13	23	finally
    //   14	20	23	finally
    //   23	25	23	finally
    //   26	33	33	finally
  }
  
  private void add()
  {
    if ((this.gameBetsQueue != null) && (this.gameBetsQueue.size() > 0)) {
      try
      {
        List<GameBets> adds = new LinkedList();
        this.gameBetsQueue.drainTo(adds, 50);
        if (CollectionUtils.isNotEmpty(adds)) {
          add(adds);
        }
      }
      catch (Exception e)
      {
        log.error("添加用户大额中奖失败", e);
      }
    }
  }
  
  private void add(List<GameBets> adds)
  {
    for (GameBets add : adds)
    {
      UserVO user = this.dataFactory.getUser(add.getUserId());
      if (user != null)
      {
        UserHighPrize highPrize = convert(add, user);
        
        this.highPrizeDao.add(highPrize);
        
        addToRedis(highPrize, user);
      }
    }
  }
  
  private UserHighPrize convert(GameBets gameBets, UserVO user)
  {
    UserHighPrize highTimes = new UserHighPrize();
    highTimes.setUserId(user.getId());
    highTimes.setPlatform(gameBets.getPlatformId());
    SysPlatform sysPlatform = this.dataFactory.getSysPlatform(gameBets.getPlatformId());
    highTimes.setName(sysPlatform.getName());
    highTimes.setNameId(gameBets.getPlatformId()+"");
    highTimes.setSubName(gameBets.getGameName());
    highTimes.setRefId(gameBets.getId() +"");
    highTimes.setMoney(gameBets.getMoney());
    highTimes.setPrizeMoney(gameBets.getPrizeMoney());
    
    double times = gameBets.getPrizeMoney();
    if (gameBets.getMoney() > 0.0D) {
      times = gameBets.getPrizeMoney() / gameBets.getMoney();
    }
    times = MathUtil.doubleFormat(times, 3);
    highTimes.setTimes(times);
    highTimes.setTime(gameBets.getTime());
    highTimes.setStatus(0);
    
    return highTimes;
  }
  
  private void addToRedis(UserHighPrize highPrize, UserVO user)
  {
    String field = highPrize.getId()+"";
    Map<String, Object> value = new HashMap();
    
    value.put("platform", Integer.valueOf(highPrize.getPlatform()));
    value.put("username", user.getUsername());
    value.put("name", highPrize.getName());
    value.put("subName", highPrize.getSubName());
    value.put("refId", highPrize.getRefId());
    value.put("money", Double.valueOf(highPrize.getMoney()));
    value.put("prizeMoney", Double.valueOf(highPrize.getPrizeMoney()));
    value.put("times", Double.valueOf(highPrize.getTimes()));
    value.put("type", Integer.valueOf(1));
    
    this.jedisTemplate.hset("USER:HIGH_PRIZE:NOTICE", field, JSON.toJSONString(value));
  }
  
  public PageList search(Integer type, String username, Integer platform, String nameId, String subName, String refId, Double minMoney, Double maxMoney, Double minPrizeMoney, Double maxPrizeMoney, Double minTimes, Double maxTimes, String minTime, String maxTime, Integer status, String confirmUsername, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    StringBuilder sqlStr = new StringBuilder();
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
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
    String nickname = "试玩用户";
    if (type != null) {
      sqlStr.append("  and u.type = ").append(type);
    } else {
      sqlStr.append("  and u.upid != ").append(0);
    }
    sqlStr.append("  and u.upid != ").append(0);
    sqlStr.append(" order by b.id desc ");
    
    List<UserHighPrizeTimesVO> list = new ArrayList();
    PageList pList = this.highPrizeDao.find(sqlStr.toString(), start, limit);
    for (Object tmpBean : pList.getList())
    {
      UserHighPrizeTimesVO tmpVO = new UserHighPrizeTimesVO((UserHighPrize)tmpBean, this.dataFactory);
      list.add(tmpVO);
    }
    pList.setList(list);
    return pList;
  }
  
  public UserHighPrize getById(int id)
  {
    return this.highPrizeDao.getById(id);
  }
  
  public synchronized boolean lock(int id, String username)
  {
    UserHighPrize highPrize = this.highPrizeDao.getById(id);
    if (highPrize == null) {
      return false;
    }
    if (highPrize.getStatus() == 0) {
      return this.highPrizeDao.updateStatusAndConfirmUsername(id, 1, username);
    }
    if ((highPrize.getStatus() == 1) && (StringUtils.equals(username, highPrize.getConfirmUsername()))) {
      return true;
    }
    return false;
  }
  
  public synchronized boolean unlock(int id, String username)
  {
    UserHighPrize highPrize = this.highPrizeDao.getById(id);
    if (highPrize == null) {
      return false;
    }
    if ((highPrize.getStatus() == 1) && (username.equals(highPrize.getConfirmUsername()))) {
      return this.highPrizeDao.updateStatusAndConfirmUsername(id, 0, null);
    }
    return false;
  }
  
  public synchronized boolean confirm(int id, String username)
  {
    UserHighPrize highPrize = this.highPrizeDao.getById(id);
    if (highPrize == null) {
      return false;
    }
    if ((highPrize.getStatus() == 1) && (username.equals(highPrize.getConfirmUsername()))) {
      return this.highPrizeDao.updateStatus(id, 2);
    }
    return false;
  }
  
  public void addIfNecessary(GameBets gameBets)
  {
    if (gameBets.getPrizeMoney() <= 0.0D) {
      return;
    }
    if ((gameBets.getPrizeMoney() <= 100.0D) && (gameBets.getMoney() <= 100.0D)) {
      return;
    }
    double times = gameBets.getPrizeMoney();
    if (gameBets.getMoney() > 0.0D) {
      times = gameBets.getPrizeMoney() / gameBets.getMoney();
    }
    if ((gameBets.getPrizeMoney() >= 5000.0D) || (times >= 13.0D)) {
      this.gameBetsQueue.offer(gameBets);
    }
  }
  
  public int getUnProcessCount()
  {
    return this.highPrizeDao.getUnProcessCount();
  }
  
  public Map<String, String> getAllHighPrizeNotices()
  {
    return this.jedisTemplate.hgetAll("USER:HIGH_PRIZE:NOTICE");
  }
  
  public void delHighPrizeNotice(String field)
  {
    this.jedisTemplate.hdel("USER:HIGH_PRIZE:NOTICE", new String[] { field });
  }
}
