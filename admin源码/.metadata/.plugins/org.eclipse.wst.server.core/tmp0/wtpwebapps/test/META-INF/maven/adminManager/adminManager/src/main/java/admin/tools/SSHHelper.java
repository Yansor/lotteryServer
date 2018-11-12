package admin.tools;

import java.util.Hashtable;
import java.io.InputStream;
import com.jcraft.jsch.Session;
import java.io.IOException;
import com.jcraft.jsch.JSchException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.jcraft.jsch.ChannelExec;
import java.util.Properties;
import com.jcraft.jsch.JSch;

public class SSHHelper
{
    public static String exec(final String host, final String user, final String passwd, final int port, final String command) {
        String result = "";
        int exitStatus = -1;
        Session session = null;
        ChannelExec openChannel = null;
        try {
            final JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(passwd);
            session.connect();
            openChannel = (ChannelExec)session.openChannel("exec");
            openChannel.setCommand(command);
            openChannel.connect();
            final InputStream in = openChannel.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while (!StringUtils.isEmpty(buf = reader.readLine())) {
                result = String.valueOf(result) + new String(buf.getBytes("utf8"), "UTF-8") + "\n";
            }
            exitStatus = openChannel.getExitStatus();
            System.out.println(result);
            if (exitStatus == 0) {
                if (result.contains("running")) {
                    result = "运行";
                }
                else if (result.contains("not running")) {
                    result = "停止";
                }
                else if (result.contains("stopped")) {
                    result = "停止";
                }
                else {
                    result = "未知";
                }
            }
            else {
                result = "获取状态失败";
            }
        }
        catch (JSchException ex) {}
        catch (IOException e) {
            result = String.valueOf(result) + e.getMessage();
            return "{\"code\":" + exitStatus + ",\"message\":\"" + result + "\"}";
        }
        finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
        if (openChannel != null && !openChannel.isClosed()) {
            openChannel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        result = "{\"code\":" + exitStatus + ",\"message\":\"" + result + "\"}";
        return result;
    }
    
    public static String execQuick(final String host, final String user, final String passwd, final int port, final String command) {
        String result = "";
        int exitStatus = -1;
        Session session = null;
        ChannelExec openChannel = null;
        try {
            final JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(passwd);
            session.connect();
            openChannel = (ChannelExec)session.openChannel("exec");
            openChannel.setCommand(command);
            openChannel.connect();
            final InputStream in = openChannel.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            if (!StringUtils.isEmpty(buf = reader.readLine())) {
                result = String.valueOf(result) + new String(buf.getBytes("utf8"), "UTF-8") + "\n";
            }
            exitStatus = openChannel.getExitStatus();
            if (exitStatus == 0) {
                result = "运行";
            }
            else {
                result = "请重启";
            }
            System.out.println(exitStatus);
        }
        catch (JSchException ex) {}
        catch (IOException e) {
            result = String.valueOf(result) + e.getMessage();
            return "{\"code\":" + exitStatus + ",\"message\":\"" + result + "\"}";
        }
        finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
        if (openChannel != null && !openChannel.isClosed()) {
            openChannel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        result = "{\"code\":" + exitStatus + ",\"message\":\"" + result + "\"}";
        return result;
    }
    
    public static void main(final String[] args) {
        final String exec = exec("104.193.92.177", "root", "hellobc@hd2015", 22, "service tomcat LotteryServer stop");
        System.out.println(exec);
    }
}
