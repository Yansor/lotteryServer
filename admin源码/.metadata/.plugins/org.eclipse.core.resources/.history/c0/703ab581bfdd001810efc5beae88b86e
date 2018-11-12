package lottery.web.content;

import lottery.domains.content.vo.user.UserBetsOriginalVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.web.content.utils.UserCodePointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.UserBetsOriginalService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserBetsOriginalController extends AbstractActionController
{
    @Autowired
    private UserBetsOriginalService uBetsOriginalService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    
    @RequestMapping(value = { "/lottery-user-bets/original-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_ORIGINAL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/original-list";
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
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBetsOriginalService.search(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, start, limit);
                if (pList != null) {
                    final double[] totalMoney = this.uBetsOriginalService.getTotalMoney(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status);
                    json.accumulate("totalMoney", totalMoney[0]);
                    json.accumulate("totalPrizeMoney", totalMoney[1]);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
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
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-bets/original-get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BETS_ORIGINAL_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bets/original-get";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserBetsOriginalVO result = this.uBetsOriginalService.getById(id);
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
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
