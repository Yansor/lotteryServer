/*    */ package javautils.datasource;
/*    */ 
/*    */ import com.alibaba.druid.filter.Filter;
/*    */ import com.alibaba.druid.pool.DruidDataSource;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomDruidDataSource
/*    */   extends DruidDataSource
/*    */ {
/*    */   public List<Filter> getProxyFilters()
/*    */   {
/* 15 */     List<Filter> proxyFilters = new ArrayList();
/* 16 */     proxyFilters.add(ConfigDruidDataSource.configLog4jFilter());
/* 17 */     proxyFilters.add(ConfigDruidDataSource.configStatFilter());
/* 18 */     return proxyFilters;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/datasource/CustomDruidDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */