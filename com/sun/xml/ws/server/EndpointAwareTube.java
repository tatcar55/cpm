package com.sun.xml.ws.server;

import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.WSEndpoint;

public interface EndpointAwareTube extends Tube {
  void setEndpoint(WSEndpoint<?> paramWSEndpoint);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\EndpointAwareTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */