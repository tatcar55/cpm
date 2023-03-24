package com.sun.org.apache.xml.internal.security.algorithms;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Algorithm extends SignatureElementProxy {
  public Algorithm(Document paramDocument, String paramString) {
    super(paramDocument);
    setAlgorithmURI(paramString);
  }
  
  public Algorithm(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public String getAlgorithmURI() {
    return this._constructionElement.getAttributeNS(null, "Algorithm");
  }
  
  protected void setAlgorithmURI(String paramString) {
    if (paramString != null)
      this._constructionElement.setAttributeNS(null, "Algorithm", paramString); 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\algorithms\Algorithm.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */