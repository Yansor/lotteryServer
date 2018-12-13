/*     */ package lottery.domains.open.utils.ssc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Random;
/*     */ import javautils.array.ArrayUtils;
/*     */ import org.springframework.beans.factory.InitializingBean;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class LotteryCodeUtils implements InitializingBean
/*     */ {
/*  14 */   private static LinkedList<String> CODES_SSC = new LinkedList();
/*  15 */   private static LinkedList<String> CODES_11X5 = new LinkedList();
/*  16 */   private static LinkedList<String> CODES_K3 = new LinkedList();
/*  17 */   private static LinkedList<String> CODES_3D = new LinkedList();
/*  18 */   private static LinkedList<String> CODES_PK10 = new LinkedList();
/*     */   
/*     */   public void afterPropertiesSet() throws Exception
/*     */   {
/*  22 */     generateSSCCodes();
/*  23 */     generate11X5Codes();
/*  24 */     generateK3Codes();
/*  25 */     generate3DCodes();
/*  26 */     generatepk10Codes();
/*     */   }
/*     */   
/*     */   public LinkedList<String> getSSCCodes() {
/*  30 */     LinkedList<String> result = new LinkedList();
/*  31 */     result.addAll(CODES_SSC);
/*  32 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String randomSSC()
/*     */   {
/*  39 */     Random random = new Random();
/*  40 */     int index = random.nextInt(CODES_SSC.size());
/*  41 */     return (String)CODES_SSC.get(index);
/*     */   }
/*     */   
/*     */   public LinkedList<String> get11X5Codes() {
/*  45 */     LinkedList<String> result = new LinkedList();
/*  46 */     result.addAll(CODES_11X5);
/*  47 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String random11X5()
/*     */   {
/*  54 */     Random random = new Random();
/*  55 */     int index = random.nextInt(CODES_11X5.size());
/*  56 */     return (String)CODES_11X5.get(index);
/*     */   }
/*     */   
/*     */   public LinkedList<String> getK3Codes() {
/*  60 */     LinkedList<String> result = new LinkedList();
/*  61 */     result.addAll(CODES_K3);
/*  62 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String randomK3()
/*     */   {
/*  69 */     Random random = new Random();
/*  70 */     int index = random.nextInt(CODES_K3.size());
/*  71 */     return (String)CODES_K3.get(index);
/*     */   }
/*     */   
/*     */   public LinkedList<String> get3DCodes() {
/*  75 */     LinkedList<String> result = new LinkedList();
/*  76 */     result.addAll(CODES_3D);
/*  77 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String random3D()
/*     */   {
/*  84 */     Random random = new Random();
/*  85 */     int index = random.nextInt(CODES_3D.size());
/*  86 */     return (String)CODES_3D.get(index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String randompk10()
/*     */   {
/*  93 */     Random random = new Random();
/*  94 */     int index = random.nextInt(CODES_PK10.size());
/*  95 */     return (String)CODES_PK10.get(index);
/*     */   }
/*     */   
/*     */   public LinkedList<String> getpk10Codes() {
/*  99 */     LinkedList<String> result = new LinkedList();
/* 100 */     result.addAll(CODES_PK10);
/* 101 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generateSSCCodes()
/*     */   {
/* 108 */     CODES_SSC = new LinkedList();
/* 109 */     LinkedHashSet<String> codesSet = new LinkedHashSet();
/*     */     
/* 111 */     String[] numCodes = "0,1,2,3,4,5,6,7,8,9".split(",");
/*     */     
/* 113 */     for (int i = 0; i < numCodes.length; i++) {
/* 114 */       for (int j = 0; j < numCodes.length; j++) {
/* 115 */         for (int k = 0; k < numCodes.length; k++) {
/* 116 */           for (int l = 0; l < numCodes.length; l++) {
/* 117 */             for (int m = 0; m < numCodes.length; m++) {
/* 118 */               String num = numCodes[i] + "," + numCodes[j] + "," + numCodes[k] + "," + numCodes[l] + "," + numCodes[m];
/*     */               
/* 120 */               codesSet.add(num);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 127 */     CODES_SSC.addAll(codesSet);
/* 128 */     codesSet.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generate11X5Codes()
/*     */   {
/* 135 */     CODES_11X5 = new LinkedList();
/* 136 */     LinkedHashSet<String> codesSet = new LinkedHashSet();
/*     */     
/* 138 */     String[] numCodes = "01,02,03,04,05,06,07,08,09,10,11".split(",");
/*     */     
/* 140 */     for (int i = 0; i < numCodes.length; i++) {
/* 141 */       for (int j = 0; j < numCodes.length; j++) {
/* 142 */         for (int k = 0; k < numCodes.length; k++) {
/* 143 */           for (int l = 0; l < numCodes.length; l++) {
/* 144 */             for (int m = 0; m < numCodes.length; m++)
/*     */             {
/* 146 */               String num1 = numCodes[i];
/* 147 */               String num2 = numCodes[j];
/* 148 */               String num3 = numCodes[k];
/* 149 */               String num4 = numCodes[l];
/* 150 */               String num5 = numCodes[m];
/*     */               
/* 152 */               String[] nums = { num1, num2, num3, num4, num5 };
/*     */               
/* 154 */               if (!ArrayUtils.hasSame(nums)) {
/* 155 */                 String num = num1 + "," + num2 + "," + num3 + "," + num4 + "," + num5;
/* 156 */                 codesSet.add(num);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 164 */     CODES_11X5.addAll(codesSet);
/* 165 */     codesSet.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generateK3Codes()
/*     */   {
/* 172 */     CODES_K3 = new LinkedList();
/* 173 */     LinkedHashSet<String> codesSet = new LinkedHashSet();
/*     */     
/* 175 */     String[] numCodes = "1,2,3,4,5,6".split(",");
/*     */     
/* 177 */     for (int i = 0; i < numCodes.length; i++) {
/* 178 */       for (int j = 0; j < numCodes.length; j++) {
/* 179 */         for (int k = 0; k < numCodes.length; k++) {
/* 180 */           String num = numCodes[i] + "," + numCodes[j] + "," + numCodes[k];
/* 181 */           codesSet.add(num);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 186 */     CODES_K3.addAll(codesSet);
/* 187 */     codesSet.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generate3DCodes()
/*     */   {
/* 194 */     CODES_3D = new LinkedList();
/* 195 */     LinkedHashSet<String> codesSet = new LinkedHashSet();
/*     */     
/* 197 */     String[] numCodes = "0,1,2,3,4,5,6,7,8,9".split(",");
/*     */     
/* 199 */     for (int i = 0; i < numCodes.length; i++) {
/* 200 */       for (int j = 0; j < numCodes.length; j++) {
/* 201 */         for (int k = 0; k < numCodes.length; k++) {
/* 202 */           String num = numCodes[i] + "," + numCodes[j] + "," + numCodes[k];
/* 203 */           codesSet.add(num);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 208 */     CODES_3D.addAll(codesSet);
/* 209 */     codesSet.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void generatepk10Codes()
/*     */   {
/* 216 */     CODES_PK10 = new LinkedList();
/* 217 */     LinkedHashSet<String> codesSet = new LinkedHashSet();
/*     */     
/* 219 */     for (int i = 1; i <= 1728; i++) {
/* 220 */       int[] result = new int[10];
/* 221 */       int count = 0;
/* 222 */       boolean flag; int j; while (count < 10) {
/* 223 */         int num = (int)(Math.random() * 10.0D) + 1;
/* 224 */         flag = true;
/* 225 */         for (j = 0; j < 10; j++) {
/* 226 */           if (num == result[j]) {
/* 227 */             flag = false;
/* 228 */             break;
/*     */           }
/*     */         }
/* 231 */         if (flag) {
/* 232 */           result[count] = num;
/* 233 */           count++;
/*     */         }
/*     */       }
/* 236 */       String num = "";
/* 237 */       for ( int m : result) {
/* 238 */         if (m < 10) {
/* 239 */           num = num + "0" + m + ",";
/*     */         } else {
/* 241 */           num = num + m + ",";
/*     */         }
/*     */       }
/* 244 */       num = num.substring(0, num.length() - 1);
/* 245 */       codesSet.add(num);
/*     */     }
/*     */     
/* 248 */     CODES_PK10.addAll(codesSet);
/* 249 */     codesSet.clear();
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 253 */     LotteryCodeUtils utils = new LotteryCodeUtils();
/* 254 */     utils.generateSSCCodes();
/* 255 */     LinkedList<String> sscCodes = utils.getSSCCodes();
/* 256 */     System.out.println("时时彩所有开奖号码共" + sscCodes.size() + "注");
/* 257 */     System.out.println("时时彩所有开奖号码10线程每线程计算" + sscCodes.size() / 10 + "注");
/*     */     
/* 259 */     utils.generate11X5Codes();
/* 260 */     LinkedList<String> x5Codes = utils.get11X5Codes();
/* 261 */     System.out.println("11选5所有开奖号码共" + x5Codes.size() + "注");
/* 262 */     System.out.println("11选5所有开奖号码10线程每线程使用" + x5Codes.size() / 10 + "个线程");
/*     */     
/* 264 */     utils.generateK3Codes();
/* 265 */     LinkedList<String> k3Codes = utils.getK3Codes();
/* 266 */     System.out.println("快3所有开奖号码共" + k3Codes.size() + "注");
/* 267 */     System.out.println("快3所有开奖号码10线程每线程使用" + k3Codes.size() / 10 + "个线程");
/*     */     
/* 269 */     utils.generate3DCodes();
/* 270 */     LinkedList<String> d3Codes = utils.get3DCodes();
/* 271 */     System.out.println("3D所有开奖号码共" + d3Codes.size() + "注");
/* 272 */     System.out.println("3D所有开奖号码10线程每线程使用" + d3Codes.size() / 10 + "个线程");
/*     */     
/* 274 */     utils.generatepk10Codes();
/* 275 */     LinkedList<String> pk10Codes = utils.getpk10Codes();
/* 276 */     System.out.println("PK10所有开奖号码共" + pk10Codes.size() + "注");
/* 277 */     System.out.println("PK10所有开奖号码10线程每线程使用" + pk10Codes.size() / 10 + "个线程");
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryOpen/lotteryOpen.jar!/lottery/domains/open/utils/ssc/LotteryCodeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */