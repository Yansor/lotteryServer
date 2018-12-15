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
import lottery.domains.content.biz.UserGameDividendBillService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserGameDividendBill;
import lottery.domains.content.vo.user.UserGameDividendBillVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserGameDividendBillController
  extends AbstractActionController
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
  
  @RequestMapping(value={"/user-game-dividend-bill/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_GAME_DIVIDEND_BILL_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-game-dividend-bill/list";
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
        List<Integer> userIds = new ArrayList();
        boolean legalUser = true;
        if (StringUtils.isNotEmpty(username))
        {
          User user = this.userDao.getByUsername(username);
          if (user == null)
          {
            legalUser = false;
          }
          else
          {
            userIds.add(Integer.valueOf(user.getId()));
            List<User> userDirectLowers = this.userDao.getUserDirectLower(user.getId());
            for (User userDirectLower : userDirectLowers) {
              userIds.add(Integer.valueOf(userDirectLower.getId()));
            }
          }
        }
        double totalUserAmount = 0.0D;
        if (!legalUser)
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        else
        {
          PageList pList = this.uGameDividendBillService.search(userIds, sTime, eTime, minUserAmount, maxUserAmount, status, start, limit);
          if (pList != null)
          {
            totalUserAmount = this.uGameDividendBillService.sumUserAmount(userIds, sTime, eTime, minUserAmount, maxUserAmount, status);
            
            json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
            json.accumulate("data", pList.getList());
          }
          else
          {
            json.accumulate("totalCount", Integer.valueOf(0));
            json.accumulate("data", "[]");
          }
        }
        json.accumulate("totalUserAmount", Double.valueOf(totalUserAmount));
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
  
  @RequestMapping(value={"/user-game-dividend-bill/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_GAME_DIVIDEND_BILL_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-game-dividend-bill/get";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
        if (userDividendBill == null)
        {
          json.set(Integer.valueOf(2), "2-3001");
        }
        else
        {
          json.accumulate("data", new UserGameDividendBillVO(userDividendBill, this.dataFactory));
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
  
  @RequestMapping(value={"/user-game-dividend-bill/agree"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_GAME_DIVIDEND_BILL_AGREE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-game-dividend-bill/agree";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        double userAmount = HttpUtil.getDoubleParameter(request, "userAmount").doubleValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String remarks = request.getParameter("remarks");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
              if ((userDividendBill == null) || (userDividendBill.getStatus() != 2))
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uGameDividendBillService.agree(id, userAmount, remarks);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logAgreeGameDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, userAmount, remarks);
                  json.set(Integer.valueOf(0), "0-5");
                }
                else
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
  
  @RequestMapping(value={"/user-game-dividend-bill/deny"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_GAME_DIVIDEND_BILL_DENY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-game-dividend-bill/deny";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        double userAmount = HttpUtil.getDoubleParameter(request, "userAmount").doubleValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String remarks = request.getParameter("remarks");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
              if ((userDividendBill == null) || (userDividendBill.getStatus() != 2))
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uGameDividendBillService.deny(id, userAmount, remarks);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logDenyGameDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, userAmount, remarks);
                  json.set(Integer.valueOf(0), "0-5");
                }
                else
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
  
  @RequestMapping(value={"/user-game-dividend-bill/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void USER_GAME_DIVIDEND_BILL_DEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/user-game-dividend-bill/del";
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
              UserGameDividendBill userDividendBill = this.uGameDividendBillService.getById(id);
              if (userDividendBill == null)
              {
                json.set(Integer.valueOf(2), "2-3001");
              }
              else
              {
                boolean result = this.uGameDividendBillService.del(id);
                if (result)
                {
                  UserVO user = this.dataFactory.getUser(userDividendBill.getUserId());
                  this.adminUserLogJob.logDelGameDividend(uEntity, request, user == null ? "" : user.getUsername(), userDividendBill, remarks);
                  json.set(Integer.valueOf(0), "0-5");
                }
                else
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
