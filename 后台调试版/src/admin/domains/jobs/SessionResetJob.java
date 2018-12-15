package admin.domains.jobs;

import java.util.HashSet;
import java.util.Set;
import javautils.redis.JedisTemplate;
import lottery.domains.content.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionResetJob
{
  @Autowired
  private JedisTemplate jedisTemplate;
  @Autowired
  private UserDao uDao;
  private static boolean isRunning = false;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="0 0/1 * * * *")
  public void reset()
  {
    // Byte code:
    //   0: ldc 1
    //   2: dup
    //   3: astore_1
    //   4: monitorenter
    //   5: getstatic 16	admin/domains/jobs/SessionResetJob:isRunning	Z
    //   8: ifeq +6 -> 14
    //   11: aload_1
    //   12: monitorexit
    //   13: return
    //   14: iconst_1
    //   15: putstatic 16	admin/domains/jobs/SessionResetJob:isRunning	Z
    //   18: aload_1
    //   19: monitorexit
    //   20: goto +6 -> 26
    //   23: aload_1
    //   24: monitorexit
    //   25: athrow
    //   26: aload_0
    //   27: invokespecial 29	admin/domains/jobs/SessionResetJob:check	()V
    //   30: goto +10 -> 40
    //   33: astore_1
    //   34: iconst_0
    //   35: putstatic 16	admin/domains/jobs/SessionResetJob:isRunning	Z
    //   38: aload_1
    //   39: athrow
    //   40: iconst_0
    //   41: putstatic 16	admin/domains/jobs/SessionResetJob:isRunning	Z
    //   44: return
    // Line number table:
    //   Java source line #29	-> byte code offset #0
    //   Java source line #30	-> byte code offset #5
    //   Java source line #32	-> byte code offset #11
    //   Java source line #34	-> byte code offset #14
    //   Java source line #29	-> byte code offset #18
    //   Java source line #37	-> byte code offset #26
    //   Java source line #38	-> byte code offset #30
    //   Java source line #39	-> byte code offset #34
    //   Java source line #40	-> byte code offset #38
    //   Java source line #39	-> byte code offset #40
    //   Java source line #41	-> byte code offset #44
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	45	0	this	SessionResetJob
    //   3	36	1	Ljava/lang/Object;	Object
    // Exception table:
    //   from	to	target	type
    //   5	13	23	finally
    //   14	20	23	finally
    //   23	25	23	finally
    //   26	33	33	finally
  }
  
  private void check()
  {
    Set<String> sessionIds = this.jedisTemplate.keys("spring:session:sessions:*");
    if ((sessionIds == null) || (sessionIds.isEmpty()))
    {
      this.uDao.updateAllOffline();
      return;
    }
    Set<String> processIds = new HashSet(sessionIds.size());
    for (String sessionId : sessionIds) {
      processIds.add(sessionId.replaceAll("spring:session:sessions:", ""));
    }
    this.uDao.updateOnlineStatusNotIn(processIds);
  }
}
