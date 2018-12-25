 package com.fsy.javautils.jdbc.util;

 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.util.ArrayList;
 import java.util.List;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class SQLUtil
 {
/* 14 */   private static final Logger logger = LoggerFactory.getLogger(SQLUtil.class);

   public boolean aduSql(Connection conn, String sql)
   {
/* 18 */     boolean flag = false;
     try {
/* 20 */       if (conn != null) {
/* 21 */         PreparedStatement ps = conn.prepareStatement(sql);
/* 22 */         ps.executeUpdate();
/* 23 */         ps.close();
/* 24 */         flag = true;
       }
     } catch (Exception e) {
/* 27 */       logger.error("aduSql异常", e);
/* 28 */       flag = false;
     }
/* 30 */     return flag;
   }

   public static boolean aduSql(Connection conn, String sql, Object[] params) {
/* 34 */     boolean flag = false;
     try {
/* 36 */       if (conn != null) {
/* 37 */         PreparedStatement ps = conn.prepareStatement(sql);
/* 38 */         if (params != null) {
/* 39 */           for (int i = 0; i < params.length; i++) {
/* 40 */             ps.setObject(i + 1, params[i]);
           }
         }
/* 43 */         ps.executeUpdate();
/* 44 */         flag = true;
/* 45 */         ps.close();
       }
     } catch (Exception e) {
/* 48 */       logger.error("aduSql异常", e);
/* 49 */       flag = false;
     }
/* 51 */     return flag;
   }

   public static List<Object[]> exList(Connection conn, String sql, Object[] params) {
/* 55 */     ArrayList<Object[]> list = new ArrayList();
     try {
/* 57 */       if (conn != null) {
/* 58 */         PreparedStatement ps = conn.prepareStatement(sql);
/* 59 */         if (params != null) {
/* 60 */           for (int i = 0; i < params.length; i++) {
/* 61 */             ps.setObject(i + 1, params[i]);
           }
         }
/* 64 */         ResultSet rs = ps.executeQuery();
/* 65 */         while (rs.next()) {
/* 66 */           int columns = rs.getMetaData().getColumnCount();
/* 67 */           Object[] objArr = new Object[columns];
/* 68 */           for (int i = 0; i < columns; i++) {
/* 69 */             objArr[i] = rs.getObject(i + 1);
           }
/* 71 */           list.add(objArr);
         }
/* 73 */         rs.close();
/* 74 */         ps.close();
       }
     } catch (Exception e) {
/* 77 */       logger.error("exList异常", e);
     }
/* 79 */     return list;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/util/SQLUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */