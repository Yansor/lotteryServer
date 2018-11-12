package lottery.web.content;

import java.util.Map;
import lottery.domains.content.entity.UserDividend;
import lottery.domains.content.vo.user.UserDividendVO;
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
import admin.domains.jobs.AdminUserCriticalLogJob;
import lottery.web.content.utils.UserCodePointUtil;
import admin.domains.jobs.AdminUserLogJob;
import lottery.domains.pool.LotteryDataFactory;
import admin.domains.jobs.AdminUserActionLogJob;
import lottery.domains.content.biz.UserDividendService;
import lottery.domains.content.biz.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserDividendController extends AbstractActionController
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService uService;
    @Autowired
    private UserDividendService userDividendService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private AdminUserCriticalLogJob adminUserCriticalLogJob;
    
    @RequestMapping(value = { "/lottery-user-dividend/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend/list";
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
                final Integer fixed = HttpUtil.getIntParameter(request, "fixed");
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
                    final PageList pList = this.userDividendService.search(userIds, sTime, eTime, minScale, maxScale, minValidUser, maxValidUser, status, fixed, start, limit);
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
    
    @RequestMapping(value = { "/lottery-user-dividend/del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend/del";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final User uBean = this.userDao.getByUsername(username);
                if (uBean == null) {
                    json.set(2, "2-32");
                }
                else if (this.userDividendService.checkCanDel(json, uBean)) {
                    final boolean result = this.userDividendService.deleteByTeam(username);
                    if (result) {
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
    
    @RequestMapping(value = { "/lottery-user-dividend/edit-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_EDIT_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend/edit-get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserDividend dividend = this.userDividendService.getById(id);
                if (dividend == null) {
                    json.set(1, "1-7");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final User user = this.userDao.getById(dividend.getUserId());
                if (user == null) {
                    json.set(1, "2-32");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (!this.userDividendService.checkCanEdit(json, user)) {
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDividend upDividend = this.userDividendService.getByUserId(user.getUpid());
                if (!this.uCodePointUtil.isLevel2Proxy(user) && upDividend == null) {
                    json.set(1, "1-8");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final double[] minMaxScale = this.userDividendService.getMinMaxScale(user);
                final double minScale = minMaxScale[0];
                final double maxScale = minMaxScale[1];
                final double[] minMaxSales = this.userDividendService.getMinMaxSales(user);
                final double minSales = minMaxSales[0];
                final double maxSales = minMaxSales[1];
                final double[] minMaxLoss = this.userDividendService.getMinMaxLoss(user);
                final double minLoss = minMaxLoss[0];
                final double maxLoss = minMaxLoss[1];
                final int[] minMaxUser = this.userDividendService.getMinMaxUser(user);
                final int minUser = minMaxUser[0];
                final int maxUser = minMaxUser[1];
                final List<String> userLevels = this.uService.getUserLevels(user);
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("bean", (dividend == null) ? null : new UserDividendVO(dividend, this.dataFactory));
                data.put("upBean", (upDividend == null) ? null : new UserDividendVO(upDividend, this.dataFactory));
                data.put("minScale", minScale);
                data.put("maxScale", maxScale);
                data.put("minSales", minSales);
                data.put("maxSales", maxSales);
                data.put("minLoss", minLoss);
                data.put("maxLoss", maxLoss);
                data.put("minUser", minUser);
                data.put("maxUser", maxUser);
                data.put("scaleLevel", dividend.getScaleLevel());
                data.put("lossLevel", dividend.getLossLevel());
                data.put("salesLevel", dividend.getSalesLevel());
                data.put("userLevel", dividend.getUserLevel());
                data.put("minValidUser", this.dataFactory.getDividendConfig().getMinValidUserl());
                data.put("maxSignLevel", this.dataFactory.getDividendConfig().getMaxSignLevel());
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
    
    @RequestMapping(value = { "/lottery-user-dividend/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend/edit";
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
                final double[] scaleCfg = this.dataFactory.getDividendConfig().getLevelsScale();
                final double[] salesCfg = this.dataFactory.getDividendConfig().getLevelsSales();
                final double[] lossCfg = this.dataFactory.getDividendConfig().getLevelsLoss();
                final int[] userCfg = { this.dataFactory.getDividendConfig().getMinValidUserl(), 1000 };
                if (Double.valueOf(scaleLevels[0]) < scaleCfg[0] || Double.valueOf(scaleLevels[scaleLevels.length - 1]) > scaleCfg[1] || Double.valueOf(salesLevels[0]) < salesCfg[0] || Double.valueOf(salesLevels[salesLevels.length - 1]) > salesCfg[1] || Double.valueOf(lossLevels[0]) < lossCfg[0] || Double.valueOf(lossLevels[lossLevels.length - 1]) > lossCfg[1] || Integer.valueOf(userLevels[0]) < userCfg[0] || Integer.valueOf(userLevels[userLevels.length - 1]) > userCfg[1]) {
                    json.set(1, "1-8");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDividend dividend = this.userDividendService.getById(id);
                if (dividend == null) {
                    json.set(1, "1-7");
                }
                else {
                    final boolean result = this.userDividendService.update(json, id, scaleLevel, lossLevel, salesLevel, Integer.valueOf(userLevels[0]), userLevel);
                    if (result) {
                        this.adminUserLogJob.logEditDividend(uEntity, request, dividend, scaleLevel, lossLevel, salesLevel, userLevel);
                        json.set(0, "0-5");
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
    
    @RequestMapping(value = { "/lottery-user-dividend/add-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_ADD_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend/add-get";
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
                if (!this.userDividendService.checkCanEdit(json, user)) {
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDividend dividend = this.userDividendService.getByUserId(user.getId());
                if (dividend != null) {
                    json.set(2, "2-3010");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final UserDividend upDividend = this.userDividendService.getByUserId(user.getUpid());
                if (!this.uCodePointUtil.isLevel2Proxy(user) && upDividend == null) {
                    json.set(2, "2-3011");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final double[] minMaxScale = this.userDividendService.getMinMaxScale(user);
                final double minScale = minMaxScale[0];
                final double maxScale = minMaxScale[1];
                final double[] minMaxSales = this.userDividendService.getMinMaxSales(user);
                final double minSales = minMaxSales[0];
                final double maxSales = minMaxSales[1];
                final double[] minMaxLoss = this.userDividendService.getMinMaxLoss(user);
                final double minLoss = minMaxLoss[0];
                final double maxLoss = minMaxLoss[1];
                final int[] minMaxUser = this.userDividendService.getMinMaxUser(user);
                final int minUser = minMaxUser[0];
                final int maxUser = minMaxUser[1];
                final List<String> userLevels = this.uService.getUserLevels(user);
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("upBean", (upDividend == null) ? null : new UserDividendVO(upDividend, this.dataFactory));
                data.put("minScale", minScale);
                data.put("maxScale", maxScale);
                data.put("minSales", minSales);
                data.put("maxSales", maxSales);
                data.put("minLoss", minLoss);
                data.put("maxLoss", maxLoss);
                data.put("minUser", minUser);
                data.put("maxUser", maxUser);
                data.put("minValidUser", this.dataFactory.getDividendConfig().getMinValidUserl());
                data.put("maxSignLevel", this.dataFactory.getDividendConfig().getMaxSignLevel());
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
    
    @RequestMapping(value = { "/lottery-user-dividend/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DIVIDEND_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-dividend/add";
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
                final User user = this.uService.getByUsername(username);
                if (user == null) {
                    json.set(2, "2-32");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final String[] scaleLevels = scaleLevel.split(",");
                final String[] lossLevels = lossLevel.split(",");
                final String[] salesLevels = salesLevel.split(",");
                final String[] userLevels = userLevel.split(",");
                final double[] scaleCfg = this.dataFactory.getDividendConfig().getLevelsScale();
                final double[] salesCfg = this.dataFactory.getDividendConfig().getLevelsSales();
                final double[] lossCfg = this.dataFactory.getDividendConfig().getLevelsLoss();
                final int[] userCfg = { this.dataFactory.getDividendConfig().getMinValidUserl(), 1000 };
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
                final boolean result = this.userDividendService.add(json, username, scaleLevel, lossLevel, salesLevel, Integer.valueOf(userLevels[0]), status, userLevel);
                if (result) {
                    this.adminUserLogJob.logAddDividend(uEntity, request, username, scaleLevel, lossLevel, salesLevel, userLevel, status);
                    this.adminUserCriticalLogJob.logDelDividend(uEntity, request, username, actionKey);
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
