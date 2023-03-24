package com.sun.xml.ws.api.server;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import javax.xml.namespace.QName;

public abstract class EndpointReferenceExtensionContributor {
  public abstract WSEndpointReference.EPRExtension getEPRExtension(WSEndpoint paramWSEndpoint, @Nullable WSEndpointReference.EPRExtension paramEPRExtension);
  
  public abstract QName getQName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\EndpointReferenceExtensionContributor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */