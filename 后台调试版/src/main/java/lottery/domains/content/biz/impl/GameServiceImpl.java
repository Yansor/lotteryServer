package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.GameService;
import lottery.domains.content.dao.GameDao;
import lottery.domains.content.entity.Game;
import lottery.domains.content.vo.user.GameVO;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl
  implements GameService
{
  @Autowired
  private GameDao gameDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  public PageList search(String gameName, String gameCode, Integer typeId, Integer platformId, Integer display, Integer flashSupport, Integer h5Support, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(gameName)) {
      criterions.add(Restrictions.like("gameName", gameName, MatchMode.ANYWHERE));
    }
    if (StringUtil.isNotNull(gameCode)) {
      criterions.add(Restrictions.like("gameCode", gameCode, MatchMode.ANYWHERE));
    }
    if (typeId != null) {
      criterions.add(Restrictions.eq("typeId", typeId));
    }
    if (platformId != null) {
      criterions.add(Restrictions.eq("platformId", platformId));
    }
    if (display != null) {
      criterions.add(Restrictions.eq("display", display));
    }
    if (flashSupport != null) {
      criterions.add(Restrictions.eq("flashSupport", flashSupport));
    }
    if (h5Support != null) {
      criterions.add(Restrictions.eq("h5Support", h5Support));
    }
    orders.add(Order.desc("id"));
    if (typeId != null) {
      orders.add(Order.asc("sequence"));
    }
    List<GameVO> list = new ArrayList();
    PageList plist = this.gameDao.search(criterions, orders, start, limit);
    for (Object tmpBean : plist.getList()) {
      list.add(new GameVO((Game)tmpBean, this.dataFactory));
    }
    plist.setList(list);
    return plist;
  }
  
  public boolean add(String gameName, String gameCode, Integer typeId, Integer platformId, String imgUrl, int sequence, int display, Integer flashSupport, Integer h5Support, Integer progressiveSupport, String progressiveCode)
  {
    Game game = new Game();
    game.setGameName(gameName);
    game.setGameCode(gameCode);
    game.setTypeId(typeId.intValue());
    game.setPlatformId(platformId.intValue());
    game.setImgUrl(imgUrl);
    game.setSequence(sequence);
    game.setDisplay(display);
    game.setFlashSupport(flashSupport.intValue());
    game.setH5Support(h5Support.intValue());
    game.setProgressiveSupport(progressiveSupport.intValue());
    game.setProgressiveCode(progressiveCode);
    return this.gameDao.add(game);
  }
  
  public Game getById(int id)
  {
    return this.gameDao.getById(id);
  }
  
  public Game getByGameName(String gameName)
  {
    return this.gameDao.getByGameName(gameName);
  }
  
  public Game getByGameCode(String gameCode)
  {
    return this.gameDao.getByGameCode(gameCode);
  }
  
  public boolean deleteById(int id)
  {
    return this.gameDao.deleteById(id);
  }
  
  public boolean update(int id, String gameName, String gameCode, Integer typeId, Integer platformId, String imgUrl, Integer sequence, Integer display, Integer flashSupport, Integer h5Support, Integer progressiveSupport, String progressiveCode)
  {
    Game game = getById(id);
    game.setGameName(gameName);
    game.setGameCode(gameCode);
    game.setTypeId(typeId.intValue());
    game.setPlatformId(platformId.intValue());
    game.setImgUrl(imgUrl);
    game.setSequence(sequence.intValue());
    game.setDisplay(display.intValue());
    game.setFlashSupport(flashSupport.intValue());
    game.setH5Support(h5Support.intValue());
    game.setProgressiveSupport(progressiveSupport.intValue());
    game.setProgressiveCode(progressiveCode);
    return this.gameDao.update(game);
  }
  
  public boolean updateSequence(int id, int sequence)
  {
    return this.gameDao.updateSequence(id, sequence);
  }
  
  public boolean updateDisplay(int id, int display)
  {
    return this.gameDao.updateDisplay(id, display);
  }
}
