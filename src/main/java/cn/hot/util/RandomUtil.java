package cn.hot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    public String uuid(){
        return UUID.randomUUID().toString();
    }

    /**
     * 生成随机数，长度是为日期+时间戳的随机
     * @return
     */
    public static String random(){
        Long l = System.currentTimeMillis();
        String hex = Long.toHexString(Math.abs(l)).toUpperCase();
        return hex;
    }

    public static String timeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder result = new StringBuilder();
        result.append(sdf.format(new Date()));
        result.append(Math.abs(new Random(System.currentTimeMillis()).nextLong()));
        if(result.length()>32){
            return result.substring(0,32);
        }
        return result.toString();
    }

}
