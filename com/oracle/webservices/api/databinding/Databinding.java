package com.oracle.webservices.api.databinding;

import com.oracle.webservices.api.message.MessageContext;
import java.lang.reflect.Method;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.WebServiceFeature;
import org.xml.sax.EntityResolver;

public interface Databinding {
  JavaCallInfo createJavaCallInfo(Method paramMethod, Object[] paramArrayOfObject);
  
  MessageContext serializeRequest(JavaCallInfo paramJavaCallInfo);
  
  JavaCallInfo deserializeResponse(MessageContext paramMessageContext, JavaCallInfo paramJavaCallInfo);
  
  JavaCallInfo deserializeRequest(MessageContext paramMessageContext);
  
  MessageContext serializeResponse(JavaCallInfo paramJavaCallInfo);
  
  public static interface Builder {
    Builder targetNamespace(String param1String);
    
    Builder serviceName(QName param1QName);
    
    Builder portName(QName param1QName);
    
    Builder wsdlURL(URL param1URL);
    
    Builder wsdlSource(Source param1Source);
    
    Builder entityResolver(EntityResolver param1EntityResolver);
    
    Builder classLoader(ClassLoader param1ClassLoader);
    
    Builder feature(WebServiceFeature... param1VarArgs);
    
    Builder property(String param1String, Object param1Object);
    
    Databinding build();
    
    WSDLGenerator createWSDLGenerator();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\Databinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */