package com.fsy.lottery.domains.content.pool;

import java.util.List;
import com.fsy.lottery.domains.content.entity.Lottery;
import com.fsy.lottery.domains.content.entity.LotteryOpenTime;

public abstract interface LotteryDataFactory
{
  public abstract void init();
  
  public abstract void initLotteryOpenTime();
  
  public abstract List<LotteryOpenTime> listLotteryOpenTime(String paramString);
  
  public abstract void initLotteryOpenCode();
  
  public abstract void initLottery();
  
  public abstract Lottery getLottery(int paramInt);
  
  public abstract Lottery getLottery(String paramString);
  
  public abstract List<Lottery> listLottery();
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/pool/LotteryDataFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */