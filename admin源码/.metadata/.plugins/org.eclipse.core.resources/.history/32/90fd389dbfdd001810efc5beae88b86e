package lottery.web.content;

import java.util.List;
import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import lottery.domains.content.entity.SysConfig;
import javautils.http.HttpUtil;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class SysConfigController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private SysConfigService sysConfigService;
    
    @RequestMapping(value = { "/lottery-sys-config/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_CONFIG_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String group = request.getParameter("group");
        final String key = request.getParameter("key");
        final SysConfig bean = this.sysConfigService.get(group, key);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-sys-config/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_CONFIG_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-config/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<SysConfig> slist = this.sysConfigService.listAll();
                json.accumulate("slist", slist);
                final List<String> alist = super.listSysConfigKey(uEntity);
                json.accumulate("alist", alist);
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
    
    @RequestMapping(value = { "/lottery-sys-config/update" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_CONFIG_UPDATE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-config/update";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String key = request.getParameter("key");
                final String group = request.getParameter("group");
                final String value = request.getParameter("value");
                final boolean result = this.sysConfigService.update(group, key, value);
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
