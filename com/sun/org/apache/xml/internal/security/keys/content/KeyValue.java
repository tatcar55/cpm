package com.sun.org.apache.xml.internal.security.keys.content;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.security.PublicKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KeyValue extends SignatureElementProxy implements KeyInfoContent {
  public KeyValue(Document paramDocument, DSAKeyValue paramDSAKeyValue) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
    this._constructionElement.appendChild(paramDSAKeyValue.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public KeyValue(Document paramDocument, RSAKeyValue paramRSAKeyValue) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
    this._constructionElement.appendChild(paramRSAKeyValue.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public KeyValue(Document paramDocument, Element paramElement) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
    this._constructionElement.appendChild(paramElement);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public KeyValue(Document paramDocument, PublicKey paramPublicKey) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
    if (paramPublicKey instanceof java.security.interfaces.DSAPublicKey) {
      DSAKeyValue dSAKeyValue = new DSAKeyValue(this._doc, paramPublicKey);
      this._constructionElement.appendChild(dSAKeyValue.getElement());
      XMLUtils.addReturnToElement(this._constructionElement);
    } else if (paramPublicKey instanceof java.security.interfaces.RSAPublicKey) {
      RSAKeyValue rSAKeyValue = new RSAKeyValue(this._doc, paramPublicKey);
      this._constructionElement.appendChild(rSAKeyValue.getElement());
      XMLUtils.addReturnToElement(this._constructionElement);
    } 
  }
  
  public KeyValue(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public PublicKey getPublicKey() throws XMLSecurityException {
    Element element1 = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "RSAKeyValue", 0);
    if (element1 != null) {
      RSAKeyValue rSAKeyValue = new RSAKeyValue(element1, this._baseURI);
      return rSAKeyValue.getPublicKey();
    } 
    Element element2 = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "DSAKeyValue", 0);
    if (element2 != null) {
      DSAKeyValue dSAKeyValue = new DSAKeyValue(element2, this._baseURI);
      return dSAKeyValue.getPublicKey();
    } 
    return null;
  }
  
  public String getBaseLocalName() {
    return "KeyValue";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\KeyValue.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */