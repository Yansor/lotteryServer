package lottery.web.content;

import admin.web.helper.AbstractActionController;
import javautils.date.Moment;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryStatService;
import lottery.domains.content.vo.chart.ChartLineVO;
import lottery.domains.content.vo.chart.ChartPieVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController
  extends AbstractActionController
{
  @Autowired
  private LotteryStatService lotteryStatService;
  
  @RequestMapping(value={"/dashboard/total-info"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_TOTAL_INFO(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    JSONObject json = new JSONObject();
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    
    int totalUserRegist = this.lotteryStatService.getTotalUserRegist(sTime, eTime);
    
    long totalBetsMoney = this.lotteryStatService.getTotalBetsMoney(sTime, eTime);
    
    int totalOrderCount = this.lotteryStatService.getTotalOrderCount(sTime, eTime);
    
    double totalProfitMoney = this.lotteryStatService.getTotalProfitMoney(sTime, eTime);
    json.accumulate("totalUserRegist", totalUserRegist);
    json.accumulate("totalBetsMoney", totalBetsMoney);
    json.accumulate("totalOrderCount", totalOrderCount);
    json.accumulate("totalProfitMoney", totalProfitMoney);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/dashboard/chart-user-regist"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_CHART_USER_REGIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    ChartLineVO lineVO = this.lotteryStatService.getUserRegistChart(sDate, eDate);
    JSONObject json = JSONObject.fromObject(lineVO);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/dashboard/chart-user-login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_CHART_USER_LOGIN(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    ChartLineVO lineVO = this.lotteryStatService.getUserLoginChart(sDate, eDate);
    JSONObject json = JSONObject.fromObject(lineVO);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/dashboard/chart-user-bets"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_CHART_USER_BETS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    Integer type = HttpUtil.getIntParameter(request, "type");
    Integer id = HttpUtil.getIntParameter(request, "id");
    ChartLineVO lineVO = this.lotteryStatService.getUserBetsChart(type, id, sDate, eDate);
    JSONObject json = JSONObject.fromObject(lineVO);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/dashboard/chart-user-cash"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_CHART_USER_CASH(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    ChartLineVO lineVO = this.lotteryStatService.getUserCashChart(sDate, eDate);
    JSONObject json = JSONObject.fromObject(lineVO);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/dashboard/chart-user-complex"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_CHART_USER_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    Integer type = HttpUtil.getIntParameter(request, "type");
    Integer id = HttpUtil.getIntParameter(request, "id");
    ChartLineVO lineVO = this.lotteryStatService.getUserComplexChart(type, id, sDate, eDate);
    JSONObject json = JSONObject.fromObject(lineVO);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/dashboard/chart-lottery-hot"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void DASHBOARD_CHART_LOTTERY_HOT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String sDate = request.getParameter("sDate");
    String eDate = request.getParameter("eDate");
    
    String sTime = new Moment().fromDate(sDate).toSimpleDate();
    String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
    
    Integer type = HttpUtil.getIntParameter(request, "type");
    ChartPieVO pieVO = this.lotteryStatService.getLotteryHotChart(type, sTime, eTime);
    JSONObject json = JSONObject.fromObject(pieVO);
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
