package admin.tools.entity;

public class ServerConfig
{
    private String host;
    private int port;
    private String user;
    private String password;
    private String cmd;
    
    public ServerConfig(final String host, final int port, final String user, final String password, final String cmd) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.cmd = cmd;
    }
    
    public ServerConfig() {
    }
    
    public String getHost() {
        return this.host;
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public void setUser(final String user) {
        this.user = user;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getCmd() {
        return this.cmd;
    }
    
    public void setCmd(final String cmd) {
        this.cmd = cmd;
    }
}
