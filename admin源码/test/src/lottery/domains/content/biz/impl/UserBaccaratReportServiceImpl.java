package lottery.domains.content.biz.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import lottery.domains.content.entity.User;
import java.util.HashMap;
import lottery.domains.content.vo.bill.UserBaccaratReportVO;
import java.util.List;
import lottery.domains.content.entity.UserBaccaratReport;
import lottery.domains.content.dao.UserBaccaratReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBaccaratReportService;

@Service
public class UserBaccaratReportServiceImpl implements UserBaccaratReportService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBaccaratReportDao uBaccaratReportDao;
    
    @Override
    public boolean update(final int userId, final int type, final double amount, final String time) {
        final UserBaccaratReport entity = new UserBaccaratReport();
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
            case 11: {
                entity.setWaterReturn(amount);
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
            case 5: {
                entity.setActivity(amount);
                break;
            }
            default: {
                return false;
            }
        }
        final UserBaccaratReport bean = this.uBaccaratReportDao.get(userId, time);
        if (bean != null) {
            entity.setId(bean.getId());
            return this.uBaccaratReportDao.update(entity);
        }
        entity.setUserId(userId);
        entity.setTime(time);
        return this.uBaccaratReportDao.add(entity);
    }
    
    @Override
    public List<UserBaccaratReportVO> report(final String sTime, final String eTime) {
        final Map<Integer, User> lowerUsersMap = new HashMap<Integer, User>();
        final List<User> lowerUserList = this.uDao.listAll();
        final List<User> directUserList = this.uDao.getUserDirectLower(0);
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        for (final User tmpUser : lowerUserList) {
            lowerUsersMap.put(tmpUser.getId(), tmpUser);
        }
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)eTime));
        }
        final List<UserBaccaratReport> result = this.uBaccaratReportDao.find(criterions, orders);
        final Map<Integer, UserBaccaratReportVO> resultMap = new HashMap<Integer, UserBaccaratReportVO>();
        final UserBaccaratReportVO tBean = new UserBaccaratReportVO("总计");
        for (final UserBaccaratReport tmpBean : result) {
            final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
            if (thisUser.getUpid() == 0) {
                if (!resultMap.containsKey(thisUser.getId())) {
                    resultMap.put(thisUser.getId(), new UserBaccaratReportVO(thisUser.getUsername()));
                }
                resultMap.get(thisUser.getId()).addBean(tmpBean);
            }
            else {
                for (final User tmpUser2 : directUserList) {
                    if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                        if (!resultMap.containsKey(tmpUser2.getId())) {
                            resultMap.put(tmpUser2.getId(), new UserBaccaratReportVO(tmpUser2.getUsername()));
                        }
                        resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                    }
                }
            }
            tBean.addBean(tmpBean);
        }
        final List<UserBaccaratReportVO> list = new ArrayList<UserBaccaratReportVO>();
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
    
    @Override
    public List<UserBaccaratReportVO> report(final int userId, final String sTime, final String eTime) {
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
            final List<UserBaccaratReport> result = this.uBaccaratReportDao.find(criterions, orders);
            final Map<Integer, UserBaccaratReportVO> resultMap = new HashMap<Integer, UserBaccaratReportVO>();
            final UserBaccaratReportVO tBean = new UserBaccaratReportVO("总计");
            for (final UserBaccaratReport tmpBean : result) {
                if (tmpBean.getUserId() == targetUser.getId()) {
                    if (!resultMap.containsKey(targetUser.getId())) {
                        resultMap.put(targetUser.getId(), new UserBaccaratReportVO(targetUser.getUsername()));
                    }
                    resultMap.get(targetUser.getId()).addBean(tmpBean);
                }
                else {
                    final User thisUser = lowerUsersMap.get(tmpBean.getUserId());
                    if (thisUser.getUpid() == targetUser.getId()) {
                        if (!resultMap.containsKey(thisUser.getId())) {
                            resultMap.put(thisUser.getId(), new UserBaccaratReportVO(thisUser.getUsername()));
                        }
                        resultMap.get(thisUser.getId()).addBean(tmpBean);
                    }
                    else {
                        for (final User tmpUser2 : directUserList) {
                            if (thisUser.getUpids().indexOf("[" + tmpUser2.getId() + "]") != -1) {
                                if (!resultMap.containsKey(tmpUser2.getId())) {
                                    resultMap.put(tmpUser2.getId(), new UserBaccaratReportVO(tmpUser2.getUsername()));
                                }
                                resultMap.get(tmpUser2.getId()).addBean(tmpBean);
                            }
                        }
                    }
                }
                tBean.addBean(tmpBean);
            }
            final List<UserBaccaratReportVO> list = new ArrayList<UserBaccaratReportVO>();
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
