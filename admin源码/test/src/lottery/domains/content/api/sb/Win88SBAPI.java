package lottery.domains.content.api.sb;

import org.apache.commons.codec.digest.DigestUtils;
import javautils.http.ToUrlParamUtils;
import java.util.LinkedHashMap;
import com.alibaba.fastjson.JSON;
import java.util.Map;
import javautils.http.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;

@Component
public class Win88SBAPI implements InitializingBean
{
    private static final Logger log;
    @Value("${sb.opcode}")
    private String opCode;
    @Value("${sb.md5key}")
    private String md5key;
    @Value("${sb.apiurl}")
    private String apiUrl;
    private static final String SPORTBETLOG_URL = "api/GetSportBetLog";
    private static final String HELLO_URL = "api/Hello";
    
    static {
        log = LoggerFactory.getLogger((Class)Win88SBAPI.class);
    }
    
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isNotEmpty(this.apiUrl) && !this.apiUrl.endsWith("/")) {
            this.apiUrl = String.valueOf(this.apiUrl) + "/";
        }
    }
    
    public static String transSportType(final String sportType) {
        if (StringUtils.isEmpty(sportType)) {
            return "足球";
        }
        switch (sportType) {
            case "1": {
                return "足球";
            }
            case "2": {
                return "篮球";
            }
            case "3": {
                return "足球";
            }
            case "4": {
                return "冰上曲棍球";
            }
            case "5": {
                return "网球";
            }
            case "6": {
                return "排球";
            }
            case "7": {
                return "台球";
            }
            case "8": {
                return "棒球";
            }
            case "9": {
                return "羽毛球";
            }
            case "10": {
                return "高尔夫";
            }
            case "11": {
                return "赛车";
            }
            case "12": {
                return "游泳";
            }
            case "13": {
                return "政治";
            }
            case "14": {
                return "水球";
            }
            case "15": {
                return "跳水";
            }
            case "16": {
                return "拳击";
            }
            case "17": {
                return "射箭";
            }
            case "18": {
                return "乒乓球";
            }
            case "19": {
                return "举重";
            }
            case "20": {
                return "皮划艇";
            }
            case "21": {
                return "体操";
            }
            case "22": {
                return "田径";
            }
            case "23": {
                return "马术";
            }
            case "24": {
                return "手球";
            }
            case "25": {
                return "飞镖";
            }
            case "26": {
                return "橄榄球";
            }
            case "27": {
                return "板球";
            }
            case "28": {
                return "曲棍球";
            }
            case "29": {
                return "冬季运动";
            }
            case "30": {
                return "壁球";
            }
            case "31": {
                return "娱乐";
            }
            case "32": {
                return "网前球";
            }
            case "33": {
                return "骑自行车";
            }
            case "41": {
                return "铁人三项";
            }
            case "42": {
                return "摔跤";
            }
            case "43": {
                return "电子竞技";
            }
            case "44": {
                return "泰拳";
            }
            case "50": {
                return "板球游戏";
            }
            case "99": {
                return "其他运动";
            }
            case "151": {
                return "赛马";
            }
            case "152": {
                return "灰狗";
            }
            case "153": {
                return "马具";
            }
            case "154": {
                return "赛马固定赔率";
            }
            case "161": {
                return "数字游戏";
            }
            case "180": {
                return "虚拟足球";
            }
            case "181": {
                return "虚拟赛马";
            }
            case "182": {
                return "虚拟灵狮";
            }
            case "183": {
                return "虚拟赛道";
            }
            case "184": {
                return "虚拟F1";
            }
            case "185": {
                return "虚拟自行车";
            }
            case "186": {
                return "虚拟网球";
            }
            case "202": {
                return "基诺";
            }
            case "208": {
                return "RNG游戏";
            }
            case "209": {
                return "迷你游戏";
            }
            case "210": {
                return "移动";
            }
            case "251": {
                return "赌场";
            }
            case "99MP": {
                return "混合足球";
            }
            default:
                break;
        }
        return "未知";
    }
    
    public static int transTicketStatus(final String ticketStatus) {
        if ("Waiting".equalsIgnoreCase(ticketStatus)) {
            return 2;
        }
        if ("Running".equalsIgnoreCase(ticketStatus)) {
            return 3;
        }
        if ("Won".equalsIgnoreCase(ticketStatus)) {
            return 4;
        }
        if ("Lose".equalsIgnoreCase(ticketStatus)) {
            return 5;
        }
        if ("Draw".equalsIgnoreCase(ticketStatus)) {
            return 6;
        }
        if ("Reject".equalsIgnoreCase(ticketStatus)) {
            return 7;
        }
        if ("Refund".equalsIgnoreCase(ticketStatus)) {
            return 8;
        }
        if ("Void".equalsIgnoreCase(ticketStatus)) {
            return 9;
        }
        if ("Half Won".equalsIgnoreCase(ticketStatus)) {
            return 10;
        }
        if ("Half LOSE".equalsIgnoreCase(ticketStatus)) {
            return 11;
        }
        return -1;
    }
    
    public boolean hello() {
        try {
            final String url = new StringBuffer(this.apiUrl).append("api/Hello").append("?OpCode=").append(this.opCode).toString();
            final String json = HttpClientUtil.get(url, null, 5000);
            if (StringUtils.isEmpty(json)) {
                Win88SBAPI.log.error("访问SB接口时返回空，访问地址：" + url);
                return false;
            }
            final Win88SBCommonResult result = (Win88SBCommonResult)JSON.parseObject(json, (Class)Win88SBCommonResult.class);
            if (result == null) {
                Win88SBAPI.log.error("连接SB发生解析错误，请求地址：" + url + ",返回：" + json);
                return false;
            }
            if (!"0".equals(result.getErrorCode())) {
                Win88SBAPI.log.error("连接SB发生错误,返回：" + json);
                return false;
            }
            return true;
        }
        catch (Exception e) {
            Win88SBAPI.log.error("连接SB发生错误", (Throwable)e);
            return false;
        }
    }
    
    public Win88SBSportBetLogResult sportBetLog(final String lastVersionKey) throws Exception {
        final LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
        params.put("OpCode", this.opCode);
        params.put("LastVersionKey", lastVersionKey);
        params.put("lang", "cs");
        final Object postResult = this.post("api/GetSportBetLog", params, Win88SBSportBetLogResult.class);
        final Win88SBSportBetLogResult result = (Win88SBSportBetLogResult)postResult;
        if ("0".equals(result.getErrorCode())) {
            return result;
        }
        if ("23000".equals(result.getErrorCode())) {
            Win88SBAPI.log.error("获取沙巴投注记录时返回错误码表示现在没有记录,访问参数：" + JSON.toJSONString((Object)params) + ",返回：" + JSON.toJSONString((Object)result));
            return null;
        }
        final String errorCode = result.getErrorCode();
        if (StringUtils.isEmpty(errorCode)) {
            Win88SBAPI.log.error("获取沙巴投注记录时返回错误码未知,访问参数：" + JSON.toJSONString((Object)params) + ",返回：" + JSON.toJSONString((Object)result));
            throw new RuntimeException("获取沙巴投注记录时返回错误码未知,访问参数：" + JSON.toJSONString((Object)params));
        }
        Win88SBAPI.log.error("获取沙巴投注记录时返回错误码" + errorCode + "," + result.getMessage() + ",访问参数：" + JSON.toJSONString((Object)params) + ",返回：" + JSON.toJSONString((Object)result));
        throw new RuntimeException("获取沙巴投注记录时返回错误码" + errorCode + "," + result.getMessage() + ",访问参数：" + JSON.toJSONString((Object)params) + ",返回：" + JSON.toJSONString((Object)result));
    }
    
    private Object post(final String subUrl, final Map<String, String> params, final Class<? extends Win88SBResult> resultClass) throws Exception {
        final String paramsStr = ToUrlParamUtils.toUrlParam(params);
        String securityToken = new StringBuffer(this.md5key).append("/").append(subUrl).append("?").append(paramsStr).toString();
        securityToken = DigestUtils.md5Hex(securityToken).toUpperCase();
        final String paramsEncode = new StringBuffer("SecurityToken=").append(securityToken).append("&").append(paramsStr).toString();
        final String url = new StringBuffer(this.apiUrl).append(subUrl).append("?").append(paramsEncode).toString();
        Win88SBAPI.log.debug("开始请求沙巴：{}", (Object)url);
        final String json = HttpClientUtil.get(url, null, 30000);
        if (StringUtils.isEmpty(json)) {
            Win88SBAPI.log.error("连接沙巴返回记录时返回空，访问地址：" + url);
            throw new RuntimeException("连接沙巴返回记录时返回空，访问地址：" + url);
        }
        Win88SBResult result;
        try {
            result = (Win88SBResult)JSON.parseObject(json, (Class)resultClass);
        }
        catch (Exception e) {
            Win88SBAPI.log.error("解析沙巴返回信息错误，请求地址：" + url + ",请求参数：" + JSON.toJSONString((Object)params) + ",返回：" + json);
            throw e;
        }
        if (result == null) {
            Win88SBAPI.log.error("解析沙巴返回信息错误，请求地址：" + url + ",请求参数：" + JSON.toJSONString((Object)params) + ",返回：" + json);
            throw new RuntimeException("解析沙巴返回信息错误，请求地址：" + url + ",请求参数：" + JSON.toJSONString((Object)params) + ",返回：" + json);
        }
        return result;
    }
}
