package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.GameBetsService;
import lottery.domains.content.dao.GameBetsDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.GameBets;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.user.GameBetsVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameBetsServiceImpl
  implements GameBetsService
{
  @Autowired
  private GameBetsDao gameBetsDao;
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public GameBetsVO getById(int id)
  {
    GameBets gameBets = this.gameBetsDao.getById(id);
    if (gameBets != null) {
      return new GameBetsVO(gameBets, this.lotteryDataFactory);
    }
    return null;
  }
  
  public PageList search(String keyword, String username, Integer platformId, String minTime, String maxTime, Double minMoney, Double maxMoney, Double minPrizeMoney, Double maxPrizeMoney, String gameCode, String gameType, String gameName, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      }
    }
    if (StringUtil.isNotNull(keyword))
    {
      Disjunction disjunction = Restrictions.or(new Criterion[0]);
      if (StringUtil.isInteger(keyword)) {
        disjunction.add(Restrictions.eq("id", Integer.valueOf(Integer.parseInt(keyword))));
      }
      disjunction.add(Restrictions.like("betsId", keyword, MatchMode.ANYWHERE));
      criterions.add(disjunction);
    }
    if (platformId != null) {
      criterions.add(Restrictions.eq("platformId", Integer.valueOf(platformId.intValue())));
    }
    if (StringUtil.isNotNull(minTime)) {
      criterions.add(Restrictions.gt("time", minTime));
    }
    if (StringUtil.isNotNull(maxTime)) {
      criterions.add(Restrictions.lt("time", maxTime));
    }
    if (minMoney != null) {
      criterions.add(Restrictions.ge("money", Double.valueOf(minMoney.doubleValue())));
    }
    if (maxMoney != null) {
      criterions.add(Restrictions.le("money", Double.valueOf(maxMoney.doubleValue())));
    }
    if (minPrizeMoney != null) {
      criterions.add(Restrictions.ge("prizeMoney", Double.valueOf(minPrizeMoney.doubleValue())));
    }
    if (maxPrizeMoney != null) {
      criterions.add(Restrictions.le("prizeMoney", Double.valueOf(maxPrizeMoney.doubleValue())));
    }
    if (StringUtils.isNotEmpty(gameCode)) {
      criterions.add(Restrictions.like("gameCode", gameCode, MatchMode.ANYWHERE));
    }
    if (StringUtils.isNotEmpty(gameType)) {
      criterions.add(Restrictions.like("gameType", gameType, MatchMode.ANYWHERE));
    }
    if (StringUtils.isNotEmpty(gameName)) {
      criterions.add(Restrictions.like("gameName", gameName, MatchMode.ANYWHERE));
    }
    orders.add(Order.desc("id"));
    List<GameBetsVO> list = new ArrayList();
    PageList pList = this.gameBetsDao.find(criterions, orders, start, limit);
    for (Object tmpBean : pList.getList())
    {
      GameBetsVO tmpVO = new GameBetsVO((GameBets)tmpBean, this.lotteryDataFactory);
      list.add(tmpVO);
    }
    pList.setList(list);
    return pList;
  }
  
  public double[] getTotalMoney(String keyword, String username, Integer platformId, String minTime, String maxTime, Double minMoney, Double maxMoney, Double minPrizeMoney, Double maxPrizeMoney, String gameCode, String gameType, String gameName)
  {
    Integer userId = null;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        userId = Integer.valueOf(user.getId());
      }
    }
    return this.gameBetsDao.getTotalMoney(keyword, userId, platformId, minTime, maxTime, minMoney, maxMoney, minPrizeMoney, maxPrizeMoney, gameCode, gameType, gameName);
  }
  
  @Transactional(readOnly=true)
  public double getBillingOrder(int userId, String startTime, String endTime)
  {
    return this.gameBetsDao.getBillingOrder(userId, startTime, endTime);
  }
}
