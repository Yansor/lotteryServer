package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.biz.UserRechargeService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.entity.HistoryUserWithdraw;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.vo.bill.UserBillVO;
import lottery.domains.content.vo.user.HistoryUserWithdrawVO;
import lottery.domains.content.vo.user.UserBetsVO;
import lottery.domains.content.vo.user.UserCardVO;
import lottery.domains.content.vo.user.UserProfileVO;
import lottery.domains.content.vo.user.UserRechargeVO;
import lottery.domains.content.vo.user.UserWithdrawVO;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserWithdrawController
  extends AbstractActionController
{
  private static ConcurrentHashMap<Integer, Boolean> API_PAY_ORDER_CACHE = new ConcurrentHashMap();
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private UserService uService;
  @Autowired
  private UserBetsService uBetsService;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserCardService uCardService;
  @Autowired
  private UserRechargeService uRechargeService;
  @Autowired
  private UserWithdrawService uWithdrawService;
  @Autowired
  private UserWithdrawLimitService userWithdrawLimitService;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  @RequestMapping(value={"/lottery-user-withdraw/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String billno = request.getParameter("billno");
        String username = request.getParameter("username");
        String minTime = request.getParameter("minTime");
        String maxTime = request.getParameter("maxTime");
        String minOperatorTime = request.getParameter("minOperatorTime");
        String maxOperatorTime = request.getParameter("maxOperatorTime");
        Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
        String keyword = request.getParameter("keyword");
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer type = HttpUtil.getIntParameter(request, "type");
        Integer checkStatus = HttpUtil.getIntParameter(request, "checkStatus");
        Integer remitStatus = HttpUtil.getIntParameter(request, "remitStatus");
        Integer paymentChannelId = HttpUtil.getIntParameter(request, "paymentChannelId");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uWithdrawService.search(type, billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId, start, limit);
        if (pList != null)
        {
          double[] totalWithdraw = this.uWithdrawService.getTotalWithdraw(billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
          json.accumulate("totalRecMoney", Double.valueOf(totalWithdraw[0]));
          json.accumulate("totalFeeMoney", Double.valueOf(totalWithdraw[1]));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalRecMoney", Integer.valueOf(0));
          json.accumulate("totalFeeMoney", Integer.valueOf(0));
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
  
  @RequestMapping(value={"/history-lottery-user-withdraw/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_WITHDRAW_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-withdraw/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String billno = request.getParameter("billno");
        String username = request.getParameter("username");
        String minTime = request.getParameter("minTime");
        String maxTime = request.getParameter("maxTime");
        String minOperatorTime = request.getParameter("minOperatorTime");
        String maxOperatorTime = request.getParameter("maxOperatorTime");
        Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
        String keyword = request.getParameter("keyword");
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer checkStatus = HttpUtil.getIntParameter(request, "checkStatus");
        Integer remitStatus = HttpUtil.getIntParameter(request, "remitStatus");
        Integer paymentChannelId = HttpUtil.getIntParameter(request, "paymentChannelId");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uWithdrawService.searchHistory(billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId, start, limit);
        if (pList != null)
        {
          double[] totalWithdraw = this.uWithdrawService.getHistoryTotalWithdraw(billno, username, minTime, maxTime, minOperatorTime, maxOperatorTime, minMoney, maxMoney, keyword, status, checkStatus, remitStatus, paymentChannelId);
          json.accumulate("totalRecMoney", Double.valueOf(totalWithdraw[0]));
          json.accumulate("totalFeeMoney", Double.valueOf(totalWithdraw[1]));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("totalRecMoney", Integer.valueOf(0));
          json.accumulate("totalFeeMoney", Integer.valueOf(0));
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
  
  @RequestMapping(value={"/lottery-user-withdraw/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserWithdrawVO result = this.uWithdrawService.getById(id);
        json.accumulate("data", result);
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
  
  @RequestMapping(value={"/history-lottery-user-withdraw/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_WITHDRAW_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-withdraw/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        HistoryUserWithdrawVO result = this.uWithdrawService.getHistoryById(id);
        json.accumulate("data", result);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/pay-get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_PAY_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/pay-get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserWithdrawVO result = this.uWithdrawService.getById(id);
        json.accumulate("data", result);
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
  
  @RequestMapping(value={"/history-lottery-user-withdraw/pay-get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_WITHDRAW_PAY_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-withdraw/pay-get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        HistoryUserWithdrawVO result = this.uWithdrawService.getHistoryById(id);
        json.accumulate("data", result);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/check"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_CHECK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/check";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserWithdrawVO wBean = this.uWithdrawService.getById(id);
        if (wBean != null)
        {
          int userId = wBean.getBean().getUserId();
          UserProfileVO uBean = this.uService.getUserProfile(wBean.getUsername());
          List<UserBillVO> uBillList = this.uBillService.getLatest(userId, 5, 20);
          List<UserCardVO> uCardList = this.uCardService.getByUserId(userId);
          List<UserRechargeVO> uRechargeList = this.uRechargeService.getLatest(userId, 1, 10);
          List<UserWithdrawVO> uWithdrawList = this.uWithdrawService.getLatest(userId, 1, 10);
          List<UserBetsVO> uOrderList = this.uBetsService.getSuspiciousOrder(userId, 10);
          json.accumulate("wBean", wBean);
          json.accumulate("uBean", uBean);
          json.accumulate("uBillList", uBillList);
          json.accumulate("uCardList", uCardList);
          json.accumulate("uRechargeList", uRechargeList);
          json.accumulate("uWithdrawList", uWithdrawList);
          json.accumulate("uOrderList", uOrderList);
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
  
  @RequestMapping(value={"/history-lottery-user-withdraw/check"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_WITHDRAW_CHECK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-withdraw/check";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        HistoryUserWithdrawVO wBean = this.uWithdrawService.getHistoryById(id);
        if (wBean != null)
        {
          int userId = wBean.getBean().getUserId();
          UserProfileVO uBean = this.uService.getUserProfile(wBean.getUsername());
          List<UserBillVO> uBillList = this.uBillService.getLatest(userId, 5, 20);
          List<UserCardVO> uCardList = this.uCardService.getByUserId(userId);
          List<UserRechargeVO> uRechargeList = this.uRechargeService.getLatest(userId, 1, 10);
          List<UserWithdrawVO> uWithdrawList = this.uWithdrawService.getLatest(userId, 1, 10);
          List<UserBetsVO> uOrderList = this.uBetsService.getSuspiciousOrder(userId, 10);
          
          json.accumulate("wBean", wBean);
          json.accumulate("uBean", uBean);
          json.accumulate("uBillList", uBillList);
          json.accumulate("uCardList", uCardList);
          json.accumulate("uRechargeList", uRechargeList);
          json.accumulate("uWithdrawList", uWithdrawList);
          json.accumulate("uOrderList", uOrderList);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/check-result"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_CHECK_RESULT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/check-result";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        if (status == 1)
        {
          boolean result = this.uWithdrawService.check(uEntity, json, id, status);
          if (result)
          {
            this.adminUserLogJob.logCheckWithdraw(uEntity, request, id, status);
            json.set(Integer.valueOf(0), "0-5");
          }
        }
        else
        {
          String remarks = request.getParameter("remarks");
          boolean result = this.uWithdrawService.reviewedFail(uEntity, json, id, remarks, uEntity.getUsername());
          if (result)
          {
            this.adminUserLogJob.reviewedFail(uEntity, request, id, remarks);
            json.set(Integer.valueOf(0), "0-5");
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
  
  @RequestMapping(value={"/lottery-user-withdraw/manual-pay"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_MANUAL_PAY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/manual-pay";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String payBillno = request.getParameter("payBillno");
        String remarks = request.getParameter("remarks");
        boolean result = this.uWithdrawService.manualPay(uEntity, json, id, payBillno, remarks, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logManualPay(uEntity, request, id, payBillno, remarks);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/refuse"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_REFUSE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/refuse";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String reason = request.getParameter("reason");
        String remarks = request.getParameter("remarks");
        boolean result = this.uWithdrawService.refuse(uEntity, json, id, reason, remarks, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logRefuseWithdraw(uEntity, request, id, reason, remarks);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/withdraw-failure"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_WITHDRAW_FAILURE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/withdraw-failure";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String remarks = request.getParameter("remarks");
        boolean result = this.uWithdrawService.withdrawFailure(uEntity, json, id, remarks, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logWithdrawFailure(uEntity, request, id, remarks);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/complete-remit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_COMPLETE_REMIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/complete-remit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean result = this.uWithdrawService.completeRemit(uEntity, json, id, uEntity.getUsername());
        if (result)
        {
          this.adminUserLogJob.logCompleteRemitWithdraw(uEntity, request, id);
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
  
  @RequestMapping(value={"/lottery-user-withdraw/lock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_LOCK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/lock";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String operatorUser = uEntity.getUsername();
        boolean result = this.uWithdrawService.lock(uEntity, json, id, operatorUser);
        if (result)
        {
          this.adminUserLogJob.logLockWithdraw(uEntity, request, id);
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-withdraw/unlock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_UNLOCK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/unlock";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String operatorUser = uEntity.getUsername();
        boolean result = this.uWithdrawService.unlock(uEntity, json, id, operatorUser);
        if (result)
        {
          this.adminUserLogJob.logUnLockWithdraw(uEntity, request, id);
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-withdraw/api-pay"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_WITHDRAW_API_PAY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-withdraw/api-pay";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String channelCode = HttpUtil.getStringParameterTrim(request, "channelCode");
        Integer channelId = HttpUtil.getIntParameter(request, "channelId");
        if (API_PAY_ORDER_CACHE.containsKey(Integer.valueOf(id)))
        {
          json.set(Integer.valueOf(2), "2-4017");
        }
        else
        {
          PaymentChannel channel = this.dataFactory.getPaymentChannelFullProperty(channelCode, channelId);
          if (channel == null)
          {
            json.setWithParams(Integer.valueOf(2), "2-4015", new Object[] { channelCode });
          }
          else
          {
            API_PAY_ORDER_CACHE.put(Integer.valueOf(id), Boolean.valueOf(true));
            
            boolean result = this.uWithdrawService.apiPay(uEntity, json, id, channel);
            if (result)
            {
              this.adminUserLogJob.logAPIPay(uEntity, request, id, channel);
              if ((json.getError() == null) || (json.getError().intValue() == 0)) {
                json.set(Integer.valueOf(0), "0-5");
              }
            }
            else
            {
              API_PAY_ORDER_CACHE.remove(Integer.valueOf(id));
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
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
