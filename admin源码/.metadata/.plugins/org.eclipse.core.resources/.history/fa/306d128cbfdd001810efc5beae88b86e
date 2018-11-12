package lottery.domains.content.biz.impl;

import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.UserWithdrawLog;
import javautils.date.Moment;
import admin.web.WebJSONObject;
import admin.domains.content.entity.AdminUser;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import lottery.domains.content.entity.User;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import javautils.jdbc.PageList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import lottery.domains.content.entity.HistoryUserWithdraw;
import lottery.domains.content.vo.user.HistoryUserWithdrawVO;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.vo.user.UserWithdrawVO;
import org.slf4j.LoggerFactory;
import lottery.domains.content.dao.UserWithdrawLogDao;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.biz.PaymentChannelBankService;
import lottery.domains.content.biz.PaymentChannelService;
import lottery.domains.content.payment.tgf.TGFPayment;
import lottery.domains.content.payment.af.AFPayment;
import lottery.domains.content.payment.yr.YRPayment;
import lottery.domains.content.payment.htf.HTFPayment;
import lottery.domains.content.payment.fkt.FKTPayment;
import lottery.domains.content.payment.cf.CFPayment;
import lottery.domains.content.payment.ht.HTPayment;
import lottery.domains.content.payment.RX.RXPayment;
import lottery.domains.content.payment.zs.ZSPayment;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.dao.UserWithdrawLimitDao;
import lottery.domains.content.dao.UserWithdrawDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserWithdrawService;

@Service
public class UserWithdrawServiceImpl implements UserWithdrawService
{
    public static final int[] PROCESSING_STATUSES;
    private static final Logger log;
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
    
    static {
        PROCESSING_STATUSES = new int[] { -3, 1, 3 };
        log = LoggerFactory.getLogger((Class)UserWithdrawServiceImpl.class);
    }
    
    @Override
    public UserWithdrawVO getById(final int id) {
        final UserWithdraw bean = this.uWithdrawDao.getById(id);
        if (bean != null) {
            return new UserWithdrawVO(bean, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public HistoryUserWithdrawVO getHistoryById(final int id) {
        final HistoryUserWithdraw bean = this.uWithdrawDao.getHistoryById(id);
        if (bean != null) {
            return new HistoryUserWithdrawVO(bean, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public List<UserWithdrawVO> getLatest(final int userId, final int status, final int count) {
        final List<UserWithdrawVO> formatList = new ArrayList<UserWithdrawVO>();
        final List<UserWithdraw> list = this.uWithdrawDao.getLatest(userId, status, count);
        for (final UserWithdraw tmpBean : list) {
            formatList.add(new UserWithdrawVO(tmpBean, this.lotteryDataFactory));
        }
        return formatList;
    }
    
    @Override
    public List<UserWithdraw> listByRemitStatus(final int[] remitStatuses, final boolean third, final String sTime, final String eTime) {
        return this.uWithdrawDao.listByRemitStatus(remitStatuses, third, sTime, eTime);
    }
    
    @Override
    public PageList search(final Integer type, final String billno, final String username, final String minTime, final String maxTime, final String minOperatorTime, final String maxOperatorTime, final Double minMoney, final Double maxMoney, final String keyword, final Integer status, final Integer checkStatus, final Integer remitStatus, final Integer paymentChannelId, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        final StringBuilder queryStr = new StringBuilder();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                queryStr.append(" and b.user_id  = ").append(user.getId());
            }
            else {
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
            queryStr.append(" and b.money  >= ").append((double)minMoney);
        }
        if (maxMoney != null) {
            queryStr.append(" and b.money  <= ").append((double)maxMoney);
        }
        if (StringUtil.isNotNull(keyword)) {
            queryStr.append("and (b.card_name like %" + keyword + "% or b.card_id like  %" + keyword + "% )");
        }
        if (status != null) {
            queryStr.append(" and b.status  = ").append((int)status);
        }
        if (checkStatus != null) {
            queryStr.append(" and b.check_status  = ").append((int)checkStatus);
        }
        if (remitStatus != null) {
            queryStr.append(" and b.remit_status  = ").append((int)remitStatus);
        }
        if (paymentChannelId != null) {
            queryStr.append(" and b.payment_channel_id  = ").append(paymentChannelId);
        }
        if (type != null) {
            queryStr.append(" and  u.type  = ").append(type);
        }
        else {
            queryStr.append(" and u.upid  != ").append(0);
        }
        queryStr.append("  ORDER BY b.time,b.id DESC ");
        if (isSearch) {
            final List<UserWithdrawVO> list = new ArrayList<UserWithdrawVO>();
            final PageList pList = this.uWithdrawDao.find(queryStr.toString(), start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserWithdrawVO((UserWithdraw)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public PageList searchHistory(final String billno, final String username, final String minTime, final String maxTime, final String minOperatorTime, final String maxOperatorTime, final Double minMoney, final Double maxMoney, final String keyword, final Integer status, final Integer checkStatus, final Integer remitStatus, final Integer paymentChannelId, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(billno)) {
            criterions.add((Criterion)Restrictions.eq("billno", (Object)billno));
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.gt("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (StringUtil.isNotNull(minOperatorTime)) {
            criterions.add((Criterion)Restrictions.gt("operatorTime", (Object)minOperatorTime));
        }
        if (StringUtil.isNotNull(maxOperatorTime)) {
            criterions.add((Criterion)Restrictions.lt("operatorTime", (Object)maxOperatorTime));
        }
        if (minMoney != null) {
            criterions.add((Criterion)Restrictions.ge("money", (Object)minMoney));
        }
        if (maxMoney != null) {
            criterions.add((Criterion)Restrictions.le("money", (Object)maxMoney));
        }
        if (StringUtil.isNotNull(keyword)) {
            criterions.add((Criterion)Restrictions.or((Criterion)Restrictions.like("cardName", keyword, MatchMode.ANYWHERE), (Criterion)Restrictions.like("cardId", keyword, MatchMode.ANYWHERE)));
        }
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        if (checkStatus != null) {
            criterions.add((Criterion)Restrictions.eq("checkStatus", (Object)checkStatus));
        }
        if (remitStatus != null) {
            criterions.add((Criterion)Restrictions.eq("remitStatus", (Object)remitStatus));
        }
        if (paymentChannelId != null) {
            criterions.add((Criterion)Restrictions.eq("paymentChannelId", (Object)paymentChannelId));
        }
        orders.add(Order.desc("time"));
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<HistoryUserWithdrawVO> list = new ArrayList<HistoryUserWithdrawVO>();
            final PageList pList = this.uWithdrawDao.findHistory(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new HistoryUserWithdrawVO((HistoryUserWithdraw)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean manualPay(final AdminUser uEntity, final WebJSONObject json, final int id, final String payBillno, final String remarks, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getLockStatus() == 1) {
                if (operatorUser.equals(entity.getOperatorUser())) {
                    final String operatorTime = new Moment().toSimpleTime();
                    final String infos = "您的提现已提交至银行处理,请耐心等候！";
                    entity.setInfos(infos);
                    entity.setPayBillno(payBillno);
                    entity.setOperatorUser(operatorUser);
                    entity.setOperatorTime(operatorTime);
                    entity.setRemarks("手动出款");
                    entity.setRemitStatus(1);
                    entity.setPayType(2);
                    entity.setPaymentChannelId(-1);
                    final boolean result = this.uWithdrawDao.update(entity);
                    if (result) {
                        final String time = new Moment().toSimpleTime();
                        final String action = String.format("手动出款；操作人：%s", uEntity.getUsername());
                        this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                    }
                    return result;
                }
                json.set(2, "2-2021");
            }
            else {
                json.set(2, "2-2020");
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean completeRemit(final AdminUser uEntity, final WebJSONObject json, final int id, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getLockStatus() == 1) {
                if (operatorUser.equals(entity.getOperatorUser())) {
                    final String operatorTime = new Moment().toSimpleTime();
                    final String infos = "您的提现已处理，请您注意查收！";
                    entity.setStatus(1);
                    entity.setInfos(infos);
                    entity.setOperatorUser(operatorUser);
                    entity.setOperatorTime(operatorTime);
                    entity.setLockStatus(0);
                    entity.setRemitStatus(2);
                    final boolean result = this.uWithdrawDao.update(entity);
                    if (result) {
                        this.uBillService.addWithdrawReport(entity);
                        this.uSysMessageService.addConfirmWithdraw(entity.getUserId(), entity.getMoney(), entity.getRecMoney());
                        final String time = new Moment().toSimpleTime();
                        final String action = String.format("<span style=\"color: #35AA47;\">打款完成</span>；操作人：%s", uEntity.getUsername());
                        this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                    }
                    return result;
                }
                json.set(2, "2-2021");
            }
            else {
                json.set(2, "2-2019");
            }
        }
        return false;
    }
    
    @Override
    public boolean apiPay(final AdminUser uEntity, final WebJSONObject json, final int id, final PaymentChannel channel) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity == null || entity.getStatus() != 0) {
            json.set(2, "2-2019");
            return false;
        }
        if (entity.getLockStatus() != 1) {
            json.set(2, "2-2020");
            return false;
        }
        if (!uEntity.getUsername().equals(entity.getOperatorUser())) {
            json.set(2, "2-2021");
            return false;
        }
        final UserCard card = this.uCardService.getByUserAndCardId(entity.getUserId(), entity.getCardId().trim());
        if (card == null) {
            json.set(2, "2-4011");
            return false;
        }
        final PaymentChannelBank bank = this.paymentChannelBankService.getByChannelAndBankId(channel.getApiPayBankChannelCode(), card.getBankId());
        String payBillno = null;
        final String operatorTime = new Moment().toSimpleTime();
        Label_1214: {
            Label_1193: {
                Label_1172: {
                    Label_1151: {
                        Label_1130: {
                            Label_1109: {
                                Label_1088: {
                                    Label_1067: {
                                        final String channelCode;
                                        switch ((channelCode = channel.getChannelCode()).hashCode()) {
                                            case -1596233098: {
                                                if (!channelCode.equals("htAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break;
                                            }
                                            case -1470714580: {
                                                if (!channelCode.equals("rxWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1088;
                                            }
                                            case -1260499827: {
                                                if (!channelCode.equals("cfAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1109;
                                            }
                                            case -1177921821: {
                                                if (!channelCode.equals("zsAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1067;
                                            }
                                            case -973996174: {
                                                if (!channelCode.equals("htWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break;
                                            }
                                            case -694198332: {
                                                if (!channelCode.equals("htfAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1151;
                                            }
                                            case -638262903: {
                                                if (!channelCode.equals("cfWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1109;
                                            }
                                            case -569487244: {
                                                if (!channelCode.equals("htfJDPay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1151;
                                            }
                                            case -555684897: {
                                                if (!channelCode.equals("zsWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1067;
                                            }
                                            case -451153201: {
                                                if (!channelCode.equals("afAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1193;
                                            }
                                            case -339530887: {
                                                if (!channelCode.equals("fktAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1130;
                                            }
                                            case -71961408: {
                                                if (!channelCode.equals("htfWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1151;
                                            }
                                            case 3109: {
                                                if (!channelCode.equals("af")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1193;
                                            }
                                            case 3171: {
                                                if (!channelCode.equals("cf")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1109;
                                            }
                                            case 3340: {
                                                if (!channelCode.equals("ht")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break;
                                            }
                                            case 3654: {
                                                if (!channelCode.equals("rx")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1088;
                                            }
                                            case 3865: {
                                                if (!channelCode.equals("yr")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1172;
                                            }
                                            case 3897: {
                                                if (!channelCode.equals("zs")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1067;
                                            }
                                            case 101455: {
                                                if (!channelCode.equals("fkt")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1130;
                                            }
                                            case 103642: {
                                                if (!channelCode.equals("htf")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1151;
                                            }
                                            case 114771: {
                                                if (!channelCode.equals("tgf")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1214;
                                            }
                                            case 2990341: {
                                                if (!channelCode.equals("afQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1193;
                                            }
                                            case 3049923: {
                                                if (!channelCode.equals("cfQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1109;
                                            }
                                            case 3212332: {
                                                if (!channelCode.equals("htQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break;
                                            }
                                            case 3514086: {
                                                if (!channelCode.equals("rxQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1088;
                                            }
                                            case 3716857: {
                                                if (!channelCode.equals("yrQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1172;
                                            }
                                            case 3747609: {
                                                if (!channelCode.equals("zsQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1067;
                                            }
                                            case 97500847: {
                                                if (!channelCode.equals("fktQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1130;
                                            }
                                            case 99602554: {
                                                if (!channelCode.equals("htfQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1151;
                                            }
                                            case 110297523: {
                                                if (!channelCode.equals("tgfQQ")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1214;
                                            }
                                            case 171083723: {
                                                if (!channelCode.equals("afWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1193;
                                            }
                                            case 216754331: {
                                                if (!channelCode.equals("tgfJDPay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1214;
                                            }
                                            case 224702810: {
                                                if (!channelCode.equals("tgfQuick")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1214;
                                            }
                                            case 282706037: {
                                                if (!channelCode.equals("fktWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1130;
                                            }
                                            case 486731459: {
                                                if (!channelCode.equals("yrAlipay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1172;
                                            }
                                            case 659170955: {
                                                if (!channelCode.equals("cfJDPay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1109;
                                            }
                                            case 1108968383: {
                                                if (!channelCode.equals("yrWeChat")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1172;
                                            }
                                            case 1202530178: {
                                                if (!channelCode.equals("htJDPay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break;
                                            }
                                            case 1243068959: {
                                                if (!channelCode.equals("fktJDPay")) {
                                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                                }
                                                break Label_1130;
                                            }
                                        }
                                        payBillno = this.htPayment.daifu(json, entity, card, bank, channel);
                                        return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                    }
                                    payBillno = this.zsPayment.daifu(json, entity, card, bank, channel);
                                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                                }
                                payBillno = this.rxPayment.daifu(json, entity, card, bank, channel);
                                return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                            }
                            payBillno = this.cfPayment.daifu(json, entity, card, bank, channel);
                            return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                        }
                        payBillno = this.fktPayment.daifu(json, entity, card, bank, channel);
                        return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                    }
                    payBillno = this.htfPayment.daifu(json, entity, card, bank, channel);
                    return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
                }
                payBillno = this.yrPayment.daifu(json, entity, card, bank, channel);
                return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
            }
            payBillno = this.afPayment.daifu(json, entity, card, bank, channel);
            return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
        }
        payBillno = this.tgfPayment.daifu(json, entity, card, bank, channel);
        return this.apiPayResultProcess(uEntity, entity, operatorTime, channel, payBillno, json);
    }
    
    private boolean apiPayResultProcess(final AdminUser uEntity, final UserWithdraw entity, final String operatorTime, final PaymentChannel channel, final String payBillno, final WebJSONObject json) {
        if (json.getError() != null && json.getError() == -1) {
            final String infos = "您的提现已提交至银行处理,请耐心等候！";
            entity.setInfos(infos);
            entity.setOperatorUser(uEntity.getUsername());
            entity.setOperatorTime(operatorTime);
            entity.setRemarks("使用" + channel.getName() + "代付");
            entity.setRemitStatus(-3);
            entity.setPayType(1);
            entity.setPaymentChannelId(channel.getId());
            final boolean result = this.uWithdrawDao.update(entity);
            if (result) {
                final String time = new Moment().toSimpleTime();
                final String action = String.format("使用" + channel.getName() + "代付，连接异常，系统开始自动同步状态；操作人：%s", uEntity.getUsername());
                this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
            }
            return result;
        }
        if (StringUtils.isNotEmpty(payBillno)) {
            final String infos = "您的提现已处理，请您注意查收！";
            entity.setInfos(infos);
            entity.setPayBillno(payBillno);
            entity.setOperatorUser(uEntity.getUsername());
            entity.setOperatorTime(operatorTime);
            entity.setRemarks("使用" + channel.getName() + "代付");
            entity.setRemitStatus(3);
            entity.setPayType(1);
            entity.setPaymentChannelId(channel.getId());
            final boolean result = this.uWithdrawDao.update(entity);
            if (result) {
                final String time = new Moment().toSimpleTime();
                final String action = String.format("使用" + channel.getName() + "代付，提交成功；操作人：%s", uEntity.getUsername());
                this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
            }
            return result;
        }
        return false;
    }
    
    @Override
    public boolean check(final AdminUser uEntity, final WebJSONObject json, final int id, final int status) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getCheckStatus() == 0) {
                entity.setCheckStatus(status);
                final Boolean boolean1 = this.uWithdrawDao.update(entity);
                if (boolean1) {
                    this.uWithdrawLimitDao.delByUserId(entity.getUserId());
                    final String time = new Moment().toSimpleTime();
                    final String action = String.format("<font color=\"#35AA47\">审核通过</font>；操作人：%s", uEntity.getUsername());
                    this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                }
                return boolean1;
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean refuse(final AdminUser uEntity, final WebJSONObject json, final int id, final String reason, final String remarks, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getLockStatus() == 1) {
                if (operatorUser.equals(entity.getOperatorUser())) {
                    final User uBean = this.uDao.getById(entity.getUserId());
                    if (uBean != null) {
                        final boolean uflag = this.uDao.updateLotteryMoney(entity.getUserId(), entity.getMoney());
                        if (uflag) {
                            final String operatorTime = new Moment().toSimpleTime();
                            String infos = "您的提现已被拒绝，金额已返还！";
                            if (StringUtil.isNotNull(reason)) {
                                infos = String.valueOf(infos) + "原因：" + reason;
                            }
                            if (StringUtil.isNotNull(remarks)) {
                                infos = String.valueOf(infos) + "备注：" + remarks;
                            }
                            entity.setStatus(-1);
                            entity.setInfos(infos);
                            entity.setOperatorUser(operatorUser);
                            entity.setOperatorTime(operatorTime);
                            entity.setRemarks(infos);
                            entity.setLockStatus(0);
                            final boolean flag = this.uWithdrawDao.update(entity);
                            if (flag) {
                                this.uBillService.addDrawBackBill(entity, uBean, entity.getRemarks());
                                this.uSysMessageService.addRefuseWithdraw(entity.getUserId(), entity.getMoney());
                                final String time = new Moment().toSimpleTime();
                                final String action = String.format("<font color=\"#D84A38\">拒绝支付</font>；操作人：%s；%s", uEntity.getUsername(), infos);
                                this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                            }
                            return flag;
                        }
                    }
                }
                else {
                    json.set(2, "2-2021");
                }
            }
            else {
                json.set(2, "2-2020");
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean withdrawFailure(final AdminUser uEntity, final WebJSONObject json, final int id, final String remarks, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getLockStatus() == 1) {
                if (operatorUser.equals(entity.getOperatorUser())) {
                    final User uBean = this.uDao.getById(entity.getUserId());
                    if (uBean != null) {
                        final boolean uflag = this.uDao.updateLotteryMoney(entity.getUserId(), entity.getMoney());
                        if (uflag) {
                            final String operatorTime = new Moment().toSimpleTime();
                            String infos = "您的提现失败，金额已返还！";
                            if (StringUtil.isNotNull(remarks)) {
                                infos = String.valueOf(infos) + "备注：" + remarks;
                            }
                            entity.setStatus(1);
                            entity.setInfos(infos);
                            entity.setOperatorUser(operatorUser);
                            entity.setOperatorTime(operatorTime);
                            entity.setRemarks(infos);
                            entity.setLockStatus(0);
                            entity.setRemitStatus(-2);
                            final boolean flag = this.uWithdrawDao.update(entity);
                            if (flag) {
                                this.uBillService.addDrawBackBill(entity, uBean, entity.getRemarks());
                                this.uSysMessageService.addRefuse(entity.getUserId(), entity.getMoney());
                                final String time = new Moment().toSimpleTime();
                                final String action = String.format("<span style=\"color: #D84A38;\">打款失败</span>；操作人：%s;%s", uEntity.getUsername(), infos);
                                this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                            }
                            return flag;
                        }
                    }
                }
                else {
                    json.set(2, "2-2021");
                }
            }
            else {
                json.set(2, "2-2020");
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean reviewedFail(final AdminUser uEntity, final WebJSONObject json, final int id, final String remarks, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            final User uBean = this.uDao.getById(entity.getUserId());
            if (uBean != null) {
                final boolean uflag = this.uDao.updateLotteryMoney(entity.getUserId(), entity.getMoney());
                if (uflag) {
                    String infos = "您的提现审核失败，金额已返还！";
                    if (StringUtil.isNotNull(remarks)) {
                        infos = String.valueOf(infos) + "备注：" + remarks;
                    }
                    entity.setStatus(1);
                    entity.setInfos(infos);
                    entity.setOperatorUser(operatorUser);
                    entity.setRemarks(infos);
                    entity.setLockStatus(0);
                    entity.setCheckStatus(-1);
                    final boolean flag = this.uWithdrawDao.update(entity);
                    if (flag) {
                        this.uBillService.addDrawBackBill(entity, uBean, entity.getRemarks());
                        this.uSysMessageService.addShFail(entity.getUserId(), entity.getMoney());
                        final String time = new Moment().toSimpleTime();
                        final String action = String.format("<font color=\"#D84A38\">审核拒绝</font>；操作人：%s；%s", uEntity.getUsername(), infos);
                        this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                    }
                    return flag;
                }
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean lock(final AdminUser uEntity, final WebJSONObject json, final int id, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getCheckStatus() != 0) {
                if (entity.getLockStatus() == 0) {
                    final String operatorTime = new Moment().toSimpleTime();
                    final Boolean boolean1 = this.uWithdrawDao.lock(entity.getBillno(), operatorUser, operatorTime);
                    if (boolean1) {
                        final String time = new Moment().toSimpleTime();
                        final String action = String.format("锁定；操作人：%s", uEntity.getUsername());
                        this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                    }
                    return boolean1;
                }
                json.set(2, "2-2021");
            }
            else {
                json.set(2, "2-2023");
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean unlock(final AdminUser uEntity, final WebJSONObject json, final int id, final String operatorUser) {
        final UserWithdraw entity = this.uWithdrawDao.getById(id);
        if (entity != null && entity.getStatus() == 0) {
            if (entity.getLockStatus() == 1) {
                if (operatorUser != null && operatorUser.equals(entity.getOperatorUser())) {
                    final Boolean boolean1 = this.uWithdrawDao.unlock(entity.getBillno(), operatorUser);
                    if (boolean1) {
                        final String time = new Moment().toSimpleTime();
                        final String action = String.format("解锁；操作人：%s", uEntity.getUsername());
                        this.userWithdrawLogDao.add(new UserWithdrawLog(entity.getBillno(), entity.getUserId(), uEntity.getId(), action, time));
                    }
                    return boolean1;
                }
                json.set(2, "2-2021");
            }
            else {
                json.set(2, "2-2020");
            }
        }
        else {
            json.set(2, "2-2019");
        }
        return false;
    }
    
    @Override
    public boolean update(final UserWithdraw withdraw) {
        return this.uWithdrawDao.update(withdraw);
    }
    
    @Override
    public double[] getTotalWithdraw(final String billno, final String username, final String minTime, final String maxTime, final String minOperatorTime, final String maxOperatorTime, final Double minMoney, final Double maxMoney, final String keyword, final Integer status, final Integer checkStatus, final Integer remitStatus, final Integer paymentChannelId) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uWithdrawDao.getTotalWithdraw(billno, userId, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
    }
    
    @Override
    public double[] getHistoryTotalWithdraw(final String billno, final String username, final String minTime, final String maxTime, final String minOperatorTime, final String maxOperatorTime, final Double minMoney, final Double maxMoney, final String keyword, final Integer status, final Integer checkStatus, final Integer remitStatus, final Integer paymentChannelId) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uWithdrawDao.getHistoryTotalWithdraw(billno, userId, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
    }
}
