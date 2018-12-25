 package com.fsy.javautils.json;

 import net.sf.json.xml.XMLSerializer;

 public class JSONUtil
 {
   public static net.sf.json.JSON toJSONString(String xml)
   {
/*  9 */     XMLSerializer xmlSerializer = new XMLSerializer();
/* 10 */     net.sf.json.JSON json = xmlSerializer.read(xml);
/* 11 */     return json;
   }
 }


