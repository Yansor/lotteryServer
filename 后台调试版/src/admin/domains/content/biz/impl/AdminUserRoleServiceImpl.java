package admin.domains.content.biz.impl;

import admin.domains.content.biz.AdminUserRoleService;
import admin.domains.content.biz.utils.TreeUtil;
import admin.domains.content.dao.AdminUserRoleDao;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserRoleVO;
import admin.domains.pool.AdminDataFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javautils.StringUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserRoleServiceImpl
  implements AdminUserRoleService
{
  @Autowired
  private AdminUserRoleDao adminUserRoleDao;
  @Autowired
  private AdminDataFactory adminDataFactory;
  
  public void listRoleChild(AdminUserRole adminUserRole, List<AdminUserRole> alist, List<AdminUserRole> rlist)
  {
    for (AdminUserRole tmpRole : alist)
    {
      tmpRole.setMenus(null);
      tmpRole.setActions(null);
      if (tmpRole.getUpid() == adminUserRole.getId())
      {
        rlist.add(tmpRole);
        listRoleChild(tmpRole, alist, rlist);
      }
    }
  }
  
  public List<AdminUserRole> listAll(int id)
  {
    List<AdminUserRole> rlist = new ArrayList();
    AdminUserRole adminUserRole = this.adminDataFactory.getAdminUserRole(id);
    adminUserRole.setMenus(null);
    adminUserRole.setActions(null);
    adminUserRole.setUpid(0);
    rlist.add(adminUserRole);
    List<AdminUserRole> alist = this.adminDataFactory.listAdminUserRole();
    listRoleChild(adminUserRole, alist, rlist);
    return rlist;
  }
  
  public List<AdminUserRole> listTree(int id)
  {
    List<AdminUserRole> rlist = listAll(id);
    List<AdminUserRole> list = TreeUtil.listRoleRoot(rlist);
    return list;
  }
  
  public boolean add(String name, int upid, String description, int sort)
  {
    int status = 0;
    String menus = null;
    String actions = null;
    AdminUserRole entity = new AdminUserRole(name, upid, description, sort, status, menus, actions);
    boolean result = this.adminUserRoleDao.save(entity);
    if (result) {
      this.adminDataFactory.initAdminUserRole();
    }
    return result;
  }
  
  public boolean edit(int id, String name, int upid, String description, int sort)
  {
    AdminUserRole entity = this.adminUserRoleDao.getById(id);
    if (entity != null)
    {
      entity.setName(name);
      entity.setUpid(upid);
      entity.setDescription(description);
      entity.setSort(sort);
      boolean result = this.adminUserRoleDao.update(entity);
      if (result) {
        this.adminDataFactory.initAdminUserRole();
      }
      return result;
    }
    return false;
  }
  
  public boolean updateStatus(int id, int status)
  {
    AdminUserRole entity = this.adminUserRoleDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(status);
      boolean result = this.adminUserRoleDao.update(entity);
      if (result) {
        this.adminDataFactory.initAdminUserRole();
      }
      return result;
    }
    return false;
  }
  
  public AdminUserRoleVO getByName(String name)
  {
    AdminUserRole entity = this.adminUserRoleDao.getByName(name);
    if (entity != null)
    {
      AdminUserRoleVO bean = new AdminUserRoleVO(entity, this.adminDataFactory);
      return bean;
    }
    return null;
  }
  
  public AdminUserRoleVO getById(int id)
  {
    AdminUserRole entity = this.adminUserRoleDao.getById(id);
    if (entity != null)
    {
      AdminUserRoleVO bean = new AdminUserRoleVO(entity, this.adminDataFactory);
      return bean;
    }
    return null;
  }
  
  public boolean saveAccess(int id, String ids)
  {
    String[] arr = ids.split(",");
    Set<Integer> mSet = new HashSet();
    Set<Integer> aSet = new HashSet();
    String[] arrayOfString1;
    int j = (arrayOfString1 = arr).length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString1[i];
      if (StringUtil.isIntegerString(s)) {
        aSet.add(Integer.valueOf(Integer.parseInt(s)));
      }
    }
    for (Iterator localIterator = aSet.iterator(); localIterator.hasNext();)
    {
      int action = ((Integer)localIterator.next()).intValue();
      Object tmpList = this.adminDataFactory.getAdminUserMenuIdsByAction(action);
      mSet.addAll((Collection)tmpList);
    }
    AdminUserRole entity = this.adminUserRoleDao.getById(id);
    if (entity != null)
    {
      entity.setMenus(JSONArray.fromObject(mSet).toString());
      entity.setActions(JSONArray.fromObject(aSet).toString());
      boolean result = this.adminUserRoleDao.update(entity);
      if (result)
      {
        Object adminUserRoles = this.adminUserRoleDao.getByUpid(id);
        recursivelyUserRolesMenusActions((List)adminUserRoles, mSet, aSet);
        this.adminDataFactory.initAdminUserRole();
      }
      return result;
    }
    return false;
  }
  
  public void recursivelyUserRolesMenusActions(List<AdminUserRole> adminUserRoles, Set<Integer> mSet, Set<Integer> aSet)
  {
    for (AdminUserRole adminUserRole : adminUserRoles)
    {
      JSONArray jsonArrayMenus = JSONArray.fromObject(adminUserRole.getMenus());
      JSONArray jsonArrayActions = JSONArray.fromObject(adminUserRole.getActions());
      Object[] menus = jsonArrayMenus.toArray();
      Object[] arrayOfObject1;
      int j = (arrayOfObject1 = menus).length;
      for (int i = 0; i < j; i++)
      {
        Object object = arrayOfObject1[i];
        if (!mSet.contains(object)) {
          jsonArrayMenus.remove(object);
        }
      }
      Object[] actions = jsonArrayActions.toArray();
      Object[] arrayOfObject2;
      int k = (arrayOfObject2 = actions).length;
      for (j = 0; j < k; j++)
      {
        Object object = arrayOfObject2[j];
        if (!aSet.contains(object)) {
          jsonArrayActions.remove(object);
        }
      }
      adminUserRole.setMenus(jsonArrayMenus.toString());
      adminUserRole.setActions(jsonArrayActions.toString());
      this.adminUserRoleDao.update(adminUserRole);
      Object adminUserRoles1 = this.adminUserRoleDao.getByUpid(adminUserRole.getId());
      if ((adminUserRoles1 != null) && (((List)adminUserRoles1).size() > 0)) {
        recursivelyUserRolesMenusActions((List)adminUserRoles1, mSet, aSet);
      }
    }
  }
}
