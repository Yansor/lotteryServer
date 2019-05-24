package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.List;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.dao.LotteryOpenCodeDao;
import lottery.domains.content.dao.UserBetsDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.entity.UserBets;
import lottery.domains.content.vo.user.HistoryUserBetsVO;
import lottery.domains.content.vo.user.UserBetsVO;
import lottery.web.content.utils.UserCodePointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserBetsController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private UserBetsService uBetsService;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  @Autowired
  private LotteryOpenCodeDao lotteryOpenCodeDao;
  @Autowired
  private UserBetsDao userBetsDao;
  @Autowired
  private LotteryDao lotteryDao;
  
  @RequestMapping(value={"/lottery-user-bets/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
        String username = HttpUtil.getStringParameterTrim(request, "username");
        Integer type = HttpUtil.getIntParameter(request, "type");
        Integer utype = HttpUtil.getIntParameter(request, "utype");
        Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
        String expect = HttpUtil.getStringParameterTrim(request, "expect");
        Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
        String minTime = HttpUtil.getStringParameterTrim(request, "minTime");
        if (StringUtil.isNotNull(minTime)) {
          minTime = minTime + " 00:00:00";
        }
        String maxTime = HttpUtil.getStringParameterTrim(request, "maxTime");
        if (StringUtil.isNotNull(maxTime)) {
          maxTime = maxTime + " 00:00:00";
        }
        String minPrizeTime = HttpUtil.getStringParameterTrim(request, "minPrizeTime");
        if (StringUtil.isNotNull(minPrizeTime)) {
          minPrizeTime = minPrizeTime + " 00:00:00";
        }
        String maxPrizeTime = HttpUtil.getStringParameterTrim(request, "maxPrizeTime");
        if (StringUtil.isNotNull(maxPrizeTime)) {
          maxPrizeTime = maxPrizeTime + " 00:00:00";
        }
        Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
        Integer minMultiple = HttpUtil.getIntParameter(request, "minMultiple");
        Integer maxMultiple = HttpUtil.getIntParameter(request, "maxMultiple");
        Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
        Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer locked = HttpUtil.getIntParameter(request, "locked");
        String ip = HttpUtil.getStringParameterTrim(request, "ip");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uBetsService.search(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, 
          maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, 
          minPrizeMoney, maxPrizeMoney, status, locked, ip, start, limit);
        if (pList != null)
        {
          double[] totalMoney = this.uBetsService.getTotalMoney(keyword, username, utype, type, lotteryId, expect, 
            ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, 
            maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked, ip);
          
          double canceltotalMoney = 0.0D;
          double[] canceltotalMoneys = this.uBetsService.getTotalMoney(keyword, username, utype, type, lotteryId, 
            expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, 
            minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, Integer.valueOf(-1), locked, ip);
          canceltotalMoney = canceltotalMoneys[0];
          
          json.accumulate("totalMoney", Double.valueOf(totalMoney[0]));
          json.accumulate("canceltotalMoney", Double.valueOf(canceltotalMoney));
          json.accumulate("totalPrizeMoney", Double.valueOf(totalMoney[1]));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("canceltotalMoney", Integer.valueOf(0));
          json.accumulate("totalMoney", Integer.valueOf(0));
          json.accumulate("totalPrizeMoney", Integer.valueOf(0));
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        List<String> userLevels = this.uCodePointUtil.getUserLevels(username);
        json.accumulate("userLevels", userLevels);
        
        json.set(Integer.valueOf(0), "0-3");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/history-lottery-user-bets/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_BETS_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-bets/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String keyword = HttpUtil.getStringParameterTrim(request, "keyword");
        String username = HttpUtil.getStringParameterTrim(request, "username");
        Integer type = HttpUtil.getIntParameter(request, "type");
        Integer utype = HttpUtil.getIntParameter(request, "utype");
        Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
        String expect = HttpUtil.getStringParameterTrim(request, "expect");
        Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
        String minTime = HttpUtil.getStringParameterTrim(request, "minTime");
        if (StringUtil.isNotNull(minTime)) {
          minTime = minTime + " 00:00:00";
        }
        String maxTime = HttpUtil.getStringParameterTrim(request, "maxTime");
        if (StringUtil.isNotNull(maxTime)) {
          maxTime = maxTime + " 00:00:00";
        }
        String minPrizeTime = HttpUtil.getStringParameterTrim(request, "minPrizeTime");
        if (StringUtil.isNotNull(minPrizeTime)) {
          minPrizeTime = minPrizeTime + " 00:00:00";
        }
        String maxPrizeTime = HttpUtil.getStringParameterTrim(request, "maxPrizeTime");
        if (StringUtil.isNotNull(maxPrizeTime)) {
          maxPrizeTime = maxPrizeTime + " 00:00:00";
        }
        Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
        Integer minMultiple = HttpUtil.getIntParameter(request, "minMultiple");
        Integer maxMultiple = HttpUtil.getIntParameter(request, "maxMultiple");
        Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
        Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
        Integer status = HttpUtil.getIntParameter(request, "status");
        Integer locked = HttpUtil.getIntParameter(request, "locked");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uBetsService.searchHistory(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, 
          maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, maxMultiple, 
          minPrizeMoney, maxPrizeMoney, status, locked, start, limit);
        if (pList != null)
        {
          double[] totalMoney = this.uBetsService.getHistoryTotalMoney(keyword, username, utype, type, lotteryId, expect, 
            ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, minMultiple, 
            maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked);
          double canceltotalMoney = 0.0D;
          double[] canceltotalMoneys = this.uBetsService.getHistoryTotalMoney(keyword, username, utype, type, lotteryId, 
            expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, minMoney, maxMoney, 
            minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, locked);
          canceltotalMoney = canceltotalMoneys[0];
          json.accumulate("canceltotalMoney", Double.valueOf(canceltotalMoney));
          json.accumulate("totalMoney", Double.valueOf(totalMoney[0]));
          json.accumulate("totalPrizeMoney", Double.valueOf(totalMoney[1]));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
          json.accumulate("canceltotalMoney", Integer.valueOf(0));
          json.accumulate("totalMoney", Integer.valueOf(0));
          json.accumulate("totalPrizeMoney", Integer.valueOf(0));
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        json.set(Integer.valueOf(0), "0-3");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-bets/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserBetsVO result = this.uBetsService.getById(id);
        String expect = result.getBean().getExpect();
        int lotteryId = result.getBean().getLotteryId();
        LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeDao.get(this.lotteryDao.getById(lotteryId).getShortName(), expect);
        if (lotteryOpenCode != null)
        {
          result.getBean().setOpenCode(lotteryOpenCode.getCode());
          result.getBean().setPrizeTime(lotteryOpenCode.getOpenTime());
        }
        json.accumulate("data", result);
        json.set(Integer.valueOf(0), "0-3");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/history-lottery-user-bets/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_USER_BETS_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-user-bets/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        HistoryUserBetsVO result = this.uBetsService.getHistoryById(id);
        json.accumulate("data", result);
        json.set(Integer.valueOf(0), "0-3");
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-bets/batch"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_BATCH(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/batch";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String step = HttpUtil.getStringParameterTrim(request, "step");
        int lotteryId = HttpUtil.getIntParameter(request, "lotteryId").intValue();
        Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
        String expect = HttpUtil.getStringParameterTrim(request, "expect");
        String match = HttpUtil.getStringParameterTrim(request, "match");
        if ("query".equals(step))
        {
          int count = this.uBetsService.notOpened(lotteryId, ruleId, expect, match).size();
          json.set(Integer.valueOf(0), "0-3");
          json.accumulate("data", Integer.valueOf(count));
        }
        if ("execute".equals(step))
        {
          boolean result = this.uBetsService.cancel(lotteryId, ruleId, expect, match);
          if (result)
          {
            this.adminUserLogJob.logBatchCancelOrder(uEntity, request, lotteryId, ruleId, expect, match);
            json.set(Integer.valueOf(0), "0-5");
          }
          else
          {
            json.set(Integer.valueOf(1), "1-5");
          }
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-bets/cancel"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_CANCEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/cancel";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        boolean result = this.uBetsService.cancel(id);
        if (result)
        {
          this.adminUserLogJob.logCancelOrder(uEntity, request, id);
          json.set(Integer.valueOf(0), "0-5");
        }
        else
        {
          json.set(Integer.valueOf(1), "1-5");
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-bets/change"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_CHANGE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/change";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int locked = HttpUtil.getIntParameter(request, "locked").intValue();
        String act = HttpUtil.getStringParameterTrim(request, "act");
        String codes = HttpUtil.getStringParameterTrim(request, "codes");
        if (act.equals("locked"))
        {
          this.userBetsDao.updateLocked(id, locked);
          json.set(Integer.valueOf(0), "0-3");
          if (locked == 1) {
            json.setMessage("锁定成功");
          }
          if (locked == 0) {
            json.setMessage("解锁成功");
          }
        }
        if (act.equals("change"))
        {
          if (locked == 1)
          {
            UserBets result = this.uBetsService.getBetsById(id);
            int lotteryId = result.getLotteryId();
            String expect = result.getExpect();
            LotteryOpenCode lotteryOpenCode = this.lotteryOpenCodeDao.get(this.lotteryDao.getById(lotteryId).getShortName(), expect);
            if (lotteryOpenCode != null)
            {
              this.userBetsDao.updateStatus(id, 1, codes, lotteryOpenCode.getCode(), 0.0D, lotteryOpenCode.getOpenTime());
              json.set(Integer.valueOf(0), "0-3");
              json.setMessage("改为不中成功");
            }
            else
            {
              json.set(Integer.valueOf(2), "2-4");
              json.setMessage("暂未开奖");
            }
          }
          if (locked == 0)
          {
            json.set(Integer.valueOf(2), "2-4");
            json.setMessage("暂未锁定");
          }
        }
      }
      else
      {
        json.set(Integer.valueOf(2), "2-4");
      }
    }
    else {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
