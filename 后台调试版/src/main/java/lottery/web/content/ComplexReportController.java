package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserGameReportService;
import lottery.domains.content.biz.UserLotteryDetailsReportService;
import lottery.domains.content.biz.UserLotteryReportService;
import lottery.domains.content.biz.UserMainReportService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.bill.HistoryUserGameReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.HistoryUserLotteryReportVO;
import lottery.domains.content.vo.bill.UserBetsReportVO;
import lottery.domains.content.vo.bill.UserGameReportVO;
import lottery.domains.content.vo.bill.UserLotteryDetailsReportVO;
import lottery.domains.content.vo.bill.UserLotteryReportVO;
import lottery.domains.content.vo.bill.UserMainReportVO;
import lottery.domains.content.vo.bill.UserProfitRankingVO;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ComplexReportController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserMainReportService uMainReportService;
  @Autowired
  private UserLotteryReportService uLotteryReportService;
  @Autowired
  private UserLotteryDetailsReportService uLotteryDetailsReportService;
  @Autowired
  private UserGameReportService uGameReportService;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  
  @RequestMapping(value={"/main-report/complex"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void MAIN_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/main-report/complex";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String sTime = HttpUtil.getStringParameterTrim(request, "sTime");
        String eTime = HttpUtil.getStringParameterTrim(request, "eTime");
        if (StringUtils.isNotEmpty(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          if (targetUser != null)
          {
            List<UserMainReportVO> result = this.uMainReportService.report(targetUser.getId(), sTime, eTime);
            json.accumulate("list", result);
          }
          else
          {
            json.accumulate("list", new ArrayList());
          }
        }
        else
        {
          List<UserMainReportVO> result = this.uMainReportService.report(sTime, eTime);
          json.accumulate("list", result);
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
  
  @RequestMapping(value={"/lottery-report/complex"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-report/complex";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Integer type = HttpUtil.getIntParameter(request, "type");
        if (StringUtil.isNotNull(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          if (targetUser != null)
          {
            List<UserLotteryReportVO> result = this.uLotteryReportService.report(targetUser.getId(), sTime, eTime);
            json.accumulate("list", result);
          }
          else
          {
            json.accumulate("list", new ArrayList());
          }
        }
        else if (type != null)
        {
          List<UserLotteryReportVO> result = this.uLotteryReportService.reportByType(Integer.valueOf(4), sTime, eTime);
          json.accumulate("list", result);
        }
        else
        {
          List<UserLotteryReportVO> result = this.uLotteryReportService.report(sTime, eTime);
          json.accumulate("list", result);
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
  
  @RequestMapping(value={"/history-lottery-report/complex"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-report/complex";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        if (StringUtil.isNotNull(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          if (targetUser != null)
          {
            List<HistoryUserLotteryReportVO> result = this.uLotteryReportService.historyReport(targetUser.getId(), sTime, eTime);
            json.accumulate("list", result);
          }
          else
          {
            json.accumulate("list", new ArrayList());
          }
        }
        else
        {
          List<HistoryUserLotteryReportVO> result = this.uLotteryReportService.historyReport(sTime, eTime);
          json.accumulate("list", result);
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
  
  @RequestMapping(value={"/lottery-report/complex-details"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_REPORT_COMPLEX_DETAILS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-report/complex-details";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Boolean self = HttpUtil.getBooleanParameter(request, "self");
        if (StringUtil.isNotNull(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          List<UserLotteryDetailsReportVO> result;
          if ((self != null) && (self.booleanValue())) {
            result = this.uLotteryDetailsReportService.reportSelf(targetUser.getId(), lotteryId, sTime, eTime);
          } else {
            result = this.uLotteryDetailsReportService.reportLowersAndSelf(targetUser.getId(), lotteryId, sTime, eTime);
          }
          json.accumulate("list", result);
          json.set(Integer.valueOf(0), "0-3");
        }
        else
        {
          json.set(Integer.valueOf(1), "1-3");
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
  
  @RequestMapping(value={"/history-lottery-report/complex-details"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_LOTTERY_REPORT_COMPLEX_DETAILS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-lottery-report/complex-details";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        Integer lotteryId = HttpUtil.getIntParameter(request, "lotteryId");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Boolean self = HttpUtil.getBooleanParameter(request, "self");
        if (StringUtil.isNotNull(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          List<HistoryUserLotteryDetailsReportVO> result;
          if ((self != null) && (self.booleanValue())) {
            result = this.uLotteryDetailsReportService.historyReportSelf(targetUser.getId(), lotteryId, sTime, eTime);
          } else {
            result = this.uLotteryDetailsReportService.historyReportLowersAndSelf(targetUser.getId(), lotteryId, sTime, eTime);
          }
          json.accumulate("list", result);
          json.set(Integer.valueOf(0), "0-3");
        }
        else
        {
          json.set(Integer.valueOf(1), "1-3");
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
  
  @RequestMapping(value={"/lottery-report/profit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_REPORT_PROFIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-report/profit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        Integer type = HttpUtil.getIntParameter(request, "type");
        Integer lottery = HttpUtil.getIntParameter(request, "lottery");
        Integer ruleId = HttpUtil.getIntParameter(request, "ruleId");
        String sTime = HttpUtil.getStringParameterTrim(request, "sTime");
        String eTime = HttpUtil.getStringParameterTrim(request, "eTime");
        
        List<UserBetsReportVO> result = this.uLotteryDetailsReportService.sumUserBets(type, lottery, ruleId, sTime, eTime);
        json.accumulate("list", result);
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
  
  @RequestMapping(value={"/game-report/complex"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game-report/complex";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        if (StringUtil.isNotNull(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          List<UserGameReportVO> result = this.uGameReportService.report(targetUser.getId(), sTime, eTime);
          json.accumulate("list", result);
        }
        else
        {
          List<UserGameReportVO> result = this.uGameReportService.report(sTime, eTime);
          json.accumulate("list", result);
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
  
  @RequestMapping(value={"/history-game-report/complex"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void HISTORY_GAME_REPORT_COMPLEX(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/history-game-report/complex";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        if (StringUtil.isNotNull(username))
        {
          User targetUser = this.uDao.getByUsername(username);
          List<HistoryUserGameReportVO> result = this.uGameReportService.historyReport(targetUser.getId(), sTime, eTime);
          json.accumulate("list", result);
        }
        else
        {
          List<HistoryUserGameReportVO> result = this.uGameReportService.historyReport(sTime, eTime);
          json.accumulate("list", result);
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
  
  @RequestMapping(value={"/lottery-report/user-profit-ranking"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_REPORT_USER_PROFIT_RANKING(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-report/user-profit-ranking";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Integer userId = HttpUtil.getIntParameter(request, "userId");
        
        List<UserProfitRankingVO> result = this.uLotteryReportService.listUserProfitRanking(userId, sTime, eTime, 0, 20);
        json.accumulate("list", result);
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
}
