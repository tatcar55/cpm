package com.sun.xml.ws.api.security.trust;

public interface Status {
  boolean isValid();
  
  String getCode();
  
  String getReason();
  
  void setCode(String paramString);
  
  void setReason(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\Status.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */