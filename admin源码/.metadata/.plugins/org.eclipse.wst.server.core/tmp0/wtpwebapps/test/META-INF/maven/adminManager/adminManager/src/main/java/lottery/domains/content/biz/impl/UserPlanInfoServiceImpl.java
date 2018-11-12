package lottery.domains.content.biz.impl;

import lottery.domains.content.entity.UserPlanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserPlanInfoDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.UserPlanInfoService;

@Service
public class UserPlanInfoServiceImpl implements UserPlanInfoService
{
    @Autowired
    private UserPlanInfoDao uPlanInfoDao;
    
    @Override
    public UserPlanInfo get(final int userId) {
        UserPlanInfo bean = this.uPlanInfoDao.get(userId);
        if (bean == null) {
            final int level = 0;
            final int planCount = 0;
            final int prizeCount = 0;
            final double totalMoney = 0.0;
            final double totalPrize = 0.0;
            final int status = 0;
            bean = new UserPlanInfo(userId, level, planCount, prizeCount, totalMoney, totalPrize, status);
            this.uPlanInfoDao.add(bean);
        }
        return bean;
    }
}
