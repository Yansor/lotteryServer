package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.PageList;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.GameDao;
import lottery.domains.content.entity.Game;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameDaoImpl
  implements GameDao
{
  private final String tab = Game.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<Game> superDao;
  
  public PageList search(List<Criterion> criterions, List<Order> orders, int start, int limit)
  {
    String propertyName = "id";
    return this.superDao.findPageList(Game.class, propertyName, criterions, orders, start, limit);
  }
  
  public boolean add(Game game)
  {
    return this.superDao.save(game);
  }
  
  public Game getById(int id)
  {
    String hql = "from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return (Game)this.superDao.unique(hql, values);
  }
  
  public Game getByGameName(String gameName)
  {
    String hql = "from " + this.tab + " where gameName = ?0";
    Object[] values = { gameName };
    return (Game)this.superDao.unique(hql, values);
  }
  
  public Game getByGameCode(String gameCode)
  {
    String hql = "from " + this.tab + " where gameCode = ?0";
    Object[] values = { gameCode };
    return (Game)this.superDao.unique(hql, values);
  }
  
  public boolean deleteById(int id)
  {
    String hql = "delete from " + this.tab + " where id = ?0";
    Object[] values = { Integer.valueOf(id) };
    return this.superDao.delete(hql, values);
  }
  
  public boolean update(Game game)
  {
    return this.superDao.update(game);
  }
  
  public boolean updateSequence(int id, int sequence)
  {
    String hql = "update " + this.tab + " set sequence = ?0 where id = ?1";
    Object[] values = { Integer.valueOf(sequence), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
  
  public boolean updateDisplay(int id, int display)
  {
    String hql = "update " + this.tab + " set display = ?0 where id = ?1";
    Object[] values = { Integer.valueOf(display), Integer.valueOf(id) };
    return this.superDao.update(hql, values);
  }
}
