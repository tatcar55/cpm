package com.sun.xml.ws.security.trust.elements;

import java.util.Map;
import javax.xml.namespace.QName;

public interface BinaryExchange {
  String getEncodingType();
  
  Map<QName, String> getOtherAttributes();
  
  byte[] getRawValue();
  
  String getTextValue();
  
  String getValueType();
  
  void setEncodingType(String paramString);
  
  void setTextValue(String paramString);
  
  void setRawValue(byte[] paramArrayOfbyte);
  
  void setValueType(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\BinaryExchange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */