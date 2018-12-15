package lottery.domains.content.biz.impl;

import javautils.ObjectUtil;
import lottery.domains.content.biz.LotteryInstantStatService;
import lottery.domains.content.dao.ActivityBindBillDao;
import lottery.domains.content.dao.ActivityGrabBillDao;
import lottery.domains.content.dao.ActivityRechargeLoopBillDao;
import lottery.domains.content.dao.ActivityRewardBillDao;
import lottery.domains.content.dao.ActivitySalaryBillDao;
import lottery.domains.content.dao.ActivitySignBillDao;
import lottery.domains.content.dao.UserBillDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.dao.UserRechargeDao;
import lottery.domains.content.dao.UserTransfersDao;
import lottery.domains.content.dao.UserWithdrawDao;
import lottery.domains.content.vo.InstantStatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryInstantStatServiceImpl
  implements LotteryInstantStatService
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
  
  public InstantStatVO getInstantStat(String sTime, String eTime)
  {
    InstantStatVO bean = new InstantStatVO();
    
    int[] type = { 1 };
    int[] subtype = { 2 };
    int payBankId = 1;
    double money = this.uRechargeDao.getTotalRecharge(sTime, eTime, type, subtype, Integer.valueOf(payBankId));
    bean.setIcbcMoney(money);
    
    int[] type1 = { 1 };
    int[] subtype1 = { 2 };
    int payBankId1 = 2;
    double money1 = this.uRechargeDao.getTotalRecharge(sTime, eTime, type1, subtype1, Integer.valueOf(payBankId1));
    bean.setCcbMoney(money1);
    
    int[] type2 = { 1 };
    int[] subtype2 = { 2 };
    int payBankId2 = 4;
    double money2 = this.uRechargeDao.getTotalRecharge(sTime, eTime, type2, subtype2, Integer.valueOf(payBankId2));
    bean.setCmbMoney(money2);
    
    int[] type3 = { 1 };
    int[] subtype3 = { 2 };
    int payBankId3 = 18;
    double money3 = this.uRechargeDao.getTotalRecharge(sTime, eTime, type3, subtype3, Integer.valueOf(payBankId3));
    bean.setCmbcMoney(money3);
    
    double money4 = this.uRechargeDao.getThirdTotalRecharge(sTime, eTime);
    bean.setThirdMoney(money4);
    
    int[] thisType = { 3 };
    int[] subtype5 = { 1 };
    double money5 = this.uRechargeDao.getTotalRecharge(sTime, eTime, thisType, subtype5, null);
    bean.setNotarrivalMoney(money5);
    
    double money6 = this.uWithdrawDao.getTotalWithdraw(sTime, eTime);
    bean.setWithdrawalsMoney(money6);
    
    double money7 = this.uWithdrawDao.getTotalAutoRemit(sTime, eTime);
    bean.setThrildRemitMoney(money7);
    
    int type8 = 1;
    double money8 = this.uTransfersDao.getTotalTransfers(sTime, eTime, type8);
    bean.setTransUserIn(money8);
    
    int type9 = 2;
    double money9 = this.uTransfersDao.getTotalTransfers(sTime, eTime, type9);
    bean.setTransUserOut(money9);
    
    double money10 = this.uWithdrawDao.getTotalFee(sTime, eTime);
    bean.setFeeDeductMoney(money10);
    
    double feeFillMoney = this.uRechargeDao.getTotalFee(sTime, eTime);
    bean.setFeeFillMoney(feeFillMoney);
    
    int type11 = 13;
    int[] refType = new int[0];
    double money11 = this.uBillDao.getTotalMoney(sTime, eTime, type11, refType);
    bean.setAdminAddMoney(money11);
    
    int type12 = 14;
    int[] refType12 = new int[0];
    double money12 = this.uBillDao.getTotalMoney(sTime, eTime, type12, refType12);
    bean.setAdminMinusMoney(money12);
    
    int type13 = 12;
    int[] refType13 = new int[0];
    double money13 = this.uBillDao.getTotalMoney(sTime, eTime, type13, refType13);
    bean.setDividendMoney(money13);
    
    int type14 = 1;
    double money14 = this.aRewardBillDao.total(sTime, eTime, type14);
    bean.setActivityRewardXFMoney(money14);
    
    int type15 = 2;
    double money15 = this.aRewardBillDao.total(sTime, eTime, type15);
    bean.setActivityRewardYKMoney(money);
    
    int type16 = 1;
    double money16 = this.aSalaryBillDao.total(sTime, eTime, type16);
    bean.setActivitySalaryZSMoney(money16);
    
    int type17 = 2;
    double money17 = this.aSalaryBillDao.total(sTime, eTime, type17);
    bean.setActivitySalaryZDMoney(money17);
    
    double money18 = this.aBindBillDao.total(sTime, eTime);
    bean.setActivityBindMoney(money18);
    
    int type19 = 5;
    int[] refType19 = { 2 };
    double money19 = this.uBillDao.getTotalMoney(sTime, eTime, type19, refType19);
    bean.setActivityRechargeMoney(money19);
    
    double money20 = this.aRechargeLoopBillDao.total(sTime, eTime);
    bean.setActivityRechargeLoopMoney(money20);
    
    int type21 = 17;
    int[] refType21 = new int[0];
    double money21 = this.uBillDao.getTotalMoney(sTime, eTime, type21, refType21);
    bean.setActivityJiFenMoney(money21);
    
    double total = this.mActivitySignBillDao.total(sTime, eTime);
    bean.setActivitySignMoney(total);
    
    int type22 = 5;
    int[] refType22 = new int[1];
    double money22 = this.uBillDao.getTotalMoney(sTime, eTime, type22, refType22);
    bean.setActivityOtherMoney(money22);
    
    Object[] banlance = this.uDao.getTotalMoney();
    double totalBalance = ObjectUtil.toDouble(banlance[0]);
    bean.setTotalBalance(totalBalance);
    double lotteryBalance = ObjectUtil.toDouble(banlance[1]);
    bean.setLotteryBalance(lotteryBalance);
    double baccaratBalance = ObjectUtil.toDouble(banlance[2]);
    bean.setBaccaratBalance(baccaratBalance);
    
    double money23 = this.aGrabBillDao.getGrabTotal(sTime, eTime);
    bean.setActivityGrabMoney(money23);
    
    return bean;
  }
}
