package javautils.ftp;

public class FtpConfig
{
  private String server;
  private int port;
  private String username;
  private String password;
  private String location;
  
  public String getServer()
  {
    return this.server;
  }
  
  public void setServer(String server)
  {
    this.server = server;
  }
  
  public int getPort()
  {
    return this.port;
  }
  
  public void setPort(int port)
  {
    this.port = port;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getLocation()
  {
    return this.location;
  }
  
  public void setLocation(String location)
  {
    this.location = location;
  }
}
