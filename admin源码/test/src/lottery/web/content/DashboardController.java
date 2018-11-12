package lottery.web.content;

import lottery.domains.content.vo.chart.ChartPieVO;
import lottery.domains.content.vo.chart.ChartLineVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.http.HttpUtil;
import javautils.date.Moment;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.LotteryStatService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class DashboardController extends AbstractActionController
{
    @Autowired
    private LotteryStatService lotteryStatService;
    
    @RequestMapping(value = { "/dashboard/total-info" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_TOTAL_INFO(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final JSONObject json = new JSONObject();
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final int totalUserRegist = this.lotteryStatService.getTotalUserRegist(sTime, eTime);
        final long totalBetsMoney = this.lotteryStatService.getTotalBetsMoney(sTime, eTime);
        final int totalOrderCount = this.lotteryStatService.getTotalOrderCount(sTime, eTime);
        final double totalProfitMoney = (double)this.lotteryStatService.getTotalProfitMoney(sTime, eTime);
        json.accumulate("totalUserRegist", totalUserRegist);
        json.accumulate("totalBetsMoney", totalBetsMoney);
        json.accumulate("totalOrderCount", totalOrderCount);
        json.accumulate("totalProfitMoney", totalProfitMoney);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/dashboard/chart-user-regist" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_CHART_USER_REGIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final ChartLineVO lineVO = this.lotteryStatService.getUserRegistChart(sDate, eDate);
        final JSONObject json = JSONObject.fromObject((Object)lineVO);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/dashboard/chart-user-login" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_CHART_USER_LOGIN(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final ChartLineVO lineVO = this.lotteryStatService.getUserLoginChart(sDate, eDate);
        final JSONObject json = JSONObject.fromObject((Object)lineVO);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/dashboard/chart-user-bets" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_CHART_USER_BETS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final Integer type = HttpUtil.getIntParameter(request, "type");
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final ChartLineVO lineVO = this.lotteryStatService.getUserBetsChart(type, id, sDate, eDate);
        final JSONObject json = JSONObject.fromObject((Object)lineVO);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/dashboard/chart-user-cash" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_CHART_USER_CASH(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final ChartLineVO lineVO = this.lotteryStatService.getUserCashChart(sDate, eDate);
        final JSONObject json = JSONObject.fromObject((Object)lineVO);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/dashboard/chart-user-complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_CHART_USER_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final Integer type = HttpUtil.getIntParameter(request, "type");
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final ChartLineVO lineVO = this.lotteryStatService.getUserComplexChart(type, id, sDate, eDate);
        final JSONObject json = JSONObject.fromObject((Object)lineVO);
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/dashboard/chart-lottery-hot" }, method = { RequestMethod.POST })
    @ResponseBody
    public void DASHBOARD_CHART_LOTTERY_HOT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String sDate = request.getParameter("sDate");
        final String eDate = request.getParameter("eDate");
        final String sTime = new Moment().fromDate(sDate).toSimpleDate();
        final String eTime = new Moment().fromDate(eDate).add(1, "days").toSimpleDate();
        final Integer type = HttpUtil.getIntParameter(request, "type");
        final ChartPieVO pieVO = this.lotteryStatService.getLotteryHotChart(type, sTime, eTime);
        final JSONObject json = JSONObject.fromObject((Object)pieVO);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
