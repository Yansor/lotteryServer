package lottery.domains.content.payment.jiushui;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import lottery.domains.content.payment.lepay.utils.WebUtil;
import lottery.domains.content.payment.mkt.URLUtils;
import java.util.LinkedHashMap;
import lottery.domains.content.payment.jiushui.util.SignUtils;
import lottery.domains.content.payment.utils.MoneyFormat;
import lottery.domains.content.entity.PaymentChannel;
import admin.web.WebJSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JiushuiPayment
{
    private static final Logger log;
    @Value("${jiushui.daifu.url}")
    private String daifuUrl;
    @Value("${jiushui.daifu.private_key}")
    private String daifuPrivateKey;
    
    static {
        log = LoggerFactory.getLogger((Class)JiushuiPayment.class);
    }
    
    public String daifu(final WebJSONObject json, final PaymentChannel channel, final double money, final String billno, final String opnbnk, final String opnbnknam, final String name, final String card, final String branchName) {
        try {
            JiushuiPayment.log.debug("开始玖水代付,注单ID:{},姓名:{},卡号:{},分行:{}", new Object[] { billno, name, card, branchName });
            return this.daifuInternel(json, channel.getMerCode(), money, billno, opnbnk, opnbnknam, name, card, branchName);
        }
        catch (Exception e) {
            JiushuiPayment.log.error("玖水代付发生异常", (Throwable)e);
            json.set(2, "2-4000");
            return null;
        }
    }
    
    private String daifuInternel(final WebJSONObject json, final String merCode, final double money, final String billno, final String opnbnk, final String opnbnknam, final String name, final String card, final String branchName) {
        try {
            final long fenMoney = MoneyFormat.yuanToFenMoney(new StringBuilder(String.valueOf(money)).toString());
            final StringBuffer signSrc = new StringBuffer();
            signSrc.append("CP_NO=").append(billno);
            signSrc.append("&TXNAMT=").append(fenMoney);
            signSrc.append("&OPNBNK=").append(opnbnk);
            signSrc.append("&OPNBNKNAM=").append(opnbnknam);
            signSrc.append("&ACTNO=").append(card);
            signSrc.append("&ACTNAM=").append(name);
            signSrc.append("&ACTIDCARD=").append("440901197709194316");
            signSrc.append("&ACTMOBILE=").append("16888888888");
            final String dataStr = signSrc.toString();
            String sign;
            try {
                sign = SignUtils.Signaturer(dataStr, this.daifuPrivateKey);
            }
            catch (Exception e) {
                JiushuiPayment.log.error("玖水代付发生签名异常", (Throwable)e);
                json.set(2, "2-4003");
                return null;
            }
            final Map<String, String> paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("MERCNUM", merCode);
            paramsMap.put("TRANDATA", URLUtils.encode(dataStr, "UTF-8"));
            paramsMap.put("SIGN", URLUtils.encode(sign, "UTF-8"));
            final String strResult = WebUtil.doPost(this.daifuUrl, paramsMap, "UTF-8", 3000, 15000);
            if (StringUtils.isEmpty(strResult)) {
                JiushuiPayment.log.error("玖水代付请求失败，发送请求后返回空数据");
                json.set(2, "2-4006");
                return null;
            }
            final Map<String, String> retMap = (Map<String, String>)JSON.parseObject(strResult, (Class)HashMap.class);
            final String reCode = retMap.get("RECODE");
            final String reMsg = retMap.get("REMSG");
            final String PROXYNO = retMap.get("PROXYNO");
            final String CP_NO = retMap.get("CP_NO");
            if (!"000000".equals(reCode)) {
                JiushuiPayment.log.error("玖水代付请求失败,返回数据为：" + strResult + ",我方订单ID:" + billno);
                json.setWithParams(2, "2-4002", reMsg);
                return null;
            }
            if (StringUtils.isNotEmpty(PROXYNO)) {
                JiushuiPayment.log.debug("玖水代付请求返回订单号：{}", (Object)PROXYNO);
                return PROXYNO;
            }
            JiushuiPayment.log.error("玖水代付返回订单ID为空,我方订单ID:" + billno);
            json.setWithParams(2, "2-4014", new Object[0]);
            return null;
        }
        catch (Exception e2) {
            JiushuiPayment.log.error("玖水代付失败,发生异常", (Throwable)e2);
            json.set(2, "2-4000");
            return null;
        }
    }
}
