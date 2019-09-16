package com.leyou.auth.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author river
 * @title: ObjectUtils
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/615:16
 */
public class ObjectUtils {
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Long toLong(Object obj) {
        if (obj == null) {
            return 0L;
        }
        if (obj instanceof Double || obj instanceof Float) {
            //截取指定字符串之前的内容substringBefore
            return Long.valueOf(StringUtils.substringBefore(obj.toString(), "."));
        }
        if (obj instanceof Number) {
            return Long.valueOf(obj.toString());
        }
        if (obj instanceof String) {
            return Long.valueOf(obj.toString());
        } else {
            return 0L;
        }
    }

    public static Integer toInt(Object obj) {
        return toLong(obj).intValue();
    }
}
