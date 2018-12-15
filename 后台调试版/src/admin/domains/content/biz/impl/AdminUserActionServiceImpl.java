package admin.domains.content.biz.impl;

import admin.domains.content.biz.AdminUserActionService;
import admin.domains.content.dao.AdminUserActionDao;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.vo.AdminUserActionVO;
import admin.domains.pool.AdminDataFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserActionServiceImpl
  implements AdminUserActionService
{
  @Autowired
  private AdminUserActionDao adminUserActionDao;
  @Autowired
  private AdminDataFactory adminDataFactory;
  
  public List<AdminUserActionVO> listAll()
  {
    List<AdminUserAction> alist = this.adminUserActionDao.listAll();
    List<AdminUserActionVO> list = new ArrayList();
    for (AdminUserAction tmpBean : alist) {
      list.add(new AdminUserActionVO(tmpBean, this.adminDataFactory));
    }
    return list;
  }
  
  public boolean updateStatus(int id, int status)
  {
    AdminUserAction entity = this.adminUserActionDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(status);
      boolean result = this.adminUserActionDao.update(entity);
      if (result) {
        this.adminDataFactory.initAdminUserAction();
      }
      return result;
    }
    return false;
  }
}
