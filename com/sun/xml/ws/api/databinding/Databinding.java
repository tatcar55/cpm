package com.sun.xml.ws.api.databinding;

import com.oracle.webservices.api.databinding.Databinding;
import com.sun.xml.ws.api.message.MessageContextFactory;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.wsdl.DispatchException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public interface Databinding extends Databinding {
  EndpointCallBridge getEndpointBridge(Packet paramPacket) throws DispatchException;
  
  ClientCallBridge getClientBridge(Method paramMethod);
  
  void generateWSDL(WSDLGenInfo paramWSDLGenInfo);
  
  ContentType encode(Packet paramPacket, OutputStream paramOutputStream) throws IOException;
  
  void decode(InputStream paramInputStream, String paramString, Packet paramPacket) throws IOException;
  
  MessageContextFactory getMessageContextFactory();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\Databinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */