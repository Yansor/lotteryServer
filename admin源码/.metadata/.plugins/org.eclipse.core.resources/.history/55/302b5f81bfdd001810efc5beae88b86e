package lottery.web.content;

import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.vo.lottery.LotteryPlayRulesGroupSimpleVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryPlayRulesGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryPlayRulesGroupController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryPlayRulesGroupService groupService;
    
    @RequestMapping(value = { "/lottery-play-rules-group/simple-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_GROUP_SIMPLE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int typeId = HttpUtil.getIntParameter(request, "typeId");
            final List<LotteryPlayRulesGroupSimpleVO> list = this.groupService.listSimpleByType(typeId);
            json.accumulate("data", list);
            json.set(0, "0-5");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-play-rules-group/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_GROUP_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-play-rules-group/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final List<LotteryPlayRulesGroupVO> list = this.groupService.list(lotteryId);
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
    
    @RequestMapping(value = { "/lottery-play-rules-group/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_PLAY_RULES_GROUP_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-play-rules-group/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int groupId = HttpUtil.getIntParameter(request, "groupId");
                final Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
                final boolean enable = HttpUtil.getBooleanParameter(request, "enable");
                final boolean result = this.groupService.updateStatus(groupId, lotteryId, enable);
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
