package com.sun.xml.ws.server.sei;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;

public interface InvokerSource<T extends Invoker> {
  @NotNull
  T getInvoker(Packet paramPacket);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\InvokerSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */