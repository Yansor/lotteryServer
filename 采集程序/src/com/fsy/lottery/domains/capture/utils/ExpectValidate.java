/*     */ package com.fsy.lottery.domains.capture.utils;
/*     */ 
/*     */ import com.fsy.javautils.StringUtil;
/*     */ 
/*     */ 
/*     */ public class ExpectValidate
/*     */ {
/*     */   public static boolean validate(String lottery, String expect)
/*     */   {
/*  10 */     switch (lottery) {
/*     */     case "cqssc": 
/*     */     case "xjssc": 
/*     */     case "tjssc": 
/*  14 */       return isSsc(expect);
/*     */     case "tw5fc": 
/*  16 */       return isTw5fc(expect);
/*     */     case "sd11x5": 
/*     */     case "gd11x5": 
/*     */     case "jx11x5": 
/*     */     case "ah11x5": 
/*  21 */       return is11x5(expect);
/*     */     case "jsk3": 
/*     */     case "ahk3": 
/*     */     case "jlk3": 
/*     */     case "hbk3": 
/*     */     case "shk3": 
/*  27 */       return isK3(expect);
/*     */     case "fc3d": 
/*     */     case "pl3": 
/*  30 */       return is3d(expect);
/*     */     case "bjkl8": 
/*     */     case "bj5fc": 
/*  33 */       return isBjkl8(expect);
/*     */     case "bjpk10": 
/*  35 */       return isBjpk10(expect);
/*     */     case "jnd3d5fc": 
/*  37 */       return isJnd3d5fc(expect);
/*     */     case "txffc": 
/*  39 */       return isTxffc(expect);
/*     */     }
/*  41 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean isSsc(String expect)
/*     */   {
/*  47 */     if (!StringUtil.isNotNull(expect))
/*  48 */       return false;
/*  49 */     if (expect.length() != 12)
/*  50 */       return false;
/*  51 */     String[] expects = expect.split("-");
/*     */     
/*  53 */     if (expects.length != 2) {
/*  54 */       return false;
/*     */     }
/*  56 */     String date = expects[0];
/*  57 */     String exp = expects[1];
/*     */     
/*  59 */     if (!StringUtil.isInteger(date))
/*  60 */       return false;
/*  61 */     if (!StringUtil.isInteger(exp))
/*  62 */       return false;
/*  63 */     if (date.length() != 8)
/*  64 */       return false;
/*  65 */     if (exp.length() != 3) {
/*  66 */       return false;
/*     */     }
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isTxffc(String expect)
/*     */   {
/*  73 */     if (!StringUtil.isNotNull(expect))
/*  74 */       return false;
/*  75 */     if (expect.length() != 13)
/*  76 */       return false;
/*  77 */     String[] expects = expect.split("-");
/*     */     
/*  79 */     if (expects.length != 2) {
/*  80 */       return false;
/*     */     }
/*  82 */     String date = expects[0];
/*  83 */     String exp = expects[1];
/*     */     
/*  85 */     if (!StringUtil.isInteger(date))
/*  86 */       return false;
/*  87 */     if (!StringUtil.isInteger(exp))
/*  88 */       return false;
/*  89 */     if (date.length() != 8)
/*  90 */       return false;
/*  91 */     if (exp.length() != 4) {
/*  92 */       return false;
/*     */     }
/*  94 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isTw5fc(String expect)
/*     */   {
/*  99 */     if (!StringUtil.isNotNull(expect))
/* 100 */       return false;
/* 101 */     if (!StringUtil.isInteger(expect))
/* 102 */       return false;
/* 103 */     if (expect.length() != 9) {
/* 104 */       return false;
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean is11x5(String expect)
/*     */   {
/* 111 */     if (!StringUtil.isNotNull(expect))
/* 112 */       return false;
/* 113 */     if (expect.length() != 12)
/* 114 */       return false;
/* 115 */     String[] expects = expect.split("-");
/*     */     
/* 117 */     if (expects.length != 2) {
/* 118 */       return false;
/*     */     }
/* 120 */     String date = expects[0];
/* 121 */     String exp = expects[1];
/*     */     
/* 123 */     if (!StringUtil.isInteger(date))
/* 124 */       return false;
/* 125 */     if (!StringUtil.isInteger(exp))
/* 126 */       return false;
/* 127 */     if (date.length() != 8)
/* 128 */       return false;
/* 129 */     if (exp.length() != 3) {
/* 130 */       return false;
/*     */     }
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isK3(String expect)
/*     */   {
/* 137 */     if (!StringUtil.isNotNull(expect))
/* 138 */       return false;
/* 139 */     if (expect.length() != 12)
/* 140 */       return false;
/* 141 */     String[] expects = expect.split("-");
/*     */     
/* 143 */     if (expects.length != 2) {
/* 144 */       return false;
/*     */     }
/* 146 */     String date = expects[0];
/* 147 */     String exp = expects[1];
/*     */     
/* 149 */     if (!StringUtil.isInteger(date))
/* 150 */       return false;
/* 151 */     if (!StringUtil.isInteger(exp))
/* 152 */       return false;
/* 153 */     if (date.length() != 8)
/* 154 */       return false;
/* 155 */     if (exp.length() != 3) {
/* 156 */       return false;
/*     */     }
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean is3d(String expect)
/*     */   {
/* 163 */     if (!StringUtil.isNotNull(expect))
/* 164 */       return false;
/* 165 */     if (!StringUtil.isInteger(expect))
/* 166 */       return false;
/* 167 */     if (expect.length() != 5) {
/* 168 */       return false;
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isBjkl8(String expect)
/*     */   {
/* 175 */     if (!StringUtil.isNotNull(expect))
/* 176 */       return false;
/* 177 */     if (!StringUtil.isInteger(expect))
/* 178 */       return false;
/* 179 */     if (expect.length() != 6) {
/* 180 */       return false;
/*     */     }
/* 182 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isBjpk10(String expect)
/*     */   {
/* 187 */     if (!StringUtil.isNotNull(expect))
/* 188 */       return false;
/* 189 */     if (!StringUtil.isInteger(expect))
/* 190 */       return false;
/* 191 */     if (expect.length() != 6) {
/* 192 */       return false;
/*     */     }
/* 194 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isJnd3d5fc(String expect)
/*     */   {
/* 199 */     if (!StringUtil.isNotNull(expect))
/* 200 */       return false;
/* 201 */     if (!StringUtil.isInteger(expect))
/* 202 */       return false;
/* 203 */     if (expect.length() != 7) {
/* 204 */       return false;
/*     */     }
/* 206 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/capture/utils/ExpectValidate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */