package lottery.domains.content.api.ag;

import admin.web.WebJSONObject;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.date.Moment;
import javautils.encrypt.DESUtil;
import javautils.ftp.FTPServer;
import javautils.http.HttpClientUtil;
import javautils.http.ToUrlParamUtils;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
public class AGAPI
{
  private static final Logger log = LoggerFactory.getLogger(AGAPI.class);
  private static final String AG_KEY_SEPARATOR = "/\\\\/";
  private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
  @Value("${ag.agin.cagent}")
  private String aginCagent;
  @Value("${ag.md5key}")
  private String md5key;
  @Value("${ag.deskey}")
  private String deskey;
  @Value("${ag.giurl}")
  private String giurl;
  @Value("${ag.gciurl}")
  private String gciurl;
  @Value("${ag.actype}")
  private String actype;
  @Value("${ag.oddtype}")
  private String oddtype;
  @Value("${ag.ftpusername}")
  private String ftpUsername;
  @Value("${ag.ftppassword}")
  private String ftpPassword;
  @Value("${ag.ftpurl}")
  private String ftpUrl;
  @Autowired
  private RestTemplate restTemplate;
  
  public String forwardGame(String loginname, String password, String website)
  {
    Map<String, String> paramsMap = new HashMap();
    paramsMap.put("cagent", this.aginCagent);
    paramsMap.put("loginname", loginname);
    paramsMap.put("password", password);
    paramsMap.put("dm", website);
    String sid = this.aginCagent + System.currentTimeMillis();
    paramsMap.put("sid", sid);
    paramsMap.put("actype", this.actype);
    paramsMap.put("lang", "1");
    paramsMap.put("method", "lg");
    paramsMap.put("gameType", "1");
    paramsMap.put("oddtype", this.oddtype);
    paramsMap.put("cur", "CNY");
    
    String paramsStr = ToUrlParamUtils.toUrlParam(paramsMap, "/\\\\/", false);
    
    String targetParams = DESUtil.getInstance().encryptStr(paramsStr, this.deskey);
    targetParams = targetParams.replaceAll(LINE_SEPARATOR, "");
    
    String key = DigestUtils.md5Hex(targetParams + this.md5key);
    
    String url = this.gciurl + "/forwardGame.do?params=" + targetParams + "&key=" + key;
    return url;
  }
  
  public String checkOrCreateGameAccount(WebJSONObject webJSON, String username, String password)
  {
    HashMap<String, String> params = new HashMap();
    params.put("cagent", this.aginCagent);
    params.put("loginname", username);
    params.put("method", "lg");
    params.put("actype", this.actype);
    params.put("password", password);
    params.put("oddtype", this.oddtype);
    params.put("cur", "CNY");
    
    AGResult result = post(params);
    if (result == null)
    {
      log.error("AG返回内容解析为空");
      webJSON.set(Integer.valueOf(2), "2-8000");
      return null;
    }
    if (!"0".equals(result.getInfo()))
    {
      log.error("AG返回错误：" + JSON.toJSONString(result));
      String errorCode = AGCode.transErrorCode(result.getInfo());
      if ("2-8006".equals(errorCode)) {
        webJSON.set(Integer.valueOf(2), errorCode);
      } else {
        webJSON.set(Integer.valueOf(2), errorCode);
      }
      return null;
    }
    return username;
  }
  
  public Double getBalance(WebJSONObject webJSON, String username, String password)
  {
    HashMap<String, String> params = new HashMap();
    params.put("cagent", this.aginCagent);
    params.put("loginname", username);
    params.put("method", "gb");
    params.put("actype", this.actype);
    params.put("password", password);
    params.put("cur", "CNY");
    
    AGResult result = post(params);
    if (result == null)
    {
      log.error("AG返回内容解析为空");
      webJSON.set(Integer.valueOf(2), "2-8000");
      return null;
    }
    if (!NumberUtils.isNumber(result.getInfo()))
    {
      log.error("AG返回错误：" + JSON.toJSONString(result));
      String errorCode = AGCode.transErrorCode(result.getInfo());
      if ("2-8006".equals(errorCode)) {
        webJSON.set(Integer.valueOf(2), errorCode);
      } else {
        webJSON.set(Integer.valueOf(2), errorCode);
      }
      return null;
    }
    return Double.valueOf(result.getInfo());
  }
  
  public String transferIn(WebJSONObject webJSON, String username, String password, int amount)
  {
    return transfer(webJSON, username, password, amount, true);
  }
  
  public String transferOut(WebJSONObject webJSON, String username, String password, int amount)
  {
    return transfer(webJSON, username, password, amount, false);
  }
  
  private String transfer(WebJSONObject webJSON, String username, String password, int amount, boolean in)
  {
    String billno = prepareTransferCredit(webJSON, username, password, amount, in);
    if (StringUtils.isEmpty(billno)) {
      return null;
    }
    try
    {
      boolean confirm = transferCreditConfirm(webJSON, username, password, amount, billno, in);
      if (!confirm) {
        return null;
      }
      boolean result = queryOrderStatus(webJSON, billno);
      return !result ? null : billno;
    }
    catch (Exception e)
    {
      log.error("AG转账发生异常：" + billno, e);
      boolean result = queryOrderStatus(webJSON, billno);
      if (!result) {
//        tmpTernaryOp = null;
      }
    }
    return billno;
  }
  
  private String prepareTransferCredit(WebJSONObject webJSON, String username, String password, int amount, boolean in)
  {
    HashMap<String, String> params = new HashMap();
    params.put("cagent", this.aginCagent);
    params.put("loginname", username);
    params.put("method", "tc");
    String billno = this.aginCagent + System.nanoTime() + RandomUtils.nextInt(99);
    params.put("billno", billno);
    params.put("type", in ? "IN" : "OUT");
    params.put("credit", amount+"");
    params.put("actype", this.actype);
    params.put("password", password);
    params.put("cur", "CNY");
    
    AGResult result = post(params);
    if (result == null)
    {
      log.error("AG返回内容解析为空");
      webJSON.set(Integer.valueOf(2), "2-8000");
      return null;
    }
    if (!"0".equals(result.getInfo()))
    {
      log.error("AG返回错误：" + JSON.toJSONString(result));
      String errorCode = AGCode.transErrorCode(result.getInfo());
      if ("2-8006".equals(errorCode)) {
        webJSON.set(Integer.valueOf(2), errorCode);
      } else {
        webJSON.set(Integer.valueOf(2), errorCode);
      }
      return null;
    }
    return billno;
  }
  
  private boolean transferCreditConfirm(WebJSONObject webJSON, String username, String password, int amount, String billno, boolean in)
  {
    HashMap<String, String> params = new HashMap();
    params.put("cagent", this.aginCagent);
    params.put("loginname", username);
    params.put("method", "tcc");
    params.put("billno", billno);
    params.put("type", in ? "IN" : "OUT");
    params.put("credit", amount+"");
    params.put("actype", this.actype);
    params.put("flag", "1");
    params.put("password", password);
    params.put("cur", "CNY");
    
    AGResult result = post(params);
    if (result == null)
    {
      log.error("AG返回内容解析为空");
      webJSON.set(Integer.valueOf(2), "2-8000");
      return false;
    }
    if ("0".equals(result.getInfo())) {
      return true;
    }
    if ("1".equals(result.getInfo()))
    {
      log.error("AG转账确认失败：" + JSON.toJSONString(result));
      webJSON.set(Integer.valueOf(2), "2-8008");
      return false;
    }
    if ("2".equals(result.getInfo()))
    {
      webJSON.set(Integer.valueOf(2), "2-8007");
      return false;
    }
    if ("network_error".equals(result.getInfo()))
    {
      boolean status = queryOrderStatus(webJSON, billno);
      return status;
    }
    log.error("AG返回错误：" + JSON.toJSONString(result));
    String errorCode = AGCode.transErrorCode(result.getInfo());
    if ("2-8006".equals(errorCode)) {
      webJSON.set(Integer.valueOf(2), errorCode);
    } else {
      webJSON.set(Integer.valueOf(2), errorCode);
    }
    return false;
  }
  
  private boolean queryOrderStatus(WebJSONObject webJSON, String billno)
  {
    HashMap<String, String> params = new HashMap();
    params.put("cagent", this.aginCagent);
    params.put("billno", billno);
    params.put("method", "qos");
    params.put("actype", this.actype);
    params.put("cur", "CNY");
    
    AGResult result = post(params);
    if (result == null)
    {
      log.error("AG返回内容解析为空");
      webJSON.set(Integer.valueOf(2), "2-8000");
      return false;
    }
    if ("0".equals(result.getInfo())) {
      return true;
    }
    if ("1".equals(result.getInfo()))
    {
      webJSON.set(Integer.valueOf(2), "2-8008");
      return false;
    }
    if ("2".equals(result.getInfo()))
    {
      webJSON.set(Integer.valueOf(2), "2-8007");
      return false;
    }
    log.error("AG返回错误：" + JSON.toJSONString(result));
    String errorCode = AGCode.transErrorCode(result.getInfo());
    if ("2-8006".equals(errorCode)) {
      webJSON.set(Integer.valueOf(2), errorCode);
    } else {
      webJSON.set(Integer.valueOf(2), errorCode);
    }
    return false;
  }
  
  public List<AGBetRecord> getRecords(String startTime, String endTime)
    throws Exception
  {
    FTPServer ftpUtil = new FTPServer();
    try
    {
      ftpUtil.connectServer(this.ftpUrl, 21, this.ftpUsername, this.ftpPassword, null);
      
      Moment start = new Moment().fromTime(startTime);
      Moment end = new Moment().fromTime(endTime);
      
      String _startDate = start.format("yyyyMMdd");
      String _endDate = end.format("yyyyMMdd");
      String _startTime = start.format("yyyyMMddHHmm");
      String _endTime = end.format("yyyyMMddHHmm");
      
      List<String> readFiles = new ArrayList();
      
      List<String> startFiles = ftpUtil.getFileList("/AGIN/" + _startDate);
      startFiles = filterFiles(startFiles, _startDate, _startTime, _endTime, "AGIN");
      if (CollectionUtils.isNotEmpty(startFiles)) {
        readFiles.addAll(startFiles);
      }
      List<String> hunterStartFiles = ftpUtil.getFileList("/HUNTER/" + _startDate);
      hunterStartFiles = filterFiles(hunterStartFiles, _startDate, _startTime, _endTime, "HUNTER");
      if (CollectionUtils.isNotEmpty(hunterStartFiles)) {
        readFiles.addAll(hunterStartFiles);
      }
      List<String> xinStartFiles = ftpUtil.getFileList("/XIN/" + _startDate);
      xinStartFiles = filterFiles(xinStartFiles, _startDate, _startTime, _endTime, "XIN");
      if (CollectionUtils.isNotEmpty(xinStartFiles)) {
        readFiles.addAll(xinStartFiles);
      }
      List<String> yoplayStartFiles = ftpUtil.getFileList("/YOPLAY/" + _startDate);
      yoplayStartFiles = filterFiles(yoplayStartFiles, _startDate, _startTime, _endTime, "YOPLAY");
      if (CollectionUtils.isNotEmpty(yoplayStartFiles)) {
        readFiles.addAll(yoplayStartFiles);
      }
      List<String> xinEndFiles;
      if (!_startDate.equals(_endDate))
      {
        List<String> endFiles = ftpUtil.getFileList("/AGIN/" + _endDate);
        endFiles = filterFiles(endFiles, _endDate, _startTime, _endTime, "AGIN");
        if (CollectionUtils.isNotEmpty(endFiles)) {
          readFiles.addAll(endFiles);
        }
        List<String> hunterEndFiles = ftpUtil.getFileList("/HUNTER/" + _endDate);
        hunterEndFiles = filterFiles(hunterEndFiles, _endDate, _startTime, _endTime, "HUNTER");
        if (CollectionUtils.isNotEmpty(hunterEndFiles)) {
          readFiles.addAll(hunterEndFiles);
        }
        xinEndFiles = ftpUtil.getFileList("/XIN/" + _endDate);
        xinEndFiles = filterFiles(xinEndFiles, _endDate, _startTime, _endTime, "XIN");
        if (CollectionUtils.isNotEmpty(xinEndFiles)) {
          readFiles.addAll(xinEndFiles);
        }
        List<String> yoplayFiles = ftpUtil.getFileList("/YOPLAY/" + _endDate);
        yoplayFiles = filterFiles(yoplayFiles, _endDate, _startTime, _endTime, "YOPLAY");
        if (CollectionUtils.isNotEmpty(yoplayFiles)) {
          readFiles.addAll(yoplayFiles);
        }
      }
      if (CollectionUtils.isEmpty(readFiles)) {
        return null;
      }
      List<AGBetRecord> records = new ArrayList();
      for (String readFile : readFiles)
      {
        String xml = ftpUtil.readFile(readFile);
        if (!StringUtils.isEmpty(xml))
        {
          List<AGBetRecord> betRecords = toRecords(xml);
          if (CollectionUtils.isNotEmpty(betRecords)) {
            records.addAll(betRecords);
          }
        }
      }
      return records;
    }
    catch (Exception e)
    {
      log.error("获取AG投注记录时出错", e);
      throw e;
    }
    finally
    {
      try
      {
        ftpUtil.closeServer();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
  }
  
  private List<String> filterFiles(List<String> files, String date, String startTime, String endTime, String folder)
  {
    if (CollectionUtils.isEmpty(files)) {
      return null;
    }
    List<String> filterFiles = new ArrayList();
    for (String file : files)
    {
      String fileTime = file.split("\\.")[0];
      if ((fileTime.compareTo(startTime) >= 0) && (fileTime.compareTo(endTime) <= 0)) {
        filterFiles.add("/" + folder + "/" + date + "/" + file);
      }
    }
    return filterFiles;
  }
  
  private AGResult post(HashMap<String, String> params)
  {
    try
    {
      Map<String, String> headers = new HashMap();
      headers.put("User-Agent", "WEB_LIB_GI_" + this.aginCagent);
      headers.put("Content-Type", "text/xml; utf-8=;charset=UTF-8");
      
      String paramsStr = ToUrlParamUtils.toUrlParam(params, "/\\\\/", false);
      String targetParams = DESUtil.getInstance().encryptStr(paramsStr, this.deskey);
      targetParams = targetParams.replaceAll(LINE_SEPARATOR, "");
      
      String key = DigestUtils.md5Hex(targetParams + this.md5key);
      
      String url = this.giurl + "/doBusiness.do?params=" + targetParams + "&key=" + key;
      
      log.debug("AG操作参数URL：{}，操作参数：{}", url, JSON.toJSONString(params));
      
      String xml = HttpClientUtil.post(url, null, headers, 100000);
      
      return toResult(xml);
    }
    catch (Exception e)
    {
      log.error("连接AG发生错误，请求参数：" + JSON.toJSONString(params), e);
    }
    return null;
  }
  
  private AGResult toResult(String xml)
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xml)));
      
      String info = doc.getFirstChild().getAttributes().getNamedItem("info").getNodeValue();
      String msg = doc.getFirstChild().getAttributes().getNamedItem("msg").getNodeValue();
      
      AGResult result = new AGResult();
      result.setInfo(info);
      result.setMsg(msg);
      return result;
    }
    catch (Exception e)
    {
      log.error("转换AG结果出现异常：" + xml, e);
    }
    return null;
  }
  
  private List<AGBetRecord> toRecords(String xml)
  {
    List<AGBetRecord> records = new ArrayList();
    try
    {
      String[] splits = xml.split(LINE_SEPARATOR);
      String[] arrayOfString1;
      int j = (arrayOfString1 = splits).length;
      for (int i = 0; i < j; i++)
      {
        String split = arrayOfString1[i];
        if (!StringUtils.isEmpty(split))
        {
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder = factory.newDocumentBuilder();
          Document doc = builder.parse(new InputSource(new StringReader(split)));
          
          Node firstChild = doc.getFirstChild();
          
          NamedNodeMap attributes = firstChild.getAttributes();
          
          String dataType = attributes.getNamedItem("dataType").getNodeValue();
          if (("BR".equals(dataType)) || ("EBR".equals(dataType)))
          {
            String billNo = attributes.getNamedItem("billNo").getNodeValue();
            String playerName = attributes.getNamedItem("playerName").getNodeValue();
            String agentCode = attributes.getNamedItem("agentCode").getNodeValue();
            String gameCode = attributes.getNamedItem("gameCode").getNodeValue();
            String netAmount = attributes.getNamedItem("netAmount").getNodeValue();
            String betTime = attributes.getNamedItem("betTime").getNodeValue();
            String gameType = attributes.getNamedItem("gameType").getNodeValue();
            String betAmount = attributes.getNamedItem("betAmount").getNodeValue();
            String validBetAmount = attributes.getNamedItem("validBetAmount").getNodeValue();
            String flag = attributes.getNamedItem("flag").getNodeValue();
            String playType = attributes.getNamedItem("playType").getNodeValue();
            String currency = attributes.getNamedItem("currency").getNodeValue();
            String tableCode = attributes.getNamedItem("tableCode").getNodeValue();
            String recalcuTime = attributes.getNamedItem("recalcuTime").getNodeValue();
            String platformType = attributes.getNamedItem("platformType").getNodeValue();
            String remark = attributes.getNamedItem("remark").getNodeValue();
            String round = attributes.getNamedItem("round").getNodeValue();
            String result = attributes.getNamedItem("result").getNodeValue();
            String beforeCredit = attributes.getNamedItem("beforeCredit").getNodeValue();
            String deviceType = attributes.getNamedItem("deviceType").getNodeValue();
            if ("1".equals(flag))
            {
              AGBetRecord record = new AGBetRecord();
              record.setDataType(dataType);
              record.setBillNo(billNo);
              record.setPlayerName(playerName);
              record.setAgentCode(agentCode);
              record.setGameCode(gameCode);
              record.setNetAmount(netAmount);
              record.setBetTime(recalcuTime);
              record.setGameType(gameType);
              record.setBetAmount(betAmount);
              record.setValidBetAmount(validBetAmount);
              record.setFlag(flag);
              record.setPlayType(playType);
              record.setCurrency(currency);
              record.setTableCode(tableCode);
              record.setRecalcuTime(recalcuTime);
              record.setPlatformType(platformType);
              record.setRemark(remark);
              record.setRound(round);
              record.setResult(result);
              record.setBeforeCredit(beforeCredit);
              record.setDeviceType(deviceType);
              records.add(record);
            }
          }
          else if ("HSR".equals(dataType))
          {
            String billNo = attributes.getNamedItem("tradeNo").getNodeValue();
            String playerName = attributes.getNamedItem("playerName").getNodeValue();
            
            String type = attributes.getNamedItem("type").getNodeValue();
            String Earn = attributes.getNamedItem("Earn").getNodeValue();
            String creationTime = attributes.getNamedItem("creationTime").getNodeValue();
            String Cost = attributes.getNamedItem("Cost").getNodeValue();
            String Roombet = attributes.getNamedItem("Roombet").getNodeValue();
            String flag = attributes.getNamedItem("flag").getNodeValue();
            String platformType = attributes.getNamedItem("platformType").getNodeValue();
            String previousAmount = attributes.getNamedItem("previousAmount").getNodeValue();
            if ("0".equals(flag))
            {
              AGBetRecord record = new AGBetRecord();
              record.setDataType(dataType);
              record.setBillNo(billNo);
              record.setPlayerName(playerName);
              record.setRound(platformType);
              record.setGameCode("捕鱼");
              record.setNetAmount(Earn);
              record.setBetTime(creationTime);
              record.setGameType(Roombet);
              record.setBetAmount(Cost);
              record.setValidBetAmount(Cost);
              record.setBeforeCredit(previousAmount);
              record.setFlag(flag);
              records.add(record);
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      log.error("转换AG投注时出现异常：" + xml, e);
    }
    return records;
  }
  
  public static String transGameType(String gameType)
  {
    String str = gameType;
    switch (gameType.hashCode())
    {
    case 49: 
      if (str.equals("1")) {}
      break;
    case 50: 
      if (str.equals("2")) {}
      break;
    case 1567: 
      if (str.equals("10")) {}
      break;
    case 1691: 
      if (str.equals("50")) {}
      break;
    case 2120: 
      if (str.equals("BJ")) {}
      break;
    case 2192: 
      if (str.equals("DT")) {}
      break;
    case 2254: 
      if (str.equals("FT")) {}
      break;
    case 2496: 
      if (str.equals("NN")) {}
      break;
    case 48563: 
      if (str.equals("1.0")) {}
      break;
    case 48625: 
      if (str.equals("100")) {}
      break;
    case 49524: 
      if (str.equals("2.0")) {}
      break;
    case 49586: 
      if (str.equals("200")) {}
      break;
    case 50547: 
      if (str.equals("300")) {}
      break;
    case 52469: 
      if (str.equals("500")) {}
      break;
    case 65508: 
      if (str.equals("BAC")) {
        break;
      }
      break;
    case 81336: 
      if (str.equals("ROU")) {}
      break;
    case 82061: 
      if (str.equals("SHB")) {}
      break;
    case 88856: 
      if (str.equals("ZJH")) {}
      break;
    case 1475741: 
      if (str.equals("0.10")) {}
      break;
    case 1505501: 
      if (str.equals("1.00")) {}
      break;
    case 1507361: 
      if (str.equals("10.0")) {}
      break;
    case 1535292: 
      if (str.equals("2.00")) {}
      break;
    case 1626525: 
      if (str.equals("50.0")) {}
      break;
    case 2061505: 
      if (str.equals("CBAC")) {}
      break;
    case 2329624: 
      if (str.equals("LBAC")) {}
      break;
    case 2336762: 
      if (str.equals("LINK")) {}
      break;
    case 2538161: 
      if (str.equals("SBAC")) {}
      break;
    case 2607826: 
      if (str.equals("ULPK")) {}
      break;
    case 46728239: 
      if (str.equals("10.00")) {}
      break;
    case 46730099: 
      if (str.equals("100.0")) {}
      break;
    case 47653620: 
      if (str.equals("200.0")) {}
      break;
    case 48577141: 
      if (str.equals("300.0")) {}
      break;
    case 50422323: 
      if (str.equals("50.00")) {}
      break;
    case 50424183: 
      if (str.equals("500.0")) {}
      break;
    case 1448633117: 
      if (str.equals("100.00")) {}
      break;
    case 1477262268: 
      if (str.equals("200.00")) {}
      break;
    case 1505891419: 
      if (str.equals("300.00")) {}
      break;
    case 1563149721: 
      if (!str.equals("500.00"))
      {
        return gameType;
//        return "百家乐";
        
//        return "包桌百家乐";
//
//        return "连环百家乐";
//
//        return "龙虎";
//
//        return "骰宝";
//
//        return "轮盘";
//
//        return "番摊";
//
//        return "竞咪百家乐";
//
//        return "终极德州扑克";
//
//        return "保險百家樂";
//
//        return "牛牛";
//
//        return "21點";
//
//        return "炸金花";
//
//        return "0.1倍场";
//
//        return "1倍场";
//
//        return "2倍场";
//
//        return "10倍场";
//
//        return "50倍场";
//
//        return "100倍场";
//
//        return "200倍场";
//
//        return "300倍场";
      }
      else
      {
        return "500倍场";
      }
//      break;
    }
    return gameType;
  }
  
  public static String transRound(String round)
  {
    String str = round;
    switch (round.hashCode())
    {
    case 64747: 
      if (str.equals("AGQ")) {}
      break;
    case 68001: 
      if (str.equals("DSP")) {
        break;
      }
      break;
    case 75243: 
      if (str.equals("LED")) {}
      break;
    case 84989: 
      if (str.equals("VIP")) {}
      break;
    case 2006950: 
      if (str.equals("AGHH")) {}
      break;
    case 72624492: 
      if (str.equals("LOTTO")) {}
      break;
    case 2142204800: 
      if (!str.equals("HUNTER"))
      {
        return round;
//        return "国际厅";
        
//        return "旗舰厅";
//
//        return "包桌厅";
//
//        return "竞咪厅";
//
//        return "彩票";
//
//        return "豪华厅";
      }
      else
      {
        return "捕鱼厅";
      }
//      break;
    }
    return round;
  }
  
  public static String transDeviceType(String deviceType)
  {
    String str = deviceType;
    switch (deviceType.hashCode())
    {
    case 48: 
      if (str.equals("0")) {
        break;
      }
      break;
    case 49: 
      if (!str.equals("1"))
      {
//        break label64;
        return "电脑";
      }
      else
      {
        return "手机";
      }
//      break;
    }
    label64:
    return "未知";
  }
  
  public static void main(String[] args)
  {
    try
    {
      String xml = "<row dataType=\"BR\"  billNo=\"161231136782592\" playerName=\"qqq123_5667\" agentCode=\"A8P001001001001\" gameCode=\"GB00216C310OB\" netAmount=\"-20\" betTime=\"2016-12-31 13:37:00\" gameType=\"BAC\" betAmount=\"20\" validBetAmount=\"20\" flag=\"1\" playType=\"1\" currency=\"CNY\" tableCode=\"B20R\" loginIP=\"203.177.178.242\" recalcuTime=\"2016-12-31 13:37:18\" platformType=\"AGIN\" remark=\"\" round=\"AGQ\" result=\"\" beforeCredit=\"20\" deviceType=\"0\" />";
      xml = xml + LINE_SEPARATOR + xml;
      System.out.println(xml);
      
      String[] split = xml.split(LINE_SEPARATOR);
      String[] arrayOfString1;
      int j = (arrayOfString1 = split).length;
      for (int i = 0; i < j; i++)
      {
        String xmlSingle = arrayOfString1[i];
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSingle)));
        
        Node firstChild = doc.getFirstChild();
        NamedNodeMap attributes = firstChild.getAttributes();
        String dataType = attributes.getNamedItem("dataType").getNodeValue();
        System.out.println(dataType);
      }
    }
    catch (ParserConfigurationException e)
    {
      e.printStackTrace();
    }
    catch (SAXException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
