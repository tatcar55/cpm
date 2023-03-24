package com.sun.xml.ws.client;

import com.sun.xml.ws.developer.JAXWSProperties;

public interface BindingProviderProperties extends JAXWSProperties {
  @Deprecated
  public static final String HOSTNAME_VERIFICATION_PROPERTY = "com.sun.xml.ws.client.http.HostnameVerificationProperty";
  
  public static final String HTTP_COOKIE_JAR = "com.sun.xml.ws.client.http.CookieJar";
  
  public static final String REDIRECT_REQUEST_PROPERTY = "com.sun.xml.ws.client.http.RedirectRequestProperty";
  
  public static final String ONE_WAY_OPERATION = "com.sun.xml.ws.server.OneWayOperation";
  
  public static final String JAXWS_HANDLER_CONFIG = "com.sun.xml.ws.handler.config";
  
  public static final String JAXWS_CLIENT_HANDLE_PROPERTY = "com.sun.xml.ws.client.handle";
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\BindingProviderProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */