package lottery.domains.content.biz.impl;

import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.Game;
import lottery.domains.content.vo.user.GameVO;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.MatchMode;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.GameDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.GameService;

@Service
public class GameServiceImpl implements GameService
{
    @Autowired
    private GameDao gameDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @Override
    public PageList search(final String gameName, final String gameCode, final Integer typeId, final Integer platformId, final Integer display, final Integer flashSupport, final Integer h5Support, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (StringUtil.isNotNull(gameName)) {
            criterions.add((Criterion)Restrictions.like("gameName", gameName, MatchMode.ANYWHERE));
        }
        if (StringUtil.isNotNull(gameCode)) {
            criterions.add((Criterion)Restrictions.like("gameCode", gameCode, MatchMode.ANYWHERE));
        }
        if (typeId != null) {
            criterions.add((Criterion)Restrictions.eq("typeId", (Object)typeId));
        }
        if (platformId != null) {
            criterions.add((Criterion)Restrictions.eq("platformId", (Object)platformId));
        }
        if (display != null) {
            criterions.add((Criterion)Restrictions.eq("display", (Object)display));
        }
        if (flashSupport != null) {
            criterions.add((Criterion)Restrictions.eq("flashSupport", (Object)flashSupport));
        }
        if (h5Support != null) {
            criterions.add((Criterion)Restrictions.eq("h5Support", (Object)h5Support));
        }
        orders.add(Order.desc("id"));
        if (typeId != null) {
            orders.add(Order.asc("sequence"));
        }
        final List<GameVO> list = new ArrayList<GameVO>();
        final PageList plist = this.gameDao.search(criterions, orders, start, limit);
        for (final Object tmpBean : plist.getList()) {
            list.add(new GameVO((Game)tmpBean, this.dataFactory));
        }
        plist.setList(list);
        return plist;
    }
    
    @Override
    public boolean add(final String gameName, final String gameCode, final Integer typeId, final Integer platformId, final String imgUrl, final int sequence, final int display, final Integer flashSupport, final Integer h5Support, final Integer progressiveSupport, final String progressiveCode) {
        final Game game = new Game();
        game.setGameName(gameName);
        game.setGameCode(gameCode);
        game.setTypeId(typeId);
        game.setPlatformId(platformId);
        game.setImgUrl(imgUrl);
        game.setSequence(sequence);
        game.setDisplay(display);
        game.setFlashSupport(flashSupport);
        game.setH5Support(h5Support);
        game.setProgressiveSupport(progressiveSupport);
        game.setProgressiveCode(progressiveCode);
        return this.gameDao.add(game);
    }
    
    @Override
    public Game getById(final int id) {
        return this.gameDao.getById(id);
    }
    
    @Override
    public Game getByGameName(final String gameName) {
        return this.gameDao.getByGameName(gameName);
    }
    
    @Override
    public Game getByGameCode(final String gameCode) {
        return this.gameDao.getByGameCode(gameCode);
    }
    
    @Override
    public boolean deleteById(final int id) {
        return this.gameDao.deleteById(id);
    }
    
    @Override
    public boolean update(final int id, final String gameName, final String gameCode, final Integer typeId, final Integer platformId, final String imgUrl, final Integer sequence, final Integer display, final Integer flashSupport, final Integer h5Support, final Integer progressiveSupport, final String progressiveCode) {
        final Game game = this.getById(id);
        game.setGameName(gameName);
        game.setGameCode(gameCode);
        game.setTypeId(typeId);
        game.setPlatformId(platformId);
        game.setImgUrl(imgUrl);
        game.setSequence(sequence);
        game.setDisplay(display);
        game.setFlashSupport(flashSupport);
        game.setH5Support(h5Support);
        game.setProgressiveSupport(progressiveSupport);
        game.setProgressiveCode(progressiveCode);
        return this.gameDao.update(game);
    }
    
    @Override
    public boolean updateSequence(final int id, final int sequence) {
        return this.gameDao.updateSequence(id, sequence);
    }
    
    @Override
    public boolean updateDisplay(final int id, final int display) {
        return this.gameDao.updateDisplay(id, display);
    }
}
