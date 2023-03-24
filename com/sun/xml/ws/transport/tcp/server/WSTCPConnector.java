package com.sun.xml.ws.transport.tcp.server;

import java.io.IOException;

public interface WSTCPConnector {
  void listen() throws IOException;
  
  String getHost();
  
  void setHost(String paramString);
  
  int getPort();
  
  void setPort(int paramInt);
  
  TCPMessageListener getListener();
  
  void setListener(TCPMessageListener paramTCPMessageListener);
  
  void setFrameSize(int paramInt);
  
  int getFrameSize();
  
  void close();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\WSTCPConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */