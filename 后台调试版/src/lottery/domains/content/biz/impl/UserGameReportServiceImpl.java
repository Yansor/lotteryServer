package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javautils.StringUtil;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserGameReportDao;
import lottery.domains.content.entity.HistoryUserGameReport;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserGameReport;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGameReportServiceImpl
  implements UserGameReportService
{
  @Autowired
  private UserGameReportDao uGameReportDao;
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public boolean update(int userId, int platformId, double billingOrder, double prize, double waterReturn, double proxyReturn, String time)
  {
    UserGameReport entity = new UserGameReport();
    entity.setBillingOrder(billingOrder);
    entity.setPrize(prize);
    entity.setWaterReturn(waterReturn);
    entity.setProxyReturn(proxyReturn);
    entity.setTime(time);
    
    UserGameReport userGameReport = this.uGameReportDao.get(userId, platformId, time);
    if (userGameReport != null)
    {
      entity.setId(userGameReport.getId());
      return this.uGameReportDao.update(entity);
    }
    entity.setUserId(userId);
    entity.setPlatformId(platformId);
    return this.uGameReportDao.save(entity);
  }
  
  public List<UserGameReportVO> report(String sTime, String eTime)
  {
    List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
    
    List<UserGameReportVO> userReports = new ArrayList(managerIds.size());
    UserVO user;
    for (Integer managerId : managerIds)
    {
      UserGameReportVO reportVO = this.uGameReportDao.sumLowersAndSelf(managerId.intValue(), sTime, eTime);
      if ((reportVO.getTransIn() > 0.0D) || (reportVO.getTransOut() > 0.0D) || (reportVO.getPrize() > 0.0D) || 
        (reportVO.getWaterReturn() > 0.0D) || (reportVO.getProxyReturn() > 0.0D) || 
        (reportVO.getActivity() > 0.0D) || (reportVO.getBillingOrder() > 0.0D))
      {
        user = this.lotteryDataFactory.getUser(managerId.intValue());
        if (user != null)
        {
          reportVO.setName(user.getUsername());
          userReports.add(reportVO);
        }
      }
    }
    List<UserGameReportVO> result = new ArrayList(userReports.size() + 1);
    UserGameReportVO tBean = new UserGameReportVO("总计");
    for (UserGameReportVO userReport : userReports) {
      tBean.addBean(userReport);
    }
    result.add(tBean);
    result.addAll(userReports);
    return result;
  }
  
  public List<HistoryUserGameReportVO> historyReport(String sTime, String eTime)
  {
    List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
    
    List<HistoryUserGameReportVO> userReports = new ArrayList(managerIds.size());
    UserVO user;
    for (Integer managerId : managerIds)
    {
      HistoryUserGameReportVO reportVO = this.uGameReportDao.historySumLowersAndSelf(managerId.intValue(), sTime, eTime);
      if ((reportVO.getTransIn() > 0.0D) || (reportVO.getTransOut() > 0.0D) || (reportVO.getPrize() > 0.0D) || 
        (reportVO.getWaterReturn() > 0.0D) || (reportVO.getProxyReturn() > 0.0D) || 
        (reportVO.getActivity() > 0.0D) || (reportVO.getBillingOrder() > 0.0D))
      {
        user = this.lotteryDataFactory.getUser(managerId.intValue());
        if (user != null)
        {
          reportVO.setName(user.getUsername());
          userReports.add(reportVO);
        }
      }
    }
    List<HistoryUserGameReportVO> result = new ArrayList(userReports.size() + 1);
    HistoryUserGameReportVO tBean = new HistoryUserGameReportVO("总计");
    for (HistoryUserGameReportVO userReport : userReports) {
      tBean.addBean(userReport);
    }
    result.add(tBean);
    result.addAll(userReports);
    return result;
  }
  
  public List<UserGameReportVO> report(int userId, String sTime, String eTime)
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
      List<UserGameReport> result = this.uGameReportDao.find(criterions, orders);
      Object resultMap = new HashMap();
      UserGameReportVO tBean = new UserGameReportVO("总计");
      for (UserGameReport tmpBean : result)
      {
        if (tmpBean.getUserId() == targetUser.getId())
        {
          if (!((Map)resultMap).containsKey(Integer.valueOf(targetUser.getId()))) {
            ((Map)resultMap).put(Integer.valueOf(targetUser.getId()), new UserGameReportVO(targetUser.getUsername()));
          }
          ((UserGameReportVO)((Map)resultMap).get(Integer.valueOf(targetUser.getId()))).addBean(tmpBean);
        }
        else
        {
          User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
          if (thisUser.getUpid() == targetUser.getId())
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new UserGameReportVO(thisUser.getUsername()));
            }
            ((UserGameReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
          }
          else
          {
            for (User tmpUser : directUserList) {
              if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
              {
                if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
                  ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), new UserGameReportVO(tmpUser.getUsername()));
                }
                ((UserGameReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
              }
            }
          }
        }
        tBean.addBean(tmpBean);
      }
      User lowerUser;
      for (Integer lowerUserId : ((Map<Integer,UserGameReportVO>)resultMap).keySet())
      {
        UserGameReportVO reportVO = (UserGameReportVO)((Map)resultMap).get(lowerUserId);
        for (Iterator<UserGameReport> userGameReportIterator = result.iterator(); userGameReportIterator.hasNext();)
        {
          UserGameReport userGameReportIterator1 = (UserGameReport)userGameReportIterator.next();
          lowerUser = (User)lowerUsersMap.get(Integer.valueOf(userGameReportIterator1.getUserId()));
          if ((lowerUser != null) && (lowerUser.getUpid() == lowerUserId.intValue()))
          {
            reportVO.setHasMore(true);
            break;
          }
        }
      }
      List<UserGameReportVO> list = new ArrayList();
      list.add(tBean);
      Object[] keys = ((Map)resultMap).keySet().toArray();
      Arrays.sort(keys);
      int keyLength = keys.length;
      for (int report = 0; report < keyLength; report++)
      {
        Object o = keys[report];
        list.add((UserGameReportVO)((Map)resultMap).get(o));
      }
      return list;
    }
    return null;
  }
  
  public List<HistoryUserGameReportVO> historyReport(int userId, String sTime, String eTime)
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
      List<HistoryUserGameReport> result = this.uGameReportDao.findHistory(criterions, orders);
      Object resultMap = new HashMap();
      HistoryUserGameReportVO tBean = new HistoryUserGameReportVO("总计");
      for (HistoryUserGameReport tmpBean : result)
      {
        if (tmpBean.getUserId() == targetUser.getId())
        {
          if (!((Map)resultMap).containsKey(Integer.valueOf(targetUser.getId()))) {
            ((Map)resultMap).put(Integer.valueOf(targetUser.getId()), new HistoryUserGameReportVO(targetUser.getUsername()));
          }
          ((HistoryUserGameReportVO)((Map)resultMap).get(Integer.valueOf(targetUser.getId()))).addBean(tmpBean);
        }
        else
        {
          User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
          if (thisUser.getUpid() == targetUser.getId())
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new HistoryUserGameReportVO(thisUser.getUsername()));
            }
            ((HistoryUserGameReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
          }
          else
          {
            for (User tmpUser : directUserList) {
              if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
              {
                if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
                  ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), new HistoryUserGameReportVO(tmpUser.getUsername()));
                }
                ((HistoryUserGameReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
              }
            }
          }
        }
        tBean.addBean(tmpBean);
      }
      User lowerUser;
      for (Integer lowerUserId : ((Map<Integer,HistoryUserGameReportVO>)resultMap).keySet())
      {
        HistoryUserGameReportVO reportVO = (HistoryUserGameReportVO)((Map)resultMap).get(lowerUserId);
        for (Iterator<HistoryUserGameReport> historyUserGameReportIterator = result.iterator(); historyUserGameReportIterator.hasNext();)
        {
          HistoryUserGameReport report = (HistoryUserGameReport)historyUserGameReportIterator.next();
          lowerUser = (User)lowerUsersMap.get(Integer.valueOf(report.getUserId()));
          if ((lowerUser != null) && (lowerUser.getUpid() == lowerUserId.intValue()))
          {
            reportVO.setHasMore(true);
            break;
          }
        }
      }
      List<HistoryUserGameReportVO> list = new ArrayList();
      list.add(tBean);
      Object[] keys = ((Map)resultMap).keySet().toArray();
      Arrays.sort(keys);
      int localHistoryUserGameReport1 = keys.length;
      for (int report = 0; report < localHistoryUserGameReport1; report++)
      {
        Object o = keys[report];
        list.add((HistoryUserGameReportVO)((Map)resultMap).get(o));
      }
      return list;
    }
    return null;
  }
  
  public List<UserGameReportVO> reportByUser(String sTime, String eTime)
  {
    return this.uGameReportDao.reportByUser(sTime, eTime);
  }
}
