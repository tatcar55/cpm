package org.jcp.xml.dsig.internal.dom;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import javax.xml.crypto.Data;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.URIReferenceException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMURIReference;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class DOMURIDereferencer implements URIDereferencer {
  static final URIDereferencer INSTANCE = new DOMURIDereferencer();
  
  private DOMURIDereferencer() {
    Init.init();
  }
  
  public Data dereference(URIReference paramURIReference, XMLCryptoContext paramXMLCryptoContext) throws URIReferenceException {
    if (paramURIReference == null)
      throw new NullPointerException("uriRef cannot be null"); 
    if (paramXMLCryptoContext == null)
      throw new NullPointerException("context cannot be null"); 
    DOMURIReference dOMURIReference = (DOMURIReference)paramURIReference;
    Attr attr = (Attr)dOMURIReference.getHere();
    String str = paramURIReference.getURI();
    DOMCryptoContext dOMCryptoContext = (DOMCryptoContext)paramXMLCryptoContext;
    if (str != null && str.length() != 0 && str.charAt(0) == '#') {
      String str1 = str.substring(1);
      if (str1.startsWith("xpointer(id(")) {
        int i = str1.indexOf('\'');
        int j = str1.indexOf('\'', i + 1);
        str1 = str1.substring(i + 1, j);
      } 
      Element element = dOMCryptoContext.getElementById(str1);
      if (element != null)
        IdResolver.registerElementById(element, str1); 
    } 
    try {
      String str1 = paramXMLCryptoContext.getBaseURI();
      ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, str1);
      XMLSignatureInput xMLSignatureInput = resourceResolver.resolve(attr, str1);
      return (Data)(xMLSignatureInput.isOctetStream() ? new ApacheOctetStreamData(xMLSignatureInput) : new ApacheNodeSetData(xMLSignatureInput));
    } catch (Exception exception) {
      throw new URIReferenceException(exception);
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMURIDereferencer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */