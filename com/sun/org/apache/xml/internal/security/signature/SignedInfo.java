package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SignedInfo extends Manifest {
  private SignatureAlgorithm _signatureAlgorithm = null;
  
  private byte[] _c14nizedBytes = null;
  
  private Element c14nMethod = XMLUtils.createElementInSignatureSpace(this._doc, "CanonicalizationMethod");
  
  private Element signatureMethod;
  
  public SignedInfo(Document paramDocument) throws XMLSecurityException {
    this(paramDocument, "http://www.w3.org/2000/09/xmldsig#dsa-sha1", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
  }
  
  public SignedInfo(Document paramDocument, String paramString1, String paramString2) throws XMLSecurityException {
    this(paramDocument, paramString1, 0, paramString2);
  }
  
  public SignedInfo(Document paramDocument, String paramString1, int paramInt, String paramString2) throws XMLSecurityException {
    super(paramDocument);
    this.c14nMethod.setAttributeNS((String)null, "Algorithm", paramString2);
    this._constructionElement.appendChild(this.c14nMethod);
    XMLUtils.addReturnToElement(this._constructionElement);
    if (paramInt > 0) {
      this._signatureAlgorithm = new SignatureAlgorithm(this._doc, paramString1, paramInt);
    } else {
      this._signatureAlgorithm = new SignatureAlgorithm(this._doc, paramString1);
    } 
    this.signatureMethod = this._signatureAlgorithm.getElement();
    this._constructionElement.appendChild(this.signatureMethod);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public SignedInfo(Document paramDocument, Element paramElement1, Element paramElement2) throws XMLSecurityException {
    super(paramDocument);
    this._constructionElement.appendChild(this.c14nMethod);
    XMLUtils.addReturnToElement(this._constructionElement);
    this._signatureAlgorithm = new SignatureAlgorithm(paramElement1, null);
    this.signatureMethod = this._signatureAlgorithm.getElement();
    this._constructionElement.appendChild(this.signatureMethod);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public SignedInfo(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
    String str = getCanonicalizationMethodURI();
    if (!str.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") && !str.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments") && !str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && !str.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments"))
      try {
        Canonicalizer canonicalizer = Canonicalizer.getInstance(getCanonicalizationMethodURI());
        this._c14nizedBytes = canonicalizer.canonicalizeSubtree(this._constructionElement);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(this._c14nizedBytes));
        Node node = this._doc.importNode(document.getDocumentElement(), true);
        this._constructionElement.getParentNode().replaceChild(node, this._constructionElement);
        this._constructionElement = (Element)node;
      } catch (ParserConfigurationException parserConfigurationException) {
        throw new XMLSecurityException("empty", parserConfigurationException);
      } catch (IOException iOException) {
        throw new XMLSecurityException("empty", iOException);
      } catch (SAXException sAXException) {
        throw new XMLSecurityException("empty", sAXException);
      }  
    this.signatureMethod = XMLUtils.getNextElement(this.c14nMethod.getNextSibling());
    this._signatureAlgorithm = new SignatureAlgorithm(this.signatureMethod, getBaseURI());
  }
  
  public boolean verify() throws MissingResourceFailureException, XMLSecurityException {
    return verifyReferences(false);
  }
  
  public boolean verify(boolean paramBoolean) throws MissingResourceFailureException, XMLSecurityException {
    return verifyReferences(paramBoolean);
  }
  
  public byte[] getCanonicalizedOctetStream() throws CanonicalizationException, InvalidCanonicalizerException, XMLSecurityException {
    if (this._c14nizedBytes == null) {
      Canonicalizer canonicalizer = Canonicalizer.getInstance(getCanonicalizationMethodURI());
      this._c14nizedBytes = canonicalizer.canonicalizeSubtree(this._constructionElement);
    } 
    byte[] arrayOfByte = new byte[this._c14nizedBytes.length];
    System.arraycopy(this._c14nizedBytes, 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  public void signInOctectStream(OutputStream paramOutputStream) throws CanonicalizationException, InvalidCanonicalizerException, XMLSecurityException {
    if (this._c14nizedBytes == null) {
      Canonicalizer canonicalizer = Canonicalizer.getInstance(getCanonicalizationMethodURI());
      canonicalizer.setWriter(paramOutputStream);
      String str = getInclusiveNamespaces();
      if (str == null) {
        canonicalizer.canonicalizeSubtree(this._constructionElement);
      } else {
        canonicalizer.canonicalizeSubtree(this._constructionElement, str);
      } 
    } else {
      try {
        paramOutputStream.write(this._c14nizedBytes);
      } catch (IOException iOException) {
        throw new RuntimeException("" + iOException);
      } 
    } 
  }
  
  public String getCanonicalizationMethodURI() {
    return this.c14nMethod.getAttributeNS((String)null, "Algorithm");
  }
  
  public String getSignatureMethodURI() {
    Element element = getSignatureMethodElement();
    return (element != null) ? element.getAttributeNS((String)null, "Algorithm") : null;
  }
  
  public Element getSignatureMethodElement() {
    return this.signatureMethod;
  }
  
  public SecretKey createSecretKey(byte[] paramArrayOfbyte) {
    return new SecretKeySpec(paramArrayOfbyte, this._signatureAlgorithm.getJCEAlgorithmString());
  }
  
  protected SignatureAlgorithm getSignatureAlgorithm() {
    return this._signatureAlgorithm;
  }
  
  public String getBaseLocalName() {
    return "SignedInfo";
  }
  
  public String getInclusiveNamespaces() {
    String str = this.c14nMethod.getAttributeNS((String)null, "Algorithm");
    if (!str.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && !str.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments"))
      return null; 
    Element element = XMLUtils.getNextElement(this.c14nMethod.getFirstChild());
    if (element != null)
      try {
        return (new InclusiveNamespaces(element, "http://www.w3.org/2001/10/xml-exc-c14n#")).getInclusiveNamespaces();
      } catch (XMLSecurityException xMLSecurityException) {
        return null;
      }  
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\SignedInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */