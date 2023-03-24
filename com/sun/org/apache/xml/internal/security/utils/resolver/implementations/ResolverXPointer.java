package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ResolverXPointer extends ResourceResolverSpi {
  static Logger log = Logger.getLogger(ResolverXPointer.class.getName());
  
  private static final String XP = "#xpointer(id(";
  
  private static final int XP_LENGTH = "#xpointer(id(".length();
  
  public boolean engineIsThreadSafe() {
    return true;
  }
  
  public XMLSignatureInput engineResolve(Attr paramAttr, String paramString) throws ResourceResolverException {
    Element element;
    Document document1 = null;
    Document document2 = paramAttr.getOwnerElement().getOwnerDocument();
    String str = paramAttr.getNodeValue();
    if (isXPointerSlash(str)) {
      document1 = document2;
    } else if (isXPointerId(str)) {
      String str1 = getXPointerId(str);
      element = IdResolver.getElementById(document2, str1);
      if (element == null) {
        Object[] arrayOfObject = { str1 };
        throw new ResourceResolverException("signature.Verification.MissingID", arrayOfObject, paramAttr, paramString);
      } 
    } 
    XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(element);
    xMLSignatureInput.setMIMEType("text/xml");
    if (paramString != null && paramString.length() > 0) {
      xMLSignatureInput.setSourceURI(paramString.concat(paramAttr.getNodeValue()));
    } else {
      xMLSignatureInput.setSourceURI(paramAttr.getNodeValue());
    } 
    return xMLSignatureInput;
  }
  
  public boolean engineCanResolve(Attr paramAttr, String paramString) {
    if (paramAttr == null)
      return false; 
    String str = paramAttr.getNodeValue();
    return (isXPointerSlash(str) || isXPointerId(str));
  }
  
  private static boolean isXPointerSlash(String paramString) {
    return paramString.equals("#xpointer(/)");
  }
  
  private static boolean isXPointerId(String paramString) {
    if (paramString.startsWith("#xpointer(id(") && paramString.endsWith("))")) {
      String str = paramString.substring(XP_LENGTH, paramString.length() - 2);
      int i = str.length() - 1;
      if ((str.charAt(0) == '"' && str.charAt(i) == '"') || (str.charAt(0) == '\'' && str.charAt(i) == '\'')) {
        log.log(Level.FINE, "Id=" + str.substring(1, i));
        return true;
      } 
    } 
    return false;
  }
  
  private static String getXPointerId(String paramString) {
    if (paramString.startsWith("#xpointer(id(") && paramString.endsWith("))")) {
      String str = paramString.substring(XP_LENGTH, paramString.length() - 2);
      int i = str.length() - 1;
      if ((str.charAt(0) == '"' && str.charAt(i) == '"') || (str.charAt(0) == '\'' && str.charAt(i) == '\''))
        return str.substring(1, i); 
    } 
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\implementations\ResolverXPointer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */