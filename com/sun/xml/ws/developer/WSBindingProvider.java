package com.sun.xml.ws.developer;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.ComponentRegistry;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.message.Header;
import java.io.Closeable;
import java.util.List;
import javax.xml.ws.BindingProvider;
import org.glassfish.gmbal.ManagedObjectManager;

public interface WSBindingProvider extends BindingProvider, Closeable, ComponentRegistry {
  void setOutboundHeaders(List<Header> paramList);
  
  void setOutboundHeaders(Header... paramVarArgs);
  
  void setOutboundHeaders(Object... paramVarArgs);
  
  List<Header> getInboundHeaders();
  
  void setAddress(String paramString);
  
  WSEndpointReference getWSEndpointReference();
  
  WSPortInfo getPortInfo();
  
  @NotNull
  ManagedObjectManager getManagedObjectManager();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\WSBindingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */