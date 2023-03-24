package com.sun.org.apache.xml.internal.security.keys.content.x509;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509Certificate extends SignatureElementProxy implements XMLX509DataContent {
  public static final String JCA_CERT_ID = "X.509";
  
  public XMLX509Certificate(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public XMLX509Certificate(Document paramDocument, byte[] paramArrayOfbyte) {
    super(paramDocument);
    addBase64Text(paramArrayOfbyte);
  }
  
  public XMLX509Certificate(Document paramDocument, X509Certificate paramX509Certificate) throws XMLSecurityException {
    super(paramDocument);
    try {
      addBase64Text(paramX509Certificate.getEncoded());
    } catch (CertificateEncodingException certificateEncodingException) {
      throw new XMLSecurityException("empty", certificateEncodingException);
    } 
  }
  
  public byte[] getCertificateBytes() throws XMLSecurityException {
    return getBytesFromTextChild();
  }
  
  public X509Certificate getX509Certificate() throws XMLSecurityException {
    try {
      byte[] arrayOfByte = getCertificateBytes();
      CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
      X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(arrayOfByte));
      return (x509Certificate != null) ? x509Certificate : null;
    } catch (CertificateException certificateException) {
      throw new XMLSecurityException("empty", certificateException);
    } 
  }
  
  public PublicKey getPublicKey() throws XMLSecurityException {
    X509Certificate x509Certificate = getX509Certificate();
    return (x509Certificate != null) ? x509Certificate.getPublicKey() : null;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (!getClass().getName().equals(paramObject.getClass().getName()))
      return false; 
    XMLX509Certificate xMLX509Certificate = (XMLX509Certificate)paramObject;
    try {
      return MessageDigest.isEqual(xMLX509Certificate.getCertificateBytes(), getCertificateBytes());
    } catch (XMLSecurityException xMLSecurityException) {
      return false;
    } 
  }
  
  public int hashCode() {
    return 72;
  }
  
  public String getBaseLocalName() {
    return "X509Certificate";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\x509\XMLX509Certificate.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */