package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.List;
import javautils.encrypt.PasswordUtil;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserDividendBillService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDividendBill;
import lottery.domains.content.vo.user.UserDividendBillVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserDividendBillController
  extends AbstractActionController
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
  
  @RequestMapping(value={"/lottery-user-dividend-bill/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/list";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Double minUserAmount = HttpUtil.getDoubleParameter(request, "minUserAmount");
        Double maxUserAmount = HttpUtil.getDoubleParameter(request, "maxUserAmount");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer issueType = HttpUtil.getIntParameter(request, "issueType");
        List<Integer> userIds = new ArrayList();
        boolean legalUser = true;
        if (StringUtils.isNotEmpty(username))
        {
          User user = this.userService.getByUsername(username);
          if (user == null)
          {
            legalUser = false;
          }
          else
          {
            userIds.add(Integer.valueOf(user.getId()));
            List<User> userDirectLowers = this.userService.getUserDirectLower(user.getId());
            for (User userDirectLower : userDirectLowers) {
              userIds.add(Integer.valueOf(userDirectLower.getId()));
            }
          }
        }
        double platformTotalLoss = 0.0D;
        double platformTotalUserAmount = 0.0D;
        double upperTotalUserAmount = 0.0D;
        if (!legalUser)
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        else
        {
          PageList pList = this.uDividendBillService.search(userIds, sTime, eTime, minUserAmount, maxUserAmount, status, issueType, start, limit);
          double[] userAmounts = this.uDividendBillService.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount);
          platformTotalLoss = userAmounts[0];
          platformTotalUserAmount = userAmounts[1];
          upperTotalUserAmount = userAmounts[2];
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
        }
        json.accumulate("platformTotalLoss", Double.valueOf(platformTotalLoss));
        json.accumulate("platformTotalUserAmount", Double.valueOf(platformTotalUserAmount));
        json.accumulate("upperTotalUserAmount", Double.valueOf(upperTotalUserAmount));
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-dividend-bill/platform-loss-list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_PLATFORN_LOSS_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/platform-loss-list";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Double minUserAmount = HttpUtil.getDoubleParameter(request, "minUserAmount");
        Double maxUserAmount = HttpUtil.getDoubleParameter(request, "maxUserAmount");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        List<Integer> userIds = new ArrayList();
        boolean legalUser = true;
        if (StringUtils.isNotEmpty(username))
        {
          User user = this.userService.getByUsername(username);
          if (user == null)
          {
            legalUser = false;
          }
          else
          {
            userIds.add(Integer.valueOf(user.getId()));
            List<User> userDirectLowers = this.userService.getUserDirectLower(user.getId());
            for (User userDirectLower : userDirectLowers) {
              userIds.add(Integer.valueOf(userDirectLower.getId()));
            }
          }
        }
        if (!legalUser)
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        else
        {
          PageList pList = this.uDividendBillService.searchPlatformLoss(userIds, sTime, eTime, minUserAmount, maxUserAmount, start, limit);
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-dividend-bill/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/get";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
        if (userDividendBill == null)
        {
          json.set(Integer.valueOf(2), "2-3001");
        }
        else
        {
          json.accumulate("data", new UserDividendBillVO(userDividendBill, this.dataFactory));
          json.set(Integer.valueOf(0), "0-3");
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-dividend-bill/agree"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_AGREE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/agree";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String remarks = request.getParameter("remarks");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
              if ((userDividendBill == null) || (userDividendBill.getStatus() != 2))
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uDividendBillService.agree(json, id, remarks);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logAgreeDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, remarks);
                  json.set(Integer.valueOf(0), "0-5");
                }
              }
            }
            else
            {
              json.set(Integer.valueOf(2), "2-43");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-41");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-12");
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
  
  @RequestMapping(value={"/lottery-user-dividend-bill/deny"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_DENY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/deny";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String remarks = request.getParameter("remarks");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
              if ((userDividendBill == null) || (userDividendBill.getStatus() != 2))
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uDividendBillService.deny(json, id, remarks);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logDenyDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, remarks);
                  json.set(Integer.valueOf(0), "0-5");
                }
              }
            }
            else
            {
              json.set(Integer.valueOf(2), "2-43");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-41");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-12");
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
  
  @RequestMapping(value={"/lottery-user-dividend-bill/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_DEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/del";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String remarks = request.getParameter("remarks");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
              if (userDividendBill == null)
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uDividendBillService.del(json, id);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logDelDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, remarks);
                  json.set(Integer.valueOf(0), "0-5");
                }
              }
            }
            else
            {
              json.set(Integer.valueOf(2), "2-43");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-41");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-12");
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
  
  @RequestMapping(value={"/lottery-user-dividend-bill/reset"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DIVIDEND_BILL_RESET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-dividend-bill/reset";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String remarks = request.getParameter("remarks");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              UserDividendBill userDividendBill = this.uDividendBillService.getById(id);
              if (userDividendBill == null)
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uDividendBillService.reset(json, id, remarks);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logResetDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, remarks);
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
              json.set(Integer.valueOf(2), "2-43");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-41");
          }
        }
        else {
          json.set(Integer.valueOf(2), "2-12");
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
