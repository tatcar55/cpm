package com.sun.xml.ws.rx.mc.dev;

import com.sun.xml.ws.api.addressing.WSEndpointReference;

public interface WsmcRuntimeProvider {
  WSEndpointReference getWsmcAnonymousEndpointReference();
  
  void registerProtocolMessageHandler(ProtocolMessageHandler paramProtocolMessageHandler);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\dev\WsmcRuntimeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */