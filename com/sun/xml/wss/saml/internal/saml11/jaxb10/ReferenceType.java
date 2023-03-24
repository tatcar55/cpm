package com.sun.xml.wss.saml.internal.saml11.jaxb10;

public interface ReferenceType {
  String getType();
  
  void setType(String paramString);
  
  DigestMethodType getDigestMethod();
  
  void setDigestMethod(DigestMethodType paramDigestMethodType);
  
  byte[] getDigestValue();
  
  void setDigestValue(byte[] paramArrayOfbyte);
  
  String getURI();
  
  void setURI(String paramString);
  
  TransformsType getTransforms();
  
  void setTransforms(TransformsType paramTransformsType);
  
  String getId();
  
  void setId(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\ReferenceType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */