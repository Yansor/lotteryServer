package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.ActivitySignRecord;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.ActivitySignRecordDao;

@Repository
public class ActivitySignRecordDaoImpl implements ActivitySignRecordDao
{
    @Autowired
    private HibernateSuperDao<ActivitySignRecord> superDao;
    
    @Override
    public PageList find(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        return this.superDao.findPageList(ActivitySignRecord.class, criterions, orders, start, limit);
    }
}
