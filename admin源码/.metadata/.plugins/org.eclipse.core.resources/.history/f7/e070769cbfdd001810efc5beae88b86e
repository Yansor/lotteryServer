package lottery.web.content.validate;

import java.util.Iterator;
import java.util.List;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import javautils.regex.RegexUtil;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.entity.User;
import admin.web.WebJSONObject;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserValidate
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    
    public boolean testUserPoint(final WebJSONObject json, final User uBean, final double locatePoint) {
        if (locatePoint <= uBean.getLocatePoint()) {
            json.set(2, "2-2014");
            return false;
        }
        final CodeConfig config = this.lotteryDataFactory.getCodeConfig();
        if (locatePoint > config.getSysLp()) {
            json.set(2, "2-9");
            return false;
        }
        if (uBean.getUpid() != 0) {
            final User upperUser = this.uDao.getById(uBean.getUpid());
            if (upperUser != null) {
                boolean trueCode = locatePoint < upperUser.getLocatePoint();
                if (upperUser.getAllowEqualCode() == 1) {
                    trueCode = (locatePoint <= upperUser.getLocatePoint());
                }
                if (!trueCode) {
                    json.set(2, "2-2015");
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean testUsername(final WebJSONObject json, final String username) {
        final String patrn = "^[a-zA-Z]{1}([a-zA-Z0-9]){5,11}$";
        if (!RegexUtil.isMatcher(username, patrn)) {
            json.set(2, "2-2029");
            return false;
        }
        return true;
    }
    
    public boolean testNewUserPoint(final WebJSONObject json, final double locatePoint) {
        final CodeConfig config = this.lotteryDataFactory.getCodeConfig();
        if (locatePoint < 0.0 || locatePoint > config.getSysLp()) {
            json.set(2, "2-9");
            return false;
        }
        return true;
    }
    
    public boolean testNewUserPoint(final WebJSONObject json, final User upperUser, final double locatePoint) {
        final CodeConfig config = this.lotteryDataFactory.getCodeConfig();
        if (locatePoint < 0.0 || locatePoint > config.getSysLp()) {
            json.set(2, "2-9");
            return false;
        }
        if (locatePoint > config.getSysLp() || locatePoint < 0.0) {
            json.set(2, "2-2026");
            return false;
        }
        boolean trueCode = locatePoint < upperUser.getLocatePoint();
        if (upperUser.getAllowEqualCode() == 1) {
            trueCode = (locatePoint <= upperUser.getLocatePoint());
        }
        if (!trueCode) {
            json.set(2, "2-2015");
            return false;
        }
        return true;
    }
    
    public SysCodeRangeVO loadEditPoint(final User uBean) {
        final CodeConfig config = this.lotteryDataFactory.getCodeConfig();
        double testMinPoint = 0.0;
        double testMaxPoint = config.getSysLp();
        if (uBean.getUpid() != 0) {
            final User upperUser = this.uDao.getById(uBean.getUpid());
            if (upperUser != null) {
                testMaxPoint = upperUser.getLocatePoint();
                if (upperUser.getAllowEqualCode() != 1) {
                    testMaxPoint = upperUser.getLocatePoint() - 0.1;
                }
            }
        }
        final List<User> uDirectList = this.uDao.getUserDirectLower(uBean.getId());
        for (final User tmpBean : uDirectList) {
            if (tmpBean.getLocatePoint() > testMinPoint) {
                testMinPoint = tmpBean.getLocatePoint();
                if (uBean.getAllowEqualCode() == 1) {
                    continue;
                }
                testMinPoint += 0.1;
            }
        }
        final SysCodeRangeVO result = new SysCodeRangeVO();
        result.setMinPoint(testMinPoint);
        result.setMaxPoint(testMaxPoint);
        return result;
    }
}
