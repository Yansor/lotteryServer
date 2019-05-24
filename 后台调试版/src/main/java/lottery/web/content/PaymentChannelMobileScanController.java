package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.List;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.PaymentChannelQrCodeService;
import lottery.domains.content.biz.PaymentChannelService;
import lottery.domains.content.entity.PaymentChannelQrCode;
import lottery.domains.content.vo.payment.PaymentChannelVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentChannelMobileScanController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private PaymentChannelService paymentChannelService;
  @Autowired
  private PaymentChannelQrCodeService paymentChannelQrCodeService;
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_MOBILESCAN_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-channel-mobilescan/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        List<PaymentChannelVO> list = this.paymentChannelService.listAllMobileScanVOs();
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
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_MOBILESCAN_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      int id = HttpUtil.getIntParameter(request, "id").intValue();
      PaymentChannelVO bean = this.paymentChannelService.getVOById(id);
      if (bean.getFixedQRAmount() == 1)
      {
        List<PaymentChannelQrCode> qrList = this.paymentChannelQrCodeService.getByChannelId(bean.getId());
        json.accumulate("qrList", qrList);
      }
      else
      {
        json.accumulate("qrList", new ArrayList());
      }
      json.accumulate("data", bean);
      json.set(Integer.valueOf(0), "0-3");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_MOBILESCAN_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-channel-mobilescan/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String name = HttpUtil.getStringParameterTrim(request, "name");
        String mobileName = HttpUtil.getStringParameterTrim(request, "mobileName");
        String frontName = HttpUtil.getStringParameterTrim(request, "frontName");
        String channelCode = HttpUtil.getStringParameterTrim(request, "channelCode");
        String merCode = HttpUtil.getStringParameterTrim(request, "merCode");
        double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits").doubleValue();
        double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge").doubleValue();
        double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge").doubleValue();
        double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge").doubleValue();
        double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge").doubleValue();
        String maxRegisterTime = HttpUtil.getStringParameterTrim(request, "maxRegisterTime");
        String qrCodeContent = HttpUtil.getStringParameterTrim(request, "qrCodeContent");
        int fixedQRAmount = HttpUtil.getIntParameter(request, "fixedQRAmount").intValue();
        int type = HttpUtil.getIntParameter(request, "type").intValue();
        int subType = HttpUtil.getIntParameter(request, "subType").intValue();
        double consumptionPercent = HttpUtil.getDoubleParameter(request, "consumptionPercent").doubleValue();
        String whiteUsernames = HttpUtil.getStringParameterTrim(request, "whiteUsernames");
        String startTime = HttpUtil.getStringParameterTrim(request, "startTime");
        String endTime = HttpUtil.getStringParameterTrim(request, "endTime");
        String fixedAmountQrs = HttpUtil.getStringParameterTrim(request, "fixedAmountQrs");
        if (StringUtils.isNotEmpty(maxRegisterTime)) {
          maxRegisterTime = maxRegisterTime + " 23:59:59";
        }
        if (StringUtils.isEmpty(merCode)) {
          merCode = "123456";
        }
        boolean result = this.paymentChannelService.add(name, mobileName, frontName, channelCode, merCode, totalCredits, minTotalRecharge, maxTotalRecharge, minUnitRecharge, maxUnitRecharge, maxRegisterTime, qrCodeContent, fixedQRAmount, type, subType, consumptionPercent, whiteUsernames, startTime, endTime, fixedAmountQrs, 2);
        if (result)
        {
          this.adminUserLogJob.logAddPaymenChannel(uEntity, request, name);
          json.set(Integer.valueOf(0), "0-6");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_MOBILESCAN_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-channel-mobilescan/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String name = HttpUtil.getStringParameterTrim(request, "name");
        String mobileName = HttpUtil.getStringParameterTrim(request, "mobileName");
        String frontName = HttpUtil.getStringParameterTrim(request, "frontName");
        double totalCredits = HttpUtil.getDoubleParameter(request, "totalCredits").doubleValue();
        double minTotalRecharge = HttpUtil.getDoubleParameter(request, "minTotalRecharge").doubleValue();
        double maxTotalRecharge = HttpUtil.getDoubleParameter(request, "maxTotalRecharge").doubleValue();
        double minUnitRecharge = HttpUtil.getDoubleParameter(request, "minUnitRecharge").doubleValue();
        double maxUnitRecharge = HttpUtil.getDoubleParameter(request, "maxUnitRecharge").doubleValue();
        String maxRegisterTime = HttpUtil.getStringParameterTrim(request, "maxRegisterTime");
        String qrCodeContent = HttpUtil.getStringParameterTrim(request, "qrCodeContent");
        int fixedQRAmount = HttpUtil.getIntParameter(request, "fixedQRAmount").intValue();
        double consumptionPercent = HttpUtil.getDoubleParameter(request, "consumptionPercent").doubleValue();
        String whiteUsernames = HttpUtil.getStringParameterTrim(request, "whiteUsernames");
        String startTime = HttpUtil.getStringParameterTrim(request, "startTime");
        String endTime = HttpUtil.getStringParameterTrim(request, "endTime");
        String fixedAmountQrs = HttpUtil.getStringParameterTrim(request, "fixedAmountQrs");
        if (StringUtils.isNotEmpty(maxRegisterTime)) {
          maxRegisterTime = maxRegisterTime + " 23:59:59";
        }
        boolean result = this.paymentChannelService.edit(id, name, mobileName, frontName, totalCredits, minTotalRecharge, maxTotalRecharge, minUnitRecharge, maxUnitRecharge, maxRegisterTime, qrCodeContent, fixedQRAmount, consumptionPercent, whiteUsernames, startTime, endTime, fixedAmountQrs);
        if (result)
        {
          this.adminUserLogJob.logEditPaymenChannel(uEntity, request, name);
          json.set(Integer.valueOf(0), "0-6");
        }
        else
        {
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
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-channel-mobilescan/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.paymentChannelService.updateStatus(id, status);
        if (result)
        {
          this.adminUserLogJob.logEditPaymenChannelStatus(uEntity, request, id, status);
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
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-channel-mobilescan/delete";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean result = this.paymentChannelService.delete(id);
        if (result)
        {
          this.adminUserLogJob.logDeletePaymenChannel(uEntity, request, id);
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
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan-qr-code/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_PAYMENT_CHANNEL_MOBILESCAN_QR_CODE_DELETE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-payment-channel-mobilescan-qr-code/delete";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean result = this.paymentChannelQrCodeService.delete(id);
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
}
