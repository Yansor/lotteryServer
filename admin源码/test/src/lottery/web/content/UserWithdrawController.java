package lottery.web.content;

import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.vo.user.UserBetsVO;
import lottery.domains.content.vo.user.UserRechargeVO;
import lottery.domains.content.vo.user.UserCardVO;
import lottery.domains.content.vo.bill.UserBillVO;
import java.util.List;
import lottery.domains.content.vo.user.UserProfileVO;
import lottery.domains.content.vo.user.HistoryUserWithdrawVO;
import lottery.domains.content.vo.user.UserWithdrawVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.biz.UserRechargeService;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.biz.UserService;
import admin.domains.jobs.AdminUserLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserWithdrawController extends AbstractActionController
{
    private static ConcurrentHashMap<Integer, Boolean> API_PAY_ORDER_CACHE;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserService uService;
    @Autowired
    private UserBetsService uBetsService;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserCardService uCardService;
    @Autowired
    private UserRechargeService uRechargeService;
    @Autowired
    private UserWithdrawService uWithdrawService;
    @Autowired
    private UserWithdrawLimitService userWithdrawLimitService;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    static {
        UserWithdrawController.API_PAY_ORDER_CACHE = new ConcurrentHashMap<Integer, Boolean>();
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String billno = request.getParameter("billno");
                final String username = request.getParameter("username");
                final String minTime = request.getParameter("minTime");
                final String maxTime = request.getParameter("maxTime");
                final String minOperatorTime = request.getParameter("minOperatorTime");
                final String maxOperatorTime = request.getParameter("maxOperatorTime");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final String keyword = request.getParameter("keyword");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer checkStatus = HttpUtil.getIntParameter(request, "checkStatus");
                final Integer remitStatus = HttpUtil.getIntParameter(request, "remitStatus");
                final Integer paymentChannelId = HttpUtil.getIntParameter(request, "paymentChannelId");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uWithdrawService.search(type, billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId, start, limit);
                if (pList != null) {
                    final double[] totalWithdraw = this.uWithdrawService.getTotalWithdraw(billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
                    json.accumulate("totalRecMoney", totalWithdraw[0]);
                    json.accumulate("totalFeeMoney", totalWithdraw[1]);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalRecMoney", 0);
                    json.accumulate("totalFeeMoney", 0);
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/history-lottery-user-withdraw/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_WITHDRAW_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-withdraw/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String billno = request.getParameter("billno");
                final String username = request.getParameter("username");
                final String minTime = request.getParameter("minTime");
                final String maxTime = request.getParameter("maxTime");
                final String minOperatorTime = request.getParameter("minOperatorTime");
                final String maxOperatorTime = request.getParameter("maxOperatorTime");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final String keyword = request.getParameter("keyword");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer checkStatus = HttpUtil.getIntParameter(request, "checkStatus");
                final Integer remitStatus = HttpUtil.getIntParameter(request, "remitStatus");
                final Integer paymentChannelId = HttpUtil.getIntParameter(request, "paymentChannelId");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uWithdrawService.searchHistory(billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId, start, limit);
                if (pList != null) {
                    final double[] totalWithdraw = this.uWithdrawService.getHistoryTotalWithdraw(billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
                    json.accumulate("totalRecMoney", totalWithdraw[0]);
                    json.accumulate("totalFeeMoney", totalWithdraw[1]);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalRecMoney", 0);
                    json.accumulate("totalFeeMoney", 0);
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserWithdrawVO result = this.uWithdrawService.getById(id);
                json.accumulate("data", result);
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/history-lottery-user-withdraw/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_WITHDRAW_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-withdraw/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final HistoryUserWithdrawVO result = this.uWithdrawService.getHistoryById(id);
                json.accumulate("data", result);
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/pay-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_PAY_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/pay-get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserWithdrawVO result = this.uWithdrawService.getById(id);
                json.accumulate("data", result);
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/history-lottery-user-withdraw/pay-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_WITHDRAW_PAY_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-withdraw/pay-get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final HistoryUserWithdrawVO result = this.uWithdrawService.getHistoryById(id);
                json.accumulate("data", result);
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/check" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_CHECK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/check";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserWithdrawVO wBean = this.uWithdrawService.getById(id);
                if (wBean != null) {
                    final int userId = wBean.getBean().getUserId();
                    final UserProfileVO uBean = this.uService.getUserProfile(wBean.getUsername());
                    final List<UserBillVO> uBillList = this.uBillService.getLatest(userId, 5, 20);
                    final List<UserCardVO> uCardList = this.uCardService.getByUserId(userId);
                    final List<UserRechargeVO> uRechargeList = this.uRechargeService.getLatest(userId, 1, 10);
                    final List<UserWithdrawVO> uWithdrawList = this.uWithdrawService.getLatest(userId, 1, 10);
                    final List<UserBetsVO> uOrderList = this.uBetsService.getSuspiciousOrder(userId, 10);
                    json.accumulate("wBean", wBean);
                    json.accumulate("uBean", uBean);
                    json.accumulate("uBillList", uBillList);
                    json.accumulate("uCardList", uCardList);
                    json.accumulate("uRechargeList", uRechargeList);
                    json.accumulate("uWithdrawList", uWithdrawList);
                    json.accumulate("uOrderList", uOrderList);
                    json.set(0, "0-3");
                }
                else {
                    json.set(1, "1-3");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/history-lottery-user-withdraw/check" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_WITHDRAW_CHECK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-withdraw/check";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final HistoryUserWithdrawVO wBean = this.uWithdrawService.getHistoryById(id);
                if (wBean != null) {
                    final int userId = wBean.getBean().getUserId();
                    final UserProfileVO uBean = this.uService.getUserProfile(wBean.getUsername());
                    final List<UserBillVO> uBillList = this.uBillService.getLatest(userId, 5, 20);
                    final List<UserCardVO> uCardList = this.uCardService.getByUserId(userId);
                    final List<UserRechargeVO> uRechargeList = this.uRechargeService.getLatest(userId, 1, 10);
                    final List<UserWithdrawVO> uWithdrawList = this.uWithdrawService.getLatest(userId, 1, 10);
                    final List<UserBetsVO> uOrderList = this.uBetsService.getSuspiciousOrder(userId, 10);
                    json.accumulate("wBean", wBean);
                    json.accumulate("uBean", uBean);
                    json.accumulate("uBillList", uBillList);
                    json.accumulate("uCardList", uCardList);
                    json.accumulate("uRechargeList", uRechargeList);
                    json.accumulate("uWithdrawList", uWithdrawList);
                    json.accumulate("uOrderList", uOrderList);
                    json.set(0, "0-3");
                }
                else {
                    json.set(1, "1-3");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/check-result" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_CHECK_RESULT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/check-result";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                if (status == 1) {
                    final boolean result = this.uWithdrawService.check(uEntity, json, id, status);
                    if (result) {
                        this.adminUserLogJob.logCheckWithdraw(uEntity, request, id, status);
                        json.set(0, "0-5");
                    }
                }
                else {
                    final String remarks = request.getParameter("remarks");
                    final boolean result2 = this.uWithdrawService.reviewedFail(uEntity, json, id, remarks, uEntity.getUsername());
                    if (result2) {
                        this.adminUserLogJob.reviewedFail(uEntity, request, id, remarks);
                        json.set(0, "0-5");
                    }
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/manual-pay" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_MANUAL_PAY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/manual-pay";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String payBillno = request.getParameter("payBillno");
                final String remarks = request.getParameter("remarks");
                final boolean result = this.uWithdrawService.manualPay(uEntity, json, id, payBillno, remarks, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logManualPay(uEntity, request, id, payBillno, remarks);
                    json.set(0, "0-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/refuse" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_REFUSE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/refuse";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String reason = request.getParameter("reason");
                final String remarks = request.getParameter("remarks");
                final boolean result = this.uWithdrawService.refuse(uEntity, json, id, reason, remarks, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logRefuseWithdraw(uEntity, request, id, reason, remarks);
                    json.set(0, "0-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/withdraw-failure" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_WITHDRAW_FAILURE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/withdraw-failure";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String remarks = request.getParameter("remarks");
                final boolean result = this.uWithdrawService.withdrawFailure(uEntity, json, id, remarks, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logWithdrawFailure(uEntity, request, id, remarks);
                    json.set(0, "0-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/complete-remit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_COMPLETE_REMIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/complete-remit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.uWithdrawService.completeRemit(uEntity, json, id, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logCompleteRemitWithdraw(uEntity, request, id);
                    json.set(0, "0-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/lock" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_LOCK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/lock";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String operatorUser = uEntity.getUsername();
                final boolean result = this.uWithdrawService.lock(uEntity, json, id, operatorUser);
                if (result) {
                    this.adminUserLogJob.logLockWithdraw(uEntity, request, id);
                    json.set(0, "0-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/unlock" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_UNLOCK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/unlock";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String operatorUser = uEntity.getUsername();
                final boolean result = this.uWithdrawService.unlock(uEntity, json, id, operatorUser);
                if (result) {
                    this.adminUserLogJob.logUnLockWithdraw(uEntity, request, id);
                    json.set(0, "0-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw/api-pay" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_API_PAY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-withdraw/api-pay";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String channelCode = HttpUtil.getStringParameterTrim(request, "channelCode");
                final Integer channelId = HttpUtil.getIntParameter(request, "channelId");
                if (UserWithdrawController.API_PAY_ORDER_CACHE.containsKey(id)) {
                    json.set(2, "2-4017");
                }
                else {
                    final PaymentChannel channel = this.dataFactory.getPaymentChannelFullProperty(channelCode, channelId);
                    if (channel == null) {
                        json.setWithParams(2, "2-4015", channelCode);
                    }
                    else {
                        UserWithdrawController.API_PAY_ORDER_CACHE.put(id, true);
                        final boolean result = this.uWithdrawService.apiPay(uEntity, json, id, channel);
                        if (result) {
                            this.adminUserLogJob.logAPIPay(uEntity, request, id, channel);
                            if (json.getError() == null || json.getError() == 0) {
                                json.set(0, "0-5");
                            }
                        }
                        else {
                            UserWithdrawController.API_PAY_ORDER_CACHE.remove(id);
                        }
                    }
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
