package com.sun.xml.ws.api.model;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.util.Pool;
import java.lang.reflect.Method;
import java.util.Collection;
import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;

public interface SEIModel {
  Pool.Marshaller getMarshallerPool();
  
  JAXBContext getJAXBContext();
  
  JavaMethod getJavaMethod(Method paramMethod);
  
  JavaMethod getJavaMethod(QName paramQName);
  
  JavaMethod getJavaMethodForWsdlOperation(QName paramQName);
  
  Collection<? extends JavaMethod> getJavaMethods();
  
  @NotNull
  String getWSDLLocation();
  
  @NotNull
  QName getServiceQName();
  
  @NotNull
  WSDLPort getPort();
  
  @NotNull
  QName getPortName();
  
  @NotNull
  QName getPortTypeName();
  
  @NotNull
  QName getBoundPortTypeName();
  
  @NotNull
  String getTargetNamespace();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\SEIModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */