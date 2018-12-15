package admin.tools;

import admin.tools.entity.ServerConfig;
import org.springframework.stereotype.Service;

@Service
public class ServerService
{
  public String execute(ServerConfig serverConfig, String action)
  {
    String result = "";
    String cmd = serverConfig.getCmd();
    String hosts = serverConfig.getHost();
    String user = serverConfig.getUser();
    String passwd = serverConfig.getPassword();
    int port = serverConfig.getPort();
    
    String[] hostArr = parserHosts(hosts);
    String[] arrayOfString1;
    int j = (arrayOfString1 = hostArr).length;
    for (int i = 0; i < j; i++)
    {
      String host = arrayOfString1[i];
      try
      {
        result = SSHHelper.execQuick(host, user, passwd, port, cmd + 
          " " + action);
      }
      catch (Exception e)
      {
        result = "{\"code\":-2,\"message\":\"操作失败,重试！\"}";
      }
    }
    return result;
  }
  
  public String[] parserHosts(String hosts)
  {
    if ((hosts != null) && (!"".equals(hosts)))
    {
      String[] values = hosts.split(",");
      return values;
    }
    return null;
  }
}
