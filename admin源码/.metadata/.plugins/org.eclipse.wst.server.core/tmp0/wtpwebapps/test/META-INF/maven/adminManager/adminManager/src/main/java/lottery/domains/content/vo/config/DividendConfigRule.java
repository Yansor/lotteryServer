package lottery.domains.content.vo.config;

public class DividendConfigRule
{
    private double from;
    private double to;
    private double scale;
    
    public DividendConfigRule(final double from, final double to, final double scale) {
        this.from = from;
        this.to = to;
        this.scale = scale;
    }
    
    public double getFrom() {
        return this.from;
    }
    
    public void setFrom(final double from) {
        this.from = from;
    }
    
    public double getTo() {
        return this.to;
    }
    
    public void setTo(final double to) {
        this.to = to;
    }
    
    public double getScale() {
        return this.scale;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
}
