package lottery.web.content;

import lottery.domains.content.vo.user.HistoryUserBetsVO;
import lottery.domains.content.vo.user.UserBetsVO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.biz.UserBillService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserBillController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private UserBillService uBillService;
    @Autowired
    private UserBetsService uBetsService;
    
    @RequestMapping(value = { "/lottery-user-bill/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-bill/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String keyword = request.getParameter("keyword");
                final String username = request.getParameter("username");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer utype = HttpUtil.getIntParameter(request, "utype");
                final String minTime = request.getParameter("minTime");
                final String maxTime = request.getParameter("maxTime");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBillService.search(keyword, username, utype, type, minTime, maxTime, minMoney, maxMoney, status, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-user-bill/details" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_BILL_DETAILS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int id = HttpUtil.getIntParameter(request, "id");
            final UserBetsVO result = this.uBetsService.getById(id);
            json.accumulate("data", result);
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/history-lottery-user-bill/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/history-lottery-user-bill/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String keyword = request.getParameter("keyword");
                final String username = request.getParameter("username");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final Integer utype = HttpUtil.getIntParameter(request, "utype");
                final String minTime = request.getParameter("minTime");
                final String maxTime = request.getParameter("maxTime");
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxMoney");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uBillService.searchHistory(keyword, username, type, minTime, maxTime, minMoney, maxMoney, status, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/history-lottery-user-bill/details" }, method = { RequestMethod.POST })
    @ResponseBody
    public void HISTORY_LOTTERY_USER_BILL_DETAILS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int id = HttpUtil.getIntParameter(request, "id");
            final HistoryUserBetsVO result = this.uBetsService.getHistoryById(id);
            json.accumulate("data", result);
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
