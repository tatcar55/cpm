package com.sun.xml.ws.assembler.dev;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.policy.PolicyMap;

public interface ServerTubelineAssemblyContext extends TubelineAssemblyContext {
  @NotNull
  Codec getCodec();
  
  @NotNull
  WSEndpoint getEndpoint();
  
  PolicyMap getPolicyMap();
  
  @Nullable
  SEIModel getSEIModel();
  
  @NotNull
  Tube getTerminalTube();
  
  ServerTubeAssemblerContext getWrappedContext();
  
  @Nullable
  WSDLPort getWsdlPort();
  
  boolean isPolicyAvailable();
  
  boolean isSynchronous();
  
  void setCodec(@NotNull Codec paramCodec);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\dev\ServerTubelineAssemblyContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */