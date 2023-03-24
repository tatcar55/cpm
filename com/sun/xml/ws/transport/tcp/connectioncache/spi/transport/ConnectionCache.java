package com.sun.xml.ws.transport.tcp.connectioncache.spi.transport;

public interface ConnectionCache<C extends Connection> {
  String getCacheType();
  
  long numberOfConnections();
  
  long numberOfIdleConnections();
  
  long numberOfBusyConnections();
  
  long numberOfReclaimableConnections();
  
  int highWaterMark();
  
  int numberToReclaim();
  
  void close(C paramC);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\transport\ConnectionCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */