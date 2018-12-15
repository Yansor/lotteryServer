package javautils.excel;

import java.io.File;
import java.util.Hashtable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtil
{
  private static Hashtable<Object, Integer> chHt = new Hashtable();
  private static ExcelUtil instance;
  
  static
  {
    char[] ch = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    for (int i = 0; i < ch.length; i++) {
      chHt.put(Character.valueOf(ch[i]), Integer.valueOf(i));
    }
  }
  
  private static synchronized void synInit()
  {
    if (instance == null) {
      instance = new ExcelUtil();
    }
  }
  
  public static ExcelUtil getInstance()
  {
    if (instance == null) {
      synInit();
    }
    return instance;
  }
  
  public HSSFWorkbook open(String filePath)
  {
    File file = new File(filePath);
    return read(file);
  }
  
  /* Error */
  public HSSFWorkbook read(File file)
  {
    return null;
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aconst_null
    //   5: astore 4
    //   7: new 69	java/io/FileInputStream
    //   10: dup
    //   11: aload_1
    //   12: invokespecial 71	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   15: astore_3
    //   16: new 74	org/apache/poi/poifs/filesystem/POIFSFileSystem
    //   19: dup
    //   20: aload_3
    //   21: invokespecial 76	org/apache/poi/poifs/filesystem/POIFSFileSystem:<init>	(Ljava/io/InputStream;)V
    //   24: astore 4
    //   26: new 79	org/apache/poi/hssf/usermodel/HSSFWorkbook
    //   29: dup
    //   30: aload 4
    //   32: invokespecial 81	org/apache/poi/hssf/usermodel/HSSFWorkbook:<init>	(Lorg/apache/poi/poifs/filesystem/POIFSFileSystem;)V
    //   35: astore_2
    //   36: goto +44 -> 80
    //   39: astore 5
    //   41: aload 5
    //   43: invokevirtual 84	java/lang/Exception:printStackTrace	()V
    //   46: aload_3
    //   47: ifnull +46 -> 93
    //   50: aload_3
    //   51: invokevirtual 89	java/io/FileInputStream:close	()V
    //   54: goto +39 -> 93
    //   57: astore 7
    //   59: goto +34 -> 93
    //   62: astore 6
    //   64: aload_3
    //   65: ifnull +12 -> 77
    //   68: aload_3
    //   69: invokevirtual 89	java/io/FileInputStream:close	()V
    //   72: goto +5 -> 77
    //   75: astore 7
    //   77: aload 6
    //   79: athrow
    //   80: aload_3
    //   81: ifnull +12 -> 93
    //   84: aload_3
    //   85: invokevirtual 89	java/io/FileInputStream:close	()V
    //   88: goto +5 -> 93
    //   91: astore 7
    //   93: aload_2
    //   94: areturn
    // Line number table:
    //   Java source line #64	-> byte code offset #0
    //   Java source line #65	-> byte code offset #2
    //   Java source line #66	-> byte code offset #4
    //   Java source line #68	-> byte code offset #7
    //   Java source line #69	-> byte code offset #16
    //   Java source line #70	-> byte code offset #26
    //   Java source line #71	-> byte code offset #36
    //   Java source line #72	-> byte code offset #41
    //   Java source line #74	-> byte code offset #46
    //   Java source line #76	-> byte code offset #50
    //   Java source line #77	-> byte code offset #54
    //   Java source line #73	-> byte code offset #62
    //   Java source line #74	-> byte code offset #64
    //   Java source line #76	-> byte code offset #68
    //   Java source line #77	-> byte code offset #72
    //   Java source line #80	-> byte code offset #77
    //   Java source line #74	-> byte code offset #80
    //   Java source line #76	-> byte code offset #84
    //   Java source line #77	-> byte code offset #88
    //   Java source line #81	-> byte code offset #93
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	95	0	this	ExcelUtil
    //   0	95	1	file	File
    //   1	93	2	workbook	HSSFWorkbook
    //   3	82	3	is	java.io.FileInputStream
    //   5	26	4	fs	org.apache.poi.poifs.filesystem.POIFSFileSystem
    //   39	3	5	e	Exception
    //   62	16	6	localObject	Object
    //   57	1	7	localException1	Exception
    //   75	1	7	localException2	Exception
    //   91	1	7	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   7	36	39	java/lang/Exception
    //   50	54	57	java/lang/Exception
    //   7	46	62	finally
    //   68	72	75	java/lang/Exception
    //   84	88	91	java/lang/Exception
  }
  
  public boolean save(File file, HSSFWorkbook workbook)
  {
    if (file != null)
    {
      String filePath = file.getPath();
      return write(filePath, workbook);
    }
    return false;
  }
  
  public boolean saveAs(String filePath, String fileName, HSSFWorkbook workbook)
  {
    File fileDirs = new File(filePath);
    if (!fileDirs.exists()) {
      fileDirs.mkdirs();
    }
    return write(filePath + fileName, workbook);
  }
  
  /* Error */
  private boolean write(String filePath, HSSFWorkbook workbook)
  {
    return false;
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 138	java/io/FileOutputStream
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 140	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   13: astore 4
    //   15: aload_2
    //   16: aload 4
    //   18: invokevirtual 141	org/apache/poi/hssf/usermodel/HSSFWorkbook:write	(Ljava/io/OutputStream;)V
    //   21: iconst_1
    //   22: istore_3
    //   23: goto +58 -> 81
    //   26: astore 5
    //   28: aload 5
    //   30: invokevirtual 84	java/lang/Exception:printStackTrace	()V
    //   33: aload 4
    //   35: ifnull +66 -> 101
    //   38: aload 4
    //   40: invokevirtual 144	java/io/FileOutputStream:flush	()V
    //   43: aload 4
    //   45: invokevirtual 147	java/io/FileOutputStream:close	()V
    //   48: goto +53 -> 101
    //   51: astore 7
    //   53: goto +48 -> 101
    //   56: astore 6
    //   58: aload 4
    //   60: ifnull +18 -> 78
    //   63: aload 4
    //   65: invokevirtual 144	java/io/FileOutputStream:flush	()V
    //   68: aload 4
    //   70: invokevirtual 147	java/io/FileOutputStream:close	()V
    //   73: goto +5 -> 78
    //   76: astore 7
    //   78: aload 6
    //   80: athrow
    //   81: aload 4
    //   83: ifnull +18 -> 101
    //   86: aload 4
    //   88: invokevirtual 144	java/io/FileOutputStream:flush	()V
    //   91: aload 4
    //   93: invokevirtual 147	java/io/FileOutputStream:close	()V
    //   96: goto +5 -> 101
    //   99: astore 7
    //   101: iload_3
    //   102: ireturn
    // Line number table:
    //   Java source line #120	-> byte code offset #0
    //   Java source line #121	-> byte code offset #2
    //   Java source line #123	-> byte code offset #5
    //   Java source line #124	-> byte code offset #15
    //   Java source line #125	-> byte code offset #21
    //   Java source line #126	-> byte code offset #23
    //   Java source line #127	-> byte code offset #28
    //   Java source line #129	-> byte code offset #33
    //   Java source line #131	-> byte code offset #38
    //   Java source line #132	-> byte code offset #43
    //   Java source line #133	-> byte code offset #48
    //   Java source line #128	-> byte code offset #56
    //   Java source line #129	-> byte code offset #58
    //   Java source line #131	-> byte code offset #63
    //   Java source line #132	-> byte code offset #68
    //   Java source line #133	-> byte code offset #73
    //   Java source line #136	-> byte code offset #78
    //   Java source line #129	-> byte code offset #81
    //   Java source line #131	-> byte code offset #86
    //   Java source line #132	-> byte code offset #91
    //   Java source line #133	-> byte code offset #96
    //   Java source line #137	-> byte code offset #101
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	103	0	this	ExcelUtil
    //   0	103	1	filePath	String
    //   0	103	2	workbook	HSSFWorkbook
    //   1	101	3	flag	boolean
    //   3	89	4	os	java.io.FileOutputStream
    //   26	3	5	e	Exception
    //   56	23	6	localObject	Object
    //   51	1	7	localException1	Exception
    //   76	1	7	localException2	Exception
    //   99	1	7	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   5	23	26	java/lang/Exception
    //   38	48	51	java/lang/Exception
    //   5	33	56	finally
    //   63	73	76	java/lang/Exception
    //   86	96	99	java/lang/Exception
  }
  
  public static int getRowNum(HSSFSheet sheet)
  {
    return sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
  }
  
  public static HSSFRow getRow(HSSFSheet sheet, int rowNum)
  {
    HSSFRow row = null;
    if (rowNum > 0)
    {
      int rowIndex = rowNum - 1;
      row = sheet.getRow(rowIndex);
      if (row == null) {
        row = sheet.createRow(rowIndex);
      }
    }
    return row;
  }
  
  public static HSSFCell getCell(HSSFRow row, String cellName)
  {
    int cellIndex = getCellIndex(cellName);
    return row.getCell(cellIndex, Row.CREATE_NULL_AS_BLANK);
  }
  
  public static String getStringCellValue(HSSFRow row, String cellName)
  {
    int cellIndex = getCellIndex(cellName);
    HSSFCell cell = row.getCell(cellIndex);
    cell.setCellType(1);
    return cell.getStringCellValue();
  }
  
  public static int getCellIndex(String cellName)
  {
    int cellIndex = -1;
    char[] c = cellName.toCharArray();
    if (c.length == 1) {
      cellIndex = ((Integer)chHt.get(Character.valueOf(c[0]))).intValue();
    }
    if (c.length == 2) {
      cellIndex = (((Integer)chHt.get(Character.valueOf(c[0]))).intValue() + 1) * 26 + (((Integer)chHt.get(Character.valueOf(c[1]))).intValue() + 1) - 1;
    }
    return cellIndex;
  }
}
