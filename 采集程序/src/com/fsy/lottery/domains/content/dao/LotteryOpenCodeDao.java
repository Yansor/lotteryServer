package com.fsy.lottery.domains.content.dao;

import java.util.List;
import com.fsy.lottery.domains.content.entity.LotteryOpenCode;

public abstract interface LotteryOpenCodeDao
{
  public abstract LotteryOpenCode get(String paramString1, String paramString2);
  
  public abstract boolean add(LotteryOpenCode paramLotteryOpenCode);
  
  public abstract List<LotteryOpenCode> getLatest(String paramString, int paramInt);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/LotteryOpenCodeDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */