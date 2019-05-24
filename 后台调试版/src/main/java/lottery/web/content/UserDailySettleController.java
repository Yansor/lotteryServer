package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserCriticalLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javautils.math.MathUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserDailySettleService;
import lottery.domains.content.biz.UserService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.entity.UserDailySettle;
import lottery.domains.content.vo.config.DailySettleConfig;
import lottery.domains.content.vo.user.UserDailySettleVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.web.content.utils.UserCodePointUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserDailySettleController
  extends AbstractActionController
{
  @Autowired
  private UserDao userDao;
  @Autowired
  private UserDailySettleService settleService;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserCriticalLogJob adminUserCriticalLogJob;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private UserService uService;
  
  @RequestMapping(value={"/lottery-user-daily-settle/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DAILY_SETTLE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-daily-settle/list";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username");
        String sTime = request.getParameter("sTime");
        String eTime = request.getParameter("eTime");
        Double minScale = HttpUtil.getDoubleParameter(request, "minScale");
        Double maxScale = HttpUtil.getDoubleParameter(request, "maxScale");
        Integer minValidUser = HttpUtil.getIntParameter(request, "minValidUser");
        Integer maxValidUser = HttpUtil.getIntParameter(request, "maxValidUser");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        Integer status = HttpUtil.getIntParameter(request, "status");
        List<Integer> userIds = new ArrayList();
        boolean legalUser = true;
        if (StringUtils.isNotEmpty(username))
        {
          User user = this.userDao.getByUsername(username);
          if (user == null)
          {
            legalUser = false;
          }
          else
          {
            userIds.add(Integer.valueOf(user.getId()));
            List<User> userDirectLowers = this.userDao.getUserDirectLower(user.getId());
            for (User userDirectLower : userDirectLowers) {
              userIds.add(Integer.valueOf(userDirectLower.getId()));
            }
          }
        }
        if (!legalUser)
        {
          json.accumulate("totalCount", Integer.valueOf(0));
          json.accumulate("data", "[]");
        }
        else
        {
          if (minScale != null) {
            minScale = Double.valueOf(MathUtil.divide(minScale.doubleValue(), 100.0D, 4));
          }
          if (maxScale != null) {
            maxScale = Double.valueOf(MathUtil.divide(maxScale.doubleValue(), 100.0D, 4));
          }
          PageList pList = this.settleService.search(userIds, sTime, eTime, minScale, maxScale, minValidUser, 
            maxValidUser, status, start, limit);
          if (pList != null)
          {
            json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
            json.accumulate("data", pList.getList());
          }
          else
          {
            json.accumulate("totalCount", Integer.valueOf(0));
            json.accumulate("data", "[]");
          }
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-daily-settle/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DAILY_SETTLE_DEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-daily-settle/del";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = request.getParameter("username").trim();
        
        User uBean = this.userDao.getByUsername(username);
        if (uBean == null)
        {
          json.set(Integer.valueOf(2), "2-32");
        }
        else if (this.settleService.checkCanDel(json, uBean))
        {
          boolean result = this.settleService.deleteByTeam(username);
          if (result)
          {
            this.adminUserCriticalLogJob.logDelDailySettle(uEntity, request, username, actionKey);
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
  
  @RequestMapping(value={"/lottery-user-daily-settle/edit-get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DAILY_SETTLE_EDIT_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-daily-settle/edit-get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        UserDailySettle dailySettle = this.settleService.getById(id);
        if (dailySettle == null)
        {
          json.set(Integer.valueOf(1), "1-7");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        User user = this.userDao.getById(dailySettle.getUserId());
        if (user == null)
        {
          json.set(Integer.valueOf(1), "2-32");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (!this.settleService.checkCanEdit(json, user))
        {
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        UserDailySettle upDailySettle = this.settleService.getByUserId(user.getUpid());
        if ((!this.uCodePointUtil.isLevel3Proxy(user)) && (upDailySettle == null))
        {
          json.set(Integer.valueOf(1), "1-8");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        double[] minMaxScale = this.settleService.getMinMaxScale(user);
        double minScale = minMaxScale[0];
        double maxScale = minMaxScale[1];
        
        double[] minMaxSales = this.settleService.getMinMaxSales(user);
        double minSales = minMaxSales[0];
        double maxSales = minMaxSales[1];
        
        double[] minMaxLoss = this.settleService.getMinMaxLoss(user);
        double minLoss = minMaxLoss[0];
        double maxLoss = minMaxLoss[1];
        
        int[] minMaxUser = this.settleService.getMinMaxUsers(user);
        int minUser = minMaxUser[0];
        int maxUser = minMaxUser[1];
        
        List<String> userLevels = this.uService.getUserLevels(user);
        Map<String, Object> data = new HashMap();
        data.put("bean", dailySettle == null ? null : new UserDailySettleVO(dailySettle, this.dataFactory));
        data.put("upBean", upDailySettle == null ? null : new UserDailySettleVO(upDailySettle, this.dataFactory));
        data.put("minScale", Double.valueOf(minScale));
        data.put("maxScale", Double.valueOf(maxScale));
        data.put("minSales", Double.valueOf(minSales));
        data.put("maxSales", Double.valueOf(maxSales));
        data.put("minLoss", Double.valueOf(minLoss));
        data.put("maxLoss", Double.valueOf(maxLoss));
        data.put("minUser", Integer.valueOf(minUser));
        data.put("maxUser", Integer.valueOf(maxUser));
        data.put("scaleLevel", dailySettle.getScaleLevel());
        data.put("lossLevel", dailySettle.getLossLevel());
        data.put("salesLevel", dailySettle.getSalesLevel());
        data.put("userLevel", dailySettle.getUserLevel());
        data.put("minValidUser", Integer.valueOf(this.dataFactory.getDailySettleConfig().getMinValidUserl()));
        data.put("maxSignLevel", Integer.valueOf(this.dataFactory.getDailySettleConfig().getMaxSignLevel()));
        data.put("userLevels", userLevels);
        json.accumulate("data", data);
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
  
  @RequestMapping(value={"/lottery-user-daily-settle/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DAILY_SETTLE_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-daily-settle/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String scaleLevel = HttpUtil.getStringParameterTrim(request, "scaleLevel");
        String[] scaleLevels = scaleLevel.split(",");
        String lossLevel = HttpUtil.getStringParameterTrim(request, "lossLevel");
        String[] lossLevels = scaleLevel.split(",");
        String salesLevel = HttpUtil.getStringParameterTrim(request, "salesLevel");
        String[] salesLevels = salesLevel.split(",");
        String userLevel = HttpUtil.getStringParameterTrim(request, "userLevel");
        String[] userLevels = userLevel.split(",");
        
        double[] scaleCfg = this.dataFactory.getDailySettleConfig().getLevelsScale();
        double[] salesCfg = this.dataFactory.getDailySettleConfig().getLevelsSales();
        double[] lossCfg = this.dataFactory.getDailySettleConfig().getLevelsLoss();
        int[] userCfg = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
        if ((Double.valueOf(scaleLevels[0]).doubleValue() < scaleCfg[0]) || (Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > scaleCfg[1]) || 
          (Double.valueOf(salesLevels[0]).doubleValue() < salesCfg[0]) || (Double.valueOf(salesLevels[(salesLevels.length - 1)]).doubleValue() > salesCfg[1]) || 
          (Double.valueOf(lossLevels[0]).doubleValue() < lossCfg[0]) || (Double.valueOf(lossLevels[(lossLevels.length - 1)]).doubleValue() > lossCfg[1]) || 
          (Integer.valueOf(userLevels[0]).intValue() < userCfg[0]) || (Integer.valueOf(userLevels[(userLevels.length - 1)]).intValue() > userCfg[1]))
        {
          json.set(Integer.valueOf(1), "1-8");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        UserDailySettle dailySettle = this.settleService.getById(id);
        if (dailySettle == null)
        {
          json.set(Integer.valueOf(1), "1-7");
        }
        else
        {
          User uBean = this.userDao.getById(dailySettle.getUserId());
          if (this.settleService.checkCanEdit(json, uBean))
          {
            boolean result = this.settleService.update(json, id, scaleLevel, salesLevel, lossLevel, Integer.valueOf(userLevels[0]).intValue(), userLevel);
            if (result)
            {
              this.adminUserLogJob.logEditDailySettle(uEntity, request, dailySettle, scaleLevel, salesLevel, lossLevel, userLevel);
              
              json.set(Integer.valueOf(0), "0-5");
            }
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
  
  @RequestMapping(value={"/lottery-user-daily-settle/add-get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DAILY_SETTLE_ADD_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-daily-settle/add-get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        if (StringUtils.isEmpty(username))
        {
          json.set(Integer.valueOf(0), "0-3");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        User user = this.uService.getByUsername(username);
        if (user == null)
        {
          json.set(Integer.valueOf(2), "2-32");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if (!this.settleService.checkCanEdit(json, user))
        {
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        UserDailySettle dailySettle = this.settleService.getByUserId(user.getId());
        if (dailySettle != null)
        {
          json.set(Integer.valueOf(2), "2-3007");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        UserDailySettle upDailySettle = this.settleService.getByUserId(user.getUpid());
        if ((!this.uCodePointUtil.isLevel3Proxy(user)) && ((upDailySettle == null) || (upDailySettle.getStatus() != 1)))
        {
          json.set(Integer.valueOf(2), "2-3008");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        double[] minMaxScale = this.settleService.getMinMaxScale(user);
        double minScale = minMaxScale[0];
        double maxScale = minMaxScale[1];
        
        double[] minMaxSales = this.settleService.getMinMaxSales(user);
        double minSales = minMaxSales[0];
        double maxSales = minMaxSales[1];
        
        double[] minMaxLoss = this.settleService.getMinMaxLoss(user);
        double minLoss = minMaxLoss[0];
        double maxLoss = minMaxLoss[1];
        
        int[] minMaxUser = this.settleService.getMinMaxUsers(user);
        int minUser = minMaxUser[0];
        int maxUser = minMaxUser[1];
        
        List<String> userLevels = this.uService.getUserLevels(user);
        Map<String, Object> data = new HashMap();
        data.put("upBean", upDailySettle == null ? null : new UserDailySettleVO(upDailySettle, this.dataFactory));
        data.put("minScale", Double.valueOf(minScale));
        data.put("maxScale", Double.valueOf(maxScale));
        data.put("minSales", Double.valueOf(minSales));
        data.put("maxSales", Double.valueOf(maxSales));
        data.put("minLoss", Double.valueOf(minLoss));
        data.put("maxLoss", Double.valueOf(maxLoss));
        data.put("minUser", Integer.valueOf(minUser));
        data.put("maxUser", Integer.valueOf(maxUser));
        data.put("minValidUser", Integer.valueOf(this.dataFactory.getDailySettleConfig().getMinValidUserl()));
        data.put("maxSignLevel", Integer.valueOf(this.dataFactory.getDailySettleConfig().getMaxSignLevel()));
        data.put("userLevels", userLevels);
        json.accumulate("data", data);
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
  
  @RequestMapping(value={"/lottery-user-daily-settle/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_DAILY_SETTLE_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-daily-settle/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String username = HttpUtil.getStringParameterTrim(request, "username");
        String scaleLevel = HttpUtil.getStringParameterTrim(request, "scaleLevel");
        String lossLevel = HttpUtil.getStringParameterTrim(request, "lossLevel");
        String salesLevel = HttpUtil.getStringParameterTrim(request, "salesLevel");
        String userLevel = HttpUtil.getStringParameterTrim(request, "userLevel");
        
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        
        String[] scaleLevels = scaleLevel.split(",");
        String[] lossLevels = lossLevel.split(",");
        String[] salesLevels = salesLevel.split(",");
        String[] userLevels = userLevel.split(",");
        
        double[] scaleCfg = this.dataFactory.getDailySettleConfig().getLevelsScale();
        double[] salesCfg = this.dataFactory.getDailySettleConfig().getLevelsSales();
        double[] lossCfg = this.dataFactory.getDailySettleConfig().getLevelsLoss();
        int[] userCfg = { this.dataFactory.getDailySettleConfig().getMinValidUserl(), 1000 };
        
        User user = this.uService.getByUsername(username);
        if (user == null)
        {
          json.set(Integer.valueOf(2), "2-32");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if ((Double.valueOf(scaleLevels[0]).doubleValue() < scaleCfg[0]) || (Double.valueOf(scaleLevels[(scaleLevels.length - 1)]).doubleValue() > scaleCfg[1]) || 
          (Double.valueOf(salesLevels[0]).doubleValue() < salesCfg[0]) || (Double.valueOf(salesLevels[(salesLevels.length - 1)]).doubleValue() > salesCfg[1]) || 
          (Double.valueOf(lossLevels[0]).doubleValue() < lossCfg[0]) || (Double.valueOf(lossLevels[(lossLevels.length - 1)]).doubleValue() > lossCfg[1]) || 
          (Integer.valueOf(userLevels[0]).intValue() < userCfg[0]) || (Integer.valueOf(userLevels[(userLevels.length - 1)]).intValue() > userCfg[1]))
        {
          json.set(Integer.valueOf(1), "1-8");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        if ((status != 1) && (status != 2))
        {
          json.set(Integer.valueOf(1), "1-8");
          HttpUtil.write(response, json.toString(), "text/json");
          return;
        }
        boolean result = this.settleService.add(json, username, scaleLevel, salesLevel, lossLevel, Integer.valueOf(userLevels[0]).intValue(), status, userLevel);
        if (result)
        {
          this.adminUserLogJob.logAddDailySettle(uEntity, request, username, scaleLevel, salesLevel, lossLevel, userLevel, status);
          
          json.set(Integer.valueOf(0), "0-5");
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
