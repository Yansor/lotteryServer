 package com.fsy.javautils.jdbc;

 import java.util.ArrayList;
 import java.util.List;





 public class PageList
 {
   private List<?> list;
   private int count;

   public PageList()
   {
/* 17 */     this.list = new ArrayList();
/* 18 */     this.count = 0;
   }

   public PageList(List<?> list, int count) {
/* 22 */     this.list = list;
/* 23 */     this.count = count;
   }

   public List<?> getList() {
/* 27 */     return this.list;
   }

   public void setList(List<?> list) {
/* 31 */     this.list = list;
   }

   public int getCount() {
/* 35 */     return this.count;
   }

   public void setCount(int count) {
/* 39 */     this.count = count;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/jdbc/PageList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */