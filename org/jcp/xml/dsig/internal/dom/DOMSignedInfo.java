package org.jcp.xml.dsig.internal.dom;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.XMLSignatureException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMSignedInfo extends DOMStructure implements SignedInfo {
  private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
  
  private List references;
  
  private CanonicalizationMethod canonicalizationMethod;
  
  private SignatureMethod signatureMethod;
  
  private String id;
  
  private Document ownerDoc;
  
  private Element localSiElem;
  
  private InputStream canonData;
  
  static final boolean $assertionsDisabled;
  
  public DOMSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List paramList) {
    if (paramCanonicalizationMethod == null || paramSignatureMethod == null || paramList == null)
      throw new NullPointerException(); 
    this.canonicalizationMethod = paramCanonicalizationMethod;
    this.signatureMethod = paramSignatureMethod;
    this.references = Collections.unmodifiableList(new ArrayList(paramList));
    if (this.references.isEmpty())
      throw new IllegalArgumentException("list of references must contain at least one entry"); 
    byte b = 0;
    int i = this.references.size();
    while (b < i) {
      Object object = this.references.get(b);
      if (!(object instanceof javax.xml.crypto.dsig.Reference))
        throw new ClassCastException("list of references contains an illegal type"); 
      b++;
    } 
  }
  
  public DOMSignedInfo(CanonicalizationMethod paramCanonicalizationMethod, SignatureMethod paramSignatureMethod, List paramList, String paramString) {
    this(paramCanonicalizationMethod, paramSignatureMethod, paramList);
    this.id = paramString;
  }
  
  public DOMSignedInfo(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
    this.localSiElem = paramElement;
    this.ownerDoc = paramElement.getOwnerDocument();
    this.id = DOMUtils.getAttributeValue(paramElement, "Id");
    Element element1 = DOMUtils.getFirstChildElement(paramElement);
    this.canonicalizationMethod = new DOMCanonicalizationMethod(element1, paramXMLCryptoContext, paramProvider);
    Element element2 = DOMUtils.getNextSiblingElement(element1);
    this.signatureMethod = DOMSignatureMethod.unmarshal(element2);
    ArrayList arrayList = new ArrayList(5);
    for (Element element3 = DOMUtils.getNextSiblingElement(element2); element3 != null; element3 = DOMUtils.getNextSiblingElement(element3))
      arrayList.add(new DOMReference(element3, paramXMLCryptoContext, paramProvider)); 
    this.references = Collections.unmodifiableList(arrayList);
  }
  
  public CanonicalizationMethod getCanonicalizationMethod() {
    return this.canonicalizationMethod;
  }
  
  public SignatureMethod getSignatureMethod() {
    return this.signatureMethod;
  }
  
  public String getId() {
    return this.id;
  }
  
  public List getReferences() {
    return this.references;
  }
  
  public InputStream getCanonicalizedData() {
    return this.canonData;
  }
  
  public void canonicalize(XMLCryptoContext paramXMLCryptoContext, ByteArrayOutputStream paramByteArrayOutputStream) throws XMLSignatureException {
    if (paramXMLCryptoContext == null)
      throw new NullPointerException("context cannot be null"); 
    UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(paramByteArrayOutputStream);
    try {
      unsyncBufferedOutputStream.close();
    } catch (IOException iOException) {}
    DOMSubTreeData dOMSubTreeData = new DOMSubTreeData(this.localSiElem, true);
    try {
      Data data = ((DOMCanonicalizationMethod)this.canonicalizationMethod).canonicalize(dOMSubTreeData, paramXMLCryptoContext, unsyncBufferedOutputStream);
    } catch (TransformException transformException) {
      throw new XMLSignatureException(transformException);
    } 
    byte[] arrayOfByte = paramByteArrayOutputStream.toByteArray();
    if (log.isLoggable(Level.FINE)) {
      InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(arrayOfByte));
      char[] arrayOfChar = new char[arrayOfByte.length];
      try {
        inputStreamReader.read(arrayOfChar);
        log.log(Level.FINE, "Canonicalized SignedInfo:\n" + new String(arrayOfChar));
      } catch (IOException iOException) {
        log.log(Level.FINE, "IOException reading SignedInfo bytes");
      } 
      log.log(Level.FINE, "Data to be signed/verified:" + Base64.encode(arrayOfByte));
    } 
    this.canonData = new ByteArrayInputStream(arrayOfByte);
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    this.ownerDoc = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(this.ownerDoc, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#", paramString);
    DOMCanonicalizationMethod dOMCanonicalizationMethod = (DOMCanonicalizationMethod)this.canonicalizationMethod;
    dOMCanonicalizationMethod.marshal(element, paramString, paramDOMCryptoContext);
    ((DOMSignatureMethod)this.signatureMethod).marshal(element, paramString, paramDOMCryptoContext);
    byte b = 0;
    int i = this.references.size();
    while (b < i) {
      DOMReference dOMReference = this.references.get(b);
      dOMReference.marshal(element, paramString, paramDOMCryptoContext);
      b++;
    } 
    DOMUtils.setAttributeID(element, "Id", this.id);
    paramNode.appendChild(element);
    this.localSiElem = element;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof SignedInfo))
      return false; 
    SignedInfo signedInfo = (SignedInfo)paramObject;
    boolean bool = (this.id == null) ? ((signedInfo.getId() == null) ? true : false) : this.id.equals(signedInfo.getId());
    return (this.canonicalizationMethod.equals(signedInfo.getCanonicalizationMethod()) && this.signatureMethod.equals(signedInfo.getSignatureMethod()) && this.references.equals(signedInfo.getReferences()) && bool);
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 59;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMSignedInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */