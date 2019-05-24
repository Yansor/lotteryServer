package admin.web.content;

import admin.domains.content.biz.AdminUserRoleService;
import admin.domains.content.biz.AdminUserService;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserRoleVO;
import admin.domains.content.vo.AdminUserVO;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.tools.StringUtils;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.List;
import javautils.StringUtil;
import javautils.date.DateUtil;
import javautils.encrypt.PasswordUtil;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.vo.config.AdminGoogleConfig;
import lottery.domains.pool.LotteryDataFactory;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminUserController
  extends AbstractActionController
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
  
  @RequestMapping(value={"/DisposableToken"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DISPOSABLE_TOKEN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    json.set(Integer.valueOf(0), "0-3");
    String token = generateDisposableToken(session, request);
    json.accumulate("token", token);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOGIN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/login";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    String clientIpAddr = HttpUtil.getClientIpAddr(request);
    if (uEntity == null)
    {
      String username = request.getParameter("username");
      if (StringUtil.isNotNull(username)) {
        uEntity = this.adminUserDao.getByUsername(username);
      }
    }
    if (uEntity == null)
    {
      json.set(Integer.valueOf(2), "2-3");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    String password = request.getParameter("password");
    String token = getDisposableToken(session, request);
    if (!PasswordUtil.validatePassword(uEntity.getPassword(), token, password))
    {
      this.adminUserService.updatePwdError(uEntity.getId(), uEntity.getPwd_error() + 1);
      json.set(Integer.valueOf(2), "2-3");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    if (uEntity.getPwd_error() >= 3)
    {
      json.set(Integer.valueOf(2), "2-22");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    if (uEntity.getStatus() != 0)
    {
      json.set(Integer.valueOf(2), "2-21");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
    {
      if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
      {
        super.setGoogleBindUser(session, uEntity);
        json.set(Integer.valueOf(2), "2-27");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      int googlecode = Integer.parseInt(request.getParameter("googlecode"));
      if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googlecode))
      {
        json.set(Integer.valueOf(2), "2-24");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
    }
    AdminUserRoleVO role = this.roleService.getById(uEntity.getRoleId());
    if ((role == null) || (role.getBean().getStatus() != 0))
    {
      json.set(Integer.valueOf(2), "2-26");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    super.setSessionUser(session, uEntity);
    String loginTime = DateUtil.getCurrentTime();
    this.adminUserService.updateLoginTime(uEntity.getId(), loginTime);
    this.adminUserService.updatePwdError(uEntity.getId(), 0);
    json.set(Integer.valueOf(0), "0-5");
    long t2 = System.currentTimeMillis();
    this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        List<AdminUserVO> list = this.adminUserService.listAll(uEntity.getRoleId());
        json.set(Integer.valueOf(0), "0-3");
        json.accumulate("data", list);
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String password = request.getParameter("password");
        int roleId = HttpUtil.getIntParameter(request, "roleId").intValue();
        Integer setWithdrawPwd = HttpUtil.getIntParameter(request, "setWithdrawPwd");
        String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
        if (!PasswordUtil.isSimplePasswordForGenerate(password))
        {
          if ((setWithdrawPwd != null) && (setWithdrawPwd.intValue() == 1))
          {
            if (PasswordUtil.isSimplePasswordForGenerate(withdrawPwd))
            {
              json.set(Integer.valueOf(2), "2-41");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
            if (uEntity.getRoleId() != 1)
            {
              json.set(Integer.valueOf(2), "2-37");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
          }
          if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
          {
            if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
            {
              json.set(Integer.valueOf(2), "2-27");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
            int googleCode = Integer.parseInt(request.getParameter("googleCode"));
            if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
            {
              json.set(Integer.valueOf(2), "2-24");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
          }
          boolean result = this.adminUserService.add(username, password, roleId, setWithdrawPwd, withdrawPwd);
          if (result) {
            json.set(Integer.valueOf(0), "0-6");
          } else {
            json.set(Integer.valueOf(1), "1-6");
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-42");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int roleId = HttpUtil.getIntParameter(request, "roleId").intValue();
        Integer setWithdrawPwd = HttpUtil.getIntParameter(request, "setWithdrawPwd");
        String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
        if (!PasswordUtil.isSimplePasswordForGenerate(password))
        {
          if ((setWithdrawPwd != null) && (setWithdrawPwd.intValue() == 1))
          {
            if (PasswordUtil.isSimplePasswordForGenerate(withdrawPwd))
            {
              json.set(Integer.valueOf(2), "2-41");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
            if (uEntity.getRoleId() != 1)
            {
              json.set(Integer.valueOf(2), "2-37");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
          }
          if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
          {
            if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
            {
              json.set(Integer.valueOf(2), "2-27");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
            int googleCode = Integer.parseInt(request.getParameter("googleCode"));
            if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
            {
              json.set(Integer.valueOf(2), "2-24");
              HttpUtil.write(response, json.toString(), "text/json");
              return;
            }
          }
          boolean result = this.adminUserService.edit(username, password, roleId, setWithdrawPwd, withdrawPwd);
          if (result) {
            json.set(Integer.valueOf(0), "0-6");
          } else {
            json.set(Integer.valueOf(1), "1-6");
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-42");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.adminUserService.updateStatus(id, status);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/check-exist"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_CHECK_EXIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String username = request.getParameter("username");
    AdminUser bean = this.adminUserDao.getByUsername(username);
    String isExist = bean == null ? "true" : "false";
    HttpUtil.write(response, isExist, "text/json");
  }
  
  @RequestMapping(value={"/admin-user/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    int id = HttpUtil.getIntParameter(request, "id").intValue();
    AdminUser uEntity = this.adminUserDao.getById(id);
    uEntity.setPassword("***");
    if (!"notset".equalsIgnoreCase(uEntity.getWithdrawPwd())) {
      uEntity.setWithdrawPwd("***");
    }
    uEntity.setSecretKey("***");
    JSONObject json = JSONObject.fromObject(uEntity);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/info"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_INFO(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      AdminUserVO bean = new AdminUserVO(uEntity, super.getAdminDataFactory());
      json.accumulate("data", bean);
      json.set(Integer.valueOf(0), "0-3");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/mod-login-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_MOD_LOGIN_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String oldPassword = request.getParameter("oldPassword");
      String password = request.getParameter("password");
      String token = getDisposableToken(session, request);
      if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
      {
        if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
        {
          json.set(Integer.valueOf(2), "2-27");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        int googleCode = Integer.parseInt(request.getParameter("googleCode"));
        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
        {
          json.set(Integer.valueOf(2), "2-24");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
      }
      if (PasswordUtil.validatePassword(uEntity.getPassword(), token, oldPassword))
      {
        if (!PasswordUtil.isSimplePasswordForGenerate(password))
        {
          boolean result = this.adminUserService.modUserLoginPwd(uEntity.getId(), password);
          if (result) {
            json.set(Integer.valueOf(0), "0-5");
          } else {
            json.set(Integer.valueOf(1), "1-1");
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-42");
        }
      }
      else {
        json.set(Integer.valueOf(2), "2-11");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/mod-withdraw-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_MOD_WITHDRAW_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String oldPassword = request.getParameter("oldPassword");
      String password = request.getParameter("password");
      String token = getDisposableToken(session, request);
      if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
      {
        if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
        {
          json.set(Integer.valueOf(2), "2-27");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        int googleCode = Integer.parseInt(request.getParameter("googleCode"));
        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
        {
          json.set(Integer.valueOf(2), "2-24");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
      }
      if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, oldPassword))
      {
        if (!PasswordUtil.isSimplePasswordForGenerate(password))
        {
          boolean result = this.adminUserService.modUserWithdrawPwd(uEntity.getId(), password);
          if (result) {
            json.set(Integer.valueOf(0), "0-5");
          } else {
            json.set(Integer.valueOf(1), "1-1");
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-41");
        }
      }
      else {
        json.set(Integer.valueOf(2), "2-11");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/close-google-auth"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_CLOSE_GOOGLE_AUTH(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/close-google-auth";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        AdminUser upadteUser = this.adminUserDao.getById(id);
        upadteUser.setIsValidate(0);
        boolean result = this.adminUserDao.update(upadteUser);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/close-withdraw-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_CLOSE_WITHDRAW_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/close-withdraw-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String username = HttpUtil.getStringParameterTrim(request, "username");
      if (StringUtils.isEmpty(username))
      {
        json.set(Integer.valueOf(2), "2-2");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      if (uEntity.getRoleId() != 1)
      {
        json.set(Integer.valueOf(2), "2-37");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
      {
        if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
        {
          json.set(Integer.valueOf(2), "2-27");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        int googleCode = Integer.parseInt(request.getParameter("googleCode"));
        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
        {
          json.set(Integer.valueOf(2), "2-24");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
      }
      boolean result = this.adminUserService.closeWithdrawPwd(username);
      if (result) {
        json.set(Integer.valueOf(0), "0-5");
      } else {
        json.set(Integer.valueOf(1), "1-5");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/open-withdraw-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_OPEN_WITHDRAW_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/open-withdraw-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String username = HttpUtil.getStringParameterTrim(request, "username");
      String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
      if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(withdrawPwd)))
      {
        json.set(Integer.valueOf(2), "2-2");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      if (uEntity.getRoleId() != 1)
      {
        json.set(Integer.valueOf(2), "2-37");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      if (PasswordUtil.isSimplePasswordForGenerate(withdrawPwd))
      {
        json.set(Integer.valueOf(2), "2-41");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
      {
        if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
        {
          json.set(Integer.valueOf(2), "2-27");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        int googleCode = Integer.parseInt(request.getParameter("googleCode"));
        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
        {
          json.set(Integer.valueOf(2), "2-24");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
      }
      boolean result = this.adminUserService.openWithdrawPwd(username, withdrawPwd);
      if (result) {
        json.set(Integer.valueOf(0), "0-5");
      } else {
        json.set(Integer.valueOf(1), "1-5");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/unlock-withdraw-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_UNLOCK_WITHDRAW_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/unlock-withdraw-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String withdrawPwd = HttpUtil.getStringParameterTrim(request, "withdrawPwd");
      String token = getDisposableToken(session, request);
      if ((StringUtils.isEmpty(withdrawPwd)) || (StringUtils.isEmpty(token)))
      {
        json.set(Integer.valueOf(2), "2-2");
        HttpUtil.write(response, json.toString(), "text/json");
        return;
      }
      if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
      {
        if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() != 1))
        {
          json.set(Integer.valueOf(2), "2-27");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        int googleCode = Integer.parseInt(request.getParameter("googleCode"));
        if (!this.adminUserService.authoriseUser(uEntity.getUsername(), googleCode))
        {
          json.set(Integer.valueOf(2), "2-24");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
      }
      if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
      {
        boolean result = setUnlockWithdrawPwd(session, true);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-12");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/lock-withdraw-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_LOCK_WITHDRAW_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/lock-withdraw-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      boolean result = setUnlockWithdrawPwd(session, false);
      if (result) {
        json.set(Integer.valueOf(0), "0-5");
      } else {
        json.set(Integer.valueOf(1), "1-5");
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/reset-pwd-error"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_RESET_PWD_ERROR(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/reset-pwd-error";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean updated = this.adminUserService.updatePwdError(id, 0);
        if (updated) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user/edit-ips"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_EDIT_IPS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user/edit-ips";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String ips = request.getParameter("ips");
        boolean updated = this.adminUserService.updateIps(id, ips);
        if (updated) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
