package admin.domains.content.biz.impl;

import admin.domains.content.biz.AdminUserMenuService;
import admin.domains.content.biz.utils.TreeUtil;
import admin.domains.content.dao.AdminUserMenuDao;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.pool.AdminDataFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserMenuServiceImpl
  implements AdminUserMenuService
{
  @Autowired
  private AdminUserMenuDao adminUserMenuDao;
  @Autowired
  private AdminDataFactory adminDataFactory;
  
  public List<AdminUserMenu> listAll()
  {
    List<AdminUserMenu> mlist = this.adminUserMenuDao.listAll();
    List<AdminUserMenu> list = TreeUtil.listMenuRoot(mlist);
    return list;
  }
  
  public AdminUserMenu getById(int id)
  {
    return this.adminUserMenuDao.getById(id);
  }
  
  public boolean updateStatus(int id, int status)
  {
    AdminUserMenu entity = this.adminUserMenuDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(status);
      boolean result = this.adminUserMenuDao.update(entity);
      if (result) {
        this.adminDataFactory.initAdminUserMenu();
      }
      return result;
    }
    return false;
  }
  
  public boolean moveUp(int id)
  {
    AdminUserMenu entity = this.adminUserMenuDao.getById(id);
    if ((entity != null) && (entity.getSort() != 1))
    {
      List<AdminUserMenu> prev = this.adminUserMenuDao.getBySortUp(entity.getUpid(), entity.getSort());
      if ((prev != null) && (prev.size() > 0))
      {
        AdminUserMenu prevAdminUserMenu = (AdminUserMenu)prev.get(0);
        int adminUserMenuSort = entity.getSort() - prevAdminUserMenu.getSort();
        if (adminUserMenuSort > 1)
        {
          this.adminUserMenuDao.modsort(entity.getId(), -1);
        }
        else
        {
          this.adminUserMenuDao.updateSort(entity.getId(), ((AdminUserMenu)prev.get(0)).getSort());
          
          this.adminUserMenuDao.updateSort(((AdminUserMenu)prev.get(0)).getId(), entity.getSort());
        }
        this.adminDataFactory.initAdminUserMenu();
        return true;
      }
    }
    return false;
  }
  
  public boolean moveDown(int id)
  {
    AdminUserMenu entity = this.adminUserMenuDao.getById(id);
    
    int total = this.adminUserMenuDao.getMaxSort(entity.getUpid());
    if ((entity != null) && (entity.getSort() != total))
    {
      List<AdminUserMenu> nexts = this.adminUserMenuDao.getBySortDown(entity.getUpid(), entity.getSort());
      if ((nexts != null) && (nexts.size() > 0))
      {
        AdminUserMenu nextAdminUserMenu = (AdminUserMenu)nexts.get(0);
        
        int nextAdminUserMenuSort = nextAdminUserMenu.getSort() - entity.getSort();
        if (nextAdminUserMenuSort > 1)
        {
          this.adminUserMenuDao.modsort(entity.getId(), 1);
        }
        else
        {
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
