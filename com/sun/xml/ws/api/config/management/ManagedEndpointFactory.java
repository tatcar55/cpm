package com.sun.xml.ws.api.config.management;

import com.sun.xml.ws.api.server.WSEndpoint;

public interface ManagedEndpointFactory {
  <T> WSEndpoint<T> createEndpoint(WSEndpoint<T> paramWSEndpoint, EndpointCreationAttributes paramEndpointCreationAttributes);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\config\management\ManagedEndpointFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */