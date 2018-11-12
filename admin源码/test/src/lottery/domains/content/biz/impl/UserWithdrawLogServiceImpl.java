package lottery.domains.content.biz.impl;

import admin.domains.content.entity.AdminUser;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserWithdrawLog;
import lottery.domains.content.vo.user.UserWithdrawLogVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserWithdrawLogDao;
import admin.domains.content.dao.AdminUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserWithdrawLogService;

@Service
public class UserWithdrawLogServiceImpl implements UserWithdrawLogService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private UserWithdrawLogDao userWithdrawLogDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String billno, final String username, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        boolean isSearch = true;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else {
                isSearch = false;
            }
        }
        criterions.add((Criterion)Restrictions.eq("billno", (Object)billno));
        orders.add(Order.desc("id"));
        if (isSearch) {
            final List<UserWithdrawLogVO> list = new ArrayList<UserWithdrawLogVO>();
            final PageList pList = this.userWithdrawLogDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                final UserWithdrawLog tWithdrawLog = (UserWithdrawLog)tmpBean;
                final AdminUser adminUser = this.adminUserDao.getById(tWithdrawLog.getAdminUserId());
                list.add(new UserWithdrawLogVO(tWithdrawLog, adminUser));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public boolean add(final UserWithdrawLog entity) {
        return this.userWithdrawLogDao.add(entity);
    }
}
