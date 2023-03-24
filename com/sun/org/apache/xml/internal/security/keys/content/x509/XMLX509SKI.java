package com.sun.org.apache.xml.internal.security.keys.content.x509;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509SKI extends SignatureElementProxy implements XMLX509DataContent {
  static Logger log = Logger.getLogger(XMLX509SKI.class.getName());
  
  public static final String SKI_OID = "2.5.29.14";
  
  public XMLX509SKI(Document paramDocument, byte[] paramArrayOfbyte) {
    super(paramDocument);
    addBase64Text(paramArrayOfbyte);
  }
  
  public XMLX509SKI(Document paramDocument, X509Certificate paramX509Certificate) throws XMLSecurityException {
    super(paramDocument);
    addBase64Text(getSKIBytesFromCert(paramX509Certificate));
  }
  
  public XMLX509SKI(Element paramElement, String paramString) throws XMLSecurityException {
    super(paramElement, paramString);
  }
  
  public byte[] getSKIBytes() throws XMLSecurityException {
    return getBytesFromTextChild();
  }
  
  public static byte[] getSKIBytesFromCert(X509Certificate paramX509Certificate) throws XMLSecurityException {
    if (paramX509Certificate.getVersion() < 3) {
      Object[] arrayOfObject = { new Integer(paramX509Certificate.getVersion()) };
      throw new XMLSecurityException("certificate.noSki.lowVersion", arrayOfObject);
    } 
    byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.14");
    if (arrayOfByte1 == null)
      throw new XMLSecurityException("certificate.noSki.null"); 
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length - 4];
    System.arraycopy(arrayOfByte1, 4, arrayOfByte2, 0, arrayOfByte2.length);
    log.log(Level.FINE, "Base64 of SKI is " + Base64.encode(arrayOfByte2));
    return arrayOfByte2;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (!getClass().getName().equals(paramObject.getClass().getName()))
      return false; 
    XMLX509SKI xMLX509SKI = (XMLX509SKI)paramObject;
    try {
      return MessageDigest.isEqual(xMLX509SKI.getSKIBytes(), getSKIBytes());
    } catch (XMLSecurityException xMLSecurityException) {
      return false;
    } 
  }
  
  public int hashCode() {
    return 92;
  }
  
  public String getBaseLocalName() {
    return "X509SKI";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\content\x509\XMLX509SKI.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */