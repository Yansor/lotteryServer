package lottery.domains.content.vo.chart;

public class ChartPieVO
{
  public  static class PieValue{

    public PieValue(ChartPieVO VO ,String str , Number number){

    }

}
  private String[] legend;
  private ChartPieVO.PieValue[] series;
  
  public String[] getLegend()
  {
    return this.legend;
  }
  
  public void setLegend(String[] legend)
  {
    this.legend = legend;
  }
  
  public ChartPieVO.PieValue[] getSeries()
  {
    return this.series;
  }
  
  public void setSeries(ChartPieVO.PieValue[] series)
  {
    this.series = series;
  }
}
