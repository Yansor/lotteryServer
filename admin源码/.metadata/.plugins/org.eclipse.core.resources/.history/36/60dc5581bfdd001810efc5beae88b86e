package lottery.web.content;

import java.util.Map;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.vo.user.UserDailySettleVO;
import java.util.HashMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import java.util.Iterator;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.math.MathUtil;
import lottery.domains.content.entity.User;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserService;
import admin.domains.jobs.AdminUserLogJob;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserActionLogJob;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserDailySettleService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserDailySettleController extends AbstractActionController
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDailySettleService settleService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserCriticalLogJob adminUserCriticalLogJob;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserService uService;
    
    @RequestMapping(value = { "/lottery-user-daily-settle/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                Double minScale = HttpUtil.getDoubleParameter(request, "minScale");
                Double maxScale = HttpUtil.getDoubleParameter(request, "maxScale");
                final Integer minValidUser = HttpUtil.getIntParameter(request, "minValidUser");
                final Integer maxValidUser = HttpUtil.getIntParameter(request, "maxValidUser");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final List<Integer> userIds = new ArrayList<Integer>();
                boolean legalUser = true;
                if (StringUtils.isNotEmpty(username)) {
                    final User user = this.userDao.getByUsername(username);
                    if (user == null) {
                        legalUser = false;
                    }
                    else {
                        userIds.add(user.getId());
                        final List<User> userDirectLowers = this.userDao.getUserDirectLower(user.getId());
                        for (final User userDirectLower : userDirectLowers) {
                            userIds.add(userDirectLower.getId());
                        }
                    }
                }
                if (!legalUser) {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                else {
                    if (minScale != null) {
                        minScale = MathUtil.divide(minScale, 100.0, 4);
                    }
                    if (maxScale != null) {
                        maxScale = MathUtil.divide(maxScale, 100.0, 4);
                    }
                    final PageList pList = this.settleService.search(userIds, sTime, eTime, minScale, maxScale, minValidUser, maxValidUser, status, start, limit);
                    if (pList != null) {
                        json.accumulate("totalCount", pList.getCount());
                        json.accumulate("data", pList.getList());
                    }
                    else {
                        json.accumulate("totalCount", 0);
                        json.accumulate("data", "[]");
                    }
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
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-daily-settle/del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle/del";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username").trim();
                final User uBean = this.userDao.getByUsername(username);
                if (uBean == null) {
                    json.set(2, "2-32");
                }
                else if (this.settleService.checkCanDel(json, uBean)) {
                    final boolean result = this.settleService.deleteByTeam(username);
                    if (result) {
                        this.adminUserCriticalLogJob.logDelDailySettle(uEntity, request, username, actionKey);
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
    
    @RequestMapping(value = { "/lottery-user-daily-settle/edit-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_EDIT_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle/edit-get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserDailySettle dailySettle = this.settleService.getById(id);
                if (dailySettle == null) {
                    json.set(1, "1-7");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final User user = this.userDao.getById(dailySettle.getUserId());
                if (user == null) {
                    json.set(1, "2-32");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (!this.settleService.checkCanEdit(json, user)) {
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDailySettle upDailySettle = this.settleService.getByUserId(user.getUpid());
                if (!this.uCodePointUtil.isLevel3Proxy(user) && upDailySettle == null) {
                    json.set(1, "1-8");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final double[] minMaxScale = this.settleService.getMinMaxScale(user);
                final double minScale = minMaxScale[0];
                final double maxScale = minMaxScale[1];
                final double[] minMaxSales = this.settleService.getMinMaxSales(user);
                final double minSales = minMaxSales[0];
                final double maxSales = minMaxSales[1];
                final double[] minMaxLoss = this.settleService.getMinMaxLoss(user);
                final double minLoss = minMaxLoss[0];
                final double maxLoss = minMaxLoss[1];
                final int[] minMaxUser = this.settleService.getMinMaxUsers(user);
                final int minUser = minMaxUser[0];
                final int maxUser = minMaxUser[1];
                final List<String> userLevels = this.uService.getUserLevels(user);
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("bean", (dailySettle == null) ? null : new UserDailySettleVO(dailySettle, this.dataFactory));
                data.put("upBean", (upDailySettle == null) ? null : new UserDailySettleVO(upDailySettle, this.dataFactory));
                data.put("minScale", minScale);
                data.put("maxScale", maxScale);
                data.put("minSales", minSales);
                data.put("maxSales", maxSales);
                data.put("minLoss", minLoss);
                data.put("maxLoss", maxLoss);
                data.put("minUser", minUser);
                data.put("maxUser", maxUser);
                data.put("scaleLevel", dailySettle.getScaleLevel());
                data.put("lossLevel", dailySettle.getLossLevel());
                data.put("salesLevel", dailySettle.getSalesLevel());
                data.put("userLevel", dailySettle.getUserLevel());
                data.put("minValidUser", this.dataFactory.getDailySettleConfig().getMinValidUserl());
                data.put("maxSignLevel", this.dataFactory.getDailySettleConfig().getMaxSignLevel());
                data.put("userLevels", userLevels);
                json.accumulate("data", data);
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
    
    @RequestMapping(value = { "/lottery-user-daily-settle/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String scaleLevel = HttpUtil.getStringParameterTrim(request, "scaleLevel");
                final String[] scaleLevels = scaleLevel.split(",");
                final String lossLevel = HttpUtil.getStringParameterTrim(request, "lossLevel");
                final String[] lossLevels = scaleLevel.split(",");
                final String salesLevel = HttpUtil.getStringParameterTrim(request, "salesLevel");
                final String[] salesLevels = salesLevel.split(",");
                final String userLevel = HttpUtil.getStringParameterTrim(request, "userLevel");
                final String[] userLevels = userLevel.split(",");
                final double[] scaleCfg = this.dataFactory.getDailySettleConfig().getLevelsScale();
                final double[] salesCfg = this.dataFactory.getDailySettleConfig().getLevelsSales();
                final double[] lossCfg = this.dataFactory.getDailySettleConfig().getLevelsLoss();
                final int[] userCfg = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
                if (Double.valueOf(scaleLevels[0]) < scaleCfg[0] || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > scaleCfg[1] || Double.valueOf(salesLevels[0]) < salesCfg[0] || Double.valueOf(salesLevels[salesLevels.length - 1]) > salesCfg[1] || Double.valueOf(lossLevels[0]) < lossCfg[0] || Double.valueOf(lossLevels[lossLevels.length - 1]) > lossCfg[1] || Integer.valueOf(userLevels[0]) < userCfg[0] || Integer.valueOf(userLevels[userLevels.length - 1]) > userCfg[1]) {
                    json.set(1, "1-8");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDailySettle dailySettle = this.settleService.getById(id);
                if (dailySettle == null) {
                    json.set(1, "1-7");
                }
                else {
                    final User uBean = this.userDao.getById(dailySettle.getUserId());
                    if (this.settleService.checkCanEdit(json, uBean)) {
                        final boolean result = this.settleService.update(json, id, scaleLevel, salesLevel, lossLevel, Integer.valueOf(userLevels[0]), userLevel);
                        if (result) {
                            this.adminUserLogJob.logEditDailySettle(uEntity, request, dailySettle, scaleLevel, salesLevel, lossLevel, userLevel);
                            json.set(0, "0-5");
                        }
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
    
    @RequestMapping(value = { "/lottery-user-daily-settle/add-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_ADD_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle/add-get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                if (StringUtils.isEmpty(username)) {
                    json.set(0, "0-3");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final User user = this.uService.getByUsername(username);
                if (user == null) {
                    json.set(2, "2-32");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (!this.settleService.checkCanEdit(json, user)) {
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDailySettle dailySettle = this.settleService.getByUserId(user.getId());
                if (dailySettle != null) {
                    json.set(2, "2-3007");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDailySettle upDailySettle = this.settleService.getByUserId(user.getUpid());
                if (!this.uCodePointUtil.isLevel3Proxy(user) && (upDailySettle == null || upDailySettle.getStatus() != 1)) {
                    json.set(2, "2-3008");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final double[] minMaxScale = this.settleService.getMinMaxScale(user);
                final double minScale = minMaxScale[0];
                final double maxScale = minMaxScale[1];
                final double[] minMaxSales = this.settleService.getMinMaxSales(user);
                final double minSales = minMaxSales[0];
                final double maxSales = minMaxSales[1];
                final double[] minMaxLoss = this.settleService.getMinMaxLoss(user);
                final double minLoss = minMaxLoss[0];
                final double maxLoss = minMaxLoss[1];
                final int[] minMaxUser = this.settleService.getMinMaxUsers(user);
                final int minUser = minMaxUser[0];
                final int maxUser = minMaxUser[1];
                final List<String> userLevels = this.uService.getUserLevels(user);
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("upBean", (upDailySettle == null) ? null : new UserDailySettleVO(upDailySettle, this.dataFactory));
                data.put("minScale", minScale);
                data.put("maxScale", maxScale);
                data.put("minSales", minSales);
                data.put("maxSales", maxSales);
                data.put("minLoss", minLoss);
                data.put("maxLoss", maxLoss);
                data.put("minUser", minUser);
                data.put("maxUser", maxUser);
                data.put("minValidUser", this.dataFactory.getDailySettleConfig().getMinValidUserl());
                data.put("maxSignLevel", this.dataFactory.getDailySettleConfig().getMaxSignLevel());
                data.put("userLevels", userLevels);
                json.accumulate("data", data);
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
    
    @RequestMapping(value = { "/lottery-user-daily-settle/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final String scaleLevel = HttpUtil.getStringParameterTrim(request, "scaleLevel");
                final String lossLevel = HttpUtil.getStringParameterTrim(request, "lossLevel");
                final String salesLevel = HttpUtil.getStringParameterTrim(request, "salesLevel");
                final String userLevel = HttpUtil.getStringParameterTrim(request, "userLevel");
                final int status = HttpUtil.getIntParameter(request, "status");
                final String[] scaleLevels = scaleLevel.split(",");
                final String[] lossLevels = lossLevel.split(",");
                final String[] salesLevels = salesLevel.split(",");
                final String[] userLevels = userLevel.split(",");
                final double[] scaleCfg = this.dataFactory.getDailySettleConfig().getLevelsScale();
                final double[] salesCfg = this.dataFactory.getDailySettleConfig().getLevelsSales();
                final double[] lossCfg = this.dataFactory.getDailySettleConfig().getLevelsLoss();
                final int[] userCfg = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
                final User user = this.uService.getByUsername(username);
                if (user == null) {
                    json.set(2, "2-32");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (Double.valueOf(scaleLevels[0]) < scaleCfg[0] || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > scaleCfg[1] || Double.valueOf(salesLevels[0]) < salesCfg[0] || Double.valueOf(salesLevels[salesLevels.length - 1]) > salesCfg[1] || Double.valueOf(lossLevels[0]) < lossCfg[0] || Double.valueOf(lossLevels[lossLevels.length - 1]) > lossCfg[1] || Integer.valueOf(userLevels[0]) < userCfg[0] || Integer.valueOf(userLevels[userLevels.length - 1]) > userCfg[1]) {
                    json.set(1, "1-8");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (status != 1 && status != 2) {
                    json.set(1, "1-8");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final boolean result = this.settleService.add(json, username, scaleLevel, salesLevel, lossLevel, Integer.valueOf(userLevels[0]), status, userLevel);
                if (result) {
                    this.adminUserLogJob.logAddDailySettle(uEntity, request, username, scaleLevel, salesLevel, lossLevel, userLevel, status);
                    json.set(0, "0-5");
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
