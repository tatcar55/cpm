package com.sun.xml.rpc.encoding;

import javax.xml.namespace.QName;

public interface CombinedSerializer extends JAXRPCSerializer, JAXRPCDeserializer {
  QName getXmlType();
  
  boolean getEncodeType();
  
  boolean isNullable();
  
  String getEncodingStyle();
  
  CombinedSerializer getInnermostSerializer();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\CombinedSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */