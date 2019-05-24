package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import lottery.domains.content.biz.LotteryService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.content.vo.lottery.LotteryVO;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryServiceImpl
  implements LotteryService
{
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private LotteryDao lotteryDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<LotteryVO> list(String type)
  {
    List<Lottery> lotteryList;
    if ((StringUtil.isNotNull(type)) && (StringUtil.isInteger(type))) {
      lotteryList = this.lotteryDataFactory.listLottery(Integer.parseInt(type));
    } else {
      lotteryList = this.lotteryDataFactory.listLottery();
    }
    List<LotteryVO> list = new ArrayList();
    for (Lottery tmpBean : lotteryList) {
      list.add(new LotteryVO(tmpBean, this.lotteryDataFactory));
    }
    return list;
  }
  
  public boolean updateStatus(int id, int status)
  {
    boolean result = this.lotteryDao.updateStatus(id, status);
    if (result)
    {
      this.lotteryDataFactory.initLottery();
      this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY);
    }
    return result;
  }
  
  public boolean updateTimes(int id, int times)
  {
    boolean result = this.lotteryDao.updateTimes(id, times);
    if (result)
    {
      this.lotteryDataFactory.initLottery();
      this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY);
    }
    return result;
  }
  
  public Lottery getByShortName(String shortName)
  {
    return this.lotteryDataFactory.getLottery(shortName);
  }
}
