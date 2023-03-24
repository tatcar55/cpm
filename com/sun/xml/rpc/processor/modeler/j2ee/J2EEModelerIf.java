package com.sun.xml.rpc.processor.modeler.j2ee;

import com.sun.xml.rpc.processor.model.Operation;
import com.sun.xml.rpc.processor.model.literal.LiteralType;
import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase;
import com.sun.xml.rpc.wsdl.document.Message;
import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
import javax.xml.namespace.QName;

public interface J2EEModelerIf {
  LiteralType getElementTypeToLiteralType(QName paramQName);
  
  boolean useSuperExplicitServiceContextForDocLit(Message paramMessage);
  
  boolean useSuperExplicitServiceContextForRpcLit(Message paramMessage);
  
  boolean useSuperExplicitServiceContextForRpcEncoded(Message paramMessage);
  
  boolean isSuperUnwrappable();
  
  LiteralType getSuperElementTypeToLiteralType(QName paramQName);
  
  String getSuperJavaNameForOperation(Operation paramOperation);
  
  WSDLModelerBase.ProcessSOAPOperationInfo getInfo();
  
  Message getSuperOutputMessage();
  
  Message getSuperInputMessage();
  
  SOAPBody getSuperSOAPRequestBody();
  
  SOAPBody getSuperSOAPResponseBody();
  
  JavaSimpleTypeCreator getJavaTypes();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EEModelerIf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */