package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserMessage;

public class UserMessageVO
{
    private String toUser;
    private String fromUser;
    private UserMessage bean;
    
    public UserMessageVO(final UserMessage bean, final LotteryDataFactory lotteryDataFactory) {
        this.bean = bean;
        if (bean.getToUid() != 0) {
            final UserVO toUser = lotteryDataFactory.getUser(bean.getToUid());
            this.toUser = toUser.getUsername();
        }
        if (bean.getFromUid() != 0) {
            final UserVO fromUser = lotteryDataFactory.getUser(bean.getFromUid());
            this.fromUser = fromUser.getUsername();
        }
    }
    
    public String getToUser() {
        return this.toUser;
    }
    
    public void setToUser(final String toUser) {
        this.toUser = toUser;
    }
    
    public String getFromUser() {
        return this.fromUser;
    }
    
    public void setFromUser(final String fromUser) {
        this.fromUser = fromUser;
    }
    
    public UserMessage getBean() {
        return this.bean;
    }
    
    public void setBean(final UserMessage bean) {
        this.bean = bean;
    }
}
