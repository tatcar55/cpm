package com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.w3c.dom.Element;

public class EncryptedKeyResolver extends KeyResolverSpi {
  static Logger log = Logger.getLogger(RSAKeyValueResolver.class.getName());
  
  Key _kek;
  
  String _algorithm;
  
  public EncryptedKeyResolver(String paramString) {
    this._kek = null;
    this._algorithm = paramString;
  }
  
  public EncryptedKeyResolver(String paramString, Key paramKey) {
    this._algorithm = paramString;
    this._kek = paramKey;
  }
  
  public PublicKey engineLookupAndResolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    return null;
  }
  
  public X509Certificate engineLookupResolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    return null;
  }
  
  public SecretKey engineLookupAndResolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) {
    SecretKey secretKey = null;
    log.log(Level.FINE, "EncryptedKeyResolver - Can I resolve " + paramElement.getTagName());
    if (paramElement == null)
      return null; 
    boolean bool = XMLUtils.elementIsInEncryptionSpace(paramElement, "EncryptedKey");
    if (bool) {
      log.log(Level.FINE, "Passed an Encrypted Key");
      try {
        XMLCipher xMLCipher = XMLCipher.getInstance();
        xMLCipher.init(4, this._kek);
        EncryptedKey encryptedKey = xMLCipher.loadEncryptedKey(paramElement);
        secretKey = (SecretKey)xMLCipher.decryptKey(encryptedKey, this._algorithm);
      } catch (Exception exception) {}
    } 
    return secretKey;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\keyresolver\implementations\EncryptedKeyResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */