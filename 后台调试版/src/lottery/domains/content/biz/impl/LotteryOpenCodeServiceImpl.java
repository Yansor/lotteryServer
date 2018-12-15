package lottery.domains.content.biz.impl;

import admin.web.WebJSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import javautils.redis.JedisTemplate;
import lottery.domains.content.biz.LotteryOpenCodeService;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.dao.LotteryOpenCodeDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.lottery.LotteryOpenCodeVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import lottery.web.content.validate.CodeValidate;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryOpenCodeServiceImpl
  implements LotteryOpenCodeService
{
  private static final String OPEN_CODE_KEY = "OPEN_CODE:%s";
  private static final String ADMIN_OPEN_CODE_KEY = "ADMIN_OPEN_CODE:%s";
  private static final int OPEN_CODE_MOST_EXPECT = 50;
  @Autowired
  private CodeValidate codeValidate;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private LotteryOpenCodeDao lotteryOpenCodeDao;
  @Autowired
  private JedisTemplate jedisTemplate;
  @Autowired
  private LotteryOpenUtil lotteryOpenUtil;
  @Autowired
  private LotteryDao lotteryDao;
  
  public PageList search(String lottery, String expect, int start, int limit)
  {
    if (start < 0) {
      start = 0;
    }
    if (limit < 0) {
      limit = 10;
    }
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(lottery)) {
      criterions.add(Restrictions.eq("lottery", lottery));
    }
    if (StringUtil.isNotNull(expect)) {
      criterions.add(Restrictions.eq("expect", expect));
    }
    orders.add(Order.desc("expect"));
    PageList pList = this.lotteryOpenCodeDao.find(criterions, orders, start, limit);
    List<LotteryOpenCodeVO> list = new ArrayList();
    for (Object tmpBean : pList.getList()) {
      list.add(new LotteryOpenCodeVO((LotteryOpenCode)tmpBean, this.lotteryDataFactory));
    }
    pList.setList(list);
    return pList;
  }
  
  public LotteryOpenCodeVO get(String lottery, String expect)
  {
    LotteryOpenCode entity = this.lotteryOpenCodeDao.get(lottery, expect);
    if (entity != null)
    {
      LotteryOpenCodeVO bean = new LotteryOpenCodeVO(entity, this.lotteryDataFactory);
      return bean;
    }
    return null;
  }
  
  public boolean add(WebJSONObject json, String lottery, String expect, String code, String opername)
  {
    if (!this.codeValidate.validateExpect(json, lottery, expect))
    {
      Lottery lo = this.lotteryDao.getByShortName(lottery);
      if ((lo != null) && (lo.getSelf() == 1))
      {
        String key = String.format("ADMIN_OPEN_CODE:%s", new Object[] { lottery });
        this.jedisTemplate.hset(key, expect, code);
        return true;
      }
      return false;
    }
    int openStatus = 0;
    if (("txffc".equals(lottery)) || ("txlhd".equals(lottery)))
    {
      String lastExpect = this.lotteryOpenUtil.subtractExpect(lottery, expect);
      LotteryOpenCode lastOpenCode = this.lotteryOpenCodeDao.get(lottery, lastExpect);
      if ((lastOpenCode != null) && 
        (lastOpenCode.getCode().equals(code))) {
        openStatus = 2;
      }
    }
    LotteryOpenCode entity = this.lotteryOpenCodeDao.get(lottery, expect);
    if (entity == null)
    {
      String time = new Moment().toSimpleTime();
      LotteryOpenCode bean = new LotteryOpenCode();
      bean.setLottery(lottery);
      bean.setExpect(expect);
      bean.setCode(code);
      bean.setTime(time);
      bean.setInterfaceTime(time);
      bean.setOpenStatus(Integer.valueOf(openStatus));
      bean.setRemarks(opername);
      
      boolean result = this.lotteryOpenCodeDao.add(bean);
      if (result) {
        addedToRedis(bean);
      }
      return result;
    }
    entity.setCode(code);
    entity.setRemarks(opername);
    entity.setOpenStatus(Integer.valueOf(openStatus));
    boolean result = this.lotteryOpenCodeDao.update(entity);
    if (result) {
      addedToRedis(entity);
    }
    return result;
  }
  
  public boolean delete(LotteryOpenCode bean)
  {
    return this.lotteryOpenCodeDao.delete(bean);
  }
  
  public LotteryOpenCode getFirstExpectByInterfaceTime(String lottery, String startTime, String endTime)
  {
    return this.lotteryOpenCodeDao.getFirstExpectByInterfaceTime(lottery, startTime, endTime);
  }
  
  public int countByInterfaceTime(String lottery, String startTime, String endTime)
  {
    return this.lotteryOpenCodeDao.countByInterfaceTime(lottery, startTime, endTime);
  }
  
  public List<LotteryOpenCode> getLatest(String lottery, int count)
  {
    return this.lotteryOpenCodeDao.getLatest(lottery, count);
  }
  
  private void addedToRedis(LotteryOpenCode entity)
  {
    String key = String.format("OPEN_CODE:%s", new Object[] { entity.getLottery() });
    if ("jsmmc".equals(entity.getLottery())) {
      return;
    }
    Set<String> hkeys = this.jedisTemplate.hkeys(key);
    if (CollectionUtils.isEmpty(hkeys))
    {
      this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
    }
    else
    {
      TreeSet<String> sortHKeys = new TreeSet(hkeys);
      
      String[] expects = (String[])sortHKeys.toArray(new String[0]);
      String firstExpect = expects[0];
      if (entity.getExpect().compareTo(firstExpect) > 0)
      {
        this.jedisTemplate.hset(key, entity.getExpect(), entity.getCode());
        sortHKeys.add(entity.getExpect());
      }
      if ((CollectionUtils.isNotEmpty(sortHKeys)) && (sortHKeys.size() > 50))
      {
        int exceedSize = sortHKeys.size() - 50;
        
        Iterator<String> iterator = sortHKeys.iterator();
        int count = 0;
        List<String> delFields = new ArrayList();
        while (iterator.hasNext())
        {
          if (count >= exceedSize) {
            break;
          }
          delFields.add((String)iterator.next());
          iterator.remove();
          count++;
        }
        this.jedisTemplate.hdel(key, (String[])delFields.toArray(new String[0]));
      }
    }
  }
}
