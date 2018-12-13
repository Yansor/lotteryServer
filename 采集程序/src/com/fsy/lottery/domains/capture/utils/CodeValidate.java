/*     */ package com.fsy.lottery.domains.capture.utils;
/*     */ 
/*     */ import com.fsy.javautils.StringUtil;
/*     */ 
/*     */ public class CodeValidate {
/*     */   public static boolean validate(String lottery, String code) {
/*   7 */     switch (lottery) {
/*     */     case "cqssc": 
/*     */     case "xjssc": 
/*     */     case "tjssc": 
/*     */     case "tw5fc": 
/*     */     case "bj5fc": 
/*     */     case "jnd3d5fc": 
/*     */     case "txffc": 
/*  15 */       return isSsc(code);
/*     */     case "sd11x5": 
/*     */     case "gd11x5": 
/*     */     case "jx11x5": 
/*     */     case "ah11x5": 
/*  20 */       return is11x5(code);
/*     */     case "jsk3": 
/*     */     case "ahk3": 
/*     */     case "jlk3": 
/*     */     case "hbk3": 
/*     */     case "shk3": 
/*  26 */       return isK3(code);
/*     */     case "fc3d": 
/*     */     case "pl3": 
/*  29 */       return is3d(code);
/*     */     case "bjkl8": 
/*  31 */       return isBjkl8(code);
/*     */     case "bjpk10": 
/*  33 */       return isBjpk10(code);
/*     */     }
/*  35 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean isSsc(String s)
/*     */   {
/*  41 */     if (!StringUtil.isNotNull(s))
/*  42 */       return false;
/*  43 */     String[] codes = s.split(",");
/*     */     
/*  45 */     if (codes.length != 5)
/*  46 */       return false;
/*  47 */     for (String tmpS : codes)
/*     */     {
/*  49 */       if (!StringUtil.isInteger(tmpS)) {
/*  50 */         return false;
/*     */       }
/*  52 */       if (tmpS.length() != 1)
/*  53 */         return false;
/*  54 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/*  56 */       if ((tmpC < 0) || (tmpC > 9))
/*  57 */         return false;
/*     */     }
/*  59 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean is11x5(String s)
/*     */   {
/*  64 */     if (!StringUtil.isNotNull(s))
/*  65 */       return false;
/*  66 */     String[] codes = s.split(",");
/*     */     
/*  68 */     if (codes.length != 5)
/*  69 */       return false;
/*  70 */     for (String tmpS : codes)
/*     */     {
/*  72 */       if (!StringUtil.isInteger(tmpS)) {
/*  73 */         return false;
/*     */       }
/*  75 */       if (tmpS.length() != 2)
/*  76 */         return false;
/*  77 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/*  79 */       if ((tmpC < 1) || (tmpC > 11))
/*  80 */         return false;
/*     */     }
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isK3(String s)
/*     */   {
/*  87 */     if (!StringUtil.isNotNull(s))
/*  88 */       return false;
/*  89 */     String[] codes = s.split(",");
/*     */     
/*  91 */     if (codes.length != 3)
/*  92 */       return false;
/*  93 */     for (String tmpS : codes)
/*     */     {
/*  95 */       if (!StringUtil.isInteger(tmpS)) {
/*  96 */         return false;
/*     */       }
/*  98 */       if (tmpS.length() != 1)
/*  99 */         return false;
/* 100 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/* 102 */       if ((tmpC < 1) || (tmpC > 6))
/* 103 */         return false;
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean is3d(String s)
/*     */   {
/* 110 */     if (!StringUtil.isNotNull(s))
/* 111 */       return false;
/* 112 */     String[] codes = s.split(",");
/*     */     
/* 114 */     if (codes.length != 3)
/* 115 */       return false;
/* 116 */     for (String tmpS : codes)
/*     */     {
/* 118 */       if (!StringUtil.isInteger(tmpS)) {
/* 119 */         return false;
/*     */       }
/* 121 */       if (tmpS.length() != 1)
/* 122 */         return false;
/* 123 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/* 125 */       if ((tmpC < 0) || (tmpC > 9))
/* 126 */         return false;
/*     */     }
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isBjkl8(String s)
/*     */   {
/* 133 */     if (!StringUtil.isNotNull(s))
/* 134 */       return false;
/* 135 */     String[] codes = s.split(",");
/*     */     
/* 137 */     if (codes.length != 20)
/* 138 */       return false;
/* 139 */     for (String tmpS : codes)
/*     */     {
/* 141 */       if (!StringUtil.isInteger(tmpS)) {
/* 142 */         return false;
/*     */       }
/* 144 */       if (tmpS.length() != 2)
/* 145 */         return false;
/* 146 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/* 148 */       if ((tmpC < 1) || (tmpC > 80))
/* 149 */         return false;
/*     */     }
/* 151 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isBjpk10(String s)
/*     */   {
/* 156 */     if (!StringUtil.isNotNull(s))
/* 157 */       return false;
/* 158 */     String[] codes = s.split(",");
/*     */     
/* 160 */     if (codes.length != 10)
/* 161 */       return false;
/* 162 */     for (String tmpS : codes)
/*     */     {
/* 164 */       if (!StringUtil.isInteger(tmpS)) {
/* 165 */         return false;
/*     */       }
/* 167 */       if (tmpS.length() != 2)
/* 168 */         return false;
/* 169 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/* 171 */       if ((tmpC < 1) || (tmpC > 10))
/* 172 */         return false;
/*     */     }
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isKy481(String s)
/*     */   {
/* 179 */     if (!StringUtil.isNotNull(s))
/* 180 */       return false;
/* 181 */     String[] codes = s.split(",");
/*     */     
/* 183 */     if (codes.length != 4)
/* 184 */       return false;
/* 185 */     for (String tmpS : codes)
/*     */     {
/* 187 */       if (!StringUtil.isInteger(tmpS)) {
/* 188 */         return false;
/*     */       }
/* 190 */       if (tmpS.length() != 1)
/* 191 */         return false;
/* 192 */       int tmpC = Integer.parseInt(tmpS);
/*     */       
/* 194 */       if ((tmpC < 1) || (tmpC > 8))
/* 195 */         return false;
/*     */     }
/* 197 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/CodeValidate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */