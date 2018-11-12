package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import java.util.Iterator;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import lottery.domains.content.entity.User;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.web.content.utils.UserCodePointUtil;
import admin.domains.jobs.AdminUserActionLogJob;
import lottery.domains.content.biz.UserDailySettleBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserDailySettleBillController extends AbstractActionController
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDailySettleBillService settleBillService;
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    
    @RequestMapping(value = { "/lottery-user-daily-settle-bill/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_DAILY_SETTLE_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-daily-settle-bill/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Double minUserAmount = HttpUtil.getDoubleParameter(request, "minUserAmount");
                final Double maxUserAmount = HttpUtil.getDoubleParameter(request, "maxUserAmount");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final List<Integer> userIds = new ArrayList<Integer>();
                boolean legalUser = true;
                if (StringUtils.isNotEmpty(username)) {
                    final User user = this.userDao.getByUsername(username);
                    if (user == null) {
                        legalUser = false;
                    }
                    else {
                        userIds.add(user.getId());
                        final List<User> userDirectLowers = this.userDao.getUserDirectLower(user.getId());
                        for (final User userDirectLower : userDirectLowers) {
                            userIds.add(userDirectLower.getId());
                        }
                    }
                }
                if (!legalUser) {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                else {
                    final PageList pList = this.settleBillService.search(userIds, sTime, eTime, minUserAmount, maxUserAmount, status, start, limit);
                    if (pList != null) {
                        json.accumulate("totalCount", pList.getCount());
                        json.accumulate("data", pList.getList());
                    }
                    else {
                        json.accumulate("totalCount", 0);
                        json.accumulate("data", "[]");
                    }
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
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
