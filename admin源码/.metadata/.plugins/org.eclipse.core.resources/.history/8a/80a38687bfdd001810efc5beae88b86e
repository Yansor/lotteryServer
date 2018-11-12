package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserGameWaterBill;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserGameWaterBillDao;

@Repository
public class UserGameWaterBillDaoImpl implements UserGameWaterBillDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserGameWaterBill> superDao;
    
    public UserGameWaterBillDaoImpl() {
        this.tab = UserGameWaterBill.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(UserGameWaterBill.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final UserGameWaterBill bill) {
        return this.superDao.save(bill);
    }
}
