/*    */ package javautils.map;
/*    */ 
/*    */ import java.util.Comparator;
import java.util.LinkedList;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class MapUtil
/*    */ {
/*    */   public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAsc(Map<K, V> map)
/*    */   {
/* 11 */     java.util.List<Entry<K, V>> list = new LinkedList(map.entrySet());
/* 12 */     java.util.Collections.sort(list, new Comparator<Entry<K, V>>() {

/*    */       public int compare(Entry<K, V> o1, Entry<K, V> o2) {
/* 14 */         return ((Comparable)o1.getValue()).compareTo(o2.getValue());
/*    */       }
/*    */       
/* 17 */     });
/* 18 */     Map<K, V> result = new java.util.LinkedHashMap();
/* 19 */     for (Entry<K, V> entry : list) {
/* 20 */       result.put(entry.getKey(), entry.getValue());
/*    */     }
/* 22 */     return result;
/*    */   }
/*    */   
/*    */   public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
/* 26 */     java.util.List<Entry<K, V>> list = new LinkedList(map.entrySet());
/* 27 */     java.util.Collections.sort(list, new java.util.Comparator<Entry<K, V>>() {
/*    */       public int compare(Entry<K, V> o1, Entry<K, V> o2) {
/* 29 */         return ((Comparable)o2.getValue()).compareTo(o1.getValue());
/*    */       }
/*    */       
/* 32 */     });
/* 33 */     Map<K, V> result = new java.util.LinkedHashMap();
/* 34 */     for (Entry<K, V> entry : list) {
/* 35 */       result.put(entry.getKey(), entry.getValue());
/*    */     }
/* 37 */     return result;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {}
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/javautils/map/MapUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */