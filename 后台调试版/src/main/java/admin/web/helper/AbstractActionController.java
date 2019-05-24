package admin.web.helper;

import admin.domains.content.biz.utils.JSMenuVO;
import admin.domains.content.biz.utils.TreeUtil;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.pool.AdminDataFactory;
import admin.web.helper.session.SessionManager;
import admin.web.helper.session.SessionUser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javautils.StringUtil;
import javautils.encrypt.TokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.pool.LotteryDataFactory;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractActionController
{
  private static final Logger logger = LoggerFactory.getLogger(AbstractActionController.class);
  @Autowired
  private AdminDataFactory adminDataFactory;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private AdminUserDao adminUserDao;
  
  protected final AdminDataFactory getAdminDataFactory()
  {
    return this.adminDataFactory;
  }
  
  protected final LotteryDataFactory getLotteryDataFactory()
  {
    return this.lotteryDataFactory;
  }
  
  protected final AdminUser getSessionUser(HttpSession session)
  {
    SessionUser sessionUser = SessionManager.getCurrentUser(session);
    if (sessionUser != null)
    {
      AdminUser uEntity = this.adminUserDao.getByUsername(sessionUser.getUsername());
      if ((uEntity != null) && (uEntity.getPassword().equals(sessionUser.getPassword()))) {
        return uEntity;
      }
    }
    return null;
  }
  
  protected final List<String> listSysConfigKey(AdminUser bean)
  {
    List<String> list = new ArrayList();
    if (bean != null)
    {
      AdminUserRole adminUserRole = this.adminDataFactory.getAdminUserRole(bean.getRoleId());
      AdminUserMenu adminUserMenu = this.adminDataFactory.getAdminUserMenuByLink("lottery-sys-config");
      if ((adminUserRole != null) && (adminUserMenu != null))
      {
        JSONArray uActions = JSONArray.fromObject(adminUserRole.getActions());
        JSONArray sActions = JSONArray.fromObject(adminUserMenu.getAllActions());
        Iterator localIterator2;
        for (Iterator localIterator1 = uActions.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
        {
          Object uAid = localIterator1.next();
          localIterator2 = sActions.iterator();
//          continue;
          Object sAid = localIterator2.next();
          if (((Integer)uAid).intValue() == ((Integer)sAid).intValue())
          {
            AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(((Integer)sAid).intValue());
            if (adminUserAction != null) {
              list.add(adminUserAction.getKey());
            }
          }
        }
      }
    }
    return list;
  }
  
  protected final boolean hasAccess(AdminUser bean, String actionKey)
  {
    if ((bean != null) && (bean.getStatus() == 0) && 
      (StringUtil.isNotNull(actionKey)))
    {
      AdminUserRole adminUserRole = this.adminDataFactory.getAdminUserRole(bean.getRoleId());
      if ((adminUserRole != null) && (adminUserRole.getStatus() == 0) && 
        (StringUtil.isNotNull(adminUserRole.getActions())))
      {
        JSONArray actionJson = JSONArray.fromObject(adminUserRole.getActions());
        List<AdminUserAction> adminUserActionList = new ArrayList();
        for (Object actionId : actionJson)
        {
          AdminUserAction adminUserAction = this.adminDataFactory.getAdminUserAction(((Integer)actionId).intValue());
          if ((adminUserAction != null) && (adminUserAction.getStatus() == 0)) {
            adminUserActionList.add(adminUserAction);
          }
        }
        for (AdminUserAction adminUserAction : adminUserActionList) {
          if (actionKey.equals(adminUserAction.getKey())) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  protected final List<JSMenuVO> listUserMenu(AdminUser bean)
  {
    List<AdminUserMenu> mlist = this.adminDataFactory.getAdminUserMenuByRoleId(bean.getRoleId());
    List<AdminUserMenu> list = new ArrayList();
    for (AdminUserMenu tmpBean : mlist) {
      if (tmpBean.getStatus() != -1) {
        list.add(tmpBean);
      }
    }
    return TreeUtil.listJSMenuRoot(TreeUtil.listMenuRoot(list));
  }
  
  protected final void setSessionUser(HttpSession session, AdminUser bean)
  {
    SessionUser sessionUser = new SessionUser();
    sessionUser.setUsername(bean.getUsername());
    sessionUser.setPassword(bean.getPassword());
    sessionUser.setRoleId(bean.getRoleId());
    SessionManager.setCurrentUser(session, sessionUser);
  }
  
  protected final void setGoogleBindUser(HttpSession session, AdminUser bean)
  {
    session.setAttribute("SESSION_GOOGLE_USER", bean);
  }
  
  protected final AdminUser getGoogleBindUser(HttpSession session)
  {
    Object attribute = session.getAttribute("SESSION_GOOGLE_USER");
    if (attribute == null) {
      return null;
    }
    return (AdminUser)attribute;
  }
  
  protected final void clearGoogleBindUser(HttpSession session)
  {
    session.removeAttribute("SESSION_GOOGLE_USER");
  }
  
  protected final AdminUser getCurrUser(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      AdminUser uEntity = getSessionUser(session);
      if (uEntity != null) {
        return uEntity;
      }
    }
    catch (Exception e)
    {
      logger.error("从session获取用户失败", e);
    }
    return null;
  }
  
  protected final void logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    session.invalidate();
  }
  
  protected String generateDisposableToken(HttpSession session, HttpServletRequest request)
  {
    Object attribute = session.getAttribute("DISPOSABLE_TOKEN");
    if (attribute != null) {
      return attribute.toString();
    }
    String tokenStr = TokenUtil.generateDisposableToken();
    session.setAttribute("DISPOSABLE_TOKEN", tokenStr);
    
    return tokenStr;
  }
  
  protected String getDisposableToken(HttpSession session, HttpServletRequest request)
  {
    Object disposableToken = session.getAttribute("DISPOSABLE_TOKEN");
    if (disposableToken == null) {
      return null;
    }
    return disposableToken.toString();
  }
  
  protected boolean setUnlockWithdrawPwd(HttpSession session, boolean unlocked)
  {
    session.setAttribute("SESSION_UNLOCK_WITHDARWPWD", Boolean.valueOf(unlocked));
    return true;
  }
  
  protected boolean isUnlockedWithdrawPwd(HttpSession session)
  {
    Object attribute = session.getAttribute("SESSION_UNLOCK_WITHDARWPWD");
    if (attribute == null) {
      return false;
    }
    return ((Boolean)attribute).booleanValue();
  }
}
