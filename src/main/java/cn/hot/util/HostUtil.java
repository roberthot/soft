package cn.hot.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务器相关设置
 */
public class HostUtil {

    public static String getHostName(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            return hostname;
        } catch (UnknownHostException e) {
        }
        return "localhost";
    }

}
