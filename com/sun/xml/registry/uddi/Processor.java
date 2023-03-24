package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.util.MarshallerUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Processor {
  RegistryServiceImpl service;
  
  UDDIMapper mapper;
  
  ResponseTransformer transformer;
  
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  public Processor(RegistryServiceImpl paramRegistryServiceImpl, UDDIMapper paramUDDIMapper) {
    this.service = paramRegistryServiceImpl;
    this.mapper = paramUDDIMapper;
    this.transformer = new ResponseTransformer(paramUDDIMapper);
  }
  
  BulkResponse processRequestJAXB(Object paramObject, boolean paramBoolean, Collection paramCollection, String paramString) throws JAXRException {
    SOAPMessage sOAPMessage = null;
    try {
      sOAPMessage = MarshallerUtil.getInstance().jaxbMarshalObject(paramObject);
    } catch (JAXBException jAXBException) {
      throw new JAXRException(jAXBException);
    } 
    return processResponseJAXB(this.service.send(sOAPMessage, paramBoolean), paramCollection, paramString);
  }
  
  BulkResponse processResponseJAXB(Node paramNode, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl;
    BulkResponse bulkResponse = null;
    if (paramNode == null)
      return null; 
    Node node = null;
    String str = paramNode.getNodeName();
    if (((SOAPBody)paramNode).hasFault()) {
      if (paramNode instanceof Element) {
        NodeList nodeList = ((Element)paramNode).getElementsByTagName("dispositionReport");
        if (nodeList != null) {
          int i = nodeList.getLength();
          if (i > 0)
            for (byte b = 0; b < i; b++) {
              Node node1 = nodeList.item(b);
              if (node1 != null) {
                node = node1;
                str = "dispositionReport";
                break;
              } 
            }  
        } 
      } 
    } else {
      node = paramNode.getFirstChild();
    } 
    this.logger.finest("Node name " + str);
    try {
      Object object = MarshallerUtil.getInstance().jaxbUnmarshalObject(node);
      this.logger.finest("Class name " + object.getClass().getName());
      bulkResponse = invokeMethod(object, paramCollection, paramString);
    } catch (JAXBException jAXBException) {
      throw new JAXRException(jAXBException);
    } 
    if (bulkResponse == null)
      bulkResponseImpl = new BulkResponseImpl(); 
    return (BulkResponse)bulkResponseImpl;
  }
  
  public BulkResponse invokeMethod(Object paramObject, Collection paramCollection, String paramString) throws JAXRException {
    return this.transformer.transformResponse(paramObject, paramCollection, paramString);
  }
  
  public BulkResponse invoke(Object paramObject, Collection paramCollection, String paramString) throws InvocationTargetException, IllegalAccessException, JAXRException {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramObject;
    arrayOfObject[1] = paramCollection;
    arrayOfObject[2] = paramString;
    Class[] arrayOfClass = new Class[3];
    arrayOfClass[0] = arrayOfObject[0].getClass();
    arrayOfClass[1] = Collection.class;
    arrayOfClass[2] = String.class;
    try {
      Class<UDDIMapper> clazz = UDDIMapper.class;
      Method method = clazz.getMethod("transformResponse", arrayOfClass);
      return (BulkResponse)method.invoke(this.mapper, arrayOfObject);
    } catch (InvocationTargetException invocationTargetException) {
      Exception exception = (Exception)invocationTargetException.getTargetException();
      throw new JAXRException(exception);
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new InvocationTargetException(noSuchMethodException, ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Can't_find_method_"));
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\Processor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */