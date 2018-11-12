package lottery.web.content;

import lottery.domains.content.entity.SysNotice;
import net.sf.json.JSONObject;
import javautils.date.Moment;
import javautils.StringUtil;
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
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.SysNoticeService;
import lottery.domains.content.dao.SysNoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.jobs.AdminUserActionLogJob;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class SysNoticeController extends AbstractActionController
{
    @Autowired
    private AdminUserActionLogJob adminUserActionLogJob;
    @Autowired
    private SysNoticeDao sysNoticeDao;
    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private UserSysMessageService mUserSysMessageService;
    
    @RequestMapping(value = { "/lottery-sys-notice/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-notice/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final Integer status = HttpUtil.getIntParameter(request, "status");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.sysNoticeService.search(status, start, limit);
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
    
    @RequestMapping(value = { "/lottery-sys-notice/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-notice/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String title = request.getParameter("title");
                final String content = request.getParameter("content");
                final String simpleContent = request.getParameter("simpleContent");
                final int sort = HttpUtil.getIntParameter(request, "sort");
                final int status = HttpUtil.getIntParameter(request, "status");
                String date = request.getParameter("date");
                if (!StringUtil.isNotNull(date)) {
                    date = new Moment().toSimpleDate();
                }
                final boolean result = this.sysNoticeService.add(title, content, simpleContent, sort, status, date);
                if (result) {
                    json.set(0, "0-6");
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
    
    @RequestMapping(value = { "/lottery-sys-notice/edit" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE_EDIT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-notice/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String title = request.getParameter("title");
                final String content = request.getParameter("content");
                final String simpleContent = request.getParameter("simpleContent");
                final int sort = HttpUtil.getIntParameter(request, "sort");
                final int status = HttpUtil.getIntParameter(request, "status");
                String date = request.getParameter("date");
                if (!StringUtil.isNotNull(date)) {
                    date = new Moment().toSimpleDate();
                }
                final boolean result = this.sysNoticeService.edit(id, title, content, simpleContent, sort, status, date);
                if (result) {
                    json.set(0, "0-6");
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
    
    @RequestMapping(value = { "/lottery-sys-notice/update-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE_UPDATE_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-notice/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.sysNoticeService.updateStatus(id, status);
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
    
    @RequestMapping(value = { "/lottery-sys-notice/update-sort" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE_UPDATE_SORT(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-notice/edit";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int sort = HttpUtil.getIntParameter(request, "sort");
                final boolean result = this.sysNoticeService.updateSort(id, sort);
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
    
    @RequestMapping(value = { "/lottery-sys-notice/delete" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE_DELETE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/lottery-sys-notice/delete";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final boolean result = this.sysNoticeService.delete(id);
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
    
    @RequestMapping(value = { "/lottery-sys-notice/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void LOTTERY_SYS_NOTICE_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final int id = HttpUtil.getIntParameter(request, "id");
        final SysNotice bean = this.sysNoticeDao.getById(id);
        final JSONObject json = JSONObject.fromObject((Object)bean);
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
