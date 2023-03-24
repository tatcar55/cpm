package com.sun.xml.registry.uddi;

import javax.security.auth.callback.Callback;

public class UDDIMapperCallback implements Callback {
  UDDIMapper mapper;
  
  public void setUDDIMapper(UDDIMapper paramUDDIMapper) {
    this.mapper = paramUDDIMapper;
  }
  
  public UDDIMapper getUDDIMapper() {
    return this.mapper;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\UDDIMapperCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */