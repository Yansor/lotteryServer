package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserSecurity;
import lottery.domains.content.vo.user.UserSecurityVO;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserSecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserSecurityService;

@Service
public class UserSecurityServiceImpl implements UserSecurityService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserSecurityDao uSecurityDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String key, final int start, final int limit) {
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
        if (StringUtil.isNotNull(key)) {
            criterions.add((Criterion)Restrictions.like("key", key, MatchMode.ANYWHERE));
        }
        if (isSearch) {
            final List<UserSecurityVO> list = new ArrayList<UserSecurityVO>();
            final PageList pList = this.uSecurityDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserSecurityVO((UserSecurity)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public List<UserSecurityVO> getByUserId(final int id) {
        final List<UserSecurity> slist = this.uSecurityDao.getByUserId(id);
        final List<UserSecurityVO> list = new ArrayList<UserSecurityVO>();
        for (final UserSecurity tmpBean : slist) {
            list.add(new UserSecurityVO(tmpBean, this.lotteryDataFactory));
        }
        return list;
    }
    
    @Override
    public boolean reset(final String username) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            final boolean flag = this.uSecurityDao.delete(uBean.getId());
            return flag;
        }
        return false;
    }
}
