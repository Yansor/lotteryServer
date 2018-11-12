package lottery.web.content;

import org.apache.commons.lang.StringUtils;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.vo.payment.PaymentChannelSimpleVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.biz.PaymentChannelService;
import admin.domains.jobs.AdminUserLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class PaymentChannelController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private PaymentChannelService paymentChannelService;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @RequestMapping(value = { "/lottery-payment-channel/simple-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_SIMPLE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final List<PaymentChannelSimpleVO> list = this.dataFactory.listPaymentChannelVOsSimple();
            json.set(0, "0-3");
            json.accumulate("data", list);
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-payment-channel/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<PaymentChannelVO> list = this.paymentChannelService.listAllVOs();
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
    
    @RequestMapping(value = { "/lottery-payment-channel/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int id = HttpUtil.getIntParameter(request, "id");
            final PaymentChannelVO bean = this.paymentChannelService.getVOById(id);
            json.accumulate("data", bean);
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-payment-channel/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String name = HttpUtil.getStringParameterTrim(request, "name");
                final String mobileName = HttpUtil.getStringParameterTrim(request, "mobileName");
                final String frontName = HttpUtil.getStringParameterTrim(request, "frontName");
                final String channelCode = HttpUtil.getStringParameterTrim(request, "channelCode");
                String merCode = HttpUtil.getStringParameterTrim(request, "merCode");
                final double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits");
                final double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge");
                final double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge");
                final double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge");
                final double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge");
                String maxRegisterTime = HttpUtil.getStringParameterTrim(request, "maxRegisterTime");
                final String qrCodeContent = HttpUtil.getStringParameterTrim(request, "qrCodeContent");
                final int fixedQRAmount = HttpUtil.getIntParameter(request, "fixedQRAmount");
                final int type = HttpUtil.getIntParameter(request, "type");
                final int subType = HttpUtil.getIntParameter(request, "subType");
                final double consumptionPercent = HttpUtil.getDoubleParameter(request, "consumptionPercent");
                final String whiteUsernames = HttpUtil.getStringParameterTrim(request, "whiteUsernames");
                final String startTime = HttpUtil.getStringParameterTrim(request, "startTime");
                final String endTime = HttpUtil.getStringParameterTrim(request, "endTime");
                final String fixedAmountQrs = HttpUtil.getStringParameterTrim(request, "fixedAmountQrs");
                if (StringUtils.isNotEmpty(maxRegisterTime)) {
                    maxRegisterTime = String.valueOf(maxRegisterTime) + " 23:59:59";
                }
                if (StringUtils.isEmpty(merCode)) {
                    merCode = "123456";
                }
                final boolean result = this.paymentChannelService.add(name, mobileName, frontName, channelCode, merCode, totalCredits, minTotalRecharge, maxTotalRecharge, minUnitRecharge, maxUnitRecharge, maxRegisterTime, qrCodeContent, fixedQRAmount, type, subType, consumptionPercent, whiteUsernames, startTime, endTime, fixedAmountQrs, 1);
                if (result) {
                    this.adminUserLogJob.logAddPaymenChannel(uEntity, request, name);
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
    
    @RequestMapping(value = { "/lottery-payment-channel/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String name = HttpUtil.getStringParameterTrim(request, "name");
                final String mobileName = HttpUtil.getStringParameterTrim(request, "mobileName");
                final String frontName = HttpUtil.getStringParameterTrim(request, "frontName");
                final double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits");
                final double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge");
                final double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge");
                final double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge");
                final double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge");
                String maxRegisterTime = HttpUtil.getStringParameterTrim(request, "maxRegisterTime");
                final String qrCodeContent = HttpUtil.getStringParameterTrim(request, "qrCodeContent");
                Integer fixedQRAmount = HttpUtil.getIntParameter(request, "fixedQRAmount");
                final double consumptionPercent = HttpUtil.getDoubleParameter(request, "consumptionPercent");
                final String whiteUsernames = HttpUtil.getStringParameterTrim(request, "whiteUsernames");
                final String startTime = HttpUtil.getStringParameterTrim(request, "startTime");
                final String endTime = HttpUtil.getStringParameterTrim(request, "endTime");
                final String fixedAmountQrs = HttpUtil.getStringParameterTrim(request, "fixedAmountQrs");
                if (fixedQRAmount == null) {
                    fixedQRAmount = 0;
                }
                if (StringUtils.isNotEmpty(maxRegisterTime)) {
                    maxRegisterTime = String.valueOf(maxRegisterTime) + " 23:59:59";
                }
                final boolean result = this.paymentChannelService.edit(id, name, mobileName, frontName, totalCredits, minTotalRecharge, maxTotalRecharge, minUnitRecharge, maxUnitRecharge, maxRegisterTime, qrCodeContent, fixedQRAmount, consumptionPercent, whiteUsernames, startTime, endTime, fixedAmountQrs);
                if (result) {
                    this.adminUserLogJob.logEditPaymenChannel(uEntity, request, name);
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
    
    @RequestMapping(value = { "/lottery-payment-channel/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.paymentChannelService.updateStatus(id, status);
                if (result) {
                    this.adminUserLogJob.logEditPaymenChannelStatus(uEntity, request, id, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-payment-channel/reset-credits" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_RESET_CREDITS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/reset-credits";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.paymentChannelService.resetCredits(id);
                if (result) {
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-payment-channel/delete" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_DELETE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/delete";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.paymentChannelService.delete(id);
                if (result) {
                    this.adminUserLogJob.logDeletePaymenChannel(uEntity, request, id);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-payment-channel/move-up" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_MOVEUP(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/move-up";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.paymentChannelService.moveUp(id);
                if (result) {
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-payment-channel/move-down" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PAYMENT_CHANNEL_MOVEDOWN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-payment-channel/move-down";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.paymentChannelService.moveDown(id);
                if (result) {
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
