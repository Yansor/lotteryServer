package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javautils.StringUtil;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserMainReportDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserMainReport;
import lottery.domains.content.vo.bill.UserMainReportVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMainReportServiceImpl
  implements UserMainReportService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserMainReportDao uMainReportDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public boolean update(int userId, int type, double amount, String time)
  {
    UserMainReport entity = new UserMainReport();
    switch (type)
    {
    case 1: 
      entity.setRecharge(amount);
      break;
    case 2: 
      entity.setWithdrawals(amount);
      break;
    case 3: 
      entity.setTransIn(amount);
      break;
    case 4: 
      entity.setTransOut(amount);
      break;
    case 5: 
      entity.setActivity(amount);
      break;
    default: 
      return false;
    }
    UserMainReport bean = this.uMainReportDao.get(userId, time);
    if (bean != null)
    {
      entity.setId(bean.getId());
      return this.uMainReportDao.update(entity);
    }
    entity.setUserId(userId);
    entity.setTime(time);
    return this.uMainReportDao.add(entity);
  }
  
  public List<UserMainReportVO> report(String sTime, String eTime)
  {
    List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
    
    List<UserMainReportVO> userReports = new ArrayList(managerIds.size());
    UserVO user;
    for (Integer managerId : managerIds)
    {
      UserMainReportVO reportVO = this.uMainReportDao.sumLowersAndSelf(managerId.intValue(), sTime, eTime);
      if ((reportVO.getRecharge() > 0.0D) || (reportVO.getWithdrawals() > 0.0D) || (reportVO.getTransIn() > 0.0D) || 
        (reportVO.getTransOut() > 0.0D) || (reportVO.getAccountIn() > 0.0D) || 
        (reportVO.getAccountOut() > 0.0D) || (reportVO.getActivity() > 0.0D))
      {
        user = this.lotteryDataFactory.getUser(managerId.intValue());
        if (user != null)
        {
          reportVO.setHasMore(true);
          reportVO.setName(user.getUsername());
          userReports.add(reportVO);
        }
      }
    }
    List<UserMainReportVO> result = new ArrayList(userReports.size() + 1);
    UserMainReportVO tBean = new UserMainReportVO("总计");
    for (UserMainReportVO userReport : userReports) {
      tBean.addBean(userReport);
    }
    result.add(tBean);
    result.addAll(userReports);
    return result;
  }
  
  public List<UserMainReportVO> report(int userId, String sTime, String eTime)
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
      List<UserMainReport> result = this.uMainReportDao.find(criterions, orders);
      Object resultMap = new HashMap();
      UserMainReportVO tBean = new UserMainReportVO("总计");
      for (UserMainReport tmpBean : result)
      {
        if (tmpBean.getUserId() == targetUser.getId())
        {
          if (!((Map)resultMap).containsKey(Integer.valueOf(targetUser.getId()))) {
            ((Map)resultMap).put(Integer.valueOf(targetUser.getId()), new UserMainReportVO(targetUser.getUsername()));
          }
          ((UserMainReportVO)((Map)resultMap).get(Integer.valueOf(targetUser.getId()))).addBean(tmpBean);
        }
        else
        {
          User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
          if (thisUser.getUpid() == targetUser.getId())
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new UserMainReportVO(thisUser.getUsername()));
            }
            ((UserMainReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
          }
          else
          {
            for (User tmpUser : directUserList) {
              if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
              {
                if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
                  ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), new UserMainReportVO(tmpUser.getUsername()));
                }
                ((UserMainReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
              }
            }
          }
        }
        tBean.addBean(tmpBean);
      }
      User lowerUser;
      for (Integer lowerUserId : ((Map<Integer,UserMainReportVO>)resultMap).keySet())
      {
        UserMainReportVO reportVO = (UserMainReportVO)((Map)resultMap).get(lowerUserId);
        for (Iterator<UserMainReport> userMainReportIterator = result.iterator(); userMainReportIterator.hasNext();)
        {
          UserMainReport report = (UserMainReport)userMainReportIterator.next();
          lowerUser = (User)lowerUsersMap.get(Integer.valueOf(report.getUserId()));
          if ((lowerUser != null) && (lowerUser.getUpid() == lowerUserId.intValue()))
          {
            reportVO.setHasMore(true);
            break;
          }
        }
      }
      List<UserMainReportVO> list = new ArrayList();
      list.add(tBean);
      Object[] keys = ((Map)resultMap).keySet().toArray();
      Arrays.sort(keys);
      int localUserMainReport1 = keys.length;
      for (int report = 0; report < localUserMainReport1; report++)
      {
        Object o = keys[report];
        list.add((UserMainReportVO)((Map)resultMap).get(o));
      }
      return list;
    }
    return null;
  }
}
