package lottery.domains.content.jobs;

import javautils.ObjectUtil;
import javautils.date.Moment;
import lottery.domains.content.biz.UserBalanceSnapshotService;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.UserBalanceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBalanceSnapshotJob
{
  private static final Logger log = LoggerFactory.getLogger(UserBalanceSnapshotJob.class);
  private static volatile boolean isRunning = false;
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserBalanceSnapshotService uBalanceSnapshotService;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="59 59 23 * * ?")
  public void syncOrder()
  {
    // Byte code:
    //   0: ldc 1
    //   2: dup
    //   3: astore_1
    //   4: monitorenter
    //   5: getstatic 26	lottery/domains/content/jobs/UserBalanceSnapshotJob:isRunning	Z
    //   8: ifeq +6 -> 14
    //   11: aload_1
    //   12: monitorexit
    //   13: return
    //   14: iconst_1
    //   15: putstatic 26	lottery/domains/content/jobs/UserBalanceSnapshotJob:isRunning	Z
    //   18: aload_1
    //   19: monitorexit
    //   20: goto +6 -> 26
    //   23: aload_1
    //   24: monitorexit
    //   25: athrow
    //   26: getstatic 24	lottery/domains/content/jobs/UserBalanceSnapshotJob:log	Lorg/slf4j/Logger;
    //   29: ldc 39
    //   31: invokeinterface 41 2 0
    //   36: aload_0
    //   37: invokespecial 47	lottery/domains/content/jobs/UserBalanceSnapshotJob:start	()V
    //   40: getstatic 24	lottery/domains/content/jobs/UserBalanceSnapshotJob:log	Lorg/slf4j/Logger;
    //   43: ldc 50
    //   45: invokeinterface 41 2 0
    //   50: goto +29 -> 79
    //   53: astore_1
    //   54: getstatic 24	lottery/domains/content/jobs/UserBalanceSnapshotJob:log	Lorg/slf4j/Logger;
    //   57: ldc 52
    //   59: aload_1
    //   60: invokeinterface 54 3 0
    //   65: iconst_0
    //   66: putstatic 26	lottery/domains/content/jobs/UserBalanceSnapshotJob:isRunning	Z
    //   69: goto +14 -> 83
    //   72: astore_2
    //   73: iconst_0
    //   74: putstatic 26	lottery/domains/content/jobs/UserBalanceSnapshotJob:isRunning	Z
    //   77: aload_2
    //   78: athrow
    //   79: iconst_0
    //   80: putstatic 26	lottery/domains/content/jobs/UserBalanceSnapshotJob:isRunning	Z
    //   83: return
    // Line number table:
    //   Java source line #30	-> byte code offset #0
    //   Java source line #31	-> byte code offset #5
    //   Java source line #33	-> byte code offset #11
    //   Java source line #35	-> byte code offset #14
    //   Java source line #30	-> byte code offset #18
    //   Java source line #38	-> byte code offset #26
    //   Java source line #41	-> byte code offset #36
    //   Java source line #43	-> byte code offset #40
    //   Java source line #44	-> byte code offset #50
    //   Java source line #45	-> byte code offset #54
    //   Java source line #47	-> byte code offset #65
    //   Java source line #46	-> byte code offset #72
    //   Java source line #47	-> byte code offset #73
    //   Java source line #48	-> byte code offset #77
    //   Java source line #47	-> byte code offset #79
    //   Java source line #49	-> byte code offset #83
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	this	UserBalanceSnapshotJob
    //   3	21	1	Ljava/lang/Object;	Object
    //   53	7	1	e	Exception
    //   72	6	2	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   5	13	23	finally
    //   14	20	23	finally
    //   23	25	23	finally
    //   26	50	53	java/lang/Exception
    //   26	65	72	finally
  }
  
  private void start()
  {
    Object[] balance = this.uDao.getTotalMoney();
    double totalMoney = ObjectUtil.toDouble(balance[0]);
    double lotteryMoney = ObjectUtil.toDouble(balance[1]);
    
    String time = new Moment().toSimpleTime();
    
    save(totalMoney, lotteryMoney, time);
  }
  
  private void save(double totalMoney, double lotteryMoney, String time)
  {
    UserBalanceSnapshot snapshot = new UserBalanceSnapshot();
    snapshot.setTotalMoney(totalMoney);
    snapshot.setLotteryMoney(lotteryMoney);
    snapshot.setTime(time);
    this.uBalanceSnapshotService.add(snapshot);
  }
}
