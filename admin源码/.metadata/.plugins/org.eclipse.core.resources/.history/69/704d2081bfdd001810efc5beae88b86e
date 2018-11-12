package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import lottery.domains.content.vo.chart.ChartLineVO;
import java.util.List;
import lottery.domains.content.vo.chart.RechargeWithdrawTotal;
import admin.domains.content.entity.AdminUser;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryStatService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class RechargeWithdrawController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryStatService lotteryStatService;
    
    @RequestMapping(value = { "/recharge-withdraw-complex" }, method = { RequestMethod.POST })
    @ResponseBody
    public void RECHARGE_WITHDRAW_COMPLEX(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/recharge-withdraw-complex";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String sDate = request.getParameter("sDate");
                final String eDate = request.getParameter("eDate");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer subtype = HttpUtil.getIntParameter(request, "subtype");
                if (StringUtils.isNotEmpty(sDate) && StringUtils.isNotEmpty(eDate)) {
                    final RechargeWithdrawTotal totalRechargeWithdrawData = this.lotteryStatService.getTotalRechargeWithdrawData(sDate, eDate, type, subtype);
                    final List<ChartLineVO> rechargeWithdrawDataChart = this.lotteryStatService.getRechargeWithdrawDataChart(sDate, eDate, type, subtype);
                    final Map<String, Object> data = new HashMap<String, Object>();
                    data.put("total", totalRechargeWithdrawData);
                    data.put("charts", rechargeWithdrawDataChart);
                    json.accumulate("data", data);
                    json.set(0, "0-3");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
