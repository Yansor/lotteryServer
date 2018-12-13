/*     */ package com.fsy.lottery.domains.capture.jobs;
/*     */ 
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import com.fsy.javautils.date.Moment;
/*     */ import com.fsy.javautils.http.HttpClientUtil;
/*     */ import com.fsy.lottery.domains.capture.sites.pcqq.PCQQBean;
/*     */ import com.fsy.lottery.domains.capture.utils.CodeValidate;
/*     */ import com.fsy.lottery.domains.capture.utils.ExpectValidate;
/*     */ import com.fsy.lottery.domains.content.biz.LotteryOpenCodeService;
/*     */ import com.fsy.lottery.domains.content.entity.LotteryOpenCode;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class PCQQJob
/*     */ {
/*     */   private static final String ONLINE_URL = "https://mma.qq.com/cgi-bin/im/online";
/*     */   private static final String CGI_SVRTIME_URL = "http://cgi.im.qq.com/cgi-bin/cgi_svrtime";
/*  32 */   private static final Logger logger = LoggerFactory.getLogger(PCQQJob.class);
/*     */   
/*     */   private static final String NAME = "txffc";
/*  35 */   private static ConcurrentHashMap<String, String> HIS_OPEN_CODES = new ConcurrentHashMap();
/*     */   
/*     */   @Autowired
/*     */   private LotteryOpenCodeService lotteryOpenCodeService;
/*     */   
/*  40 */   private static boolean isRuning = false;
/*     */   
/*     */ 
/*     */   @Scheduled(cron="8,15,20,25,30,35 * * * * *")
/*     */   public void schedule()
/*     */   {
/*  46 */     synchronized (PCQQJob.class) {
/*  47 */       if (isRuning == true) {
/*  48 */         return;
/*     */       }
/*  50 */       isRuning = true;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  55 */       logger.debug("开始抓取PCQQ官网在线人数数据>>>>>>>>>>>>>>>>");
/*     */       
/*  57 */       long start = System.currentTimeMillis();
/*     */       
/*  59 */       start();
/*  60 */       long spend = System.currentTimeMillis() - start;
/*     */       
/*  62 */       logger.debug("完成抓取PCQQ官网在线人数数据>>>>>>>>>>>>>>>>耗时{}", Long.valueOf(spend));
/*     */     } catch (Exception e) {
/*  64 */       logger.error("抓取PCQQ官网在线人数数据出错", e);
/*     */     } finally {
/*  66 */       isRuning = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void start() {
/*  71 */     int startMinute = new Moment().get("minute");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */     String result = getOnlineNum();
/*     */     
/*  81 */     int endMinute = new Moment().get("minute");
/*  82 */     if (startMinute != endMinute) {
/*  83 */       logger.error("PCQQ官网在线人数抓取超时，开始分钟{}，结束分钟{}", Integer.valueOf(startMinute), Integer.valueOf(endMinute));
/*  84 */       return;
/*     */     }
/*     */     
/*  87 */     int endSecond = new Moment().get("second");
/*  88 */     if (endSecond >= 40) {
/*  89 */       logger.error("PCQQ官网在线人数抓取不处理，因为处理完成的秒数{}>=40秒，可能是下一期的数据", Integer.valueOf(endSecond));
/*  90 */       return;
/*     */     }
/*     */     
/*  93 */     handleData(result);
/*     */   }
/*     */   
/*     */   private boolean validateTencentServerTime() {
/*  97 */     Moment tencentServerTime = getTencentServerTime();
/*  98 */     if (tencentServerTime == null) {
/*  99 */       return false;
/*     */     }
/* 101 */     Moment now = new Moment();
/*     */     
/* 103 */     int diffSeconds = now.difference(tencentServerTime, "second");
/*     */     
/* 105 */     if ((diffSeconds >= 5) || (diffSeconds <= -5)) {
/* 106 */       logger.error("当前服务器时间超过腾讯服务器时间太多，抓取服务器时间{}，PCQQ官网时间{}，本次不处理", now.toSimpleTime(), tencentServerTime.toSimpleTime());
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     logger.error("抓取服务器时间{}，PCQQ官网时间{}", now.toSimpleTime(), tencentServerTime.toSimpleTime());
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   private void handleData(String result) {
/* 115 */     if (StringUtils.isEmpty(result)) {
/* 116 */       return;
/*     */     }
/*     */     
/* 119 */     PCQQBean pcqqBean = (PCQQBean)JSON.parseObject(result, PCQQBean.class);
/* 120 */     if (pcqqBean == null) {
/* 121 */       logger.error("解析PCQQ返回数据出错，返回数据" + result);
/* 122 */       return;
/*     */     }
/*     */     
/*     */ 
/* 126 */     handleBean(pcqqBean);
/*     */   }
/*     */   
/*     */   private boolean handleBean(PCQQBean bean) {
/* 130 */     boolean valid = checkData(bean);
/* 131 */     if (!valid) {
/* 132 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 136 */     String currentExpect = getExpectByTime(bean.getOnlinetime());
/* 137 */     if (!ExpectValidate.validate("txffc", currentExpect)) {
/* 138 */       logger.error("PCQQ官网腾讯分分彩抓取期数" + currentExpect + "错误");
/* 139 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 143 */     if (HIS_OPEN_CODES.containsKey(currentExpect)) {
/* 144 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 148 */     String currentCode = convertCode(bean.getOnlinenumber());
/* 149 */     if (!CodeValidate.validate("txffc", currentCode)) {
/* 150 */       logger.error("PCQQ官网腾讯分分彩抓取号码" + currentCode + "错误");
/* 151 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 155 */     LotteryOpenCode dbData = this.lotteryOpenCodeService.get("txffc", currentExpect);
/* 156 */     if (dbData != null) {
/* 157 */       HIS_OPEN_CODES.put(currentExpect, dbData.getCode());
/*     */       
/* 159 */       if (!dbData.getCode().equals(currentCode)) {
/* 160 */         logger.error("PCQQ官网抓取时遇到错误：抓取{}期开奖号码{}与数据库已有开奖号码{}不符", new Object[] { currentExpect, currentCode, dbData.getCode() });
/* 161 */         return false;
/*     */       }
/*     */       
/* 164 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 168 */     HIS_OPEN_CODES.put(currentExpect, currentCode);
/*     */     
/*     */ 
/* 171 */     String lastExpectTime = new Moment().fromTime(bean.getOnlinetime()).subtract(1, "minutes").toSimpleTime();
/* 172 */     String lastExpect = getExpectByTime(lastExpectTime);
/*     */     
/* 174 */     if (!HIS_OPEN_CODES.containsKey(lastExpect)) {
/* 175 */       LotteryOpenCode lastExpectCode = this.lotteryOpenCodeService.get("txffc", lastExpect);
/* 176 */       if (lastExpectCode == null) {
/* 177 */         logger.warn("PCQQ官网抓取时没有获取到上期" + lastExpect + "的开奖数据，本次不处理，本期：" + currentExpect);
/* 178 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 182 */       HIS_OPEN_CODES.put(lastExpect, lastExpectCode.getCode());
/*     */     }
/*     */     
/*     */ 
/* 186 */     String lastExpectCode = (String)HIS_OPEN_CODES.get(lastExpect);
/* 187 */     if (StringUtils.isEmpty(lastExpectCode)) {
/* 188 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 192 */     int status = 0;
/* 193 */     if (lastExpectCode.equals(currentCode))
/*     */     {
/* 195 */       status = 2;
/*     */     }
/*     */     
/* 198 */     LotteryOpenCode lotteryOpenCode = new LotteryOpenCode("txffc", currentExpect, currentCode, new Moment().toSimpleTime(), Integer.valueOf(status), null, "PCQQ");
/* 199 */     lotteryOpenCode.setInterfaceTime(bean.getOnlinetime());
/*     */     
/* 201 */     boolean added = this.lotteryOpenCodeService.add(lotteryOpenCode, false);
/*     */     
/* 203 */     if (added) {
/* 204 */       logger.info("PCQQ官网成功抓取腾讯分分彩{}期开奖号码{}，上期{}开奖号码{}，是否自动撤单：{}", new Object[] { currentExpect, currentCode, lastExpect, lastExpectCode, status == 2 ? "是" : "否" });
/*     */       
/*     */ 
/* 207 */       if ("txffc".equals("txffc")) {
/* 208 */         LotteryOpenCode txlhdCode = new LotteryOpenCode("txlhd", lotteryOpenCode.getExpect(), lotteryOpenCode.getCode(), lotteryOpenCode.getTime(), lotteryOpenCode.getOpenStatus(), null, lotteryOpenCode.getRemarks());
/* 209 */         txlhdCode.setInterfaceTime(lotteryOpenCode.getInterfaceTime());
/* 210 */         this.lotteryOpenCodeService.add(txlhdCode, false);
/*     */       }
/*     */     }
/*     */     
/* 214 */     return added;
/*     */   }
/*     */   
/*     */ 
/*     */   public static Moment getTencentServerTime()
/*     */   {
/*     */     try
/*     */     {
/* 222 */       String url = "http://cgi.im.qq.com/cgi-bin/cgi_svrtime?_=" + System.currentTimeMillis();
/*     */       
/* 224 */       String data = getHttpResult(url);
/* 225 */       if (data != null) {
/* 226 */         return new Moment().fromTime(data);
/*     */       }
/*     */       
/* 229 */       return null;
/*     */     } catch (Exception e) {
/* 231 */       logger.error("获取PCQQ官网服务器时间出错", e); }
/* 232 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getOnlineNum()
/*     */   {
/*     */     try
/*     */     {
/* 241 */       String url = "https://mma.qq.com/cgi-bin/im/online?_=" + System.currentTimeMillis();
/*     */       
/* 243 */       String data = getHttpResult(url);
/* 244 */       if ((data != null) && (data.indexOf("online_resp") > -1)) {
/* 245 */         data = data.substring(12);
/* 246 */         return data.substring(0, data.length() - 1);
/*     */       }
/*     */       
/* 249 */       return null;
/*     */     } catch (Exception e) {
/* 251 */       logger.error("获取PCQQ官网在线人数出错", e); }
/* 252 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getHttpResult(String url)
/*     */   {
/*     */     try
/*     */     {
/* 262 */       Map<String, String> header = new HashMap();
/* 263 */       header.put("referer", "http://im.qq.com/pcqq/");
/* 264 */       header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
/*     */       
/* 266 */       return HttpClientUtil.post(url, null, header, 10000);
/*     */     }
/*     */     catch (Exception e) {
/* 269 */       logger.error("请求PCQQ官网出错", e); }
/* 270 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean checkData(PCQQBean bean)
/*     */   {
/* 276 */     if (bean == null) {
/* 277 */       logger.error("PCQQ官网数据非法，空数据");
/* 278 */       return false;
/*     */     }
/* 280 */     if ((StringUtils.isEmpty(bean.getOnlinetime())) || (bean.getOnlinenumber() <= 0)) {
/* 281 */       logger.error("PCQQ官网数据非法:" + JSON.toJSONString(bean));
/* 282 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 286 */     if (bean.getOnlinenumber() < 1000) {
/* 287 */       logger.error("PCQQ官网数据非法:" + JSON.toJSONString(bean));
/* 288 */       return false;
/*     */     }
/*     */     
/* 291 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String convertCode(int onlinenumber)
/*     */   {
/* 303 */     String[] chars = (onlinenumber + "").split("");
/* 304 */     int sum = 0;
/* 305 */     for (String aChar : chars) {
/* 306 */       if ((aChar != null) && (!"".equals(aChar))) {
/* 307 */         sum += Integer.valueOf(aChar).intValue();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 312 */     String wan = sum + "";
/* 313 */     wan = wan.substring(wan.length() - 1);
/*     */     
/* 315 */     String qian = chars[(chars.length - 4)] + "";
/* 316 */     String bai = chars[(chars.length - 3)] + "";
/* 317 */     String shi = chars[(chars.length - 2)] + "";
/* 318 */     String ge = chars[(chars.length - 1)] + "";
/*     */     
/* 320 */     return wan + "," + qian + "," + bai + "," + shi + "," + ge;
/*     */   }
/*     */   
/*     */   private static String getExpectByTime(String time) {
/* 324 */     Moment moment = new Moment().fromTime(time);
/* 325 */     int hour = moment.get("hour");
/* 326 */     int minute = moment.get("minute");
/*     */     
/* 328 */     if ((hour == 0) && (minute == 0))
/*     */     {
/* 330 */       moment = moment.add(-1, "minutes");
/* 331 */       hour = 24;
/*     */     }
/* 333 */     String date = moment.format("yyyyMMdd");
/* 334 */     int dayExpect = hour * 60 + minute;
/* 335 */     String expect = date + "-" + String.format("%04d", new Object[] { Integer.valueOf(dayExpect) });
/* 336 */     return expect;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 340 */     String time = "2017-09-18 00:01:59";
/* 341 */     String curExpect = getExpectByTime(time);
/* 342 */     System.out.println(curExpect);
/*     */     
/* 344 */     String lastExpectTime = new Moment().fromTime(time).subtract(1, "minutes").toSimpleTime();
/* 345 */     String lastExpect = getExpectByTime(lastExpectTime);
/*     */     
/* 347 */     System.out.println(lastExpect);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String convertCode2(int onlinenumber)
/*     */   {
/* 378 */     String[] chars = (onlinenumber + "").split("");
/* 379 */     int sum = 0;
/* 380 */     for (String aChar : chars) {
/* 381 */       if ((aChar != null) && (!"".equals(aChar))) {
/* 382 */         sum += Integer.valueOf(aChar).intValue();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 387 */     String wan = sum + "";
/* 388 */     wan = wan.substring(wan.length() - 1);
/*     */     
/* 390 */     String qian = chars[(chars.length - 4)] + "";
/* 391 */     String bai = chars[(chars.length - 3)] + "";
/* 392 */     String shi = chars[(chars.length - 2)] + "";
/* 393 */     String ge = chars[(chars.length - 1)] + "";
/*     */     
/* 395 */     return wan + "," + qian + "," + bai + "," + shi + "," + ge;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/jobs/PCQQJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */