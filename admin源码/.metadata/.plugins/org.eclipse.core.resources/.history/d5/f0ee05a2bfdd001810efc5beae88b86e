package lottery.domains.content.payment.RX;

import org.apache.commons.lang.StringUtils;
import javautils.http.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lottery.domains.content.payment.RX.utils.Base64Utils;
import lottery.domains.content.payment.RX.utils.RSAEncrypt;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import admin.web.WebJSONObject;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;
import lottery.domains.content.AbstractPayment;

@Component
public class RXPayment extends AbstractPayment implements InitializingBean
{
    private static Map<Integer, String> BRANCH_IDS;
    public static final String NONE_NOTIFY_URL = "http://www.baidu.com";
    @Value("${rx.daifu.url}")
    private String daifuUrl;
    @Value("${rx.daifu.queryurl}")
    private String daifuQueryUrl;
    
    static {
        RXPayment.BRANCH_IDS = new HashMap<Integer, String>();
    }
    
    public void afterPropertiesSet() throws Exception {
        RXPayment.BRANCH_IDS.put(1, "01000017");
        RXPayment.BRANCH_IDS.put(2, "01050000");
        RXPayment.BRANCH_IDS.put(3, "01000001");
        RXPayment.BRANCH_IDS.put(4, "01000010");
        RXPayment.BRANCH_IDS.put(5, "01000002");
        RXPayment.BRANCH_IDS.put(6, "01000003");
        RXPayment.BRANCH_IDS.put(7, "01000012");
        RXPayment.BRANCH_IDS.put(8, "01000011");
        RXPayment.BRANCH_IDS.put(9, "01000004");
        RXPayment.BRANCH_IDS.put(13, "01000015");
        RXPayment.BRANCH_IDS.put(14, "01000014");
        RXPayment.BRANCH_IDS.put(15, "01000008");
        RXPayment.BRANCH_IDS.put(16, "01000000");
        RXPayment.BRANCH_IDS.put(18, "01000007");
        RXPayment.BRANCH_IDS.put(19, "01000005");
        RXPayment.BRANCH_IDS.put(20, "01000006");
        RXPayment.BRANCH_IDS.put(23, "01000009");
    }
    
    @Override
    public String daifu(final WebJSONObject json, final UserWithdraw order, final UserCard card, final PaymentChannelBank bank, final PaymentChannel channel) {
        try {
            this.logStart(order, bank, channel);
            return this.daifuInternal(json, order, card, bank, channel);
        }
        catch (Exception e) {
            this.logException(order, bank, channel, "代付请求失败", e);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    private String daifuInternal(final WebJSONObject json, final UserWithdraw order, final UserCard card, final PaymentChannelBank bank, final PaymentChannel channel) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_3         /* card */
        //     4: invokevirtual   lottery/domains/content/entity/UserCard.getBankId:()I
        //     7: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    10: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    15: checkcast       Ljava/lang/String;
        //    18: astore          bankCode
        //    20: aload           bankCode
        //    22: ifnonnull       37
        //    25: aload_1         /* json */
        //    26: iconst_2       
        //    27: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    30: ldc             "2-4012"
        //    32: invokevirtual   admin/web/WebJSONObject.set:(Ljava/lang/Integer;Ljava/lang/String;)V
        //    35: aconst_null    
        //    36: areturn        
        //    37: aload_2         /* order */
        //    38: invokevirtual   lottery/domains/content/entity/UserWithdraw.getBillno:()Ljava/lang/String;
        //    41: astore          orderId
        //    43: new             Ljava/lang/StringBuilder;
        //    46: dup            
        //    47: aload_2         /* order */
        //    48: invokevirtual   lottery/domains/content/entity/UserWithdraw.getRecMoney:()D
        //    51: invokestatic    java/lang/String.valueOf:(D)Ljava/lang/String;
        //    54: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    57: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    60: invokestatic    lottery/domains/content/payment/zs/utils/StringUtil.changeY2F:(Ljava/lang/String;)Ljava/lang/Long;
        //    63: astore          totalAmount
        //    65: aload           channel
        //    67: invokevirtual   lottery/domains/content/entity/PaymentChannel.getMerCode:()Ljava/lang/String;
        //    70: astore          merchantCode
        //    72: aload_2         /* order */
        //    73: invokevirtual   lottery/domains/content/entity/UserWithdraw.getCardId:()Ljava/lang/String;
        //    76: astore          intoCardNo
        //    78: aload_2         /* order */
        //    79: invokevirtual   lottery/domains/content/entity/UserWithdraw.getCardName:()Ljava/lang/String;
        //    82: astore          intoCardName
        //    84: ldc             "ToPay"
        //    86: astore          type
        //    88: new             Ljava/lang/StringBuilder;
        //    91: dup            
        //    92: ldc             "{\"accName\":\""
        //    94: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    97: aload           intoCardName
        //    99: ldc             "utf-8"
        //   101: invokestatic    java/net/URLEncoder.encode:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   104: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   107: ldc             "\","
        //   109: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   112: ldc             "\"accNo\":\""
        //   114: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   117: aload           intoCardNo
        //   119: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   122: ldc             "\","
        //   124: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   127: ldc             "\"account\":\""
        //   129: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   132: aload           merchantCode
        //   134: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   137: ldc             "\","
        //   139: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   142: ldc             "\"amount\":\""
        //   144: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   147: aload           totalAmount
        //   149: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   152: ldc             "\","
        //   154: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   157: ldc             "\"banktype\":\""
        //   159: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   162: aload           bankCode
        //   164: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   167: ldc             "\","
        //   169: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   172: ldc             "\"notify_url\":\""
        //   174: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   177: ldc             "http://www.baidu.com"
        //   179: ldc             "utf-8"
        //   181: invokestatic    java/net/URLEncoder.encode:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   184: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   187: ldc             "\","
        //   189: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   192: ldc             "\"orderId\":\""
        //   194: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   197: aload           orderId
        //   199: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   202: ldc             "\","
        //   204: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   207: ldc             "\"type\":\""
        //   209: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   212: aload           type
        //   214: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   217: ldc             "\""
        //   219: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   222: ldc             "}"
        //   224: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   227: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   230: astore          data
        //   232: aload           channel
        //   234: invokevirtual   lottery/domains/content/entity/PaymentChannel.getRsaPlatformPublicKey:()Ljava/lang/String;
        //   237: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.loadPublicKeyByStr:(Ljava/lang/String;)Ljava/security/interfaces/RSAPublicKey;
        //   240: aload           data
        //   242: invokevirtual   java/lang/String.getBytes:()[B
        //   245: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.encrypt:(Ljava/security/interfaces/RSAPublicKey;[B)[B
        //   248: astore          cipherData
        //   250: aload           cipherData
        //   252: invokestatic    lottery/domains/content/payment/RX/utils/Base64Utils.encode:([B)Ljava/lang/String;
        //   255: astore          cipher
        //   257: aload           channel
        //   259: invokevirtual   lottery/domains/content/entity/PaymentChannel.getRsaPrivateKey:()Ljava/lang/String;
        //   262: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.loadPrivateKeyByStr:(Ljava/lang/String;)Ljava/security/interfaces/RSAPrivateKey;
        //   265: aload           data
        //   267: invokevirtual   java/lang/String.getBytes:()[B
        //   270: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.encrypt:(Ljava/security/interfaces/RSAPrivateKey;[B)[B
        //   273: astore          cipherData
        //   275: aload           cipherData
        //   277: invokestatic    lottery/domains/content/payment/RX/utils/Base64Utils.encode:([B)Ljava/lang/String;
        //   280: astore          signature
        //   282: new             Lcom/alibaba/fastjson/JSONObject;
        //   285: dup            
        //   286: invokespecial   com/alibaba/fastjson/JSONObject.<init>:()V
        //   289: astore          params
        //   291: aload           params
        //   293: ldc             "data"
        //   295: aload           cipher
        //   297: invokevirtual   com/alibaba/fastjson/JSONObject.put:(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
        //   300: pop            
        //   301: aload           params
        //   303: ldc             "signature"
        //   305: aload           signature
        //   307: invokevirtual   com/alibaba/fastjson/JSONObject.put:(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;
        //   310: pop            
        //   311: aload_0         /* this */
        //   312: getfield        lottery/domains/content/payment/RX/RXPayment.daifuUrl:Ljava/lang/String;
        //   315: aload           params
        //   317: invokestatic    com/alibaba/fastjson/JSON.toJSONString:(Ljava/lang/Object;)Ljava/lang/String;
        //   320: aconst_null    
        //   321: sipush          10000
        //   324: invokestatic    javautils/http/HttpClientUtil.postAsStream:(Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;I)Ljava/lang/String;
        //   327: astore          retStr
        //   329: aload           retStr
        //   331: invokestatic    org/apache/commons/lang/StringUtils.isEmpty:(Ljava/lang/String;)Z
        //   334: ifeq            362
        //   337: aload_0         /* this */
        //   338: aload_2         /* order */
        //   339: aload           bank
        //   341: aload           channel
        //   343: ldc_w           "\u63a5\u53e3\u8fd4\u56de\u7a7a\uff0c\u53ef\u80fd\u662f\u8bf7\u6c42\u8d85\u65f6"
        //   346: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logError:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   349: aload_1         /* json */
        //   350: iconst_m1      
        //   351: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   354: ldc_w           "-1"
        //   357: invokevirtual   admin/web/WebJSONObject.set:(Ljava/lang/Integer;Ljava/lang/String;)V
        //   360: aconst_null    
        //   361: areturn        
        //   362: aload           retStr
        //   364: invokestatic    com/alibaba/fastjson/JSONObject.parseObject:(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject;
        //   367: astore          resp
        //   369: aload           resp
        //   371: ldc             "data"
        //   373: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   376: astore          retData
        //   378: aload           resp
        //   380: ldc             "signature"
        //   382: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   385: astore          retSignature
        //   387: aload           resp
        //   389: ldc_w           "state"
        //   392: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   395: astore          reqstate
        //   397: aload           resp
        //   399: ldc_w           "message"
        //   402: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   405: astore          reqmessage
        //   407: aload           reqstate
        //   409: invokestatic    org/apache/commons/lang/StringUtils.isNotEmpty:(Ljava/lang/String;)Z
        //   412: ifeq            479
        //   415: aload_0         /* this */
        //   416: aload_2         /* order */
        //   417: aload           bank
        //   419: aload           channel
        //   421: new             Ljava/lang/StringBuilder;
        //   424: dup            
        //   425: ldc_w           "\u8bf7\u6c42\u5931\u8d25\uff0c\u8fd4\u56de\u6570\u636e\u4e3a\uff1a"
        //   428: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   431: aload           retStr
        //   433: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   436: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   439: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logError:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   442: aload_0         /* this */
        //   443: aload           retStr
        //   445: aload           reqmessage
        //   447: invokespecial   lottery/domains/content/payment/RX/RXPayment.transferErrorMsg:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   450: astore          msg
        //   452: aload_1         /* json */
        //   453: iconst_2       
        //   454: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   457: ldc_w           "2-4002"
        //   460: iconst_1       
        //   461: anewarray       Ljava/lang/Object;
        //   464: dup            
        //   465: iconst_0       
        //   466: aload           msg
        //   468: bipush          20
        //   470: invokestatic    org/apache/commons/lang/StringUtils.abbreviate:(Ljava/lang/String;I)Ljava/lang/String;
        //   473: aastore        
        //   474: invokevirtual   admin/web/WebJSONObject.setWithParams:(Ljava/lang/Integer;Ljava/lang/String;[Ljava/lang/Object;)V
        //   477: aconst_null    
        //   478: areturn        
        //   479: aload           retSignature
        //   481: invokestatic    org/apache/commons/lang/StringUtils.isEmpty:(Ljava/lang/String;)Z
        //   484: ifne            495
        //   487: aload           retData
        //   489: invokestatic    org/apache/commons/lang/StringUtils.isEmpty:(Ljava/lang/String;)Z
        //   492: ifeq            559
        //   495: aload_0         /* this */
        //   496: aload_2         /* order */
        //   497: aload           bank
        //   499: aload           channel
        //   501: new             Ljava/lang/StringBuilder;
        //   504: dup            
        //   505: ldc_w           "\u8bf7\u6c42\u5931\u8d25\uff0c\u8fd4\u56de\u6570\u636e\u4e3a\uff1a"
        //   508: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   511: aload           retStr
        //   513: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   516: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   519: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logError:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   522: aload_0         /* this */
        //   523: aload           retStr
        //   525: aload           reqmessage
        //   527: invokespecial   lottery/domains/content/payment/RX/RXPayment.transferErrorMsg:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   530: astore          msg
        //   532: aload_1         /* json */
        //   533: iconst_2       
        //   534: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   537: ldc_w           "2-4002"
        //   540: iconst_1       
        //   541: anewarray       Ljava/lang/Object;
        //   544: dup            
        //   545: iconst_0       
        //   546: aload           msg
        //   548: bipush          20
        //   550: invokestatic    org/apache/commons/lang/StringUtils.abbreviate:(Ljava/lang/String;I)Ljava/lang/String;
        //   553: aastore        
        //   554: invokevirtual   admin/web/WebJSONObject.setWithParams:(Ljava/lang/Integer;Ljava/lang/String;[Ljava/lang/Object;)V
        //   557: aconst_null    
        //   558: areturn        
        //   559: aload           channel
        //   561: invokevirtual   lottery/domains/content/entity/PaymentChannel.getRsaPrivateKey:()Ljava/lang/String;
        //   564: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.loadPrivateKeyByStr:(Ljava/lang/String;)Ljava/security/interfaces/RSAPrivateKey;
        //   567: aload           retData
        //   569: invokestatic    lottery/domains/content/payment/RX/utils/Base64.decode:(Ljava/lang/String;)[B
        //   572: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.decrypt:(Ljava/security/interfaces/RSAPrivateKey;[B)[B
        //   575: astore          res
        //   577: new             Ljava/lang/String;
        //   580: dup            
        //   581: aload           res
        //   583: invokespecial   java/lang/String.<init>:([B)V
        //   586: astore          restr
        //   588: aload           restr
        //   590: aload           retSignature
        //   592: invokestatic    lottery/domains/content/payment/RX/utils/Base64.decode:(Ljava/lang/String;)[B
        //   595: aload           channel
        //   597: invokevirtual   lottery/domains/content/entity/PaymentChannel.getRsaPlatformPublicKey:()Ljava/lang/String;
        //   600: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.loadPublicKeyByStr:(Ljava/lang/String;)Ljava/security/interfaces/RSAPublicKey;
        //   603: invokestatic    lottery/domains/content/payment/RX/utils/RSAEncrypt.publicsign:(Ljava/lang/String;[BLjava/security/interfaces/RSAPublicKey;)Z
        //   606: istore          sign
        //   608: aload           restr
        //   610: invokestatic    com/alibaba/fastjson/JSON.parseObject:(Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject;
        //   613: astore          jsonObject
        //   615: aload           jsonObject
        //   617: ldc_w           "orderId"
        //   620: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   623: astore          resorderId
        //   625: aload           jsonObject
        //   627: ldc_w           "state"
        //   630: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   633: astore          state
        //   635: aload           jsonObject
        //   637: ldc_w           "message"
        //   640: invokevirtual   com/alibaba/fastjson/JSONObject.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   643: astore          message
        //   645: aload_0         /* this */
        //   646: aload           state
        //   648: invokespecial   lottery/domains/content/payment/RX/RXPayment.isSuccessDaifuState:(Ljava/lang/String;)Z
        //   651: ifeq            720
        //   654: aload           resorderId
        //   656: invokestatic    org/apache/commons/lang/StringUtils.isNotEmpty:(Ljava/lang/String;)Z
        //   659: ifeq            720
        //   662: iload           sign
        //   664: ifne            708
        //   667: aload_0         /* this */
        //   668: aload_2         /* order */
        //   669: aload           bank
        //   671: aload           channel
        //   673: new             Ljava/lang/StringBuilder;
        //   676: dup            
        //   677: ldc_w           "\u8bf7\u6c42\u6210\u529f\uff0c\u4f46\u6570\u636e\u9a8c\u7b7e\u5931\u8d25\uff0c\u8fd4\u56de\u6570\u636e\uff1a"
        //   680: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   683: aload           retStr
        //   685: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   688: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   691: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logWarn:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   694: aload_1         /* json */
        //   695: iconst_2       
        //   696: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   699: ldc_w           "2-4008"
        //   702: invokevirtual   admin/web/WebJSONObject.set:(Ljava/lang/Integer;Ljava/lang/String;)V
        //   705: aload           resorderId
        //   707: areturn        
        //   708: aload_0         /* this */
        //   709: aload_2         /* order */
        //   710: aload           resorderId
        //   712: aload           channel
        //   714: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logSuccess:(Llottery/domains/content/entity/UserWithdraw;Ljava/lang/String;Llottery/domains/content/entity/PaymentChannel;)V
        //   717: aload           resorderId
        //   719: areturn        
        //   720: aload_0         /* this */
        //   721: aload_2         /* order */
        //   722: aload           bank
        //   724: aload           channel
        //   726: new             Ljava/lang/StringBuilder;
        //   729: dup            
        //   730: ldc_w           "\u8bf7\u6c42\u8fd4\u56de\u72b6\u6001\u8868\u793a\u5931\u8d25\uff0c\u8fd4\u56de\u6570\u636e\uff1a"
        //   733: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   736: aload           retStr
        //   738: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   741: ldc_w           "\uff0c\u5f00\u59cb\u67e5\u8be2\u8ba2\u5355\u72b6\u6001"
        //   744: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   747: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   750: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logError:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   753: aload_0         /* this */
        //   754: aload_2         /* order */
        //   755: aload           channel
        //   757: invokevirtual   lottery/domains/content/payment/RX/RXPayment.query:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannel;)Llottery/domains/content/payment/RX/RXDaifuQueryResult;
        //   760: astore          queryResult
        //   762: aload_0         /* this */
        //   763: aload           queryResult
        //   765: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getOrderId_state:()Ljava/lang/String;
        //   768: invokespecial   lottery/domains/content/payment/RX/RXPayment.isSuccessDaifuQueryState:(Ljava/lang/String;)Z
        //   771: ifeq            817
        //   774: ldc_w           "61"
        //   777: aload           queryResult
        //   779: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getState:()Ljava/lang/String;
        //   782: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   785: ifeq            817
        //   788: aload           queryResult
        //   790: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getOrderId:()Ljava/lang/String;
        //   793: invokestatic    org/apache/commons/lang/StringUtils.isNotEmpty:(Ljava/lang/String;)Z
        //   796: ifeq            817
        //   799: aload_0         /* this */
        //   800: aload_2         /* order */
        //   801: aload           queryResult
        //   803: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getOrderId:()Ljava/lang/String;
        //   806: aload           channel
        //   808: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logSuccess:(Llottery/domains/content/entity/UserWithdraw;Ljava/lang/String;Llottery/domains/content/entity/PaymentChannel;)V
        //   811: aload           queryResult
        //   813: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getOrderId:()Ljava/lang/String;
        //   816: areturn        
        //   817: aload_0         /* this */
        //   818: aload           queryResult
        //   820: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getOrderId_state:()Ljava/lang/String;
        //   823: invokespecial   lottery/domains/content/payment/RX/RXPayment.isSuccessDaifuQueryState:(Ljava/lang/String;)Z
        //   826: ifne            889
        //   829: aload_0         /* this */
        //   830: aload           queryResult
        //   832: invokevirtual   lottery/domains/content/payment/RX/RXDaifuQueryResult.getOrderId_state:()Ljava/lang/String;
        //   835: invokespecial   lottery/domains/content/payment/RX/RXPayment.getDaifuQueryStateStr:(Ljava/lang/String;)Ljava/lang/String;
        //   838: astore          stateStr
        //   840: aload_0         /* this */
        //   841: aload_2         /* order */
        //   842: aload           bank
        //   844: aload           channel
        //   846: new             Ljava/lang/StringBuilder;
        //   849: dup            
        //   850: ldc_w           "\u8bf7\u6c42\u5931\u8d25\uff0c\u8fd4\u56de\u6570\u636e\u4e3a\uff1a"
        //   853: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   856: aload           retStr
        //   858: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   861: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   864: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logError:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   867: aload_1         /* json */
        //   868: iconst_2       
        //   869: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   872: ldc_w           "2-4002"
        //   875: iconst_1       
        //   876: anewarray       Ljava/lang/Object;
        //   879: dup            
        //   880: iconst_0       
        //   881: aload           stateStr
        //   883: aastore        
        //   884: invokevirtual   admin/web/WebJSONObject.setWithParams:(Ljava/lang/Integer;Ljava/lang/String;[Ljava/lang/Object;)V
        //   887: aconst_null    
        //   888: areturn        
        //   889: aload_0         /* this */
        //   890: aload_2         /* order */
        //   891: aload           bank
        //   893: aload           channel
        //   895: new             Ljava/lang/StringBuilder;
        //   898: dup            
        //   899: ldc_w           "\u8bf7\u6c42\u5931\u8d25\uff0c\u8fd4\u56de\u6570\u636e\u4e3a\uff1a"
        //   902: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   905: aload           retStr
        //   907: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   910: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   913: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logError:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;)V
        //   916: aload_0         /* this */
        //   917: aload           restr
        //   919: aload           message
        //   921: invokespecial   lottery/domains/content/payment/RX/RXPayment.transferErrorMsg:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   924: astore          msg
        //   926: aload_1         /* json */
        //   927: iconst_2       
        //   928: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   931: ldc_w           "2-4002"
        //   934: iconst_1       
        //   935: anewarray       Ljava/lang/Object;
        //   938: dup            
        //   939: iconst_0       
        //   940: aload           msg
        //   942: bipush          20
        //   944: invokestatic    org/apache/commons/lang/StringUtils.abbreviate:(Ljava/lang/String;I)Ljava/lang/String;
        //   947: aastore        
        //   948: invokevirtual   admin/web/WebJSONObject.setWithParams:(Ljava/lang/Integer;Ljava/lang/String;[Ljava/lang/Object;)V
        //   951: aconst_null    
        //   952: areturn        
        //   953: astore          e
        //   955: aload_0         /* this */
        //   956: aload_2         /* order */
        //   957: aload           bank
        //   959: aload           channel
        //   961: ldc             "\u4ee3\u4ed8\u8bf7\u6c42\u5931\u8d25"
        //   963: aload           e
        //   965: invokevirtual   lottery/domains/content/payment/RX/RXPayment.logException:(Llottery/domains/content/entity/UserWithdraw;Llottery/domains/content/entity/PaymentChannelBank;Llottery/domains/content/entity/PaymentChannel;Ljava/lang/String;Ljava/lang/Exception;)V
        //   968: aload_1         /* json */
        //   969: iconst_m1      
        //   970: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   973: ldc_w           "-1"
        //   976: invokevirtual   admin/web/WebJSONObject.set:(Ljava/lang/Integer;Ljava/lang/String;)V
        //   979: aconst_null    
        //   980: areturn        
        //    StackMapTable: 00 0A FC 00 25 07 00 86 FF 01 44 00 13 07 00 01 07 00 6A 07 00 8B 07 00 7D 07 01 98 07 00 A6 07 00 86 07 00 86 07 01 9A 07 00 86 07 00 86 07 00 86 07 00 86 07 00 86 07 01 9C 07 00 86 07 00 86 07 00 F7 07 00 86 00 00 FF 00 74 00 18 07 00 01 07 00 6A 07 00 8B 07 00 7D 07 01 98 07 00 A6 07 00 86 07 00 86 07 01 9A 07 00 86 07 00 86 07 00 86 07 00 86 07 00 86 07 01 9C 07 00 86 07 00 86 07 00 F7 07 00 86 07 00 F7 07 00 86 07 00 86 07 00 86 07 00 86 00 00 0F 3F FF 00 94 00 1F 07 00 01 07 00 6A 07 00 8B 07 00 7D 07 01 98 07 00 A6 07 00 86 07 00 86 07 01 9A 07 00 86 07 00 86 07 00 86 07 00 86 07 00 86 07 01 9C 07 00 86 07 00 86 07 00 F7 07 00 86 07 00 F7 07 00 86 07 00 86 07 00 86 07 00 86 07 01 9C 07 00 86 01 07 00 F7 07 00 86 07 00 86 07 00 86 00 00 0B FC 00 60 07 01 67 FB 00 47 FF 00 3F 00 07 07 00 01 07 00 6A 07 00 8B 07 00 7D 07 01 98 07 00 A6 07 00 86 00 01 07 00 28
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  37     360    953    981    Ljava/lang/Exception;
        //  362    477    953    981    Ljava/lang/Exception;
        //  479    557    953    981    Ljava/lang/Exception;
        //  559    707    953    981    Ljava/lang/Exception;
        //  708    719    953    981    Ljava/lang/Exception;
        //  720    816    953    981    Ljava/lang/Exception;
        //  817    887    953    981    Ljava/lang/Exception;
        //  889    951    953    981    Ljava/lang/Exception;
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
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:881)
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
    
    public RXDaifuQueryResult query(final UserWithdraw order, final PaymentChannel channel) {
        try {
            final String orderId = order.getBillno();
            final String merchantCode = channel.getMerCode();
            final String type = "ToQuery";
            final String data = "{\"account\":\"" + merchantCode + "\"," + "\"orderId\":\"" + orderId + "\"," + "\"type\":\"" + type + "\"" + "}";
            byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(channel.getRsaPlatformPublicKey()), data.getBytes());
            final String cipher = Base64Utils.encode(cipherData);
            cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(channel.getRsaPrivateKey()), data.getBytes());
            final String signature = Base64Utils.encode(cipherData);
            final JSONObject params = new JSONObject();
            params.put("data", (Object)cipher);
            params.put("signature", (Object)signature);
            final String retStr = HttpClientUtil.postAsStream(this.daifuQueryUrl, JSON.toJSONString((Object)params), null, 10000);
            if (StringUtils.isEmpty(retStr)) {
                this.logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
                return null;
            }
            this.logInfo(order, null, channel, "查询返回数据：" + retStr);
            final JSONObject resp = JSONObject.parseObject(retStr);
            final String retData = resp.getString("data");
            final String retSignature = resp.getString("signature");
            if (StringUtils.isEmpty(retSignature) || StringUtils.isEmpty(retData)) {
                this.logError(order, null, channel, "查询返回数据表示失败");
                return null;
            }
            final byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(channel.getRsaPrivateKey()), Base64Utils.decode(retData));
            final String restr = new String(res);
            final RXDaifuQueryResult result = (RXDaifuQueryResult)JSON.parseObject(restr, (Class)RXDaifuQueryResult.class);
            if (result == null) {
                this.logError(order, null, channel, "查询请求失败，解析返回数据失败");
                return null;
            }
            if (!"61".equals(result.getState())) {
                this.logError(order, null, channel, "查询返回不是61，表示失败");
                return null;
            }
            return result;
        }
        catch (Exception e) {
            this.logException(order, null, channel, "查询请求失败", e);
            return null;
        }
    }
    
    private String transferErrorMsg(final String retStr, final String msg) {
        if (StringUtils.isEmpty(msg)) {
            return retStr;
        }
        return msg;
    }
    
    private boolean isSuccessDaifuState(final String state) {
        return "38".equalsIgnoreCase(state);
    }
    
    private boolean isSuccessDaifuQueryState(final String state) {
        return "2".equalsIgnoreCase(state) || "4".equalsIgnoreCase(state);
    }
    
    private String getDaifuQueryStateStr(final String state) {
        if ("1".equalsIgnoreCase(state)) {
            return "代付失败";
        }
        if ("2".equalsIgnoreCase(state)) {
            return "代付受理中";
        }
        if ("3".equalsIgnoreCase(state)) {
            return "代付失败退回";
        }
        if ("4".equalsIgnoreCase(state)) {
            return "代付成功";
        }
        return "未知状态";
    }
    
    public int transferBankStatus(final String bankStatus) {
        int remitStatus = -4;
        switch (bankStatus) {
            case "1": {
                remitStatus = -2;
                break;
            }
            case "2": {
                remitStatus = 1;
                break;
            }
            case "3": {
                remitStatus = -2;
                break;
            }
            case "4": {
                remitStatus = 2;
                break;
            }
            default:
                break;
        }
        return remitStatus;
    }
}
