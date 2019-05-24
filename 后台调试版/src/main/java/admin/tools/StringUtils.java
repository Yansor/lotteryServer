package admin.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringUtils
{
  public static boolean isEmpty(String input)
  {
    if ((input == null) || ("".equals(input.trim()))) {
      return true;
    }
    for (int i = 0; i < input.length(); i++)
    {
      char c = input.charAt(i);
      if ((c != ' ') && (c != '\t') && (c != '\r') && (c != '\n')) {
        return false;
      }
    }
    return true;
  }
  
  public static int toInt(String str, int defValue)
  {
    try
    {
      return Integer.parseInt(str);
    }
    catch (Exception localException) {}
    return defValue;
  }
  
  public static Double toDouble(String str, Double defValue)
  {
    try
    {
      return Double.valueOf(Double.parseDouble(str));
    }
    catch (Exception localException) {}
    return defValue;
  }
  
  public static int toInt(Object obj)
  {
    if (obj == null) {
      return 0;
    }
    return toInt(obj.toString(), 0);
  }
  
  public static long toLong(String obj)
  {
    try
    {
      return Long.parseLong(obj);
    }
    catch (Exception localException) {}
    return 0L;
  }
  
  public static boolean toBool(String b)
  {
    try
    {
      return Boolean.parseBoolean(b);
    }
    catch (Exception localException) {}
    return false;
  }
  
  /* Error */
  public static String toConvertString(java.io.InputStream is)
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String temp = "";
    String line = "";

      try {
        while(((line = br.readLine()) != null)){
          temp+=line;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }


    return temp;
    // Byte code:
    //   0: new 92	java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial 94	java/lang/StringBuffer:<init>	()V
    //   7: astore_1
    //   8: new 95	java/io/InputStreamReader
    //   11: dup
    //   12: aload_0
    //   13: invokespecial 97	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   16: astore_2
    //   17: new 100	java/io/BufferedReader
    //   20: dup
    //   21: aload_2
    //   22: invokespecial 102	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   25: astore_3
    //   26: aload_3
    //   27: invokevirtual 105	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   30: astore 4
    //   32: goto +16 -> 48
    //   35: aload_1
    //   36: aload 4
    //   38: invokevirtual 108	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   41: pop
    //   42: aload_3
    //   43: invokevirtual 105	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   46: astore 4
    //   48: aload 4
    //   50: ifnonnull -15 -> 35
    //   53: goto +92 -> 145
    //   56: astore 4
    //   58: aload 4
    //   60: invokevirtual 112	java/io/IOException:printStackTrace	()V
    //   63: aload_2
    //   64: ifnull +11 -> 75
    //   67: aload_2
    //   68: invokevirtual 117	java/io/InputStreamReader:close	()V
    //   71: aload_2
    //   72: invokevirtual 117	java/io/InputStreamReader:close	()V
    //   75: aload_3
    //   76: ifnull +9 -> 85
    //   79: aload_3
    //   80: invokevirtual 120	java/io/BufferedReader:close	()V
    //   83: aconst_null
    //   84: astore_3
    //   85: aload_0
    //   86: ifnull +96 -> 182
    //   89: aload_0
    //   90: invokevirtual 121	java/io/InputStream:close	()V
    //   93: aconst_null
    //   94: astore_0
    //   95: goto +87 -> 182
    //   98: astore 6
    //   100: goto +82 -> 182
    //   103: astore 5
    //   105: aload_2
    //   106: ifnull +11 -> 117
    //   109: aload_2
    //   110: invokevirtual 117	java/io/InputStreamReader:close	()V
    //   113: aload_2
    //   114: invokevirtual 117	java/io/InputStreamReader:close	()V
    //   117: aload_3
    //   118: ifnull +9 -> 127
    //   121: aload_3
    //   122: invokevirtual 120	java/io/BufferedReader:close	()V
    //   125: aconst_null
    //   126: astore_3
    //   127: aload_0
    //   128: ifnull +14 -> 142
    //   131: aload_0
    //   132: invokevirtual 121	java/io/InputStream:close	()V
    //   135: aconst_null
    //   136: astore_0
    //   137: goto +5 -> 142
    //   140: astore 6
    //   142: aload 5
    //   144: athrow
    //   145: aload_2
    //   146: ifnull +11 -> 157
    //   149: aload_2
    //   150: invokevirtual 117	java/io/InputStreamReader:close	()V
    //   153: aload_2
    //   154: invokevirtual 117	java/io/InputStreamReader:close	()V
    //   157: aload_3
    //   158: ifnull +9 -> 167
    //   161: aload_3
    //   162: invokevirtual 120	java/io/BufferedReader:close	()V
    //   165: aconst_null
    //   166: astore_3
    //   167: aload_0
    //   168: ifnull +14 -> 182
    //   171: aload_0
    //   172: invokevirtual 121	java/io/InputStream:close	()V
    //   175: aconst_null
    //   176: astore_0
    //   177: goto +5 -> 182
    //   180: astore 6
    //   182: aload_1
    //   183: invokevirtual 124	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   186: areturn
    // Line number table:
    //   Java source line #104	-> byte code offset #0
    //   Java source line #105	-> byte code offset #8
    //   Java source line #106	-> byte code offset #17
    //   Java source line #109	-> byte code offset #26
    //   Java source line #110	-> byte code offset #32
    //   Java source line #111	-> byte code offset #35
    //   Java source line #112	-> byte code offset #42
    //   Java source line #110	-> byte code offset #48
    //   Java source line #114	-> byte code offset #53
    //   Java source line #115	-> byte code offset #58
    //   Java source line #118	-> byte code offset #63
    //   Java source line #119	-> byte code offset #67
    //   Java source line #120	-> byte code offset #71
    //   Java source line #122	-> byte code offset #75
    //   Java source line #123	-> byte code offset #79
    //   Java source line #124	-> byte code offset #83
    //   Java source line #126	-> byte code offset #85
    //   Java source line #127	-> byte code offset #89
    //   Java source line #128	-> byte code offset #93
    //   Java source line #130	-> byte code offset #95
    //   Java source line #116	-> byte code offset #103
    //   Java source line #118	-> byte code offset #105
    //   Java source line #119	-> byte code offset #109
    //   Java source line #120	-> byte code offset #113
    //   Java source line #122	-> byte code offset #117
    //   Java source line #123	-> byte code offset #121
    //   Java source line #124	-> byte code offset #125
    //   Java source line #126	-> byte code offset #127
    //   Java source line #127	-> byte code offset #131
    //   Java source line #128	-> byte code offset #135
    //   Java source line #130	-> byte code offset #137
    //   Java source line #132	-> byte code offset #142
    //   Java source line #118	-> byte code offset #145
    //   Java source line #119	-> byte code offset #149
    //   Java source line #120	-> byte code offset #153
    //   Java source line #122	-> byte code offset #157
    //   Java source line #123	-> byte code offset #161
    //   Java source line #124	-> byte code offset #165
    //   Java source line #126	-> byte code offset #167
    //   Java source line #127	-> byte code offset #171
    //   Java source line #128	-> byte code offset #175
    //   Java source line #130	-> byte code offset #177
    //   Java source line #133	-> byte code offset #182
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	187	0	is	java.io.InputStream
    //   7	176	1	res	StringBuffer
    //   16	138	2	isr	java.io.InputStreamReader
    //   25	142	3	read	java.io.BufferedReader
    //   30	19	4	line	String
    //   56	3	4	e	java.io.IOException
    //   103	40	5	localObject	Object
    //   98	1	6	localIOException1	java.io.IOException
    //   140	1	6	localIOException2	java.io.IOException
    //   180	1	6	localIOException3	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   26	53	56	java/io/IOException
    //   63	95	98	java/io/IOException
    //   26	63	103	finally
    //   105	137	140	java/io/IOException
    //   145	177	180	java/io/IOException
  }
  
  public static int getSubStrCount(String str, String key)
  {
    int count = 0;
    int index = 0;
    while ((index = str.indexOf(key, index)) != -1)
    {
      index += key.length();
      count++;
    }
    return count;
  }
  
  public static String reverse(String str)
  {
    String reverseStr = new StringBuffer(str).reverse().toString();
    return reverseStr;
  }
}
