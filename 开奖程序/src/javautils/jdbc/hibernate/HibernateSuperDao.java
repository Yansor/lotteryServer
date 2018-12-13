/*     */ package javautils.jdbc.hibernate;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import javautils.jdbc.PageList;
/*     */ import javautils.jdbc.util.BatchSQLUtil;
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
/*  47 */       throw e;
/*     */     }
/*  49 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional
/*     */   public boolean update(Object entity)
/*     */   {
/*  59 */     boolean flag = false;
/*     */     try {
/*  61 */       getCurrentSession().update(entity);
/*  62 */       flag = true;
/*     */     } catch (Exception e) {
/*  64 */       logger.error("更新实体类异常", e);
/*  65 */       throw e;
/*     */     }
/*  67 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional
/*     */   public int updateBySql(String sql)
/*     */   {
/*     */     try
/*     */     {
/*  78 */       SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
/*  79 */       return sqlQuery.executeUpdate();
/*     */     } catch (Exception e) {
/*  81 */       logger.error("更新实体类异常", e);
/*  82 */       throw e;
/*     */     }
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
/*  94 */     boolean flag = false;
/*     */     try {
/*  96 */       Query query = getCurrentSession().createQuery(hql);
/*  97 */       for (int i = 0; i < values.length; i++) {
/*  98 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 100 */       int result = query.executeUpdate();
/* 101 */       flag = result > 0;
/*     */     } catch (Exception e) {
/* 103 */       logger.error("更新实体类异常", e);
/* 104 */       throw e;
/*     */     }
/* 106 */     return flag;
/*     */   }
/*     */   
/*     */   @Transactional
/*     */   public int updateByHql(String hql) {
/*     */     try {
/* 112 */       Query query = getCurrentSession().createQuery(hql);
/* 113 */       return query.executeUpdate();
/*     */     }
/*     */     catch (Exception e) {
/* 116 */       logger.error("更新实体类异常", e);
/* 117 */       throw e;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean delete(Object entity)
/*     */   {
/* 127 */     boolean flag = false;
/*     */     try {
/* 129 */       getCurrentSession().delete(entity);
/* 130 */       flag = true;
/*     */     } catch (Exception e) {
/* 132 */       logger.error("删除实体类异常", e);
/* 133 */       throw e;
/*     */     }
/* 135 */     return flag;
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
/* 146 */     boolean flag = false;
/*     */     try {
/* 148 */       Query query = getCurrentSession().createQuery(hql);
/* 149 */       for (int i = 0; i < values.length; i++) {
/* 150 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 152 */       int result = query.executeUpdate();
/* 153 */       flag = result > 0;
/*     */     } catch (Exception e) {
/* 155 */       logger.error("删除实体类异常", e);
/* 156 */       throw e;
/*     */     }
/* 158 */     return flag;
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
/* 169 */       Query query = getCurrentSession().createQuery(hql);
/* 170 */       return query.list();
/*     */     } catch (Exception e) {
/* 172 */       logger.error("查询出错", e);
/*     */     }
/* 174 */     return null;
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
/* 187 */       Query query = getCurrentSession().createQuery(hql);
/* 188 */       if ((start >= 0) && (limit > 0)) {
/* 189 */         query.setFirstResult(start);
/* 190 */         query.setMaxResults(limit);
/*     */       }
/* 192 */       return query.list();
/*     */     } catch (Exception e) {
/* 194 */       logger.error("查询出错", e);
/*     */     }
/* 196 */     return null;
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
/* 207 */       Query query = getCurrentSession().createQuery(hql);
/* 208 */       return query.uniqueResult();
/*     */     } catch (Exception e) {
/* 210 */       logger.error("查询出错", e);
/*     */     }
/* 212 */     return null;
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
/* 224 */       Query query = getCurrentSession().createQuery(hql);
/* 225 */       for (int i = 0; i < values.length; i++) {
/* 226 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 228 */       return query.uniqueResult();
/*     */     } catch (Exception e) {
/* 230 */       logger.error("查询出错", e);
/*     */     }
/* 232 */     return null;
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
/* 244 */       Query query = getCurrentSession().createQuery(hql);
/* 245 */       for (int i = 0; i < values.length; i++) {
/* 246 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 248 */       return query.list();
/*     */     } catch (Exception e) {
/* 250 */       logger.error("查询出错", e);
/*     */     }
/* 252 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transactional(readOnly=true)
/*     */   public List<?> listObject(String hql, Object[] values, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 264 */       Query query = getCurrentSession().createQuery(hql);
/* 265 */       for (int i = 0; i < values.length; i++) {
/* 266 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 268 */       if ((start >= 0) && (limit > 0)) {
/* 269 */         query.setFirstResult(start);
/* 270 */         query.setMaxResults(limit);
/*     */       }
/* 272 */       return query.list();
/*     */     } catch (Exception e) {
/* 274 */       logger.error("查询出错", e);
/*     */     }
/* 276 */     return null;
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
/* 288 */       Query query = getCurrentSession().createQuery(hql);
/* 289 */       for (int i = 0; i < values.length; i++) {
/* 290 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 292 */       return query.list();
/*     */     } catch (Exception e) {
/* 294 */       logger.error("查询出错", e);
/*     */     }
/* 296 */     return null;
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
/* 310 */       Query query = getCurrentSession().createQuery(hql);
/* 311 */       for (int i = 0; i < values.length; i++) {
/* 312 */         query.setParameter(String.valueOf(i), values[i]);
/*     */       }
/* 314 */       if ((start >= 0) && (limit > 0)) {
/* 315 */         query.setFirstResult(start);
/* 316 */         query.setMaxResults(limit);
/*     */       }
/* 318 */       return query.list();
/*     */     } catch (Exception e) {
/* 320 */       logger.error("查询出错", e);
/*     */     }
/* 322 */     return null;
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
/* 333 */       SQLQuery query = getCurrentSession().createSQLQuery(sql);
/* 334 */       return query.list();
/*     */     } catch (Exception e) {
/* 336 */       logger.error("查询出错", e);
/*     */     }
/* 338 */     return null;
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
/* 349 */       SQLQuery query = getCurrentSession().createSQLQuery(sql);
/* 350 */       return query.uniqueResult();
/*     */     } catch (Exception e) {
/* 352 */       logger.error("查询出错", e);
/*     */     }
/* 354 */     return null;
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
/* 368 */       PageList pList = new PageList();
/*     */       
/* 370 */       SQLQuery countQuery = getCurrentSession().createSQLQuery(countSql);
/* 371 */       Number result = (Number)countQuery.uniqueResult();
/* 372 */       int count = result.intValue();
/* 373 */       pList.setCount(count);
/*     */       
/* 375 */       SQLQuery listQuery = getCurrentSession().createSQLQuery(querySql);
/* 376 */       if ((start >= 0) && (limit > 0)) {
/* 377 */         listQuery.setFirstResult(start);
/* 378 */         listQuery.setMaxResults(limit);
/*     */       }
/* 380 */       List<T> list = listQuery.list();
/* 381 */       pList.setList(list);
/* 382 */       return pList;
/*     */     } catch (Exception e) {
/* 384 */       logger.error("查询出错", e);
/*     */     }
/* 386 */     return null;
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
/*     */   public PageList findPageList(Class<T> clazz, String propertyName, List<Criterion> criterions, List<Order> orders, int start, int limit)
/*     */   {
/*     */     try
/*     */     {
/* 401 */       PageList pList = new PageList();
/* 402 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 403 */       for (Criterion criterion : criterions) {
/* 404 */         criteria.add(criterion);
/*     */       }
/* 406 */       if ((orders != null) && (orders.size() > 0)) {
/* 407 */         for (Order order : orders) {
/* 408 */           criteria.addOrder(order);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 413 */       criteria.setProjection(Projections.count(propertyName));
/* 414 */       Number result = (Number)criteria.uniqueResult();
/* 415 */       int count = result.intValue();
/* 416 */       pList.setCount(count);
/* 417 */       criteria.setProjection(null);
/*     */       
/* 419 */       if ((start >= 0) && (limit > 0)) {
/* 420 */         criteria.setFirstResult(start);
/* 421 */         criteria.setMaxResults(limit);
/*     */       }
/* 423 */       List<T> list = criteria.list();
/* 424 */       pList.setList(list);
/* 425 */       return pList;
/*     */     } catch (Exception e) {
/* 427 */       logger.error("查询出错", e);
/*     */     }
/* 429 */     return null;
/*     */   }
/*     */   
/*     */   @Transactional(readOnly=true)
/*     */   public PageList findPageList(Class<T> clazz, List<Criterion> criterions, List<Order> orders, int start, int limit) {
/*     */     try {
/* 435 */       PageList pList = new PageList();
/* 436 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 437 */       for (Criterion criterion : criterions) {
/* 438 */         criteria.add(criterion);
/*     */       }
/* 440 */       if ((orders != null) && (orders.size() > 0)) {
/* 441 */         for (Order order : orders) {
/* 442 */           criteria.addOrder(order);
/*     */         }
/*     */       }
/*     */       
/* 446 */       criteria.setProjection(Projections.rowCount());
/* 447 */       Number result = (Number)criteria.uniqueResult();
/* 448 */       int count = result.intValue();
/* 449 */       pList.setCount(count);
/* 450 */       criteria.setProjection(null);
/*     */       
/* 452 */       if ((start >= 0) && (limit > 0)) {
/* 453 */         criteria.setFirstResult(start);
/* 454 */         criteria.setMaxResults(limit);
/*     */       }
/* 456 */       List<T> list = criteria.list();
/* 457 */       pList.setList(list);
/* 458 */       return pList;
/*     */     } catch (Exception e) {
/* 460 */       logger.error("查询出错", e);
/*     */     }
/* 462 */     return null;
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
/* 475 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 476 */       for (Criterion criterion : criterions) {
/* 477 */         criteria.add(criterion);
/*     */       }
/* 479 */       if ((orders != null) && (orders.size() > 0)) {
/* 480 */         for (Order order : orders) {
/* 481 */           criteria.addOrder(order);
/*     */         }
/*     */       }
/* 484 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 486 */       logger.error("查询出错", e);
/*     */     }
/* 488 */     return null;
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
/* 502 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 503 */       for (Criterion criterion : criterions) {
/* 504 */         criteria.add(criterion);
/*     */       }
/* 506 */       if ((orders != null) && (orders.size() > 0)) {
/* 507 */         for (Order order : orders) {
/* 508 */           criteria.addOrder(order);
/*     */         }
/*     */       }
/* 511 */       if ((start >= 0) && (limit > 0)) {
/* 512 */         criteria.setFirstResult(start);
/* 513 */         criteria.setMaxResults(limit);
/*     */       }
/* 515 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 517 */       logger.error("查询出错", e);
/*     */     }
/* 519 */     return null;
/*     */   }
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
/* 532 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 533 */       for (Criterion criterion : criterions) {
/* 534 */         criteria.add(criterion);
/*     */       }
/* 536 */       if ((orders != null) && (orders.size() > 0)) {
/* 537 */         for (Order order : orders) {
/* 538 */           criteria.addOrder(order);
/*     */         }
/*     */       }
/* 541 */       criteria.setProjection(projection);
/* 542 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 544 */       logger.error("查询出错", e);
/*     */     }
/* 546 */     return null;
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
/* 562 */       Criteria criteria = getCurrentSession().createCriteria(clazz);
/* 563 */       for (Criterion criterion : criterions) {
/* 564 */         criteria.add(criterion);
/*     */       }
/* 566 */       if ((orders != null) && (orders.size() > 0)) {
/* 567 */         for (Order order : orders) {
/* 568 */           criteria.addOrder(order);
/*     */         }
/*     */       }
/* 571 */       criteria.setProjection(projection);
/* 572 */       if ((start >= 0) && (limit > 0)) {
/* 573 */         criteria.setFirstResult(start);
/* 574 */         criteria.setMaxResults(limit);
/*     */       }
/* 576 */       return criteria.list();
/*     */     } catch (Exception e) {
/* 578 */       logger.error("查询出错", e);
/*     */     }
/* 580 */     return null;
/*     */   }
/*     */   
/*     */   @Transactional
/*     */   public boolean doWork(final String sql, final List<Object[]> params) {
/* 585 */     boolean flag = false;
/*     */     try {
/* 587 */       getCurrentSession().doWork(new org.hibernate.jdbc.Work()
/*     */       {
/*     */         public void execute(Connection connection) throws SQLException {
/* 590 */           BatchSQLUtil sqlUtil = new BatchSQLUtil(connection, sql);
/* 591 */           for (Object[] param : params) {
/* 592 */             sqlUtil.addCount(param);
/*     */           }
/* 594 */           sqlUtil.commit();
/*     */         }
/* 596 */       });
/* 597 */       flag = true;
/*     */     } catch (Exception e) {
/* 599 */       logger.error("doWork出错", e);
/* 600 */       throw e;
/*     */     }
/* 602 */     return flag;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/jdbc/hibernate/HibernateSuperDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */