package com.sun.xml.ws.security.opt.api.keyinfo;

public interface BinarySecurityToken extends Token {
  String getValueType();
  
  String getEncodingType();
  
  byte[] getTokenValue();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\keyinfo\BinarySecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */