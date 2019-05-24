package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.List;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.PaymentBankService;
import lottery.domains.content.entity.PaymentBank;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentBankController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private PaymentBankService paymentBankService;
  
  @RequestMapping(value={"/lottery-payment-bank/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_BANK_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    List<PaymentBank> list = this.paymentBankService.listAll();
    JSONArray json = JSONArray.fromObject(list);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-payment-bank/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_BANK_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    int id = HttpUtil.getIntParameter(request, "id").intValue();
    PaymentBank bean = this.paymentBankService.getById(id);
    JSONObject json = JSONObject.fromObject(bean);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-payment-bank/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_BANK_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-bank/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String name = request.getParameter("name");
        String url = request.getParameter("url");
        boolean result = this.paymentBankService.update(id, name, url);
        if (result) {
          json.set(Integer.valueOf(0), "0-6");
        } else {
          json.set(Integer.valueOf(1), "1-6");
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
