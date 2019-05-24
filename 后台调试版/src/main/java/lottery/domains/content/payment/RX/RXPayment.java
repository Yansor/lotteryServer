package lottery.domains.content.payment.RX;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javautils.http.HttpClientUtil;
import lottery.domains.content.AbstractPayment;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.payment.RX.utils.Base64;
import lottery.domains.content.payment.RX.utils.Base64Utils;
import lottery.domains.content.payment.RX.utils.RSAEncrypt;
import lottery.domains.content.payment.zs.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RXPayment
  extends AbstractPayment
  implements InitializingBean
{
  private static Map<Integer, String> BRANCH_IDS = new HashMap();
  public static final String NONE_NOTIFY_URL = "http://www.baidu.com";
  @Value("${rx.daifu.url}")
  private String daifuUrl;
  @Value("${rx.daifu.queryurl}")
  private String daifuQueryUrl;
  
  public void afterPropertiesSet()
    throws Exception
  {
    BRANCH_IDS.put(Integer.valueOf(1), "01000017");
    BRANCH_IDS.put(Integer.valueOf(2), "01050000");
    BRANCH_IDS.put(Integer.valueOf(3), "01000001");
    BRANCH_IDS.put(Integer.valueOf(4), "01000010");
    BRANCH_IDS.put(Integer.valueOf(5), "01000002");
    BRANCH_IDS.put(Integer.valueOf(6), "01000003");
    BRANCH_IDS.put(Integer.valueOf(7), "01000012");
    BRANCH_IDS.put(Integer.valueOf(8), "01000011");
    BRANCH_IDS.put(Integer.valueOf(9), "01000004");
    BRANCH_IDS.put(Integer.valueOf(13), "01000015");
    BRANCH_IDS.put(Integer.valueOf(14), "01000014");
    BRANCH_IDS.put(Integer.valueOf(15), "01000008");
    BRANCH_IDS.put(Integer.valueOf(16), "01000000");
    BRANCH_IDS.put(Integer.valueOf(18), "01000007");
    BRANCH_IDS.put(Integer.valueOf(19), "01000005");
    BRANCH_IDS.put(Integer.valueOf(20), "01000006");
    BRANCH_IDS.put(Integer.valueOf(23), "01000009");
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
    String bankCode = (String)BRANCH_IDS.get(Integer.valueOf(card.getBankId()));
    if (bankCode == null)
    {
      json.set(Integer.valueOf(2), "2-4012");
      return null;
    }
    try
    {
      String orderId = order.getBillno();
      Long totalAmount = StringUtil.changeY2F(order.getRecMoney()+"");
      String merchantCode = channel.getMerCode();
      String intoCardNo = order.getCardId();
      String intoCardName = order.getCardName();
      String type = "ToPay";
      String data = "{\"accName\":\"" + URLEncoder.encode(intoCardName, "utf-8") + "\"," + 
        "\"accNo\":\"" + intoCardNo + "\"," + "\"account\":\"" + merchantCode + "\"," + 
        "\"amount\":\"" + totalAmount + "\"," + "\"banktype\":\"" + bankCode + "\"," + 
        "\"notify_url\":\"" + URLEncoder.encode("http://www.baidu.com", "utf-8") + "\"," + "\"orderId\":\"" + orderId + "\"," + 
        "\"type\":\"" + type + "\"" + "}";
      
      byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(channel.getRsaPlatformPublicKey()), data.getBytes());
      String cipher = Base64Utils.encode(cipherData);
      
      cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(channel.getRsaPrivateKey()), data.getBytes());
      String signature = Base64Utils.encode(cipherData);
      JSONObject params = new JSONObject();
      params.put("data", cipher);
      params.put("signature", signature);
      
      String retStr = HttpClientUtil.postAsStream(this.daifuUrl, JSON.toJSONString(params), null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, bank, channel, "接口返回空，可能是请求超时");
        json.set(Integer.valueOf(-1), "-1");
        return null;
      }
      JSONObject resp = JSONObject.parseObject(retStr);
      String retData = resp.getString("data");
      String retSignature = resp.getString("signature");
      String reqstate = resp.getString("state");
      String reqmessage = resp.getString("message");
      if (StringUtils.isNotEmpty(reqstate))
      {
        logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
        String msg = transferErrorMsg(retStr, reqmessage);
        json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { StringUtils.abbreviate(msg, 20) });
        return null;
      }
      if ((StringUtils.isEmpty(retSignature)) || (StringUtils.isEmpty(retData)))
      {
        logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
        String msg = transferErrorMsg(retStr, reqmessage);
        json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { StringUtils.abbreviate(msg, 20) });
        return null;
      }
      byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(channel.getRsaPrivateKey()), Base64.decode(retData));
      String restr = new String(res);
      
      boolean sign = RSAEncrypt.publicsign(restr, Base64.decode(retSignature), RSAEncrypt.loadPublicKeyByStr(channel.getRsaPlatformPublicKey()));
      
      JSONObject jsonObject = JSON.parseObject(restr);
      String resorderId = jsonObject.getString("orderId");
      String state = jsonObject.getString("state");
      String message = jsonObject.getString("message");
      if ((isSuccessDaifuState(state)) && (StringUtils.isNotEmpty(resorderId)))
      {
        if (!sign)
        {
          logWarn(order, bank, channel, "请求成功，但数据验签失败，返回数据：" + retStr);
          json.set(Integer.valueOf(2), "2-4008");
          return resorderId;
        }
        logSuccess(order, resorderId, channel);
        return resorderId;
      }
      logError(order, bank, channel, "请求返回状态表示失败，返回数据：" + retStr + "，开始查询订单状态");
      RXDaifuQueryResult queryResult = query(order, channel);
      if ((isSuccessDaifuQueryState(queryResult.getOrderId_state())) && 
        ("61".equalsIgnoreCase(queryResult.getState())) && 
        (StringUtils.isNotEmpty(queryResult.getOrderId())))
      {
        logSuccess(order, queryResult.getOrderId(), channel);
        return queryResult.getOrderId();
      }
      if (!isSuccessDaifuQueryState(queryResult.getOrderId_state()))
      {
        String stateStr = getDaifuQueryStateStr(queryResult.getOrderId_state());
        logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
        json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { stateStr });
        return null;
      }
      logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
      String msg = transferErrorMsg(restr, message);
      json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { StringUtils.abbreviate(msg, 20) });
      return null;
    }
    catch (Exception e)
    {
      logException(order, bank, channel, "代付请求失败", e);
      json.set(Integer.valueOf(-1), "-1");
    }
    return null;
  }
  
  public RXDaifuQueryResult query(UserWithdraw order, PaymentChannel channel)
  {
    try
    {
      String orderId = order.getBillno();
      String merchantCode = channel.getMerCode();
      String type = "ToQuery";
      String data = "{\"account\":\"" + merchantCode + "\"," + 
        "\"orderId\":\"" + orderId + "\"," + 
        "\"type\":\"" + type + "\"" + "}";
      
      byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(channel.getRsaPlatformPublicKey()), data.getBytes());
      String cipher = Base64Utils.encode(cipherData);
      
      cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(channel.getRsaPrivateKey()), data.getBytes());
      String signature = Base64Utils.encode(cipherData);
      
      JSONObject params = new JSONObject();
      params.put("data", cipher);
      params.put("signature", signature);
      
      String retStr = HttpClientUtil.postAsStream(this.daifuQueryUrl, JSON.toJSONString(params), null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
        return null;
      }
      logInfo(order, null, channel, "查询返回数据：" + retStr);
      
      JSONObject resp = JSONObject.parseObject(retStr);
      String retData = resp.getString("data");
      String retSignature = resp.getString("signature");
      if ((StringUtils.isEmpty(retSignature)) || (StringUtils.isEmpty(retData)))
      {
        logError(order, null, channel, "查询返回数据表示失败");
        return null;
      }
      byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(channel.getRsaPrivateKey()), Base64Utils.decode(retData));
      String restr = new String(res);
      RXDaifuQueryResult result = (RXDaifuQueryResult)JSON.parseObject(restr, RXDaifuQueryResult.class);
      if (result == null)
      {
        logError(order, null, channel, "查询请求失败，解析返回数据失败");
        return null;
      }
      if (!"61".equals(result.getState()))
      {
        logError(order, null, channel, "查询返回不是61，表示失败");
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
  
  private String transferErrorMsg(String retStr, String msg)
  {
    if (StringUtils.isEmpty(msg)) {
      return retStr;
    }
    return msg;
  }
  
  private boolean isSuccessDaifuState(String state)
  {
    return "38".equalsIgnoreCase(state);
  }
  
  private boolean isSuccessDaifuQueryState(String state)
  {
    if ("2".equalsIgnoreCase(state)) {
      return true;
    }
    if ("4".equalsIgnoreCase(state)) {
      return true;
    }
    return false;
  }
  
  private String getDaifuQueryStateStr(String state)
  {
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
  
  public int transferBankStatus(String bankStatus)
  {
    int remitStatus = -4;
    String str;
    switch ((str = bankStatus).hashCode())
    {
    case 49: 
      if (str.equals("1")) {
        break;
      }
      break;
    case 50: 
      if (str.equals("2")) {}
      break;
    case 51: 
      if (str.equals("3")) {}
      break;
    case 52: 
      if (!str.equals("4"))
      {
        return remitStatus;
//        remitStatus = -2; return remitStatus;
//        remitStatus = 1; return remitStatus;
//        remitStatus = -2;
      }
      else
      {
        remitStatus = 2;
      }
      break;
    }
    return remitStatus;
  }
}
