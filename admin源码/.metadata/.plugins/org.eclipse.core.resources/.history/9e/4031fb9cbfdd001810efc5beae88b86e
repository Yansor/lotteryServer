package lottery.web.content;

import javax.servlet.ServletContext;
import admin.tools.StringUtils;
import java.util.Properties;
import javautils.http.EasyHttpClient;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.tools.entity.ServerConfig;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import admin.tools.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class SysControlController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private ServerService mServerService;
    
    @RequestMapping(value = { "/lottery-sys-control/do" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_CONTROL_DO(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-control/do";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String server = request.getParameter("server");
            final String action = request.getParameter("action");
            final ServerConfig config = this.getConfig(request, server);
            final String result = this.mServerService.execute(config, action);
            HttpUtil.write(response, result, "text/json");
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
    
    @RequestMapping(value = { "/lottery-sys-control/status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_CONTROL_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String server = request.getParameter("server");
        final String host = request.getParameter("host");
        final EasyHttpClient client = new EasyHttpClient();
        final String string = client.get(String.valueOf(host) + "/lottery-sys-control?action=status&server=" + server);
        HttpUtil.write(response, string, "text/json");
    }
    
    public ServerConfig getConfig(final HttpServletRequest req, final String key) {
        try {
            final Properties p = new Properties();
            final ServletContext ctx = req.getSession().getServletContext();
            p.load(ctx.getResourceAsStream("WEB-INF/properties/config.properties"));
            final String values = p.getProperty(key);
            final String[] split = values.split(";");
            final int port = StringUtils.toInt(split[1], 22);
            final ServerConfig config = new ServerConfig(split[0], port, split[2], split[3], split[4]);
            return config;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
