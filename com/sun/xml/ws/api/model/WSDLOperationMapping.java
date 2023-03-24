package com.sun.xml.ws.api.model;

import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import javax.xml.namespace.QName;

public interface WSDLOperationMapping {
  WSDLBoundOperation getWSDLBoundOperation();
  
  JavaMethod getJavaMethod();
  
  QName getOperationName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\WSDLOperationMapping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */