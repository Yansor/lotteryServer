package lottery.web.content;

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
import lottery.domains.content.biz.UserBetsPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserBetsPlanController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private UserBetsPlanService uBetsPlanService;
    
    @RequestMapping(value = { "/lottery-user-bets-plan/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_PLAN_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets-plan/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
                final String username = HttpUtil.getStringParameterTrim(request, "username");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final String expect = HttpUtil.getStringParameterTrim(request, "expect");
                final Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
                final String minTime = HttpUtil.getStringParameterTrim(request, "minTime");
                final String maxTime = HttpUtil.getStringParameterTrim(request, "maxTime");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
                final Integer minMultiple = HttpUtil.getIntParameter(request, "minMultiple");
                final Integer maxMultiple = HttpUtil.getIntParameter(request, "maxMultiple");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBetsPlanService.search(keyword, username, lotteryId, expect, ruleId, minTime, maxTime, minMoney, maxMoney, minMultiple, maxMultiple, status, start, limit);
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
}
