package admin.domains.content.biz.impl;

import java.util.Iterator;
import admin.domains.content.entity.AdminUserAction;
import java.util.ArrayList;
import admin.domains.content.vo.AdminUserActionVO;
import java.util.List;
import admin.domains.pool.AdminDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserActionDao;
import org.springframework.stereotype.Service;
import admin.domains.content.biz.AdminUserActionService;

@Service
public class AdminUserActionServiceImpl implements AdminUserActionService
{
    @Autowired
    private AdminUserActionDao adminUserActionDao;
    @Autowired
    private AdminDataFactory adminDataFactory;
    
    @Override
    public List<AdminUserActionVO> listAll() {
        final List<AdminUserAction> alist = this.adminUserActionDao.listAll();
        final List<AdminUserActionVO> list = new ArrayList<AdminUserActionVO>();
        for (final AdminUserAction tmpBean : alist) {
            list.add(new AdminUserActionVO(tmpBean, this.adminDataFactory));
        }
        return list;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final AdminUserAction entity = this.adminUserActionDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean result = this.adminUserActionDao.update(entity);
            if (result) {
                this.adminDataFactory.initAdminUserAction();
            }
            return result;
        }
        return false;
    }
}
