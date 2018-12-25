 package com.fsy.lottery.domains.capture.sites.opencai;

 import java.io.BufferedReader;
 import java.io.InputStream;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.util.Iterator;
 import net.sf.json.JSONException;
 import net.sf.json.JSONObject;

 public class OpenCaiUtils
 {
   public static void main(String[] args)
   {
/* 15 */     String name = "";
/* 16 */     String uid = "";
/* 17 */     String token = "5332B2C452B7E697";

/* 19 */     String url = "http://api.caipiaokong.com/lottery/";
/* 20 */     url = url + "?name=" + name;
/* 21 */     url = url + "&format=json";
/* 22 */     url = url + "&uid=" + uid;
/* 23 */     url = url + "&token=" + token;

/* 25 */     url = "http://c.apiplus.cn/newly.do?token=5332B2C452B7E697&rows=3&format=json";

/* 27 */     String urlAll = url;
/* 28 */     String charset = "UTF-8";
/* 29 */     String jsonResult = get(urlAll, charset);
/* 30 */     JSONObject object = JSONObject.fromObject(jsonResult);
     try {
/* 32 */       Iterator it = object.keys();
/* 33 */       while (it.hasNext()) {
/* 34 */         String key = (String)it.next();
/* 35 */         String value = object.getString(key);
/* 36 */         JSONObject object1 = JSONObject.fromObject(value);
/* 37 */         String outputStr = "id:" + key;
/* 38 */         outputStr = outputStr + " number:" + object1.getString("number");
/* 39 */         outputStr = outputStr + " dateline:" + object1.getString("dateline");
       }
     } catch (JSONException e) {
/* 42 */       e.printStackTrace();
     }
   }









   public static String get(String urlAll, String charset)
   {
/* 56 */     BufferedReader reader = null;
/* 57 */     String result = null;
/* 58 */     StringBuffer sbf = new StringBuffer();
/* 59 */     String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
     try {
/* 61 */       URL url = new URL(urlAll);

/* 63 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 64 */       connection.setRequestMethod("GET");
/* 65 */       connection.setReadTimeout(30000);
/* 66 */       connection.setConnectTimeout(30000);
/* 67 */       connection.setRequestProperty("User-agent", userAgent);
/* 68 */       connection.connect();
/* 69 */       InputStream is = connection.getInputStream();
/* 70 */       reader = new BufferedReader(new java.io.InputStreamReader(is, charset));
/* 71 */       String strRead = null;
/* 72 */       while ((strRead = reader.readLine()) != null) {
/* 73 */         sbf.append(strRead);
/* 74 */         sbf.append("\r\n");
       }
/* 76 */       reader.close();
/* 77 */       result = sbf.toString();
     } catch (Exception e) {
/* 79 */       e.printStackTrace();
     }
/* 81 */     return result;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/sites/opencai/OpenCaiUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */