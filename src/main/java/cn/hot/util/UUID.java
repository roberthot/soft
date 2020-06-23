package cn.hot.util;

public class UUID {

    public static String uuid(){
        return java.util.UUID.randomUUID().toString().replaceAll("-","");
    }
}
