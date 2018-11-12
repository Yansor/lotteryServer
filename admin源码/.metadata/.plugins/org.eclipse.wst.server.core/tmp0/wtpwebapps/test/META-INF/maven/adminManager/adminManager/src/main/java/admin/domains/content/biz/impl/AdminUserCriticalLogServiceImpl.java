package admin.domains.content.biz.impl;

import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.vo.AdminUserActionVO;
import lottery.domains.content.vo.user.UserVO;
import java.util.Iterator;
import org.hibernate.criterion.Disjunction;
import admin.domains.content.entity.AdminUser;
import java.util.List;
import admin.domains.content.entity.AdminUserCriticalLog;
import admin.domains.content.vo.AdminUserCriticalLogVO;
import org.hibernate.criterion.MatchMode;
import java.util.StringTokenizer;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.dao.AdminUserCriticalLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import org.springframework.stereotype.Service;
import admin.domains.content.biz.AdminUserCriticalLogService;

@Service
public class AdminUserCriticalLogServiceImpl implements AdminUserCriticalLogService
{
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserCriticalLogDao adminUserCriticalLogDao;
    @Autowired
    private AdminDataFactory adminDataFactory;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final Integer actionId, final String username, final String ip, final String keyword, final String sDate, final String eDate, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final AdminUser user = this.adminUserDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(ip)) {
            criterions.add((Criterion)Restrictions.eq("ip", (Object)ip));
        }
        if (actionId != null) {
            criterions.add((Criterion)Restrictions.eq("actionId", (Object)actionId));
        }
        if (StringUtil.isNotNull(keyword)) {
            final StringTokenizer token = new StringTokenizer(keyword);
            final Disjunction disjunction = Restrictions.or(new Criterion[0]);
            while (token.hasMoreElements()) {
                final String value = (String)token.nextElement();
                disjunction.add((Criterion)Restrictions.like("action", value, MatchMode.ANYWHERE));
            }
            criterions.add((Criterion)disjunction);
        }
        if (StringUtil.isNotNull(sDate)) {
            criterions.add((Criterion)Restrictions.ge("time", (Object)sDate));
        }
        if (StringUtil.isNotNull(eDate)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)eDate));
        }
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<AdminUserCriticalLogVO> list = new ArrayList<AdminUserCriticalLogVO>();
            final PageList pList = this.adminUserCriticalLogDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new AdminUserCriticalLogVO((AdminUserCriticalLog)tmpBean, this.adminDataFactory, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList search(final String username, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final UserVO user = this.lotteryDataFactory.getUser(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        final Object[] actionId = { 10008, 10105, 10020 };
        criterions.add(Restrictions.in("actionId", actionId));
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<AdminUserCriticalLogVO> list = new ArrayList<AdminUserCriticalLogVO>();
            final PageList pList = this.adminUserCriticalLogDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new AdminUserCriticalLogVO((AdminUserCriticalLog)tmpBean, this.adminDataFactory, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public List<AdminUserActionVO> findAction() {
        final List<AdminUserCriticalLog> list = this.adminUserCriticalLogDao.findAction();
        final List<AdminUserActionVO> adminUserActionVOs = new ArrayList<AdminUserActionVO>();
        for (final AdminUserCriticalLog adminUserCriticalLog : list) {
            final AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(adminUserCriticalLog.getActionId());
            if (adminUserAction != null) {
                adminUserActionVOs.add(new AdminUserActionVO(adminUserAction, this.adminDataFactory));
            }
        }
        return adminUserActionVOs;
    }
}
