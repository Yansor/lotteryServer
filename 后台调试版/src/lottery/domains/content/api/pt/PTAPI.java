package lottery.domains.content.api.pt;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PTAPI
{
  private static final Logger log = LoggerFactory.getLogger(PTAPI.class);
  private static final int PER_PAGE = 2000;
  @Value("${pt.entitykey}")
  private String entityKey;
  @Value("${pt.prefix}")
  private String prefix;
  @Value("${pt.kioskname}")
  private String kioskname;
  @Value("${pt.adminname}")
  private String adminname;
  @Value("${pt.url}")
  private String url;
  @Autowired
  @Qualifier("ptRestTemplate")
  private RestTemplate ptRestTemplate;
  
  public String playerCreate(WebJSONObject webJSON, String username, String password)
    throws Exception
  {
    String realUsername = this.prefix + username;
    realUsername = realUsername.toUpperCase();
    String url = String.format("/player/create/adminname/%s/kioskname/%s/custom02/WIN88ENTITY/playername/%s/password/%s", new Object[] { this.adminname, this.kioskname, realUsername, password });
    
    String result = post(url);
    if (StringUtils.isEmpty(result))
    {
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    HashMap<String, Object> resultMap = (HashMap)JSON.parseObject(result, HashMap.class);
    String errorCode = assertError(resultMap);
    if (errorCode != null)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), errorCode);
      return null;
    }
    HashMap<String, Object> resultMap2 = (HashMap)JSON.parseObject(resultMap.get("result").toString(), HashMap.class);
    if ((resultMap2 == null) || (!resultMap2.containsKey("playername")))
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    return resultMap2.get("playername").toString();
  }
  
  public Double playerBalance(WebJSONObject webJSON, String username)
  {
    String realUsername = username;
    realUsername = realUsername.toUpperCase();
    String url = String.format("/player/balance/playername/%s", new Object[] { realUsername });
    String result;
    try
    {
      result = post(url);
    }
    catch (Exception e)
    {

      log.error("获取PT余额出错", e);
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    if (StringUtils.isEmpty(result))
    {
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    HashMap<String, Object> resultMap = (HashMap)JSON.parseObject(result, HashMap.class);
    String errorCode = assertError(resultMap);
    if (errorCode != null)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), errorCode);
      return null;
    }
    HashMap<String, Object> resultMap2 = (HashMap)JSON.parseObject(resultMap.get("result").toString(), HashMap.class);
    if ((resultMap2 == null) || (!resultMap2.containsKey("balance")))
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    return Double.valueOf(resultMap2.get("balance").toString());
  }
  
  public boolean playerUpdatePassword(WebJSONObject webJSON, String username, String password)
    throws Exception
  {
    String realUsername = username;
    realUsername = realUsername.toUpperCase();
    String url = String.format("/player/update/playername/%s/password/%s", new Object[] { realUsername, password });
    
    String result = post(url);
    if (StringUtils.isEmpty(result))
    {
      webJSON.set(Integer.valueOf(2), "2-7004");
      return false;
    }
    HashMap<String, Object> resultMap = (HashMap)JSON.parseObject(result, HashMap.class);
    String errorCode = assertError(resultMap);
    if (errorCode != null)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), errorCode);
      return false;
    }
    HashMap<String, Object> resultMap2 = (HashMap)JSON.parseObject(resultMap.get("result").toString(), HashMap.class);
    if ((resultMap2 == null) || (!resultMap2.containsKey("playername")))
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), "2-7004");
      return false;
    }
    return true;
  }
  
  public Double playerDeposit(WebJSONObject webJSON, String username, double amount, String billNo)
    throws Exception
  {
    String realUsername = username;
    realUsername = realUsername.toUpperCase();
    String url = String.format("/player/deposit/adminname/%s/playername/%s/amount/%s/externaltranid/%s", new Object[] { this.adminname, realUsername, Double.valueOf(amount), billNo });
    
    String result = post(url);
    if (StringUtils.isEmpty(result))
    {
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    HashMap<String, Object> resultMap = (HashMap)JSON.parseObject(result, HashMap.class);
    String errorCode = assertError(resultMap);
    if (errorCode != null)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), errorCode);
      return null;
    }
    if (result.indexOf("currentplayerbalance") == -1)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    HashMap<String, Object> resultMap2 = (HashMap)JSON.parseObject(resultMap.get("result").toString(), HashMap.class);
    Object currentplayerbalance = resultMap2.get("currentplayerbalance");
    if (currentplayerbalance == null) {
      return Double.valueOf(0.0D);
    }
    return Double.valueOf(resultMap2.get("currentplayerbalance").toString());
  }
  
  public Double playerWithdraw(WebJSONObject webJSON, String username, double amount, String billNo)
    throws Exception
  {
    String realUsername = this.prefix + username;
    realUsername = realUsername.toUpperCase();
    String url = String.format("/player/withdraw/adminname/%s/playername/%s/amount/%s/externaltranid/%s", new Object[] { this.adminname, realUsername, Double.valueOf(amount), billNo });
    
    String result = post(url);
    if (StringUtils.isEmpty(result))
    {
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    HashMap<String, Object> resultMap = (HashMap)JSON.parseObject(result, HashMap.class);
    String errorCode = assertError(resultMap);
    if (errorCode != null)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), errorCode);
      return null;
    }
    if (result.indexOf("currentplayerbalance") == -1)
    {
      log.error("PT返回错误：" + result);
      webJSON.set(Integer.valueOf(2), "2-7004");
      return null;
    }
    HashMap<String, Object> resultMap2 = (HashMap)JSON.parseObject(resultMap.get("result").toString(), HashMap.class);
    Object currentplayerbalance = resultMap2.get("currentplayerbalance");
    if (currentplayerbalance == null) {
      return Double.valueOf(0.0D);
    }
    return Double.valueOf(resultMap2.get("currentplayerbalance").toString());
  }
  
  public PTPlayerGame playerGames(String startTime, String endTime)
    throws Exception
  {
    PTPlayerGame page1 = playerGamesByPage(startTime, endTime, 2000, 1);
    if (page1 == null) {
      return null;
    }
    int totalPages = page1.getPagination().getTotalPages();
    
    int currentPage = 2;
    do
    {
      PTPlayerGame subPage = playerGamesByPage(startTime, endTime, 2000, currentPage++);
      if (subPage == null) {
        subPage = playerGamesByPage(startTime, endTime, 2000, currentPage);
      }
      if (subPage == null) {
        return null;
      }
      if (subPage.getPagination().getTotalCount() > 0) {
        page1.getResult().addAll(subPage.getResult());
      }
      totalPages--;
    } while (totalPages > 1);
    return page1;
  }
  
  private PTPlayerGame playerGamesByPage(String startTime, String endTime, int perPage, int page)
    throws Exception
  {
    String url = String.format("/customreport/getdata/reportname/PlayerGames/startdate/%s/enddate/%s/frozen/all/perPage/%s/page/%s", new Object[] {
      startTime, endTime, Integer.valueOf(perPage), Integer.valueOf(page) });
    
    String result = post(url);
    if (StringUtils.isEmpty(result))
    {
      log.error("获取PT注单返回空");
      return null;
    }
    HashMap<String, Object> resultMap = (HashMap)JSON.parseObject(result, HashMap.class);
    String errorCode = assertError(resultMap);
    if (errorCode != null)
    {
      log.error("获取PT注单错误" + result);
      throw new Exception("转换PT注单错误");
    }
    PTPlayerGame ptPlayerGame = (PTPlayerGame)JSON.parseObject(result, PTPlayerGame.class);
    return ptPlayerGame;
  }
  
  private String assertError(HashMap<String, Object> resultMap)
  {
    if (resultMap.containsKey("errorcode"))
    {
      Object errorcode = resultMap.get("errorcode");
      String code = PTCode.transErrorCode(errorcode.toString());
      return code;
    }
    return null;
  }
  
  private String post(String subUrl)
    throws Exception
  {
    try
    {
      HttpHeaders headers = new HttpHeaders();
      headers.add("X_ENTITY_KEY", this.entityKey);
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      
      HttpEntity<String> request = new HttpEntity("", headers);
      
      return (String)this.ptRestTemplate.postForObject(this.url + subUrl, request, String.class, new Object[0]);
    }
    catch (Exception e)
    {
      log.error("连接PT发生错误，路径：" + subUrl, e);
      throw e;
    }
  }
}
