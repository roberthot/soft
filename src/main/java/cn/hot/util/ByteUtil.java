package cn.hot.util;

/**
 * 将硬件数据编码
 */
public class ByteUtil {

    /**
     * 将16进制字串转化可传输到设备的字节码
     * @param sb
     * @return
     */
    public static byte[] hexToBytes(String sb) {
        int len = sb.length() / 2;
        String[] c = new String[len];
        byte[] cc = new byte[len];
        int start = 0;
        int end = 2;
        for (int i = 0; start < sb.length(); start += 2) {
            String subStr = sb.substring(start, end);
            end += 2;
            c[i] = subStr;
            i++;
        }
        for (int i = 0; i < c.length; i++) {
            byte[] bytes = c[i].getBytes();
            byte uniteBytes = uniteBytes(bytes[0], bytes[1]);
            cc[i] = uniteBytes;
        }

        return cc;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
