package lottery.domains.content.payment.mkt;

import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.lang.StringUtils;
import java.util.LinkedList;
import java.util.List;

public class KeyValues
{
    private List<KeyValue> keyValues;
    
    public KeyValues() {
        this.keyValues = new LinkedList<KeyValue>();
    }
    
    public List<KeyValue> items() {
        return this.keyValues;
    }
    
    public void add(final KeyValue kv) {
        if (kv != null && !StringUtils.isEmpty(kv.getVal())) {
            this.keyValues.add(kv);
        }
    }
    
    public String sign(final String key, final String inputCharset) {
        final StringBuilder sb = new StringBuilder();
        Collections.sort(this.keyValues, new Comparator<KeyValue>() {
            @Override
            public int compare(final KeyValue l, final KeyValue r) {
                return l.compare(r);
            }
        });
        for (final KeyValue kv : this.keyValues) {
            URLUtils.appendParam(sb, kv.getKey(), kv.getVal());
        }
        URLUtils.appendParam(sb, "key", key);
        String s = sb.toString();
        s = s.substring(1, s.length());
        return MD5Encoder.encode(s, inputCharset);
    }
}
