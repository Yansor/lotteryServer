package admin.web.content;

import admin.domains.content.biz.AdminUserActionService;
import admin.domains.content.biz.utils.TreeUtil;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserActionVO;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.pool.AdminDataFactory;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminUserActionController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionService adminUserActionService;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  
  @RequestMapping(value={"/admin-user-action/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_ACTION_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user-action/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        List<AdminUserActionVO> list = this.adminUserActionService.listAll();
        JSONArray data = JSONArray.fromObject(list);
        HttpUtil.write(response, data.toString(), "text/json");
        return;
      }
      json.set(Integer.valueOf(2), "2-4");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user-action/jstree"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void ADMIN_USER_ACTION_JSTREE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    int roleId = HttpUtil.getIntParameter(request, "roleId").intValue();
    AdminUserRole adminUserRole = super.getAdminDataFactory().getAdminUserRole(roleId);
    List<AdminUserMenu> mList = new ArrayList();
    List<AdminUserAction> aList = new ArrayList();
    if (adminUserRole.getUpid() != 0)
    {
      mList = super.getAdminDataFactory().getAdminUserMenuByRoleId(adminUserRole.getUpid());
      aList = super.getAdminDataFactory().getAdminUserActionByRoleId(adminUserRole.getUpid());
    }
    else
    {
      mList = super.getAdminDataFactory().listAdminUserMenu();
      aList = super.getAdminDataFactory().listAdminUserAction();
    }
    Map<Integer, AdminUserAction> aMap = new HashMap();
    for (AdminUserAction tmpBean : aList) {
      aMap.put(Integer.valueOf(tmpBean.getId()), tmpBean);
    }
    List<AdminUserMenu> menuList = TreeUtil.listMenuRoot(mList);
    Object jsTreeList = TreeUtil.listJSTreeRoot2(menuList, aMap);
    JSONArray json = JSONArray.fromObject(jsTreeList);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/admin-user-action/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void AMDIN_USER_ACTION_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/admin-user-action/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.adminUserActionService.updateStatus(id, status);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
