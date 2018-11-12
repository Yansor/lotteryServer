package lottery.web.content;

import lottery.domains.content.vo.user.UserVO;
import javautils.encrypt.PasswordUtil;
import lottery.domains.content.entity.UserGameDividendBill;
import lottery.domains.content.vo.user.UserGameDividendBillVO;
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
import lottery.domains.content.biz.UserGameDividendBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserGameDividendBillController extends AbstractActionController
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserGameDividendBillService uGameDividendBillService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @RequestMapping(value = { "/user-game-dividend-bill/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_GAME_DIVIDEND_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-game-dividend-bill/list";
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
                final List<Integer> userIds = new ArrayList<Integer>();
                boolean legalUser = true;
                if (StringUtils.isNotEmpty(username)) {
                    final User user = this.userDao.getByUsername(username);
                    if (user == null) {
                        legalUser = false;
                    }
                    else {
                        userIds.add(user.getId());
                        final List<User> userDirectLowers = this.userDao.getUserDirectLower(user.getId());
                        for (final User userDirectLower : userDirectLowers) {
                            userIds.add(userDirectLower.getId());
                        }
                    }
                }
                double totalUserAmount = 0.0;
                if (!legalUser) {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                else {
                    final PageList pList = this.uGameDividendBillService.search(userIds, sTime, eTime, minUserAmount, maxUserAmount, status, start, limit);
                    if (pList != null) {
                        totalUserAmount = this.uGameDividendBillService.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount, status);
                        json.accumulate("totalCount", pList.getCount());
                        json.accumulate("data", pList.getList());
                    }
                    else {
                        json.accumulate("totalCount", 0);
                        json.accumulate("data", "[]");
                    }
                }
                json.accumulate("totalUserAmount", totalUserAmount);
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
    
    @RequestMapping(value = { "/user-game-dividend-bill/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_GAME_DIVIDEND_BILL_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-game-dividend-bill/get";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
                if (userDividendBill == null) {
                    json.set(2, "2-3001");
                }
                else {
                    json.accumulate("data", new UserGameDividendBillVO(userDividendBill, this.dataFactory));
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
    
    @RequestMapping(value = { "/user-game-dividend-bill/agree" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_GAME_DIVIDEND_BILL_AGREE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-game-dividend-bill/agree";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final double userAmount = HttpUtil.getDoubleParameter(request, "userAmount");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String remarks = request.getParameter("remarks");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
                            if (userDividendBill == null || userDividendBill.getStatus() != 2) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uGameDividendBillService.agree(id, userAmount, remarks);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logAgreeGameDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, userAmount, remarks);
                                    json.set(0, "0-5");
                                }
                                else {
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
    
    @RequestMapping(value = { "/user-game-dividend-bill/deny" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_GAME_DIVIDEND_BILL_DENY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-game-dividend-bill/deny";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final double userAmount = HttpUtil.getDoubleParameter(request, "userAmount");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String remarks = request.getParameter("remarks");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
                            if (userDividendBill == null || userDividendBill.getStatus() != 2) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uGameDividendBillService.deny(id, userAmount, remarks);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logDenyGameDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, userAmount, remarks);
                                    json.set(0, "0-5");
                                }
                                else {
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
    
    @RequestMapping(value = { "/user-game-dividend-bill/del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_GAME_DIVIDEND_BILL_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-game-dividend-bill/del";
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
                            final UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
                            if (userDividendBill == null) {
                                json.set(2, "2-3001");
                            }
                            else {
                                final boolean result = this.uGameDividendBillService.del(id);
                                if (result) {
                                    final UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                                    this.adminUserLogJob.logDelGameDividend(uEntity, request, (user == null) ? "" : user.getUsername(), userDividendBill, remarks);
                                    json.set(0, "0-5");
                                }
                                else {
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
