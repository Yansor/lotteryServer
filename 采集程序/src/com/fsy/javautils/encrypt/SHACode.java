/*     */ package com.fsy.javautils.encrypt;
/*     */ 
/*     */ public class SHACode { private static final long SH0 = 1732584193L;
/*     */   private static final long SH1 = 4023233417L;
/*     */   private static final long SH2 = 2562383102L;
/*     */   private static final long SH3 = 271733878L;
/*     */   private static final long SH4 = 3285377520L;
/*     */   private static final long K0 = 1518500249L;
/*     */   private static final long K1 = 1859775393L;
/*     */   private static final long K2 = 2400959708L;
/*     */   private static final long K3 = 3395469782L;
/*     */   private static long[] W;
/*     */   private static byte[] BW;
/*     */   private static int p0;
/*     */   private static int p1;
/*     */   private static int p2;
/*     */   private static int p3;
/*     */   private static int p4;
/*     */   private static long A;
/*     */   private static long B;
/*     */   private static long C;
/*     */   private static long D;
/*     */   private static long E;
/*     */   private static long temp;
/*     */   
/*  26 */   private static long f0(long x, long y, long z) { return z ^ x & (y ^ z); }
/*     */   
/*     */   private static long f1(long x, long y, long z)
/*     */   {
/*  30 */     return x ^ y ^ z;
/*     */   }
/*     */   
/*     */   private static long f2(long x, long y, long z) {
/*  34 */     return x & y | z & (x | y);
/*     */   }
/*     */   
/*     */   private static long f3(long x, long y, long z) {
/*  38 */     return x ^ y ^ z;
/*     */   }
/*     */   
/*     */   private static long S(int n, long X) {
/*  42 */     long tem = 0L;
///*  43 */     X &= 0xFFFFFFFFFFFFFFFF;
    X &= 0xFFFFFFFF;
/*     */     
/*  45 */     if (n == 1) {
/*  46 */       tem = X >> 31 & 1L;
/*     */     }
/*     */     
/*  49 */     if (n == 5)
/*  50 */       tem = X >> 27 & 0x1F;
/*  51 */     if (n == 30) {
/*  52 */       tem = X >> 2 & 0x3FFFFFFF;
/*     */     }
/*  54 */     return X << n | tem;
/*     */   }
/*     */   
/*     */ 
/*     */   private static void r0(int f, long K)
/*     */   {
/*  60 */     temp = S(5, A) + f0(B, C, D) + E + W[(p0++)] + K;
/*  61 */     E = D;
/*  62 */     D = C;
/*  63 */     C = S(30, B);
/*  64 */     B = A;
/*  65 */     A = temp;
/*     */   }
/*     */   
/*     */   private static void r1(int f, long K)
/*     */   {
/*  70 */     temp = W[(p1++)] ^ W[(p2++)] ^ W[(p3++)] ^ W[(p4++)];
/*     */     
/*  72 */     switch (f) {
/*     */     case 0: 
/*  74 */       temp = S(5, A) + f0(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
/*  75 */       break;
/*     */     case 1: 
/*  77 */       temp = S(5, A) + f1(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
/*  78 */       break;
/*     */     case 2: 
/*  80 */       temp = S(5, A) + f2(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
/*  81 */       break;
/*     */     case 3: 
/*  83 */       temp = S(5, A) + f3(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
/*     */     }
/*     */     
/*     */     
/*  87 */     E = D;
/*  88 */     D = C;
/*  89 */     C = S(30, B);
/*  90 */     B = A;
/*  91 */     A = temp;
/*     */   }
/*     */   
/*     */   public static long getCode(String mem)
/*     */   {
/*  96 */     int length = mem.toCharArray().length;
/*     */     
/*     */ 
/*     */ 
/* 100 */     int sp = 0;
/*     */     
/* 102 */     W = new long[80];
/* 103 */     BW = new byte['ŀ'];
/* 104 */     int padded = 0;
/* 105 */     char[] s = mem.toCharArray();
/* 106 */     long h0 = 1732584193L;
/* 107 */     long h1 = 4023233417L;
/* 108 */     long h2 = 2562383102L;
/* 109 */     long h3 = 271733878L;
/* 110 */     long h4 = 3285377520L;
/* 111 */     int lo_length ; int hi_length = lo_length = 0;
///* 112 */     for (;;) { int nread; int nread; if (length < 64) {
    for (;;) { int nread; ; if (length < 64) {
/* 113 */         nread = length;
/*     */       } else
/* 115 */         nread = 64;
/* 116 */       length -= nread;
/* 117 */       for (int m = 0; m < nread; m++)
/* 118 */         BW[m] = ((byte)s[(sp++)]);
/* 119 */       if (nread < 64) {
/* 120 */         int nbits = nread * 8;
/*     */         
///* 122 */         if (lo_length += nbits < nbits)
            if ((lo_length += nbits) < nbits)
/* 123 */           hi_length++;
/* 124 */         if ((nread < 64) && (padded == 0)) {
/* 125 */           BW[(nread++)] = Byte.MIN_VALUE;
/* 126 */           padded = 1;
/*     */         }
/*     */         
/* 129 */         for (int i = nread; i < 64; i++) {
/* 130 */           BW[i] = 0;
/*     */         }
/* 132 */         byte[] tar = new byte[4];
/* 133 */         for (int z = 0; z < 64; z += 4) {
/* 134 */           for (int y = 0; y < 4; y++) {
/* 135 */             tar[y] = BW[(z + y)];
/*     */           }
/* 137 */           W[(z >> 2)] = 0L;
/* 138 */           W[(z >> 2)] = ((W[(z >> 2)] | tar[0]) << 8);
/* 139 */           W[(z >> 2)] = ((W[(z >> 2)] | tar[1] & 0xFF) << 8);
/* 140 */           W[(z >> 2)] = ((W[(z >> 2)] | tar[2] & 0xFF) << 8 | tar[3] & 0xFF);
/*     */         }
/*     */         
/*     */ 
/* 144 */         if (nread <= 56) {
/* 145 */           W[14] = hi_length;
/* 146 */           W[15] = lo_length;
/*     */         }
/*     */       } else {
/* 149 */         lo_length += 512; if (lo_length < 512) {
/* 150 */           hi_length++;
/*     */         }
/* 152 */         byte[] tar = new byte[4];
/* 153 */         for (int z = 0; z < 64; z += 4) {
/* 154 */           for (int y = 0; y < 4; y++) {
/* 155 */             tar[y] = BW[(z + y)];
/*     */           }
/* 157 */           W[(z >> 2)] = 0L;
/* 158 */           W[(z >> 2)] = ((W[(z >> 4)] | tar[0]) << 8);
/* 159 */           W[(z >> 2)] = ((W[(z >> 2)] | tar[1] & 0xFF) << 8);
/* 160 */           W[(z >> 2)] = ((W[(z >> 2)] | tar[2] & 0xFF) << 8 | tar[3] & 0xFF);
/*     */         }
/*     */       }
/*     */       
/* 164 */       p0 = 0;
/* 165 */       A = h0;
/* 166 */       B = h1;
/* 167 */       C = h2;
/* 168 */       D = h3;
/* 169 */       E = h4;
/*     */       
/* 171 */       r0(0, 1518500249L);
/* 172 */       r0(0, 1518500249L);
/* 173 */       r0(0, 1518500249L);
/* 174 */       r0(0, 1518500249L);
/* 175 */       r0(0, 1518500249L);
/* 176 */       r0(0, 1518500249L);
/* 177 */       r0(0, 1518500249L);
/* 178 */       r0(0, 1518500249L);
/* 179 */       r0(0, 1518500249L);
/* 180 */       r0(0, 1518500249L);
/* 181 */       r0(0, 1518500249L);
/* 182 */       r0(0, 1518500249L);
/* 183 */       r0(0, 1518500249L);
/* 184 */       r0(0, 1518500249L);
/* 185 */       r0(0, 1518500249L);
/* 186 */       r0(0, 1518500249L);
/*     */       
/* 188 */       p1 = 13;
/* 189 */       p2 = 8;
/* 190 */       p3 = 2;
/* 191 */       p4 = 0;
/* 192 */       r1(0, 1518500249L);
/* 193 */       r1(0, 1518500249L);
/* 194 */       r1(0, 1518500249L);
/* 195 */       r1(0, 1518500249L);
/*     */       
/* 197 */       r1(1, 1859775393L);
/* 198 */       r1(1, 1859775393L);
/* 199 */       r1(1, 1859775393L);
/* 200 */       r1(1, 1859775393L);
/* 201 */       r1(1, 1859775393L);
/* 202 */       r1(1, 1859775393L);
/* 203 */       r1(1, 1859775393L);
/* 204 */       r1(1, 1859775393L);
/* 205 */       r1(1, 1859775393L);
/* 206 */       r1(1, 1859775393L);
/* 207 */       r1(1, 1859775393L);
/* 208 */       r1(1, 1859775393L);
/* 209 */       r1(1, 1859775393L);
/* 210 */       r1(1, 1859775393L);
/* 211 */       r1(1, 1859775393L);
/* 212 */       r1(1, 1859775393L);
/* 213 */       r1(1, 1859775393L);
/* 214 */       r1(1, 1859775393L);
/* 215 */       r1(1, 1859775393L);
/* 216 */       r1(1, 1859775393L);
/*     */       
/* 218 */       r1(2, 2400959708L);
/* 219 */       r1(2, 2400959708L);
/* 220 */       r1(2, 2400959708L);
/* 221 */       r1(2, 2400959708L);
/* 222 */       r1(2, 2400959708L);
/* 223 */       r1(2, 2400959708L);
/* 224 */       r1(2, 2400959708L);
/* 225 */       r1(2, 2400959708L);
/* 226 */       r1(2, 2400959708L);
/* 227 */       r1(2, 2400959708L);
/* 228 */       r1(2, 2400959708L);
/* 229 */       r1(2, 2400959708L);
/* 230 */       r1(2, 2400959708L);
/* 231 */       r1(2, 2400959708L);
/* 232 */       r1(2, 2400959708L);
/* 233 */       r1(2, 2400959708L);
/* 234 */       r1(2, 2400959708L);
/* 235 */       r1(2, 2400959708L);
/* 236 */       r1(2, 2400959708L);
/* 237 */       r1(2, 2400959708L);
/*     */       
/* 239 */       r1(3, 3395469782L);
/* 240 */       r1(3, 3395469782L);
/* 241 */       r1(3, 3395469782L);
/* 242 */       r1(3, 3395469782L);
/* 243 */       r1(3, 3395469782L);
/* 244 */       r1(3, 3395469782L);
/* 245 */       r1(3, 3395469782L);
/* 246 */       r1(3, 3395469782L);
/* 247 */       r1(3, 3395469782L);
/* 248 */       r1(3, 3395469782L);
/* 249 */       r1(3, 3395469782L);
/* 250 */       r1(3, 3395469782L);
/* 251 */       r1(3, 3395469782L);
/* 252 */       r1(3, 3395469782L);
/* 253 */       r1(3, 3395469782L);
/* 254 */       r1(3, 3395469782L);
/* 255 */       r1(3, 3395469782L);
/* 256 */       r1(3, 3395469782L);
/* 257 */       r1(3, 3395469782L);
/* 258 */       r1(3, 3395469782L);
/*     */       
/* 260 */       h0 += A;
/* 261 */       h1 += B;
/* 262 */       h2 += C;
/* 263 */       h3 += D;
/* 264 */       h4 += E;
/*     */       
/*     */ 
/* 267 */       if (nread <= 56) {
/*     */         break;
/*     */       }
/*     */     }
/* 271 */     W = null;
/* 272 */     BW = null;
/* 273 */     s = null;
/* 274 */     A = 0L;
/* 275 */     B = 0L;
/* 276 */     C = 0L;
/* 277 */     D = 0L;
/* 278 */     E = 0L;
/* 279 */     temp = 0L;
/* 280 */     h4 = 0L;
/* 281 */     h3 = 0L;
/* 282 */     h2 = 0L;
/* 283 */     h1 = 0L;
/* 284 */     p0 = 0;
/* 285 */     p1 = 0;
/* 286 */     p2 = 0;
/* 287 */     p3 = 0;
/* 288 */     p4 = 0;
/*     */     
/*     */ 
/*     */ 
/* 292 */     int lowBit = (int)(h0 & 0xFFFF);
/* 293 */     int highBit = (int)(h0 >> 16 & 0xFFFFF);
/* 294 */     h0 = 0L;
/* 295 */     String shaHigh = new String(Integer.toHexString(highBit));
/*     */     
/* 297 */     int leng = shaHigh.length();
/* 298 */     if (leng > 4) {
/* 299 */       shaHigh = shaHigh.substring(leng - 4);
/*     */     }
/* 301 */     else if (leng < 4) {
/* 302 */       for (int m = 0; m < 4 - leng; m++) {
/* 303 */         shaHigh = "0" + shaHigh;
/*     */       }
/*     */     }
/* 306 */     String shaLow = new String(Integer.toHexString(lowBit));
/*     */     
/* 308 */     leng = shaLow.length();
/* 309 */     if (leng > 4) {
/* 310 */       shaLow = shaLow.substring(leng - 4);
/*     */     }
/* 312 */     else if (leng < 4) {
/* 313 */       for (int m = 0; m < 4 - leng; m++)
/* 314 */         shaLow = "0" + shaLow;
/*     */     }
/* 316 */     return Long.parseLong(shaHigh + shaLow, 16);
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/encrypt/SHACode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */