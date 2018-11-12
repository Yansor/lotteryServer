package lottery.domains.content.vo;

public class SelectVO
{
    private Object key;
    private Object value;
    private Object other;
    
    public SelectVO(final Object key, final Object value, final Object other) {
        this.key = key;
        this.value = value;
        this.other = other;
    }
    
    public Object getKey() {
        return this.key;
    }
    
    public void setKey(final Object key) {
        this.key = key;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public Object getOther() {
        return this.other;
    }
    
    public void setOther(final Object other) {
        this.other = other;
    }
}
