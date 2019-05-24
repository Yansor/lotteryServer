package lottery.domains.content.api.sb;

import com.alibaba.fastjson.JSON;
import java.util.LinkedHashMap;
import java.util.Map;
import javautils.http.HttpClientUtil;
import javautils.http.ToUrlParamUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Win88SBAPI
  implements InitializingBean
{
  private static final Logger log = LoggerFactory.getLogger(Win88SBAPI.class);
  @Value("${sb.opcode}")
  private String opCode;
  @Value("${sb.md5key}")
  private String md5key;
  @Value("${sb.apiurl}")
  private String apiUrl;
  private static final String SPORTBETLOG_URL = "api/GetSportBetLog";
  private static final String HELLO_URL = "api/Hello";
  
  public void afterPropertiesSet()
    throws Exception
  {
    if ((StringUtils.isNotEmpty(this.apiUrl)) && 
      (!this.apiUrl.endsWith("/"))) {
      this.apiUrl += "/";
    }
  }
  
  public static String transSportType(String sportType)
  {
    if (StringUtils.isEmpty(sportType)) {
      return "足球";
    }
    String str = sportType;
    switch (sportType.hashCode())
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
      if (str.equals("4")) {}
      break;
    case 53: 
      if (str.equals("5")) {}
      break;
    case 54: 
      if (str.equals("6")) {}
      break;
    case 55: 
      if (str.equals("7")) {}
      break;
    case 56: 
      if (str.equals("8")) {}
      break;
    case 57: 
      if (str.equals("9")) {}
      break;
    case 1567: 
      if (str.equals("10")) {}
      break;
    case 1568: 
      if (str.equals("11")) {}
      break;
    case 1569: 
      if (str.equals("12")) {}
      break;
    case 1570: 
      if (str.equals("13")) {}
      break;
    case 1571: 
      if (str.equals("14")) {}
      break;
    case 1572: 
      if (str.equals("15")) {}
      break;
    case 1573: 
      if (str.equals("16")) {}
      break;
    case 1574: 
      if (str.equals("17")) {}
      break;
    case 1575: 
      if (str.equals("18")) {}
      break;
    case 1576: 
      if (str.equals("19")) {}
      break;
    case 1598: 
      if (str.equals("20")) {}
      break;
    case 1599: 
      if (str.equals("21")) {}
      break;
    case 1600: 
      if (str.equals("22")) {}
      break;
    case 1601: 
      if (str.equals("23")) {}
      break;
    case 1602: 
      if (str.equals("24")) {}
      break;
    case 1603: 
      if (str.equals("25")) {}
      break;
    case 1604: 
      if (str.equals("26")) {}
      break;
    case 1605: 
      if (str.equals("27")) {}
      break;
    case 1606: 
      if (str.equals("28")) {}
      break;
    case 1607: 
      if (str.equals("29")) {}
      break;
    case 1629: 
      if (str.equals("30")) {}
      break;
    case 1630: 
      if (str.equals("31")) {}
      break;
    case 1631: 
      if (str.equals("32")) {}
      break;
    case 1632: 
      if (str.equals("33")) {}
      break;
    case 1661: 
      if (str.equals("41")) {}
      break;
    case 1662: 
      if (str.equals("42")) {}
      break;
    case 1663: 
      if (str.equals("43")) {}
      break;
    case 1664: 
      if (str.equals("44")) {}
      break;
    case 1691: 
      if (str.equals("50")) {}
      break;
    case 1824: 
      if (str.equals("99")) {}
      break;
    case 48781: 
      if (str.equals("151")) {}
      break;
    case 48782: 
      if (str.equals("152")) {}
      break;
    case 48783: 
      if (str.equals("153")) {}
      break;
    case 48784: 
      if (str.equals("154")) {}
      break;
    case 48812: 
      if (str.equals("161")) {}
      break;
    case 48873: 
      if (str.equals("180")) {}
      break;
    case 48874: 
      if (str.equals("181")) {}
      break;
    case 48875: 
      if (str.equals("182")) {}
      break;
    case 48876: 
      if (str.equals("183")) {}
      break;
    case 48877: 
      if (str.equals("184")) {}
      break;
    case 48878: 
      if (str.equals("185")) {}
      break;
    case 48879: 
      if (str.equals("186")) {}
      break;
    case 49588: 
      if (str.equals("202")) {}
      break;
    case 49594: 
      if (str.equals("208")) {}
      break;
    case 49595: 
      if (str.equals("209")) {}
      break;
    case 49617: 
      if (str.equals("210")) {}
      break;
    case 49742: 
      if (str.equals("251")) {}
      break;
    case 1755331: 
      if (!str.equals("99MP"))
      {
//        break label1371;
//        return "足球";
//        return "篮球";
//        return "足球";
//        return "冰上曲棍球";
//        return "网球";
//        return "排球";
//        return "台球";
//        return "棒球";
//        return "羽毛球";
//        return "高尔夫";
//        return "赛车";
//        return "游泳";
//        return "政治";
//        return "水球";
//        return "跳水";
//        return "拳击";
//        return "射箭";
//        return "乒乓球";
//        return "举重";
//        return "皮划艇";
//        return "体操";
//        return "田径";
//        return "马术";
//        return "手球";
//        return "飞镖";
//        return "橄榄球";
//        return "板球";
//        return "曲棍球";
//        return "冬季运动";
//        return "壁球";
//        return "娱乐";
//        return "网前球";
//        return "骑自行车";
//        return "铁人三项";
//        return "摔跤";
//        return "电子竞技";
//        return "泰拳";
//        return "板球游戏";
//        return "其他运动";
      }
      else
      {
//        return "混合足球";
//        return "赛马";
//        return "灰狗";
//        return "马具";
//        return "赛马固定赔率";
//        return "数字游戏";
//        return "虚拟足球";
//        return "虚拟赛马";
//        return "虚拟灵狮";
//        return "虚拟赛道";
//        return "虚拟F1";
//        return "虚拟自行车";
//        return "虚拟网球";
//        return "基诺";
//        return "赌场";
//        return "RNG游戏";
//        return "迷你游戏";
//        return "移动";
      }
      break;
    }
    label1371:
    return "未知";
  }
  
  public static int transTicketStatus(String ticketStatus)
  {
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
  
  public boolean hello()
  {
    try
    {
      String url = this.apiUrl + "api/Hello" + "?OpCode=" + this.opCode;
      
      String json = HttpClientUtil.get(url, null, 5000);
      if (StringUtils.isEmpty(json))
      {
        log.error("访问SB接口时返回空，访问地址：" + url);
        return false;
      }
      Win88SBCommonResult result = (Win88SBCommonResult)JSON.parseObject(json, Win88SBCommonResult.class);
      if (result == null)
      {
        log.error("连接SB发生解析错误，请求地址：" + url + ",返回：" + json);
        return false;
      }
      if (!"0".equals(result.getErrorCode()))
      {
        log.error("连接SB发生错误,返回：" + json);
        return false;
      }
      return true;
    }
    catch (Exception e)
    {
      log.error("连接SB发生错误", e);
    }
    return false;
  }
  
  public Win88SBSportBetLogResult sportBetLog(String lastVersionKey)
    throws Exception
  {
    LinkedHashMap<String, String> params = new LinkedHashMap();
    params.put("OpCode", this.opCode);
    params.put("LastVersionKey", lastVersionKey);
    params.put("lang", "cs");
    
    Object postResult = post("api/GetSportBetLog", params, Win88SBSportBetLogResult.class);
    
    Win88SBSportBetLogResult result = (Win88SBSportBetLogResult)postResult;
    if (!"0".equals(result.getErrorCode()))
    {
      if ("23000".equals(result.getErrorCode()))
      {
        log.error("获取沙巴投注记录时返回错误码表示现在没有记录,访问参数：" + JSON.toJSONString(params) + ",返回：" + JSON.toJSONString(result));
        return null;
      }
      String errorCode = result.getErrorCode();
      if (StringUtils.isEmpty(errorCode))
      {
        log.error("获取沙巴投注记录时返回错误码未知,访问参数：" + JSON.toJSONString(params) + ",返回：" + JSON.toJSONString(result));
        throw new RuntimeException("获取沙巴投注记录时返回错误码未知,访问参数：" + JSON.toJSONString(params));
      }
      log.error("获取沙巴投注记录时返回错误码" + errorCode + "," + result.getMessage() + ",访问参数：" + JSON.toJSONString(params) + ",返回：" + JSON.toJSONString(result));
      throw new RuntimeException("获取沙巴投注记录时返回错误码" + errorCode + "," + result.getMessage() + ",访问参数：" + JSON.toJSONString(params) + ",返回：" + JSON.toJSONString(result));
    }
    return result;
  }
  
  private Object post(String subUrl, Map<String, String> params, Class<? extends Win88SBResult> resultClass)
    throws Exception
  {
    String paramsStr = ToUrlParamUtils.toUrlParam(params);
    
    String securityToken = this.md5key + "/" + subUrl + "?" + paramsStr;
    securityToken = DigestUtils.md5Hex(securityToken).toUpperCase();
    
    String paramsEncode = "SecurityToken=" + securityToken + "&" + paramsStr;
    
    String url = this.apiUrl + subUrl + "?" + paramsEncode;
    
    log.debug("开始请求沙巴：{}", url);
    
    String json = HttpClientUtil.get(url, null, 30000);
    if (StringUtils.isEmpty(json))
    {
      log.error("连接沙巴返回记录时返回空，访问地址：" + url);
      throw new RuntimeException("连接沙巴返回记录时返回空，访问地址：" + url);
    }
    Win88SBResult result;
    try
    {
      result = (Win88SBResult)JSON.parseObject(json, resultClass);
    }
    catch (Exception e)
    {

      log.error("解析沙巴返回信息错误，请求地址：" + url + ",请求参数：" + JSON.toJSONString(params) + ",返回：" + json);
      throw e;
    }

    if (result == null)
    {
      log.error("解析沙巴返回信息错误，请求地址：" + url + ",请求参数：" + JSON.toJSONString(params) + ",返回：" + json);
      throw new RuntimeException("解析沙巴返回信息错误，请求地址：" + url + ",请求参数：" + JSON.toJSONString(params) + ",返回：" + json);
    }
    return result;
  }
}
