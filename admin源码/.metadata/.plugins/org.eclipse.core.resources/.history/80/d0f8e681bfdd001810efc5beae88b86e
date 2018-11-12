package lottery.web.content;

import org.apache.commons.lang.StringUtils;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.vo.user.UserCardVO;
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
import lottery.web.content.validate.UserCardValidate;
import lottery.domains.content.biz.UserBankcardUnbindService;
import lottery.domains.content.biz.UserCardService;
import lottery.domains.content.dao.UserCardDao;
import lottery.domains.content.dao.UserDao;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserLogJob;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class UserCardController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private AdminUserLogJob adminUserLogJob;
    @Autowired
    private AdminUserCriticalLogJob adminUserCriticalLogJob;
    @Autowired
    private UserDao uDao;
    @Autowired
    private UserCardDao uCardDao;
    @Autowired
    private UserCardService uCardService;
    @Autowired
    private UserBankcardUnbindService uBankcardUnbindService;
    @Autowired
    private UserCardValidate uCardValidate;
    
    @RequestMapping(value = { "/lottery-user-card/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CARD_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final String keyword = request.getParameter("keyword");
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.uCardService.search(username, keyword, status, start, limit);
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
    
    @RequestMapping(value = { "/lottery-user-card/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CARD_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final UserCardVO result = this.uCardService.getById(id);
                json.accumulate("data", result);
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
    
    @RequestMapping(value = { "/lottery-user-card/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CARD_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int bankId = HttpUtil.getIntParameter(request, "bankId");
                final String bankBranch = request.getParameter("bankBranch");
                final String cardId = request.getParameter("cardId");
                final UserCard cBean = this.uCardDao.getById(id);
                if (cBean != null) {
                    final User targetUser = this.uDao.getById(cBean.getUserId());
                    if (targetUser != null) {
                        final String cardName = targetUser.getWithdrawName();
                        if (this.uCardValidate.required(json, bankId, cardName, cardId)) {
                            if (this.uCardValidate.checkCardId(cardId)) {
                                final UserCard exBean = this.uCardDao.getByCardId(cardId);
                                if (exBean == null || exBean.getId() == id) {
                                    final boolean result = this.uCardService.edit(id, bankId, bankBranch, cardId);
                                    if (result) {
                                        this.adminUserLogJob.logModUserCard(uEntity, request, targetUser.getUsername(), bankId, bankBranch, cardId);
                                        this.adminUserCriticalLogJob.logModUserCard(uEntity, request, targetUser.getUsername(), bankId, bankBranch, cardId, actionKey);
                                        json.set(0, "0-6");
                                    }
                                    else {
                                        json.set(1, "1-6");
                                    }
                                }
                                else {
                                    json.set(2, "2-1015");
                                }
                            }
                            else {
                                json.set(2, "2-1014");
                            }
                        }
                    }
                    else {
                        json.set(2, "2-3");
                    }
                }
                else {
                    json.set(1, "1-6");
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
    
    @RequestMapping(value = { "/lottery-user-card/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CARD_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String username = request.getParameter("username");
                final int bankId = HttpUtil.getIntParameter(request, "bankId");
                final String bankBranch = request.getParameter("bankBranch");
                final String cardId = request.getParameter("cardId");
                final User targetUser = this.uDao.getByUsername(username);
                if (targetUser != null) {
                    if (targetUser.getBindStatus() == 1) {
                        final String cardName = targetUser.getWithdrawName();
                        if (this.uCardValidate.required(json, bankId, cardName, cardId)) {
                            if (this.uCardValidate.checkCardId(cardId)) {
                                if (this.uCardDao.getByCardId(cardId) == null) {
                                    final boolean result = this.uCardService.add(username, bankId, bankBranch, cardName, cardId, 0);
                                    if (result) {
                                        this.adminUserLogJob.logAddUserCard(uEntity, request, username, bankId, bankBranch, cardId);
                                        this.adminUserCriticalLogJob.logAddUserCard(uEntity, request, username, bankId, bankBranch, cardId, actionKey);
                                        json.set(0, "0-6");
                                    }
                                    else {
                                        json.set(1, "1-6");
                                    }
                                }
                                else {
                                    json.set(2, "2-1015");
                                }
                            }
                            else {
                                json.set(2, "2-1014");
                            }
                        }
                    }
                    else {
                        json.set(2, "2-1016");
                    }
                }
                else {
                    json.set(2, "2-3");
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
    
    @RequestMapping(value = { "/lottery-user-card/lock-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_CARD_LOCK_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/lock-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.uCardService.updateStatus(id, status);
                if (result) {
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
    
    @RequestMapping(value = { "/lottery-user-card/unbid-list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UNBIND_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/unbid-list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final String userName = request.getParameter("username");
            final String cardId = request.getParameter("cardId");
            final String unbindTime = request.getParameter("unbindTime");
            final int start = HttpUtil.getIntParameter(request, "start");
            final int limit = HttpUtil.getIntParameter(request, "limit");
            final PageList pList = this.uBankcardUnbindService.search(userName, cardId, unbindTime, start, limit);
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
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/lottery-user-card/unbid-del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_USER_UNBIND_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-user-card/unbid-del";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String cardId = HttpUtil.getStringParameterTrim(request, "cardId");
                final String remark = HttpUtil.getStringParameterTrim(request, "remark");
                if (StringUtils.isEmpty(cardId)) {
                    json.set(2, "2-2");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (StringUtils.isEmpty(remark)) {
                    json.set(2, "2-30");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                if (remark.length() > 128) {
                    json.set(2, "2-35");
                    HttpUtil.write(response, json.toString(), "text/json");
                    return;
                }
                final boolean result = this.uBankcardUnbindService.delByCardId(cardId);
                if (result) {
                    this.adminUserLogJob.logDelUserCardUnbindRecord(uEntity, request, cardId, remark);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
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
