package lottery.domains.content.dao.impl;

import java.util.Collection;
import javautils.array.ArrayUtils;
import javautils.jdbc.PageList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.entity.User;
import javautils.jdbc.hibernate.HibernateSuperDao;
import org.springframework.stereotype.Repository;
import lottery.domains.content.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao
{
    public static final String tab;
    @Autowired
    private HibernateSuperDao<User> superDao;
    
    static {
        tab = User.class.getSimpleName();
    }
    
    @Override
    public boolean add(final User entity) {
        return this.superDao.save(entity);
    }
    
    @Override
    public boolean update(final User entity) {
        return this.superDao.update(entity);
    }
    
    @Override
    public List<User> listAll() {
        final String hql = "from " + UserDaoImpl.tab;
        return this.superDao.list(hql);
    }
    
    @Override
    public User getById(final int id) {
        final String hql = "from " + UserDaoImpl.tab + " where id = ?0";
        final Object[] values = { id };
        return (User)this.superDao.unique(hql, values);
    }
    
    @Override
    public User getByUsername(final String username) {
        final String hql = "from " + UserDaoImpl.tab + " where username = ?0";
        final Object[] values = { username };
        return (User)this.superDao.unique(hql, values);
    }
    
    @Override
    public boolean updateType(final int id, final int type) {
        final String hql = "update " + UserDaoImpl.tab + " set type = ?1 where id = ?0";
        final Object[] values = { id, type };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean unbindGoogle(final int id) {
        final String hql = "update " + UserDaoImpl.tab + " set secretKey = null,isBindGoogle = 0 where id = ?0";
        final Object[] values = { id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean resetLockTime(final int id) {
        final String hql = "update " + UserDaoImpl.tab + " set lockTime = null where id = ?0";
        final Object[] values = { id };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateWithdrawName(final int id, final String withdrawName) {
        final String hql = "update " + UserDaoImpl.tab + " set withdrawName = ?1 where id = ?0";
        final Object[] values = { id, withdrawName };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateWithdrawPassword(final int id, final String md5Pwd) {
        final String hql = "update " + UserDaoImpl.tab + " set withdrawPassword = ?1 where id = ?0";
        final Object[] values = { id, md5Pwd };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateLoginPwd(final int id, final String md5Pwd) {
        final String hql = "update " + UserDaoImpl.tab + " set password = ?1 where id = ?0";
        final Object[] values = { id, md5Pwd };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateImgPwd(final int id, final String md5Pwd) {
        final String hql = "update " + UserDaoImpl.tab + " set imgPassword = ?1 where id = ?0";
        final Object[] values = { id, md5Pwd };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateTotalMoney(final int id, final double amount) {
        final String hql = "update " + UserDaoImpl.tab + " set totalMoney = totalMoney + ?1 where id = ?0";
        final Object[] values = { id, amount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateLotteryMoney(final int id, final double amount) {
        final String hql = "update " + UserDaoImpl.tab + " set lotteryMoney = lotteryMoney + ?1 where id = ?0";
        final Object[] values = { id, amount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateLotteryMoney(final int id, final double lotteryAmount, final double freezeAmount) {
        String hql = "update " + UserDaoImpl.tab + " set lotteryMoney = lotteryMoney + ?1, freezeMoney = freezeMoney + ?2 where id = ?0";
        if (lotteryAmount < 0.0) {
            hql = String.valueOf(hql) + " and lotteryMoney >= " + -lotteryAmount;
        }
        if (freezeAmount < 0.0) {
            hql = String.valueOf(hql) + " and freezeMoney >= " + -freezeAmount;
        }
        final Object[] values = { id, lotteryAmount, freezeAmount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateBaccaratMoney(final int id, final double amount) {
        final String hql = "update " + UserDaoImpl.tab + " set baccaratMoney = baccaratMoney + ?1 where id = ?0";
        final Object[] values = { id, amount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateFreezeMoney(final int id, final double amount) {
        final String hql = "update " + UserDaoImpl.tab + " set freezeMoney = freezeMoney + ?1 where id = ?0";
        final Object[] values = { id, amount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateDividendMoney(final int id, final double amount) {
        final String hql = "update " + UserDaoImpl.tab + " set dividendMoney = dividendMoney + ?1 where id = ?0";
        final Object[] values = { id, amount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateMoney(final int id, final double totalAmount, final double lotteryAmount, final double baccaratAmount, final double freezeAmount, final double dividendAmount) {
        final String hql = "update " + UserDaoImpl.tab + " set totalMoney = totalMoney + ?1, lotteryMoney = lotteryMoney + ?2, baccaratMoney = baccaratMoney + ?3, freezeMoney = freezeMoney + ?4, dividendMoney = dividendMoney + ?5 where id = ?0";
        final Object[] values = { id, totalAmount, lotteryAmount, baccaratAmount, freezeAmount, dividendAmount };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<User> getUserLower(final int id) {
        final String hql = "from " + UserDaoImpl.tab + " where upids like ?0";
        final Object[] values = { "%[" + id + "]%" };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<User> getUserDirectLower(final int id) {
        final String hql = "from " + UserDaoImpl.tab + " where upid = ?0";
        final Object[] values = { id };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<Integer> getUserDirectLowerId(final int id) {
        final String hql = "select id from " + UserDaoImpl.tab + " where upid = ?0 and type = ?1 ";
        final Object[] values = { id, 1 };
        final List<?> objects = this.superDao.listObject(hql, values);
        final List<Integer> userIds = new ArrayList<Integer>();
        for (final Object object : objects) {
            userIds.add(Integer.valueOf(object.toString()));
        }
        return userIds;
    }
    
    @Override
    public List<User> getUserLowerWithoutCode(final int id, final int code) {
        final String hql = "from " + UserDaoImpl.tab + " where upids like ?0 and code <> ?1";
        final Object[] values = { "%[" + id + "]%", code };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<User> getUserDirectLowerWithoutCode(final int id, final int code) {
        final String hql = "from " + UserDaoImpl.tab + " where upid = ?0 and code <> ?1";
        final Object[] values = { id, code };
        return this.superDao.list(hql, values);
    }
    
    @Override
    public List<Integer> getUserDirectLowerIdWithoutCode(final int id, final int code) {
        final String hql = "select id from " + UserDaoImpl.tab + " where upid = ?0 and code <> ?1";
        final Object[] values = { id, code };
        final List<?> objects = this.superDao.listObject(hql, values);
        final List<Integer> userIds = new ArrayList<Integer>();
        for (final Object object : objects) {
            userIds.add(Integer.valueOf(object.toString()));
        }
        return userIds;
    }
    
    @Override
    public boolean updateLotteryPoint(final int id, final int code, final double lp, final int BStatus, final double nlp) {
        final String hql = "update " + UserDaoImpl.tab + " set code = ?1, locatePoint = ?2, notLocatePoint = ?3,BStatus =?4 where id = ?0";
        final Object[] values = { id, code, lp, nlp, BStatus };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateProxy(final int id, final int upid, final String upids) {
        final String hql = "update " + UserDaoImpl.tab + " set upid = ?1, upids = ?2 where id = ?0";
        final Object[] values = { id, upid, upids };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateLockTime(final int id, final String lockTime) {
        final String hql = "update " + UserDaoImpl.tab + " set lockTime = ?1 where id = ?0";
        final Object[] values = { id, lockTime };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateExtraPoint(final int id, final double point) {
        final String hql = "update " + UserDaoImpl.tab + " set extraPoint = ?1 where id = ?0";
        final Object[] values = { id, point };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateAStatus(final int id, final int status, final String message) {
        final String hql = "update " + UserDaoImpl.tab + " set AStatus = ?1, message = ?2 where id = ?0";
        final Object[] values = { id, status, message };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateBStatus(final int id, final int status, final String message) {
        final String hql = "update " + UserDaoImpl.tab + " set BStatus = ?1, message = ?2 where id = ?0";
        final Object[] values = { id, status, message };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateAllowEqualCode(final int id, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowEqualCode = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateAllowTransfers(final int id, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowTransfers = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateAllowPlatformTransfers(final int id, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowPlatformTransfers = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateAllowWithdraw(final int id, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowWithdraw = ?1 where id = ?0";
        final Object[] values = { id, status };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateVipLevel(final int id, final int level) {
        final String hql = "update " + UserDaoImpl.tab + " set vipLevel = ?1 where id = ?0";
        final Object[] values = { id, level };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public List<User> list(final List<Criterion> criterions, final List<Order> orders) {
        return this.superDao.findByCriteria(User.class, criterions, orders);
    }
    
    @Override
    public PageList search(final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        final String propertyName = "id";
        return this.superDao.findPageList(User.class, propertyName, criterions, orders, start, limit);
    }
    
    @Override
    public int getTotalUserRegist(final String sTime, final String eTime) {
        final String hql = "select count(id) from " + UserDaoImpl.tab + " where upid != ?2 and registTime >= ?0 and registTime < ?1";
        final Object[] values = { sTime, eTime, 0 };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public List<?> getDayUserRegist(final String sTime, final String eTime) {
        final String hql = "select substring(registTime, 1, 10), count(id) from " + UserDaoImpl.tab + " where registTime >= ?0 and registTime < ?1  and upid != ?2  group by substring(registTime, 1, 10)";
        final Object[] values = { sTime, eTime, 0 };
        return this.superDao.listObject(hql, values);
    }
    
    @Override
    public Object[] getTotalMoney() {
        final String hql = "select sum(totalMoney), sum(lotteryMoney), sum(baccaratMoney) from " + UserDaoImpl.tab + " where upid !=?0";
        final Object[] values = { 0 };
        return (Object[])this.superDao.unique(hql, values);
    }
    
    @Override
    public int getOnlineCount(final Integer[] ids) {
        String hql = "select count(id) from " + UserDaoImpl.tab + " where onlineStatus = 1  and upid !=0";
        if (ids != null && ids.length > 0) {
            hql = String.valueOf(hql) + " and id in (" + ArrayUtils.transInIds(ids) + ")";
        }
        final Object result = this.superDao.unique(hql);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public void updateOnlineStatusNotIn(final Collection<String> sessionIds) {
        if (sessionIds == null || sessionIds.isEmpty()) {
            return;
        }
        final String hql = "update " + UserDaoImpl.tab + " set sessionId = null, onlineStatus = 0 where sessionId not in (" + ArrayUtils.toStringWithQuote(sessionIds) + ") or sessionId is null";
        final Object[] values = new Object[0];
        this.superDao.update(hql, values);
    }
    
    @Override
    public void updateAllOffline() {
        final String hql = "update " + UserDaoImpl.tab + " set sessionId = null, onlineStatus = 0";
        final Object[] values = new Object[0];
        this.superDao.update(hql, values);
    }
    
    @Override
    public void updateOffline(final int userId) {
        final String hql = "update " + UserDaoImpl.tab + " set sessionId = null, onlineStatus = 0 where id = ?0";
        final Object[] values = { userId };
        this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateRelatedUpper(final int userId, final int relatedUpid, final double relatedPoint) {
        final String hql = "update " + UserDaoImpl.tab + " set relatedUpid = ?0,relatedPoint=?1 where id = ?2";
        final Object[] values = { relatedUpid, relatedPoint, userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateRelatedLowers(final int userId, final String relatedLowers) {
        final String hql = "update " + UserDaoImpl.tab + " set relatedLowers = ?0 where id = ?1";
        final Object[] values = { relatedLowers, userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean updateRelatedUsers(final int userId, final String relatedUserIds) {
        final String hql = "update " + UserDaoImpl.tab + " set relatedUsers = ?1 where type = 3 and id = ?0";
        final Object[] values = { userId, relatedUserIds };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean lockTeam(final int userId, final int status, final String remark) {
        final String hql = "update " + UserDaoImpl.tab + " set AStatus = ?0, message = ?1, sessionId = null, onlineStatus = 0 where upids like ?2 or id = ?3";
        final Object[] values = { status, remark, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean unLockTeam(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set AStatus = ?0, message = null where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean prohibitTeamWithdraw(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowWithdraw = ?0 where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean allowTeamWithdraw(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowWithdraw = ?0 where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean allowTeamTransfers(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowTransfers = ?0 where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean prohibitTeamTransfers(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowTransfers = ?0 where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean allowTeamPlatformTransfers(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowPlatformTransfers = ?0 where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean prohibitTeamPlatformTransfers(final int userId, final int status) {
        final String hql = "update " + UserDaoImpl.tab + " set allowPlatformTransfers = ?0 where upids like ?1 or id = ?2";
        final Object[] values = { status, "%[" + userId + "]%", userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean changeZhaoShang(final int userId, final int isCJZhaoShang) {
        final String hql = "update " + UserDaoImpl.tab + " set isCjZhaoShang = ?0 where id = ?1";
        final Object[] values = { isCJZhaoShang, userId };
        return this.superDao.update(hql, values);
    }
    
    @Override
    public boolean delete(final int userId) {
        final String hql = "delete from " + UserDaoImpl.tab + " where id = ?0";
        final Object[] values = { userId };
        return this.superDao.delete(hql, values);
    }
    
    @Override
    public int getDemoUserCount() {
        final String hql = "select count(id) from " + UserDaoImpl.tab + " where 1=1 and (nickname = ?0 and upid =0) and type = ?1";
        final Object[] values = { "试玩用户", 2 };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public int getFictitiousUserCount() {
        final String hql = "select count(id) from " + UserDaoImpl.tab + " where type = ?0";
        final Object[] values = { 4 };
        final Object result = this.superDao.unique(hql, values);
        return (result != null) ? ((Number)result).intValue() : 0;
    }
    
    @Override
    public List<User> listAllByType(final int type) {
        final String hql = "from " + UserDaoImpl.tab + " where type = ?0";
        final Object[] values = { type };
        return this.superDao.list(hql, values);
    }
}
