package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.UserBetsOriginalService;
import lottery.domains.content.vo.user.UserBetsOriginalVO;
import lottery.web.content.utils.UserCodePointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserBetsOriginalController
  extends AbstractActionController
{
  @Autowired
  private UserBetsOriginalService uBetsOriginalService;
  @Autowired
  private UserCodePointUtil uCodePointUtil;
  
  @RequestMapping(value={"/lottery-user-bets/original-list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_ORIGINAL_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/original-list";
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
        
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.uBetsOriginalService.search(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, 
          minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status, start, limit);
        if (pList != null)
        {
          double[] totalMoney = this.uBetsOriginalService.getTotalMoney(keyword, username, utype, type, lotteryId, expect, ruleId, minTime, maxTime, minPrizeTime, maxPrizeTime, 
            minMoney, maxMoney, minMultiple, maxMultiple, minPrizeMoney, maxPrizeMoney, status);
          json.accumulate("totalMoney", Double.valueOf(totalMoney[0]));
          json.accumulate("totalPrizeMoney", Double.valueOf(totalMoney[1]));
          json.accumulate("totalCount", Integer.valueOf(pList.getCount()));
          json.accumulate("data", pList.getList());
        }
        else
        {
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-user-bets/original-get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_BETS_ORIGINAL_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-user-bets/original-get";
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        UserBetsOriginalVO result = this.uBetsOriginalService.getById(id);
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
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
