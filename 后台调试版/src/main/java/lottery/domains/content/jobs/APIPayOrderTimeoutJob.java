package lottery.domains.content.jobs;

import java.util.List;
import javautils.date.Moment;
import lottery.domains.content.biz.UserWithdrawLogService;
import lottery.domains.content.biz.UserWithdrawService;
import lottery.domains.content.entity.UserWithdraw;
import lottery.domains.content.entity.UserWithdrawLog;
//import lottery.domains.content.global.RemitStatusConstants.Status;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APIPayOrderTimeoutJob
{
  private static final Logger log = LoggerFactory.getLogger(APIPayOrderTimeoutJob.class);
  private static volatile boolean isRunning = false;
  private static final int[] PROCESSING_STATUSES = { 1, 3, -3 };
  @Autowired
  private UserWithdrawService uWithdrawService;
  @Autowired
  private UserWithdrawLogService userWithdrawLogService;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="0 0/10 * * * ?")
  public void scheduler()
  {
    // Byte code:
    //   0: ldc 1
    //   2: dup
    //   3: astore_1
    //   4: monitorenter
    //   5: getstatic 28	lottery/domains/content/jobs/APIPayOrderTimeoutJob:isRunning	Z
    //   8: ifeq +6 -> 14
    //   11: aload_1
    //   12: monitorexit
    //   13: return
    //   14: iconst_1
    //   15: putstatic 28	lottery/domains/content/jobs/APIPayOrderTimeoutJob:isRunning	Z
    //   18: aload_1
    //   19: monitorexit
    //   20: goto +6 -> 26
    //   23: aload_1
    //   24: monitorexit
    //   25: athrow
    //   26: aload_0
    //   27: invokespecial 43	lottery/domains/content/jobs/APIPayOrderTimeoutJob:process	()V
    //   30: goto +29 -> 59
    //   33: astore_1
    //   34: getstatic 26	lottery/domains/content/jobs/APIPayOrderTimeoutJob:log	Lorg/slf4j/Logger;
    //   37: ldc 46
    //   39: aload_1
    //   40: invokeinterface 48 3 0
    //   45: iconst_0
    //   46: putstatic 28	lottery/domains/content/jobs/APIPayOrderTimeoutJob:isRunning	Z
    //   49: goto +14 -> 63
    //   52: astore_2
    //   53: iconst_0
    //   54: putstatic 28	lottery/domains/content/jobs/APIPayOrderTimeoutJob:isRunning	Z
    //   57: aload_2
    //   58: athrow
    //   59: iconst_0
    //   60: putstatic 28	lottery/domains/content/jobs/APIPayOrderTimeoutJob:isRunning	Z
    //   63: return
    // Line number table:
    //   Java source line #39	-> byte code offset #0
    //   Java source line #40	-> byte code offset #5
    //   Java source line #42	-> byte code offset #11
    //   Java source line #44	-> byte code offset #14
    //   Java source line #39	-> byte code offset #18
    //   Java source line #49	-> byte code offset #26
    //   Java source line #50	-> byte code offset #30
    //   Java source line #51	-> byte code offset #34
    //   Java source line #53	-> byte code offset #45
    //   Java source line #52	-> byte code offset #52
    //   Java source line #53	-> byte code offset #53
    //   Java source line #54	-> byte code offset #57
    //   Java source line #53	-> byte code offset #59
    //   Java source line #55	-> byte code offset #63
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	APIPayOrderTimeoutJob
    //   3	21	1	Ljava/lang/Object;	Object
    //   33	7	1	e	Exception
    //   52	6	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   5	13	23	finally
    //   14	20	23	finally
    //   23	25	23	finally
    //   26	30	33	java/lang/Exception
    //   26	45	52	finally
  }
  
  private void process()
  {
    List<UserWithdraw> withdrawOrders = getWithdrawOrders();
    if (CollectionUtils.isEmpty(withdrawOrders)) {
      return;
    }
    for (UserWithdraw withdrawOrder : withdrawOrders)
    {
      withdrawOrder.setRemitStatus(-4);
      this.uWithdrawService.update(withdrawOrder);
      String content = "联系qq895686843";
      //RemitStatusConstants.Status.getTypeByContent(-4);
      String time = new Moment().toSimpleTime();
      String action = String.format("%s；操作人：系统", new Object[] { content });
      this.userWithdrawLogService.add(new UserWithdrawLog(withdrawOrder.getBillno(), withdrawOrder.getUserId(), -1, action, time));
    }
  }
  
  private List<UserWithdraw> getWithdrawOrders()
  {
    String sTime = new Moment().subtract(7, "days").toSimpleDate();
    String eTime = new Moment().subtract(1, "days").toSimpleDate();
    return this.uWithdrawService.listByRemitStatus(PROCESSING_STATUSES, true, sTime, eTime);
  }
}
