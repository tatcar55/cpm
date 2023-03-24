package com.sun.org.apache.xml.internal.security.algorithms.implementations;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public abstract class IntegrityHmac extends SignatureAlgorithmSpi {
  static Logger log = Logger.getLogger(IntegrityHmacSHA1.class.getName());
  
  private Mac _macAlgorithm = null;
  
  int _HMACOutputLength = 0;
  
  private boolean _HMACOutputLengthSet = false;
  
  public abstract String engineGetURI();
  
  abstract int getDigestLength();
  
  public IntegrityHmac() throws XMLSignatureException {
    String str = JCEMapper.translateURItoJCEID(engineGetURI());
    log.log(Level.FINE, "Created IntegrityHmacSHA1 using " + str);
    try {
      this._macAlgorithm = Mac.getInstance(str);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      Object[] arrayOfObject = { str, noSuchAlgorithmException.getLocalizedMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
    } 
  }
  
  protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
    throw new XMLSignatureException("empty");
  }
  
  public void reset() {
    this._HMACOutputLength = 0;
    this._HMACOutputLengthSet = false;
    this._macAlgorithm.reset();
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws XMLSignatureException {
    try {
      if (this._HMACOutputLengthSet && this._HMACOutputLength < getDigestLength()) {
        log.log(Level.FINE, "HMACOutputLength must not be less than " + getDigestLength());
        Object[] arrayOfObject = { String.valueOf(getDigestLength()) };
        throw new XMLSignatureException("algorithms.HMACOutputLengthMin", arrayOfObject);
      } 
      byte[] arrayOfByte = this._macAlgorithm.doFinal();
      return MessageDigestAlgorithm.isEqual(arrayOfByte, paramArrayOfbyte);
    } catch (IllegalStateException illegalStateException) {
      throw new XMLSignatureException("empty", illegalStateException);
    } 
  }
  
  protected void engineInitVerify(Key paramKey) throws XMLSignatureException {
    if (!(paramKey instanceof SecretKey)) {
      String str1 = paramKey.getClass().getName();
      String str2 = SecretKey.class.getName();
      Object[] arrayOfObject = { str1, str2 };
      throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
    } 
    try {
      this._macAlgorithm.init(paramKey);
    } catch (InvalidKeyException invalidKeyException) {
      Mac mac = this._macAlgorithm;
      try {
        this._macAlgorithm = Mac.getInstance(this._macAlgorithm.getAlgorithm());
      } catch (Exception exception) {
        log.log(Level.FINE, "Exception when reinstantiating Mac:" + exception);
        this._macAlgorithm = mac;
      } 
      throw new XMLSignatureException("empty", invalidKeyException);
    } 
  }
  
  protected byte[] engineSign() throws XMLSignatureException {
    try {
      if (this._HMACOutputLengthSet && this._HMACOutputLength < getDigestLength()) {
        log.log(Level.FINE, "HMACOutputLength must not be less than " + getDigestLength());
        Object[] arrayOfObject = { String.valueOf(getDigestLength()) };
        throw new XMLSignatureException("algorithms.HMACOutputLengthMin", arrayOfObject);
      } 
      return this._macAlgorithm.doFinal();
    } catch (IllegalStateException illegalStateException) {
      throw new XMLSignatureException("empty", illegalStateException);
    } 
  }
  
  private static byte[] reduceBitLength(byte[] paramArrayOfbyte, int paramInt) {
    int i = paramInt / 8;
    int j = paramInt % 8;
    byte[] arrayOfByte = new byte[i + ((j == 0) ? 0 : 1)];
    System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, i);
    if (j > 0) {
      byte[] arrayOfByte1 = { 0, Byte.MIN_VALUE, -64, -32, -16, -8, -4, -2 };
      arrayOfByte[i] = (byte)(paramArrayOfbyte[i] & arrayOfByte1[j]);
    } 
    return arrayOfByte;
  }
  
  protected void engineInitSign(Key paramKey) throws XMLSignatureException {
    if (!(paramKey instanceof SecretKey)) {
      String str1 = paramKey.getClass().getName();
      String str2 = SecretKey.class.getName();
      Object[] arrayOfObject = { str1, str2 };
      throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
    } 
    try {
      this._macAlgorithm.init(paramKey);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLSignatureException("empty", invalidKeyException);
    } 
  }
  
  protected void engineInitSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
    if (!(paramKey instanceof SecretKey)) {
      String str1 = paramKey.getClass().getName();
      String str2 = SecretKey.class.getName();
      Object[] arrayOfObject = { str1, str2 };
      throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
    } 
    try {
      this._macAlgorithm.init(paramKey, paramAlgorithmParameterSpec);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLSignatureException("empty", invalidKeyException);
    } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
      throw new XMLSignatureException("empty", invalidAlgorithmParameterException);
    } 
  }
  
  protected void engineInitSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
    throw new XMLSignatureException("algorithms.CannotUseSecureRandomOnMAC");
  }
  
  protected void engineUpdate(byte[] paramArrayOfbyte) throws XMLSignatureException {
    try {
      this._macAlgorithm.update(paramArrayOfbyte);
    } catch (IllegalStateException illegalStateException) {
      throw new XMLSignatureException("empty", illegalStateException);
    } 
  }
  
  protected void engineUpdate(byte paramByte) throws XMLSignatureException {
    try {
      this._macAlgorithm.update(paramByte);
    } catch (IllegalStateException illegalStateException) {
      throw new XMLSignatureException("empty", illegalStateException);
    } 
  }
  
  protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
    try {
      this._macAlgorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
    } catch (IllegalStateException illegalStateException) {
      throw new XMLSignatureException("empty", illegalStateException);
    } 
  }
  
  protected String engineGetJCEAlgorithmString() {
    log.log(Level.FINE, "engineGetJCEAlgorithmString()");
    return this._macAlgorithm.getAlgorithm();
  }
  
  protected String engineGetJCEProviderName() {
    return this._macAlgorithm.getProvider().getName();
  }
  
  protected void engineSetHMACOutputLength(int paramInt) {
    this._HMACOutputLength = paramInt;
    this._HMACOutputLengthSet = true;
  }
  
  protected void engineGetContextFromElement(Element paramElement) {
    super.engineGetContextFromElement(paramElement);
    if (paramElement == null)
      throw new IllegalArgumentException("element null"); 
    Text text = XMLUtils.selectDsNodeText(paramElement.getFirstChild(), "HMACOutputLength", 0);
    if (text != null) {
      this._HMACOutputLength = Integer.parseInt(text.getData());
      this._HMACOutputLengthSet = true;
    } 
  }
  
  public void engineAddContextToElement(Element paramElement) {
    if (paramElement == null)
      throw new IllegalArgumentException("null element"); 
    if (this._HMACOutputLengthSet) {
      Document document = paramElement.getOwnerDocument();
      Element element = XMLUtils.createElementInSignatureSpace(document, "HMACOutputLength");
      Text text = document.createTextNode((new Integer(this._HMACOutputLength)).toString());
      element.appendChild(text);
      XMLUtils.addReturnToElement(paramElement);
      paramElement.appendChild(element);
      XMLUtils.addReturnToElement(paramElement);
    } 
  }
  
  public static class IntegrityHmacMD5 extends IntegrityHmac {
    public String engineGetURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-md5";
    }
    
    int getDigestLength() {
      return 128;
    }
  }
  
  public static class IntegrityHmacRIPEMD160 extends IntegrityHmac {
    public String engineGetURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160";
    }
    
    int getDigestLength() {
      return 160;
    }
  }
  
  public static class IntegrityHmacSHA512 extends IntegrityHmac {
    public String engineGetURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha512";
    }
    
    int getDigestLength() {
      return 512;
    }
  }
  
  public static class IntegrityHmacSHA384 extends IntegrityHmac {
    public String engineGetURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha384";
    }
    
    int getDigestLength() {
      return 384;
    }
  }
  
  public static class IntegrityHmacSHA256 extends IntegrityHmac {
    public String engineGetURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#hmac-sha256";
    }
    
    int getDigestLength() {
      return 256;
    }
  }
  
  public static class IntegrityHmacSHA1 extends IntegrityHmac {
    public String engineGetURI() {
      return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
    }
    
    int getDigestLength() {
      return 160;
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\algorithms\implementations\IntegrityHmac.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */