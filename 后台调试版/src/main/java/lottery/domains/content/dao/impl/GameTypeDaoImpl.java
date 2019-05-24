package lottery.domains.content.dao.impl;

import java.util.List;
import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.GameTypeDao;
import lottery.domains.content.entity.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameTypeDaoImpl
  implements GameTypeDao
{
  private final String tab = GameType.class.getSimpleName();
  @Autowired
  private HibernateSuperDao<GameType> superDao;
  
  public List<GameType> listAll()
  {
    String hql = "from " + this.tab + " order by platformId asc,sequence desc";
    return this.superDao.list(hql);
  }
}
