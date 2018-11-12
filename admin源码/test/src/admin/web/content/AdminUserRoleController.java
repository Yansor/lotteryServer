package admin.web.content;

import net.sf.json.JSONObject;
import admin.domains.content.vo.AdminUserRoleVO;
import javautils.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import admin.domains.content.entity.AdminUserRole;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.biz.AdminUserRoleService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class AdminUserRoleController extends AbstractActionController
{
    @Autowired
    private AdminUserRoleService adminUserRoleService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    
    @RequestMapping(value = { "/admin-user-role/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-role/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<AdminUserRole> list = this.adminUserRoleService.listAll(uEntity.getRoleId());
                json.accumulate("list", list);
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
    
    @RequestMapping(value = { "/admin-user-role/tree-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_TREE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final List<AdminUserRole> list = this.adminUserRoleService.listTree(uEntity.getRoleId());
            json.accumulate("list", list);
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user-role/check-exist" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_CHECK_EXIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String id = request.getParameter("id");
        final String name = request.getParameter("name");
        final AdminUserRoleVO bean = this.adminUserRoleService.getByName(name);
        String isExist = "false";
        if (bean != null) {
            if (StringUtil.isNotNull(id) && StringUtil.isInteger(id) && bean.getBean().getId() == Integer.parseInt(id)) {
                isExist = "true";
            }
        }
        else {
            isExist = "true";
        }
        HttpUtil.write(response, isExist, "text/json");
    }
    
    @RequestMapping(value = { "/admin-user-role/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final int id = HttpUtil.getIntParameter(request, "id");
        final AdminUserRoleVO bean = this.adminUserRoleService.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/admin-user-role/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-role/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String name = request.getParameter("name");
                final int upid = HttpUtil.getIntParameter(request, "upid");
                final String description = request.getParameter("description");
                final int sort = HttpUtil.getIntParameter(request, "sort");
                final boolean result = this.adminUserRoleService.add(name, upid, description, sort);
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
    
    @RequestMapping(value = { "/admin-user-role/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-role/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String name = request.getParameter("name");
                final int upid = HttpUtil.getIntParameter(request, "upid");
                final String description = request.getParameter("description");
                final int sort = HttpUtil.getIntParameter(request, "sort");
                final boolean result = this.adminUserRoleService.edit(id, name, upid, description, sort);
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
    
    @RequestMapping(value = { "/admin-user-role/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-role/update-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.adminUserRoleService.updateStatus(id, status);
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
    
    @RequestMapping(value = { "/admin-user-role/save-access" }, method = { RequestMethod.POST })
    @ResponseBody
    public void ADMIN_USER_ROLE_SAVE_ACCESS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/admin-user-role/save-access";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String ids = request.getParameter("ids");
                final boolean result = this.adminUserRoleService.saveAccess(id, ids);
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
