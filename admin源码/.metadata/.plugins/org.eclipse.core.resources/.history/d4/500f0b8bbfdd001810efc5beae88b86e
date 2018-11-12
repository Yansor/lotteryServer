package lottery.domains.content.biz.impl;

import javautils.ObjectUtil;
import lottery.domains.content.vo.InstantStatVO;
import lottery.domains.content.dao.ActivitySignBillDao;
import lottery.domains.content.dao.ActivityGrabBillDao;
import lottery.domains.content.dao.ActivityRechargeLoopBillDao;
import lottery.domains.content.dao.ActivitySalaryBillDao;
import lottery.domains.content.dao.ActivityRewardBillDao;
import lottery.domains.content.dao.ActivityBindBillDao;
import lottery.domains.content.dao.UserTransfersDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserBillDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.LotteryInstantStatService;

@Service
public class LotteryInstantStatServiceImpl implements LotteryInstantStatService
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserBillDao uBillDao;
    @Autowired
    private UserRechargeDao uRechargeDao;
    @Autowired
    private UserWithdrawDao uWithdrawDao;
    @Autowired
    private UserTransfersDao uTransfersDao;
    @Autowired
    private ActivityBindBillDao aBindBillDao;
    @Autowired
    private ActivityRewardBillDao aRewardBillDao;
    @Autowired
    private ActivitySalaryBillDao aSalaryBillDao;
    @Autowired
    private ActivityRechargeLoopBillDao aRechargeLoopBillDao;
    @Autowired
    private ActivityGrabBillDao aGrabBillDao;
    @Autowired
    private ActivitySignBillDao mActivitySignBillDao;
    
    @Override
    public InstantStatVO getInstantStat(final String sTime, final String eTime) {
        final InstantStatVO bean = new InstantStatVO();
        int[] type = { 1 };
        int[] subtype = { 2 };
        int payBankId = 1;
        double money = this.uRechargeDao.getTotalRecharge(sTime, eTime, type, subtype, payBankId);
        bean.setIcbcMoney(money);
        type = new int[] { 1 };
        subtype = new int[] { 2 };
        payBankId = 2;
        money = this.uRechargeDao.getTotalRecharge(sTime, eTime, type, subtype, payBankId);
        bean.setCcbMoney(money);
        type = new int[] { 1 };
        subtype = new int[] { 2 };
        payBankId = 4;
        money = this.uRechargeDao.getTotalRecharge(sTime, eTime, type, subtype, payBankId);
        bean.setCmbMoney(money);
        type = new int[] { 1 };
        subtype = new int[] { 2 };
        payBankId = 18;
        money = this.uRechargeDao.getTotalRecharge(sTime, eTime, type, subtype, payBankId);
        bean.setCmbcMoney(money);
        double money2 = this.uRechargeDao.getThirdTotalRecharge(sTime, eTime);
        bean.setThirdMoney(money2);
        final int[] thisType = { 3 };
        subtype = new int[] { 1 };
        double money3 = this.uRechargeDao.getTotalRecharge(sTime, eTime, thisType, subtype, null);
        bean.setNotarrivalMoney(money3);
        money2 = this.uWithdrawDao.getTotalWithdraw(sTime, eTime);
        bean.setWithdrawalsMoney(money2);
        money2 = this.uWithdrawDao.getTotalAutoRemit(sTime, eTime);
        bean.setThrildRemitMoney(money2);
        int type2 = 1;
        double money4 = this.uTransfersDao.getTotalTransfers(sTime, eTime, type2);
        bean.setTransUserIn(money4);
        type2 = 2;
        money4 = this.uTransfersDao.getTotalTransfers(sTime, eTime, type2);
        bean.setTransUserOut(money4);
        money2 = this.uWithdrawDao.getTotalFee(sTime, eTime);
        bean.setFeeDeductMoney(money2);
        final double feeFillMoney = this.uRechargeDao.getTotalFee(sTime, eTime);
        bean.setFeeFillMoney(feeFillMoney);
        type2 = 13;
        int[] refType = new int[0];
        money3 = this.uBillDao.getTotalMoney(sTime, eTime, type2, refType);
        bean.setAdminAddMoney(money3);
        type2 = 14;
        refType = new int[0];
        money3 = this.uBillDao.getTotalMoney(sTime, eTime, type2, refType);
        bean.setAdminMinusMoney(money3);
        type2 = 12;
        refType = new int[0];
        money3 = this.uBillDao.getTotalMoney(sTime, eTime, type2, refType);
        bean.setDividendMoney(money3);
        type2 = 1;
        money4 = this.aRewardBillDao.total(sTime, eTime, type2);
        bean.setActivityRewardXFMoney(money4);
        type2 = 2;
        money4 = this.aRewardBillDao.total(sTime, eTime, type2);
        bean.setActivityRewardYKMoney(money4);
        type2 = 1;
        money4 = this.aSalaryBillDao.total(sTime, eTime, type2);
        bean.setActivitySalaryZSMoney(money4);
        type2 = 2;
        money4 = this.aSalaryBillDao.total(sTime, eTime, type2);
        bean.setActivitySalaryZDMoney(money4);
        money2 = this.aBindBillDao.total(sTime, eTime);
        bean.setActivityBindMoney(money2);
        type2 = 5;
        refType = new int[] { 2 };
        money3 = this.uBillDao.getTotalMoney(sTime, eTime, type2, refType);
        bean.setActivityRechargeMoney(money3);
        money2 = this.aRechargeLoopBillDao.total(sTime, eTime);
        bean.setActivityRechargeLoopMoney(money2);
        type2 = 17;
        refType = new int[0];
        money3 = this.uBillDao.getTotalMoney(sTime, eTime, type2, refType);
        bean.setActivityJiFenMoney(money3);
        final double total = this.mActivitySignBillDao.total(sTime, eTime);
        bean.setActivitySignMoney(total);
        type2 = 5;
        refType = new int[] { 0 };
        money3 = this.uBillDao.getTotalMoney(sTime, eTime, type2, refType);
        bean.setActivityOtherMoney(money3);
        final Object[] banlance = this.uDao.getTotalMoney();
        final double totalBalance = ObjectUtil.toDouble(banlance[0]);
        bean.setTotalBalance(totalBalance);
        final double lotteryBalance = ObjectUtil.toDouble(banlance[1]);
        bean.setLotteryBalance(lotteryBalance);
        final double baccaratBalance = ObjectUtil.toDouble(banlance[2]);
        bean.setBaccaratBalance(baccaratBalance);
        money2 = this.aGrabBillDao.getGrabTotal(sTime, eTime);
        bean.setActivityGrabMoney(money2);
        return bean;
    }
}
