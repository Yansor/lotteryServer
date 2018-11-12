package lottery.domains.content.biz.impl;

import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.Iterator;
import lottery.domains.content.vo.user.UserBetsLimitVO;
import org.hibernate.criterion.Restrictions;
import javautils.StringUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import javautils.jdbc.PageList;
import java.util.List;
import lottery.domains.content.entity.User;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import lottery.domains.content.entity.UserBetsLimit;
import javautils.redis.JedisTemplate;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserBetsLimitDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBetsLimitService;

@Service
public class UserBetsLimitServiceImpl implements UserBetsLimitService
{
    @Autowired
    private UserBetsLimitDao userBetsLimitDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private JedisTemplate jedisTemplate;
    private static final String USER_LIMIT_KEY = "USER:LIMIT:";
    
    @Override
    public boolean addUserBetsLimit(final String username, final int lotteryId, final double maxBet, final String source, final double maxPrize) {
        final User user = this.userDao.getByUsername(username);
        if (source.equals("user") && user == null) {
            throw new IllegalArgumentException("用户名不存在!");
        }
        final UserBetsLimit userBetsLimit = new UserBetsLimit();
        if (user != null) {
            userBetsLimit.setUserId(user.getId());
        }
        else {
            userBetsLimit.setUserId(0);
        }
        userBetsLimit.setLotteryId(lotteryId);
        userBetsLimit.setMaxBet(maxBet);
        userBetsLimit.setMaxPrize(maxPrize);
        final boolean saved = this.userBetsLimitDao.save(userBetsLimit);
        if (saved) {
            List<UserBetsLimit> limits = new ArrayList<UserBetsLimit>();
            final String oldValue = this.jedisTemplate.get("USER:LIMIT:" + userBetsLimit.getUserId());
            if (StringUtils.isNotEmpty(oldValue)) {
                limits = (List<UserBetsLimit>)JSON.parseArray(oldValue, (Class)UserBetsLimit.class);
            }
            limits.add(userBetsLimit);
            this.jedisTemplate.set("USER:LIMIT:" + userBetsLimit.getUserId(), JSON.toJSONString((Object)limits));
        }
        return this.userBetsLimitDao.save(userBetsLimit);
    }
    
    @Override
    public PageList search(final String username, final int start, final int limit, final boolean queryGobalSetting) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("id"));
        if (StringUtil.isNotNull(username)) {
            final User user = this.userDao.getByUsername(username);
            if (user != null) {
                criterions.add((Criterion)Restrictions.eq("userId", (Object)user.getId()));
            }
            else if (!queryGobalSetting && user == null) {
                return null;
            }
        }
        if (queryGobalSetting) {
            criterions.add((Criterion)Restrictions.eq("userId", (Object)0));
        }
        else {
            criterions.add((Criterion)Restrictions.ne("userId", (Object)0));
        }
        final PageList pageList = this.userBetsLimitDao.find(criterions, orders, start, limit);
        final List<UserBetsLimitVO> data = new ArrayList<UserBetsLimitVO>();
        if (pageList != null) {
            for (final Object o : pageList.getList()) {
                data.add(new UserBetsLimitVO((UserBetsLimit)o, this.lotteryDataFactory));
            }
            pageList.setList(data);
        }
        return pageList;
    }
    
    @Override
    public boolean updateUserBetsLimit(final UserBetsLimit ubLimit) {
        final boolean result = this.userBetsLimitDao.update(ubLimit);
        if (result) {
            List<UserBetsLimit> limits = new ArrayList<UserBetsLimit>();
            final String oldValue = this.jedisTemplate.get("USER:LIMIT:" + ubLimit.getUserId());
            if (StringUtils.isNotEmpty(oldValue)) {
                limits = (List<UserBetsLimit>)JSON.parseArray(oldValue, (Class)UserBetsLimit.class);
                if (CollectionUtils.isEmpty((Collection)limits)) {
                    limits = new ArrayList<UserBetsLimit>();
                    limits.add(ubLimit);
                }
                else {
                    for (final UserBetsLimit limit : limits) {
                        if (limit.getLotteryId() == ubLimit.getLotteryId()) {
                            limit.setMaxBet(ubLimit.getMaxBet());
                            limit.setMaxPrize(ubLimit.getMaxPrize());
                            break;
                        }
                    }
                }
            }
            else {
                limits.add(ubLimit);
            }
            this.jedisTemplate.set("USER:LIMIT:" + ubLimit.getUserId(), JSON.toJSONString((Object)limits));
        }
        return result;
    }
    
    @Override
    public UserBetsLimitVO getById(final int id) {
        final UserBetsLimit userBetsLimit = this.userBetsLimitDao.getById(id);
        if (userBetsLimit != null) {
            return new UserBetsLimitVO(userBetsLimit, this.lotteryDataFactory);
        }
        return null;
    }
    
    @Override
    public boolean deleteUserBetsLimit(final int id) {
        final UserBetsLimit userBetsLimit = this.userBetsLimitDao.getById(id);
        this.jedisTemplate.del("USER:LIMIT:" + userBetsLimit.getUserId());
        return this.userBetsLimitDao.delete(id);
    }
    
    @Override
    public boolean addOrUpdate(final Integer id, final String username, final int lotteryId, final double maxBet, final String source, final double maxPrize) {
        if (id == null) {
            return this.addUserBetsLimit(username, lotteryId, maxBet, source, maxPrize);
        }
        final UserBetsLimit updateVO = new UserBetsLimit();
        updateVO.setId(id);
        final User user = this.userDao.getByUsername(username);
        updateVO.setLotteryId(lotteryId);
        updateVO.setUserId((user == null) ? 0 : user.getId());
        updateVO.setMaxBet(maxBet);
        updateVO.setMaxPrize(maxPrize);
        return this.updateUserBetsLimit(updateVO);
    }
}
