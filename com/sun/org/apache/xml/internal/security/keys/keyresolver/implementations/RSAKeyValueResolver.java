package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.w3c.dom.Element;

public class RSAKeyValueResolver extends KeyResolverSpi {
  static Logger log = Logger.getLogger(RSAKeyValueResolver.class.getName());
  
  public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    log.log(Level.FINE, "Can I resolve " + paramElement.getTagName());
    if (paramElement == null)
      return null; 
    boolean bool = XMLUtils.elementIsInSignatureSpace(paramElement, "KeyValue");
    Element element = null;
    if (bool) {
      element = XMLUtils.selectDsNode(paramElement.getFirstChild(), "RSAKeyValue", 0);
    } else if (XMLUtils.elementIsInSignatureSpace(paramElement, "RSAKeyValue")) {
      element = paramElement;
    } 
    if (element == null)
      return null; 
    try {
      RSAKeyValue rSAKeyValue = new RSAKeyValue(element, paramString);
      return rSAKeyValue.getPublicKey();
    } catch (XMLSecurityException xMLSecurityException) {
      log.log(Level.FINE, "XMLSecurityException", xMLSecurityException);
      return null;
    } 
  }
  
  public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    return null;
  }
  
  public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\keyresolver\implementations\RSAKeyValueResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */