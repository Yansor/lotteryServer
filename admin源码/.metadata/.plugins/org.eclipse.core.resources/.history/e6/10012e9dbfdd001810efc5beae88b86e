package lottery.web.content;

import lottery.domains.content.entity.UserBetsHitRanking;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserBetsHitRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserBetsHitRankingController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private UserBetsHitRankingService uBetsHitRankingService;
    @Autowired
    private UserSysMessageService mUserSysMessageService;
    
    @RequestMapping(value = { "/user-bets-hit-ranking/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_BETS_HIT_RANKING_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-bets-hit-ranking/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBetsHitRankingService.search(start, limit);
                json.accumulate("totalCount", pList.getCount());
                json.accumulate("data", pList.getList());
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
    
    @RequestMapping(value = { "/user-bets-hit-ranking/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_BETS_HIT_RANKING_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-bets-hit-ranking/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String name = request.getParameter("name");
                final String username = request.getParameter("username");
                final int prizeMoney = HttpUtil.getIntParameter(request, "prizeMoney");
                final String time = request.getParameter("time");
                final String code = request.getParameter("code");
                final String type = request.getParameter("type");
                final int platform = HttpUtil.getIntParameter(request, "platform");
                if (StringUtils.isEmpty(name)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(username)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (prizeMoney < 0 || prizeMoney > 99999999) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(time)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(code)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(type)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (platform != 2 && platform != 4 && platform != 4) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final boolean result = this.uBetsHitRankingService.add(name, username, prizeMoney, time, code, type, platform);
                if (result) {
                    json.set(0, "0-6");
                }
                else {
                    json.set(1, "1-6");
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
    
    @RequestMapping(value = { "/user-bets-hit-ranking/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_BETS_HIT_RANKING_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-bets-hit-ranking/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String name = request.getParameter("name");
                final String username = request.getParameter("username");
                final int prizeMoney = HttpUtil.getIntParameter(request, "prizeMoney");
                final String time = request.getParameter("time");
                final String code = request.getParameter("code");
                final String type = request.getParameter("type");
                final int platform = HttpUtil.getIntParameter(request, "platform");
                if (StringUtils.isEmpty(name)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(username)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (prizeMoney < 0 || prizeMoney > 99999999) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(time)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(code)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(type)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (platform != 2 && platform != 4 && platform != 4) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final boolean result = this.uBetsHitRankingService.edit(id, name, username, prizeMoney, time, code, type, platform);
                if (result) {
                    json.set(0, "0-6");
                }
                else {
                    json.set(1, "1-6");
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
    
    @RequestMapping(value = { "/user-bets-hit-ranking/del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_BETS_HIT_RANKING_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-bets-hit-ranking/del";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.uBetsHitRankingService.delete(id);
                if (result) {
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
    
    @RequestMapping(value = { "/user-bets-hit-ranking/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_BETS_HIT_RANKING_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final int id = HttpUtil.getIntParameter(request, "id");
        final UserBetsHitRanking bean = this.uBetsHitRankingService.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
