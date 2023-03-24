package com.sun.xml.ws.api.model.wsdl;

public interface WSDLExtensible extends WSDLObject {
  Iterable<WSDLExtension> getExtensions();
  
  <T extends WSDLExtension> Iterable<T> getExtensions(Class<T> paramClass);
  
  <T extends WSDLExtension> T getExtension(Class<T> paramClass);
  
  void addExtension(WSDLExtension paramWSDLExtension);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLExtensible.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */