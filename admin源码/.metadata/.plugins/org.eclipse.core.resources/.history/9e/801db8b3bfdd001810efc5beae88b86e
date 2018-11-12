package admin.domains.content.biz.impl;

import java.util.Set;
import net.sf.json.JSONArray;
import java.util.Collection;
import javautils.StringUtil;
import java.util.HashSet;
import admin.domains.content.vo.AdminUserRoleVO;
import admin.domains.content.biz.utils.TreeUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.pool.AdminDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserRoleDao;
import org.springframework.stereotype.Service;
import admin.domains.content.biz.AdminUserRoleService;

@Service
public class AdminUserRoleServiceImpl implements AdminUserRoleService
{
    @Autowired
    private AdminUserRoleDao adminUserRoleDao;
    @Autowired
    private AdminDataFactory adminDataFactory;
    
    public void listRoleChild(final AdminUserRole adminUserRole, final List<AdminUserRole> alist, final List<AdminUserRole> rlist) {
        for (final AdminUserRole tmpRole : alist) {
            tmpRole.setMenus(null);
            tmpRole.setActions(null);
            if (tmpRole.getUpid() == adminUserRole.getId()) {
                rlist.add(tmpRole);
                this.listRoleChild(tmpRole, alist, rlist);
            }
        }
    }
    
    @Override
    public List<AdminUserRole> listAll(final int id) {
        final List<AdminUserRole> rlist = new ArrayList<AdminUserRole>();
        final AdminUserRole adminUserRole = this.adminDataFactory.getAdminUserRole(id);
        adminUserRole.setMenus(null);
        adminUserRole.setActions(null);
        adminUserRole.setUpid(0);
        rlist.add(adminUserRole);
        final List<AdminUserRole> alist = this.adminDataFactory.listAdminUserRole();
        this.listRoleChild(adminUserRole, alist, rlist);
        return rlist;
    }
    
    @Override
    public List<AdminUserRole> listTree(final int id) {
        final List<AdminUserRole> rlist = this.listAll(id);
        final List<AdminUserRole> list = TreeUtil.listRoleRoot(rlist);
        return list;
    }
    
    @Override
    public boolean add(final String name, final int upid, final String description, final int sort) {
        final int status = 0;
        final String menus = null;
        final String actions = null;
        final AdminUserRole entity = new AdminUserRole(name, upid, description, sort, status, menus, actions);
        final boolean result = this.adminUserRoleDao.save(entity);
        if (result) {
            this.adminDataFactory.initAdminUserRole();
        }
        return result;
    }
    
    @Override
    public boolean edit(final int id, final String name, final int upid, final String description, final int sort) {
        final AdminUserRole entity = this.adminUserRoleDao.getById(id);
        if (entity != null) {
            entity.setName(name);
            entity.setUpid(upid);
            entity.setDescription(description);
            entity.setSort(sort);
            final boolean result = this.adminUserRoleDao.update(entity);
            if (result) {
                this.adminDataFactory.initAdminUserRole();
            }
            return result;
        }
        return false;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final AdminUserRole entity = this.adminUserRoleDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean result = this.adminUserRoleDao.update(entity);
            if (result) {
                this.adminDataFactory.initAdminUserRole();
            }
            return result;
        }
        return false;
    }
    
    @Override
    public AdminUserRoleVO getByName(final String name) {
        final AdminUserRole entity = this.adminUserRoleDao.getByName(name);
        if (entity != null) {
            final AdminUserRoleVO bean = new AdminUserRoleVO(entity, this.adminDataFactory);
            return bean;
        }
        return null;
    }
    
    @Override
    public AdminUserRoleVO getById(final int id) {
        final AdminUserRole entity = this.adminUserRoleDao.getById(id);
        if (entity != null) {
            final AdminUserRoleVO bean = new AdminUserRoleVO(entity, this.adminDataFactory);
            return bean;
        }
        return null;
    }
    
    @Override
    public boolean saveAccess(final int id, final String ids) {
        final String[] arr = ids.split(",");
        final Set<Integer> mSet = new HashSet<Integer>();
        final Set<Integer> aSet = new HashSet<Integer>();
        String[] array;
        for (int length = (array = arr).length, i = 0; i < length; ++i) {
            final String s = array[i];
            if (StringUtil.isIntegerString(s)) {
                aSet.add(Integer.parseInt(s));
            }
        }
        for (final int action : aSet) {
            final Set<Integer> tmpList = this.adminDataFactory.getAdminUserMenuIdsByAction(action);
            mSet.addAll(tmpList);
        }
        final AdminUserRole entity = this.adminUserRoleDao.getById(id);
        if (entity != null) {
            entity.setMenus(JSONArray.fromObject((Object)mSet).toString());
            entity.setActions(JSONArray.fromObject((Object)aSet).toString());
            final boolean result = this.adminUserRoleDao.update(entity);
            if (result) {
                final List<AdminUserRole> adminUserRoles = this.adminUserRoleDao.getByUpid(id);
                this.recursivelyUserRolesMenusActions(adminUserRoles, mSet, aSet);
                this.adminDataFactory.initAdminUserRole();
            }
            return result;
        }
        return false;
    }
    
    public void recursivelyUserRolesMenusActions(final List<AdminUserRole> adminUserRoles, final Set<Integer> mSet, final Set<Integer> aSet) {
        for (final AdminUserRole adminUserRole : adminUserRoles) {
            final JSONArray jsonArrayMenus = JSONArray.fromObject((Object)adminUserRole.getMenus());
            final JSONArray jsonArrayActions = JSONArray.fromObject((Object)adminUserRole.getActions());
            final Object[] menus = jsonArrayMenus.toArray();
            Object[] array;
            for (int length = (array = menus).length, i = 0; i < length; ++i) {
                final Object object = array[i];
                if (!mSet.contains(object)) {
                    jsonArrayMenus.remove(object);
                }
            }
            final Object[] actions = jsonArrayActions.toArray();
            Object[] array2;
            for (int length2 = (array2 = actions).length, j = 0; j < length2; ++j) {
                final Object object2 = array2[j];
                if (!aSet.contains(object2)) {
                    jsonArrayActions.remove(object2);
                }
            }
            adminUserRole.setMenus(jsonArrayMenus.toString());
            adminUserRole.setActions(jsonArrayActions.toString());
            this.adminUserRoleDao.update(adminUserRole);
            final List<AdminUserRole> adminUserRoles2 = this.adminUserRoleDao.getByUpid(adminUserRole.getId());
            if (adminUserRoles2 != null && adminUserRoles2.size() > 0) {
                this.recursivelyUserRolesMenusActions(adminUserRoles2, mSet, aSet);
            }
        }
    }
}
