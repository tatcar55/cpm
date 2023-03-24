package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.SignerOutputStream;
import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import java.io.IOException;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public final class XMLSignature extends SignatureElementProxy {
  static Logger log = Logger.getLogger(XMLSignature.class.getName());
  
  public static final String ALGO_ID_MAC_HMAC_SHA1 = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
  
  public static final String ALGO_ID_SIGNATURE_DSA = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
  
  public static final String ALGO_ID_SIGNATURE_RSA = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
  
  public static final String ALGO_ID_SIGNATURE_RSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
  
  public static final String ALGO_ID_SIGNATURE_NOT_RECOMMENDED_RSA_MD5 = "http://www.w3.org/2001/04/xmldsig-more#rsa-md5";
  
  public static final String ALGO_ID_SIGNATURE_RSA_RIPEMD160 = "http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160";
  
  public static final String ALGO_ID_SIGNATURE_RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
  
  public static final String ALGO_ID_SIGNATURE_RSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
  
  public static final String ALGO_ID_SIGNATURE_RSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
  
  public static final String ALGO_ID_MAC_HMAC_NOT_RECOMMENDED_MD5 = "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
  
  public static final String ALGO_ID_MAC_HMAC_RIPEMD160 = "http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160";
  
  public static final String ALGO_ID_MAC_HMAC_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
  
  public static final String ALGO_ID_MAC_HMAC_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
  
  public static final String ALGO_ID_MAC_HMAC_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
  
  public static final String ALGO_ID_SIGNATURE_ECDSA_SHA1 = "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
  
  private SignedInfo _signedInfo = null;
  
  private KeyInfo _keyInfo = null;
  
  private boolean _followManifestsDuringValidation = false;
  
  private Element signatureValueElement;
  
  public XMLSignature(Document paramDocument, String paramString1, String paramString2) throws XMLSecurityException {
    this(paramDocument, paramString1, paramString2, 0, "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
  }
  
  public XMLSignature(Document paramDocument, String paramString1, String paramString2, int paramInt) throws XMLSecurityException {
    this(paramDocument, paramString1, paramString2, paramInt, "http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
  }
  
  public XMLSignature(Document paramDocument, String paramString1, String paramString2, String paramString3) throws XMLSecurityException {
    this(paramDocument, paramString1, paramString2, 0, paramString3);
  }
  
  public XMLSignature(Document paramDocument, String paramString1, String paramString2, int paramInt, String paramString3) throws XMLSecurityException {
    super(paramDocument);
    String str = getDefaultPrefixBindings("http://www.w3.org/2000/09/xmldsig#");
    if (str == null) {
      this._constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
    } else {
      this._constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", str, "http://www.w3.org/2000/09/xmldsig#");
    } 
    XMLUtils.addReturnToElement(this._constructionElement);
    this._baseURI = paramString1;
    this._signedInfo = new SignedInfo(this._doc, paramString2, paramInt, paramString3);
    this._constructionElement.appendChild(this._signedInfo.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
    this.signatureValueElement = XMLUtils.createElementInSignatureSpace(this._doc, "SignatureValue");
    this._constructionElement.appendChild(this.signatureValueElement);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public XMLSignature(Document paramDocument, String paramString, Element paramElement1, Element paramElement2) throws XMLSecurityException {
    super(paramDocument);
    String str = getDefaultPrefixBindings("http://www.w3.org/2000/09/xmldsig#");
    if (str == null) {
      this._constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2000/09/xmldsig#");
    } else {
      this._constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", str, "http://www.w3.org/2000/09/xmldsig#");
    } 
    XMLUtils.addReturnToElement(this._constructionElement);
    this._baseURI = paramString;
    this._signedInfo = new SignedInfo(this._doc, paramElement1, paramElement2);
    this._constructionElement.appendChild(this._signedInfo.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
    this.signatureValueElement = XMLUtils.createElementInSignatureSpace(this._doc, "SignatureValue");
    this._constructionElement.appendChild(this.signatureValueElement);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public XMLSignature(Element paramElement, String paramString) throws XMLSignatureException, XMLSecurityException {
    super(paramElement, paramString);
    Element element1 = XMLUtils.getNextElement(paramElement.getFirstChild());
    if (element1 == null) {
      Object[] arrayOfObject = { "SignedInfo", "Signature" };
      throw new XMLSignatureException("xml.WrongContent", arrayOfObject);
    } 
    this._signedInfo = new SignedInfo(element1, paramString);
    this.signatureValueElement = XMLUtils.getNextElement(element1.getNextSibling());
    if (this.signatureValueElement == null) {
      Object[] arrayOfObject = { "SignatureValue", "Signature" };
      throw new XMLSignatureException("xml.WrongContent", arrayOfObject);
    } 
    Element element2 = XMLUtils.getNextElement(this.signatureValueElement.getNextSibling());
    if (element2 != null && element2.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && element2.getLocalName().equals("KeyInfo"))
      this._keyInfo = new KeyInfo(element2, paramString); 
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
  
  public SignedInfo getSignedInfo() {
    return this._signedInfo;
  }
  
  public byte[] getSignatureValue() throws XMLSignatureException {
    try {
      return Base64.decode(this.signatureValueElement);
    } catch (Base64DecodingException base64DecodingException) {
      throw new XMLSignatureException("empty", base64DecodingException);
    } 
  }
  
  private void setSignatureValueElement(byte[] paramArrayOfbyte) {
    while (this.signatureValueElement.hasChildNodes())
      this.signatureValueElement.removeChild(this.signatureValueElement.getFirstChild()); 
    String str = Base64.encode(paramArrayOfbyte);
    if (str.length() > 76 && !XMLUtils.ignoreLineBreaks())
      str = "\n" + str + "\n"; 
    Text text = this._doc.createTextNode(str);
    this.signatureValueElement.appendChild(text);
  }
  
  public KeyInfo getKeyInfo() {
    if (this._keyInfo == null) {
      this._keyInfo = new KeyInfo(this._doc);
      Element element1 = this._keyInfo.getElement();
      Element element2 = null;
      Node node = this._constructionElement.getFirstChild();
      element2 = XMLUtils.selectDsNode(node, "Object", 0);
      if (element2 != null) {
        this._constructionElement.insertBefore(element1, element2);
        XMLUtils.addReturnBeforeChild(this._constructionElement, element2);
      } else {
        this._constructionElement.appendChild(element1);
        XMLUtils.addReturnToElement(this._constructionElement);
      } 
    } 
    return this._keyInfo;
  }
  
  public void appendObject(ObjectContainer paramObjectContainer) throws XMLSignatureException {
    this._constructionElement.appendChild(paramObjectContainer.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public ObjectContainer getObjectItem(int paramInt) {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "Object", paramInt);
    try {
      return new ObjectContainer(element, this._baseURI);
    } catch (XMLSecurityException xMLSecurityException) {
      return null;
    } 
  }
  
  public int getObjectLength() {
    return length("http://www.w3.org/2000/09/xmldsig#", "Object");
  }
  
  public void sign(Key paramKey) throws XMLSignatureException {
    if (paramKey instanceof PublicKey)
      throw new IllegalArgumentException(I18n.translate("algorithms.operationOnlyVerification")); 
    try {
      SignedInfo signedInfo = getSignedInfo();
      SignatureAlgorithm signatureAlgorithm = signedInfo.getSignatureAlgorithm();
      signatureAlgorithm.initSign(paramKey);
      signedInfo.generateDigestValues();
      UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(new SignerOutputStream(signatureAlgorithm));
      try {
        unsyncBufferedOutputStream.close();
      } catch (IOException iOException) {}
      signedInfo.signInOctectStream(unsyncBufferedOutputStream);
      byte[] arrayOfByte = signatureAlgorithm.sign();
      setSignatureValueElement(arrayOfByte);
    } catch (XMLSignatureException xMLSignatureException) {
      throw xMLSignatureException;
    } catch (CanonicalizationException canonicalizationException) {
      throw new XMLSignatureException("empty", canonicalizationException);
    } catch (InvalidCanonicalizerException invalidCanonicalizerException) {
      throw new XMLSignatureException("empty", invalidCanonicalizerException);
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public void addResourceResolver(ResourceResolver paramResourceResolver) {
    getSignedInfo().addResourceResolver(paramResourceResolver);
  }
  
  public void addResourceResolver(ResourceResolverSpi paramResourceResolverSpi) {
    getSignedInfo().addResourceResolver(paramResourceResolverSpi);
  }
  
  public boolean checkSignatureValue(X509Certificate paramX509Certificate) throws XMLSignatureException {
    if (paramX509Certificate != null)
      return checkSignatureValue(paramX509Certificate.getPublicKey()); 
    Object[] arrayOfObject = { "Didn't get a certificate" };
    throw new XMLSignatureException("empty", arrayOfObject);
  }
  
  public boolean checkSignatureValue(Key paramKey) throws XMLSignatureException {
    if (paramKey == null) {
      Object[] arrayOfObject = { "Didn't get a key" };
      throw new XMLSignatureException("empty", arrayOfObject);
    } 
    try {
      SignedInfo signedInfo = getSignedInfo();
      SignatureAlgorithm signatureAlgorithm = signedInfo.getSignatureAlgorithm();
      log.log(Level.FINE, "SignatureMethodURI = " + signatureAlgorithm.getAlgorithmURI());
      log.log(Level.FINE, "jceSigAlgorithm    = " + signatureAlgorithm.getJCEAlgorithmString());
      log.log(Level.FINE, "jceSigProvider     = " + signatureAlgorithm.getJCEProviderName());
      log.log(Level.FINE, "PublicKey = " + paramKey);
      signatureAlgorithm.initVerify(paramKey);
      SignerOutputStream signerOutputStream = new SignerOutputStream(signatureAlgorithm);
      UnsyncBufferedOutputStream unsyncBufferedOutputStream = new UnsyncBufferedOutputStream(signerOutputStream);
      signedInfo.signInOctectStream(unsyncBufferedOutputStream);
      try {
        unsyncBufferedOutputStream.close();
      } catch (IOException iOException) {}
      byte[] arrayOfByte = getSignatureValue();
      if (!signatureAlgorithm.verify(arrayOfByte)) {
        log.log(Level.WARNING, "Signature verification failed.");
        return false;
      } 
      return signedInfo.verify(this._followManifestsDuringValidation);
    } catch (XMLSignatureException xMLSignatureException) {
      throw xMLSignatureException;
    } catch (XMLSecurityException xMLSecurityException) {
      throw new XMLSignatureException("empty", xMLSecurityException);
    } 
  }
  
  public void addDocument(String paramString1, Transforms paramTransforms, String paramString2, String paramString3, String paramString4) throws XMLSignatureException {
    this._signedInfo.addDocument(this._baseURI, paramString1, paramTransforms, paramString2, paramString3, paramString4);
  }
  
  public void addDocument(String paramString1, Transforms paramTransforms, String paramString2) throws XMLSignatureException {
    this._signedInfo.addDocument(this._baseURI, paramString1, paramTransforms, paramString2, null, null);
  }
  
  public void addDocument(String paramString, Transforms paramTransforms) throws XMLSignatureException {
    this._signedInfo.addDocument(this._baseURI, paramString, paramTransforms, "http://www.w3.org/2000/09/xmldsig#sha1", null, null);
  }
  
  public void addDocument(String paramString) throws XMLSignatureException {
    this._signedInfo.addDocument(this._baseURI, paramString, null, "http://www.w3.org/2000/09/xmldsig#sha1", null, null);
  }
  
  public void addKeyInfo(X509Certificate paramX509Certificate) throws XMLSecurityException {
    X509Data x509Data = new X509Data(this._doc);
    x509Data.addCertificate(paramX509Certificate);
    getKeyInfo().add(x509Data);
  }
  
  public void addKeyInfo(PublicKey paramPublicKey) {
    getKeyInfo().add(paramPublicKey);
  }
  
  public SecretKey createSecretKey(byte[] paramArrayOfbyte) {
    return getSignedInfo().createSecretKey(paramArrayOfbyte);
  }
  
  public void setFollowNestedManifests(boolean paramBoolean) {
    this._followManifestsDuringValidation = paramBoolean;
  }
  
  public String getBaseLocalName() {
    return "Signature";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\XMLSignature.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */