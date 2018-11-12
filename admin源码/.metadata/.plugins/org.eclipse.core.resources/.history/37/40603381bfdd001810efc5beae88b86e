package lottery.web.content;

import lottery.domains.content.vo.bill.UserProfitRankingVO;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import javautils.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.vo.bill.UserMainReportVO;
import java.util.List;
import lottery.domains.content.entity.User;
import admin.domains.content.entity.AdminUser;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.biz.UserLotteryDetailsReportService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class ComplexReportController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserMainReportService uMainReportService;
    @Autowired
    private UserLotteryReportService uLotteryReportService;
    @Autowired
    private UserLotteryDetailsReportService uLotteryDetailsReportService;
    @Autowired
    private UserGameReportService uGameReportService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    
    @RequestMapping(value = { "/main-report/complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void MAIN_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/main-report/complex";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String sTime = HttpUtil.getStringParameterTrim(request, "sTime");
                final String eTime = HttpUtil.getStringParameterTrim(request, "eTime");
                if (StringUtils.isNotEmpty(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    if (targetUser != null) {
                        final List<UserMainReportVO> result = this.uMainReportService.report(targetUser.getId(), sTime, eTime);
                        json.accumulate("list", result);
                    }
                    else {
                        json.accumulate("list", new ArrayList());
                    }
                }
                else {
                    final List<UserMainReportVO> result2 = this.uMainReportService.report(sTime, eTime);
                    json.accumulate("list", result2);
                }
                final List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
                json.accumulate("userLevels", userLevels);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-report/complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-report/complex";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                if (StringUtil.isNotNull(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    if (targetUser != null) {
                        final List<UserLotteryReportVO> result = this.uLotteryReportService.report(targetUser.getId(), sTime, eTime);
                        json.accumulate("list", result);
                    }
                    else {
                        json.accumulate("list", new ArrayList());
                    }
                }
                else if (type != null) {
                    final List<UserLotteryReportVO> result2 = this.uLotteryReportService.reportByType(4, sTime, eTime);
                    json.accumulate("list", result2);
                }
                else {
                    final List<UserLotteryReportVO> result2 = this.uLotteryReportService.report(sTime, eTime);
                    json.accumulate("list", result2);
                }
                final List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
                json.accumulate("userLevels", userLevels);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/history-lottery-report/complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-report/complex";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                if (StringUtil.isNotNull(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    if (targetUser != null) {
                        final List<HistoryUserLotteryReportVO> result = this.uLotteryReportService.historyReport(targetUser.getId(), sTime, eTime);
                        json.accumulate("list", result);
                    }
                    else {
                        json.accumulate("list", new ArrayList());
                    }
                }
                else {
                    final List<HistoryUserLotteryReportVO> result2 = this.uLotteryReportService.historyReport(sTime, eTime);
                    json.accumulate("list", result2);
                }
                final List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
                json.accumulate("userLevels", userLevels);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-report/complex-details" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_REPORT_COMPLEX_DETAILS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-report/complex-details";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Boolean self = HttpUtil.getBooleanParameter(request, "self");
                if (StringUtil.isNotNull(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    List<UserLotteryDetailsReportVO> result;
                    if (self != null && self) {
                        result = this.uLotteryDetailsReportService.reportSelf(targetUser.getId(), lotteryId, sTime, eTime);
                    }
                    else {
                        result = this.uLotteryDetailsReportService.reportLowersAndSelf(targetUser.getId(), lotteryId, sTime, eTime);
                    }
                    json.accumulate("list", result);
                    json.set(0, "0-3");
                }
                else {
                    json.set(1, "1-3");
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
    
    @RequestMapping(value = { "/history-lottery-report/complex-details" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_REPORT_COMPLEX_DETAILS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-report/complex-details";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Boolean self = HttpUtil.getBooleanParameter(request, "self");
                if (StringUtil.isNotNull(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    List<HistoryUserLotteryDetailsReportVO> result;
                    if (self != null && self) {
                        result = this.uLotteryDetailsReportService.historyReportSelf(targetUser.getId(), lotteryId, sTime, eTime);
                    }
                    else {
                        result = this.uLotteryDetailsReportService.historyReportLowersAndSelf(targetUser.getId(), lotteryId, sTime, eTime);
                    }
                    json.accumulate("list", result);
                    json.set(0, "0-3");
                }
                else {
                    json.set(1, "1-3");
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
    
    @RequestMapping(value = { "/lottery-report/profit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_REPORT_PROFIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-report/profit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer lottery = HttpUtil.getIntParameter(request, "lottery");
                final Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
                final String sTime = HttpUtil.getStringParameterTrim(request, "sTime");
                final String eTime = HttpUtil.getStringParameterTrim(request, "eTime");
                final List<UserBetsReportVO> result = this.uLotteryDetailsReportService.sumUserBets(type, lottery, ruleId, sTime, eTime);
                json.accumulate("list", result);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/game-report/complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game-report/complex";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                if (StringUtil.isNotNull(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    final List<UserGameReportVO> result = this.uGameReportService.report(targetUser.getId(), sTime, eTime);
                    json.accumulate("list", result);
                }
                else {
                    final List<UserGameReportVO> result2 = this.uGameReportService.report(sTime, eTime);
                    json.accumulate("list", result2);
                }
                final List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
                json.accumulate("userLevels", userLevels);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/history-game-report/complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_GAME_REPORT_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-game-report/complex";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                if (StringUtil.isNotNull(username)) {
                    final User targetUser = this.uDao.getByUsername(username);
                    final List<HistoryUserGameReportVO> result = this.uGameReportService.historyReport(targetUser.getId(), sTime, eTime);
                    json.accumulate("list", result);
                }
                else {
                    final List<HistoryUserGameReportVO> result2 = this.uGameReportService.historyReport(sTime, eTime);
                    json.accumulate("list", result2);
                }
                final List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
                json.accumulate("userLevels", userLevels);
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-report/user-profit-ranking" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_REPORT_USER_PROFIT_RANKING(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-report/user-profit-ranking";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Integer userId = HttpUtil.getIntParameter(request, "userId");
                final List<UserProfitRankingVO> result = this.uLotteryReportService.listUserProfitRanking(userId, sTime, eTime, 0, 20);
                json.accumulate("list", result);
                json.set(0, "0-3");
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
