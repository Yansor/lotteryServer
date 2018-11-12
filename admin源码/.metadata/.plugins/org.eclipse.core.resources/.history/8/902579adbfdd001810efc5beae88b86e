package lottery.domains.content.api.ag;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javautils.http.HttpClientUtil;
import java.util.Iterator;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import java.util.ArrayList;
import javautils.date.Moment;
import javautils.ftp.FTPServer;
import java.util.List;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import com.alibaba.fastjson.JSON;
import admin.web.WebJSONObject;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import javautils.encrypt.DESUtil;
import javautils.http.ToUrlParamUtils;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AGAPI
{
    private static final Logger log;
    private static final String AG_KEY_SEPARATOR = "/\\\\/";
    private static final String LINE_SEPARATOR;
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
    
    static {
        log = LoggerFactory.getLogger((Class)AGAPI.class);
        LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    }
    
    public String forwardGame(final String loginname, final String password, final String website) {
        final Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("cagent", this.aginCagent);
        paramsMap.put("loginname", loginname);
        paramsMap.put("password", password);
        paramsMap.put("dm", website);
        final String sid = String.valueOf(this.aginCagent) + System.currentTimeMillis();
        paramsMap.put("sid", sid);
        paramsMap.put("actype", this.actype);
        paramsMap.put("lang", "1");
        paramsMap.put("method", "lg");
        paramsMap.put("gameType", "1");
        paramsMap.put("oddtype", this.oddtype);
        paramsMap.put("cur", "CNY");
        final String paramsStr = ToUrlParamUtils.toUrlParam(paramsMap, "/\\\\/", false);
        String targetParams = DESUtil.getInstance().encryptStr(paramsStr, this.deskey);
        targetParams = targetParams.replaceAll(AGAPI.LINE_SEPARATOR, "");
        final String key = DigestUtils.md5Hex(String.valueOf(targetParams) + this.md5key);
        final String url = String.valueOf(this.gciurl) + "/forwardGame.do?params=" + targetParams + "&key=" + key;
        return url;
    }
    
    public String checkOrCreateGameAccount(final WebJSONObject webJSON, final String username, final String password) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("cagent", this.aginCagent);
        params.put("loginname", username);
        params.put("method", "lg");
        params.put("actype", this.actype);
        params.put("password", password);
        params.put("oddtype", this.oddtype);
        params.put("cur", "CNY");
        final AGResult result = this.post(params);
        if (result == null) {
            AGAPI.log.error("AG返回内容解析为空");
            webJSON.set(2, "2-8000");
            return null;
        }
        if (!"0".equals(result.getInfo())) {
            AGAPI.log.error("AG返回错误：" + JSON.toJSONString((Object)result));
            final String errorCode = AGCode.transErrorCode(result.getInfo());
            if ("2-8006".equals(errorCode)) {
                webJSON.set(2, errorCode);
            }
            else {
                webJSON.set(2, errorCode);
            }
            return null;
        }
        return username;
    }
    
    public Double getBalance(final WebJSONObject webJSON, final String username, final String password) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("cagent", this.aginCagent);
        params.put("loginname", username);
        params.put("method", "gb");
        params.put("actype", this.actype);
        params.put("password", password);
        params.put("cur", "CNY");
        final AGResult result = this.post(params);
        if (result == null) {
            AGAPI.log.error("AG返回内容解析为空");
            webJSON.set(2, "2-8000");
            return null;
        }
        if (!NumberUtils.isNumber(result.getInfo())) {
            AGAPI.log.error("AG返回错误：" + JSON.toJSONString((Object)result));
            final String errorCode = AGCode.transErrorCode(result.getInfo());
            if ("2-8006".equals(errorCode)) {
                webJSON.set(2, errorCode);
            }
            else {
                webJSON.set(2, errorCode);
            }
            return null;
        }
        return Double.valueOf(result.getInfo());
    }
    
    public String transferIn(final WebJSONObject webJSON, final String username, final String password, final int amount) {
        return this.transfer(webJSON, username, password, amount, true);
    }
    
    public String transferOut(final WebJSONObject webJSON, final String username, final String password, final int amount) {
        return this.transfer(webJSON, username, password, amount, false);
    }
    
    private String transfer(final WebJSONObject webJSON, final String username, final String password, final int amount, final boolean in) {
        final String billno = this.prepareTransferCredit(webJSON, username, password, amount, in);
        if (StringUtils.isEmpty(billno)) {
            return null;
        }
        try {
            final boolean confirm = this.transferCreditConfirm(webJSON, username, password, amount, billno, in);
            if (!confirm) {
                return null;
            }
            final boolean result = this.queryOrderStatus(webJSON, billno);
            return result ? billno : null;
        }
        catch (Exception e) {
            AGAPI.log.error("AG转账发生异常：" + billno, (Throwable)e);
            final boolean result = this.queryOrderStatus(webJSON, billno);
            return result ? billno : null;
        }
    }
    
    private String prepareTransferCredit(final WebJSONObject webJSON, final String username, final String password, final int amount, final boolean in) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("cagent", this.aginCagent);
        params.put("loginname", username);
        params.put("method", "tc");
        final String billno = String.valueOf(this.aginCagent) + System.nanoTime() + RandomUtils.nextInt(99);
        params.put("billno", billno);
        params.put("type", in ? "IN" : "OUT");
        params.put("credit", new StringBuilder(String.valueOf(amount)).toString());
        params.put("actype", this.actype);
        params.put("password", password);
        params.put("cur", "CNY");
        final AGResult result = this.post(params);
        if (result == null) {
            AGAPI.log.error("AG返回内容解析为空");
            webJSON.set(2, "2-8000");
            return null;
        }
        if (!"0".equals(result.getInfo())) {
            AGAPI.log.error("AG返回错误：" + JSON.toJSONString((Object)result));
            final String errorCode = AGCode.transErrorCode(result.getInfo());
            if ("2-8006".equals(errorCode)) {
                webJSON.set(2, errorCode);
            }
            else {
                webJSON.set(2, errorCode);
            }
            return null;
        }
        return billno;
    }
    
    private boolean transferCreditConfirm(final WebJSONObject webJSON, final String username, final String password, final int amount, final String billno, final boolean in) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("cagent", this.aginCagent);
        params.put("loginname", username);
        params.put("method", "tcc");
        params.put("billno", billno);
        params.put("type", in ? "IN" : "OUT");
        params.put("credit", new StringBuilder(String.valueOf(amount)).toString());
        params.put("actype", this.actype);
        params.put("flag", "1");
        params.put("password", password);
        params.put("cur", "CNY");
        final AGResult result = this.post(params);
        if (result == null) {
            AGAPI.log.error("AG返回内容解析为空");
            webJSON.set(2, "2-8000");
            return false;
        }
        if ("0".equals(result.getInfo())) {
            return true;
        }
        if ("1".equals(result.getInfo())) {
            AGAPI.log.error("AG转账确认失败：" + JSON.toJSONString((Object)result));
            webJSON.set(2, "2-8008");
            return false;
        }
        if ("2".equals(result.getInfo())) {
            webJSON.set(2, "2-8007");
            return false;
        }
        if ("network_error".equals(result.getInfo())) {
            final boolean status = this.queryOrderStatus(webJSON, billno);
            return status;
        }
        AGAPI.log.error("AG返回错误：" + JSON.toJSONString((Object)result));
        final String errorCode = AGCode.transErrorCode(result.getInfo());
        if ("2-8006".equals(errorCode)) {
            webJSON.set(2, errorCode);
        }
        else {
            webJSON.set(2, errorCode);
        }
        return false;
    }
    
    private boolean queryOrderStatus(final WebJSONObject webJSON, final String billno) {
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("cagent", this.aginCagent);
        params.put("billno", billno);
        params.put("method", "qos");
        params.put("actype", this.actype);
        params.put("cur", "CNY");
        final AGResult result = this.post(params);
        if (result == null) {
            AGAPI.log.error("AG返回内容解析为空");
            webJSON.set(2, "2-8000");
            return false;
        }
        if ("0".equals(result.getInfo())) {
            return true;
        }
        if ("1".equals(result.getInfo())) {
            webJSON.set(2, "2-8008");
            return false;
        }
        if ("2".equals(result.getInfo())) {
            webJSON.set(2, "2-8007");
            return false;
        }
        AGAPI.log.error("AG返回错误：" + JSON.toJSONString((Object)result));
        final String errorCode = AGCode.transErrorCode(result.getInfo());
        if ("2-8006".equals(errorCode)) {
            webJSON.set(2, errorCode);
        }
        else {
            webJSON.set(2, errorCode);
        }
        return false;
    }
    
    public List<AGBetRecord> getRecords(final String startTime, final String endTime) throws Exception {
        final FTPServer ftpUtil = new FTPServer();
        try {
            ftpUtil.connectServer(this.ftpUrl, 21, this.ftpUsername, this.ftpPassword, null);
            final Moment start = new Moment().fromTime(startTime);
            final Moment end = new Moment().fromTime(endTime);
            final String _startDate = start.format("yyyyMMdd");
            final String _endDate = end.format("yyyyMMdd");
            final String _startTime = start.format("yyyyMMddHHmm");
            final String _endTime = end.format("yyyyMMddHHmm");
            final List<String> readFiles = new ArrayList<String>();
            List<String> startFiles = ftpUtil.getFileList("/AGIN/" + _startDate);
            startFiles = this.filterFiles(startFiles, _startDate, _startTime, _endTime, "AGIN");
            if (CollectionUtils.isNotEmpty((Collection)startFiles)) {
                readFiles.addAll(startFiles);
            }
            List<String> hunterStartFiles = ftpUtil.getFileList("/HUNTER/" + _startDate);
            hunterStartFiles = this.filterFiles(hunterStartFiles, _startDate, _startTime, _endTime, "HUNTER");
            if (CollectionUtils.isNotEmpty((Collection)hunterStartFiles)) {
                readFiles.addAll(hunterStartFiles);
            }
            List<String> xinStartFiles = ftpUtil.getFileList("/XIN/" + _startDate);
            xinStartFiles = this.filterFiles(xinStartFiles, _startDate, _startTime, _endTime, "XIN");
            if (CollectionUtils.isNotEmpty((Collection)xinStartFiles)) {
                readFiles.addAll(xinStartFiles);
            }
            List<String> yoplayStartFiles = ftpUtil.getFileList("/YOPLAY/" + _startDate);
            yoplayStartFiles = this.filterFiles(yoplayStartFiles, _startDate, _startTime, _endTime, "YOPLAY");
            if (CollectionUtils.isNotEmpty((Collection)yoplayStartFiles)) {
                readFiles.addAll(yoplayStartFiles);
            }
            if (!_startDate.equals(_endDate)) {
                List<String> endFiles = ftpUtil.getFileList("/AGIN/" + _endDate);
                endFiles = this.filterFiles(endFiles, _endDate, _startTime, _endTime, "AGIN");
                if (CollectionUtils.isNotEmpty((Collection)endFiles)) {
                    readFiles.addAll(endFiles);
                }
                List<String> hunterEndFiles = ftpUtil.getFileList("/HUNTER/" + _endDate);
                hunterEndFiles = this.filterFiles(hunterEndFiles, _endDate, _startTime, _endTime, "HUNTER");
                if (CollectionUtils.isNotEmpty((Collection)hunterEndFiles)) {
                    readFiles.addAll(hunterEndFiles);
                }
                List<String> xinEndFiles = ftpUtil.getFileList("/XIN/" + _endDate);
                xinEndFiles = this.filterFiles(xinEndFiles, _endDate, _startTime, _endTime, "XIN");
                if (CollectionUtils.isNotEmpty((Collection)xinEndFiles)) {
                    readFiles.addAll(xinEndFiles);
                }
                List<String> yoplayFiles = ftpUtil.getFileList("/YOPLAY/" + _endDate);
                yoplayFiles = this.filterFiles(yoplayFiles, _endDate, _startTime, _endTime, "YOPLAY");
                if (CollectionUtils.isNotEmpty((Collection)yoplayFiles)) {
                    readFiles.addAll(yoplayFiles);
                }
            }
            if (CollectionUtils.isEmpty((Collection)readFiles)) {
                return null;
            }
            final List<AGBetRecord> records = new ArrayList<AGBetRecord>();
            for (final String readFile : readFiles) {
                final String xml = ftpUtil.readFile(readFile);
                if (StringUtils.isEmpty(xml)) {
                    continue;
                }
                final List<AGBetRecord> betRecords = this.toRecords(xml);
                if (!CollectionUtils.isNotEmpty((Collection)betRecords)) {
                    continue;
                }
                records.addAll(betRecords);
            }
            return records;
        }
        catch (Exception e) {
            AGAPI.log.error("获取AG投注记录时出错", (Throwable)e);
            throw e;
        }
        finally {
            try {
                ftpUtil.closeServer();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    private List<String> filterFiles(final List<String> files, final String date, final String startTime, final String endTime, final String folder) {
        if (CollectionUtils.isEmpty((Collection)files)) {
            return null;
        }
        final List<String> filterFiles = new ArrayList<String>();
        for (final String file : files) {
            final String fileTime = file.split("\\.")[0];
            if (fileTime.compareTo(startTime) >= 0 && fileTime.compareTo(endTime) <= 0) {
                filterFiles.add("/" + folder + "/" + date + "/" + file);
            }
        }
        return filterFiles;
    }
    
    private AGResult post(final HashMap<String, String> params) {
        try {
            final Map<String, String> headers = new HashMap<String, String>();
            headers.put("User-Agent", "WEB_LIB_GI_" + this.aginCagent);
            headers.put("Content-Type", "text/xml; utf-8=;charset=UTF-8");
            final String paramsStr = ToUrlParamUtils.toUrlParam(params, "/\\\\/", false);
            String targetParams = DESUtil.getInstance().encryptStr(paramsStr, this.deskey);
            targetParams = targetParams.replaceAll(AGAPI.LINE_SEPARATOR, "");
            final String key = DigestUtils.md5Hex(String.valueOf(targetParams) + this.md5key);
            final String url = String.valueOf(this.giurl) + "/doBusiness.do?params=" + targetParams + "&key=" + key;
            AGAPI.log.debug("AG操作参数URL：{}，操作参数：{}", (Object)url, (Object)JSON.toJSONString((Object)params));
            final String xml = HttpClientUtil.post(url, null, headers, 100000);
            final AGResult result = this.toResult(xml);
            return result;
        }
        catch (Exception e) {
            AGAPI.log.error("连接AG发生错误，请求参数：" + JSON.toJSONString((Object)params), (Throwable)e);
            return null;
        }
    }
    
    private AGResult toResult(final String xml) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(new InputSource(new StringReader(xml)));
            final String info = doc.getFirstChild().getAttributes().getNamedItem("info").getNodeValue();
            final String msg = doc.getFirstChild().getAttributes().getNamedItem("msg").getNodeValue();
            final AGResult result = new AGResult();
            result.setInfo(info);
            result.setMsg(msg);
            return result;
        }
        catch (Exception e) {
            AGAPI.log.error("转换AG结果出现异常：" + xml, (Throwable)e);
            return null;
        }
    }
    
    private List<AGBetRecord> toRecords(final String xml) {
        final List<AGBetRecord> records = new ArrayList<AGBetRecord>();
        try {
            final String[] splits = xml.split(AGAPI.LINE_SEPARATOR);
            String[] array;
            for (int length = (array = splits).length, i = 0; i < length; ++i) {
                final String split = array[i];
                if (!StringUtils.isEmpty(split)) {
                    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    final DocumentBuilder builder = factory.newDocumentBuilder();
                    final Document doc = builder.parse(new InputSource(new StringReader(split)));
                    final Node firstChild = doc.getFirstChild();
                    final NamedNodeMap attributes = firstChild.getAttributes();
                    final String dataType = attributes.getNamedItem("dataType").getNodeValue();
                    if ("BR".equals(dataType) || "EBR".equals(dataType)) {
                        final String billNo = attributes.getNamedItem("billNo").getNodeValue();
                        final String playerName = attributes.getNamedItem("playerName").getNodeValue();
                        final String agentCode = attributes.getNamedItem("agentCode").getNodeValue();
                        final String gameCode = attributes.getNamedItem("gameCode").getNodeValue();
                        final String netAmount = attributes.getNamedItem("netAmount").getNodeValue();
                        final String betTime = attributes.getNamedItem("betTime").getNodeValue();
                        final String gameType = attributes.getNamedItem("gameType").getNodeValue();
                        final String betAmount = attributes.getNamedItem("betAmount").getNodeValue();
                        final String validBetAmount = attributes.getNamedItem("validBetAmount").getNodeValue();
                        final String flag = attributes.getNamedItem("flag").getNodeValue();
                        final String playType = attributes.getNamedItem("playType").getNodeValue();
                        final String currency = attributes.getNamedItem("currency").getNodeValue();
                        final String tableCode = attributes.getNamedItem("tableCode").getNodeValue();
                        final String recalcuTime = attributes.getNamedItem("recalcuTime").getNodeValue();
                        final String platformType = attributes.getNamedItem("platformType").getNodeValue();
                        final String remark = attributes.getNamedItem("remark").getNodeValue();
                        final String round = attributes.getNamedItem("round").getNodeValue();
                        final String result = attributes.getNamedItem("result").getNodeValue();
                        final String beforeCredit = attributes.getNamedItem("beforeCredit").getNodeValue();
                        final String deviceType = attributes.getNamedItem("deviceType").getNodeValue();
                        if ("1".equals(flag)) {
                            final AGBetRecord record = new AGBetRecord();
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
                    else if ("HSR".equals(dataType)) {
                        final String billNo = attributes.getNamedItem("tradeNo").getNodeValue();
                        final String playerName = attributes.getNamedItem("playerName").getNodeValue();
                        final String type = attributes.getNamedItem("type").getNodeValue();
                        final String Earn = attributes.getNamedItem("Earn").getNodeValue();
                        final String creationTime = attributes.getNamedItem("creationTime").getNodeValue();
                        final String Cost = attributes.getNamedItem("Cost").getNodeValue();
                        final String Roombet = attributes.getNamedItem("Roombet").getNodeValue();
                        final String flag2 = attributes.getNamedItem("flag").getNodeValue();
                        final String platformType2 = attributes.getNamedItem("platformType").getNodeValue();
                        final String previousAmount = attributes.getNamedItem("previousAmount").getNodeValue();
                        if ("0".equals(flag2)) {
                            final AGBetRecord record2 = new AGBetRecord();
                            record2.setDataType(dataType);
                            record2.setBillNo(billNo);
                            record2.setPlayerName(playerName);
                            record2.setRound(platformType2);
                            record2.setGameCode("捕鱼");
                            record2.setNetAmount(Earn);
                            record2.setBetTime(creationTime);
                            record2.setGameType(Roombet);
                            record2.setBetAmount(Cost);
                            record2.setValidBetAmount(Cost);
                            record2.setBeforeCredit(previousAmount);
                            record2.setFlag(flag2);
                            records.add(record2);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            AGAPI.log.error("转换AG投注时出现异常：" + xml, (Throwable)e);
        }
        return records;
    }
    
    public static String transGameType(final String gameType) {
        switch (gameType.hashCode()) {
            case 49: {
                if (!gameType.equals("1")) {
                    return gameType;
                }
                break;
            }
            case 50: {
                if (!gameType.equals("2")) {
                    return gameType;
                }
                return "2倍场";
            }
            case 1567: {
                if (!gameType.equals("10")) {
                    return gameType;
                }
                return "10倍场";
            }
            case 1691: {
                if (!gameType.equals("50")) {
                    return gameType;
                }
                return "50倍场";
            }
            case 2120: {
                if (!gameType.equals("BJ")) {
                    return gameType;
                }
                return "21點";
            }
            case 2192: {
                if (!gameType.equals("DT")) {
                    return gameType;
                }
                return "龙虎";
            }
            case 2254: {
                if (!gameType.equals("FT")) {
                    return gameType;
                }
                return "番摊";
            }
            case 2496: {
                if (!gameType.equals("NN")) {
                    return gameType;
                }
                return "牛牛";
            }
            case 48563: {
                if (!gameType.equals("1.0")) {
                    return gameType;
                }
                break;
            }
            case 48625: {
                if (!gameType.equals("100")) {
                    return gameType;
                }
                return "100倍场";
            }
            case 49524: {
                if (!gameType.equals("2.0")) {
                    return gameType;
                }
                return "2倍场";
            }
            case 49586: {
                if (!gameType.equals("200")) {
                    return gameType;
                }
                return "200倍场";
            }
            case 50547: {
                if (!gameType.equals("300")) {
                    return gameType;
                }
                return "300倍场";
            }
            case 52469: {
                if (!gameType.equals("500")) {
                    return gameType;
                }
                return "500倍场";
            }
            case 65508: {
                if (!gameType.equals("BAC")) {
                    return gameType;
                }
                return "百家乐";
            }
            case 81336: {
                if (!gameType.equals("ROU")) {
                    return gameType;
                }
                return "轮盘";
            }
            case 82061: {
                if (!gameType.equals("SHB")) {
                    return gameType;
                }
                return "骰宝";
            }
            case 88856: {
                if (!gameType.equals("ZJH")) {
                    return gameType;
                }
                return "炸金花";
            }
            case 1475741: {
                if (!gameType.equals("0.10")) {
                    return gameType;
                }
                return "0.1倍场";
            }
            case 1505501: {
                if (!gameType.equals("1.00")) {
                    return gameType;
                }
                break;
            }
            case 1507361: {
                if (!gameType.equals("10.0")) {
                    return gameType;
                }
                return "10倍场";
            }
            case 1535292: {
                if (!gameType.equals("2.00")) {
                    return gameType;
                }
                return "2倍场";
            }
            case 1626525: {
                if (!gameType.equals("50.0")) {
                    return gameType;
                }
                return "50倍场";
            }
            case 2061505: {
                if (!gameType.equals("CBAC")) {
                    return gameType;
                }
                return "包桌百家乐";
            }
            case 2329624: {
                if (!gameType.equals("LBAC")) {
                    return gameType;
                }
                return "竞咪百家乐";
            }
            case 2336762: {
                if (!gameType.equals("LINK")) {
                    return gameType;
                }
                return "连环百家乐";
            }
            case 2538161: {
                if (!gameType.equals("SBAC")) {
                    return gameType;
                }
                return "保險百家樂";
            }
            case 2607826: {
                if (!gameType.equals("ULPK")) {
                    return gameType;
                }
                return "终极德州扑克";
            }
            case 46728239: {
                if (!gameType.equals("10.00")) {
                    return gameType;
                }
                return "10倍场";
            }
            case 46730099: {
                if (!gameType.equals("100.0")) {
                    return gameType;
                }
                return "100倍场";
            }
            case 47653620: {
                if (!gameType.equals("200.0")) {
                    return gameType;
                }
                return "200倍场";
            }
            case 48577141: {
                if (!gameType.equals("300.0")) {
                    return gameType;
                }
                return "300倍场";
            }
            case 50422323: {
                if (!gameType.equals("50.00")) {
                    return gameType;
                }
                return "50倍场";
            }
            case 50424183: {
                if (!gameType.equals("500.0")) {
                    return gameType;
                }
                return "500倍场";
            }
            case 1448633117: {
                if (!gameType.equals("100.00")) {
                    return gameType;
                }
                return "100倍场";
            }
            case 1477262268: {
                if (!gameType.equals("200.00")) {
                    return gameType;
                }
                return "200倍场";
            }
            case 1505891419: {
                if (!gameType.equals("300.00")) {
                    return gameType;
                }
                return "300倍场";
            }
            case 1563149721: {
                if (!gameType.equals("500.00")) {
                    return gameType;
                }
                return "500倍场";
            }
        }
        return "1倍场";
    }
    
    public static String transRound(final String round) {
        switch (round) {
            case "AGQ": {
                return "旗舰厅";
            }
            case "DSP": {
                return "国际厅";
            }
            case "LED": {
                return "竞咪厅";
            }
            case "VIP": {
                return "包桌厅";
            }
            case "AGHH": {
                return "豪华厅";
            }
            case "LOTTO": {
                return "彩票";
            }
            case "HUNTER": {
                return "捕鱼厅";
            }
            default:
                break;
        }
        return round;
    }
    
    public static String transDeviceType(final String deviceType) {
        switch (deviceType) {
            case "0": {
                return "电脑";
            }
            case "1": {
                return "手机";
            }
            default:
                break;
        }
        return "未知";
    }
    
    public static void main(final String[] args) {
        try {
            String xml = "<row dataType=\"BR\"  billNo=\"161231136782592\" playerName=\"qqq123_5667\" agentCode=\"A8P001001001001\" gameCode=\"GB00216C310OB\" netAmount=\"-20\" betTime=\"2016-12-31 13:37:00\" gameType=\"BAC\" betAmount=\"20\" validBetAmount=\"20\" flag=\"1\" playType=\"1\" currency=\"CNY\" tableCode=\"B20R\" loginIP=\"203.177.178.242\" recalcuTime=\"2016-12-31 13:37:18\" platformType=\"AGIN\" remark=\"\" round=\"AGQ\" result=\"\" beforeCredit=\"20\" deviceType=\"0\" />";
            xml = String.valueOf(xml) + AGAPI.LINE_SEPARATOR + xml;
            System.out.println(xml);
            final String[] split = xml.split(AGAPI.LINE_SEPARATOR);
            String[] array;
            for (int length = (array = split).length, i = 0; i < length; ++i) {
                final String xmlSingle = array[i];
                final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder builder = factory.newDocumentBuilder();
                final Document doc = builder.parse(new InputSource(new StringReader(xmlSingle)));
                final Node firstChild = doc.getFirstChild();
                final NamedNodeMap attributes = firstChild.getAttributes();
                final String dataType = attributes.getNamedItem("dataType").getNodeValue();
                System.out.println(dataType);
            }
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }
}
