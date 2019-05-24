package lottery.domains.content.payment.cf;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javautils.date.Moment;
import javautils.http.HttpClientUtil;
import javautils.http.ToUrlParamUtils;
import lottery.domains.content.AbstractPayment;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.payment.lepay.utils.StringUtil;
import lottery.domains.content.payment.utils.MoneyFormat;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CFPayment
  extends AbstractPayment
  implements InitializingBean
{
  private static final String BATCH_BIZ_TYPE = "00000";
  private static final String BATCH_VERSION = "00";
  private static final String CHARSET = "UTF-8";
  private static final String SIGN_TYPE = "SHA";
  private static Map<Integer, String> BRANCH_NAMES = new HashMap();
  private static Map<Integer, String> BANK_NAMES = new HashMap();
  @Value("${cf.daifu.url}")
  private String daifuUrl;
  private static final String SIGN_ALGORITHMS = "SHA-1";
  
  public void afterPropertiesSet()
    throws Exception
  {
    BRANCH_NAMES.put(Integer.valueOf(1), "中国工商银行股份有限公司上海市龙江路支行");
    BRANCH_NAMES.put(Integer.valueOf(2), "中国建设银行北京市分行营业部");
    BRANCH_NAMES.put(Integer.valueOf(3), "中国农业银行股份有限公司忻州和平分理处");
    BRANCH_NAMES.put(Integer.valueOf(4), "招商银行股份有限公司厦门金湖支行");
    BRANCH_NAMES.put(Integer.valueOf(5), "中国银行股份有限公司赣州市客家大道支行");
    BRANCH_NAMES.put(Integer.valueOf(6), "交通银行北京安翔里支行");
    BRANCH_NAMES.put(Integer.valueOf(7), "上海浦东发展银行安亭支行");
    BRANCH_NAMES.put(Integer.valueOf(8), "兴业银行北京安华支行");
    BRANCH_NAMES.put(Integer.valueOf(9), "中信银行北京安贞支行");
    BRANCH_NAMES.put(Integer.valueOf(10), "宁波银行股份有限公司北京东城支行");
    BRANCH_NAMES.put(Integer.valueOf(11), "上海银行股份有限公司北京安贞支行");
    BRANCH_NAMES.put(Integer.valueOf(12), "杭州银行股份有限公司上海北新泾支行");
    BRANCH_NAMES.put(Integer.valueOf(13), "渤海银行股份有限公司北京朝阳门支行");
    BRANCH_NAMES.put(Integer.valueOf(14), "浙商银行股份有限公司杭州滨江支行");
    BRANCH_NAMES.put(Integer.valueOf(15), "广发银行股份有限公司北京朝阳北路支行");
    BRANCH_NAMES.put(Integer.valueOf(16), "中国邮政储蓄银行股份有限公司北京昌平区北七家支行");
    BRANCH_NAMES.put(Integer.valueOf(17), "深圳发展银行");
    BRANCH_NAMES.put(Integer.valueOf(18), "中国民生银行股份有限公北京西大望路支行");
    BRANCH_NAMES.put(Integer.valueOf(19), "中国光大银行股份有限公司北京安定门支行");
    BRANCH_NAMES.put(Integer.valueOf(20), "华夏银行北京德外支行");
    BRANCH_NAMES.put(Integer.valueOf(21), "北京银行安定门支行");
    BRANCH_NAMES.put(Integer.valueOf(22), "南京银行股份有限公司北京车公庄支行");
    BRANCH_NAMES.put(Integer.valueOf(23), "平安银行股份有限公司北京北苑支行");
    BRANCH_NAMES.put(Integer.valueOf(24), "北京农村商业银行股份有限公司漷县支行");
    
    BANK_NAMES.put(Integer.valueOf(1), "中国工商银行");
    BANK_NAMES.put(Integer.valueOf(2), "中国建设银行");
    BANK_NAMES.put(Integer.valueOf(3), "中国农业银行");
    BANK_NAMES.put(Integer.valueOf(4), "招商银行");
    BANK_NAMES.put(Integer.valueOf(5), "中国银行");
    BANK_NAMES.put(Integer.valueOf(6), "交通银行");
    BANK_NAMES.put(Integer.valueOf(7), "上海浦东发展银行");
    BANK_NAMES.put(Integer.valueOf(8), "兴业银行");
    BANK_NAMES.put(Integer.valueOf(9), "中信银行");
    BANK_NAMES.put(Integer.valueOf(10), "宁波银行");
    BANK_NAMES.put(Integer.valueOf(11), "上海银行");
    BANK_NAMES.put(Integer.valueOf(12), "杭州银行");
    BANK_NAMES.put(Integer.valueOf(13), "渤海银行");
    BANK_NAMES.put(Integer.valueOf(14), "浙商银行");
    BANK_NAMES.put(Integer.valueOf(15), "广发银行");
    BANK_NAMES.put(Integer.valueOf(16), "中国邮政储蓄银行");
    BANK_NAMES.put(Integer.valueOf(18), "中国民生银行");
    BANK_NAMES.put(Integer.valueOf(19), "中国光大银行");
    BANK_NAMES.put(Integer.valueOf(20), "华夏银行");
    BANK_NAMES.put(Integer.valueOf(21), "北京银行");
    BANK_NAMES.put(Integer.valueOf(22), "南京银行");
    BANK_NAMES.put(Integer.valueOf(23), "平安银行");
    BANK_NAMES.put(Integer.valueOf(24), "北京农村商业银行");
  }
  
  public String daifu(WebJSONObject json, UserWithdraw order, UserCard card, PaymentChannelBank bank, PaymentChannel channel)
  {
    try
    {
      logStart(order, bank, channel);
      return daifuInternal(json, order, card, bank, channel);
    }
    catch (Exception e)
    {
      logException(order, bank, channel, "代付请求失败", e);
      json.set(Integer.valueOf(2), "2-4000");
    }
    return null;
  }
  
  private String daifuInternal(WebJSONObject json, UserWithdraw order, UserCard card, PaymentChannelBank bank, PaymentChannel channel)
  {
    try
    {
      String bankName = (String)BANK_NAMES.get(Integer.valueOf(card.getBankId()));
      if (StringUtil.isEmpty(bankName))
      {
        json.set(Integer.valueOf(2), "2-4012");
        return null;
      }
      String amount = MoneyFormat.pasMoney(Double.valueOf(order.getRecMoney()));
      
      StringBuffer batchContent = new StringBuffer();
      batchContent.append("1").append(",");
      batchContent.append(card.getCardId()).append(",");
      batchContent.append(card.getCardName()).append(",");
      batchContent.append(bankName).append(",");
      String branchName = (String)BRANCH_NAMES.get(Integer.valueOf(card.getBankId()));
      batchContent.append(branchName).append(",");
      batchContent.append(branchName).append(",");
      batchContent.append("私").append(",");
      batchContent.append(amount).append(",");
      batchContent.append("CNY").append(",");
      batchContent.append("北京").append(",");
      batchContent.append("北京").append(",");
      batchContent.append("").append(",");
      batchContent.append("").append(",");
      batchContent.append("").append(",");
      batchContent.append("").append(",");
      batchContent.append(order.getBillno()).append(",");
      batchContent.append("APIPAY");
      
      String date = new Moment().format("yyyyMMdd");
      
      Map<String, String> paramsMap = new TreeMap();
      paramsMap.put("batchAmount", amount);
      paramsMap.put("batchBiztype", "00000");
      paramsMap.put("batchContent", batchContent.toString());
      paramsMap.put("batchCount", "1");
      paramsMap.put("batchDate", date);
      paramsMap.put("batchNo", order.getBillno());
      paramsMap.put("batchVersion", "00");
      paramsMap.put("charset", "UTF-8");
      paramsMap.put("merchantId", channel.getMerCode());
      
      String signStr = ToUrlParamUtils.toUrlParam(paramsMap, "&", true) + channel.getMd5Key();
      String sign = sign(signStr, "UTF-8");
      paramsMap.put("signType", "SHA");
      paramsMap.put("sign", sign);
      
      String url = this.daifuUrl + "/agentPay/v1/batch/" + channel.getMerCode() + "-" + order.getBillno();
      
      String retStr = HttpClientUtil.post(url, paramsMap, null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, bank, channel, "接口返回空，可能是请求超时");
        json.set(Integer.valueOf(-1), "-1");
        return null;
      }
      CFPayResult result = (CFPayResult)JSON.parseObject(retStr, CFPayResult.class);
      if (result == null)
      {
        logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
        json.setWithParams(Integer.valueOf(2), "2-4007", new Object[] { StringUtils.abbreviate(retStr, 20) });
        return null;
      }
      if ("S0001".equalsIgnoreCase(result.getRespCode()))
      {
        logSuccess(order, order.getBillno(), channel);
        return order.getBillno();
      }
      if (StringUtils.isEmpty(result.getRespMessage()))
      {
        String msg = "未知错误";
        logError(order, bank, channel, "请求返回空的错误消息，返回数据：" + retStr + "，开始查询订单状态");
        
        CFPayQueryResult queryResult = query(order, channel, date);
        if (isAccepted(queryResult))
        {
          logSuccess(order, queryResult.getBatchNo(), channel);
          return queryResult.getBatchNo();
        }
        logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
        json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { msg });
        return null;
      }
      logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
      json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { result.getRespMessage() });
      return null;
    }
    catch (Exception e)
    {
      logException(order, bank, channel, "代付请求失败", e);
      json.set(Integer.valueOf(-1), "-1");
    }
    return null;
  }
  
  public CFPayQueryResult query(UserWithdraw order, PaymentChannel channel)
  {
    String date = new Moment().fromTime(order.getOperatorTime()).format("yyyyMMdd");
    return query(order, channel, date);
  }
  
  private CFPayQueryResult query(UserWithdraw order, PaymentChannel channel, String date)
  {
    try
    {
      Map<String, String> paramsMap = new TreeMap();
      paramsMap.put("batchDate", date);
      paramsMap.put("batchNo", order.getBillno());
      paramsMap.put("batchVersion", "00");
      paramsMap.put("charset", "UTF-8");
      paramsMap.put("merchantId", channel.getMerCode());
      
      String signStr = ToUrlParamUtils.toUrlParam(paramsMap, "&", true) + channel.getMd5Key();
      String sign = sign(signStr, "UTF-8");
      paramsMap.put("signType", "SHA");
      paramsMap.put("sign", sign);
      
      String paramsStr = ToUrlParamUtils.toUrlParam(paramsMap, "&", true);
      
      String url = this.daifuUrl + "/agentPay/v1/batch/" + channel.getMerCode() + "-" + order.getBillno() + "?" + paramsStr;
      String retStr = HttpClientUtil.get(url, null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
        return null;
      }
      logInfo(order, null, channel, "查询返回数据：" + retStr);
      
      CFPayQueryResult result = (CFPayQueryResult)JSON.parseObject(retStr, CFPayQueryResult.class);
      if (result == null)
      {
        logError(order, null, channel, "查询请求失败，解析返回数据失败");
        return null;
      }
      return result;
    }
    catch (Exception e)
    {
      logException(order, null, channel, "查询请求失败", e);
    }
    return null;
  }
  
  private static String sign(String content, String inputCharset)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(content.getBytes(inputCharset));
      
      byte[] messageDigest = digest.digest();
      
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < messageDigest.length; i++)
      {
        String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
        if (shaHex.length() < 2) {
          hexString.append(0);
        }
        hexString.append(shaHex);
      }
      return hexString.toString().toUpperCase();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public boolean isAccepted(CFPayQueryResult result)
  {
    return ("S0001".equalsIgnoreCase(result.getRespCode())) && 
      (StringUtils.isNotEmpty(result.getBatchNo()));
  }
  
  public int transferBankStatus(String batchContent)
  {
    String bankStatus = getBankStatusFromBatchContent(batchContent);
    if (bankStatus == null) {
      return 1;
    }
    if ("null".equalsIgnoreCase(bankStatus)) {
      return 1;
    }
    if ("成功".equalsIgnoreCase(bankStatus)) {
      return 2;
    }
    if ("success".equalsIgnoreCase(bankStatus)) {
      return 2;
    }
    if ("failure".equalsIgnoreCase(bankStatus)) {
      return -5;
    }
    if ("失败".equalsIgnoreCase(bankStatus)) {
      return -5;
    }
    return -4;
  }
  
  private String getBankStatusFromBatchContent(String batchContent)
  {
    String[] batchContents = batchContent.split(",");
    String bankStatus = batchContents[12];
    
    return bankStatus;
  }
  
  public static void main(String[] args)
  {
    String content = "1,171206111554nkjWivyJ,6217000010074430920,弋会超,中国建设银行北京市分行营业部,中国建设银行北京市分行营业部,中国建设银行,0,1100.00,CNY,APIPAY,,成功,";
    String[] batchContents = content.split(",");
    String bankStatus = batchContents[12];
    
    System.out.println(bankStatus);
  }
}
