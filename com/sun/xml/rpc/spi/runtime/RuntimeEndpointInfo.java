package com.sun.xml.rpc.spi.runtime;

import javax.xml.namespace.QName;

public interface RuntimeEndpointInfo {
  void setRemoteInterface(Class paramClass);
  
  void setImplementationClass(Class paramClass);
  
  void setTieClass(Class paramClass);
  
  void setName(String paramString);
  
  void setDeployed(boolean paramBoolean);
  
  void setPortName(QName paramQName);
  
  void setServiceName(QName paramQName);
  
  void setUrlPattern(String paramString);
  
  Class getTieClass();
  
  Class getRemoteInterface();
  
  Class getImplementationClass();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\RuntimeEndpointInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */