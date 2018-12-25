 package com.fsy.javautils.jdbc.util;

 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;


 public class BatchSQLUtil
 {
/* 12 */   private static final Logger logger = LoggerFactory.getLogger(BatchSQLUtil.class);
   private Connection conn;
   private PreparedStatement ps;

   public BatchSQLUtil(Connection conn, String sql)
   {
     try {
/* 19 */       this.conn = conn;
/* 20 */       this.conn.setAutoCommit(false);
/* 21 */       this.ps = this.conn.prepareStatement(sql);
     } catch (Exception e) {
/* 23 */       logger.error("BatchSQLUtil异常", e);
     }
   }

   public void addCount(Object[] param) {
     try {
/* 29 */       for (int i = 0; i < param.length; i++) {
/* 30 */         this.ps.setObject(i + 1, param[i]);
       }
/* 32 */       this.ps.addBatch();
     } catch (SQLException e) {
/* 34 */       logger.error("addCount异常", e);
     }
   }

   public void commit() {
     try {
/* 40 */       this.ps.executeBatch();
/* 41 */       this.conn.commit();
/* 42 */       this.ps.clearBatch();
     } catch (SQLException e) {
/* 44 */       logger.error("commit异常", e);
     }
   }

   public void close() {
     try {
/* 50 */       this.ps.close();
/* 51 */       this.conn.close();
     } catch (SQLException e) {
/* 53 */       logger.error("close异常", e);
     }
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/util/BatchSQLUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */