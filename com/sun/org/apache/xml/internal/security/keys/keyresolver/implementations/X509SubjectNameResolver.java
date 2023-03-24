package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SubjectName;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.w3c.dom.Element;

public class X509SubjectNameResolver extends KeyResolverSpi {
  static Logger log = Logger.getLogger(X509SubjectNameResolver.class.getName());
  
  public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    X509Certificate x509Certificate = engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
    return (x509Certificate != null) ? x509Certificate.getPublicKey() : null;
  }
  
  public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    log.log(Level.FINE, "Can I resolve " + paramElement.getTagName() + "?");
    Element[] arrayOfElement = null;
    XMLX509SubjectName[] arrayOfXMLX509SubjectName = null;
    if (!XMLUtils.elementIsInSignatureSpace(paramElement, "X509Data")) {
      log.log(Level.FINE, "I can't");
      return null;
    } 
    arrayOfElement = XMLUtils.selectDsNodes(paramElement.getFirstChild(), "X509SubjectName");
    if (arrayOfElement == null || arrayOfElement.length <= 0) {
      log.log(Level.FINE, "I can't");
      return null;
    } 
    try {
      if (paramStorageResolver == null) {
        Object[] arrayOfObject = { "X509SubjectName" };
        KeyResolverException keyResolverException = new KeyResolverException("KeyResolver.needStorageResolver", arrayOfObject);
        log.log(Level.INFO, "", keyResolverException);
        throw keyResolverException;
      } 
      arrayOfXMLX509SubjectName = new XMLX509SubjectName[arrayOfElement.length];
      for (byte b = 0; b < arrayOfElement.length; b++)
        arrayOfXMLX509SubjectName[b] = new XMLX509SubjectName(arrayOfElement[b], paramString); 
      while (paramStorageResolver.hasNext()) {
        X509Certificate x509Certificate = paramStorageResolver.next();
        XMLX509SubjectName xMLX509SubjectName = new XMLX509SubjectName(paramElement.getOwnerDocument(), x509Certificate);
        log.log(Level.FINE, "Found Certificate SN: " + xMLX509SubjectName.getSubjectName());
        for (byte b1 = 0; b1 < arrayOfXMLX509SubjectName.length; b1++) {
          log.log(Level.FINE, "Found Element SN:     " + arrayOfXMLX509SubjectName[b1].getSubjectName());
          if (xMLX509SubjectName.equals(arrayOfXMLX509SubjectName[b1])) {
            log.log(Level.FINE, "match !!! ");
            return x509Certificate;
          } 
          log.log(Level.FINE, "no match...");
        } 
      } 
      return null;
    } catch (XMLSecurityException xMLSecurityException) {
      log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
      throw new KeyResolverException("generic.EmptyMessage", xMLSecurityException);
    } 
  }
  
  public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\keyresolver\implementations\X509SubjectNameResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */