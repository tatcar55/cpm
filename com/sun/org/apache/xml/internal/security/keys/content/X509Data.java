package com.sun.org.apache.xml.internal.security.keys.content;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509CRL;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Certificate;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509IssuerSerial;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SubjectName;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class X509Data extends SignatureElementProxy implements KeyInfoContent {
  static Logger log = Logger.getLogger(X509Data.class.getName());
  
  public X509Data(Document paramDocument) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public X509Data(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
    Node node = this._constructionElement.getFirstChild();
    while (node != null) {
      if (node.getNodeType() != 1) {
        node = node.getNextSibling();
        continue;
      } 
      return;
    } 
    Object[] arrayOfObject = { "Elements", "X509Data" };
    throw new XMLSecurityException("xml.WrongContent", arrayOfObject);
  }
  
  public void addIssuerSerial(String paramString, BigInteger paramBigInteger) {
    add(new XMLX509IssuerSerial(this._doc, paramString, paramBigInteger));
  }
  
  public void addIssuerSerial(String paramString1, String paramString2) {
    add(new XMLX509IssuerSerial(this._doc, paramString1, paramString2));
  }
  
  public void addIssuerSerial(String paramString, int paramInt) {
    add(new XMLX509IssuerSerial(this._doc, paramString, paramInt));
  }
  
  public void add(XMLX509IssuerSerial paramXMLX509IssuerSerial) {
    this._constructionElement.appendChild(paramXMLX509IssuerSerial.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addSKI(byte[] paramArrayOfbyte) {
    add(new XMLX509SKI(this._doc, paramArrayOfbyte));
  }
  
  public void addSKI(X509Certificate paramX509Certificate) throws XMLSecurityException {
    add(new XMLX509SKI(this._doc, paramX509Certificate));
  }
  
  public void add(XMLX509SKI paramXMLX509SKI) {
    this._constructionElement.appendChild(paramXMLX509SKI.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addSubjectName(String paramString) {
    add(new XMLX509SubjectName(this._doc, paramString));
  }
  
  public void addSubjectName(X509Certificate paramX509Certificate) {
    add(new XMLX509SubjectName(this._doc, paramX509Certificate));
  }
  
  public void add(XMLX509SubjectName paramXMLX509SubjectName) {
    this._constructionElement.appendChild(paramXMLX509SubjectName.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addCertificate(X509Certificate paramX509Certificate) throws XMLSecurityException {
    add(new XMLX509Certificate(this._doc, paramX509Certificate));
  }
  
  public void addCertificate(byte[] paramArrayOfbyte) {
    add(new XMLX509Certificate(this._doc, paramArrayOfbyte));
  }
  
  public void add(XMLX509Certificate paramXMLX509Certificate) {
    this._constructionElement.appendChild(paramXMLX509Certificate.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addCRL(byte[] paramArrayOfbyte) {
    add(new XMLX509CRL(this._doc, paramArrayOfbyte));
  }
  
  public void add(XMLX509CRL paramXMLX509CRL) {
    this._constructionElement.appendChild(paramXMLX509CRL.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addUnknownElement(Element paramElement) {
    this._constructionElement.appendChild(paramElement);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public int lengthIssuerSerial() {
    return length("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
  }
  
  public int lengthSKI() {
    return length("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
  }
  
  public int lengthSubjectName() {
    return length("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
  }
  
  public int lengthCertificate() {
    return length("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
  }
  
  public int lengthCRL() {
    return length("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
  }
  
  public int lengthUnknownElement() {
    byte b = 0;
    for (Node node = this._constructionElement.getFirstChild(); node != null; node = node.getNextSibling()) {
      if (node.getNodeType() == 1 && !node.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#"))
        b++; 
    } 
    return b;
  }
  
  public XMLX509IssuerSerial itemIssuerSerial(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "X509IssuerSerial", paramInt);
    return (element != null) ? new XMLX509IssuerSerial(element, this._baseURI) : null;
  }
  
  public XMLX509SKI itemSKI(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "X509SKI", paramInt);
    return (element != null) ? new XMLX509SKI(element, this._baseURI) : null;
  }
  
  public XMLX509SubjectName itemSubjectName(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "X509SubjectName", paramInt);
    return (element != null) ? new XMLX509SubjectName(element, this._baseURI) : null;
  }
  
  public XMLX509Certificate itemCertificate(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "X509Certificate", paramInt);
    return (element != null) ? new XMLX509Certificate(element, this._baseURI) : null;
  }
  
  public XMLX509CRL itemCRL(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "X509CRL", paramInt);
    return (element != null) ? new XMLX509CRL(element, this._baseURI) : null;
  }
  
  public Element itemUnknownElement(int paramInt) {
    log.log(Level.FINE, "itemUnknownElement not implemented:" + paramInt);
    return null;
  }
  
  public boolean containsIssuerSerial() {
    return (lengthIssuerSerial() > 0);
  }
  
  public boolean containsSKI() {
    return (lengthSKI() > 0);
  }
  
  public boolean containsSubjectName() {
    return (lengthSubjectName() > 0);
  }
  
  public boolean containsCertificate() {
    return (lengthCertificate() > 0);
  }
  
  public boolean containsCRL() {
    return (lengthCRL() > 0);
  }
  
  public boolean containsUnknownElement() {
    return (lengthUnknownElement() > 0);
  }
  
  public String getBaseLocalName() {
    return "X509Data";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\X509Data.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */