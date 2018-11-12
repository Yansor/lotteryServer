package lottery.web.content;

import javautils.encrypt.PasswordUtil;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.lottery.LotteryOpenCodeVO;
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
import lottery.web.content.validate.CodeValidate;
import lottery.domains.content.biz.LotteryOpenCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class LotteryOpenCodeController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private LotteryOpenCodeService lotteryOpenCodeService;
    @Autowired
    private CodeValidate codeValidate;
    
    @RequestMapping(value = { "/lottery-open-code/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_CODE_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-code/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String lottery = request.getParameter("lottery");
                final String expect = request.getParameter("expect");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.lotteryOpenCodeService.search(lottery, expect, start, limit);
                json.accumulate("totalCount", pList.getCount());
                json.accumulate("data", pList.getList());
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
    
    @RequestMapping(value = { "/lottery-open-code/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_CODE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-code/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String lottery = request.getParameter("lottery");
            final String expect = request.getParameter("expect");
            final LotteryOpenCodeVO entity = this.lotteryOpenCodeService.get(lottery, expect);
            json.accumulate("data", entity);
            json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-open-code/delete" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_CODE_DELETE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-code/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String lottery = request.getParameter("lottery");
            final String expect = request.getParameter("expect");
            final LotteryOpenCodeVO entity = this.lotteryOpenCodeService.get(lottery, expect);
            final LotteryOpenCode bean = entity.getBean();
            this.lotteryOpenCodeService.delete(bean);
            json.set(0, "0-3");
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
    
    @RequestMapping(value = { "/lottery-open-code/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_CODE_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-status/manual-control";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String lottery = request.getParameter("lottery");
                final String expect = request.getParameter("expect");
                final String code = request.getParameter("code");
                if (this.codeValidate.validateCode(json, lottery, code) && this.codeValidate.validateExpect(json, lottery, expect)) {
                    final boolean result = this.lotteryOpenCodeService.add(json, lottery, expect, code, uEntity.getUsername());
                    if (result) {
                        json.set(0, "0-5");
                    }
                }
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
    
    @RequestMapping(value = { "/lottery-open-code/correct" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_OPEN_CODE_CORRECT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-open-status/manual-control";
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String lottery = request.getParameter("lottery");
                final String expect = request.getParameter("expect");
                final String code = request.getParameter("code");
                final String moneyPwd = request.getParameter("moneyPwd");
                final String token = this.getDisposableToken(session, request);
                if (PasswordUtil.validatePassword(uEntity.getWithdrawPwd(), token, moneyPwd)) {
                    if (!PasswordUtil.isSimplePassword(uEntity.getWithdrawPwd())) {
                        if (this.isUnlockedWithdrawPwd(session)) {
                            if (this.codeValidate.validateCode(json, lottery, code)) {
                                final boolean result = this.lotteryOpenCodeService.add(json, lottery, expect, code, "手动修正号码");
                                if (result) {
                                    json.set(0, "0-5");
                                }
                            }
                        }
                        else {
                            json.set(2, "2-43");
                        }
                    }
                    else {
                        json.set(2, "2-41");
                    }
                }
                else {
                    json.set(2, "2-12");
                }
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
