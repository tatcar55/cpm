package com.sun.xml.ws.assembler.dev;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.EndpointAddress;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.WSService;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.policy.PolicyMap;

public interface ClientTubelineAssemblyContext extends TubelineAssemblyContext {
  @NotNull
  EndpointAddress getAddress();
  
  @NotNull
  WSBinding getBinding();
  
  @NotNull
  Codec getCodec();
  
  Container getContainer();
  
  PolicyMap getPolicyMap();
  
  WSPortInfo getPortInfo();
  
  @Nullable
  SEIModel getSEIModel();
  
  @NotNull
  WSService getService();
  
  ClientTubeAssemblerContext getWrappedContext();
  
  WSDLPort getWsdlPort();
  
  boolean isPolicyAvailable();
  
  void setCodec(@NotNull Codec paramCodec);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\dev\ClientTubelineAssemblyContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */