package admin.domains.pool.impl;

import admin.domains.content.dao.AdminUserActionDao;
import admin.domains.content.dao.AdminUserDao;
import admin.domains.content.dao.AdminUserMenuDao;
import admin.domains.content.dao.AdminUserRoleDao;
import admin.domains.content.entity.AdminUser;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.vo.AdminUserBaseVO;
import admin.domains.pool.AdminDataFactory;
import admin.web.WSC;
import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import javautils.StringUtil;
import javautils.ip.IpUtil;
import javax.servlet.ServletContext;
import lottery.domains.content.biz.UserHighPrizeService;
import lottery.web.websocket.WebSocketMsgSender;
import net.sf.json.JSONArray;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ServletContextAware;

@Component
public class AdminDataFactoryImpl
  implements AdminDataFactory, InitializingBean, ServletContextAware
{
  private static final Logger logger = LoggerFactory.getLogger(AdminDataFactoryImpl.class);
  private Properties sysMessage;
  @Autowired
  private AdminUserDao adminUserDao;
  
  public void init()
  {
    logger.info("init AdminDataFactory....start");
    initSysMessage();
    initIpData();
    
    initAdminUserAction();
    initAdminUserMenu();
    initAdminUserRole();
    logger.info("init AdminDataFactory....done");
  }
  
  public void setServletContext(ServletContext context)
  {
    WSC.PROJECT_PATH = context.getRealPath("").replace("\\", "/");
    logger.info("Project Path:" + WSC.PROJECT_PATH);
  }
  
  private void initIpData()
  {
    try
    {
      File file = ResourceUtils.getFile("classpath:config/ip/17monipdb.dat");
      IpUtil.load(file.getPath());
      logger.info("初始化ip数据库完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化ip数据库失败！");
    }
  }
  
  public void afterPropertiesSet()
    throws Exception
  {
    init();
  }
  
  public void initSysMessage()
  {
    try
    {
      String fileClassPath = "classpath:config/message/language.cn.xml";
      File file = ResourceUtils.getFile(fileClassPath);
      if (file != null)
      {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(file);
        properties.loadFromXML(inputStream);
        inputStream.close();
        if (this.sysMessage != null) {
          this.sysMessage.clear();
        }
        this.sysMessage = properties;
        logger.info("初始化语言文件完成。");
      }
      else
      {
        throw new FileNotFoundException();
      }
    }
    catch (Exception e)
    {
      logger.error("加载语言文件失败！", e);
    }
  }
  
  public String getSysMessage(String key)
  {
    return this.sysMessage.getProperty(key);
  }
  
  private Map<Integer, AdminUserBaseVO> adminUserMap = new LinkedHashMap();
  @Autowired
  private AdminUserActionDao adminUserActionDao;
  
  public void initAdminUser()
  {
    try
    {
      List<AdminUser> list = this.adminUserDao.listAll();
      Map<Integer, AdminUserBaseVO> tmpMap = new LinkedHashMap();
      for (AdminUser adminUser : list) {
        tmpMap.put(Integer.valueOf(adminUser.getId()), new AdminUserBaseVO(adminUser.getId(), adminUser.getUsername()));
      }
      if (this.adminUserMap != null) {
        this.adminUserMap.clear();
      }
      this.adminUserMap = tmpMap;
      logger.info("初始化系统用户完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化系统用户失败！");
    }
  }
  
  public AdminUserBaseVO getAdminUser(int id)
  {
    if (this.adminUserMap.containsKey(Integer.valueOf(id))) {
      return (AdminUserBaseVO)this.adminUserMap.get(Integer.valueOf(id));
    }
    AdminUser adminUser = this.adminUserDao.getById(id);
    if (adminUser != null)
    {
      this.adminUserMap.put(Integer.valueOf(adminUser.getId()), new AdminUserBaseVO(adminUser.getId(), adminUser.getUsername()));
      return (AdminUserBaseVO)this.adminUserMap.get(Integer.valueOf(id));
    }
    return null;
  }
  
  private Map<Integer, AdminUserAction> adminUserActionMap = new LinkedHashMap();
  @Autowired
  private AdminUserMenuDao adminUserMenuDao;
  
  public void initAdminUserAction()
  {
    try
    {
      List<AdminUserAction> list = this.adminUserActionDao.listAll();
      Map<Integer, AdminUserAction> tmpMap = new LinkedHashMap();
      for (AdminUserAction adminUserAction : list) {
        tmpMap.put(Integer.valueOf(adminUserAction.getId()), adminUserAction);
      }
      if (this.adminUserActionMap != null) {
        this.adminUserActionMap.clear();
      }
      this.adminUserActionMap = tmpMap;
      logger.info("初始化管理员行为分组完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化管理员行为分组失败！");
    }
  }
  
  public List<AdminUserAction> listAdminUserAction()
  {
    List<AdminUserAction> list = new ArrayList();
    Object[] keys = this.adminUserActionMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      list.add((AdminUserAction)this.adminUserActionMap.get(o));
    }
    return list;
  }
  
  public AdminUserAction getAdminUserAction(int id)
  {
    if (this.adminUserActionMap.containsKey(Integer.valueOf(id))) {
      return (AdminUserAction)this.adminUserActionMap.get(Integer.valueOf(id));
    }
    return null;
  }
  
  public AdminUserAction getAdminUserAction(String actionKey)
  {
    if (StringUtil.isNotNull(actionKey))
    {
      Object[] keys = this.adminUserActionMap.keySet().toArray();
      Object[] arrayOfObject1;
      int j = (arrayOfObject1 = keys).length;
      for (int i = 0; i < j; i++)
      {
        Object o = arrayOfObject1[i];
        AdminUserAction adminUserAction = (AdminUserAction)this.adminUserActionMap.get(o);
        if (actionKey.equals(adminUserAction.getKey())) {
          return adminUserAction;
        }
      }
    }
    return null;
  }
  
  public List<AdminUserAction> getAdminUserActionByRoleId(int role)
  {
    List<AdminUserAction> list = new ArrayList();
    if (this.adminUserRoleMap.containsKey(Integer.valueOf(role)))
    {
      AdminUserRole adminUserRole = (AdminUserRole)this.adminUserRoleMap.get(Integer.valueOf(role));
      if (StringUtil.isNotNull(adminUserRole.getActions()))
      {
        JSONArray menuJson = JSONArray.fromObject(adminUserRole.getActions());
        for (Object obj : menuJson) {
          if (this.adminUserActionMap.containsKey(Integer.valueOf(((Integer)obj).intValue()))) {
            list.add((AdminUserAction)this.adminUserActionMap.get(Integer.valueOf(((Integer)obj).intValue())));
          }
        }
      }
    }
    return list;
  }
  
  private Map<Integer, AdminUserMenu> adminUserMenuMap = new LinkedHashMap();
  @Autowired
  private AdminUserRoleDao adminUserRoleDao;
  
  public void initAdminUserMenu()
  {
    try
    {
      List<AdminUserMenu> list = this.adminUserMenuDao.listAll();
      Map<Integer, AdminUserMenu> tmpMap = new LinkedHashMap();
      for (AdminUserMenu adminUserMenu : list) {
        tmpMap.put(Integer.valueOf(adminUserMenu.getId()), adminUserMenu);
      }
      if (this.adminUserMenuMap != null) {
        this.adminUserMenuMap.clear();
      }
      this.adminUserMenuMap = tmpMap;
      logger.info("初始化管理员菜单完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化管理员菜单失败！");
    }
  }
  
  public List<AdminUserMenu> listAdminUserMenu()
  {
    List<AdminUserMenu> list = new ArrayList();
    Object[] keys = this.adminUserMenuMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      
      list.add(((AdminUserMenu)this.adminUserMenuMap.get(o)).clone());
    }
    return list;
  }
  
  public AdminUserMenu getAdminUserMenuByLink(String link)
  {
    Object[] keys = this.adminUserMenuMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      AdminUserMenu tmpMenu = (AdminUserMenu)this.adminUserMenuMap.get(o);
      if ((link != null) && (link.equals(tmpMenu.getLink()))) {
        return tmpMenu.clone();
      }
    }
    return null;
  }
  
  public List<AdminUserMenu> getAdminUserMenuByRoleId(int role)
  {
    List<AdminUserMenu> list = new ArrayList();
    if (this.adminUserRoleMap.containsKey(Integer.valueOf(role)))
    {
      AdminUserRole adminUserRole = (AdminUserRole)this.adminUserRoleMap.get(Integer.valueOf(role));
      if (StringUtil.isNotNull(adminUserRole.getMenus()))
      {
        List<Integer> menuIds = JSON.parseArray(adminUserRole.getMenus(), Integer.class);
        for (Object menuId : menuIds) {
          if (this.adminUserMenuMap.containsKey(menuId)) {
            list.add(((AdminUserMenu)this.adminUserMenuMap.get(menuId)).clone());
          }
        }
      }
    }
    Collections.sort(list, new Comparator<AdminUserMenu>() {
      @Override
      public int compare(AdminUserMenu o1, AdminUserMenu o2) {
        return o1.getId() - o2.getId() ;
      }
    });
    
    return list;
  }
  
  public Set<Integer> getAdminUserMenuIdsByAction(int action)
  {
    Set<Integer> set = new HashSet();
    Object[] keys = this.adminUserMenuMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      
      AdminUserMenu tmpBean = ((AdminUserMenu)this.adminUserMenuMap.get(o)).clone();
      if (tmpBean.getBaseAction() != 0)
      {
        JSONArray jsonArrayActions = JSONArray.fromObject(tmpBean.getAllActions());
        if (jsonArrayActions.contains(Integer.valueOf(action)))
        {
          set.add(Integer.valueOf(tmpBean.getId()));
          
          int upid = tmpBean.getUpid();
          do
          {
            if (this.adminUserMenuMap.containsKey(Integer.valueOf(upid)))
            {
              AdminUserMenu upMenu = ((AdminUserMenu)this.adminUserMenuMap.get(Integer.valueOf(upid))).clone();
              if (upMenu != null)
              {
                set.add(Integer.valueOf(upMenu.getId()));
                upid = upMenu.getUpid();
              }
            }
          } while (upid != 0);
        }
      }
    }
    return set;
  }
  
  private Map<Integer, AdminUserRole> adminUserRoleMap = new LinkedHashMap();
  
  public void initAdminUserRole()
  {
    try
    {
      List<AdminUserRole> list = this.adminUserRoleDao.listAll();
      Map<Integer, AdminUserRole> tmpMap = new LinkedHashMap();
      for (AdminUserRole adminUserRole : list) {
        tmpMap.put(Integer.valueOf(adminUserRole.getId()), adminUserRole);
      }
      if (this.adminUserRoleMap != null) {
        this.adminUserRoleMap.clear();
      }
      this.adminUserRoleMap = tmpMap;
      logger.info("初始化管理员角色完成！");
    }
    catch (Exception e)
    {
      logger.error("初始化管理员角色失败！");
    }
  }
  
  public AdminUserRole getAdminUserRole(int id)
  {
    if (this.adminUserRoleMap.containsKey(Integer.valueOf(id))) {
      return ((AdminUserRole)this.adminUserRoleMap.get(Integer.valueOf(id))).clone();
    }
    return null;
  }
  
  public List<AdminUserRole> listAdminUserRole()
  {
    List<AdminUserRole> adminUserRoleList = new ArrayList();
    Object[] keys = this.adminUserRoleMap.keySet().toArray();
    Object[] arrayOfObject1;
    int j = (arrayOfObject1 = keys).length;
    for (int i = 0; i < j; i++)
    {
      Object o = arrayOfObject1[i];
      adminUserRoleList.add(((AdminUserRole)this.adminUserRoleMap.get(o)).clone());
    }
    return adminUserRoleList;
  }
  
  private static volatile boolean isRunningHighPrizeNotice = false;
  private static Object highPrizeNoticeLock = new Object();
  @Autowired
  private UserHighPrizeService highPrizeService;
  @Autowired
  private WebSocketMsgSender msgSender;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="0/3 * * * * *")
  public void highPrizeNoticesJob()
  {
    // Byte code:
    //   0: getstatic 59	admin/domains/pool/impl/AdminDataFactoryImpl:highPrizeNoticeLock	Ljava/lang/Object;
    //   3: dup
    //   4: astore_1
    //   5: monitorenter
    //   6: getstatic 54	admin/domains/pool/impl/AdminDataFactoryImpl:isRunningHighPrizeNotice	Z
    //   9: ifeq +6 -> 15
    //   12: aload_1
    //   13: monitorexit
    //   14: return
    //   15: iconst_1
    //   16: putstatic 54	admin/domains/pool/impl/AdminDataFactoryImpl:isRunningHighPrizeNotice	Z
    //   19: aload_1
    //   20: monitorexit
    //   21: goto +6 -> 27
    //   24: aload_1
    //   25: monitorexit
    //   26: athrow
    //   27: aload_0
    //   28: invokespecial 482	admin/domains/pool/impl/AdminDataFactoryImpl:highPrizeNotices	()V
    //   31: goto +10 -> 41
    //   34: astore_1
    //   35: iconst_0
    //   36: putstatic 54	admin/domains/pool/impl/AdminDataFactoryImpl:isRunningHighPrizeNotice	Z
    //   39: aload_1
    //   40: athrow
    //   41: iconst_0
    //   42: putstatic 54	admin/domains/pool/impl/AdminDataFactoryImpl:isRunningHighPrizeNotice	Z
    //   45: return
    // Line number table:
    //   Java source line #392	-> byte code offset #0
    //   Java source line #393	-> byte code offset #6
    //   Java source line #395	-> byte code offset #12
    //   Java source line #397	-> byte code offset #15
    //   Java source line #392	-> byte code offset #19
    //   Java source line #402	-> byte code offset #27
    //   Java source line #403	-> byte code offset #31
    //   Java source line #404	-> byte code offset #35
    //   Java source line #405	-> byte code offset #39
    //   Java source line #404	-> byte code offset #41
    //   Java source line #406	-> byte code offset #45
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	46	0	this	AdminDataFactoryImpl
    //   4	36	1	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   6	14	24	finally
    //   15	21	24	finally
    //   24	26	24	finally
    //   27	34	34	finally
  }
  
  private void highPrizeNotices()
  {
    Map<String, String> allHighPrizeNotices = this.highPrizeService.getAllHighPrizeNotices();
    if (MapUtils.isEmpty(allHighPrizeNotices)) {
      return;
    }
    Set<String> keys = allHighPrizeNotices.keySet();
    for (String key : keys)
    {
      this.msgSender.sendHighPrizeNotice((String)allHighPrizeNotices.get(key));
      this.highPrizeService.delHighPrizeNotice(key);
    }
  }
}
