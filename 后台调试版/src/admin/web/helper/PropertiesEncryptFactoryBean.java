package admin.web.helper;

import java.util.Properties;
import javautils.encrypt.DESUtil;
import org.springframework.beans.factory.FactoryBean;

public class PropertiesEncryptFactoryBean
  implements FactoryBean<Properties>
{
  private Properties properties;
  
  public Properties getObject()
    throws Exception
  {
    return getProperties();
  }
  
  public Class<?> getObjectType()
  {
    return Properties.class;
  }
  
  public boolean isSingleton()
  {
    return true;
  }
  
  public Properties getProperties()
  {
    return this.properties;
  }
  
  public void setProperties(Properties properties)
  {
    this.properties = properties;
    try
    {
      String username = properties.getProperty("user");
      String password = properties.getProperty("password");
      String key = properties.getProperty("key");
      String decryptUsername = DESUtil.getInstance().decryptStr(username, key);
      String decryptPassword = DESUtil.getInstance().decryptStr(password, key);
      properties.put("user", decryptUsername);
      properties.put("password", decryptPassword);
    }
    catch (Exception localException) {}
  }
}
