package com.sun.xml.ws.security.secconv;

import com.sun.xml.ws.api.message.Packet;
import javax.xml.bind.JAXBElement;

public interface SecureConversationInitiator {
  JAXBElement startSecureConversation(Packet paramPacket) throws WSSecureConversationException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\SecureConversationInitiator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */