package com.sun.xml.ws.api.wsdl.parser;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.WSDLModel;
import com.sun.xml.ws.api.policy.PolicyResolver;
import com.sun.xml.ws.api.server.Container;

public interface WSDLParserExtensionContext {
  boolean isClientSide();
  
  WSDLModel getWSDLModel();
  
  @NotNull
  Container getContainer();
  
  @NotNull
  PolicyResolver getPolicyResolver();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\wsdl\parser\WSDLParserExtensionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */