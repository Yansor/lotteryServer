package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.ActivitySignService;
import lottery.domains.content.dao.ActivitySignBillDao;
import lottery.domains.content.dao.ActivitySignRecordDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserLotteryReportDao;
import lottery.domains.content.entity.ActivitySignBill;
import lottery.domains.content.entity.ActivitySignRecord;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserLotteryReport;
import lottery.domains.content.vo.activity.ActivitySignBillVO;
import lottery.domains.content.vo.activity.ActivitySignRecordVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitySignServiceImpl
  implements ActivitySignService
{
  @Autowired
  private UserDao userDao;
  @Autowired
  private ActivitySignBillDao activitySignBillDao;
  @Autowired
  private ActivitySignRecordDao activitySignRecordDao;
  @Autowired
  private UserLotteryReportDao uLotteryReportDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public PageList searchBill(String username, String date, int start, int limit)
  {
    if (start < 0) {
      start = 0;
    }
    if (limit < 0) {
      limit = 10;
    }
    if (limit > 100) {
      limit = 100;
    }
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("id"));
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User uBean = this.userDao.getByUsername(username);
      if (uBean != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(uBean.getId())));
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(date)) {
      criterions.add(Restrictions.like("time", date, MatchMode.ANYWHERE));
    }
    if (isSearch)
    {
      PageList pList = this.activitySignBillDao.find(criterions, orders, start, limit);
      List<ActivitySignBillVO> list = new ArrayList();
      for (Object o : pList.getList()) {
        list.add(new ActivitySignBillVO((ActivitySignBill)o, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  double getTotalCost(String startTime, String endTime, int userId)
  {
    String sDate = new Moment().fromTime(startTime).toSimpleDate();
    String eDate = new Moment().fromTime(endTime).add(1, "days").toSimpleDate();
    int[] ids = { userId };
    List<UserLotteryReport> lotteryDayList = this.uLotteryReportDao.getDayList(ids, sDate, eDate);
    double totalCost = 0.0D;
    for (UserLotteryReport tmpBean : lotteryDayList) {
      totalCost += tmpBean.getBillingOrder();
    }
    return totalCost;
  }
  
  public PageList searchRecord(String username, int start, int limit)
  {
    if (start < 0) {
      start = 0;
    }
    if (limit < 0) {
      limit = 10;
    }
    if (limit > 100) {
      limit = 100;
    }
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("lastSignTime"));
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User uBean = this.userDao.getByUsername(username);
      if (uBean != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(uBean.getId())));
      } else {
        isSearch = false;
      }
    }
    if (isSearch)
    {
      PageList pList = this.activitySignRecordDao.find(criterions, orders, start, limit);
      List<ActivitySignRecordVO> list = new ArrayList();
      for (Object o : pList.getList())
      {
        ActivitySignRecord signRecord = (ActivitySignRecord)o;
        
        list.add(new ActivitySignRecordVO(signRecord, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
}
