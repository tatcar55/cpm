package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import javax.xml.namespace.QName;

public interface WSDLBoundFault extends WSDLObject, WSDLExtensible {
  @NotNull
  String getName();
  
  @Nullable
  QName getQName();
  
  @Nullable
  WSDLFault getFault();
  
  @NotNull
  WSDLBoundOperation getBoundOperation();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLBoundFault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */