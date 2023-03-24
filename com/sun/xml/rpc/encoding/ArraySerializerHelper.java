package com.sun.xml.rpc.encoding;

import com.sun.xml.rpc.streaming.Attributes;
import com.sun.xml.rpc.streaming.XMLWriter;
import javax.xml.namespace.QName;

interface ArraySerializerHelper {
  void serializeArray(QName paramQName, String paramString, XMLWriter paramXMLWriter);
  
  String getArrayType(Attributes paramAttributes);
  
  String getElemTypeStr(String paramString);
  
  QName getArrayQnameEncoding();
  
  String encodeArrayDimensions(int[] paramArrayOfint);
  
  void whatAmI();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ArraySerializerHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */