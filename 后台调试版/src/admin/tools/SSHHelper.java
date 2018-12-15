package admin.tools;

import java.io.PrintStream;

public class SSHHelper
{
  /* Error */
  public static String exec(String host, String user, String passwd, int port, String command)
  {
    return null;
    // Byte code:
    //   0: ldc 16
    //   2: astore 5
    //   4: iconst_m1
    //   5: istore 6
    //   7: aconst_null
    //   8: astore 7
    //   10: aconst_null
    //   11: astore 8
    //   13: new 18	com/jcraft/jsch/JSch
    //   16: dup
    //   17: invokespecial 20	com/jcraft/jsch/JSch:<init>	()V
    //   20: astore 9
    //   22: aload 9
    //   24: aload_1
    //   25: aload_0
    //   26: iload_3
    //   27: invokevirtual 21	com/jcraft/jsch/JSch:getSession	(Ljava/lang/String;Ljava/lang/String;I)Lcom/jcraft/jsch/Session;
    //   30: astore 7
    //   32: new 25	java/util/Properties
    //   35: dup
    //   36: invokespecial 27	java/util/Properties:<init>	()V
    //   39: astore 10
    //   41: aload 10
    //   43: ldc 28
    //   45: ldc 30
    //   47: invokevirtual 32	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   50: pop
    //   51: aload 7
    //   53: aload 10
    //   55: invokevirtual 36	com/jcraft/jsch/Session:setConfig	(Ljava/util/Properties;)V
    //   58: aload 7
    //   60: aload_2
    //   61: invokevirtual 42	com/jcraft/jsch/Session:setPassword	(Ljava/lang/String;)V
    //   64: aload 7
    //   66: invokevirtual 46	com/jcraft/jsch/Session:connect	()V
    //   69: aload 7
    //   71: ldc 49
    //   73: invokevirtual 50	com/jcraft/jsch/Session:openChannel	(Ljava/lang/String;)Lcom/jcraft/jsch/Channel;
    //   76: checkcast 54	com/jcraft/jsch/ChannelExec
    //   79: astore 8
    //   81: aload 8
    //   83: aload 4
    //   85: invokevirtual 56	com/jcraft/jsch/ChannelExec:setCommand	(Ljava/lang/String;)V
    //   88: aload 8
    //   90: invokevirtual 59	com/jcraft/jsch/ChannelExec:connect	()V
    //   93: aload 8
    //   95: invokevirtual 60	com/jcraft/jsch/ChannelExec:getInputStream	()Ljava/io/InputStream;
    //   98: astore 11
    //   100: new 64	java/io/BufferedReader
    //   103: dup
    //   104: new 66	java/io/InputStreamReader
    //   107: dup
    //   108: aload 11
    //   110: invokespecial 68	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   113: invokespecial 71	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   116: astore 12
    //   118: aconst_null
    //   119: astore 13
    //   121: goto +44 -> 165
    //   124: new 74	java/lang/StringBuilder
    //   127: dup
    //   128: aload 5
    //   130: invokestatic 76	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   133: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   136: new 77	java/lang/String
    //   139: dup
    //   140: aload 13
    //   142: ldc 84
    //   144: invokevirtual 86	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   147: ldc 90
    //   149: invokespecial 92	java/lang/String:<init>	([BLjava/lang/String;)V
    //   152: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: ldc 99
    //   157: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   163: astore 5
    //   165: aload 12
    //   167: invokevirtual 105	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   170: dup
    //   171: astore 13
    //   173: invokestatic 108	admin/tools/StringUtils:isEmpty	(Ljava/lang/String;)Z
    //   176: ifeq -52 -> 124
    //   179: aload 8
    //   181: invokevirtual 114	com/jcraft/jsch/ChannelExec:getExitStatus	()I
    //   184: istore 6
    //   186: getstatic 118	java/lang/System:out	Ljava/io/PrintStream;
    //   189: aload 5
    //   191: invokevirtual 124	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   194: iload 6
    //   196: ifne +61 -> 257
    //   199: aload 5
    //   201: ldc -127
    //   203: invokevirtual 131	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   206: ifeq +10 -> 216
    //   209: ldc -121
    //   211: astore 5
    //   213: goto +158 -> 371
    //   216: aload 5
    //   218: ldc -119
    //   220: invokevirtual 131	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   223: ifeq +10 -> 233
    //   226: ldc -117
    //   228: astore 5
    //   230: goto +141 -> 371
    //   233: aload 5
    //   235: ldc -115
    //   237: invokevirtual 131	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   240: ifeq +10 -> 250
    //   243: ldc -117
    //   245: astore 5
    //   247: goto +124 -> 371
    //   250: ldc -113
    //   252: astore 5
    //   254: goto +117 -> 371
    //   257: ldc -111
    //   259: astore 5
    //   261: goto +110 -> 371
    //   264: astore 9
    //   266: new 74	java/lang/StringBuilder
    //   269: dup
    //   270: aload 5
    //   272: invokestatic 76	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   275: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   278: aload 9
    //   280: invokevirtual 147	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   283: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   289: astore 5
    //   291: aload 8
    //   293: ifnull +16 -> 309
    //   296: aload 8
    //   298: invokevirtual 152	com/jcraft/jsch/ChannelExec:isClosed	()Z
    //   301: ifne +8 -> 309
    //   304: aload 8
    //   306: invokevirtual 156	com/jcraft/jsch/ChannelExec:disconnect	()V
    //   309: aload 7
    //   311: ifnull +96 -> 407
    //   314: aload 7
    //   316: invokevirtual 159	com/jcraft/jsch/Session:isConnected	()Z
    //   319: ifeq +88 -> 407
    //   322: aload 7
    //   324: invokevirtual 162	com/jcraft/jsch/Session:disconnect	()V
    //   327: goto +80 -> 407
    //   330: astore 14
    //   332: aload 8
    //   334: ifnull +16 -> 350
    //   337: aload 8
    //   339: invokevirtual 152	com/jcraft/jsch/ChannelExec:isClosed	()Z
    //   342: ifne +8 -> 350
    //   345: aload 8
    //   347: invokevirtual 156	com/jcraft/jsch/ChannelExec:disconnect	()V
    //   350: aload 7
    //   352: ifnull +16 -> 368
    //   355: aload 7
    //   357: invokevirtual 159	com/jcraft/jsch/Session:isConnected	()Z
    //   360: ifeq +8 -> 368
    //   363: aload 7
    //   365: invokevirtual 162	com/jcraft/jsch/Session:disconnect	()V
    //   368: aload 14
    //   370: athrow
    //   371: aload 8
    //   373: ifnull +16 -> 389
    //   376: aload 8
    //   378: invokevirtual 152	com/jcraft/jsch/ChannelExec:isClosed	()Z
    //   381: ifne +8 -> 389
    //   384: aload 8
    //   386: invokevirtual 156	com/jcraft/jsch/ChannelExec:disconnect	()V
    //   389: aload 7
    //   391: ifnull +16 -> 407
    //   394: aload 7
    //   396: invokevirtual 159	com/jcraft/jsch/Session:isConnected	()Z
    //   399: ifeq +8 -> 407
    //   402: aload 7
    //   404: invokevirtual 162	com/jcraft/jsch/Session:disconnect	()V
    //   407: new 74	java/lang/StringBuilder
    //   410: dup
    //   411: ldc -93
    //   413: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   416: iload 6
    //   418: invokevirtual 165	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   421: ldc -88
    //   423: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   426: aload 5
    //   428: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   431: ldc -86
    //   433: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   436: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   439: astore 5
    //   441: aload 5
    //   443: areturn
    // Line number table:
    //   Java source line #36	-> byte code offset #0
    //   Java source line #37	-> byte code offset #4
    //   Java source line #38	-> byte code offset #7
    //   Java source line #39	-> byte code offset #10
    //   Java source line #41	-> byte code offset #13
    //   Java source line #42	-> byte code offset #22
    //   Java source line #43	-> byte code offset #32
    //   Java source line #44	-> byte code offset #41
    //   Java source line #45	-> byte code offset #51
    //   Java source line #46	-> byte code offset #58
    //   Java source line #47	-> byte code offset #64
    //   Java source line #48	-> byte code offset #69
    //   Java source line #49	-> byte code offset #81
    //   Java source line #51	-> byte code offset #88
    //   Java source line #52	-> byte code offset #93
    //   Java source line #53	-> byte code offset #100
    //   Java source line #54	-> byte code offset #104
    //   Java source line #53	-> byte code offset #113
    //   Java source line #55	-> byte code offset #118
    //   Java source line #56	-> byte code offset #121
    //   Java source line #57	-> byte code offset #124
    //   Java source line #56	-> byte code offset #165
    //   Java source line #59	-> byte code offset #179
    //   Java source line #61	-> byte code offset #186
    //   Java source line #62	-> byte code offset #194
    //   Java source line #63	-> byte code offset #199
    //   Java source line #64	-> byte code offset #209
    //   Java source line #65	-> byte code offset #213
    //   Java source line #66	-> byte code offset #226
    //   Java source line #67	-> byte code offset #230
    //   Java source line #68	-> byte code offset #243
    //   Java source line #69	-> byte code offset #247
    //   Java source line #70	-> byte code offset #250
    //   Java source line #72	-> byte code offset #254
    //   Java source line #73	-> byte code offset #257
    //   Java source line #75	-> byte code offset #261
    //   Java source line #76	-> byte code offset #266
    //   Java source line #78	-> byte code offset #291
    //   Java source line #79	-> byte code offset #304
    //   Java source line #81	-> byte code offset #309
    //   Java source line #82	-> byte code offset #322
    //   Java source line #77	-> byte code offset #330
    //   Java source line #78	-> byte code offset #332
    //   Java source line #79	-> byte code offset #345
    //   Java source line #81	-> byte code offset #350
    //   Java source line #82	-> byte code offset #363
    //   Java source line #84	-> byte code offset #368
    //   Java source line #78	-> byte code offset #371
    //   Java source line #79	-> byte code offset #384
    //   Java source line #81	-> byte code offset #389
    //   Java source line #82	-> byte code offset #402
    //   Java source line #85	-> byte code offset #407
    //   Java source line #86	-> byte code offset #441
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	444	0	host	String
    //   0	444	1	user	String
    //   0	444	2	passwd	String
    //   0	444	3	port	int
    //   0	444	4	command	String
    //   2	440	5	result	String
    //   5	412	6	exitStatus	int
    //   8	395	7	session	com.jcraft.jsch.Session
    //   11	374	8	openChannel	com.jcraft.jsch.ChannelExec
    //   20	3	9	jsch	com.jcraft.jsch.JSch
    //   264	15	9	e	Exception
    //   39	15	10	config	java.util.Properties
    //   98	11	11	in	java.io.InputStream
    //   116	50	12	reader	java.io.BufferedReader
    //   119	53	13	buf	String
    //   330	39	14	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   13	261	264	com/jcraft/jsch/JSchException
    //   13	261	264	java/io/IOException
    //   13	291	330	finally
  }
  
  /* Error */
  public static String execQuick(String host, String user, String passwd, int port, String command)
  {
    return null;
    // Byte code:
    //   0: ldc 16
    //   2: astore 5
    //   4: iconst_m1
    //   5: istore 6
    //   7: aconst_null
    //   8: astore 7
    //   10: aconst_null
    //   11: astore 8
    //   13: new 18	com/jcraft/jsch/JSch
    //   16: dup
    //   17: invokespecial 20	com/jcraft/jsch/JSch:<init>	()V
    //   20: astore 9
    //   22: aload 9
    //   24: aload_1
    //   25: aload_0
    //   26: iload_3
    //   27: invokevirtual 21	com/jcraft/jsch/JSch:getSession	(Ljava/lang/String;Ljava/lang/String;I)Lcom/jcraft/jsch/Session;
    //   30: astore 7
    //   32: new 25	java/util/Properties
    //   35: dup
    //   36: invokespecial 27	java/util/Properties:<init>	()V
    //   39: astore 10
    //   41: aload 10
    //   43: ldc 28
    //   45: ldc 30
    //   47: invokevirtual 32	java/util/Properties:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   50: pop
    //   51: aload 7
    //   53: aload 10
    //   55: invokevirtual 36	com/jcraft/jsch/Session:setConfig	(Ljava/util/Properties;)V
    //   58: aload 7
    //   60: aload_2
    //   61: invokevirtual 42	com/jcraft/jsch/Session:setPassword	(Ljava/lang/String;)V
    //   64: aload 7
    //   66: invokevirtual 46	com/jcraft/jsch/Session:connect	()V
    //   69: aload 7
    //   71: ldc 49
    //   73: invokevirtual 50	com/jcraft/jsch/Session:openChannel	(Ljava/lang/String;)Lcom/jcraft/jsch/Channel;
    //   76: checkcast 54	com/jcraft/jsch/ChannelExec
    //   79: astore 8
    //   81: aload 8
    //   83: aload 4
    //   85: invokevirtual 56	com/jcraft/jsch/ChannelExec:setCommand	(Ljava/lang/String;)V
    //   88: aload 8
    //   90: invokevirtual 59	com/jcraft/jsch/ChannelExec:connect	()V
    //   93: aload 8
    //   95: invokevirtual 60	com/jcraft/jsch/ChannelExec:getInputStream	()Ljava/io/InputStream;
    //   98: astore 11
    //   100: new 64	java/io/BufferedReader
    //   103: dup
    //   104: new 66	java/io/InputStreamReader
    //   107: dup
    //   108: aload 11
    //   110: invokespecial 68	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   113: invokespecial 71	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   116: astore 12
    //   118: aconst_null
    //   119: astore 13
    //   121: aload 12
    //   123: invokevirtual 105	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   126: dup
    //   127: astore 13
    //   129: invokestatic 108	admin/tools/StringUtils:isEmpty	(Ljava/lang/String;)Z
    //   132: ifne +44 -> 176
    //   135: new 74	java/lang/StringBuilder
    //   138: dup
    //   139: aload 5
    //   141: invokestatic 76	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   144: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   147: new 77	java/lang/String
    //   150: dup
    //   151: aload 13
    //   153: ldc 84
    //   155: invokevirtual 86	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   158: ldc 90
    //   160: invokespecial 92	java/lang/String:<init>	([BLjava/lang/String;)V
    //   163: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: ldc 99
    //   168: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   174: astore 5
    //   176: aload 8
    //   178: invokevirtual 114	com/jcraft/jsch/ChannelExec:getExitStatus	()I
    //   181: istore 6
    //   183: iload 6
    //   185: ifne +10 -> 195
    //   188: ldc -121
    //   190: astore 5
    //   192: goto +7 -> 199
    //   195: ldc -51
    //   197: astore 5
    //   199: getstatic 118	java/lang/System:out	Ljava/io/PrintStream;
    //   202: iload 6
    //   204: invokevirtual 207	java/io/PrintStream:println	(I)V
    //   207: goto +110 -> 317
    //   210: astore 9
    //   212: new 74	java/lang/StringBuilder
    //   215: dup
    //   216: aload 5
    //   218: invokestatic 76	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   221: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   224: aload 9
    //   226: invokevirtual 147	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   229: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   235: astore 5
    //   237: aload 8
    //   239: ifnull +16 -> 255
    //   242: aload 8
    //   244: invokevirtual 152	com/jcraft/jsch/ChannelExec:isClosed	()Z
    //   247: ifne +8 -> 255
    //   250: aload 8
    //   252: invokevirtual 156	com/jcraft/jsch/ChannelExec:disconnect	()V
    //   255: aload 7
    //   257: ifnull +96 -> 353
    //   260: aload 7
    //   262: invokevirtual 159	com/jcraft/jsch/Session:isConnected	()Z
    //   265: ifeq +88 -> 353
    //   268: aload 7
    //   270: invokevirtual 162	com/jcraft/jsch/Session:disconnect	()V
    //   273: goto +80 -> 353
    //   276: astore 14
    //   278: aload 8
    //   280: ifnull +16 -> 296
    //   283: aload 8
    //   285: invokevirtual 152	com/jcraft/jsch/ChannelExec:isClosed	()Z
    //   288: ifne +8 -> 296
    //   291: aload 8
    //   293: invokevirtual 156	com/jcraft/jsch/ChannelExec:disconnect	()V
    //   296: aload 7
    //   298: ifnull +16 -> 314
    //   301: aload 7
    //   303: invokevirtual 159	com/jcraft/jsch/Session:isConnected	()Z
    //   306: ifeq +8 -> 314
    //   309: aload 7
    //   311: invokevirtual 162	com/jcraft/jsch/Session:disconnect	()V
    //   314: aload 14
    //   316: athrow
    //   317: aload 8
    //   319: ifnull +16 -> 335
    //   322: aload 8
    //   324: invokevirtual 152	com/jcraft/jsch/ChannelExec:isClosed	()Z
    //   327: ifne +8 -> 335
    //   330: aload 8
    //   332: invokevirtual 156	com/jcraft/jsch/ChannelExec:disconnect	()V
    //   335: aload 7
    //   337: ifnull +16 -> 353
    //   340: aload 7
    //   342: invokevirtual 159	com/jcraft/jsch/Session:isConnected	()Z
    //   345: ifeq +8 -> 353
    //   348: aload 7
    //   350: invokevirtual 162	com/jcraft/jsch/Session:disconnect	()V
    //   353: new 74	java/lang/StringBuilder
    //   356: dup
    //   357: ldc -93
    //   359: invokespecial 82	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   362: iload 6
    //   364: invokevirtual 165	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   367: ldc -88
    //   369: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   372: aload 5
    //   374: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: ldc -86
    //   379: invokevirtual 95	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   382: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   385: astore 5
    //   387: aload 5
    //   389: areturn
    // Line number table:
    //   Java source line #91	-> byte code offset #0
    //   Java source line #92	-> byte code offset #4
    //   Java source line #93	-> byte code offset #7
    //   Java source line #94	-> byte code offset #10
    //   Java source line #96	-> byte code offset #13
    //   Java source line #97	-> byte code offset #22
    //   Java source line #98	-> byte code offset #32
    //   Java source line #99	-> byte code offset #41
    //   Java source line #100	-> byte code offset #51
    //   Java source line #101	-> byte code offset #58
    //   Java source line #102	-> byte code offset #64
    //   Java source line #103	-> byte code offset #69
    //   Java source line #104	-> byte code offset #81
    //   Java source line #106	-> byte code offset #88
    //   Java source line #107	-> byte code offset #93
    //   Java source line #108	-> byte code offset #100
    //   Java source line #109	-> byte code offset #104
    //   Java source line #108	-> byte code offset #113
    //   Java source line #110	-> byte code offset #118
    //   Java source line #111	-> byte code offset #121
    //   Java source line #112	-> byte code offset #135
    //   Java source line #114	-> byte code offset #176
    //   Java source line #116	-> byte code offset #183
    //   Java source line #117	-> byte code offset #188
    //   Java source line #118	-> byte code offset #192
    //   Java source line #119	-> byte code offset #195
    //   Java source line #121	-> byte code offset #199
    //   Java source line #122	-> byte code offset #207
    //   Java source line #123	-> byte code offset #212
    //   Java source line #125	-> byte code offset #237
    //   Java source line #126	-> byte code offset #250
    //   Java source line #128	-> byte code offset #255
    //   Java source line #129	-> byte code offset #268
    //   Java source line #124	-> byte code offset #276
    //   Java source line #125	-> byte code offset #278
    //   Java source line #126	-> byte code offset #291
    //   Java source line #128	-> byte code offset #296
    //   Java source line #129	-> byte code offset #309
    //   Java source line #131	-> byte code offset #314
    //   Java source line #125	-> byte code offset #317
    //   Java source line #126	-> byte code offset #330
    //   Java source line #128	-> byte code offset #335
    //   Java source line #129	-> byte code offset #348
    //   Java source line #132	-> byte code offset #353
    //   Java source line #133	-> byte code offset #387
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	390	0	host	String
    //   0	390	1	user	String
    //   0	390	2	passwd	String
    //   0	390	3	port	int
    //   0	390	4	command	String
    //   2	386	5	result	String
    //   5	358	6	exitStatus	int
    //   8	341	7	session	com.jcraft.jsch.Session
    //   11	320	8	openChannel	com.jcraft.jsch.ChannelExec
    //   20	3	9	jsch	com.jcraft.jsch.JSch
    //   210	15	9	e	Exception
    //   39	15	10	config	java.util.Properties
    //   98	11	11	in	java.io.InputStream
    //   116	6	12	reader	java.io.BufferedReader
    //   119	33	13	buf	String
    //   276	39	14	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   13	207	210	com/jcraft/jsch/JSchException
    //   13	207	210	java/io/IOException
    //   13	237	276	finally
  }
  
  public static void main(String[] args)
  {
    String exec = exec("104.193.92.177", "root", "hellobc@hd2015", 22, 
      "service tomcat LotteryServer stop");
    System.out.println(exec);
  }
}
