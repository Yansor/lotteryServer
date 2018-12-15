package lottery.domains.content.biz.impl;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.PaymentChannelBankService;
import lottery.domains.content.biz.PaymentChannelService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.dao.UserWithdrawLimitDao;
import lottery.domains.content.dao.UserWithdrawLogDao;
import lottery.domains.content.entity.HistoryUserWithdraw;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.entity.UserWithdrawLog;
import lottery.domains.content.payment.RX.RXPayment;
import lottery.domains.content.payment.af.AFPayment;
import lottery.domains.content.payment.cf.CFPayment;
import lottery.domains.content.payment.fkt.FKTPayment;
import lottery.domains.content.payment.ht.HTPayment;
import lottery.domains.content.payment.htf.HTFPayment;
import lottery.domains.content.payment.tgf.TGFPayment;
import lottery.domains.content.payment.yr.YRPayment;
import lottery.domains.content.payment.zs.ZSPayment;
import lottery.domains.content.vo.user.HistoryUserWithdrawVO;
import lottery.domains.content.vo.user.UserWithdrawVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWithdrawServiceImpl
  implements UserWithdrawService
{
  public static final int[] PROCESSING_STATUSES = { -3, 
    1, 3 };
  private static final Logger log = LoggerFactory.getLogger(UserWithdrawServiceImpl.class);
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserWithdrawDao uWithdrawDao;
  @Autowired
  private UserWithdrawLimitDao uWithdrawLimitDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserSysMessageService uSysMessageService;
  @Autowired
  private ZSPayment zsPayment;
  @Autowired
  private RXPayment rxPayment;
  @Autowired
  private HTPayment htPayment;
  @Autowired
  private CFPayment cfPayment;
  @Autowired
  private FKTPayment fktPayment;
  @Autowired
  private HTFPayment htfPayment;
  @Autowired
  private YRPayment yrPayment;
  @Autowired
  private AFPayment afPayment;
  @Autowired
  private TGFPayment tgfPayment;
  @Autowired
  private PaymentChannelService paymentChannelService;
  @Autowired
  private PaymentChannelBankService paymentChannelBankService;
  @Autowired
  private UserCardService uCardService;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private UserWithdrawLogDao userWithdrawLogDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public UserWithdrawVO getById(int id)
  {
    UserWithdraw bean = this.uWithdrawDao.getById(id);
    if (bean != null) {
      return new UserWithdrawVO(bean, this.lotteryDataFactory);
    }
    return null;
  }
  
  public HistoryUserWithdrawVO getHistoryById(int id)
  {
    HistoryUserWithdraw bean = this.uWithdrawDao.getHistoryById(id);
    if (bean != null) {
      return new HistoryUserWithdrawVO(bean, this.lotteryDataFactory);
    }
    return null;
  }
  
  public List<UserWithdrawVO> getLatest(int userId, int status, int count)
  {
    List<UserWithdrawVO> formatList = new ArrayList();
    List<UserWithdraw> list = this.uWithdrawDao.getLatest(userId, status, count);
    for (UserWithdraw tmpBean : list) {
      formatList.add(new UserWithdrawVO(tmpBean, this.lotteryDataFactory));
    }
    return formatList;
  }
  
  public List<UserWithdraw> listByRemitStatus(int[] remitStatuses, boolean third, String sTime, String eTime)
  {
    return this.uWithdrawDao.listByRemitStatus(remitStatuses, third, sTime, eTime);
  }
  
  public PageList search(Integer type, String billno, String username, String minTime, String maxTime, String minOperatorTime, String maxOperatorTime, Double minMoney, Double maxMoney, String keyword, Integer status, Integer checkStatus, Integer remitStatus, Integer paymentChannelId, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    StringBuilder queryStr = new StringBuilder();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        queryStr.append(" and b.user_id  = ").append(user.getId());
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(billno)) {
      queryStr.append(" and b.billno  = ").append("'" + billno + "'");
    }
    if (StringUtil.isNotNull(minTime)) {
      queryStr.append(" and b.time  > ").append("'" + minTime + "'");
    }
    if (StringUtil.isNotNull(maxTime)) {
      queryStr.append(" and b.time  < ").append("'" + maxTime + "'");
    }
    if (StringUtil.isNotNull(minOperatorTime)) {
      queryStr.append(" and b.operator_time  > ").append("'" + minOperatorTime + "'");
    }
    if (StringUtil.isNotNull(maxOperatorTime)) {
      queryStr.append(" and b.operator_time  < ").append("'" + maxOperatorTime + "'");
    }
    if (minMoney != null) {
      queryStr.append(" and b.money  >= ").append(minMoney.doubleValue());
    }
    if (maxMoney != null) {
      queryStr.append(" and b.money  <= ").append(maxMoney.doubleValue());
    }
    if (StringUtil.isNotNull(keyword)) {
      queryStr.append("and (b.card_name like %" + keyword + "% or b.card_id like  %" + keyword + "% )");
    }
    if (status != null) {
      queryStr.append(" and b.status  = ").append(status.intValue());
    }
    if (checkStatus != null) {
      queryStr.append(" and b.check_status  = ").append(checkStatus.intValue());
    }
    if (remitStatus != null) {
      queryStr.append(" and b.remit_status  = ").append(remitStatus.intValue());
    }
    if (paymentChannelId != null) {
      queryStr.append(" and b.payment_channel_id  = ").append(paymentChannelId);
    }
    if (type != null) {
      queryStr.append(" and  u.type  = ").append(type);
    } else {
      queryStr.append(" and u.upid  != ").append(0);
    }
    queryStr.append("  ORDER BY b.time,b.id DESC ");
    if (isSearch)
    {
      List<UserWithdrawVO> list = new ArrayList();
      PageList pList = this.uWithdrawDao.find(queryStr.toString(), start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new UserWithdrawVO((UserWithdraw)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public PageList searchHistory(String billno, String username, String minTime, String maxTime, String minOperatorTime, String maxOperatorTime, Double minMoney, Double maxMoney, String keyword, Integer status, Integer checkStatus, Integer remitStatus, Integer paymentChannelId, int start, int limit)
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
    if (StringUtil.isNotNull(billno)) {
      criterions.add(Restrictions.eq("billno", billno));
    }
    if (StringUtil.isNotNull(minTime)) {
      criterions.add(Restrictions.gt("time", minTime));
    }
    if (StringUtil.isNotNull(maxTime)) {
      criterions.add(Restrictions.lt("time", maxTime));
    }
    if (StringUtil.isNotNull(minOperatorTime)) {
      criterions.add(Restrictions.gt("operatorTime", minOperatorTime));
    }
    if (StringUtil.isNotNull(maxOperatorTime)) {
      criterions.add(Restrictions.lt("operatorTime", maxOperatorTime));
    }
    if (minMoney != null) {
      criterions.add(Restrictions.ge("money", Double.valueOf(minMoney.doubleValue())));
    }
    if (maxMoney != null) {
      criterions.add(Restrictions.le("money", Double.valueOf(maxMoney.doubleValue())));
    }
    if (StringUtil.isNotNull(keyword)) {
      criterions.add(Restrictions.or(Restrictions.like("cardName", keyword, MatchMode.ANYWHERE), 
        Restrictions.like("cardId", keyword, MatchMode.ANYWHERE)));
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", Integer.valueOf(status.intValue())));
    }
    if (checkStatus != null) {
      criterions.add(Restrictions.eq("checkStatus", Integer.valueOf(checkStatus.intValue())));
    }
    if (remitStatus != null) {
      criterions.add(Restrictions.eq("remitStatus", Integer.valueOf(remitStatus.intValue())));
    }
    if (paymentChannelId != null) {
      criterions.add(Restrictions.eq("paymentChannelId", paymentChannelId));
    }
    orders.add(Order.desc("time"));
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<HistoryUserWithdrawVO> list = new ArrayList();
      PageList pList = this.uWithdrawDao.findHistory(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new HistoryUserWithdrawVO((HistoryUserWithdraw)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public boolean manualPay(AdminUser uEntity, WebJSONObject json, int id, String payBillno, String remarks, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      if (entity.getLockStatus() == 1)
      {
        if (operatorUser.equals(entity.getOperatorUser()))
        {
          String operatorTime = new Moment().toSimpleTime();
          String infos = "您的提现已提交至银行处理,请耐心等候！";
          
          entity.setInfos(infos);
          entity.setPayBillno(payBillno);
          entity.setOperatorUser(operatorUser);
          entity.setOperatorTime(operatorTime);
          entity.setRemarks("手动出款");
          entity.setRemitStatus(1);
          entity.setPayType(Integer.valueOf(2));
          entity.setPaymentChannelId(Integer.valueOf(-1));
          boolean result = this.uWithdrawDao.update(entity);
          if (result)
          {
            String time = new Moment().toSimpleTime();
            String action = String.format("手动出款；操作人：%s", new Object[] { uEntity.getUsername() });
            this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
              uEntity.getId(), action, time));
          }
          return result;
        }
        json.set(Integer.valueOf(2), "2-2021");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-2020");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean completeRemit(AdminUser uEntity, WebJSONObject json, int id, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0)) {
      if (entity.getLockStatus() == 1)
      {
        if (operatorUser.equals(entity.getOperatorUser()))
        {
          String operatorTime = new Moment().toSimpleTime();
          String infos = "您的提现已处理，请您注意查收！";
          entity.setStatus(1);
          entity.setInfos(infos);
          entity.setOperatorUser(operatorUser);
          entity.setOperatorTime(operatorTime);
          entity.setLockStatus(0);
          entity.setRemitStatus(2);
          boolean result = this.uWithdrawDao.update(entity);
          if (result)
          {
            this.uBillService.addWithdrawReport(entity);
            this.uSysMessageService.addConfirmWithdraw(entity.getUserId(), entity.getMoney(), 
              entity.getRecMoney());
            String time = new Moment().toSimpleTime();
            String action = String.format("<span style=\"color: #35AA47;\">打款完成</span>；操作人：%s", new Object[] {
              uEntity.getUsername() });
            this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
              uEntity.getId(), action, time));
          }
          return result;
        }
        json.set(Integer.valueOf(2), "2-2021");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-2019");
      }
    }
    return false;
  }
  
  public boolean apiPay(AdminUser uEntity, WebJSONObject json, int id, PaymentChannel channel)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity == null) || (entity.getStatus() != 0))
    {
      json.set(Integer.valueOf(2), "2-2019");
      return false;
    }
    if (entity.getLockStatus() != 1)
    {
      json.set(Integer.valueOf(2), "2-2020");
      return false;
    }
    if (!uEntity.getUsername().equals(entity.getOperatorUser()))
    {
      json.set(Integer.valueOf(2), "2-2021");
      return false;
    }
    UserCard card = this.uCardService.getByUserAndCardId(entity.getUserId(), entity.getCardId().trim());
    if (card == null)
    {
      json.set(Integer.valueOf(2), "2-4011");
      return false;
    }
    PaymentChannelBank bank = this.paymentChannelBankService.getByChannelAndBankId(channel.getApiPayBankChannelCode(), 
      card.getBankId());
    
    String payBillno = null;
    String operatorTime = new Moment().toSimpleTime();
    String str1;
    switch ((str1 = channel.getChannelCode()).hashCode())
    {
    case -1596233098: 
      if (str1.equals("htAlipay")) {
        break;
      }
      break;
    case -1470714580: 
      if (str1.equals("rxWeChat")) {}
      break;
    case -1260499827: 
      if (str1.equals("cfAlipay")) {}
      break;
    case -1177921821: 
      if (str1.equals("zsAlipay")) {}
      break;
    case -973996174: 
      if (str1.equals("htWeChat")) {
        break;
      }
      break;
    case -694198332: 
      if (str1.equals("htfAlipay")) {}
      break;
    case -638262903: 
      if (str1.equals("cfWeChat")) {}
      break;
    case -569487244: 
      if (str1.equals("htfJDPay")) {}
      break;
    case -555684897: 
      if (str1.equals("zsWeChat")) {}
      break;
    case -451153201: 
      if (str1.equals("afAlipay")) {}
      break;
    case -339530887: 
      if (str1.equals("fktAlipay")) {}
      break;
    case -71961408: 
      if (str1.equals("htfWeChat")) {}
      break;
    case 3109: 
      if (str1.equals("af")) {}
      break;
    case 3171: 
      if (str1.equals("cf")) {}
      break;
    case 3340: 
      if (str1.equals("ht")) {
        break;
      }
      break;
    case 3654: 
      if (str1.equals("rx")) {}
      break;
    case 3865: 
      if (str1.equals("yr")) {}
      break;
    case 3897: 
      if (str1.equals("zs")) {}
      break;
    case 101455: 
      if (str1.equals("fkt")) {}
      break;
    case 103642: 
      if (str1.equals("htf")) {}
      break;
    case 114771: 
      if (str1.equals("tgf")) {}
      break;
    case 2990341: 
      if (str1.equals("afQQ")) {}
      break;
    case 3049923: 
      if (str1.equals("cfQQ")) {}
      break;
    case 3212332: 
      if (str1.equals("htQQ")) {
        break;
      }
      break;
    case 3514086: 
      if (str1.equals("rxQQ")) {}
      break;
    case 3716857: 
      if (str1.equals("yrQQ")) {}
      break;
    case 3747609: 
      if (str1.equals("zsQQ")) {}
      break;
    case 97500847: 
      if (str1.equals("fktQQ")) {}
      break;
    case 99602554: 
      if (str1.equals("htfQQ")) {}
      break;
    case 110297523: 
      if (str1.equals("tgfQQ")) {}
      break;
    case 171083723: 
      if (str1.equals("afWeChat")) {}
      break;
    case 216754331: 
      if (str1.equals("tgfJDPay")) {}
      break;
    case 224702810: 
      if (str1.equals("tgfQuick")) {}
      break;
    case 282706037: 
      if (str1.equals("fktWeChat")) {}
      break;
    case 486731459: 
      if (str1.equals("yrAlipay")) {}
      break;
    case 659170955: 
      if (str1.equals("cfJDPay")) {}
      break;
    case 1108968383: 
      if (str1.equals("yrWeChat")) {}
      break;
    case 1202530178: 
      if (str1.equals("htJDPay")) {
        break;
      }
      break;
    case 1243068959: 
      if (!str1.equals("fktJDPay"))
      {
//        break label1232;
//        payBillno = this.htPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.zsPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.rxPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.cfPayment.daifu(json, entity, card, bank, channel);
      }
      else
      {
        payBillno = this.fktPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.htfPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.yrPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.afPayment.daifu(json, entity, card, bank, channel);
//        break label1232;
//        payBillno = this.tgfPayment.daifu(json, entity, card, bank, channel);
      }
      break;
    }
    label1232:
    return apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
  }
  
  private boolean apiPayResultProcess(AdminUser uEntity, UserWithdraw entity, String operatorTime, PaymentChannel channel, String payBillno, WebJSONObject json)
  {
    if ((json.getError() != null) && (json.getError().intValue() == -1))
    {
      String infos = "您的提现已提交至银行处理,请耐心等候！";
      entity.setInfos(infos);
      entity.setOperatorUser(uEntity.getUsername());
      entity.setOperatorTime(operatorTime);
      entity.setRemarks("使用" + channel.getName() + "代付");
      entity.setRemitStatus(-3);
      entity.setPayType(Integer.valueOf(1));
      entity.setPaymentChannelId(Integer.valueOf(channel.getId()));
      boolean result = this.uWithdrawDao.update(entity);
      if (result)
      {
        String time = new Moment().toSimpleTime();
        String action = String.format("使用" + channel.getName() + "代付，连接异常，系统开始自动同步状态；操作人：%s", new Object[] {
          uEntity.getUsername() });
        this.userWithdrawLogDao.add(
          new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
      }
      return result;
    }
    if (StringUtils.isNotEmpty(payBillno))
    {
      String infos = "您的提现已处理，请您注意查收！";
      entity.setInfos(infos);
      entity.setPayBillno(payBillno);
      entity.setOperatorUser(uEntity.getUsername());
      entity.setOperatorTime(operatorTime);
      entity.setRemarks("使用" + channel.getName() + "代付");
      entity.setRemitStatus(3);
      entity.setPayType(Integer.valueOf(1));
      entity.setPaymentChannelId(Integer.valueOf(channel.getId()));
      boolean result = this.uWithdrawDao.update(entity);
      if (result)
      {
        String time = new Moment().toSimpleTime();
        String action = String.format("使用" + channel.getName() + "代付，提交成功；操作人：%s", new Object[] { uEntity.getUsername() });
        this.userWithdrawLogDao.add(
          new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
      }
      return result;
    }
    return false;
  }
  
  public boolean check(AdminUser uEntity, WebJSONObject json, int id, int status)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      if (entity.getCheckStatus() == 0)
      {
        entity.setCheckStatus(status);
        Boolean boolean1 = Boolean.valueOf(this.uWithdrawDao.update(entity));
        if (boolean1.booleanValue())
        {
          this.uWithdrawLimitDao.delByUserId(entity.getUserId());
          String time = new Moment().toSimpleTime();
          String action = String.format("<font color=\"#35AA47\">审核通过</font>；操作人：%s", new Object[] { uEntity.getUsername() });
          this.userWithdrawLogDao.add(
            new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
        }
        return boolean1.booleanValue();
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean refuse(AdminUser uEntity, WebJSONObject json, int id, String reason, String remarks, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      if (entity.getLockStatus() == 1)
      {
        if (operatorUser.equals(entity.getOperatorUser()))
        {
          User uBean = this.uDao.getById(entity.getUserId());
          if (uBean != null)
          {
            boolean uflag = this.uDao.updateLotteryMoney(entity.getUserId(), entity.getMoney());
            if (uflag)
            {
              String operatorTime = new Moment().toSimpleTime();
              String infos = "您的提现已被拒绝，金额已返还！";
              if (StringUtil.isNotNull(reason)) {
                infos = infos + "原因：" + reason;
              }
              if (StringUtil.isNotNull(remarks)) {
                infos = infos + "备注：" + remarks;
              }
              entity.setStatus(-1);
              entity.setInfos(infos);
              entity.setOperatorUser(operatorUser);
              entity.setOperatorTime(operatorTime);
              entity.setRemarks(infos);
              entity.setLockStatus(0);
              boolean flag = this.uWithdrawDao.update(entity);
              if (flag)
              {
                this.uBillService.addDrawBackBill(entity, uBean, entity.getRemarks());
                this.uSysMessageService.addRefuseWithdraw(entity.getUserId(), entity.getMoney());
                String time = new Moment().toSimpleTime();
                String action = String.format("<font color=\"#D84A38\">拒绝支付</font>；操作人：%s；%s", new Object[] {
                  uEntity.getUsername(), infos });
                this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
                  uEntity.getId(), action, time));
              }
              return flag;
            }
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-2021");
        }
      }
      else {
        json.set(Integer.valueOf(2), "2-2020");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean withdrawFailure(AdminUser uEntity, WebJSONObject json, int id, String remarks, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      if (entity.getLockStatus() == 1)
      {
        if (operatorUser.equals(entity.getOperatorUser()))
        {
          User uBean = this.uDao.getById(entity.getUserId());
          if (uBean != null)
          {
            boolean uflag = this.uDao.updateLotteryMoney(entity.getUserId(), entity.getMoney());
            if (uflag)
            {
              String operatorTime = new Moment().toSimpleTime();
              String infos = "您的提现失败，金额已返还！";
              if (StringUtil.isNotNull(remarks)) {
                infos = infos + "备注：" + remarks;
              }
              entity.setStatus(1);
              entity.setInfos(infos);
              entity.setOperatorUser(operatorUser);
              entity.setOperatorTime(operatorTime);
              entity.setRemarks(infos);
              entity.setLockStatus(0);
              entity.setRemitStatus(-2);
              boolean flag = this.uWithdrawDao.update(entity);
              if (flag)
              {
                this.uBillService.addDrawBackBill(entity, uBean, entity.getRemarks());
                this.uSysMessageService.addRefuse(entity.getUserId(), entity.getMoney());
                String time = new Moment().toSimpleTime();
                String action = String.format("<span style=\"color: #D84A38;\">打款失败</span>；操作人：%s;%s", new Object[] {
                  uEntity.getUsername(), infos });
                this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
                  uEntity.getId(), action, time));
              }
              return flag;
            }
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-2021");
        }
      }
      else {
        json.set(Integer.valueOf(2), "2-2020");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean reviewedFail(AdminUser uEntity, WebJSONObject json, int id, String remarks, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      User uBean = this.uDao.getById(entity.getUserId());
      if (uBean != null)
      {
        boolean uflag = this.uDao.updateLotteryMoney(entity.getUserId(), entity.getMoney());
        if (uflag)
        {
          String infos = "您的提现审核失败，金额已返还！";
          if (StringUtil.isNotNull(remarks)) {
            infos = infos + "备注：" + remarks;
          }
          entity.setStatus(1);
          entity.setInfos(infos);
          entity.setOperatorUser(operatorUser);
          entity.setRemarks(infos);
          entity.setLockStatus(0);
          entity.setCheckStatus(-1);
          boolean flag = this.uWithdrawDao.update(entity);
          if (flag)
          {
            this.uBillService.addDrawBackBill(entity, uBean, entity.getRemarks());
            this.uSysMessageService.addShFail(entity.getUserId(), entity.getMoney());
            String time = new Moment().toSimpleTime();
            String action = String.format("<font color=\"#D84A38\">审核拒绝</font>；操作人：%s；%s", new Object[] {
              uEntity.getUsername(), infos });
            this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
              uEntity.getId(), action, time));
          }
          return flag;
        }
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean lock(AdminUser uEntity, WebJSONObject json, int id, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      if (entity.getCheckStatus() != 0)
      {
        if (entity.getLockStatus() == 0)
        {
          String operatorTime = new Moment().toSimpleTime();
          Boolean boolean1 = Boolean.valueOf(this.uWithdrawDao.lock(entity.getBillno(), operatorUser, operatorTime));
          if (boolean1.booleanValue())
          {
            String time = new Moment().toSimpleTime();
            String action = String.format("锁定；操作人：%s", new Object[] { uEntity.getUsername() });
            this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
              uEntity.getId(), action, time));
          }
          return boolean1.booleanValue();
        }
        json.set(Integer.valueOf(2), "2-2021");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-2023");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean unlock(AdminUser uEntity, WebJSONObject json, int id, String operatorUser)
  {
    UserWithdraw entity = this.uWithdrawDao.getById(id);
    if ((entity != null) && (entity.getStatus() == 0))
    {
      if (entity.getLockStatus() == 1)
      {
        if ((operatorUser != null) && (operatorUser.equals(entity.getOperatorUser())))
        {
          Boolean boolean1 = Boolean.valueOf(this.uWithdrawDao.unlock(entity.getBillno(), operatorUser));
          if (boolean1.booleanValue())
          {
            String time = new Moment().toSimpleTime();
            String action = String.format("解锁；操作人：%s", new Object[] { uEntity.getUsername() });
            this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), 
              uEntity.getId(), action, time));
          }
          return boolean1.booleanValue();
        }
        json.set(Integer.valueOf(2), "2-2021");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-2020");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-2019");
    }
    return false;
  }
  
  public boolean update(UserWithdraw withdraw)
  {
    return this.uWithdrawDao.update(withdraw);
  }
  
  public double[] getTotalWithdraw(String billno, String username, String minTime, String maxTime, String minOperatorTime, String maxOperatorTime, Double minMoney, Double maxMoney, String keyword, Integer status, Integer checkStatus, Integer remitStatus, Integer paymentChannelId)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.uWithdrawDao.getTotalWithdraw(billno, userId, minTime, maxTime, minOperatorTime, maxOperatorTime, 
      minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
  }
  
  public double[] getHistoryTotalWithdraw(String billno, String username, String minTime, String maxTime, String minOperatorTime, String maxOperatorTime, Double minMoney, Double maxMoney, String keyword, Integer status, Integer checkStatus, Integer remitStatus, Integer paymentChannelId)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.uWithdrawDao.getHistoryTotalWithdraw(billno, userId, minTime, maxTime, minOperatorTime, maxOperatorTime, 
      minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
  }
}
