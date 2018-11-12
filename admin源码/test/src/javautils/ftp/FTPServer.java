package javautils.ftp;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPFile;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

public class FTPServer
{
    private FTPClient ftpClient;
    public static final int BINARY_FILE_TYPE = 2;
    public static final int ASCII_FILE_TYPE = 0;
    
    public void connectServer(final FtpConfig ftpConfig) throws IOException {
        final String server = ftpConfig.getServer();
        final int port = ftpConfig.getPort();
        final String user = ftpConfig.getUsername();
        final String password = ftpConfig.getPassword();
        final String location = ftpConfig.getLocation();
        this.connectServer(server, port, user, password, location);
    }
    
    public void connectServer(final String server, final int port, final String user, final String password, final String path) throws IOException {
        (this.ftpClient = new FTPClient()).setDataTimeout(10000);
        this.ftpClient.setConnectTimeout(10000);
        this.ftpClient.setDefaultTimeout(10000);
        this.ftpClient.connect(server, port);
        this.ftpClient.login(user, password);
        if (path != null && path.length() != 0) {
            this.ftpClient.changeWorkingDirectory(path);
        }
        this.ftpClient.setBufferSize(1024);
        this.ftpClient.setControlEncoding("UTF-8");
        this.ftpClient.setFileType(2);
    }
    
    public void setFileType(final int fileType) throws IOException {
        this.ftpClient.setFileType(fileType);
    }
    
    public void closeServer() throws IOException {
        if (this.ftpClient != null && this.ftpClient.isConnected()) {
            this.ftpClient.logout();
            this.ftpClient.disconnect();
        }
    }
    
    public boolean changeDirectory(final String path) throws IOException {
        return this.ftpClient.changeWorkingDirectory(path);
    }
    
    public boolean createDirectory(final String pathName) throws IOException {
        return this.ftpClient.makeDirectory(pathName);
    }
    
    public boolean removeDirectory(final String path) throws IOException {
        return this.ftpClient.removeDirectory(path);
    }
    
    public boolean removeDirectory(final String path, final boolean isAll) throws IOException {
        if (!isAll) {
            return this.removeDirectory(path);
        }
        final FTPFile[] ftpFileArr = this.ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return this.removeDirectory(path);
        }
        FTPFile[] array;
        for (int length = (array = ftpFileArr).length, i = 0; i < length; ++i) {
            final FTPFile ftpFile = array[i];
            final String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                this.removeDirectory(String.valueOf(path) + "/" + name, true);
            }
            else if (ftpFile.isFile()) {
                this.deleteFile(String.valueOf(path) + "/" + name);
            }
            else if (!ftpFile.isSymbolicLink()) {
                ftpFile.isUnknown();
            }
        }
        return this.ftpClient.removeDirectory(path);
    }
    
    public boolean existDirectory(final String path) throws IOException {
        boolean flag = false;
        final FTPFile[] ftpFileArr = this.ftpClient.listFiles(path);
        FTPFile[] array;
        for (int length = (array = ftpFileArr).length, i = 0; i < length; ++i) {
            final FTPFile ftpFile = array[i];
            if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    public List<String> getFileList(final String path) throws IOException {
        final FTPFile[] ftpFiles = this.ftpClient.listFiles(path);
        final List<String> retList = new ArrayList<String>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        FTPFile[] array;
        for (int length = (array = ftpFiles).length, i = 0; i < length; ++i) {
            final FTPFile ftpFile = array[i];
            if (ftpFile.isFile()) {
                retList.add(ftpFile.getName());
            }
        }
        return retList;
    }
    
    public List<String> getDiretoryList(final String path) throws IOException {
        final FTPFile[] ftpDirectories = this.ftpClient.listDirectories(path);
        final List<String> retList = new ArrayList<String>();
        if (ftpDirectories == null || ftpDirectories.length == 0) {
            return retList;
        }
        FTPFile[] array;
        for (int length = (array = ftpDirectories).length, i = 0; i < length; ++i) {
            final FTPFile ftpDirectory = array[i];
            if (ftpDirectory.isDirectory()) {
                retList.add(ftpDirectory.getName());
            }
        }
        return retList;
    }
    
    public boolean deleteFile(final String pathName) throws IOException {
        return this.ftpClient.deleteFile(pathName);
    }
    
    public boolean uploadFile(final String localFilePath, final String remoteFileName) throws IOException {
        boolean flag = false;
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(localFilePath);
            flag = this.ftpClient.storeFile(remoteFileName, iStream);
        }
        catch (IOException e) {
            flag = false;
            return flag;
        }
        finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        if (iStream != null) {
            iStream.close();
        }
        return flag;
    }
    
    public boolean uploadFile(final String fileName) throws IOException {
        return this.uploadFile(fileName, fileName);
    }
    
    public boolean uploadFile(final InputStream iStream, final String newName) throws IOException {
        boolean flag = false;
        try {
            flag = this.ftpClient.storeFile(newName, iStream);
        }
        catch (IOException e) {
            flag = false;
            return flag;
        }
        finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        if (iStream != null) {
            iStream.close();
        }
        return flag;
    }
    
    public boolean download(final String remoteFileName, final String localFileName) throws IOException {
        boolean flag = false;
        final File outfile = new File(localFileName);
        OutputStream oStream = null;
        try {
            oStream = new FileOutputStream(outfile);
            flag = this.ftpClient.retrieveFile(remoteFileName, oStream);
        }
        catch (IOException e) {
            flag = false;
            return flag;
        }
        finally {
            oStream.close();
        }
        oStream.close();
        return flag;
    }
    
    public String readFile(final String remoteFile) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String result = null;
        try {
            this.ftpClient.retrieveFile(remoteFile, (OutputStream)bos);
            result = bos.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        finally {
            bos.close();
        }
        bos.close();
        return result;
    }
    
    public InputStream downFile(final String sourceFileName) throws IOException {
        return this.ftpClient.retrieveFileStream(sourceFileName);
    }
}
