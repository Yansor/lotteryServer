package javautils.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;
import java.io.File;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.Hashtable;

public class ExcelUtil
{
    private static Hashtable<Object, Integer> chHt;
    private static ExcelUtil instance;
    
    static {
        ExcelUtil.chHt = new Hashtable<Object, Integer>();
        final char[] ch = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        for (int i = 0; i < ch.length; ++i) {
            ExcelUtil.chHt.put(ch[i], i);
        }
    }
    
    private ExcelUtil() {
    }
    
    private static synchronized void synInit() {
        if (ExcelUtil.instance == null) {
            ExcelUtil.instance = new ExcelUtil();
        }
    }
    
    public static ExcelUtil getInstance() {
        if (ExcelUtil.instance == null) {
            synInit();
        }
        return ExcelUtil.instance;
    }
    
    public HSSFWorkbook open(final String filePath) {
        final File file = new File(filePath);
        return this.read(file);
    }
    
    public HSSFWorkbook read(final File file) {
        HSSFWorkbook workbook = null;
        FileInputStream is = null;
        POIFSFileSystem fs = null;
        try {
            is = new FileInputStream(file);
            fs = new POIFSFileSystem((InputStream)is);
            workbook = new HSSFWorkbook(fs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Exception ex) {}
            }
        }
        if (is != null) {
            try {
                is.close();
            }
            catch (Exception ex2) {}
        }
        return workbook;
    }
    
    public boolean save(final File file, final HSSFWorkbook workbook) {
        if (file != null) {
            final String filePath = file.getPath();
            return this.write(filePath, workbook);
        }
        return false;
    }
    
    public boolean saveAs(final String filePath, final String fileName, final HSSFWorkbook workbook) {
        final File fileDirs = new File(filePath);
        if (!fileDirs.exists()) {
            fileDirs.mkdirs();
        }
        return this.write(String.valueOf(filePath) + fileName, workbook);
    }
    
    private boolean write(final String filePath, final HSSFWorkbook workbook) {
        boolean flag = false;
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            workbook.write((OutputStream)os);
            flag = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                }
                catch (Exception ex) {}
            }
        }
        if (os != null) {
            try {
                os.flush();
                os.close();
            }
            catch (Exception ex2) {}
        }
        return flag;
    }
    
    public static int getRowNum(final HSSFSheet sheet) {
        return sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
    }
    
    public static HSSFRow getRow(final HSSFSheet sheet, final int rowNum) {
        HSSFRow row = null;
        if (rowNum > 0) {
            final int rowIndex = rowNum - 1;
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
        }
        return row;
    }
    
    public static HSSFCell getCell(final HSSFRow row, final String cellName) {
        final int cellIndex = getCellIndex(cellName);
        return row.getCell(cellIndex, Row.CREATE_NULL_AS_BLANK);
    }
    
    public static String getStringCellValue(final HSSFRow row, final String cellName) {
        final int cellIndex = getCellIndex(cellName);
        final HSSFCell cell = row.getCell(cellIndex);
        cell.setCellType(1);
        return cell.getStringCellValue();
    }
    
    public static int getCellIndex(final String cellName) {
        int cellIndex = -1;
        final char[] c = cellName.toCharArray();
        if (c.length == 1) {
            cellIndex = ExcelUtil.chHt.get(c[0]);
        }
        if (c.length == 2) {
            cellIndex = (ExcelUtil.chHt.get(c[0]) + 1) * 26 + (ExcelUtil.chHt.get(c[1]) + 1) - 1;
        }
        return cellIndex;
    }
}
