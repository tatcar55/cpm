package com.sun.xml.rpc.encoding.simpletype;

import com.sun.xml.rpc.streaming.XMLReader;
import com.sun.xml.rpc.streaming.XMLWriter;

public interface SimpleTypeEncoder {
  String objectToString(Object paramObject, XMLWriter paramXMLWriter) throws Exception;
  
  Object stringToObject(String paramString, XMLReader paramXMLReader) throws Exception;
  
  void writeValue(Object paramObject, XMLWriter paramXMLWriter) throws Exception;
  
  void writeAdditionalNamespaceDeclarations(Object paramObject, XMLWriter paramXMLWriter) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\SimpleTypeEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */