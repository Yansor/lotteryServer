 package com.fsy.javautils.html;

 import org.htmlparser.Parser;
 import org.htmlparser.filters.CssSelectorNodeFilter;
 import org.htmlparser.filters.TagNameFilter;
 import org.htmlparser.util.NodeList;
 import org.htmlparser.util.ParserException;



 public class HtmlUtils
 {
   public static NodeList FilterCss(String html, String css, String charset)
     throws ParserException
   {
/* 16 */     Parser parser = Parser.createParser(html, charset);
/* 17 */     CssSelectorNodeFilter filter = new CssSelectorNodeFilter(css);
/* 18 */     NodeList nodeList = parser.extractAllNodesThatMatch(filter);
/* 19 */     return nodeList;
   }

   public static NodeList FilterTag(String html, String tag, String charset) throws ParserException {
/* 23 */     Parser parser = Parser.createParser(html, charset);
/* 24 */     TagNameFilter filter = new TagNameFilter(tag);
/* 25 */     NodeList nodeList = parser.extractAllNodesThatMatch(filter);
/* 26 */     return nodeList;
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/html/HtmlUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */