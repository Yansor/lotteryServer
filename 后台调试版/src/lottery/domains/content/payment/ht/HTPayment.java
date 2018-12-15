package lottery.domains.content.payment.ht;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javautils.date.Moment;
import javautils.http.HttpClientUtil;
import lottery.domains.content.AbstractPayment;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.payment.utils.MoneyFormat;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HTPayment
  extends AbstractPayment
{
  @Value("${ht.daifu.url}")
  private String daifuUrl;
  @Value("${ht.daifu.queryurl}")
  private String daifuQueryUrl;
  
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
      String amount = MoneyFormat.pasMoney(Double.valueOf(order.getRecMoney()));
      String time = new Moment().toSimpleTime();
      
      Map<String, String> paramsMap = new HashMap();
      paramsMap.put("merchant_code", channel.getMerCode());
      paramsMap.put("order_amount", amount);
      paramsMap.put("trade_no", order.getBillno());
      paramsMap.put("order_time", time);
      paramsMap.put("bank_code", bank.getCode());
      paramsMap.put("account_name", order.getCardName());
      paramsMap.put("account_number", order.getCardId());
      String sign = getSign(paramsMap, channel.getMd5Key());
      paramsMap.put("sign", sign);
      
      String retStr = HttpClientUtil.post(this.daifuUrl, paramsMap, null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, bank, channel, "接口返回空，可能是请求超时");
        json.set(Integer.valueOf(-1), "-1");
        return null;
      }
      HTPayResult result = (HTPayResult)JSON.parseObject(retStr, HTPayResult.class);
      if (result == null)
      {
        logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
        json.setWithParams(Integer.valueOf(2), "2-4007", new Object[] { StringUtils.abbreviate(retStr, 20) });
        return null;
      }
      if (isAccepted(result))
      {
        logSuccess(order, result.getOrderId(), channel);
        return result.getOrderId();
      }
      if (StringUtils.isEmpty(result.getErrrorMsg()))
      {
        String msg = "未知错误";
        logError(order, bank, channel, "请求返回空的错误消息，返回数据：" + retStr + "，开始查询订单状态");
        
        HTPayResult queryResult = query(order, channel);
        if ((queryResult != null) && (isAccepted(queryResult)))
        {
          logSuccess(order, queryResult.getOrderId(), channel);
          return queryResult.getOrderId();
        }
        logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
        json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { msg });
        return null;
      }
      logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
      json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { result.getErrrorMsg() });
      return null;
    }
    catch (Exception e)
    {
      logException(order, bank, channel, "代付请求失败", e);
      json.set(Integer.valueOf(-1), "-1");
    }
    return null;
  }
  
  public HTPayResult query(UserWithdraw order, PaymentChannel channel)
  {
    try
    {
      String time = new Moment().toSimpleTime();
      Map<String, String> paramsMap = new HashMap();
      paramsMap.put("merchant_code", channel.getMerCode());
      paramsMap.put("now_date", time);
      paramsMap.put("trade_no", order.getBillno());
      String sign = getSign(paramsMap, channel.getMd5Key());
      paramsMap.put("sign", sign);
      
      String retStr = HttpClientUtil.post(this.daifuQueryUrl, paramsMap, null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
        return null;
      }
      logInfo(order, null, channel, "查询返回数据：" + retStr);
      
      HTPayResult result = (HTPayResult)JSON.parseObject(retStr, HTPayResult.class);
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
  
  private static String getSign(Map<String, String> paramsMap, String md5Key)
  {
    TreeMap<String, String> signMap = new TreeMap(paramsMap);
    
    StringBuffer signStr = new StringBuffer();
    
    Iterator<Map.Entry<String, String>> it = signMap.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, String> entry = (Map.Entry)it.next();
      String key = (String)entry.getKey();
      if ((!"sign".equalsIgnoreCase(key)) && (!"reqBody".equalsIgnoreCase(key)))
      {
        String value = (String)entry.getValue();
        if (!StringUtils.isEmpty(value))
        {
          signStr.append((String)entry.getKey()).append("=").append(value);
          if (it.hasNext()) {
            signStr.append("&");
          }
        }
      }
    }
    signStr.append("&key=").append(md5Key);
    
    String sign = DigestUtils.md5Hex(signStr.toString());
    
    return sign;
  }
  
  public boolean isAccepted(HTPayResult result)
  {
    return ("true".equalsIgnoreCase(result.getIsSuccess())) && 
      (isAcceptedBankStatus(result.getBankStatus())) && 
      (StringUtils.isNotEmpty(result.getOrderId()));
  }
  
  public int transferBankStatus(String bankStatus)
  {
    int remitStatus = -4;
    String str;
    switch ((str = bankStatus).hashCode())
    {
    case 48: 
      if (str.equals("0")) {
        break;
      }
      break;
    case 49: 
      if (str.equals("1")) {}
      break;
    case 50: 
      if (str.equals("2")) {}
      break;
    case 51: 
      if (!str.equals("3"))
      {
        return remitStatus;
//        remitStatus = 3; return remitStatus;
//        remitStatus = 1; return remitStatus;
//        remitStatus = 2;
      }
      else
      {
        remitStatus = -5;
      }
      break;
    }
    return remitStatus;
  }
  
  public boolean isUnprocess(HTPayResult result)
  {
    return ("true".equalsIgnoreCase(result.getIsSuccess())) && 
      ("0".equals(result.getBankStatus())) && 
      (StringUtils.isNotEmpty(result.getOrderId()));
  }
  
  public boolean isBankingProcessing(HTPayResult result)
  {
    return ("true".equalsIgnoreCase(result.getIsSuccess())) && 
      ("1".equals(result.getBankStatus())) && 
      (StringUtils.isNotEmpty(result.getOrderId()));
  }
  
  public boolean isBankingProcessSuccessed(HTPayResult result)
  {
    return ("true".equalsIgnoreCase(result.getIsSuccess())) && 
      ("2".equals(result.getBankStatus())) && 
      (StringUtils.isNotEmpty(result.getOrderId()));
  }
  
  public boolean isBankingProcessedFaild(HTPayResult result)
  {
    return ("true".equalsIgnoreCase(result.getIsSuccess())) && 
      ("3".equals(result.getBankStatus())) && 
      (StringUtils.isNotEmpty(result.getOrderId()));
  }
  
  private boolean isAcceptedBankStatus(String status)
  {
    if ("0".equalsIgnoreCase(status)) {
      return true;
    }
    if ("1".equalsIgnoreCase(status)) {
      return true;
    }
    if ("2".equalsIgnoreCase(status)) {
      return true;
    }
    return false;
  }
}
