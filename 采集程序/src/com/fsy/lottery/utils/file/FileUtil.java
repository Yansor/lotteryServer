/*     */ package com.fsy.lottery.utils.file;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FileUtil
/*     */ {
/*  14 */   private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtil.class);
/*     */   
/*     */   private static FileUtil instance;
/*     */   
/*     */ 
/*     */   private static synchronized void synInit()
/*     */   {
/*  21 */     if (instance == null) {
/*  22 */       instance = new FileUtil();
/*     */     }
/*     */   }
/*     */   
/*     */   public static FileUtil getInstance() {
/*  27 */     if (instance == null) {
/*  28 */       synInit();
/*     */     }
/*  30 */     return instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] eReadFile(String sFileName)
/*     */   {
/*  39 */     byte[] sbStr = new byte[2];
/*  40 */     int rpos = 0;
/*     */     try {
/*  42 */       long alth = 0L;
/*  43 */       int rsize = 102400;
/*  44 */       byte[] tmpbt = new byte[rsize];
/*  45 */       RandomAccessFile raf = new RandomAccessFile(sFileName, "r");
/*  46 */       alth = raf.length();
/*  47 */       sbStr = new byte[(int)alth];
/*  48 */       while (alth > rpos) {
/*  49 */         raf.seek(rpos);
/*  50 */         tmpbt = new byte[rsize];
/*  51 */         if (alth - rpos < rsize) {
/*  52 */           tmpbt = new byte[(int)(alth - rpos)];
/*     */         }
/*  54 */         raf.read(tmpbt);
/*  55 */         int i = 0; for (int j = tmpbt.length; i < j; i++) {
/*  56 */           sbStr[(rpos + i)] = tmpbt[i];
/*     */         }
/*  58 */         rpos += rsize;
/*     */       }
/*  60 */       raf.close();
/*     */     } catch (Exception e) {
/*  62 */       logger.error("eReadFile异常", e);
/*     */     }
/*  64 */     return sbStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean writeFile(String excpath, String excname, byte[] tmpstr)
/*     */   {
/*  75 */     boolean res = false;
/*     */     try {
/*  77 */       if (!new File(excpath).isDirectory()) {
/*  78 */         new File(excpath).mkdirs();
/*     */       }
/*  80 */       String tmpPath = excpath + excname;
/*  81 */       FileOutputStream wf = new FileOutputStream(tmpPath, false);
/*  82 */       wf.write(tmpstr);
/*  83 */       wf.flush();
/*  84 */       wf.close();
/*  85 */       return res;
/*     */     } catch (Throwable e) {
/*  87 */       logger.error("writeFile异常", e);
/*     */     }
/*  89 */     return res;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean writeFileEX(String excpath, String excname, byte[] tmpstr)
/*     */   {
/* 100 */     boolean res = false;
/*     */     try {
/* 102 */       if (!new File(excpath).isDirectory()) {
/* 103 */         new File(excpath).mkdirs();
/*     */       }
/* 105 */       String tmpPath = excpath + excname;
/* 106 */       RandomAccessFile raf = new RandomAccessFile(tmpPath, "rw");
/* 107 */       raf.seek(raf.length());
/* 108 */       raf.setLength(raf.length() + tmpstr.length);
/* 109 */       raf.write(tmpstr);
/* 110 */       raf.close();
/* 111 */       return res;
/*     */     } catch (Throwable e) {
/* 113 */       logger.error("writeFileEX异常", e);
/*     */     }
/* 115 */     return res;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] readFile(String sFileName)
/*     */   {
/* 124 */     File file = new File(sFileName);
/* 125 */     if (!file.exists()) {
/* 126 */       return "".getBytes();
/*     */     }
/* 128 */     byte[] sbStr = new byte[2];
/* 129 */     int rpos = 0;
/*     */     try {
/* 131 */       long alth = 0L;
/* 132 */       int rsize = 102400;
/* 133 */       byte[] tmpbt = new byte[rsize];
/* 134 */       RandomAccessFile raf = new RandomAccessFile(sFileName, "r");
/* 135 */       alth = raf.length();
/* 136 */       sbStr = new byte[(int)alth];
/* 137 */       while (alth > rpos) {
/* 138 */         raf.seek(rpos);
/* 139 */         tmpbt = new byte[rsize];
/* 140 */         if (alth - rpos < rsize) {
/* 141 */           tmpbt = new byte[(int)(alth - rpos)];
/*     */         }
/* 143 */         raf.read(tmpbt);
/* 144 */         int i = 0; for (int j = tmpbt.length; i < j; i++) {
/* 145 */           sbStr[(rpos + i)] = tmpbt[i];
/*     */         }
/* 147 */         rpos += rsize;
/*     */       }
/* 149 */       raf.close();
/*     */     } catch (Throwable e) {
/* 151 */       logger.error("readFile异常", e);
/* 152 */       sbStr = null;
/*     */     }
/* 154 */     return sbStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getFileLength(String sFileName)
/*     */   {
/* 163 */     long alth = 0L;
/*     */     try {
/* 165 */       RandomAccessFile raf = new RandomAccessFile(sFileName, "r");
/* 166 */       alth = raf.length();
/* 167 */       raf.close();
/*     */     } catch (Throwable e) {
/* 169 */       logger.error("getFileLength异常", e);
/*     */     }
/* 171 */     return alth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] readFile(String sFileName, int lpos, int rlth)
/*     */   {
/* 182 */     byte[] sbStr = new byte[2];
/* 183 */     int rpos = lpos;
/*     */     try {
/* 185 */       long alth = 0L;
/* 186 */       int rsize = 102400;
/* 187 */       byte[] tmpbt = new byte[rsize];
/* 188 */       RandomAccessFile raf = new RandomAccessFile(sFileName, "r");
/* 189 */       alth = raf.length();
/* 190 */       if (lpos + rlth < alth) {
/* 191 */         alth = lpos + rlth;
/*     */       }
/* 193 */       sbStr = new byte[(int)(alth - lpos)];
/* 194 */       while (alth > rpos) {
/* 195 */         raf.seek(rpos);
/* 196 */         tmpbt = new byte[rsize];
/* 197 */         if (alth - rpos < rsize) {
/* 198 */           tmpbt = new byte[(int)(alth - rpos)];
/*     */         }
/* 200 */         raf.read(tmpbt);
/* 201 */         int i = 0; for (int j = tmpbt.length; i < j; i++) {
/* 202 */           sbStr[(rpos - lpos + i)] = tmpbt[i];
/*     */         }
/* 204 */         rpos += rsize;
/*     */       }
/* 206 */       raf.close();
/* 207 */       tmpbt = new byte[1];
/*     */     } catch (Throwable e) {
/* 209 */       logger.error("readFile异常", e);
/* 210 */       sbStr = null;
/*     */     }
/* 212 */     return sbStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String readFile(java.util.List<String> pathList)
/*     */   {
/* 221 */     StringBuffer buffer = new StringBuffer();
/* 222 */     for (String file : pathList) {
/*     */       try {
/* 224 */         byte[] bt = readFile(file);
/* 225 */         if (bt.length > 0) {
/* 226 */           buffer.append(new String(bt));
/*     */         }
/*     */       } catch (Exception e) {
/* 229 */         logger.info("文件不存在!");
/*     */       }
/*     */     }
/* 232 */     return buffer.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean copyFile(File srcFile, File newFile)
/*     */   {
/* 242 */     boolean flag = false;
/*     */     try {
/* 244 */       if (srcFile.exists()) {
/* 245 */         BufferedInputStream fis = new BufferedInputStream(new FileInputStream(srcFile));
/*     */         
/* 247 */         BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile));
/*     */         
/* 249 */         int byteSize = 0;
/* 250 */         while ((byteSize = fis.read()) != -1) {
/* 251 */           fos.write(byteSize);
/*     */         }
/* 253 */         fos.close();
/* 254 */         fis.close();
/* 255 */         flag = true;
/*     */       } else {
/* 257 */         return false;
/*     */       }
/*     */     } catch (Throwable e) {
/* 260 */       logger.error("copyFile异常", e);
/* 261 */       return false;
/*     */     }
/* 263 */     return flag;
/*     */   }
/*     */   
/*     */   public void eMKDir(String eFilePath) {
/*     */     try {
/* 268 */       int ipos = eFilePath.lastIndexOf(File.separator);
/* 269 */       String excpath = eFilePath.substring(0, ipos);
/* 270 */       if (!new File(excpath).isDirectory()) {
/* 271 */         new File(excpath).mkdirs();
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean delFile(File deleteFile)
/*     */   {
/*     */     try
/*     */     {
/* 282 */       deleteFile.delete();
/*     */     } catch (Throwable e) {
/* 284 */       logger.error("delFile异常", e);
/* 285 */       return false;
/*     */     }
/* 287 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean moveFile(File oldFile, File newFile)
/*     */   {
/* 294 */     if ((copyFile(oldFile, newFile)) && (delFile(oldFile))) {
/* 295 */       return true;
/*     */     }
/* 297 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean copyDir(String srcDirPath, String distDirPath)
/*     */   {
/*     */     try
/*     */     {
/* 306 */       new File(distDirPath).mkdirs();
/* 307 */       File srcDir = new File(srcDirPath);
/* 308 */       String[] files = srcDir.list();
/* 309 */       File temp = null;
/* 310 */       for (int i = 0; i < files.length; i++) {
/* 311 */         if (srcDirPath.endsWith(File.separator)) {
/* 312 */           temp = new File(srcDirPath + files[i]);
/*     */         } else {
/* 314 */           temp = new File(srcDirPath + File.separator + files[i]);
/*     */         }
/* 316 */         if (temp.isFile()) {
/* 317 */           FileInputStream input = new FileInputStream(temp);
/*     */           
/* 319 */           FileOutputStream output = new FileOutputStream(distDirPath + File.separator + temp.getName().toString());
/* 320 */           byte[] bytes = new byte['Ѐ'];
/*     */           int len;
/* 322 */           while ((len = input.read(bytes)) != -1) {
/* 323 */             output.write(bytes, 0, len);
/*     */           }
/* 325 */           output.flush();
/* 326 */           output.close();
/* 327 */           input.close();
/*     */         }
/* 329 */         if (temp.isDirectory()) {
/* 330 */           copyDir(srcDirPath + File.separator + files[i], distDirPath + File.separator + files[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable e) {
/* 335 */       logger.error("copyDir异常", e);
/* 336 */       return false;
/*     */     }
/* 338 */     return true;
/*     */   }
/*     */   
/*     */   public void deleteFolder(File dir) {
/* 342 */     File[] filelist = dir.listFiles();
/* 343 */     int listlen = filelist.length;
/* 344 */     for (int i = listlen - 1; i >= 0; i--) {
/*     */       try {
/* 346 */         if (filelist[i].isDirectory()) {
/* 347 */           deleteFolder(filelist[i]);
/*     */         }
/*     */       } catch (Throwable e) {
/* 350 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 353 */     dir.delete();
/*     */   }
/*     */   
/*     */   public byte[] getFileBytes(String filePath) {
/* 357 */     File file = new File(filePath);
/* 358 */     byte[] bytes = getFileBytes(file);
/* 359 */     return bytes;
/*     */   }
/*     */   
/*     */   public byte[] getFileBytes(File file) {
/* 363 */     byte [] bt = null;
/* 364 */     BufferedInputStream bis = null;
/* 365 */     ByteArrayOutputStream baos = null;
/*     */     try {
/* 367 */       bis = new BufferedInputStream(new FileInputStream(file));
/* 368 */       baos = new ByteArrayOutputStream();
/* 369 */       byte[] bytes = new byte['Ѐ'];
/*     */       int len;
/* 371 */       while ((len = bis.read(bytes)) != -1) {
/* 372 */         baos.write(bytes, 0, len);
/*     */       }
/* 374 */       baos.flush();
/* 375 */       bt = baos.toByteArray();
/* 376 */       baos.close();
/* 377 */       bis.close();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 390 */       return bt;
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 379 */       logger.error("getFileBytes异常", e);
/*     */     } finally {
/*     */       try {
/* 382 */         if (baos != null)
/* 383 */           baos.close();
/* 384 */         if (bis != null)
/* 385 */           bis.close();
/*     */       } catch (Throwable e) {
/* 387 */         logger.error("getFileBytes异常", e);
/*     */       }
/*     */     }
return null;
/*     */   }
/*     */ }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/lottery/utils/file/FileUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */