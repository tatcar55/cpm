package org.jcp.xml.dsig.internal.dom;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.ByteArrayInputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DOMX509Data extends DOMStructure implements X509Data {
  private final List content;
  
  private CertificateFactory cf;
  
  static final boolean $assertionsDisabled;
  
  public DOMX509Data(List paramList) {
    if (paramList == null)
      throw new NullPointerException("content cannot be null"); 
    ArrayList arrayList = new ArrayList(paramList);
    if (arrayList.isEmpty())
      throw new IllegalArgumentException("content cannot be empty"); 
    byte b = 0;
    int i = arrayList.size();
    while (b < i) {
      String str = (String)arrayList.get(b);
      if (str instanceof String) {
        new X500Principal(str);
      } else if (!(str instanceof byte[]) && !(str instanceof X509Certificate) && !(str instanceof X509CRL) && !(str instanceof javax.xml.crypto.XMLStructure)) {
        throw new ClassCastException("content[" + b + "] is not a valid X509Data type");
      } 
      b++;
    } 
    this.content = Collections.unmodifiableList((List)arrayList);
  }
  
  public DOMX509Data(Element paramElement) throws MarshalException {
    NodeList nodeList = paramElement.getChildNodes();
    int i = nodeList.getLength();
    ArrayList arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++) {
      Node node = nodeList.item(b);
      if (node.getNodeType() == 1) {
        Element element = (Element)node;
        String str = element.getLocalName();
        if (str.equals("X509Certificate")) {
          arrayList.add(unmarshalX509Certificate(element));
        } else if (str.equals("X509IssuerSerial")) {
          arrayList.add(new DOMX509IssuerSerial(element));
        } else if (str.equals("X509SubjectName")) {
          arrayList.add(element.getFirstChild().getNodeValue());
        } else if (str.equals("X509SKI")) {
          try {
            arrayList.add(Base64.decode(element));
          } catch (Base64DecodingException base64DecodingException) {
            throw new MarshalException("cannot decode X509SKI", base64DecodingException);
          } 
        } else if (str.equals("X509CRL")) {
          arrayList.add(unmarshalX509CRL(element));
        } else {
          arrayList.add(new DOMStructure(element));
        } 
      } 
    } 
    this.content = Collections.unmodifiableList(arrayList);
  }
  
  public List getContent() {
    return this.content;
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(document, "X509Data", "http://www.w3.org/2000/09/xmldsig#", paramString);
    byte b = 0;
    int i = this.content.size();
    while (b < i) {
      X509Certificate x509Certificate = (X509Certificate)this.content.get(b);
      if (x509Certificate instanceof X509Certificate) {
        marshalCert(x509Certificate, element, document, paramString);
      } else if (x509Certificate instanceof javax.xml.crypto.XMLStructure) {
        if (x509Certificate instanceof javax.xml.crypto.dsig.keyinfo.X509IssuerSerial) {
          ((DOMX509IssuerSerial)x509Certificate).marshal(element, paramString, paramDOMCryptoContext);
        } else {
          DOMStructure dOMStructure = (DOMStructure)x509Certificate;
          DOMUtils.appendChild(element, dOMStructure.getNode());
        } 
      } else if (x509Certificate instanceof byte[]) {
        marshalSKI((byte[])x509Certificate, element, document, paramString);
      } else if (x509Certificate instanceof String) {
        marshalSubjectName((String)x509Certificate, element, document, paramString);
      } else if (x509Certificate instanceof X509CRL) {
        marshalCRL((X509CRL)x509Certificate, element, document, paramString);
      } 
      b++;
    } 
    paramNode.appendChild(element);
  }
  
  private void marshalSKI(byte[] paramArrayOfbyte, Node paramNode, Document paramDocument, String paramString) {
    Element element = DOMUtils.createElement(paramDocument, "X509SKI", "http://www.w3.org/2000/09/xmldsig#", paramString);
    element.appendChild(paramDocument.createTextNode(Base64.encode(paramArrayOfbyte)));
    paramNode.appendChild(element);
  }
  
  private void marshalSubjectName(String paramString1, Node paramNode, Document paramDocument, String paramString2) {
    Element element = DOMUtils.createElement(paramDocument, "X509SubjectName", "http://www.w3.org/2000/09/xmldsig#", paramString2);
    element.appendChild(paramDocument.createTextNode(paramString1));
    paramNode.appendChild(element);
  }
  
  private void marshalCert(X509Certificate paramX509Certificate, Node paramNode, Document paramDocument, String paramString) throws MarshalException {
    Element element = DOMUtils.createElement(paramDocument, "X509Certificate", "http://www.w3.org/2000/09/xmldsig#", paramString);
    try {
      element.appendChild(paramDocument.createTextNode(Base64.encode(paramX509Certificate.getEncoded())));
    } catch (CertificateEncodingException certificateEncodingException) {
      throw new MarshalException("Error encoding X509Certificate", certificateEncodingException);
    } 
    paramNode.appendChild(element);
  }
  
  private void marshalCRL(X509CRL paramX509CRL, Node paramNode, Document paramDocument, String paramString) throws MarshalException {
    Element element = DOMUtils.createElement(paramDocument, "X509CRL", "http://www.w3.org/2000/09/xmldsig#", paramString);
    try {
      element.appendChild(paramDocument.createTextNode(Base64.encode(paramX509CRL.getEncoded())));
    } catch (CRLException cRLException) {
      throw new MarshalException("Error encoding X509CRL", cRLException);
    } 
    paramNode.appendChild(element);
  }
  
  private X509Certificate unmarshalX509Certificate(Element paramElement) throws MarshalException {
    try {
      ByteArrayInputStream byteArrayInputStream = unmarshalBase64Binary(paramElement);
      return (X509Certificate)this.cf.generateCertificate(byteArrayInputStream);
    } catch (CertificateException certificateException) {
      throw new MarshalException("Cannot create X509Certificate", certificateException);
    } 
  }
  
  private X509CRL unmarshalX509CRL(Element paramElement) throws MarshalException {
    try {
      ByteArrayInputStream byteArrayInputStream = unmarshalBase64Binary(paramElement);
      return (X509CRL)this.cf.generateCRL(byteArrayInputStream);
    } catch (CRLException cRLException) {
      throw new MarshalException("Cannot create X509CRL", cRLException);
    } 
  }
  
  private ByteArrayInputStream unmarshalBase64Binary(Element paramElement) throws MarshalException {
    try {
      if (this.cf == null)
        this.cf = CertificateFactory.getInstance("X.509"); 
      return new ByteArrayInputStream(Base64.decode(paramElement));
    } catch (CertificateException certificateException) {
      throw new MarshalException("Cannot create CertificateFactory", certificateException);
    } catch (Base64DecodingException base64DecodingException) {
      throw new MarshalException("Cannot decode Base64-encoded val", base64DecodingException);
    } 
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof X509Data))
      return false; 
    X509Data x509Data = (X509Data)paramObject;
    List list = x509Data.getContent();
    int i = this.content.size();
    if (i != list.size())
      return false; 
    for (byte b = 0; b < i; b++) {
      byte[] arrayOfByte1 = (byte[])this.content.get(b);
      byte[] arrayOfByte2 = (byte[])list.get(b);
      if (arrayOfByte1 instanceof byte[]) {
        if (!(arrayOfByte2 instanceof byte[]) || !Arrays.equals(arrayOfByte1, arrayOfByte2))
          return false; 
      } else if (!arrayOfByte1.equals(arrayOfByte2)) {
        return false;
      } 
    } 
    return true;
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 56;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMX509Data.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */