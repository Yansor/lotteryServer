package admin.web.helper;

import java.util.Hashtable;
import javautils.encrypt.DESUtil;
import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;

public class PropertiesEncryptFactoryBean implements FactoryBean<Properties>
{
    private Properties properties;
    
    public Properties getObject() throws Exception {
        return this.getProperties();
    }
    
    public Class<?> getObjectType() {
        return Properties.class;
    }
    
    public boolean isSingleton() {
        return true;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    public void setProperties(final Properties properties) {
        this.properties = properties;
        try {
            final String username = properties.getProperty("user");
            final String password = properties.getProperty("password");
            final String key = properties.getProperty("key");
            final String decryptUsername = DESUtil.getInstance().decryptStr(username, key);
            final String decryptPassword = DESUtil.getInstance().decryptStr(password, key);
            properties.put("user", decryptUsername);
            properties.put("password", decryptPassword);
        }
        catch (Exception ex) {}
    }
}
