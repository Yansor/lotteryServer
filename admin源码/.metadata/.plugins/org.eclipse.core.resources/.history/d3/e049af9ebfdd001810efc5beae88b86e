package lottery.domains.content.vo.user;

import org.apache.commons.lang.math.NumberUtils;
import javautils.StringUtil;
import java.util.LinkedList;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.User;
import java.util.List;

public class UserProfileVO
{
    private String upUser;
    private List<String> levelUsers;
    private List<String> relatedUsers;
    private String relatedUpUser;
    private int lowerUsers;
    private User bean;
    private boolean isZhaoShang;
    
    public UserProfileVO(final User bean, final List<User> lowerUsers, final LotteryDataFactory df, final UserCodePointUtil uCodePointUtil) {
        this.levelUsers = new LinkedList<String>();
        this.relatedUsers = new LinkedList<String>();
        (this.bean = bean).setPassword("***");
        this.bean.setSecretKey("***");
        if (StringUtil.isNotNull(this.bean.getWithdrawPassword())) {
            this.bean.setWithdrawPassword("***");
        }
        if (StringUtil.isNotNull(this.bean.getImgPassword())) {
            this.bean.setImgPassword("***");
        }
        this.lowerUsers = lowerUsers.size();
        if (bean.getUpid() != 0) {
            final UserVO user = df.getUser(bean.getUpid());
            this.upUser = user.getUsername();
        }
        if (StringUtil.isNotNull(bean.getUpids())) {
            final String[] ids = bean.getUpids().replaceAll("\\[|\\]", "").split(",");
            String[] array;
            for (int length = (array = ids).length, i = 0; i < length; ++i) {
                final String id = array[i];
                final UserVO user2 = df.getUser(Integer.parseInt(id));
                if (user2 != null) {
                    this.levelUsers.add(user2.getUsername());
                }
                else {
                    this.levelUsers.add("unknown");
                }
            }
        }
        if (bean.getRelatedUpid() != 0) {
            final UserVO user = df.getUser(bean.getRelatedUpid());
            if (user != null) {
                this.relatedUpUser = user.getUsername();
            }
        }
        if (this.bean.getType() == 3 && StringUtil.isNotNull(this.bean.getRelatedUsers())) {
            final String[] ids = bean.getRelatedUsers().replaceAll("\\[|\\]", "").split(",");
            String[] array2;
            for (int length2 = (array2 = ids).length, j = 0; j < length2; ++j) {
                final String id = array2[j];
                if (NumberUtils.isDigits(id)) {
                    final UserVO relatedUser = df.getUser(Integer.valueOf(id));
                    if (relatedUser != null) {
                        this.relatedUsers.add(relatedUser.getUsername());
                    }
                }
            }
        }
        this.isZhaoShang = uCodePointUtil.isLevel2Proxy(bean);
    }
    
    public String getUpUser() {
        return this.upUser;
    }
    
    public void setUpUser(final String upUser) {
        this.upUser = upUser;
    }
    
    public List<String> getLevelUsers() {
        return this.levelUsers;
    }
    
    public void setLevelUsers(final List<String> levelUsers) {
        this.levelUsers = levelUsers;
    }
    
    public String getRelatedUpUser() {
        return this.relatedUpUser;
    }
    
    public void setRelatedUpUser(final String relatedUpUser) {
        this.relatedUpUser = relatedUpUser;
    }
    
    public int getLowerUsers() {
        return this.lowerUsers;
    }
    
    public void setLowerUsers(final int lowerUsers) {
        this.lowerUsers = lowerUsers;
    }
    
    public User getBean() {
        return this.bean;
    }
    
    public void setBean(final User bean) {
        this.bean = bean;
    }
    
    public List<String> getRelatedUsers() {
        return this.relatedUsers;
    }
    
    public void setRelatedUsers(final List<String> relatedUsers) {
        this.relatedUsers = relatedUsers;
    }
    
    public boolean isZhaoShang() {
        return this.isZhaoShang;
    }
    
    public void setZhaoShang(final boolean zhaoShang) {
        this.isZhaoShang = zhaoShang;
    }
}
