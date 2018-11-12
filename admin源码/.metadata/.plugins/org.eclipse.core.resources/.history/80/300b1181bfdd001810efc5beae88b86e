package lottery.web.content.utils;

import lottery.domains.content.vo.user.UserVO;
import javautils.array.ArrayUtils;
import java.util.LinkedList;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.vo.user.UserCodeRangeVO;
import java.util.ArrayList;
import lottery.domains.content.vo.user.UserCodeQuotaVO;
import java.util.List;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.config.CodeConfig;
import javautils.math.MathUtil;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.dao.UserCodeQuotaDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserCodePointUtil
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserCodeQuotaDao uCodeQuotaDao;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    public int getUserCode(final double locatePoint) {
        final CodeConfig config = this.dataFactory.getCodeConfig();
        return config.getSysCode() - (int)(MathUtil.subtract(config.getSysLp(), locatePoint) * 20.0);
    }
    
    public double getLocatePoint(final int code) {
        final CodeConfig config = this.dataFactory.getCodeConfig();
        return config.getSysLp() - (config.getSysCode() - code) / 20.0;
    }
    
    public double getNotLocatePoint(final int code) {
        final CodeConfig config = this.dataFactory.getCodeConfig();
        final double result = config.getSysNlp() - (config.getSysCode() - code) / 20.0;
        return (result < 0.0) ? 0.0 : result;
    }
    
    public double getNotLocatePoint(final double locatePoint) {
        return this.getNotLocatePoint(this.getUserCode(locatePoint));
    }
    
    public List<UserCodeQuotaVO> listTotalQuota(final User uBean) {
        return new ArrayList<UserCodeQuotaVO>();
    }
    
    public List<UserCodeQuotaVO> listSurplusQuota(final User uBean) {
        return new ArrayList<UserCodeQuotaVO>();
    }
    
    public UserCodeRangeVO getUserCodeRange(final User uBean, final List<UserCodeQuotaVO> surplusList) {
        return null;
    }
    
    public List<String> getUserLevels(final String username) {
        if (StringUtils.isEmpty(username)) {
            return new ArrayList<String>();
        }
        final List<String> userLevels = new LinkedList<String>();
        final User user = this.uDao.getByUsername(username);
        if (user == null) {
            return userLevels;
        }
        if (StringUtils.isEmpty(user.getUpids())) {
            return userLevels;
        }
        final int[] upids = ArrayUtils.transGetIds(user.getUpids());
        for (int i = upids.length - 1; i >= 0; --i) {
            final UserVO upUser = this.dataFactory.getUser(upids[i]);
            if (upUser != null) {
                userLevels.add(upUser.getUsername());
            }
        }
        userLevels.add(username);
        return userLevels;
    }
    
    public boolean isLevel1Proxy(final User uBean) {
        return uBean.getType() == 1 && uBean.getUpid() == 72;
    }
    
    public boolean isLevel2Proxy(final User uBean) {
        if (uBean.getType() == 1 && uBean.getUpid() != 0 && uBean.getUpid() != 72) {
            final User upBean = this.uDao.getById(uBean.getUpid());
            if (this.isLevel1Proxy(upBean)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isLevel2ZhaoShangProxy(final User uBean) {
        if (uBean.getType() == 1 && uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode() && uBean.getUpid() != 0 && uBean.getUpid() != 72 && uBean.getIsCjZhaoShang() == 0) {
            final User upBean = this.uDao.getById(uBean.getUpid());
            if (this.isLevel1Proxy(upBean)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isLevel2CJZhaoShangProxy(final User uBean) {
        if (uBean.getType() == 1 && uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode() && uBean.getUpid() != 0 && uBean.getUpid() != 72 && uBean.getIsCjZhaoShang() == 1) {
            final User upBean = this.uDao.getById(uBean.getUpid());
            if (this.isLevel1Proxy(upBean)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isLevel3Proxy(final User uBean) {
        if (uBean.getType() == 1 && uBean.getUpid() != 0 && uBean.getUpid() != 72) {
            final User upBean = this.uDao.getById(uBean.getUpid());
            if (this.isLevel2Proxy(upBean)) {
                return true;
            }
        }
        return false;
    }
}
