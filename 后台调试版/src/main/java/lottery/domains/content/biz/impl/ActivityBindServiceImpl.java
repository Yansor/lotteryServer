package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.ActivityBindService;
import lottery.domains.content.dao.ActivityBindBillDao;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.entity.ActivityBindBill;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.activity.ActivityBindBillVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityBindServiceImpl
  implements ActivityBindService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private ActivityBindBillDao aBindBillDao;
  @Autowired
  private UserBetsDao uBetsDao;
  @Autowired
  private UserRechargeDao uRechargeDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public PageList search(String username, String upperUser, String date, String keyword, Integer status, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    int i;
    if (StringUtil.isNotNull(upperUser))
    {
      User user = this.uDao.getByUsername(upperUser);
      if (user != null)
      {
        List<User> lowers = this.uDao.getUserLower(user.getId());
        if (lowers.size() > 0)
        {
          Integer[] ids = new Integer[lowers.size()];
          for (i = 0; i < lowers.size(); i++) {
            ids[i] = Integer.valueOf(((User)lowers.get(i)).getId());
          }
          criterions.add(Restrictions.in("userId", ids));
        }
        else
        {
          isSearch = false;
        }
      }
      else
      {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(date)) {
      criterions.add(Restrictions.like("time", date, MatchMode.ANYWHERE));
    }
    if (StringUtil.isNotNull(keyword))
    {
      Disjunction disjunction = Restrictions.or(new Criterion[0]);
      disjunction.add(Restrictions.eq("ip", keyword));
      disjunction.add(Restrictions.eq("bindName", keyword));
      disjunction.add(Restrictions.eq("bindCard", keyword));
      criterions.add(disjunction);
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", status));
    }
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<ActivityBindBillVO> list = new ArrayList();
      PageList pList = this.aBindBillDao.find(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList())
      {
        ActivityBindBill bBean = (ActivityBindBill)tmpBean;
        User uBean = this.uDao.getById(bBean.getUserId());
        ActivityBindBillVO vBean = new ActivityBindBillVO(bBean, uBean, this.lotteryDataFactory);
        
        boolean isRecharge = this.uRechargeDao.isRecharge(bBean.getUserId());
        vBean.setRecharge(isRecharge);
        
        boolean isCost = this.uBetsDao.isCost(bBean.getUserId());
        vBean.setCost(isCost);
        list.add(vBean);
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public boolean refuse(int id)
  {
    ActivityBindBill entity = this.aBindBillDao.getById(id);
    if ((entity != null) && 
      (entity.getStatus() == 0))
    {
      entity.setStatus(-1);
      return this.aBindBillDao.update(entity);
    }
    return false;
  }
  
  public boolean check(int id)
  {
    ActivityBindBill entity = this.aBindBillDao.getById(id);
    if (entity != null)
    {
      List<ActivityBindBill> list = this.aBindBillDao.get(entity.getIp(), entity.getBindName(), entity.getBindCard());
      if ((list != null) && (list.size() > 1)) {
        return true;
      }
    }
    return false;
  }
}
