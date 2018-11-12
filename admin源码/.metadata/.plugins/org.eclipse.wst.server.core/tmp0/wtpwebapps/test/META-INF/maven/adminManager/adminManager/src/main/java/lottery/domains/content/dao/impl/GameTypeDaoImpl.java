package lottery.domains.content.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.GameType;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.GameTypeDao;

@Repository
public class GameTypeDaoImpl implements GameTypeDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<GameType> superDao;
    
    public GameTypeDaoImpl() {
        this.tab = GameType.class.getSimpleName();
    }
    
    @Override
    public List<GameType> listAll() {
        final String hql = "from " + this.tab + " order by platformId asc,sequence desc";
        return this.superDao.list(hql);
    }
}
