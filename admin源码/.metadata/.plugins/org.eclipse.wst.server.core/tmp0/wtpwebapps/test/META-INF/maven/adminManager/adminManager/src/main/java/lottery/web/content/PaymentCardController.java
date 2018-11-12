package lottery.web.content;

import javautils.encrypt.PasswordUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Iterator;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import lottery.domains.content.entity.PaymentCard;
import lottery.domains.content.vo.payment.PaymentCardVO;
import java.util.ArrayList;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.PaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class PaymentCardController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private PaymentCardService paymentCardService;
    
    @RequestMapping(value = { "/lottery-payment-card/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<PaymentCardVO> list = new ArrayList<PaymentCardVO>();
                final List<PaymentCard> clist = this.paymentCardService.listAll();
                for (final PaymentCard tmpBean : clist) {
                    list.add(new PaymentCardVO(tmpBean, super.getLotteryDataFactory()));
                }
                json.set(0, "0-3");
                json.accumulate("data", list);
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
    
    @RequestMapping(value = { "/lottery-payment-card/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final PaymentCard bean = this.paymentCardService.getById(id);
                            json.accumulate("data", bean);
                            json.set(0, "0-3");
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-payment-card/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (this.isUnlockedWithdrawPwd(session)) {
                        if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                            final int bankId = HttpUtil.getIntParameter(request, "bankId");
                            final String branchName = request.getParameter("branchName");
                            final String cardName = request.getParameter("cardName");
                            final String cardId = request.getParameter("cardId");
                            final double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits");
                            final double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge");
                            final double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge");
                            final String startTime = request.getParameter("sTime");
                            final String endTime = request.getParameter("eTime");
                            final double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge");
                            final double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge");
                            final boolean result = this.paymentCardService.add(bankId, branchName, cardName, cardId, totalCredits, minTotalRecharge, maxTotalRecharge, startTime, endTime, minUnitRecharge, maxUnitRecharge);
                            if (result) {
                                json.set(0, "0-6");
                            }
                            else {
                                json.set(1, "1-6");
                            }
                        }
                        else {
                            json.set(2, "2-41");
                        }
                    }
                    else {
                        json.set(2, "2-43");
                    }
                }
                else {
                    json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/lottery-payment-card/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final int id = HttpUtil.getIntParameter(request, "id");
                            final int bankId = HttpUtil.getIntParameter(request, "bankId");
                            final String branchName = request.getParameter("branchName");
                            final String cardName = request.getParameter("cardName");
                            final String cardId = request.getParameter("cardId");
                            final double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits");
                            final double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge");
                            final double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge");
                            final String startTime = request.getParameter("sTime");
                            final String endTime = request.getParameter("eTime");
                            final double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge");
                            final double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge");
                            final boolean result = this.paymentCardService.edit(id, bankId, branchName, cardName, cardId, totalCredits, minTotalRecharge, maxTotalRecharge, startTime, endTime, minUnitRecharge, maxUnitRecharge);
                            if (result) {
                                json.set(0, "0-6");
                            }
                            else {
                                json.set(1, "1-6");
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/lottery-payment-card/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final int id = HttpUtil.getIntParameter(request, "id");
                            final int status = HttpUtil.getIntParameter(request, "status");
                            final boolean result = this.paymentCardService.updateStatus(id, status);
                            if (result) {
                                json.set(0, "0-5");
                            }
                            else {
                                json.set(1, "1-5");
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/lottery-payment-card/reset-credits" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_RESET_CREDITS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/reset-credits";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final int id = HttpUtil.getIntParameter(request, "id");
                            final boolean result = this.paymentCardService.resetCredits(id);
                            if (result) {
                                json.set(0, "0-5");
                            }
                            else {
                                json.set(1, "1-5");
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
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
    
    @RequestMapping(value = { "/lottery-payment-card/delete" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CARD_DELETE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-card/delete";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String withdrawPwd = request.getParameter("withdrawPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, withdrawPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            final int id = HttpUtil.getIntParameter(request, "id");
                            final boolean result = this.paymentCardService.delete(id);
                            if (result) {
                                json.set(0, "0-5");
                            }
                            else {
                                json.set(1, "1-5");
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
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
