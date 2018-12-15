package lottery.domains.content.biz.impl;

import java.util.List;
import lottery.domains.content.biz.LotteryTypeService;
import lottery.domains.content.dao.DbServerSyncDao;
import lottery.domains.content.dao.LotteryTypeDao;
import lottery.domains.content.entity.LotteryType;
import lottery.domains.content.global.DbServerSyncEnum;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryTypeServiceImpl
  implements LotteryTypeService
{
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  @Autowired
  private LotteryTypeDao lotteryTypeDao;
  @Autowired
  private DbServerSyncDao dbServerSyncDao;
  
  public List<LotteryType> listAll()
  {
    return this.lotteryDataFactory.listLotteryType();
  }
  
  public boolean updateStatus(int id, int status)
  {
    boolean result = this.lotteryTypeDao.updateStatus(id, status);
    if (result)
    {
      this.lotteryDataFactory.initLotteryType();
      this.dbServerSyncDao.update(DbServerSyncEnum.LOTTERY_TYPE);
    }
    return result;
  }
}
