package lottery.domains.content.biz.impl;

import java.util.Iterator;
import java.util.List;
import lottery.domains.content.vo.user.UserVO;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import javautils.date.Moment;
import lottery.domains.content.vo.user.UserWithdrawLimitVO;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.Map;
import javautils.math.MathUtil;
import lottery.domains.content.entity.UserWithdrawLimit;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.biz.GameBetsService;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserWithdrawLimitDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserWithdrawLimitService;

@Service
public class UserWithdrawLimitServiceImpl implements UserWithdrawLimitService
{
    @Autowired
    private UserWithdrawLimitDao uWithdrawLimitDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    @Autowired
    private GameBetsService gameBetsService;
    @Autowired
    private UserBetsService uBetsService;
    
    @Override
    public boolean add(final int userId, final double amount, final String time, final int type, final int subType, final double percent) {
        if (percent > 0.0) {
            final UserWithdrawLimit entity = new UserWithdrawLimit();
            entity.setRechargeMoney(amount);
            entity.setRechargeTime(time);
            entity.setUserId(userId);
            entity.setProportion(percent);
            entity.setConsumptionRequirements(MathUtil.multiply(amount, percent));
            entity.setType(type);
            entity.setSubType(subType);
            return this.uWithdrawLimitDao.add(entity);
        }
        return true;
    }
    
    @Override
    public boolean delByUserId(final int userId) {
        return this.uWithdrawLimitDao.delByUserId(userId);
    }
    
    @Override
    public UserWithdrawLimit getByUserId(final int userId) {
        return this.uWithdrawLimitDao.getByUserId(userId);
    }
    
    @Override
    public Map<String, Object> getUserWithdrawLimits(final int userId, final String time) {
        final UserVO user = this.dataFactory.getUser(userId);
        final String username = (user == null) ? "未知" : user.getUsername();
        final List<UserWithdrawLimit> records = this.uWithdrawLimitDao.getUserWithdrawLimits(userId, time);
        final List<UserWithdrawLimit> entities = new ArrayList<UserWithdrawLimit>();
        for (final UserWithdrawLimit record : records) {
            if (StringUtils.isNotEmpty(record.getRechargeTime()) && record.getRechargeMoney() > 0.0) {
                entities.add(record);
            }
        }
        final List<UserWithdrawLimit> rechargeEntities = new ArrayList<UserWithdrawLimit>();
        for (final UserWithdrawLimit entity : entities) {
            if (entity.getConsumptionRequirements() > 0.0) {
                rechargeEntities.add(entity);
            }
        }
        final int listSize = rechargeEntities.size();
        final List<UserWithdrawLimitVO> rechargeVOs = new ArrayList<UserWithdrawLimitVO>();
        for (int i = 0; i < listSize; ++i) {
            final UserWithdrawLimit entity2 = rechargeEntities.get(i);
            final double needConsumption = entity2.getConsumptionRequirements();
            double remainConsumption = 0.0;
            double totalBilling = 0.0;
            if (needConsumption < 0.0) {
                remainConsumption = needConsumption;
            }
            else if (needConsumption > 0.0) {
                final String rechargeSTime = entity2.getRechargeTime();
                String rechargeETime;
                if (listSize > i + 1) {
                    rechargeETime = rechargeEntities.get(i + 1).getRechargeTime();
                }
                else {
                    rechargeETime = new Moment().toSimpleTime();
                }
                final double[] consumptions = this.getConsumptions(userId, needConsumption, rechargeSTime, rechargeETime);
                remainConsumption = consumptions[0];
                totalBilling = consumptions[1];
            }
            final UserWithdrawLimitVO userWithdrawLimitVO = new UserWithdrawLimitVO(entity2, username, totalBilling, remainConsumption);
            rechargeVOs.add(userWithdrawLimitVO);
        }
        if (rechargeVOs.size() > 1) {
            for (int i = 0; i < rechargeVOs.size(); ++i) {
                final UserWithdrawLimitVO last = rechargeVOs.get(i);
                double lastBilling = last.getTotalBilling();
                if (lastBilling > 0.0) {
                    for (int j = 0; j < i; ++j) {
                        final UserWithdrawLimitVO limitVO = rechargeVOs.get(j);
                        double remainConsumption2 = limitVO.getRemainConsumption();
                        if (remainConsumption2 > 0.0) {
                            final double giveBilling = (remainConsumption2 > lastBilling) ? lastBilling : remainConsumption2;
                            remainConsumption2 = MathUtil.subtract(remainConsumption2, giveBilling);
                            limitVO.setTotalBilling(MathUtil.add(limitVO.getTotalBilling(), giveBilling));
                            limitVO.setRemainConsumption(remainConsumption2);
                            lastBilling = MathUtil.subtract(lastBilling, giveBilling);
                        }
                    }
                    last.setTotalBilling(lastBilling);
                    double lastRemainConsumption = MathUtil.subtract(last.getBean().getConsumptionRequirements(), lastBilling);
                    if (lastRemainConsumption < 0.0) {
                        lastRemainConsumption = 0.0;
                    }
                    last.setRemainConsumption(lastRemainConsumption);
                }
            }
        }
        for (final UserWithdrawLimit entity3 : entities) {
            if (entity3.getConsumptionRequirements() < 0.0) {
                final UserWithdrawLimitVO userWithdrawLimitVO2 = new UserWithdrawLimitVO(entity3, username, 0.0, entity3.getConsumptionRequirements());
                rechargeVOs.add(userWithdrawLimitVO2);
            }
        }
        Collections.sort(rechargeVOs, new Comparator<UserWithdrawLimitVO>() {
            @Override
            public int compare(final UserWithdrawLimitVO o1, final UserWithdrawLimitVO o2) {
                return o1.getBean().getRechargeTime().compareTo(o2.getBean().getRechargeTime());
            }
        });
        double totalRemainConsumption = 0.0;
        final Iterator<UserWithdrawLimitVO> iterator4 = rechargeVOs.iterator();
        while (iterator4.hasNext()) {
            final UserWithdrawLimitVO userWithdrawLimitVO2 = iterator4.next();
            totalRemainConsumption = MathUtil.add(totalRemainConsumption, userWithdrawLimitVO2.getRemainConsumption());
        }
        if (totalRemainConsumption < 0.0) {
            totalRemainConsumption = 0.0;
        }
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", rechargeVOs);
        map.put("totalRemainConsumption", totalRemainConsumption);
        return map;
    }
    
    private UserWithdrawLimitVO getLastRecharge(final List<UserWithdrawLimitVO> vos) {
        for (int i = vos.size() - 1; i >= 0; --i) {
            if (vos.get(i).getBean().getType() != 5) {
                return vos.get(i);
            }
        }
        return null;
    }
    
    private double[] getConsumptions(final int userId, final double needConsumption, final String sTime, final String eTime) {
        double remainConsumption = needConsumption;
        double lotteryBilling = this.uBetsService.getBillingOrder(userId, sTime, eTime);
        if (lotteryBilling < 0.0) {
            lotteryBilling = 0.0;
        }
        remainConsumption = MathUtil.subtract(remainConsumption, lotteryBilling);
        if (remainConsumption < 0.0) {
            remainConsumption = 0.0;
        }
        double gameBilling = this.gameBetsService.getBillingOrder(userId, sTime, eTime);
        if (gameBilling < 0.0) {
            gameBilling = 0.0;
        }
        remainConsumption = MathUtil.subtract(remainConsumption, gameBilling);
        if (remainConsumption < 0.0) {
            remainConsumption = 0.0;
        }
        final double totalBilling = MathUtil.add(lotteryBilling, gameBilling);
        return new double[] { remainConsumption, totalBilling };
    }
}
