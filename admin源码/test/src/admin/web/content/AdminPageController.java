package admin.web.content;

import javautils.date.Moment;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.domains.content.biz.utils.JSMenuVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class AdminPageController extends AbstractActionController
{
    @Autowired
    private AdminUserDao mAdminUserDao;
    
    @RequestMapping(value = { "/index" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView MAIN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/login");
        }
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<JSMenuVO> mlist = super.listUserMenu(uEntity);
        model.put("mlist", JSON.toJSONString((Object)mlist));
        return new ModelAndView("/index", (Map)model);
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOGIN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            return new ModelAndView("redirect:/index");
        }
        return new ModelAndView("/login");
    }
    
    @RequestMapping(value = { "/logout" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOGOUT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        super.logOut(session, request, response);
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value = { "/access-denied" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACCESS_DENIED(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView("/access-denied");
    }
    
    @RequestMapping(value = { "/page-not-found" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView PAGE_NOT_FOUND(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView("/page-not-found");
    }
    
    @RequestMapping(value = { "/page-error" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView PAGE_ERROR(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView("/page-error");
    }
    
    @RequestMapping(value = { "/page-not-login" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView PAGE_NOT_LOGIN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView("/page-not-login");
    }
    
    @RequestMapping(value = { "/dashboard" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView DASHBOARD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/dashboard");
    }
    
    @RequestMapping(value = { "/lottery" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery");
    }
    
    @RequestMapping(value = { "/lottery-type" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_TYPE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-type");
    }
    
    @RequestMapping(value = { "/lottery-crawler-status" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_CRAWLER_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-crawler-status");
    }
    
    @RequestMapping(value = { "/lottery-open-time" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_OPEN_TIME(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-open-time");
    }
    
    @RequestMapping(value = { "/lottery-open-code" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_OPEN_CODE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-open-code");
    }
    
    @RequestMapping(value = { "/lottery-open-status" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_OPEN_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-open-status");
    }
    
    @RequestMapping(value = { "/lottery-play-rules" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PLAY_RULES(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-play-rules");
    }
    
    @RequestMapping(value = { "/lottery-play-rules-group" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PLAY_RULES_GROUP(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-play-rules-group");
    }
    
    @RequestMapping(value = { "/lottery-user" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user");
    }
    
    @RequestMapping(value = { "/lottery-user-online" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_ONLINE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-online");
    }
    
    @RequestMapping(value = { "/lottery-user-blacklist" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BLACK_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-blacklist");
    }
    
    @RequestMapping(value = { "/lottery-user-whitelist" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_WHITE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-whitelist");
    }
    
    @RequestMapping(value = { "/lottery-user-profile" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_PROFILE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-profile");
    }
    
    @RequestMapping(value = { "/lottery-user-card" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_CARD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-card");
    }
    
    @RequestMapping(value = { "/lottery-user-unbind-card" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_UNBIND_CARD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-unbind-card");
    }
    
    @RequestMapping(value = { "/lottery-user-security" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_SECURITY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-security");
    }
    
    @RequestMapping(value = { "/lottery-user-recharge" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_RECHARGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-recharge");
    }
    
    @RequestMapping(value = { "/history-lottery-user-recharge" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_USER_RECHARGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/history-lottery-user-recharge");
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_WITHDRAW(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("LoginUser", uEntity.getUsername());
        return new ModelAndView("/lottery-user-withdraw", (Map)model);
    }
    
    @RequestMapping(value = { "/history-lottery-user-withdraw" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_USER_WITHDRAW(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("LoginUser", uEntity.getUsername());
        return new ModelAndView("/history-lottery-user-withdraw", (Map)model);
    }
    
    @RequestMapping(value = { "/lottery-user-withdraw-check" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_WITHDRAW_CHECK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-withdraw-check");
    }
    
    @RequestMapping(value = { "/history-lottery-user-withdraw-check" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_USER_WITHDRAW_CHECK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/history-lottery-user-withdraw-check");
    }
    
    @RequestMapping(value = { "/lottery-user-bets" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BETS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-bets");
    }
    
    @RequestMapping(value = { "/history-lottery-user-bets" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_USER_BETS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/history-lottery-user-bets");
    }
    
    @RequestMapping(value = { "/lottery-user-bets-original" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BETS_ORIGINAL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-bets-original");
    }
    
    @RequestMapping(value = { "/lottery-user-bets-plan" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BETS_PLAN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-bets-plan");
    }
    
    @RequestMapping(value = { "/lottery-user-bets-batch" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BETS_BATCH(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-bets-batch");
    }
    
    @RequestMapping(value = { "/lottery-user-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-bill");
    }
    
    @RequestMapping(value = { "/history-lottery-user-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_USER_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/history-lottery-user-bill");
    }
    
    @RequestMapping(value = { "/lottery-user-message" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_MESSAGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-message");
    }
    
    @RequestMapping(value = { "/user-high-prize" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView USER_HIGH_PRIZE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("LoginUser", uEntity.getUsername());
        return new ModelAndView("/user-high-prize", (Map)model);
    }
    
    @RequestMapping(value = { "/lottery-platform-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PLATFORM_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-platform-bill");
    }
    
    @RequestMapping(value = { "/lottery-user-daily-settle-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_DAILY_SETTLE_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-daily-settle-bill");
    }
    
    @RequestMapping(value = { "/lottery-user-daily-settle" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_DAILY_SETTLE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-daily-settle");
    }
    
    @RequestMapping(value = { "/lottery-user-dividend" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_DIVIDEND(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-dividend");
    }
    
    @RequestMapping(value = { "/lottery-user-dividend-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_DIVIDEND_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-dividend-bill");
    }
    
    @RequestMapping(value = { "/user-game-dividend-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView USER_GAME_DIVIDEND_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/user-game-dividend-bill");
    }
    
    @RequestMapping(value = { "/user-game-water-bill" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView USER_GAME_WATER_BILL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/user-game-water-bill");
    }
    
    @RequestMapping(value = { "/main-report-complex" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView MAIN_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/main-report-complex");
    }
    
    @RequestMapping(value = { "/lottery-report-complex" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-report-complex");
    }
    
    @RequestMapping(value = { "/history-lottery-report-complex" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/history-lottery-report-complex");
    }
    
    @RequestMapping(value = { "/lottery-report-profit" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_REPORT_PROFIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-report-profit");
    }
    
    @RequestMapping(value = { "/game-report-complex" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView GAME_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/game-report-complex");
    }
    
    @RequestMapping(value = { "/history-game-report-complex" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_GAME_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/history-game-report-complex");
    }
    
    @RequestMapping(value = { "/recharge-withdraw-complex" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView RECHARGE_WITHDRAW_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/recharge-withdraw-complex");
    }
    
    @RequestMapping(value = { "/lottery-report-user-profit-ranking" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_REPORT_USER_PROFIT_RANKING(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-report-user-profit-ranking");
    }
    
    @RequestMapping(value = { "/lottery-payment-bank" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PAYMENT_BANK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-payment-bank");
    }
    
    @RequestMapping(value = { "/lottery-payment-card" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PAYMENT_CARD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-payment-card");
    }
    
    @RequestMapping(value = { "/lottery-payment-channel" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PAYMENT_CHANNEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-payment-channel");
    }
    
    @RequestMapping(value = { "/lottery-payment-channel-mobilescan" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PAYMENT_CHANNEL_MOBILESCAN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-payment-channel-mobilescan");
    }
    
    @RequestMapping(value = { "/lottery-payment-channel-bank" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_PAYMENT_CHANNEL_BANK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-payment-channel-bank");
    }
    
    @RequestMapping(value = { "/lottery-sys-config" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_SYS_CONFIG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-sys-config");
    }
    
    @RequestMapping(value = { "/lottery-sys-notice" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_SYS_NOTICE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-sys-notice");
    }
    
    @RequestMapping(value = { "/lottery-user-action-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_ACTION_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-user-action-log");
    }
    
    @RequestMapping(value = { "/lottery-user-login-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_LOGIN_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("/lottery-user-login-log");
    }
    
    @RequestMapping(value = { "/history-lottery-user-login-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView HISTORY_LOTTERY_USER_LOGIN_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("/history-lottery-user-login-log");
    }
    
    @RequestMapping(value = { "/lottery-user-login-sameip-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_LOGIN_SAMIP_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("/lottery-user-login-sameip-log");
    }
    
    @RequestMapping(value = { "/lottery-user-bets-limit" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_USER_BETS_LIMIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("/lottery-user-bets-limit");
    }
    
    @RequestMapping(value = { "/activity-rebate-reward" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_REWARD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-reward");
    }
    
    @RequestMapping(value = { "/activity-rebate-salary" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_SALARY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-salary");
    }
    
    @RequestMapping(value = { "/activity-rebate-bind" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_BIND(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-bind");
    }
    
    @RequestMapping(value = { "/activity-rebate-recharge" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_RECHARGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-recharge");
    }
    
    @RequestMapping(value = { "/activity-rebate-recharge-loop" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_RECHARGE_LOOP(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-recharge-loop");
    }
    
    @RequestMapping(value = { "/activity-rebate-sign" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_SIGN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-sign");
    }
    
    @RequestMapping(value = { "/activity-rebate-grab" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_GRAB(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-grab");
    }
    
    @RequestMapping(value = { "/activity-rebate-packet" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_PACKET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-packet");
    }
    
    @RequestMapping(value = { "/activity-rebate-cost" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_COST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-cost");
    }
    
    @RequestMapping(value = { "/activity-red-packet-rain" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_RED_PACKET_RAIN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-red-packet-rain");
    }
    
    @RequestMapping(value = { "/activity-first-recharge" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_FIRST_RECHARGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-first-recharge");
    }
    
    @RequestMapping(value = { "/activity-rebate-wheel" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ACTIVITY_REBATE_WHEEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/activity-rebate-wheel");
    }
    
    @RequestMapping(value = { "/vip-upgrade-list" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView VIP_UPGRADE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/vip-upgrade-list");
    }
    
    @RequestMapping(value = { "/vip-upgrade-gifts" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView VIP_UPGRADE_GIFTS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/vip-upgrade-gifts");
    }
    
    @RequestMapping(value = { "/vip-birthday-gifts" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView VIP_BIRTHDAY_GIFTS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/vip-birthday-gifts");
    }
    
    @RequestMapping(value = { "/vip-free-chips" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView VIP_FREE_CHIPS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/vip-free-chips");
    }
    
    @RequestMapping(value = { "/vip-integral-exchange" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView VIP_INTEGRAL_EXCHANGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/vip-integral-exchange");
    }
    
    @RequestMapping(value = { "/lottery-instant-stat" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_INSTANT_STAT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        final String ServerTime = new Moment().toSimpleTime();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("ServerTime", ServerTime);
        return new ModelAndView("/lottery-instant-stat", (Map)model);
    }
    
    @RequestMapping(value = { "/user-balance-snapshot" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView USER_BALANCE_SNAPSHOT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        final String ServerTime = new Moment().toSimpleTime();
        final Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView("/user-balance-snapshot", (Map)model);
    }
    
    @RequestMapping(value = { "/admin-user" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/admin-user");
    }
    
    @RequestMapping(value = { "/admin-user-role" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER_ROLE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/admin-user-role");
    }
    
    @RequestMapping(value = { "/admin-user-menu" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER_MENU(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/admin-user-menu");
    }
    
    @RequestMapping(value = { "/admin-user-action" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER_ACTION(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/admin-user-action");
    }
    
    @RequestMapping(value = { "/admin-user-action-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER_ACTION_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/admin-user-action-log");
    }
    
    @RequestMapping(value = { "/admin-user-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/admin-user-log");
    }
    
    @RequestMapping(value = { "/admin-user-critical-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView ADMIN_USER_CRITICAL_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/admin-user-critical-log");
        }
        return new ModelAndView("/admin-user-critical-log");
    }
    
    @RequestMapping(value = { "/lottery-sys-control" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView LOTTERY_SYS_CONTROL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/lottery-sys-control");
    }
    
    @RequestMapping(value = { "/game-list" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView GAME_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/game-list");
    }
    
    @RequestMapping(value = { "/game-platform-list" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView GAME_PLATFORM_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/game-platform-list");
    }
    
    @RequestMapping(value = { "/game-bets" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView GAME_BETS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/game-bets");
    }
    
    @RequestMapping(value = { "/user-bets-hit-ranking" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView USER_BETS_HIT_RANKING(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/user-bets-hit-ranking");
    }
    
    @RequestMapping(value = { "/user-bets-same-ip-log" }, method = { RequestMethod.GET })
    @ResponseBody
    public ModelAndView USER_BETS_SAME_IP_LOG(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity == null) {
            return new ModelAndView("redirect:/page-not-login");
        }
        return new ModelAndView("/user-bets-same-ip-log");
    }
}
