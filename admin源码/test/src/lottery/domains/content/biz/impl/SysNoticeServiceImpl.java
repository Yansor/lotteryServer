package lottery.domains.content.biz.impl;

import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.entity.SysNotice;
import javautils.date.Moment;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import javautils.jdbc.PageList;
import lottery.domains.content.dao.DbServerSyncDao;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.dao.SysNoticeDao;
import org.springframework.stereotype.Service;
import lottery.domains.content.biz.SysNoticeService;

@Service
public class SysNoticeServiceImpl implements SysNoticeService
{
    @Autowired
    private SysNoticeDao sysNoticeDao;
    @Autowired
    private DbServerSyncDao dbServerSyncDao;
    
    @Override
    public PageList search(final Integer status, final int start, final int limit) {
        final List<Criterion> criterions = new ArrayList<Criterion>();
        final List<Order> orders = new ArrayList<Order>();
        if (status != null) {
            criterions.add((Criterion)Restrictions.eq("status", (Object)status));
        }
        orders.add(Order.desc("sort"));
        orders.add(Order.desc("time"));
        return this.sysNoticeDao.find(criterions, orders, start, limit);
    }
    
    @Override
    public boolean add(final String title, final String content, final String simpleContent, final int sort, final int status, final String date) {
        final String time = new Moment().toSimpleTime();
        final SysNotice entity = new SysNotice(title, content, simpleContent, sort, status, date, time);
        final boolean added = this.sysNoticeDao.add(entity);
        if (added) {
            this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
        }
        return added;
    }
    
    @Override
    public boolean edit(final int id, final String title, final String content, final String simpleContent, final int sort, final int status, final String date) {
        final SysNotice entity = this.sysNoticeDao.getById(id);
        if (entity != null) {
            entity.setTitle(title);
            entity.setContent(content);
            entity.setSimpleContent(simpleContent);
            entity.setSort(sort);
            entity.setStatus(status);
            entity.setDate(date);
            final boolean updated = this.sysNoticeDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean updateStatus(final int id, final int status) {
        final SysNotice entity = this.sysNoticeDao.getById(id);
        if (entity != null) {
            entity.setStatus(status);
            final boolean updated = this.sysNoticeDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean updateSort(final int id, final int sort) {
        final SysNotice entity = this.sysNoticeDao.getById(id);
        if (entity != null) {
            entity.setSort(sort);
            final boolean updated = this.sysNoticeDao.update(entity);
            if (updated) {
                this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
            }
            return updated;
        }
        return false;
    }
    
    @Override
    public boolean delete(final int id) {
        final boolean deleted = this.sysNoticeDao.delete(id);
        if (deleted) {
            this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
        }
        return deleted;
    }
}
