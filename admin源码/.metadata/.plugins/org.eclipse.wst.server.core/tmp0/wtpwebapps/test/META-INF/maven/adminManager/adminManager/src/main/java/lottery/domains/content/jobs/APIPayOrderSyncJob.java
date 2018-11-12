package lottery.domains.content.jobs;

import lottery.domains.content.vo.user.UserWithdrawVO;
import lottery.domains.content.entity.UserWithdrawLog;
import lottery.domains.content.global.RemitStatusConstants.Status;
import java.util.Arrays;
import lottery.domains.content.biz.impl.UserWithdrawServiceImpl;
import lottery.domains.content.payment.htf.HTFPayQueryResult;
import lottery.domains.content.payment.fkt.FKTPayResult;
import lottery.domains.content.payment.cf.CFPayQueryResult;
import lottery.domains.content.payment.RX.RXDaifuQueryResult;
import lottery.domains.content.payment.zs.ZSDaifuQueryResult;
import lottery.domains.content.payment.ht.HTPayResult;
import lottery.domains.content.payment.yr.YRDaifuQueryResult;
import lottery.domains.content.payment.af.AFDaifuQueryResult;
import lottery.domains.content.payment.tgf.utils.QueryResponseEntity;
import lottery.domains.content.entity.PaymentChannel;
import org.apache.commons.lang.StringUtils;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.UserWithdraw;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.payment.tgf.TGFPayment;
import lottery.domains.content.payment.af.AFPayment;
import lottery.domains.content.payment.yr.YRPayment;
import lottery.domains.content.payment.htf.HTFPayment;
import lottery.domains.content.payment.fkt.FKTPayment;
import lottery.domains.content.payment.cf.CFPayment;
import lottery.domains.content.payment.RX.RXPayment;
import lottery.domains.content.payment.zs.ZSPayment;
import lottery.domains.content.payment.ht.HTPayment;
import lottery.domains.content.biz.UserWithdrawLogService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserWithdrawService;
import javautils.date.Moment;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class APIPayOrderSyncJob
{
    private static final Logger log;
    private static volatile boolean isRunning;
    private static final int SYNC_STATUS_TIMEOUT_MINUTES = 10;
    private static final int NULL_RESULT_TIMEOUT_MINUTES = 10;
    private static Map<String, Moment> FIRST_TIME_NULL_RESULT;
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
    
    static {
        log = LoggerFactory.getLogger((Class)APIPayOrderSyncJob.class);
        APIPayOrderSyncJob.isRunning = false;
        APIPayOrderSyncJob.FIRST_TIME_NULL_RESULT = new HashMap<String, Moment>();
    }
    
    @Scheduled(cron = "0,20,40 * * * * ?")
    public void scheduler() {
        synchronized (APIPayOrderSyncJob.class) {
            if (APIPayOrderSyncJob.isRunning) {
                // monitorexit(APIPayOrderSyncJob.class)
                return;
            }
            APIPayOrderSyncJob.isRunning = true;
        }
        // monitorexit(APIPayOrderSyncJob.class)
        try {
            this.startSync();
        }
        catch (Exception e) {
            APIPayOrderSyncJob.log.error("同步API代付单状态出错", (Throwable)e);
            return;
        }
        finally {
            APIPayOrderSyncJob.isRunning = false;
        }
        APIPayOrderSyncJob.isRunning = false;
    }
    
    private void startSync() {
        final List<UserWithdraw> withdrawOrders = this.getWithdrawOrders();
        if (CollectionUtils.isEmpty((Collection)withdrawOrders)) {
            return;
        }
        for (final UserWithdraw withdrawOrder : withdrawOrders) {
            this.syncOrder(withdrawOrder);
        }
    }
    
    private void syncOrder(final UserWithdraw withdrawOrder) {
        if (withdrawOrder.getPaymentChannelId() == null || withdrawOrder.getPaymentChannelId() <= 0) {
            APIPayOrderSyncJob.log.warn("API代付注单{}为未知第三方代付{}或不是第三方代付，本次不查询", (Object)withdrawOrder.getBillno(), (Object)withdrawOrder.getPaymentChannelId());
            return;
        }
        if (withdrawOrder.getRemitStatus() == -3) {
            this.processSyncStatus(withdrawOrder);
        }
        else {
            this.processRemitStatus(withdrawOrder);
        }
    }
    
    private void processSyncStatus(final UserWithdraw withdrawOrder) {
        final boolean timeout = this.isTimeoutForSyncStatus(withdrawOrder);
        if (timeout) {
            this.updateRemitStatus(withdrawOrder, -4);
            APIPayOrderSyncJob.log.info("API代付单{}已超时，将注单修改为未知状态，且不再处理", (Object)withdrawOrder.getBillno());
            return;
        }
        this.processRemitStatus(withdrawOrder);
    }
    
    private void processRemitStatus(final UserWithdraw withdrawOrder) {
        final PaymentChannel channel = this.dataFactory.getPaymentChannelFullProperty(withdrawOrder.getPaymentChannelId());
        if (channel == null) {
            APIPayOrderSyncJob.log.warn("API代付单{}为未知第三方支付{}，本次不查询", (Object)withdrawOrder.getBillno(), (Object)withdrawOrder.getPaymentChannelId());
            return;
        }
        final Object[] thirdStatus = this.getThirdStatus(channel, withdrawOrder);
        if (thirdStatus == null) {
            if (APIPayOrderSyncJob.FIRST_TIME_NULL_RESULT.containsKey(withdrawOrder.getBillno())) {
                final Moment firstTimeNullResult = APIPayOrderSyncJob.FIRST_TIME_NULL_RESULT.get(withdrawOrder.getBillno());
                final Moment now = new Moment();
                final int minutes = now.difference(firstTimeNullResult, "minute");
                if (minutes >= 10) {
                    APIPayOrderSyncJob.FIRST_TIME_NULL_RESULT.remove(withdrawOrder.getBillno());
                    this.updateRemitStatus(withdrawOrder, -4);
                    APIPayOrderSyncJob.log.info("API代付单{}，第三方{}超过{}分钟未返回数据，修改为未知状态", new Object[] { withdrawOrder.getBillno(), channel.getName(), 10 });
                }
            }
            else {
                APIPayOrderSyncJob.FIRST_TIME_NULL_RESULT.put(withdrawOrder.getBillno(), new Moment());
                APIPayOrderSyncJob.log.info("API代付单{}，第三方{}返回空数据，本次不修改", (Object)withdrawOrder.getBillno(), (Object)channel.getName());
            }
            return;
        }
        APIPayOrderSyncJob.FIRST_TIME_NULL_RESULT.remove(withdrawOrder.getBillno());
        final String payBillno = (thirdStatus[0] == null) ? null : thirdStatus[0].toString();
        final int remitStatus = Integer.valueOf(thirdStatus[1].toString());
        if (StringUtils.isEmpty(payBillno)) {
            this.updateRemitStatus(withdrawOrder, -4);
            APIPayOrderSyncJob.log.info("API代付单{}，第三方{}返回注单号为空，修改为未知状态", (Object)withdrawOrder.getBillno(), (Object)channel.getName());
            return;
        }
        if (remitStatus == withdrawOrder.getRemitStatus()) {
            APIPayOrderSyncJob.log.info("API代付单{}，第三方{}返回状态与数据库一致，本次不修改", (Object)withdrawOrder.getBillno(), (Object)channel.getName());
            return;
        }
        if (StringUtils.isEmpty(withdrawOrder.getPayBillno()) && StringUtils.isNotEmpty(payBillno)) {
            withdrawOrder.setPayBillno(payBillno);
        }
        if (remitStatus == 2) {
            this.updateAsBankProcessed(withdrawOrder, payBillno);
            APIPayOrderSyncJob.log.info("API代付单{}，第三方{}返回状态表示银行已打款完成，本注单最终处理成功", (Object)withdrawOrder.getBillno(), (Object)channel.getName());
        }
        else {
            this.updateRemitStatus(withdrawOrder, remitStatus);
        }
    }
    
    private Object[] getThirdStatus(final PaymentChannel channel, final UserWithdraw order) {
        final String channelCode;
        switch ((channelCode = channel.getChannelCode()).hashCode()) {
            case -1596233098: {
                if (!channelCode.equals("htAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                break;
            }
            case -1470714580: {
                if (!channelCode.equals("rxWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getRXStatus(channel, order);
            }
            case -1260499827: {
                if (!channelCode.equals("cfAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getCFStatus(channel, order);
            }
            case -1177921821: {
                if (!channelCode.equals("zsAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getZSStatus(channel, order);
            }
            case -1107887928: {
                if (!channelCode.equals("afQuick")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getAFStatus(channel, order);
            }
            case -973996174: {
                if (!channelCode.equals("htWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                break;
            }
            case -694198332: {
                if (!channelCode.equals("htfAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getHTFStatus(channel, order);
            }
            case -638262903: {
                if (!channelCode.equals("cfWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getCFStatus(channel, order);
            }
            case -569487244: {
                if (!channelCode.equals("htfJDPay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getHTFStatus(channel, order);
            }
            case -555684897: {
                if (!channelCode.equals("zsWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getZSStatus(channel, order);
            }
            case -451153201: {
                if (!channelCode.equals("afAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getAFStatus(channel, order);
            }
            case -339530887: {
                if (!channelCode.equals("fktAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getFKTStatus(channel, order);
            }
            case -71961408: {
                if (!channelCode.equals("htfWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getHTFStatus(channel, order);
            }
            case 3109: {
                if (!channelCode.equals("af")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getAFStatus(channel, order);
            }
            case 3171: {
                if (!channelCode.equals("cf")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getCFStatus(channel, order);
            }
            case 3340: {
                if (!channelCode.equals("ht")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                break;
            }
            case 3654: {
                if (!channelCode.equals("rx")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getRXStatus(channel, order);
            }
            case 3865: {
                if (!channelCode.equals("yr")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getYRStatus(channel, order);
            }
            case 3897: {
                if (!channelCode.equals("zs")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getZSStatus(channel, order);
            }
            case 101455: {
                if (!channelCode.equals("fkt")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getFKTStatus(channel, order);
            }
            case 103642: {
                if (!channelCode.equals("htf")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getHTFStatus(channel, order);
            }
            case 114771: {
                if (!channelCode.equals("tgf")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getTGFStatus(channel, order);
            }
            case 2990341: {
                if (!channelCode.equals("afQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getAFStatus(channel, order);
            }
            case 3049923: {
                if (!channelCode.equals("cfQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getCFStatus(channel, order);
            }
            case 3212332: {
                if (!channelCode.equals("htQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                break;
            }
            case 3514086: {
                if (!channelCode.equals("rxQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getRXStatus(channel, order);
            }
            case 3716857: {
                if (!channelCode.equals("yrQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getYRStatus(channel, order);
            }
            case 3747609: {
                if (!channelCode.equals("zsQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getZSStatus(channel, order);
            }
            case 97500847: {
                if (!channelCode.equals("fktQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getFKTStatus(channel, order);
            }
            case 99602554: {
                if (!channelCode.equals("htfQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getHTFStatus(channel, order);
            }
            case 110297523: {
                if (!channelCode.equals("tgfQQ")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getTGFStatus(channel, order);
            }
            case 171083723: {
                if (!channelCode.equals("afWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getAFStatus(channel, order);
            }
            case 216754331: {
                if (!channelCode.equals("tgfJDPay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getTGFStatus(channel, order);
            }
            case 224702810: {
                if (!channelCode.equals("tgfQuick")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getTGFStatus(channel, order);
            }
            case 282706037: {
                if (!channelCode.equals("fktWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getFKTStatus(channel, order);
            }
            case 486731459: {
                if (!channelCode.equals("yrAlipay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getYRStatus(channel, order);
            }
            case 659170955: {
                if (!channelCode.equals("cfJDPay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getCFStatus(channel, order);
            }
            case 1108968383: {
                if (!channelCode.equals("yrWeChat")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getYRStatus(channel, order);
            }
            case 1202530178: {
                if (!channelCode.equals("htJDPay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                break;
            }
            case 1243068959: {
                if (!channelCode.equals("fktJDPay")) {
                    throw new RuntimeException("不支持的第三方代付查询：" + channel.getName());
                }
                return this.getFKTStatus(channel, order);
            }
        }
        return this.getHTStatus(channel, order);
    }
    
    private Object[] getTGFStatus(final PaymentChannel channel, final UserWithdraw order) {
        final QueryResponseEntity result = this.tgfPayment.query(order, channel);
        if (result != null && StringUtils.isNotEmpty(result.getStatus())) {
            final int remitStatus = this.tgfPayment.transferBankStatus(result.getStatus());
            return new Object[] { order.getBillno(), remitStatus };
        }
        return null;
    }
    
    private Object[] getAFStatus(final PaymentChannel channel, final UserWithdraw order) {
        final AFDaifuQueryResult result = this.afPayment.query(order, channel);
        if (result != null && StringUtils.isNotEmpty(result.getResult())) {
            final int remitStatus = this.afPayment.transferBankStatus(result.getResult());
            return new Object[] { result.getOrder_no(), remitStatus };
        }
        return null;
    }
    
    private Object[] getYRStatus(final PaymentChannel channel, final UserWithdraw order) {
        final YRDaifuQueryResult result = this.yrPayment.query(order, channel);
        if (result != null && StringUtils.isNotEmpty(result.getRemitStatus())) {
            final int remitStatus = this.yrPayment.transferBankStatus(result.getRemitStatus());
            return new Object[] { result.getOutTradeNo(), remitStatus };
        }
        return null;
    }
    
    private Object[] getHTStatus(final PaymentChannel channel, final UserWithdraw order) {
        final HTPayResult result = this.htPayment.query(order, channel);
        if (result != null && StringUtils.isNotEmpty(result.getBankStatus())) {
            final int remitStatus = this.htPayment.transferBankStatus(result.getBankStatus());
            return new Object[] { result.getOrderId(), remitStatus };
        }
        return null;
    }
    
    private Object[] getZSStatus(final PaymentChannel channel, final UserWithdraw order) {
        final ZSDaifuQueryResult result = this.zsPayment.query(order, channel);
        if (result != null && StringUtils.isNotEmpty(result.getState())) {
            final int remitStatus = this.zsPayment.transferBankStatus(result.getState());
            return new Object[] { result.getOutOrderId(), remitStatus };
        }
        return null;
    }
    
    private Object[] getRXStatus(final PaymentChannel channel, final UserWithdraw order) {
        final RXDaifuQueryResult result = this.rxPayment.query(order, channel);
        if (result != null && StringUtils.isNotEmpty(result.getOrderId_state())) {
            final int remitStatus = this.rxPayment.transferBankStatus(result.getOrderId_state());
            return new Object[] { result.getOrderId(), remitStatus };
        }
        return null;
    }
    
    private Object[] getCFStatus(final PaymentChannel channel, final UserWithdraw order) {
        final CFPayQueryResult result = this.cfPayment.query(order, channel);
        if (result != null && this.cfPayment.isAccepted(result)) {
            final int remitStatus = this.cfPayment.transferBankStatus(result.getBatchContent());
            return new Object[] { result.getBatchNo(), remitStatus };
        }
        return null;
    }
    
    private Object[] getFKTStatus(final PaymentChannel channel, final UserWithdraw order) {
        final FKTPayResult result = this.fktPayment.query(order, channel);
        if (result != null && this.fktPayment.isAcceptedRequest(result.getIsSuccess())) {
            final int remitStatus = this.fktPayment.transferBankStatus(result.getBankStatus());
            return new Object[] { result.getOrderId(), remitStatus };
        }
        return null;
    }
    
    private Object[] getHTFStatus(final PaymentChannel channel, final UserWithdraw order) {
        final HTFPayQueryResult result = this.htfPayment.query(order, channel);
        if (result != null && this.htfPayment.isAcceptedRequest(result.getRetCode()) && StringUtils.isNotEmpty(result.getHyBillNo())) {
            final int remitStatus = this.htfPayment.transferBankStatus(result.getDetailData());
            return new Object[] { result.getHyBillNo(), remitStatus };
        }
        return null;
    }
    
    private void updateRemitStatus(final UserWithdraw withdraw, final int remitStatus) {
        final UserWithdrawVO newestData = this.uWithdrawService.getById(withdraw.getId());
        if (newestData == null || newestData.getBean() == null) {
            return;
        }
        if (Arrays.binarySearch(UserWithdrawServiceImpl.PROCESSING_STATUSES, newestData.getBean().getRemitStatus()) <= -1) {
            APIPayOrderSyncJob.log.warn("API代付注单{}不是可操作状态，无法将打款状态修改为{}, 本次不处理", (Object)withdraw.getBillno(), (Object)remitStatus);
            return;
        }
        withdraw.setRemitStatus(remitStatus);
        this.uWithdrawService.update(withdraw);
        String content = Status.getTypeByContent(remitStatus);
        if (StringUtils.isBlank(content)) {
            content = "未知";
        }
        final String time = new Moment().toSimpleTime();
        final String action = String.format("%s；操作人：系统", content);
        this.userWithdrawLogService.add(new UserWithdrawLog(withdraw.getBillno(), withdraw.getUserId(), -1, action, time));
    }
    
    private void updateAsBankProcessed(final UserWithdraw withdraw, final String payBillno) {
        final UserWithdrawVO newestData = this.uWithdrawService.getById(withdraw.getId());
        if (newestData == null || newestData.getBean() == null) {
            return;
        }
        if (Arrays.binarySearch(UserWithdrawServiceImpl.PROCESSING_STATUSES, newestData.getBean().getRemitStatus()) <= -1) {
            APIPayOrderSyncJob.log.warn("API代付注单{}不是处理中状态，无法修改为打款完成", (Object)withdraw.getBillno());
            return;
        }
        final String infos = "您的提现已处理，请您注意查收！";
        withdraw.setStatus(1);
        withdraw.setInfos(infos);
        withdraw.setPayBillno(payBillno);
        withdraw.setLockStatus(0);
        withdraw.setRemitStatus(2);
        final boolean result = this.uWithdrawService.update(withdraw);
        if (result) {
            this.uBillService.addWithdrawReport(withdraw);
            this.uSysMessageService.addConfirmWithdraw(withdraw.getUserId(), withdraw.getMoney(), withdraw.getRecMoney());
            final String content = Status.getTypeByContent(2);
            final String time = new Moment().toSimpleTime();
            final String action = String.format("%s；操作人：系统", content);
            this.userWithdrawLogService.add(new UserWithdrawLog(withdraw.getBillno(), withdraw.getUserId(), -1, action, time));
        }
    }
    
    private boolean isTimeoutForSyncStatus(final UserWithdraw withdraw) {
        final Moment now = new Moment();
        final Moment operateTime = new Moment().fromTime(withdraw.getOperatorTime());
        final int minutes = now.difference(operateTime, "minute");
        return minutes >= 10;
    }
    
    private List<UserWithdraw> getWithdrawOrders() {
        final String sTime = new Moment().subtract(1, "days").toSimpleDate();
        final String eTime = new Moment().add(1, "days").toSimpleDate();
        return this.uWithdrawService.listByRemitStatus(UserWithdrawServiceImpl.PROCESSING_STATUSES, true, sTime, eTime);
    }
}
