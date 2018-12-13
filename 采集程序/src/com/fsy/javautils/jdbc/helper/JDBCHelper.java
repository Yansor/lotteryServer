/*     */ package com.fsy.javautils.jdbc.helper;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
import java.util.List;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.sql.DataSource;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBCHelper
/*     */ {
/*     */   public static final String MYSQL = "com.mysql.jdbc.Driver";
/*     */   
/*     */   public static Connection openConnection(String url, String username, String password, String type)
/*     */   {
/*  23 */     Connection conn = null;
/*     */     try {
/*  25 */       Class.forName(type);
/*  26 */       conn = DriverManager.getConnection(url, username, password);
/*     */     } catch (Exception localException) {}
/*  28 */     return conn;
/*     */   }
/*     */   
/*     */   public static boolean executeUpdate(String sql, Connection conn) {
/*  32 */     boolean flag = false;
/*  33 */     if (conn != null) {
/*  34 */       PreparedStatement pst = null;
/*     */       try {
/*  36 */         pst = conn.prepareStatement(sql);
/*  37 */         int count = pst.executeUpdate();
/*  38 */         return count > 0;
/*     */       }
/*     */       catch (Exception localException) {}finally {
/*     */         try {
/*  42 */           if (pst != null) pst.close();
/*  43 */           if (conn != null) conn.close();
/*     */         }
/*     */         catch (SQLException localSQLException2) {}
/*     */       }
/*     */     }
return false;

/*     */   }
/*     */   
/*     */   public static JSONArray executeQuery(String sql, Connection conn) {
/*  51 */     List list = null;
/*  52 */     if (conn != null) {
/*  53 */       PreparedStatement pst = null;
/*     */       try {
/*  55 */         pst = conn.prepareStatement(sql);
/*  56 */         ResultSet rs = pst.executeQuery();
/*  57 */         return toJsonArray(rs);
/*     */       } catch (Exception localException) {}finally {
/*     */         try {
/*  60 */           if (pst != null) pst.close();
/*  61 */           if (conn != null) conn.close();
/*     */         }
/*     */         catch (SQLException localSQLException2) {}
/*     */       }
/*     */     }
            return null;
/*     */   }
/*     */   
/*     */   public static JSONArray toJsonArray(ResultSet rs) throws Exception {
/*  69 */     JSONArray list = new JSONArray();
/*  70 */     if (rs != null) {
/*  71 */       ResultSetMetaData metaData = rs.getMetaData();
/*  72 */       int columnCount = metaData.getColumnCount();
/*  73 */       while (rs.next()) {
/*  74 */         JSONObject obj = new JSONObject();
/*  75 */         for (int i = 0; i < columnCount; i++) {
/*  76 */           String columnName = metaData.getColumnLabel(i + 1);
/*  77 */           String value = rs.getString(columnName);
/*  78 */           obj.put(columnName, value);
/*     */         }
/*  80 */         list.add(obj);
/*     */       }
/*     */     }
/*  83 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Connection openConnection(String jndi)
/*     */   {
/*  92 */     Connection conn = null;
/*     */     try {
/*  94 */       Context context = new InitialContext();
/*  95 */       DataSource source = (DataSource)context.lookup(jndi);
/*  96 */       conn = source.getConnection();
/*     */     } catch (Exception localException) {}
/*  98 */     return conn;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 102 */     String url = "jdbc:mysql://192.168.1.111:3306/test?useUnicode=true&characterEncoding=utf-8";
/* 103 */     String username = "root";
/* 104 */     String password = "root";
/* 105 */     Connection conn = openConnection(url, username, password, "com.mysql.jdbc.Driver");
/* 106 */     String sql = "select * from user";
/* 107 */     executeQuery(sql, conn);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/helper/JDBCHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */