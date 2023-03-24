package com.sun.xml.ws.transport.tcp.servicechannel.stubs;

import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "ServiceChannelWSImpl", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/")
@XmlSeeAlso({ObjectFactory.class})
public interface ServiceChannelWSImpl {
  @WebMethod
  @RequestWrapper(localName = "initiateSession", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", className = "com.sun.xml.ws.transport.tcp.servicechannel.stubs.InitiateSession")
  @ResponseWrapper(localName = "initiateSessionResponse", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", className = "com.sun.xml.ws.transport.tcp.servicechannel.stubs.InitiateSessionResponse")
  void initiateSession() throws ServiceChannelException;
  
  @WebMethod
  @WebResult(name = "channelId", targetNamespace = "")
  @RequestWrapper(localName = "openChannel", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", className = "com.sun.xml.ws.transport.tcp.servicechannel.stubs.OpenChannel")
  @ResponseWrapper(localName = "openChannelResponse", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", className = "com.sun.xml.ws.transport.tcp.servicechannel.stubs.OpenChannelResponse")
  int openChannel(@WebParam(name = "targetWSURI", targetNamespace = "") String paramString, @WebParam(name = "negotiatedMimeTypes", targetNamespace = "", mode = WebParam.Mode.INOUT) Holder<List<String>> paramHolder1, @WebParam(name = "negotiatedParams", targetNamespace = "", mode = WebParam.Mode.INOUT) Holder<List<String>> paramHolder2) throws ServiceChannelException;
  
  @WebMethod
  @RequestWrapper(localName = "closeChannel", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", className = "com.sun.xml.ws.transport.tcp.servicechannel.stubs.CloseChannel")
  @ResponseWrapper(localName = "closeChannelResponse", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/", className = "com.sun.xml.ws.transport.tcp.servicechannel.stubs.CloseChannelResponse")
  void closeChannel(@WebParam(name = "channelId", targetNamespace = "") int paramInt) throws ServiceChannelException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\stubs\ServiceChannelWSImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */