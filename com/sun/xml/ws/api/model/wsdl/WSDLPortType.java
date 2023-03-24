package com.sun.xml.ws.api.model.wsdl;

import javax.xml.namespace.QName;

public interface WSDLPortType extends WSDLObject, WSDLExtensible {
  QName getName();
  
  WSDLOperation get(String paramString);
  
  Iterable<? extends WSDLOperation> getOperations();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLPortType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */