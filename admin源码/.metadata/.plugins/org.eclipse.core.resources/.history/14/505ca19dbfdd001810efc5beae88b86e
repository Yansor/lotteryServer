package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.web.content.utils.UserCodePointUtil;
import admin.domains.jobs.AdminUserLogJob;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserHighPrizeService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserHighPrizeController extends AbstractActionController
{
    @Autowired
    private UserHighPrizeService highPrizeService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    
    @RequestMapping(value = { "/user-high-prize/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_HIGH_PRIZE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-high-prize/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final Integer platform = HttpUtil.getIntParameter(request, "platform");
                final String nameId = request.getParameter("nameId");
                final String subName = request.getParameter("subName");
                final String refId = request.getParameter("refId");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
                final Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
                final Double minTimes = HttpUtil.getDoubleParameter(request, "minTimes");
                final Double maxTimes = HttpUtil.getDoubleParameter(request, "maxTimes");
                String minTime = request.getParameter("minTime");
                String maxTime = request.getParameter("maxTime");
                if (StringUtils.isNotEmpty(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                if (StringUtils.isNotEmpty(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 23:59:59";
                }
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final String confirmUsername = request.getParameter("confirmUsername");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.highPrizeService.search(type, username, platform, nameId, subName, refId, minMoney, maxMoney, minPrizeMoney, maxPrizeMoney, minTimes, maxTimes, minTime, maxTime, status, confirmUsername, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
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
    
    @RequestMapping(value = { "/user-high-prize/lock" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_HIGH_PRIZE_LOCK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-high-prize/lock";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.highPrizeService.lock(id, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logLockHighPrize(uEntity, request, id);
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
    
    @RequestMapping(value = { "/user-high-prize/unlock" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_HIGH_PRIZE_UNLOCK(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-high-prize/unlock";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.highPrizeService.unlock(id, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logUnLockHighPrize(uEntity, request, id);
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
    
    @RequestMapping(value = { "/user-high-prize/confirm" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_HIGH_PRIZE_CONFIRM(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-high-prize/confirm";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.highPrizeService.confirm(id, uEntity.getUsername());
                if (result) {
                    this.adminUserLogJob.logConfirmHighPrize(uEntity, request, id);
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
}
