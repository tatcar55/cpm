package com.sun.xml.ws.rx.mc.dev;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;
import java.util.Collection;

public interface ProtocolMessageHandler {
  @NotNull
  Collection<String> getSuportedWsaActions();
  
  void processProtocolMessage(Packet paramPacket);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\dev\ProtocolMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */