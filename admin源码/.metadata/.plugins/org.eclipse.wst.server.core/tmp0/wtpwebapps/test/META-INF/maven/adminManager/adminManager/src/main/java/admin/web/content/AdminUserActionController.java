package admin.web.content;

import admin.domains.content.biz.utils.JSTreeVO;
import java.util.Iterator;
import java.util.Map;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.biz.utils.TreeUtil;
import java.util.HashMap;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserMenu;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.domains.content.vo.AdminUserActionVO;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import net.sf.json.JSONArray;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.biz.AdminUserActionService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class AdminUserActionController extends AbstractActionController
{
    @Autowired
    private AdminUserActionService adminUserActionService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    
    @RequestMapping(value = { "/admin-user-action/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ACTION_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-action/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<AdminUserActionVO> list = this.adminUserActionService.listAll();
                final JSONArray data = JSONArray.fromObject((Object)list);
                HttpUtil.write(response, data.toString(), "text/json");
                return;
            }
            json.set(2, "2-4");
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
    
    @RequestMapping(value = { "/admin-user-action/jstree" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ACTION_JSTREE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final int roleId = HttpUtil.getIntParameter(request, "roleId");
        final AdminUserRole adminUserRole = super.getAdminDataFactory().getAdminUserRole(roleId);
        List<AdminUserMenu> mList = new ArrayList<AdminUserMenu>();
        List<AdminUserAction> aList = new ArrayList<AdminUserAction>();
        if (adminUserRole.getUpid() != 0) {
            mList = super.getAdminDataFactory().getAdminUserMenuByRoleId(adminUserRole.getUpid());
            aList = super.getAdminDataFactory().getAdminUserActionByRoleId(adminUserRole.getUpid());
        }
        else {
            mList = super.getAdminDataFactory().listAdminUserMenu();
            aList = super.getAdminDataFactory().listAdminUserAction();
        }
        final Map<Integer, AdminUserAction> aMap = new HashMap<Integer, AdminUserAction>();
        for (final AdminUserAction tmpBean : aList) {
            aMap.put(tmpBean.getId(), tmpBean);
        }
        final List<AdminUserMenu> menuList = TreeUtil.listMenuRoot(mList);
        final List<JSTreeVO> jsTreeList = TreeUtil.listJSTreeRoot2(menuList, aMap);
        final JSONArray json = JSONArray.fromObject((Object)jsTreeList);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user-action/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void AMDIN_USER_ACTION_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-action/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.adminUserActionService.updateStatus(id, status);
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
