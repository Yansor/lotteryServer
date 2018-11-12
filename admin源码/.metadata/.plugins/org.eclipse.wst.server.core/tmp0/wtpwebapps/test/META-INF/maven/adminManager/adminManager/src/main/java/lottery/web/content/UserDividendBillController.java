package lottery.web.content;

import lottery.domains.content.vo.user.UserVO;
import javautils.encrypt.PasswordUtil;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.vo.user.UserDividendBillVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import java.util.Iterator;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import lottery.domains.content.entity.User;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import admin.domains.jobs.AdminUserLogJob;
import admin.domains.jobs.AdminUserActionLogJob;
import lottery.domains.content.biz.UserDividendBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserDividendBillController extends AbstractActionController
{
    @Autowired
    private UserService userService;
    @Autowired
    private UserDividendBillService uDividendBillService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Double minUserAmount = HttpUtil.getDoubleParameter(request, "minUserAmount");
                final Double maxUserAmount = HttpUtil.getDoubleParameter(request, "maxUserAmount");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer issueType = HttpUtil.getIntParameter(request, "issueType");
                final List<Integer> userIds = new ArrayList<Integer>();
                boolean legalUser = true;
                if (StringUtils.isNotEmpty(username)) {
                    final User user = this.userService.getByUsername(username);
                    if (user == null) {
                        legalUser = false;
                    }
                    else {
                        userIds.add(user.getId());
                        final List<User> userDirectLowers = this.userService.getUserDirectLower(user.getId());
                        for (final User userDirectLower : userDirectLowers) {
                            userIds.add(userDirectLower.getId());
                        }
                    }
                }
                double platformTotalLoss = 0.0;
                double platformTotalUserAmount = 0.0;
                double upperTotalUserAmount = 0.0;
                if (!legalUser) {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                else {
                    final PageList pList = this.uDividendBillService.search(userIds, sTime, eTime, minUserAmount, maxUserAmount, status, issueType, start, limit);
                    final double[] userAmounts = this.uDividendBillService.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount);
                    platformTotalLoss = userAmounts[0];
                    platformTotalUserAmount = userAmounts[1];
                    upperTotalUserAmount = userAmounts[2];
                    if (pList != null) {
                        json.accumulate("totalCount", pList.getCount());
                        json.accumulate("data", pList.getList());
                    }
                    else {
                        json.accumulate("totalCount", 0);
                        json.accumulate("data", "[]");
                    }
                }
                json.accumulate("platformTotalLoss", platformTotalLoss);
                json.accumulate("platformTotalUserAmount", platformTotalUserAmount);
                json.accumulate("upperTotalUserAmount", upperTotalUserAmount);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/platform-loss-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_PLATFORN_LOSS_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/platform-loss-list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Double minUserAmount = HttpUtil.getDoubleParameter(request, "minUserAmount");
                final Double maxUserAmount = HttpUtil.getDoubleParameter(request, "maxUserAmount");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final List<Integer> userIds = new ArrayList<Integer>();
                boolean legalUser = true;
                if (StringUtils.isNotEmpty(username)) {
                    final User user = this.userService.getByUsername(username);
                    if (user == null) {
                        legalUser = false;
                    }
                    else {
                        userIds.add(user.getId());
                        final List<User> userDirectLowers = this.userService.getUserDirectLower(user.getId());
                        for (final User userDirectLower : userDirectLowers) {
                            userIds.add(userDirectLower.getId());
                        }
                    }
                }
                if (!legalUser) {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                else {
                    final PageList pList = this.uDividendBillService.searchPlatformLoss(userIds, sTime, eTime, minUserAmount, maxUserAmount, start, limit);
                    if (pList != null) {
                        json.accumulate("totalCount", pList.getCount());
                        json.accumulate("data", pList.getList());
                    }
                    else {
                        json.accumulate("totalCount", 0);
                        json.accumulate("data", "[]");
                    }
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
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/get";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
                if (userDividendBill == null) {
                    json.set(2, "2-3001");
                }
                else {
                    json.accumulate("data", new UserDividendBillVO(userDividendBill, this.dataFactory));
                    json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/agree" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_AGREE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/agree";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String remarks = request.getParameter("remarks");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
                            if (userDividendBill == null || userDividendBill.getStatus() != 2) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uDividendBillService.agree(json, id, remarks);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logAgreeDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, remarks);
                                    json.set(0, "0-5");
                                }
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
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/deny" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_DENY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/deny";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String remarks = request.getParameter("remarks");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
                            if (userDividendBill == null || userDividendBill.getStatus() != 2) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uDividendBillService.deny(json, id, remarks);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logDenyDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, remarks);
                                    json.set(0, "0-5");
                                }
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
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/del";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String remarks = request.getParameter("remarks");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
                            if (userDividendBill == null) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uDividendBillService.del(json, id);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logDelDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, remarks);
                                    json.set(0, "0-5");
                                }
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
    
    @RequestMapping(value = { "/lottery-user-dividend-bill/reset" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_BILL_RESET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend-bill/reset";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String remarks = request.getParameter("remarks");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
                            if (userDividendBill == null) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uDividendBillService.reset(json, id, remarks);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logResetDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, remarks);
                                    json.set(0, "0-5");
                                }
                                else if (json.getError() == null) {
                                    json.set(1, "1-5");
                                }
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
}
