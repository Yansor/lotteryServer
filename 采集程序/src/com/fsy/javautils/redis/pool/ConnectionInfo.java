 package com.fsy.javautils.redis.pool;











 public class ConnectionInfo
 {
/* 15 */   public static final String DEFAULT_PASSWORD = null;

/* 17 */   private int database = 0;
/* 18 */   private String password = DEFAULT_PASSWORD;
/* 19 */   private int timeout = 2000;

   public ConnectionInfo() {}

   public ConnectionInfo(int database, String password, int timeout)
   {
/* 25 */     this.timeout = timeout;
/* 26 */     if ((password != null) && (password.length() > 0)) {
/* 27 */       this.password = password;
     }
     else {
/* 30 */       this.password = DEFAULT_PASSWORD;
     }
/* 32 */     this.database = database;
   }

   public int getDatabase() {
/* 36 */     return this.database;
   }

   public void setDatabase(int database) {
/* 40 */     this.database = database;
   }

   public String getPassword() {
/* 44 */     return this.password;
   }

   public void setPassword(String password) {
/* 48 */     if ((password != null) && (password.length() > 0)) {
/* 49 */       this.password = password;
     }
     else {
/* 52 */       this.password = DEFAULT_PASSWORD;
     }
   }

   public int getTimeout() {
/* 57 */     return this.timeout;
   }

   public void setTimeout(int timeout) {
/* 61 */     this.timeout = timeout;
   }

   public String toString()
   {
/* 66 */     return "ConnectionInfo [database=" + this.database + ", password=" + this.password + ", timeout=" + this.timeout + "]";
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/redis/pool/ConnectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */