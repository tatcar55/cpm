package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ObjectContainer extends SignatureElementProxy {
  public ObjectContainer(Document paramDocument) {
    super(paramDocument);
  }
  
  public ObjectContainer(Element paramElement, String paramString) throws XMLSecurityException {
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
  
  public void setMimeType(String paramString) {
    if (paramString != null)
      this._constructionElement.setAttributeNS((String)null, "MimeType", paramString); 
  }
  
  public String getMimeType() {
    return this._constructionElement.getAttributeNS((String)null, "MimeType");
  }
  
  public void setEncoding(String paramString) {
    if (paramString != null)
      this._constructionElement.setAttributeNS((String)null, "Encoding", paramString); 
  }
  
  public String getEncoding() {
    return this._constructionElement.getAttributeNS((String)null, "Encoding");
  }
  
  public Node appendChild(Node paramNode) {
    null = null;
    return this._constructionElement.appendChild(paramNode);
  }
  
  public String getBaseLocalName() {
    return "Object";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\ObjectContainer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */