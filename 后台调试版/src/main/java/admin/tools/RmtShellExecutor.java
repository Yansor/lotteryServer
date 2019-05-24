package admin.tools;

import ch.ethz.ssh2.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class RmtShellExecutor
{
  private Connection conn;
  private String ip;
  private String usr;
  private String psword;
  private String charset = Charset.defaultCharset().toString();
  private static final int TIME_OUT = 60000;
  
  public RmtShellExecutor(String ip, String user, String passwd)
  {
    this.ip = ip;
    this.usr = user;
    this.psword = passwd;
  }
  
  private boolean login()
    throws IOException
  {
    this.conn = new Connection(this.ip);
    this.conn.connect();
    return this.conn.authenticateWithPassword(this.usr, this.psword);
  }
  
  /* Error */
  public int execNoResultMessage(String cmds)
  {
    // Byte code:
    //   0: iconst_m1
    //   1: istore_2
    //   2: aload_0
    //   3: invokespecial 68	admin/tools/RmtShellExecutor:login	()Z
    //   6: ifeq +37 -> 43
    //   9: aload_0
    //   10: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   13: invokevirtual 70	ch/ethz/ssh2/Connection:openSession	()Lch/ethz/ssh2/Session;
    //   16: astore_3
    //   17: aload_3
    //   18: aload_1
    //   19: invokevirtual 74	ch/ethz/ssh2/Session:execCommand	(Ljava/lang/String;)V
    //   22: aload_3
    //   23: bipush 32
    //   25: ldc2_w 79
    //   28: invokevirtual 81	ch/ethz/ssh2/Session:waitForCondition	(IJ)I
    //   31: pop
    //   32: aload_3
    //   33: invokevirtual 85	ch/ethz/ssh2/Session:getExitStatus	()Ljava/lang/Integer;
    //   36: invokevirtual 89	java/lang/Integer:intValue	()I
    //   39: istore_2
    //   40: goto +58 -> 98
    //   43: getstatic 95	java/lang/System:out	Ljava/io/PrintStream;
    //   46: new 101	java/lang/StringBuilder
    //   49: dup
    //   50: ldc 103
    //   52: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   55: aload_0
    //   56: getfield 34	admin/tools/RmtShellExecutor:ip	Ljava/lang/String;
    //   59: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: invokevirtual 110	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: invokevirtual 111	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   68: goto +30 -> 98
    //   71: astore_3
    //   72: aload_3
    //   73: invokevirtual 116	java/io/IOException:printStackTrace	()V
    //   76: goto +22 -> 98
    //   79: astore 4
    //   81: aload_0
    //   82: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   85: ifnull +10 -> 95
    //   88: aload_0
    //   89: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   92: invokevirtual 119	ch/ethz/ssh2/Connection:close	()V
    //   95: aload 4
    //   97: athrow
    //   98: aload_0
    //   99: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   102: ifnull +10 -> 112
    //   105: aload_0
    //   106: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   109: invokevirtual 119	ch/ethz/ssh2/Connection:close	()V
    //   112: iload_2
    //   113: ireturn
    // Line number table:
    //   Java source line #64	-> byte code offset #0
    //   Java source line #67	-> byte code offset #2
    //   Java source line #69	-> byte code offset #9
    //   Java source line #71	-> byte code offset #17
    //   Java source line #77	-> byte code offset #22
    //   Java source line #78	-> byte code offset #25
    //   Java source line #77	-> byte code offset #28
    //   Java source line #79	-> byte code offset #32
    //   Java source line #80	-> byte code offset #40
    //   Java source line #81	-> byte code offset #43
    //   Java source line #83	-> byte code offset #68
    //   Java source line #84	-> byte code offset #72
    //   Java source line #86	-> byte code offset #76
    //   Java source line #87	-> byte code offset #81
    //   Java source line #88	-> byte code offset #88
    //   Java source line #90	-> byte code offset #95
    //   Java source line #87	-> byte code offset #98
    //   Java source line #88	-> byte code offset #105
    //   Java source line #91	-> byte code offset #112
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	114	0	this	RmtShellExecutor
    //   0	114	1	cmds	String
    //   1	112	2	ret	int
    //   16	17	3	session	ch.ethz.ssh2.Session
    //   71	2	3	e	IOException
    //   79	17	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	68	71	java/io/IOException
    //   2	79	79	finally
    return  0 ;
  }
  
  /* Error */
  public String execCommand(String cmds, boolean retMessage)
  {
    // Byte code:
    //   0: ldc -122
    //   2: astore_3
    //   3: ldc -120
    //   5: astore 4
    //   7: iconst_m1
    //   8: istore 5
    //   10: aload_0
    //   11: invokespecial 68	admin/tools/RmtShellExecutor:login	()Z
    //   14: ifeq +175 -> 189
    //   17: aload_0
    //   18: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   21: invokevirtual 70	ch/ethz/ssh2/Connection:openSession	()Lch/ethz/ssh2/Session;
    //   24: astore 6
    //   26: aload 6
    //   28: ifnull +201 -> 229
    //   31: aload 6
    //   33: aload_1
    //   34: invokevirtual 74	ch/ethz/ssh2/Session:execCommand	(Ljava/lang/String;)V
    //   37: iload_2
    //   38: ifeq +29 -> 67
    //   41: new 138	ch/ethz/ssh2/StreamGobbler
    //   44: dup
    //   45: aload 6
    //   47: invokevirtual 140	ch/ethz/ssh2/Session:getStdout	()Ljava/io/InputStream;
    //   50: invokespecial 144	ch/ethz/ssh2/StreamGobbler:<init>	(Ljava/io/InputStream;)V
    //   53: astore 7
    //   55: aload_0
    //   56: aload 7
    //   58: aload_0
    //   59: getfield 32	admin/tools/RmtShellExecutor:charset	Ljava/lang/String;
    //   62: invokevirtual 147	admin/tools/RmtShellExecutor:processStream	(Ljava/io/InputStream;Ljava/lang/String;)Ljava/lang/String;
    //   65: astore 4
    //   67: aload 6
    //   69: bipush 32
    //   71: ldc2_w 79
    //   74: invokevirtual 81	ch/ethz/ssh2/Session:waitForCondition	(IJ)I
    //   77: pop
    //   78: aload 6
    //   80: invokevirtual 85	ch/ethz/ssh2/Session:getExitStatus	()Ljava/lang/Integer;
    //   83: invokevirtual 89	java/lang/Integer:intValue	()I
    //   86: istore 5
    //   88: iload_2
    //   89: ifeq +81 -> 170
    //   92: iload 5
    //   94: ifne +135 -> 229
    //   97: aload 4
    //   99: ifnull +20 -> 119
    //   102: aload 4
    //   104: ldc -105
    //   106: invokevirtual 153	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   109: ifeq +10 -> 119
    //   112: ldc -99
    //   114: astore 4
    //   116: goto +113 -> 229
    //   119: aload 4
    //   121: ifnull +20 -> 141
    //   124: aload 4
    //   126: ldc -97
    //   128: invokevirtual 153	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   131: ifeq +10 -> 141
    //   134: ldc -95
    //   136: astore 4
    //   138: goto +91 -> 229
    //   141: aload 4
    //   143: ifnull +20 -> 163
    //   146: aload 4
    //   148: ldc -93
    //   150: invokevirtual 153	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   153: ifeq +10 -> 163
    //   156: ldc -91
    //   158: astore 4
    //   160: goto +69 -> 229
    //   163: ldc -89
    //   165: astore 4
    //   167: goto +62 -> 229
    //   170: iload 5
    //   172: ifne +10 -> 182
    //   175: ldc -87
    //   177: astore 4
    //   179: goto +50 -> 229
    //   182: ldc -85
    //   184: astore 4
    //   186: goto +43 -> 229
    //   189: ldc -83
    //   191: astore 4
    //   193: goto +36 -> 229
    //   196: astore 6
    //   198: aload 6
    //   200: invokevirtual 175	java/lang/Exception:printStackTrace	()V
    //   203: ldc -78
    //   205: astore 4
    //   207: goto +22 -> 229
    //   210: astore 8
    //   212: aload_0
    //   213: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   216: ifnull +10 -> 226
    //   219: aload_0
    //   220: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   223: invokevirtual 119	ch/ethz/ssh2/Connection:close	()V
    //   226: aload 8
    //   228: athrow
    //   229: aload_0
    //   230: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   233: ifnull +10 -> 243
    //   236: aload_0
    //   237: getfield 56	admin/tools/RmtShellExecutor:conn	Lch/ethz/ssh2/Connection;
    //   240: invokevirtual 119	ch/ethz/ssh2/Connection:close	()V
    //   243: new 101	java/lang/StringBuilder
    //   246: dup
    //   247: ldc -76
    //   249: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   252: iload 5
    //   254: invokevirtual 182	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   257: ldc -71
    //   259: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: aload 4
    //   264: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   267: ldc -69
    //   269: invokevirtual 106	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: invokevirtual 110	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   275: astore_3
    //   276: getstatic 95	java/lang/System:out	Ljava/io/PrintStream;
    //   279: aload_3
    //   280: invokevirtual 111	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   283: aload_3
    //   284: areturn
    // Line number table:
    //   Java source line #101	-> byte code offset #0
    //   Java source line #102	-> byte code offset #3
    //   Java source line #103	-> byte code offset #7
    //   Java source line #106	-> byte code offset #10
    //   Java source line #108	-> byte code offset #17
    //   Java source line #109	-> byte code offset #26
    //   Java source line #111	-> byte code offset #31
    //   Java source line #112	-> byte code offset #37
    //   Java source line #113	-> byte code offset #41
    //   Java source line #114	-> byte code offset #45
    //   Java source line #113	-> byte code offset #50
    //   Java source line #115	-> byte code offset #55
    //   Java source line #117	-> byte code offset #67
    //   Java source line #118	-> byte code offset #71
    //   Java source line #117	-> byte code offset #74
    //   Java source line #119	-> byte code offset #78
    //   Java source line #120	-> byte code offset #88
    //   Java source line #121	-> byte code offset #92
    //   Java source line #122	-> byte code offset #97
    //   Java source line #123	-> byte code offset #102
    //   Java source line #124	-> byte code offset #112
    //   Java source line #125	-> byte code offset #116
    //   Java source line #126	-> byte code offset #124
    //   Java source line #127	-> byte code offset #134
    //   Java source line #128	-> byte code offset #138
    //   Java source line #129	-> byte code offset #146
    //   Java source line #130	-> byte code offset #156
    //   Java source line #131	-> byte code offset #160
    //   Java source line #132	-> byte code offset #163
    //   Java source line #135	-> byte code offset #167
    //   Java source line #136	-> byte code offset #170
    //   Java source line #137	-> byte code offset #175
    //   Java source line #138	-> byte code offset #179
    //   Java source line #139	-> byte code offset #182
    //   Java source line #143	-> byte code offset #186
    //   Java source line #144	-> byte code offset #189
    //   Java source line #146	-> byte code offset #193
    //   Java source line #147	-> byte code offset #198
    //   Java source line #148	-> byte code offset #203
    //   Java source line #150	-> byte code offset #207
    //   Java source line #151	-> byte code offset #212
    //   Java source line #152	-> byte code offset #219
    //   Java source line #154	-> byte code offset #226
    //   Java source line #151	-> byte code offset #229
    //   Java source line #152	-> byte code offset #236
    //   Java source line #155	-> byte code offset #243
    //   Java source line #156	-> byte code offset #276
    //   Java source line #157	-> byte code offset #283
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	285	0	this	RmtShellExecutor
    //   0	285	1	cmds	String
    //   0	285	2	retMessage	boolean
    //   2	282	3	result	String
    //   5	258	4	outStr	String
    //   8	245	5	ret	int
    //   24	55	6	session	ch.ethz.ssh2.Session
    //   196	3	6	e	Exception
    //   53	4	7	stdOut	ch.ethz.ssh2.StreamGobbler
    //   210	17	8	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   10	193	196	java/lang/Exception
    //   10	210	210	finally
    return null;
  }
  
  public String processStream(InputStream in, String charset)
    throws Exception
  {
    byte[] buf = new byte['Ѐ'];
    StringBuilder sb = new StringBuilder();
    while (in.read(buf) != -1) {
      sb.append(new String(buf, charset));
    }
    return sb.toString();
  }
  
  public static void main(String[] args)
    throws Exception
  {
    RmtShellExecutor exe = new RmtShellExecutor("104.193.92.177", "root", 
      "hellobc@hd2015");
    
    exe.execCommand("service tomcat LotteryServer start", false);
  }
}
