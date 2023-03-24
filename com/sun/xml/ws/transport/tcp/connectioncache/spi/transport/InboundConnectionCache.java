package com.sun.xml.ws.transport.tcp.connectioncache.spi.transport;

public interface InboundConnectionCache<C extends Connection> extends ConnectionCache<C> {
  void requestReceived(C paramC);
  
  void requestProcessed(C paramC, int paramInt);
  
  void responseSent(C paramC);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\transport\InboundConnectionCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */