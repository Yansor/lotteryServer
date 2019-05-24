package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javautils.StringUtil;
import lottery.domains.content.biz.UserBaccaratReportService;
import lottery.domains.content.dao.UserBaccaratReportDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserBaccaratReport;
import lottery.domains.content.vo.bill.UserBaccaratReportVO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBaccaratReportServiceImpl
  implements UserBaccaratReportService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBaccaratReportDao uBaccaratReportDao;
  
  public boolean update(int userId, int type, double amount, String time)
  {
    UserBaccaratReport entity = new UserBaccaratReport();
    switch (type)
    {
    case 3: 
      entity.setTransIn(amount);
      break;
    case 4: 
      entity.setTransOut(amount);
      break;
    case 6: 
      entity.setSpend(amount);
      break;
    case 7: 
      entity.setPrize(amount);
      break;
    case 11: 
      entity.setWaterReturn(amount);
      break;
    case 9: 
      entity.setProxyReturn(amount);
      break;
    case 10: 
      entity.setCancelOrder(amount);
      break;
    case 5: 
      entity.setActivity(amount);
      break;
    case 8: 
    default: 
      return false;
    }
    UserBaccaratReport bean = this.uBaccaratReportDao.get(userId, time);
    if (bean != null)
    {
      entity.setId(bean.getId());
      return this.uBaccaratReportDao.update(entity);
    }
    entity.setUserId(userId);
    entity.setTime(time);
    return this.uBaccaratReportDao.add(entity);
  }
  
  public List<UserBaccaratReportVO> report(String sTime, String eTime)
  {
    Map<Integer, User> lowerUsersMap = new HashMap();
    List<User> lowerUserList = this.uDao.listAll();
    List<User> directUserList = this.uDao.getUserDirectLower(0);
    
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    for (User tmpUser : lowerUserList) {
      lowerUsersMap.put(Integer.valueOf(tmpUser.getId()), tmpUser);
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("time", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.lt("time", eTime));
    }
    List<UserBaccaratReport> result = this.uBaccaratReportDao.find(criterions, orders);
    Object resultMap = new HashMap();
    UserBaccaratReportVO tBean = new UserBaccaratReportVO("总计");
    for (UserBaccaratReport tmpBean : result)
    {
      User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
      if (thisUser.getUpid() == 0)
      {
        if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
          ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new UserBaccaratReportVO(thisUser.getUsername()));
        }
        ((UserBaccaratReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
      }
      else
      {
        for (Iterator localIterator3 = directUserList.iterator(); localIterator3.hasNext();)
        {
          User tmpUser = (User)localIterator3.next();
          if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), new UserBaccaratReportVO(tmpUser.getUsername()));
            }
            ((UserBaccaratReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
          }
        }
      }
      tBean.addBean(tmpBean);
    }
    List<UserBaccaratReportVO> list = new ArrayList();
    list.add(tBean);
    Object[] keys = ((Map)resultMap).keySet().toArray();
    Arrays.sort(keys);
    Object[] arrayOfObject1;
    int localUser1 = (arrayOfObject1 = keys).length;
    for (int tmpUser = 0; tmpUser < localUser1; tmpUser++)
    {
      Object o = arrayOfObject1[tmpUser];
      list.add((UserBaccaratReportVO)((Map)resultMap).get(o));
    }
    return list;
  }
  
  public List<UserBaccaratReportVO> report(int userId, String sTime, String eTime)
  {
    User targetUser = this.uDao.getById(userId);
    if (targetUser != null)
    {
      Map<Integer, User> lowerUsersMap = new HashMap();
      List<User> lowerUserList = this.uDao.getUserLower(targetUser.getId());
      List<User> directUserList = this.uDao.getUserDirectLower(targetUser.getId());
      
      List<Criterion> criterions = new ArrayList();
      List<Order> orders = new ArrayList();
      List<Integer> toUids = new ArrayList();
      toUids.add(Integer.valueOf(targetUser.getId()));
      for (User tmpUser : lowerUserList)
      {
        toUids.add(Integer.valueOf(tmpUser.getId()));
        lowerUsersMap.put(Integer.valueOf(tmpUser.getId()), tmpUser);
      }
      if (StringUtil.isNotNull(sTime)) {
        criterions.add(Restrictions.ge("time", sTime));
      }
      if (StringUtil.isNotNull(eTime)) {
        criterions.add(Restrictions.lt("time", eTime));
      }
      criterions.add(Restrictions.in("userId", toUids));
      List<UserBaccaratReport> result = this.uBaccaratReportDao.find(criterions, orders);
      Object resultMap = new HashMap();
      UserBaccaratReportVO tBean = new UserBaccaratReportVO("总计");
      for (UserBaccaratReport tmpBean : result)
      {
        if (tmpBean.getUserId() == targetUser.getId())
        {
          if (!((Map)resultMap).containsKey(Integer.valueOf(targetUser.getId()))) {
            ((Map)resultMap).put(Integer.valueOf(targetUser.getId()), new UserBaccaratReportVO(targetUser.getUsername()));
          }
          ((UserBaccaratReportVO)((Map)resultMap).get(Integer.valueOf(targetUser.getId()))).addBean(tmpBean);
        }
        else
        {
          User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
          if (thisUser.getUpid() == targetUser.getId())
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new UserBaccaratReportVO(thisUser.getUsername()));
            }
            ((UserBaccaratReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
          }
          else
          {
            for (Iterator localIterator3 = directUserList.iterator(); localIterator3.hasNext();)
            {
              User tmpUser = (User)localIterator3.next();
              if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
              {
                if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
                  ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), new UserBaccaratReportVO(tmpUser.getUsername()));
                }
                ((UserBaccaratReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
              }
            }
          }
        }
        tBean.addBean(tmpBean);
      }
      List<UserBaccaratReportVO> list = new ArrayList();
      list.add(tBean);
      Object[] keys = ((Map)resultMap).keySet().toArray();
      Arrays.sort(keys);
      Object[] arrayOfObject1;
      int localUser1 = (arrayOfObject1 = keys).length;
      for (int tmpUser = 0; tmpUser < localUser1; tmpUser++)
      {
        Object o = arrayOfObject1[tmpUser];
        list.add((UserBaccaratReportVO)((Map)resultMap).get(o));
      }
      return list;
    }
    return null;
  }
}
