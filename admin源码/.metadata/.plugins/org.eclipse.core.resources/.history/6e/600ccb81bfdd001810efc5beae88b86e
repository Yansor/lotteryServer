package lottery.web.content;

import lottery.domains.content.entity.UserBets;
import lottery.domains.content.vo.user.HistoryUserBetsVO;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.user.UserBetsVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.dao.LotteryOpenCodeDao;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserBetsService;
import admin.domains.jobs.AdminUserLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserBetsController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserBetsService uBetsService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private LotteryOpenCodeDao lotteryOpenCodeDao;
    @Autowired
    private UserBetsDao userBetsDao;
    @Autowired
    private LotteryDao lotteryDao;
    
    @RequestMapping(value = { "/lottery-user-bets/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer utype = HttpUtil.getIntParameter(request, "utype");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final String expect = HttpUtil.getStringParameterTrim(request, "expect");
                final Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
                String minTime = HttpUtil.getStringParameterTrim(request, "minTime");
                if (StringUtil.isNotNull(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                String maxTime = HttpUtil.getStringParameterTrim(request, "maxTime");
                if (StringUtil.isNotNull(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 00:00:00";
                }
                String minPrizeTime = HttpUtil.getStringParameterTrim(request, "minPrizeTime");
                if (StringUtil.isNotNull(minPrizeTime)) {
                    minPrizeTime = String.valueOf(minPrizeTime) + " 00:00:00";
                }
                String maxPrizeTime = HttpUtil.getStringParameterTrim(request, "maxPrizeTime");
                if (StringUtil.isNotNull(maxPrizeTime)) {
                    maxPrizeTime = String.valueOf(maxPrizeTime) + " 00:00:00";
                }
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
                final Integer minMultiple = HttpUtil.getIntParameter(request, "minMultiple");
                final Integer maxMultiple = HttpUtil.getIntParameter(request, "maxMultiple");
                final Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
                final Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer locked = HttpUtil.getIntParameter(request, "locked");
                final String ip = HttpUtil.getStringParameterTrim(request, "ip");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBetsService.search(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked, ip, start, limit);
                if (pList != null) {
                    final double[] totalMoney = this.uBetsService.getTotalMoney(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked, ip);
                    double canceltotalMoney = 0.0;
                    final double[] canceltotalMoneys = this.uBetsService.getTotalMoney(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, -1, locked, ip);
                    canceltotalMoney = canceltotalMoneys[0];
                    json.accumulate("totalMoney", totalMoney[0]);
                    json.accumulate("canceltotalMoney", canceltotalMoney);
                    json.accumulate("totalPrizeMoney", totalMoney[1]);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("canceltotalMoney", 0);
                    json.accumulate("totalMoney", 0);
                    json.accumulate("totalPrizeMoney", 0);
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
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
    
    @RequestMapping(value = { "/history-lottery-user-bets/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_BETS_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-bets/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer utype = HttpUtil.getIntParameter(request, "utype");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final String expect = HttpUtil.getStringParameterTrim(request, "expect");
                final Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
                String minTime = HttpUtil.getStringParameterTrim(request, "minTime");
                if (StringUtil.isNotNull(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                String maxTime = HttpUtil.getStringParameterTrim(request, "maxTime");
                if (StringUtil.isNotNull(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 00:00:00";
                }
                String minPrizeTime = HttpUtil.getStringParameterTrim(request, "minPrizeTime");
                if (StringUtil.isNotNull(minPrizeTime)) {
                    minPrizeTime = String.valueOf(minPrizeTime) + " 00:00:00";
                }
                String maxPrizeTime = HttpUtil.getStringParameterTrim(request, "maxPrizeTime");
                if (StringUtil.isNotNull(maxPrizeTime)) {
                    maxPrizeTime = String.valueOf(maxPrizeTime) + " 00:00:00";
                }
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
                final Integer minMultiple = HttpUtil.getIntParameter(request, "minMultiple");
                final Integer maxMultiple = HttpUtil.getIntParameter(request, "maxMultiple");
                final Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
                final Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer locked = HttpUtil.getIntParameter(request, "locked");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBetsService.searchHistory(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked, start, limit);
                if (pList != null) {
                    final double[] totalMoney = this.uBetsService.getHistoryTotalMoney(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked);
                    double canceltotalMoney = 0.0;
                    final double[] canceltotalMoneys = this.uBetsService.getHistoryTotalMoney(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked);
                    canceltotalMoney = canceltotalMoneys[0];
                    json.accumulate("canceltotalMoney", canceltotalMoney);
                    json.accumulate("totalMoney", totalMoney[0]);
                    json.accumulate("totalPrizeMoney", totalMoney[1]);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("canceltotalMoney", 0);
                    json.accumulate("totalMoney", 0);
                    json.accumulate("totalPrizeMoney", 0);
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
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
    
    @RequestMapping(value = { "/lottery-user-bets/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserBetsVO result = this.uBetsService.getById(id);
                final String expect = result.getBean().getExpect();
                final int lotteryId = result.getBean().getLotteryId();
                final LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeDao.get(this.lotteryDao.getById(lotteryId).getShortName(), expect);
                if (lotteryOpenCode != null) {
                    result.getBean().setOpenCode(lotteryOpenCode.getCode());
                    result.getBean().setPrizeTime(lotteryOpenCode.getOpenTime());
                }
                json.accumulate("data", result);
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
    
    @RequestMapping(value = { "/history-lottery-user-bets/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_BETS_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-bets/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final HistoryUserBetsVO result = this.uBetsService.getHistoryById(id);
                json.accumulate("data", result);
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
    
    @RequestMapping(value = { "/lottery-user-bets/batch" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_BATCH(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/batch";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String step = HttpUtil.getStringParameterTrim(request, "step");
                final int lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
                final String expect = HttpUtil.getStringParameterTrim(request, "expect");
                final String match = HttpUtil.getStringParameterTrim(request, "match");
                if ("query".equals(step)) {
                    final int count = this.uBetsService.notOpened(lotteryId, ruleId, expect, match).size();
                    json.set(0, "0-3");
                    json.accumulate("data", count);
                }
                if ("execute".equals(step)) {
                    final boolean result = this.uBetsService.cancel(lotteryId, ruleId, expect, match);
                    if (result) {
                        this.adminUserLogJob.logBatchCancelOrder(uEntity, request, lotteryId, ruleId, expect, match);
                        json.set(0, "0-5");
                    }
                    else {
                        json.set(1, "1-5");
                    }
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
    
    @RequestMapping(value = { "/lottery-user-bets/cancel" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_CANCEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/cancel";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.uBetsService.cancel(id);
                if (result) {
                    this.adminUserLogJob.logCancelOrder(uEntity, request, id);
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
    
    @RequestMapping(value = { "/lottery-user-bets/change" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_CHANGE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/change";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int locked = HttpUtil.getIntParameter(request, "locked");
                final String act = HttpUtil.getStringParameterTrim(request, "act");
                final String codes = HttpUtil.getStringParameterTrim(request, "codes");
                if (act.equals("locked")) {
                    this.userBetsDao.updateLocked(id, locked);
                    json.set(0, "0-3");
                    if (locked == 1) {
                        json.setMessage("锁定成功");
                    }
                    if (locked == 0) {
                        json.setMessage("解锁成功");
                    }
                }
                if (act.equals("change")) {
                    if (locked == 1) {
                        final UserBets result = this.uBetsService.getBetsById(id);
                        final int lotteryId = result.getLotteryId();
                        final String expect = result.getExpect();
                        final LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeDao.get(this.lotteryDao.getById(lotteryId).getShortName(), expect);
                        if (lotteryOpenCode != null) {
                            this.userBetsDao.updateStatus(id, 1, codes, lotteryOpenCode.getCode(), 0.0, lotteryOpenCode.getOpenTime());
                            json.set(0, "0-3");
                            json.setMessage("改为不中成功");
                        }
                        else {
                            json.set(2, "2-4");
                            json.setMessage("暂未开奖");
                        }
                    }
                    if (locked == 0) {
                        json.set(2, "2-4");
                        json.setMessage("暂未锁定");
                    }
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
