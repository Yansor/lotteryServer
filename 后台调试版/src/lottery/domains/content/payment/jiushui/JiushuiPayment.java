package lottery.domains.content.payment.jiushui;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lottery.domains.content.entity.PaymentChannel;
import lottery.domains.content.payment.jiushui.util.SignUtils;
import lottery.domains.content.payment.lepay.utils.WebUtil;
import lottery.domains.content.payment.mkt.URLUtils;
import lottery.domains.content.payment.utils.MoneyFormat;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JiushuiPayment
{
  private static final Logger log = LoggerFactory.getLogger(JiushuiPayment.class);
  @Value("${jiushui.daifu.url}")
  private String daifuUrl;
  @Value("${jiushui.daifu.private_key}")
  private String daifuPrivateKey;
  
  public String daifu(WebJSONObject json, PaymentChannel channel, double money, String billno, String opnbnk, String opnbnknam, String name, String card, String branchName)
  {
    try
    {
      log.debug("开始玖水代付,注单ID:{},姓名:{},卡号:{},分行:{}", new Object[] { billno, name, card, branchName });
      return daifuInternel(json, channel.getMerCode(), money, billno, opnbnk, opnbnknam, name, card, branchName);
    }
    catch (Exception e)
    {
      log.error("玖水代付发生异常", e);
      json.set(Integer.valueOf(2), "2-4000");
    }
    return null;
  }
  
  private String daifuInternel(WebJSONObject json, String merCode, double money, String billno, String opnbnk, String opnbnknam, String name, String card, String branchName)
  {
    try
    {
      long fenMoney = MoneyFormat.yuanToFenMoney(money+"");
      
      StringBuffer signSrc = new StringBuffer();
      signSrc.append("CP_NO=").append(billno);
      signSrc.append("&TXNAMT=").append(fenMoney);
      signSrc.append("&OPNBNK=").append(opnbnk);
      signSrc.append("&OPNBNKNAM=").append(opnbnknam);
      signSrc.append("&ACTNO=").append(card);
      signSrc.append("&ACTNAM=").append(name);
      signSrc.append("&ACTIDCARD=").append("440901197709194316");
      signSrc.append("&ACTMOBILE=").append("16888888888");
      
      String dataStr = signSrc.toString();
      String sign = null;
      try
      {
        sign = SignUtils.Signaturer(dataStr, this.daifuPrivateKey);
      }
      catch (Exception e)
      {
        log.error("玖水代付发生签名异常", e);
        json.set(Integer.valueOf(2), "2-4003");
        return null;
      }
      Map<String, String> paramsMap = new LinkedHashMap();
      paramsMap.put("MERCNUM", merCode);
      paramsMap.put("TRANDATA", URLUtils.encode(dataStr, "UTF-8"));
      paramsMap.put("SIGN", URLUtils.encode(sign, "UTF-8"));
      
      String strResult = WebUtil.doPost(this.daifuUrl, paramsMap, "UTF-8", 3000, 15000);
      if (StringUtils.isEmpty(strResult))
      {
        log.error("玖水代付请求失败，发送请求后返回空数据");
        json.set(Integer.valueOf(2), "2-4006");
        return null;
      }
      Map<String, String> retMap = (Map)JSON.parseObject(strResult, HashMap.class);
      String reCode = (String)retMap.get("RECODE");
      String reMsg = (String)retMap.get("REMSG");
      String PROXYNO = (String)retMap.get("PROXYNO");
      String CP_NO = (String)retMap.get("CP_NO");
      if ("000000".equals(reCode))
      {
        if (StringUtils.isNotEmpty(PROXYNO))
        {
          log.debug("玖水代付请求返回订单号：{}", PROXYNO);
          return PROXYNO;
        }
        log.error("玖水代付返回订单ID为空,我方订单ID:" + billno);
        json.setWithParams(Integer.valueOf(2), "2-4014", new Object[0]);
        return null;
      }
      log.error("玖水代付请求失败,返回数据为：" + strResult + ",我方订单ID:" + billno);
      json.setWithParams(Integer.valueOf(2), "2-4002", new Object[] { reMsg });
      return null;
    }
    catch (Exception e)
    {
      log.error("玖水代付失败,发生异常", e);
      json.set(Integer.valueOf(2), "2-4000");
    }
    return null;
  }
}
