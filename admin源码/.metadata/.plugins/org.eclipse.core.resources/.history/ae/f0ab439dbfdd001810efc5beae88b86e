package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.utils.lottery.open.OpenTime;
import lottery.domains.content.vo.lottery.LotteryOpenStatusVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import java.util.ArrayList;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import lottery.domains.content.biz.LotteryOpenStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryOpenStatusController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryOpenStatusService lotteryOpenStatusService;
    @Autowired
    private LotteryOpenUtil lotteryOpenUtil;
    
    @RequestMapping(value = { "/lottery-open-status/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_STATUS_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-status/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String lotteryId = request.getParameter("lotteryId");
                final String date = request.getParameter("date");
                if (Integer.valueOf(lotteryId) != 117) {
                    final List<LotteryOpenStatusVO> list = this.lotteryOpenStatusService.search(lotteryId, date);
                    final OpenTime thisOpentime = this.lotteryOpenUtil.getCurrOpenTime(Integer.parseInt(lotteryId));
                    json.accumulate("data", list);
                    json.accumulate("thisOpentime", thisOpentime);
                }
                else {
                    json.accumulate("data", new ArrayList());
                    json.accumulate("thisOpentime", null);
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
    
    @RequestMapping(value = { "/lottery-open-status/manual-control" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_MANUAL_CONTROL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-status/manual-control";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String lottery = request.getParameter("lottery");
                final String expect = request.getParameter("expect");
                final boolean result = this.lotteryOpenStatusService.doManualControl(lottery, expect);
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
