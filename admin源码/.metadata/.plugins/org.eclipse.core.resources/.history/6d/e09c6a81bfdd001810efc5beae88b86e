package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.entity.PaymentBank;
import java.util.List;
import javautils.http.HttpUtil;
import net.sf.json.JSONArray;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.PaymentBankService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class PaymentBankController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private PaymentBankService paymentBankService;
    
    @RequestMapping(value = { "/lottery-payment-bank/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_BANK_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final List<PaymentBank> list = this.paymentBankService.listAll();
        final JSONArray json = JSONArray.fromObject((Object)list);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-payment-bank/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_BANK_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final int id = HttpUtil.getIntParameter(request, "id");
        final PaymentBank bean = this.paymentBankService.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-payment-bank/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_BANK_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-bank/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String name = request.getParameter("name");
                final String url = request.getParameter("url");
                final boolean result = this.paymentBankService.update(id, name, url);
                if (result) {
                    json.set(0, "0-6");
                }
                else {
                    json.set(1, "1-6");
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
