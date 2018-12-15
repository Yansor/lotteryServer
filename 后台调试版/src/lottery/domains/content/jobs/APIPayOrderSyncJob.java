package lottery.domains.content.jobs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.date.Moment;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserWithdrawLogService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.biz.impl.UserWithdrawServiceImpl;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.entity.UserWithdrawLog;
import lottery.domains.content.payment.RX.RXDaifuQueryResult;
import lottery.domains.content.payment.RX.RXPayment;
import lottery.domains.content.payment.af.AFDaifuQueryResult;
import lottery.domains.content.payment.af.AFPayment;
import lottery.domains.content.payment.cf.CFPayQueryResult;
import lottery.domains.content.payment.cf.CFPayment;
import lottery.domains.content.payment.fkt.FKTPayResult;
import lottery.domains.content.payment.fkt.FKTPayment;
import lottery.domains.content.payment.ht.HTPayResult;
import lottery.domains.content.payment.ht.HTPayment;
import lottery.domains.content.payment.htf.HTFPayQueryResult;
import lottery.domains.content.payment.htf.HTFPayment;
import lottery.domains.content.payment.tgf.TGFPayment;
import lottery.domains.content.payment.tgf.utils.QueryResponseEntity;
import lottery.domains.content.payment.yr.YRDaifuQueryResult;
import lottery.domains.content.payment.yr.YRPayment;
import lottery.domains.content.payment.zs.ZSDaifuQueryResult;
import lottery.domains.content.payment.zs.ZSPayment;
import lottery.domains.content.vo.user.UserWithdrawVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APIPayOrderSyncJob
{
  private static final Logger log = LoggerFactory.getLogger(APIPayOrderSyncJob.class);
  private static volatile boolean isRunning = false;
  private static final int SYNC_STATUS_TIMEOUT_MINUTES = 10;
  private static final int NULL_RESULT_TIMEOUT_MINUTES = 10;
  private static Map<String, Moment> FIRST_TIME_NULL_RESULT = new HashMap();
  @Autowired
  private UserWithdrawService uWithdrawService;
  @Autowired
  private UserWithdrawLogService userWithdrawLogService;
  @Autowired
  private HTPayment htPayment;
  @Autowired
  private ZSPayment zsPayment;
  @Autowired
  private RXPayment rxPayment;
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
  private UserBillService uBillService;
  @Autowired
  private UserSysMessageService uSysMessageService;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="0,20,40 * * * * ?")
  public void scheduler()
  {
    // Byte code:
    //   0: ldc 1
    //   2: dup
    //   3: astore_1
    //   4: monitorenter
    //   5: getstatic 59	lottery/domains/content/jobs/APIPayOrderSyncJob:isRunning	Z
    //   8: ifeq +6 -> 14
    //   11: aload_1
    //   12: monitorexit
    //   13: return
    //   14: iconst_1
    //   15: putstatic 59	lottery/domains/content/jobs/APIPayOrderSyncJob:isRunning	Z
    //   18: aload_1
    //   19: monitorexit
    //   20: goto +6 -> 26
    //   23: aload_1
    //   24: monitorexit
    //   25: athrow
    //   26: aload_0
    //   27: invokespecial 77	lottery/domains/content/jobs/APIPayOrderSyncJob:startSync	()V
    //   30: goto +29 -> 59
    //   33: astore_1
    //   34: getstatic 57	lottery/domains/content/jobs/APIPayOrderSyncJob:log	Lorg/slf4j/Logger;
    //   37: ldc 80
    //   39: aload_1
    //   40: invokeinterface 82 3 0
    //   45: iconst_0
    //   46: putstatic 59	lottery/domains/content/jobs/APIPayOrderSyncJob:isRunning	Z
    //   49: goto +14 -> 63
    //   52: astore_2
    //   53: iconst_0
    //   54: putstatic 59	lottery/domains/content/jobs/APIPayOrderSyncJob:isRunning	Z
    //   57: aload_2
    //   58: athrow
    //   59: iconst_0
    //   60: putstatic 59	lottery/domains/content/jobs/APIPayOrderSyncJob:isRunning	Z
    //   63: return
    // Line number table:
    //   Java source line #103	-> byte code offset #0
    //   Java source line #104	-> byte code offset #5
    //   Java source line #106	-> byte code offset #11
    //   Java source line #108	-> byte code offset #14
    //   Java source line #103	-> byte code offset #18
    //   Java source line #113	-> byte code offset #26
    //   Java source line #114	-> byte code offset #30
    //   Java source line #115	-> byte code offset #34
    //   Java source line #117	-> byte code offset #45
    //   Java source line #116	-> byte code offset #52
    //   Java source line #117	-> byte code offset #53
    //   Java source line #118	-> byte code offset #57
    //   Java source line #117	-> byte code offset #59
    //   Java source line #119	-> byte code offset #63
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	APIPayOrderSyncJob
    //   3	21	1	Ljava/lang/Object;	Object
    //   33	7	1	e	Exception
    //   52	6	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   5	13	23	finally
    //   14	20	23	finally
    //   23	25	23	finally
    //   26	30	33	java/lang/Exception
    //   26	45	52	finally
  }
  
  private void startSync()
  {
    List<UserWithdraw> withdrawOrders = getWithdrawOrders();
    if (CollectionUtils.isEmpty(withdrawOrders)) {
      return;
    }
    for (UserWithdraw withdrawOrder : withdrawOrders) {
      syncOrder(withdrawOrder);
    }
  }
  
  private void syncOrder(UserWithdraw withdrawOrder)
  {
    if ((withdrawOrder.getPaymentChannelId() == null) || (withdrawOrder.getPaymentChannelId().intValue() <= 0))
    {
      log.warn("API代付注单{}为未知第三方代付{}或不是第三方代付，本次不查询", withdrawOrder.getBillno(), withdrawOrder.getPaymentChannelId());
      return;
    }
    if (withdrawOrder.getRemitStatus() == -3) {
      processSyncStatus(withdrawOrder);
    } else {
      processRemitStatus(withdrawOrder);
    }
  }
  
  private void processSyncStatus(UserWithdraw withdrawOrder)
  {
    boolean timeout = isTimeoutForSyncStatus(withdrawOrder);
    if (timeout)
    {
      updateRemitStatus(withdrawOrder, -4);
      log.info("API代付单{}已超时，将注单修改为未知状态，且不再处理", withdrawOrder.getBillno());
      return;
    }
    processRemitStatus(withdrawOrder);
  }
  
  private void processRemitStatus(UserWithdraw withdrawOrder)
  {
    PaymentChannel channel = this.dataFactory.getPaymentChannelFullProperty(withdrawOrder.getPaymentChannelId().intValue());
    if (channel == null)
    {
      log.warn("API代付单{}为未知第三方支付{}，本次不查询", withdrawOrder.getBillno(), withdrawOrder.getPaymentChannelId());
      return;
    }
    Object[] thirdStatus = getThirdStatus(channel, withdrawOrder);
    if (thirdStatus == null)
    {
      if (FIRST_TIME_NULL_RESULT.containsKey(withdrawOrder.getBillno()))
      {
        Moment firstTimeNullResult = (Moment)FIRST_TIME_NULL_RESULT.get(withdrawOrder.getBillno());
        Moment now = new Moment();
        
        int minutes = now.difference(firstTimeNullResult, "minute");
        if (minutes >= 10)
        {
          FIRST_TIME_NULL_RESULT.remove(withdrawOrder.getBillno());
          updateRemitStatus(withdrawOrder, -4);
          log.info("API代付单{}，第三方{}超过{}分钟未返回数据，修改为未知状态", new Object[] { withdrawOrder.getBillno(), channel.getName(), Integer.valueOf(10) });
        }
      }
      else
      {
        FIRST_TIME_NULL_RESULT.put(withdrawOrder.getBillno(), new Moment());
        log.info("API代付单{}，第三方{}返回空数据，本次不修改", withdrawOrder.getBillno(), channel.getName());
      }
      return;
    }
    FIRST_TIME_NULL_RESULT.remove(withdrawOrder.getBillno());
    
    String payBillno = thirdStatus[0] == null ? null : thirdStatus[0].toString();
    int remitStatus = Integer.valueOf(thirdStatus[1].toString()).intValue();
    if (StringUtils.isEmpty(payBillno))
    {
      updateRemitStatus(withdrawOrder, -4);
      log.info("API代付单{}，第三方{}返回注单号为空，修改为未知状态", withdrawOrder.getBillno(), channel.getName());
      return;
    }
    if (remitStatus == withdrawOrder.getRemitStatus())
    {
      log.info("API代付单{}，第三方{}返回状态与数据库一致，本次不修改", withdrawOrder.getBillno(), channel.getName());
      return;
    }
    if ((StringUtils.isEmpty(withdrawOrder.getPayBillno())) && (StringUtils.isNotEmpty(payBillno))) {
      withdrawOrder.setPayBillno(payBillno);
    }
    if (remitStatus == 2)
    {
      updateAsBankProcessed(withdrawOrder, payBillno);
      log.info("API代付单{}，第三方{}返回状态表示银行已打款完成，本注单最终处理成功", withdrawOrder.getBillno(), channel.getName());
    }
    else
    {
      updateRemitStatus(withdrawOrder, remitStatus);
    }
  }
  
  private Object[] getThirdStatus(PaymentChannel channel, UserWithdraw order)
  {
    String str;
    switch ((str = channel.getChannelCode()).hashCode())
    {
    case -1596233098: 
      if (str.equals("htAlipay")) {
        break;
      }
      break;
    case -1470714580: 
      if (str.equals("rxWeChat")) {}
      break;
    case -1260499827: 
      if (str.equals("cfAlipay")) {}
      break;
    case -1177921821: 
      if (str.equals("zsAlipay")) {}
      break;
    case -1107887928: 
      if (str.equals("afQuick")) {}
      break;
    case -973996174: 
      if (str.equals("htWeChat")) {
        break;
      }
      break;
    case -694198332: 
      if (str.equals("htfAlipay")) {}
      break;
    case -638262903: 
      if (str.equals("cfWeChat")) {}
      break;
    case -569487244: 
      if (str.equals("htfJDPay")) {}
      break;
    case -555684897: 
      if (str.equals("zsWeChat")) {}
      break;
    case -451153201: 
      if (str.equals("afAlipay")) {}
      break;
    case -339530887: 
      if (str.equals("fktAlipay")) {}
      break;
    case -71961408: 
      if (str.equals("htfWeChat")) {}
      break;
    case 3109: 
      if (str.equals("af")) {}
      break;
    case 3171: 
      if (str.equals("cf")) {}
      break;
    case 3340: 
      if (str.equals("ht")) {
        break;
      }
      break;
    case 3654: 
      if (str.equals("rx")) {}
      break;
    case 3865: 
      if (str.equals("yr")) {}
      break;
    case 3897: 
      if (str.equals("zs")) {}
      break;
    case 101455: 
      if (str.equals("fkt")) {}
      break;
    case 103642: 
      if (str.equals("htf")) {}
      break;
    case 114771: 
      if (str.equals("tgf")) {}
      break;
    case 2990341: 
      if (str.equals("afQQ")) {}
      break;
    case 3049923: 
      if (str.equals("cfQQ")) {}
      break;
    case 3212332: 
      if (str.equals("htQQ")) {
        break;
      }
      break;
    case 3514086: 
      if (str.equals("rxQQ")) {}
      break;
    case 3716857: 
      if (str.equals("yrQQ")) {}
      break;
    case 3747609: 
      if (str.equals("zsQQ")) {}
      break;
    case 97500847: 
      if (str.equals("fktQQ")) {}
      break;
    case 99602554: 
      if (str.equals("htfQQ")) {}
      break;
    case 110297523: 
      if (str.equals("tgfQQ")) {}
      break;
    case 171083723: 
      if (str.equals("afWeChat")) {}
      break;
    case 216754331: 
      if (str.equals("tgfJDPay")) {}
      break;
    case 224702810: 
      if (str.equals("tgfQuick")) {}
      break;
    case 282706037: 
      if (str.equals("fktWeChat")) {}
      break;
    case 486731459: 
      if (str.equals("yrAlipay")) {}
      break;
    case 659170955: 
      if (str.equals("cfJDPay")) {}
      break;
    case 1108968383: 
      if (str.equals("yrWeChat")) {}
      break;
    case 1202530178: 
      if (str.equals("htJDPay")) {
        break;
      }
      break;
//    case 1243068959:
//      if (!str.equals("fktJDPay"))
//      {
//        break label923;
//        return getHTStatus(channel, order);
//
//        return getZSStatus(channel, order);
//
//        return getRXStatus(channel, order);
//
//        return getCFStatus(channel, order);
//      }
//      else
//      {
//        return getFKTStatus(channel, order);
//
//        return getHTFStatus(channel, order);
//
//        return getYRStatus(channel, order);
//
//        return getAFStatus(channel, order);
//
//        return getTGFStatus(channel, order);
//      }
//      break;
    }
    label923:
    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
  }
  
  private Object[] getTGFStatus(PaymentChannel channel, UserWithdraw order)
  {
    QueryResponseEntity result = this.tgfPayment.query(order, channel);
    if ((result != null) && (StringUtils.isNotEmpty(result.getStatus())))
    {
      int remitStatus = this.tgfPayment.transferBankStatus(result.getStatus());
      return new Object[] { order.getBillno(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getAFStatus(PaymentChannel channel, UserWithdraw order)
  {
    AFDaifuQueryResult result = this.afPayment.query(order, channel);
    if ((result != null) && (StringUtils.isNotEmpty(result.getResult())))
    {
      int remitStatus = this.afPayment.transferBankStatus(result.getResult());
      return new Object[] { result.getOrder_no(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getYRStatus(PaymentChannel channel, UserWithdraw order)
  {
    YRDaifuQueryResult result = this.yrPayment.query(order, channel);
    if ((result != null) && (StringUtils.isNotEmpty(result.getRemitStatus())))
    {
      int remitStatus = this.yrPayment.transferBankStatus(result.getRemitStatus());
      return new Object[] { result.getOutTradeNo(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getHTStatus(PaymentChannel channel, UserWithdraw order)
  {
    HTPayResult result = this.htPayment.query(order, channel);
    if ((result != null) && (StringUtils.isNotEmpty(result.getBankStatus())))
    {
      int remitStatus = this.htPayment.transferBankStatus(result.getBankStatus());
      return new Object[] { result.getOrderId(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getZSStatus(PaymentChannel channel, UserWithdraw order)
  {
    ZSDaifuQueryResult result = this.zsPayment.query(order, channel);
    if ((result != null) && (StringUtils.isNotEmpty(result.getState())))
    {
      int remitStatus = this.zsPayment.transferBankStatus(result.getState());
      return new Object[] { result.getOutOrderId(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getRXStatus(PaymentChannel channel, UserWithdraw order)
  {
    RXDaifuQueryResult result = this.rxPayment.query(order, channel);
    if ((result != null) && (StringUtils.isNotEmpty(result.getOrderId_state())))
    {
      int remitStatus = this.rxPayment.transferBankStatus(result.getOrderId_state());
      return new Object[] { result.getOrderId(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getCFStatus(PaymentChannel channel, UserWithdraw order)
  {
    CFPayQueryResult result = this.cfPayment.query(order, channel);
    if ((result != null) && (this.cfPayment.isAccepted(result)))
    {
      int remitStatus = this.cfPayment.transferBankStatus(result.getBatchContent());
      return new Object[] { result.getBatchNo(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getFKTStatus(PaymentChannel channel, UserWithdraw order)
  {
    FKTPayResult result = this.fktPayment.query(order, channel);
    if ((result != null) && (this.fktPayment.isAcceptedRequest(result.getIsSuccess())))
    {
      int remitStatus = this.fktPayment.transferBankStatus(result.getBankStatus());
      return new Object[] { result.getOrderId(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private Object[] getHTFStatus(PaymentChannel channel, UserWithdraw order)
  {
    HTFPayQueryResult result = this.htfPayment.query(order, channel);
    if ((result != null) && (this.htfPayment.isAcceptedRequest(result.getRetCode())) && (StringUtils.isNotEmpty(result.getHyBillNo())))
    {
      int remitStatus = this.htfPayment.transferBankStatus(result.getDetailData());
      return new Object[] { result.getHyBillNo(), Integer.valueOf(remitStatus) };
    }
    return null;
  }
  
  private void updateRemitStatus(UserWithdraw withdraw, int remitStatus)
  {
    UserWithdrawVO newestData = this.uWithdrawService.getById(withdraw.getId());
    if ((newestData == null) || (newestData.getBean() == null)) {
      return;
    }
    if (Arrays.binarySearch(UserWithdrawServiceImpl.PROCESSING_STATUSES, newestData.getBean().getRemitStatus()) <= -1)
    {
      log.warn("API代付注单{}不是可操作状态，无法将打款状态修改为{}, 本次不处理", withdraw.getBillno(), Integer.valueOf(remitStatus));
      return;
    }
    withdraw.setRemitStatus(remitStatus);
    this.uWithdrawService.update(withdraw);
    
    String content = "";
    if (StringUtils.isBlank(content)) {
      content = "未知";
    }
    String time = new Moment().toSimpleTime();
    String action = String.format("%s；操作人：系统", new Object[] { content });
    this.userWithdrawLogService.add(new UserWithdrawLog(withdraw.getBillno(), withdraw.getUserId(), -1, action, time));
  }
  
  private void updateAsBankProcessed(UserWithdraw withdraw, String payBillno)
  {
    UserWithdrawVO newestData = this.uWithdrawService.getById(withdraw.getId());
    if ((newestData == null) || (newestData.getBean() == null)) {
      return;
    }
    if (Arrays.binarySearch(UserWithdrawServiceImpl.PROCESSING_STATUSES, newestData.getBean().getRemitStatus()) <= -1)
    {
      log.warn("API代付注单{}不是处理中状态，无法修改为打款完成", withdraw.getBillno());
      return;
    }
    String infos = "您的提现已处理，请您注意查收！";
    withdraw.setStatus(1);
    withdraw.setInfos(infos);
    withdraw.setPayBillno(payBillno);
    withdraw.setLockStatus(0);
    withdraw.setRemitStatus(2);
    boolean result = this.uWithdrawService.update(withdraw);
    if (result)
    {
      this.uBillService.addWithdrawReport(withdraw);
      this.uSysMessageService.addConfirmWithdraw(withdraw.getUserId(), withdraw.getMoney(), withdraw.getRecMoney());
//      String content = RemitStatusConstants.Status.getTypeByContent(2);
      String time = new Moment().toSimpleTime();
      String action = String.format("%s；操作人：系统", new Object[] { "请联系qq895686843" });
      this.userWithdrawLogService.add(new UserWithdrawLog(withdraw.getBillno(), withdraw.getUserId(), -1, action, time));
    }
  }
  
  private boolean isTimeoutForSyncStatus(UserWithdraw withdraw)
  {
    Moment now = new Moment();
    Moment operateTime = new Moment().fromTime(withdraw.getOperatorTime());
    
    int minutes = now.difference(operateTime, "minute");
    
    return minutes >= 10;
  }
  
  private List<UserWithdraw> getWithdrawOrders()
  {
    String sTime = new Moment().subtract(1, "days").toSimpleDate();
    String eTime = new Moment().add(1, "days").toSimpleDate();
    return this.uWithdrawService.listByRemitStatus(UserWithdrawServiceImpl.PROCESSING_STATUSES, true, sTime, eTime);
  }
}
