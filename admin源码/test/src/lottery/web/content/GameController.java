package lottery.web.content;

import lottery.domains.content.vo.user.GameBetsVO;
import javautils.StringUtil;
import java.util.Map;
import lottery.domains.content.entity.UserGameAccount;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import lottery.domains.content.entity.SysPlatform;
import lottery.domains.content.entity.Game;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import javautils.jdbc.PageList;
import admin.domains.content.entity.AdminUser;
import javautils.http.HttpUtil;
import admin.web.WebJSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lottery.domains.content.api.ag.AGAPI;
import lottery.domains.content.api.pt.PTAPI;
import admin.domains.jobs.AdminUserLogJob;
import admin.domains.jobs.AdminUserActionLogJob;
import lottery.domains.content.biz.SysPlatformService;
import lottery.domains.content.dao.UserGameAccountDao;
import lottery.domains.content.biz.GameBetsService;
import org.springframework.beans.factory.annotation.Autowired;
import lottery.domains.content.biz.GameService;
import org.springframework.stereotype.Controller;
import admin.web.helper.AbstractActionController;

@Controller
public class GameController extends AbstractActionController
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
    
    @RequestMapping(value = { "/game/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String gameName = request.getParameter("gameName");
                final String gameCode = request.getParameter("gameCode");
                final Integer typeId = HttpUtil.getIntParameter(request, "typeId");
                final Integer platformId = HttpUtil.getIntParameter(request, "platformId");
                final Integer display = HttpUtil.getIntParameter(request, "display");
                final Integer flashSupport = HttpUtil.getIntParameter(request, "flashSupport");
                final Integer h5Support = HttpUtil.getIntParameter(request, "h5Support");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.gameService.search(gameName, gameCode, typeId, platformId, display, flashSupport, h5Support, start, limit);
                if (pList != null) {
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final Game game = this.gameService.getById(id);
                if (game != null) {
                    json.accumulate("data", game);
                }
                else {
                    json.accumulate("data", "{}");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/add" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_ADD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/add";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String gameName = request.getParameter("gameName");
                final String gameCode = request.getParameter("gameCode");
                final Integer platformId = HttpUtil.getIntParameter(request, "platformId");
                final Integer typeId = HttpUtil.getIntParameter(request, "typeId");
                final String imgUrl = request.getParameter("imgUrl");
                final int sequence = HttpUtil.getIntParameter(request, "sequence");
                final int display = HttpUtil.getIntParameter(request, "display");
                final Integer flashSupport = HttpUtil.getIntParameter(request, "flashSupport");
                final Integer h5Support = HttpUtil.getIntParameter(request, "h5Support");
                final Integer progressiveSupport = HttpUtil.getIntParameter(request, "progressiveSupport");
                final String progressiveCode = request.getParameter("progressiveCode");
                final boolean result = this.gameService.add(gameName, gameCode, typeId, platformId, imgUrl, sequence, display, flashSupport, h5Support, progressiveSupport, progressiveCode);
                if (result) {
                    this.adminUserLogJob.logAddGame(uEntity, request, gameName);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/mod" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_MOD(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/mod";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final String gameName = request.getParameter("gameName");
                final String gameCode = request.getParameter("gameCode");
                final Integer typeId = HttpUtil.getIntParameter(request, "typeId");
                final Integer platformId = HttpUtil.getIntParameter(request, "platformId");
                final String imgUrl = request.getParameter("imgUrl");
                final Integer sequence = HttpUtil.getIntParameter(request, "sequence");
                final Integer display = HttpUtil.getIntParameter(request, "display");
                final Integer flashSupport = HttpUtil.getIntParameter(request, "flashSupport");
                final Integer h5Support = HttpUtil.getIntParameter(request, "h5Support");
                final Integer progressiveSupport = HttpUtil.getIntParameter(request, "progressiveSupport");
                final String progressiveCode = request.getParameter("progressiveCode");
                final boolean result = this.gameService.update(id, gameName, gameCode, typeId, platformId, imgUrl, sequence, display, flashSupport, h5Support, progressiveSupport, progressiveCode);
                if (result) {
                    this.adminUserLogJob.logUpdateGame(uEntity, request, gameName);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/del" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_DEL(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/del";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final Game game = this.gameService.getById(id);
                if (game == null) {
                    json.set(1, "2-3001");
                }
                else {
                    final boolean result = this.gameService.deleteById(id);
                    if (result) {
                        this.adminUserLogJob.logDelGame(uEntity, request, game.getGameName());
                        json.set(0, "0-5");
                    }
                    else {
                        json.set(1, "1-5");
                    }
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/mod-display" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_MOD_DISPLAY(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/mod-display";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int display = HttpUtil.getIntParameter(request, "display");
                final Game game = this.gameService.getById(id);
                if (game == null) {
                    json.set(1, "2-3001");
                }
                else {
                    final boolean result = this.gameService.updateDisplay(id, display);
                    if (result) {
                        this.adminUserLogJob.logUpdateGameDisplay(uEntity, request, game.getGameName(), display);
                        json.set(0, "0-5");
                    }
                    else {
                        json.set(1, "1-5");
                    }
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/check-gamename-exist" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_CHECK_GAMENAME_EXIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String gameName = request.getParameter("gameName");
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final Game game = this.gameService.getByGameName(gameName);
        String isExist;
        if (game == null) {
            isExist = "true";
        }
        else if (id != null && game.getId() == id) {
            isExist = "true";
        }
        else {
            isExist = "false";
        }
        HttpUtil.write(response, isExist, "text/json");
    }
    
    @RequestMapping(value = { "/game/check-gamecode-exist" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_CHECK_GAMECODE_EXIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String gameCode = request.getParameter("gameCode");
        final Integer id = HttpUtil.getIntParameter(request, "id");
        final Game game = this.gameService.getByGameCode(gameCode);
        String isExist;
        if (game == null) {
            isExist = "true";
        }
        else if (id != null && game.getId() == id) {
            isExist = "true";
        }
        else {
            isExist = "false";
        }
        HttpUtil.write(response, isExist, "text/json");
    }
    
    @RequestMapping(value = { "/game/platform/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_PLATFORM_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/platform/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final List<SysPlatform> sysPlatforms = this.sysPlatformService.listAll();
                final Iterator<SysPlatform> iterator = sysPlatforms.iterator();
                while (iterator.hasNext()) {
                    final SysPlatform next = iterator.next();
                    if (next.getId() != 4 && next.getId() != 11 && next.getId() != 12 && next.getId() != 13) {
                        iterator.remove();
                    }
                }
                if (CollectionUtils.isNotEmpty((Collection)sysPlatforms)) {
                    json.accumulate("data", sysPlatforms);
                }
                else {
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/platform/mod-status" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_PLATFORM_MOD_STATUS(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/platform/mod-status";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final int id = HttpUtil.getIntParameter(request, "id");
                final int status = HttpUtil.getIntParameter(request, "status");
                final boolean result = this.sysPlatformService.updateStatus(id, status);
                if (result) {
                    this.adminUserLogJob.logPlatformModStatus(uEntity, request, id, status);
                    json.set(0, "0-5");
                }
                else {
                    json.set(1, "1-5");
                }
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game/balance" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_BALANCE(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game/balance";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int platformId = HttpUtil.getIntParameter(request, "platformId");
            final int userId = HttpUtil.getIntParameter(request, "userId");
            final UserGameAccount account = this.uGameAccountDao.get(userId, platformId);
            final Map<String, Object> data = new HashMap<String, Object>();
            if (account == null) {
                data.put("balance", 0);
                json.accumulate("data", data);
                json.set(0, "0-3");
            }
            else {
                Double balance = null;
                if (account != null) {
                    if (platformId == 11) {
                        balance = this.ptAPI.playerBalance(json, account.getUsername());
                    }
                    else if (platformId == 4) {
                        balance = this.agAPI.getBalance(json, account.getUsername(), account.getPassword());
                    }
                }
                if (balance != null) {
                    data.put("balance", balance);
                    json.accumulate("data", data);
                    json.set(0, "0-3");
                }
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game-bets/list" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_BETS_LIST(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game-bets/list";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            if (super.hasAccess(uEntity, actionKey)) {
                final String keyword = request.getParameter("keyword");
                final String username = request.getParameter("username");
                final Integer platformId = HttpUtil.getIntParameter(request, "platformId");
                String minTime = request.getParameter("minTime");
                if (StringUtil.isNotNull(minTime)) {
                    minTime = String.valueOf(minTime) + " 00:00:00";
                }
                String maxTime = request.getParameter("maxTime");
                if (StringUtil.isNotNull(maxTime)) {
                    maxTime = String.valueOf(maxTime) + " 00:00:00";
                }
                final Double minMoney = HttpUtil.getDoubleParameter(request, "minBetsMoney");
                final Double maxMoney = HttpUtil.getDoubleParameter(request, "maxBetsMoney");
                final Double minPrizeMoney = HttpUtil.getDoubleParameter(request, "minPrizeMoney");
                final Double maxPrizeMoney = HttpUtil.getDoubleParameter(request, "maxPrizeMoney");
                final String gameCode = request.getParameter("gameCode");
                final String gameType = request.getParameter("gameType");
                final String gameName = request.getParameter("gameName");
                final int start = HttpUtil.getIntParameter(request, "start");
                final int limit = HttpUtil.getIntParameter(request, "limit");
                final PageList pList = this.gameBetsService.search(keyword, username, platformId, minTime, maxTime, minMoney, maxMoney, minPrizeMoney, maxPrizeMoney, gameCode, gameType, gameName, start, limit);
                if (pList != null) {
                    final double[] totalMoney = this.gameBetsService.getTotalMoney(keyword, username, platformId, minTime, maxTime, minMoney, maxMoney, minPrizeMoney, maxPrizeMoney, gameCode, gameType, gameName);
                    json.accumulate("totalMoney", totalMoney[0]);
                    json.accumulate("totalPrizeMoney", totalMoney[1]);
                    json.accumulate("totalCount", pList.getCount());
                    json.accumulate("data", pList.getList());
                }
                else {
                    json.accumulate("totalMoney", 0);
                    json.accumulate("totalPrizeMoney", 0);
                    json.accumulate("totalCount", 0);
                    json.accumulate("data", "[]");
                }
                json.set(0, "0-3");
            }
            else {
                json.set(2, "2-4");
            }
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
    
    @RequestMapping(value = { "/game-bets/get" }, method = { RequestMethod.POST })
    @ResponseBody
    public void GAME_BETS_GET(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        final String actionKey = "/game-bets/get";
        final long t1 = System.currentTimeMillis();
        final WebJSONObject json = new WebJSONObject(super.getAdminDataFactory());
        final AdminUser uEntity = super.getCurrUser(session, request, response);
        if (uEntity != null) {
            final int id = HttpUtil.getIntParameter(request, "id");
            final GameBetsVO gameBetsVO = this.gameBetsService.getById(id);
            if (gameBetsVO != null) {
                json.accumulate("data", gameBetsVO);
            }
            else {
                json.accumulate("data", "{}");
            }
            json.set(0, "0-3");
        }
        else {
            json.set(2, "2-6");
        }
        final long t2 = System.currentTimeMillis();
        if (uEntity != null) {
            this.adminUserActionLogJob.add(request, actionKey, uEntity, json, t2 - t1);
        }
        HttpUtil.write(response, json.toString(), "text/json");
    }
}
