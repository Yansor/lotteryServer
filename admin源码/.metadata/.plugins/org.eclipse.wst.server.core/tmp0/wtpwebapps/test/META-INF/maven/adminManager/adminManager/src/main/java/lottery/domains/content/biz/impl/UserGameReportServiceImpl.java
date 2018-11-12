package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.HistoryUserGameReport;
import java.util.Map;
import java.util.Arrays;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.User;
import java.util.HashMap;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.user.UserVO;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import lottery.domains.content.vo.bill.UserGameReportVO;
import java.util.List;
import lottery.domains.content.entity.UserGameReport;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserGameReportDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserGameReportService;

@Service
public class UserGameReportServiceImpl implements UserGameReportService
{
    @Autowired
    private UserGameReportDao uGameReportDao;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public boolean update(final int userId, final int platformId, final double billingOrder, final double prize, final double waterReturn, final double proxyReturn, final String time) {
        final UserGameReport entity = new UserGameReport();
        entity.setBillingOrder(billingOrder);
        entity.setPrize(prize);
        entity.setWaterReturn(waterReturn);
        entity.setProxyReturn(proxyReturn);
        entity.setTime(time);
        final UserGameReport userGameReport = this.uGameReportDao.get(userId, platformId, time);
        if (userGameReport != null) {
            entity.setId(userGameReport.getId());
            return this.uGameReportDao.update(entity);
        }
        entity.setUserId(userId);
        entity.setPlatformId(platformId);
        return this.uGameReportDao.save(entity);
    }
    
    @Override
    public List<UserGameReportVO> report(final String sTime, final String eTime) {
        final List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
        final List<UserGameReportVO> userReports = new ArrayList<UserGameReportVO>(managerIds.size());
        for (final Integer managerId : managerIds) {
            final UserGameReportVO reportVO = this.uGameReportDao.sumLowersAndSelf(managerId, sTime, eTime);
            if (reportVO.getTransIn() <= 0.0 && reportVO.getTransOut() <= 0.0 && reportVO.getPrize() <= 0.0 && reportVO.getWaterReturn() <= 0.0 && reportVO.getProxyReturn() <= 0.0 && reportVO.getActivity() <= 0.0 && reportVO.getBillingOrder() <= 0.0) {
                continue;
            }
            final UserVO user = this.lotteryDataFactory.getUser(managerId);
            if (user == null) {
                continue;
            }
            reportVO.setName(user.getUsername());
            userReports.add(reportVO);
        }
        final List<UserGameReportVO> result = new ArrayList<UserGameReportVO>(userReports.size() + 1);
        final UserGameReportVO tBean = new UserGameReportVO("总计");
        for (final UserGameReportVO userReport : userReports) {
            tBean.addBean(userReport);
        }
        result.add(tBean);
        result.addAll(userReports);
        return result;
    }
    
    @Override
    public List<HistoryUserGameReportVO> historyReport(final String sTime, final String eTime) {
        final List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
        final List<HistoryUserGameReportVO> userReports = new ArrayList<HistoryUserGameReportVO>(managerIds.size());
        for (final Integer managerId : managerIds) {
            final HistoryUserGameReportVO reportVO = this.uGameReportDao.historySumLowersAndSelf(managerId, sTime, eTime);
            if (reportVO.getTransIn() <= 0.0 && reportVO.getTransOut() <= 0.0 && reportVO.getPrize() <= 0.0 && reportVO.getWaterReturn() <= 0.0 && reportVO.getProxyReturn() <= 0.0 && reportVO.getActivity() <= 0.0 && reportVO.getBillingOrder() <= 0.0) {
                continue;
            }
            final UserVO user = this.lotteryDataFactory.getUser(managerId);
            if (user == null) {
                continue;
            }
            reportVO.setName(user.getUsername());
            userReports.add(reportVO);
        }
        final List<HistoryUserGameReportVO> result = new ArrayList<HistoryUserGameReportVO>(userReports.size() + 1);
        final HistoryUserGameReportVO tBean = new HistoryUserGameReportVO("总计");
        for (final HistoryUserGameReportVO userReport : userReports) {
            tBean.addBean(userReport);
        }
        result.add(tBean);
        result.addAll(userReports);
        return result;
    }
    
    @Override
    public List<UserGameReportVO> report(final int userId, final String sTime, final String eTime) {
        final User targetUser = this.uDao.getById(userId);
        if (targetUser != null) {
            final Map<Integer, User> lowerUsersMap = new HashMap<Integer, User>();
            final List<User> lowerUserList = this.uDao.getUserLower(targetUser.getId());
            final List<User> directUserList = this.uDao.getUserDirectLower(targetUser.getId());
            final List<Criterion> criterions = new ArrayList<Criterion>();
            final List<Order> orders = new ArrayList<Order>();
            final List<Integer> toUids = new ArrayList<Integer>();
            toUids.add(targetUser.getId());
            for (final User tmpUser : lowerUserList) {
                toUids.add(tmpUser.getId());
                lowerUsersMap.put(tmpUser.getId(), tmpUser);
            }
            if (StringUtil.isNotNull(sTime)) {
                criterions.add((Criterion)Restrictions.ge("time", (Object)sTime));
            }
            if (StringUtil.isNotNull(eTime)) {
                criterions.add((Criterion)Restrictions.lt("time", (Object)eTime));
            }
            criterions.add(Restrictions.in("userId", (Collection)toUids));
            final List<UserGameReport> result = this.uGameReportDao.find(criterions, orders);
            final Map<Integer, UserGameReportVO> resultMap = new HashMap<Integer, UserGameReportVO>();
            final UserGameReportVO tBean = new UserGameReportVO("总计");
            for (final UserGameReport tmpBean : result) {
                if (tmpBean.getUserId() == targetUser.getId()) {
                    if (!resultMap.containsKey(targetUser.getId())) {
                        resultMap.put(targetUser.getId(), new UserGameReportVO(targetUser.getUsername()));
                    }
                    resultMap.get(targetUser.getId()).addBean(tmpBean);
                }
                else {
                    final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
                    if (thisUser.getUpid() == targetUser.getId()) {
                        if (!resultMap.containsKey(thisUser.getId())) {
                            resultMap.put(thisUser.getId(), new UserGameReportVO(thisUser.getUsername()));
                        }
                        resultMap.get(thisUser.getId()).addBean(tmpBean);
                    }
                    else {
                        for (final User tmpUser2 : directUserList) {
                            if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                                if (!resultMap.containsKey(tmpUser2.getId())) {
                                    resultMap.put(tmpUser2.getId(), new UserGameReportVO(tmpUser2.getUsername()));
                                }
                                resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                            }
                        }
                    }
                }
                tBean.addBean(tmpBean);
            }
            for (final Integer lowerUserId : resultMap.keySet()) {
                final UserGameReportVO reportVO = resultMap.get(lowerUserId);
                for (final UserGameReport report : result) {
                    final User lowerUser = lowerUsersMap.get(report.getUserId());
                    if (lowerUser != null && lowerUser.getUpid() == lowerUserId) {
                        reportVO.setHasMore(true);
                        break;
                    }
                }
            }
            final List<UserGameReportVO> list = new ArrayList<UserGameReportVO>();
            list.add(tBean);
            final Object[] keys = resultMap.keySet().toArray();
            Arrays.sort(keys);
            Object[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final Object o = array[i];
                list.add(resultMap.get(o));
            }
            return list;
        }
        return null;
    }
    
    @Override
    public List<HistoryUserGameReportVO> historyReport(final int userId, final String sTime, final String eTime) {
        final User targetUser = this.uDao.getById(userId);
        if (targetUser != null) {
            final Map<Integer, User> lowerUsersMap = new HashMap<Integer, User>();
            final List<User> lowerUserList = this.uDao.getUserLower(targetUser.getId());
            final List<User> directUserList = this.uDao.getUserDirectLower(targetUser.getId());
            final List<Criterion> criterions = new ArrayList<Criterion>();
            final List<Order> orders = new ArrayList<Order>();
            final List<Integer> toUids = new ArrayList<Integer>();
            toUids.add(targetUser.getId());
            for (final User tmpUser : lowerUserList) {
                toUids.add(tmpUser.getId());
                lowerUsersMap.put(tmpUser.getId(), tmpUser);
            }
            if (StringUtil.isNotNull(sTime)) {
                criterions.add((Criterion)Restrictions.ge("time", (Object)sTime));
            }
            if (StringUtil.isNotNull(eTime)) {
                criterions.add((Criterion)Restrictions.lt("time", (Object)eTime));
            }
            criterions.add(Restrictions.in("userId", (Collection)toUids));
            final List<HistoryUserGameReport> result = this.uGameReportDao.findHistory(criterions, orders);
            final Map<Integer, HistoryUserGameReportVO> resultMap = new HashMap<Integer, HistoryUserGameReportVO>();
            final HistoryUserGameReportVO tBean = new HistoryUserGameReportVO("总计");
            for (final HistoryUserGameReport tmpBean : result) {
                if (tmpBean.getUserId() == targetUser.getId()) {
                    if (!resultMap.containsKey(targetUser.getId())) {
                        resultMap.put(targetUser.getId(), new HistoryUserGameReportVO(targetUser.getUsername()));
                    }
                    resultMap.get(targetUser.getId()).addBean(tmpBean);
                }
                else {
                    final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
                    if (thisUser.getUpid() == targetUser.getId()) {
                        if (!resultMap.containsKey(thisUser.getId())) {
                            resultMap.put(thisUser.getId(), new HistoryUserGameReportVO(thisUser.getUsername()));
                        }
                        resultMap.get(thisUser.getId()).addBean(tmpBean);
                    }
                    else {
                        for (final User tmpUser2 : directUserList) {
                            if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                                if (!resultMap.containsKey(tmpUser2.getId())) {
                                    resultMap.put(tmpUser2.getId(), new HistoryUserGameReportVO(tmpUser2.getUsername()));
                                }
                                resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                            }
                        }
                    }
                }
                tBean.addBean(tmpBean);
            }
            for (final Integer lowerUserId : resultMap.keySet()) {
                final HistoryUserGameReportVO reportVO = resultMap.get(lowerUserId);
                for (final HistoryUserGameReport report : result) {
                    final User lowerUser = lowerUsersMap.get(report.getUserId());
                    if (lowerUser != null && lowerUser.getUpid() == lowerUserId) {
                        reportVO.setHasMore(true);
                        break;
                    }
                }
            }
            final List<HistoryUserGameReportVO> list = new ArrayList<HistoryUserGameReportVO>();
            list.add(tBean);
            final Object[] keys = resultMap.keySet().toArray();
            Arrays.sort(keys);
            Object[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final Object o = array[i];
                list.add(resultMap.get(o));
            }
            return list;
        }
        return null;
    }
    
    @Override
    public List<UserGameReportVO> reportByUser(final String sTime, final String eTime) {
        return this.uGameReportDao.reportByUser(sTime, eTime);
    }
}
