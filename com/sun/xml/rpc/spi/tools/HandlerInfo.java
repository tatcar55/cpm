package com.sun.xml.rpc.spi.tools;

import java.util.Map;
import javax.xml.namespace.QName;

public interface HandlerInfo {
  void setHandlerClassName(String paramString);
  
  Map getProperties();
  
  void addHeaderName(QName paramQName);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\tools\HandlerInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */