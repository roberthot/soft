package cn.hot.util;

import java.util.List;

/**
 * fileName ListUtil
 *
 * @author kangxusheng
 * @date 2019/5/13 19:25
 * History:
 */
public class ListUtil {

    /**
     * 判断集合是否为空
     *
     * @param list
     *            集合
     */
    public static boolean isNotEmpty(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}