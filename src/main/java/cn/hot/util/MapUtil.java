package cn.hot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class MapUtil {
    private static Logger log = LoggerFactory.getLogger( MapUtil.class );

    public static <T> T convert(Map<String,Object> map, Class<T> clazz){
        try {
            T t = clazz.newInstance();
            for (String key : map.keySet()) {
                if(map.get(key) == null) continue;
                String setMethod = StringUtil.lineToHump( "set_" + key );
                try {
                    Method method = clazz.getDeclaredMethod( setMethod, map.get( key ).getClass() );
                    method.invoke( t,map.get( key ) );
                } catch (NoSuchMethodException e) {
                    //log.error( "转化异常，无此方法，继续转化：{}", e.getMessage() );
                }
            }
            return t;
        }catch (Exception e){
            log.error( "创建类失败：{}",e.getMessage() );
        }
        return null;
    }

}
