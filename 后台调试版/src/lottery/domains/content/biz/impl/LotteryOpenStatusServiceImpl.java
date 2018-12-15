package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.redis.JedisTemplate;
import lottery.domains.content.biz.LotteryOpenStatusService;
import lottery.domains.content.dao.LotteryOpenCodeDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.entity.LotteryOpenCode;
import lottery.domains.content.vo.lottery.LotteryOpenStatusVO;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import lottery.domains.utils.lottery.open.OpenTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryOpenStatusServiceImpl
  implements LotteryOpenStatusService
{
  private static final String ADMIN_OPEN_CODE_KEY = "ADMIN_OPEN_CODE:%s";
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private LotteryOpenCodeDao lotteryOpenCodeDao;
  @Autowired
  private LotteryOpenUtil lotteryOpenUtil;
  @Autowired
  private JedisTemplate jedisTemplate;
  
  public List<LotteryOpenStatusVO> search(String lotteryId, String date)
  {
    List<LotteryOpenStatusVO> list = new ArrayList();
    if ((StringUtil.isDateString(date)) && (StringUtil.isInteger(lotteryId)))
    {
      Lottery lottery = this.lotteryDataFactory.getLottery(Integer.parseInt(lotteryId));
      if (lottery != null)
      {
        List<OpenTime> openList = this.lotteryOpenUtil.getOpenDateList(lottery.getId(), date);
        if (openList != null)
        {
          String[] expects = new String[openList.size()];
          int i = 0;
          for (int j = openList.size(); i < j; i++) {
            expects[i] = ((OpenTime)openList.get(i)).getExpect();
          }
          Map<String, LotteryOpenCode> openCodeMap = new HashMap();
          LotteryOpenCode tmpCodeBean;
          if ((expects != null) && (expects.length > 0))
          {
            List<LotteryOpenCode> lotteryOpenCodeList = this.lotteryOpenCodeDao.list(lottery.getShortName(), expects);
            for (Iterator localIterator = lotteryOpenCodeList.iterator(); localIterator.hasNext();)
            {
              tmpCodeBean = (LotteryOpenCode)localIterator.next();
              openCodeMap.put(tmpCodeBean.getExpect(), tmpCodeBean);
            }
          }
          for (OpenTime openTime : openList)
          {
            LotteryOpenStatusVO tmpBean = new LotteryOpenStatusVO();
            tmpBean.setLottery(lottery);
            tmpBean.setOpenTime(openTime);
            if (openCodeMap.containsKey(openTime.getExpect()))
            {
              tmpBean.setOpenCode((LotteryOpenCode)openCodeMap.get(openTime.getExpect()));
            }
            else
            {
              String key = String.format("ADMIN_OPEN_CODE:%s", new Object[] { lottery.getShortName() });
              boolean iskey = this.jedisTemplate.hexists(key, openTime.getExpect()).booleanValue();
              if (iskey)
              {
                String code = this.jedisTemplate.hget(key, openTime.getExpect());
                LotteryOpenCode bean = new LotteryOpenCode();
                bean.setCode(code);
                bean.setOpenStatus(Integer.valueOf(0));
                bean.setLottery(lottery.getShortName());
                bean.setTime(new Moment().toSimpleTime());
                bean.setInterfaceTime(new Moment().toSimpleTime());
                tmpBean.setOpenCode(bean);
              }
            }
            list.add(tmpBean);
          }
        }
      }
    }
    return list;
  }
  
  public boolean doManualControl(String lottery, String expect)
  {
    LotteryOpenCode entity = this.lotteryOpenCodeDao.get(lottery, expect);
    if (entity != null)
    {
      entity.setOpenStatus(Integer.valueOf(0));
      entity.setOpenTime(null);
      return this.lotteryOpenCodeDao.update(entity);
    }
    return false;
  }
}
