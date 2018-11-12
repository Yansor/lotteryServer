package lottery.domains.content.biz.impl;

import java.util.Iterator;
import lottery.domains.content.entity.User;
import java.util.List;
import lottery.domains.content.entity.UserBetsOriginal;
import lottery.domains.content.vo.user.UserBetsOriginalVO;
import java.util.ArrayList;
import javautils.StringUtil;
import javautils.jdbc.PageList;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserBetsOriginalDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserBetsOriginalService;

@Service
public class UserBetsOriginalServiceImpl implements UserBetsOriginalService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBetsOriginalDao uBetsOriginalDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    @Override
    public PageList search(final String keyword, final String username, final Integer utype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status, final int start, final int limit) {
        boolean isSearch = true;
        final StringBuilder sqlStr = new StringBuilder();
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                sqlStr.append(" and b.user_id = ").append(user.getId());
            }
            else {
                isSearch = false;
            }
        }
        if (StringUtil.isNotNull(keyword)) {
            if (StringUtil.isInteger(keyword)) {
                sqlStr.append("\tand b.id = ").append(Integer.parseInt(keyword));
            }
            else {
                sqlStr.append("\tand b.billno = ").append(keyword);
            }
        }
        if (type != null) {
            sqlStr.append("\tand b.type = ").append((int)type);
        }
        if (lotteryId != null) {
            sqlStr.append("\tand b.lottery_id = ").append((int)lotteryId);
        }
        if (StringUtil.isNotNull(expect)) {
            sqlStr.append("\tand b.expect = ").append(expect);
        }
        if (ruleId != null) {
            sqlStr.append("\tand b.rule_id = ").append(ruleId);
        }
        if (StringUtil.isNotNull(minTime)) {
            sqlStr.append("  and b.time > ").append("'" + minTime + "'");
        }
        if (StringUtil.isNotNull(maxTime)) {
            sqlStr.append("  and b.time < ").append("'" + maxTime + "'");
        }
        if (StringUtil.isNotNull(minPrizeTime)) {
            sqlStr.append("  and b.prize_time > ").append("'" + minPrizeTime + "'");
        }
        if (StringUtil.isNotNull(maxPrizeTime)) {
            sqlStr.append("  and b.prize_time < ").append("'" + maxPrizeTime + "'");
        }
        if (minMoney != null) {
            sqlStr.append("  and b.money >= ").append((double)minMoney);
        }
        if (maxMoney != null) {
            sqlStr.append("  and b.money <= ").append((double)maxMoney);
        }
        if (minMultiple != null) {
            sqlStr.append("  and b.multiple >= ").append((double)minMultiple);
        }
        if (maxMultiple != null) {
            sqlStr.append("  and b.multiple <= ").append((double)maxMultiple);
        }
        if (minPrizeMoney != null) {
            sqlStr.append("  and b.prize_money >= ").append((double)minPrizeMoney);
        }
        if (maxPrizeMoney != null) {
            sqlStr.append("  and b.prize_money >= ").append((double)maxPrizeMoney);
        }
        if (status != null) {
            sqlStr.append("  and b.status = ").append((int)status);
        }
        if (utype != null) {
            sqlStr.append("  and u.type = ").append(utype);
        }
        else {
            sqlStr.append("  and u.upid != ").append(0);
        }
        sqlStr.append("  order by  b.id  desc  ");
        if (isSearch) {
            final List<UserBetsOriginalVO> list = new ArrayList<UserBetsOriginalVO>();
            final PageList pList = this.uBetsOriginalDao.find(sqlStr.toString(), start, limit);
            for (final Object tmpBean : pList.getList()) {
                final UserBetsOriginalVO tmpVO = new UserBetsOriginalVO((UserBetsOriginal)tmpBean, this.lotteryDataFactory, false);
                list.add(tmpVO);
            }
            pList.setList(list);
            return pList;
        }
        return null;
    }
    
    @Override
    public UserBetsOriginalVO getById(final int id) {
        final UserBetsOriginal entity = this.uBetsOriginalDao.getById(id);
        if (entity != null) {
            final UserBetsOriginalVO bean = new UserBetsOriginalVO(entity, this.lotteryDataFactory, true);
            return bean;
        }
        return null;
    }
    
    @Override
    public double[] getTotalMoney(final String keyword, final String username, final Integer utype, final Integer type, final Integer lotteryId, final String expect, final Integer ruleId, final String minTime, final String maxTime, final String minPrizeTime, final String maxPrizeTime, final Double minMoney, final Double maxMoney, final Integer minMultiple, final Integer maxMultiple, final Double minPrizeMoney, final Double maxPrizeMoney, final Integer status) {
        Integer userId = null;
        if (StringUtil.isNotNull(username)) {
            final User user = this.uDao.getByUsername(username);
            if (user != null) {
                userId = user.getId();
            }
        }
        return this.uBetsOriginalDao.getTotalMoney(keyword, userId, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status);
    }
}
