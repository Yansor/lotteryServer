package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.UserGameWaterBillService;
import lottery.domains.content.dao.UserGameWaterBillDao;
import lottery.domains.content.entity.UserGameWaterBill;
import lottery.domains.content.vo.user.UserGameWaterBillVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGameWaterBillServiceImpl
  implements UserGameWaterBillService
{
  @Autowired
  private UserGameWaterBillDao uGameWaterBillDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  public PageList search(Integer userId, String sTime, String eTime, Double minUserAmount, Double maxUserAmount, Integer type, Integer status, int start, int limit)
  {
    start = start < 0 ? 0 : start;
    limit = limit < 0 ? 0 : limit;
    limit = limit > 20 ? 20 : limit;
    
    List<Criterion> criterions = new ArrayList();
    if (userId != null) {
      criterions.add(Restrictions.eq("userId", userId));
    }
    if (StringUtil.isNotNull(sTime)) {
      criterions.add(Restrictions.ge("indicateDate", sTime));
    }
    if (StringUtil.isNotNull(eTime)) {
      criterions.add(Restrictions.lt("indicateDate", eTime));
    }
    if (minUserAmount != null) {
      criterions.add(Restrictions.ge("userAmount", minUserAmount));
    }
    if (maxUserAmount != null) {
      criterions.add(Restrictions.le("userAmount", maxUserAmount));
    }
    if (type != null) {
      criterions.add(Restrictions.eq("type", type));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", type));
    }
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    PageList pList = this.uGameWaterBillDao.search(criterions, orders, start, limit);
    List<UserGameWaterBillVO> convertList = new ArrayList();
    if ((pList != null) && (pList.getList() != null)) {
      for (Object tmpBean : pList.getList()) {
        convertList.add(new UserGameWaterBillVO((UserGameWaterBill)tmpBean, this.dataFactory));
      }
    }
    pList.setList(convertList);
    return pList;
  }
  
  public boolean add(UserGameWaterBill bill)
  {
    return this.uGameWaterBillDao.add(bill);
  }
}
