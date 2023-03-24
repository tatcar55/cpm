package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import javax.xml.namespace.QName;

public interface WSDLOperation extends WSDLObject, WSDLExtensible {
  @NotNull
  QName getName();
  
  @NotNull
  WSDLInput getInput();
  
  @Nullable
  WSDLOutput getOutput();
  
  boolean isOneWay();
  
  Iterable<? extends WSDLFault> getFaults();
  
  @Nullable
  WSDLFault getFault(QName paramQName);
  
  @NotNull
  QName getPortTypeName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */