package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.SysNoticeService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.SysNoticeDao;
import lottery.domains.content.entity.SysNotice;
import lottery.domains.content.global.DbServerSyncEnum;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysNoticeServiceImpl
  implements SysNoticeService
{
  @Autowired
  private SysNoticeDao sysNoticeDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public PageList search(Integer status, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    if (status != null) {
      criterions.add(Restrictions.eq("status", Integer.valueOf(status.intValue())));
    }
    orders.add(Order.desc("sort"));
    orders.add(Order.desc("time"));
    return this.sysNoticeDao.find(criterions, orders, start, limit);
  }
  
  public boolean add(String title, String content, String simpleContent, int sort, int status, String date)
  {
    String time = new Moment().toSimpleTime();
    SysNotice entity = new SysNotice(title, content, simpleContent, Integer.valueOf(sort), Integer.valueOf(status), date, time);
    boolean added = this.sysNoticeDao.add(entity);
    if (added) {
      this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
    }
    return added;
  }
  
  public boolean edit(int id, String title, String content, String simpleContent, int sort, int status, String date)
  {
    SysNotice entity = this.sysNoticeDao.getById(id);
    if (entity != null)
    {
      entity.setTitle(title);
      entity.setContent(content);
      entity.setSimpleContent(simpleContent);
      entity.setSort(Integer.valueOf(sort));
      entity.setStatus(Integer.valueOf(status));
      entity.setDate(date);
      boolean updated = this.sysNoticeDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
      }
      return updated;
    }
    return false;
  }
  
  public boolean updateStatus(int id, int status)
  {
    SysNotice entity = this.sysNoticeDao.getById(id);
    if (entity != null)
    {
      entity.setStatus(Integer.valueOf(status));
      boolean updated = this.sysNoticeDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
      }
      return updated;
    }
    return false;
  }
  
  public boolean updateSort(int id, int sort)
  {
    SysNotice entity = this.sysNoticeDao.getById(id);
    if (entity != null)
    {
      entity.setSort(Integer.valueOf(sort));
      boolean updated = this.sysNoticeDao.update(entity);
      if (updated) {
        this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
      }
      return updated;
    }
    return false;
  }
  
  public boolean delete(int id)
  {
    boolean deleted = this.sysNoticeDao.delete(id);
    if (deleted) {
      this.dbServerSyncDao.update(DbServerSyncEnum.SYS_NOTICE);
    }
    return deleted;
  }
}
