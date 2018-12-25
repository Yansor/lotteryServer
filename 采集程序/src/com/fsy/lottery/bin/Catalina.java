package com.fsy.lottery.bin;

import java.util.concurrent.ExecutorService;
import com.fsy.lottery.domains.content.global.JobConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Catalina
{
  ExecutorService newFixedThreadPool;
  @Autowired
  private JobConfig config;
  
  public void executeOpenCai() {}
  
  public void executeSSC() {}
  
  public void execute11x5() {}
  
  public void executeK3() {}
  
  public void executeOthers() {}
}