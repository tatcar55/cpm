package com.sun.xml.ws.security.spi;

import javax.security.auth.Subject;

public interface SecurityContext {
  Subject getSubject();
  
  void setSubject(Subject paramSubject);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\spi\SecurityContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */