package admin.tools;

import admin.tools.entity.ServerConfig;
import org.springframework.stereotype.Service;

@Service
public class ServerService
{
    public String execute(final ServerConfig serverConfig, final String action) {
        String result = "";
        final String cmd = serverConfig.getCmd();
        final String hosts = serverConfig.getHost();
        final String user = serverConfig.getUser();
        final String passwd = serverConfig.getPassword();
        final int port = serverConfig.getPort();
        final String[] hostArr = this.parserHosts(hosts);
        String[] array;
        for (int length = (array = hostArr).length, i = 0; i < length; ++i) {
            final String host = array[i];
            try {
                result = SSHHelper.execQuick(host, user, passwd, port, String.valueOf(cmd) + " " + action);
            }
            catch (Exception e) {
                result = "{\"code\":-2,\"message\":\"操作失败,重试！\"}";
            }
        }
        return result;
    }
    
    public String[] parserHosts(final String hosts) {
        if (hosts != null && !"".equals(hosts)) {
            final String[] values = hosts.split(",");
            return values;
        }
        return null;
    }
}
