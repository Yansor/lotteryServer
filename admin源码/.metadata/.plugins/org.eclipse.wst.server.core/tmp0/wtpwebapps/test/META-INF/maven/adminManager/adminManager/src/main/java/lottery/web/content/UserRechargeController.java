package lottery.web.content;

import lottery.domains.content.vo.user.HistoryUserRechargeVO;
import lottery.domains.content.vo.user.UserRechargeVO;
import javautils.encrypt.PasswordUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import javautils.StringUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import admin.domains.jobs.AdminUserCriticalLogJob;
import lottery.domains.content.biz.PaymentChannelService;
import admin.domains.jobs.MailJob;
import lottery.domains.content.biz.UserRechargeService;
import admin.domains.jobs.AdminUserLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserRechargeController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserRechargeService uRechargeService;
    @Autowired
    private MailJob mailJob;
    @Autowired
    private PaymentChannelService paymentChannelService;
    @Autowired
    private AdminUserCriticalLogJob adminUserCriticalLogJob;
    
    @RequestMapping(value = { "/lottery-user-recharge/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RECHARGE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-recharge/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String billno = request.getParameter("billno");
                final String username = request.getParameter("username");
                String minTime = request.getParameter("minTime");
                if (StringUtil.isNotNull(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                String maxTime = request.getParameter("maxTime");
                if (StringUtil.isNotNull(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 00:00:00";
                }
                String minPayTime = request.getParameter("minPayTime");
                if (StringUtil.isNotNull(minPayTime)) {
                    minPayTime = String.valueOf(minPayTime) + " 00:00:00";
                }
                String maxPayTime = request.getParameter("maxPayTime");
                if (StringUtil.isNotNull(maxPayTime)) {
                    maxPayTime = String.valueOf(maxPayTime) + " 00:00:00";
                }
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer channelId = HttpUtil.getIntParameter(request, "channelId");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final PageList pList = this.uRechargeService.search(type, billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId, start, limit);
                if (pList != null) {
                    final double totalRecharge = this.uRechargeService.getTotalRecharge(type, billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
                    json.accumulate("totalRecharge", totalRecharge);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalRecharge", 0);
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
    
    @RequestMapping(value = { "/history-lottery-user-recharge/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_RECHARGE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-recharge/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String billno = request.getParameter("billno");
                final String username = request.getParameter("username");
                String minTime = request.getParameter("minTime");
                if (StringUtil.isNotNull(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                String maxTime = request.getParameter("maxTime");
                if (StringUtil.isNotNull(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 00:00:00";
                }
                String minPayTime = request.getParameter("minPayTime");
                if (StringUtil.isNotNull(minPayTime)) {
                    minPayTime = String.valueOf(minPayTime) + " 00:00:00";
                }
                String maxPayTime = request.getParameter("maxPayTime");
                if (StringUtil.isNotNull(maxPayTime)) {
                    maxPayTime = String.valueOf(maxPayTime) + " 00:00:00";
                }
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer channelId = HttpUtil.getIntParameter(request, "channelId");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uRechargeService.searchHistory(billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId, start, limit);
                if (pList != null) {
                    final double totalRecharge = this.uRechargeService.getHistoryTotalRecharge(billno, username, minTime, maxTime, minPayTime, maxPayTime, minMoney, maxMoney, status, channelId);
                    json.accumulate("totalRecharge", totalRecharge);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalRecharge", 0);
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
    
    @RequestMapping(value = { "/lottery-user-recharge/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RECHARGE_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-recharge/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int type = HttpUtil.getIntParameter(request, "type");
                final int account = HttpUtil.getIntParameter(request, "account");
                final double amount = HttpUtil.getDoubleParameter(request, "amount");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final String remarks = request.getParameter("remarks");
                if (remarks == null || StringUtils.isEmpty(remarks.trim())) {
                    json.set(2, "2-30");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final boolean result = this.uRechargeService.addSystemRecharge(username, type, account, amount, limit, remarks);
                            if (result) {
                                this.adminUserLogJob.logRecharge(uEntity, request, username, type, account, amount, limit, remarks);
                                this.mailJob.sendSystemRecharge(username, uEntity.getUsername(), type, account, amount, remarks);
                                json.set(0, "0-5");
                            }
                            else {
                                json.set(1, "1-5");
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/lottery-user-recharge/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RECHARGE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-recharge/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserRechargeVO result = this.uRechargeService.getById(id);
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
    
    @RequestMapping(value = { "/history-lottery-user-recharge/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_RECHARGE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-recharge/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final HistoryUserRechargeVO result = this.uRechargeService.getHistoryById(id);
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
    
    @RequestMapping(value = { "/lottery-user-recharge/patch" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RECHARGE_PATCH(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-recharge/patch";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String billno = request.getParameter("billno");
                final String payBillno = request.getParameter("paybillno");
                final String remarks = request.getParameter("remarks");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final boolean result = this.uRechargeService.patchOrder(billno, payBillno, remarks);
                            if (result) {
                                this.adminUserLogJob.logPatchRecharge(uEntity, request, billno, payBillno, remarks);
                                json.set(0, "0-3");
                            }
                            else {
                                json.set(1, "1-3");
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/lottery-user-recharge/cancel" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RECHARGE_CANCEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-recharge/cancel";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String billno = request.getParameter("billno");
                final boolean result = this.uRechargeService.cancelOrder(billno);
                if (result) {
                    this.adminUserLogJob.logCancelRecharge(uEntity, request, billno);
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
}
