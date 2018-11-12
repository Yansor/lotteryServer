package admin.domains.pool.impl;

import org.apache.commons.collections.MapUtils;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import javautils.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import admin.domains.content.entity.AdminUser;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import javautils.ip.IpUtil;
import org.springframework.util.ResourceUtils;
import admin.web.WSC;
import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import org.slf4j.LoggerFactory;
import lottery.web.websocket.WebSocketMsgSender;
import lottery.domains.content.biz.UserHighPrizeService;
import admin.domains.content.entity.AdminUserRole;
import admin.domains.content.dao.AdminUserRoleDao;
import admin.domains.content.entity.AdminUserMenu;
import admin.domains.content.dao.AdminUserMenuDao;
import admin.domains.content.entity.AdminUserAction;
import admin.domains.content.dao.AdminUserActionDao;
import admin.domains.content.vo.AdminUserBaseVO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import java.util.Properties;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.beans.factory.InitializingBean;
import admin.domains.pool.AdminDataFactory;

@Component
public class AdminDataFactoryImpl implements AdminDataFactory, InitializingBean, ServletContextAware
{
    private static final Logger logger;
    private Properties sysMessage;
    @Autowired
    private AdminUserDao adminUserDao;
    private Map<Integer, AdminUserBaseVO> adminUserMap;
    @Autowired
    private AdminUserActionDao adminUserActionDao;
    private Map<Integer, AdminUserAction> adminUserActionMap;
    @Autowired
    private AdminUserMenuDao adminUserMenuDao;
    private Map<Integer, AdminUserMenu> adminUserMenuMap;
    @Autowired
    private AdminUserRoleDao adminUserRoleDao;
    private Map<Integer, AdminUserRole> adminUserRoleMap;
    private static volatile boolean isRunningHighPrizeNotice;
    private static Object highPrizeNoticeLock;
    @Autowired
    private UserHighPrizeService highPrizeService;
    @Autowired
    private WebSocketMsgSender msgSender;
    
    static {
        logger = LoggerFactory.getLogger((Class)AdminDataFactoryImpl.class);
        AdminDataFactoryImpl.isRunningHighPrizeNotice = false;
        AdminDataFactoryImpl.highPrizeNoticeLock = new Object();
    }
    
    public AdminDataFactoryImpl() {
        this.adminUserMap = new LinkedHashMap<Integer, AdminUserBaseVO>();
        this.adminUserActionMap = new LinkedHashMap<Integer, AdminUserAction>();
        this.adminUserMenuMap = new LinkedHashMap<Integer, AdminUserMenu>();
        this.adminUserRoleMap = new LinkedHashMap<Integer, AdminUserRole>();
    }
    
    @Override
    public void init() {
        AdminDataFactoryImpl.logger.info("init AdminDataFactory....start");
        this.initSysMessage();
        this.initIpData();
        this.initAdminUserAction();
        this.initAdminUserMenu();
        this.initAdminUserRole();
        AdminDataFactoryImpl.logger.info("init AdminDataFactory....done");
    }
    
    public void setServletContext(final ServletContext context) {
        WSC.PROJECT_PATH = context.getRealPath("").replace("\\", "/");
        AdminDataFactoryImpl.logger.info("Project Path:" + WSC.PROJECT_PATH);
    }
    
    private void initIpData() {
        try {
            final File file = ResourceUtils.getFile("classpath:config/ip/17monipdb.dat");
            IpUtil.load(file.getPath());
            AdminDataFactoryImpl.logger.info("初始化ip数据库完成！");
        }
        catch (Exception e) {
            AdminDataFactoryImpl.logger.error("初始化ip数据库失败！");
        }
    }
    
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
    
    @Override
    public void initSysMessage() {
        try {
            final String fileClassPath = "classpath:config/message/language.cn.xml";
            final File file = ResourceUtils.getFile(fileClassPath);
            if (file == null) {
                throw new FileNotFoundException();
            }
            final Properties properties = new Properties();
            final InputStream inputStream = new FileInputStream(file);
            properties.loadFromXML(inputStream);
            inputStream.close();
            if (this.sysMessage != null) {
                this.sysMessage.clear();
            }
            this.sysMessage = properties;
            AdminDataFactoryImpl.logger.info("初始化语言文件完成。");
        }
        catch (Exception e) {
            AdminDataFactoryImpl.logger.error("加载语言文件失败！", (Throwable)e);
        }
    }
    
    @Override
    public String getSysMessage(final String key) {
        return this.sysMessage.getProperty(key);
    }
    
    @Override
    public void initAdminUser() {
        try {
            final List<AdminUser> list = this.adminUserDao.listAll();
            final Map<Integer, AdminUserBaseVO> tmpMap = new LinkedHashMap<Integer, AdminUserBaseVO>();
            for (final AdminUser adminUser : list) {
                tmpMap.put(adminUser.getId(), new AdminUserBaseVO(adminUser.getId(), adminUser.getUsername()));
            }
            if (this.adminUserMap != null) {
                this.adminUserMap.clear();
            }
            this.adminUserMap = tmpMap;
            AdminDataFactoryImpl.logger.info("初始化系统用户完成！");
        }
        catch (Exception e) {
            AdminDataFactoryImpl.logger.error("初始化系统用户失败！");
        }
    }
    
    @Override
    public AdminUserBaseVO getAdminUser(final int id) {
        if (this.adminUserMap.containsKey(id)) {
            return this.adminUserMap.get(id);
        }
        final AdminUser adminUser = this.adminUserDao.getById(id);
        if (adminUser != null) {
            this.adminUserMap.put(adminUser.getId(), new AdminUserBaseVO(adminUser.getId(), adminUser.getUsername()));
            return this.adminUserMap.get(id);
        }
        return null;
    }
    
    @Override
    public void initAdminUserAction() {
        try {
            final List<AdminUserAction> list = this.adminUserActionDao.listAll();
            final Map<Integer, AdminUserAction> tmpMap = new LinkedHashMap<Integer, AdminUserAction>();
            for (final AdminUserAction adminUserAction : list) {
                tmpMap.put(adminUserAction.getId(), adminUserAction);
            }
            if (this.adminUserActionMap != null) {
                this.adminUserActionMap.clear();
            }
            this.adminUserActionMap = tmpMap;
            AdminDataFactoryImpl.logger.info("初始化管理员行为分组完成！");
        }
        catch (Exception e) {
            AdminDataFactoryImpl.logger.error("初始化管理员行为分组失败！");
        }
    }
    
    @Override
    public List<AdminUserAction> listAdminUserAction() {
        final List<AdminUserAction> list = new ArrayList<AdminUserAction>();
        final Object[] keys = this.adminUserActionMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            list.add(this.adminUserActionMap.get(o));
        }
        return list;
    }
    
    @Override
    public AdminUserAction getAdminUserAction(final int id) {
        if (this.adminUserActionMap.containsKey(id)) {
            return this.adminUserActionMap.get(id);
        }
        return null;
    }
    
    @Override
    public AdminUserAction getAdminUserAction(final String actionKey) {
        if (StringUtil.isNotNull(actionKey)) {
            final Object[] keys = this.adminUserActionMap.keySet().toArray();
            Object[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final Object o = array[i];
                final AdminUserAction adminUserAction = this.adminUserActionMap.get(o);
                if (actionKey.equals(adminUserAction.getKey())) {
                    return adminUserAction;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<AdminUserAction> getAdminUserActionByRoleId(final int role) {
        final List<AdminUserAction> list = new ArrayList<AdminUserAction>();
        if (this.adminUserRoleMap.containsKey(role)) {
            final AdminUserRole adminUserRole = this.adminUserRoleMap.get(role);
            if (StringUtil.isNotNull(adminUserRole.getActions())) {
                final JSONArray menuJson = JSONArray.fromObject((Object)adminUserRole.getActions());
                for (final Object obj : menuJson) {
                    if (this.adminUserActionMap.containsKey((int)obj)) {
                        list.add(this.adminUserActionMap.get((int)obj));
                    }
                }
            }
        }
        return list;
    }
    
    @Override
    public void initAdminUserMenu() {
        try {
            final List<AdminUserMenu> list = this.adminUserMenuDao.listAll();
            final Map<Integer, AdminUserMenu> tmpMap = new LinkedHashMap<Integer, AdminUserMenu>();
            for (final AdminUserMenu adminUserMenu : list) {
                tmpMap.put(adminUserMenu.getId(), adminUserMenu);
            }
            if (this.adminUserMenuMap != null) {
                this.adminUserMenuMap.clear();
            }
            this.adminUserMenuMap = tmpMap;
            AdminDataFactoryImpl.logger.info("初始化管理员菜单完成！");
        }
        catch (Exception e) {
            AdminDataFactoryImpl.logger.error("初始化管理员菜单失败！");
        }
    }
    
    @Override
    public List<AdminUserMenu> listAdminUserMenu() {
        final List<AdminUserMenu> list = new ArrayList<AdminUserMenu>();
        final Object[] keys = this.adminUserMenuMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            list.add(this.adminUserMenuMap.get(o).clone());
        }
        return list;
    }
    
    @Override
    public AdminUserMenu getAdminUserMenuByLink(final String link) {
        final Object[] keys = this.adminUserMenuMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            final AdminUserMenu tmpMenu = this.adminUserMenuMap.get(o);
            if (link != null && link.equals(tmpMenu.getLink())) {
                return tmpMenu.clone();
            }
        }
        return null;
    }
    
    @Override
    public List<AdminUserMenu> getAdminUserMenuByRoleId(final int role) {
        final List<AdminUserMenu> list = new ArrayList<AdminUserMenu>();
        if (this.adminUserRoleMap.containsKey(role)) {
            final AdminUserRole adminUserRole = this.adminUserRoleMap.get(role);
            if (StringUtil.isNotNull(adminUserRole.getMenus())) {
                final List<Integer> menuIds = (List<Integer>)JSON.parseArray(adminUserRole.getMenus(), (Class)Integer.class);
                for (final Object menuId : menuIds) {
                    if (this.adminUserMenuMap.containsKey(menuId)) {
                        list.add(this.adminUserMenuMap.get(menuId).clone());
                    }
                }
            }
        }
        Collections.sort(list, new Comparator<AdminUserMenu>() {
            @Override
            public int compare(final AdminUserMenu o1, final AdminUserMenu o2) {
                if (o1.getSort() > o2.getSort()) {
                    return 1;
                }
                if (o1.getSort() < o2.getSort()) {
                    return -1;
                }
                return 0;
            }
        });
        return list;
    }
    
    @Override
    public Set<Integer> getAdminUserMenuIdsByAction(final int action) {
        final Set<Integer> set = new HashSet<Integer>();
        final Object[] keys = this.adminUserMenuMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            final AdminUserMenu tmpBean = this.adminUserMenuMap.get(o).clone();
            if (tmpBean.getBaseAction() != 0) {
                final JSONArray jsonArrayActions = JSONArray.fromObject((Object)tmpBean.getAllActions());
                if (jsonArrayActions.contains((Object)action)) {
                    set.add(tmpBean.getId());
                    int upid = tmpBean.getUpid();
                    do {
                        if (this.adminUserMenuMap.containsKey(upid)) {
                            final AdminUserMenu upMenu = this.adminUserMenuMap.get(upid).clone();
                            if (upMenu == null) {
                                continue;
                            }
                            set.add(upMenu.getId());
                            upid = upMenu.getUpid();
                        }
                    } while (upid != 0);
                }
            }
        }
        return set;
    }
    
    @Override
    public void initAdminUserRole() {
        try {
            final List<AdminUserRole> list = this.adminUserRoleDao.listAll();
            final Map<Integer, AdminUserRole> tmpMap = new LinkedHashMap<Integer, AdminUserRole>();
            for (final AdminUserRole adminUserRole : list) {
                tmpMap.put(adminUserRole.getId(), adminUserRole);
            }
            if (this.adminUserRoleMap != null) {
                this.adminUserRoleMap.clear();
            }
            this.adminUserRoleMap = tmpMap;
            AdminDataFactoryImpl.logger.info("初始化管理员角色完成！");
        }
        catch (Exception e) {
            AdminDataFactoryImpl.logger.error("初始化管理员角色失败！");
        }
    }
    
    @Override
    public AdminUserRole getAdminUserRole(final int id) {
        if (this.adminUserRoleMap.containsKey(id)) {
            return this.adminUserRoleMap.get(id).clone();
        }
        return null;
    }
    
    @Override
    public List<AdminUserRole> listAdminUserRole() {
        final List<AdminUserRole> adminUserRoleList = new ArrayList<AdminUserRole>();
        final Object[] keys = this.adminUserRoleMap.keySet().toArray();
        Object[] array;
        for (int length = (array = keys).length, i = 0; i < length; ++i) {
            final Object o = array[i];
            adminUserRoleList.add(this.adminUserRoleMap.get(o).clone());
        }
        return adminUserRoleList;
    }
    
    @Scheduled(cron = "0/3 * * * * *")
    public void highPrizeNoticesJob() {
        synchronized (AdminDataFactoryImpl.highPrizeNoticeLock) {
            if (AdminDataFactoryImpl.isRunningHighPrizeNotice) {
                // monitorexit(AdminDataFactoryImpl.highPrizeNoticeLock)
                return;
            }
            AdminDataFactoryImpl.isRunningHighPrizeNotice = true;
        }
        // monitorexit(AdminDataFactoryImpl.highPrizeNoticeLock)
        try {
            this.highPrizeNotices();
        }
        finally {
            AdminDataFactoryImpl.isRunningHighPrizeNotice = false;
        }
        AdminDataFactoryImpl.isRunningHighPrizeNotice = false;
    }
    
    private void highPrizeNotices() {
        final Map<String, String> allHighPrizeNotices = this.highPrizeService.getAllHighPrizeNotices();
        if (MapUtils.isEmpty((Map)allHighPrizeNotices)) {
            return;
        }
        final Set<String> keys = allHighPrizeNotices.keySet();
        for (final String key : keys) {
            this.msgSender.sendHighPrizeNotice(allHighPrizeNotices.get(key));
            this.highPrizeService.delHighPrizeNotice(key);
        }
    }
}
