//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fsy.lottery.domains.capture.jobs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.fsy.javautils.StringUtil;
import com.fsy.javautils.date.Moment;
import com.fsy.javautils.http.HttpClientUtil;
import com.fsy.lottery.domains.capture.sites.opencai.OpenCaiBean;
import com.fsy.lottery.domains.capture.utils.CodeValidate;
import com.fsy.lottery.domains.capture.utils.ExpectValidate;
import com.fsy.lottery.domains.content.biz.LotteryOpenCodeService;
import com.fsy.lottery.domains.content.entity.LotteryOpenCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OpenCaiJobRepair {
    private static final Logger logger = LoggerFactory.getLogger(OpenCaiJobRepair.class);
    private static final String SUB_URL = "/newly.do?token=tc5b7f6b9131346d3k&rows=5&format=json";
//    private static final String[] SITES = new String[]{"http://d.apiplus.net", "http://z.apiplus.net"};

    private static final String [] REPAIR_SITE = new String [] {
            "http://api.b1api.com/api?p=json&t=txffc&limit=1&token=3f67473734bb03a0" ,  //腾讯分分彩
            "http://api.b1api.com/api?p=json&t=qqffc&limit=1&token=3f67473734bb03a0" ,  //qq分分彩
            "http://api.duourl.com/api?p=json&t=jxk3&limit=10&token=f31e3156d7563c34" ,   //江西快3
            "http://api.duourl.com/api?p=json&t=hebk3&limit=1&token=f31e3156d7563c34" ,  //河北快3
            "http://api.duourl.com/api?p=json&t=gxk3&limit=1&token=f31e3156d7563c34"  ,  //广西快三
            "http://api.b1api.com/api?p=json&t=jsk3&limit=1&token=3f67473734bb03a0"  ,  //江苏快三
            "http://api.b1api.com/api?p=json&t=shk3&limit=1&token=3f67473734bb03a0"  ,  // 上海快三
            "http://api.duourl.com/api?p=json&t=hbk3&limit=1&token=f31e3156d7563c34"  ,  // 湖北快三
            "http://api.b1api.com/api?p=json&t=ahk3&limit=1&token=3f67473734bb03a0"  ,  //安徽快三
            "http://api.b1api.com/api?p=json&t=xj115&limit=1&token=3f67473734bb03a0" ,  //新疆11选5
            "http://api.duourl.com/api?p=json&t=ah115&limit=1&token=f31e3156d7563c34" , //安徽11选5
            "http://api.b1api.com/api?p=json&t=sh115&limit=1&token=3f67473734bb03a0" , //上海11选5
            "http://api.b1api.com/api?p=json&t=jx115&limit=1&token=3f67473734bb03a0" , //江西11选5
            "http://api.duourl.com/api?p=json&t=sd115&limit=1&token=f31e3156d7563c34" , //山东11选5
            "http://api.b1api.com/api?p=json&t=gd115&limit=1&token=3f67473734bb03a0" , //广东11选5
//            "http://api.duourl.com/api?p=json&t=bjpk10&limit=1&token=f31e3156d7563c34", //北京PK10
            "http://api.b1api.com/api?p=json&t=bjpk10&limit=1&token=3f67473734bb03a0",
            "http://api.duourl.com/api?p=json&t=hgydwfc&limit=1&token=f31e3156d7563c34", //韩国1.5分彩
            "http://api.duourl.com/api?p=json&t=djydwfc&limit=1&token=f31e3156d7563c34", //东京1.5分彩
            "http://api.b1api.com/api?p=json&t=xjssc&limit=1&token=3f67473734bb03a0" , //新疆时时彩
            "http://api.b1api.com/api?p=json&t=cqssc&limit=1&token=3f67473734bb03a0"  , //重庆时时彩
            "http://api.duourl.com/api?p=json&t=tjssc&limit=1&token=f31e3156d7563c34" , //天津时时彩
            "http://api.b1api.com/api?p=json&t=fc3d&limit=1&token=3f67473734bb03a0",  //福彩3d
            "http://api.b1api.com/api?p=json&t=pl3&limit=1&token=3f67473734bb03a0"    //排列3
    };
    @Autowired
    private LotteryOpenCodeService lotteryOpenCodeService;
    private static boolean isRuning = false;

    public OpenCaiJobRepair() {
    }

    @Scheduled(
            cron = "0/10 * * * * * "
            //0/10 * * * * *  10s
    )
    public void execute() {
        Class var1 = OpenCaiJobRepair.class;
        synchronized(OpenCaiJobRepair.class) {
            if (isRuning) {
                return;
            }

            isRuning = true;
        }

        try {
            this.start();
        } catch (Exception var6) {
            logger.error("抓取OpenCai开奖数据出错", var6);
        } finally {
            isRuning = false;
        }

    }

    public void start() {
        //定时获取token

        for(int i = 0; i < REPAIR_SITE.length; ++i) {
            String site = REPAIR_SITE[i];
            new Thread(()->{
                logger.debug("开始抓取OpenCai[{}]开奖数据", site);
                long start = System.currentTimeMillis();
//            String repairSite = "http://r.apiplus.net/";
//            String repairUrl = repairSite + "/newly.do?token=dea265e2986ba09933afaeb7438a84e1&code=xjssc&rows=5&format=json";
//            String url = site + "/newly.do?token=tc5b7f6b9131346d3k&rows=5&format=json" + "&_=" + System.currentTimeMillis();
//            String result = getHttpResult(url);
                String result = getHttpResult(site);
                boolean succeed = false;
                if (StringUtils.isNotEmpty(result)) {
                    succeed = this.handleData(result, site);
                }

                long spend = System.currentTimeMillis() - start;
                if (succeed) {
                    logger.debug("成功抓取OpenCai[{}]开奖数据，并处理成功，耗时{}", site, spend);

                }else
                logger.warn("完成抓取OpenCai[{}]开奖数据，但处理失败，耗时{}", site, spend);
            }).start();

        }

    }

    private boolean handleData(String data, String site) {
        try {
            if (!StringUtil.isNotNull(data)) {
                return false;
            } else {
                Object object = JSONObject.fromObject(data).get("data");
                JSONArray array = JSONArray.fromObject(object);
                List<OpenCaiBean> list = new ArrayList();
                Iterator iter = array.iterator();

                while(iter.hasNext()) {
                    JSONObject jsonObject = (JSONObject)iter.next();
                    OpenCaiBean bean = (OpenCaiBean)JSONObject.toBean(jsonObject, OpenCaiBean.class);
                    list.add(bean);
                }

                Collections.reverse(list);
                Iterator var11 = list.iterator();

                while(var11.hasNext()) {
                    OpenCaiBean bean = (OpenCaiBean)var11.next();
                    this.handleOpenCaiBean(bean, site);
                }

                return true;
            }
        } catch (Exception var9) {
            logger.error("解析OpenCai数据出错：" + data + "，URL：" + site, var9);
            return false;
        }
    }

    private boolean handleOpenCaiBean(OpenCaiBean bean, String site) {
//        label137: {
//            String expect;
//            label136: {
//                label135: {
//                    label143: {
//                        lotteryOpenCode = null;
//                        String var5;
//                        String openCode;
//                        switch((var5 = bean.getCode()).hashCode()) {
//                            case -1420382844:
//                                if (!var5.equals("ah11x5")) {
//                                    break label137;
//                                }
//                                break label135;
//                            case -1387976286:
//                                if (var5.equals("bjpk10")) {
//                                    expect = bean.getExpect();
//                                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//                                }
//                                break label137;
//                            case -1367811591:
//                                if (var5.equals("cakeno")) {
//                                    openCode = bean.getOpencode().substring(0, 59);
//                                    String cakenoCode = this.convertFFCCode(openCode);
//                                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), bean.getExpect(), cakenoCode, bean.getOpentime(), Integer.valueOf(0));
//                                    lotteryOpenCode.setLottery("jnd3d5fc");
//                                }
//                                break label137;
//                            case -1252302022:
//                                if (!var5.equals("gd11x5")) {
//                                    break label137;
//                                }
//                                break label135;
//                            case -1147944149:
//                                if (!var5.equals("jx11x5")) {
//                                    break label137;
//                                }
//                                break label135;
//                            case -923144308:
//                                if (var5.equals("twbingo")) {
//                                    openCode = bean.getOpencode().split("\\+")[0];
//                                    String twbingoCode = this.convertFFCCode(openCode);
//                                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), bean.getExpect(), twbingoCode, bean.getOpentime(), Integer.valueOf(0));
//                                    lotteryOpenCode.setLottery("tw5fc");
//                                }
//                                break label137;
//                            case -908752210:
//                                if (!var5.equals("sd11x5")) {
//                                    break label137;
//                                }
//                                break label135;
//                            case -905058126:
//                                if (!var5.equals("sh11x5")) {
//                                    break label137;
//                                }
//                                break label135;
//                            case 111031:
//                                if (!var5.equals("pl3")) {
//                                    break label137;
//                                }
//                                break label143;
//                            case 111033:
//                                if (var5.equals("pl5")) {
//                                    expect = bean.getExpect();
//                                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//                                }
//                                break label137;
//                            case 2993039:
//                                if (!var5.equals("ahk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 3135502:
//                                if (!var5.equals("fc3d")) {
//                                    break label137;
//                                }
//                                break label143;
//                            case 3187161:
//                                if (!var5.equals("gxk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 3265002:
//                                if (!var5.equals("jlk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 3271729:
//                                if (!var5.equals("jsk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 3276534:
//                                if (!var5.equals("jxk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 3529277:
//                                if (!var5.equals("shk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 93769135:
//                                if (var5.equals("bjkl8")) {
//                                    openCode = bean.getOpencode().split("\\+")[0];
//                                    String bjkl8Code = this.convertFFCCode(openCode);
//                                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), bean.getExpect(), bjkl8Code, bean.getOpentime(), Integer.valueOf(0));
//                                    lotteryOpenCode.setLottery("bj5fc");
//                                }
//                                break label137;
//                            case 94909141:
//                                if (!var5.equals("cqssc")) {
//                                    break label137;
//                                }
//                                break label136;
//                            case 99152621:
//                                if (!var5.equals("hebk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 99629277:
//                                if (!var5.equals("hubk3")) {
//                                    break label137;
//                                }
//                                break;
//                            case 101582325:
//                                if (!var5.equals("jxssc")) {
//                                    break label137;
//                                }
//                                break label136;
//                            case 109417367:
//                                if (var5.equals("shssl")) {
//                                    expect = bean.getExpect();
//                                    expect = expect.substring(0, 8) + "-" + expect.substring(8);
//                                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//                                }
//                                break label137;
//                            case 110400461:
//                                if (!var5.equals("tjssc")) {
//                                    break label137;
//                                }
//                                break label136;
//                            case 114094545:
//                                if (!var5.equals("xjssc")) {
//                                    break label137;
//                                }
//                                break label136;
//                            default:
//                                break label137;
//                        }
//
//                        if (bean.getCode().equals("hubk3")) {
//                            bean.setCode("hbk3");
//                        }
//
//                        expect = bean.getExpect();
//                        expect = expect.substring(0, 8) + "-" + expect.substring(8);
//                        lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//                        break label137;
//                    }
//
//                    expect = bean.getExpect();
//                    expect = expect.substring(2, 7);
//                    lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//                    break label137;
//                }
//
//                expect = bean.getExpect();
//                expect = expect.substring(0, 8) + "-0" + expect.substring(8);
//                lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//                break label137;
//            }
//
//            expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            lotteryOpenCode = new LotteryOpenCode(bean.getCode(), expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
//        }

        LotteryOpenCode lotteryOpenCode = null;
        if(site.contains("txffc")){
            //腾讯分分彩
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            lotteryOpenCode = new LotteryOpenCode("txffc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("qqffc")){
            //QQ分分彩
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("fgffc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("jxk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("jxk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("hebk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("hebk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));

        }else if(site.contains("gxk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("hebk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("jsk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("jsk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("shk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("shk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("hbk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("hbk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("ahk3")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("ahk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("xj115")){
            //平台没有此奖
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("sc11xx5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("ah115")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("ah11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("sh115")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("sh11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("jx115")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("jx11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("sd115")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("sd11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("gd115")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("gd11x5", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
          }else if(site.contains("bjpk10")){
            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("bjpk10", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("hgydwfc")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("hg1d5fc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("djydwfc")){
            //东京1.5分彩
//            String expect = bean.getExpect();
//            expect = expect.substring(0, 8) + "-" + expect.substring(8);
//            //前端code fgffc
//            lotteryOpenCode = new LotteryOpenCode("hbk3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("xjssc")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("xjssc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("cqssc")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("cqssc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));
        }else if(site.contains("tjssc")){
            String expect = bean.getExpect();
            expect = expect.substring(0, 8) + "-" + expect.substring(8);
            //前端code fgffc
            lotteryOpenCode = new LotteryOpenCode("tjssc", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));

        }else if(site.contains("fc3d")){
            String expect = bean.getExpect();
            //前端code fc3d
            expect = expect.substring(2, 7);
            lotteryOpenCode = new LotteryOpenCode("fc3d", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));

        }
        else if(site.contains("pl3")){
            String expect = bean.getExpect();
            //前端code fc3d
            expect = expect.substring(2, 7) ;
            lotteryOpenCode = new LotteryOpenCode("pl3", expect, bean.getOpencode(), bean.getOpentime(), Integer.valueOf(0));

        }

        if (lotteryOpenCode == null) {
            return false;
        } else if (!CodeValidate.validate(lotteryOpenCode.getLottery(), lotteryOpenCode.getCode())) {
            logger.error("开彩网" + lotteryOpenCode.getLottery() + "抓取号码" + lotteryOpenCode.getCode() + "错误");
            return false;
        } else if (!ExpectValidate.validate(lotteryOpenCode.getLottery(), lotteryOpenCode.getExpect())) {
            logger.error("开彩网" + lotteryOpenCode.getLottery() + "抓取期数" + lotteryOpenCode.getExpect() + "错误");
            return false;
        } else {
            lotteryOpenCode.setRemarks(site);
            lotteryOpenCode.setTime((new Moment()).toSimpleTime());
            if (StringUtils.isNotEmpty(bean.getOpentime())) {
                lotteryOpenCode.setInterfaceTime(bean.getOpentime());
            } else {
                lotteryOpenCode.setInterfaceTime((new Moment()).toSimpleTime());
            }

            boolean add = this.lotteryOpenCodeService.add(lotteryOpenCode, false);
            if (add && "bj5fc".equals(lotteryOpenCode.getLottery())) {
                LotteryOpenCode bjkl8Code = new LotteryOpenCode("bjkl8", lotteryOpenCode.getExpect(), bean.getOpencode().split("\\+")[0], (new Moment()).toSimpleTime(), Integer.valueOf(0), (String)null, site);
                bjkl8Code.setInterfaceTime(lotteryOpenCode.getInterfaceTime());
                this.lotteryOpenCodeService.add(bjkl8Code, false);
            }

            return add;
        }
    }

    public static String getHttpResult(String url) {
//        try {
//            Map<String, String> header = new HashMap();
//            header.put("referer", "http://www.baidu.com/");
//            header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
//            String data = HttpClientUtil.post(url, (Map)null, header, 10000);
//            return data;
//        } catch (Exception var3) {
//            logger.error("请求OpenCai出错,URL：" + url, var3);
//            return null;
//        }
        try {
        Map<String, String> header = new HashMap();
        header.put("referer", "http://www.baidu.com/");
        header.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36");
        String data = HttpClientUtil.get(url, header, 10000);
        return data;
    } catch (Exception var3) {
        logger.error("请求OpenCai出错,URL：" + url, var3);
        return null;
    }
    }

    private String convertFFCCode(String openCode) {
        String[] codes = openCode.split(",");
        String code1 = this.mergeFFCCode(codes[0], codes[1], codes[2], codes[3]);
        String code2 = this.mergeFFCCode(codes[4], codes[5], codes[6], codes[7]);
        String code3 = this.mergeFFCCode(codes[8], codes[9], codes[10], codes[11]);
        String code4 = this.mergeFFCCode(codes[12], codes[13], codes[14], codes[15]);
        String code5 = this.mergeFFCCode(codes[16], codes[17], codes[18], codes[19]);
        return code1 + "," + code2 + "," + code3 + "," + code4 + "," + code5;
    }

    private String mergeFFCCode(String code1, String code2, String code3, String code4) {
        int codeInt1 = Integer.valueOf(code1).intValue();
        int codeInt2 = Integer.valueOf(code2).intValue();
        int codeInt3 = Integer.valueOf(code3).intValue();
        int codeInt4 = Integer.valueOf(code4).intValue();
        int codeSum = codeInt1 + codeInt2 + codeInt3 + codeInt4;
        String codeSumStr = String.valueOf(codeSum);
        String finalCode = codeSumStr.substring(codeSumStr.length() - 1);
        return finalCode;
    }

    public static void main(String[] args) {
        String a = "2018282";
        System.out.println(a.substring(2, 7));

    }
}
