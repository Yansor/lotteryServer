package lottery.domains.content.payment.tgf.utils;

import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLParserUtil
{
  public static void parse(String xmlData, Map<String, String> resultMap)
    throws Exception
  {
    Document doc = DocumentHelper.parseText(xmlData);
    Element root = doc.getRootElement();
    parseNode(root, resultMap);
  }
  
  private static void parseNode(Element node, Map<String, String> resultMap)
  {
    List attList = node.attributes();
    List eleList = node.elements();
    for (int i = 0; i < attList.size(); i++)
    {
      Attribute att = (Attribute)attList.get(i);
      resultMap.put(att.getPath(), att.getText().trim());
    }
    resultMap.put(node.getPath(), node.getTextTrim());
    for (int i = 0; i < eleList.size(); i++) {
      parseNode((Element)eleList.get(i), resultMap);
    }
  }
}
