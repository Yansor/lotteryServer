package admin.web.helper;

import javautils.encrypt.TokenUtil;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import admin.domains.content.biz.utils.TreeUtil;
import admin.domains.content.biz.utils.JSMenuVO;
import javautils.StringUtil;
import admin.domains.content.entity.AdminUserAction;
import java.util.Iterator;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.entity.AdminUserRole;
import net.sf.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import admin.web.helper.session.SessionUser;
import admin.web.helper.session.SessionManager;
import admin.domains.content.entity.AdminUser;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import admin.domains.content.dao.AdminUserDao;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.pool.AdminDataFactory;
import org.slf4j.Logger;

public abstract class AbstractActionController
{
    private static final Logger logger;
    @Autowired
    private AdminDataFactory adminDataFactory;
    @Autowired
    private LotteryDataFactory lotteryDataFactory;
    @Autowired
    private AdminUserDao adminUserDao;
    
    static {
        logger = LoggerFactory.getLogger((Class)AbstractActionController.class);
    }
    
    protected final AdminDataFactory getAdminDataFactory() {
        return this.adminDataFactory;
    }
    
    protected final LotteryDataFactory getLotteryDataFactory() {
        return this.lotteryDataFactory;
    }
    
    protected final AdminUser getSessionUser(final HttpSession session) {
        final SessionUser sessionUser = SessionManager.getCurrentUser(session);
        if (sessionUser != null) {
            final AdminUser uEntity = this.adminUserDao.getByUsername(sessionUser.getUsername());
            if (uEntity != null && uEntity.getPassword().equals(sessionUser.getPassword())) {
                return uEntity;
            }
        }
        return null;
    }
    
    protected final List<String> listSysConfigKey(final AdminUser bean) {
        final List<String> list = new ArrayList<String>();
        if (bean != null) {
            final AdminUserRole adminUserRole = this.adminDataFactory.getAdminUserRole(bean.getRoleId());
            final AdminUserMenu adminUserMenu = this.adminDataFactory.getAdminUserMenuByLink("lottery-sys-config");
            if (adminUserRole != null && adminUserMenu != null) {
                final JSONArray uActions = JSONArray.fromObject((Object)adminUserRole.getActions());
                final JSONArray sActions = JSONArray.fromObject((Object)adminUserMenu.getAllActions());
                for (final Object uAid : uActions) {
                    for (final Object sAid : sActions) {
                        if ((int)uAid == (int)sAid) {
                            final AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction((int)sAid);
                            if (adminUserAction == null) {
                                continue;
                            }
                            list.add(adminUserAction.getKey());
                        }
                    }
                }
            }
        }
        return list;
    }
    
    protected final boolean hasAccess(final AdminUser bean, final String actionKey) {
        if (bean != null && bean.getStatus() == 0 && StringUtil.isNotNull(actionKey)) {
            final AdminUserRole adminUserRole = this.adminDataFactory.getAdminUserRole(bean.getRoleId());
            if (adminUserRole != null && adminUserRole.getStatus() == 0 && StringUtil.isNotNull(adminUserRole.getActions())) {
                final JSONArray actionJson = JSONArray.fromObject((Object)adminUserRole.getActions());
                final List<AdminUserAction> adminUserActionList = new ArrayList<AdminUserAction>();
                for (final Object actionId : actionJson) {
                    final AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction((int)actionId);
                    if (adminUserAction != null && adminUserAction.getStatus() == 0) {
                        adminUserActionList.add(adminUserAction);
                    }
                }
                for (final AdminUserAction adminUserAction2 : adminUserActionList) {
                    if (actionKey.equals(adminUserAction2.getKey())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    protected final List<JSMenuVO> listUserMenu(final AdminUser bean) {
        final List<AdminUserMenu> mlist = this.adminDataFactory.getAdminUserMenuByRoleId(bean.getRoleId());
        final List<AdminUserMenu> list = new ArrayList<AdminUserMenu>();
        for (final AdminUserMenu tmpBean : mlist) {
            if (tmpBean.getStatus() != -1) {
                list.add(tmpBean);
            }
        }
        return TreeUtil.listJSMenuRoot(TreeUtil.listMenuRoot(list));
    }
    
    protected final void setSessionUser(final HttpSession session, final AdminUser bean) {
        final SessionUser sessionUser = new SessionUser();
        sessionUser.setUsername(bean.getUsername());
        sessionUser.setPassword(bean.getPassword());
        sessionUser.setRoleId(bean.getRoleId());
        SessionManager.setCurrentUser(session, sessionUser);
    }
    
    protected final void setGoogleBindUser(final HttpSession session, final AdminUser bean) {
        session.setAttribute("SESSION_GOOGLE_USER", (Object)bean);
    }
    
    protected final AdminUser getGoogleBindUser(final HttpSession session) {
        final Object attribute = session.getAttribute("SESSION_GOOGLE_USER");
        if (attribute == null) {
            return null;
        }
        return (AdminUser)attribute;
    }
    
    protected final void clearGoogleBindUser(final HttpSession session) {
        session.removeAttribute("SESSION_GOOGLE_USER");
    }
    
    protected final AdminUser getCurrUser(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final AdminUser uEntity = this.getSessionUser(session);
            if (uEntity != null) {
                return uEntity;
            }
        }
        catch (Exception e) {
            AbstractActionController.logger.error("从session获取用户失败", (Throwable)e);
        }
        return null;
    }
    
    protected final void logOut(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        session.invalidate();
    }
    
    protected String generateDisposableToken(final HttpSession session, final HttpServletRequest request) {
        final Object attribute = session.getAttribute("DISPOSABLE_TOKEN");
        if (attribute != null) {
            return attribute.toString();
        }
        final String tokenStr = TokenUtil.generateDisposableToken();
        session.setAttribute("DISPOSABLE_TOKEN", (Object)tokenStr);
        return tokenStr;
    }
    
    protected String getDisposableToken(final HttpSession session, final HttpServletRequest request) {
        final Object disposableToken = session.getAttribute("DISPOSABLE_TOKEN");
        if (disposableToken == null) {
            return null;
        }
        return disposableToken.toString();
    }
    
    protected boolean setUnlockWithdrawPwd(final HttpSession session, final boolean unlocked) {
        session.setAttribute("SESSION_UNLOCK_WITHDARWPWD", (Object)unlocked);
        return true;
    }
    
    protected boolean isUnlockedWithdrawPwd(final HttpSession session) {
        final Object attribute = session.getAttribute("SESSION_UNLOCK_WITHDARWPWD");
        return attribute != null && (boolean)attribute;
    }
}
