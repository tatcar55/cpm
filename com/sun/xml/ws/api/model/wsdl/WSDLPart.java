package com.sun.xml.ws.api.model.wsdl;

import com.sun.xml.ws.api.model.ParameterBinding;

public interface WSDLPart extends WSDLObject {
  String getName();
  
  ParameterBinding getBinding();
  
  int getIndex();
  
  WSDLPartDescriptor getDescriptor();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */