package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserHighPrizeService;
import lottery.domains.content.dao.ActivityBindBillDao;
import lottery.domains.content.dao.ActivityRechargeBillDao;
import lottery.domains.content.dao.PaymentCardDao;
import lottery.domains.content.dao.PaymentChannelDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.SessionCounterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController
  extends AbstractActionController
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserWithdrawDao uWithdrawDao;
  @Autowired
  private UserRechargeDao uRechargeDao;
  @Autowired
  private ActivityBindBillDao aBindBillDao;
  @Autowired
  private ActivityRechargeBillDao aRechargeBillDao;
  @Autowired
  private UserHighPrizeService highPrizeService;
  @Autowired
  private PaymentCardDao paymentCardDao;
  @Autowired
  private PaymentChannelDao paymentChannelDao;
  @Autowired
  private SessionCounterUtil sessionCounterUtil;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  @RequestMapping(value={"/global"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GLOBAL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      int uOnlineCount = this.uDao.getOnlineCount(null);
      int uWithdrawCount = this.uWithdrawDao.getWaitTodo();
      int aBindCount = this.aBindBillDao.getWaitTodo();
      int aRechargeCount = this.aRechargeBillDao.getWaitTodo();
      int bRegchargeCount = this.uRechargeDao.getRechargeCount(0, 1, 2);
      
      int pCardOverload = this.paymentCardDao.getOverload();
      int pChannelOverload = this.paymentChannelDao.getOverload();
      json.accumulate("bRegchargeCount", Integer.valueOf(bRegchargeCount));
      json.accumulate("uOnlineCount", Integer.valueOf(uOnlineCount));
      json.accumulate("uWithdrawCount", Integer.valueOf(uWithdrawCount));
      json.accumulate("aBindCount", Integer.valueOf(aBindCount));
      json.accumulate("aRechargeCount", Integer.valueOf(aRechargeCount));
      json.accumulate("pCardOverload", Integer.valueOf(pCardOverload));
      json.accumulate("pChannelOverload", Integer.valueOf(pChannelOverload));
      json.accumulate("isUnlockedWithdrawPwd", Boolean.valueOf(isUnlockedWithdrawPwd(session)));
      json.set(Integer.valueOf(0), "0-3");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-global-config"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_GLOBAL_CONFIG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      json.accumulate("adminGlobalConfig", this.dataFactory.getAdminGlobalConfig());
      json.set(Integer.valueOf(0), "0-3");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/high-prize-unprocess-count"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HIGH_PRIZE_UNPROCESS_COUNT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      int unProcessCount = this.highPrizeService.getUnProcessCount();
      json.accumulate("unProcessCount", Integer.valueOf(unProcessCount));
      json.set(Integer.valueOf(0), "0-3");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
