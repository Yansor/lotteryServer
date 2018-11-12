package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.SessionCounterUtil;
import lottery.domains.content.dao.PaymentChannelDao;
import lottery.domains.content.dao.PaymentCardDao;
import lottery.domains.content.biz.UserHighPrizeService;
import lottery.domains.content.dao.ActivityRechargeBillDao;
import lottery.domains.content.dao.ActivityBindBillDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserWithdrawDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class CommonController extends AbstractActionController
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
    
    @RequestMapping(value = { "/global" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GLOBAL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int uOnlineCount = this.uDao.getOnlineCount(null);
            final int uWithdrawCount = this.uWithdrawDao.getWaitTodo();
            final int aBindCount = this.aBindBillDao.getWaitTodo();
            final int aRechargeCount = this.aRechargeBillDao.getWaitTodo();
            final int bRegchargeCount = this.uRechargeDao.getRechargeCount(0, 1, 2);
            final int pCardOverload = this.paymentCardDao.getOverload();
            final int pChannelOverload = this.paymentChannelDao.getOverload();
            json.accumulate("bRegchargeCount", bRegchargeCount);
            json.accumulate("uOnlineCount", uOnlineCount);
            json.accumulate("uWithdrawCount", uWithdrawCount);
            json.accumulate("aBindCount", aBindCount);
            json.accumulate("aRechargeCount", aRechargeCount);
            json.accumulate("pCardOverload", pCardOverload);
            json.accumulate("pChannelOverload", pChannelOverload);
            json.accumulate("isUnlockedWithdrawPwd", this.isUnlockedWithdrawPwd(session));
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-global-config" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_GLOBAL_CONFIG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            json.accumulate("adminGlobalConfig", this.dataFactory.getAdminGlobalConfig());
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/high-prize-unprocess-count" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HIGH_PRIZE_UNPROCESS_COUNT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int unProcessCount = this.highPrizeService.getUnProcessCount();
            json.accumulate("unProcessCount", unProcessCount);
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
