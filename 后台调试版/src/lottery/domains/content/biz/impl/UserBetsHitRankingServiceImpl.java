package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.UserBetsHitRankingService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.UserBetsHitRankingDao;
import lottery.domains.content.entity.UserBetsHitRanking;
import lottery.domains.content.global.DbServerSyncEnum;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBetsHitRankingServiceImpl
  implements UserBetsHitRankingService
{
  @Autowired
  private UserBetsHitRankingDao uBetsHitRankingDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public PageList search(int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    orders.add(Order.desc("prizeMoney"));
    orders.add(Order.desc("time"));
    return this.uBetsHitRankingDao.find(criterions, orders, start, limit);
  }
  
  public boolean add(String name, String username, int prizeMoney, String time, String code, String type, int platform)
  {
    UserBetsHitRanking entity = new UserBetsHitRanking(name, username, prizeMoney, time, code, type, platform);
    boolean added = this.uBetsHitRankingDao.add(entity);
    if (added) {
      this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
    }
    return added;
  }
  
  public boolean edit(int id, String name, String username, int prizeMoney, String time, String code, String type, int platform)
  {
    UserBetsHitRanking entity = this.uBetsHitRankingDao.getById(id);
    if (entity != null)
    {
      entity.setName(name);
      entity.setUsername(username);
      entity.setPrizeMoney(prizeMoney);
      entity.setTime(time);
      entity.setCode(code);
      entity.setType(type);
      entity.setPlatform(platform);
      boolean updated = this.uBetsHitRankingDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
      }
      return updated;
    }
    return false;
  }
  
  public boolean delete(int id)
  {
    boolean deleted = this.uBetsHitRankingDao.delete(id);
    if (deleted) {
      this.dbServerSyncDao.update(DbServerSyncEnum.HIT_RANKING);
    }
    return deleted;
  }
  
  public UserBetsHitRanking getById(int id)
  {
    return this.uBetsHitRankingDao.getById(id);
  }
}
