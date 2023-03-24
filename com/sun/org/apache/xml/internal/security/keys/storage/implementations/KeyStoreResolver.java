package com.sun.org.apache.xml.internal.security.keys.storage.implementations;

import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;

public class KeyStoreResolver extends StorageResolverSpi {
  KeyStore _keyStore = null;
  
  Iterator _iterator = null;
  
  public KeyStoreResolver(KeyStore paramKeyStore) throws StorageResolverException {
    this._keyStore = paramKeyStore;
    this._iterator = new KeyStoreIterator(this._keyStore);
  }
  
  public Iterator getIterator() {
    return this._iterator;
  }
  
  public static void main(String[] paramArrayOfString) throws Exception {
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(new FileInputStream("data/com/sun/org/apache/xml/internal/security/samples/input/keystore.jks"), "xmlsecurity".toCharArray());
    KeyStoreResolver keyStoreResolver = new KeyStoreResolver(keyStore);
    Iterator iterator = keyStoreResolver.getIterator();
    while (iterator.hasNext()) {
      X509Certificate x509Certificate = iterator.next();
      byte[] arrayOfByte = XMLX509SKI.getSKIBytesFromCert(x509Certificate);
      System.out.println(Base64.encode(arrayOfByte));
    } 
  }
  
  static class KeyStoreIterator implements Iterator {
    KeyStore _keyStore = null;
    
    Enumeration _aliases = null;
    
    public KeyStoreIterator(KeyStore param1KeyStore) throws StorageResolverException {
      try {
        this._keyStore = param1KeyStore;
        this._aliases = this._keyStore.aliases();
      } catch (KeyStoreException keyStoreException) {
        throw new StorageResolverException("generic.EmptyMessage", keyStoreException);
      } 
    }
    
    public boolean hasNext() {
      return this._aliases.hasMoreElements();
    }
    
    public Object next() {
      String str = this._aliases.nextElement();
      try {
        return this._keyStore.getCertificate(str);
      } catch (KeyStoreException keyStoreException) {
        return null;
      } 
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Can't remove keys from KeyStore");
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\storage\implementations\KeyStoreResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */