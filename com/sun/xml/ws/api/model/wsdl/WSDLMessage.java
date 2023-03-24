package com.sun.xml.ws.api.model.wsdl;

import javax.xml.namespace.QName;

public interface WSDLMessage extends WSDLObject, WSDLExtensible {
  QName getName();
  
  Iterable<? extends WSDLPart> parts();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */