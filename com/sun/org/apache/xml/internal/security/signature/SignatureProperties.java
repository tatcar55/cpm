package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureProperties extends SignatureElementProxy {
  public SignatureProperties(Document paramDocument) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public SignatureProperties(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public int getLength() {
    Element[] arrayOfElement = XMLUtils.selectDsNodes(this._constructionElement, "SignatureProperty");
    return arrayOfElement.length;
  }
  
  public SignatureProperty item(int paramInt) throws XMLSignatureException {
    try {
      Element element = XMLUtils.selectDsNode(this._constructionElement, "SignatureProperty", paramInt);
      return (element == null) ? null : new SignatureProperty(element, this._baseURI);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public void setId(String paramString) {
    if (paramString != null) {
      this._constructionElement.setAttributeNS((String)null, "Id", paramString);
      IdResolver.registerElementById(this._constructionElement, paramString);
    } 
  }
  
  public String getId() {
    return this._constructionElement.getAttributeNS((String)null, "Id");
  }
  
  public void addSignatureProperty(SignatureProperty paramSignatureProperty) {
    this._constructionElement.appendChild(paramSignatureProperty.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public String getBaseLocalName() {
    return "SignatureProperties";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\SignatureProperties.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */