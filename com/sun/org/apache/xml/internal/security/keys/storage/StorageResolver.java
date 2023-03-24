package com.sun.org.apache.xml.internal.security.keys.storage;

import com.sun.org.apache.xml.internal.security.keys.storage.implementations.KeyStoreResolver;
import com.sun.org.apache.xml.internal.security.keys.storage.implementations.SingleCertificateResolver;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageResolver {
  static Logger log = Logger.getLogger(StorageResolver.class.getName());
  
  List _storageResolvers = null;
  
  Iterator _iterator = null;
  
  public StorageResolver() {}
  
  public StorageResolver(StorageResolverSpi paramStorageResolverSpi) {
    add(paramStorageResolverSpi);
  }
  
  public void add(StorageResolverSpi paramStorageResolverSpi) {
    if (this._storageResolvers == null)
      this._storageResolvers = new ArrayList(); 
    this._storageResolvers.add(paramStorageResolverSpi);
    this._iterator = null;
  }
  
  public StorageResolver(KeyStore paramKeyStore) {
    add(paramKeyStore);
  }
  
  public void add(KeyStore paramKeyStore) {
    try {
      add(new KeyStoreResolver(paramKeyStore));
    } catch (StorageResolverException storageResolverException) {
      log.log(Level.SEVERE, "Could not add KeyStore because of: ", storageResolverException);
    } 
  }
  
  public StorageResolver(X509Certificate paramX509Certificate) {
    add(paramX509Certificate);
  }
  
  public void add(X509Certificate paramX509Certificate) {
    add(new SingleCertificateResolver(paramX509Certificate));
  }
  
  public Iterator getIterator() {
    if (this._iterator == null) {
      if (this._storageResolvers == null)
        this._storageResolvers = new ArrayList(); 
      this._iterator = new StorageResolverIterator(this._storageResolvers.iterator());
    } 
    return this._iterator;
  }
  
  public boolean hasNext() {
    if (this._iterator == null) {
      if (this._storageResolvers == null)
        this._storageResolvers = new ArrayList(); 
      this._iterator = new StorageResolverIterator(this._storageResolvers.iterator());
    } 
    return this._iterator.hasNext();
  }
  
  public X509Certificate next() {
    return this._iterator.next();
  }
  
  static class StorageResolverIterator implements Iterator {
    Iterator _resolvers = null;
    
    public StorageResolverIterator(Iterator param1Iterator) {
      this._resolvers = param1Iterator;
    }
    
    public boolean hasNext() {
      return this._resolvers.hasNext();
    }
    
    public Object next() {
      return this._resolvers.next();
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Can't remove keys from KeyStore");
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\storage\StorageResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */