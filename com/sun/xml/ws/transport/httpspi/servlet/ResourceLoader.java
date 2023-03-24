package com.sun.xml.ws.transport.httpspi.servlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public interface ResourceLoader {
  URL getResource(String paramString) throws MalformedURLException;
  
  URL getCatalogFile() throws MalformedURLException;
  
  Set<String> getResourcePaths(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\ResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */