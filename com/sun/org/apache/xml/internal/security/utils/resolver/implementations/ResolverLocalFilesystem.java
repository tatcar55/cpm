package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import com.sun.org.apache.xml.internal.utils.URI;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;

public class ResolverLocalFilesystem extends ResourceResolverSpi {
  static Logger log = Logger.getLogger(ResolverLocalFilesystem.class.getName());
  
  private static int FILE_URI_LENGTH = "file:/".length();
  
  public boolean engineIsThreadSafe() {
    return true;
  }
  
  public XMLSignatureInput engineResolve(Attr paramAttr, String paramString) throws ResourceResolverException {
    try {
      URI uRI1 = getNewURI(paramAttr.getNodeValue(), paramString);
      URI uRI2 = new URI(uRI1);
      uRI2.setFragment(null);
      String str = translateUriToFilename(uRI2.toString());
      FileInputStream fileInputStream = new FileInputStream(str);
      XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(fileInputStream);
      xMLSignatureInput.setSourceURI(uRI1.toString());
      return xMLSignatureInput;
    } catch (Exception exception) {
      throw new ResourceResolverException("generic.EmptyMessage", exception, paramAttr, paramString);
    } 
  }
  
  private static String translateUriToFilename(String paramString) {
    String str = paramString.substring(FILE_URI_LENGTH);
    if (str.indexOf("%20") > -1) {
      int i = 0;
      int j = 0;
      StringBuffer stringBuffer = new StringBuffer(str.length());
      while (true) {
        j = str.indexOf("%20", i);
        if (j == -1) {
          stringBuffer.append(str.substring(i));
        } else {
          stringBuffer.append(str.substring(i, j));
          stringBuffer.append(' ');
          i = j + 3;
        } 
        if (j == -1) {
          str = stringBuffer.toString();
          break;
        } 
      } 
    } 
    return (str.charAt(1) == ':') ? str : ("/" + str);
  }
  
  public boolean engineCanResolve(Attr paramAttr, String paramString) {
    if (paramAttr == null)
      return false; 
    String str = paramAttr.getNodeValue();
    if (str.equals("") || str.charAt(0) == '#' || str.startsWith("http:"))
      return false; 
    try {
      log.log(Level.FINE, "I was asked whether I can resolve " + str);
      if (str.startsWith("file:") || paramString.startsWith("file:")) {
        log.log(Level.FINE, "I state that I can resolve " + str);
        return true;
      } 
    } catch (Exception exception) {}
    log.log(Level.FINE, "But I can't");
    return false;
  }
  
  private static URI getNewURI(String paramString1, String paramString2) throws URI.MalformedURIException {
    return (paramString2 == null || "".equals(paramString2)) ? new URI(paramString1) : new URI(new URI(paramString2), paramString1);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\implementations\ResolverLocalFilesystem.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */