/*     */ package lottery.domains.pool.play;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import lottery.domains.pool.play.impl.BDWPlayHandler;
/*     */ import lottery.domains.pool.play.impl.RXDS115PlayHandler;
/*     */ import lottery.domains.pool.play.impl.SSCLHPlayHanlder;
/*     */ import lottery.domains.pool.play.impl.ZHHZPlayHandler;
/*     */ import lottery.domains.pool.play.impl.ZHXDSPlayHandler;
/*     */ import lottery.domains.pool.play.impl.ZHXFSPlayHandler;
/*     */ import lottery.domains.pool.play.impl.ZXDSPlayHandler;
/*     */ import lottery.domains.pool.play.impl.ZXPlayHanlder;
/*     */ 
/*     */ public class TicketPlayHandlerContext
/*     */ {
/*  16 */   private static Map<String, ITicketPlayHandler> playHandlers = new HashMap();
/*     */   
/*     */   static {
/*  19 */     sscHandlers();
/*  20 */     x5Handlers();
/*  21 */     k3Handlers();
/*  22 */     d3Handlers();
/*  23 */     kl8Handlers();
/*  24 */     pk10Handlers();
/*  25 */     lhdHandlers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ITicketPlayHandler getHandler(int typeId, String ruleCode)
/*     */   {
/*  34 */     String type = typeId + "_" + ruleCode;
/*  35 */     return getHandler(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static ITicketPlayHandler getHandler(String type)
/*     */   {
/*  42 */     return (ITicketPlayHandler)playHandlers.get(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> sscHandlers()
/*     */   {
/*  49 */     Map<String, ITicketPlayHandler> map = new HashMap();
/*     */     
/*  51 */     playHandlers.put("1_wxzhixfs", new ZHXFSPlayHandler("1_wxzhixfs", ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  53 */     playHandlers.put("1_wxzhixds", new ZHXDSPlayHandler("1_wxzhixds", ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  55 */     playHandlers.put("1_wxzux120", new BDWPlayHandler("1_wxzux120", 5, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  57 */     playHandlers.put("1_wxzux60", new ZXPlayHanlder("1_wxzux60", 1, 2, 3, 1, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  59 */     playHandlers.put("1_wxzux30", new ZXPlayHanlder("1_wxzux30", 2, 2, 1, 1, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  61 */     playHandlers.put("1_wxzux20", new ZXPlayHanlder("1_wxzux20", 1, 3, 2, 1, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  63 */     playHandlers.put("1_wxzux10", new ZXPlayHanlder("1_wxzux10", 1, 3, 1, 2, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  65 */     playHandlers.put("1_wxzux5", new ZXPlayHanlder("1_wxzux5", 1, 4, 1, 1, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  67 */     playHandlers.put("1_wxzhixzh", new lottery.domains.pool.play.impl.ZXZHPlasyHandler("1_wxzhixzh", ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*  69 */     playHandlers.put("1_wxdxds", new lottery.domains.pool.play.impl.ZHDXDSPlayHanlder("1_wxdxds", ITicketPlayHandler.OFFSETS_WUXIN, 23, 22, 2));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  74 */     playHandlers.put("1_sixzhixfsh", new ZHXFSPlayHandler("1_sixzhixfsh", ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*  76 */     playHandlers.put("1_sixzhixdsh", new ZHXDSPlayHandler("1_sixzhixdsh", ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*  78 */     playHandlers.put("1_sixzux24h", new BDWPlayHandler("1_sixzux24h", 4, ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*  80 */     playHandlers.put("1_sixzux12h", new ZXPlayHanlder("1_sixzux12h", 1, 2, 2, 1, ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*  82 */     playHandlers.put("1_sixzux6h", new lottery.domains.pool.play.impl.ZX6PlayHanlder("1_sixzux6h", ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*  84 */     playHandlers.put("1_sixzux4h", new ZXPlayHanlder("1_sixzux4h", 1, 3, 1, 1, ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*  86 */     playHandlers.put("1_sixzhixzhh", new lottery.domains.pool.play.impl.ZXZHPlasyHandler("1_sixzhixzhh", ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*     */ 
/*  89 */     playHandlers.put("1_sixzhixfsq", new ZHXFSPlayHandler("1_sixzhixfsq", ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*  91 */     playHandlers.put("1_sixzhixdsq", new ZHXDSPlayHandler("1_sixzhixdsq", ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*  93 */     playHandlers.put("1_sixzux24q", new BDWPlayHandler("1_sixzux24q", 4, ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*  95 */     playHandlers.put("1_sixzux12q", new ZXPlayHanlder("1_sixzux12q", 1, 2, 2, 1, ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*  97 */     playHandlers.put("1_sixzux6q", new lottery.domains.pool.play.impl.ZX6PlayHanlder("1_sixzux6q", ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*  99 */     playHandlers.put("1_sixzux4q", new ZXPlayHanlder("1_sixzux4q", 1, 3, 1, 1, ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/* 101 */     playHandlers.put("1_sixzhixzhq", new lottery.domains.pool.play.impl.ZXZHPlasyHandler("1_sixzhixzhq", ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 106 */     playHandlers.put("1_sxzhixfsq", new ZHXFSPlayHandler("1_sxzhixfsq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 108 */     playHandlers.put("1_sxzhixdsq", new ZHXDSPlayHandler("1_sxzhixdsq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 110 */     playHandlers.put("1_sxzhixhzq", new ZHHZPlayHandler("1_sxzhixhzq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 112 */     playHandlers.put("1_sxzuxzsq", new lottery.domains.pool.play.impl.ZXZ3PlayHanlder("1_sxzuxzsq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 114 */     playHandlers.put("1_sxzuxzlq", new BDWPlayHandler("1_sxzuxzlq", 3, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 116 */     playHandlers.put("1_sxhhzxq", new ZXDSPlayHandler("1_sxhhzxq", "1_sxzuxzsq", "1_sxzuxzlq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 119 */     playHandlers.put("1_sxzuxhzq", new lottery.domains.pool.play.impl.ZXHZPlayHanlder("1_sxzuxzsq", "1_sxzuxzlq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 122 */     playHandlers.put("1_sxzhixfsz", new ZHXFSPlayHandler("1_sxzhixfsz", ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 124 */     playHandlers.put("1_sxzhixdsz", new ZHXDSPlayHandler("1_sxzhixdsz", ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 126 */     playHandlers.put("1_sxzhixhzz", new ZHHZPlayHandler("1_sxzhixhzz", ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 128 */     playHandlers.put("1_sxzuxzsz", new lottery.domains.pool.play.impl.ZXZ3PlayHanlder("1_sxzuxzsz", ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 130 */     playHandlers.put("1_sxzuxzlz", new BDWPlayHandler("1_sxzuxzlz", 3, ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 132 */     playHandlers.put("1_sxhhzxz", new ZXDSPlayHandler("1_sxhhzxz", "1_sxzuxzsz", "1_sxzuxzlz", ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/*     */ 
/* 135 */     playHandlers.put("1_sxzuxhzz", new lottery.domains.pool.play.impl.ZXHZPlayHanlder("1_sxzuxzsz", "1_sxzuxzlz", ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/*     */ 
/* 138 */     playHandlers.put("1_sxzhixfsh", new ZHXFSPlayHandler("1_sxzhixfsh", ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/* 140 */     playHandlers.put("1_sxzhixdsh", new ZHXDSPlayHandler("1_sxzhixdsh", ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/* 142 */     playHandlers.put("1_sxzhixhzh", new ZHHZPlayHandler("1_sxzhixhzh", ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/* 144 */     playHandlers.put("1_sxzuxzsh", new lottery.domains.pool.play.impl.ZXZ3PlayHanlder("1_sxzuxzsh", ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/* 146 */     playHandlers.put("1_sxzuxzlh", new BDWPlayHandler("1_sxzuxzlh", 3, ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/* 148 */     playHandlers.put("1_sxhhzxh", new ZXDSPlayHandler("1_sxhhzxh", "1_sxzuxzsh", "1_sxzuxzlh", ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/*     */ 
/* 151 */     playHandlers.put("1_sxzuxhzh", new lottery.domains.pool.play.impl.ZXHZPlayHanlder("1_sxzuxzsh", "1_sxzuxzlh", ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 156 */     playHandlers.put("1_exzhixfsq", new ZHXFSPlayHandler("1_exzhixfsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 158 */     playHandlers.put("1_exzhixfsqkill", new lottery.domains.pool.play.impl.ZHXFSKillPlayHandler("1_exzhixfsqkill", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 160 */     playHandlers.put("1_exzhixdsq", new ZHXDSPlayHandler("1_exzhixdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 162 */     playHandlers.put("1_exzhixhzq", new ZHHZPlayHandler("1_exzhixhzq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 164 */     playHandlers.put("1_dxdsq", new lottery.domains.pool.play.impl.ZHXDXDSPlayHanlder("1_dxdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 166 */     playHandlers.put("1_exzuxfsq", new BDWPlayHandler("1_exzuxfsq", 2, ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 168 */     playHandlers.put("1_exzuxdsq", new ZXDSPlayHandler("1_exzuxdsq", "1_exzuxdsq", "1_exzuxdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/*     */ 
/* 171 */     playHandlers.put("1_exzuxhzq", new lottery.domains.pool.play.impl.ZXHZPlayHanlder("1_exzuxhzq", "1_exzuxhzq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/*     */ 
/* 174 */     playHandlers.put("1_exzhixfsh", new ZHXFSPlayHandler("1_exzhixfsh", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/* 176 */     playHandlers.put("1_exzhixdsh", new ZHXDSPlayHandler("1_exzhixdsh", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/* 178 */     playHandlers.put("1_exzhixfshkill", new lottery.domains.pool.play.impl.ZHXFSKillPlayHandler("1_exzhixfshkill", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/* 180 */     playHandlers.put("1_exzhixhzh", new ZHHZPlayHandler("1_exzhixhzh", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/* 182 */     playHandlers.put("1_dxdsh", new lottery.domains.pool.play.impl.ZHXDXDSPlayHanlder("1_dxdsh", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/* 184 */     playHandlers.put("1_exzuxfsh", new BDWPlayHandler("1_exzuxfsh", 2, ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/* 186 */     playHandlers.put("1_exzuxdsh", new ZXDSPlayHandler("1_exzuxdsh", "1_exzuxdsh", "1_exzuxdsh", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/*     */ 
/* 189 */     playHandlers.put("1_exzuxhzh", new lottery.domains.pool.play.impl.ZXHZPlayHanlder("1_exzuxhzh", "1_exzuxhzh", ITicketPlayHandler.OFFSETS_HOUER));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 194 */     playHandlers.put("1_dw", new lottery.domains.pool.play.impl.DWDPlayHandler("1_dw", ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 199 */     playHandlers.put("1_bdw1mq", new BDWPlayHandler("1_bdw1mq", 1, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 201 */     playHandlers.put("1_bdw1mz", new BDWPlayHandler("1_bdw1mz", 1, ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 203 */     playHandlers.put("1_bdw1mh", new BDWPlayHandler("1_bdw1mh", 1, ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/*     */ 
/* 206 */     playHandlers.put("1_bdw2mq", new BDWPlayHandler("1_bdw2mq", 2, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 208 */     playHandlers.put("1_bdw2mz", new BDWPlayHandler("1_bdw2mz", 2, ITicketPlayHandler.OFFSETS_ZHONGSAN));
/*     */     
/* 210 */     playHandlers.put("1_bdw2mh", new BDWPlayHandler("1_bdw2mh", 2, ITicketPlayHandler.OFFSETS_HOUSAN));
/*     */     
/*     */ 
/* 213 */     playHandlers.put("1_bdwsix1mq", new BDWPlayHandler("1_bdwsix1mq", 1, ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/* 215 */     playHandlers.put("1_bdwsix2mq", new BDWPlayHandler("1_bdwsix2mq", 2, ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*     */ 
/* 218 */     playHandlers.put("1_bdwsix1mh", new BDWPlayHandler("1_bdwsix1mh", 1, ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/* 220 */     playHandlers.put("1_bdwsix2mh", new BDWPlayHandler("1_bdwsix2mh", 2, ITicketPlayHandler.OFFSETS_HOUSI));
/*     */     
/*     */ 
/* 223 */     playHandlers.put("1_bdwwx2m", new BDWPlayHandler("1_bdwwx2m", 2, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 225 */     playHandlers.put("1_bdwwx3m", new BDWPlayHandler("1_bdwwx3m", 3, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 231 */     playHandlers.put("1_rx2fs", new lottery.domains.pool.play.impl.RXZHFSPlayHandler("1_rx2fs", 2));
/* 232 */     playHandlers.put("1_rx3fs", new lottery.domains.pool.play.impl.RXZHFSPlayHandler("1_rx3fs", 3));
/* 233 */     playHandlers.put("1_rx4fs", new lottery.domains.pool.play.impl.RXZHFSPlayHandler("1_rx4fs", 4));
/*     */     
/* 235 */     playHandlers.put("1_rx2ds", new lottery.domains.pool.play.impl.SSCRXZHDSPlayHandler("1_rx2ds", 2));
/* 236 */     playHandlers.put("1_rx3ds", new lottery.domains.pool.play.impl.SSCRXZHDSPlayHandler("1_rx3ds", 3));
/* 237 */     playHandlers.put("1_rx4ds", new lottery.domains.pool.play.impl.SSCRXZHDSPlayHandler("1_rx4ds", 4));
/*     */     
/* 239 */     playHandlers.put("1_rx2zx", new lottery.domains.pool.play.impl.SSCRXZXFSPlayHandler("1_rx2zx", 2));
/* 240 */     playHandlers.put("1_rx3z6", new lottery.domains.pool.play.impl.SSCRXZXZ6PlayHanlder("1_rx3z6"));
/* 241 */     playHandlers.put("1_rx3z3", new lottery.domains.pool.play.impl.SSCRXZXZ3PlayHanlder("1_rx3z3"));
/*     */     
/*     */ 
/*     */ 
/* 245 */     playHandlers.put("1_qwyffs", new lottery.domains.pool.play.impl.SSCQWPlayHandler("1_qwyffs", ITicketPlayHandler.OFFSETS_WUXIN, 1));
/*     */     
/* 247 */     playHandlers.put("1_qwhscs", new lottery.domains.pool.play.impl.SSCQWPlayHandler("1_qwhscs", ITicketPlayHandler.OFFSETS_WUXIN, 2));
/*     */     
/* 249 */     playHandlers.put("1_qwsxbx", new lottery.domains.pool.play.impl.SSCQWPlayHandler("1_qwsxbx", ITicketPlayHandler.OFFSETS_WUXIN, 3));
/*     */     
/* 251 */     playHandlers.put("1_qwsjfc", new lottery.domains.pool.play.impl.SSCQWPlayHandler("1_qwsjfc", ITicketPlayHandler.OFFSETS_WUXIN, 4));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 256 */     playHandlers.put("1_longhuhewq", new SSCLHPlayHanlder("1_longhuhewq", 0, 1));
/*     */     
/* 258 */     playHandlers.put("1_longhuhewb", new SSCLHPlayHanlder("1_longhuhewb", 0, 2));
/*     */     
/* 260 */     playHandlers.put("1_longhuhews", new SSCLHPlayHanlder("1_longhuhews", 0, 3));
/*     */     
/* 262 */     playHandlers.put("1_longhuhewg", new SSCLHPlayHanlder("1_longhuhewg", 0, 4));
/*     */     
/* 264 */     playHandlers.put("1_longhuheqb", new SSCLHPlayHanlder("1_longhuheqb", 1, 2));
/*     */     
/* 266 */     playHandlers.put("1_longhuheqs", new SSCLHPlayHanlder("1_longhuheqs", 1, 3));
/*     */     
/* 268 */     playHandlers.put("1_longhuheqg", new SSCLHPlayHanlder("1_longhuheqg", 1, 4));
/*     */     
/* 270 */     playHandlers.put("1_longhuhebs", new SSCLHPlayHanlder("1_longhuhebs", 2, 3));
/*     */     
/* 272 */     playHandlers.put("1_longhuhebg", new SSCLHPlayHanlder("1_longhuhebg", 2, 4));
/*     */     
/* 274 */     playHandlers.put("1_longhuhesg", new SSCLHPlayHanlder("1_longhuhesg", 3, 4));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 279 */     playHandlers.put("1_sscniuniu", new lottery.domains.pool.play.impl.SSCNiuNiuPlayHanlder("1_sscniuniu", ITicketPlayHandler.OFFSETS_WUXIN, ","));
/*     */     
/*     */ 
/*     */ 
/* 283 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> x5Handlers()
/*     */   {
/* 290 */     Map<String, ITicketPlayHandler> map = new HashMap();
/*     */     
/* 292 */     playHandlers.put("2_sanmzhixfsq", new lottery.domains.pool.play.impl.ZHXFS115PlayHandler("2_sanmzhixfsq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 294 */     playHandlers.put("2_sanmzhixdsq", new lottery.domains.pool.play.impl.ZHXDS115PlayHandler("2_sanmzhixdsq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 296 */     playHandlers.put("2_sanmzuxfsq", new BDWPlayHandler("2_sanmzuxfsq", 3, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 298 */     playHandlers.put("2_sanmzuxdsq", new lottery.domains.pool.play.impl.ZXDS115PlayHandler("2_sanmzuxdsq", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 303 */     playHandlers.put("2_ermzhixfsq", new lottery.domains.pool.play.impl.ZHXFS115PlayHandler("2_ermzhixfsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 305 */     playHandlers.put("2_ermzhixdsq", new lottery.domains.pool.play.impl.ZHXDS115PlayHandler("2_ermzhixdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 307 */     playHandlers.put("2_ermzuxfsq", new BDWPlayHandler("2_ermzuxfsq", 2, ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 309 */     playHandlers.put("2_ermzuxdsq", new lottery.domains.pool.play.impl.ZXDS115PlayHandler("2_ermzuxdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 314 */     playHandlers.put("2_bdw", new BDWPlayHandler("2_bdw", 1, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 319 */     playHandlers.put("2_dwd", new lottery.domains.pool.play.impl.DWDPlayHandler("2_dwd", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 324 */     playHandlers.put("2_dds", new lottery.domains.pool.play.impl.QW11X5DDSPlayHandler("2_dds", ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 326 */     playHandlers.put("2_czw", new lottery.domains.pool.play.impl.SYX5CZWPlayHandler("2_czw", 1, ITicketPlayHandler.OFFSET_11X5ZHONG));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 331 */     playHandlers.put("2_rx1fs", new BDWPlayHandler("2_rx1fs", 1, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 333 */     playHandlers.put("2_rx2fs", new BDWPlayHandler("2_rx2fs", 2, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 335 */     playHandlers.put("2_rx3fs", new BDWPlayHandler("2_rx3fs", 3, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 337 */     playHandlers.put("2_rx4fs", new BDWPlayHandler("2_rx4fs", 4, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 339 */     playHandlers.put("2_rx5fs", new BDWPlayHandler("2_rx5fs", 5, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 341 */     playHandlers.put("2_rx6fs", new BDWPlayHandler("2_rx6fs", 6, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 343 */     playHandlers.put("2_rx7fs", new BDWPlayHandler("2_rx7fs", 7, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 345 */     playHandlers.put("2_rx8fs", new BDWPlayHandler("2_rx8fs", 8, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*     */ 
/* 348 */     playHandlers.put("2_rx1ds", new RXDS115PlayHandler("2_rx1ds", 1, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 350 */     playHandlers.put("2_rx2ds", new RXDS115PlayHandler("2_rx2ds", 2, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 352 */     playHandlers.put("2_rx3ds", new RXDS115PlayHandler("2_rx3ds", 3, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 354 */     playHandlers.put("2_rx4ds", new RXDS115PlayHandler("2_rx4ds", 4, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 356 */     playHandlers.put("2_rx5ds", new RXDS115PlayHandler("2_rx5ds", 5, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 358 */     playHandlers.put("2_rx6ds", new RXDS115PlayHandler("2_rx6ds", 6, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 360 */     playHandlers.put("2_rx7ds", new RXDS115PlayHandler("2_rx7ds", 7, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 362 */     playHandlers.put("2_rx8ds", new RXDS115PlayHandler("2_rx8ds", 8, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 367 */     playHandlers.put("2_rxtd2", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd2", 2, 2, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 369 */     playHandlers.put("2_rxtd3", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd3", 3, 3, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 371 */     playHandlers.put("2_rxtd4", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd4", 4, 4, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 373 */     playHandlers.put("2_rxtd5", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd5", 5, 5, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 375 */     playHandlers.put("2_rxtd6", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd6", 5, 6, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 377 */     playHandlers.put("2_rxtd7", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd7", 5, 7, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/* 379 */     playHandlers.put("2_rxtd8", new lottery.domains.pool.play.impl.RXTD115PlayHandler("2_rxtd8", 5, 8, ITicketPlayHandler.OFFSETS_WUXIN));
/*     */     
/*     */ 
/* 382 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> k3Handlers()
/*     */   {
/* 389 */     Map<String, ITicketPlayHandler> map = new HashMap();
/* 390 */     playHandlers.put("3_ebthdx", new BDWPlayHandler("3_ebthdx", 2, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 392 */     playHandlers.put("3_ebthds", new lottery.domains.pool.play.impl.ZXDSK3PlayHandler("3_ebthds", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 394 */     playHandlers.put("3_ebthdt", new lottery.domains.pool.play.impl.ZXDTPlayHandler("3_ebthdt", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 397 */     playHandlers.put("3_ethdx", new lottery.domains.pool.play.impl.ZXETHDXBZK3PlayHander("3_ethdx", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 399 */     playHandlers.put("3_ethds", new lottery.domains.pool.play.impl.ZXDSK3PlayHandler("3_ethds", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 401 */     playHandlers.put("3_ethfx", new lottery.domains.pool.play.impl.ZXETHFXK3PlayHander("3_ethfx", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 404 */     playHandlers.put("3_sbthdx", new BDWPlayHandler("3_sbthdx", 3, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 406 */     playHandlers.put("3_sbthds", new lottery.domains.pool.play.impl.ZXDSK3PlayHandler("3_sbthds", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 409 */     playHandlers.put("3_sthdx", new lottery.domains.pool.play.impl.DXTXK3STHPlayHandler("3_sthdx", 1, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 411 */     playHandlers.put("3_sthtx", new lottery.domains.pool.play.impl.DXTXK3STHPlayHandler("3_sthtx", 1, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 413 */     playHandlers.put("3_slhtx", new lottery.domains.pool.play.impl.TXK3SLHPlayHandler("3_slhtx", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 415 */     playHandlers.put("3_hezhi", new ZHHZPlayHandler("3_hezhi", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 417 */     playHandlers.put("3_hzdxds", new lottery.domains.pool.play.impl.ZHDXDSPlayHanlder("3_hzdxds", ITicketPlayHandler.OFFSETS_QIANSAN, 11, 10, 2));
/*     */     
/* 419 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> d3Handlers()
/*     */   {
/* 431 */     Map<String, ITicketPlayHandler> map = new HashMap();
/*     */     
/* 433 */     playHandlers.put("4_sanxzhixfs", new ZHXFSPlayHandler("4_sanxzhixfs", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 435 */     playHandlers.put("4_sanxzhixds", new ZHXDSPlayHandler("4_sanxzhixds", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 437 */     playHandlers.put("4_sanxzhixhz", new ZHHZPlayHandler("4_sanxzhixhz", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 440 */     playHandlers.put("4_sanxzs", new lottery.domains.pool.play.impl.ZXZ3PlayHanlder("4_sanxzs", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 442 */     playHandlers.put("4_sanxzl", new BDWPlayHandler("4_sanxzl", 3, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 444 */     playHandlers.put("4_sanxhhzx", new ZXDSPlayHandler("4_sanxhhzx", "4_sanxzs", "4_sanxzl", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 449 */     playHandlers.put("4_exzhixfsh", new ZHXFSPlayHandler("4_exzhixfsh", ITicketPlayHandler.OFFSETS_ERSAN));
/*     */     
/* 451 */     playHandlers.put("4_exzhixdsh", new ZHXDSPlayHandler("4_exzhixdsh", ITicketPlayHandler.OFFSETS_ERSAN));
/*     */     
/* 453 */     playHandlers.put("4_exzhixhzh", new ZHHZPlayHandler("4_exzhixhzh", ITicketPlayHandler.OFFSETS_ERSAN));
/*     */     
/* 455 */     playHandlers.put("4_exzuxfsh", new BDWPlayHandler("4_exzuxfsh", 2, ITicketPlayHandler.OFFSETS_ERSAN));
/*     */     
/* 457 */     playHandlers.put("4_exzuxdsh", new ZXDSPlayHandler("4_exzuxdsh", "4_exzuxdsh", "4_exzuxdsh", ITicketPlayHandler.OFFSETS_ERSAN));
/*     */     
/*     */ 
/* 460 */     playHandlers.put("4_exzhixfsq", new ZHXFSPlayHandler("4_exzhixfsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 462 */     playHandlers.put("4_exzhixdsq", new ZHXDSPlayHandler("4_exzhixdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 464 */     playHandlers.put("4_exzhixhzq", new ZHHZPlayHandler("4_exzhixhzq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 466 */     playHandlers.put("4_exzuxfsq", new BDWPlayHandler("4_exzuxfsq", 2, ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 468 */     playHandlers.put("4_exzuxdsq", new ZXDSPlayHandler("4_exzuxdsq", "4_exzuxdsq", "4_exzuxdsq", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/*     */ 
/*     */ 
/* 472 */     playHandlers.put("4_dwd", new lottery.domains.pool.play.impl.DWDPlayHandler("4_dwd", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/*     */ 
/* 476 */     playHandlers.put("4_yimabdw", new BDWPlayHandler("4_yimabdw", 1, ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 479 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> kl8Handlers()
/*     */   {
/* 486 */     Map<String, ITicketPlayHandler> map = new HashMap();
/* 487 */     playHandlers.put("5_hezhids", new lottery.domains.pool.play.impl.QWKL8PlayHandler("5_hezhids"));
/*     */     
/* 489 */     playHandlers.put("5_hezhidx", new lottery.domains.pool.play.impl.QWKL8PlayHandler("5_hezhidx"));
/*     */     
/* 491 */     playHandlers.put("5_jopan", new lottery.domains.pool.play.impl.QWKL8PlayHandler("5_jopan"));
/*     */     
/* 493 */     playHandlers.put("5_sxpan", new lottery.domains.pool.play.impl.QWKL8PlayHandler("5_sxpan"));
/*     */     
/* 495 */     playHandlers.put("5_hzdxds", new lottery.domains.pool.play.impl.QWKL8PlayHandler("5_hzdxds"));
/*     */     
/*     */ 
/* 498 */     playHandlers.put("5_rx1", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx1", 1, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/* 500 */     playHandlers.put("5_rx2", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx2", 2, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/* 502 */     playHandlers.put("5_rx3", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx3", 3, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/* 504 */     playHandlers.put("5_rx4", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx4", 4, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/* 506 */     playHandlers.put("5_rx5", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx5", 5, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/* 508 */     playHandlers.put("5_rx6", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx6", 6, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/* 510 */     playHandlers.put("5_rx7", new lottery.domains.pool.play.impl.RXKL8PlayHandler("5_rx7", 7, ITicketPlayHandler.OFFSETS_QIANERSHI));
/*     */     
/*     */ 
/* 513 */     playHandlers.put("5_hezhiwx", new lottery.domains.pool.play.impl.QWKL8PlayHandler("5_hezhiwx"));
/*     */     
/*     */ 
/* 516 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> pk10Handlers()
/*     */   {
/* 524 */     Map<String, ITicketPlayHandler> map = new HashMap();
/* 525 */     playHandlers.put("6_qianyi", new BDWPlayHandler("6_qianyi", 1, ITicketPlayHandler.OFFSETS_QIANYI));
/*     */     
/*     */ 
/* 528 */     playHandlers.put("6_qianerzxfs", new lottery.domains.pool.play.impl.ZHIXFSERMAYSPlayHandler("6_qianerzxfs", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/* 530 */     playHandlers.put("6_qianerzxds", new lottery.domains.pool.play.impl.ZHXDSPK10PlayHandler("6_qianerzxds", ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/*     */ 
/* 533 */     playHandlers.put("6_qiansanzxfs", new lottery.domains.pool.play.impl.ZHIXFSERMAYSPlayHandler("6_qiansanzxfs", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/* 535 */     playHandlers.put("6_qiansanzxds", new lottery.domains.pool.play.impl.ZHXDSPK10PlayHandler("6_qiansanzxds", ITicketPlayHandler.OFFSETS_QIANSAN));
/*     */     
/*     */ 
/* 538 */     playHandlers.put("6_qiansizxfs", new lottery.domains.pool.play.impl.ZHIXFSERMAYSPlayHandler("6_qiansizxfs", ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/* 540 */     playHandlers.put("6_qiansizxds", new lottery.domains.pool.play.impl.ZHXDSPK10PlayHandler("6_qiansizxds", ITicketPlayHandler.OFFSETS_QIANSI));
/*     */     
/*     */ 
/* 543 */     playHandlers.put("6_qianwuzxfs", new lottery.domains.pool.play.impl.ZHIXFSERMAYSPlayHandler("6_qianwuzxfs", ITicketPlayHandler.OFFSETS_QIANWU));
/*     */     
/* 545 */     playHandlers.put("6_qianwuzxds", new lottery.domains.pool.play.impl.ZHXDSPK10PlayHandler("6_qianwuzxds", ITicketPlayHandler.OFFSETS_QIANWU));
/*     */     
/*     */ 
/*     */ 
/* 549 */     playHandlers.put("6_qwdingweidan", new lottery.domains.pool.play.impl.DWDPK10PlayHandler("6_qwdingweidan", ITicketPlayHandler.OFFSETS_QIANWU));
/*     */     
/*     */ 
/* 552 */     playHandlers.put("6_hwdingweidan", new lottery.domains.pool.play.impl.DWDPK10PlayHandler("6_hwdingweidan", ITicketPlayHandler.OFFSETS_HOUWU));
/*     */     
/*     */ 
/* 555 */     playHandlers.put("6_dw1dxds", new lottery.domains.pool.play.impl.DXDSPK10PlayHanlder("6_dw1dxds", 0));
/*     */     
/* 557 */     playHandlers.put("6_dw2dxds", new lottery.domains.pool.play.impl.DXDSPK10PlayHanlder("6_dw2dxds", 1));
/*     */     
/* 559 */     playHandlers.put("6_dw3dxds", new lottery.domains.pool.play.impl.DXDSPK10PlayHanlder("6_dw3dxds", 2));
/*     */     
/* 561 */     playHandlers.put("6_dw4dxds", new lottery.domains.pool.play.impl.DXDSPK10PlayHanlder("6_dw4dxds", 3));
/*     */     
/* 563 */     playHandlers.put("6_dw5dxds", new lottery.domains.pool.play.impl.DXDSPK10PlayHanlder("6_dw5dxds", 4));
/*     */     
/*     */ 
/* 566 */     playHandlers.put("6_pk10_dxdsgyhz", new lottery.domains.pool.play.impl.HZDXDSPlayHanlder("6_pk10_dxdsgyhz", new int[] { 12, 13, 14, 15, 16, 17, 18, 19 }, new int[] { 3, 4, 5, 6, 7, 8, 9, 10, 11 }, ITicketPlayHandler.OFFSETS_QIANER));
/*     */     
/*     */ 
/* 569 */     playHandlers.put("6_pk10_hzgyhz", new lottery.domains.pool.play.impl.HZMultiplePrizePlayHandler("6_pk10_hzgyhz", ITicketPlayHandler.OFFSETS_QIANER, 3, 19));
/*     */     
/* 571 */     playHandlers.put("6_pk10_hzqshz", new lottery.domains.pool.play.impl.HZMultiplePrizePlayHandler("6_pk10_hzqshz", ITicketPlayHandler.OFFSETS_QIANSAN, 6, 27));
/*     */     
/*     */ 
/* 574 */     playHandlers.put("6_01vs10", new lottery.domains.pool.play.impl.LHPlayHanlder("6_01vs10", 0, 9, " "));
/*     */     
/* 576 */     playHandlers.put("6_02vs09", new lottery.domains.pool.play.impl.LHPlayHanlder("6_02vs09", 1, 8, " "));
/*     */     
/* 578 */     playHandlers.put("6_03vs08", new lottery.domains.pool.play.impl.LHPlayHanlder("6_03vs08", 2, 7, " "));
/*     */     
/* 580 */     playHandlers.put("6_04vs07", new lottery.domains.pool.play.impl.LHPlayHanlder("6_04vs07", 3, 6, " "));
/*     */     
/* 582 */     playHandlers.put("6_05vs06", new lottery.domains.pool.play.impl.LHPlayHanlder("6_05vs06", 4, 5, " "));
/*     */     
/* 584 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Map<String, ITicketPlayHandler> lhdHandlers()
/*     */   {
/* 592 */     Map<String, ITicketPlayHandler> map = new HashMap();
/*     */     
/*     */ 
/* 595 */     playHandlers.put("7_lhd_longhuhewq", new SSCLHPlayHanlder("7_lhd_longhuhewq", 0, 1));
/*     */     
/* 597 */     playHandlers.put("7_lhd_longhuhewb", new SSCLHPlayHanlder("7_lhd_longhuhewb", 0, 2));
/*     */     
/* 599 */     playHandlers.put("7_lhd_longhuhews", new SSCLHPlayHanlder("7_lhd_longhuhews", 0, 3));
/*     */     
/* 601 */     playHandlers.put("7_lhd_longhuhewg", new SSCLHPlayHanlder("7_lhd_longhuhewg", 0, 4));
/*     */     
/* 603 */     playHandlers.put("7_lhd_longhuheqb", new SSCLHPlayHanlder("7_lhd_longhuheqb", 1, 2));
/*     */     
/* 605 */     playHandlers.put("7_lhd_longhuheqs", new SSCLHPlayHanlder("7_lhd_longhuheqs", 1, 3));
/*     */     
/* 607 */     playHandlers.put("7_lhd_longhuheqg", new SSCLHPlayHanlder("7_lhd_longhuheqg", 1, 4));
/*     */     
/* 609 */     playHandlers.put("7_lhd_longhuhebs", new SSCLHPlayHanlder("7_lhd_longhuhebs", 2, 3));
/*     */     
/* 611 */     playHandlers.put("7_lhd_longhuhebg", new SSCLHPlayHanlder("7_lhd_longhuhebg", 2, 4));
/*     */     
/* 613 */     playHandlers.put("7_lhd_longhuhesg", new SSCLHPlayHanlder("7_lhd_longhuhesg", 3, 4));
/*     */     
/*     */ 
/*     */ 
/* 617 */     return map;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/pool/play/TicketPlayHandlerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */