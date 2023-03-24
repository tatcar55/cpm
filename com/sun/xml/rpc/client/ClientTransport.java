package com.sun.xml.rpc.client;

import com.sun.xml.rpc.soap.message.SOAPMessageContext;

public interface ClientTransport {
  void invoke(String paramString, SOAPMessageContext paramSOAPMessageContext);
  
  void invokeOneWay(String paramString, SOAPMessageContext paramSOAPMessageContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\ClientTransport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */