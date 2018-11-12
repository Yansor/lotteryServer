package lottery.domains.content.dao.impl;

import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.Game;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.GameDao;

@Repository
public class GameDaoImpl implements GameDao
{
    private final String tab;
    @Autowired
    private HibernateSuperDao<Game> superDao;
    
    public GameDaoImpl() {
        this.tab = Game.class.getSimpleName();
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(Game.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final Game game) {
        return this.superDao.save(game);
    }
    
    @Override
    public Game getById(final int id) {
        final String hql = "from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return (Game)this.superDao.unique(hql, values);
    }
    
    @Override
    public Game getByGameName(final String gameName) {
        final String hql = "from " + this.tab + " where gameName = ?0";
        final Object[] values = { gameName };
        return (Game)this.superDao.unique(hql, values);
    }
    
    @Override
    public Game getByGameCode(final String gameCode) {
        final String hql = "from " + this.tab + " where gameCode = ?0";
        final Object[] values = { gameCode };
        return (Game)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean deleteById(final int id) {
        final String hql = "delete from " + this.tab + " where id = ?0";
        final Object[] values = { id };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public boolean update(final Game game) {
        return this.superDao.update(game);
    }
    
    @Override
    public boolean updateSequence(final int id, final int sequence) {
        final String hql = "update " + this.tab + " set sequence = ?0 where id = ?1";
        final Object[] values = { sequence, id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateDisplay(final int id, final int display) {
        final String hql = "update " + this.tab + " set display = ?0 where id = ?1";
        final Object[] values = { display, id };
        return this.superDao.update(hql, values);
    }
}
