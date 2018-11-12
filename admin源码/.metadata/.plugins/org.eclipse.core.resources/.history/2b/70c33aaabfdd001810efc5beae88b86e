package lottery.domains.content.biz.impl;

import java.util.Map;
import java.util.Arrays;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import lottery.domains.content.entity.User;
import java.util.HashMap;
import lottery.domains.content.vo.user.UserVO;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import lottery.domains.content.vo.bill.UserMainReportVO;
import java.util.List;
import lottery.domains.content.entity.UserMainReport;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserMainReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserMainReportService;

@Service
public class UserMainReportServiceImpl implements UserMainReportService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserMainReportDao uMainReportDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public boolean update(final int userId, final int type, final double amount, final String time) {
        final UserMainReport entity = new UserMainReport();
        switch (type) {
            case 1: {
                entity.setRecharge(amount);
                break;
            }
            case 2: {
                entity.setWithdrawals(amount);
                break;
            }
            case 3: {
                entity.setTransIn(amount);
                break;
            }
            case 4: {
                entity.setTransOut(amount);
                break;
            }
            case 5: {
                entity.setActivity(amount);
                break;
            }
            default: {
                return false;
            }
        }
        final UserMainReport bean = this.uMainReportDao.get(userId, time);
        if (bean != null) {
            entity.setId(bean.getId());
            return this.uMainReportDao.update(entity);
        }
        entity.setUserId(userId);
        entity.setTime(time);
        return this.uMainReportDao.add(entity);
    }
    
    @Override
    public List<UserMainReportVO> report(final String sTime, final String eTime) {
        final List<Integer> managerIds = this.uDao.getUserDirectLowerId(0);
        final List<UserMainReportVO> userReports = new ArrayList<UserMainReportVO>(managerIds.size());
        for (final Integer managerId : managerIds) {
            final UserMainReportVO reportVO = this.uMainReportDao.sumLowersAndSelf(managerId, sTime, eTime);
            if (reportVO.getRecharge() <= 0.0 && reportVO.getWithdrawals() <= 0.0 && reportVO.getTransIn() <= 0.0 && reportVO.getTransOut() <= 0.0 && reportVO.getAccountIn() <= 0.0 && reportVO.getAccountOut() <= 0.0 && reportVO.getActivity() <= 0.0) {
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
        final List<UserMainReportVO> result = new ArrayList<UserMainReportVO>(userReports.size() + 1);
        final UserMainReportVO tBean = new UserMainReportVO("总计");
        for (final UserMainReportVO userReport : userReports) {
            tBean.addBean(userReport);
        }
        result.add(tBean);
        result.addAll(userReports);
        return result;
    }
    
    @Override
    public List<UserMainReportVO> report(final int userId, final String sTime, final String eTime) {
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
            final List<UserMainReport> result = this.uMainReportDao.find(criterions, orders);
            final Map<Integer, UserMainReportVO> resultMap = new HashMap<Integer, UserMainReportVO>();
            final UserMainReportVO tBean = new UserMainReportVO("总计");
            for (final UserMainReport tmpBean : result) {
                if (tmpBean.getUserId() == targetUser.getId()) {
                    if (!resultMap.containsKey(targetUser.getId())) {
                        resultMap.put(targetUser.getId(), new UserMainReportVO(targetUser.getUsername()));
                    }
                    resultMap.get(targetUser.getId()).addBean(tmpBean);
                }
                else {
                    final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
                    if (thisUser.getUpid() == targetUser.getId()) {
                        if (!resultMap.containsKey(thisUser.getId())) {
                            resultMap.put(thisUser.getId(), new UserMainReportVO(thisUser.getUsername()));
                        }
                        resultMap.get(thisUser.getId()).addBean(tmpBean);
                    }
                    else {
                        for (final User tmpUser2 : directUserList) {
                            if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                                if (!resultMap.containsKey(tmpUser2.getId())) {
                                    resultMap.put(tmpUser2.getId(), new UserMainReportVO(tmpUser2.getUsername()));
                                }
                                resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                            }
                        }
                    }
                }
                tBean.addBean(tmpBean);
            }
            for (final Integer lowerUserId : resultMap.keySet()) {
                final UserMainReportVO reportVO = resultMap.get(lowerUserId);
                for (final UserMainReport report : result) {
                    final User lowerUser = lowerUsersMap.get(report.getUserId());
                    if (lowerUser != null && lowerUser.getUpid() == lowerUserId) {
                        reportVO.setHasMore(true);
                        break;
                    }
                }
            }
            final List<UserMainReportVO> list = new ArrayList<UserMainReportVO>();
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
}
