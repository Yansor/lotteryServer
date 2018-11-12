package lottery.domains.content.vo.user;

public class UserCodeQuotaVO
{
    private int userId;
    private int code;
    private int quantity;
    private int sysAllocateQuantity;
    private int upAllocateQuantity;
    private int surplus;
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(final int code) {
        this.code = code;
    }
    
    public int getQuantity() {
        return this.sysAllocateQuantity + this.upAllocateQuantity;
    }
    
    public int getSysAllocateQuantity() {
        return this.sysAllocateQuantity;
    }
    
    public void setSysAllocateQuantity(final int sysAllocateQuantity) {
        this.sysAllocateQuantity = sysAllocateQuantity;
    }
    
    public int getUpAllocateQuantity() {
        return this.upAllocateQuantity;
    }
    
    public void setUpAllocateQuantity(final int upAllocateQuantity) {
        this.upAllocateQuantity = upAllocateQuantity;
    }
    
    public int getSurplus() {
        return this.surplus;
    }
    
    public void setSurplus(final int surplus) {
        if (surplus < 0) {
            this.surplus = 0;
        }
        else {
            this.surplus = surplus;
        }
    }
}
