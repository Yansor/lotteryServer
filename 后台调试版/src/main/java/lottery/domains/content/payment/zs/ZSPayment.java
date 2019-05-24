package lottery.domains.content.payment.zs;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import javautils.http.HttpClientUtil;
import lottery.domains.content.AbstractPayment;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.payment.zs.utils.MD5Encrypt;
import lottery.domains.content.payment.zs.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZSPayment
  extends AbstractPayment
  implements InitializingBean
{
  private static Map<Integer, String> BRANCH_NAMES = new HashMap();
  @Value("${zs.daifu.url}")
  private String daifuUrl;
  @Value("${zs.daifu.queryurl}")
  private String daifuQueryUrl;
  
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
      String nonceStr = StringUtil.getRandomNum(32);
      String outOrderId = order.getBillno();
      Long totalAmount = StringUtil.changeY2F(order.getRecMoney()+"");
      String merchantCode = channel.getMerCode();
      String intoCardNo = order.getCardId();
      String intoCardName = order.getCardName();
      String intoCardType = "2";
      String type = "04";
      String bankName = "";
      String remark = "";
      String bankCode = "";
      String signStr = String.format(
        "bankCode=%s&bankName=%s&intoCardName=%s&intoCardNo=%s&intoCardType=%s&merchantCode=%s&nonceStr=%s&outOrderId=%s&totalAmount=%s&type=%s&KEY=%s", new Object[] {
        
        bankCode, bankName, intoCardName, intoCardNo, intoCardType, merchantCode, nonceStr, outOrderId, totalAmount, type, channel.getMd5Key() });
      
      String sign = MD5Encrypt.getMessageDigest(signStr);
      Map<String, String> paramsMap = new HashMap();
      paramsMap.put("bankCode", bankCode);
      paramsMap.put("bankName", bankName);
      paramsMap.put("intoCardName", intoCardName);
      paramsMap.put("intoCardNo", intoCardNo);
      paramsMap.put("intoCardType", intoCardType);
      paramsMap.put("merchantCode", merchantCode);
      paramsMap.put("nonceStr", nonceStr);
      paramsMap.put("outOrderId", outOrderId);
      paramsMap.put("totalAmount", totalAmount+"");
      paramsMap.put("type", type);
      paramsMap.put("remark", remark);
      paramsMap.put("sign", sign);
      
      String retStr = HttpClientUtil.post(this.daifuUrl, paramsMap, null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, bank, channel, "接口返回空，可能是请求超时");
        json.set(Integer.valueOf(-1), "-1");
        return null;
      }
      JSONObject jsonObject = JSON.parseObject(retStr);
      String code = jsonObject.getString("code");
      String msg = jsonObject.getString("msg");
      if ("00".equals(code))
      {
        String data = jsonObject.getString("data");
        ZSDaifuResult result = (ZSDaifuResult)JSON.parseObject(data, ZSDaifuResult.class);
        if (result == null)
        {
          logError(order, bank, channel, "请求失败，解析返回数据失败，返回数据为：" + retStr);
          json.setWithParams(Integer.valueOf(2), "2-4007", new Object[] { StringUtils.abbreviate(retStr, 20) });
          return null;
        }
        if (StringUtils.isEmpty(result.getOrderId()))
        {
          logError(order, bank, channel, "请求失败，返回第三方注单号为空，返回数据为：" + retStr);
          json.setWithParams(Integer.valueOf(2), "2-4007", new Object[] { StringUtils.abbreviate(retStr, 20) });
          return null;
        }
        logSuccess(order, result.getOrderId(), channel);
        return result.getOrderId();
      }
      logError(order, bank, channel, "请求返回错误消息，返回数据：" + retStr + "，开始查询订单状态");
      ZSDaifuQueryResult queryResult = query(order, channel);
      if (isSuccessRequested(queryResult))
      {
        logSuccess(order, queryResult.getOrderId(), channel);
        return queryResult.getOrderId();
      }
      logError(order, bank, channel, "请求失败，返回数据为：" + retStr);
      json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { msg });
      return null;
    }
    catch (Exception e)
    {
      logException(order, bank, channel, "代付请求失败", e);
      json.set(Integer.valueOf(-1), "-1");
    }
    return null;
  }
  
  public ZSDaifuQueryResult query(UserWithdraw order, PaymentChannel channel)
  {
    try
    {
      String nonceStr = StringUtil.getRandomNum(32);
      String outOrderId = order.getBillno();
      String md5Key = channel.getMd5Key();
      String merchantCode = channel.getMerCode();
      String signStr = String.format(
        "merchantCode=%s&nonceStr=%s&outOrderId=%s&KEY=%s", new Object[] {
        merchantCode, nonceStr, outOrderId, md5Key });
      String sign = MD5Encrypt.getMessageDigest(signStr);
      Map<String, String> paramsMap = new HashMap();
      paramsMap.put("merchantCode", merchantCode);
      paramsMap.put("nonceStr", nonceStr);
      paramsMap.put("outOrderId", outOrderId);
      paramsMap.put("sign", sign);
      
      String retStr = HttpClientUtil.post(this.daifuQueryUrl, paramsMap, null, 10000);
      if (StringUtils.isEmpty(retStr))
      {
        logError(order, null, channel, "查询请求失败，发送请求后返回空数据");
        return null;
      }
      logInfo(order, null, channel, "查询返回数据：" + retStr);
      
      JSONObject jsonObject = JSON.parseObject(retStr);
      String code = jsonObject.getString("code");
      String data = null;
      if ("00".equals(code)) {
        data = jsonObject.getString("data");
      }
      if (StringUtils.isEmpty(data))
      {
        logError(order, null, channel, "查询请求失败，解析返回数据失败");
        return null;
      }
      ZSDaifuQueryResult result = (ZSDaifuQueryResult)JSON.parseObject(data, ZSDaifuQueryResult.class);
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
  
  public boolean isSuccessRequested(ZSDaifuQueryResult queryResult)
  {
    if (queryResult == null) {
      return false;
    }
    if (StringUtils.isEmpty(queryResult.getOrderId())) {
      return false;
    }
    if ("00".equalsIgnoreCase(queryResult.getState())) {
      return true;
    }
    if ("90".equalsIgnoreCase(queryResult.getState())) {
      return true;
    }
    return false;
  }
  
  public int transferBankStatus(String bankStatus)
  {
    int remitStatus = -4;
    String str;
    switch ((str = bankStatus).hashCode())
    {
    case 1536: 
      if (str.equals("00")) {
        break;
      }
      break;
    case 1538: 
      if (str.equals("02")) {}
      break;
    case 1815: 
      if (!str.equals("90"))
      {
        return remitStatus;
//        remitStatus = 2;
      }
      else
      {
        remitStatus = 1; return remitStatus;
//        remitStatus = -2;
      }
//      break;
    }
    return remitStatus;
  }
}
