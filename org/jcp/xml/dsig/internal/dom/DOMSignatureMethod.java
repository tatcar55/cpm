package org.jcp.xml.dsig.internal.dom;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignContext;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLValidateContext;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import org.jcp.xml.dsig.internal.SignerOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class DOMSignatureMethod extends DOMStructure implements SignatureMethod {
  private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
  
  static final String RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
  
  static final String RSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
  
  static final String RSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
  
  static final String HMAC_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
  
  static final String HMAC_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
  
  static final String HMAC_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
  
  private SignatureMethodParameterSpec params;
  
  private Signature signature;
  
  static final boolean $assertionsDisabled;
  
  DOMSignatureMethod(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramAlgorithmParameterSpec != null && !(paramAlgorithmParameterSpec instanceof SignatureMethodParameterSpec))
      throw new InvalidAlgorithmParameterException("params must be of type SignatureMethodParameterSpec"); 
    checkParams((SignatureMethodParameterSpec)paramAlgorithmParameterSpec);
    this.params = (SignatureMethodParameterSpec)paramAlgorithmParameterSpec;
  }
  
  DOMSignatureMethod(Element paramElement) throws MarshalException {
    Element element = DOMUtils.getFirstChildElement(paramElement);
    if (element != null)
      this.params = unmarshalParams(element); 
    try {
      checkParams(this.params);
    } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
      throw new MarshalException(invalidAlgorithmParameterException);
    } 
  }
  
  static SignatureMethod unmarshal(Element paramElement) throws MarshalException {
    String str = DOMUtils.getAttributeValue(paramElement, "Algorithm");
    if (str.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1"))
      return new SHA1withRSA(paramElement); 
    if (str.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"))
      return new SHA256withRSA(paramElement); 
    if (str.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384"))
      return new SHA384withRSA(paramElement); 
    if (str.equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"))
      return new SHA512withRSA(paramElement); 
    if (str.equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
      return new SHA1withDSA(paramElement); 
    if (str.equals("http://www.w3.org/2000/09/xmldsig#hmac-sha1"))
      return new DOMHMACSignatureMethod.SHA1(paramElement); 
    if (str.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256"))
      return new DOMHMACSignatureMethod.SHA256(paramElement); 
    if (str.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384"))
      return new DOMHMACSignatureMethod.SHA384(paramElement); 
    if (str.equals("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512"))
      return new DOMHMACSignatureMethod.SHA512(paramElement); 
    throw new MarshalException("unsupported SignatureMethod algorithm: " + str);
  }
  
  void checkParams(SignatureMethodParameterSpec paramSignatureMethodParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramSignatureMethodParameterSpec != null)
      throw new InvalidAlgorithmParameterException("no parameters should be specified for the " + getSignatureAlgorithm() + " SignatureMethod algorithm"); 
  }
  
  public final AlgorithmParameterSpec getParameterSpec() {
    return this.params;
  }
  
  SignatureMethodParameterSpec unmarshalParams(Element paramElement) throws MarshalException {
    throw new MarshalException("no parameters should be specified for the " + getSignatureAlgorithm() + " SignatureMethod algorithm");
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(document, "SignatureMethod", "http://www.w3.org/2000/09/xmldsig#", paramString);
    DOMUtils.setAttribute(element, "Algorithm", getAlgorithm());
    if (this.params != null)
      marshalParams(element, paramString); 
    paramNode.appendChild(element);
  }
  
  boolean verify(Key paramKey, DOMSignedInfo paramDOMSignedInfo, byte[] paramArrayOfbyte, XMLValidateContext paramXMLValidateContext) throws InvalidKeyException, SignatureException, XMLSignatureException {
    if (paramKey == null || paramDOMSignedInfo == null || paramArrayOfbyte == null)
      throw new NullPointerException(); 
    if (!(paramKey instanceof PublicKey))
      throw new InvalidKeyException("key must be PublicKey"); 
    if (this.signature == null)
      try {
        Provider provider = (Provider)paramXMLValidateContext.getProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider");
        this.signature = (provider == null) ? Signature.getInstance(getSignatureAlgorithm()) : Signature.getInstance(getSignatureAlgorithm(), provider);
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLSignatureException(noSuchAlgorithmException);
      }  
    this.signature.initVerify((PublicKey)paramKey);
    if (log.isLoggable(Level.FINE)) {
      log.log(Level.FINE, "Signature provider:" + this.signature.getProvider());
      log.log(Level.FINE, "verifying with key: " + paramKey);
    } 
    paramDOMSignedInfo.canonicalize(paramXMLValidateContext, new SignerOutputStream(this.signature));
    if (getAlgorithm().equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
      try {
        return this.signature.verify(convertXMLDSIGtoASN1(paramArrayOfbyte));
      } catch (IOException iOException) {
        throw new XMLSignatureException(iOException);
      }  
    return this.signature.verify(paramArrayOfbyte);
  }
  
  byte[] sign(Key paramKey, DOMSignedInfo paramDOMSignedInfo, XMLSignContext paramXMLSignContext) throws InvalidKeyException, XMLSignatureException {
    if (paramKey == null || paramDOMSignedInfo == null)
      throw new NullPointerException(); 
    if (!(paramKey instanceof PrivateKey))
      throw new InvalidKeyException("key must be PrivateKey"); 
    if (this.signature == null)
      try {
        Provider provider = (Provider)paramXMLSignContext.getProperty("org.jcp.xml.dsig.internal.dom.SignatureProvider");
        this.signature = (provider == null) ? Signature.getInstance(getSignatureAlgorithm()) : Signature.getInstance(getSignatureAlgorithm(), provider);
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLSignatureException(noSuchAlgorithmException);
      }  
    this.signature.initSign((PrivateKey)paramKey);
    if (log.isLoggable(Level.FINE)) {
      log.log(Level.FINE, "Signature provider:" + this.signature.getProvider());
      log.log(Level.FINE, "Signing with key: " + paramKey);
    } 
    paramDOMSignedInfo.canonicalize(paramXMLSignContext, new SignerOutputStream(this.signature));
    try {
      return getAlgorithm().equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1") ? convertASN1toXMLDSIG(this.signature.sign()) : this.signature.sign();
    } catch (SignatureException signatureException) {
      throw new XMLSignatureException(signatureException);
    } catch (IOException iOException) {
      throw new XMLSignatureException(iOException);
    } 
  }
  
  void marshalParams(Element paramElement, String paramString) throws MarshalException {
    throw new MarshalException("no parameters should be specified for the " + getSignatureAlgorithm() + " SignatureMethod algorithm");
  }
  
  abstract String getSignatureAlgorithm();
  
  boolean paramsEqual(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    return (getParameterSpec() == paramAlgorithmParameterSpec);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof SignatureMethod))
      return false; 
    SignatureMethod signatureMethod = (SignatureMethod)paramObject;
    return (getAlgorithm().equals(signatureMethod.getAlgorithm()) && paramsEqual(signatureMethod.getParameterSpec()));
  }
  
  private static byte[] convertASN1toXMLDSIG(byte[] paramArrayOfbyte) throws IOException {
    byte b1 = paramArrayOfbyte[3];
    byte b2;
    for (b2 = b1; b2 > 0 && paramArrayOfbyte[4 + b1 - b2] == 0; b2--);
    byte b3 = paramArrayOfbyte[5 + b1];
    byte b4;
    for (b4 = b3; b4 > 0 && paramArrayOfbyte[6 + b1 + b3 - b4] == 0; b4--);
    if (paramArrayOfbyte[0] != 48 || paramArrayOfbyte[1] != paramArrayOfbyte.length - 2 || paramArrayOfbyte[2] != 2 || b2 > 20 || paramArrayOfbyte[4 + b1] != 2 || b4 > 20)
      throw new IOException("Invalid ASN.1 format of DSA signature"); 
    byte[] arrayOfByte = new byte[40];
    System.arraycopy(paramArrayOfbyte, 4 + b1 - b2, arrayOfByte, 20 - b2, b2);
    System.arraycopy(paramArrayOfbyte, 6 + b1 + b3 - b4, arrayOfByte, 40 - b4, b4);
    return arrayOfByte;
  }
  
  private static byte[] convertXMLDSIGtoASN1(byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte.length != 40)
      throw new IOException("Invalid XMLDSIG format of DSA signature"); 
    byte b1;
    for (b1 = 20; b1 > 0 && paramArrayOfbyte[20 - b1] == 0; b1--);
    byte b2 = b1;
    if (paramArrayOfbyte[20 - b1] < 0)
      b2++; 
    byte b3;
    for (b3 = 20; b3 > 0 && paramArrayOfbyte[40 - b3] == 0; b3--);
    byte b4 = b3;
    if (paramArrayOfbyte[40 - b3] < 0)
      b4++; 
    byte[] arrayOfByte = new byte[6 + b2 + b4];
    arrayOfByte[0] = 48;
    arrayOfByte[1] = (byte)(4 + b2 + b4);
    arrayOfByte[2] = 2;
    arrayOfByte[3] = (byte)b2;
    System.arraycopy(paramArrayOfbyte, 20 - b1, arrayOfByte, 4 + b2 - b1, b1);
    arrayOfByte[4 + b2] = 2;
    arrayOfByte[5 + b2] = (byte)b4;
    System.arraycopy(paramArrayOfbyte, 40 - b3, arrayOfByte, 6 + b2 + b4 - b3, b3);
    return arrayOfByte;
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 57;
  }
  
  static final class SHA1withDSA extends DOMSignatureMethod {
    SHA1withDSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA1withDSA(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
    }
    
    String getSignatureAlgorithm() {
      return "SHA1withDSA";
    }
  }
  
  static final class SHA512withRSA extends DOMSignatureMethod {
    SHA512withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA512withRSA(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
    }
    
    String getSignatureAlgorithm() {
      return "SHA512withRSA";
    }
  }
  
  static final class SHA384withRSA extends DOMSignatureMethod {
    SHA384withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA384withRSA(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
    }
    
    String getSignatureAlgorithm() {
      return "SHA384withRSA";
    }
  }
  
  static final class SHA256withRSA extends DOMSignatureMethod {
    SHA256withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA256withRSA(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
    }
    
    String getSignatureAlgorithm() {
      return "SHA256withRSA";
    }
  }
  
  static final class SHA1withRSA extends DOMSignatureMethod {
    SHA1withRSA(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA1withRSA(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
    }
    
    String getSignatureAlgorithm() {
      return "SHA1withRSA";
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMSignatureMethod.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */