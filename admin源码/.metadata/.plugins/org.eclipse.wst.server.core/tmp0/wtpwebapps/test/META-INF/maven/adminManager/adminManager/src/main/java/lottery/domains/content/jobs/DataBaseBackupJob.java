package lottery.domains.content.jobs;

import javautils.StringUtil;
import javautils.array.ArrayUtils;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import javautils.ObjectUtil;
import java.util.Iterator;
import java.util.List;
import lottery.domains.content.entity.Lottery;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import javautils.date.Moment;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.LoggerFactory;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.LotteryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DataBaseBackupJob
{
    private final Logger logger;
    boolean isRunning;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private LotteryDao lotteryDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    private final int LIMIT_UNIT_COUNT = 500;
    private final int USER_BETS_REMAIN_MONTH = 1;
    private final int USER_RECHARGE_UNPAID_REMAIN_DAYS = 2;
    private final int USER_RECHARGE_REMAIN_MONTH = 3;
    private final int USER_HIGH_PRIZE_REMAIN_MONTH = 1;
    private final int USER_BILL_REMAIN_MONTH = 1;
    private final int USER_BILL_BACKUP_REMAIN_MONTH = 6;
    private final int OUT_LOTTERY_REMAIN_COUNT = 200;
    private final int SELF_LOTTERY_REMAIN_COUNT = 3000;
    private final int JSMMC_LOTTERY_REMAIN_COUNT = 100;
    private final int GAME_BETS_REMAIN_MONTH = 1;
    
    public DataBaseBackupJob() {
        this.logger = LoggerFactory.getLogger((Class)DataBaseBackupJob.class);
        this.isRunning = false;
    }
    
    @Scheduled(cron = "0 30 5 * * *")
    public void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            try {
                this.backupUserBets();
                this.deleteDemoUserBetsOriginal();
                this.deleteUserBetsOriginal();
                this.backupGameBets();
                this.backupUserBill();
                this.deleteSysMessage();
                this.backupUserMessage();
                this.deleteOpenCode();
                this.backupAdminUserLog();
                this.backupAdminActionLog();
                this.backupUserLoginLog();
                this.backupUserActionLog();
                this.backupUserRechargeUnPaid();
                this.backupUserRecharge();
                this.backupUserWithdraw();
                this.backupUserTransfer();
                this.backupRedPacketRainBill();
                this.backupRedPacketRainTime();
                this.deleteUserHighPrize();
                this.deleteDemoUserHighPrize();
                this.backupDailySettleBill();
                this.backupDividendBill();
                this.backupGameWaterBill();
                this.backupGameDividendBill();
                this.backupUserMainReport();
                this.backupUserLotteryReport();
                this.backupUserLotteryDetailsReport();
                this.backupUserGameReport();
            }
            catch (Exception e) {
                this.logger.error("备份数据出错", (Throwable)e);
                return;
            }
            finally {
                this.isRunning = false;
            }
            this.isRunning = false;
        }
    }
    
    private void backupUserBets() {
        final String realRemainTime = new Moment().subtract(1, "months").toSimpleTime();
        final String backupRemainTime = new Moment().subtract(3, "months").toSimpleTime();
        final String realWhere = "b.time < '" + realRemainTime + "' AND b.status NOT IN (0)";
        final String backupWhere = "b.time < '" + backupRemainTime + "'";
        final String demRealRemainTime = new Moment().subtract(1, "days").toSimpleTime();
        final String demorealWhere = "b.time < '" + demRealRemainTime + "' AND b.status NOT IN (0)";
        this.delFictitiousUserBackupBase("虚拟用户彩票投注记录", realWhere, realWhere, "user_bets");
        this.delDemoUserBackupBase("试玩用户彩票投注记录", demorealWhere, demorealWhere, "user_bets");
        this.commonUserBackupBase("彩票投注记录", realWhere, backupWhere, "user_bets");
    }
    
    private void backupUserBill() {
        this.commonNoDemoBackupByMonth("账单记录", 1, 3, "user_bill");
    }
    
    private void deleteDemoUserBetsOriginal() {
        this.logger.info("删除试玩用户原始投注订单...");
        final String time = new Moment().subtract(1, "days").toSimpleTime();
        final Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        try {
            final String deleteSql = "DELETE FROM `ecai`.`user_bets_original`  WHERE user_id in (select id from `ecai`.`user` where nickname = '试玩用户' ) and  `time` < '" + time + "'";
            final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
            final int deleteCount = deleteQuery.executeUpdate();
            if (deleteCount > 0) {
                this.logger.info("删除原始投注数据完成，共删除" + deleteCount + "条数据。");
                session.getTransaction().commit();
            }
        }
        catch (Exception e) {
            this.logger.error("删除原始投注数据出错...回滚");
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private void deleteUserBetsOriginal() {
        this.logger.info("开始删除原始投注数据...");
        final String time = new Moment().subtract(31, "days").toSimpleTime();
        final String oldDbTab = "`ecai`.`user_bets_original`";
        final Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        try {
            final String deleteSql = "DELETE FROM `ecai`.`user_bets_original` WHERE `time` < '" + time + "'";
            final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
            final int deleteCount = deleteQuery.executeUpdate();
            if (deleteCount > 0) {
                this.logger.info("删除原始投注数据完成，共删除" + deleteCount + "条数据。");
                session.getTransaction().commit();
            }
        }
        catch (Exception e) {
            this.logger.error("删除原始投注数据出错...回滚");
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private void deleteSysMessage() {
        this.logger.info("开始删除无用通知数据...");
        final String time = new Moment().subtract(7, "days").toSimpleTime();
        final Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            final String deleteSql = "DELETE FROM `ecai`.`user_sys_message` WHERE `time` < '" + time + "'";
            final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
            final int deleteCount = deleteQuery.executeUpdate();
            if (deleteCount > 0) {
                this.logger.info("共删除" + deleteCount + "条无用系统通知数据。");
                session.getTransaction().commit();
            }
            else {
                this.logger.info("无可用通知数据删除");
            }
        }
        catch (Exception e) {
            this.logger.info("删除系统通知出错...回滚");
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private void backupUserMessage() {
        this.commonBackupByMonth("用户消息", 3, 12, "user_message");
    }
    
    private void deleteOpenCode() {
        this.logger.info("开始删除开奖号码数据...");
        final List<Lottery> lotteries = this.lotteryDao.listAll();
        for (final Lottery lottery : lotteries) {
            if (lottery.getId() == 117) {
                this.delOpenCodeByJSMMC(lottery);
            }
            else {
                int remainCount = (lottery.getSelf() == 1) ? 3000 : 200;
                if (lottery.getId() == 127) {
                    remainCount = 400;
                }
                this.delOpenCodeByLottery(lottery, null, remainCount);
            }
        }
    }
    
    private void delOpenCodeByLottery(final Lottery lottery, final Integer userId, final int remainCount) {
        final Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            final String oldDbTab = "`ecai`.`lottery_open_code`";
            String countSql = "SELECT COUNT(`id`) FROM " + oldDbTab + " WHERE `lottery` = '" + lottery.getShortName() + "'";
            if (userId != null) {
                countSql = String.valueOf(countSql) + " AND `user_id` = " + userId;
            }
            final SQLQuery countQuery = session.createSQLQuery(countSql);
            final Object countResult = countQuery.uniqueResult();
            final Integer count = ObjectUtil.toInt(countResult);
            if (count == null || count <= remainCount) {
                return;
            }
            String selectSql = "SELECT MIN(t1.`id`) FROM (SELECT `id` FROM " + oldDbTab + " WHERE `lottery` = '" + lottery.getShortName() + "'";
            if (userId != null) {
                selectSql = String.valueOf(selectSql) + " AND `user_id` = " + userId;
            }
            selectSql = String.valueOf(selectSql) + " ORDER BY `id` DESC LIMIT 0, " + remainCount + ") t1";
            final SQLQuery selectQuery = session.createSQLQuery(selectSql);
            final Object selectResult = selectQuery.uniqueResult();
            final Integer targetId = ObjectUtil.toInt(selectResult);
            if (targetId != null && targetId > 0) {
                String deleteSql = "DELETE FROM " + oldDbTab + " WHERE `id` < " + targetId + " AND `lottery` = '" + lottery.getShortName() + "'";
                if (userId != null) {
                    deleteSql = String.valueOf(deleteSql) + " AND `user_id` = " + userId;
                }
                final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
                final int deleteCount = deleteQuery.executeUpdate();
                if (deleteCount > 0) {
                    this.logger.info("删除{}开奖号码数据完成，共删除" + deleteCount + "条数据。", (Object)lottery.getShortName());
                    session.getTransaction().commit();
                }
                else {
                    this.logger.info("{}无可用数据删除", (Object)lottery.getShortName());
                }
            }
        }
        catch (Exception e) {
            this.logger.error("删除{}开奖号码数据完成...回滚", (Object)lottery.getShortName());
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private void delOpenCodeByJSMMC(final Lottery lottery) {
        if (lottery.getId() != 117) {
            return;
        }
        final List<Integer> userIds = this.getJSMMCUserIds();
        if (CollectionUtils.isEmpty((Collection)userIds)) {
            return;
        }
        for (final Integer userId : userIds) {
            this.delOpenCodeByLottery(lottery, userId, 100);
        }
        final Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            final String oldDbTab = "`ecai`.`lottery_open_code`";
            final String deleteSql = "DELETE FROM " + oldDbTab + " WHERE `lottery` = 'jsmmc' AND `time` < '" + new Moment().subtract(30, "days").toSimpleTime() + "'";
            final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
            final int deleteCount = deleteQuery.executeUpdate();
            if (deleteCount > 0) {
                this.logger.info("删除急速秒秒彩一个月前数据完成，共删除" + deleteCount + "条数据。");
                session.getTransaction().commit();
            }
            else {
                this.logger.info("{}无可用数据删除", (Object)lottery.getShortName());
            }
        }
        catch (Exception e) {
            this.logger.error("删除急速秒秒彩一个月前数据完成...回滚");
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private List<Integer> getJSMMCUserIds() {
        final List<Integer> userIds = new ArrayList<Integer>();
        final Session session = this.sessionFactory.openSession();
        try {
            final SQLQuery selectQuery = session.createSQLQuery("SELECT DISTINCT user_id FROM lottery_open_code WHERE user_id IS NOT NULL AND lottery = 'jsmmc'");
            final List list = selectQuery.list();
            for (final Object object : list) {
                userIds.add(Integer.valueOf(object.toString()));
            }
        }
        catch (Exception e) {
            this.logger.error("转移开奖号码出错", (Throwable)e);
            return userIds;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
        return userIds;
    }
    
    private void backupAdminUserLog() {
        this.commonBackupByMonth("后台操作日志", 6, 12, "admin_user_log");
    }
    
    private void backupAdminActionLog() {
        this.commonBackupByMonth("后台详细日志", 6, 12, "admin_user_action_log");
    }
    
    private void backupUserLoginLog() {
        this.commonBackupByMonth("前台用户登录日志", 12, 36, "user_login_log");
    }
    
    private void backupUserActionLog() {
        this.commonBackupByMonth("前台用户操作日志", 12, 36, "user_action_log");
    }
    
    private void backupGameBets() {
        this.commonBackupByMonth("用户第三方游戏投注记录", 3, 3, "game_bets");
    }
    
    private void deleteUserHighPrize() {
        final String name = "大额中奖记录";
        final String time = new Moment().subtract(1, "months").toSimpleTime();
        this.logger.info("开始删除{}...", (Object)name);
        final Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            final String deleteSql = "DELETE FROM `ecai`.`user_high_prize` WHERE `time` < '" + time + "'";
            final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
            final int deleteCount = deleteQuery.executeUpdate();
            if (deleteCount > 0) {
                this.logger.info("删除{}，共删除{}条数据。", (Object)name, (Object)deleteCount);
                session.getTransaction().commit();
            }
            else {
                this.logger.info("无可用删除", (Object)name);
            }
        }
        catch (Exception e) {
            this.logger.error("删除" + name + "记录出错...回滚");
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private void deleteDemoUserHighPrize() {
        final String name = "大额中奖记录";
        final String time = new Moment().subtract(1, "days").toSimpleTime();
        this.logger.info("开始删除{}...", (Object)name);
        final Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            final String deleteSql = "DELETE FROM `ecai`.`user_high_prize` WHERE user_id in (select id from `ecai`.`user` where nickname = '试玩用户') and  `time` < '" + time + "'";
            final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
            final int deleteCount = deleteQuery.executeUpdate();
            if (deleteCount > 0) {
                this.logger.info("删除{}，共删除{}条数据。", (Object)name, (Object)deleteCount);
                session.getTransaction().commit();
            }
            else {
                this.logger.info("无可用删除", (Object)name);
            }
        }
        catch (Exception e) {
            this.logger.error("删除" + name + "记录出错...回滚");
            session.getTransaction().rollback();
            return;
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    private void backupUserRechargeUnPaid() {
        final String realRemainTime = new Moment().subtract(7, "days").toSimpleTime();
        final String realWhere = "`time` < '" + realRemainTime + "' AND `status`=0";
        final String backupWhere = null;
        this.commonBackupBase("用户未支付充值记录", realWhere, backupWhere, "user_recharge");
    }
    
    private void backupUserRecharge() {
        this.commonBackupByMonth("用户充值记录", 3, 12, "user_recharge");
    }
    
    private void backupUserWithdraw() {
        this.commonBackupByMonth("用户取现记录", 3, 12, "user_withdraw");
    }
    
    private void backupUserTransfer() {
        this.commonBackupByMonth("用户转账记录", 1, 12, "user_transfers");
    }
    
    private void backupRedPacketRainBill() {
        this.commonBackupByMonth("红包雨账单", 1, 12, "activity_red_packet_rain_bill");
    }
    
    private void backupRedPacketRainTime() {
        this.commonBackupByMonth("红包雨时间配置", 1, 12, "activity_red_packet_rain_time", "end_time");
    }
    
    private void backupDailySettleBill() {
        this.commonBackupByMonth("彩票日结账单", 2, 12, "user_daily_settle_bill", "settle_time");
    }
    
    private void backupDividendBill() {
        this.commonBackupByMonth("彩票分红账单", 2, 12, "user_dividend_bill", "settle_time");
    }
    
    private void backupGameWaterBill() {
        this.commonBackupByMonth("老虎机真人体育返水账单", 2, 12, "user_game_water_bill", "settle_time");
    }
    
    private void backupGameDividendBill() {
        this.commonBackupByMonth("老虎机真人体育分红账单", 2, 12, "user_game_dividend_bill", "settle_time");
    }
    
    private void backupUserMainReport() {
        this.commonBackupByMonth("主账户报表", 2, 12, "user_main_report");
    }
    
    private void backupUserLotteryReport() {
        this.commonBackupByMonth("彩票报表", 2, 12, "user_lottery_report");
    }
    
    private void backupUserLotteryDetailsReport() {
        this.commonBackupByMonth("彩票详情报表", 2, 12, "user_lottery_details_report");
    }
    
    private void backupUserGameReport() {
        this.commonBackupByMonth("老虎机真人体育报表", 2, 12, "user_game_report");
    }
    
    private void commonNoDemoBackupByMonth(final String name, final int realRemainMonth, final int backupRemainMonth, final String table) {
        final String realRemainTime = new Moment().subtract(realRemainMonth, "months").toSimpleDate();
        final String backupRemainTime = new Moment().subtract(backupRemainMonth, "months").toSimpleDate();
        final String demRealRemainTime = new Moment().subtract(1, "days").toSimpleTime();
        final String demorealWhere = "b.time < '" + demRealRemainTime + "' ";
        this.delDemoUserBackupBase(name, demorealWhere, null, table);
        this.delFictitiousUserBackupBase(name, realRemainTime, null, table);
        this.commonNoDemoBackupByTime(name, realRemainTime, backupRemainTime, table, "b.time");
    }
    
    private void commonBackupByMonth(final String name, final int realRemainMonth, final int backupRemainMonth, final String table) {
        final String realRemainTime = new Moment().subtract(realRemainMonth, "months").toSimpleDate();
        final String backupRemainTime = new Moment().subtract(backupRemainMonth, "months").toSimpleDate();
        this.commonBackupByTime(name, realRemainTime, backupRemainTime, table, "time");
    }
    
    private void commonBackupByMonth(final String name, final int realRemainMonth, final int backupRemainMonth, final String table, final String timeField) {
        final String realRemainTime = new Moment().subtract(realRemainMonth, "months").toSimpleDate();
        final String backupRemainTime = new Moment().subtract(backupRemainMonth, "months").toSimpleDate();
        this.commonBackupByTime(name, realRemainTime, backupRemainTime, table, timeField);
    }
    
    private void commonNoDemoBackupByTime(final String name, final String realRemainTime, final String backupRemainTime, final String table, final String timeField) {
        final String realWhere = timeField + " < '" + realRemainTime + "'";
        final String backupWhere = timeField + " < '" + backupRemainTime + "'";
        this.commonUserBackupBase(name, realWhere, backupWhere, table);
    }
    
    private void commonBackupByTime(final String name, final String realRemainTime, final String backupRemainTime, final String table, final String timeField) {
        final String realWhere = "`" + timeField + "` < '" + realRemainTime + "'";
        final String backupWhere = "`" + timeField + "` < '" + backupRemainTime + "'";
        this.commonBackupBase(name, realWhere, backupWhere, table);
    }
    
    private void commonBackupBase(final String name, final String realWhere, final String backupWhere, final String table) {
        boolean migrateDone = false;
        final String oldDbTab = "`ecai`.`" + table + "`";
        final String newDbTab = "`ecaibackup`.`" + table + "`";
        this.logger.info("开始迁移{}前的{}数据", (Object)realWhere, (Object)name);
        do {
            final Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            try {
                final String idSql = "SELECT `id` FROM " + oldDbTab + " WHERE " + realWhere + " ORDER BY `id` DESC LIMIT 0," + 500;
                final SQLQuery idQuery = session.createSQLQuery(idSql);
                final Object idResult = idQuery.list();
                if (idResult == null) {
                    migrateDone = true;
                    this.logger.info("没有可以迁移的{}数据。", (Object)name);
                    return;
                }
                final ArrayList<Integer> ids = (ArrayList<Integer>)idResult;
                if (ids == null || ids.size() <= 0) {
                    migrateDone = true;
                    this.logger.info("没有可以迁移的数据。", (Object)name);
                    return;
                }
                final String insertSql = "INSERT INTO " + newDbTab + " (SELECT * FROM " + oldDbTab + " WHERE `id` IN (" + ArrayUtils.toString(ids) + "))";
                final String deleteSql = "DELETE FROM " + oldDbTab + " WHERE `id` IN (" + ArrayUtils.toString(ids) + ")";
                final SQLQuery insertQuery = session.createSQLQuery(insertSql);
                final int insertCount = insertQuery.executeUpdate();
                if (insertCount > 0) {
                    this.logger.info("迁移{}数据{}条数据。", (Object)name, (Object)insertCount);
                    final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
                    final int deleteCount = deleteQuery.executeUpdate();
                    if (deleteCount > 0) {
                        session.getTransaction().commit();
                    }
                }
                else {
                    migrateDone = true;
                    this.logger.info("没有可以迁移的{}数据。", (Object)name);
                }
            }
            catch (Exception e) {
                this.logger.error("迁移" + name + "数据出错...回滚");
                session.getTransaction().rollback();
                migrateDone = true;
                continue;
            }
            finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        } while (!migrateDone);
        if (StringUtil.isNotNull(backupWhere)) {
            this.logger.info("开始删除{}前的{}备份数据", (Object)backupWhere, (Object)name);
            final Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            try {
                final String deleteSql2 = "DELETE FROM " + newDbTab + " WHERE " + backupWhere;
                final SQLQuery deleteQuery2 = session.createSQLQuery(deleteSql2);
                final int deleteCount2 = deleteQuery2.executeUpdate();
                if (deleteCount2 > 0) {
                    this.logger.info("删除{}备份数据完成，共删除{}条数据。", (Object)name, (Object)deleteCount2);
                    session.getTransaction().commit();
                }
                else {
                    this.logger.info("没有可用的{}备份数据删除，本次未删除任何备份数据。", (Object)name, (Object)deleteCount2);
                }
            }
            catch (Exception e) {
                this.logger.error("删除" + name + "备份数据出错...回滚");
                session.getTransaction().rollback();
                return;
            }
            finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    
    private void delFictitiousUserBackupBase(final String name, final String realWhere, final String backupWhere, final String table) {
        boolean migrateDone = false;
        final String oldDbTab = "`ecai`.`" + table + "`";
        final String userTab = "user";
        final String userDbTab = "`ecai`.`" + userTab + "`";
        this.logger.info("开始删除虚拟用户{}前的{}数据", (Object)realWhere, (Object)name);
        do {
            final Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            try {
                final String idSql = "SELECT b.id FROM " + oldDbTab + " b,  " + userDbTab + " u WHERE b.user_id = u.id  and u.type = 4  and " + realWhere + " ORDER BY b.id DESC LIMIT 0," + 500;
                final SQLQuery idQuery = session.createSQLQuery(idSql);
                final Object idResult = idQuery.list();
                if (idResult == null) {
                    migrateDone = true;
                    this.logger.info("没有可以删除的{}数据。", (Object)name);
                    return;
                }
                final ArrayList<Integer> ids = (ArrayList<Integer>)idResult;
                if (ids == null || ids.size() <= 0) {
                    migrateDone = true;
                    this.logger.info("没有可以删除的数据。", (Object)name);
                    return;
                }
                final String deleteSql = "DELETE FROM " + oldDbTab + " WHERE `id` IN (" + ArrayUtils.toString(ids) + ")";
                final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
                final int deleteCount = deleteQuery.executeUpdate();
                if (deleteCount > 0) {
                    session.getTransaction().commit();
                }
            }
            catch (Exception e) {
                this.logger.error("删除试玩用户" + name + "数据出错...回滚");
                session.getTransaction().rollback();
                migrateDone = true;
                continue;
            }
            finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        } while (!migrateDone);
    }
    
    private void delDemoUserBackupBase(final String name, final String realWhere, final String backupWhere, final String table) {
        boolean migrateDone = false;
        final String oldDbTab = "`ecai`.`" + table + "`";
        final String userTab = "user";
        final String userDbTab = "`ecai`.`" + userTab + "`";
        this.logger.info("开始删除试玩用户{}前的{}数据", (Object)realWhere, (Object)name);
        do {
            final Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            try {
                final String idSql = "SELECT b.id FROM " + oldDbTab + " b,  " + userDbTab + " u WHERE b.user_id = u.id  and u.nickname  = '试玩用户' and " + realWhere + " ORDER BY b.id DESC LIMIT 0," + 500;
                final SQLQuery idQuery = session.createSQLQuery(idSql);
                final Object idResult = idQuery.list();
                if (idResult == null) {
                    migrateDone = true;
                    this.logger.info("没有可以删除的{}数据。", (Object)name);
                    return;
                }
                final ArrayList<Integer> ids = (ArrayList<Integer>)idResult;
                if (ids == null || ids.size() <= 0) {
                    migrateDone = true;
                    this.logger.info("没有可以删除的数据。", (Object)name);
                    return;
                }
                final String deleteSql = "DELETE FROM " + oldDbTab + " WHERE `id` IN (" + ArrayUtils.toString(ids) + ")";
                final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
                final int deleteCount = deleteQuery.executeUpdate();
                if (deleteCount > 0) {
                    session.getTransaction().commit();
                }
            }
            catch (Exception e) {
                this.logger.error("删除试玩用户" + name + "数据出错...回滚");
                session.getTransaction().rollback();
                migrateDone = true;
                continue;
            }
            finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        } while (!migrateDone);
    }
    
    private void commonUserBackupBase(final String name, final String realWhere, final String backupWhere, final String table) {
        boolean migrateDone = false;
        final String oldDbTab = "`ecai`.`" + table + "`";
        final String userTab = "user";
        final String userDbTab = "`ecai`.`" + userTab + "`";
        final String newDbTab = "`ecaibackup`.`" + table + "`";
        this.logger.info("开始迁移{}前的{}数据", (Object)realWhere, (Object)name);
        do {
            final Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            try {
                final String idSql = "SELECT b.id FROM " + oldDbTab + " b,  " + userDbTab + " u WHERE b.user_id = u.id  and u.upid != 0  and " + realWhere + " ORDER BY b.id DESC LIMIT 0," + 500;
                final SQLQuery idQuery = session.createSQLQuery(idSql);
                final Object idResult = idQuery.list();
                if (idResult == null) {
                    migrateDone = true;
                    this.logger.info("没有可以迁移的{}数据。", (Object)name);
                    return;
                }
                final ArrayList<Integer> ids = (ArrayList<Integer>)idResult;
                if (ids == null || ids.size() <= 0) {
                    migrateDone = true;
                    this.logger.info("没有可以迁移的数据。", (Object)name);
                    return;
                }
                final String insertSql = "INSERT INTO " + newDbTab + " (SELECT * FROM " + oldDbTab + " WHERE `id` IN (" + ArrayUtils.toString(ids) + "))";
                final String deleteSql = "DELETE FROM " + oldDbTab + " WHERE `id` IN (" + ArrayUtils.toString(ids) + ")";
                final SQLQuery insertQuery = session.createSQLQuery(insertSql);
                final int insertCount = insertQuery.executeUpdate();
                if (insertCount > 0) {
                    this.logger.info("迁移{}数据{}条数据。", (Object)name, (Object)insertCount);
                    final SQLQuery deleteQuery = session.createSQLQuery(deleteSql);
                    final int deleteCount = deleteQuery.executeUpdate();
                    if (deleteCount > 0) {
                        session.getTransaction().commit();
                    }
                }
                else {
                    migrateDone = true;
                    this.logger.info("没有可以迁移的{}数据。", (Object)name);
                }
            }
            catch (Exception e) {
                this.logger.error("迁移" + name + "数据出错...回滚");
                session.getTransaction().rollback();
                migrateDone = true;
                continue;
            }
            finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        } while (!migrateDone);
        if (StringUtil.isNotNull(backupWhere)) {
            this.logger.info("开始删除{}前的{}备份数据", (Object)backupWhere, (Object)name);
            final Session session = this.sessionFactory.openSession();
            session.beginTransaction();
            try {
                final String deleteSql2 = "DELETE FROM " + newDbTab + " WHERE " + backupWhere;
                final SQLQuery deleteQuery2 = session.createSQLQuery(deleteSql2);
                final int deleteCount2 = deleteQuery2.executeUpdate();
                if (deleteCount2 > 0) {
                    this.logger.info("删除{}备份数据完成，共删除{}条数据。", (Object)name, (Object)deleteCount2);
                    session.getTransaction().commit();
                }
                else {
                    this.logger.info("没有可用的{}备份数据删除，本次未删除任何备份数据。", (Object)name, (Object)deleteCount2);
                }
            }
            catch (Exception e) {
                this.logger.error("删除" + name + "备份数据出错...回滚");
                session.getTransaction().rollback();
                return;
            }
            finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
