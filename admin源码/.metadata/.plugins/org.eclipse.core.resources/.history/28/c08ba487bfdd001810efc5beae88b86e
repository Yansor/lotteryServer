package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.VipBirthdayGifts;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.VipBirthdayGiftsDao;

@Repository
public class VipBirthdayGiftsDaoImpl implements VipBirthdayGiftsDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<VipBirthdayGifts> superDao;
    
    public VipBirthdayGiftsDaoImpl() {
        this.tab = VipBirthdayGifts.class.getSimpleName();
    }
    
    @Override
    public boolean add(final VipBirthdayGifts entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public VipBirthdayGifts getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (VipBirthdayGifts)this.superDao.unique(hql, values);
    }
    
    @Override
    public int getWaitTodo() {
        final String hql = "select count(id) from " + this.tab + " where status = 0";
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public boolean hasRecord(final int userId, final int year) {
        final String hql = "from " + this.tab + " where userId = ?0 and birthday like ?1";
        final Object[] values = { userId, String.valueOf(year) + "%" };
        final List<VipBirthdayGifts> list = this.superDao.list(hql, values);
        return list != null && list.size() > 0;
    }
    
    @Override
    public boolean update(final VipBirthdayGifts entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(VipBirthdayGifts.class, criterions, orders, start, limit);
    }
}
