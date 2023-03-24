package com.sun.xml.ws.api.server;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;
import javax.xml.ws.WebServiceContext;

public interface WSWebServiceContext extends WebServiceContext {
  @Nullable
  Packet getRequestPacket();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\WSWebServiceContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */