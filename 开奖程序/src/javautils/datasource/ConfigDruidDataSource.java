/*    */ package javautils.datasource;
/*    */ 
/*    */ import com.alibaba.druid.filter.logging.Log4jFilter;
/*    */ import com.alibaba.druid.filter.stat.StatFilter;
/*    */ 
/*    */ 
/*    */ public class ConfigDruidDataSource
/*    */ {
/*    */   public static Log4jFilter configLog4jFilter()
/*    */   {
/* 11 */     Log4jFilter filter = new Log4jFilter();
/* 12 */     filter.setStatementExecuteQueryAfterLogEnabled(false);
/* 13 */     filter.setStatementExecuteUpdateAfterLogEnabled(false);
/* 14 */     filter.setStatementExecuteBatchAfterLogEnabled(false);
/* 15 */     filter.setStatementExecuteAfterLogEnabled(false);
/* 16 */     filter.setStatementExecutableSqlLogEnable(false);
/* 17 */     filter.setStatementParameterSetLogEnabled(false);
/* 18 */     filter.setStatementParameterClearLogEnable(false);
/* 19 */     filter.setStatementCreateAfterLogEnabled(false);
/* 20 */     filter.setStatementCloseAfterLogEnabled(false);
/* 21 */     filter.setStatementPrepareAfterLogEnabled(false);
/* 22 */     filter.setStatementPrepareCallAfterLogEnabled(false);
/* 23 */     filter.setStatementLogEnabled(false);
/* 24 */     filter.setStatementLogErrorEnabled(false);
/* 25 */     return filter;
/*    */   }
/*    */   
/*    */   public static StatFilter configStatFilter() {
/* 29 */     StatFilter filter = new StatFilter();
/* 30 */     filter.setSlowSqlMillis(5000L);
/* 31 */     filter.setLogSlowSql(true);
/* 32 */     filter.setMergeSql(true);
/* 33 */     return filter;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/datasource/ConfigDruidDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */