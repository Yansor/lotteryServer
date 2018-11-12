package javautils.encrypt;

import lottery.domains.content.entity.UserBetsOriginal;
import org.apache.commons.lang.RandomStringUtils;

public class UserBetsEncrypt
{
    private static final DESUtil DES;
    private static final String DES_KEY = "#$ddw4FFWfg#GR0(DSFW@#?!@#!@#$C$$3GhyUhb";
    
    static {
        DES = DESUtil.getInstance();
    }
    
    public static String encryptCertification(final String certification) {
        return UserBetsEncrypt.DES.encryptStr(certification, "#$ddw4FFWfg#GR0(DSFW@#?!@#!@#$C$$3GhyUhb");
    }
    
    public static String decryptCertification(final String certification) {
        return UserBetsEncrypt.DES.decryptStr(certification, "#$ddw4FFWfg#GR0(DSFW@#?!@#!@#$C$$3GhyUhb");
    }
    
    public static String getRandomCertification() {
        return RandomStringUtils.random(10, true, true);
    }
    
    public static String getIdentification(final UserBetsOriginal original, final String certification) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/lang/StringBuffer.<init>:()V
        //     7: astore_2        /* sb */
        //     8: aload_0         /* original */
        //     9: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getPoint:()D
        //    12: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    15: invokevirtual   java/lang/Double.intValue:()I
        //    18: istore_3        /* point */
        //    19: aload_0         /* original */
        //    20: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getMoney:()D
        //    23: invokestatic    java/lang/Double.valueOf:(D)Ljava/lang/Double;
        //    26: invokevirtual   java/lang/Double.intValue:()I
        //    29: istore          money
        //    31: aload_2         /* sb */
        //    32: aload_0         /* original */
        //    33: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getId:()I
        //    36: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //    39: aload_0         /* original */
        //    40: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getBillno:()Ljava/lang/String;
        //    43: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    46: aload_0         /* original */
        //    47: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getUserId:()I
        //    50: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //    53: aload_0         /* original */
        //    54: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getType:()I
        //    57: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //    60: aload_0         /* original */
        //    61: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getLotteryId:()I
        //    64: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //    67: aload_0         /* original */
        //    68: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getExpect:()Ljava/lang/String;
        //    71: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    74: aload_0         /* original */
        //    75: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getRuleId:()I
        //    78: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //    81: aload_0         /* original */
        //    82: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getCodes:()Ljava/lang/String;
        //    85: invokestatic    org/apache/commons/codec/digest/DigestUtils.md5Hex:(Ljava/lang/String;)Ljava/lang/String;
        //    88: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //    91: aload_0         /* original */
        //    92: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getNums:()I
        //    95: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //    98: aload_0         /* original */
        //    99: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getModel:()Ljava/lang/String;
        //   102: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   105: aload_0         /* original */
        //   106: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getMultiple:()I
        //   109: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //   112: aload_0         /* original */
        //   113: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getCode:()I
        //   116: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //   119: iload_3         /* point */
        //   120: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //   123: iload           money
        //   125: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //   128: aload_0         /* original */
        //   129: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getTime:()Ljava/lang/String;
        //   132: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   135: aload_0         /* original */
        //   136: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getStopTime:()Ljava/lang/String;
        //   139: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   142: aload_0         /* original */
        //   143: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getOpenTime:()Ljava/lang/String;
        //   146: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   149: aload_0         /* original */
        //   150: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getStatus:()I
        //   153: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //   156: aload_0         /* original */
        //   157: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getOpenCode:()Ljava/lang/String;
        //   160: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   163: aload_0         /* original */
        //   164: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getPrizeMoney:()Ljava/lang/Double;
        //   167: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   170: aload_0         /* original */
        //   171: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getPrizeTime:()Ljava/lang/String;
        //   174: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   177: aload_0         /* original */
        //   178: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getChaseBillno:()Ljava/lang/String;
        //   181: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   184: aload_0         /* original */
        //   185: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getChaseBillno:()Ljava/lang/String;
        //   188: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   191: aload_0         /* original */
        //   192: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getPlanBillno:()Ljava/lang/String;
        //   195: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   198: aload_0         /* original */
        //   199: invokevirtual   lottery/domains/content/entity/UserBetsOriginal.getRewardMoney:()Ljava/lang/Double;
        //   202: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/Object;)Ljava/lang/StringBuffer;
        //   205: aload_1         /* certification */
        //   206: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   209: pop            
        //   210: aload_2         /* sb */
        //   211: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   214: astore          thisData
        //   216: new             Ljava/lang/StringBuilder;
        //   219: dup            
        //   220: aload           thisData
        //   222: invokestatic    java/lang/String.valueOf:(Ljava/lang/Object;)Ljava/lang/String;
        //   225: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   228: aload_1         /* certification */
        //   229: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   232: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   235: invokestatic    org/apache/commons/codec/digest/DigestUtils.md5Hex:(Ljava/lang/String;)Ljava/lang/String;
        //   238: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.StackOverflowError
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:770)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:766)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2463)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:801)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:694)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:571)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:538)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:141)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
