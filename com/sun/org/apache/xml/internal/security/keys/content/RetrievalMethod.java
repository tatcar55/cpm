package com.sun.org.apache.xml.internal.security.keys.content;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RetrievalMethod extends SignatureElementProxy implements KeyInfoContent {
  public static final String TYPE_DSA = "http://www.w3.org/2000/09/xmldsig#DSAKeyValue";
  
  public static final String TYPE_RSA = "http://www.w3.org/2000/09/xmldsig#RSAKeyValue";
  
  public static final String TYPE_PGP = "http://www.w3.org/2000/09/xmldsig#PGPData";
  
  public static final String TYPE_SPKI = "http://www.w3.org/2000/09/xmldsig#SPKIData";
  
  public static final String TYPE_MGMT = "http://www.w3.org/2000/09/xmldsig#MgmtData";
  
  public static final String TYPE_X509 = "http://www.w3.org/2000/09/xmldsig#X509Data";
  
  public static final String TYPE_RAWX509 = "http://www.w3.org/2000/09/xmldsig#rawX509Certificate";
  
  public RetrievalMethod(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public RetrievalMethod(Document paramDocument, String paramString1, Transforms paramTransforms, String paramString2) {
    super(paramDocument);
    this._constructionElement.setAttributeNS((String)null, "URI", paramString1);
    if (paramString2 != null)
      this._constructionElement.setAttributeNS((String)null, "Type", paramString2); 
    if (paramTransforms != null) {
      this._constructionElement.appendChild(paramTransforms.getElement());
      XMLUtils.addReturnToElement(this._constructionElement);
    } 
  }
  
  public Attr getURIAttr() {
    return this._constructionElement.getAttributeNodeNS((String)null, "URI");
  }
  
  public String getURI() {
    return getURIAttr().getNodeValue();
  }
  
  public String getType() {
    return this._constructionElement.getAttributeNS((String)null, "Type");
  }
  
  public Transforms getTransforms() throws XMLSecurityException {
    try {
      Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "Transforms", 0);
      return (element != null) ? new Transforms(element, this._baseURI) : null;
    } catch (XMLSignatureException xMLSignatureException) {
      throw new XMLSecurityException("empty", xMLSignatureException);
    } 
  }
  
  public String getBaseLocalName() {
    return "RetrievalMethod";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\RetrievalMethod.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */