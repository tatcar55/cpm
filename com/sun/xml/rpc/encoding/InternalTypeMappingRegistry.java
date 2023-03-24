package com.sun.xml.rpc.encoding;

import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.Deserializer;
import javax.xml.rpc.encoding.Serializer;

public interface InternalTypeMappingRegistry {
  Serializer getSerializer(String paramString, Class paramClass, QName paramQName) throws Exception;
  
  Serializer getSerializer(String paramString, Class paramClass) throws Exception;
  
  Serializer getSerializer(String paramString, QName paramQName) throws Exception;
  
  Deserializer getDeserializer(String paramString, Class paramClass, QName paramQName) throws Exception;
  
  Deserializer getDeserializer(String paramString, QName paramQName) throws Exception;
  
  Class getJavaType(String paramString, QName paramQName) throws Exception;
  
  QName getXmlType(String paramString, Class paramClass) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\InternalTypeMappingRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */