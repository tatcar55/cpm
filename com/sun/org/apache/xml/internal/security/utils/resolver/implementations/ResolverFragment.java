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

public class ResolverFragment extends ResourceResolverSpi {
  static Logger log = Logger.getLogger(ResolverFragment.class.getName());
  
  public boolean engineIsThreadSafe() {
    return true;
  }
  
  public XMLSignatureInput engineResolve(Attr paramAttr, String paramString) throws ResourceResolverException {
    Element element;
    String str = paramAttr.getNodeValue();
    Document document1 = paramAttr.getOwnerElement().getOwnerDocument();
    Document document2 = null;
    if (str.equals("")) {
      log.log(Level.FINE, "ResolverFragment with empty URI (means complete document)");
      document2 = document1;
    } else {
      String str1 = str.substring(1);
      element = IdResolver.getElementById(document1, str1);
      if (element == null) {
        Object[] arrayOfObject = { str1 };
        throw new ResourceResolverException("signature.Verification.MissingID", arrayOfObject, paramAttr, paramString);
      } 
      log.log(Level.FINE, "Try to catch an Element with ID " + str1 + " and Element was " + element);
    } 
    XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(element);
    xMLSignatureInput.setExcludeComments(true);
    xMLSignatureInput.setMIMEType("text/xml");
    xMLSignatureInput.setSourceURI((paramString != null) ? paramString.concat(paramAttr.getNodeValue()) : paramAttr.getNodeValue());
    return xMLSignatureInput;
  }
  
  public boolean engineCanResolve(Attr paramAttr, String paramString) {
    if (paramAttr == null) {
      log.log(Level.FINE, "Quick fail for null uri");
      return false;
    } 
    String str = paramAttr.getNodeValue();
    if (str.equals("") || (str.charAt(0) == '#' && (str.charAt(1) != 'x' || !str.startsWith("#xpointer(")))) {
      log.log(Level.FINE, "State I can resolve reference: \"" + str + "\"");
      return true;
    } 
    log.log(Level.FINE, "Do not seem to be able to resolve reference: \"" + str + "\"");
    return false;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\implementations\ResolverFragment.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */