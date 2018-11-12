package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.UserPlanInfo;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserPlanInfoDao;

@Repository
public class UserPlanInfoDaoImpl implements UserPlanInfoDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<UserPlanInfo> superDao;
    
    public UserPlanInfoDaoImpl() {
        this.tab = UserPlanInfo.class.getSimpleName();
    }
    
    @Override
    public boolean add(final UserPlanInfo entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public UserPlanInfo get(final int userId) {
        final String hql = "from " + this.tab + " where userId = ?0";
        final Object[] values = { userId };
        return (UserPlanInfo)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean update(final UserPlanInfo entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public boolean update(final int userId, final int planCount, final int prizeCount, final double totalMoney, final double totalPrize) {
        final String hql = "update " + this.tab + " set planCount = planCount + ?1, prizeCount = prizeCount + ?2, totalMoney = totalMoney + ?3, totalPrize = totalPrize + ?4 where userId = ?0";
        final Object[] values = { userId, planCount, prizeCount, totalMoney, totalPrize };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateLevel(final int userId, final int level) {
        final String hql = "update " + this.tab + " set level = ?1 where userId = ?0";
        final Object[] values = { userId, level };
        return this.superDao.update(hql, values);
    }
}
