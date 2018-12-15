package javautils.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPServer
{
  private FTPClient ftpClient;
  public static final int BINARY_FILE_TYPE = 2;
  public static final int ASCII_FILE_TYPE = 0;
  
  public void connectServer(FtpConfig ftpConfig)
    throws IOException
  {
    String server = ftpConfig.getServer();
    int port = ftpConfig.getPort();
    String user = ftpConfig.getUsername();
    String password = ftpConfig.getPassword();
    String location = ftpConfig.getLocation();
    connectServer(server, port, user, password, location);
  }
  
  public void connectServer(String server, int port, String user, String password, String path)
    throws IOException
  {
    this.ftpClient = new FTPClient();
    this.ftpClient.setDataTimeout(10000);
    this.ftpClient.setConnectTimeout(10000);
    
    this.ftpClient.setDefaultTimeout(10000);
    
    this.ftpClient.connect(server, port);
    
    this.ftpClient.login(user, password);
    if ((path != null) && (path.length() != 0)) {
      this.ftpClient.changeWorkingDirectory(path);
    }
    this.ftpClient.setBufferSize(1024);
    this.ftpClient.setControlEncoding("UTF-8");
    this.ftpClient.setFileType(2);
  }
  
  public void setFileType(int fileType)
    throws IOException
  {
    this.ftpClient.setFileType(fileType);
  }
  
  public void closeServer()
    throws IOException
  {
    if ((this.ftpClient != null) && (this.ftpClient.isConnected()))
    {
      this.ftpClient.logout();
      this.ftpClient.disconnect();
    }
  }
  
  public boolean changeDirectory(String path)
    throws IOException
  {
    return this.ftpClient.changeWorkingDirectory(path);
  }
  
  public boolean createDirectory(String pathName)
    throws IOException
  {
    return this.ftpClient.makeDirectory(pathName);
  }
  
  public boolean removeDirectory(String path)
    throws IOException
  {
    return this.ftpClient.removeDirectory(path);
  }
  
  public boolean removeDirectory(String path, boolean isAll)
    throws IOException
  {
    if (!isAll) {
      return removeDirectory(path);
    }
    FTPFile[] ftpFileArr = this.ftpClient.listFiles(path);
    if ((ftpFileArr == null) || (ftpFileArr.length == 0)) {
      return removeDirectory(path);
    }
    FTPFile[] arrayOfFTPFile1;
    int j = (arrayOfFTPFile1 = ftpFileArr).length;
    for (int i = 0; i < j; i++)
    {
      FTPFile ftpFile = arrayOfFTPFile1[i];
      String name = ftpFile.getName();
      if (ftpFile.isDirectory()) {
        removeDirectory(path + "/" + name, true);
      } else if (ftpFile.isFile()) {
        deleteFile(path + "/" + name);
      } else if (!ftpFile.isSymbolicLink()) {
        ftpFile.isUnknown();
      }
    }
    return this.ftpClient.removeDirectory(path);
  }
  
  public boolean existDirectory(String path)
    throws IOException
  {
    boolean flag = false;
    FTPFile[] ftpFileArr = this.ftpClient.listFiles(path);
    FTPFile[] arrayOfFTPFile1;
    int j = (arrayOfFTPFile1 = ftpFileArr).length;
    for (int i = 0; i < j; i++)
    {
      FTPFile ftpFile = arrayOfFTPFile1[i];
      if ((ftpFile.isDirectory()) && 
        (ftpFile.getName().equalsIgnoreCase(path)))
      {
        flag = true;
        break;
      }
    }
    return flag;
  }
  
  public List<String> getFileList(String path)
    throws IOException
  {
    FTPFile[] ftpFiles = this.ftpClient.listFiles(path);
    
    List<String> retList = new ArrayList();
    if ((ftpFiles == null) || (ftpFiles.length == 0)) {
      return retList;
    }
    FTPFile[] arrayOfFTPFile1;
    int j = (arrayOfFTPFile1 = ftpFiles).length;
    for (int i = 0; i < j; i++)
    {
      FTPFile ftpFile = arrayOfFTPFile1[i];
      if (ftpFile.isFile()) {
        retList.add(ftpFile.getName());
      }
    }
    return retList;
  }
  
  public List<String> getDiretoryList(String path)
    throws IOException
  {
    FTPFile[] ftpDirectories = this.ftpClient.listDirectories(path);
    
    List<String> retList = new ArrayList();
    if ((ftpDirectories == null) || (ftpDirectories.length == 0)) {
      return retList;
    }
    FTPFile[] arrayOfFTPFile1;
    int j = (arrayOfFTPFile1 = ftpDirectories).length;
    for (int i = 0; i < j; i++)
    {
      FTPFile ftpDirectory = arrayOfFTPFile1[i];
      if (ftpDirectory.isDirectory()) {
        retList.add(ftpDirectory.getName());
      }
    }
    return retList;
  }
  
  public boolean deleteFile(String pathName)
    throws IOException
  {
    return this.ftpClient.deleteFile(pathName);
  }
  
  /* Error */
  public boolean uploadFile(String localFilePath, String remoteFileName)
    throws IOException
  {
    return false;
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 209	java/io/FileInputStream
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 211	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   13: astore 4
    //   15: aload_0
    //   16: getfield 60	javautils/ftp/FTPServer:ftpClient	Lorg/apache/commons/net/ftp/FTPClient;
    //   19: aload_2
    //   20: aload 4
    //   22: invokevirtual 212	org/apache/commons/net/ftp/FTPClient:storeFile	(Ljava/lang/String;Ljava/io/InputStream;)Z
    //   25: istore_3
    //   26: goto +38 -> 64
    //   29: astore 5
    //   31: iconst_0
    //   32: istore_3
    //   33: iload_3
    //   34: istore 7
    //   36: aload 4
    //   38: ifnull +8 -> 46
    //   41: aload 4
    //   43: invokevirtual 216	java/io/InputStream:close	()V
    //   46: iload 7
    //   48: ireturn
    //   49: astore 6
    //   51: aload 4
    //   53: ifnull +8 -> 61
    //   56: aload 4
    //   58: invokevirtual 216	java/io/InputStream:close	()V
    //   61: aload 6
    //   63: athrow
    //   64: aload 4
    //   66: ifnull +8 -> 74
    //   69: aload 4
    //   71: invokevirtual 216	java/io/InputStream:close	()V
    //   74: iload_3
    //   75: ireturn
    // Line number table:
    //   Java source line #238	-> byte code offset #0
    //   Java source line #239	-> byte code offset #2
    //   Java source line #241	-> byte code offset #5
    //   Java source line #245	-> byte code offset #15
    //   Java source line #246	-> byte code offset #26
    //   Java source line #247	-> byte code offset #31
    //   Java source line #248	-> byte code offset #33
    //   Java source line #250	-> byte code offset #36
    //   Java source line #251	-> byte code offset #41
    //   Java source line #248	-> byte code offset #46
    //   Java source line #249	-> byte code offset #49
    //   Java source line #250	-> byte code offset #51
    //   Java source line #251	-> byte code offset #56
    //   Java source line #253	-> byte code offset #61
    //   Java source line #250	-> byte code offset #64
    //   Java source line #251	-> byte code offset #69
    //   Java source line #254	-> byte code offset #74
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	this	FTPServer
    //   0	76	1	localFilePath	String
    //   0	76	2	remoteFileName	String
    //   1	74	3	flag	boolean
    //   3	67	4	iStream	InputStream
    //   29	3	5	e 	IOException
    //   49	13	6	localObject	Object
    //   34	13	7	bool1	boolean
    // Exception table:
    //   from	to	target	type
    //   5	26	29	java/io/IOException
    //   5	36	49	finally
  }
  
  public boolean uploadFile(String fileName)
    throws IOException
  {
    return uploadFile(fileName, fileName);
  }
  
  /* Error */
  public boolean uploadFile(InputStream iStream, String newName)
    throws IOException
  {
    return false;
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aload_0
    //   3: getfield 60	javautils/ftp/FTPServer:ftpClient	Lorg/apache/commons/net/ftp/FTPClient;
    //   6: aload_2
    //   7: aload_1
    //   8: invokevirtual 212	org/apache/commons/net/ftp/FTPClient:storeFile	(Ljava/lang/String;Ljava/io/InputStream;)Z
    //   11: istore_3
    //   12: goto +34 -> 46
    //   15: astore 4
    //   17: iconst_0
    //   18: istore_3
    //   19: iload_3
    //   20: istore 6
    //   22: aload_1
    //   23: ifnull +7 -> 30
    //   26: aload_1
    //   27: invokevirtual 216	java/io/InputStream:close	()V
    //   30: iload 6
    //   32: ireturn
    //   33: astore 5
    //   35: aload_1
    //   36: ifnull +7 -> 43
    //   39: aload_1
    //   40: invokevirtual 216	java/io/InputStream:close	()V
    //   43: aload 5
    //   45: athrow
    //   46: aload_1
    //   47: ifnull +7 -> 54
    //   50: aload_1
    //   51: invokevirtual 216	java/io/InputStream:close	()V
    //   54: iload_3
    //   55: ireturn
    // Line number table:
    //   Java source line #276	-> byte code offset #0
    //   Java source line #278	-> byte code offset #2
    //   Java source line #279	-> byte code offset #12
    //   Java source line #280	-> byte code offset #17
    //   Java source line #281	-> byte code offset #19
    //   Java source line #283	-> byte code offset #22
    //   Java source line #284	-> byte code offset #26
    //   Java source line #281	-> byte code offset #30
    //   Java source line #282	-> byte code offset #33
    //   Java source line #283	-> byte code offset #35
    //   Java source line #284	-> byte code offset #39
    //   Java source line #286	-> byte code offset #43
    //   Java source line #283	-> byte code offset #46
    //   Java source line #284	-> byte code offset #50
    //   Java source line #287	-> byte code offset #54
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	FTPServer
    //   0	56	1	iStream	InputStream
    //   0	56	2	newName	String
    //   1	54	3	flag	boolean
    //   15	3	4	e	IOException
    //   33	11	5	localObject	Object
    //   20	11	6	bool1	boolean
    // Exception table:
    //   from	to	target	type
    //   2	12	15	java/io/IOException
    //   2	22	33	finally
  }
  
  /* Error */
  public boolean download(String remoteFileName, String localFileName)
    throws IOException
  {
    return false;
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: new 235	java/io/File
    //   5: dup
    //   6: aload_2
    //   7: invokespecial 237	java/io/File:<init>	(Ljava/lang/String;)V
    //   10: astore 4
    //   12: aconst_null
    //   13: astore 5
    //   15: new 238	java/io/FileOutputStream
    //   18: dup
    //   19: aload 4
    //   21: invokespecial 240	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   24: astore 5
    //   26: aload_0
    //   27: getfield 60	javautils/ftp/FTPServer:ftpClient	Lorg/apache/commons/net/ftp/FTPClient;
    //   30: aload_1
    //   31: aload 5
    //   33: invokevirtual 243	org/apache/commons/net/ftp/FTPClient:retrieveFile	(Ljava/lang/String;Ljava/io/OutputStream;)Z
    //   36: istore_3
    //   37: goto +28 -> 65
    //   40: astore 6
    //   42: iconst_0
    //   43: istore_3
    //   44: iload_3
    //   45: istore 8
    //   47: aload 5
    //   49: invokevirtual 247	java/io/OutputStream:close	()V
    //   52: iload 8
    //   54: ireturn
    //   55: astore 7
    //   57: aload 5
    //   59: invokevirtual 247	java/io/OutputStream:close	()V
    //   62: aload 7
    //   64: athrow
    //   65: aload 5
    //   67: invokevirtual 247	java/io/OutputStream:close	()V
    //   70: iload_3
    //   71: ireturn
    // Line number table:
    //   Java source line #299	-> byte code offset #0
    //   Java source line #300	-> byte code offset #2
    //   Java source line #301	-> byte code offset #12
    //   Java source line #303	-> byte code offset #15
    //   Java source line #307	-> byte code offset #26
    //   Java source line #308	-> byte code offset #37
    //   Java source line #309	-> byte code offset #42
    //   Java source line #310	-> byte code offset #44
    //   Java source line #312	-> byte code offset #47
    //   Java source line #310	-> byte code offset #52
    //   Java source line #311	-> byte code offset #55
    //   Java source line #312	-> byte code offset #57
    //   Java source line #313	-> byte code offset #62
    //   Java source line #312	-> byte code offset #65
    //   Java source line #314	-> byte code offset #70
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	FTPServer
    //   0	72	1	remoteFileName	String
    //   0	72	2	localFileName	String
    //   1	70	3	flag	boolean
    //   10	10	4	outfile	java.io.File
    //   13	53	5	oStream	java.io.OutputStream
    //   40	3	6	e	IOException
    //   55	8	7	localObject	Object
    //   45	8	8	bool1	boolean
    // Exception table:
    //   from	to	target	type
    //   15	37	40	java/io/IOException
    //   15	47	55	finally
  }
  
  /* Error */
  public String readFile(String remoteFile)
    throws IOException
  {
    return null;
    // Byte code:
    //   0: new 257	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 259	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aload_0
    //   11: getfield 60	javautils/ftp/FTPServer:ftpClient	Lorg/apache/commons/net/ftp/FTPClient;
    //   14: aload_1
    //   15: aload_2
    //   16: invokevirtual 243	org/apache/commons/net/ftp/FTPClient:retrieveFile	(Ljava/lang/String;Ljava/io/OutputStream;)Z
    //   19: pop
    //   20: aload_2
    //   21: invokevirtual 260	java/io/ByteArrayOutputStream:toString	()Ljava/lang/String;
    //   24: astore_3
    //   25: goto +26 -> 51
    //   28: astore 4
    //   30: aload 4
    //   32: invokevirtual 261	java/io/IOException:printStackTrace	()V
    //   35: aload_2
    //   36: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   39: goto +16 -> 55
    //   42: astore 5
    //   44: aload_2
    //   45: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   48: aload 5
    //   50: athrow
    //   51: aload_2
    //   52: invokevirtual 264	java/io/ByteArrayOutputStream:close	()V
    //   55: aload_3
    //   56: areturn
    // Line number table:
    //   Java source line #318	-> byte code offset #0
    //   Java source line #319	-> byte code offset #8
    //   Java source line #321	-> byte code offset #10
    //   Java source line #322	-> byte code offset #20
    //   Java source line #323	-> byte code offset #25
    //   Java source line #324	-> byte code offset #30
    //   Java source line #326	-> byte code offset #35
    //   Java source line #325	-> byte code offset #42
    //   Java source line #326	-> byte code offset #44
    //   Java source line #327	-> byte code offset #48
    //   Java source line #326	-> byte code offset #51
    //   Java source line #328	-> byte code offset #55
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	FTPServer
    //   0	57	1	remoteFile	String
    //   7	45	2	bos	java.io.ByteArrayOutputStream
    //   9	47	3	result	String
    //   28	3	4	e	IOException
    //   42	7	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   10	25	28	java/io/IOException
    //   10	35	42	finally
  }
  
  public InputStream downFile(String sourceFileName)
    throws IOException
  {
    return this.ftpClient.retrieveFileStream(sourceFileName);
  }
}
