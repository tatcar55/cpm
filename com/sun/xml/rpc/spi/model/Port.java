package com.sun.xml.rpc.spi.model;

import com.sun.xml.rpc.spi.tools.HandlerChainInfo;
import javax.xml.namespace.QName;

public interface Port extends ModelObject {
  QName getName();
  
  HandlerChainInfo getServerHCI();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\model\Port.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */