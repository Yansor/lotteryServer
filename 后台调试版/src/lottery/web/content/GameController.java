package lottery.web.content;

import admin.domains.content.entity.AdminUser;
import admin.domains.jobs.AdminUserActionLogJob;
import admin.domains.jobs.AdminUserLogJob;
import admin.web.WebJSONObject;
import admin.web.helper.AbstractActionController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.http.HttpUtil;
import javautils.jdbc.PageList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lottery.domains.content.api.ag.AGAPI;
import lottery.domains.content.api.pt.PTAPI;
import lottery.domains.content.biz.GameBetsService;
import lottery.domains.content.biz.GameService;
import lottery.domains.content.biz.SysPlatformService;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.entity.Game;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.entity.UserGameAccount;
import lottery.domains.content.vo.user.GameBetsVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController
  extends AbstractActionController
{
  @Autowired
  private GameService gameService;
  @Autowired
  private GameBetsService gameBetsService;
  @Autowired
  private UserGameAccountDao uGameAccountDao;
  @Autowired
  private SysPlatformService sysPlatformService;
  @Autowired
  private AdminUserActionLogJob adminUserActionLogJob;
  @Autowired
  private AdminUserLogJob adminUserLogJob;
  @Autowired
  private PTAPI ptAPI;
  @Autowired
  private AGAPI agAPI;
  
  @RequestMapping(value={"/game/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String gameName = request.getParameter("gameName");
        String gameCode = request.getParameter("gameCode");
        Integer typeId = HttpUtil.getIntParameter(request, "typeId");
        Integer platformId = HttpUtil.getIntParameter(request, "platformId");
        Integer display = HttpUtil.getIntParameter(request, "display");
        Integer flashSupport = HttpUtil.getIntParameter(request, "flashSupport");
        Integer h5Support = HttpUtil.getIntParameter(request, "h5Support");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.gameService.search(gameName, gameCode, typeId, platformId, display, flashSupport, h5Support, start, limit);
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
  
  @RequestMapping(value={"/game/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        Game game = this.gameService.getById(id);
        if (game != null) {
          json.accumulate("data", game);
        } else {
          json.accumulate("data", "{}");
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
  
  @RequestMapping(value={"/game/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_ADD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/add";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String gameName = request.getParameter("gameName");
        String gameCode = request.getParameter("gameCode");
        Integer platformId = HttpUtil.getIntParameter(request, "platformId");
        Integer typeId = HttpUtil.getIntParameter(request, "typeId");
        String imgUrl = request.getParameter("imgUrl");
        int sequence = HttpUtil.getIntParameter(request, "sequence").intValue();
        int display = HttpUtil.getIntParameter(request, "display").intValue();
        Integer flashSupport = HttpUtil.getIntParameter(request, "flashSupport");
        Integer h5Support = HttpUtil.getIntParameter(request, "h5Support");
        Integer progressiveSupport = HttpUtil.getIntParameter(request, "progressiveSupport");
        String progressiveCode = request.getParameter("progressiveCode");
        
        boolean result = this.gameService.add(gameName, gameCode, typeId, platformId, imgUrl, sequence, display, flashSupport, h5Support, progressiveSupport, progressiveCode);
        if (result)
        {
          this.adminUserLogJob.logAddGame(uEntity, request, gameName);
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
  
  @RequestMapping(value={"/game/mod"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_MOD(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/mod";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        String gameName = request.getParameter("gameName");
        String gameCode = request.getParameter("gameCode");
        Integer typeId = HttpUtil.getIntParameter(request, "typeId");
        Integer platformId = HttpUtil.getIntParameter(request, "platformId");
        String imgUrl = request.getParameter("imgUrl");
        Integer sequence = HttpUtil.getIntParameter(request, "sequence");
        Integer display = HttpUtil.getIntParameter(request, "display");
        Integer flashSupport = HttpUtil.getIntParameter(request, "flashSupport");
        Integer h5Support = HttpUtil.getIntParameter(request, "h5Support");
        Integer progressiveSupport = HttpUtil.getIntParameter(request, "progressiveSupport");
        String progressiveCode = request.getParameter("progressiveCode");
        
        boolean result = this.gameService.update(id, gameName, gameCode, typeId, platformId, imgUrl, sequence, display, flashSupport, h5Support, progressiveSupport, progressiveCode);
        if (result)
        {
          this.adminUserLogJob.logUpdateGame(uEntity, request, gameName);
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
  
  @RequestMapping(value={"/game/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_DEL(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/del";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        
        Game game = this.gameService.getById(id);
        if (game == null)
        {
          json.set(Integer.valueOf(1), "2-3001");
        }
        else
        {
          boolean result = this.gameService.deleteById(id);
          if (result)
          {
            this.adminUserLogJob.logDelGame(uEntity, request, game.getGameName());
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
  
  @RequestMapping(value={"/game/mod-display"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_MOD_DISPLAY(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/mod-display";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int display = HttpUtil.getIntParameter(request, "display").intValue();
        
        Game game = this.gameService.getById(id);
        if (game == null)
        {
          json.set(Integer.valueOf(1), "2-3001");
        }
        else
        {
          boolean result = this.gameService.updateDisplay(id, display);
          if (result)
          {
            this.adminUserLogJob.logUpdateGameDisplay(uEntity, request, game.getGameName(), display);
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
  
  @RequestMapping(value={"/game/check-gamename-exist"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_CHECK_GAMENAME_EXIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String gameName = request.getParameter("gameName");
    Integer id = HttpUtil.getIntParameter(request, "id");
    
    Game game = this.gameService.getByGameName(gameName);
    String isExist;
    if (game == null)
    {
      isExist = "true";
    }
    else
    {
      if ((id != null) && (game.getId() == id.intValue())) {
        isExist = "true";
      } else {
        isExist = "false";
      }
    }
    HttpUtil.write(response, isExist, "text/json");
  }
  
  @RequestMapping(value={"/game/check-gamecode-exist"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_CHECK_GAMECODE_EXIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String gameCode = request.getParameter("gameCode");
    Integer id = HttpUtil.getIntParameter(request, "id");
    
    Game game = this.gameService.getByGameCode(gameCode);
    String isExist;
    if (game == null)
    {
      isExist = "true";
    }
    else
    {
      if ((id != null) && (game.getId() == id.intValue())) {
        isExist = "true";
      } else {
        isExist = "false";
      }
    }
    HttpUtil.write(response, isExist, "text/json");
  }
  
  @RequestMapping(value={"/game/platform/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_PLATFORM_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/platform/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        List<SysPlatform> sysPlatforms = this.sysPlatformService.listAll();
        
        Iterator<SysPlatform> iterator = sysPlatforms.iterator();
        while (iterator.hasNext())
        {
          SysPlatform next = (SysPlatform)iterator.next();
          if ((next.getId() != 4) && (next.getId() != 11) && (next.getId() != 12) && (next.getId() != 13)) {
            iterator.remove();
          }
        }
        if (CollectionUtils.isNotEmpty(sysPlatforms)) {
          json.accumulate("data", sysPlatforms);
        } else {
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
  
  @RequestMapping(value={"/game/platform/mod-status"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_PLATFORM_MOD_STATUS(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/platform/mod-status";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        int id = HttpUtil.getIntParameter(request, "id").intValue();
        int status = HttpUtil.getIntParameter(request, "status").intValue();
        
        boolean result = this.sysPlatformService.updateStatus(id, status);
        if (result)
        {
          this.adminUserLogJob.logPlatformModStatus(uEntity, request, id, status);
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
  
  @RequestMapping(value={"/game/balance"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_BALANCE(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game/balance";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      int platformId = HttpUtil.getIntParameter(request, "platformId").intValue();
      int userId = HttpUtil.getIntParameter(request, "userId").intValue();
      UserGameAccount account = this.uGameAccountDao.get(userId, platformId);
      Map<String, Object> data = new HashMap();
      if (account == null)
      {
        data.put("balance", Integer.valueOf(0));
        json.accumulate("data", data);
        json.set(Integer.valueOf(0), "0-3");
      }
      else
      {
        Double balance = null;
        if (account != null) {
          if (platformId == 11) {
            balance = this.ptAPI.playerBalance(json, account.getUsername());
          } else if (platformId == 4) {
            balance = this.agAPI.getBalance(json, account.getUsername(), account.getPassword());
          }
        }
        if (balance != null)
        {
          data.put("balance", balance);
          json.accumulate("data", data);
          json.set(Integer.valueOf(0), "0-3");
        }
      }
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/game-bets/list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_BETS_LIST(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game-bets/list";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      if (super.hasAccess(uEntity, actionKey))
      {
        String keyword = request.getParameter("keyword");
        String username = request.getParameter("username");
        Integer platformId = HttpUtil.getIntParameter(request, "platformId");
        String minTime = request.getParameter("minTime");
        if (StringUtil.isNotNull(minTime)) {
          minTime = minTime + " 00:00:00";
        }
        String maxTime = request.getParameter("maxTime");
        if (StringUtil.isNotNull(maxTime)) {
          maxTime = maxTime + " 00:00:00";
        }
        Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
        Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
        Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
        Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
        String gameCode = request.getParameter("gameCode");
        String gameType = request.getParameter("gameType");
        String gameName = request.getParameter("gameName");
        int start = HttpUtil.getIntParameter(request, "start").intValue();
        int limit = HttpUtil.getIntParameter(request, "limit").intValue();
        PageList pList = this.gameBetsService.search(keyword, username, platformId, minTime, maxTime, minMoney, maxMoney, 
          minPrizeMoney, maxPrizeMoney, gameCode, gameType, gameName, start, limit);
        if (pList != null)
        {
          double[] totalMoney = this.gameBetsService.getTotalMoney(keyword, username, platformId, minTime, maxTime, minMoney, maxMoney, 
            minPrizeMoney, maxPrizeMoney, gameCode, gameType, gameName);
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
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
  
  @RequestMapping(value={"/game-bets/get"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public void GAME_BETS_GET(HttpSession session, HttpServletRequest request, HttpServletResponse response)
  {
    String actionKey = "/game-bets/get";
    long t1 = System.currentTimeMillis();
    WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
    AdminUser uEntity = super.getCurrUser(session, request, response);
    if (uEntity != null)
    {
      int id = HttpUtil.getIntParameter(request, "id").intValue();
      GameBetsVO gameBetsVO = this.gameBetsService.getById(id);
      if (gameBetsVO != null) {
        json.accumulate("data", gameBetsVO);
      } else {
        json.accumulate("data", "{}");
      }
      json.set(Integer.valueOf(0), "0-3");
    }
    else
    {
      json.set(Integer.valueOf(2), "2-6");
    }
    long t2 = System.currentTimeMillis();
    if (uEntity != null) {
      this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
    }
    HttpUtil.write(response, json.toString(), "text/json");
  }
}
