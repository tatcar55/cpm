package com.sun.xml.wss.provider.wsit;

import com.sun.xml.ws.api.message.Packet;
import javax.security.auth.message.MessageInfo;

public interface PacketMessageInfo extends MessageInfo {
  SOAPAuthParam getSOAPAuthParam();
  
  Packet getRequestPacket();
  
  Packet getResponsePacket();
  
  void setRequestPacket(Packet paramPacket);
  
  void setResponsePacket(Packet paramPacket);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\PacketMessageInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */