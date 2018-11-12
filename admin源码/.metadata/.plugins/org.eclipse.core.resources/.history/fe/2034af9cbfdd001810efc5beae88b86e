package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.entity.LotteryType;
import java.util.List;
import javautils.http.HttpUtil;
import net.sf.json.JSONArray;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryTypeController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryTypeService lotteryTypeService;
    
    @RequestMapping(value = { "/lottery-type/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_TYPE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final List<LotteryType> list = this.lotteryTypeService.listAll();
        final JSONArray json = JSONArray.fromObject((Object)list);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-type/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-type/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.lotteryTypeService.updateStatus(id, status);
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
