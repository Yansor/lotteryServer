package admin.tools.entity;

public class ServerConfig
{
  private String host;
  private int port;
  private String user;
  private String password;
  private String cmd;
  
  public ServerConfig(String host, int port, String user, String password, String cmd)
  {
    this.host = host;
    this.port = port;
    this.user = user;
    this.password = password;
    this.cmd = cmd;
  }
  
  public ServerConfig() {}
  
  public String getHost()
  {
    return this.host;
  }
  
  public void setHost(String host)
  {
    this.host = host;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public void setPort(int port)
  {
    this.port = port;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String user)
  {
    this.user = user;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getCmd()
  {
    return this.cmd;
  }
  
  public void setCmd(String cmd)
  {
    this.cmd = cmd;
  }
}
