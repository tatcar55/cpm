package com.sun.org.apache.xml.internal.security.keys.content;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Element;

public class PGPData extends SignatureElementProxy implements KeyInfoContent {
  public PGPData(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public String getBaseLocalName() {
    return "PGPData";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\PGPData.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */