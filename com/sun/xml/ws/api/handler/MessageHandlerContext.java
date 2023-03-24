package com.sun.xml.ws.api.handler;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import java.util.Set;
import javax.xml.ws.handler.MessageContext;

public interface MessageHandlerContext extends MessageContext {
  Message getMessage();
  
  void setMessage(Message paramMessage);
  
  Set<String> getRoles();
  
  WSBinding getWSBinding();
  
  @Nullable
  SEIModel getSEIModel();
  
  @Nullable
  WSDLPort getPort();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\handler\MessageHandlerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */