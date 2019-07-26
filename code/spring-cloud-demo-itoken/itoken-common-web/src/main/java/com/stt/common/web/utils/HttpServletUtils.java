package com.stt.common.web.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpServletUtils {

    /**
     * 获取完整请求路径，带请求参数
     *
     * @param request
     * @return
     */
    public static String getFullPath2(HttpServletRequest request) {

        // 获取所有请求,返回 Map 集合
        Map<String, String[]> map = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = map.entrySet().iterator();

        // 遍历集合
        StringBuffer url = request.getRequestURL();

        if(!CollectionUtils.isEmpty(map)){

            url.append("?");

            while (iterator.hasNext()) {
                Map.Entry<String, String[]> item = iterator.next();
                //请求名
                String key = item.getKey();
                //请求值
                for (String value : item.getValue()) {
                    // 拼接每个请求参数 key=value&
                    url.append(key).append("=").append(value).append("&");
                }
            }
        }
        return url.toString();
    }

    public static String getFullPath(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        String url = uri.toString();
        // 获取所有请求,返回 Map 集合
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = map.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entry.iterator();

        // 遍历集合
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> item = iterator.next();
            //请求名
            String key = item.getKey();
            //请求值
            for (String value : item.getValue()) {
                // 拼接每个请求参数 key=value&
                sb.append(key + "=" + value + "&");
            }
        }

        String string = sb.toString();
        if(!StringUtils.isEmpty(string)){
            // 拼接 URL, URL?key=value&key=value& 并且去掉最后一个 &
            url = url + "?" + string.substring(0, string.lastIndexOf("&"));
        }

        return url;
    }
}