package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import javax.xml.namespace.QName;

public interface WSDLFault extends WSDLObject, WSDLExtensible {
  String getName();
  
  WSDLMessage getMessage();
  
  @NotNull
  WSDLOperation getOperation();
  
  @NotNull
  QName getQName();
  
  String getAction();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLFault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */