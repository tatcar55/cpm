package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class SignatureElementProxy extends ElementProxy {
  protected SignatureElementProxy() {}
  
  public SignatureElementProxy(Document paramDocument) {
    if (paramDocument == null)
      throw new RuntimeException("Document is null"); 
    this._doc = paramDocument;
    this._constructionElement = XMLUtils.createElementInSignatureSpace(this._doc, getBaseLocalName());
  }
  
  public SignatureElementProxy(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public String getBaseNamespace() {
    return "http://www.w3.org/2000/09/xmldsig#";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\SignatureElementProxy.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */