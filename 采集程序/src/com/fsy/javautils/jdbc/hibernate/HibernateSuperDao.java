/*     */ package com.fsy.javautils.jdbc.hibernate;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import com.fsy.javautils.jdbc.PageList;
/*     */ import com.fsy.javautils.jdbc.util.BatchSQLUtil;
/*     */ import org.hibernate.Criteria;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.SQLQuery;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.criterion.Criterion;
/*     */ import org.hibernate.criterion.Order;
/*     */ import org.hibernate.criterion.Projection;
/*     */ import org.hibernate.criterion.Projections;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @org.springframework.stereotype.Component
/*     */ public class HibernateSuperDao<T>
/*     */ {
/*  25 */   private static final Logger logger = LoggerFactory.getLogger(HibernateSuperDao.class);
/*     */   @Autowired
/*     */   private SessionFactory sessionFactory;
/*     */   
/*     */   public Session getCurrentSession()
/*     */   {
/*  31 */     return this.sessionFactory.getCurrentSession();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional
/*     */   public boolean save(Object entity)
/*     */   {
/*  41 */     boolean flag = false;
/*     */     try {
/*  43 */       getCurrentSession().save(entity);
/*  44 */       flag = true;
/*     */     } catch (Exception e) {
/*  46 */       logger.error("保存实体类异常", e);
/*     */     }
/*  48 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional
/*     */   public boolean update(Object entity)
/*     */   {
/*  58 */     boolean flag = false;
/*     */     try {
/*  60 */       getCurrentSession().update(entity);
/*  61 */       flag = true;
/*     */     } catch (Exception e) {
/*  63 */       logger.error("更新实体类异常", e);
/*     */     }
/*  65 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional
/*     */   public boolean update(String hql, Object[] values)
/*     */   {
/*  76 */     boolean flag = false;
/*     */     try {
/*  78 */       Query query = getCurrentSession().createQuery(hql);
/*  79 */       for (int i = 0; i < values.length; i++) {
/*  80 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/*  82 */       int result = query.executeUpdate();
/*  83 */       flag = result > 0;
/*     */     } catch (Exception e) {
/*  85 */       logger.error("更新实体类异常", e);
/*     */     }
/*  87 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean delete(Object entity)
/*     */   {
/*  96 */     boolean flag = false;
/*     */     try {
/*  98 */       getCurrentSession().delete(entity);
/*  99 */       flag = true;
/*     */     } catch (Exception e) {
/* 101 */       logger.error("删除实体类异常", e);
/*     */     }
/* 103 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional
/*     */   public boolean delete(String hql, Object[] values)
/*     */   {
/* 114 */     boolean flag = false;
/*     */     try {
/* 116 */       Query query = getCurrentSession().createQuery(hql);
/* 117 */       for (int i = 0; i < values.length; i++) {
/* 118 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 120 */       int result = query.executeUpdate();
/* 121 */       flag = result > 0;
/*     */     } catch (Exception e) {
/* 123 */       logger.error("删除实体类异常", e);
/*     */     }
/* 125 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<T> list(String hql)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       Query query = getCurrentSession().createQuery(hql);
/* 137 */       return query.list();
/*     */     } catch (Exception e) {
/* 139 */       logger.error("查询出错", e);
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<T> list(String hql, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 154 */       Query query = getCurrentSession().createQuery(hql);
/* 155 */       if ((start >= 0) && (limit > 0)) {
/* 156 */         query.setFirstResult(start);
/* 157 */         query.setMaxResults(limit);
/*     */       }
/* 159 */       return query.list();
/*     */     } catch (Exception e) {
/* 161 */       logger.error("查询出错", e);
/*     */     }
/* 163 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Object unique(String hql)
/*     */   {
/*     */     try
/*     */     {
/* 174 */       Query query = getCurrentSession().createQuery(hql);
/* 175 */       return query.uniqueResult();
/*     */     } catch (Exception e) {
/* 177 */       logger.error("查询出错", e);
/*     */     }
/* 179 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Object unique(String hql, Object[] values)
/*     */   {
/*     */     try
/*     */     {
/* 191 */       Query query = getCurrentSession().createQuery(hql);
/* 192 */       for (int i = 0; i < values.length; i++) {
/* 193 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 195 */       return query.uniqueResult();
/*     */     } catch (Exception e) {
/* 197 */       logger.error("查询出错", e);
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<T> list(String hql, Object[] values)
/*     */   {
/*     */     try
/*     */     {
/* 211 */       Query query = getCurrentSession().createQuery(hql);
/* 212 */       for (int i = 0; i < values.length; i++) {
/* 213 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 215 */       return query.list();
/*     */     } catch (Exception e) {
/* 217 */       logger.error("查询出错", e);
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<?> listObject(String hql, Object[] values)
/*     */   {
/*     */     try
/*     */     {
/* 231 */       Query query = getCurrentSession().createQuery(hql);
/* 232 */       for (int i = 0; i < values.length; i++) {
/* 233 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 235 */       return query.list();
/*     */     } catch (Exception e) {
/* 237 */       logger.error("查询出错", e);
/*     */     }
/* 239 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<T> list(String hql, Object[] values, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 253 */       Query query = getCurrentSession().createQuery(hql);
/* 254 */       for (int i = 0; i < values.length; i++) {
/* 255 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 257 */       if ((start >= 0) && (limit > 0)) {
/* 258 */         query.setFirstResult(start);
/* 259 */         query.setMaxResults(limit);
/*     */       }
/* 261 */       return query.list();
/*     */     } catch (Exception e) {
/* 263 */       logger.error("查询出错", e);
/*     */     }
/* 265 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<?> findWithSql(String sql)
/*     */   {
/*     */     try
/*     */     {
/* 276 */       SQLQuery query = getCurrentSession().createSQLQuery(sql);
/* 277 */       return query.list();
/*     */     } catch (Exception e) {
/* 279 */       logger.error("查询出错", e);
/*     */     }
/* 281 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public Object uniqueWithSqlCount(String sql)
/*     */   {
/*     */     try
/*     */     {
/* 292 */       SQLQuery query = getCurrentSession().createSQLQuery(sql);
/* 293 */       return query.uniqueResult();
/*     */     } catch (Exception e) {
/* 295 */       logger.error("查询出错", e);
/*     */     }
/* 297 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public PageList findPageList(String querySql, String countSql, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 311 */       PageList pList = new PageList();
/*     */       
/* 313 */       SQLQuery countQuery = getCurrentSession().createSQLQuery(countSql);
/* 314 */       Number result = (Number)countQuery.uniqueResult();
/* 315 */       int count = result.intValue();
/* 316 */       pList.setCount(count);
/*     */       
/* 318 */       SQLQuery listQuery = getCurrentSession().createSQLQuery(querySql);
/* 319 */       if ((start >= 0) && (limit > 0)) {
/* 320 */         listQuery.setFirstResult(start);
/* 321 */         listQuery.setMaxResults(limit);
/*     */       }
/* 323 */       List<T> list = listQuery.list();
/* 324 */       pList.setList(list);
/* 325 */       return pList;
/*     */     } catch (Exception e) {
/* 327 */       logger.error("查询出错", e);
/*     */     }
/* 329 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public PageList findPageList(Class<T> clazz, List<Criterion> criterions, List<Order> orders, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 344 */       PageList pList = new PageList();
/* 345 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 346 */       for (Criterion criterion : criterions) {
/* 347 */         criteria.add(criterion);
/*     */       }
/* 349 */       for (Order order : orders) {
/* 350 */         criteria.addOrder(order);
/*     */       }
/*     */       
/* 353 */       criteria.setProjection(Projections.rowCount());
/* 354 */       Number result = (Number)criteria.uniqueResult();
/* 355 */       int count = result.intValue();
/* 356 */       pList.setCount(count);
/* 357 */       criteria.setProjection(null);
/*     */       
/* 359 */       if ((start >= 0) && (limit > 0)) {
/* 360 */         criteria.setFirstResult(start);
/* 361 */         criteria.setMaxResults(limit);
/*     */       }
/* 363 */       List<T> list = criteria.list();
/* 364 */       pList.setList(list);
/* 365 */       return pList;
/*     */     } catch (Exception e) {
/* 367 */       logger.error("查询出错", e);
/*     */     }
/* 369 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<T> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders)
/*     */   {
/*     */     try
/*     */     {
/* 382 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 383 */       for (Criterion criterion : criterions) {
/* 384 */         criteria.add(criterion);
/*     */       }
/* 386 */       for (Order order : orders) {
/* 387 */         criteria.addOrder(order);
/*     */       }
/* 389 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 391 */       logger.error("查询出错", e);
/*     */     }
/* 393 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<T> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 407 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 408 */       for (Criterion criterion : criterions) {
/* 409 */         criteria.add(criterion);
/*     */       }
/* 411 */       for (Order order : orders) {
/* 412 */         criteria.addOrder(order);
/*     */       }
/* 414 */       if ((start >= 0) && (limit > 0)) {
/* 415 */         criteria.setFirstResult(start);
/* 416 */         criteria.setMaxResults(limit);
/*     */       }
/* 418 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 420 */       logger.error("查询出错", e);
/*     */     }
/* 422 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<?> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders, Projection projection, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 438 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 439 */       for (Criterion criterion : criterions) {
/* 440 */         criteria.add(criterion);
/*     */       }
/* 442 */       for (Order order : orders) {
/* 443 */         criteria.addOrder(order);
/*     */       }
/* 445 */       criteria.setProjection(projection);
/* 446 */       if ((start >= 0) && (limit > 0)) {
/* 447 */         criteria.setFirstResult(start);
/* 448 */         criteria.setMaxResults(limit);
/*     */       }
/* 450 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 452 */       logger.error("查询出错", e);
/*     */     }
/* 454 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<?> findByCriteria(Class<T> clazz, List<Criterion> criterions, List<Order> orders, Projection projection)
/*     */   {
/*     */     try
/*     */     {
/* 470 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 471 */       for (Criterion criterion : criterions) {
/* 472 */         criteria.add(criterion);
/*     */       }
/* 474 */       for (Order order : orders) {
/* 475 */         criteria.addOrder(order);
/*     */       }
/* 477 */       criteria.setProjection(projection);
/* 478 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 480 */       logger.error("查询出错", e);
/*     */     }
/* 482 */     return null;
/*     */   }
/*     */   
/*     */   @Transactional
/*     */   public boolean doWork(final String sql, final List<Object[]> params) {
/* 487 */     boolean flag = false;
/*     */     try {
/* 489 */       getCurrentSession().doWork(new org.hibernate.jdbc.Work()
/*     */       {
/*     */         public void execute(Connection connection) throws SQLException {
/* 492 */           BatchSQLUtil sqlUtil = new BatchSQLUtil(connection, sql);
/* 493 */           for (Object[] param : params) {
/* 494 */             sqlUtil.addCount(param);
/*     */           }
/* 496 */           sqlUtil.commit();
/*     */         }
/* 498 */       });
/* 499 */       flag = true;
/*     */     } catch (Exception e) {
/* 501 */       logger.error("doWork出错", e);
/*     */     }
/* 503 */     return flag;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/hibernate/HibernateSuperDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */