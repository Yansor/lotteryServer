package lottery.domains.content.payment.tgf.utils;

import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import java.util.Map;

public class XMLParserUtil
{
    public static void parse(final String xmlData, final Map<String, String> resultMap) throws Exception {
        final Document doc = DocumentHelper.parseText(xmlData);
        final Element root = doc.getRootElement();
        parseNode(root, resultMap);
    }
    
    private static void parseNode(final Element node, final Map<String, String> resultMap) {
        final List attList = node.attributes();
        final List eleList = node.elements();
        for (int i = 0; i < attList.size(); ++i) {
            final Attribute att = (Attribute) attList.get(i);
            resultMap.put(att.getPath(), att.getText().trim());
        }
        resultMap.put(node.getPath(), node.getTextTrim());
        for (int i = 0; i < eleList.size(); ++i) {
            parseNode((Element) eleList.get(i), resultMap);
        }
    }
}
