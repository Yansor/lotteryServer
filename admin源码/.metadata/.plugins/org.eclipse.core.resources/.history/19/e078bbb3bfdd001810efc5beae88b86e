package admin.domains.content.biz.impl;

import java.util.Iterator;
import admin.domains.content.entity.AdminUser;
import java.util.List;
import admin.domains.content.entity.AdminUserActionLog;
import admin.domains.content.vo.AdminUserActionLogVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.dao.AdminUserActionLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import org.springframework.stereotype.Service;
import admin.domains.content.biz.AdminUserActionLogService;

@Service
public class AdminUserActionLogServiceImpl implements AdminUserActionLogService
{
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserActionLogDao adminUserActionLogDao;
    @Autowired
    private AdminDataFactory adminDataFactory;
    
    @Override
    public PageList search(final String username, final String actionId, final String error, final String sTime, final String eTime, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 20;
        }
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        if (StringUtil.isNotNull(sTime)) {
            criterions.add((Criterion)Restrictions.gt("time", (Object)sTime));
        }
        if (StringUtil.isNotNull(eTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)eTime));
        }
        if (StringUtil.isNotNull(username)) {
            final AdminUser uEntity = this.adminUserDao.getByUsername(username);
            if (uEntity != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)uEntity.getId()));
            }
        }
        if (StringUtil.isInteger(actionId)) {
            criterions.add((Criterion)Restrictions.eq("actionId", (Object)Integer.parseInt(actionId)));
        }
        if (StringUtil.isInteger(error)) {
            criterions.add((Criterion)Restrictions.eq("error", (Object)Integer.parseInt(error)));
        }
        final PageList pList = this.adminUserActionLogDao.find(criterions, orders, start, limit);
        final List<AdminUserActionLogVO> list = new ArrayList<AdminUserActionLogVO>();
        for (final Object tmpBean : pList.getList()) {
            list.add(new AdminUserActionLogVO((AdminUserActionLog)tmpBean, this.adminDataFactory));
        }
        pList.setList(list);
        return pList;
    }
}
