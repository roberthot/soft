package cn.hot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DeviceUtil {
    private static Logger log = LoggerFactory.getLogger(DeviceUtil.class);

    /**
     * 将卡号转化为16进制
     * @param card
     * @return
     */
    public static String toHexCard(String card){
        card = Long.toHexString(Long.parseLong(card));
        int len = card.length();
        for(int i=0;i<8-len;i++){
            card = "0"+card;
        }
        return card.toUpperCase();
    }

    private static int split(String str) {
        int len = 2;
        int sum = 0;
        if(str.length() % 2 == 1) str = "0"+str;
        for (int i = 0; i < str.length(); i = i + 2) {
            sum = sum + Integer.parseInt(str.substring(i, i + len), 16);
        }
        return sum;
    }

    /**
     * 获取校验码
     * @param str
     * @length 校验码长度
     * @return
     */
    public static String getCheckCode(String str,int length){
        if(str.indexOf("null")!=-1 || str.length()%2!=0){
            log.error("消息内容有误checkCode:{}",str);
            return get(0,4);
        }
        int len = 0;
        for(int i=0;i<str.length();i=i+2){
            len = len + Integer.parseInt(str.substring(i,i+2),16);
        }

        int mode = 65535;
        if(length == 2){
            mode = 256;
        }
        return get(len%mode,length);
    }

    /**
     * 转化数据的大小端模式
     * @param hex
     * @return
     */
    public static String hexReverse(String hex){
        StringBuilder stringBuilder = new StringBuilder();
        int size = hex.length()/2;

        for(int i=size;i>0;i--){
            stringBuilder.append(hex.substring((i-1)*2,(i)*2));
        }
        return stringBuilder.toString();
    }

    /**
     * 16进度转化为10进度
     * @param hex
     * @return
     */
    public static long hex2Long(String hex){
        return Long.parseLong(hex,16);
    }

    /**
     * 将数据反转之后，转化为10进制
     * @param hex
     * @return
     */
    public static long reverseHex2Long(String hex){
        return hex2Long(hexReverse(hex));
    }


    /**
     * 16进度转化为10进度
     * @param hex
     * @return
     */
    public static int hex2Int(String hex){
        return Integer.parseInt(hex,16);
    }

    /**
     * 将数据反转之后，转化为10进制
     * @param hex
     * @return
     */
    public static int reverseHex2Int(String hex){
        return hex2Int(hexReverse(hex));
    }

    /**
     * 获取设备短号，不包含端口
     * @param code
     * @return
     */
    public static int getShortCode(String code){
        code = code.substring(4,6) + code.substring(2,4) + code.substring(0,2);
        try {
            int intCode = Integer.parseInt(code, 16);
            return intCode;
        }catch(Exception e){
            return 0;
        }
    }

    public static int getShortCode(int shortCode,int port){
        port = port + 1;
        if(port < 10){
            return Integer.parseInt(shortCode + "0" + port);
        }else{
            return Integer.parseInt(shortCode + "" + port );
        }
    }

    /**
     *	 将手机上的sim号转化
     * 	字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     *	 转化手机串号使用,从设备上报时需要用此方法转化
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }



    public static String get(long num,int len){
        String num_ = Long.toHexString(num);
        if(num_.length()>len){
            num_ = num_.substring(1,5);
        }
        int nlen = num_.length();
        for(int i=0;i<len-nlen;i++){
            num_ = "0" + num_;
        }
        return reversal(num_);
    }

    public static String getOrderNo(long num,int len){
        String num_ = String.valueOf(num);
        if(num_.length()>len){
            num_ = num_.substring(1,5);
        }
        int nlen = num_.length();
        for(int i=0;i<len-nlen;i++){
            num_ = "0" + num_;
        }
        return reversal(num_);
    }

    public static String reversal(String str){
        String result = "";
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i = i + 2) {
            list.add(str.substring(i, i + 2));
        }
        for(int i=list.size()-1;i>=0;i--){
            result = result + list.get(i);
        }
        return result;
    }

    public static String getDeviceCode(int shortCode,int port){
        String code = String.valueOf(shortCode);
        port = port + 1;
        if(port<10) code = code + "0" + port;
        else code = code + port;
        return code;
    }

}
