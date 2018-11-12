package lottery.domains.content.payment.tgf.utils;

import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import java.util.Map;
import java.net.URLConnection;
import java.net.SocketTimeoutException;
import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.security.GeneralSecurityException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.KeyManager;
import java.security.SecureRandom;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

public class Merchant
{
    public static String transact(final String paramsStr, final String serverUrl) throws Exception {
        if (StringUtils.isBlank(serverUrl) || StringUtils.isBlank(paramsStr)) {
            throw new NullPointerException("请求地址或请求数据为空!");
        }
        final myX509TrustManager xtm = new myX509TrustManager();
        final myHostnameVerifier hnv = new myHostnameVerifier();
        final ByteArrayOutputStream respData = new ByteArrayOutputStream();
        final byte[] b = new byte[8192];
        String result = "";
        try {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                final X509TrustManager[] xtmArray = { xtm };
                sslContext.init(null, xtmArray, new SecureRandom());
            }
            catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(hnv);
            URLConnection conn = null;
            if (serverUrl.toLowerCase().startsWith("https")) {
                final HttpsURLConnection httpsUrlConn = (HttpsURLConnection)new URL(serverUrl).openConnection();
                httpsUrlConn.setRequestMethod("POST");
                conn = httpsUrlConn;
            }
            else {
                final HttpURLConnection httpUrlConn = (HttpURLConnection)new URL(serverUrl).openConnection();
                httpUrlConn.setRequestMethod("POST");
                conn = httpUrlConn;
            }
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.1) Gecko/20061204 Firefox/2.0.0.3");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("connection", "close");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.getOutputStream().write(paramsStr.getBytes("utf-8"));
            conn.getOutputStream().flush();
            int len = 0;
            try {
                while (true) {
                    len = conn.getInputStream().read(b);
                    if (len <= 0) {
                        break;
                    }
                    respData.write(b, 0, len);
                }
                conn.getInputStream().close();
            }
            catch (SocketTimeoutException ee) {
                throw new RuntimeException("读取响应数据出错！" + ee.getMessage());
            }
            result = respData.toString("utf-8");
            System.out.println("==============================返回的数据" + result);
            if (StringUtils.isBlank(result)) {
                throw new RuntimeException("返回参数错误！");
            }
        }
        catch (Exception e2) {
            throw new RuntimeException("发送POST请求出现异常！" + e2.getMessage());
        }
        checkResult(result);
        return result;
    }
    
    public static String generateQuickPayApplyRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank((String)paramsMap.get("amount"))) {
            throw new Exception("amount不能为空");
        }
        if (!paramsMap.containsKey("cardType") || StringUtils.isBlank((String)paramsMap.get("cardType"))) {
            throw new Exception("cardType不能为空");
        }
        if (!paramsMap.containsKey("cardNo") || StringUtils.isBlank((String)paramsMap.get("cardNo"))) {
            throw new Exception("cardNo不能为空");
        }
        if (!paramsMap.containsKey("cardName") || StringUtils.isBlank((String)paramsMap.get("cardName"))) {
            throw new Exception("cardName不能为空");
        }
        if (!paramsMap.containsKey("idCardNo") || StringUtils.isBlank((String)paramsMap.get("idCardNo"))) {
            throw new Exception("idCardNo不能为空");
        }
        if (!paramsMap.containsKey("mobile") || StringUtils.isBlank((String)paramsMap.get("mobile"))) {
            throw new Exception("mobile不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&tradeNo=%s&tradeDate=%s&amount=%s&notifyUrl=%s&extra=%s&summary=%s&expireTime=%s&clientIp=%s&cardType=%s&cardNo=%s&cardName=%s&idCardNo=%s&mobile=%s&cvn2=%s&validDate=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"), paramsMap.get("notifyUrl"), paramsMap.get("extra"), paramsMap.get("summary"), paramsMap.get("expireTime"), paramsMap.get("clientIp"), paramsMap.get("cardType"), paramsMap.get("cardNo"), paramsMap.get("cardName"), paramsMap.get("idCardNo"), paramsMap.get("mobile"), paramsMap.get("cvn2"), paramsMap.get("validDate"));
        return resultStr;
    }
    
    public static String generateQuickPayConfirmRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("opeNo") || StringUtils.isBlank((String)paramsMap.get("opeNo"))) {
            throw new Exception("opeNo不能为空");
        }
        if (!paramsMap.containsKey("opeDate") || StringUtils.isBlank((String)paramsMap.get("opeDate"))) {
            throw new Exception("opeDate不能为空");
        }
        if (!paramsMap.containsKey("sessionID") || StringUtils.isBlank((String)paramsMap.get("sessionID"))) {
            throw new Exception("sessionID不能为空");
        }
        if (!paramsMap.containsKey("dymPwd") || StringUtils.isBlank((String)paramsMap.get("dymPwd"))) {
            throw new Exception("dymPwd不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&opeNo=%s&opeDate=%s&sessionID=%s&dymPwd=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("opeNo"), paramsMap.get("opeDate"), paramsMap.get("sessionID"), paramsMap.get("dymPwd"));
        return resultStr;
    }
    
    private static void checkResult(final String result) throws Exception {
        if (StringUtils.isBlank(result)) {
            throw new NullPointerException("返回数据为空!");
        }
        try {
            final Document resultDOM = DocumentHelper.parseText(result);
            final Element root = resultDOM.getRootElement();
            final String responseData = root.element("detail").asXML();
            final String signMsg = root.element("sign").getStringValue();
            if (StringUtils.isBlank(responseData) || StringUtils.isBlank(signMsg)) {
                throw new Exception("解析返回验签或原数据错误！");
            }
        }
        catch (DocumentException e) {
            throw new RuntimeException("xml解析错误：" + e);
        }
    }
    
    public static String generatePayRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank("merId")) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank("tradeNo")) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank("tradeDate")) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank("amount")) {
            throw new Exception("amount不能为空");
        }
        if (!paramsMap.containsKey("notifyUrl") || StringUtils.isBlank("notifyUrl")) {
            throw new Exception("notifyUrl不能为空");
        }
        if (!paramsMap.containsKey("extra")) {
            throw new Exception("extra可以为空，但必须存在！");
        }
        if (!paramsMap.containsKey("summary") || StringUtils.isBlank("summary")) {
            throw new Exception("summary不能为空");
        }
        final String paramsStr = String.format("service=%s&version=%s&merId=%s&tradeNo=%s&tradeDate=%s&amount=%s&notifyUrl=%s&extra=%s&summary=%s&expireTime=%s&clientIp=%s&bankId=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"), paramsMap.get("notifyUrl"), paramsMap.get("extra"), paramsMap.get("summary"), (paramsMap.get("expireTime") == null) ? "" : paramsMap.get("expireTime"), (paramsMap.get("clientIp") == null) ? "" : paramsMap.get("clientIp"), (paramsMap.get("bankId") == null) ? "" : paramsMap.get("bankId"));
        return paramsStr;
    }
    
    public static String generateAlspQueryRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("typeId") || StringUtils.isBlank((String)paramsMap.get("typeId"))) {
            throw new Exception("typeId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank((String)paramsMap.get("amount"))) {
            throw new Exception("amount不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&typeId=%s&tradeNo=%s&tradeDate=%s&amount=%s&notifyUrl=%s&extra=%s&summary=%s&expireTime=%s&clientIp=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("typeId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"), paramsMap.get("notifyUrl"), paramsMap.get("extra"), paramsMap.get("summary"), paramsMap.get("expireTime"), paramsMap.get("clientIp"));
        return resultStr;
    }
    
    public static String generateAlspQueryRequestH5(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("typeId") || StringUtils.isBlank((String)paramsMap.get("typeId"))) {
            throw new Exception("typeId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank((String)paramsMap.get("amount"))) {
            throw new Exception("amount不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&typeId=%s&tradeNo=%s&tradeDate=%s&amount=%s&notifyUrl=%s&extra=%s&summary=%s&expireTime=%s&clientIp=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("typeId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"), paramsMap.get("notifyUrl"), paramsMap.get("extra"), paramsMap.get("summary"), paramsMap.get("expireTime"), paramsMap.get("clientIp"));
        return resultStr;
    }
    
    public static String generateQueryRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank((String)paramsMap.get("amount"))) {
            throw new Exception("amount不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&tradeNo=%s&tradeDate=%s&amount=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"));
        return resultStr;
    }
    
    public static String generateRefundRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank((String)paramsMap.get("amount"))) {
            throw new Exception("amount不能为空");
        }
        if (!paramsMap.containsKey("summary") || StringUtils.isBlank((String)paramsMap.get("summary"))) {
            throw new Exception("summary不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&tradeNo=%s&tradeDate=%s&amount=%s&summary=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"), paramsMap.get("summary"));
        return resultStr;
    }
    
    public static String generateRefundRequestYue(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"));
        return resultStr;
    }
    
    public static String generateSingleSettRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        if (!paramsMap.containsKey("amount") || StringUtils.isBlank((String)paramsMap.get("amount"))) {
            throw new Exception("amount不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&tradeNo=%s&tradeDate=%s&amount=%s&notifyUrl=%s&extra=%s&summary=%s&bankCardNo=%s&bankCardName=%s&bankId=%s&bankName=%s&purpose=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"), paramsMap.get("amount"), paramsMap.get("notifyUrl"), paramsMap.get("extra"), paramsMap.get("summary"), paramsMap.get("bankCardNo"), paramsMap.get("bankCardName"), paramsMap.get("bankId"), paramsMap.get("bankName"), paramsMap.get("purpose"));
        return resultStr;
    }
    
    public static String generateSingleSettQueryRequest(final Map<String, String> paramsMap) throws Exception {
        if (!paramsMap.containsKey("service") || StringUtils.isBlank((String)paramsMap.get("service"))) {
            throw new Exception("service不能为空");
        }
        if (!paramsMap.containsKey("version") || StringUtils.isBlank((String)paramsMap.get("version"))) {
            throw new Exception("version不能为空");
        }
        if (!paramsMap.containsKey("merId") || StringUtils.isBlank((String)paramsMap.get("merId"))) {
            throw new Exception("merId不能为空");
        }
        if (!paramsMap.containsKey("tradeNo") || StringUtils.isBlank((String)paramsMap.get("tradeNo"))) {
            throw new Exception("tradeNo不能为空");
        }
        if (!paramsMap.containsKey("tradeDate") || StringUtils.isBlank((String)paramsMap.get("tradeDate"))) {
            throw new Exception("tradeDate不能为空");
        }
        final String resultStr = String.format("service=%s&version=%s&merId=%s&tradeNo=%s&tradeDate=%s", paramsMap.get("service"), paramsMap.get("version"), paramsMap.get("merId"), paramsMap.get("tradeNo"), paramsMap.get("tradeDate"));
        return resultStr;
    }
}
