package com.sun.org.apache.xml.internal.security.keys;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.KeyName;
import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
import com.sun.org.apache.xml.internal.security.keys.content.PGPData;
import com.sun.org.apache.xml.internal.security.keys.content.RetrievalMethod;
import com.sun.org.apache.xml.internal.security.keys.content.SPKIData;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.IdResolver;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KeyInfo extends SignatureElementProxy {
  static Logger log = Logger.getLogger(KeyInfo.class.getName());
  
  List x509Datas = null;
  
  List encryptedKeys = null;
  
  static final List nullList;
  
  List _internalKeyResolvers = null;
  
  List _storageResolvers = nullList;
  
  static boolean _alreadyInitialized = false;
  
  public KeyInfo(Document paramDocument) {
    super(paramDocument);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public KeyInfo(Element paramElement, String paramString) throws XMLSecurityException {
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
  
  public void addKeyName(String paramString) {
    add(new KeyName(this._doc, paramString));
  }
  
  public void add(KeyName paramKeyName) {
    this._constructionElement.appendChild(paramKeyName.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addKeyValue(PublicKey paramPublicKey) {
    add(new KeyValue(this._doc, paramPublicKey));
  }
  
  public void addKeyValue(Element paramElement) {
    add(new KeyValue(this._doc, paramElement));
  }
  
  public void add(DSAKeyValue paramDSAKeyValue) {
    add(new KeyValue(this._doc, paramDSAKeyValue));
  }
  
  public void add(RSAKeyValue paramRSAKeyValue) {
    add(new KeyValue(this._doc, paramRSAKeyValue));
  }
  
  public void add(PublicKey paramPublicKey) {
    add(new KeyValue(this._doc, paramPublicKey));
  }
  
  public void add(KeyValue paramKeyValue) {
    this._constructionElement.appendChild(paramKeyValue.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addMgmtData(String paramString) {
    add(new MgmtData(this._doc, paramString));
  }
  
  public void add(MgmtData paramMgmtData) {
    this._constructionElement.appendChild(paramMgmtData.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void add(PGPData paramPGPData) {
    this._constructionElement.appendChild(paramPGPData.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addRetrievalMethod(String paramString1, Transforms paramTransforms, String paramString2) {
    add(new RetrievalMethod(this._doc, paramString1, paramTransforms, paramString2));
  }
  
  public void add(RetrievalMethod paramRetrievalMethod) {
    this._constructionElement.appendChild(paramRetrievalMethod.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void add(SPKIData paramSPKIData) {
    this._constructionElement.appendChild(paramSPKIData.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void add(X509Data paramX509Data) {
    if (this.x509Datas == null)
      this.x509Datas = new ArrayList(); 
    this.x509Datas.add(paramX509Data);
    this._constructionElement.appendChild(paramX509Data.getElement());
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void add(EncryptedKey paramEncryptedKey) throws XMLEncryptionException {
    if (this.encryptedKeys == null)
      this.encryptedKeys = new ArrayList(); 
    this.encryptedKeys.add(paramEncryptedKey);
    XMLCipher xMLCipher = XMLCipher.getInstance();
    this._constructionElement.appendChild(xMLCipher.martial(paramEncryptedKey));
  }
  
  public void addUnknownElement(Element paramElement) {
    this._constructionElement.appendChild(paramElement);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public int lengthKeyName() {
    return length("http://www.w3.org/2000/09/xmldsig#", "KeyName");
  }
  
  public int lengthKeyValue() {
    return length("http://www.w3.org/2000/09/xmldsig#", "KeyValue");
  }
  
  public int lengthMgmtData() {
    return length("http://www.w3.org/2000/09/xmldsig#", "MgmtData");
  }
  
  public int lengthPGPData() {
    return length("http://www.w3.org/2000/09/xmldsig#", "PGPData");
  }
  
  public int lengthRetrievalMethod() {
    return length("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod");
  }
  
  public int lengthSPKIData() {
    return length("http://www.w3.org/2000/09/xmldsig#", "SPKIData");
  }
  
  public int lengthX509Data() {
    return (this.x509Datas != null) ? this.x509Datas.size() : length("http://www.w3.org/2000/09/xmldsig#", "X509Data");
  }
  
  public int lengthUnknownElement() {
    byte b1 = 0;
    NodeList nodeList = this._constructionElement.getChildNodes();
    for (byte b2 = 0; b2 < nodeList.getLength(); b2++) {
      Node node = nodeList.item(b2);
      if (node.getNodeType() == 1 && node.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#"))
        b1++; 
    } 
    return b1;
  }
  
  public KeyName itemKeyName(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "KeyName", paramInt);
    return (element != null) ? new KeyName(element, this._baseURI) : null;
  }
  
  public KeyValue itemKeyValue(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "KeyValue", paramInt);
    return (element != null) ? new KeyValue(element, this._baseURI) : null;
  }
  
  public MgmtData itemMgmtData(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "MgmtData", paramInt);
    return (element != null) ? new MgmtData(element, this._baseURI) : null;
  }
  
  public PGPData itemPGPData(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "PGPData", paramInt);
    return (element != null) ? new PGPData(element, this._baseURI) : null;
  }
  
  public RetrievalMethod itemRetrievalMethod(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "RetrievalMethod", paramInt);
    return (element != null) ? new RetrievalMethod(element, this._baseURI) : null;
  }
  
  public SPKIData itemSPKIData(int paramInt) throws XMLSecurityException {
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "SPKIData", paramInt);
    return (element != null) ? new SPKIData(element, this._baseURI) : null;
  }
  
  public X509Data itemX509Data(int paramInt) throws XMLSecurityException {
    if (this.x509Datas != null)
      return this.x509Datas.get(paramInt); 
    Element element = XMLUtils.selectDsNode(this._constructionElement.getFirstChild(), "X509Data", paramInt);
    return (element != null) ? new X509Data(element, this._baseURI) : null;
  }
  
  public EncryptedKey itemEncryptedKey(int paramInt) throws XMLSecurityException {
    if (this.encryptedKeys != null)
      return this.encryptedKeys.get(paramInt); 
    Element element = XMLUtils.selectXencNode(this._constructionElement.getFirstChild(), "EncryptedKey", paramInt);
    if (element != null) {
      XMLCipher xMLCipher = XMLCipher.getInstance();
      xMLCipher.init(4, null);
      return xMLCipher.loadEncryptedKey(element);
    } 
    return null;
  }
  
  public Element itemUnknownElement(int paramInt) {
    NodeList nodeList = this._constructionElement.getChildNodes();
    byte b1 = 0;
    for (byte b2 = 0; b2 < nodeList.getLength(); b2++) {
      Node node = nodeList.item(b2);
      if (node.getNodeType() == 1 && node.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && ++b1 == paramInt)
        return (Element)node; 
    } 
    return null;
  }
  
  public boolean isEmpty() {
    return (this._constructionElement.getFirstChild() == null);
  }
  
  public boolean containsKeyName() {
    return (lengthKeyName() > 0);
  }
  
  public boolean containsKeyValue() {
    return (lengthKeyValue() > 0);
  }
  
  public boolean containsMgmtData() {
    return (lengthMgmtData() > 0);
  }
  
  public boolean containsPGPData() {
    return (lengthPGPData() > 0);
  }
  
  public boolean containsRetrievalMethod() {
    return (lengthRetrievalMethod() > 0);
  }
  
  public boolean containsSPKIData() {
    return (lengthSPKIData() > 0);
  }
  
  public boolean containsUnknownElement() {
    return (lengthUnknownElement() > 0);
  }
  
  public boolean containsX509Data() {
    return (lengthX509Data() > 0);
  }
  
  public PublicKey getPublicKey() throws KeyResolverException {
    PublicKey publicKey = getPublicKeyFromInternalResolvers();
    if (publicKey != null) {
      log.log(Level.FINE, "I could find a key using the per-KeyInfo key resolvers");
      return publicKey;
    } 
    log.log(Level.FINE, "I couldn't find a key using the per-KeyInfo key resolvers");
    publicKey = getPublicKeyFromStaticResolvers();
    if (publicKey != null) {
      log.log(Level.FINE, "I could find a key using the system-wide key resolvers");
      return publicKey;
    } 
    log.log(Level.FINE, "I couldn't find a key using the system-wide key resolvers");
    return null;
  }
  
  PublicKey getPublicKeyFromStaticResolvers() throws KeyResolverException {
    int i = KeyResolver.length();
    int j = this._storageResolvers.size();
    Iterator iterator = KeyResolver.iterator();
    for (byte b = 0; b < i; b++) {
      KeyResolverSpi keyResolverSpi = iterator.next();
      Node node = this._constructionElement.getFirstChild();
      String str = getBaseURI();
      while (node != null) {
        if (node.getNodeType() == 1)
          for (byte b1 = 0; b1 < j; b1++) {
            StorageResolver storageResolver = this._storageResolvers.get(b1);
            PublicKey publicKey = keyResolverSpi.engineLookupAndResolvePublicKey((Element)node, str, storageResolver);
            if (publicKey != null) {
              KeyResolver.hit(iterator);
              return publicKey;
            } 
          }  
        node = node.getNextSibling();
      } 
    } 
    return null;
  }
  
  PublicKey getPublicKeyFromInternalResolvers() throws KeyResolverException {
    int i = lengthInternalKeyResolver();
    int j = this._storageResolvers.size();
    for (byte b = 0; b < i; b++) {
      KeyResolverSpi keyResolverSpi = itemInternalKeyResolver(b);
      log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
      Node node = this._constructionElement.getFirstChild();
      String str = getBaseURI();
      while (node != null) {
        if (node.getNodeType() == 1)
          for (byte b1 = 0; b1 < j; b1++) {
            StorageResolver storageResolver = this._storageResolvers.get(b1);
            PublicKey publicKey = keyResolverSpi.engineLookupAndResolvePublicKey((Element)node, str, storageResolver);
            if (publicKey != null)
              return publicKey; 
          }  
        node = node.getNextSibling();
      } 
    } 
    return null;
  }
  
  public X509Certificate getX509Certificate() throws KeyResolverException {
    X509Certificate x509Certificate = getX509CertificateFromInternalResolvers();
    if (x509Certificate != null) {
      log.log(Level.FINE, "I could find a X509Certificate using the per-KeyInfo key resolvers");
      return x509Certificate;
    } 
    log.log(Level.FINE, "I couldn't find a X509Certificate using the per-KeyInfo key resolvers");
    x509Certificate = getX509CertificateFromStaticResolvers();
    if (x509Certificate != null) {
      log.log(Level.FINE, "I could find a X509Certificate using the system-wide key resolvers");
      return x509Certificate;
    } 
    log.log(Level.FINE, "I couldn't find a X509Certificate using the system-wide key resolvers");
    return null;
  }
  
  X509Certificate getX509CertificateFromStaticResolvers() throws KeyResolverException {
    log.log(Level.FINE, "Start getX509CertificateFromStaticResolvers() with " + KeyResolver.length() + " resolvers");
    String str = getBaseURI();
    int i = KeyResolver.length();
    int j = this._storageResolvers.size();
    Iterator iterator = KeyResolver.iterator();
    for (byte b = 0; b < i; b++) {
      KeyResolverSpi keyResolverSpi = iterator.next();
      X509Certificate x509Certificate = applyCurrentResolver(str, j, keyResolverSpi);
      if (x509Certificate != null) {
        KeyResolver.hit(iterator);
        return x509Certificate;
      } 
    } 
    return null;
  }
  
  private X509Certificate applyCurrentResolver(String paramString, int paramInt, KeyResolverSpi paramKeyResolverSpi) throws KeyResolverException {
    for (Node node = this._constructionElement.getFirstChild(); node != null; node = node.getNextSibling()) {
      if (node.getNodeType() == 1)
        for (byte b = 0; b < paramInt; b++) {
          StorageResolver storageResolver = this._storageResolvers.get(b);
          X509Certificate x509Certificate = paramKeyResolverSpi.engineLookupResolveX509Certificate((Element)node, paramString, storageResolver);
          if (x509Certificate != null)
            return x509Certificate; 
        }  
    } 
    return null;
  }
  
  X509Certificate getX509CertificateFromInternalResolvers() throws KeyResolverException {
    log.log(Level.FINE, "Start getX509CertificateFromInternalResolvers() with " + lengthInternalKeyResolver() + " resolvers");
    String str = getBaseURI();
    int i = this._storageResolvers.size();
    for (byte b = 0; b < lengthInternalKeyResolver(); b++) {
      KeyResolverSpi keyResolverSpi = itemInternalKeyResolver(b);
      log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
      X509Certificate x509Certificate = applyCurrentResolver(str, i, keyResolverSpi);
      if (x509Certificate != null)
        return x509Certificate; 
    } 
    return null;
  }
  
  public SecretKey getSecretKey() throws KeyResolverException {
    SecretKey secretKey = getSecretKeyFromInternalResolvers();
    if (secretKey != null) {
      log.log(Level.FINE, "I could find a secret key using the per-KeyInfo key resolvers");
      return secretKey;
    } 
    log.log(Level.FINE, "I couldn't find a secret key using the per-KeyInfo key resolvers");
    secretKey = getSecretKeyFromStaticResolvers();
    if (secretKey != null) {
      log.log(Level.FINE, "I could find a secret key using the system-wide key resolvers");
      return secretKey;
    } 
    log.log(Level.FINE, "I couldn't find a secret key using the system-wide key resolvers");
    return null;
  }
  
  SecretKey getSecretKeyFromStaticResolvers() throws KeyResolverException {
    int i = KeyResolver.length();
    int j = this._storageResolvers.size();
    Iterator iterator = KeyResolver.iterator();
    for (byte b = 0; b < i; b++) {
      KeyResolverSpi keyResolverSpi = iterator.next();
      Node node = this._constructionElement.getFirstChild();
      String str = getBaseURI();
      while (node != null) {
        if (node.getNodeType() == 1)
          for (byte b1 = 0; b1 < j; b1++) {
            StorageResolver storageResolver = this._storageResolvers.get(b1);
            SecretKey secretKey = keyResolverSpi.engineLookupAndResolveSecretKey((Element)node, str, storageResolver);
            if (secretKey != null)
              return secretKey; 
          }  
        node = node.getNextSibling();
      } 
    } 
    return null;
  }
  
  SecretKey getSecretKeyFromInternalResolvers() throws KeyResolverException {
    int i = this._storageResolvers.size();
    for (byte b = 0; b < lengthInternalKeyResolver(); b++) {
      KeyResolverSpi keyResolverSpi = itemInternalKeyResolver(b);
      log.log(Level.FINE, "Try " + keyResolverSpi.getClass().getName());
      Node node = this._constructionElement.getFirstChild();
      String str = getBaseURI();
      while (node != null) {
        if (node.getNodeType() == 1)
          for (byte b1 = 0; b1 < i; b1++) {
            StorageResolver storageResolver = this._storageResolvers.get(b1);
            SecretKey secretKey = keyResolverSpi.engineLookupAndResolveSecretKey((Element)node, str, storageResolver);
            if (secretKey != null)
              return secretKey; 
          }  
        node = node.getNextSibling();
      } 
    } 
    return null;
  }
  
  public void registerInternalKeyResolver(KeyResolverSpi paramKeyResolverSpi) {
    if (this._internalKeyResolvers == null)
      this._internalKeyResolvers = new ArrayList(); 
    this._internalKeyResolvers.add(paramKeyResolverSpi);
  }
  
  int lengthInternalKeyResolver() {
    return (this._internalKeyResolvers == null) ? 0 : this._internalKeyResolvers.size();
  }
  
  KeyResolverSpi itemInternalKeyResolver(int paramInt) {
    return this._internalKeyResolvers.get(paramInt);
  }
  
  public void addStorageResolver(StorageResolver paramStorageResolver) {
    if (this._storageResolvers == nullList)
      this._storageResolvers = new ArrayList(); 
    this._storageResolvers.add(paramStorageResolver);
  }
  
  public static void init() {
    if (!_alreadyInitialized) {
      if (log == null) {
        log = Logger.getLogger(KeyInfo.class.getName());
        log.log(Level.SEVERE, "Had to assign log in the init() function");
      } 
      _alreadyInitialized = true;
    } 
  }
  
  public String getBaseLocalName() {
    return "KeyInfo";
  }
  
  static {
    ArrayList arrayList = new ArrayList();
    arrayList.add(null);
    nullList = Collections.unmodifiableList(arrayList);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\KeyInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */