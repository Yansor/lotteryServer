package admin.domains.content.biz.impl;

import admin.domains.content.biz.AdminUserActionLogService;
import admin.domains.content.dao.AdminUserActionLogDao;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserActionLog;
import admin.domains.content.vo.AdminUserActionLogVO;
import admin.domains.pool.AdminDataFactory;
import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserActionLogServiceImpl
  implements AdminUserActionLogService
{
  @Autowired
  private AdminUserDao adminUserDao;
  @Autowired
  private AdminUserActionLogDao adminUserActionLogDao;
  @Autowired
  private AdminDataFactory adminDataFactory;
  
  public PageList search(String username, String actionId, String error, String sTime, String eTime, int start, int limit)
  {
    if (start < 0) {
      start = 0;
    }
    if (limit < 0) {
      limit = 20;
    }
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.gt("time", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.lt("time", eTime));
    }
    if (StringUtil.isNotNull(username))
    {
      AdminUser uEntity = this.adminUserDao.getByUsername(username);
      if (uEntity != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(uEntity.getId())));
      }
    }
    if (StringUtil.isInteger(actionId)) {
      criterions.add(Restrictions.eq("actionId", Integer.valueOf(Integer.parseInt(actionId))));
    }
    if (StringUtil.isInteger(error)) {
      criterions.add(Restrictions.eq("error", Integer.valueOf(Integer.parseInt(error))));
    }
    PageList pList = this.adminUserActionLogDao.find(criterions, orders, start, limit);
    List<AdminUserActionLogVO> list = new ArrayList();
    for (Object tmpBean : pList.getList()) {
      list.add(new AdminUserActionLogVO((AdminUserActionLog)tmpBean, this.adminDataFactory));
    }
    pList.setList(list);
    return pList;
  }
}
