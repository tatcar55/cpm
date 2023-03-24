package com.sun.xml.ws.transport.http.client;

import java.net.URI;
import java.util.List;

interface CookieStore {
  void add(URI paramURI, HttpCookie paramHttpCookie);
  
  List<HttpCookie> get(URI paramURI);
  
  List<HttpCookie> getCookies();
  
  List<URI> getURIs();
  
  boolean remove(URI paramURI, HttpCookie paramHttpCookie);
  
  boolean removeAll();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\CookieStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */