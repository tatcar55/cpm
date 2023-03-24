package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import javax.xml.namespace.QName;

public interface WSDLService extends WSDLObject, WSDLExtensible {
  @NotNull
  WSDLModel getParent();
  
  @NotNull
  QName getName();
  
  WSDLPort get(QName paramQName);
  
  WSDLPort getFirstPort();
  
  Iterable<? extends WSDLPort> getPorts();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */