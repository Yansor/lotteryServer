package javautils.http;

import java.util.Iterator;
import java.util.List;
import java.net.HttpURLConnection;
import java.util.Enumeration;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javautils.StringUtil;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HttpUtil
{
    private static final Logger logger;
    public static final String json = "text/json";
    public static final String html = "text/html";
    public static final String xml = "text/xml";
    
    static {
        logger = LoggerFactory.getLogger((Class)HttpUtil.class);
    }
    
    public static void write(final HttpServletResponse response, final String s) {
        HttpUtil.logger.debug(s);
        if (StringUtil.isNotNull(s)) {
            try {
                response.setCharacterEncoding("utf-8");
                final PrintWriter writer = response.getWriter();
                writer.write(s);
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void write(final HttpServletResponse response, final String s, final String ContentType) {
        HttpUtil.logger.debug(s);
        if (StringUtil.isNotNull(s)) {
            try {
                response.setContentType(ContentType);
                response.setCharacterEncoding("utf-8");
                final PrintWriter writer = response.getWriter();
                writer.write(s);
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void writeJSONP(final HttpServletRequest request, final HttpServletResponse response, final String s, final String ContentType) {
        final String callback = request.getParameter("callback");
        HttpUtil.logger.debug(s);
        if (StringUtil.isNotNull(s)) {
            try {
                final String callbackStr = String.valueOf(callback) + "(" + s + ")";
                response.setContentType(ContentType);
                response.setCharacterEncoding("utf-8");
                final PrintWriter writer = response.getWriter();
                writer.write(callbackStr);
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void writeJSONP(final HttpServletRequest request, final HttpServletResponse response, final String s) {
        final String callback = request.getParameter("callback");
        HttpUtil.logger.debug(s);
        if (StringUtil.isNotNull(s)) {
            try {
                final String callbackStr = String.valueOf(callback) + "(" + s + ")";
                response.setCharacterEncoding("utf-8");
                final PrintWriter writer = response.getWriter();
                writer.write(callbackStr);
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getWebDomainPath(final HttpServletRequest request) {
        String name = request.getServerName();
        if ("localhost".equals(name)) {
            name = "127.0.0.1";
        }
        final int port = request.getServerPort();
        if (port == 80) {
            return "http://" + name;
        }
        return "http://" + name + ":" + port;
    }
    
    public static String getWebPath(final HttpServletRequest request) {
        final String path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
        return path;
    }
    
    public static String getRequestPath(final HttpServletRequest request) {
        String path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
        final String queryStr = request.getQueryString();
        if (StringUtil.isNotNull(queryStr)) {
            path = String.valueOf(path) + "?" + queryStr;
        }
        return path;
    }
    
    public static Map<String, String> getRequestMap(final String queryStr) {
        final Map<String, String> map = new HashMap<String, String>();
        if (StringUtil.isNotNull(queryStr)) {
            final String[] strs = queryStr.split("&");
            String[] array;
            for (int length = (array = strs).length, i = 0; i < length; ++i) {
                final String str = array[i];
                final String[] keyValue = str.split("=");
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }
    
    public static String getClientIpAddr(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.split(",").length > 0) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
    
    public static Short getShortParameter(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            return Short.parseShort(value);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Integer getIntParameter(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            if (StringUtil.isNotNull(value)) {
                return Integer.parseInt(value);
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static String getStringParameterTrim(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            if (value != null) {
                return value.trim();
            }
            return value;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Boolean getBooleanParameter(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            return Boolean.parseBoolean(value);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Double getDoubleParameter(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            if (StringUtil.isNotNull(value)) {
                return Double.parseDouble(value);
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Float getFloatParameter(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            return Float.parseFloat(value);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Long getLongParameter(final HttpServletRequest request, final String name) {
        final String value = request.getParameter(name);
        try {
            return Long.parseLong(value);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static String encodeURL(String destURL) {
        try {
            destURL = URLEncoder.encode(destURL, "utf-8");
        }
        catch (UnsupportedEncodingException ex) {}
        return destURL;
    }
    
    public static String decodeURL(String destURL) {
        try {
            destURL = URLDecoder.decode(destURL, "utf-8");
        }
        catch (UnsupportedEncodingException ex) {}
        return destURL;
    }
    
    public static String getRequestURL(final HttpServletRequest request) {
        boolean flag = true;
        final StringBuffer requestURL = request.getRequestURL();
        final Enumeration<String> paramNames = (Enumeration<String>)request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            if (flag) {
                flag = false;
                requestURL.append("?");
            }
            else {
                requestURL.append("&");
            }
            final String paramName = paramNames.nextElement();
            try {
                final String paramValue = URLEncoder.encode(request.getParameter(paramName), "utf-8");
                requestURL.append(paramName).append("=").append(paramValue);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return requestURL.toString();
    }
    
    public static void printRquestParams(final HttpServletRequest request) {
        final Map<String, String[]> paramsMap = (Map<String, String[]>)request.getParameterMap();
        Object[] array;
        for (int length = (array = paramsMap.keySet().toArray()).length, i = 0; i < length; ++i) {
            final Object key = array[i];
            System.out.println("key:" + key);
            System.out.print("value:");
            String[] array2;
            for (int length2 = (array2 = paramsMap.get(key)).length, j = 0; j < length2; ++j) {
                final String value = array2[j];
                System.out.print(value);
            }
            System.out.println();
        }
    }
    
    public static void printHeaderFields(final HttpURLConnection conn) {
        final Map<String, List<String>> headerFields = conn.getHeaderFields();
        Object[] array;
        for (int length = (array = headerFields.keySet().toArray()).length, i = 0; i < length; ++i) {
            final Object key = array[i];
            System.out.println(key + ":" + headerFields.get(key).toString());
            for (final String value : headerFields.get(key)) {
                System.out.println("==============:" + value);
            }
        }
    }
}
