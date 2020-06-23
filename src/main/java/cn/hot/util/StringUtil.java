package cn.hot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    /**
     * 	判断是不是为"" 和null
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        if(null == str) return true;
        if("".equals(str.trim())) return true;
        if("null".equals(str)) return true;
        return false;
    }

    /**
     *
     * @param str str的内容是不是 字符串 null，而不是空null
     * @return
     */
    public static boolean isNull(String str){
        if(isBlank(str)) return false;
        if("null".equalsIgnoreCase(str)) return true;
        return false;
    }

    public static String clearNull(String str){
        if(isBlank(str) || "null".equalsIgnoreCase(str)) return "";
        return str.trim();
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 	随机4四位数，作为消息id
     * @return
     */
    public static String getMessageId(){
        long time = System.currentTimeMillis();
        String hex = Long.toHexString(time);
        return hex.substring(hex.length()-4,hex.length()).toUpperCase();
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    /**下划线转驼峰*/
    public static String lineToHump(String str){
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**驼峰转下划线,效率比上面高*/
    public static String humpToLine2(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isHexNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9a-fA-F]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String vinConvert(String vin){
        List<String> list = getStrList(vin,2);
        StringBuilder vin1 = new StringBuilder();
        for (String s : list) {
            vin1.append((char)Integer.parseInt(s,16));
        }
        return vin1.toString();
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
                                          int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }

    //去除数后面多余的0
    public static String rvZeroAndDot(String s){
        if (s.isEmpty()) {
            return "null";
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 	清除左边的数据0
     * @param str
     * @return
     */
    public static String ltrim0(String str){
        return str.replaceAll("^(0+)", "");
    }

    public static boolean judgeContainsStr(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    public static String generateSql(String sql, Map<String,Object> map){
        for (String s : map.keySet()) {
            sql = sql.replace("${"+s+"}",map.get(s).toString());
        }
        return sql;
    }

    //获取UUID
    public static String getUUID() {
        return UUID.uuid().toString().replaceAll("-", "");
    }

    //获取图片名称后缀格式 如.png
    public static String getImgExt(String oldName) {
        return oldName.substring(oldName.lastIndexOf("."));
    }
}
