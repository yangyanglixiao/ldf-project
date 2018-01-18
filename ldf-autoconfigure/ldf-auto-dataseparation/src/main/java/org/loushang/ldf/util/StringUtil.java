package org.loushang.ldf.util;

public class StringUtil {

    /**
     * @description 判断给定字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str != null && str.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @description 判断给定字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.length() > 0) {
            return false;
        }

        return true;
    }
}
