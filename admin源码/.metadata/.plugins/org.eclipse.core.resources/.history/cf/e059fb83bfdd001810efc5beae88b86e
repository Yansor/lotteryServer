package lottery.domains.content.payment.lepay.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil
{
    private static ObjectMapper JSON;
    
    static {
        JsonUtil.JSON = new ObjectMapper();
    }
    
    public static String toJsonString(final Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return JsonUtil.JSON.writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T parseObject(final String json, final Class<T> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return (T)JsonUtil.JSON.readValue(json, (Class)valueType);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T> T parseObject(final String json, final TypeReference<T> valueTupeRef) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return (T)JsonUtil.JSON.readValue(json, (TypeReference)valueTupeRef);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
