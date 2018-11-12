package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.MatchMode;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.vo.user.UserBankcardUnbindVO;
import lottery.domains.content.entity.UserBankcardUnbindRecord;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBankcardUnbindDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBankcardUnbindService;

@Service
public class UserBankcardUnbindServiceImpl implements UserBankcardUnbindService
{
    @Autowired
    private UserBankcardUnbindDao userBankcardUnbindDao;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public boolean add(final UserBankcardUnbindRecord entity) {
        return this.userBankcardUnbindDao.add(entity);
    }
    
    @Override
    public boolean update(final UserBankcardUnbindRecord entity) {
        return this.userBankcardUnbindDao.update(entity);
    }
    
    @Override
    public boolean delByCardId(final String cardId) {
        return this.userBankcardUnbindDao.delByCardId(cardId);
    }
    
    @Override
    public boolean updateByParam(final String userIds, final String cardId, final int unbindNum, final String unbindTime) {
        return this.userBankcardUnbindDao.updateByParam(userIds, cardId, unbindNum, unbindTime);
    }
    
    @Override
    public UserBankcardUnbindVO getUnbindInfoById(final int id) {
        final UserBankcardUnbindRecord entity = this.userBankcardUnbindDao.getUnbindInfoById(id);
        if (entity != null) {
            return new UserBankcardUnbindVO(entity, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public UserBankcardUnbindVO getUnbindInfoBycardId(final String cardId) {
        final UserBankcardUnbindRecord entity = this.userBankcardUnbindDao.getUnbindInfoBycardId(cardId);
        if (entity != null) {
            return new UserBankcardUnbindVO(entity, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public PageList search(final String userNames, final String cardId, final String unbindTime, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(userNames)) {
            final User userEntity = this.uDao.getByUsername(userNames);
            if (userEntity != null) {
                criterions.add((Criterion)Restrictions.like("userIds", "#" + userEntity.getId() + "#", MatchMode.ANYWHERE));
            }
            else {
                criterions.add((Criterion)Restrictions.like("userIds", String.valueOf("0000"), MatchMode.ANYWHERE));
            }
        }
        if (StringUtil.isNotNull(cardId)) {
            criterions.add((Criterion)Restrictions.like("cardId", cardId, MatchMode.ANYWHERE));
        }
        if (StringUtil.isNotNull(unbindTime)) {
            criterions.add((Criterion)Restrictions.like("unbindTime", unbindTime, MatchMode.ANYWHERE));
        }
        orders.add(Order.desc("unbindTime"));
        orders.add(Order.desc("id"));
        final PageList pList = this.userBankcardUnbindDao.search(criterions, orders, start, limit);
        final List<UserBankcardUnbindVO> list = new ArrayList<UserBankcardUnbindVO>();
        for (final Object o : pList.getList()) {
            list.add(new UserBankcardUnbindVO((UserBankcardUnbindRecord)o, this.lotteryDataFactory));
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public List<UserBankcardUnbindVO> listAll() {
        final List<UserBankcardUnbindVO> list = new ArrayList<UserBankcardUnbindVO>();
        final List<UserBankcardUnbindRecord> clist = this.userBankcardUnbindDao.listAll();
        for (final UserBankcardUnbindRecord tmpBean : clist) {
            list.add(new UserBankcardUnbindVO(tmpBean, this.lotteryDataFactory));
        }
        return list;
    }
}
