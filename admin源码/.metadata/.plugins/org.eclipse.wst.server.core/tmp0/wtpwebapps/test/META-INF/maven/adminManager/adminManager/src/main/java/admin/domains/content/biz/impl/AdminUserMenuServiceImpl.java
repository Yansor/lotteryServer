package admin.domains.content.biz.impl;

import admin.domains.content.biz.utils.TreeUtil;
import admin.domains.content.entity.AdminUserMenu;
import java.util.List;
import admin.domains.pool.AdminDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserMenuDao;
import org.springframework.stereotype.Service;
import admin.domains.content.biz.AdminUserMenuService;

@Service
public class AdminUserMenuServiceImpl implements AdminUserMenuService
{
    @Autowired
    private AdminUserMenuDao adminUserMenuDao;
    @Autowired
    private AdminDataFactory adminDataFactory;
    
    @Override
    public List<AdminUserMenu> listAll() {
        final List<AdminUserMenu> mlist = this.adminUserMenuDao.listAll();
        final List<AdminUserMenu> list = TreeUtil.listMenuRoot(mlist);
        return list;
    }
    
    @Override
    public AdminUserMenu getById(final int id) {
        return this.adminUserMenuDao.getById(id);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final AdminUserMenu entity = this.adminUserMenuDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean result = this.adminUserMenuDao.update(entity);
            if (result) {
                this.adminDataFactory.initAdminUserMenu();
            }
            return result;
        }
        return false;
    }
    
    @Override
    public boolean moveUp(final int id) {
        final AdminUserMenu entity = this.adminUserMenuDao.getById(id);
        if (entity != null && entity.getSort() != 1) {
            final List<AdminUserMenu> prev = this.adminUserMenuDao.getBySortUp(entity.getUpid(), entity.getSort());
            if (prev != null && prev.size() > 0) {
                final AdminUserMenu prevAdminUserMenu = prev.get(0);
                final int adminUserMenuSort = entity.getSort() - prevAdminUserMenu.getSort();
                if (adminUserMenuSort > 1) {
                    this.adminUserMenuDao.modsort(entity.getId(), -1);
                }
                else {
                    this.adminUserMenuDao.updateSort(entity.getId(), prev.get(0).getSort());
                    this.adminUserMenuDao.updateSort(prev.get(0).getId(), entity.getSort());
                }
                this.adminDataFactory.initAdminUserMenu();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean moveDown(final int id) {
        final AdminUserMenu entity = this.adminUserMenuDao.getById(id);
        final int total = this.adminUserMenuDao.getMaxSort(entity.getUpid());
        if (entity != null && entity.getSort() != total) {
            final List<AdminUserMenu> nexts = this.adminUserMenuDao.getBySortDown(entity.getUpid(), entity.getSort());
            if (nexts != null && nexts.size() > 0) {
                final AdminUserMenu nextAdminUserMenu = nexts.get(0);
                final int nextAdminUserMenuSort = nextAdminUserMenu.getSort() - entity.getSort();
                if (nextAdminUserMenuSort > 1) {
                    this.adminUserMenuDao.modsort(entity.getId(), 1);
                }
                else {
                    this.adminUserMenuDao.updateSort(entity.getId(), nextAdminUserMenu.getSort());
                    this.adminUserMenuDao.updateSort(nextAdminUserMenu.getId(), entity.getSort());
                }
                this.adminDataFactory.initAdminUserMenu();
                return true;
            }
        }
        return false;
    }
}
