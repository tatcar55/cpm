package org.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignContext;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLValidateContext;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import org.jcp.xml.dsig.internal.MacOutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class DOMHMACSignatureMethod extends DOMSignatureMethod {
  private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal.dom");
  
  private Mac hmac;
  
  private int outputLength;
  
  private boolean outputLengthSet;
  
  DOMHMACSignatureMethod(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
    super(paramAlgorithmParameterSpec);
  }
  
  DOMHMACSignatureMethod(Element paramElement) throws MarshalException {
    super(paramElement);
  }
  
  void checkParams(SignatureMethodParameterSpec paramSignatureMethodParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramSignatureMethodParameterSpec != null) {
      if (!(paramSignatureMethodParameterSpec instanceof HMACParameterSpec))
        throw new InvalidAlgorithmParameterException("params must be of type HMACParameterSpec"); 
      this.outputLength = ((HMACParameterSpec)paramSignatureMethodParameterSpec).getOutputLength();
      this.outputLengthSet = true;
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "Setting outputLength from HMACParameterSpec to: " + this.outputLength); 
    } 
  }
  
  SignatureMethodParameterSpec unmarshalParams(Element paramElement) throws MarshalException {
    this.outputLength = (new Integer(paramElement.getFirstChild().getNodeValue())).intValue();
    this.outputLengthSet = true;
    if (log.isLoggable(Level.FINE))
      log.log(Level.FINE, "unmarshalled outputLength: " + this.outputLength); 
    return new HMACParameterSpec(this.outputLength);
  }
  
  void marshalParams(Element paramElement, String paramString) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramElement);
    Element element = DOMUtils.createElement(document, "HMACOutputLength", "http://www.w3.org/2000/09/xmldsig#", paramString);
    element.appendChild(document.createTextNode(String.valueOf(this.outputLength)));
    paramElement.appendChild(element);
  }
  
  boolean verify(Key paramKey, DOMSignedInfo paramDOMSignedInfo, byte[] paramArrayOfbyte, XMLValidateContext paramXMLValidateContext) throws InvalidKeyException, SignatureException, XMLSignatureException {
    if (paramKey == null || paramDOMSignedInfo == null || paramArrayOfbyte == null)
      throw new NullPointerException(); 
    if (!(paramKey instanceof javax.crypto.SecretKey))
      throw new InvalidKeyException("key must be SecretKey"); 
    if (this.hmac == null)
      try {
        this.hmac = Mac.getInstance(getSignatureAlgorithm());
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLSignatureException(noSuchAlgorithmException);
      }  
    if (this.outputLengthSet && this.outputLength < getDigestLength())
      throw new XMLSignatureException("HMACOutputLength must not be less than " + getDigestLength()); 
    this.hmac.init(paramKey);
    paramDOMSignedInfo.canonicalize(paramXMLValidateContext, new MacOutputStream(this.hmac));
    byte[] arrayOfByte = this.hmac.doFinal();
    return MessageDigest.isEqual(paramArrayOfbyte, arrayOfByte);
  }
  
  byte[] sign(Key paramKey, DOMSignedInfo paramDOMSignedInfo, XMLSignContext paramXMLSignContext) throws InvalidKeyException, XMLSignatureException {
    if (paramKey == null || paramDOMSignedInfo == null)
      throw new NullPointerException(); 
    if (!(paramKey instanceof javax.crypto.SecretKey))
      throw new InvalidKeyException("key must be SecretKey"); 
    if (this.hmac == null)
      try {
        this.hmac = Mac.getInstance(getSignatureAlgorithm());
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        throw new XMLSignatureException(noSuchAlgorithmException);
      }  
    if (this.outputLengthSet && this.outputLength < getDigestLength())
      throw new XMLSignatureException("HMACOutputLength must not be less than " + getDigestLength()); 
    this.hmac.init(paramKey);
    paramDOMSignedInfo.canonicalize(paramXMLSignContext, new MacOutputStream(this.hmac));
    return this.hmac.doFinal();
  }
  
  boolean paramsEqual(AlgorithmParameterSpec paramAlgorithmParameterSpec) {
    if (getParameterSpec() == paramAlgorithmParameterSpec)
      return true; 
    if (!(paramAlgorithmParameterSpec instanceof HMACParameterSpec))
      return false; 
    HMACParameterSpec hMACParameterSpec = (HMACParameterSpec)paramAlgorithmParameterSpec;
    return (this.outputLength == hMACParameterSpec.getOutputLength());
  }
  
  abstract int getDigestLength();
  
  static final class SHA512 extends DOMHMACSignatureMethod {
    SHA512(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA512(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
    }
    
    String getSignatureAlgorithm() {
      return "HmacSHA512";
    }
    
    int getDigestLength() {
      return 512;
    }
  }
  
  static final class SHA384 extends DOMHMACSignatureMethod {
    SHA384(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA384(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
    }
    
    String getSignatureAlgorithm() {
      return "HmacSHA384";
    }
    
    int getDigestLength() {
      return 384;
    }
  }
  
  static final class SHA256 extends DOMHMACSignatureMethod {
    SHA256(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA256(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
    }
    
    String getSignatureAlgorithm() {
      return "HmacSHA256";
    }
    
    int getDigestLength() {
      return 256;
    }
  }
  
  static final class SHA1 extends DOMHMACSignatureMethod {
    SHA1(AlgorithmParameterSpec param1AlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
      super(param1AlgorithmParameterSpec);
    }
    
    SHA1(Element param1Element) throws MarshalException {
      super(param1Element);
    }
    
    public String getAlgorithm() {
      return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
    }
    
    String getSignatureAlgorithm() {
      return "HmacSHA1";
    }
    
    int getDigestLength() {
      return 160;
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMHMACSignatureMethod.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */