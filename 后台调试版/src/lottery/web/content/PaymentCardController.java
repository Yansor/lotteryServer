package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.List;
import javautils.encrypt.PasswordUtil;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.PaymentCardService;
import lottery.domains.content.entity.PaymentCard;
import lottery.domains.content.vo.payment.PaymentCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentCardController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private PaymentCardService paymentCardService;
  
  @RequestMapping(value={"/lottery-payment-card/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        List<PaymentCardVO> list = new ArrayList();
        List<PaymentCard> clist = this.paymentCardService.listAll();
        for (PaymentCard tmpBean : clist) {
          list.add(new PaymentCardVO(tmpBean, super.getLotteryDataFactory()));
        }
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
  
  @RequestMapping(value={"/lottery-payment-card/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              PaymentCard bean = this.paymentCardService.getById(id);
              json.accumulate("data", bean);
              json.set(Integer.valueOf(0), "0-3");
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-payment-card/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (isUnlockedWithdrawPwd(session))
          {
            if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
            {
              int bankId = HttpUtil.getIntParameter(request, "bankId").intValue();
              String branchName = request.getParameter("branchName");
              String cardName = request.getParameter("cardName");
              String cardId = request.getParameter("cardId");
              double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits").doubleValue();
              double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge").doubleValue();
              double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge").doubleValue();
              String startTime = request.getParameter("sTime");
              String endTime = request.getParameter("eTime");
              double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge").doubleValue();
              double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge").doubleValue();
              boolean result = this.paymentCardService.add(bankId, branchName, cardName, cardId, totalCredits, minTotalRecharge, maxTotalRecharge, startTime, endTime, minUnitRecharge, maxUnitRecharge);
              if (result) {
                json.set(Integer.valueOf(0), "0-6");
              } else {
                json.set(Integer.valueOf(1), "1-6");
              }
            }
            else
            {
              json.set(Integer.valueOf(2), "2-41");
            }
          }
          else {
            json.set(Integer.valueOf(2), "2-43");
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
  
  @RequestMapping(value={"/lottery-payment-card/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              int id = HttpUtil.getIntParameter(request, "id").intValue();
              int bankId = HttpUtil.getIntParameter(request, "bankId").intValue();
              String branchName = request.getParameter("branchName");
              String cardName = request.getParameter("cardName");
              String cardId = request.getParameter("cardId");
              double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits").doubleValue();
              double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge").doubleValue();
              double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge").doubleValue();
              String startTime = request.getParameter("sTime");
              String endTime = request.getParameter("eTime");
              double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge").doubleValue();
              double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge").doubleValue();
              boolean result = this.paymentCardService.edit(id, bankId, branchName, cardName, cardId, totalCredits, minTotalRecharge, maxTotalRecharge, startTime, endTime, minUnitRecharge, maxUnitRecharge);
              if (result) {
                json.set(Integer.valueOf(0), "0-6");
              } else {
                json.set(Integer.valueOf(1), "1-6");
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
  
  @RequestMapping(value={"/lottery-payment-card/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              int id = HttpUtil.getIntParameter(request, "id").intValue();
              int status = HttpUtil.getIntParameter(request, "status").intValue();
              boolean result = this.paymentCardService.updateStatus(id, status);
              if (result) {
                json.set(Integer.valueOf(0), "0-5");
              } else {
                json.set(Integer.valueOf(1), "1-5");
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
  
  @RequestMapping(value={"/lottery-payment-card/reset-credits"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_RESET_CREDITS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/reset-credits";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              int id = HttpUtil.getIntParameter(request, "id").intValue();
              boolean result = this.paymentCardService.resetCredits(id);
              if (result) {
                json.set(Integer.valueOf(0), "0-5");
              } else {
                json.set(Integer.valueOf(1), "1-5");
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
  
  @RequestMapping(value={"/lottery-payment-card/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CARD_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-card/delete";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String withdrawPwd = request.getParameter("withdrawPwd");
        String token = getDisposableToken(session, request);
        if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd))
        {
          if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd()))
          {
            if (isUnlockedWithdrawPwd(session))
            {
              int id = HttpUtil.getIntParameter(request, "id").intValue();
              boolean result = this.paymentCardService.delete(id);
              if (result) {
                json.set(Integer.valueOf(0), "0-5");
              } else {
                json.set(Integer.valueOf(1), "1-5");
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
