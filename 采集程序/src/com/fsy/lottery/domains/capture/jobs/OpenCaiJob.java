///*     */ package com.fsy.lottery.domains.capture.jobs;
///*     */
///*     */ import java.util.ArrayList;
///*     */ import java.util.Collections;
///*     */ import java.util.HashMap;
///*     */ import java.util.Iterator;
///*     */ import java.util.List;
///*     */ import java.util.Map;
///*     */ import com.fsy.javautils.StringUtil;
///*     */ import com.fsy.javautils.http.HttpClientUtil;
///*     */ import com.fsy.lottery.domains.capture.sites.opencai.OpenCaiBean;
///*     */ import com.fsy.lottery.domains.content.biz.LotteryOpenCodeService;
///*     */ import net.sf.json.JSONArray;
///*     */ import net.sf.json.JSONObject;
///*     */ import org.apache.commons.lang.StringUtils;
///*     */ import org.slf4j.Logger;
///*     */ import org.slf4j.LoggerFactory;
///*     */ import org.springframework.beans.factory.annotation.Autowired;
///*     */ import org.springframework.stereotype.Component;
///*     */
///*     */
///*     */
///*     */
///*     */ @Component
///*     */ public class OpenCaiJob
///*     */ {
///*  27 */   private static final Logger logger = LoggerFactory.getLogger(OpenCaiJob.class);
///*     */
///*     */   private static final String SUB_URL = "/newly.do?token=tc5b7f6b9131346d3k&rows=5&format=json";
///*  30 */   private static final String[] SITES = { "http://d.apiplus.net", "http://z.apiplus.net" };
///*     */
///*     */
///*     */
///*     */   @Autowired
///*     */   private LotteryOpenCodeService lotteryOpenCodeService;
///*     */
///*     */
///*  38 */   private static boolean isRuning = false;
///*     */
///*     */   /* Error */
///*     */   @org.springframework.scheduling.annotation.Scheduled(cron="0/5 * * * * *")
///*     */   public void execute()
///*     */   {
///*     */     // Byte code:
///*     */     //   0: ldc 1
///*     */     //   2: dup
///*     */     //   3: astore_1
///*     */     //   4: monitorenter
///*     */     //   5: getstatic 39	lottery/domains/capture/jobs/OpenCaiJob:isRuning	Z
///*     */     //   8: ifeq +6 -> 14
///*     */     //   11: aload_1
///*     */     //   12: monitorexit
///*     */     //   13: return
///*     */     //   14: iconst_1
///*     */     //   15: putstatic 39	lottery/domains/capture/jobs/OpenCaiJob:isRuning	Z
///*     */     //   18: aload_1
///*     */     //   19: monitorexit
///*     */     //   20: goto +6 -> 26
///*     */     //   23: aload_1
///*     */     //   24: monitorexit
///*     */     //   25: athrow
///*     */     //   26: aload_0
///*     */     //   27: invokevirtual 52	lottery/domains/capture/jobs/OpenCaiJob:start	()V
///*     */     //   30: goto +29 -> 59
///*     */     //   33: astore_1
///*     */     //   34: getstatic 29	lottery/domains/capture/jobs/OpenCaiJob:logger	Lorg/slf4j/Logger;
///*     */     //   37: ldc 55
///*     */     //   39: aload_1
///*     */     //   40: invokeinterface 57 3 0
///*     */     //   45: iconst_0
///*     */     //   46: putstatic 39	lottery/domains/capture/jobs/OpenCaiJob:isRuning	Z
///*     */     //   49: goto +14 -> 63
///*     */     //   52: astore_2
///*     */     //   53: iconst_0
///*     */     //   54: putstatic 39	lottery/domains/capture/jobs/OpenCaiJob:isRuning	Z
///*     */     //   57: aload_2
///*     */     //   58: athrow
///*     */     //   59: iconst_0
///*     */     //   60: putstatic 39	lottery/domains/capture/jobs/OpenCaiJob:isRuning	Z
///*     */     //   63: return
///*     */     // Line number table:
///*     */     //   Java source line #44	-> byte code offset #0
///*     */     //   Java source line #45	-> byte code offset #5
///*     */     //   Java source line #46	-> byte code offset #11
///*     */     //   Java source line #48	-> byte code offset #14
///*     */     //   Java source line #44	-> byte code offset #18
///*     */     //   Java source line #52	-> byte code offset #26
///*     */     //   Java source line #53	-> byte code offset #30
///*     */     //   Java source line #54	-> byte code offset #34
///*     */     //   Java source line #56	-> byte code offset #45
///*     */     //   Java source line #55	-> byte code offset #52
///*     */     //   Java source line #56	-> byte code offset #53
///*     */     //   Java source line #57	-> byte code offset #57
///*     */     //   Java source line #56	-> byte code offset #59
///*     */     //   Java source line #58	-> byte code offset #63
///*     */     // Local variable table:
///*     */     //   start	length	slot	name	signature
///*     */     //   0	64	0	this	OpenCaiJob
///*     */     //   3	21	1	Ljava/lang/Object;	Object
///*     */     //   33	7	1	e	Exception
///*     */     //   52	6	2	localObject1	Object
///*     */     // Exception table:
///*     */     //   from	to	target	type
///*     */     //   5	13	23	finally
///*     */     //   14	20	23	finally
///*     */     //   23	25	23	finally
///*     */     //   26	30	33	java/lang/Exception
///*     */     //   26	45	52	finally
///*     */   }
///*     */
///*     */   public void start()
///*     */   {
///*  61 */     for (int i = 0; i < SITES.length; i++) {
///*  62 */       String site = SITES[i];
///*  63 */       logger.debug("开始抓取OpenCai[{}]开奖数据", site);
///*  64 */       long start = System.currentTimeMillis();
///*     */
///*  66 */       String url = site + "/newly.do?token=tc5b7f6b9131346d3k&rows=5&format=json" + "&_=" + System.currentTimeMillis();
///*     */
///*  68 */       String result = getHttpResult(url);
///*     */
///*  70 */       boolean succeed = false;
///*  71 */       if (StringUtils.isNotEmpty(result)) {
///*  72 */         succeed = handleData(result, site);
///*     */       }
///*     */
///*  75 */       long spend = System.currentTimeMillis() - start;
///*  76 */       if (succeed) {
///*  77 */         logger.debug("成功抓取OpenCai[{}]开奖数据，并处理成功，耗时{}", site, Long.valueOf(spend));
///*  78 */         break;
///*     */       }
///*     */
///*  81 */       logger.warn("完成抓取OpenCai[{}]开奖数据，但处理失败，耗时{}", site, Long.valueOf(spend));
///*     */     }
///*     */   }
///*     */
///*     */   private boolean handleData(String data, String site)
///*     */   {
///*     */     try {
///*  88 */       if (StringUtil.isNotNull(data)) {
///*  89 */         Object object = JSONObject.fromObject(data).get("data");
///*  90 */         JSONArray array = JSONArray.fromObject(object);
///*  91 */         List<OpenCaiBean> list = new ArrayList();
///*  92 */         JSONObject jsonObject; for (Iterator iter = array.iterator(); iter.hasNext();) {
///*  93 */           jsonObject = (JSONObject)iter.next();
///*  94 */           OpenCaiBean bean = (OpenCaiBean)JSONObject.toBean(jsonObject,
///*  95 */             OpenCaiBean.class);
///*  96 */           list.add(bean);
///*     */         }
///*  98 */         Collections.reverse(list);
///*  99 */         for (OpenCaiBean bean : list) {
///* 100 */           handleOpenCaiBean(bean, site);
///*     */         }
///*     */
///* 103 */         return true;
///*     */       }
///* 105 */       return false;
///*     */     } catch (Exception e) {
///* 107 */       logger.error("解析OpenCai数据出错：" + data + "，URL：" + site, e); }
///* 108 */     return false;
///*     */   }
///*     */
///*     */   /* Error */
///*     */   private boolean handleOpenCaiBean(OpenCaiBean bean, String site)
///*     */   {
//    return false;
///*     */     // Byte code:
///*     */     //   0: aconst_null
///*     */     //   1: astore_3
///*     */     //   2: aload_1
///*     */     //   3: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   6: dup
///*     */     //   7: astore 5
///*     */     //   9: invokevirtual 226	java/lang/String:hashCode	()I
///*     */     //   12: lookupswitch	default:+1153->1165, -1420382844:+212->224, -1387976286:+225->237, -1367811591:+238->250, -1252302022:+251->263, -1147944149:+264->276, -923144308:+277->289, -908752210:+290->302, -905058126:+303->315, 111031:+316->328, 111033:+329->341, 2993039:+342->354, 3135502:+355->367, 3187161:+368->380, 3265002:+382->394, 3271729:+396->408, 3276534:+410->422, 3529277:+424->436, 93769135:+438->450, 94909141:+452->464, 99152621:+466->478, 99629277:+480->492, 101582325:+494->506, 109417367:+508->520, 110400461:+522->534, 114094545:+536->548
///*     */     //   224: aload 5
///*     */     //   226: ldc -26
///*     */     //   228: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   231: ifne +405 -> 636
///*     */     //   234: goto +931 -> 1165
///*     */     //   237: aload 5
///*     */     //   239: ldc -21
///*     */     //   241: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   244: ifne +663 -> 907
///*     */     //   247: goto +918 -> 1165
///*     */     //   250: aload 5
///*     */     //   252: ldc -19
///*     */     //   254: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   257: ifne +743 -> 1000
///*     */     //   260: goto +905 -> 1165
///*     */     //   263: aload 5
///*     */     //   265: ldc -17
///*     */     //   267: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   270: ifne +366 -> 636
///*     */     //   273: goto +892 -> 1165
///*     */     //   276: aload 5
///*     */     //   278: ldc -15
///*     */     //   280: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   283: ifne +353 -> 636
///*     */     //   286: goto +879 -> 1165
///*     */     //   289: aload 5
///*     */     //   291: ldc -13
///*     */     //   293: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   296: ifne +646 -> 942
///*     */     //   299: goto +866 -> 1165
///*     */     //   302: aload 5
///*     */     //   304: ldc -11
///*     */     //   306: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   309: ifne +327 -> 636
///*     */     //   312: goto +853 -> 1165
///*     */     //   315: aload 5
///*     */     //   317: ldc -9
///*     */     //   319: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   322: ifne +314 -> 636
///*     */     //   325: goto +840 -> 1165
///*     */     //   328: aload 5
///*     */     //   330: ldc -7
///*     */     //   332: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   335: ifne +375 -> 710
///*     */     //   338: goto +827 -> 1165
///*     */     //   341: aload 5
///*     */     //   343: ldc -5
///*     */     //   345: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   348: ifne +782 -> 1130
///*     */     //   351: goto +814 -> 1165
///*     */     //   354: aload 5
///*     */     //   356: ldc -3
///*     */     //   358: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   361: ifne +394 -> 755
///*     */     //   364: goto +801 -> 1165
///*     */     //   367: aload 5
///*     */     //   369: ldc -1
///*     */     //   371: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   374: ifne +336 -> 710
///*     */     //   377: goto +788 -> 1165
///*     */     //   380: aload 5
///*     */     //   382: ldc_w 257
///*     */     //   385: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   388: ifne +367 -> 755
///*     */     //   391: goto +774 -> 1165
///*     */     //   394: aload 5
///*     */     //   396: ldc_w 259
///*     */     //   399: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   402: ifne +353 -> 755
///*     */     //   405: goto +760 -> 1165
///*     */     //   408: aload 5
///*     */     //   410: ldc_w 261
///*     */     //   413: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   416: ifne +339 -> 755
///*     */     //   419: goto +746 -> 1165
///*     */     //   422: aload 5
///*     */     //   424: ldc_w 263
///*     */     //   427: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   430: ifne +325 -> 755
///*     */     //   433: goto +732 -> 1165
///*     */     //   436: aload 5
///*     */     //   438: ldc_w 265
///*     */     //   441: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   444: ifne +311 -> 755
///*     */     //   447: goto +718 -> 1165
///*     */     //   450: aload 5
///*     */     //   452: ldc_w 267
///*     */     //   455: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   458: ifne +391 -> 849
///*     */     //   461: goto +704 -> 1165
///*     */     //   464: aload 5
///*     */     //   466: ldc_w 269
///*     */     //   469: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   472: ifne +90 -> 562
///*     */     //   475: goto +690 -> 1165
///*     */     //   478: aload 5
///*     */     //   480: ldc_w 271
///*     */     //   483: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   486: ifne +269 -> 755
///*     */     //   489: goto +676 -> 1165
///*     */     //   492: aload 5
///*     */     //   494: ldc_w 273
///*     */     //   497: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   500: ifne +255 -> 755
///*     */     //   503: goto +662 -> 1165
///*     */     //   506: aload 5
///*     */     //   508: ldc_w 275
///*     */     //   511: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   514: ifne +48 -> 562
///*     */     //   517: goto +648 -> 1165
///*     */     //   520: aload 5
///*     */     //   522: ldc_w 277
///*     */     //   525: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   528: ifne +528 -> 1056
///*     */     //   531: goto +634 -> 1165
///*     */     //   534: aload 5
///*     */     //   536: ldc_w 279
///*     */     //   539: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   542: ifne +20 -> 562
///*     */     //   545: goto +620 -> 1165
///*     */     //   548: aload 5
///*     */     //   550: ldc_w 281
///*     */     //   553: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   556: ifne +6 -> 562
///*     */     //   559: goto +606 -> 1165
///*     */     //   562: aload_1
///*     */     //   563: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   566: astore 4
///*     */     //   568: new 84	java/lang/StringBuilder
///*     */     //   571: dup
///*     */     //   572: aload 4
///*     */     //   574: iconst_0
///*     */     //   575: bipush 8
///*     */     //   577: invokevirtual 286	java/lang/String:substring	(II)Ljava/lang/String;
///*     */     //   580: invokestatic 86	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
///*     */     //   583: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
///*     */     //   586: ldc_w 290
///*     */     //   589: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   592: aload 4
///*     */     //   594: bipush 8
///*     */     //   596: invokevirtual 292	java/lang/String:substring	(I)Ljava/lang/String;
///*     */     //   599: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   602: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
///*     */     //   605: astore 4
///*     */     //   607: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   610: dup
///*     */     //   611: aload_1
///*     */     //   612: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   615: aload 4
///*     */     //   617: aload_1
///*     */     //   618: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   621: aload_1
///*     */     //   622: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   625: iconst_0
///*     */     //   626: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   629: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   632: astore_3
///*     */     //   633: goto +532 -> 1165
///*     */     //   636: aload_1
///*     */     //   637: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   640: astore 4
///*     */     //   642: new 84	java/lang/StringBuilder
///*     */     //   645: dup
///*     */     //   646: aload 4
///*     */     //   648: iconst_0
///*     */     //   649: bipush 8
///*     */     //   651: invokevirtual 286	java/lang/String:substring	(II)Ljava/lang/String;
///*     */     //   654: invokestatic 86	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
///*     */     //   657: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
///*     */     //   660: ldc_w 311
///*     */     //   663: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   666: aload 4
///*     */     //   668: bipush 8
///*     */     //   670: invokevirtual 292	java/lang/String:substring	(I)Ljava/lang/String;
///*     */     //   673: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   676: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
///*     */     //   679: astore 4
///*     */     //   681: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   684: dup
///*     */     //   685: aload_1
///*     */     //   686: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   689: aload 4
///*     */     //   691: aload_1
///*     */     //   692: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   695: aload_1
///*     */     //   696: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   699: iconst_0
///*     */     //   700: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   703: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   706: astore_3
///*     */     //   707: goto +458 -> 1165
///*     */     //   710: aload_1
///*     */     //   711: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   714: astore 4
///*     */     //   716: aload 4
///*     */     //   718: iconst_2
///*     */     //   719: bipush 7
///*     */     //   721: invokevirtual 286	java/lang/String:substring	(II)Ljava/lang/String;
///*     */     //   724: astore 4
///*     */     //   726: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   729: dup
///*     */     //   730: aload_1
///*     */     //   731: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   734: aload 4
///*     */     //   736: aload_1
///*     */     //   737: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   740: aload_1
///*     */     //   741: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   744: iconst_0
///*     */     //   745: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   748: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   751: astore_3
///*     */     //   752: goto +413 -> 1165
///*     */     //   755: aload_1
///*     */     //   756: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   759: ldc_w 273
///*     */     //   762: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   765: ifeq +10 -> 775
///*     */     //   768: aload_1
///*     */     //   769: ldc_w 313
///*     */     //   772: invokevirtual 315	lottery/domains/capture/sites/opencai/OpenCaiBean:setCode	(Ljava/lang/String;)V
///*     */     //   775: aload_1
///*     */     //   776: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   779: astore 4
///*     */     //   781: new 84	java/lang/StringBuilder
///*     */     //   784: dup
///*     */     //   785: aload 4
///*     */     //   787: iconst_0
///*     */     //   788: bipush 8
///*     */     //   790: invokevirtual 286	java/lang/String:substring	(II)Ljava/lang/String;
///*     */     //   793: invokestatic 86	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
///*     */     //   796: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
///*     */     //   799: ldc_w 290
///*     */     //   802: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   805: aload 4
///*     */     //   807: bipush 8
///*     */     //   809: invokevirtual 292	java/lang/String:substring	(I)Ljava/lang/String;
///*     */     //   812: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   815: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
///*     */     //   818: astore 4
///*     */     //   820: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   823: dup
///*     */     //   824: aload_1
///*     */     //   825: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   828: aload 4
///*     */     //   830: aload_1
///*     */     //   831: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   834: aload_1
///*     */     //   835: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   838: iconst_0
///*     */     //   839: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   842: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   845: astore_3
///*     */     //   846: goto +319 -> 1165
///*     */     //   849: aload_1
///*     */     //   850: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   853: ldc_w 318
///*     */     //   856: invokevirtual 320	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
///*     */     //   859: iconst_0
///*     */     //   860: aaload
///*     */     //   861: astore 6
///*     */     //   863: aload_0
///*     */     //   864: aload 6
///*     */     //   866: invokespecial 324	lottery/domains/capture/jobs/OpenCaiJob:convertFFCCode	(Ljava/lang/String;)Ljava/lang/String;
///*     */     //   869: astore 7
///*     */     //   871: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   874: dup
///*     */     //   875: aload_1
///*     */     //   876: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   879: aload_1
///*     */     //   880: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   883: aload 7
///*     */     //   885: aload_1
///*     */     //   886: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   889: iconst_0
///*     */     //   890: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   893: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   896: astore_3
///*     */     //   897: aload_3
///*     */     //   898: ldc_w 327
///*     */     //   901: invokevirtual 329	lottery/domains/content/entity/LotteryOpenCode:setLottery	(Ljava/lang/String;)V
///*     */     //   904: goto +261 -> 1165
///*     */     //   907: aload_1
///*     */     //   908: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   911: astore 4
///*     */     //   913: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   916: dup
///*     */     //   917: aload_1
///*     */     //   918: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   921: aload 4
///*     */     //   923: aload_1
///*     */     //   924: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   927: aload_1
///*     */     //   928: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   931: iconst_0
///*     */     //   932: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   935: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   938: astore_3
///*     */     //   939: goto +226 -> 1165
///*     */     //   942: aload_1
///*     */     //   943: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   946: ldc_w 318
///*     */     //   949: invokevirtual 320	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
///*     */     //   952: iconst_0
///*     */     //   953: aaload
///*     */     //   954: astore 6
///*     */     //   956: aload_0
///*     */     //   957: aload 6
///*     */     //   959: invokespecial 324	lottery/domains/capture/jobs/OpenCaiJob:convertFFCCode	(Ljava/lang/String;)Ljava/lang/String;
///*     */     //   962: astore 8
///*     */     //   964: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   967: dup
///*     */     //   968: aload_1
///*     */     //   969: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   972: aload_1
///*     */     //   973: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   976: aload 8
///*     */     //   978: aload_1
///*     */     //   979: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   982: iconst_0
///*     */     //   983: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   986: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   989: astore_3
///*     */     //   990: aload_3
///*     */     //   991: ldc_w 332
///*     */     //   994: invokevirtual 329	lottery/domains/content/entity/LotteryOpenCode:setLottery	(Ljava/lang/String;)V
///*     */     //   997: goto +168 -> 1165
///*     */     //   1000: aload_1
///*     */     //   1001: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   1004: iconst_0
///*     */     //   1005: bipush 59
///*     */     //   1007: invokevirtual 286	java/lang/String:substring	(II)Ljava/lang/String;
///*     */     //   1010: astore 6
///*     */     //   1012: aload_0
///*     */     //   1013: aload 6
///*     */     //   1015: invokespecial 324	lottery/domains/capture/jobs/OpenCaiJob:convertFFCCode	(Ljava/lang/String;)Ljava/lang/String;
///*     */     //   1018: astore 9
///*     */     //   1020: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   1023: dup
///*     */     //   1024: aload_1
///*     */     //   1025: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   1028: aload_1
///*     */     //   1029: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   1032: aload 9
///*     */     //   1034: aload_1
///*     */     //   1035: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   1038: iconst_0
///*     */     //   1039: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   1042: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   1045: astore_3
///*     */     //   1046: aload_3
///*     */     //   1047: ldc_w 334
///*     */     //   1050: invokevirtual 329	lottery/domains/content/entity/LotteryOpenCode:setLottery	(Ljava/lang/String;)V
///*     */     //   1053: goto +112 -> 1165
///*     */     //   1056: aload_1
///*     */     //   1057: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   1060: astore 4
///*     */     //   1062: new 84	java/lang/StringBuilder
///*     */     //   1065: dup
///*     */     //   1066: aload 4
///*     */     //   1068: iconst_0
///*     */     //   1069: bipush 8
///*     */     //   1071: invokevirtual 286	java/lang/String:substring	(II)Ljava/lang/String;
///*     */     //   1074: invokestatic 86	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
///*     */     //   1077: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
///*     */     //   1080: ldc_w 290
///*     */     //   1083: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1086: aload 4
///*     */     //   1088: bipush 8
///*     */     //   1090: invokevirtual 292	java/lang/String:substring	(I)Ljava/lang/String;
///*     */     //   1093: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1096: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
///*     */     //   1099: astore 4
///*     */     //   1101: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   1104: dup
///*     */     //   1105: aload_1
///*     */     //   1106: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   1109: aload 4
///*     */     //   1111: aload_1
///*     */     //   1112: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   1115: aload_1
///*     */     //   1116: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   1119: iconst_0
///*     */     //   1120: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   1123: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   1126: astore_3
///*     */     //   1127: goto +38 -> 1165
///*     */     //   1130: aload_1
///*     */     //   1131: invokevirtual 283	lottery/domains/capture/sites/opencai/OpenCaiBean:getExpect	()Ljava/lang/String;
///*     */     //   1134: astore 4
///*     */     //   1136: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   1139: dup
///*     */     //   1140: aload_1
///*     */     //   1141: invokevirtual 223	lottery/domains/capture/sites/opencai/OpenCaiBean:getCode	()Ljava/lang/String;
///*     */     //   1144: aload 4
///*     */     //   1146: aload_1
///*     */     //   1147: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   1150: aload_1
///*     */     //   1151: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   1154: iconst_0
///*     */     //   1155: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   1158: invokespecial 308	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V
///*     */     //   1161: astore_3
///*     */     //   1162: goto +3 -> 1165
///*     */     //   1165: aload_3
///*     */     //   1166: ifnonnull +5 -> 1171
///*     */     //   1169: iconst_0
///*     */     //   1170: ireturn
///*     */     //   1171: aload_3
///*     */     //   1172: invokevirtual 336	lottery/domains/content/entity/LotteryOpenCode:getLottery	()Ljava/lang/String;
///*     */     //   1175: aload_3
///*     */     //   1176: invokevirtual 339	lottery/domains/content/entity/LotteryOpenCode:getCode	()Ljava/lang/String;
///*     */     //   1179: invokestatic 340	lottery/domains/capture/utils/CodeValidate:validate	(Ljava/lang/String;Ljava/lang/String;)Z
///*     */     //   1182: ifne +52 -> 1234
///*     */     //   1185: getstatic 29	lottery/domains/capture/jobs/OpenCaiJob:logger	Lorg/slf4j/Logger;
///*     */     //   1188: new 84	java/lang/StringBuilder
///*     */     //   1191: dup
///*     */     //   1192: ldc_w 345
///*     */     //   1195: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
///*     */     //   1198: aload_3
///*     */     //   1199: invokevirtual 336	lottery/domains/content/entity/LotteryOpenCode:getLottery	()Ljava/lang/String;
///*     */     //   1202: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1205: ldc_w 347
///*     */     //   1208: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1211: aload_3
///*     */     //   1212: invokevirtual 339	lottery/domains/content/entity/LotteryOpenCode:getCode	()Ljava/lang/String;
///*     */     //   1215: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1218: ldc_w 349
///*     */     //   1221: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1224: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
///*     */     //   1227: invokeinterface 351 2 0
///*     */     //   1232: iconst_0
///*     */     //   1233: ireturn
///*     */     //   1234: aload_3
///*     */     //   1235: invokevirtual 336	lottery/domains/content/entity/LotteryOpenCode:getLottery	()Ljava/lang/String;
///*     */     //   1238: aload_3
///*     */     //   1239: invokevirtual 353	lottery/domains/content/entity/LotteryOpenCode:getExpect	()Ljava/lang/String;
///*     */     //   1242: invokestatic 354	lottery/domains/capture/utils/ExpectValidate:validate	(Ljava/lang/String;Ljava/lang/String;)Z
///*     */     //   1245: ifne +52 -> 1297
///*     */     //   1248: getstatic 29	lottery/domains/capture/jobs/OpenCaiJob:logger	Lorg/slf4j/Logger;
///*     */     //   1251: new 84	java/lang/StringBuilder
///*     */     //   1254: dup
///*     */     //   1255: ldc_w 345
///*     */     //   1258: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
///*     */     //   1261: aload_3
///*     */     //   1262: invokevirtual 336	lottery/domains/content/entity/LotteryOpenCode:getLottery	()Ljava/lang/String;
///*     */     //   1265: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1268: ldc_w 357
///*     */     //   1271: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1274: aload_3
///*     */     //   1275: invokevirtual 353	lottery/domains/content/entity/LotteryOpenCode:getExpect	()Ljava/lang/String;
///*     */     //   1278: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1281: ldc_w 349
///*     */     //   1284: invokevirtual 93	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
///*     */     //   1287: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
///*     */     //   1290: invokeinterface 351 2 0
///*     */     //   1295: iconst_0
///*     */     //   1296: ireturn
///*     */     //   1297: aload_3
///*     */     //   1298: aload_2
///*     */     //   1299: invokevirtual 359	lottery/domains/content/entity/LotteryOpenCode:setRemarks	(Ljava/lang/String;)V
///*     */     //   1302: aload_3
///*     */     //   1303: new 362	javautils/date/Moment
///*     */     //   1306: dup
///*     */     //   1307: invokespecial 364	javautils/date/Moment:<init>	()V
///*     */     //   1310: invokevirtual 365	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
///*     */     //   1313: invokevirtual 368	lottery/domains/content/entity/LotteryOpenCode:setTime	(Ljava/lang/String;)V
///*     */     //   1316: aload_1
///*     */     //   1317: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   1320: invokestatic 110	org/apache/commons/lang/StringUtils:isNotEmpty	(Ljava/lang/String;)Z
///*     */     //   1323: ifeq +14 -> 1337
///*     */     //   1326: aload_3
///*     */     //   1327: aload_1
///*     */     //   1328: invokevirtual 300	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpentime	()Ljava/lang/String;
///*     */     //   1331: invokevirtual 371	lottery/domains/content/entity/LotteryOpenCode:setInterfaceTime	(Ljava/lang/String;)V
///*     */     //   1334: goto +17 -> 1351
///*     */     //   1337: aload_3
///*     */     //   1338: new 362	javautils/date/Moment
///*     */     //   1341: dup
///*     */     //   1342: invokespecial 364	javautils/date/Moment:<init>	()V
///*     */     //   1345: invokevirtual 365	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
///*     */     //   1348: invokevirtual 371	lottery/domains/content/entity/LotteryOpenCode:setInterfaceTime	(Ljava/lang/String;)V
///*     */     //   1351: aload_0
///*     */     //   1352: getfield 374	lottery/domains/capture/jobs/OpenCaiJob:lotteryOpenCodeService	Llottery/domains/content/biz/LotteryOpenCodeService;
///*     */     //   1355: aload_3
///*     */     //   1356: iconst_0
///*     */     //   1357: invokeinterface 376 3 0
///*     */     //   1362: istore 6
///*     */     //   1364: iload 6
///*     */     //   1366: ifeq +82 -> 1448
///*     */     //   1369: ldc_w 327
///*     */     //   1372: aload_3
///*     */     //   1373: invokevirtual 336	lottery/domains/content/entity/LotteryOpenCode:getLottery	()Ljava/lang/String;
///*     */     //   1376: invokevirtual 232	java/lang/String:equals	(Ljava/lang/Object;)Z
///*     */     //   1379: ifeq +69 -> 1448
///*     */     //   1382: new 295	lottery/domains/content/entity/LotteryOpenCode
///*     */     //   1385: dup
///*     */     //   1386: ldc_w 267
///*     */     //   1389: aload_3
///*     */     //   1390: invokevirtual 353	lottery/domains/content/entity/LotteryOpenCode:getExpect	()Ljava/lang/String;
///*     */     //   1393: aload_1
///*     */     //   1394: invokevirtual 297	lottery/domains/capture/sites/opencai/OpenCaiBean:getOpencode	()Ljava/lang/String;
///*     */     //   1397: ldc_w 318
///*     */     //   1400: invokevirtual 320	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
///*     */     //   1403: iconst_0
///*     */     //   1404: aaload
///*     */     //   1405: new 362	javautils/date/Moment
///*     */     //   1408: dup
///*     */     //   1409: invokespecial 364	javautils/date/Moment:<init>	()V
///*     */     //   1412: invokevirtual 365	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
///*     */     //   1415: iconst_0
///*     */     //   1416: invokestatic 303	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
///*     */     //   1419: aconst_null
///*     */     //   1420: aload_2
///*     */     //   1421: invokespecial 381	lottery/domains/content/entity/LotteryOpenCode:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;)V
///*     */     //   1424: astore 7
///*     */     //   1426: aload 7
///*     */     //   1428: aload_3
///*     */     //   1429: invokevirtual 384	lottery/domains/content/entity/LotteryOpenCode:getInterfaceTime	()Ljava/lang/String;
///*     */     //   1432: invokevirtual 371	lottery/domains/content/entity/LotteryOpenCode:setInterfaceTime	(Ljava/lang/String;)V
///*     */     //   1435: aload_0
///*     */     //   1436: getfield 374	lottery/domains/capture/jobs/OpenCaiJob:lotteryOpenCodeService	Llottery/domains/content/biz/LotteryOpenCodeService;
///*     */     //   1439: aload 7
///*     */     //   1441: iconst_0
///*     */     //   1442: invokeinterface 376 3 0
///*     */     //   1447: pop
///*     */     //   1448: iload 6
///*     */     //   1450: ireturn
///*     */     // Line number table:
///*     */     //   Java source line #113	-> byte code offset #0
///*     */     //   Java source line #115	-> byte code offset #2
///*     */     //   Java source line #121	-> byte code offset #562
///*     */     //   Java source line #122	-> byte code offset #568
///*     */     //   Java source line #123	-> byte code offset #607
///*     */     //   Java source line #124	-> byte code offset #617
///*     */     //   Java source line #123	-> byte code offset #629
///*     */     //   Java source line #125	-> byte code offset #633
///*     */     //   Java source line #132	-> byte code offset #636
///*     */     //   Java source line #133	-> byte code offset #642
///*     */     //   Java source line #134	-> byte code offset #681
///*     */     //   Java source line #135	-> byte code offset #691
///*     */     //   Java source line #134	-> byte code offset #703
///*     */     //   Java source line #136	-> byte code offset #707
///*     */     //   Java source line #140	-> byte code offset #710
///*     */     //   Java source line #141	-> byte code offset #716
///*     */     //   Java source line #142	-> byte code offset #726
///*     */     //   Java source line #143	-> byte code offset #736
///*     */     //   Java source line #142	-> byte code offset #748
///*     */     //   Java source line #144	-> byte code offset #752
///*     */     //   Java source line #154	-> byte code offset #755
///*     */     //   Java source line #155	-> byte code offset #768
///*     */     //   Java source line #157	-> byte code offset #775
///*     */     //   Java source line #158	-> byte code offset #781
///*     */     //   Java source line #159	-> byte code offset #820
///*     */     //   Java source line #160	-> byte code offset #830
///*     */     //   Java source line #159	-> byte code offset #842
///*     */     //   Java source line #161	-> byte code offset #846
///*     */     //   Java source line #163	-> byte code offset #849
///*     */     //   Java source line #164	-> byte code offset #863
///*     */     //   Java source line #165	-> byte code offset #871
///*     */     //   Java source line #166	-> byte code offset #883
///*     */     //   Java source line #165	-> byte code offset #893
///*     */     //   Java source line #167	-> byte code offset #897
///*     */     //   Java source line #168	-> byte code offset #904
///*     */     //   Java source line #170	-> byte code offset #907
///*     */     //   Java source line #171	-> byte code offset #913
///*     */     //   Java source line #172	-> byte code offset #923
///*     */     //   Java source line #171	-> byte code offset #935
///*     */     //   Java source line #173	-> byte code offset #939
///*     */     //   Java source line #194	-> byte code offset #942
///*     */     //   Java source line #195	-> byte code offset #956
///*     */     //   Java source line #196	-> byte code offset #964
///*     */     //   Java source line #197	-> byte code offset #976
///*     */     //   Java source line #196	-> byte code offset #986
///*     */     //   Java source line #198	-> byte code offset #990
///*     */     //   Java source line #199	-> byte code offset #997
///*     */     //   Java source line #202	-> byte code offset #1000
///*     */     //   Java source line #203	-> byte code offset #1012
///*     */     //   Java source line #204	-> byte code offset #1020
///*     */     //   Java source line #205	-> byte code offset #1032
///*     */     //   Java source line #204	-> byte code offset #1042
///*     */     //   Java source line #206	-> byte code offset #1046
///*     */     //   Java source line #207	-> byte code offset #1053
///*     */     //   Java source line #216	-> byte code offset #1056
///*     */     //   Java source line #217	-> byte code offset #1062
///*     */     //   Java source line #218	-> byte code offset #1101
///*     */     //   Java source line #219	-> byte code offset #1111
///*     */     //   Java source line #218	-> byte code offset #1123
///*     */     //   Java source line #220	-> byte code offset #1127
///*     */     //   Java source line #222	-> byte code offset #1130
///*     */     //   Java source line #223	-> byte code offset #1136
///*     */     //   Java source line #224	-> byte code offset #1146
///*     */     //   Java source line #223	-> byte code offset #1158
///*     */     //   Java source line #225	-> byte code offset #1162
///*     */     //   Java source line #230	-> byte code offset #1165
///*     */     //   Java source line #231	-> byte code offset #1169
///*     */     //   Java source line #234	-> byte code offset #1171
///*     */     //   Java source line #235	-> byte code offset #1185
///*     */     //   Java source line #236	-> byte code offset #1232
///*     */     //   Java source line #239	-> byte code offset #1234
///*     */     //   Java source line #240	-> byte code offset #1248
///*     */     //   Java source line #241	-> byte code offset #1295
///*     */     //   Java source line #244	-> byte code offset #1297
///*     */     //   Java source line #245	-> byte code offset #1302
///*     */     //   Java source line #246	-> byte code offset #1316
///*     */     //   Java source line #247	-> byte code offset #1326
///*     */     //   Java source line #248	-> byte code offset #1334
///*     */     //   Java source line #250	-> byte code offset #1337
///*     */     //   Java source line #253	-> byte code offset #1351
///*     */     //   Java source line #254	-> byte code offset #1364
///*     */     //   Java source line #256	-> byte code offset #1369
///*     */     //   Java source line #257	-> byte code offset #1382
///*     */     //   Java source line #258	-> byte code offset #1426
///*     */     //   Java source line #259	-> byte code offset #1435
///*     */     //   Java source line #262	-> byte code offset #1448
///*     */     // Local variable table:
///*     */     //   start	length	slot	name	signature
///*     */     //   0	1451	0	this	OpenCaiJob
///*     */     //   0	1451	1	bean	OpenCaiBean
///*     */     //   0	1451	2	site	String
///*     */     //   1	1428	3	lotteryOpenCode	lottery.domains.content.entity.LotteryOpenCode
///*     */     //   566	50	4	expect	String
///*     */     //   640	50	4	expect	String
///*     */     //   714	21	4	expect	String
///*     */     //   779	50	4	expect	String
///*     */     //   911	11	4	expect	String
///*     */     //   1060	50	4	expect	String
///*     */     //   1134	11	4	expect	String
///*     */     //   7	542	5	str1	String
///*     */     //   861	4	6	openCode	String
///*     */     //   954	4	6	openCode	String
///*     */     //   1010	4	6	openCode	String
///*     */     //   1362	87	6	add	boolean
///*     */     //   869	15	7	bjkl8Code	String
///*     */     //   1424	16	7	bjkl8Code	lottery.domains.content.entity.LotteryOpenCode
///*     */     //   962	15	8	twbingoCode	String
///*     */     //   1018	15	9	cakenoCode	String
//
///*     */   }
///*     */
///*     */   public static String getHttpResult(String url)
///*     */   {
///*     */     try
///*     */     {
///* 273 */       Map<String, String> header = new HashMap();
///* 274 */       header.put("referer", "http://www.baidu.com/");
///* 275 */       header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
///*     */
///* 277 */       return HttpClientUtil.post(url, null, header, 10000);
///*     */     }
///*     */     catch (Exception e) {
///* 280 */       logger.error("请求OpenCai出错,URL：" + url, e); }
///* 281 */     return null;
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */   private String convertFFCCode(String openCode)
///*     */   {
///* 289 */     String[] codes = openCode.split(",");
///* 290 */     String code1 = mergeFFCCode(codes[0], codes[1], codes[2], codes[3]);
///* 291 */     String code2 = mergeFFCCode(codes[4], codes[5], codes[6], codes[7]);
///* 292 */     String code3 = mergeFFCCode(codes[8], codes[9], codes[10], codes[11]);
///* 293 */     String code4 = mergeFFCCode(codes[12], codes[13], codes[14], codes[15]);
///* 294 */     String code5 = mergeFFCCode(codes[16], codes[17], codes[18], codes[19]);
///*     */
///* 296 */     return code1 + "," + code2 + "," + code3 + "," + code4 + "," + code5;
///*     */   }
///*     */
///* 299 */   private String mergeFFCCode(String code1, String code2, String code3, String code4) { int codeInt1 = Integer.valueOf(code1).intValue();
///* 300 */     int codeInt2 = Integer.valueOf(code2).intValue();
///* 301 */     int codeInt3 = Integer.valueOf(code3).intValue();
///* 302 */     int codeInt4 = Integer.valueOf(code4).intValue();
///*     */
///* 304 */     int codeSum = codeInt1 + codeInt2 + codeInt3 + codeInt4;
///* 305 */     String codeSumStr = codeSum+"";
///* 306 */     String finalCode = codeSumStr.substring(codeSumStr.length() - 1);
///* 307 */     return finalCode;
///*     */   }
///*     */ }
//
//
///* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/jobs/OpenCaiJob.class
// * Java compiler version: 8 (52.0)
// * JD-Core Version:       0.7.1
// */