package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.List;
import javautils.http.HttpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.biz.LotteryCrawlerStatusService;
import lottery.domains.content.entity.LotteryCrawlerStatus;
import lottery.domains.content.vo.lottery.LotteryCrawlerStatusVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LotteryCrawlerStatusController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private LotteryCrawlerStatusService lotteryCrawlerStatusService;
  
  @RequestMapping(value={"/lottery-crawler-status/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_CRAWLER_STATUS_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-crawler-status/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        List<LotteryCrawlerStatusVO> list = this.lotteryCrawlerStatusService.listAll();
        json.set(Integer.valueOf(0), "0-3");
        json.accumulate("data", list);
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
  
  @RequestMapping(value={"/lottery-crawler-status/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_CRAWLER_STATUS_EDIT(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-crawler-status/edit";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String lottery = request.getParameter("lottery");
        String lastExpect = request.getParameter("lastExpect");
        String lastUpdate = request.getParameter("lastUpdate");
        boolean result = this.lotteryCrawlerStatusService.update(lottery, lastExpect, lastUpdate);
        if (result) {
          json.set(Integer.valueOf(0), "0-6");
        } else {
          json.set(Integer.valueOf(1), "1-6");
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
  
  @RequestMapping(value={"/lottery-crawler-status/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_CRAWLER_STATUS_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String lottery = request.getParameter("lottery");
    LotteryCrawlerStatus bean = this.lotteryCrawlerStatusService.getByLottery(lottery);
    JSONObject json = JSONObject.fromObject(bean);
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
