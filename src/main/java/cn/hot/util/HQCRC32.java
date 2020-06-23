package cn.hot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.zip.CRC32;

/**
 * CRC32校验
 * 主要作用验证码充电
 */
public class HQCRC32 {

    private static Logger logger = LoggerFactory.getLogger(HQCRC32.class);

    private static HQCRC32 crc32 = new HQCRC32();

    public static HQCRC32 getInstance() {
        return crc32;
    }

    private HQCRC32() {
    }

    private static final int STREAM_BUFFER_LENGTH = 1024;

    /**
     * 编码
     **/
    public static long encrypt(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        String check = crc32.getValue()+"";
        int length = check.length();
        return Long.parseLong(check.substring(length-6,length));
    }

    /**
     * 编码
     **/
    public long encrypt(InputStream data) throws IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        CRC32 crc32 = new CRC32();
        while (read > -1) {
            crc32.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }
        return crc32.getValue();
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
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
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            String s4 = new BigInteger(s.substring(i*1,(i+1)*1),16).toString(10);
            str = str + s4;
        }
        return str;
    }

    public static String vertifyCode(String deviceId,String port){
        long time_ = System.currentTimeMillis() / 1000;
        long time = time_ - (time_ % 60);
        String hexTime_ = new BigInteger(time + "", 10).toString(16);
        String hexTime =hexTime_.substring(6, 8) + hexTime_.substring(4, 6) + hexTime_.substring(2, 4) + hexTime_.substring(0, 2);
        String check = (deviceId + hexTime + port).toUpperCase();
        logger.info("获取的六位验证码:{}",encrypt(HQCRC32.toByteArray(check))+"");
        String yanzhengma = encrypt(HQCRC32.toByteArray(check)) + "";
        return yanzhengma;
    }


    public static byte[] toByteArray(String hexString) {
        if (hexString.isEmpty())
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

}
