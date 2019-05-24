package admin.web.content;

import admin.domains.content.biz.utils.JSMenuVO;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.web.helper.AbstractActionController;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.date.Moment;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPageController
  extends AbstractActionController
{
  @Autowired
  private AdminUserDao mAdminUserDao;
  
  @RequestMapping(value={"/index"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView MAIN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/login");
    }
    Map<String, Object> model = new HashMap();
    List<JSMenuVO> mlist = super.listUserMenu(uEntity);
    
    model.put("mlist", JSON.toJSONString(mlist));
    return new ModelAndView("/index", model);
  }
  
  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOGIN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null) {
      return new ModelAndView("redirect:/index");
    }
    return new ModelAndView("/login");
  }
  
  @RequestMapping(value={"/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOGOUT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    super.logOut(session, request, response);
    return new ModelAndView("redirect:/");
  }
  
  @RequestMapping(value={"/access-denied"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACCESS_DENIED(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    return new ModelAndView("/access-denied");
  }
  
  @RequestMapping(value={"/page-not-found"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView PAGE_NOT_FOUND(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    return new ModelAndView("/page-not-found");
  }
  
  @RequestMapping(value={"/page-error"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView PAGE_ERROR(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    return new ModelAndView("/page-error");
  }
  
  @RequestMapping(value={"/page-not-login"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView PAGE_NOT_LOGIN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    return new ModelAndView("/page-not-login");
  }
  
  @RequestMapping(value={"/dashboard"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView DASHBOARD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/dashboard");
  }
  
  @RequestMapping(value={"/lottery"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery");
  }
  
  @RequestMapping(value={"/lottery-type"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_TYPE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-type");
  }
  
  @RequestMapping(value={"/lottery-crawler-status"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_CRAWLER_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-crawler-status");
  }
  
  @RequestMapping(value={"/lottery-open-time"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_OPEN_TIME(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-open-time");
  }
  
  @RequestMapping(value={"/lottery-open-code"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_OPEN_CODE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-open-code");
  }
  
  @RequestMapping(value={"/lottery-open-status"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_OPEN_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-open-status");
  }
  
  @RequestMapping(value={"/lottery-play-rules"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PLAY_RULES(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-play-rules");
  }
  
  @RequestMapping(value={"/lottery-play-rules-group"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PLAY_RULES_GROUP(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-play-rules-group");
  }
  
  @RequestMapping(value={"/lottery-user"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user");
  }
  
  @RequestMapping(value={"/lottery-user-online"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_ONLINE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-online");
  }
  
  @RequestMapping(value={"/lottery-user-blacklist"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BLACK_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-blacklist");
  }
  
  @RequestMapping(value={"/lottery-user-whitelist"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_WHITE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-whitelist");
  }
  
  @RequestMapping(value={"/lottery-user-profile"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_PROFILE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-profile");
  }
  
  @RequestMapping(value={"/lottery-user-card"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_CARD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-card");
  }
  
  @RequestMapping(value={"/lottery-user-unbind-card"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_UNBIND_CARD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-unbind-card");
  }
  
  @RequestMapping(value={"/lottery-user-security"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_SECURITY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-security");
  }
  
  @RequestMapping(value={"/lottery-user-recharge"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_RECHARGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-recharge");
  }
  
  @RequestMapping(value={"/history-lottery-user-recharge"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_USER_RECHARGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/history-lottery-user-recharge");
  }
  
  @RequestMapping(value={"/lottery-user-withdraw"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_WITHDRAW(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    Map<String, Object> model = new HashMap();
    model.put("LoginUser", uEntity.getUsername());
    return new ModelAndView("/lottery-user-withdraw", model);
  }
  
  @RequestMapping(value={"/history-lottery-user-withdraw"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_USER_WITHDRAW(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    Map<String, Object> model = new HashMap();
    model.put("LoginUser", uEntity.getUsername());
    return new ModelAndView("/history-lottery-user-withdraw", model);
  }
  
  @RequestMapping(value={"/lottery-user-withdraw-check"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_WITHDRAW_CHECK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-withdraw-check");
  }
  
  @RequestMapping(value={"/history-lottery-user-withdraw-check"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_USER_WITHDRAW_CHECK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/history-lottery-user-withdraw-check");
  }
  
  @RequestMapping(value={"/lottery-user-bets"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BETS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-bets");
  }
  
  @RequestMapping(value={"/history-lottery-user-bets"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_USER_BETS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/history-lottery-user-bets");
  }
  
  @RequestMapping(value={"/lottery-user-bets-original"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BETS_ORIGINAL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-bets-original");
  }
  
  @RequestMapping(value={"/lottery-user-bets-plan"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BETS_PLAN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-bets-plan");
  }
  
  @RequestMapping(value={"/lottery-user-bets-batch"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BETS_BATCH(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-bets-batch");
  }
  
  @RequestMapping(value={"/lottery-user-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-bill");
  }
  
  @RequestMapping(value={"/history-lottery-user-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_USER_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/history-lottery-user-bill");
  }
  
  @RequestMapping(value={"/lottery-user-message"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_MESSAGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-message");
  }
  
  @RequestMapping(value={"/user-high-prize"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView USER_HIGH_PRIZE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    Map<String, Object> model = new HashMap();
    model.put("LoginUser", uEntity.getUsername());
    return new ModelAndView("/user-high-prize", model);
  }
  
  @RequestMapping(value={"/lottery-platform-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PLATFORM_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-platform-bill");
  }
  
  @RequestMapping(value={"/lottery-user-daily-settle-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_DAILY_SETTLE_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-daily-settle-bill");
  }
  
  @RequestMapping(value={"/lottery-user-daily-settle"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_DAILY_SETTLE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-daily-settle");
  }
  
  @RequestMapping(value={"/lottery-user-dividend"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_DIVIDEND(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-dividend");
  }
  
  @RequestMapping(value={"/lottery-user-dividend-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_DIVIDEND_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-dividend-bill");
  }
  
  @RequestMapping(value={"/user-game-dividend-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView USER_GAME_DIVIDEND_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/user-game-dividend-bill");
  }
  
  @RequestMapping(value={"/user-game-water-bill"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView USER_GAME_WATER_BILL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/user-game-water-bill");
  }
  
  @RequestMapping(value={"/main-report-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView MAIN_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/main-report-complex");
  }
  
  @RequestMapping(value={"/lottery-report-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-report-complex");
  }
  
  @RequestMapping(value={"/history-lottery-report-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/history-lottery-report-complex");
  }
  
  @RequestMapping(value={"/lottery-report-profit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_REPORT_PROFIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-report-profit");
  }
  
  @RequestMapping(value={"/game-report-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView GAME_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/game-report-complex");
  }
  
  @RequestMapping(value={"/history-game-report-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_GAME_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/history-game-report-complex");
  }
  
  @RequestMapping(value={"/recharge-withdraw-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView RECHARGE_WITHDRAW_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/recharge-withdraw-complex");
  }
  
  @RequestMapping(value={"/lottery-report-user-profit-ranking"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_REPORT_USER_PROFIT_RANKING(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-report-user-profit-ranking");
  }
  
  @RequestMapping(value={"/lottery-payment-bank"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PAYMENT_BANK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-payment-bank");
  }
  
  @RequestMapping(value={"/lottery-payment-card"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PAYMENT_CARD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-payment-card");
  }
  
  @RequestMapping(value={"/lottery-payment-channel"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PAYMENT_CHANNEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-payment-channel");
  }
  
  @RequestMapping(value={"/lottery-payment-channel-mobilescan"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PAYMENT_CHANNEL_MOBILESCAN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-payment-channel-mobilescan");
  }
  
  @RequestMapping(value={"/lottery-payment-channel-bank"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_PAYMENT_CHANNEL_BANK(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-payment-channel-bank");
  }
  
  @RequestMapping(value={"/lottery-sys-config"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_SYS_CONFIG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-sys-config");
  }
  
  @RequestMapping(value={"/lottery-sys-notice"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_SYS_NOTICE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-sys-notice");
  }
  
  @RequestMapping(value={"/lottery-user-action-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_ACTION_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-user-action-log");
  }
  
  @RequestMapping(value={"/lottery-user-login-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_LOGIN_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/login");
    }
    return new ModelAndView("/lottery-user-login-log");
  }
  
  @RequestMapping(value={"/history-lottery-user-login-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView HISTORY_LOTTERY_USER_LOGIN_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/login");
    }
    return new ModelAndView("/history-lottery-user-login-log");
  }
  
  @RequestMapping(value={"/lottery-user-login-sameip-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_LOGIN_SAMIP_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/login");
    }
    return new ModelAndView("/lottery-user-login-sameip-log");
  }
  
  @RequestMapping(value={"/lottery-user-bets-limit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_USER_BETS_LIMIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/login");
    }
    return new ModelAndView("/lottery-user-bets-limit");
  }
  
  @RequestMapping(value={"/activity-rebate-reward"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_REWARD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-reward");
  }
  
  @RequestMapping(value={"/activity-rebate-salary"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_SALARY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-salary");
  }
  
  @RequestMapping(value={"/activity-rebate-bind"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_BIND(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-bind");
  }
  
  @RequestMapping(value={"/activity-rebate-recharge"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_RECHARGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-recharge");
  }
  
  @RequestMapping(value={"/activity-rebate-recharge-loop"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_RECHARGE_LOOP(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-recharge-loop");
  }
  
  @RequestMapping(value={"/activity-rebate-sign"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_SIGN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-sign");
  }
  
  @RequestMapping(value={"/activity-rebate-grab"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_GRAB(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-grab");
  }
  
  @RequestMapping(value={"/activity-rebate-packet"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_PACKET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-packet");
  }
  
  @RequestMapping(value={"/activity-rebate-cost"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_COST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-cost");
  }
  
  @RequestMapping(value={"/activity-red-packet-rain"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_RED_PACKET_RAIN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-red-packet-rain");
  }
  
  @RequestMapping(value={"/activity-first-recharge"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_FIRST_RECHARGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-first-recharge");
  }
  
  @RequestMapping(value={"/activity-rebate-wheel"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ACTIVITY_REBATE_WHEEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/activity-rebate-wheel");
  }
  
  @RequestMapping(value={"/vip-upgrade-list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView VIP_UPGRADE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/vip-upgrade-list");
  }
  
  @RequestMapping(value={"/vip-upgrade-gifts"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView VIP_UPGRADE_GIFTS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/vip-upgrade-gifts");
  }
  
  @RequestMapping(value={"/vip-birthday-gifts"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView VIP_BIRTHDAY_GIFTS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/vip-birthday-gifts");
  }
  
  @RequestMapping(value={"/vip-free-chips"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView VIP_FREE_CHIPS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/vip-free-chips");
  }
  
  @RequestMapping(value={"/vip-integral-exchange"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView VIP_INTEGRAL_EXCHANGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/vip-integral-exchange");
  }
  
  @RequestMapping(value={"/lottery-instant-stat"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_INSTANT_STAT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    String ServerTime = new Moment().toSimpleTime();
    Map<String, Object> model = new HashMap();
    model.put("ServerTime", ServerTime);
    return new ModelAndView("/lottery-instant-stat", model);
  }
  
  @RequestMapping(value={"/user-balance-snapshot"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView USER_BALANCE_SNAPSHOT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    String ServerTime = new Moment().toSimpleTime();
    Map<String, Object> model = new HashMap();
    return new ModelAndView("/user-balance-snapshot", model);
  }
  
  @RequestMapping(value={"/admin-user"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/admin-user");
  }
  
  @RequestMapping(value={"/admin-user-role"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER_ROLE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/admin-user-role");
  }
  
  @RequestMapping(value={"/admin-user-menu"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER_MENU(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/admin-user-menu");
  }
  
  @RequestMapping(value={"/admin-user-action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER_ACTION(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/admin-user-action");
  }
  
  @RequestMapping(value={"/admin-user-action-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER_ACTION_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/admin-user-action-log");
  }
  
  @RequestMapping(value={"/admin-user-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/admin-user-log");
  }
  
  @RequestMapping(value={"/admin-user-critical-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView ADMIN_USER_CRITICAL_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/admin-user-critical-log");
    }
    return new ModelAndView("/admin-user-critical-log");
  }
  
  @RequestMapping(value={"/lottery-sys-control"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView LOTTERY_SYS_CONTROL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/lottery-sys-control");
  }
  
  @RequestMapping(value={"/game-list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView GAME_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/game-list");
  }
  
  @RequestMapping(value={"/game-platform-list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView GAME_PLATFORM_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/game-platform-list");
  }
  
  @RequestMapping(value={"/game-bets"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView GAME_BETS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/game-bets");
  }
  
  @RequestMapping(value={"/user-bets-hit-ranking"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView USER_BETS_HIT_RANKING(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/user-bets-hit-ranking");
  }
  
  @RequestMapping(value={"/user-bets-same-ip-log"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView USER_BETS_SAME_IP_LOG(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity == null) {
      return new ModelAndView("redirect:/page-not-login");
    }
    return new ModelAndView("/user-bets-same-ip-log");
  }
}
