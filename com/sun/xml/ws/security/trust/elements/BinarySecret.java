package com.sun.xml.ws.security.trust.elements;

import java.util.Map;
import javax.xml.namespace.QName;

public interface BinarySecret {
  public static final String ASYMMETRIC_KEY_TYPE = "http://schemas.xmlsoap.org/ws/2005/02/trust/AsymmetricKey";
  
  public static final String SYMMETRIC_KEY_TYPE = "http://schemas.xmlsoap.org/ws/2005/02/trust/SymmetricKey";
  
  public static final String NONCE_KEY_TYPE = "http://schemas.xmlsoap.org/ws/2005/02/trust/Nonce";
  
  Map<QName, String> getOtherAttributes();
  
  String getType();
  
  byte[] getRawValue();
  
  String getTextValue();
  
  void setType(String paramString);
  
  void setTextValue(String paramString);
  
  void setRawValue(byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\BinarySecret.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */