package com.sun.xml.ws.transport.tcp.connectioncache.spi.transport;

import java.io.IOException;

public interface OutboundConnectionCache<C extends Connection> extends ConnectionCache<C> {
  int maxParallelConnections();
  
  boolean canCreateNewConnection(ContactInfo<C> paramContactInfo);
  
  C get(ContactInfo<C> paramContactInfo, ConnectionFinder<C> paramConnectionFinder) throws IOException;
  
  C get(ContactInfo<C> paramContactInfo) throws IOException;
  
  void release(C paramC, int paramInt);
  
  void responseReceived(C paramC);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\transport\OutboundConnectionCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */