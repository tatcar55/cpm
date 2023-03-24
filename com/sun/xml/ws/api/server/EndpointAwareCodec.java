package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.pipe.Codec;

public interface EndpointAwareCodec extends Codec {
  void setEndpoint(@NotNull WSEndpoint paramWSEndpoint);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\EndpointAwareCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */