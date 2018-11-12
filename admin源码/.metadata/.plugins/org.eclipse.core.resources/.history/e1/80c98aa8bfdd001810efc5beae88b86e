package lottery.domains.content.biz.impl;

import org.springframework.transaction.annotation.Transactional;
import java.util.Iterator;
import org.hibernate.criterion.Disjunction;
import lottery.domains.content.entity.User;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.GameBets;
import lottery.domains.content.vo.user.GameBetsVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.GameBetsDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.GameBetsService;

@Service
public class GameBetsServiceImpl implements GameBetsService
{
    @Autowired
    private GameBetsDao gameBetsDao;
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public GameBetsVO getById(final int id) {
        final GameBets gameBets = this.gameBetsDao.getById(id);
        if (gameBets != null) {
            return new GameBetsVO(gameBets, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public PageList search(final String keyword, final String username, final Integer platformId, final String minTime, final String maxTime, final Double minMoney, final Double maxMoney, final Double minPrizeMoney, final Double maxPrizeMoney, final String gameCode, final String gameType, final String gameName, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
        }
        if (StringUtil.isNotNull(keyword)) {
            final Disjunction disjunction = Restrictions.or(new Criterion[0]);
            if (StringUtil.isInteger(keyword)) {
                disjunction.add((Criterion)Restrictions.eq("id", (Object)Integer.parseInt(keyword)));
            }
            disjunction.add((Criterion)Restrictions.like("betsId", keyword, MatchMode.ANYWHERE));
            criterions.add((Criterion)disjunction);
        }
        if (platformId != null) {
            criterions.add((Criterion)Restrictions.eq("platformId", (Object)platformId));
        }
        if (StringUtil.isNotNull(minTime)) {
            criterions.add((Criterion)Restrictions.gt("time", (Object)minTime));
        }
        if (StringUtil.isNotNull(maxTime)) {
            criterions.add((Criterion)Restrictions.lt("time", (Object)maxTime));
        }
        if (minMoney != null) {
            criterions.add((Criterion)Restrictions.ge("money", (Object)minMoney));
        }
        if (maxMoney != null) {
            criterions.add((Criterion)Restrictions.le("money", (Object)maxMoney));
        }
        if (minPrizeMoney != null) {
            criterions.add((Criterion)Restrictions.ge("prizeMoney", (Object)minPrizeMoney));
        }
        if (maxPrizeMoney != null) {
            criterions.add((Criterion)Restrictions.le("prizeMoney", (Object)maxPrizeMoney));
        }
        if (StringUtils.isNotEmpty(gameCode)) {
            criterions.add((Criterion)Restrictions.like("gameCode", gameCode, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotEmpty(gameType)) {
            criterions.add((Criterion)Restrictions.like("gameType", gameType, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotEmpty(gameName)) {
            criterions.add((Criterion)Restrictions.like("gameName", gameName, MatchMode.ANYWHERE));
        }
        orders.add(Order.desc("id"));
        final List<GameBetsVO> list = new ArrayList<GameBetsVO>();
        final PageList pList = this.gameBetsDao.find(criterions, orders, start, limit);
        for (final Object tmpBean : pList.getList()) {
            final GameBetsVO tmpVO = new GameBetsVO((GameBets)tmpBean, this.lotteryDataFactory);
            list.add(tmpVO);
        }
        pList.setList(list);
        return pList;
    }
    
    @Override
    public double[] getTotalMoney(final String keyword, final String username, final Integer platformId, final String minTime, final String maxTime, final Double minMoney, final Double maxMoney, final Double minPrizeMoney, final Double maxPrizeMoney, final String gameCode, final String gameType, final String gameName) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.gameBetsDao.getTotalMoney(keyword, userId, platformId, minTime, maxTime, minMoney, maxMoney, minPrizeMoney, maxPrizeMoney, gameCode, gameType, gameName);
    }
    
    @Transactional(readOnly = true)
    @Override
    public double getBillingOrder(final int userId, final String startTime, final String endTime) {
        return this.gameBetsDao.getBillingOrder(userId, startTime, endTime);
    }
}
