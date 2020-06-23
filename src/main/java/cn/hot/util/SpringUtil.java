package cn.hot.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 动态获取spring容器实例，如果在dubbo和springboot结合使用，则只能在当前包下使用，不能在dubbo下用
 *
 */
@Configuration
public class SpringUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtil.applicationContext = context;
    }

    public static <R> R getBean(Class<R> clazz){
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
