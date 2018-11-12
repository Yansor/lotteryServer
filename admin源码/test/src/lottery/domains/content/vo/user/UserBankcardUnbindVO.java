package lottery.domains.content.vo.user;

import org.apache.commons.lang.StringUtils;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserBankcardUnbindRecord;

public class UserBankcardUnbindVO
{
    private int id;
    private String userIds;
    private String cardId;
    private int unbindNum;
    private String unbindTime;
    private String username;
    
    public UserBankcardUnbindVO(final UserBankcardUnbindRecord entity, final LotteryDataFactory df) {
        this.id = entity.getId();
        this.userIds = entity.getUserIds();
        this.cardId = entity.getCardId();
        this.unbindNum = entity.getUnbindNum();
        this.unbindTime = entity.getUnbindTime();
        String usernames = "";
        if (entity.getUserIds() != null && !entity.getUserIds().equals("")) {
            if (entity.getUserIds().contains("#")) {
                final String[] ids = entity.getUserIds().split("#");
                final StringBuffer nameapp = new StringBuffer();
                String[] array;
                for (int length = (array = ids).length, i = 0; i < length; ++i) {
                    final String string = array[i];
                    if (StringUtils.isNotBlank(string)) {
                        final UserVO user = df.getUser(Integer.parseInt(string));
                        if (user != null) {
                            nameapp.append(user.getUsername()).append(",");
                        }
                    }
                }
                final String res = nameapp.toString();
                usernames = res.substring(0, res.length() - 1);
            }
            else {
                final UserVO user2 = df.getUser(Integer.parseInt(entity.getUserIds()));
                if (user2 != null) {
                    usernames = user2.getUsername();
                }
            }
        }
        this.username = usernames;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUserIds() {
        return this.userIds;
    }
    
    public void setUserIds(final String userIds) {
        this.userIds = userIds;
    }
    
    public String getCardId() {
        return this.cardId;
    }
    
    public void setCardId(final String cardId) {
        this.cardId = cardId;
    }
    
    public int getUnbindNum() {
        return this.unbindNum;
    }
    
    public void setUnbindNum(final int unbindNum) {
        this.unbindNum = unbindNum;
    }
    
    public String getUnbindTime() {
        return this.unbindTime;
    }
    
    public void setUnbindTime(final String unbindTime) {
        this.unbindTime = unbindTime;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
}
