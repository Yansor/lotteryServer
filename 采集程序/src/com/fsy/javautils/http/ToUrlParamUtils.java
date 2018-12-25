 package com.fsy.javautils.http;

 import java.util.Iterator;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;




 public class ToUrlParamUtils
 {
   private static final String EMPTY = "";
   private static final String EQUALS = "=";
   private static final String DEFAULT_SEPARATOR = "&";

   public static String toUrlParam(Map<String, String> params)
   {
/* 19 */     return toUrlParam(params, "&", true);
   }

   public static String toUrlParam(Map<String, String> params, String separator) {
/* 23 */     return toUrlParam(params, separator, true);
   }

   public static String toUrlParam(Map<String, String> params, String separator, boolean ignoreEmpty) {
/* 27 */     if ((params == null) || (params.isEmpty())) {
/* 28 */       return "";
     }

/* 31 */     StringBuffer url = new StringBuffer();

/* 33 */     Iterator<Entry<String, String>> it = params.entrySet().iterator();
/* 34 */     while (it.hasNext()) {
/* 35 */       Entry<String, String> entry = (Entry)it.next();
/* 36 */       String value = (String)entry.getValue();
/* 37 */       boolean valueIsEmpty = isEmpty(value);
/* 38 */       if ((!ignoreEmpty) || (!valueIsEmpty))
       {


/* 42 */         url.append((String)entry.getKey()).append("=").append(value);
/* 43 */         if (it.hasNext()) {
/* 44 */           url.append(separator);
         }
       }
     }
/* 48 */     return url.toString();
   }

   private static boolean isEmpty(String value) {
/* 52 */     return (value == null) || ("".equals(value));
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/http/ToUrlParamUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */