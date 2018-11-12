package lottery.domains.content.api.pt;

import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import java.util.Collection;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import admin.web.WebJSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PTAPI
{
    private static final Logger log;
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
    
    static {
        log = LoggerFactory.getLogger((Class)PTAPI.class);
    }
    
    public String playerCreate(final WebJSONObject webJSON, final String username, final String password) throws Exception {
        String realUsername = String.valueOf(this.prefix) + username;
        realUsername = realUsername.toUpperCase();
        final String url = String.format("/player/create/adminname/%s/kioskname/%s/custom02/WIN88ENTITY/playername/%s/password/%s", this.adminname, this.kioskname, realUsername, password);
        final String result = this.post(url);
        if (StringUtils.isEmpty(result)) {
            webJSON.set(2, "2-7004");
            return null;
        }
        final HashMap<String, Object> resultMap = (HashMap<String, Object>)JSON.parseObject(result, (Class)HashMap.class);
        final String errorCode = this.assertError(resultMap);
        if (errorCode != null) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, errorCode);
            return null;
        }
        final HashMap<String, Object> resultMap2 = (HashMap<String, Object>)JSON.parseObject(resultMap.get("result").toString(), (Class)HashMap.class);
        if (resultMap2 == null || !resultMap2.containsKey("playername")) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, "2-7004");
            return null;
        }
        return resultMap2.get("playername").toString();
    }
    
    public Double playerBalance(final WebJSONObject webJSON, final String username) {
        String realUsername = username;
        realUsername = realUsername.toUpperCase();
        final String url = String.format("/player/balance/playername/%s", realUsername);
        String result;
        try {
            result = this.post(url);
        }
        catch (Exception e) {
            PTAPI.log.error("获取PT余额出错", (Throwable)e);
            webJSON.set(2, "2-7004");
            return null;
        }
        if (StringUtils.isEmpty(result)) {
            webJSON.set(2, "2-7004");
            return null;
        }
        final HashMap<String, Object> resultMap = (HashMap<String, Object>)JSON.parseObject(result, (Class)HashMap.class);
        final String errorCode = this.assertError(resultMap);
        if (errorCode != null) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, errorCode);
            return null;
        }
        final HashMap<String, Object> resultMap2 = (HashMap<String, Object>)JSON.parseObject(resultMap.get("result").toString(), (Class)HashMap.class);
        if (resultMap2 == null || !resultMap2.containsKey("balance")) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, "2-7004");
            return null;
        }
        return Double.valueOf(resultMap2.get("balance").toString());
    }
    
    public boolean playerUpdatePassword(final WebJSONObject webJSON, final String username, final String password) throws Exception {
        String realUsername = username;
        realUsername = realUsername.toUpperCase();
        final String url = String.format("/player/update/playername/%s/password/%s", realUsername, password);
        final String result = this.post(url);
        if (StringUtils.isEmpty(result)) {
            webJSON.set(2, "2-7004");
            return false;
        }
        final HashMap<String, Object> resultMap = (HashMap<String, Object>)JSON.parseObject(result, (Class)HashMap.class);
        final String errorCode = this.assertError(resultMap);
        if (errorCode != null) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, errorCode);
            return false;
        }
        final HashMap<String, Object> resultMap2 = (HashMap<String, Object>)JSON.parseObject(resultMap.get("result").toString(), (Class)HashMap.class);
        if (resultMap2 == null || !resultMap2.containsKey("playername")) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, "2-7004");
            return false;
        }
        return true;
    }
    
    public Double playerDeposit(final WebJSONObject webJSON, final String username, final double amount, final String billNo) throws Exception {
        String realUsername = username;
        realUsername = realUsername.toUpperCase();
        final String url = String.format("/player/deposit/adminname/%s/playername/%s/amount/%s/externaltranid/%s", this.adminname, realUsername, amount, billNo);
        final String result = this.post(url);
        if (StringUtils.isEmpty(result)) {
            webJSON.set(2, "2-7004");
            return null;
        }
        final HashMap<String, Object> resultMap = (HashMap<String, Object>)JSON.parseObject(result, (Class)HashMap.class);
        final String errorCode = this.assertError(resultMap);
        if (errorCode != null) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, errorCode);
            return null;
        }
        if (result.indexOf("currentplayerbalance") == -1) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, "2-7004");
            return null;
        }
        final HashMap<String, Object> resultMap2 = (HashMap<String, Object>)JSON.parseObject(resultMap.get("result").toString(), (Class)HashMap.class);
        final Object currentplayerbalance = resultMap2.get("currentplayerbalance");
        if (currentplayerbalance == null) {
            return 0.0;
        }
        return Double.valueOf(resultMap2.get("currentplayerbalance").toString());
    }
    
    public Double playerWithdraw(final WebJSONObject webJSON, final String username, final double amount, final String billNo) throws Exception {
        String realUsername = String.valueOf(this.prefix) + username;
        realUsername = realUsername.toUpperCase();
        final String url = String.format("/player/withdraw/adminname/%s/playername/%s/amount/%s/externaltranid/%s", this.adminname, realUsername, amount, billNo);
        final String result = this.post(url);
        if (StringUtils.isEmpty(result)) {
            webJSON.set(2, "2-7004");
            return null;
        }
        final HashMap<String, Object> resultMap = (HashMap<String, Object>)JSON.parseObject(result, (Class)HashMap.class);
        final String errorCode = this.assertError(resultMap);
        if (errorCode != null) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, errorCode);
            return null;
        }
        if (result.indexOf("currentplayerbalance") == -1) {
            PTAPI.log.error("PT返回错误：" + result);
            webJSON.set(2, "2-7004");
            return null;
        }
        final HashMap<String, Object> resultMap2 = (HashMap<String, Object>)JSON.parseObject(resultMap.get("result").toString(), (Class)HashMap.class);
        final Object currentplayerbalance = resultMap2.get("currentplayerbalance");
        if (currentplayerbalance == null) {
            return 0.0;
        }
        return Double.valueOf(resultMap2.get("currentplayerbalance").toString());
    }
    
    public PTPlayerGame playerGames(final String startTime, final String endTime) throws Exception {
        final PTPlayerGame page1 = this.playerGamesByPage(startTime, endTime, 2000, 1);
        if (page1 == null) {
            return null;
        }
        int totalPages = page1.getPagination().getTotalPages();
        int currentPage = 2;
        while (--totalPages > 1) {
            PTPlayerGame subPage = this.playerGamesByPage(startTime, endTime, 2000, currentPage++);
            if (subPage == null) {
                subPage = this.playerGamesByPage(startTime, endTime, 2000, currentPage);
            }
            if (subPage == null) {
                return null;
            }
            if (subPage.getPagination().getTotalCount() <= 0) {
                continue;
            }
            page1.getResult().addAll(subPage.getResult());
        }
        return page1;
    }
    
    private PTPlayerGame playerGamesByPage(final String startTime, final String endTime, final int perPage, final int page) throws Exception {
        final String url = String.format("/customreport/getdata/reportname/PlayerGames/startdate/%s/enddate/%s/frozen/all/perPage/%s/page/%s", startTime, endTime, perPage, page);
        final String result = this.post(url);
        if (StringUtils.isEmpty(result)) {
            PTAPI.log.error("获取PT注单返回空");
            return null;
        }
        final HashMap<String, Object> resultMap = (HashMap<String, Object>)JSON.parseObject(result, (Class)HashMap.class);
        final String errorCode = this.assertError(resultMap);
        if (errorCode != null) {
            PTAPI.log.error("获取PT注单错误" + result);
            throw new Exception("转换PT注单错误");
        }
        final PTPlayerGame ptPlayerGame = (PTPlayerGame)JSON.parseObject(result, (Class)PTPlayerGame.class);
        return ptPlayerGame;
    }
    
    private String assertError(final HashMap<String, Object> resultMap) {
        if (resultMap.containsKey("errorcode")) {
            final Object errorcode = resultMap.get("errorcode");
            final String code = PTCode.transErrorCode(errorcode.toString());
            return code;
        }
        return null;
    }
    
    private String post(final String subUrl) throws Exception {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.add("X_ENTITY_KEY", this.entityKey);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            final HttpEntity<String> request = (HttpEntity<String>)new HttpEntity((Object)"", (MultiValueMap)headers);
            final String result = (String)this.ptRestTemplate.postForObject(String.valueOf(this.url) + subUrl, (Object)request, (Class)String.class, new Object[0]);
            return result;
        }
        catch (Exception e) {
            PTAPI.log.error("连接PT发生错误，路径：" + subUrl, (Throwable)e);
            throw e;
        }
    }
}
