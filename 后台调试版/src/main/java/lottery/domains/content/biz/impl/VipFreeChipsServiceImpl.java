package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.VipFreeChipsService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.VipFreeChipsDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.VipFreeChips;
import lottery.domains.content.vo.config.VipConfig;
import lottery.domains.content.vo.vip.VipFreeChipsVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VipFreeChipsServiceImpl
  implements VipFreeChipsService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private VipFreeChipsDao vFreeChipsDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public PageList search(String username, Integer level, String date, Integer status, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    if (level != null) {
      criterions.add(Restrictions.eq("level", Integer.valueOf(level.intValue())));
    }
    if (StringUtil.isNotNull(date)) {
      criterions.add(Restrictions.like("time", date, MatchMode.ANYWHERE));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", status));
    }
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<VipFreeChipsVO> list = new ArrayList();
      PageList pList = this.vFreeChipsDao.find(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new VipFreeChipsVO((VipFreeChips)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public boolean calculate()
  {
    List<User> ulist = this.uDao.listAll();
    
    String sTime = new Moment().day(10).toSimpleDate();
    String eTime = new Moment().day(10).add(1, "months").toSimpleDate();
    String thisTime = new Moment().toSimpleTime();
    VipConfig vipConfig = this.lotteryDataFactory.getVipConfig();
    double[] freeChips = vipConfig.getFreeChips();
    for (User tmpBean : ulist) {
      try
      {
        int vipLevel = tmpBean.getVipLevel();
        double freeMoney = freeChips[vipLevel];
        if (freeMoney > 0.0D)
        {
          boolean hasRecord = this.vFreeChipsDao.hasRecord(tmpBean.getId(), sTime, eTime);
          if (!hasRecord)
          {
            int status = 0;
            int isReceived = 0;
            VipFreeChips entity = new VipFreeChips(tmpBean.getId(), tmpBean.getVipLevel(), freeMoney, sTime, eTime, thisTime, status, isReceived);
            this.vFreeChipsDao.add(entity);
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return true;
  }
  
  public boolean agreeAll()
  {
    List<VipFreeChips> list = this.vFreeChipsDao.getUntreated();
    for (VipFreeChips tmpBean : list) {
      if (tmpBean.getStatus() == 0)
      {
        tmpBean.setStatus(1);
        this.vFreeChipsDao.update(tmpBean);
      }
    }
    return true;
  }
}
