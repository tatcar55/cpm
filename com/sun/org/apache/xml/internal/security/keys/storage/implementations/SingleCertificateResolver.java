package com.sun.org.apache.xml.internal.security.keys.storage.implementations;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;
import java.security.cert.X509Certificate;
import java.util.Iterator;

public class SingleCertificateResolver extends StorageResolverSpi {
  X509Certificate _certificate = null;
  
  Iterator _iterator = null;
  
  public SingleCertificateResolver(X509Certificate paramX509Certificate) {
    this._certificate = paramX509Certificate;
    this._iterator = new InternalIterator(this._certificate);
  }
  
  public Iterator getIterator() {
    return this._iterator;
  }
  
  static class InternalIterator implements Iterator {
    boolean _alreadyReturned = false;
    
    X509Certificate _certificate = null;
    
    public InternalIterator(X509Certificate param1X509Certificate) {
      this._certificate = param1X509Certificate;
    }
    
    public boolean hasNext() {
      return !this._alreadyReturned;
    }
    
    public Object next() {
      this._alreadyReturned = true;
      return this._certificate;
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Can't remove keys from KeyStore");
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\storage\implementations\SingleCertificateResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */