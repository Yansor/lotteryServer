package lottery.domains.content.vo.user;

import java.util.LinkedList;
import lottery.domains.pool.LotteryDataFactory;
import java.util.List;
import lottery.domains.content.entity.UserDividendBill;

public class UserDividendBillVO
{
    private String username;
    private UserDividendBill bean;
    private List<String> userLevels;
    
    public UserDividendBillVO(final UserDividendBill bean, final LotteryDataFactory dataFactory) {
        this.userLevels = new LinkedList<String>();
        final UserVO user = dataFactory.getUser(bean.getUserId());
        if (user == null) {
            this.username = "未知[" + bean.getUserId() + "]";
        }
        else {
            this.username = user.getUsername();
        }
        this.bean = bean;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public UserDividendBill getBean() {
        return this.bean;
    }
    
    public void setBean(final UserDividendBill bean) {
        this.bean = bean;
    }
    
    public List<String> getUserLevels() {
        return this.userLevels;
    }
    
    public void setUserLevels(final List<String> userLevels) {
        this.userLevels = userLevels;
    }
}
