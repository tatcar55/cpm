package com.sun.org.apache.xml.internal.security.algorithms;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
import com.sun.org.apache.xml.internal.security.exceptions.AlgorithmAlreadyRegisteredException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureAlgorithm extends Algorithm {
  static Logger log = Logger.getLogger(SignatureAlgorithm.class.getName());
  
  static boolean _alreadyInitialized = false;
  
  static HashMap _algorithmHash = null;
  
  static ThreadLocal instancesSigning = new ThreadLocal() {
      protected Object initialValue() {
        return new HashMap();
      }
    };
  
  static ThreadLocal instancesVerify = new ThreadLocal() {
      protected Object initialValue() {
        return new HashMap();
      }
    };
  
  static ThreadLocal keysSigning = new ThreadLocal() {
      protected Object initialValue() {
        return new HashMap();
      }
    };
  
  static ThreadLocal keysVerify = new ThreadLocal() {
      protected Object initialValue() {
        return new HashMap();
      }
    };
  
  protected SignatureAlgorithmSpi _signatureAlgorithm = null;
  
  private String algorithmURI;
  
  public SignatureAlgorithm(Document paramDocument, String paramString) throws XMLSecurityException {
    super(paramDocument, paramString);
    this.algorithmURI = paramString;
  }
  
  private void initializeAlgorithm(boolean paramBoolean) throws XMLSignatureException {
    if (this._signatureAlgorithm != null)
      return; 
    this._signatureAlgorithm = paramBoolean ? getInstanceForSigning(this.algorithmURI) : getInstanceForVerify(this.algorithmURI);
    this._signatureAlgorithm.engineGetContextFromElement(this._constructionElement);
  }
  
  private static SignatureAlgorithmSpi getInstanceForSigning(String paramString) throws XMLSignatureException {
    SignatureAlgorithmSpi signatureAlgorithmSpi = (SignatureAlgorithmSpi)((Map)instancesSigning.get()).get(paramString);
    if (signatureAlgorithmSpi != null) {
      signatureAlgorithmSpi.reset();
      return signatureAlgorithmSpi;
    } 
    signatureAlgorithmSpi = buildSigner(paramString, signatureAlgorithmSpi);
    ((Map)instancesSigning.get()).put(paramString, signatureAlgorithmSpi);
    return signatureAlgorithmSpi;
  }
  
  private static SignatureAlgorithmSpi getInstanceForVerify(String paramString) throws XMLSignatureException {
    SignatureAlgorithmSpi signatureAlgorithmSpi = (SignatureAlgorithmSpi)((Map)instancesVerify.get()).get(paramString);
    if (signatureAlgorithmSpi != null) {
      signatureAlgorithmSpi.reset();
      return signatureAlgorithmSpi;
    } 
    signatureAlgorithmSpi = buildSigner(paramString, signatureAlgorithmSpi);
    ((Map)instancesVerify.get()).put(paramString, signatureAlgorithmSpi);
    return signatureAlgorithmSpi;
  }
  
  private static SignatureAlgorithmSpi buildSigner(String paramString, SignatureAlgorithmSpi paramSignatureAlgorithmSpi) throws XMLSignatureException {
    try {
      Class clazz = getImplementingClass(paramString);
      log.log(Level.FINE, "Create URI \"" + paramString + "\" class \"" + clazz + "\"");
      return clazz.newInstance();
    } catch (IllegalAccessException illegalAccessException) {
      Object[] arrayOfObject = { paramString, illegalAccessException.getMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, illegalAccessException);
    } catch (InstantiationException instantiationException) {
      Object[] arrayOfObject = { paramString, instantiationException.getMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, instantiationException);
    } catch (NullPointerException nullPointerException) {
      Object[] arrayOfObject = { paramString, nullPointerException.getMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, nullPointerException);
    } 
  }
  
  public SignatureAlgorithm(Document paramDocument, String paramString, int paramInt) throws XMLSecurityException {
    this(paramDocument, paramString);
    this.algorithmURI = paramString;
    initializeAlgorithm(true);
    this._signatureAlgorithm.engineSetHMACOutputLength(paramInt);
    ((IntegrityHmac)this._signatureAlgorithm).engineAddContextToElement(this._constructionElement);
  }
  
  public SignatureAlgorithm(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
    this.algorithmURI = getURI();
  }
  
  public byte[] sign() throws XMLSignatureException {
    return this._signatureAlgorithm.engineSign();
  }
  
  public String getJCEAlgorithmString() {
    try {
      return getInstanceForVerify(this.algorithmURI).engineGetJCEAlgorithmString();
    } catch (XMLSignatureException xMLSignatureException) {
      return null;
    } 
  }
  
  public String getJCEProviderName() {
    try {
      return getInstanceForVerify(this.algorithmURI).engineGetJCEProviderName();
    } catch (XMLSignatureException xMLSignatureException) {
      return null;
    } 
  }
  
  public void update(byte[] paramArrayOfbyte) throws XMLSignatureException {
    this._signatureAlgorithm.engineUpdate(paramArrayOfbyte);
  }
  
  public void update(byte paramByte) throws XMLSignatureException {
    this._signatureAlgorithm.engineUpdate(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
    this._signatureAlgorithm.engineUpdate(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public void initSign(Key paramKey) throws XMLSignatureException {
    initializeAlgorithm(true);
    Map map = keysSigning.get();
    if (map.get(this.algorithmURI) == paramKey)
      return; 
    map.put(this.algorithmURI, paramKey);
    this._signatureAlgorithm.engineInitSign(paramKey);
  }
  
  public void initSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
    initializeAlgorithm(true);
    this._signatureAlgorithm.engineInitSign(paramKey, paramSecureRandom);
  }
  
  public void initSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
    initializeAlgorithm(true);
    this._signatureAlgorithm.engineInitSign(paramKey, paramAlgorithmParameterSpec);
  }
  
  public void setParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
    this._signatureAlgorithm.engineSetParameter(paramAlgorithmParameterSpec);
  }
  
  public void initVerify(Key paramKey) throws XMLSignatureException {
    initializeAlgorithm(false);
    Map map = keysVerify.get();
    if (map.get(this.algorithmURI) == paramKey)
      return; 
    map.put(this.algorithmURI, paramKey);
    this._signatureAlgorithm.engineInitVerify(paramKey);
  }
  
  public boolean verify(byte[] paramArrayOfbyte) throws XMLSignatureException {
    return this._signatureAlgorithm.engineVerify(paramArrayOfbyte);
  }
  
  public final String getURI() {
    return this._constructionElement.getAttributeNS(null, "Algorithm");
  }
  
  public static void providerInit() {
    if (log == null)
      log = Logger.getLogger(SignatureAlgorithm.class.getName()); 
    log.log(Level.FINE, "Init() called");
    if (!_alreadyInitialized) {
      _algorithmHash = new HashMap(10);
      _alreadyInitialized = true;
    } 
  }
  
  public static void register(String paramString1, String paramString2) throws AlgorithmAlreadyRegisteredException, XMLSignatureException {
    log.log(Level.FINE, "Try to register " + paramString1 + " " + paramString2);
    Class clazz = getImplementingClass(paramString1);
    if (clazz != null) {
      String str = clazz.getName();
      if (str != null && str.length() != 0) {
        Object[] arrayOfObject = { paramString1, str };
        throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", arrayOfObject);
      } 
    } 
    try {
      _algorithmHash.put(paramString1, Class.forName(paramString2));
    } catch (ClassNotFoundException classNotFoundException) {
      Object[] arrayOfObject = { paramString1, classNotFoundException.getMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, classNotFoundException);
    } catch (NullPointerException nullPointerException) {
      Object[] arrayOfObject = { paramString1, nullPointerException.getMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject, nullPointerException);
    } 
  }
  
  private static Class getImplementingClass(String paramString) {
    return (_algorithmHash == null) ? null : (Class)_algorithmHash.get(paramString);
  }
  
  public String getBaseNamespace() {
    return "http://www.w3.org/2000/09/xmldsig#";
  }
  
  public String getBaseLocalName() {
    return "SignatureMethod";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\algorithms\SignatureAlgorithm.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */