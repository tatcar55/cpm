package com.sun.org.apache.xml.internal.security.algorithms;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;

public class MessageDigestAlgorithm extends Algorithm {
  public static final String ALGO_ID_DIGEST_NOT_RECOMMENDED_MD5 = "http://www.w3.org/2001/04/xmldsig-more#md5";
  
  public static final String ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
  
  public static final String ALGO_ID_DIGEST_SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
  
  public static final String ALGO_ID_DIGEST_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#sha384";
  
  public static final String ALGO_ID_DIGEST_SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
  
  public static final String ALGO_ID_DIGEST_RIPEMD160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
  
  MessageDigest algorithm = null;
  
  static ThreadLocal instances = new ThreadLocal() {
      protected Object initialValue() {
        return new HashMap();
      }
    };
  
  private MessageDigestAlgorithm(Document paramDocument, MessageDigest paramMessageDigest, String paramString) {
    super(paramDocument, paramString);
    this.algorithm = paramMessageDigest;
  }
  
  public static MessageDigestAlgorithm getInstance(Document paramDocument, String paramString) throws XMLSignatureException {
    MessageDigest messageDigest = getDigestInstance(paramString);
    return new MessageDigestAlgorithm(paramDocument, messageDigest, paramString);
  }
  
  private static MessageDigest getDigestInstance(String paramString) throws XMLSignatureException {
    MessageDigest messageDigest2;
    MessageDigest messageDigest1 = (MessageDigest)((Map)instances.get()).get(paramString);
    if (messageDigest1 != null)
      return messageDigest1; 
    String str1 = JCEMapper.translateURItoJCEID(paramString);
    if (str1 == null) {
      Object[] arrayOfObject = { paramString };
      throw new XMLSignatureException("algorithms.NoSuchMap", arrayOfObject);
    } 
    String str2 = JCEMapper.getProviderId();
    try {
      if (str2 == null) {
        messageDigest2 = MessageDigest.getInstance(str1);
      } else {
        messageDigest2 = MessageDigest.getInstance(str1, str2);
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      Object[] arrayOfObject = { str1, noSuchAlgorithmException.getLocalizedMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
    } catch (NoSuchProviderException noSuchProviderException) {
      Object[] arrayOfObject = { str1, noSuchProviderException.getLocalizedMessage() };
      throw new XMLSignatureException("algorithms.NoSuchAlgorithm", arrayOfObject);
    } 
    ((Map)instances.get()).put(paramString, messageDigest2);
    return messageDigest2;
  }
  
  public MessageDigest getAlgorithm() {
    return this.algorithm;
  }
  
  public static boolean isEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
    return MessageDigest.isEqual(paramArrayOfbyte1, paramArrayOfbyte2);
  }
  
  public byte[] digest() {
    return this.algorithm.digest();
  }
  
  public byte[] digest(byte[] paramArrayOfbyte) {
    return this.algorithm.digest(paramArrayOfbyte);
  }
  
  public int digest(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws DigestException {
    return this.algorithm.digest(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public String getJCEAlgorithmString() {
    return this.algorithm.getAlgorithm();
  }
  
  public Provider getJCEProvider() {
    return this.algorithm.getProvider();
  }
  
  public int getDigestLength() {
    return this.algorithm.getDigestLength();
  }
  
  public void reset() {
    this.algorithm.reset();
  }
  
  public void update(byte[] paramArrayOfbyte) {
    this.algorithm.update(paramArrayOfbyte);
  }
  
  public void update(byte paramByte) {
    this.algorithm.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.algorithm.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public String getBaseNamespace() {
    return "http://www.w3.org/2000/09/xmldsig#";
  }
  
  public String getBaseLocalName() {
    return "DigestMethod";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\algorithms\MessageDigestAlgorithm.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */