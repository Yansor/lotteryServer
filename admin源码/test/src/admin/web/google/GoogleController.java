package admin.web.google;

import javautils.StringUtil;
import javautils.encrypt.PasswordUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import admin.domains.content.entity.AdminUser;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.content.biz.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class GoogleController extends AbstractActionController
{
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @RequestMapping(value = { "/google-auth/bind" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GOOGLE_AUTH_BIND(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getGoogleBindUser(session);
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        if (uEntity == null) {
            json.set(1, "1-5");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        if (StringUtils.isEmpty(uEntity.getSecretKey()) || uEntity.getIsValidate() == 0) {
            final GoogleAuthenticatorKey credentials = this.adminUserService.createCredentialsForUser(uEntity.getUsername());
            final String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL("", uEntity.getUsername(), credentials);
            uEntity.setSecretKey(credentials.getKey());
            super.setGoogleBindUser(session, uEntity);
            json.accumulate("qr", otpAuthURL);
            json.accumulate("secret", credentials.getKey());
            json.set(0, "0-5");
        }
        else {
            json.set(2, "2-25");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/google-auth/authorize" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GOOGLE_AUTH_AUTHROIZE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getGoogleBindUser(session);
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        if (uEntity == null) {
            json.set(1, "1-5");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
        }
        final int verificationCode = HttpUtil.getIntParameter(request, "vCode");
        final String loginPwd = request.getParameter("loginPwd");
        final String token = this.getDisposableToken(session, request);
        final boolean authorize = this.adminUserService.authoriseUser(uEntity.getUsername(), verificationCode);
        final boolean isValidPwd = PasswordUtil.validatePassword(uEntity.getPassword(), token, loginPwd);
        if (authorize && isValidPwd) {
            uEntity.setIsValidate(1);
            this.adminUserDao.update(uEntity);
            super.clearGoogleBindUser(session);
            json.set(0, "0-5");
        }
        if (!isValidPwd) {
            json.set(2, "2-5");
        }
        else if (!authorize) {
            json.set(2, "2-24");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/google-auth/isbind" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GOOGLE_AUTH_ISBIND(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        if (this.dataFactory.getAdminGoogleConfig().isLoginStatus()) {
            final String username = request.getParameter("username");
            if (StringUtil.isNotNull(username)) {
                final AdminUser user = this.adminUserDao.getByUsername(username);
                if (user != null && StringUtils.isNotEmpty(user.getSecretKey()) && user.getIsValidate() == 1) {
                    HttpUtil.write(response, Boolean.toString(true));
                }
                else {
                    HttpUtil.write(response, Boolean.toString(false));
                }
            }
        }
        else {
            HttpUtil.write(response, Boolean.toString(false));
        }
    }
}
