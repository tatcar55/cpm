package com.sun.xml.ws.api.client;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.WSService;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.policy.PolicyMap;
import javax.xml.ws.handler.PortInfo;

public interface WSPortInfo extends PortInfo {
  @NotNull
  WSService getOwner();
  
  @NotNull
  BindingID getBindingId();
  
  @NotNull
  EndpointAddress getEndpointAddress();
  
  @Nullable
  WSDLPort getPort();
  
  PolicyMap getPolicyMap();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\client\WSPortInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */