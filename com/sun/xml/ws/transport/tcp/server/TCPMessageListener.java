package com.sun.xml.ws.transport.tcp.server;

import com.sun.xml.ws.transport.tcp.util.ChannelContext;
import com.sun.xml.ws.transport.tcp.util.WSTCPError;
import java.io.IOException;

public interface TCPMessageListener {
  void onMessage(ChannelContext paramChannelContext) throws IOException;
  
  void onError(ChannelContext paramChannelContext, WSTCPError paramWSTCPError) throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPMessageListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */