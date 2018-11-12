package javautils.jdbc.hibernate;

import java.sql.SQLException;
import javautils.jdbc.util.BatchSQLUtil;
import java.sql.Connection;
import org.hibernate.jdbc.Work;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import java.util.ArrayList;
import java.util.Map.Entry;
import javautils.jdbc.PageList;
import org.hibernate.SQLQuery;
import java.util.Iterator;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HibernateSuperDao<T>
{
    private static final Logger logger;
    @Autowired
    private SessionFactory sessionFactory;
    
    static {
        logger = LoggerFactory.getLogger((Class)HibernateSuperDao.class);
    }
    
    public Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }
    
    @Transactional
    public boolean save(final Object entity) {
        boolean flag = false;
        try {
            this.getCurrentSession().save(entity);
            flag = true;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("保存实体类异常", (Throwable)e);
        }
        return flag;
    }
    
    @Transactional
    public boolean update(final Object entity) {
        boolean flag = false;
        try {
            this.getCurrentSession().update(entity);
            flag = true;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("更新实体类异常", (Throwable)e);
        }
        return flag;
    }
    
    @Transactional
    public boolean update(final String hql, final Object[] values) {
        boolean flag = false;
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(String.valueOf(i), values[i]);
            }
            final int result = query.executeUpdate();
            flag = (result > 0);
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("更新实体类异常", (Throwable)e);
        }
        return flag;
    }
    
    public boolean delete(final Object entity) {
        boolean flag = false;
        try {
            this.getCurrentSession().delete(entity);
            flag = true;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("删除实体类异常", (Throwable)e);
        }
        return flag;
    }
    
    @Transactional
    public boolean delete(final String hql, final Object[] values) {
        boolean flag = false;
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(String.valueOf(i), values[i]);
            }
            final int result = query.executeUpdate();
            flag = (result > 0);
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("删除实体类异常", (Throwable)e);
        }
        return flag;
    }
    
    @Transactional(readOnly = true)
    public List<T> list(final String hql) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            return (List<T>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<T> list(final String hql, final int start, final int limit) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            if (start >= 0 && limit > 0) {
                query.setFirstResult(start);
                query.setMaxResults(limit);
            }
            return (List<T>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Object unique(final String hql) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            return query.uniqueResult();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Object unique(final String hql, final Object[] values) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(String.valueOf(i), values[i]);
            }
            return query.uniqueResult();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Object uniqueWithParams(final String hql, final Map<String, Object> params) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (final String key : params.keySet()) {
                final Object value = params.get(key);
                if (value instanceof Collection) {
                    query.setParameterList(key, (Collection)value);
                }
                else if (value instanceof Object[]) {
                    query.setParameterList(key, (Object[])value);
                }
                else {
                    query.setParameter(key, value);
                }
            }
            return query.uniqueResult();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Object uniqueSqlWithParams(final String sql, final Map<String, Object> params) {
        try {
            final Query query = (Query)this.getCurrentSession().createSQLQuery(sql);
            for (final String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
            return query.uniqueResult();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> listBySql(final String sql, final Map<String, Object> params) {
        try {
            final Query query = (Query)this.getCurrentSession().createSQLQuery(sql);
            for (final String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
            return (List<?>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> listByInSql(final String sql, final String name, final String[] params) {
        try {
            final Query query = (Query)this.getCurrentSession().createSQLQuery(sql);
            query.setParameterList(name, (Object[])params);
            return (List<?>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> listBySql(final String sql, final Map<String, Object> params, final int start, final int limit) {
        try {
            final Query query = (Query)this.getCurrentSession().createSQLQuery(sql);
            for (final String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
            if (start >= 0 && limit > 0) {
                query.setFirstResult(start);
                query.setMaxResults(limit);
            }
            return (List<?>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<T> list(final String hql, final Object[] values) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(String.valueOf(i), values[i]);
            }
            return (List<T>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> listObject(final String hql, final Object[] values) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(String.valueOf(i), values[i]);
            }
            return (List<?>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<T> list(final String hql, final Object[] values, final int start, final int limit) {
        try {
            final Query query = this.getCurrentSession().createQuery(hql);
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(String.valueOf(i), values[i]);
            }
            if (start >= 0 && limit > 0) {
                query.setFirstResult(start);
                query.setMaxResults(limit);
            }
            return (List<T>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> findWithSql(final String sql) {
        try {
            final SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
            return (List<?>)query.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public Object uniqueWithSqlCount(final String sql) {
        try {
            final SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
            return query.uniqueResult();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public PageList findPageList(final String querySql, final String countSql, final int start, final int limit) {
        try {
            final PageList pList = new PageList();
            final SQLQuery countQuery = this.getCurrentSession().createSQLQuery(countSql);
            final Number result = (Number)countQuery.uniqueResult();
            final int count = result.intValue();
            pList.setCount(count);
            final SQLQuery listQuery = this.getCurrentSession().createSQLQuery(querySql);
            if (start >= 0 && limit > 0) {
                listQuery.setFirstResult(start);
                listQuery.setMaxResults(limit);
            }
            final List<T> list = (List<T>)listQuery.list();
            pList.setList(list);
            return pList;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public PageList findPageEntityList(final String sql, final Class<T> entityType, final Map<String, Object> params, final int start, final int limit) {
        try {
            final PageList pList = new PageList();
            final String countSql = "select count(*) from (" + sql + ") _t1";
            final SQLQuery countQuery = this.getCurrentSession().createSQLQuery(countSql);
            if (params != null && !params.isEmpty()) {
                for (final Entry<String, Object> entry : params.entrySet()) {
                    countQuery.setParameter((String)entry.getKey(), entry.getValue());
                }
            }
            final Number result = (Number)countQuery.uniqueResult();
            final int count = result.intValue();
            pList.setCount(count);
            if (count > 0) {
                final SQLQuery listQuery = this.getCurrentSession().createSQLQuery(sql).addEntity((Class)entityType);
                if (params != null && !params.isEmpty()) {
                    for (final Entry<String, Object> entry2 : params.entrySet()) {
                        listQuery.setParameter((String)entry2.getKey(), entry2.getValue());
                    }
                }
                if (start >= 0 && limit > 0) {
                    listQuery.setFirstResult(start);
                    listQuery.setMaxResults(limit);
                }
                final List<T> list = (List<T>)listQuery.list();
                pList.setList(list);
            }
            else {
                pList.setList(new ArrayList<Object>());
            }
            return pList;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public PageList findPageList(final String sql, final Map<String, Object> params, final int start, final int limit) {
        try {
            final PageList pList = new PageList();
            final String countSql = "select count(*) from (" + sql + ") _t1";
            final SQLQuery countQuery = this.getCurrentSession().createSQLQuery(countSql);
            if (params != null && !params.isEmpty()) {
                for (final Entry<String, Object> entry : params.entrySet()) {
                    countQuery.setParameter((String)entry.getKey(), entry.getValue());
                }
            }
            final Number result = (Number)countQuery.uniqueResult();
            final int count = result.intValue();
            pList.setCount(count);
            if (count > 0) {
                final SQLQuery listQuery = this.getCurrentSession().createSQLQuery(sql);
                if (params != null && !params.isEmpty()) {
                    for (final Entry<String, Object> entry2 : params.entrySet()) {
                        listQuery.setParameter((String)entry2.getKey(), entry2.getValue());
                    }
                }
                if (start >= 0 && limit > 0) {
                    listQuery.setFirstResult(start);
                    listQuery.setMaxResults(limit);
                }
                final List<T> list = (List<T>)listQuery.list();
                pList.setList(list);
            }
            else {
                pList.setList(new ArrayList<Object>());
            }
            return pList;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public PageList findPageList(final Class<T> clazz, final String propertyName, final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        try {
            final PageList pList = new PageList();
            final Criteria criteria = this.getCurrentSession().createCriteria((Class)clazz);
            if (CollectionUtils.isNotEmpty((Collection)criterions)) {
                for (final Criterion criterion : criterions) {
                    criteria.add(criterion);
                }
            }
            if (CollectionUtils.isNotEmpty((Collection)orders)) {
                for (final Order order : orders) {
                    criteria.addOrder(order);
                }
            }
            criteria.setProjection((Projection)Projections.count(propertyName));
            final Number result = (Number)criteria.uniqueResult();
            final int count = result.intValue();
            pList.setCount(count);
            criteria.setProjection((Projection)null);
            if (start >= 0 && limit > 0) {
                criteria.setFirstResult(start);
                criteria.setMaxResults(limit);
            }
            final List<T> list = (List<T>)criteria.list();
            pList.setList(list);
            return pList;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public PageList findPageList(final Class<T> clazz, final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        try {
            final PageList pList = new PageList();
            final Criteria criteria = this.getCurrentSession().createCriteria((Class)clazz);
            if (CollectionUtils.isNotEmpty((Collection)criterions)) {
                for (final Criterion criterion : criterions) {
                    criteria.add(criterion);
                }
            }
            if (CollectionUtils.isNotEmpty((Collection)orders)) {
                for (final Order order : orders) {
                    criteria.addOrder(order);
                }
            }
            criteria.setProjection(Projections.rowCount());
            final Number result = (Number)criteria.uniqueResult();
            final int count = result.intValue();
            pList.setCount(count);
            criteria.setProjection((Projection)null);
            if (start >= 0 && limit > 0) {
                criteria.setFirstResult(start);
                criteria.setMaxResults(limit);
            }
            final List<T> list = (List<T>)criteria.list();
            pList.setList(list);
            return pList;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<T> findByCriteria(final Class<T> clazz, final List<Criterion> criterions, final List<Order> orders) {
        try {
            final Criteria criteria = this.getCurrentSession().createCriteria((Class)clazz);
            for (final Criterion criterion : criterions) {
                criteria.add(criterion);
            }
            if (CollectionUtils.isNotEmpty((Collection)orders)) {
                for (final Order order : orders) {
                    criteria.addOrder(order);
                }
            }
            return (List<T>)criteria.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<T> findByCriteria(final Class<T> clazz, final List<Criterion> criterions, final List<Order> orders, final int start, final int limit) {
        try {
            final Criteria criteria = this.getCurrentSession().createCriteria((Class)clazz);
            for (final Criterion criterion : criterions) {
                criteria.add(criterion);
            }
            if (CollectionUtils.isNotEmpty((Collection)orders)) {
                for (final Order order : orders) {
                    criteria.addOrder(order);
                }
            }
            if (start >= 0 && limit > 0) {
                criteria.setFirstResult(start);
                criteria.setMaxResults(limit);
            }
            return (List<T>)criteria.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> findByCriteria(final Class<T> clazz, final List<Criterion> criterions, final List<Order> orders, final Projection projection) {
        try {
            final Criteria criteria = this.getCurrentSession().createCriteria((Class)clazz);
            for (final Criterion criterion : criterions) {
                criteria.add(criterion);
            }
            if (CollectionUtils.isNotEmpty((Collection)orders)) {
                for (final Order order : orders) {
                    criteria.addOrder(order);
                }
            }
            criteria.setProjection(projection);
            return (List<?>)criteria.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    public List<?> findByCriteria(final Class<T> clazz, final List<Criterion> criterions, final List<Order> orders, final Projection projection, final int start, final int limit) {
        try {
            final Criteria criteria = this.getCurrentSession().createCriteria((Class)clazz);
            for (final Criterion criterion : criterions) {
                criteria.add(criterion);
            }
            if (CollectionUtils.isNotEmpty((Collection)orders)) {
                for (final Order order : orders) {
                    criteria.addOrder(order);
                }
            }
            criteria.setProjection(projection);
            if (start >= 0 && limit > 0) {
                criteria.setFirstResult(start);
                criteria.setMaxResults(limit);
            }
            return (List<?>)criteria.list();
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("查询出错", (Throwable)e);
            return null;
        }
    }
    
    @Transactional
    public boolean doWork(final String sql, final List<Object[]> params) {
        boolean flag = false;
        try {
            this.getCurrentSession().doWork((Work)new Work() {
                public void execute(final Connection connection) throws SQLException {
                    final BatchSQLUtil sqlUtil = new BatchSQLUtil(connection, sql);
                    for (final Object[] param : params) {
                        sqlUtil.addCount(param);
                    }
                    sqlUtil.commit();
                }
            });
            flag = true;
        }
        catch (Exception e) {
            HibernateSuperDao.logger.error("doWork出错", (Throwable)e);
        }
        return flag;
    }
}
