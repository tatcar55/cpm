package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import com.sun.org.apache.xml.internal.utils.URI;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;

public class ResolverDirectHTTP extends ResourceResolverSpi {
  static Logger log = Logger.getLogger(ResolverDirectHTTP.class.getName());
  
  private static final String[] properties = new String[] { "http.proxy.host", "http.proxy.port", "http.proxy.username", "http.proxy.password", "http.basic.username", "http.basic.password" };
  
  private static final int HttpProxyHost = 0;
  
  private static final int HttpProxyPort = 1;
  
  private static final int HttpProxyUser = 2;
  
  private static final int HttpProxyPass = 3;
  
  private static final int HttpBasicUser = 4;
  
  private static final int HttpBasicPass = 5;
  
  public boolean engineIsThreadSafe() {
    return true;
  }
  
  public XMLSignatureInput engineResolve(Attr paramAttr, String paramString) throws ResourceResolverException {
    try {
      boolean bool1 = false;
      String str1 = engineGetProperty(properties[0]);
      String str2 = engineGetProperty(properties[1]);
      if (str1 != null && str2 != null)
        bool1 = true; 
      String str3 = null;
      String str4 = null;
      String str5 = null;
      if (bool1) {
        log.log(Level.FINE, "Use of HTTP proxy enabled: " + str1 + ":" + str2);
        str3 = System.getProperty("http.proxySet");
        str4 = System.getProperty("http.proxyHost");
        str5 = System.getProperty("http.proxyPort");
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", str1);
        System.setProperty("http.proxyPort", str2);
      } 
      boolean bool2 = (str3 != null && str4 != null && str5 != null) ? true : false;
      URI uRI1 = getNewURI(paramAttr.getNodeValue(), paramString);
      URI uRI2 = new URI(uRI1);
      uRI2.setFragment(null);
      URL uRL = new URL(uRI2.toString());
      URLConnection uRLConnection = uRL.openConnection();
      String str6 = engineGetProperty(properties[2]);
      String str7 = engineGetProperty(properties[3]);
      if (str6 != null && str7 != null) {
        String str8 = str6 + ":" + str7;
        String str9 = Base64.encode(str8.getBytes());
        uRLConnection.setRequestProperty("Proxy-Authorization", str9);
      } 
      str6 = uRLConnection.getHeaderField("WWW-Authenticate");
      if (str6 != null && str6.startsWith("Basic")) {
        str7 = engineGetProperty(properties[4]);
        String str = engineGetProperty(properties[5]);
        if (str7 != null && str != null) {
          uRLConnection = uRL.openConnection();
          String str8 = str7 + ":" + str;
          String str9 = Base64.encode(str8.getBytes());
          uRLConnection.setRequestProperty("Authorization", "Basic " + str9);
        } 
      } 
      str6 = uRLConnection.getHeaderField("Content-Type");
      InputStream inputStream = uRLConnection.getInputStream();
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[4096];
      int i = 0;
      int j;
      for (j = 0; (i = inputStream.read(arrayOfByte)) >= 0; j += i)
        byteArrayOutputStream.write(arrayOfByte, 0, i); 
      log.log(Level.FINE, "Fetched " + j + " bytes from URI " + uRI1.toString());
      XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(byteArrayOutputStream.toByteArray());
      xMLSignatureInput.setSourceURI(uRI1.toString());
      xMLSignatureInput.setMIMEType(str6);
      if (bool1 && bool2) {
        System.setProperty("http.proxySet", str3);
        System.setProperty("http.proxyHost", str4);
        System.setProperty("http.proxyPort", str5);
      } 
      return xMLSignatureInput;
    } catch (MalformedURLException malformedURLException) {
      throw new ResourceResolverException("generic.EmptyMessage", malformedURLException, paramAttr, paramString);
    } catch (IOException iOException) {
      throw new ResourceResolverException("generic.EmptyMessage", iOException, paramAttr, paramString);
    } 
  }
  
  public boolean engineCanResolve(Attr paramAttr, String paramString) {
    if (paramAttr == null) {
      log.log(Level.FINE, "quick fail, uri == null");
      return false;
    } 
    String str = paramAttr.getNodeValue();
    if (str.equals("") || str.charAt(0) == '#') {
      log.log(Level.FINE, "quick fail for empty URIs and local ones");
      return false;
    } 
    log.log(Level.FINE, "I was asked whether I can resolve " + str);
    if (str.startsWith("http:") || (paramString != null && paramString.startsWith("http:"))) {
      log.log(Level.FINE, "I state that I can resolve " + str);
      return true;
    } 
    log.log(Level.FINE, "I state that I can't resolve " + str);
    return false;
  }
  
  public String[] engineGetPropertyKeys() {
    return (String[])properties.clone();
  }
  
  private URI getNewURI(String paramString1, String paramString2) throws URI.MalformedURIException {
    return (paramString2 == null || "".equals(paramString2)) ? new URI(paramString1) : new URI(new URI(paramString2), paramString1);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\implementations\ResolverDirectHTTP.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */