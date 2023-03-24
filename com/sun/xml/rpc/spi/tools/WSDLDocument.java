package com.sun.xml.rpc.spi.tools;

import javax.xml.namespace.QName;

public interface WSDLDocument {
  QName[] getAllServiceQNames();
  
  QName[] getAllPortQNames();
  
  QName[] getPortQNames(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\tools\WSDLDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */