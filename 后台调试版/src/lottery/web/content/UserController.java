package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.domains.jobs.MailJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.biz.UserInfoService;
import lottery.domains.content.biz.UserSecurityService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.dao.UserInfoDao;
import lottery.domains.content.dao.UserPlanInfoDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.entity.UserInfo;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import lottery.domains.content.vo.user.UserBaseVO;
import lottery.domains.content.vo.user.UserCardVO;
import lottery.domains.content.vo.user.UserProfileVO;
import lottery.domains.content.vo.user.UserSecurityVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.vo.user.UserWithdrawLimitVO;
import lottery.domains.content.vo.user.UserWithdrawVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.web.content.validate.UserValidate;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController
  extends AbstractActionController
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
  
  @RequestMapping(value={"/lottery-user/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String matchType = request.getParameter("matchType");
        String registTime = request.getParameter("registTime");
        
        Double minMoney = HttpUtil.getDoubleParameter(request, "minTotalMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxTotalMoney");
        Double minLotteryMoney = HttpUtil.getDoubleParameter(request, "minLotteryMoney");
        Double maxLotteryMoney = HttpUtil.getDoubleParameter(request, "maxLotteryMoney");
        Integer minCode = HttpUtil.getIntParameter(request, "minCode");
        Integer maxCode = HttpUtil.getIntParameter(request, "maxCode");
        String sortColoum = request.getParameter("sortColoum");
        String sortType = request.getParameter("sortType");
        Integer aStatus = HttpUtil.getIntParameter(request, "aStatus");
        Integer bStatus = HttpUtil.getIntParameter(request, "bStatus");
        Integer onlineStatus = HttpUtil.getIntParameter(request, "onlineStatus");
        String nickname = request.getParameter("nickname");
        Integer type = HttpUtil.getIntParameter(request, "type");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uService.search(username, matchType, registTime, minMoney, maxMoney, minLotteryMoney, maxLotteryMoney, minCode, maxCode, sortColoum, sortType, aStatus, bStatus, onlineStatus, type, nickname, start, limit);
        if (pList != null)
        {
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
        json.accumulate("userLevels", userLevels);
        
        json.set(Integer.valueOf(0), "0-3");
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
  
  @RequestMapping(value={"/lottery-user/list-online"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_LIST_ONLINE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/list-online";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String sortColoum = request.getParameter("sortColoum");
        String sortType = request.getParameter("sortType");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uService.listOnline(sortColoum, sortType, start, limit);
        if (pList != null)
        {
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        json.set(Integer.valueOf(0), "0-3");
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
  
  @RequestMapping(value={"/lottery-user/lower-online"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_LOWER_ONLINE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String username = request.getParameter("username");
    JSONObject json = new JSONObject();
    int online = 0;
    if (StringUtil.isNotNull(username))
    {
      User targetUser = this.uDao.getByUsername(username);
      if (targetUser != null)
      {
        List<User> uList = this.uDao.getUserLower(targetUser.getId());
        uList.add(targetUser);
        if (uList.size() > 0)
        {
          Integer[] ids = new Integer[uList.size()];
          for (int i = 0; i < ids.length; i++) {
            ids[i] = Integer.valueOf(((User)uList.get(i)).getId());
          }
          online = this.uDao.getOnlineCount(ids);
        }
      }
    }
    json.accumulate("data", online);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String nickname = HttpUtil.getStringParameterTrim(request, "nickname");
        String password = HttpUtil.getStringParameterTrim(request, "password");
        String upperUser = HttpUtil.getStringParameterTrim(request, "upperUser");
        String relatedUsers = HttpUtil.getStringParameterTrim(request, "relatedUsers");
        int type = HttpUtil.getIntParameter(request, "type").intValue();
        double locatePoint = HttpUtil.getDoubleParameter(request, "locatePoint").doubleValue();
        if (this.uValidate.testUsername(json, username))
        {
          locatePoint = MathUtil.doubleFormat(locatePoint, 2);
          int code = this.uCodePointUtil.getUserCode(locatePoint);
          double notLocatePoint = this.uCodePointUtil.getNotLocatePoint(locatePoint);
          if (StringUtil.isNotNull(upperUser))
          {
            User uBean = this.uDao.getByUsername(upperUser);
            if (uBean != null)
            {
              if (uBean.getType() == 1)
              {
                if (this.uValidate.testNewUserPoint(json, uBean, locatePoint))
                {
                  boolean result = this.uService.addLowerUser(json, uBean, username, nickname, password, type, code, locatePoint, notLocatePoint, relatedUsers);
                  if (result)
                  {
                    this.adminUserLogJob.logAddUser(uEntity, request, username, relatedUsers, type, locatePoint);
                    this.adminUserCriticalLogJob.logAddUser(uEntity, request, username, relatedUsers, type, locatePoint, actionKey);
                    json.set(Integer.valueOf(0), "0-5");
                  }
                }
              }
              else {
                json.set(Integer.valueOf(2), "2-2012");
              }
            }
            else {
              json.set(Integer.valueOf(2), "2-2013");
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-2013");
          }
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
  
  @RequestMapping(value={"/lottery-user/lock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_LOCK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/lock";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        String message = request.getParameter("message");
        boolean result = this.uService.aStatus(username, status, message);
        if (result)
        {
          this.adminUserLogJob.logLockUser(uEntity, request, username, status, message);
          this.adminUserCriticalLogJob.logLockUser(uEntity, request, username, status, message, actionKey);
          this.mailJob.sendLockUser(this.uDao.getByUsername(username), uEntity.getUsername(), status, message);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/unlock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UNLOCK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/unlock";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = 0;
        String message = null;
        boolean result = this.uService.aStatus(username, status, message);
        if (result)
        {
          this.adminUserLogJob.logUnlockUser(uEntity, request, username);
          this.adminUserCriticalLogJob.logUnLockUser(uEntity, request, username, actionKey);
          this.mailJob.sendUnLockUser(this.uDao.getByUsername(username), uEntity.getUsername());
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/recover"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RECOVER(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/recover";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        User result = this.uService.recover(username);
        if (result != null)
        {
          this.adminUserLogJob.logRecoverUser(uEntity, request, result);
          this.mailJob.sendRecoverUser(result, uEntity.getUsername());
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/bets-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/bets-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        String message = request.getParameter("message");
        boolean result = this.uService.bStatus(username, status, message);
        if (result)
        {
          this.adminUserLogJob.logModBStatus(uEntity, request, username, status, message);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user-profile/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_PROFILE_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-profile/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        UserProfileVO uBean = this.uService.getUserProfile(username);
        if (uBean != null)
        {
          int userId = uBean.getBean().getId();
          
          UserInfo infoBean = this.uInfoDao.get(userId);
          
          List<UserCardVO> clist = this.uCardService.getByUserId(userId);
          
          List<UserSecurityVO> slist = this.uSecurityService.getByUserId(userId);
          
          UserGameAccount ptAccount = this.uGameAccountDao.get(userId, 11);
          
          UserGameAccount agAccount = this.uGameAccountDao.get(userId, 4);
          
          json.accumulate("UserProfile", uBean);
          json.accumulate("UserInfo", infoBean);
          
          json.accumulate("CardList", clist);
          json.accumulate("SecurityList", slist);
          
          json.accumulate("PTAccount", ptAccount);
          json.accumulate("AGAccount", agAccount);
          json.set(Integer.valueOf(0), "0-3");
        }
        else
        {
          json.set(Integer.valueOf(1), "1-3");
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
  
  @RequestMapping(value={"/lottery-user/modify-login-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_LOGIN_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-login-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean result = this.uService.modifyLoginPwd(username, password);
        if (result)
        {
          this.adminUserLogJob.logModLoginPwd(uEntity, request, username);
          this.adminUserCriticalLogJob.logModLoginPwd(uEntity, request, username, actionKey);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-withdraw-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_WITHDRAW_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-withdraw-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User targetUser = this.uDao.getByUsername(username);
        if (targetUser != null)
        {
          if (targetUser.getBindStatus() == 1)
          {
            boolean result = this.uService.modifyWithdrawPwd(username, password);
            if (result)
            {
              this.adminUserLogJob.logModWithdrawPwd(uEntity, request, username);
              this.adminUserCriticalLogJob.logModWithdrawPwd(uEntity, request, username, actionKey);
              json.set(Integer.valueOf(0), "0-5");
            }
            else
            {
              json.set(Integer.valueOf(1), "1-5");
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-1016");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-3");
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
  
  @RequestMapping(value={"/lottery-user/modify-withdraw-name"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_WITHDRAW_NAME(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-withdraw-name";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String withdrawName = request.getParameter("withdrawName");
        if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(withdrawName)))
        {
          json.set(Integer.valueOf(2), "2-2");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        User targetUser = this.uDao.getByUsername(username);
        if (targetUser != null)
        {
          if (targetUser.getBindStatus() == 1)
          {
            boolean result = this.uService.modifyWithdrawName(username, withdrawName);
            if (result)
            {
              this.adminUserLogJob.logModWithdrawName(uEntity, request, username, withdrawName);
              this.adminUserCriticalLogJob.logModWithdrawName(uEntity, request, username, withdrawName, actionKey);
              json.set(Integer.valueOf(0), "0-5");
            }
            else
            {
              json.set(Integer.valueOf(1), "1-5");
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-1016");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-3");
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
  
  @RequestMapping(value={"/lottery-user/reset-image-pwd"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RESET_IMAGE_PWD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/reset-image-pwd";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        boolean result = this.uService.resetImagePwd(username);
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
  
  @RequestMapping(value={"/lottery-user/modify-point"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_POINT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-point";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        double locatePoint = HttpUtil.getDoubleParameter(request, "locatePoint").doubleValue();
        locatePoint = MathUtil.doubleFormat(locatePoint, 2);
        int code = this.uCodePointUtil.getUserCode(locatePoint);
        double notLocatePoint = this.uCodePointUtil.getNotLocatePoint(locatePoint);
        User targerUser = this.uDao.getByUsername(username);
        if (targerUser != null)
        {
          SysCodeRangeVO rBean = this.uValidate.loadEditPoint(targerUser);
          if ((locatePoint >= rBean.getMinPoint()) && (locatePoint <= rBean.getMaxPoint()))
          {
            boolean result = this.uService.modifyLotteryPoint(username, code, locatePoint, notLocatePoint);
            if (result)
            {
              this.adminUserLogJob.logModPoint(uEntity, request, username, locatePoint);
              this.adminUserCriticalLogJob.logModPoint(uEntity, request, username, locatePoint, actionKey);
              json.set(Integer.valueOf(0), "0-5");
            }
            else
            {
              json.set(Integer.valueOf(1), "1-5");
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-9");
          }
        }
        else
        {
          json.set(Integer.valueOf(2), "2-3");
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
  
  @RequestMapping(value={"/lottery-user/down-point"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DOWN_POINT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/down-point";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        boolean result = this.uService.downLinePoint(username);
        if (result)
        {
          this.adminUserLogJob.logDownPoint(uEntity, request, username);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-extra-point"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_EXTRA_POINT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-extra-point";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        double point = HttpUtil.getDoubleParameter(request, "point").doubleValue();
        point = MathUtil.doubleFormat(point, 1);
        boolean result = this.uService.modifyExtraPoint(username, point);
        if (result)
        {
          this.adminUserLogJob.logModExtraPoint(uEntity, request, username, point);
          this.adminUserCriticalLogJob.logModExtraPoint(uEntity, request, username, point, actionKey);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-quota"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_QUOTA(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-quota";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        User uBean = this.uDao.getByUsername(username);
        if (uBean != null)
        {
          if (uBean.getType() == 1)
          {
            int count1 = HttpUtil.getIntParameter(request, "count1").intValue();
            int count2 = HttpUtil.getIntParameter(request, "count2").intValue();
            int count3 = HttpUtil.getIntParameter(request, "count3").intValue();
            boolean result = this.uService.modifyQuota(username, count1, count2, count3);
            if (result) {
              json.set(Integer.valueOf(0), "0-5");
            } else {
              json.set(Integer.valueOf(1), "1-5");
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-2018");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-3");
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
  
  @RequestMapping(value={"/lottery-user/get-point-info"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_GET_POINT_INFO(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String username = request.getParameter("username");
    User entity = this.uDao.getByUsername(username);
    UserBaseVO uBean = new UserBaseVO(entity);
    SysCodeRangeVO rBean = this.uValidate.loadEditPoint(entity);
    JSONObject json = new JSONObject();
    json.accumulate("uBean", uBean);
    json.accumulate("rBean", rBean);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user/change-line"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CHANGE_LINE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/change-line";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int type = HttpUtil.getIntParameter(request, "type").intValue();
        String aUser = request.getParameter("aUser").trim();
        String bUser = request.getParameter("bUser").trim();
        if (StringUtils.equalsIgnoreCase(aUser, bUser))
        {
          json.set(Integer.valueOf(1), "1-5");
        }
        else
        {
          User aBean = this.uDao.getByUsername(aUser);
          User bBean = this.uDao.getByUsername(bUser);
          if ((aBean != null) && (bBean != null))
          {
            if (aBean.getUpid() == 0)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else if ((this.uCodePointUtil.isLevel1Proxy(aBean)) && (uEntity.getRoleId() != 1))
            {
              json.set(Integer.valueOf(2), "2-31");
            }
            else if (bBean.getUpids().indexOf("[" + aBean.getId() + "]") >= 0)
            {
              json.set(Integer.valueOf(2), "2-2024");
            }
            else if (aBean.getUpid() == bBean.getId())
            {
              json.set(Integer.valueOf(2), "2-2025");
            }
            else
            {
              boolean flag = this.uService.changeLine(type, aUser, bUser);
              if (flag)
              {
                this.adminUserLogJob.logChangeLine(uEntity, request, aUser, bUser);
                this.adminUserCriticalLogJob.logChangeLine(uEntity, request, aUser, bUser, actionKey);
                json.set(Integer.valueOf(0), "0-5");
              }
              else
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-32");
          }
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
  
  @RequestMapping(value={"/lottery-user/modify-equal-code"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_EQUAL_CODE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-equal-code";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.uService.modifyEqualCode(username, status);
        if (result)
        {
          this.adminUserLogJob.logModEqualCode(uEntity, request, username, status);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-transfers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_TRANSFERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-transfers";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.uService.modifyTransfers(username, status);
        if (result)
        {
          this.adminUserLogJob.logModTransfers(uEntity, request, username, status);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-withdraw"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_WITHDRAW(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-withdraw";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.uService.modifyWithdraw(username, status);
        if (result)
        {
          this.adminUserLogJob.logModWithdraw(uEntity, request, username, status);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-platform-transfers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_PLATFORM_TRANSFERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-platform-transfers";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.uService.modifyPlatformTransfers(username, status);
        if (result)
        {
          this.adminUserLogJob.logModPlatformTransfers(uEntity, request, username, status);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/change-proxy"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CHANGE_PROXY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/change-proxy";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        boolean result = this.uService.changeProxy(username);
        if (result)
        {
          this.adminUserLogJob.logChangeProxy(uEntity, request, username);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/unbind-google"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UNBIND_GOOGLE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/unbind-google";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        boolean result = this.uService.unbindGoogle(username);
        if (result)
        {
          this.adminUserLogJob.unbindGoogle(uEntity, request, username);
          this.adminUserCriticalLogJob.unbindGoogle(uEntity, request, username, actionKey);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/reset-lock-time"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UNBIND_RESET_LOCK_TIME(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/reset-lock-time";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        boolean result = this.uService.resetLockTime(username);
        if (result)
        {
          this.adminUserLogJob.resetLockTime(uEntity, request, username);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/reset-email"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RESET_EMAIL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/reset-email";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        boolean result = this.uInfoService.resetEmail(username);
        if (result)
        {
          this.adminUserLogJob.logResetEmail(uEntity, request, username);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-email"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_EMAIL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-email";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        boolean result = this.uInfoService.modifyEmail(username, email);
        if (result)
        {
          this.adminUserLogJob.logModEmail(uEntity, request, username, email);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/check-exist"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CHECK_EXIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String username = HttpUtil.getStringParameterTrim(request, "username");
    username = username.toLowerCase();
    User bean = this.uDao.getByUsername(username);
    String isExist = bean == null ? "true" : "false";
    HttpUtil.write(response, isExist, "text/json");
  }
  
  @RequestMapping(value={"/lottery-user/reset-user-xiaofei"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RESERT_XIAOFEI(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/reset-user-xiaofei";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        User bean = this.uDao.getByUsername(username);
        boolean resetTotal = this.mUserWithdrawLimitService.delByUserId(bean.getId());
        if (resetTotal)
        {
          this.adminUserLogJob.logResetLimit(uEntity, request, username);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-user/modify-related-upper"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_RELATED_INFO(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-related-upper";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String relatedUpUser = HttpUtil.getStringParameterTrim(request, "relatedUpUser");
        double relatedPoint = HttpUtil.getDoubleParameter(request, "relatedPoint").doubleValue();
        String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
        if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(remarks)) || 
          (StringUtils.isEmpty(relatedUpUser)) || 
          (relatedPoint < 0.0D) || 
          (relatedPoint > 1.0D))
        {
          json.set(Integer.valueOf(2), "2-2");
        }
        else
        {
          boolean updated = this.uService.modifyRelatedUpper(json, username, relatedUpUser, relatedPoint);
          if (updated)
          {
            this.adminUserLogJob.logModifyRelatedUpper(uEntity, request, username, relatedUpUser, relatedPoint, remarks);
            json.set(Integer.valueOf(0), "0-5");
          }
          else if (json.getError() == null)
          {
            json.set(Integer.valueOf(1), "1-5");
          }
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
  
  @RequestMapping(value={"/lottery-user/relive-related-upper"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_RELIVE_RELATED_INFO(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/relive-related-upper";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
        if (StringUtils.isEmpty(username))
        {
          json.set(Integer.valueOf(2), "2-2");
        }
        else if (StringUtils.isEmpty(remarks))
        {
          json.set(Integer.valueOf(2), "2-30");
        }
        else
        {
          boolean updated = this.uService.reliveRelatedUpper(json, username);
          if (updated)
          {
            this.adminUserLogJob.logReliveRelatedUpper(uEntity, request, username, remarks);
            json.set(Integer.valueOf(0), "0-5");
          }
          else if (json.getError() == null)
          {
            json.set(Integer.valueOf(1), "1-5");
          }
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
  
  @RequestMapping(value={"/lottery-user/modify-related-users"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_MODIFY_RELATED_USERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/modify-related-users";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String relatedUsers = HttpUtil.getStringParameterTrim(request, "relatedUsers");
        String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
        if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(remarks)))
        {
          json.set(Integer.valueOf(2), "2-2");
        }
        else
        {
          boolean updated = this.uService.modifyRelatedUsers(json, username, relatedUsers);
          if (updated)
          {
            this.adminUserLogJob.logModifyUpdateRelatedUsers(uEntity, request, username, relatedUsers, remarks);
            json.set(Integer.valueOf(0), "0-5");
          }
          else if (json.getError() == null)
          {
            json.set(Integer.valueOf(1), "1-5");
          }
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
  
  @RequestMapping(value={"/lottery-user/lock-team"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_LOCK_TEAM(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/lock-team";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          String remark = HttpUtil.getStringParameterTrim(request, "remark");
          if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(remark)))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else if (this.uCodePointUtil.isLevel1Proxy(uBean))
            {
              json.set(Integer.valueOf(2), "2-36");
            }
            else
            {
              boolean updated = this.uService.lockTeam(json, username, remark);
              if (updated)
              {
                this.mailJob.sendLockTeam(username, uEntity.getUsername(), remark);
                this.adminUserLogJob.logLockTeam(uEntity, request, username, remark);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/prohibit-team-withdraw"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_PROHIBIT_TEAM_WITHDRAW(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/prohibit-team-withdraw";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          if (StringUtils.isEmpty(username))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else
            {
              boolean updated = this.uService.prohibitTeamWithdraw(json, username);
              if (updated)
              {
                this.mailJob.sendProhibitTeamWithdraw(username, uEntity.getUsername());
                this.adminUserLogJob.prohibitTeamWithdraw(uEntity, request, username);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/allow-team-withdraw"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_ALLOW_TEAM_WITHDRAW(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/allow-team-withdraw";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          if (StringUtils.isEmpty(username))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else
            {
              boolean updated = this.uService.allowTeamWithdraw(json, username);
              if (updated)
              {
                this.mailJob.sendAllowTeamWithdraw(username, uEntity.getUsername());
                this.adminUserLogJob.allowTeamWithdraw(uEntity, request, username);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/allow-team-transfers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_ALLOW_TEAM_TRANSFERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/allow-team-transfers";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          if (StringUtils.isEmpty(username))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else
            {
              boolean updated = this.uService.allowTeamTransfers(json, username);
              if (updated)
              {
                this.mailJob.sendAllowTeamTransfers(username, uEntity.getUsername());
                this.adminUserLogJob.allowTeamTransfers(uEntity, request, username);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/prohibit-team-transfers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_PROHIBIT_TEAM_TRANSFERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/prohibit-team-transfers";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          if (StringUtils.isEmpty(username))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else
            {
              boolean updated = this.uService.prohibitTeamTransfers(json, username);
              if (updated)
              {
                this.mailJob.sendProhibitTeamTransfers(username, uEntity.getUsername());
                this.adminUserLogJob.prohibitTeamTransfers(uEntity, request, username);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/allow-team-platform-transfers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_ALLOW_TEAM_PLATFORM_TRANSFERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/allow-team-platform-transfers";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          if (StringUtils.isEmpty(username))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else
            {
              boolean updated = this.uService.allowTeamPlatformTransfers(json, username);
              if (updated)
              {
                this.mailJob.sendAllowTeamPlatformTransfers(username, uEntity.getUsername());
                this.adminUserLogJob.allowTeamPlatformTransfers(uEntity, request, username);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/prohibit-team-platform-transfers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_PROHIBIT_TEAM_PLATFORM_TRANSFERS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/prohibit-team-platform-transfers";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          if (StringUtils.isEmpty(username))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else
            {
              boolean updated = this.uService.prohibitTeamPlatformTransfers(json, username);
              if (updated)
              {
                this.mailJob.sendProhibitTeamPlatformTransfers(username, uEntity.getUsername());
                this.adminUserLogJob.prohibitTeamPlatformTransfers(uEntity, request, username);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/un-lock-team"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UN_LOCK_TEAM(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/un-lock-team";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        if (uEntity.getRoleId() != 1)
        {
          json.set(Integer.valueOf(2), "2-37");
        }
        else
        {
          String username = HttpUtil.getStringParameterTrim(request, "username");
          String remark = HttpUtil.getStringParameterTrim(request, "remark");
          if ((StringUtils.isEmpty(username)) || (StringUtils.isEmpty(remark)))
          {
            json.set(Integer.valueOf(2), "2-2");
          }
          else
          {
            User uBean = this.uDao.getByUsername(username);
            if (uBean == null)
            {
              json.set(Integer.valueOf(2), "2-32");
            }
            else if (uBean.getId() == 72)
            {
              json.set(Integer.valueOf(2), "2-33");
            }
            else if (this.uCodePointUtil.isLevel1Proxy(uBean))
            {
              json.set(Integer.valueOf(2), "2-36");
            }
            else
            {
              boolean updated = this.uService.unLockTeam(json, username);
              if (updated)
              {
                this.mailJob.sendUnLockTeam(username, uEntity.getUsername(), remark);
                this.adminUserLogJob.logUnLockTeam(uEntity, request, username, remark);
                json.set(Integer.valueOf(0), "0-5");
              }
              else if (json.getError() == null)
              {
                json.set(Integer.valueOf(1), "1-5");
              }
            }
          }
        }
      }
      else {
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
  
  @RequestMapping(value={"/lottery-user/user-transfer"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_TRANSFER(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/user-transfer";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        double money = HttpUtil.getDoubleParameter(request, "money").doubleValue();
        String aUser = HttpUtil.getStringParameterTrim(request, "aUser");
        String bUser = HttpUtil.getStringParameterTrim(request, "bUser");
        String remarks = HttpUtil.getStringParameterTrim(request, "remarks");
        if (StringUtils.equalsIgnoreCase(aUser, bUser))
        {
          json.set(Integer.valueOf(1), "1-5");
        }
        else if (money <= 0.0D)
        {
          json.set(Integer.valueOf(1), "1-5");
        }
        else
        {
          User aBean = this.uDao.getByUsername(aUser);
          User bBean = this.uDao.getByUsername(bUser);
          if ((aBean != null) && (bBean != null))
          {
            boolean flag = this.uService.transfer(json, aBean, bBean, money, remarks);
            if (flag)
            {
              this.adminUserLogJob.logUserTransfer(uEntity, request, aUser, bUser, money, remarks);
              this.adminUserCriticalLogJob.logUserTransfer(uEntity, request, aUser, bUser, money, remarks, actionKey);
              this.mailJob.sendUserTransfer(aUser, bUser, money, remarks);
              json.set(Integer.valueOf(0), "0-5");
            }
          }
          else
          {
            json.set(Integer.valueOf(2), "2-32");
          }
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
  
  @RequestMapping(value={"/lottery-user/withdraw-limit-list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_LIMIT_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/withdraw-limit-list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        Integer withdrawId = HttpUtil.getIntParameter(request, "withdrawId");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        UserVO user = this.dataFactory.getUser(username);
        String time;
        if (withdrawId != null)
        {
          UserWithdrawVO withdrawVO = this.uWithdrawService.getById(withdrawId.intValue());
          if (withdrawVO == null)
          {
            json.accumulate("totalCount", Integer.valueOf(0));
            json.accumulate("totalRemainConsumption", "0.00");
            json.accumulate("data", "[]");
            HttpUtil.write(response, json.toString(), "text/json");
            return;
          }
          time = withdrawVO.getBean().getTime();
        }
        else
        {
          time = new Moment().toSimpleTime();
        }
        Map<String, Object> map = this.userWithdrawLimitService.getUserWithdrawLimits(user.getId(), time);
        if (map != null)
        {
          List<UserWithdrawLimitVO> list = (List)map.get("list");
          int totalCount = list.size();
          
          List<UserWithdrawLimitVO> subList = new ArrayList();
          if (start < list.size())
          {
            for (int i = start; i < list.size(); i++) {
              if (subList.size() < limit) {
                subList.add((UserWithdrawLimitVO)list.get(i));
              }
            }
            list = subList;
          }
          json.accumulate("totalCount", Integer.valueOf(totalCount));
          json.accumulate("totalRemainConsumption", map.get("totalRemainConsumption"));
          json.accumulate("data", list);
        }
        else
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("totalRemainConsumption", "0.00");
          json.accumulate("data", "[]");
        }
        json.set(Integer.valueOf(0), "0-3");
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
  
  @RequestMapping(value={"/lottery-user/change-zhaoshang"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_CHANGE_ZHAO_SHANG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user/change-zhaoshang";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        int isCJZhaoShang = HttpUtil.getIntParameter(request, "isCJZhaoShang").intValue();
        boolean result = this.uService.changeZhaoShang(json, username, isCJZhaoShang);
        if (result)
        {
          this.adminUserLogJob.logChangeZhaoShang(uEntity, request, username, isCJZhaoShang);
          this.adminUserCriticalLogJob.logChangeZhaoShang(uEntity, request, username, isCJZhaoShang, actionKey);
          json.set(Integer.valueOf(0), "0-5");
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
