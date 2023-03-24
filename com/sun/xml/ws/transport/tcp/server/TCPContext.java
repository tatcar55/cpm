package com.sun.xml.ws.transport.tcp.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public interface TCPContext {
  URL getResource(String paramString) throws MalformedURLException;
  
  InputStream getResourceAsStream(String paramString) throws IOException;
  
  Set<String> getResourcePaths(String paramString);
  
  Object getAttribute(String paramString);
  
  void setAttribute(String paramString, Object paramObject);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */