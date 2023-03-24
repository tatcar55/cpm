package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SignatureProperty extends SignatureElementProxy {
  public SignatureProperty(Document paramDocument, String paramString) {
    this(paramDocument, paramString, null);
  }
  
  public SignatureProperty(Document paramDocument, String paramString1, String paramString2) {
    super(paramDocument);
    setTarget(paramString1);
    setId(paramString2);
  }
  
  public SignatureProperty(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
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
  
  public void setTarget(String paramString) {
    if (paramString != null)
      this._constructionElement.setAttributeNS((String)null, "Target", paramString); 
  }
  
  public String getTarget() {
    return this._constructionElement.getAttributeNS((String)null, "Target");
  }
  
  public Node appendChild(Node paramNode) {
    return this._constructionElement.appendChild(paramNode);
  }
  
  public String getBaseLocalName() {
    return "SignatureProperty";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\SignatureProperty.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */