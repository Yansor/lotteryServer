package com.fsy.lottery.domains.content.dao;

import com.fsy.lottery.domains.content.entity.LotteryCrawlerStatus;

public abstract interface LotteryCrawlerStatusDao
{
  public abstract LotteryCrawlerStatus get(String paramString);
  
  public abstract boolean update(String paramString1, String paramString2, String paramString3);
}


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/domains/content/dao/LotteryCrawlerStatusDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */