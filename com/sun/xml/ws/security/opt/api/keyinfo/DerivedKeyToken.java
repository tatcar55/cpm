package com.sun.xml.ws.security.opt.api.keyinfo;

import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
import java.math.BigInteger;

public interface DerivedKeyToken {
  String getAlgorithm();
  
  BigInteger getGeneration();
  
  String getId();
  
  String getLabel();
  
  BigInteger getLength();
  
  byte[] getNonce();
  
  BigInteger getOffset();
  
  SecurityTokenReferenceType getSecurityTokenReference();
  
  void setAlgorithm(String paramString);
  
  void setGeneration(BigInteger paramBigInteger);
  
  void setId(String paramString);
  
  void setLabel(String paramString);
  
  void setLength(BigInteger paramBigInteger);
  
  void setNonce(byte[] paramArrayOfbyte);
  
  void setOffset(BigInteger paramBigInteger);
  
  void setSecurityTokenReference(SecurityTokenReferenceType paramSecurityTokenReferenceType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\keyinfo\DerivedKeyToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */