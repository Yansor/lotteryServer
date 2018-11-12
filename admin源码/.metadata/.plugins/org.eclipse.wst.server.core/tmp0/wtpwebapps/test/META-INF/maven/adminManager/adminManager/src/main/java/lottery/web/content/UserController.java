package lottery.web.content;

import java.util.Map;
import lottery.domains.content.vo.user.UserWithdrawVO;
import lottery.domains.content.vo.user.UserVO;
import java.util.ArrayList;
import lottery.domains.content.vo.user.UserWithdrawLimitVO;
import javautils.date.Moment;
import lottery.domains.content.vo.user.UserBaseVO;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.vo.user.UserSecurityVO;
import lottery.domains.content.vo.user.UserCardVO;
import lottery.domains.content.entity.UserInfo;
import lottery.domains.content.vo.user.UserProfileVO;
import javautils.math.MathUtil;
import lottery.domains.content.entity.User;
import javautils.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.validate.UserValidate;
import admin.domains.jobs.MailJob;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.biz.UserSecurityService;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.biz.UserInfoService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.dao.UserPlanInfoDao;
import lottery.domains.content.dao.UserInfoDao;
import lottery.domains.content.dao.UserDao;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private AdminUserCriticalLogJob adminUserCriticalLogJob;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserInfoDao uInfoDao;
    @Autowired
    private UserPlanInfoDao uPlanInfoDao;
    @Autowired
    private UserWithdrawLimitService userWithdrawLimitService;
    @Autowired
    private UserWithdrawService uWithdrawService;
    @Autowired
    private UserService uService;
    @Autowired
    private UserInfoService uInfoService;
    @Autowired
    private UserCardService uCardService;
    @Autowired
    private UserSecurityService uSecurityService;
    @Autowired
    private UserGameAccountDao uGameAccountDao;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private MailJob mailJob;
    @Autowired
    private UserValidate uValidate;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private UserWithdrawLimitService mUserWithdrawLimitService;
    
    @RequestMapping(value = { "/lottery-user/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String matchType = request.getParameter("matchType");
                final String registTime = request.getParameter("registTime");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minTotalMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxTotalMoney");
                final Double minLotteryMoney = HttpUtil.getDoubleParameter(request, "minLotteryMoney");
                final Double maxLotteryMoney = HttpUtil.getDoubleParameter(request, "maxLotteryMoney");
                final Integer minCode = HttpUtil.getIntParameter(request, "minCode");
                final Integer maxCode = HttpUtil.getIntParameter(request, "maxCode");
                final String sortColoum = request.getParameter("sortColoum");
                final String sortType = request.getParameter("sortType");
                final Integer aStatus = HttpUtil.getIntParameter(request, "aStatus");
                final Integer bStatus = HttpUtil.getIntParameter(request, "bStatus");
                final Integer onlineStatus = HttpUtil.getIntParameter(request, "onlineStatus");
                final String nickname = request.getParameter("nickname");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uService.search(username, matchType, registTime, minMoney, maxMoney, minLotteryMoney, maxLotteryMoney, minCode, maxCode, sortColoum, sortType, aStatus, bStatus, onlineStatus, type, nickname, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                final List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
                json.accumulate("userLevels", userLevels);
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
    
    @RequestMapping(value = { "/lottery-user/list-online" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_LIST_ONLINE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/list-online";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String sortColoum = request.getParameter("sortColoum");
                final String sortType = request.getParameter("sortType");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uService.listOnline(sortColoum, sortType, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
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
    
    @RequestMapping(value = { "/lottery-user/lower-online" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_LOWER_ONLINE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String username = request.getParameter("username");
        final JSONObject json = new JSONObject();
        int online = 0;
        if (StringUtil.isNotNull(username)) {
            final User targetUser = this.uDao.getByUsername(username);
            if (targetUser != null) {
                final List<User> uList = this.uDao.getUserLower(targetUser.getId());
                uList.add(targetUser);
                if (uList.size() > 0) {
                    final Integer[] ids = new Integer[uList.size()];
                    for (int i = 0; i < ids.length; ++i) {
                        ids[i] = uList.get(i).getId();
                    }
                    online = this.uDao.getOnlineCount(ids);
                }
            }
        }
        json.accumulate("data", online);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String nickname = HttpUtil.getStringParameterTrim(request, "nickname");
                final String password = HttpUtil.getStringParameterTrim(request, "password");
                final String upperUser = HttpUtil.getStringParameterTrim(request, "upperUser");
                final String relatedUsers = HttpUtil.getStringParameterTrim(request, "relatedUsers");
                final int type = HttpUtil.getIntParameter(request, "type");
                double locatePoint = HttpUtil.getDoubleParameter(request, "locatePoint");
                if (this.uValidate.testUsername(json, username)) {
                    locatePoint = MathUtil.doubleFormat(locatePoint, 2);
                    final int code = this.uCodePointUtil.getUserCode(locatePoint);
                    final double notLocatePoint = this.uCodePointUtil.getNotLocatePoint(locatePoint);
                    if (StringUtil.isNotNull(upperUser)) {
                        final User uBean = this.uDao.getByUsername(upperUser);
                        if (uBean != null) {
                            if (uBean.getType() == 1) {
                                if (this.uValidate.testNewUserPoint(json, uBean, locatePoint)) {
                                    final boolean result = this.uService.addLowerUser(json, uBean, username, nickname, password, type, code, locatePoint, notLocatePoint, relatedUsers);
                                    if (result) {
                                        this.adminUserLogJob.logAddUser(uEntity, request, username, relatedUsers, type, locatePoint);
                                        this.adminUserCriticalLogJob.logAddUser(uEntity, request, username, relatedUsers, type, locatePoint, actionKey);
                                        json.set(0, "0-5");
                                    }
                                }
                            }
                            else {
                                json.set(2, "2-2012");
                            }
                        }
                        else {
                            json.set(2, "2-2013");
                        }
                    }
                    else {
                        json.set(2, "2-2013");
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
    
    @RequestMapping(value = { "/lottery-user/lock" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_LOCK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/lock";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = HttpUtil.getIntParameter(request, "status");
                final String message = request.getParameter("message");
                final boolean result = this.uService.aStatus(username, status, message);
                if (result) {
                    this.adminUserLogJob.logLockUser(uEntity, request, username, status, message);
                    this.adminUserCriticalLogJob.logLockUser(uEntity, request, username, status, message, actionKey);
                    this.mailJob.sendLockUser(this.uDao.getByUsername(username), uEntity.getUsername(), status, message);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/unlock" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UNLOCK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/unlock";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = 0;
                final String message = null;
                final boolean result = this.uService.aStatus(username, status, message);
                if (result) {
                    this.adminUserLogJob.logUnlockUser(uEntity, request, username);
                    this.adminUserCriticalLogJob.logUnLockUser(uEntity, request, username, actionKey);
                    this.mailJob.sendUnLockUser(this.uDao.getByUsername(username), uEntity.getUsername());
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/recover" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RECOVER(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/recover";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final User result = this.uService.recover(username);
                if (result != null) {
                    this.adminUserLogJob.logRecoverUser(uEntity, request, result);
                    this.mailJob.sendRecoverUser(result, uEntity.getUsername());
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/bets-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/bets-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = HttpUtil.getIntParameter(request, "status");
                final String message = request.getParameter("message");
                final boolean result = this.uService.bStatus(username, status, message);
                if (result) {
                    this.adminUserLogJob.logModBStatus(uEntity, request, username, status, message);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user-profile/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_PROFILE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-profile/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final UserProfileVO uBean = this.uService.getUserProfile(username);
                if (uBean != null) {
                    final int userId = uBean.getBean().getId();
                    final UserInfo infoBean = this.uInfoDao.get(userId);
                    final List<UserCardVO> clist = this.uCardService.getByUserId(userId);
                    final List<UserSecurityVO> slist = this.uSecurityService.getByUserId(userId);
                    final UserGameAccount ptAccount = this.uGameAccountDao.get(userId, 11);
                    final UserGameAccount agAccount = this.uGameAccountDao.get(userId, 4);
                    json.accumulate("UserProfile", uBean);
                    json.accumulate("UserInfo", infoBean);
                    json.accumulate("CardList", clist);
                    json.accumulate("SecurityList", slist);
                    json.accumulate("PTAccount", ptAccount);
                    json.accumulate("AGAccount", agAccount);
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
    
    @RequestMapping(value = { "/lottery-user/modify-login-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_LOGIN_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-login-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String password = request.getParameter("password");
                final boolean result = this.uService.modifyLoginPwd(username, password);
                if (result) {
                    this.adminUserLogJob.logModLoginPwd(uEntity, request, username);
                    this.adminUserCriticalLogJob.logModLoginPwd(uEntity, request, username, actionKey);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-withdraw-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_WITHDRAW_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-withdraw-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String password = request.getParameter("password");
                final User targetUser = this.uDao.getByUsername(username);
                if (targetUser != null) {
                    if (targetUser.getBindStatus() == 1) {
                        final boolean result = this.uService.modifyWithdrawPwd(username, password);
                        if (result) {
                            this.adminUserLogJob.logModWithdrawPwd(uEntity, request, username);
                            this.adminUserCriticalLogJob.logModWithdrawPwd(uEntity, request, username, actionKey);
                            json.set(0, "0-5");
                        }
                        else {
                            json.set(1, "1-5");
                        }
                    }
                    else {
                        json.set(2, "2-1016");
                    }
                }
                else {
                    json.set(2, "2-3");
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
    
    @RequestMapping(value = { "/lottery-user/modify-withdraw-name" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_WITHDRAW_NAME(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-withdraw-name";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String withdrawName = request.getParameter("withdrawName");
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(withdrawName)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final User targetUser = this.uDao.getByUsername(username);
                if (targetUser != null) {
                    if (targetUser.getBindStatus() == 1) {
                        final boolean result = this.uService.modifyWithdrawName(username, withdrawName);
                        if (result) {
                            this.adminUserLogJob.logModWithdrawName(uEntity, request, username, withdrawName);
                            this.adminUserCriticalLogJob.logModWithdrawName(uEntity, request, username, withdrawName, actionKey);
                            json.set(0, "0-5");
                        }
                        else {
                            json.set(1, "1-5");
                        }
                    }
                    else {
                        json.set(2, "2-1016");
                    }
                }
                else {
                    json.set(2, "2-3");
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
    
    @RequestMapping(value = { "/lottery-user/reset-image-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RESET_IMAGE_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/reset-image-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final boolean result = this.uService.resetImagePwd(username);
                if (result) {
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-point" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_POINT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-point";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                double locatePoint = HttpUtil.getDoubleParameter(request, "locatePoint");
                locatePoint = MathUtil.doubleFormat(locatePoint, 2);
                final int code = this.uCodePointUtil.getUserCode(locatePoint);
                final double notLocatePoint = this.uCodePointUtil.getNotLocatePoint(locatePoint);
                final User targerUser = this.uDao.getByUsername(username);
                if (targerUser != null) {
                    final SysCodeRangeVO rBean = this.uValidate.loadEditPoint(targerUser);
                    if (locatePoint >= rBean.getMinPoint() && locatePoint <= rBean.getMaxPoint()) {
                        final boolean result = this.uService.modifyLotteryPoint(username, code, locatePoint, notLocatePoint);
                        if (result) {
                            this.adminUserLogJob.logModPoint(uEntity, request, username, locatePoint);
                            this.adminUserCriticalLogJob.logModPoint(uEntity, request, username, locatePoint, actionKey);
                            json.set(0, "0-5");
                        }
                        else {
                            json.set(1, "1-5");
                        }
                    }
                    else {
                        json.set(2, "2-9");
                    }
                }
                else {
                    json.set(2, "2-3");
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
    
    @RequestMapping(value = { "/lottery-user/down-point" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DOWN_POINT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/down-point";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final boolean result = this.uService.downLinePoint(username);
                if (result) {
                    this.adminUserLogJob.logDownPoint(uEntity, request, username);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-extra-point" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_EXTRA_POINT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-extra-point";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                double point = HttpUtil.getDoubleParameter(request, "point");
                point = MathUtil.doubleFormat(point, 1);
                final boolean result = this.uService.modifyExtraPoint(username, point);
                if (result) {
                    this.adminUserLogJob.logModExtraPoint(uEntity, request, username, point);
                    this.adminUserCriticalLogJob.logModExtraPoint(uEntity, request, username, point, actionKey);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-quota" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_QUOTA(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-quota";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final User uBean = this.uDao.getByUsername(username);
                if (uBean != null) {
                    if (uBean.getType() == 1) {
                        final int count1 = HttpUtil.getIntParameter(request, "count1");
                        final int count2 = HttpUtil.getIntParameter(request, "count2");
                        final int count3 = HttpUtil.getIntParameter(request, "count3");
                        final boolean result = this.uService.modifyQuota(username, count1, count2, count3);
                        if (result) {
                            json.set(0, "0-5");
                        }
                        else {
                            json.set(1, "1-5");
                        }
                    }
                    else {
                        json.set(2, "2-2018");
                    }
                }
                else {
                    json.set(2, "2-3");
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
    
    @RequestMapping(value = { "/lottery-user/get-point-info" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_GET_POINT_INFO(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String username = request.getParameter("username");
        final User entity = this.uDao.getByUsername(username);
        final UserBaseVO uBean = new UserBaseVO(entity);
        final SysCodeRangeVO rBean = this.uValidate.loadEditPoint(entity);
        final JSONObject json = new JSONObject();
        json.accumulate("uBean", (Object)uBean);
        json.accumulate("rBean", (Object)rBean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user/change-line" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CHANGE_LINE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/change-line";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int type = HttpUtil.getIntParameter(request, "type");
                final String aUser = request.getParameter("aUser").trim();
                final String bUser = request.getParameter("bUser").trim();
                if (StringUtils.equalsIgnoreCase(aUser, bUser)) {
                    json.set(1, "1-5");
                }
                else {
                    final User aBean = this.uDao.getByUsername(aUser);
                    final User bBean = this.uDao.getByUsername(bUser);
                    if (aBean != null && bBean != null) {
                        if (aBean.getUpid() == 0) {
                            json.set(2, "2-33");
                        }
                        else if (this.uCodePointUtil.isLevel1Proxy(aBean) && uEntity.getRoleId() != 1) {
                            json.set(2, "2-31");
                        }
                        else if (bBean.getUpids().indexOf("[" + aBean.getId() + "]") >= 0) {
                            json.set(2, "2-2024");
                        }
                        else if (aBean.getUpid() == bBean.getId()) {
                            json.set(2, "2-2025");
                        }
                        else {
                            final boolean flag = this.uService.changeLine(type, aUser, bUser);
                            if (flag) {
                                this.adminUserLogJob.logChangeLine(uEntity, request, aUser, bUser);
                                this.adminUserCriticalLogJob.logChangeLine(uEntity, request, aUser, bUser, actionKey);
                                json.set(0, "0-5");
                            }
                            else {
                                json.set(1, "1-5");
                            }
                        }
                    }
                    else {
                        json.set(2, "2-32");
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
    
    @RequestMapping(value = { "/lottery-user/modify-equal-code" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_EQUAL_CODE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-equal-code";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.uService.modifyEqualCode(username, status);
                if (result) {
                    this.adminUserLogJob.logModEqualCode(uEntity, request, username, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-transfers" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_TRANSFERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-transfers";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.uService.modifyTransfers(username, status);
                if (result) {
                    this.adminUserLogJob.logModTransfers(uEntity, request, username, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-withdraw" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_WITHDRAW(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-withdraw";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.uService.modifyWithdraw(username, status);
                if (result) {
                    this.adminUserLogJob.logModWithdraw(uEntity, request, username, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-platform-transfers" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_PLATFORM_TRANSFERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-platform-transfers";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.uService.modifyPlatformTransfers(username, status);
                if (result) {
                    this.adminUserLogJob.logModPlatformTransfers(uEntity, request, username, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/change-proxy" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CHANGE_PROXY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/change-proxy";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final boolean result = this.uService.changeProxy(username);
                if (result) {
                    this.adminUserLogJob.logChangeProxy(uEntity, request, username);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/unbind-google" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UNBIND_GOOGLE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/unbind-google";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final boolean result = this.uService.unbindGoogle(username);
                if (result) {
                    this.adminUserLogJob.unbindGoogle(uEntity, request, username);
                    this.adminUserCriticalLogJob.unbindGoogle(uEntity, request, username, actionKey);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/reset-lock-time" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UNBIND_RESET_LOCK_TIME(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/reset-lock-time";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final boolean result = this.uService.resetLockTime(username);
                if (result) {
                    this.adminUserLogJob.resetLockTime(uEntity, request, username);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/reset-email" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RESET_EMAIL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/reset-email";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final boolean result = this.uInfoService.resetEmail(username);
                if (result) {
                    this.adminUserLogJob.logResetEmail(uEntity, request, username);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-email" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_EMAIL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-email";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String email = request.getParameter("email");
                final boolean result = this.uInfoService.modifyEmail(username, email);
                if (result) {
                    this.adminUserLogJob.logModEmail(uEntity, request, username, email);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/check-exist" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CHECK_EXIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        username = username.toLowerCase();
        final User bean = this.uDao.getByUsername(username);
        final String isExist = (bean == null) ? "true" : "false";
        HttpUtil.write(response, isExist, "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user/reset-user-xiaofei" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RESERT_XIAOFEI(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/reset-user-xiaofei";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final User bean = this.uDao.getByUsername(username);
                final boolean resetTotal = this.mUserWithdrawLimitService.delByUserId(bean.getId());
                if (resetTotal) {
                    this.adminUserLogJob.logResetLimit(uEntity, request, username);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-related-upper" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_RELATED_INFO(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-related-upper";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String relatedUpUser = HttpUtil.getStringParameterTrim(request, "relatedUpUser");
                final double relatedPoint = HttpUtil.getDoubleParameter(request, "relatedPoint");
                final String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(remarks) || StringUtils.isEmpty(relatedUpUser) || relatedPoint < 0.0 || relatedPoint > 1.0) {
                    json.set(2, "2-2");
                }
                else {
                    final boolean updated = this.uService.modifyRelatedUpper(json, username, relatedUpUser, relatedPoint);
                    if (updated) {
                        this.adminUserLogJob.logModifyRelatedUpper(uEntity, request, username, relatedUpUser, relatedPoint, remarks);
                        json.set(0, "0-5");
                    }
                    else if (json.getError() == null) {
                        json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/relive-related-upper" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_RELIVE_RELATED_INFO(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/relive-related-upper";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
                if (StringUtils.isEmpty(username)) {
                    json.set(2, "2-2");
                }
                else if (StringUtils.isEmpty(remarks)) {
                    json.set(2, "2-30");
                }
                else {
                    final boolean updated = this.uService.reliveRelatedUpper(json, username);
                    if (updated) {
                        this.adminUserLogJob.logReliveRelatedUpper(uEntity, request, username, remarks);
                        json.set(0, "0-5");
                    }
                    else if (json.getError() == null) {
                        json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/modify-related-users" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_MODIFY_RELATED_USERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/modify-related-users";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String relatedUsers = HttpUtil.getStringParameterTrim(request, "relatedUsers");
                final String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(remarks)) {
                    json.set(2, "2-2");
                }
                else {
                    final boolean updated = this.uService.modifyRelatedUsers(json, username, relatedUsers);
                    if (updated) {
                        this.adminUserLogJob.logModifyUpdateRelatedUsers(uEntity, request, username, relatedUsers, remarks);
                        json.set(0, "0-5");
                    }
                    else if (json.getError() == null) {
                        json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user/lock-team" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_LOCK_TEAM(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/lock-team";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    final String remark = HttpUtil.getStringParameterTrim(request, "remark");
                    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(remark)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else if (this.uCodePointUtil.isLevel1Proxy(uBean)) {
                            json.set(2, "2-36");
                        }
                        else {
                            final boolean updated = this.uService.lockTeam(json, username, remark);
                            if (updated) {
                                this.mailJob.sendLockTeam(username, uEntity.getUsername(), remark);
                                this.adminUserLogJob.logLockTeam(uEntity, request, username, remark);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/prohibit-team-withdraw" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_PROHIBIT_TEAM_WITHDRAW(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/prohibit-team-withdraw";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    if (StringUtils.isEmpty(username)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else {
                            final boolean updated = this.uService.prohibitTeamWithdraw(json, username);
                            if (updated) {
                                this.mailJob.sendProhibitTeamWithdraw(username, uEntity.getUsername());
                                this.adminUserLogJob.prohibitTeamWithdraw(uEntity, request, username);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/allow-team-withdraw" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_ALLOW_TEAM_WITHDRAW(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/allow-team-withdraw";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    if (StringUtils.isEmpty(username)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else {
                            final boolean updated = this.uService.allowTeamWithdraw(json, username);
                            if (updated) {
                                this.mailJob.sendAllowTeamWithdraw(username, uEntity.getUsername());
                                this.adminUserLogJob.allowTeamWithdraw(uEntity, request, username);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/allow-team-transfers" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_ALLOW_TEAM_TRANSFERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/allow-team-transfers";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    if (StringUtils.isEmpty(username)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else {
                            final boolean updated = this.uService.allowTeamTransfers(json, username);
                            if (updated) {
                                this.mailJob.sendAllowTeamTransfers(username, uEntity.getUsername());
                                this.adminUserLogJob.allowTeamTransfers(uEntity, request, username);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/prohibit-team-transfers" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_PROHIBIT_TEAM_TRANSFERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/prohibit-team-transfers";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    if (StringUtils.isEmpty(username)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else {
                            final boolean updated = this.uService.prohibitTeamTransfers(json, username);
                            if (updated) {
                                this.mailJob.sendProhibitTeamTransfers(username, uEntity.getUsername());
                                this.adminUserLogJob.prohibitTeamTransfers(uEntity, request, username);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/allow-team-platform-transfers" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_ALLOW_TEAM_PLATFORM_TRANSFERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/allow-team-platform-transfers";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    if (StringUtils.isEmpty(username)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else {
                            final boolean updated = this.uService.allowTeamPlatformTransfers(json, username);
                            if (updated) {
                                this.mailJob.sendAllowTeamPlatformTransfers(username, uEntity.getUsername());
                                this.adminUserLogJob.allowTeamPlatformTransfers(uEntity, request, username);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/prohibit-team-platform-transfers" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_PROHIBIT_TEAM_PLATFORM_TRANSFERS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/prohibit-team-platform-transfers";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    if (StringUtils.isEmpty(username)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else {
                            final boolean updated = this.uService.prohibitTeamPlatformTransfers(json, username);
                            if (updated) {
                                this.mailJob.sendProhibitTeamPlatformTransfers(username, uEntity.getUsername());
                                this.adminUserLogJob.prohibitTeamPlatformTransfers(uEntity, request, username);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/un-lock-team" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UN_LOCK_TEAM(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/un-lock-team";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                if (uEntity.getRoleId() != 1) {
                    json.set(2, "2-37");
                }
                else {
                    final String username = HttpUtil.getStringParameterTrim(request, "username");
                    final String remark = HttpUtil.getStringParameterTrim(request, "remark");
                    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(remark)) {
                        json.set(2, "2-2");
                    }
                    else {
                        final User uBean = this.uDao.getByUsername(username);
                        if (uBean == null) {
                            json.set(2, "2-32");
                        }
                        else if (uBean.getId() == 72) {
                            json.set(2, "2-33");
                        }
                        else if (this.uCodePointUtil.isLevel1Proxy(uBean)) {
                            json.set(2, "2-36");
                        }
                        else {
                            final boolean updated = this.uService.unLockTeam(json, username);
                            if (updated) {
                                this.mailJob.sendUnLockTeam(username, uEntity.getUsername(), remark);
                                this.adminUserLogJob.logUnLockTeam(uEntity, request, username, remark);
                                json.set(0, "0-5");
                            }
                            else if (json.getError() == null) {
                                json.set(1, "1-5");
                            }
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
    
    @RequestMapping(value = { "/lottery-user/user-transfer" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_TRANSFER(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/user-transfer";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final double money = HttpUtil.getDoubleParameter(request, "money");
                final String aUser = HttpUtil.getStringParameterTrim(request, "aUser");
                final String bUser = HttpUtil.getStringParameterTrim(request, "bUser");
                final String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
                if (StringUtils.equalsIgnoreCase(aUser, bUser)) {
                    json.set(1, "1-5");
                }
                else if (money <= 0.0) {
                    json.set(1, "1-5");
                }
                else {
                    final User aBean = this.uDao.getByUsername(aUser);
                    final User bBean = this.uDao.getByUsername(bUser);
                    if (aBean != null && bBean != null) {
                        final boolean flag = this.uService.transfer(json, aBean, bBean, money, remarks);
                        if (flag) {
                            this.adminUserLogJob.logUserTransfer(uEntity, request, aUser, bUser, money, remarks);
                            this.adminUserCriticalLogJob.logUserTransfer(uEntity, request, aUser, bUser, money, remarks, actionKey);
                            this.mailJob.sendUserTransfer(aUser, bUser, money, remarks);
                            json.set(0, "0-5");
                        }
                    }
                    else {
                        json.set(2, "2-32");
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
    
    @RequestMapping(value = { "/lottery-user/withdraw-limit-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_WITHDRAW_LIMIT_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/withdraw-limit-list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final Integer withdrawId = HttpUtil.getIntParameter(request, "withdrawId");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final UserVO user = this.dataFactory.getUser(username);
                String time;
                if (withdrawId != null) {
                    final UserWithdrawVO withdrawVO = this.uWithdrawService.getById(withdrawId);
                    if (withdrawVO == null) {
                        json.accumulate("totalCount", 0);
                        json.accumulate("totalRemainConsumption", "0.00");
                        json.accumulate("data", "[]");
                        HttpUtil.write(response, json.toString(), "text/json");
                        return;
                    }
                    time = withdrawVO.getBean().getTime();
                }
                else {
                    time = new Moment().toSimpleTime();
                }
                final Map<String, Object> map = this.userWithdrawLimitService.getUserWithdrawLimits(user.getId(), time);
                if (map != null) {
                    List<UserWithdrawLimitVO> list = (List<UserWithdrawLimitVO>) map.get("list");
                    final int totalCount = list.size();
                    final List<UserWithdrawLimitVO> subList = new ArrayList<UserWithdrawLimitVO>();
                    if (start < list.size()) {
                        for (int i = start; i < list.size(); ++i) {
                            if (subList.size() < limit) {
                                subList.add(list.get(i));
                            }
                        }
                        list = subList;
                    }
                    json.accumulate("totalCount", totalCount);
                    json.accumulate("totalRemainConsumption", map.get("totalRemainConsumption"));
                    json.accumulate("data", list);
                }
                else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("totalRemainConsumption", "0.00");
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
    
    @RequestMapping(value = { "/lottery-user/change-zhaoshang" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CHANGE_ZHAO_SHANG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user/change-zhaoshang";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final int isCJZhaoShang = HttpUtil.getIntParameter(request, "isCJZhaoShang");
                final boolean result = this.uService.changeZhaoShang(json, username, isCJZhaoShang);
                if (result) {
                    this.adminUserLogJob.logChangeZhaoShang(uEntity, request, username, isCJZhaoShang);
                    this.adminUserCriticalLogJob.logChangeZhaoShang(uEntity, request, username, isCJZhaoShang, actionKey);
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
}
