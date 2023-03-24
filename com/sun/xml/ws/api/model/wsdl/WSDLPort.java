package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.EndpointAddress;
import javax.xml.namespace.QName;

public interface WSDLPort extends WSDLFeaturedObject, WSDLExtensible {
  QName getName();
  
  @NotNull
  WSDLBoundPortType getBinding();
  
  EndpointAddress getAddress();
  
  @NotNull
  WSDLService getOwner();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLPort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */