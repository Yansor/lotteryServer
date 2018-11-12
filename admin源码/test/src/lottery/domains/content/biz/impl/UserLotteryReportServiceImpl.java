package lottery.domains.content.biz.impl;

import lottery.domains.content.vo.bill.UserProfitRankingVO;
import javautils.ObjectUtil;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.entity.HistoryUserLotteryReport;
import java.util.Arrays;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import java.util.Map;
import lottery.domains.content.vo.user.UserVO;
import java.util.Iterator;
import lottery.domains.content.vo.bill.UserMainReportVO;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import java.util.List;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.dao.UserBetsDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserLotteryReportService;

@Service
public class UserLotteryReportServiceImpl implements UserLotteryReportService
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
    
    @Override
    public boolean update(final int userId, final int type, final double amount, final String time) {
        final UserLotteryReport entity = new UserLotteryReport();
        switch (type) {
            case 3: {
                entity.setTransIn(amount);
                break;
            }
            case 4: {
                entity.setTransOut(amount);
                break;
            }
            case 6: {
                entity.setSpend(amount);
                break;
            }
            case 7: {
                entity.setPrize(amount);
                break;
            }
            case 8: {
                entity.setSpendReturn(amount);
                break;
            }
            case 9: {
                entity.setProxyReturn(amount);
                break;
            }
            case 10: {
                entity.setCancelOrder(amount);
                break;
            }
            case 12: {
                entity.setDividend(amount);
                break;
            }
            case 5:
            case 17:
            case 22: {
                entity.setActivity(amount);
                break;
            }
            default: {
                return false;
            }
        }
        final UserLotteryReport bean = this.uLotteryReportDao.get(userId, time);
        if (bean != null) {
            entity.setId(bean.getId());
            return this.uLotteryReportDao.update(entity);
        }
        entity.setUserId(userId);
        entity.setTime(time);
        return this.uLotteryReportDao.add(entity);
    }
    
    @Override
    public boolean updateRechargeFee(final int userId, final double amount, final String time) {
        final UserLotteryReport entity = new UserLotteryReport();
        entity.setRechargeFee(amount);
        final UserLotteryReport bean = this.uLotteryReportDao.get(userId, time);
        if (bean != null) {
            entity.setId(bean.getId());
            return this.uLotteryReportDao.update(entity);
        }
        entity.setUserId(userId);
        entity.setTime(time);
        return this.uLotteryReportDao.add(entity);
    }
    
    @Override
    public List<UserLotteryReportVO> report(final String sTime, final String eTime) {
        final List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
        final List<UserLotteryReportVO> userReports = new ArrayList<UserLotteryReportVO>(managerIds.size());
        for (final Integer managerId : managerIds) {
            final UserLotteryReportVO reportVO = this.uLotteryReportDao.sumLowersAndSelf(managerId, sTime, eTime);
            if (reportVO.getTransIn() <= 0.0 && reportVO.getTransOut() <= 0.0 && reportVO.getPrize() <= 0.0 && reportVO.getSpendReturn() <= 0.0 && reportVO.getProxyReturn() <= 0.0 && reportVO.getActivity() <= 0.0 && reportVO.getDividend() <= 0.0 && reportVO.getBillingOrder() <= 0.0 && reportVO.getRechargeFee() <= 0.0) {
                continue;
            }
            final UserVO user = this.lotteryDataFactory.getUser(managerId);
            if (user == null) {
                continue;
            }
            reportVO.setHasMore(true);
            reportVO.setName(user.getUsername());
            userReports.add(reportVO);
        }
        final List<UserLotteryReportVO> result = new ArrayList<UserLotteryReportVO>(userReports.size() + 1);
        final UserLotteryReportVO tBean = new UserLotteryReportVO("总计");
        for (final UserLotteryReportVO userReport : userReports) {
            tBean.addBean(userReport);
        }
        result.add(tBean);
        result.addAll(userReports);
        final List<UserMainReportVO> mainReport = this.userMainReportService.report(sTime, eTime);
        final Map<String, UserMainReportVO> mapReport = new HashMap<String, UserMainReportVO>();
        for (final UserMainReportVO userMainReport : mainReport) {
            mapReport.put(userMainReport.getName(), userMainReport);
        }
        for (final UserLotteryReportVO vo : result) {
            if (mapReport.containsKey(vo.getName())) {
                final UserMainReportVO mvo = mapReport.get(vo.getName());
                vo.addCash(mvo.getRecharge(), mvo.getWithdrawals());
            }
        }
        return result;
    }
    
    @Override
    public List<HistoryUserLotteryReportVO> historyReport(final String sTime, final String eTime) {
        final List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
        final List<HistoryUserLotteryReportVO> userReports = new ArrayList<HistoryUserLotteryReportVO>(managerIds.size());
        for (final Integer managerId : managerIds) {
            final HistoryUserLotteryReportVO reportVO = this.uLotteryReportDao.historySumLowersAndSelf(managerId, sTime, eTime);
            if (reportVO.getTransIn() <= 0.0 && reportVO.getTransOut() <= 0.0 && reportVO.getPrize() <= 0.0 && reportVO.getSpendReturn() <= 0.0 && reportVO.getProxyReturn() <= 0.0 && reportVO.getActivity() <= 0.0 && reportVO.getDividend() <= 0.0 && reportVO.getBillingOrder() <= 0.0 && reportVO.getRechargeFee() <= 0.0) {
                continue;
            }
            final UserVO user = this.lotteryDataFactory.getUser(managerId);
            if (user == null) {
                continue;
            }
            reportVO.setHasMore(true);
            reportVO.setName(user.getUsername());
            userReports.add(reportVO);
        }
        final List<HistoryUserLotteryReportVO> result = new ArrayList<HistoryUserLotteryReportVO>(userReports.size() + 1);
        final HistoryUserLotteryReportVO tBean = new HistoryUserLotteryReportVO("总计");
        for (final HistoryUserLotteryReportVO userReport : userReports) {
            tBean.addBean(userReport);
        }
        result.add(tBean);
        result.addAll(userReports);
        return result;
    }
    
    @Override
    public List<UserLotteryReportVO> report(final int userId, final String sTime, final String eTime) {
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
            final List<UserLotteryReport> result = this.uLotteryReportDao.find(criterions, orders);
            final Map<Integer, UserLotteryReportVO> resultMap = new HashMap<Integer, UserLotteryReportVO>();
            final UserLotteryReportVO tBean = new UserLotteryReportVO("总计");
            for (final UserLotteryReport tmpBean : result) {
                if (tmpBean.getUserId() == targetUser.getId()) {
                    if (!resultMap.containsKey(targetUser.getId())) {
                        resultMap.put(targetUser.getId(), new UserLotteryReportVO(targetUser.getUsername()));
                    }
                    resultMap.get(targetUser.getId()).addBean(tmpBean);
                }
                else {
                    final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
                    if (thisUser.getUpid() == targetUser.getId()) {
                        if (!resultMap.containsKey(thisUser.getId())) {
                            resultMap.put(thisUser.getId(), new UserLotteryReportVO(thisUser.getUsername()));
                        }
                        resultMap.get(thisUser.getId()).addBean(tmpBean);
                    }
                    else {
                        for (final User tmpUser2 : directUserList) {
                            if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                                if (!resultMap.containsKey(tmpUser2.getId())) {
                                    resultMap.put(tmpUser2.getId(), new UserLotteryReportVO(tmpUser2.getUsername()));
                                }
                                resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                            }
                        }
                    }
                }
                tBean.addBean(tmpBean);
            }
            for (final Integer lowerUserId : resultMap.keySet()) {
                final UserLotteryReportVO reportVO = resultMap.get(lowerUserId);
                for (final UserLotteryReport report : result) {
                    final User lowerUser = lowerUsersMap.get(report.getUserId());
                    if (lowerUser != null && lowerUser.getUpid() == lowerUserId) {
                        reportVO.setHasMore(true);
                        break;
                    }
                }
            }
            final List<UserLotteryReportVO> list = new ArrayList<UserLotteryReportVO>();
            list.add(tBean);
            final Object[] keys = resultMap.keySet().toArray();
            Arrays.sort(keys);
            Object[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final Object o = array[i];
                list.add(resultMap.get(o));
            }
            this.resetFirstPosition(targetUser, list);
            final List<UserMainReportVO> mainReport = this.userMainReportService.report(userId, sTime, eTime);
            final Map<String, UserMainReportVO> mapReport = new HashMap<String, UserMainReportVO>();
            for (final UserMainReportVO userMainReport : mainReport) {
                mapReport.put(userMainReport.getName(), userMainReport);
            }
            for (final UserLotteryReportVO vo : list) {
                if (mapReport.containsKey(vo.getName())) {
                    final UserMainReportVO mvo = mapReport.get(vo.getName());
                    vo.addCash(mvo.getRecharge(), mvo.getWithdrawals());
                }
            }
            return list;
        }
        return null;
    }
    
    @Override
    public List<HistoryUserLotteryReportVO> historyReport(final int userId, final String sTime, final String eTime) {
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
            final List<HistoryUserLotteryReport> result = this.uLotteryReportDao.findHistory(criterions, orders);
            final Map<Integer, HistoryUserLotteryReportVO> resultMap = new HashMap<Integer, HistoryUserLotteryReportVO>();
            final HistoryUserLotteryReportVO tBean = new HistoryUserLotteryReportVO("总计");
            for (final HistoryUserLotteryReport tmpBean : result) {
                if (tmpBean.getUserId() == targetUser.getId()) {
                    if (!resultMap.containsKey(targetUser.getId())) {
                        resultMap.put(targetUser.getId(), new HistoryUserLotteryReportVO(targetUser.getUsername()));
                    }
                    resultMap.get(targetUser.getId()).addBean(tmpBean);
                }
                else {
                    final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
                    if (thisUser.getUpid() == targetUser.getId()) {
                        if (!resultMap.containsKey(thisUser.getId())) {
                            resultMap.put(thisUser.getId(), new HistoryUserLotteryReportVO(thisUser.getUsername()));
                        }
                        resultMap.get(thisUser.getId()).addBean(tmpBean);
                    }
                    else {
                        for (final User tmpUser2 : directUserList) {
                            if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                                if (!resultMap.containsKey(tmpUser2.getId())) {
                                    resultMap.put(tmpUser2.getId(), new HistoryUserLotteryReportVO(tmpUser2.getUsername()));
                                }
                                resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                            }
                        }
                    }
                }
                tBean.addBean(tmpBean);
            }
            for (final Integer lowerUserId : resultMap.keySet()) {
                final HistoryUserLotteryReportVO reportVO = resultMap.get(lowerUserId);
                for (final HistoryUserLotteryReport report : result) {
                    final User lowerUser = lowerUsersMap.get(report.getUserId());
                    if (lowerUser != null && lowerUser.getUpid() == lowerUserId) {
                        reportVO.setHasMore(true);
                        break;
                    }
                }
            }
            final List<HistoryUserLotteryReportVO> list = new ArrayList<HistoryUserLotteryReportVO>();
            list.add(tBean);
            final Object[] keys = resultMap.keySet().toArray();
            Arrays.sort(keys);
            Object[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final Object o = array[i];
                list.add(resultMap.get(o));
            }
            this.historyResetFirstPosition(targetUser, list);
            return list;
        }
        return null;
    }
    
    private void resetFirstPosition(final User targetUser, final List<UserLotteryReportVO> list) {
        if (CollectionUtils.isEmpty((Collection)list) || list.size() <= 1) {
            return;
        }
        final UserLotteryReportVO targetUserReport = list.get(1);
        if (targetUserReport.getName() != targetUser.getUsername()) {
            UserLotteryReportVO targetUserReportReset = null;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getName().equals(targetUser.getUsername())) {
                    targetUserReportReset = list.get(i);
                    list.set(i, targetUserReport);
                    break;
                }
            }
            if (targetUserReportReset != null) {
                list.set(1, targetUserReportReset);
            }
        }
    }
    
    private void historyResetFirstPosition(final User targetUser, final List<HistoryUserLotteryReportVO> list) {
        if (CollectionUtils.isEmpty((Collection)list) || list.size() <= 1) {
            return;
        }
        final HistoryUserLotteryReportVO targetUserReport = list.get(1);
        if (targetUserReport.getName() != targetUser.getUsername()) {
            HistoryUserLotteryReportVO targetUserReportReset = null;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getName().equals(targetUser.getUsername())) {
                    targetUserReportReset = list.get(i);
                    list.set(i, targetUserReport);
                    break;
                }
            }
            if (targetUserReportReset != null) {
                list.set(1, targetUserReportReset);
            }
        }
    }
    
    @Override
    public List<UserBetsReportVO> bReport(final Integer type, final Integer lottery, final Integer ruleId, final String sTime, final String eTime) {
        final List<Integer> lids = new ArrayList<Integer>();
        if (lottery != null) {
            lids.add(lottery);
        }
        else if (type != null) {
            final List<Lottery> llist = this.lotteryDataFactory.listLottery(type);
            for (final Lottery tmpBean : llist) {
                lids.add(tmpBean.getId());
            }
        }
        final List<UserBetsReportVO> list = new ArrayList<UserBetsReportVO>();
        final List<?> rlist = this.uBetsDao.report(lids, ruleId, sTime, eTime);
        for (final Object o : rlist) {
            final Object[] values = (Object[])o;
            final String field = (values[0] != null) ? ((String)values[0]) : null;
            final double money = ObjectUtil.toDouble(values[1]);
            final double returnMoney = ObjectUtil.toDouble(values[2]);
            final double prizeMoney = ObjectUtil.toDouble(values[3]);
            final UserBetsReportVO tmpBean2 = new UserBetsReportVO();
            tmpBean2.setField(field);
            tmpBean2.setMoney(money);
            tmpBean2.setReturnMoney(returnMoney);
            tmpBean2.setPrizeMoney(prizeMoney);
            list.add(tmpBean2);
        }
        return list;
    }
    
    @Override
    public List<UserProfitRankingVO> listUserProfitRanking(final Integer userId, final String sTime, final String eTime, final int start, final int limit) {
        List<UserProfitRankingVO> rankingVOs;
        if (userId != null) {
            rankingVOs = this.uLotteryReportDao.listUserProfitRankingByDate(userId, sTime, eTime, start, limit);
        }
        else {
            rankingVOs = this.uLotteryReportDao.listUserProfitRanking(sTime, eTime, start, limit);
        }
        if (rankingVOs != null && rankingVOs.size() > 0) {
            for (final UserProfitRankingVO rankingVO : rankingVOs) {
                final UserVO user = this.lotteryDataFactory.getUser(rankingVO.getUserId());
                if (user != null) {
                    rankingVO.setName(user.getUsername());
                }
                else {
                    rankingVO.setName("未知");
                }
            }
        }
        return rankingVOs;
    }
    
    @Override
    public List<UserLotteryReportVO> reportByType(final Integer type, final String sTime, final String eTime) {
        final List<UserLotteryReportVO> result = new ArrayList<UserLotteryReportVO>();
        final List<User> users = this.uDao.listAllByType(4);
        for (final User user : users) {
            result.addAll(this.report(user.getId(), sTime, eTime));
        }
        return result;
    }
}
