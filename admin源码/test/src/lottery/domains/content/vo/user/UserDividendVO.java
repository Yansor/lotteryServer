package lottery.domains.content.vo.user;

import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.content.entity.UserDividend;

public class UserDividendVO
{
    private int id;
    private String username;
    private String scaleLevel;
    private String lossLevel;
    private String salesLevel;
    private String userLevel;
    private int minValidUser;
    private String createTime;
    private String agreeTime;
    private double totalAmount;
    private int status;
    private int fixed;
    private double minScale;
    private double maxScale;
    private String remarks;
    
    public UserDividendVO(final UserDividend bean, final LotteryDataFactory dataFactory) {
        this.id = bean.getId();
        final UserVO user = dataFactory.getUser(bean.getUserId());
        if (user == null) {
            this.username = "未知[" + bean.getUserId() + "]";
        }
        else {
            this.username = user.getUsername();
        }
        this.scaleLevel = bean.getScaleLevel();
        this.lossLevel = bean.getLossLevel();
        this.salesLevel = bean.getSalesLevel();
        this.minValidUser = bean.getMinValidUser();
        this.createTime = bean.getCreateTime();
        this.agreeTime = bean.getAgreeTime();
        this.totalAmount = bean.getTotalAmount();
        this.status = bean.getStatus();
        this.fixed = bean.getFixed();
        this.minScale = bean.getMinScale();
        this.maxScale = bean.getMaxScale();
        this.remarks = bean.getRemarks();
        this.userLevel = bean.getUserLevel();
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getScaleLevel() {
        return this.scaleLevel;
    }
    
    public void setScaleLevel(final String scaleLevel) {
        this.scaleLevel = scaleLevel;
    }
    
    public String getLossLevel() {
        return this.lossLevel;
    }
    
    public void setLossLevel(final String lossLevel) {
        this.lossLevel = lossLevel;
    }
    
    public String getSalesLevel() {
        return this.salesLevel;
    }
    
    public void setSalesLevel(final String salesLevel) {
        this.salesLevel = salesLevel;
    }
    
    public int getMinValidUser() {
        return this.minValidUser;
    }
    
    public void setMinValidUser(final int minValidUser) {
        this.minValidUser = minValidUser;
    }
    
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(final String createTime) {
        this.createTime = createTime;
    }
    
    public String getAgreeTime() {
        return this.agreeTime;
    }
    
    public void setAgreeTime(final String agreeTime) {
        this.agreeTime = agreeTime;
    }
    
    public double getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(final double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(final int status) {
        this.status = status;
    }
    
    public int getFixed() {
        return this.fixed;
    }
    
    public void setFixed(final int fixed) {
        this.fixed = fixed;
    }
    
    public double getMinScale() {
        return this.minScale;
    }
    
    public void setMinScale(final double minScale) {
        this.minScale = minScale;
    }
    
    public double getMaxScale() {
        return this.maxScale;
    }
    
    public void setMaxScale(final double maxScale) {
        this.maxScale = maxScale;
    }
    
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }
    
    public String getUserLevel() {
        return this.userLevel;
    }
    
    public void setUserLevel(final String userLevel) {
        this.userLevel = userLevel;
    }
}
