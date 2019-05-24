package javautils.ip;

import java.io.File;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class IpUtil
{
  public static void main(String[] args)
  {
    load("D:\\17monipdb.dat");
    
    System.out.println(Arrays.toString(find("180.191.100.73")));
  }
  
  public static boolean enableFileWatch = false;
  private static int offset;
  private static int[] index = new int['Ā'];
  private static ByteBuffer dataBuffer;
  private static ByteBuffer indexBuffer;
  private static Long lastModifyTime = Long.valueOf(0L);
  private static File ipFile;
  private static ReentrantLock lock = new ReentrantLock();
  
  public static void load(String filename)
  {
    ipFile = new File(filename);
    load();
    if (enableFileWatch) {
      watch();
    }
  }

  public static String[] find(String ip) {
    int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
    long ip2long_value  = ip2long(ip);
    int start = index[ip_prefix_value];
    int max_comp_len = offset - 1028;
    long index_offset = -1;
    int index_length = -1;
    byte b = 0;
    for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
      if (int2long(indexBuffer.getInt(start)) >= ip2long_value) {
        index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
        index_length = 0xFF & indexBuffer.get(start + 7);
        break;
      }
    }

    byte[] areaBytes;
    lock.lock();
    try {
      dataBuffer.position(offset + (int) index_offset - 1024);
      areaBytes = new byte[index_length];
      dataBuffer.get(areaBytes, 0, index_length);
    } finally {
      lock.unlock();
    }

    return new String(areaBytes).split("\t");
  }

  private static void watch() {
    Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        long time = ipFile.lastModified();
        if (time > lastModifyTime) {
          lastModifyTime = time;
          load();
        }
      }
    }, 1000L, 5000L, TimeUnit.MILLISECONDS);
  }
  
  /* Error */
  private static void load()
  {
    // Byte code:
    //   0: getstatic 84	javautils/ip/IpUtil:ipFile	Ljava/io/File;
    //   3: invokevirtual 203	java/io/File:lastModified	()J
    //   6: invokestatic 27	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   9: putstatic 33	javautils/ip/IpUtil:lastModifyTime	Ljava/lang/Long;
    //   12: aconst_null
    //   13: astore_0
    //   14: getstatic 40	javautils/ip/IpUtil:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   17: invokevirtual 141	java/util/concurrent/locks/ReentrantLock:lock	()V
    //   20: getstatic 84	javautils/ip/IpUtil:ipFile	Ljava/io/File;
    //   23: invokevirtual 207	java/io/File:length	()J
    //   26: invokestatic 27	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   29: invokevirtual 210	java/lang/Long:intValue	()I
    //   32: invokestatic 211	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   35: putstatic 143	javautils/ip/IpUtil:dataBuffer	Ljava/nio/ByteBuffer;
    //   38: new 215	java/io/FileInputStream
    //   41: dup
    //   42: getstatic 84	javautils/ip/IpUtil:ipFile	Ljava/io/File;
    //   45: invokespecial 217	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   48: astore_0
    //   49: sipush 4096
    //   52: newarray <illegal type>
    //   54: astore_2
    //   55: goto +19 -> 74
    //   58: aload_0
    //   59: aload_2
    //   60: invokevirtual 220	java/io/FileInputStream:read	([B)I
    //   63: istore_1
    //   64: getstatic 143	javautils/ip/IpUtil:dataBuffer	Ljava/nio/ByteBuffer;
    //   67: aload_2
    //   68: iconst_0
    //   69: iload_1
    //   70: invokevirtual 224	java/nio/ByteBuffer:put	([BII)Ljava/nio/ByteBuffer;
    //   73: pop
    //   74: aload_0
    //   75: invokevirtual 227	java/io/FileInputStream:available	()I
    //   78: ifgt -20 -> 58
    //   81: getstatic 143	javautils/ip/IpUtil:dataBuffer	Ljava/nio/ByteBuffer;
    //   84: iconst_0
    //   85: invokevirtual 145	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   88: pop
    //   89: getstatic 143	javautils/ip/IpUtil:dataBuffer	Ljava/nio/ByteBuffer;
    //   92: invokevirtual 230	java/nio/ByteBuffer:getInt	()I
    //   95: istore_3
    //   96: iload_3
    //   97: newarray <illegal type>
    //   99: astore 4
    //   101: getstatic 143	javautils/ip/IpUtil:dataBuffer	Ljava/nio/ByteBuffer;
    //   104: aload 4
    //   106: iconst_0
    //   107: iload_3
    //   108: iconst_4
    //   109: isub
    //   110: invokevirtual 149	java/nio/ByteBuffer:get	([BII)Ljava/nio/ByteBuffer;
    //   113: pop
    //   114: aload 4
    //   116: invokestatic 232	java/nio/ByteBuffer:wrap	([B)Ljava/nio/ByteBuffer;
    //   119: putstatic 121	javautils/ip/IpUtil:indexBuffer	Ljava/nio/ByteBuffer;
    //   122: getstatic 121	javautils/ip/IpUtil:indexBuffer	Ljava/nio/ByteBuffer;
    //   125: getstatic 236	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
    //   128: invokevirtual 242	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   131: pop
    //   132: iload_3
    //   133: putstatic 117	javautils/ip/IpUtil:offset	I
    //   136: iconst_0
    //   137: istore 5
    //   139: goto +17 -> 156
    //   142: getstatic 25	javautils/ip/IpUtil:index	[I
    //   145: iload 5
    //   147: iconst_1
    //   148: isub
    //   149: getstatic 121	javautils/ip/IpUtil:indexBuffer	Ljava/nio/ByteBuffer;
    //   152: invokevirtual 230	java/nio/ByteBuffer:getInt	()I
    //   155: iastore
    //   156: iload 5
    //   158: iinc 5 1
    //   161: sipush 256
    //   164: if_icmplt -22 -> 142
    //   167: getstatic 121	javautils/ip/IpUtil:indexBuffer	Ljava/nio/ByteBuffer;
    //   170: getstatic 246	java/nio/ByteOrder:BIG_ENDIAN	Ljava/nio/ByteOrder;
    //   173: invokevirtual 242	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   176: pop
    //   177: goto +50 -> 227
    //   180: astore_1
    //   181: aload_0
    //   182: ifnull +12 -> 194
    //   185: aload_0
    //   186: invokevirtual 249	java/io/FileInputStream:close	()V
    //   189: goto +5 -> 194
    //   192: astore 7
    //   194: getstatic 40	javautils/ip/IpUtil:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   197: invokevirtual 152	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   200: goto +46 -> 246
    //   203: astore 6
    //   205: aload_0
    //   206: ifnull +12 -> 218
    //   209: aload_0
    //   210: invokevirtual 249	java/io/FileInputStream:close	()V
    //   213: goto +5 -> 218
    //   216: astore 7
    //   218: getstatic 40	javautils/ip/IpUtil:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   221: invokevirtual 152	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   224: aload 6
    //   226: athrow
    //   227: aload_0
    //   228: ifnull +12 -> 240
    //   231: aload_0
    //   232: invokevirtual 249	java/io/FileInputStream:close	()V
    //   235: goto +5 -> 240
    //   238: astore 7
    //   240: getstatic 40	javautils/ip/IpUtil:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   243: invokevirtual 152	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   246: return
    // Line number table:
    //   Java source line #83	-> byte code offset #0
    //   Java source line #84	-> byte code offset #12
    //   Java source line #85	-> byte code offset #14
    //   Java source line #87	-> byte code offset #20
    //   Java source line #88	-> byte code offset #38
    //   Java source line #90	-> byte code offset #49
    //   Java source line #91	-> byte code offset #55
    //   Java source line #92	-> byte code offset #58
    //   Java source line #93	-> byte code offset #64
    //   Java source line #91	-> byte code offset #74
    //   Java source line #95	-> byte code offset #81
    //   Java source line #96	-> byte code offset #89
    //   Java source line #97	-> byte code offset #96
    //   Java source line #98	-> byte code offset #101
    //   Java source line #99	-> byte code offset #114
    //   Java source line #100	-> byte code offset #122
    //   Java source line #101	-> byte code offset #132
    //   Java source line #103	-> byte code offset #136
    //   Java source line #104	-> byte code offset #139
    //   Java source line #105	-> byte code offset #142
    //   Java source line #104	-> byte code offset #156
    //   Java source line #107	-> byte code offset #167
    //   Java source line #108	-> byte code offset #177
    //   Java source line #112	-> byte code offset #181
    //   Java source line #113	-> byte code offset #185
    //   Java source line #115	-> byte code offset #189
    //   Java source line #116	-> byte code offset #194
    //   Java source line #110	-> byte code offset #203
    //   Java source line #112	-> byte code offset #205
    //   Java source line #113	-> byte code offset #209
    //   Java source line #115	-> byte code offset #213
    //   Java source line #116	-> byte code offset #218
    //   Java source line #117	-> byte code offset #224
    //   Java source line #112	-> byte code offset #227
    //   Java source line #113	-> byte code offset #231
    //   Java source line #115	-> byte code offset #235
    //   Java source line #116	-> byte code offset #240
    //   Java source line #118	-> byte code offset #246
    // Local variable table:
    //   start	length	slot	name	signature
    //   13	219	0	fin	java.io.FileInputStream
    //   63	7	1	readBytesLength	int
    //   180	1	1	localIOException	java.io.IOException
    //   54	14	2	chunk	byte[]
    //   95	38	3	indexLength	int
    //   99	16	4	indexBytes	byte[]
    //   137	20	5	loop	int
    //   203	22	6	localObject	Object
    //   192	1	7	localIOException1	java.io.IOException
    //   216	1	7	localIOException2	java.io.IOException
    //   238	1	7	localIOException3	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   20	177	180	java/io/IOException
    //   181	189	192	java/io/IOException
    //   20	181	203	finally
    //   205	213	216	java/io/IOException
    //   227	235	238	java/io/IOException
  }
  
  private static long bytesToLong(byte a, byte b, byte c, byte d)
  {
    return int2long((a & 0xFF) << 24 | (b & 0xFF) << 16 | (c & 0xFF) << 8 | d & 0xFF);
  }
  
  private static int str2Ip(String ip)
  {
    String[] ss = ip.split("\\.");
    
    int a = Integer.parseInt(ss[0]);
    int b = Integer.parseInt(ss[1]);
    int c = Integer.parseInt(ss[2]);
    int d = Integer.parseInt(ss[3]);
    return a << 24 | b << 16 | c << 8 | d;
  }
  
  private static long ip2long(String ip)
  {
    return int2long(str2Ip(ip));
  }
  
  private static long int2long(int i)
  {
    long l = i & 0x7FFFFFFF;
    if (i < 0) {
      l |= 0x80000000;
    }
    return l;
  }
}
