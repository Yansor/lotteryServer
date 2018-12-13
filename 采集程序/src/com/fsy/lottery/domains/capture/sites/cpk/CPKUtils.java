/*    */ package com.fsy.lottery.domains.capture.sites.cpk;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import net.sf.json.JSONException;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class CPKUtils
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 16 */     String name = "";
/* 17 */     String uid = "";
/* 18 */     String token = "";
/*    */     
/* 20 */     String url = "http://api.caipiaokong.com/lottery/";
/* 21 */     url = url + "?name=" + name;
/* 22 */     url = url + "&format=json";
/* 23 */     url = url + "&uid=" + uid;
/* 24 */     url = url + "&token=" + token;
/*    */     
/* 26 */     String urlAll = url;
/* 27 */     String charset = "UTF-8";
/* 28 */     String jsonResult = get(urlAll, charset);
/* 29 */     JSONObject object = JSONObject.fromObject(jsonResult);
/*    */     try {
/* 31 */       Iterator it = object.keys();
/* 32 */       while (it.hasNext()) {
/* 33 */         String key = (String)it.next();
/* 34 */         String value = object.getString(key);
/* 35 */         JSONObject object1 = JSONObject.fromObject(value);
/* 36 */         String outputStr = "id:" + key;
/* 37 */         outputStr = outputStr + " number:" + object1.getString("number");
/* 38 */         outputStr = outputStr + " dateline:" + object1.getString("dateline");
/* 39 */         System.out.println(outputStr);
/*    */       }
/*    */     } catch (JSONException e) {
/* 42 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String get(String urlAll, String charset)
/*    */   {
/* 54 */     BufferedReader reader = null;
/* 55 */     String result = null;
/* 56 */     StringBuffer sbf = new StringBuffer();
/* 57 */     String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
/*    */     try {
/* 59 */       URL url = new URL(urlAll);
/*    */       
/* 61 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 62 */       connection.setRequestMethod("GET");
/* 63 */       connection.setReadTimeout(30000);
/* 64 */       connection.setConnectTimeout(30000);
/* 65 */       connection.setRequestProperty("User-agent", userAgent);
/* 66 */       connection.connect();
/* 67 */       InputStream is = connection.getInputStream();
/* 68 */       reader = new BufferedReader(new java.io.InputStreamReader(is, charset));
/* 69 */       String strRead = null;
/* 70 */       while ((strRead = reader.readLine()) != null) {
/* 71 */         sbf.append(strRead);
/* 72 */         sbf.append("\r\n");
/*    */       }
/* 74 */       reader.close();
/* 75 */       result = sbf.toString();
/*    */     } catch (Exception e) {
/* 77 */       e.printStackTrace();
/*    */     }
/* 79 */     return result;
/*    */   }
/*    */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/sites/cpk/CPKUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */