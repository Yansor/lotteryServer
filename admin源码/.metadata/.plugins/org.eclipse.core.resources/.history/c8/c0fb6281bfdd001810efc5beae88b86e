package lottery.web.content;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import lottery.domains.content.entity.User;
import admin.domains.content.entity.AdminUser;
import org.apache.commons.lang.StringUtils;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import lottery.domains.content.biz.UserGameWaterBillService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.UserDao;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserGameWaterBillController extends AbstractActionController
{
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserGameWaterBillService uGameWaterBillService;
    @Autowired
    private UserCodePointUtil uCodePointUtil;
    @Autowired
    private LotteryDataFactory dataFactory;
    
    @RequestMapping(value = { "/user-game-water-bill/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void USER_GAME_WATER_BILL_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/user-game-water-bill/list";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String sTime = request.getParameter("sTime");
                final String eTime = request.getParameter("eTime");
                final Double minUserAmount = HttpUtil.getDoubleParameter(request, "minUserAmount");
                final Double maxUserAmount = HttpUtil.getDoubleParameter(request, "maxUserAmount");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final Integer type = HttpUtil.getIntParameter(request, "type");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                Integer userId = null;
                if (StringUtils.isNotEmpty(username)) {
                    final User user = this.uDao.getByUsername(username);
                    if (user != null) {
                        userId = user.getId();
                    }
                }
                final PageList pList = this.uGameWaterBillService.search(userId, sTime, eTime, minUserAmount, maxUserAmount, type, status, start, limit);
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
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
