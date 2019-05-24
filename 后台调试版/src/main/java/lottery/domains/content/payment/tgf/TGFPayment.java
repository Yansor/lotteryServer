package lottery.domains.content.payment.tgf;

import admin.web.WebJSONObject;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import lottery.domains.content.AbstractPayment;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.entity.PaymentChannelBank;
import lottery.domains.content.entity.UserCard;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.payment.tgf.utils.Merchant;
import lottery.domains.content.payment.tgf.utils.QueryResponseEntity;
import lottery.domains.content.payment.tgf.utils.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

@Component
public class TGFPayment
  extends AbstractPayment
  implements InitializingBean
{
  @Autowired
  private ServletContext servletContext;
  private static Map<Integer, String> BRANCH_NAMES = new HashMap();
  public static final String NONE_NOTIFY_URL = "http://www.baidu.com";
  @Value("${af.daifu.url}")
  private String daifuUrl;
  @Value("${af.daifu.queryurl}")
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
  
  public static String base64Encoder(String ss, String charset)
  {
    BASE64Encoder en = new BASE64Encoder();
    String encStr = "";
    if ((charset == null) || ("".equals(charset)))
    {
      encStr = en.encode(ss.getBytes());
      return encStr;
    }
    try
    {
      encStr = en.encode(ss.getBytes(charset));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return encStr;
  }
  
  private String daifuInternal(WebJSONObject json, UserWithdraw order, UserCard card, PaymentChannelBank bank, PaymentChannel channel)
  {
    try
    {
      Map<String, String> paramsMap = new HashMap();
      paramsMap.put("service", "TRADE.SETTLE");
      paramsMap.put("version", "1.0.0.0");
      paramsMap.put("merId", channel.getMerCode());
      paramsMap.put("tradeNo", order.getBillno());
      Date d = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      paramsMap.put("tradeDate", sdf.format(d));
      paramsMap.put("amount", order.getRecMoney()+"");
      
      paramsMap.put("notifyUrl", "http://www.baidu.com");
      paramsMap.put("extra", order.getBillno());
      paramsMap.put("summary", order.getBillno());
      paramsMap.put("bankCardNo", order.getCardId());
      paramsMap.put("bankCardName", order.getCardName());
      paramsMap.put("bankId", bank.getCode());
      paramsMap.put("bankName", (String)BRANCH_NAMES.get(Integer.valueOf(card.getBankId())));
      paramsMap.put("purpose", "");
      
      String paramsStr = Merchant.generateSingleSettRequest(paramsMap);
      String signMsg = SignUtil.signByMD5(paramsStr, channel.getMd5Key());
      paramsStr = paramsStr + "&sign=" + signMsg;
      
      String payGateUrl = channel.getPayUrl();
      
      String responseMsg = Merchant.transact(paramsStr, payGateUrl);
      
      System.out.println(responseMsg);
      
      QueryResponseEntity entity = new QueryResponseEntity();
      entity.parse(responseMsg, channel.getMd5Key());
      if ("00".equals(entity.getRespCode()))
      {
        logSuccess(order, order.getBillno(), channel);
        return order.getBillno();
      }
      logError(order, bank, channel, "请求返回错误消息，返回数据：" + responseMsg + "，开始查询订单状态");
      QueryResponseEntity queryResult = query(order, channel);
      if (isSuccessRequested(queryResult))
      {
        logSuccess(order, order.getBillno(), channel);
        return order.getBillno();
      }
      logError(order, bank, channel, "请求失败，返回数据为：" + responseMsg);
      json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { entity.getRespDesc() });
      return null;
    }
    catch (Exception e)
    {
      logException(order, bank, channel, "代付请求失败", e);
      json.set(Integer.valueOf(-1), "-1");
    }
    return null;
  }
  
  public static void main(String[] args) {}
  
  public QueryResponseEntity query(UserWithdraw order, PaymentChannel channel)
  {
    try
    {
      Map<String, String> paramsMap = new HashMap();
      paramsMap.put("service", "TRADE.SETTLE.QUERY");
      paramsMap.put("version", "1.0.0.0");
      paramsMap.put("merId", channel.getMerCode());
      paramsMap.put("tradeNo", order.getBillno());
      Date d = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      paramsMap.put("tradeDate", sdf.format(d));
      
      String paramsStr = Merchant.generateSingleSettQueryRequest(paramsMap);
      String signMsg = SignUtil.signByMD5(paramsStr, channel.getMd5Key());
      paramsStr = paramsStr + "&sign=" + signMsg;
      String payGateUrl = channel.getPayUrl();
      
      System.out.println(paramsStr);
      String responseMsg = Merchant.transact(paramsStr, payGateUrl);
      System.out.println(responseMsg);
      
      QueryResponseEntity entity = new QueryResponseEntity();
      entity.parse(responseMsg, channel.getMd5Key());
      
      return entity;
    }
    catch (Exception e)
    {
      logException(order, null, channel, "查询请求失败", e);
    }
    return null;
  }
  
  public boolean isSuccessRequested(QueryResponseEntity queryResult)
  {
    if (StringUtils.isEmpty(queryResult.getRespCode())) {
      return false;
    }
    if ("00".equalsIgnoreCase(queryResult.getRespCode())) {
      return true;
    }
    if ("1".equalsIgnoreCase(queryResult.getStatus())) {
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
    case 49: 
      if (str.equals("1")) {
        break;
      }
      break;
    case 50: 
      if (str.equals("2")) {}
//    case 53:
//      if ((goto 96) && (str.equals("5")))
//      {
//        remitStatus = 1; return remitStatus;
//        remitStatus = 2; return remitStatus;
//        remitStatus = -2;
//      }
//      break;
    }
    return remitStatus;
  }
}
