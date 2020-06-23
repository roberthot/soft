package cn.hot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemConfig {

    private static Logger log = LoggerFactory.getLogger(SystemConfig.class);
    private static Properties properties = System.getProperties();

    static{
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            properties.load(is);
            log.info("加载配置文件完成..........");
        } catch (IOException e) {
            log.error("加载配置文件出错：",e);
        }
    }

    public static Properties getProperties() {
    	return properties;
    }
    
    public static String get(String key){
        return properties.getProperty(key,"");
    }

    public static boolean getBoolean(String key){
        String value = get(key);
        if(StringUtil.isBlank(value)){
            return false;
        }else{
            if("true".equalsIgnoreCase(value)){
                return true;
            }
            return false;
        }
    }

    public static int getInt(String key){
        String value = get(key);
        if(StringUtil.isBlank(value)){
            return 0;
        }else{
            if(StringUtil.isNumeric(value))
                return Integer.parseInt(value);
            return 0;
        }
    }

}
