package admin.web.helper;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContextAware;

@Component
public class SpringContextUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;
    
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }
    
    public static ApplicationContext getApplicationContext() {
        return SpringContextUtil.applicationContext;
    }
    
    public static Object getBean(final String name) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(name);
    }
    
    public static Object getBean(final String name, final Class<?> requiredType) throws BeansException {
        return SpringContextUtil.applicationContext.getBean(name, (Class)requiredType);
    }
    
    public static boolean containsBean(final String name) {
        return SpringContextUtil.applicationContext.containsBean(name);
    }
    
    public static boolean isSingleton(final String name) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.isSingleton(name);
    }
    
    public static Class<?> getType(final String name) throws NoSuchBeanDefinitionException {
        return (Class<?>)SpringContextUtil.applicationContext.getType(name);
    }
    
    public static String[] getAliases(final String name) throws NoSuchBeanDefinitionException {
        return SpringContextUtil.applicationContext.getAliases(name);
    }
}
