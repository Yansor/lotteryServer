package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javautils.ObjectUtil;
import javautils.StringUtil;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.entity.HistoryUserLotteryReport;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.bill.UserMainReportVO;
import lottery.domains.content.vo.bill.UserProfitRankingVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLotteryReportServiceImpl
  implements UserLotteryReportService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBetsDao uBetsDao;
  @Autowired
  private UserLotteryReportDao uLotteryReportDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private UserMainReportService userMainReportService;
  
  public boolean update(int userId, int type, double amount, String time)
  {
    UserLotteryReport entity = new UserLotteryReport();
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
    case 8: 
      entity.setSpendReturn(amount);
      break;
    case 9: 
      entity.setProxyReturn(amount);
      break;
    case 10: 
      entity.setCancelOrder(amount);
      break;
    case 12: 
      entity.setDividend(amount);
      break;
    case 5: 
    case 17: 
    case 22: 
      entity.setActivity(amount);
      break;
    case 11: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 18: 
    case 19: 
    case 20: 
    case 21: 
    default: 
      return false;
    }
    UserLotteryReport bean = this.uLotteryReportDao.get(userId, time);
    if (bean != null)
    {
      entity.setId(bean.getId());
      return this.uLotteryReportDao.update(entity);
    }
    entity.setUserId(userId);
    entity.setTime(time);
    return this.uLotteryReportDao.add(entity);
  }
  
  public boolean updateRechargeFee(int userId, double amount, String time)
  {
    UserLotteryReport entity = new UserLotteryReport();
    entity.setRechargeFee(amount);
    UserLotteryReport bean = this.uLotteryReportDao.get(userId, time);
    if (bean != null)
    {
      entity.setId(bean.getId());
      return this.uLotteryReportDao.update(entity);
    }
    entity.setUserId(userId);
    entity.setTime(time);
    return this.uLotteryReportDao.add(entity);
  }
  
  public List<UserLotteryReportVO> report(String sTime, String eTime)
  {
    List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
    
    List<UserLotteryReportVO> userReports = new ArrayList(managerIds.size());
    UserVO user;
    for (Integer managerId : managerIds)
    {
      UserLotteryReportVO reportVO = this.uLotteryReportDao.sumLowersAndSelf(managerId.intValue(), sTime, eTime);
      if ((reportVO.getTransIn() > 0.0D) || (reportVO.getTransOut() > 0.0D) || (reportVO.getPrize() > 0.0D) || 
        (reportVO.getSpendReturn() > 0.0D) || (reportVO.getProxyReturn() > 0.0D) || (reportVO.getActivity() > 0.0D) || 
        (reportVO.getDividend() > 0.0D) || (reportVO.getBillingOrder() > 0.0D) || 
        (reportVO.getRechargeFee() > 0.0D))
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
    List<UserLotteryReportVO> result = new ArrayList(userReports.size() + 1);
    UserLotteryReportVO tBean = new UserLotteryReportVO("总计");
    for (UserLotteryReportVO userReport : userReports) {
      tBean.addBean(userReport);
    }
    result.add(tBean);
    result.addAll(userReports);
    
    List<UserMainReportVO> mainReport = this.userMainReportService.report(sTime, eTime);
    Map<String, UserMainReportVO> mapReport = new HashMap();
    for (UserMainReportVO userMainReport : mainReport) {
      mapReport.put(userMainReport.getName(), userMainReport);
    }
    for (UserLotteryReportVO vo : result) {
      if (mapReport.containsKey(vo.getName()))
      {
        UserMainReportVO mvo = (UserMainReportVO)mapReport.get(vo.getName());
        vo.addCash(mvo.getRecharge(), mvo.getWithdrawals());
      }
    }
    return result;
  }
  
  public List<HistoryUserLotteryReportVO> historyReport(String sTime, String eTime)
  {
    List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
    
    List<HistoryUserLotteryReportVO> userReports = new ArrayList(managerIds.size());
    UserVO user;
    for (Integer managerId : managerIds)
    {
      HistoryUserLotteryReportVO reportVO = this.uLotteryReportDao.historySumLowersAndSelf(managerId.intValue(), sTime, eTime);
      if ((reportVO.getTransIn() > 0.0D) || (reportVO.getTransOut() > 0.0D) || (reportVO.getPrize() > 0.0D) || 
        (reportVO.getSpendReturn() > 0.0D) || (reportVO.getProxyReturn() > 0.0D) || (reportVO.getActivity() > 0.0D) || 
        (reportVO.getDividend() > 0.0D) || (reportVO.getBillingOrder() > 0.0D) || 
        (reportVO.getRechargeFee() > 0.0D))
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
    List<HistoryUserLotteryReportVO> result = new ArrayList(userReports.size() + 1);
    HistoryUserLotteryReportVO tBean = new HistoryUserLotteryReportVO("总计");
    for (HistoryUserLotteryReportVO userReport : userReports) {
      tBean.addBean(userReport);
    }
    result.add(tBean);
    result.addAll(userReports);
    return result;
  }
  
  public List<UserLotteryReportVO> report(int userId, String sTime, String eTime)
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
      List<UserLotteryReport> result = this.uLotteryReportDao.find(criterions, orders);
      Object resultMap = new HashMap();
      UserLotteryReportVO tBean = new UserLotteryReportVO("总计");
      for (UserLotteryReport tmpBean : result)
      {
        if (tmpBean.getUserId() == targetUser.getId())
        {
          if (!((Map)resultMap).containsKey(Integer.valueOf(targetUser.getId()))) {
            ((Map)resultMap).put(Integer.valueOf(targetUser.getId()), new UserLotteryReportVO(targetUser.getUsername()));
          }
          ((UserLotteryReportVO)((Map)resultMap).get(Integer.valueOf(targetUser.getId()))).addBean(tmpBean);
        }
        else
        {
          User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
          if (thisUser.getUpid() == targetUser.getId())
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new UserLotteryReportVO(thisUser.getUsername()));
            }
            ((UserLotteryReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
          }
          else
          {
            for (User tmpUser : directUserList) {
              if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
              {
                if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
                  ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), new UserLotteryReportVO(tmpUser.getUsername()));
                }
                ((UserLotteryReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
              }
            }
          }
        }
        tBean.addBean(tmpBean);
      }
      User lowerUser;
      for (Integer lowerUserId : ((Map<Integer,UserLotteryReportVO>)resultMap).keySet())
      {
        UserLotteryReportVO reportVO = (UserLotteryReportVO)((Map)resultMap).get(lowerUserId);
        for (Iterator<UserLotteryReport> report = result.iterator(); report.hasNext();)
        {
          UserLotteryReport tempReport = (UserLotteryReport)report.next();
          lowerUser = (User)lowerUsersMap.get(Integer.valueOf(tempReport.getUserId()));
          if ((lowerUser != null) && (lowerUser.getUpid() == lowerUserId.intValue()))
          {
            reportVO.setHasMore(true);
            break;
          }
        }
      }
      List<UserLotteryReportVO> list = new ArrayList();
      list.add(tBean);
      Object[] keys = ((Map)resultMap).keySet().toArray();
      Arrays.sort(keys);
      int  keyLength = keys.length;
      for (int index = 0; index < keyLength; index++)
      {
        Object o = keys[index];
        list.add((UserLotteryReportVO)((Map)resultMap).get(o));
      }
      resetFirstPosition(targetUser, list);
      
      List<UserMainReportVO> mainReport = this.userMainReportService.report(userId, sTime, eTime);
      Map<String, UserMainReportVO> mapReport = new HashMap();
      for (UserMainReportVO userMainReport : mainReport) {
        mapReport.put(userMainReport.getName(), userMainReport);
      }
      for (UserLotteryReportVO vo : list) {
        if (mapReport.containsKey(vo.getName()))
        {
          UserMainReportVO mvo = (UserMainReportVO)mapReport.get(vo.getName());
          vo.addCash(mvo.getRecharge(), mvo.getWithdrawals());
        }
      }
      return list;
    }
    return null;
  }
  
  public List<HistoryUserLotteryReportVO> historyReport(int userId, String sTime, String eTime)
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
      List<HistoryUserLotteryReport> result = this.uLotteryReportDao.findHistory(criterions, orders);
      Object resultMap = new HashMap();
      HistoryUserLotteryReportVO tBean = new HistoryUserLotteryReportVO("总计");
      for (HistoryUserLotteryReport tmpBean : result)
      {
        if (tmpBean.getUserId() == targetUser.getId())
        {
          if (!((Map)resultMap).containsKey(Integer.valueOf(targetUser.getId()))) {
            ((Map)resultMap).put(Integer.valueOf(targetUser.getId()), new HistoryUserLotteryReportVO(targetUser.getUsername()));
          }
          ((HistoryUserLotteryReportVO)((Map)resultMap).get(Integer.valueOf(targetUser.getId()))).addBean(tmpBean);
        }
        else
        {
          User thisUser = (User)lowerUsersMap.get(Integer.valueOf(tmpBean.getUserId()));
          if (thisUser.getUpid() == targetUser.getId())
          {
            if (!((Map)resultMap).containsKey(Integer.valueOf(thisUser.getId()))) {
              ((Map)resultMap).put(Integer.valueOf(thisUser.getId()), new HistoryUserLotteryReportVO(thisUser.getUsername()));
            }
            ((HistoryUserLotteryReportVO)((Map)resultMap).get(Integer.valueOf(thisUser.getId()))).addBean(tmpBean);
          }
          else
          {
            for (User tmpUser : directUserList) {
              if (thisUser.getUpids().indexOf("[" + tmpUser.getId() + "]") != -1)
              {
                if (!((Map)resultMap).containsKey(Integer.valueOf(tmpUser.getId()))) {
                  ((Map)resultMap).put(Integer.valueOf(tmpUser.getId()), 
                    new HistoryUserLotteryReportVO(tmpUser.getUsername()));
                }
                ((HistoryUserLotteryReportVO)((Map)resultMap).get(Integer.valueOf(tmpUser.getId()))).addBean(tmpBean);
              }
            }
          }
        }
        tBean.addBean(tmpBean);
      }
      User lowerUser;
      for (Integer lowerUserId : ((Map<Integer,HistoryUserLotteryReportVO>)resultMap).keySet())
      {
        HistoryUserLotteryReportVO reportVO = (HistoryUserLotteryReportVO)((Map)resultMap).get(lowerUserId);
        for (Iterator<HistoryUserLotteryReport> historyUserLotteryReportIterator = result.iterator();historyUserLotteryReportIterator.hasNext();)
        {
          HistoryUserLotteryReport tempReport = historyUserLotteryReportIterator.next();
          lowerUser = (User)lowerUsersMap.get(Integer.valueOf(tempReport.getUserId()));
          if ((lowerUser != null) && (lowerUser.getUpid() == lowerUserId.intValue()))
          {
            reportVO.setHasMore(true);
            break;
          }
        }
      }
      List<HistoryUserLotteryReportVO> list = new ArrayList();
      list.add(tBean);
      Object[] keys = ((Map)resultMap).keySet().toArray();
      Arrays.sort(keys);
      int historyLength = keys.length;
      for (int report = 0; report < historyLength; report++)
      {
        Object o = keys[report];
        list.add((HistoryUserLotteryReportVO)((Map)resultMap).get(o));
      }
      historyResetFirstPosition(targetUser, list);
      
      return list;
    }
    return null;
  }
  
  private void resetFirstPosition(User targetUser, List<UserLotteryReportVO> list)
  {
    if ((CollectionUtils.isEmpty(list)) || (list.size() <= 1)) {
      return;
    }
    UserLotteryReportVO targetUserReport = (UserLotteryReportVO)list.get(1);
    if (targetUserReport.getName() != targetUser.getUsername())
    {
      UserLotteryReportVO targetUserReportReset = null;
      for (int i = 0; i < list.size(); i++) {
        if (((UserLotteryReportVO)list.get(i)).getName().equals(targetUser.getUsername()))
        {
          targetUserReportReset = (UserLotteryReportVO)list.get(i);
          list.set(i, targetUserReport);
          break;
        }
      }
      if (targetUserReportReset != null) {
        list.set(1, targetUserReportReset);
      }
    }
  }
  
  private void historyResetFirstPosition(User targetUser, List<HistoryUserLotteryReportVO> list)
  {
    if ((CollectionUtils.isEmpty(list)) || (list.size() <= 1)) {
      return;
    }
    HistoryUserLotteryReportVO targetUserReport = (HistoryUserLotteryReportVO)list.get(1);
    if (targetUserReport.getName() != targetUser.getUsername())
    {
      HistoryUserLotteryReportVO targetUserReportReset = null;
      for (int i = 0; i < list.size(); i++) {
        if (((HistoryUserLotteryReportVO)list.get(i)).getName().equals(targetUser.getUsername()))
        {
          targetUserReportReset = (HistoryUserLotteryReportVO)list.get(i);
          list.set(i, targetUserReport);
          break;
        }
      }
      if (targetUserReportReset != null) {
        list.set(1, targetUserReportReset);
      }
    }
  }
  
  public List<UserBetsReportVO> bReport(Integer type, Integer lottery, Integer ruleId, String sTime, String eTime)
  {
    List<Integer> lids = new ArrayList();
    if (lottery != null)
    {
      lids.add(lottery);
    }
    else if (type != null)
    {
      List<Lottery> llist = this.lotteryDataFactory.listLottery(type.intValue());
      for (Lottery tmpBean : llist) {
        lids.add(Integer.valueOf(tmpBean.getId()));
      }
    }
    List<UserBetsReportVO> list = new ArrayList();
    List<?> rlist = this.uBetsDao.report(lids, ruleId, sTime, eTime);
    for (Object o : rlist)
    {
      Object[] values = (Object[])o;
      String field = values[0] != null ? (String)values[0] : null;
      double money = ObjectUtil.toDouble(values[1]);
      double returnMoney = ObjectUtil.toDouble(values[2]);
      double prizeMoney = ObjectUtil.toDouble(values[3]);
      UserBetsReportVO tmpBean = new UserBetsReportVO();
      tmpBean.setField(field);
      tmpBean.setMoney(money);
      tmpBean.setReturnMoney(returnMoney);
      tmpBean.setPrizeMoney(prizeMoney);
      list.add(tmpBean);
    }
    return list;
  }
  
  public List<UserProfitRankingVO> listUserProfitRanking(Integer userId, String sTime, String eTime, int start, int limit)
  {
    List<UserProfitRankingVO> rankingVOs;
//    List<UserProfitRankingVO> rankingVOs;
    if (userId != null) {
      rankingVOs = this.uLotteryReportDao.listUserProfitRankingByDate(userId.intValue(), sTime, eTime, start, limit);
    } else {
      rankingVOs = this.uLotteryReportDao.listUserProfitRanking(sTime, eTime, start, limit);
    }
    if ((rankingVOs != null) && (rankingVOs.size() > 0)) {
      for (UserProfitRankingVO rankingVO : rankingVOs)
      {
        UserVO user = this.lotteryDataFactory.getUser(rankingVO.getUserId());
        if (user != null) {
          rankingVO.setName(user.getUsername());
        } else {
          rankingVO.setName("未知");
        }
      }
    }
    return rankingVOs;
  }
  
  public List<UserLotteryReportVO> reportByType(Integer type, String sTime, String eTime)
  {
    List<UserLotteryReportVO> result = new ArrayList();
    List<User> users = this.uDao.listAllByType(4);
    for (User user : users) {
      result.addAll(report(user.getId(), sTime, eTime));
    }
    return result;
  }
}
