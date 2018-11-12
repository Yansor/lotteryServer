package lottery.web.content;

import lottery.domains.content.vo.lottery.LotteryPlayRulesSimpleVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.vo.lottery.LotteryPlayRulesVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryPlayRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryPlayRulesController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryPlayRulesService playRulesService;
    
    @RequestMapping(value = { "/lottery-play-rules/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-play-rules/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final Integer groupId = HttpUtil.getIntParameter(request, "groupId");
                final List<LotteryPlayRulesVO> list = this.playRulesService.list(lotteryId, groupId);
                json.accumulate("data", list);
                json.set(0, "0-5");
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
    
    @RequestMapping(value = { "/lottery-play-rules/simple-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_SIMPLE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int typeId = HttpUtil.getIntParameter(request, "typeId");
            final Integer groupId = HttpUtil.getIntParameter(request, "groupId");
            final List<LotteryPlayRulesSimpleVO> list = this.playRulesService.listSimple(typeId, groupId);
            json.accumulate("data", list);
            json.set(0, "0-5");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-play-rules/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-play-rules/get";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final int ruleId = HttpUtil.getIntParameter(request, "ruleId");
                final LotteryPlayRulesVO rulesVO = this.playRulesService.get(lotteryId, ruleId);
                json.accumulate("data", rulesVO);
                json.set(0, "0-5");
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
    
    @RequestMapping(value = { "/lottery-play-rules/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-play-rules/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int ruleId = HttpUtil.getIntParameter(request, "ruleId");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final String minNum = HttpUtil.getStringParameterTrim(request, "minNum");
                final String maxNum = HttpUtil.getStringParameterTrim(request, "maxNum");
                final boolean result = this.playRulesService.edit(ruleId, lotteryId, minNum, maxNum);
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
    
    @RequestMapping(value = { "/lottery-play-rules/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-play-rules/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int ruleId = HttpUtil.getIntParameter(request, "ruleId");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final boolean enable = HttpUtil.getBooleanParameter(request, "enable");
                final boolean result = this.playRulesService.updateStatus(ruleId, lotteryId, enable);
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
}
