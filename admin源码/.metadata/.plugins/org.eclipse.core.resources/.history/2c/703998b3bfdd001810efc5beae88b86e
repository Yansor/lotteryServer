package admin.domains.content.biz.utils;

import java.util.Map;
import admin.domains.content.entity.AdminUserAction;
import net.sf.json.JSONArray;
import javautils.StringUtil;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.entity.AdminUserRole;
import java.util.Iterator;
import java.util.LinkedList;
import admin.domains.content.entity.AdminUserMenu;
import java.util.List;

public class TreeUtil
{
    public static List<AdminUserMenu> listMenuRoot(final List<AdminUserMenu> list) {
        final List<AdminUserMenu> allEls = new LinkedList<AdminUserMenu>();
        for (final AdminUserMenu bean : list) {
            allEls.add(bean.clone());
        }
        final List<AdminUserMenu> treeEls = new LinkedList<AdminUserMenu>();
        for (final AdminUserMenu bean2 : allEls) {
            if (bean2.getUpid() == 0) {
                treeEls.add(bean2);
                listMenuChild(allEls, bean2);
            }
        }
        return treeEls;
    }
    
    public static void listMenuChild(final List<AdminUserMenu> allEls, final AdminUserMenu sbean) {
        for (final AdminUserMenu bean : allEls) {
            if (bean.getUpid() == sbean.getId()) {
                sbean.getItems().add(bean);
                listMenuChild(allEls, bean);
            }
        }
    }
    
    public static List<AdminUserRole> listRoleRoot(final List<AdminUserRole> list) {
        final List<AdminUserRole> allEls = new LinkedList<AdminUserRole>();
        for (final AdminUserRole bean : list) {
            allEls.add(bean.clone());
        }
        final List<AdminUserRole> treeEls = new LinkedList<AdminUserRole>();
        for (final AdminUserRole bean2 : allEls) {
            if (bean2.getUpid() == 0) {
                treeEls.add(bean2);
                listRoleChild(allEls, bean2);
            }
        }
        return treeEls;
    }
    
    public static void listRoleChild(final List<AdminUserRole> allEls, final AdminUserRole sbean) {
        for (final AdminUserRole bean : allEls) {
            if (bean.getUpid() == sbean.getId()) {
                sbean.getItems().add(bean);
                listRoleChild(allEls, bean);
            }
        }
    }
    
    public static List<JSMenuVO> listJSMenuRoot(final List<AdminUserMenu> list) {
        final List<JSMenuVO> mList = new LinkedList<JSMenuVO>();
        listJSMenuChild(list, mList);
        return mList;
    }
    
    public static void listJSMenuChild(final List<AdminUserMenu> list, final List<JSMenuVO> mList) {
        for (final AdminUserMenu adminUserMenu : list) {
            final JSMenuVO jsMenu = new JSMenuVO();
            jsMenu.setName(adminUserMenu.getName());
            jsMenu.setIcon(adminUserMenu.getIcon());
            jsMenu.setLink(adminUserMenu.getLink());
            if (adminUserMenu.getItems().size() > 0) {
                listJSMenuChild(adminUserMenu.getItems(), jsMenu.getItems());
            }
            mList.add(jsMenu);
        }
    }
    
    public static List<JSTreeVO> listJSTreeRoot(final List<AdminUserMenu> list, final AdminDataFactory df) {
        final List<JSTreeVO> treeList = new LinkedList<JSTreeVO>();
        listJSTreeChild(list, treeList, df);
        return treeList;
    }
    
    public static void listJSTreeChild(final List<AdminUserMenu> list, final List<JSTreeVO> treeList, final AdminDataFactory df) {
        for (final AdminUserMenu adminUserMenu : list) {
            final JSTreeVO jsTree = new JSTreeVO();
            jsTree.setId("menu_" + adminUserMenu.getId());
            jsTree.setText(adminUserMenu.getName());
            jsTree.setIcon(adminUserMenu.getIcon());
            if (adminUserMenu.getItems().size() > 0) {
                listJSTreeChild(adminUserMenu.getItems(), jsTree.getChildren(), df);
            }
            listMenuActions(adminUserMenu.getAllActions(), jsTree, df);
            treeList.add(jsTree);
        }
    }
    
    public static void listMenuActions(final String allActions, final JSTreeVO jsTree, final AdminDataFactory df) {
        if (StringUtil.isNotNull(allActions)) {
            final JSONArray json = JSONArray.fromObject((Object)allActions);
            for (final Object obj : json) {
                final AdminUserAction adminUserAction = df.getAdminUserAction((int)obj);
                final JSTreeVO acTree = new JSTreeVO();
                acTree.setId("action_" + adminUserAction.getId());
                acTree.setText(adminUserAction.getName());
                acTree.setIcon("fa fa-slack font-green-haze");
                jsTree.getChildren().add(acTree);
            }
        }
    }
    
    public static List<JSTreeVO> listJSTreeRoot2(final List<AdminUserMenu> list, final Map<Integer, AdminUserAction> aMap) {
        final List<JSTreeVO> treeList = new LinkedList<JSTreeVO>();
        listJSTreeChild2(list, treeList, aMap);
        return treeList;
    }
    
    public static void listJSTreeChild2(final List<AdminUserMenu> list, final List<JSTreeVO> treeList, final Map<Integer, AdminUserAction> aMap) {
        for (final AdminUserMenu adminUserMenu : list) {
            final JSTreeVO jsTree = new JSTreeVO();
            jsTree.setId("menu_" + adminUserMenu.getId());
            jsTree.setText(adminUserMenu.getName());
            jsTree.setIcon(adminUserMenu.getIcon());
            if (adminUserMenu.getItems().size() > 0) {
                listJSTreeChild2(adminUserMenu.getItems(), jsTree.getChildren(), aMap);
            }
            listMenuActions2(adminUserMenu.getAllActions(), jsTree, aMap);
            treeList.add(jsTree);
        }
    }
    
    public static void listMenuActions2(final String allActions, final JSTreeVO jsTree, final Map<Integer, AdminUserAction> aMap) {
        if (StringUtil.isNotNull(allActions)) {
            final JSONArray json = JSONArray.fromObject((Object)allActions);
            for (final Object obj : json) {
                if (aMap.containsKey((int)obj)) {
                    final AdminUserAction adminUserAction = aMap.get((int)obj);
                    final JSTreeVO acTree = new JSTreeVO();
                    acTree.setId("action_" + adminUserAction.getId());
                    acTree.setText(adminUserAction.getName());
                    acTree.setIcon("fa fa-slack font-green-haze");
                    jsTree.getChildren().add(acTree);
                }
            }
        }
    }
}
