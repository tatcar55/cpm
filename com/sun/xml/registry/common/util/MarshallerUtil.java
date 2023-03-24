package com.sun.xml.registry.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Node;

public class MarshallerUtil {
  Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.registry.common");
  
  private static MarshallerUtil instance = null;
  
  private String jaxrHome = null;
  
  private JAXBContext jc = JAXBContext.newInstance("com.sun.xml.registry.uddi.bindings_v2_2");
  
  public static MarshallerUtil getInstance() throws JAXBException {
    if (instance == null)
      synchronized (MarshallerUtil.class) {
        if (instance == null)
          instance = new MarshallerUtil(); 
      }  
    return instance;
  }
  
  public SOAPMessage jaxbMarshalObject(Object paramObject) throws JAXBException {
    SOAPMessage sOAPMessage = null;
    Marshaller marshaller = this.jc.createMarshaller();
    try {
      MessageFactory messageFactory = MessageFactory.newInstance();
      sOAPMessage = messageFactory.createMessage();
      sOAPMessage.setProperty("javax.xml.soap.write-xml-declaration", "true");
      marshaller.marshal(paramObject, sOAPMessage.getSOAPBody());
    } catch (SOAPException sOAPException) {
      throw new JAXBException(sOAPException);
    } 
    return sOAPMessage;
  }
  
  public Object jaxbUnmarshalInputStream(InputStream paramInputStream) throws JAXBException {
    Unmarshaller unmarshaller = this.jc.createUnmarshaller();
    return unmarshaller.unmarshal(paramInputStream);
  }
  
  public OutputStream jaxbMarshalOutStream(Object paramObject) throws JAXBException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Marshaller marshaller = this.jc.createMarshaller();
    marshaller.marshal(paramObject, byteArrayOutputStream);
    return byteArrayOutputStream;
  }
  
  public Object jaxbUnmarshalObject(Node paramNode) throws JAXBException {
    Unmarshaller unmarshaller = this.jc.createUnmarshaller();
    return unmarshaller.unmarshal(paramNode);
  }
  
  public static String generateUUID() {
    String str = null;
    try {
      str = InetAddress.getLocalHost() + (new UID()).toString();
    } catch (UnknownHostException unknownHostException) {
      unknownHostException.printStackTrace();
    } 
    return str;
  }
  
  public void log(SOAPMessage paramSOAPMessage) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      paramSOAPMessage.writeTo(byteArrayOutputStream);
    } catch (SOAPException sOAPException) {
      sOAPException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    log(byteArrayOutputStream);
  }
  
  private void log(ByteArrayOutputStream paramByteArrayOutputStream) {
    String str = paramByteArrayOutputStream.toString();
    if (this.logger.isLoggable(Level.FINEST))
      if (str.indexOf("get_authToken") != -1) {
        XMLUtil.getInstance();
        this.logger.finest(XMLUtil.authToken2XXX(str));
      } else if (str.indexOf("authInfo") != -1) {
        XMLUtil.getInstance();
        this.logger.finest(XMLUtil.authInfo2XXX(str));
      } else {
        this.logger.finest(str);
      }  
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\commo\\util\MarshallerUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */