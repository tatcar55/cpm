package com.sun.xml.ws.api.model.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.model.ParameterBinding;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;

public interface WSDLBoundPortType extends WSDLFeaturedObject, WSDLExtensible {
  QName getName();
  
  @NotNull
  WSDLModel getOwner();
  
  WSDLBoundOperation get(QName paramQName);
  
  QName getPortTypeName();
  
  WSDLPortType getPortType();
  
  Iterable<? extends WSDLBoundOperation> getBindingOperations();
  
  @NotNull
  SOAPBinding.Style getStyle();
  
  BindingID getBindingId();
  
  @Nullable
  WSDLBoundOperation getOperation(String paramString1, String paramString2);
  
  ParameterBinding getBinding(QName paramQName, String paramString, WebParam.Mode paramMode);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\wsdl\WSDLBoundPortType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */