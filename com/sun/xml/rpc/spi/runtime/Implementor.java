package com.sun.xml.rpc.spi.runtime;

import javax.xml.rpc.ServiceException;

public interface Implementor {
  void destroy();
  
  void init() throws ServiceException;
  
  Tie getTie();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\Implementor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */