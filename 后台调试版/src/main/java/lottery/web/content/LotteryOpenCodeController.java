package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.encrypt.PasswordUtil;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryOpenCodeService;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.lottery.LotteryOpenCodeVO;
import lottery.web.content.validate.CodeValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LotteryOpenCodeController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private LotteryOpenCodeService lotteryOpenCodeService;
  @Autowired
  private CodeValidate codeValidate;
  
  @RequestMapping(value={"/lottery-open-code/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_OPEN_CODE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-open-code/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String lottery = request.getParameter("lottery");
        String expect = request.getParameter("expect");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.lotteryOpenCodeService.search(lottery, expect, start, limit);
        json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
        json.accumulate("data", pList.getList());
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
  
  @RequestMapping(value={"/lottery-open-code/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_OPEN_CODE_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-open-code/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String lottery = request.getParameter("lottery");
      String expect = request.getParameter("expect");
      LotteryOpenCodeVO entity = this.lotteryOpenCodeService.get(lottery, expect);
      json.accumulate("data", entity);
      json.set(Integer.valueOf(0), "0-3");
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
  
  @RequestMapping(value={"/lottery-open-code/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_OPEN_CODE_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-open-code/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      String lottery = request.getParameter("lottery");
      String expect = request.getParameter("expect");
      LotteryOpenCodeVO entity = this.lotteryOpenCodeService.get(lottery, expect);
      LotteryOpenCode bean = entity.getBean();
      this.lotteryOpenCodeService.delete(bean);
      json.set(Integer.valueOf(0), "0-3");
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
  
  @RequestMapping(value={"/lottery-open-code/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_OPEN_CODE_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-open-status/manual-control";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String lottery = request.getParameter("lottery");
        String expect = request.getParameter("expect");
        String code = request.getParameter("code");
        if (this.codeValidate.validateCode(json, lottery, code)) {
          if (this.codeValidate.validateExpect(json, lottery, expect))
          {
            boolean result = this.lotteryOpenCodeService.add(json, lottery, expect, code, uEntity.getUsername());
            if (result) {
              json.set(Integer.valueOf(0), "0-5");
            }
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-open-code/correct"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_OPEN_CODE_CORRECT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-open-status/manual-control";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String lottery = request.getParameter("lottery");
        String expect = request.getParameter("expect");
        String code = request.getParameter("code");
        String moneyPwd = request.getParameter("moneyPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, moneyPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              if (this.codeValidate.validateCode(json, lottery, code))
              {
                boolean result = this.lotteryOpenCodeService.add(json, lottery, expect, code, "手动修正号码");
                if (result) {
                  json.set(Integer.valueOf(0), "0-5");
                }
              }
            }
            else {
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
