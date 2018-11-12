package lottery.domains.content.biz.impl;

import javautils.date.Moment;
import javautils.date.DateUtil;
import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.vo.user.UserCardVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserCardService;

@Service
public class UserCardServiceImpl implements UserCardService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserCardDao uCardDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String username, final String keyword, final Integer status, int start, int limit) {
        if (start < 0) {
            start = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        if (limit > 100) {
            limit = 100;
        }
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
        if (isSearch) {
            if (StringUtil.isNotNull(keyword)) {
                criterions.add((Criterion)Restrictions.or((Criterion)Restrictions.eq("cardId", (Object)keyword), (Criterion)Restrictions.eq("cardName", (Object)keyword)));
            }
            if (status != null) {
                criterions.add((Criterion)Restrictions.eq("status", (Object)status));
            }
            orders.add(Order.desc("time"));
            orders.add(Order.desc("id"));
            final List<UserCardVO> list = new ArrayList<UserCardVO>();
            final PageList pList = this.uCardDao.find(criterions, orders, start, limit);
            for (final Object tmpBean : pList.getList()) {
                list.add(new UserCardVO((UserCard)tmpBean, this.lotteryDataFactory));
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public UserCardVO getById(final int id) {
        final UserCard entity = this.uCardDao.getById(id);
        if (entity != null) {
            final UserCardVO bean = new UserCardVO(entity, this.lotteryDataFactory);
            return bean;
        }
        return null;
    }
    
    @Override
    public List<UserCardVO> getByUserId(final int id) {
        final List<UserCard> clist = this.uCardDao.getByUserId(id);
        final List<UserCardVO> list = new ArrayList<UserCardVO>();
        for (final UserCard tmpBean : clist) {
            list.add(new UserCardVO(tmpBean, this.lotteryDataFactory));
        }
        return list;
    }
    
    @Override
    public UserCard getByUserAndCardId(final int userId, final String cardId) {
        return this.uCardDao.getByUserAndCardId(userId, cardId);
    }
    
    @Override
    public boolean add(final String username, final int bankId, final String bankBranch, final String cardName, final String cardId, final int status) {
        final User uBean = this.uDao.getByUsername(username);
        if (uBean != null) {
            final List<UserCard> list = this.uCardDao.getByUserId(uBean.getId());
            if (list.size() < 5) {
                final int userId = uBean.getId();
                final String time = DateUtil.getCurrentTime();
                final String lockTime = null;
                final int lockStatus = 0;
                final UserCard entity = new UserCard(userId, bankId, bankBranch, cardName, cardId, status, time, lockTime, lockStatus);
                return this.uCardDao.add(entity);
            }
        }
        return false;
    }
    
    @Override
    public boolean edit(final int id, final int bankId, final String bankBranch, final String cardId) {
        final UserCard entity = this.uCardDao.getById(id);
        if (entity != null) {
            entity.setBankId(bankId);
            entity.setBankBranch(bankBranch);
            entity.setCardId(cardId);
            return this.uCardDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final UserCard entity = this.uCardDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            if (status >= 0) {
                entity.setLockTime(null);
            }
            else {
                final String time = new Moment().toSimpleTime();
                entity.setLockTime(time);
            }
            return this.uCardDao.update(entity);
        }
        return false;
    }
}
