package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;
import java.security.Principal;

public interface WebServiceContextDelegate {
  Principal getUserPrincipal(@NotNull Packet paramPacket);
  
  boolean isUserInRole(@NotNull Packet paramPacket, String paramString);
  
  @NotNull
  String getEPRAddress(@NotNull Packet paramPacket, @NotNull WSEndpoint paramWSEndpoint);
  
  @Nullable
  String getWSDLAddress(@NotNull Packet paramPacket, @NotNull WSEndpoint paramWSEndpoint);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\WebServiceContextDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */