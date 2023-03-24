package com.sun.org.apache.xml.internal.security.algorithms.implementations;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithmSpi;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SignatureECDSA extends SignatureAlgorithmSpi {
  static Logger log = Logger.getLogger(SignatureECDSA.class.getName());
  
  private Signature _signatureAlgorithm = null;
  
  public abstract String engineGetURI();
  
  private static byte[] convertASN1toXMLDSIG(byte[] paramArrayOfbyte) throws IOException {
    byte b1 = paramArrayOfbyte[3];
    byte b2;
    for (b2 = b1; b2 > 0 && paramArrayOfbyte[4 + b1 - b2] == 0; b2--);
    byte b3 = paramArrayOfbyte[5 + b1];
    byte b4;
    for (b4 = b3; b4 > 0 && paramArrayOfbyte[6 + b1 + b3 - b4] == 0; b4--);
    if (paramArrayOfbyte[0] != 48 || paramArrayOfbyte[1] != paramArrayOfbyte.length - 2 || paramArrayOfbyte[2] != 2 || b2 > 24 || paramArrayOfbyte[4 + b1] != 2 || b4 > 24)
      throw new IOException("Invalid ASN.1 format of ECDSA signature"); 
    byte[] arrayOfByte = new byte[48];
    System.arraycopy(paramArrayOfbyte, 4 + b1 - b2, arrayOfByte, 24 - b2, b2);
    System.arraycopy(paramArrayOfbyte, 6 + b1 + b3 - b4, arrayOfByte, 48 - b4, b4);
    return arrayOfByte;
  }
  
  private static byte[] convertXMLDSIGtoASN1(byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte.length != 48)
      throw new IOException("Invalid XMLDSIG format of ECDSA signature"); 
    byte b1;
    for (b1 = 24; b1 > 0 && paramArrayOfbyte[24 - b1] == 0; b1--);
    byte b2 = b1;
    if (paramArrayOfbyte[24 - b1] < 0)
      b2++; 
    byte b3;
    for (b3 = 24; b3 > 0 && paramArrayOfbyte[48 - b3] == 0; b3--);
    byte b4 = b3;
    if (paramArrayOfbyte[48 - b3] < 0)
      b4++; 
    byte[] arrayOfByte = new byte[6 + b2 + b4];
    arrayOfByte[0] = 48;
    arrayOfByte[1] = (byte)(4 + b2 + b4);
    arrayOfByte[2] = 2;
    arrayOfByte[3] = (byte)b2;
    System.arraycopy(paramArrayOfbyte, 24 - b1, arrayOfByte, 4 + b2 - b1, b1);
    arrayOfByte[4 + b2] = 2;
    arrayOfByte[5 + b2] = (byte)b4;
    System.arraycopy(paramArrayOfbyte, 48 - b3, arrayOfByte, 6 + b2 + b4 - b3, b3);
    return arrayOfByte;
  }
  
  public SignatureECDSA() throws XMLSignatureException {
    String str1 = JCEMapper.translateURItoJCEID(engineGetURI());
    log.log(Level.FINE, "Created SignatureECDSA using " + str1);
    String str2 = JCEMapper.getProviderId();
    try {
      if (str2 == null) {
        this._signatureAlgorithm = Signature.getInstance(str1);
      } else {
        this._signatureAlgorithm = Signature.getInstance(str1, str2);
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      Object[] arrayOfObject = { str1, noSuchAlgorithmException.getLocalizedMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
    } catch (NoSuchProviderException noSuchProviderException) {
      Object[] arrayOfObject = { str1, noSuchProviderException.getLocalizedMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
    } 
  }
  
  protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
    try {
      this._signatureAlgorithm.setParameter(paramAlgorithmParameterSpec);
    } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
      throw new XMLSignatureException("empty", invalidAlgorithmParameterException);
    } 
  }
  
  protected boolean engineVerify(byte[] paramArrayOfbyte) throws XMLSignatureException {
    try {
      byte[] arrayOfByte = convertXMLDSIGtoASN1(paramArrayOfbyte);
      log.log(Level.FINE, "Called ECDSA.verify() on " + Base64.encode(paramArrayOfbyte));
      return this._signatureAlgorithm.verify(arrayOfByte);
    } catch (SignatureException signatureException) {
      throw new XMLSignatureException("empty", signatureException);
    } catch (IOException iOException) {
      throw new XMLSignatureException("empty", iOException);
    } 
  }
  
  protected void engineInitVerify(Key paramKey) throws XMLSignatureException {
    if (!(paramKey instanceof PublicKey)) {
      String str1 = paramKey.getClass().getName();
      String str2 = PublicKey.class.getName();
      Object[] arrayOfObject = { str1, str2 };
      throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
    } 
    try {
      this._signatureAlgorithm.initVerify((PublicKey)paramKey);
    } catch (InvalidKeyException invalidKeyException) {
      Signature signature = this._signatureAlgorithm;
      try {
        this._signatureAlgorithm = Signature.getInstance(this._signatureAlgorithm.getAlgorithm());
      } catch (Exception exception) {
        log.log(Level.FINE, "Exception when reinstantiating Signature:" + exception);
        this._signatureAlgorithm = signature;
      } 
      throw new XMLSignatureException("empty", invalidKeyException);
    } 
  }
  
  protected byte[] engineSign() throws XMLSignatureException {
    try {
      byte[] arrayOfByte = this._signatureAlgorithm.sign();
      return convertASN1toXMLDSIG(arrayOfByte);
    } catch (SignatureException signatureException) {
      throw new XMLSignatureException("empty", signatureException);
    } catch (IOException iOException) {
      throw new XMLSignatureException("empty", iOException);
    } 
  }
  
  protected void engineInitSign(Key paramKey, SecureRandom paramSecureRandom) throws XMLSignatureException {
    if (!(paramKey instanceof PrivateKey)) {
      String str1 = paramKey.getClass().getName();
      String str2 = PrivateKey.class.getName();
      Object[] arrayOfObject = { str1, str2 };
      throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
    } 
    try {
      this._signatureAlgorithm.initSign((PrivateKey)paramKey, paramSecureRandom);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLSignatureException("empty", invalidKeyException);
    } 
  }
  
  protected void engineInitSign(Key paramKey) throws XMLSignatureException {
    if (!(paramKey instanceof PrivateKey)) {
      String str1 = paramKey.getClass().getName();
      String str2 = PrivateKey.class.getName();
      Object[] arrayOfObject = { str1, str2 };
      throw new XMLSignatureException("algorithms.WrongKeyForThisOperation", arrayOfObject);
    } 
    try {
      this._signatureAlgorithm.initSign((PrivateKey)paramKey);
    } catch (InvalidKeyException invalidKeyException) {
      throw new XMLSignatureException("empty", invalidKeyException);
    } 
  }
  
  protected void engineUpdate(byte[] paramArrayOfbyte) throws XMLSignatureException {
    try {
      this._signatureAlgorithm.update(paramArrayOfbyte);
    } catch (SignatureException signatureException) {
      throw new XMLSignatureException("empty", signatureException);
    } 
  }
  
  protected void engineUpdate(byte paramByte) throws XMLSignatureException {
    try {
      this._signatureAlgorithm.update(paramByte);
    } catch (SignatureException signatureException) {
      throw new XMLSignatureException("empty", signatureException);
    } 
  }
  
  protected void engineUpdate(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws XMLSignatureException {
    try {
      this._signatureAlgorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
    } catch (SignatureException signatureException) {
      throw new XMLSignatureException("empty", signatureException);
    } 
  }
  
  protected String engineGetJCEAlgorithmString() {
    return this._signatureAlgorithm.getAlgorithm();
  }
  
  protected String engineGetJCEProviderName() {
    return this._signatureAlgorithm.getProvider().getName();
  }
  
  protected void engineSetHMACOutputLength(int paramInt) throws XMLSignatureException {
    throw new XMLSignatureException("algorithms.HMACOutputLengthOnlyForHMAC");
  }
  
  protected void engineInitSign(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec) throws XMLSignatureException {
    throw new XMLSignatureException("algorithms.CannotUseAlgorithmParameterSpecOnRSA");
  }
  
  public static class SignatureECDSASHA1 extends SignatureECDSA {
    public String engineGetURI() {
      return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1";
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\algorithms\implementations\SignatureECDSA.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */