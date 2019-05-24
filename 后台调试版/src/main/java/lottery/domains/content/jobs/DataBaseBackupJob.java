package lottery.domains.content.jobs;

import java.util.List;
import javautils.date.Moment;
import lottery.domains.content.dao.LotteryDao;
import lottery.domains.content.entity.Lottery;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseBackupJob
{
  private final Logger logger = LoggerFactory.getLogger(DataBaseBackupJob.class);
  boolean isRunning = false;
  @Autowired
  private SessionFactory sessionFactory;
  @Autowired
  private LotteryDao lotteryDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  private final int LIMIT_UNIT_COUNT = 500;
  private final int USER_BETS_REMAIN_MONTH = 1;
  private final int USER_RECHARGE_UNPAID_REMAIN_DAYS = 2;
  private final int USER_RECHARGE_REMAIN_MONTH = 3;
  private final int USER_HIGH_PRIZE_REMAIN_MONTH = 1;
  private final int USER_BILL_REMAIN_MONTH = 1;
  private final int USER_BILL_BACKUP_REMAIN_MONTH = 6;
  private final int OUT_LOTTERY_REMAIN_COUNT = 200;
  private final int SELF_LOTTERY_REMAIN_COUNT = 3000;
  private final int JSMMC_LOTTERY_REMAIN_COUNT = 100;
  private final int GAME_BETS_REMAIN_MONTH = 1;
  
  /* Error */
  @org.springframework.scheduling.annotation.Scheduled(cron="0 30 5 * * *")
  public void start()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 51	lottery/domains/content/jobs/DataBaseBackupJob:isRunning	Z
    //   4: ifne +157 -> 161
    //   7: aload_0
    //   8: iconst_1
    //   9: putfield 51	lottery/domains/content/jobs/DataBaseBackupJob:isRunning	Z
    //   12: aload_0
    //   13: invokespecial 83	lottery/domains/content/jobs/DataBaseBackupJob:backupUserBets	()V
    //   16: aload_0
    //   17: invokespecial 86	lottery/domains/content/jobs/DataBaseBackupJob:deleteDemoUserBetsOriginal	()V
    //   20: aload_0
    //   21: invokespecial 89	lottery/domains/content/jobs/DataBaseBackupJob:deleteUserBetsOriginal	()V
    //   24: aload_0
    //   25: invokespecial 92	lottery/domains/content/jobs/DataBaseBackupJob:backupGameBets	()V
    //   28: aload_0
    //   29: invokespecial 95	lottery/domains/content/jobs/DataBaseBackupJob:backupUserBill	()V
    //   32: aload_0
    //   33: invokespecial 98	lottery/domains/content/jobs/DataBaseBackupJob:deleteSysMessage	()V
    //   36: aload_0
    //   37: invokespecial 101	lottery/domains/content/jobs/DataBaseBackupJob:backupUserMessage	()V
    //   40: aload_0
    //   41: invokespecial 104	lottery/domains/content/jobs/DataBaseBackupJob:deleteOpenCode	()V
    //   44: aload_0
    //   45: invokespecial 107	lottery/domains/content/jobs/DataBaseBackupJob:backupAdminUserLog	()V
    //   48: aload_0
    //   49: invokespecial 110	lottery/domains/content/jobs/DataBaseBackupJob:backupAdminActionLog	()V
    //   52: aload_0
    //   53: invokespecial 113	lottery/domains/content/jobs/DataBaseBackupJob:backupUserLoginLog	()V
    //   56: aload_0
    //   57: invokespecial 116	lottery/domains/content/jobs/DataBaseBackupJob:backupUserActionLog	()V
    //   60: aload_0
    //   61: invokespecial 119	lottery/domains/content/jobs/DataBaseBackupJob:backupUserRechargeUnPaid	()V
    //   64: aload_0
    //   65: invokespecial 122	lottery/domains/content/jobs/DataBaseBackupJob:backupUserRecharge	()V
    //   68: aload_0
    //   69: invokespecial 125	lottery/domains/content/jobs/DataBaseBackupJob:backupUserWithdraw	()V
    //   72: aload_0
    //   73: invokespecial 128	lottery/domains/content/jobs/DataBaseBackupJob:backupUserTransfer	()V
    //   76: aload_0
    //   77: invokespecial 131	lottery/domains/content/jobs/DataBaseBackupJob:backupRedPacketRainBill	()V
    //   80: aload_0
    //   81: invokespecial 134	lottery/domains/content/jobs/DataBaseBackupJob:backupRedPacketRainTime	()V
    //   84: aload_0
    //   85: invokespecial 137	lottery/domains/content/jobs/DataBaseBackupJob:deleteUserHighPrize	()V
    //   88: aload_0
    //   89: invokespecial 140	lottery/domains/content/jobs/DataBaseBackupJob:deleteDemoUserHighPrize	()V
    //   92: aload_0
    //   93: invokespecial 143	lottery/domains/content/jobs/DataBaseBackupJob:backupDailySettleBill	()V
    //   96: aload_0
    //   97: invokespecial 146	lottery/domains/content/jobs/DataBaseBackupJob:backupDividendBill	()V
    //   100: aload_0
    //   101: invokespecial 149	lottery/domains/content/jobs/DataBaseBackupJob:backupGameWaterBill	()V
    //   104: aload_0
    //   105: invokespecial 152	lottery/domains/content/jobs/DataBaseBackupJob:backupGameDividendBill	()V
    //   108: aload_0
    //   109: invokespecial 155	lottery/domains/content/jobs/DataBaseBackupJob:backupUserMainReport	()V
    //   112: aload_0
    //   113: invokespecial 158	lottery/domains/content/jobs/DataBaseBackupJob:backupUserLotteryReport	()V
    //   116: aload_0
    //   117: invokespecial 161	lottery/domains/content/jobs/DataBaseBackupJob:backupUserLotteryDetailsReport	()V
    //   120: aload_0
    //   121: invokespecial 164	lottery/domains/content/jobs/DataBaseBackupJob:backupUserGameReport	()V
    //   124: goto +32 -> 156
    //   127: astore_1
    //   128: aload_0
    //   129: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   132: ldc -89
    //   134: aload_1
    //   135: invokeinterface 169 3 0
    //   140: aload_0
    //   141: iconst_0
    //   142: putfield 51	lottery/domains/content/jobs/DataBaseBackupJob:isRunning	Z
    //   145: goto +16 -> 161
    //   148: astore_2
    //   149: aload_0
    //   150: iconst_0
    //   151: putfield 51	lottery/domains/content/jobs/DataBaseBackupJob:isRunning	Z
    //   154: aload_2
    //   155: athrow
    //   156: aload_0
    //   157: iconst_0
    //   158: putfield 51	lottery/domains/content/jobs/DataBaseBackupJob:isRunning	Z
    //   161: return
    // Line number table:
    //   Java source line #37	-> byte code offset #0
    //   Java source line #38	-> byte code offset #7
    //   Java source line #41	-> byte code offset #12
    //   Java source line #44	-> byte code offset #16
    //   Java source line #47	-> byte code offset #20
    //   Java source line #50	-> byte code offset #24
    //   Java source line #53	-> byte code offset #28
    //   Java source line #56	-> byte code offset #32
    //   Java source line #59	-> byte code offset #36
    //   Java source line #62	-> byte code offset #40
    //   Java source line #65	-> byte code offset #44
    //   Java source line #68	-> byte code offset #48
    //   Java source line #71	-> byte code offset #52
    //   Java source line #74	-> byte code offset #56
    //   Java source line #77	-> byte code offset #60
    //   Java source line #80	-> byte code offset #64
    //   Java source line #83	-> byte code offset #68
    //   Java source line #86	-> byte code offset #72
    //   Java source line #89	-> byte code offset #76
    //   Java source line #92	-> byte code offset #80
    //   Java source line #95	-> byte code offset #84
    //   Java source line #98	-> byte code offset #88
    //   Java source line #101	-> byte code offset #92
    //   Java source line #104	-> byte code offset #96
    //   Java source line #107	-> byte code offset #100
    //   Java source line #110	-> byte code offset #104
    //   Java source line #113	-> byte code offset #108
    //   Java source line #116	-> byte code offset #112
    //   Java source line #119	-> byte code offset #116
    //   Java source line #122	-> byte code offset #120
    //   Java source line #123	-> byte code offset #124
    //   Java source line #124	-> byte code offset #128
    //   Java source line #126	-> byte code offset #140
    //   Java source line #125	-> byte code offset #148
    //   Java source line #126	-> byte code offset #149
    //   Java source line #127	-> byte code offset #154
    //   Java source line #126	-> byte code offset #156
    //   Java source line #129	-> byte code offset #161
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	162	0	this	DataBaseBackupJob
    //   127	8	1	e	Exception
    //   148	7	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   12	124	127	java/lang/Exception
    //   12	140	148	finally
  }
  
  private void backupUserBets()
  {
    String realRemainTime = new Moment().subtract(1, "months").toSimpleTime();
    String backupRemainTime = new Moment().subtract(3, "months").toSimpleTime();
    String realWhere = "b.time < '" + realRemainTime + "' AND b.status NOT IN (0)";
    String backupWhere = "b.time < '" + backupRemainTime + "'";
    
    String demRealRemainTime = new Moment().subtract(1, "days").toSimpleTime();
    String demorealWhere = "b.time < '" + demRealRemainTime + "' AND b.status NOT IN (0)";
    delFictitiousUserBackupBase("虚拟用户彩票投注记录", realWhere, realWhere, "user_bets");
    delDemoUserBackupBase("试玩用户彩票投注记录", demorealWhere, demorealWhere, "user_bets");
    
    commonUserBackupBase("彩票投注记录", realWhere, backupWhere, "user_bets");
  }
  
  private void backupUserBill()
  {
    commonNoDemoBackupByMonth("账单记录", 1, 3, "user_bill");
  }
  
  /* Error */
  private void deleteDemoUserBetsOriginal()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   4: ldc -8
    //   6: invokeinterface 250 2 0
    //   11: new 182	javautils/date/Moment
    //   14: dup
    //   15: invokespecial 184	javautils/date/Moment:<init>	()V
    //   18: iconst_1
    //   19: ldc -43
    //   21: invokevirtual 187	javautils/date/Moment:subtract	(ILjava/lang/String;)Ljavautils/date/Moment;
    //   24: invokevirtual 191	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
    //   27: astore_1
    //   28: aload_0
    //   29: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   32: invokeinterface 255 1 0
    //   37: astore_2
    //   38: aload_2
    //   39: invokeinterface 261 1 0
    //   44: pop
    //   45: new 195	java/lang/StringBuilder
    //   48: dup
    //   49: ldc_w 267
    //   52: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   55: aload_1
    //   56: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: ldc -45
    //   61: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: astore_3
    //   68: aload_2
    //   69: aload_3
    //   70: invokeinterface 269 2 0
    //   75: astore 4
    //   77: aload 4
    //   79: invokeinterface 273 1 0
    //   84: istore 5
    //   86: iload 5
    //   88: ifle +122 -> 210
    //   91: aload_0
    //   92: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   95: new 195	java/lang/StringBuilder
    //   98: dup
    //   99: ldc_w 279
    //   102: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   105: iload 5
    //   107: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   110: ldc_w 284
    //   113: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: invokeinterface 250 2 0
    //   124: aload_2
    //   125: invokeinterface 286 1 0
    //   130: invokeinterface 289 1 0
    //   135: goto +75 -> 210
    //   138: astore_3
    //   139: aload_0
    //   140: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   143: ldc_w 294
    //   146: invokeinterface 296 2 0
    //   151: aload_2
    //   152: invokeinterface 286 1 0
    //   157: invokeinterface 298 1 0
    //   162: aload_2
    //   163: ifnull +67 -> 230
    //   166: aload_2
    //   167: invokeinterface 301 1 0
    //   172: ifeq +58 -> 230
    //   175: aload_2
    //   176: invokeinterface 305 1 0
    //   181: pop
    //   182: goto +48 -> 230
    //   185: astore 6
    //   187: aload_2
    //   188: ifnull +19 -> 207
    //   191: aload_2
    //   192: invokeinterface 301 1 0
    //   197: ifeq +10 -> 207
    //   200: aload_2
    //   201: invokeinterface 305 1 0
    //   206: pop
    //   207: aload 6
    //   209: athrow
    //   210: aload_2
    //   211: ifnull +19 -> 230
    //   214: aload_2
    //   215: invokeinterface 301 1 0
    //   220: ifeq +10 -> 230
    //   223: aload_2
    //   224: invokeinterface 305 1 0
    //   229: pop
    //   230: return
    // Line number table:
    //   Java source line #185	-> byte code offset #0
    //   Java source line #186	-> byte code offset #11
    //   Java source line #189	-> byte code offset #28
    //   Java source line #190	-> byte code offset #38
    //   Java source line #192	-> byte code offset #45
    //   Java source line #194	-> byte code offset #55
    //   Java source line #192	-> byte code offset #64
    //   Java source line #196	-> byte code offset #68
    //   Java source line #197	-> byte code offset #77
    //   Java source line #198	-> byte code offset #86
    //   Java source line #199	-> byte code offset #91
    //   Java source line #200	-> byte code offset #124
    //   Java source line #202	-> byte code offset #135
    //   Java source line #203	-> byte code offset #139
    //   Java source line #204	-> byte code offset #151
    //   Java source line #206	-> byte code offset #162
    //   Java source line #207	-> byte code offset #175
    //   Java source line #205	-> byte code offset #185
    //   Java source line #206	-> byte code offset #187
    //   Java source line #207	-> byte code offset #200
    //   Java source line #209	-> byte code offset #207
    //   Java source line #206	-> byte code offset #210
    //   Java source line #207	-> byte code offset #223
    //   Java source line #210	-> byte code offset #230
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	231	0	this	DataBaseBackupJob
    //   27	29	1	time	String
    //   37	187	2	session	org.hibernate.Session
    //   67	3	3	deleteSql	String
    //   138	2	3	e	Exception
    //   75	3	4	deleteQuery	org.hibernate.SQLQuery
    //   84	22	5	deleteCount	int
    //   185	23	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   45	135	138	java/lang/Exception
    //   45	162	185	finally
  }
  
  /* Error */
  private void deleteUserBetsOriginal()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   4: ldc_w 318
    //   7: invokeinterface 250 2 0
    //   12: new 182	javautils/date/Moment
    //   15: dup
    //   16: invokespecial 184	javautils/date/Moment:<init>	()V
    //   19: bipush 31
    //   21: ldc -43
    //   23: invokevirtual 187	javautils/date/Moment:subtract	(ILjava/lang/String;)Ljavautils/date/Moment;
    //   26: invokevirtual 191	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
    //   29: astore_1
    //   30: ldc_w 320
    //   33: astore_2
    //   34: aload_0
    //   35: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   38: invokeinterface 255 1 0
    //   43: astore_3
    //   44: aload_3
    //   45: invokeinterface 261 1 0
    //   50: pop
    //   51: new 195	java/lang/StringBuilder
    //   54: dup
    //   55: ldc_w 322
    //   58: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   61: aload_1
    //   62: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: ldc -45
    //   67: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: astore 4
    //   75: aload_3
    //   76: aload 4
    //   78: invokeinterface 269 2 0
    //   83: astore 5
    //   85: aload 5
    //   87: invokeinterface 273 1 0
    //   92: istore 6
    //   94: iload 6
    //   96: ifle +123 -> 219
    //   99: aload_0
    //   100: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   103: new 195	java/lang/StringBuilder
    //   106: dup
    //   107: ldc_w 279
    //   110: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   113: iload 6
    //   115: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   118: ldc_w 284
    //   121: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   127: invokeinterface 250 2 0
    //   132: aload_3
    //   133: invokeinterface 286 1 0
    //   138: invokeinterface 289 1 0
    //   143: goto +76 -> 219
    //   146: astore 4
    //   148: aload_0
    //   149: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   152: ldc_w 294
    //   155: invokeinterface 296 2 0
    //   160: aload_3
    //   161: invokeinterface 286 1 0
    //   166: invokeinterface 298 1 0
    //   171: aload_3
    //   172: ifnull +67 -> 239
    //   175: aload_3
    //   176: invokeinterface 301 1 0
    //   181: ifeq +58 -> 239
    //   184: aload_3
    //   185: invokeinterface 305 1 0
    //   190: pop
    //   191: goto +48 -> 239
    //   194: astore 7
    //   196: aload_3
    //   197: ifnull +19 -> 216
    //   200: aload_3
    //   201: invokeinterface 301 1 0
    //   206: ifeq +10 -> 216
    //   209: aload_3
    //   210: invokeinterface 305 1 0
    //   215: pop
    //   216: aload 7
    //   218: athrow
    //   219: aload_3
    //   220: ifnull +19 -> 239
    //   223: aload_3
    //   224: invokeinterface 301 1 0
    //   229: ifeq +10 -> 239
    //   232: aload_3
    //   233: invokeinterface 305 1 0
    //   238: pop
    //   239: return
    // Line number table:
    //   Java source line #216	-> byte code offset #0
    //   Java source line #217	-> byte code offset #12
    //   Java source line #218	-> byte code offset #30
    //   Java source line #221	-> byte code offset #34
    //   Java source line #222	-> byte code offset #44
    //   Java source line #224	-> byte code offset #51
    //   Java source line #226	-> byte code offset #75
    //   Java source line #227	-> byte code offset #85
    //   Java source line #228	-> byte code offset #94
    //   Java source line #229	-> byte code offset #99
    //   Java source line #230	-> byte code offset #132
    //   Java source line #232	-> byte code offset #143
    //   Java source line #233	-> byte code offset #148
    //   Java source line #234	-> byte code offset #160
    //   Java source line #236	-> byte code offset #171
    //   Java source line #237	-> byte code offset #184
    //   Java source line #235	-> byte code offset #194
    //   Java source line #236	-> byte code offset #196
    //   Java source line #237	-> byte code offset #209
    //   Java source line #239	-> byte code offset #216
    //   Java source line #236	-> byte code offset #219
    //   Java source line #237	-> byte code offset #232
    //   Java source line #240	-> byte code offset #239
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	240	0	this	DataBaseBackupJob
    //   29	33	1	time	String
    //   33	2	2	oldDbTab	String
    //   43	190	3	session	org.hibernate.Session
    //   73	4	4	deleteSql	String
    //   146	3	4	e	Exception
    //   83	3	5	deleteQuery	org.hibernate.SQLQuery
    //   92	22	6	deleteCount	int
    //   194	23	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   51	143	146	java/lang/Exception
    //   51	171	194	finally
  }
  
  /* Error */
  private void deleteSysMessage()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   4: ldc_w 325
    //   7: invokeinterface 250 2 0
    //   12: new 182	javautils/date/Moment
    //   15: dup
    //   16: invokespecial 184	javautils/date/Moment:<init>	()V
    //   19: bipush 7
    //   21: ldc -43
    //   23: invokevirtual 187	javautils/date/Moment:subtract	(ILjava/lang/String;)Ljavautils/date/Moment;
    //   26: invokevirtual 191	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
    //   29: astore_1
    //   30: aload_0
    //   31: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   34: invokeinterface 255 1 0
    //   39: astore_2
    //   40: aload_2
    //   41: invokeinterface 261 1 0
    //   46: pop
    //   47: new 195	java/lang/StringBuilder
    //   50: dup
    //   51: ldc_w 327
    //   54: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   57: aload_1
    //   58: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: ldc -45
    //   63: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: astore_3
    //   70: aload_2
    //   71: aload_3
    //   72: invokeinterface 269 2 0
    //   77: astore 4
    //   79: aload 4
    //   81: invokeinterface 273 1 0
    //   86: istore 5
    //   88: iload 5
    //   90: ifle +50 -> 140
    //   93: aload_0
    //   94: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   97: new 195	java/lang/StringBuilder
    //   100: dup
    //   101: ldc_w 329
    //   104: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   107: iload 5
    //   109: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   112: ldc_w 331
    //   115: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   121: invokeinterface 250 2 0
    //   126: aload_2
    //   127: invokeinterface 286 1 0
    //   132: invokeinterface 289 1 0
    //   137: goto +90 -> 227
    //   140: aload_0
    //   141: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   144: ldc_w 333
    //   147: invokeinterface 250 2 0
    //   152: goto +75 -> 227
    //   155: astore_3
    //   156: aload_0
    //   157: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   160: ldc_w 335
    //   163: invokeinterface 250 2 0
    //   168: aload_2
    //   169: invokeinterface 286 1 0
    //   174: invokeinterface 298 1 0
    //   179: aload_2
    //   180: ifnull +67 -> 247
    //   183: aload_2
    //   184: invokeinterface 301 1 0
    //   189: ifeq +58 -> 247
    //   192: aload_2
    //   193: invokeinterface 305 1 0
    //   198: pop
    //   199: goto +48 -> 247
    //   202: astore 6
    //   204: aload_2
    //   205: ifnull +19 -> 224
    //   208: aload_2
    //   209: invokeinterface 301 1 0
    //   214: ifeq +10 -> 224
    //   217: aload_2
    //   218: invokeinterface 305 1 0
    //   223: pop
    //   224: aload 6
    //   226: athrow
    //   227: aload_2
    //   228: ifnull +19 -> 247
    //   231: aload_2
    //   232: invokeinterface 301 1 0
    //   237: ifeq +10 -> 247
    //   240: aload_2
    //   241: invokeinterface 305 1 0
    //   246: pop
    //   247: return
    // Line number table:
    //   Java source line #246	-> byte code offset #0
    //   Java source line #247	-> byte code offset #12
    //   Java source line #248	-> byte code offset #30
    //   Java source line #250	-> byte code offset #40
    //   Java source line #251	-> byte code offset #47
    //   Java source line #252	-> byte code offset #70
    //   Java source line #253	-> byte code offset #79
    //   Java source line #254	-> byte code offset #88
    //   Java source line #255	-> byte code offset #93
    //   Java source line #256	-> byte code offset #126
    //   Java source line #257	-> byte code offset #137
    //   Java source line #258	-> byte code offset #140
    //   Java source line #260	-> byte code offset #152
    //   Java source line #261	-> byte code offset #156
    //   Java source line #262	-> byte code offset #168
    //   Java source line #264	-> byte code offset #179
    //   Java source line #265	-> byte code offset #192
    //   Java source line #263	-> byte code offset #202
    //   Java source line #264	-> byte code offset #204
    //   Java source line #265	-> byte code offset #217
    //   Java source line #267	-> byte code offset #224
    //   Java source line #264	-> byte code offset #227
    //   Java source line #265	-> byte code offset #240
    //   Java source line #268	-> byte code offset #247
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	248	0	this	DataBaseBackupJob
    //   29	29	1	time	String
    //   39	202	2	session	org.hibernate.Session
    //   69	3	3	deleteSql	String
    //   155	2	3	e	Exception
    //   77	3	4	deleteQuery	org.hibernate.SQLQuery
    //   86	22	5	deleteCount	int
    //   202	23	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   40	152	155	java/lang/Exception
    //   40	179	202	finally
  }
  
  private void backupUserMessage()
  {
    commonBackupByMonth("用户消息", 3, 12, "user_message");
  }
  
  private void deleteOpenCode()
  {
    this.logger.info("开始删除开奖号码数据...");
    List<Lottery> lotteries = this.lotteryDao.listAll();
    for (Lottery lottery : lotteries) {
      if (lottery.getId() == 117)
      {
        delOpenCodeByJSMMC(lottery);
      }
      else
      {
        int remainCount = lottery.getSelf() == 1 ? 3000 : 200;
        if (lottery.getId() == 127) {
          remainCount = 400;
        }
        delOpenCodeByLottery(lottery, null, remainCount);
      }
    }
  }
  
  /* Error */
  private void delOpenCodeByLottery(Lottery lottery, Integer userId, int remainCount)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   4: invokeinterface 255 1 0
    //   9: astore 4
    //   11: aload 4
    //   13: invokeinterface 261 1 0
    //   18: pop
    //   19: ldc_w 392
    //   22: astore 5
    //   24: new 195	java/lang/StringBuilder
    //   27: dup
    //   28: ldc_w 394
    //   31: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   34: aload 5
    //   36: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: ldc_w 396
    //   42: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: aload_1
    //   46: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   49: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: ldc -45
    //   54: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   60: astore 6
    //   62: aload_2
    //   63: ifnull +30 -> 93
    //   66: new 195	java/lang/StringBuilder
    //   69: dup
    //   70: aload 6
    //   72: invokestatic 401	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   75: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   78: ldc_w 405
    //   81: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: aload_2
    //   85: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   88: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: astore 6
    //   93: aload 4
    //   95: aload 6
    //   97: invokeinterface 269 2 0
    //   102: astore 7
    //   104: aload 7
    //   106: invokeinterface 410 1 0
    //   111: astore 8
    //   113: aload 8
    //   115: invokestatic 413	javautils/ObjectUtil:toInt	(Ljava/lang/Object;)I
    //   118: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   121: astore 9
    //   123: aload 9
    //   125: ifnull +12 -> 137
    //   128: aload 9
    //   130: invokevirtual 424	java/lang/Integer:intValue	()I
    //   133: iload_3
    //   134: if_icmpgt +27 -> 161
    //   137: aload 4
    //   139: ifnull +21 -> 160
    //   142: aload 4
    //   144: invokeinterface 301 1 0
    //   149: ifeq +11 -> 160
    //   152: aload 4
    //   154: invokeinterface 305 1 0
    //   159: pop
    //   160: return
    //   161: new 195	java/lang/StringBuilder
    //   164: dup
    //   165: ldc_w 427
    //   168: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   171: aload 5
    //   173: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: ldc_w 396
    //   179: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: aload_1
    //   183: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   186: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   189: ldc -45
    //   191: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   197: astore 10
    //   199: aload_2
    //   200: ifnull +30 -> 230
    //   203: new 195	java/lang/StringBuilder
    //   206: dup
    //   207: aload 10
    //   209: invokestatic 401	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   212: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   215: ldc_w 405
    //   218: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   221: aload_2
    //   222: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   225: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   228: astore 10
    //   230: new 195	java/lang/StringBuilder
    //   233: dup
    //   234: aload 10
    //   236: invokestatic 401	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   239: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   242: ldc_w 429
    //   245: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: iload_3
    //   249: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   252: ldc_w 431
    //   255: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   261: astore 10
    //   263: aload 4
    //   265: aload 10
    //   267: invokeinterface 269 2 0
    //   272: astore 11
    //   274: aload 11
    //   276: invokeinterface 410 1 0
    //   281: astore 12
    //   283: aload 12
    //   285: invokestatic 413	javautils/ObjectUtil:toInt	(Ljava/lang/Object;)I
    //   288: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   291: astore 13
    //   293: aload 13
    //   295: ifnull +271 -> 566
    //   298: aload 13
    //   300: invokevirtual 424	java/lang/Integer:intValue	()I
    //   303: ifle +263 -> 566
    //   306: new 195	java/lang/StringBuilder
    //   309: dup
    //   310: ldc_w 433
    //   313: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   316: aload 5
    //   318: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: ldc_w 435
    //   324: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: aload 13
    //   329: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   332: ldc_w 437
    //   335: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: aload_1
    //   339: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   342: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   345: ldc -45
    //   347: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   353: astore 14
    //   355: aload_2
    //   356: ifnull +30 -> 386
    //   359: new 195	java/lang/StringBuilder
    //   362: dup
    //   363: aload 14
    //   365: invokestatic 401	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   368: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   371: ldc_w 405
    //   374: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: aload_2
    //   378: invokevirtual 407	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   381: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   384: astore 14
    //   386: aload 4
    //   388: aload 14
    //   390: invokeinterface 269 2 0
    //   395: astore 15
    //   397: aload 15
    //   399: invokeinterface 273 1 0
    //   404: istore 16
    //   406: iload 16
    //   408: ifle +55 -> 463
    //   411: aload_0
    //   412: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   415: new 195	java/lang/StringBuilder
    //   418: dup
    //   419: ldc_w 439
    //   422: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   425: iload 16
    //   427: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   430: ldc_w 284
    //   433: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   436: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   439: aload_1
    //   440: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   443: invokeinterface 441 3 0
    //   448: aload 4
    //   450: invokeinterface 286 1 0
    //   455: invokeinterface 289 1 0
    //   460: goto +106 -> 566
    //   463: aload_0
    //   464: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   467: ldc_w 444
    //   470: aload_1
    //   471: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   474: invokeinterface 441 3 0
    //   479: goto +87 -> 566
    //   482: astore 5
    //   484: aload_0
    //   485: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   488: ldc_w 446
    //   491: aload_1
    //   492: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   495: invokeinterface 448 3 0
    //   500: aload 4
    //   502: invokeinterface 286 1 0
    //   507: invokeinterface 298 1 0
    //   512: aload 4
    //   514: ifnull +75 -> 589
    //   517: aload 4
    //   519: invokeinterface 301 1 0
    //   524: ifeq +65 -> 589
    //   527: aload 4
    //   529: invokeinterface 305 1 0
    //   534: pop
    //   535: goto +54 -> 589
    //   538: astore 17
    //   540: aload 4
    //   542: ifnull +21 -> 563
    //   545: aload 4
    //   547: invokeinterface 301 1 0
    //   552: ifeq +11 -> 563
    //   555: aload 4
    //   557: invokeinterface 305 1 0
    //   562: pop
    //   563: aload 17
    //   565: athrow
    //   566: aload 4
    //   568: ifnull +21 -> 589
    //   571: aload 4
    //   573: invokeinterface 301 1 0
    //   578: ifeq +11 -> 589
    //   581: aload 4
    //   583: invokeinterface 305 1 0
    //   588: pop
    //   589: return
    // Line number table:
    //   Java source line #297	-> byte code offset #0
    //   Java source line #299	-> byte code offset #11
    //   Java source line #300	-> byte code offset #19
    //   Java source line #302	-> byte code offset #24
    //   Java source line #303	-> byte code offset #52
    //   Java source line #302	-> byte code offset #57
    //   Java source line #304	-> byte code offset #62
    //   Java source line #305	-> byte code offset #66
    //   Java source line #307	-> byte code offset #93
    //   Java source line #308	-> byte code offset #104
    //   Java source line #309	-> byte code offset #113
    //   Java source line #310	-> byte code offset #123
    //   Java source line #343	-> byte code offset #137
    //   Java source line #344	-> byte code offset #152
    //   Java source line #311	-> byte code offset #160
    //   Java source line #314	-> byte code offset #161
    //   Java source line #315	-> byte code offset #182
    //   Java source line #314	-> byte code offset #194
    //   Java source line #316	-> byte code offset #199
    //   Java source line #317	-> byte code offset #203
    //   Java source line #319	-> byte code offset #230
    //   Java source line #321	-> byte code offset #263
    //   Java source line #322	-> byte code offset #274
    //   Java source line #323	-> byte code offset #283
    //   Java source line #324	-> byte code offset #293
    //   Java source line #325	-> byte code offset #306
    //   Java source line #326	-> byte code offset #338
    //   Java source line #325	-> byte code offset #350
    //   Java source line #327	-> byte code offset #355
    //   Java source line #328	-> byte code offset #359
    //   Java source line #330	-> byte code offset #386
    //   Java source line #331	-> byte code offset #397
    //   Java source line #332	-> byte code offset #406
    //   Java source line #333	-> byte code offset #411
    //   Java source line #334	-> byte code offset #448
    //   Java source line #335	-> byte code offset #460
    //   Java source line #336	-> byte code offset #463
    //   Java source line #339	-> byte code offset #479
    //   Java source line #340	-> byte code offset #484
    //   Java source line #341	-> byte code offset #500
    //   Java source line #343	-> byte code offset #512
    //   Java source line #344	-> byte code offset #527
    //   Java source line #342	-> byte code offset #538
    //   Java source line #343	-> byte code offset #540
    //   Java source line #344	-> byte code offset #555
    //   Java source line #346	-> byte code offset #563
    //   Java source line #343	-> byte code offset #566
    //   Java source line #344	-> byte code offset #581
    //   Java source line #347	-> byte code offset #589
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	590	0	this	DataBaseBackupJob
    //   0	590	1	lottery	Lottery
    //   0	590	2	userId	Integer
    //   0	590	3	remainCount	int
    //   9	573	4	session	org.hibernate.Session
    //   22	295	5	oldDbTab	String
    //   482	3	5	e	Exception
    //   60	36	6	countSql	String
    //   102	3	7	countQuery	org.hibernate.SQLQuery
    //   111	3	8	countResult	Object
    //   121	8	9	count	Integer
    //   197	69	10	selectSql	String
    //   272	3	11	selectQuery	org.hibernate.SQLQuery
    //   281	3	12	selectResult	Object
    //   291	37	13	targetId	Integer
    //   353	36	14	deleteSql	String
    //   395	3	15	deleteQuery	org.hibernate.SQLQuery
    //   404	22	16	deleteCount	int
    //   538	26	17	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   11	137	482	java/lang/Exception
    //   161	479	482	java/lang/Exception
    //   11	137	538	finally
    //   161	512	538	finally
  }
  
  /* Error */
  private void delOpenCodeByJSMMC(Lottery lottery)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 368	lottery/domains/content/entity/Lottery:getId	()I
    //   4: bipush 117
    //   6: if_icmpeq +4 -> 10
    //   9: return
    //   10: aload_0
    //   11: invokespecial 461	lottery/domains/content/jobs/DataBaseBackupJob:getJSMMCUserIds	()Ljava/util/List;
    //   14: astore_2
    //   15: aload_2
    //   16: invokestatic 464	org/apache/commons/collections/CollectionUtils:isEmpty	(Ljava/util/Collection;)Z
    //   19: ifeq +4 -> 23
    //   22: return
    //   23: aload_2
    //   24: invokeinterface 354 1 0
    //   29: astore 4
    //   31: goto +22 -> 53
    //   34: aload 4
    //   36: invokeinterface 360 1 0
    //   41: checkcast 420	java/lang/Integer
    //   44: astore_3
    //   45: aload_0
    //   46: aload_1
    //   47: aload_3
    //   48: bipush 100
    //   50: invokespecial 378	lottery/domains/content/jobs/DataBaseBackupJob:delOpenCodeByLottery	(Llottery/domains/content/entity/Lottery;Ljava/lang/Integer;I)V
    //   53: aload 4
    //   55: invokeinterface 382 1 0
    //   60: ifne -26 -> 34
    //   63: aload_0
    //   64: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   67: invokeinterface 255 1 0
    //   72: astore_3
    //   73: aload_3
    //   74: invokeinterface 261 1 0
    //   79: pop
    //   80: ldc_w 392
    //   83: astore 4
    //   85: new 195	java/lang/StringBuilder
    //   88: dup
    //   89: ldc_w 433
    //   92: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   95: aload 4
    //   97: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: ldc_w 470
    //   103: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: new 182	javautils/date/Moment
    //   109: dup
    //   110: invokespecial 184	javautils/date/Moment:<init>	()V
    //   113: bipush 30
    //   115: ldc -43
    //   117: invokevirtual 187	javautils/date/Moment:subtract	(ILjava/lang/String;)Ljavautils/date/Moment;
    //   120: invokevirtual 191	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
    //   123: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: ldc -45
    //   128: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: astore 5
    //   136: aload_3
    //   137: aload 5
    //   139: invokeinterface 269 2 0
    //   144: astore 6
    //   146: aload 6
    //   148: invokeinterface 273 1 0
    //   153: istore 7
    //   155: iload 7
    //   157: ifle +50 -> 207
    //   160: aload_0
    //   161: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   164: new 195	java/lang/StringBuilder
    //   167: dup
    //   168: ldc_w 472
    //   171: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   174: iload 7
    //   176: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   179: ldc_w 284
    //   182: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   188: invokeinterface 250 2 0
    //   193: aload_3
    //   194: invokeinterface 286 1 0
    //   199: invokeinterface 289 1 0
    //   204: goto +95 -> 299
    //   207: aload_0
    //   208: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   211: ldc_w 444
    //   214: aload_1
    //   215: invokevirtual 398	lottery/domains/content/entity/Lottery:getShortName	()Ljava/lang/String;
    //   218: invokeinterface 441 3 0
    //   223: goto +76 -> 299
    //   226: astore 4
    //   228: aload_0
    //   229: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   232: ldc_w 474
    //   235: invokeinterface 296 2 0
    //   240: aload_3
    //   241: invokeinterface 286 1 0
    //   246: invokeinterface 298 1 0
    //   251: aload_3
    //   252: ifnull +67 -> 319
    //   255: aload_3
    //   256: invokeinterface 301 1 0
    //   261: ifeq +58 -> 319
    //   264: aload_3
    //   265: invokeinterface 305 1 0
    //   270: pop
    //   271: goto +48 -> 319
    //   274: astore 8
    //   276: aload_3
    //   277: ifnull +19 -> 296
    //   280: aload_3
    //   281: invokeinterface 301 1 0
    //   286: ifeq +10 -> 296
    //   289: aload_3
    //   290: invokeinterface 305 1 0
    //   295: pop
    //   296: aload 8
    //   298: athrow
    //   299: aload_3
    //   300: ifnull +19 -> 319
    //   303: aload_3
    //   304: invokeinterface 301 1 0
    //   309: ifeq +10 -> 319
    //   312: aload_3
    //   313: invokeinterface 305 1 0
    //   318: pop
    //   319: return
    // Line number table:
    //   Java source line #353	-> byte code offset #0
    //   Java source line #354	-> byte code offset #9
    //   Java source line #358	-> byte code offset #10
    //   Java source line #359	-> byte code offset #15
    //   Java source line #360	-> byte code offset #22
    //   Java source line #362	-> byte code offset #23
    //   Java source line #363	-> byte code offset #45
    //   Java source line #362	-> byte code offset #53
    //   Java source line #367	-> byte code offset #63
    //   Java source line #369	-> byte code offset #73
    //   Java source line #370	-> byte code offset #80
    //   Java source line #372	-> byte code offset #85
    //   Java source line #373	-> byte code offset #106
    //   Java source line #372	-> byte code offset #131
    //   Java source line #374	-> byte code offset #136
    //   Java source line #375	-> byte code offset #146
    //   Java source line #376	-> byte code offset #155
    //   Java source line #377	-> byte code offset #160
    //   Java source line #378	-> byte code offset #193
    //   Java source line #379	-> byte code offset #204
    //   Java source line #380	-> byte code offset #207
    //   Java source line #382	-> byte code offset #223
    //   Java source line #383	-> byte code offset #228
    //   Java source line #384	-> byte code offset #240
    //   Java source line #386	-> byte code offset #251
    //   Java source line #387	-> byte code offset #264
    //   Java source line #385	-> byte code offset #274
    //   Java source line #386	-> byte code offset #276
    //   Java source line #387	-> byte code offset #289
    //   Java source line #389	-> byte code offset #296
    //   Java source line #386	-> byte code offset #299
    //   Java source line #387	-> byte code offset #312
    //   Java source line #390	-> byte code offset #319
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	320	0	this	DataBaseBackupJob
    //   0	320	1	lottery	Lottery
    //   14	10	2	userIds	List<Integer>
    //   44	4	3	userId	Integer
    //   72	241	3	session	org.hibernate.Session
    //   29	25	4	localIterator	java.util.Iterator
    //   83	13	4	oldDbTab	String
    //   226	3	4	e	Exception
    //   134	4	5	deleteSql	String
    //   144	3	6	deleteQuery	org.hibernate.SQLQuery
    //   153	22	7	deleteCount	int
    //   274	23	8	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   73	223	226	java/lang/Exception
    //   73	251	274	finally
  }
  
  /* Error */
  private List<Integer> getJSMMCUserIds()
  {
    return null;
    // Byte code:
    //   0: new 480	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 482	java/util/ArrayList:<init>	()V
    //   7: astore_1
    //   8: aload_0
    //   9: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   12: invokeinterface 255 1 0
    //   17: astore_2
    //   18: aload_2
    //   19: ldc_w 483
    //   22: invokeinterface 269 2 0
    //   27: astore_3
    //   28: aload_3
    //   29: invokeinterface 485 1 0
    //   34: astore 4
    //   36: aload 4
    //   38: invokeinterface 354 1 0
    //   43: astore 6
    //   45: goto +27 -> 72
    //   48: aload 6
    //   50: invokeinterface 360 1 0
    //   55: astore 5
    //   57: aload_1
    //   58: aload 5
    //   60: invokevirtual 488	java/lang/Object:toString	()Ljava/lang/String;
    //   63: invokestatic 489	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   66: invokeinterface 492 2 0
    //   71: pop
    //   72: aload 6
    //   74: invokeinterface 382 1 0
    //   79: ifne -31 -> 48
    //   82: goto +65 -> 147
    //   85: astore_3
    //   86: aload_0
    //   87: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   90: ldc_w 496
    //   93: aload_3
    //   94: invokeinterface 169 3 0
    //   99: aload_2
    //   100: ifnull +67 -> 167
    //   103: aload_2
    //   104: invokeinterface 301 1 0
    //   109: ifeq +58 -> 167
    //   112: aload_2
    //   113: invokeinterface 305 1 0
    //   118: pop
    //   119: goto +48 -> 167
    //   122: astore 7
    //   124: aload_2
    //   125: ifnull +19 -> 144
    //   128: aload_2
    //   129: invokeinterface 301 1 0
    //   134: ifeq +10 -> 144
    //   137: aload_2
    //   138: invokeinterface 305 1 0
    //   143: pop
    //   144: aload 7
    //   146: athrow
    //   147: aload_2
    //   148: ifnull +19 -> 167
    //   151: aload_2
    //   152: invokeinterface 301 1 0
    //   157: ifeq +10 -> 167
    //   160: aload_2
    //   161: invokeinterface 305 1 0
    //   166: pop
    //   167: aload_1
    //   168: areturn
    // Line number table:
    //   Java source line #393	-> byte code offset #0
    //   Java source line #394	-> byte code offset #8
    //   Java source line #396	-> byte code offset #18
    //   Java source line #397	-> byte code offset #19
    //   Java source line #396	-> byte code offset #22
    //   Java source line #398	-> byte code offset #28
    //   Java source line #399	-> byte code offset #36
    //   Java source line #400	-> byte code offset #57
    //   Java source line #399	-> byte code offset #72
    //   Java source line #402	-> byte code offset #82
    //   Java source line #403	-> byte code offset #86
    //   Java source line #405	-> byte code offset #99
    //   Java source line #406	-> byte code offset #112
    //   Java source line #404	-> byte code offset #122
    //   Java source line #405	-> byte code offset #124
    //   Java source line #406	-> byte code offset #137
    //   Java source line #408	-> byte code offset #144
    //   Java source line #405	-> byte code offset #147
    //   Java source line #406	-> byte code offset #160
    //   Java source line #409	-> byte code offset #167
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	169	0	this	DataBaseBackupJob
    //   7	161	1	userIds	List<Integer>
    //   17	144	2	session	org.hibernate.Session
    //   27	2	3	selectQuery	org.hibernate.SQLQuery
    //   85	9	3	e	Exception
    //   34	3	4	list	List
    //   55	4	5	object	Object
    //   43	30	6	localIterator	java.util.Iterator
    //   122	23	7	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   18	82	85	java/lang/Exception
    //   18	99	122	finally
  }
  
  private void backupAdminUserLog()
  {
    commonBackupByMonth("后台操作日志", 6, 12, "admin_user_log");
  }
  
  private void backupAdminActionLog()
  {
    commonBackupByMonth("后台详细日志", 6, 12, "admin_user_action_log");
  }
  
  private void backupUserLoginLog()
  {
    commonBackupByMonth("前台用户登录日志", 12, 36, "user_login_log");
  }
  
  private void backupUserActionLog()
  {
    commonBackupByMonth("前台用户操作日志", 12, 36, "user_action_log");
  }
  
  private void backupGameBets()
  {
    commonBackupByMonth("用户第三方游戏投注记录", 3, 3, "game_bets");
  }
  
  /* Error */
  private void deleteUserHighPrize()
  {
    // Byte code:
    //   0: ldc_w 519
    //   3: astore_1
    //   4: new 182	javautils/date/Moment
    //   7: dup
    //   8: invokespecial 184	javautils/date/Moment:<init>	()V
    //   11: iconst_1
    //   12: ldc -71
    //   14: invokevirtual 187	javautils/date/Moment:subtract	(ILjava/lang/String;)Ljavautils/date/Moment;
    //   17: invokevirtual 191	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
    //   20: astore_2
    //   21: aload_0
    //   22: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   25: ldc_w 521
    //   28: aload_1
    //   29: invokeinterface 441 3 0
    //   34: aload_0
    //   35: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   38: invokeinterface 255 1 0
    //   43: astore_3
    //   44: aload_3
    //   45: invokeinterface 261 1 0
    //   50: pop
    //   51: new 195	java/lang/StringBuilder
    //   54: dup
    //   55: ldc_w 523
    //   58: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   61: aload_2
    //   62: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: ldc -45
    //   67: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: astore 4
    //   75: aload_3
    //   76: aload 4
    //   78: invokeinterface 269 2 0
    //   83: astore 5
    //   85: aload 5
    //   87: invokeinterface 273 1 0
    //   92: istore 6
    //   94: iload 6
    //   96: ifle +35 -> 131
    //   99: aload_0
    //   100: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   103: ldc_w 525
    //   106: aload_1
    //   107: iload 6
    //   109: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   112: invokeinterface 527 4 0
    //   117: aload_3
    //   118: invokeinterface 286 1 0
    //   123: invokeinterface 289 1 0
    //   128: goto +112 -> 240
    //   131: aload_0
    //   132: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   135: ldc_w 530
    //   138: aload_1
    //   139: invokeinterface 441 3 0
    //   144: goto +96 -> 240
    //   147: astore 4
    //   149: aload_0
    //   150: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   153: new 195	java/lang/StringBuilder
    //   156: dup
    //   157: ldc_w 532
    //   160: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   163: aload_1
    //   164: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: ldc_w 534
    //   170: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   176: invokeinterface 296 2 0
    //   181: aload_3
    //   182: invokeinterface 286 1 0
    //   187: invokeinterface 298 1 0
    //   192: aload_3
    //   193: ifnull +67 -> 260
    //   196: aload_3
    //   197: invokeinterface 301 1 0
    //   202: ifeq +58 -> 260
    //   205: aload_3
    //   206: invokeinterface 305 1 0
    //   211: pop
    //   212: goto +48 -> 260
    //   215: astore 7
    //   217: aload_3
    //   218: ifnull +19 -> 237
    //   221: aload_3
    //   222: invokeinterface 301 1 0
    //   227: ifeq +10 -> 237
    //   230: aload_3
    //   231: invokeinterface 305 1 0
    //   236: pop
    //   237: aload 7
    //   239: athrow
    //   240: aload_3
    //   241: ifnull +19 -> 260
    //   244: aload_3
    //   245: invokeinterface 301 1 0
    //   250: ifeq +10 -> 260
    //   253: aload_3
    //   254: invokeinterface 305 1 0
    //   259: pop
    //   260: return
    // Line number table:
    //   Java source line #451	-> byte code offset #0
    //   Java source line #453	-> byte code offset #4
    //   Java source line #454	-> byte code offset #21
    //   Java source line #456	-> byte code offset #34
    //   Java source line #458	-> byte code offset #44
    //   Java source line #460	-> byte code offset #51
    //   Java source line #461	-> byte code offset #75
    //   Java source line #462	-> byte code offset #85
    //   Java source line #463	-> byte code offset #94
    //   Java source line #464	-> byte code offset #99
    //   Java source line #465	-> byte code offset #117
    //   Java source line #466	-> byte code offset #128
    //   Java source line #467	-> byte code offset #131
    //   Java source line #469	-> byte code offset #144
    //   Java source line #470	-> byte code offset #149
    //   Java source line #471	-> byte code offset #181
    //   Java source line #473	-> byte code offset #192
    //   Java source line #474	-> byte code offset #205
    //   Java source line #472	-> byte code offset #215
    //   Java source line #473	-> byte code offset #217
    //   Java source line #474	-> byte code offset #230
    //   Java source line #476	-> byte code offset #237
    //   Java source line #473	-> byte code offset #240
    //   Java source line #474	-> byte code offset #253
    //   Java source line #477	-> byte code offset #260
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	261	0	this	DataBaseBackupJob
    //   3	161	1	name	String
    //   20	42	2	time	String
    //   43	211	3	session	org.hibernate.Session
    //   73	4	4	deleteSql	String
    //   147	3	4	e	Exception
    //   83	3	5	deleteQuery	org.hibernate.SQLQuery
    //   92	16	6	deleteCount	int
    //   215	23	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   44	144	147	java/lang/Exception
    //   44	192	215	finally
  }
  
  /* Error */
  private void deleteDemoUserHighPrize()
  {
    // Byte code:
    //   0: ldc_w 519
    //   3: astore_1
    //   4: new 182	javautils/date/Moment
    //   7: dup
    //   8: invokespecial 184	javautils/date/Moment:<init>	()V
    //   11: iconst_1
    //   12: ldc -43
    //   14: invokevirtual 187	javautils/date/Moment:subtract	(ILjava/lang/String;)Ljavautils/date/Moment;
    //   17: invokevirtual 191	javautils/date/Moment:toSimpleTime	()Ljava/lang/String;
    //   20: astore_2
    //   21: aload_0
    //   22: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   25: ldc_w 521
    //   28: aload_1
    //   29: invokeinterface 441 3 0
    //   34: aload_0
    //   35: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   38: invokeinterface 255 1 0
    //   43: astore_3
    //   44: aload_3
    //   45: invokeinterface 261 1 0
    //   50: pop
    //   51: new 195	java/lang/StringBuilder
    //   54: dup
    //   55: ldc_w 537
    //   58: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   61: aload_2
    //   62: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: ldc -45
    //   67: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: astore 4
    //   75: aload_3
    //   76: aload 4
    //   78: invokeinterface 269 2 0
    //   83: astore 5
    //   85: aload 5
    //   87: invokeinterface 273 1 0
    //   92: istore 6
    //   94: iload 6
    //   96: ifle +35 -> 131
    //   99: aload_0
    //   100: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   103: ldc_w 525
    //   106: aload_1
    //   107: iload 6
    //   109: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   112: invokeinterface 527 4 0
    //   117: aload_3
    //   118: invokeinterface 286 1 0
    //   123: invokeinterface 289 1 0
    //   128: goto +112 -> 240
    //   131: aload_0
    //   132: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   135: ldc_w 530
    //   138: aload_1
    //   139: invokeinterface 441 3 0
    //   144: goto +96 -> 240
    //   147: astore 4
    //   149: aload_0
    //   150: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   153: new 195	java/lang/StringBuilder
    //   156: dup
    //   157: ldc_w 532
    //   160: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   163: aload_1
    //   164: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: ldc_w 534
    //   170: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   173: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   176: invokeinterface 296 2 0
    //   181: aload_3
    //   182: invokeinterface 286 1 0
    //   187: invokeinterface 298 1 0
    //   192: aload_3
    //   193: ifnull +67 -> 260
    //   196: aload_3
    //   197: invokeinterface 301 1 0
    //   202: ifeq +58 -> 260
    //   205: aload_3
    //   206: invokeinterface 305 1 0
    //   211: pop
    //   212: goto +48 -> 260
    //   215: astore 7
    //   217: aload_3
    //   218: ifnull +19 -> 237
    //   221: aload_3
    //   222: invokeinterface 301 1 0
    //   227: ifeq +10 -> 237
    //   230: aload_3
    //   231: invokeinterface 305 1 0
    //   236: pop
    //   237: aload 7
    //   239: athrow
    //   240: aload_3
    //   241: ifnull +19 -> 260
    //   244: aload_3
    //   245: invokeinterface 301 1 0
    //   250: ifeq +10 -> 260
    //   253: aload_3
    //   254: invokeinterface 305 1 0
    //   259: pop
    //   260: return
    // Line number table:
    //   Java source line #482	-> byte code offset #0
    //   Java source line #484	-> byte code offset #4
    //   Java source line #485	-> byte code offset #21
    //   Java source line #487	-> byte code offset #34
    //   Java source line #489	-> byte code offset #44
    //   Java source line #490	-> byte code offset #51
    //   Java source line #491	-> byte code offset #75
    //   Java source line #492	-> byte code offset #85
    //   Java source line #493	-> byte code offset #94
    //   Java source line #494	-> byte code offset #99
    //   Java source line #495	-> byte code offset #117
    //   Java source line #496	-> byte code offset #128
    //   Java source line #497	-> byte code offset #131
    //   Java source line #499	-> byte code offset #144
    //   Java source line #500	-> byte code offset #149
    //   Java source line #501	-> byte code offset #181
    //   Java source line #503	-> byte code offset #192
    //   Java source line #504	-> byte code offset #205
    //   Java source line #502	-> byte code offset #215
    //   Java source line #503	-> byte code offset #217
    //   Java source line #504	-> byte code offset #230
    //   Java source line #506	-> byte code offset #237
    //   Java source line #503	-> byte code offset #240
    //   Java source line #504	-> byte code offset #253
    //   Java source line #507	-> byte code offset #260
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	261	0	this	DataBaseBackupJob
    //   3	161	1	name	String
    //   20	42	2	time	String
    //   43	211	3	session	org.hibernate.Session
    //   73	4	4	deleteSql	String
    //   147	3	4	e	Exception
    //   83	3	5	deleteQuery	org.hibernate.SQLQuery
    //   92	16	6	deleteCount	int
    //   215	23	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   44	144	147	java/lang/Exception
    //   44	192	215	finally
  }
  
  private void backupUserRechargeUnPaid()
  {
    String realRemainTime = new Moment().subtract(7, "days").toSimpleTime();
    String realWhere = "`time` < '" + realRemainTime + "' AND `status`=0";
    String backupWhere = null;
    
    commonBackupBase("用户未支付充值记录", realWhere, backupWhere, "user_recharge");
  }
  
  private void backupUserRecharge()
  {
    commonBackupByMonth("用户充值记录", 3, 12, "user_recharge");
  }
  
  private void backupUserWithdraw()
  {
    commonBackupByMonth("用户取现记录", 3, 12, "user_withdraw");
  }
  
  private void backupUserTransfer()
  {
    commonBackupByMonth("用户转账记录", 1, 12, "user_transfers");
  }
  
  private void backupRedPacketRainBill()
  {
    commonBackupByMonth("红包雨账单", 1, 12, "activity_red_packet_rain_bill");
  }
  
  private void backupRedPacketRainTime()
  {
    commonBackupByMonth("红包雨时间配置", 1, 12, "activity_red_packet_rain_time", "end_time");
  }
  
  private void backupDailySettleBill()
  {
    commonBackupByMonth("彩票日结账单", 2, 12, "user_daily_settle_bill", "settle_time");
  }
  
  private void backupDividendBill()
  {
    commonBackupByMonth("彩票分红账单", 2, 12, "user_dividend_bill", "settle_time");
  }
  
  private void backupGameWaterBill()
  {
    commonBackupByMonth("老虎机真人体育返水账单", 2, 12, "user_game_water_bill", "settle_time");
  }
  
  private void backupGameDividendBill()
  {
    commonBackupByMonth("老虎机真人体育分红账单", 2, 12, "user_game_dividend_bill", "settle_time");
  }
  
  private void backupUserMainReport()
  {
    commonBackupByMonth("主账户报表", 2, 12, "user_main_report");
  }
  
  private void backupUserLotteryReport()
  {
    commonBackupByMonth("彩票报表", 2, 12, "user_lottery_report");
  }
  
  private void backupUserLotteryDetailsReport()
  {
    commonBackupByMonth("彩票详情报表", 2, 12, "user_lottery_details_report");
  }
  
  private void backupUserGameReport()
  {
    commonBackupByMonth("老虎机真人体育报表", 2, 12, "user_game_report");
  }
  
  private void commonNoDemoBackupByMonth(String name, int realRemainMonth, int backupRemainMonth, String table)
  {
    String realRemainTime = new Moment().subtract(realRemainMonth, "months").toSimpleDate();
    String backupRemainTime = new Moment().subtract(backupRemainMonth, "months").toSimpleDate();
    
    String demRealRemainTime = new Moment().subtract(1, "days").toSimpleTime();
    String demorealWhere = "b.time < '" + demRealRemainTime + "' ";
    delDemoUserBackupBase(name, demorealWhere, null, table);
    delFictitiousUserBackupBase(name, realRemainTime, null, table);
    
    commonNoDemoBackupByTime(name, realRemainTime, backupRemainTime, table, "b.time");
  }
  
  private void commonBackupByMonth(String name, int realRemainMonth, int backupRemainMonth, String table)
  {
    String realRemainTime = new Moment().subtract(realRemainMonth, "months").toSimpleDate();
    String backupRemainTime = new Moment().subtract(backupRemainMonth, "months").toSimpleDate();
    
    commonBackupByTime(name, realRemainTime, backupRemainTime, table, "time");
  }
  
  private void commonBackupByMonth(String name, int realRemainMonth, int backupRemainMonth, String table, String timeField)
  {
    String realRemainTime = new Moment().subtract(realRemainMonth, "months").toSimpleDate();
    String backupRemainTime = new Moment().subtract(backupRemainMonth, "months").toSimpleDate();
    
    commonBackupByTime(name, realRemainTime, backupRemainTime, table, timeField);
  }
  
  private void commonNoDemoBackupByTime(String name, String realRemainTime, String backupRemainTime, String table, String timeField)
  {
    String realWhere = timeField + " < '" + realRemainTime + "'";
    String backupWhere = timeField + " < '" + backupRemainTime + "'";
    commonUserBackupBase(name, realWhere, backupWhere, table);
  }
  
  private void commonBackupByTime(String name, String realRemainTime, String backupRemainTime, String table, String timeField)
  {
    String realWhere = "`" + timeField + "` < '" + realRemainTime + "'";
    String backupWhere = "`" + timeField + "` < '" + backupRemainTime + "'";
    
    commonBackupBase(name, realWhere, backupWhere, table);
  }
  
  /* Error */
  private void commonBackupBase(String name, String realWhere, String backupWhere, String table)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: new 195	java/lang/StringBuilder
    //   6: dup
    //   7: ldc_w 633
    //   10: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   13: aload 4
    //   15: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc_w 629
    //   21: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: astore 6
    //   29: new 195	java/lang/StringBuilder
    //   32: dup
    //   33: ldc_w 635
    //   36: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   39: aload 4
    //   41: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: ldc_w 629
    //   47: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   53: astore 7
    //   55: aload_0
    //   56: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   59: ldc_w 637
    //   62: aload_2
    //   63: aload_1
    //   64: invokeinterface 527 4 0
    //   69: aload_0
    //   70: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   73: invokeinterface 255 1 0
    //   78: astore 8
    //   80: aload 8
    //   82: invokeinterface 261 1 0
    //   87: pop
    //   88: new 195	java/lang/StringBuilder
    //   91: dup
    //   92: ldc_w 639
    //   95: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   98: aload 6
    //   100: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   103: ldc_w 641
    //   106: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: aload_2
    //   110: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: ldc_w 643
    //   116: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: sipush 500
    //   122: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   125: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   128: astore 9
    //   130: aload 8
    //   132: aload 9
    //   134: invokeinterface 269 2 0
    //   139: astore 10
    //   141: aload 10
    //   143: invokeinterface 485 1 0
    //   148: astore 11
    //   150: aload 11
    //   152: ifnonnull +43 -> 195
    //   155: iconst_1
    //   156: istore 5
    //   158: aload_0
    //   159: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   162: ldc_w 645
    //   165: aload_1
    //   166: invokeinterface 441 3 0
    //   171: aload 8
    //   173: ifnull +21 -> 194
    //   176: aload 8
    //   178: invokeinterface 301 1 0
    //   183: ifeq +11 -> 194
    //   186: aload 8
    //   188: invokeinterface 305 1 0
    //   193: pop
    //   194: return
    //   195: aload 11
    //   197: checkcast 480	java/util/ArrayList
    //   200: astore 12
    //   202: aload 12
    //   204: ifnull +11 -> 215
    //   207: aload 12
    //   209: invokevirtual 647	java/util/ArrayList:size	()I
    //   212: ifgt +43 -> 255
    //   215: iconst_1
    //   216: istore 5
    //   218: aload_0
    //   219: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   222: ldc_w 650
    //   225: aload_1
    //   226: invokeinterface 441 3 0
    //   231: aload 8
    //   233: ifnull +21 -> 254
    //   236: aload 8
    //   238: invokeinterface 301 1 0
    //   243: ifeq +11 -> 254
    //   246: aload 8
    //   248: invokeinterface 305 1 0
    //   253: pop
    //   254: return
    //   255: new 195	java/lang/StringBuilder
    //   258: dup
    //   259: ldc_w 652
    //   262: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   265: aload 7
    //   267: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: ldc_w 654
    //   273: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: aload 6
    //   278: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: ldc_w 656
    //   284: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: aload 12
    //   289: invokestatic 658	javautils/array/ArrayUtils:toString	(Ljava/util/List;)Ljava/lang/String;
    //   292: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: ldc_w 663
    //   298: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   304: astore 13
    //   306: new 195	java/lang/StringBuilder
    //   309: dup
    //   310: ldc_w 433
    //   313: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   316: aload 6
    //   318: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: ldc_w 656
    //   324: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: aload 12
    //   329: invokestatic 658	javautils/array/ArrayUtils:toString	(Ljava/util/List;)Ljava/lang/String;
    //   332: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   335: ldc_w 665
    //   338: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   341: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   344: astore 14
    //   346: aload 8
    //   348: aload 13
    //   350: invokeinterface 269 2 0
    //   355: astore 15
    //   357: aload 15
    //   359: invokeinterface 273 1 0
    //   364: istore 16
    //   366: iload 16
    //   368: ifle +61 -> 429
    //   371: aload_0
    //   372: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   375: ldc_w 667
    //   378: aload_1
    //   379: iload 16
    //   381: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   384: invokeinterface 527 4 0
    //   389: aload 8
    //   391: aload 14
    //   393: invokeinterface 269 2 0
    //   398: astore 17
    //   400: aload 17
    //   402: invokeinterface 273 1 0
    //   407: istore 18
    //   409: iload 18
    //   411: ifle +140 -> 551
    //   414: aload 8
    //   416: invokeinterface 286 1 0
    //   421: invokeinterface 289 1 0
    //   426: goto +125 -> 551
    //   429: iconst_1
    //   430: istore 5
    //   432: aload_0
    //   433: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   436: ldc_w 645
    //   439: aload_1
    //   440: invokeinterface 441 3 0
    //   445: goto +106 -> 551
    //   448: astore 9
    //   450: aload_0
    //   451: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   454: new 195	java/lang/StringBuilder
    //   457: dup
    //   458: ldc_w 669
    //   461: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   464: aload_1
    //   465: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   468: ldc_w 671
    //   471: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   474: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   477: invokeinterface 296 2 0
    //   482: aload 8
    //   484: invokeinterface 286 1 0
    //   489: invokeinterface 298 1 0
    //   494: iconst_1
    //   495: istore 5
    //   497: aload 8
    //   499: ifnull +75 -> 574
    //   502: aload 8
    //   504: invokeinterface 301 1 0
    //   509: ifeq +65 -> 574
    //   512: aload 8
    //   514: invokeinterface 305 1 0
    //   519: pop
    //   520: goto +54 -> 574
    //   523: astore 19
    //   525: aload 8
    //   527: ifnull +21 -> 548
    //   530: aload 8
    //   532: invokeinterface 301 1 0
    //   537: ifeq +11 -> 548
    //   540: aload 8
    //   542: invokeinterface 305 1 0
    //   547: pop
    //   548: aload 19
    //   550: athrow
    //   551: aload 8
    //   553: ifnull +21 -> 574
    //   556: aload 8
    //   558: invokeinterface 301 1 0
    //   563: ifeq +11 -> 574
    //   566: aload 8
    //   568: invokeinterface 305 1 0
    //   573: pop
    //   574: iload 5
    //   576: ifeq -507 -> 69
    //   579: aload_3
    //   580: invokestatic 673	javautils/StringUtil:isNotNull	(Ljava/lang/String;)Z
    //   583: ifeq +268 -> 851
    //   586: aload_0
    //   587: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   590: ldc_w 679
    //   593: aload_3
    //   594: aload_1
    //   595: invokeinterface 527 4 0
    //   600: aload_0
    //   601: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   604: invokeinterface 255 1 0
    //   609: astore 8
    //   611: aload 8
    //   613: invokeinterface 261 1 0
    //   618: pop
    //   619: new 195	java/lang/StringBuilder
    //   622: dup
    //   623: ldc_w 433
    //   626: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   629: aload 7
    //   631: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   634: ldc_w 641
    //   637: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   640: aload_3
    //   641: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   647: astore 9
    //   649: aload 8
    //   651: aload 9
    //   653: invokeinterface 269 2 0
    //   658: astore 10
    //   660: aload 10
    //   662: invokeinterface 273 1 0
    //   667: istore 11
    //   669: iload 11
    //   671: ifle +36 -> 707
    //   674: aload_0
    //   675: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   678: ldc_w 681
    //   681: aload_1
    //   682: iload 11
    //   684: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   687: invokeinterface 527 4 0
    //   692: aload 8
    //   694: invokeinterface 286 1 0
    //   699: invokeinterface 289 1 0
    //   704: goto +124 -> 828
    //   707: aload_0
    //   708: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   711: ldc_w 683
    //   714: aload_1
    //   715: iload 11
    //   717: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   720: invokeinterface 527 4 0
    //   725: goto +103 -> 828
    //   728: astore 9
    //   730: aload_0
    //   731: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   734: new 195	java/lang/StringBuilder
    //   737: dup
    //   738: ldc_w 532
    //   741: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   744: aload_1
    //   745: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   748: ldc_w 685
    //   751: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   754: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   757: invokeinterface 296 2 0
    //   762: aload 8
    //   764: invokeinterface 286 1 0
    //   769: invokeinterface 298 1 0
    //   774: aload 8
    //   776: ifnull +75 -> 851
    //   779: aload 8
    //   781: invokeinterface 301 1 0
    //   786: ifeq +65 -> 851
    //   789: aload 8
    //   791: invokeinterface 305 1 0
    //   796: pop
    //   797: goto +54 -> 851
    //   800: astore 12
    //   802: aload 8
    //   804: ifnull +21 -> 825
    //   807: aload 8
    //   809: invokeinterface 301 1 0
    //   814: ifeq +11 -> 825
    //   817: aload 8
    //   819: invokeinterface 305 1 0
    //   824: pop
    //   825: aload 12
    //   827: athrow
    //   828: aload 8
    //   830: ifnull +21 -> 851
    //   833: aload 8
    //   835: invokeinterface 301 1 0
    //   840: ifeq +11 -> 851
    //   843: aload 8
    //   845: invokeinterface 305 1 0
    //   850: pop
    //   851: return
    // Line number table:
    //   Java source line #656	-> byte code offset #0
    //   Java source line #657	-> byte code offset #3
    //   Java source line #658	-> byte code offset #29
    //   Java source line #661	-> byte code offset #55
    //   Java source line #663	-> byte code offset #69
    //   Java source line #664	-> byte code offset #80
    //   Java source line #666	-> byte code offset #88
    //   Java source line #667	-> byte code offset #119
    //   Java source line #666	-> byte code offset #125
    //   Java source line #668	-> byte code offset #130
    //   Java source line #669	-> byte code offset #141
    //   Java source line #670	-> byte code offset #150
    //   Java source line #671	-> byte code offset #155
    //   Java source line #672	-> byte code offset #158
    //   Java source line #706	-> byte code offset #171
    //   Java source line #707	-> byte code offset #186
    //   Java source line #673	-> byte code offset #194
    //   Java source line #676	-> byte code offset #195
    //   Java source line #677	-> byte code offset #202
    //   Java source line #678	-> byte code offset #215
    //   Java source line #679	-> byte code offset #218
    //   Java source line #706	-> byte code offset #231
    //   Java source line #707	-> byte code offset #246
    //   Java source line #680	-> byte code offset #254
    //   Java source line #683	-> byte code offset #255
    //   Java source line #684	-> byte code offset #287
    //   Java source line #683	-> byte code offset #301
    //   Java source line #685	-> byte code offset #306
    //   Java source line #686	-> byte code offset #327
    //   Java source line #685	-> byte code offset #341
    //   Java source line #688	-> byte code offset #346
    //   Java source line #689	-> byte code offset #357
    //   Java source line #690	-> byte code offset #366
    //   Java source line #691	-> byte code offset #371
    //   Java source line #692	-> byte code offset #389
    //   Java source line #693	-> byte code offset #400
    //   Java source line #694	-> byte code offset #409
    //   Java source line #695	-> byte code offset #414
    //   Java source line #697	-> byte code offset #426
    //   Java source line #698	-> byte code offset #429
    //   Java source line #699	-> byte code offset #432
    //   Java source line #701	-> byte code offset #445
    //   Java source line #702	-> byte code offset #450
    //   Java source line #703	-> byte code offset #482
    //   Java source line #704	-> byte code offset #494
    //   Java source line #706	-> byte code offset #497
    //   Java source line #707	-> byte code offset #512
    //   Java source line #705	-> byte code offset #523
    //   Java source line #706	-> byte code offset #525
    //   Java source line #707	-> byte code offset #540
    //   Java source line #709	-> byte code offset #548
    //   Java source line #706	-> byte code offset #551
    //   Java source line #707	-> byte code offset #566
    //   Java source line #710	-> byte code offset #574
    //   Java source line #713	-> byte code offset #579
    //   Java source line #714	-> byte code offset #586
    //   Java source line #715	-> byte code offset #600
    //   Java source line #716	-> byte code offset #611
    //   Java source line #718	-> byte code offset #619
    //   Java source line #720	-> byte code offset #649
    //   Java source line #721	-> byte code offset #660
    //   Java source line #722	-> byte code offset #669
    //   Java source line #723	-> byte code offset #674
    //   Java source line #724	-> byte code offset #692
    //   Java source line #725	-> byte code offset #704
    //   Java source line #726	-> byte code offset #707
    //   Java source line #728	-> byte code offset #725
    //   Java source line #729	-> byte code offset #730
    //   Java source line #730	-> byte code offset #762
    //   Java source line #732	-> byte code offset #774
    //   Java source line #733	-> byte code offset #789
    //   Java source line #731	-> byte code offset #800
    //   Java source line #732	-> byte code offset #802
    //   Java source line #733	-> byte code offset #817
    //   Java source line #735	-> byte code offset #825
    //   Java source line #732	-> byte code offset #828
    //   Java source line #733	-> byte code offset #843
    //   Java source line #737	-> byte code offset #851
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	852	0	this	DataBaseBackupJob
    //   0	852	1	name	String
    //   0	852	2	realWhere	String
    //   0	852	3	backupWhere	String
    //   0	852	4	table	String
    //   1	574	5	migrateDone	boolean
    //   27	290	6	oldDbTab	String
    //   53	577	7	newDbTab	String
    //   78	489	8	session	org.hibernate.Session
    //   609	235	8	session	org.hibernate.Session
    //   128	5	9	idSql	String
    //   448	3	9	e	Exception
    //   647	5	9	deleteSql	String
    //   728	3	9	e	Exception
    //   139	3	10	idQuery	org.hibernate.SQLQuery
    //   658	3	10	deleteQuery	org.hibernate.SQLQuery
    //   148	48	11	idResult	Object
    //   667	49	11	deleteCount	int
    //   200	626	12	ids	java.util.ArrayList<Integer>
    //   304	45	13	insertSql	String
    //   344	48	14	deleteSql	String
    //   355	3	15	insertQuery	org.hibernate.SQLQuery
    //   364	16	16	insertCount	int
    //   398	3	17	deleteQuery	org.hibernate.SQLQuery
    //   407	3	18	deleteCount	int
    //   523	26	19	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   88	171	448	java/lang/Exception
    //   195	231	448	java/lang/Exception
    //   255	445	448	java/lang/Exception
    //   88	171	523	finally
    //   195	231	523	finally
    //   255	497	523	finally
    //   619	725	728	java/lang/Exception
    //   619	774	800	finally
  }
  
  /* Error */
  private void delFictitiousUserBackupBase(String name, String realWhere, String backupWhere, String table)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: new 195	java/lang/StringBuilder
    //   6: dup
    //   7: ldc_w 633
    //   10: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   13: aload 4
    //   15: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc_w 629
    //   21: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: astore 6
    //   29: ldc_w 698
    //   32: astore 7
    //   34: new 195	java/lang/StringBuilder
    //   37: dup
    //   38: ldc_w 633
    //   41: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   44: aload 7
    //   46: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc_w 629
    //   52: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: astore 8
    //   60: aload_0
    //   61: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   64: ldc_w 700
    //   67: aload_2
    //   68: aload_1
    //   69: invokeinterface 527 4 0
    //   74: aload_0
    //   75: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   78: invokeinterface 255 1 0
    //   83: astore 9
    //   85: aload 9
    //   87: invokeinterface 261 1 0
    //   92: pop
    //   93: new 195	java/lang/StringBuilder
    //   96: dup
    //   97: ldc_w 702
    //   100: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   103: aload 6
    //   105: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: ldc_w 704
    //   111: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: aload 8
    //   116: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: ldc_w 706
    //   122: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: aload_2
    //   126: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: ldc_w 708
    //   132: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: sipush 500
    //   138: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   141: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: astore 10
    //   146: aload 9
    //   148: aload 10
    //   150: invokeinterface 269 2 0
    //   155: astore 11
    //   157: aload 11
    //   159: invokeinterface 485 1 0
    //   164: astore 12
    //   166: aload 12
    //   168: ifnonnull +43 -> 211
    //   171: iconst_1
    //   172: istore 5
    //   174: aload_0
    //   175: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   178: ldc_w 710
    //   181: aload_1
    //   182: invokeinterface 441 3 0
    //   187: aload 9
    //   189: ifnull +21 -> 210
    //   192: aload 9
    //   194: invokeinterface 301 1 0
    //   199: ifeq +11 -> 210
    //   202: aload 9
    //   204: invokeinterface 305 1 0
    //   209: pop
    //   210: return
    //   211: aload 12
    //   213: checkcast 480	java/util/ArrayList
    //   216: astore 13
    //   218: aload 13
    //   220: ifnull +11 -> 231
    //   223: aload 13
    //   225: invokevirtual 647	java/util/ArrayList:size	()I
    //   228: ifgt +43 -> 271
    //   231: iconst_1
    //   232: istore 5
    //   234: aload_0
    //   235: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   238: ldc_w 712
    //   241: aload_1
    //   242: invokeinterface 441 3 0
    //   247: aload 9
    //   249: ifnull +21 -> 270
    //   252: aload 9
    //   254: invokeinterface 301 1 0
    //   259: ifeq +11 -> 270
    //   262: aload 9
    //   264: invokeinterface 305 1 0
    //   269: pop
    //   270: return
    //   271: new 195	java/lang/StringBuilder
    //   274: dup
    //   275: ldc_w 433
    //   278: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   281: aload 6
    //   283: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: ldc_w 656
    //   289: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: aload 13
    //   294: invokestatic 658	javautils/array/ArrayUtils:toString	(Ljava/util/List;)Ljava/lang/String;
    //   297: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   300: ldc_w 665
    //   303: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   309: astore 14
    //   311: aload 9
    //   313: aload 14
    //   315: invokeinterface 269 2 0
    //   320: astore 15
    //   322: aload 15
    //   324: invokeinterface 273 1 0
    //   329: istore 16
    //   331: iload 16
    //   333: ifle +121 -> 454
    //   336: aload 9
    //   338: invokeinterface 286 1 0
    //   343: invokeinterface 289 1 0
    //   348: goto +106 -> 454
    //   351: astore 10
    //   353: aload_0
    //   354: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   357: new 195	java/lang/StringBuilder
    //   360: dup
    //   361: ldc_w 714
    //   364: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   367: aload_1
    //   368: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: ldc_w 671
    //   374: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   380: invokeinterface 296 2 0
    //   385: aload 9
    //   387: invokeinterface 286 1 0
    //   392: invokeinterface 298 1 0
    //   397: iconst_1
    //   398: istore 5
    //   400: aload 9
    //   402: ifnull +75 -> 477
    //   405: aload 9
    //   407: invokeinterface 301 1 0
    //   412: ifeq +65 -> 477
    //   415: aload 9
    //   417: invokeinterface 305 1 0
    //   422: pop
    //   423: goto +54 -> 477
    //   426: astore 17
    //   428: aload 9
    //   430: ifnull +21 -> 451
    //   433: aload 9
    //   435: invokeinterface 301 1 0
    //   440: ifeq +11 -> 451
    //   443: aload 9
    //   445: invokeinterface 305 1 0
    //   450: pop
    //   451: aload 17
    //   453: athrow
    //   454: aload 9
    //   456: ifnull +21 -> 477
    //   459: aload 9
    //   461: invokeinterface 301 1 0
    //   466: ifeq +11 -> 477
    //   469: aload 9
    //   471: invokeinterface 305 1 0
    //   476: pop
    //   477: iload 5
    //   479: ifeq -405 -> 74
    //   482: return
    // Line number table:
    //   Java source line #741	-> byte code offset #0
    //   Java source line #742	-> byte code offset #3
    //   Java source line #743	-> byte code offset #29
    //   Java source line #744	-> byte code offset #34
    //   Java source line #747	-> byte code offset #60
    //   Java source line #749	-> byte code offset #74
    //   Java source line #750	-> byte code offset #85
    //   Java source line #752	-> byte code offset #93
    //   Java source line #753	-> byte code offset #119
    //   Java source line #754	-> byte code offset #129
    //   Java source line #752	-> byte code offset #141
    //   Java source line #755	-> byte code offset #146
    //   Java source line #756	-> byte code offset #157
    //   Java source line #757	-> byte code offset #166
    //   Java source line #758	-> byte code offset #171
    //   Java source line #759	-> byte code offset #174
    //   Java source line #783	-> byte code offset #187
    //   Java source line #784	-> byte code offset #202
    //   Java source line #760	-> byte code offset #210
    //   Java source line #763	-> byte code offset #211
    //   Java source line #764	-> byte code offset #218
    //   Java source line #765	-> byte code offset #231
    //   Java source line #766	-> byte code offset #234
    //   Java source line #783	-> byte code offset #247
    //   Java source line #784	-> byte code offset #262
    //   Java source line #767	-> byte code offset #270
    //   Java source line #770	-> byte code offset #271
    //   Java source line #771	-> byte code offset #292
    //   Java source line #770	-> byte code offset #306
    //   Java source line #773	-> byte code offset #311
    //   Java source line #774	-> byte code offset #322
    //   Java source line #775	-> byte code offset #331
    //   Java source line #776	-> byte code offset #336
    //   Java source line #778	-> byte code offset #348
    //   Java source line #779	-> byte code offset #353
    //   Java source line #780	-> byte code offset #385
    //   Java source line #781	-> byte code offset #397
    //   Java source line #783	-> byte code offset #400
    //   Java source line #784	-> byte code offset #415
    //   Java source line #782	-> byte code offset #426
    //   Java source line #783	-> byte code offset #428
    //   Java source line #784	-> byte code offset #443
    //   Java source line #786	-> byte code offset #451
    //   Java source line #783	-> byte code offset #454
    //   Java source line #784	-> byte code offset #469
    //   Java source line #787	-> byte code offset #477
    //   Java source line #788	-> byte code offset #482
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	483	0	this	DataBaseBackupJob
    //   0	483	1	name	String
    //   0	483	2	realWhere	String
    //   0	483	3	backupWhere	String
    //   0	483	4	table	String
    //   1	477	5	migrateDone	boolean
    //   27	255	6	oldDbTab	String
    //   32	13	7	userTab	String
    //   58	57	8	userDbTab	String
    //   83	387	9	session	org.hibernate.Session
    //   144	5	10	idSql	String
    //   351	3	10	e	Exception
    //   155	3	11	idQuery	org.hibernate.SQLQuery
    //   164	48	12	idResult	Object
    //   216	77	13	ids	java.util.ArrayList<Integer>
    //   309	5	14	deleteSql	String
    //   320	3	15	deleteQuery	org.hibernate.SQLQuery
    //   329	3	16	deleteCount	int
    //   426	26	17	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   93	187	351	java/lang/Exception
    //   211	247	351	java/lang/Exception
    //   271	348	351	java/lang/Exception
    //   93	187	426	finally
    //   211	247	426	finally
    //   271	400	426	finally
  }
  
  /* Error */
  private void delDemoUserBackupBase(String name, String realWhere, String backupWhere, String table)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: new 195	java/lang/StringBuilder
    //   6: dup
    //   7: ldc_w 633
    //   10: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   13: aload 4
    //   15: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc_w 629
    //   21: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: astore 6
    //   29: ldc_w 698
    //   32: astore 7
    //   34: new 195	java/lang/StringBuilder
    //   37: dup
    //   38: ldc_w 633
    //   41: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   44: aload 7
    //   46: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc_w 629
    //   52: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: astore 8
    //   60: aload_0
    //   61: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   64: ldc_w 718
    //   67: aload_2
    //   68: aload_1
    //   69: invokeinterface 527 4 0
    //   74: aload_0
    //   75: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   78: invokeinterface 255 1 0
    //   83: astore 9
    //   85: aload 9
    //   87: invokeinterface 261 1 0
    //   92: pop
    //   93: new 195	java/lang/StringBuilder
    //   96: dup
    //   97: ldc_w 702
    //   100: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   103: aload 6
    //   105: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: ldc_w 704
    //   111: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: aload 8
    //   116: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: ldc_w 720
    //   122: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: aload_2
    //   126: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: ldc_w 708
    //   132: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: sipush 500
    //   138: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   141: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: astore 10
    //   146: aload 9
    //   148: aload 10
    //   150: invokeinterface 269 2 0
    //   155: astore 11
    //   157: aload 11
    //   159: invokeinterface 485 1 0
    //   164: astore 12
    //   166: aload 12
    //   168: ifnonnull +43 -> 211
    //   171: iconst_1
    //   172: istore 5
    //   174: aload_0
    //   175: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   178: ldc_w 710
    //   181: aload_1
    //   182: invokeinterface 441 3 0
    //   187: aload 9
    //   189: ifnull +21 -> 210
    //   192: aload 9
    //   194: invokeinterface 301 1 0
    //   199: ifeq +11 -> 210
    //   202: aload 9
    //   204: invokeinterface 305 1 0
    //   209: pop
    //   210: return
    //   211: aload 12
    //   213: checkcast 480	java/util/ArrayList
    //   216: astore 13
    //   218: aload 13
    //   220: ifnull +11 -> 231
    //   223: aload 13
    //   225: invokevirtual 647	java/util/ArrayList:size	()I
    //   228: ifgt +43 -> 271
    //   231: iconst_1
    //   232: istore 5
    //   234: aload_0
    //   235: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   238: ldc_w 712
    //   241: aload_1
    //   242: invokeinterface 441 3 0
    //   247: aload 9
    //   249: ifnull +21 -> 270
    //   252: aload 9
    //   254: invokeinterface 301 1 0
    //   259: ifeq +11 -> 270
    //   262: aload 9
    //   264: invokeinterface 305 1 0
    //   269: pop
    //   270: return
    //   271: new 195	java/lang/StringBuilder
    //   274: dup
    //   275: ldc_w 433
    //   278: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   281: aload 6
    //   283: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: ldc_w 656
    //   289: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: aload 13
    //   294: invokestatic 658	javautils/array/ArrayUtils:toString	(Ljava/util/List;)Ljava/lang/String;
    //   297: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   300: ldc_w 665
    //   303: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   309: astore 14
    //   311: aload 9
    //   313: aload 14
    //   315: invokeinterface 269 2 0
    //   320: astore 15
    //   322: aload 15
    //   324: invokeinterface 273 1 0
    //   329: istore 16
    //   331: iload 16
    //   333: ifle +121 -> 454
    //   336: aload 9
    //   338: invokeinterface 286 1 0
    //   343: invokeinterface 289 1 0
    //   348: goto +106 -> 454
    //   351: astore 10
    //   353: aload_0
    //   354: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   357: new 195	java/lang/StringBuilder
    //   360: dup
    //   361: ldc_w 714
    //   364: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   367: aload_1
    //   368: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: ldc_w 671
    //   374: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   380: invokeinterface 296 2 0
    //   385: aload 9
    //   387: invokeinterface 286 1 0
    //   392: invokeinterface 298 1 0
    //   397: iconst_1
    //   398: istore 5
    //   400: aload 9
    //   402: ifnull +75 -> 477
    //   405: aload 9
    //   407: invokeinterface 301 1 0
    //   412: ifeq +65 -> 477
    //   415: aload 9
    //   417: invokeinterface 305 1 0
    //   422: pop
    //   423: goto +54 -> 477
    //   426: astore 17
    //   428: aload 9
    //   430: ifnull +21 -> 451
    //   433: aload 9
    //   435: invokeinterface 301 1 0
    //   440: ifeq +11 -> 451
    //   443: aload 9
    //   445: invokeinterface 305 1 0
    //   450: pop
    //   451: aload 17
    //   453: athrow
    //   454: aload 9
    //   456: ifnull +21 -> 477
    //   459: aload 9
    //   461: invokeinterface 301 1 0
    //   466: ifeq +11 -> 477
    //   469: aload 9
    //   471: invokeinterface 305 1 0
    //   476: pop
    //   477: iload 5
    //   479: ifeq -405 -> 74
    //   482: return
    // Line number table:
    //   Java source line #791	-> byte code offset #0
    //   Java source line #792	-> byte code offset #3
    //   Java source line #793	-> byte code offset #29
    //   Java source line #794	-> byte code offset #34
    //   Java source line #797	-> byte code offset #60
    //   Java source line #799	-> byte code offset #74
    //   Java source line #800	-> byte code offset #85
    //   Java source line #802	-> byte code offset #93
    //   Java source line #803	-> byte code offset #119
    //   Java source line #804	-> byte code offset #129
    //   Java source line #802	-> byte code offset #141
    //   Java source line #805	-> byte code offset #146
    //   Java source line #806	-> byte code offset #157
    //   Java source line #807	-> byte code offset #166
    //   Java source line #808	-> byte code offset #171
    //   Java source line #809	-> byte code offset #174
    //   Java source line #833	-> byte code offset #187
    //   Java source line #834	-> byte code offset #202
    //   Java source line #810	-> byte code offset #210
    //   Java source line #813	-> byte code offset #211
    //   Java source line #814	-> byte code offset #218
    //   Java source line #815	-> byte code offset #231
    //   Java source line #816	-> byte code offset #234
    //   Java source line #833	-> byte code offset #247
    //   Java source line #834	-> byte code offset #262
    //   Java source line #817	-> byte code offset #270
    //   Java source line #820	-> byte code offset #271
    //   Java source line #821	-> byte code offset #292
    //   Java source line #820	-> byte code offset #306
    //   Java source line #823	-> byte code offset #311
    //   Java source line #824	-> byte code offset #322
    //   Java source line #825	-> byte code offset #331
    //   Java source line #826	-> byte code offset #336
    //   Java source line #828	-> byte code offset #348
    //   Java source line #829	-> byte code offset #353
    //   Java source line #830	-> byte code offset #385
    //   Java source line #831	-> byte code offset #397
    //   Java source line #833	-> byte code offset #400
    //   Java source line #834	-> byte code offset #415
    //   Java source line #832	-> byte code offset #426
    //   Java source line #833	-> byte code offset #428
    //   Java source line #834	-> byte code offset #443
    //   Java source line #836	-> byte code offset #451
    //   Java source line #833	-> byte code offset #454
    //   Java source line #834	-> byte code offset #469
    //   Java source line #837	-> byte code offset #477
    //   Java source line #838	-> byte code offset #482
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	483	0	this	DataBaseBackupJob
    //   0	483	1	name	String
    //   0	483	2	realWhere	String
    //   0	483	3	backupWhere	String
    //   0	483	4	table	String
    //   1	477	5	migrateDone	boolean
    //   27	255	6	oldDbTab	String
    //   32	13	7	userTab	String
    //   58	57	8	userDbTab	String
    //   83	387	9	session	org.hibernate.Session
    //   144	5	10	idSql	String
    //   351	3	10	e	Exception
    //   155	3	11	idQuery	org.hibernate.SQLQuery
    //   164	48	12	idResult	Object
    //   216	77	13	ids	java.util.ArrayList<Integer>
    //   309	5	14	deleteSql	String
    //   320	3	15	deleteQuery	org.hibernate.SQLQuery
    //   329	3	16	deleteCount	int
    //   426	26	17	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   93	187	351	java/lang/Exception
    //   211	247	351	java/lang/Exception
    //   271	348	351	java/lang/Exception
    //   93	187	426	finally
    //   211	247	426	finally
    //   271	400	426	finally
  }
  
  /* Error */
  private void commonUserBackupBase(String name, String realWhere, String backupWhere, String table)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 5
    //   3: new 195	java/lang/StringBuilder
    //   6: dup
    //   7: ldc_w 633
    //   10: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   13: aload 4
    //   15: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc_w 629
    //   21: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: astore 6
    //   29: ldc_w 698
    //   32: astore 7
    //   34: new 195	java/lang/StringBuilder
    //   37: dup
    //   38: ldc_w 633
    //   41: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   44: aload 7
    //   46: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc_w 629
    //   52: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: astore 8
    //   60: new 195	java/lang/StringBuilder
    //   63: dup
    //   64: ldc_w 635
    //   67: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   70: aload 4
    //   72: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: ldc_w 629
    //   78: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   84: astore 9
    //   86: aload_0
    //   87: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   90: ldc_w 637
    //   93: aload_2
    //   94: aload_1
    //   95: invokeinterface 527 4 0
    //   100: aload_0
    //   101: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   104: invokeinterface 255 1 0
    //   109: astore 10
    //   111: aload 10
    //   113: invokeinterface 261 1 0
    //   118: pop
    //   119: new 195	java/lang/StringBuilder
    //   122: dup
    //   123: ldc_w 702
    //   126: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   129: aload 6
    //   131: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: ldc_w 704
    //   137: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: aload 8
    //   142: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: ldc_w 722
    //   148: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: aload_2
    //   152: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: ldc_w 708
    //   158: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: sipush 500
    //   164: invokevirtual 281	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   167: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   170: astore 11
    //   172: aload 10
    //   174: aload 11
    //   176: invokeinterface 269 2 0
    //   181: astore 12
    //   183: aload 12
    //   185: invokeinterface 485 1 0
    //   190: astore 13
    //   192: aload 13
    //   194: ifnonnull +43 -> 237
    //   197: iconst_1
    //   198: istore 5
    //   200: aload_0
    //   201: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   204: ldc_w 645
    //   207: aload_1
    //   208: invokeinterface 441 3 0
    //   213: aload 10
    //   215: ifnull +21 -> 236
    //   218: aload 10
    //   220: invokeinterface 301 1 0
    //   225: ifeq +11 -> 236
    //   228: aload 10
    //   230: invokeinterface 305 1 0
    //   235: pop
    //   236: return
    //   237: aload 13
    //   239: checkcast 480	java/util/ArrayList
    //   242: astore 14
    //   244: aload 14
    //   246: ifnull +11 -> 257
    //   249: aload 14
    //   251: invokevirtual 647	java/util/ArrayList:size	()I
    //   254: ifgt +43 -> 297
    //   257: iconst_1
    //   258: istore 5
    //   260: aload_0
    //   261: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   264: ldc_w 650
    //   267: aload_1
    //   268: invokeinterface 441 3 0
    //   273: aload 10
    //   275: ifnull +21 -> 296
    //   278: aload 10
    //   280: invokeinterface 301 1 0
    //   285: ifeq +11 -> 296
    //   288: aload 10
    //   290: invokeinterface 305 1 0
    //   295: pop
    //   296: return
    //   297: new 195	java/lang/StringBuilder
    //   300: dup
    //   301: ldc_w 652
    //   304: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   307: aload 9
    //   309: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: ldc_w 654
    //   315: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: aload 6
    //   320: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   323: ldc_w 656
    //   326: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   329: aload 14
    //   331: invokestatic 658	javautils/array/ArrayUtils:toString	(Ljava/util/List;)Ljava/lang/String;
    //   334: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: ldc_w 663
    //   340: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   343: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   346: astore 15
    //   348: new 195	java/lang/StringBuilder
    //   351: dup
    //   352: ldc_w 433
    //   355: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   358: aload 6
    //   360: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   363: ldc_w 656
    //   366: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: aload 14
    //   371: invokestatic 658	javautils/array/ArrayUtils:toString	(Ljava/util/List;)Ljava/lang/String;
    //   374: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: ldc_w 665
    //   380: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   383: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   386: astore 16
    //   388: aload 10
    //   390: aload 15
    //   392: invokeinterface 269 2 0
    //   397: astore 17
    //   399: aload 17
    //   401: invokeinterface 273 1 0
    //   406: istore 18
    //   408: iload 18
    //   410: ifle +61 -> 471
    //   413: aload_0
    //   414: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   417: ldc_w 667
    //   420: aload_1
    //   421: iload 18
    //   423: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   426: invokeinterface 527 4 0
    //   431: aload 10
    //   433: aload 16
    //   435: invokeinterface 269 2 0
    //   440: astore 19
    //   442: aload 19
    //   444: invokeinterface 273 1 0
    //   449: istore 20
    //   451: iload 20
    //   453: ifle +140 -> 593
    //   456: aload 10
    //   458: invokeinterface 286 1 0
    //   463: invokeinterface 289 1 0
    //   468: goto +125 -> 593
    //   471: iconst_1
    //   472: istore 5
    //   474: aload_0
    //   475: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   478: ldc_w 645
    //   481: aload_1
    //   482: invokeinterface 441 3 0
    //   487: goto +106 -> 593
    //   490: astore 11
    //   492: aload_0
    //   493: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   496: new 195	java/lang/StringBuilder
    //   499: dup
    //   500: ldc_w 669
    //   503: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   506: aload_1
    //   507: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   510: ldc_w 671
    //   513: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   516: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   519: invokeinterface 296 2 0
    //   524: aload 10
    //   526: invokeinterface 286 1 0
    //   531: invokeinterface 298 1 0
    //   536: iconst_1
    //   537: istore 5
    //   539: aload 10
    //   541: ifnull +75 -> 616
    //   544: aload 10
    //   546: invokeinterface 301 1 0
    //   551: ifeq +65 -> 616
    //   554: aload 10
    //   556: invokeinterface 305 1 0
    //   561: pop
    //   562: goto +54 -> 616
    //   565: astore 21
    //   567: aload 10
    //   569: ifnull +21 -> 590
    //   572: aload 10
    //   574: invokeinterface 301 1 0
    //   579: ifeq +11 -> 590
    //   582: aload 10
    //   584: invokeinterface 305 1 0
    //   589: pop
    //   590: aload 21
    //   592: athrow
    //   593: aload 10
    //   595: ifnull +21 -> 616
    //   598: aload 10
    //   600: invokeinterface 301 1 0
    //   605: ifeq +11 -> 616
    //   608: aload 10
    //   610: invokeinterface 305 1 0
    //   615: pop
    //   616: iload 5
    //   618: ifeq -518 -> 100
    //   621: aload_3
    //   622: invokestatic 673	javautils/StringUtil:isNotNull	(Ljava/lang/String;)Z
    //   625: ifeq +268 -> 893
    //   628: aload_0
    //   629: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   632: ldc_w 679
    //   635: aload_3
    //   636: aload_1
    //   637: invokeinterface 527 4 0
    //   642: aload_0
    //   643: getfield 253	lottery/domains/content/jobs/DataBaseBackupJob:sessionFactory	Lorg/hibernate/SessionFactory;
    //   646: invokeinterface 255 1 0
    //   651: astore 10
    //   653: aload 10
    //   655: invokeinterface 261 1 0
    //   660: pop
    //   661: new 195	java/lang/StringBuilder
    //   664: dup
    //   665: ldc_w 433
    //   668: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   671: aload 9
    //   673: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   676: ldc_w 641
    //   679: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   682: aload_3
    //   683: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   686: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   689: astore 11
    //   691: aload 10
    //   693: aload 11
    //   695: invokeinterface 269 2 0
    //   700: astore 12
    //   702: aload 12
    //   704: invokeinterface 273 1 0
    //   709: istore 13
    //   711: iload 13
    //   713: ifle +36 -> 749
    //   716: aload_0
    //   717: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   720: ldc_w 681
    //   723: aload_1
    //   724: iload 13
    //   726: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   729: invokeinterface 527 4 0
    //   734: aload 10
    //   736: invokeinterface 286 1 0
    //   741: invokeinterface 289 1 0
    //   746: goto +124 -> 870
    //   749: aload_0
    //   750: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   753: ldc_w 683
    //   756: aload_1
    //   757: iload 13
    //   759: invokestatic 419	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   762: invokeinterface 527 4 0
    //   767: goto +103 -> 870
    //   770: astore 11
    //   772: aload_0
    //   773: getfield 49	lottery/domains/content/jobs/DataBaseBackupJob:logger	Lorg/slf4j/Logger;
    //   776: new 195	java/lang/StringBuilder
    //   779: dup
    //   780: ldc_w 532
    //   783: invokespecial 199	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   786: aload_1
    //   787: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   790: ldc_w 685
    //   793: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   796: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   799: invokeinterface 296 2 0
    //   804: aload 10
    //   806: invokeinterface 286 1 0
    //   811: invokeinterface 298 1 0
    //   816: aload 10
    //   818: ifnull +75 -> 893
    //   821: aload 10
    //   823: invokeinterface 301 1 0
    //   828: ifeq +65 -> 893
    //   831: aload 10
    //   833: invokeinterface 305 1 0
    //   838: pop
    //   839: goto +54 -> 893
    //   842: astore 14
    //   844: aload 10
    //   846: ifnull +21 -> 867
    //   849: aload 10
    //   851: invokeinterface 301 1 0
    //   856: ifeq +11 -> 867
    //   859: aload 10
    //   861: invokeinterface 305 1 0
    //   866: pop
    //   867: aload 14
    //   869: athrow
    //   870: aload 10
    //   872: ifnull +21 -> 893
    //   875: aload 10
    //   877: invokeinterface 301 1 0
    //   882: ifeq +11 -> 893
    //   885: aload 10
    //   887: invokeinterface 305 1 0
    //   892: pop
    //   893: return
    // Line number table:
    //   Java source line #841	-> byte code offset #0
    //   Java source line #842	-> byte code offset #3
    //   Java source line #843	-> byte code offset #29
    //   Java source line #844	-> byte code offset #34
    //   Java source line #846	-> byte code offset #60
    //   Java source line #849	-> byte code offset #86
    //   Java source line #851	-> byte code offset #100
    //   Java source line #852	-> byte code offset #111
    //   Java source line #854	-> byte code offset #119
    //   Java source line #855	-> byte code offset #145
    //   Java source line #856	-> byte code offset #155
    //   Java source line #854	-> byte code offset #167
    //   Java source line #857	-> byte code offset #172
    //   Java source line #858	-> byte code offset #183
    //   Java source line #859	-> byte code offset #192
    //   Java source line #860	-> byte code offset #197
    //   Java source line #861	-> byte code offset #200
    //   Java source line #896	-> byte code offset #213
    //   Java source line #897	-> byte code offset #228
    //   Java source line #862	-> byte code offset #236
    //   Java source line #865	-> byte code offset #237
    //   Java source line #866	-> byte code offset #244
    //   Java source line #867	-> byte code offset #257
    //   Java source line #868	-> byte code offset #260
    //   Java source line #896	-> byte code offset #273
    //   Java source line #897	-> byte code offset #288
    //   Java source line #869	-> byte code offset #296
    //   Java source line #872	-> byte code offset #297
    //   Java source line #873	-> byte code offset #329
    //   Java source line #872	-> byte code offset #343
    //   Java source line #875	-> byte code offset #348
    //   Java source line #876	-> byte code offset #369
    //   Java source line #875	-> byte code offset #383
    //   Java source line #878	-> byte code offset #388
    //   Java source line #879	-> byte code offset #399
    //   Java source line #880	-> byte code offset #408
    //   Java source line #881	-> byte code offset #413
    //   Java source line #882	-> byte code offset #431
    //   Java source line #883	-> byte code offset #442
    //   Java source line #884	-> byte code offset #451
    //   Java source line #885	-> byte code offset #456
    //   Java source line #887	-> byte code offset #468
    //   Java source line #888	-> byte code offset #471
    //   Java source line #889	-> byte code offset #474
    //   Java source line #891	-> byte code offset #487
    //   Java source line #892	-> byte code offset #492
    //   Java source line #893	-> byte code offset #524
    //   Java source line #894	-> byte code offset #536
    //   Java source line #896	-> byte code offset #539
    //   Java source line #897	-> byte code offset #554
    //   Java source line #895	-> byte code offset #565
    //   Java source line #896	-> byte code offset #567
    //   Java source line #897	-> byte code offset #582
    //   Java source line #899	-> byte code offset #590
    //   Java source line #896	-> byte code offset #593
    //   Java source line #897	-> byte code offset #608
    //   Java source line #900	-> byte code offset #616
    //   Java source line #903	-> byte code offset #621
    //   Java source line #904	-> byte code offset #628
    //   Java source line #905	-> byte code offset #642
    //   Java source line #906	-> byte code offset #653
    //   Java source line #908	-> byte code offset #661
    //   Java source line #910	-> byte code offset #691
    //   Java source line #911	-> byte code offset #702
    //   Java source line #912	-> byte code offset #711
    //   Java source line #913	-> byte code offset #716
    //   Java source line #914	-> byte code offset #734
    //   Java source line #915	-> byte code offset #746
    //   Java source line #916	-> byte code offset #749
    //   Java source line #918	-> byte code offset #767
    //   Java source line #919	-> byte code offset #772
    //   Java source line #920	-> byte code offset #804
    //   Java source line #922	-> byte code offset #816
    //   Java source line #923	-> byte code offset #831
    //   Java source line #921	-> byte code offset #842
    //   Java source line #922	-> byte code offset #844
    //   Java source line #923	-> byte code offset #859
    //   Java source line #925	-> byte code offset #867
    //   Java source line #922	-> byte code offset #870
    //   Java source line #923	-> byte code offset #885
    //   Java source line #927	-> byte code offset #893
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	894	0	this	DataBaseBackupJob
    //   0	894	1	name	String
    //   0	894	2	realWhere	String
    //   0	894	3	backupWhere	String
    //   0	894	4	table	String
    //   1	616	5	migrateDone	boolean
    //   27	332	6	oldDbTab	String
    //   32	13	7	userTab	String
    //   58	83	8	userDbTab	String
    //   84	588	9	newDbTab	String
    //   109	500	10	session	org.hibernate.Session
    //   651	235	10	session	org.hibernate.Session
    //   170	5	11	idSql	String
    //   490	3	11	e	Exception
    //   689	5	11	deleteSql	String
    //   770	3	11	e	Exception
    //   181	3	12	idQuery	org.hibernate.SQLQuery
    //   700	3	12	deleteQuery	org.hibernate.SQLQuery
    //   190	48	13	idResult	Object
    //   709	49	13	deleteCount	int
    //   242	626	14	ids	java.util.ArrayList<Integer>
    //   346	45	15	insertSql	String
    //   386	48	16	deleteSql	String
    //   397	3	17	insertQuery	org.hibernate.SQLQuery
    //   406	16	18	insertCount	int
    //   440	3	19	deleteQuery	org.hibernate.SQLQuery
    //   449	3	20	deleteCount	int
    //   565	26	21	localObject1	Object
    // Exception table:
    //   from	to	target	type
    //   119	213	490	java/lang/Exception
    //   237	273	490	java/lang/Exception
    //   297	487	490	java/lang/Exception
    //   119	213	565	finally
    //   237	273	565	finally
    //   297	539	565	finally
    //   661	767	770	java/lang/Exception
    //   661	816	842	finally
  }
}
