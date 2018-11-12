package admin.tools;

import java.io.InputStream;
import ch.ethz.ssh2.StreamGobbler;
import ch.ethz.ssh2.Session;
import java.io.IOException;
import java.nio.charset.Charset;
import ch.ethz.ssh2.Connection;

public class RmtShellExecutor
{
    private Connection conn;
    private String ip;
    private String usr;
    private String psword;
    private String charset;
    private static final int TIME_OUT = 60000;
    
    public RmtShellExecutor(final String ip, final String user, final String passwd) {
        this.charset = Charset.defaultCharset().toString();
        this.ip = ip;
        this.usr = user;
        this.psword = passwd;
    }
    
    private boolean login() throws IOException {
        (this.conn = new Connection(this.ip)).connect();
        return this.conn.authenticateWithPassword(this.usr, this.psword);
    }
    
    public int execNoResultMessage(final String cmds) {
        int ret = -1;
        try {
            if (this.login()) {
                final Session session = this.conn.openSession();
                session.execCommand(cmds);
                session.waitForCondition(32, 60000L);
                ret = session.getExitStatus();
            }
            else {
                System.out.println("登录远程机器失败" + this.ip);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (this.conn != null) {
                this.conn.close();
            }
        }
        if (this.conn != null) {
            this.conn.close();
        }
        return ret;
    }
    
    public String execCommand(final String cmds, final boolean retMessage) {
        String result = "";
        String outStr = "1";
        int ret = -1;
        try {
            if (this.login()) {
                final Session session = this.conn.openSession();
                if (session != null) {
                    session.execCommand(cmds);
                    if (retMessage) {
                        final StreamGobbler stdOut = new StreamGobbler(session.getStdout());
                        outStr = this.processStream((InputStream)stdOut, this.charset);
                    }
                    session.waitForCondition(32, 60000L);
                    ret = session.getExitStatus();
                    if (retMessage) {
                        if (ret == 0) {
                            if (outStr != null && outStr.contains("stopped")) {
                                outStr = "服务器已停止...";
                            }
                            else if (outStr != null && outStr.contains("not running")) {
                                outStr = "服务器不在运行当中...";
                            }
                            else if (outStr != null && outStr.contains("running")) {
                                outStr = "服务器正在运行...";
                            }
                            else {
                                outStr = "服务器未知错误...";
                            }
                        }
                    }
                    else if (ret == 0) {
                        outStr = "Lucky！服务器操作成功...";
                    }
                    else {
                        outStr = "oups！服务器操作失败...";
                    }
                }
            }
            else {
                outStr = "登录远程机器失败...";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            outStr = "程序未知异常";
        }
        finally {
            if (this.conn != null) {
                this.conn.close();
            }
        }
        if (this.conn != null) {
            this.conn.close();
        }
        result = "{\"code\":" + ret + ",\"message\":\"" + outStr + "\"}";
        System.out.println(result);
        return result;
    }
    
    public String processStream(final InputStream in, final String charset) throws Exception {
        final byte[] buf = new byte[1024];
        final StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }
    
    public static void main(final String[] args) throws Exception {
        final RmtShellExecutor exe = new RmtShellExecutor("104.193.92.177", "root", "hellobc@hd2015");
        exe.execCommand("service tomcat LotteryServer start", false);
    }
}
