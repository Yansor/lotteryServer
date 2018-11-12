package javautils.redis.pool;

public class ConnectionInfo
{
    public static final String DEFAULT_PASSWORD;
    private int database;
    private String password;
    private int timeout;
    
    static {
        DEFAULT_PASSWORD = null;
    }
    
    public ConnectionInfo() {
        this.database = 0;
        this.password = ConnectionInfo.DEFAULT_PASSWORD;
        this.timeout = 2000;
    }
    
    public ConnectionInfo(final int database, final String password, final int timeout) {
        this.database = 0;
        this.password = ConnectionInfo.DEFAULT_PASSWORD;
        this.timeout = 2000;
        this.timeout = timeout;
        if (password != null && password.length() > 0) {
            this.password = password;
        }
        else {
            this.password = ConnectionInfo.DEFAULT_PASSWORD;
        }
        this.database = database;
    }
    
    public int getDatabase() {
        return this.database;
    }
    
    public void setDatabase(final int database) {
        this.database = database;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        if (password != null && password.length() > 0) {
            this.password = password;
        }
        else {
            this.password = ConnectionInfo.DEFAULT_PASSWORD;
        }
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }
    
    @Override
    public String toString() {
        return "ConnectionInfo [database=" + this.database + ", password=" + this.password + ", timeout=" + this.timeout + "]";
    }
}
