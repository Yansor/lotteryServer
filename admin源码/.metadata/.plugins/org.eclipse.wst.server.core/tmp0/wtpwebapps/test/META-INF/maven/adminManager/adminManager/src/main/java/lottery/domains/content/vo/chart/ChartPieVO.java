package lottery.domains.content.vo.chart;

public class ChartPieVO
{
    private String[] legend;
    private PieValue[] series;
    
    public String[] getLegend() {
        return this.legend;
    }
    
    public void setLegend(final String[] legend) {
        this.legend = legend;
    }
    
    public PieValue[] getSeries() {
        return this.series;
    }
    
    public void setSeries(final PieValue[] series) {
        this.series = series;
    }
    
    public class PieValue
    {
        private String name;
        private Number value;
        
        public PieValue() {
        }
        
        public PieValue(final String name, final Number value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return this.name;
        }
        
        public void setName(final String name) {
            this.name = name;
        }
        
        public Number getValue() {
            return this.value;
        }
        
        public void setValue(final Number value) {
            this.value = value;
        }
    }
}
