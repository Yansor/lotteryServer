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
import lottery.domains.content.biz.LotteryTypeService;
import lottery.domains.content.entity.LotteryType;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LotteryTypeController
  extends AbstractActionController
{
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private LotteryTypeService lotteryTypeService;
  
  @RequestMapping(value={"/lottery-type/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_TYPE_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    List<LotteryType> list = this.lotteryTypeService.listAll();
    JSONArray json = JSONArray.fromObject(list);
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/lottery-type/update-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void LOTTERY_USER_UPDATE_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/lottery-type/update-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        boolean result = this.lotteryTypeService.updateStatus(id, status);
        if (result) {
          json.set(Integer.valueOf(0), "0-5");
        } else {
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
}
