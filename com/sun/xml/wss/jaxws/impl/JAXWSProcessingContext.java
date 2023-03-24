package com.sun.xml.wss.jaxws.impl;

import com.sun.xml.ws.api.message.Message;
import com.sun.xml.wss.SecurityProcessingContext;

public interface JAXWSProcessingContext extends SecurityProcessingContext {
  void setMessage(Message paramMessage);
  
  Message getMessage();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\JAXWSProcessingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */