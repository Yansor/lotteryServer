package admin.domains.content.biz.impl;

import org.springframework.scheduling.annotation.Scheduled;
import javautils.date.Moment;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import javautils.StringUtil;
import javautils.encrypt.PasswordUtil;
import javautils.date.DateUtil;
import java.util.Iterator;
import java.util.Set;
import admin.domains.content.entity.AdminUser;
import java.util.ArrayList;
import admin.domains.content.entity.AdminUserRole;
import java.util.HashSet;
import admin.domains.content.vo.AdminUserVO;
import java.util.List;
import com.warrenstrange.googleauth.KeyRepresentation;
import java.util.concurrent.TimeUnit;
import admin.domains.pool.AdminDataFactory;
import admin.domains.content.biz.AdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import admin.domains.content.dao.AdminUserDao;
import java.util.concurrent.ConcurrentHashMap;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder;
import org.springframework.stereotype.Service;
import admin.domains.content.biz.AdminUserService;

@Service
public class AdminUserServiceImpl implements AdminUserService
{
    private static final GoogleAuthenticatorConfigBuilder gacb;
    private static final GoogleAuthenticator googleAuthenticator;
    private static final ConcurrentHashMap<String, String> GOOGLE_CODES;
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminUserRoleService adminUserRoleService;
    @Autowired
    private AdminDataFactory adminDataFactory;
    
    static {
        gacb = new GoogleAuthenticatorConfigBuilder().setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30L)).setWindowSize(5).setCodeDigits(6).setKeyRepresentation(KeyRepresentation.BASE32);
        googleAuthenticator = new GoogleAuthenticator(AdminUserServiceImpl.gacb.build());
        GOOGLE_CODES = new ConcurrentHashMap<String, String>();
    }
    
    @Override
    public List<AdminUserVO> listAll(final int roleId) {
        final List<AdminUserRole> rlist = this.adminUserRoleService.listAll(roleId);
        final Set<Integer> rSet = new HashSet<Integer>();
        for (final AdminUserRole tmpRole : rlist) {
            if (tmpRole.getUpid() != 0) {
                rSet.add(tmpRole.getId());
            }
        }
        final List<AdminUser> ulist = this.adminUserDao.listAll();
        final List<AdminUserVO> list = new ArrayList<AdminUserVO>();
        for (final AdminUser tmpBean : ulist) {
            if (tmpBean.getId() == 0) {
                continue;
            }
            if (!rSet.contains(tmpBean.getRoleId())) {
                continue;
            }
            list.add(new AdminUserVO(tmpBean, this.adminDataFactory));
        }
        return list;
    }
    
    @Override
    public boolean add(final String username, String password, final int roleId, final Integer setWithdrawPwd, final String withdrawPwd) {
        final String registTime = DateUtil.getCurrentTime();
        password = PasswordUtil.generatePasswordByMD5(password);
        String dbWithdrawPwd;
        if (setWithdrawPwd != null && setWithdrawPwd == 1) {
            dbWithdrawPwd = PasswordUtil.generatePasswordByMD5(withdrawPwd);
        }
        else {
            dbWithdrawPwd = "notset";
        }
        final String loginTime = null;
        final int status = 0;
        final String ips = "";
        final int pwd_error = 0;
        final AdminUser entity = new AdminUser(username, password, dbWithdrawPwd, roleId, registTime, loginTime, status, ips, pwd_error);
        final boolean result = this.adminUserDao.add(entity);
        if (result) {
            this.adminDataFactory.initAdminUser();
        }
        return result;
    }
    
    @Override
    public boolean edit(final String username, String password, final int roleId, final Integer setWithdrawPwd, final String withdrawPwd) {
        password = PasswordUtil.generatePasswordByMD5(password);
        final AdminUser entity = this.adminUserDao.getByUsername(username);
        if (entity != null) {
            if (StringUtil.isNotNull(password)) {
                entity.setPassword(password);
            }
            if (setWithdrawPwd != null && setWithdrawPwd == 1) {
                final String dbWithdrawPwd = PasswordUtil.generatePasswordByMD5(withdrawPwd);
                entity.setWithdrawPwd(dbWithdrawPwd);
            }
            entity.setRoleId(roleId);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean closeWithdrawPwd(final String username) {
        final AdminUser entity = this.adminUserDao.getByUsername(username);
        if (entity == null) {
            return false;
        }
        entity.setWithdrawPwd("notset");
        return this.adminUserDao.update(entity);
    }
    
    @Override
    public boolean openWithdrawPwd(final String username, final String withdrawPwd) {
        final AdminUser entity = this.adminUserDao.getByUsername(username);
        if (entity == null) {
            return false;
        }
        final String dbWithdrawPwd = PasswordUtil.generatePasswordByMD5(withdrawPwd);
        entity.setWithdrawPwd(dbWithdrawPwd);
        return this.adminUserDao.update(entity);
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final AdminUser entity = this.adminUserDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean updatePwdError(final int id, final int count) {
        final AdminUser entity = this.adminUserDao.getById(id);
        if (entity != null) {
            entity.setPwd_error(count);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean updateIps(final int id, final String ip) {
        final AdminUser entity = this.adminUserDao.getById(id);
        if (entity != null) {
            String ips = entity.getIps();
            ips = String.valueOf(ips) + "," + ip;
            entity.setIps(ips);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean updateLoginTime(final int id, final String loginTime) {
        final AdminUser entity = this.adminUserDao.getById(id);
        if (entity != null) {
            entity.setLoginTime(loginTime);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean modUserLoginPwd(final int id, String password) {
        password = PasswordUtil.generatePasswordByMD5(password);
        final AdminUser entity = this.adminUserDao.getById(id);
        if (entity != null) {
            entity.setPassword(password);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public boolean modUserWithdrawPwd(final int id, String withdrawPwd) {
        withdrawPwd = PasswordUtil.generatePasswordByMD5(withdrawPwd);
        final AdminUser entity = this.adminUserDao.getById(id);
        if (entity != null) {
            entity.setWithdrawPwd(withdrawPwd);
            return this.adminUserDao.update(entity);
        }
        return false;
    }
    
    @Override
    public GoogleAuthenticatorKey createCredentialsForUser(final String username) {
        final GoogleAuthenticatorKey key = AdminUserServiceImpl.googleAuthenticator.createCredentials();
        final AdminUser uEntity = this.adminUserDao.getByUsername(username);
        uEntity.setSecretKey(key.getKey());
        this.adminUserDao.update(uEntity);
        return key;
    }
    
    @Override
    public boolean authoriseUser(final String username, final int verificationCode) {
        final String cacheKey = String.valueOf(username) + "_" + verificationCode;
        final String usedTime = AdminUserServiceImpl.GOOGLE_CODES.get(cacheKey);
        if (usedTime != null) {
            final Moment now = new Moment();
            final Moment usedMoment = new Moment().fromTime(usedTime);
            final int usedMinutes = now.difference(usedMoment, "minute");
            if (usedMinutes <= 60) {
                return false;
            }
        }
        final String secretKey = this.adminUserDao.getByUsername(username).getSecretKey();
        final boolean isCodeValid = AdminUserServiceImpl.googleAuthenticator.authorize(secretKey, verificationCode);
        if (isCodeValid) {
            AdminUserServiceImpl.GOOGLE_CODES.put(cacheKey, new Moment().toSimpleTime());
        }
        return isCodeValid;
    }
    
    @Scheduled(cron = "0 30 5 * * *")
    public void clear() {
        AdminUserServiceImpl.GOOGLE_CODES.clear();
    }
}
