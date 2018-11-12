package lottery.domains.content.payment.utils;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.util.HashMap;

import org.dom4j.Document;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;

import org.dom4j.io.SAXReader;

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.SortedMap;

import javax.servlet.ServletInputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class XmlUtils
{
    public static String parseRequst(final HttpServletRequest request) {
        String body = "";
        try {
            final ServletInputStream inputStream = request.getInputStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)inputStream));
            while (true) {
                final String info = br.readLine();
                if (info == null) {
                    break;
                }
                if (body == null || "".equals(body)) {
                    body = info;
                }
                else {
                    body = String.valueOf(body) + info;
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return body;
    }
    
    public static String parseXML(final SortedMap<String, String> parameters) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        final Set<Entry<String, String>> es = parameters.entrySet();
        for (final Entry entry : es) {
            final String k = (String) entry.getKey();
            final String v = (String) entry.getValue();
            if (v != null && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
    
    public static SortedMap getParameterMap(final HttpServletRequest request) {
        final Map properties = request.getParameterMap();
        final SortedMap returnMap = new TreeMap();
        final Iterator entries = properties.entrySet().iterator();
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            final Entry entry = (Entry) entries.next();
            name = (String) entry.getKey();
            final Object valueObj = entry.getValue();
            if (valueObj == null) {
                value = "";
            }
            else if (valueObj instanceof String[]) {
                final String[] values = (String[])valueObj;
                for (int i = 0; i < values.length; ++i) {
                    value = String.valueOf(values[i]) + ",";
                }
                value = value.substring(0, value.length() - 1);
            }
            else {
                value = valueObj.toString();
            }
            returnMap.put(name, value.trim());
        }
        return returnMap;
    }
    
    public static Map<String, String> toMap(final byte[] xmlBytes, final String charset) throws Exception {
        final SAXReader reader = new SAXReader(false);
        final InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        final Document doc = reader.read(source);
        final Map<String, String> params = toMap(doc.getRootElement());
        return params;
    }
    
    public static Map<String, String> getXmlElmentValue(final String xml) {
        final Map<String, String> map = new HashMap<String, String>();
        try {
            final Document doc = DocumentHelper.parseText(xml);
            final Element el = doc.getRootElement();
            return recGetXmlElementValue(el, map);
        }
        catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Map<String, String> recGetXmlElementValue(final Element ele, final Map<String, String> map) {
        final List<Element> eleList = (List<Element>)ele.elements();
        if (eleList.size() == 0) {
            map.put(ele.getName(), ele.getTextTrim());
            return map;
        }
        for (final Element innerEle : eleList) {
            recGetXmlElementValue(innerEle, map);
        }
        return map;
    }
    
    public static Map<String, String> toMap(final Element element) {
        final Map<String, String> rest = new HashMap<String, String>();
        final List<Element> els = (List<Element>)element.elements();
        for (final Element el : els) {
            rest.put(el.getName().toLowerCase(), el.getTextTrim());
        }
        return rest;
    }
    
    public static String toXml(final Map<String, String> params) {
        final StringBuilder buf = new StringBuilder();
        final List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        buf.append("<xml>");
        for (final String key : keys) {
            buf.append("<").append(key).append(">");
            buf.append("<![CDATA[").append(params.get(key)).append("]]>");
            buf.append("</").append(key).append(">\n");
        }
        buf.append("</xml>");
        return buf.toString();
    }
}
