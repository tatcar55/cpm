package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;

public interface PGPDataType {
  List getAny();
  
  byte[] getPGPKeyPacket();
  
  void setPGPKeyPacket(byte[] paramArrayOfbyte);
  
  byte[] getPGPKeyID();
  
  void setPGPKeyID(byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\PGPDataType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */