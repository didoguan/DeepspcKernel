package com.deepspc.kernel.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    /**
     * json字符串转list
     * @param json
     * @return
     */
    public static List<Map<String, Object>> json2list(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        JSONArray array = JSON.parseArray(json);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int size = array.size();
        for (int i = 0; i < size; i++) {
            list.add((Map<String, Object>)array.getJSONObject(i));
        }
        return list;
    }

    /**
     * json字符串转list
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> json2list(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseArray(json, cls);
    }

    /**
     * json字符串转对象
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T json2obj(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, cls);
    }
}
