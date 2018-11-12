package admin.web.content;

import net.sf.json.JSONObject;
import admin.domains.content.vo.AdminUserVO;
import java.util.List;
import admin.domains.content.vo.AdminUserRoleVO;
import admin.domains.content.entity.AdminUser;
import javautils.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import javautils.encrypt.PasswordUtil;
import javautils.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.content.biz.AdminUserRoleService;
import admin.domains.content.biz.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class AdminUserController extends AbstractActionController
{
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserRoleService roleService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @RequestMapping(value = { "/DisposableToken" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DISPOSABLE_TOKEN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        json.set(0, "0-3");
        final String token = this.generateDisposableToken(session, request);
        json.accumulate("token", token);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOGIN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/login";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        AdminUser uEntity = super.getCurrUser(session, request, response);
        final String clientIpAddr = HttpUtil.getClientIpAddr(request);
        if (uEntity == null) {
            final String username = request.getParameter("username");
            if (StringUtil.isNotNull(username)) {
                uEntity = this.adminUserDao.getByUsername(username);
            }
        }
        if (uEntity == null) {
            json.set(2, "2-3");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        final String password = request.getParameter("password");
        final String token = this.getDisposableToken(session, request);
        if (!PasswordUtil.validatePassword(uEntity.getPassword(), token, password)) {
            this.adminUserService.updatePwdError(uEntity.getId(), uEntity.getPwd_error() + 1);
            json.set(2, "2-3");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        if (uEntity.getPwd_error() >= 3) {
            json.set(2, "2-22");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        if (uEntity.getStatus() != 0) {
            json.set(2, "2-21");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
            if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                super.setGoogleBindUser(session, uEntity);
                json.set(2, "2-27");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            final int googlecode = Integer.parseInt(request.getParameter("googlecode"));
            if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googlecode)) {
                json.set(2, "2-24");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
        }
        final AdminUserRoleVO role = this.roleService.getById(uEntity.getRoleId());
        if (role == null || role.getBean().getStatus() != 0) {
            json.set(2, "2-26");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        super.setSessionUser(session, uEntity);
        final String loginTime = DateUtil.getCurrentTime();
        this.adminUserService.updateLoginTime(uEntity.getId(), loginTime);
        this.adminUserService.updatePwdError(uEntity.getId(), 0);
        json.set(0, "0-5");
        final long t2 = System.currentTimeMillis();
        this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<AdminUserVO> list = this.adminUserService.listAll(uEntity.getRoleId());
                json.set(0, "0-3");
                json.accumulate("data", list);
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
    
    @RequestMapping(value = { "/admin-user/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String password = request.getParameter("password");
                final int roleId = HttpUtil.getIntParameter(request, "roleId");
                final Integer setWithdrawPwd = HttpUtil.getIntParameter(request, "setWithdrawPwd");
                final String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
                if (!PasswordUtil.isSimplePasswordForGenerate(password)) {
                    if (setWithdrawPwd != null && setWithdrawPwd == 1) {
                        if (PasswordUtil.isSimplePasswordForGenerate(withdrawPwd)) {
                            json.set(2, "2-41");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                        if (uEntity.getRoleId() != 1) {
                            json.set(2, "2-37");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                    }
                    if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                        if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                            json.set(2, "2-27");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                        final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                            json.set(2, "2-24");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                    }
                    final boolean result = this.adminUserService.add(username, password, roleId, setWithdrawPwd, withdrawPwd);
                    if (result) {
                        json.set(0, "0-6");
                    }
                    else {
                        json.set(1, "1-6");
                    }
                }
                else {
                    json.set(2, "2-42");
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
    
    @RequestMapping(value = { "/admin-user/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String password = request.getParameter("password");
                final int roleId = HttpUtil.getIntParameter(request, "roleId");
                final Integer setWithdrawPwd = HttpUtil.getIntParameter(request, "setWithdrawPwd");
                final String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
                if (!PasswordUtil.isSimplePasswordForGenerate(password)) {
                    if (setWithdrawPwd != null && setWithdrawPwd == 1) {
                        if (PasswordUtil.isSimplePasswordForGenerate(withdrawPwd)) {
                            json.set(2, "2-41");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                        if (uEntity.getRoleId() != 1) {
                            json.set(2, "2-37");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                    }
                    if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                        if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                            json.set(2, "2-27");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                        final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                            json.set(2, "2-24");
                            HttpUtil.write(response, json.toString(), "text/json");
                            return;
                        }
                    }
                    final boolean result = this.adminUserService.edit(username, password, roleId, setWithdrawPwd, withdrawPwd);
                    if (result) {
                        json.set(0, "0-6");
                    }
                    else {
                        json.set(1, "1-6");
                    }
                }
                else {
                    json.set(2, "2-42");
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
    
    @RequestMapping(value = { "/admin-user/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.adminUserService.updateStatus(id, status);
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
    
    @RequestMapping(value = { "/admin-user/check-exist" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_CHECK_EXIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String username = request.getParameter("username");
        final AdminUser bean = this.adminUserDao.getByUsername(username);
        final String isExist = (bean == null) ? "true" : "false";
        HttpUtil.write(response, isExist, "text/json");
    }
    
    @RequestMapping(value = { "/admin-user/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final int id = HttpUtil.getIntParameter(request, "id");
        final AdminUser uEntity = this.adminUserDao.getById(id);
        uEntity.setPassword("***");
        if (!"notset".equalsIgnoreCase(uEntity.getWithdrawPwd())) {
            uEntity.setWithdrawPwd("***");
        }
        uEntity.setSecretKey("***");
        final JSONObject json = JSONObject.fromObject((Object)uEntity);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user/info" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_INFO(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final AdminUserVO bean = new AdminUserVO(uEntity, super.getAdminDataFactory());
            json.accumulate("data", bean);
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user/mod-login-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_MOD_LOGIN_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String oldPassword = request.getParameter("oldPassword");
            final String password = request.getParameter("password");
            final String token = this.getDisposableToken(session, request);
            if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                    json.set(2, "2-27");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                    json.set(2, "2-24");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
            }
            if (PasswordUtil.validatePassword(uEntity.getPassword(), token, oldPassword)) {
                if (!PasswordUtil.isSimplePasswordForGenerate(password)) {
                    final boolean result = this.adminUserService.modUserLoginPwd(uEntity.getId(), password);
                    if (result) {
                        json.set(0, "0-5");
                    }
                    else {
                        json.set(1, "1-1");
                    }
                }
                else {
                    json.set(2, "2-42");
                }
            }
            else {
                json.set(2, "2-11");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user/mod-withdraw-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_MOD_WITHDRAW_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String oldPassword = request.getParameter("oldPassword");
            final String password = request.getParameter("password");
            final String token = this.getDisposableToken(session, request);
            if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                    json.set(2, "2-27");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                    json.set(2, "2-24");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
            }
            if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, oldPassword)) {
                if (!PasswordUtil.isSimplePasswordForGenerate(password)) {
                    final boolean result = this.adminUserService.modUserWithdrawPwd(uEntity.getId(), password);
                    if (result) {
                        json.set(0, "0-5");
                    }
                    else {
                        json.set(1, "1-1");
                    }
                }
                else {
                    json.set(2, "2-41");
                }
            }
            else {
                json.set(2, "2-11");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user/close-google-auth" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_CLOSE_GOOGLE_AUTH(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/close-google-auth";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final AdminUser upadteUser = this.adminUserDao.getById(id);
                upadteUser.setIsValidate(0);
                final boolean result = this.adminUserDao.update(upadteUser);
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
    
    @RequestMapping(value = { "/admin-user/close-withdraw-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_CLOSE_WITHDRAW_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/close-withdraw-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String username = HttpUtil.getStringParameterTrim(request, "username");
            if (StringUtils.isEmpty(username)) {
                json.set(2, "2-2");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            if (uEntity.getRoleId() != 1) {
                json.set(2, "2-37");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                    json.set(2, "2-27");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                    json.set(2, "2-24");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
            }
            final boolean result = this.adminUserService.closeWithdrawPwd(username);
            if (result) {
                json.set(0, "0-5");
            }
            else {
                json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/admin-user/open-withdraw-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_OPEN_WITHDRAW_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/open-withdraw-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String username = HttpUtil.getStringParameterTrim(request, "username");
            final String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(withdrawPwd)) {
                json.set(2, "2-2");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            if (uEntity.getRoleId() != 1) {
                json.set(2, "2-37");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            if (PasswordUtil.isSimplePasswordForGenerate(withdrawPwd)) {
                json.set(2, "2-41");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                    json.set(2, "2-27");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                    json.set(2, "2-24");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
            }
            final boolean result = this.adminUserService.openWithdrawPwd(username, withdrawPwd);
            if (result) {
                json.set(0, "0-5");
            }
            else {
                json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/admin-user/unlock-withdraw-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_UNLOCK_WITHDRAW_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/unlock-withdraw-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
            final String token = this.getDisposableToken(session, request);
            if (StringUtils.isEmpty(withdrawPwd) || StringUtils.isEmpty(token)) {
                json.set(2, "2-2");
                HttpUtil.write(response, json.toString(), "text/json");
                return;
            }
            if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
                if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() != 1) {
                    json.set(2, "2-27");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final int googleCode = Integer.parseInt(request.getParameter("googleCode"));
                if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode)) {
                    json.set(2, "2-24");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
            }
            if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                final boolean result = this.setUnlockWithdrawPwd(session, true);
                if (result) {
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
                }
            }
            else {
                json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/admin-user/lock-withdraw-pwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_LOCK_WITHDRAW_PWD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/lock-withdraw-pwd";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final boolean result = this.setUnlockWithdrawPwd(session, false);
            if (result) {
                json.set(0, "0-5");
            }
            else {
                json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/admin-user/reset-pwd-error" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_RESET_PWD_ERROR(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/reset-pwd-error";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean updated = this.adminUserService.updatePwdError(id, 0);
                if (updated) {
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
    
    @RequestMapping(value = { "/admin-user/edit-ips" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_EDIT_IPS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user/edit-ips";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String ips = request.getParameter("ips");
                final boolean updated = this.adminUserService.updateIps(id, ips);
                if (updated) {
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
}
