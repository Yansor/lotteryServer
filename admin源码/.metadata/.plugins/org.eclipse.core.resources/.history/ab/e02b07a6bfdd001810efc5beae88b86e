package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.LotteryType;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.LotteryTypeDao;

@Repository
public class LotteryTypeDaoImpl implements LotteryTypeDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<LotteryType> superDao;
    
    public LotteryTypeDaoImpl() {
        this.tab = LotteryType.class.getSimpleName();
    }
    
    @Override
    public List<LotteryType> listAll() {
        final String hql = "from " + this.tab + " order by sort";
        return this.superDao.list(hql);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final String hql = "update " + this.tab + " set status = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
}
