package admin.web.google;

import admin.domains.content.biz.AdminUserService;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import javautils.StringUtil;
import javautils.encrypt.PasswordUtil;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.vo.config.AdminGoogleConfig;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GoogleController
  extends AbstractActionController
{
  @Autowired
  private AdminUserDao adminUserDao;
  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  @RequestMapping(value={"/google-auth/bind"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GOOGLE_AUTH_BIND(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getGoogleBindUser(session);
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    if (uEntity == null)
    {
      json.set(Integer.valueOf(1), "1-5");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    if ((StringUtils.isEmpty(uEntity.getSecretKey())) || (uEntity.getIsValidate() == 0))
    {
      GoogleAuthenticatorKey credentials = this.adminUserService.createCredentialsForUser(uEntity.getUsername());
      String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL("", uEntity.getUsername(), credentials);
      uEntity.setSecretKey(credentials.getKey());
      super.setGoogleBindUser(session, uEntity);
      json.accumulate("qr", otpAuthURL);
      json.accumulate("secret", credentials.getKey());
      json.set(Integer.valueOf(0), "0-5");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-25");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/google-auth/authorize"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GOOGLE_AUTH_AUTHROIZE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getGoogleBindUser(session);
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    if (uEntity == null)
    {
      json.set(Integer.valueOf(1), "1-5");
      HttpUtil.write(response, json.toString(), "text/json");
      return;
    }
    int verificationCode = HttpUtil.getIntParameter(request, "vCode").intValue();
    String loginPwd = request.getParameter("loginPwd");
    String token = getDisposableToken(session, request);
    
    boolean authorize = this.adminUserService.authoriseUser(uEntity.getUsername(), verificationCode);
    boolean isValidPwd = PasswordUtil.validatePassword(uEntity.getPassword(), token, loginPwd);
    if ((authorize) && (isValidPwd))
    {
      uEntity.setIsValidate(1);
      this.adminUserDao.update(uEntity);
      super.clearGoogleBindUser(session);
      json.set(Integer.valueOf(0), "0-5");
    }
    if (!isValidPwd) {
      json.set(Integer.valueOf(2), "2-5");
    } else if (!authorize) {
      json.set(Integer.valueOf(2), "2-24");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/google-auth/isbind"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GOOGLE_AUTH_ISBIND(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    if (this.dataFactory.getAdminGoogleConfig().isLoginStatus())
    {
      String username = request.getParameter("username");
      if (StringUtil.isNotNull(username))
      {
        AdminUser user = this.adminUserDao.getByUsername(username);
        if ((user != null) && (StringUtils.isNotEmpty(user.getSecretKey())) && (user.getIsValidate() == 1)) {
          HttpUtil.write(response, Boolean.toString(true));
        } else {
          HttpUtil.write(response, Boolean.toString(false));
        }
      }
    }
    else
    {
      HttpUtil.write(response, Boolean.toString(false));
    }
  }
}
