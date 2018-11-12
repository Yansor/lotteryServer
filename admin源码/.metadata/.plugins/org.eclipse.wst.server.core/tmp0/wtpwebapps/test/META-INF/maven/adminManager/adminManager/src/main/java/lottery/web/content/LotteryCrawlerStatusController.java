package lottery.web.content;

import lottery.domains.content.entity.LotteryCrawlerStatus;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.vo.lottery.LotteryCrawlerStatusVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryCrawlerStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryCrawlerStatusController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryCrawlerStatusService lotteryCrawlerStatusService;
    
    @RequestMapping(value = { "/lottery-crawler-status/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_CRAWLER_STATUS_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-crawler-status/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<LotteryCrawlerStatusVO> list = this.lotteryCrawlerStatusService.listAll();
                json.set(0, "0-3");
                json.accumulate("data", list);
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
    
    @RequestMapping(value = { "/lottery-crawler-status/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_CRAWLER_STATUS_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-crawler-status/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String lottery = request.getParameter("lottery");
                final String lastExpect = request.getParameter("lastExpect");
                final String lastUpdate = request.getParameter("lastUpdate");
                final boolean result = this.lotteryCrawlerStatusService.update(lottery, lastExpect, lastUpdate);
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
    
    @RequestMapping(value = { "/lottery-crawler-status/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_CRAWLER_STATUS_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String lottery = request.getParameter("lottery");
        final LotteryCrawlerStatus bean = this.lotteryCrawlerStatusService.getByLottery(lottery);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
