package admin.domains.content.biz.impl;

import admin.domains.content.biz.AdminUserCriticalLogService;
import admin.domains.content.dao.AdminUserCriticalLogDao;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserCriticalLog;
import admin.domains.content.vo.AdminUserActionVO;
import admin.domains.content.vo.AdminUserCriticalLogVO;
import admin.domains.pool.AdminDataFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserCriticalLogServiceImpl
  implements AdminUserCriticalLogService
{
  @Autowired
  private AdminUserDao adminUserDao;
  @Autowired
  private AdminUserCriticalLogDao adminUserCriticalLogDao;
  @Autowired
  private AdminDataFactory adminDataFactory;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public PageList search(Integer actionId, String username, String ip, String keyword, String sDate, String eDate, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      AdminUser user = this.adminUserDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(ip)) {
      criterions.add(Restrictions.eq("ip", ip));
    }
    if (actionId != null) {
      criterions.add(Restrictions.eq("actionId", actionId));
    }
    if (StringUtil.isNotNull(keyword))
    {
      StringTokenizer token = new StringTokenizer(keyword);
      Disjunction disjunction = Restrictions.or(new Criterion[0]);
      while (token.hasMoreElements())
      {
        String value = (String)token.nextElement();
        disjunction.add(Restrictions.like("action", value, MatchMode.ANYWHERE));
      }
      criterions.add(disjunction);
    }
    if (StringUtil.isNotNull(sDate)) {
      criterions.add(Restrictions.ge("time", sDate));
    }
    if (StringUtil.isNotNull(eDate)) {
      criterions.add(Restrictions.lt("time", eDate));
    }
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<AdminUserCriticalLogVO> list = new ArrayList();
      PageList pList = this.adminUserCriticalLogDao.find(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new AdminUserCriticalLogVO((AdminUserCriticalLog)tmpBean, this.adminDataFactory, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public PageList search(String username, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      UserVO user = this.lotteryDataFactory.getUser(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    Object[] actionId = { Integer.valueOf(10008), Integer.valueOf(10105), Integer.valueOf(10020) };
    criterions.add(Restrictions.in("actionId", actionId));
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<AdminUserCriticalLogVO> list = new ArrayList();
      PageList pList = this.adminUserCriticalLogDao.find(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new AdminUserCriticalLogVO((AdminUserCriticalLog)tmpBean, this.adminDataFactory, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public List<AdminUserActionVO> findAction()
  {
    List<AdminUserCriticalLog> list = this.adminUserCriticalLogDao.findAction();
    List<AdminUserActionVO> adminUserActionVOs = new ArrayList();
    for (AdminUserCriticalLog adminUserCriticalLog : list)
    {
      AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(adminUserCriticalLog.getActionId());
      if (adminUserAction != null) {
        adminUserActionVOs.add(new AdminUserActionVO(adminUserAction, this.adminDataFactory));
      }
    }
    return adminUserActionVOs;
  }
}
