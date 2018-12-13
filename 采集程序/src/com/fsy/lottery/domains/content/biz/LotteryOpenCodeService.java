package com.fsy.lottery.domains.content.biz;

import com.fsy.lottery.domains.content.entity.LotteryOpenCode;

public abstract interface LotteryOpenCodeService
{
  public abstract void initLotteryOpenCode();
  
  public abstract boolean hasCaptured(String paramString1, String paramString2);
  
  public abstract boolean add(LotteryOpenCode paramLotteryOpenCode, boolean paramBoolean);
  
  public abstract LotteryOpenCode get(String paramString1, String paramString2);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/biz/LotteryOpenCodeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */