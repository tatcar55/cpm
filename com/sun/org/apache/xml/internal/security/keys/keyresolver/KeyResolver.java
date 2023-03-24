package com.sun.org.apache.xml.internal.security.keys.keyresolver;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.w3c.dom.Element;

public class KeyResolver {
  static Logger log = Logger.getLogger(KeyResolver.class.getName());
  
  static boolean _alreadyInitialized = false;
  
  static List _resolverVector = null;
  
  protected KeyResolverSpi _resolverSpi = null;
  
  protected StorageResolver _storage = null;
  
  private KeyResolver(String paramString) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    this._resolverSpi = (KeyResolverSpi)Class.forName(paramString).newInstance();
    this._resolverSpi.setGlobalResolver(true);
  }
  
  public static int length() {
    return _resolverVector.size();
  }
  
  public static void hit(Iterator paramIterator) {
    ResolverIterator resolverIterator = (ResolverIterator)paramIterator;
    int i = resolverIterator.i;
    if (i != 1 && resolverIterator.res == _resolverVector) {
      List list = (List)((ArrayList)_resolverVector).clone();
      Object object = list.remove(i - 1);
      list.add(0, object);
      _resolverVector = list;
    } 
  }
  
  public static final X509Certificate getX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    List list = _resolverVector;
    for (byte b = 0; b < list.size(); b++) {
      KeyResolver keyResolver = list.get(b);
      if (keyResolver == null) {
        Object[] arrayOfObject1 = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
        throw new KeyResolverException("utils.resolver.noClass", arrayOfObject1);
      } 
      log.log(Level.FINE, "check resolvability by class " + keyResolver.getClass());
      X509Certificate x509Certificate = keyResolver.resolveX509Certificate(paramElement, paramString, paramStorageResolver);
      if (x509Certificate != null)
        return x509Certificate; 
    } 
    Object[] arrayOfObject = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
    throw new KeyResolverException("utils.resolver.noClass", arrayOfObject);
  }
  
  public static final PublicKey getPublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    List list = _resolverVector;
    for (byte b = 0; b < list.size(); b++) {
      KeyResolver keyResolver = list.get(b);
      if (keyResolver == null) {
        Object[] arrayOfObject1 = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
        throw new KeyResolverException("utils.resolver.noClass", arrayOfObject1);
      } 
      log.log(Level.FINE, "check resolvability by class " + keyResolver.getClass());
      PublicKey publicKey = keyResolver.resolvePublicKey(paramElement, paramString, paramStorageResolver);
      if (publicKey != null) {
        if (b != 0 && list == _resolverVector) {
          list = (List)((ArrayList)_resolverVector).clone();
          KeyResolver keyResolver1 = (KeyResolver)list.remove(b);
          list.add(0, keyResolver1);
          _resolverVector = list;
        } 
        return publicKey;
      } 
    } 
    Object[] arrayOfObject = { (paramElement != null && paramElement.getNodeType() == 1) ? paramElement.getTagName() : "null" };
    throw new KeyResolverException("utils.resolver.noClass", arrayOfObject);
  }
  
  public static void init() {
    if (!_alreadyInitialized) {
      _resolverVector = new ArrayList(10);
      _alreadyInitialized = true;
    } 
  }
  
  public static void register(String paramString) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    _resolverVector.add(new KeyResolver(paramString));
  }
  
  public static void registerAtStart(String paramString) {
    _resolverVector.add(0, paramString);
  }
  
  public PublicKey resolvePublicKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    return this._resolverSpi.engineLookupAndResolvePublicKey(paramElement, paramString, paramStorageResolver);
  }
  
  public X509Certificate resolveX509Certificate(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    return this._resolverSpi.engineLookupResolveX509Certificate(paramElement, paramString, paramStorageResolver);
  }
  
  public SecretKey resolveSecretKey(Element paramElement, String paramString, StorageResolver paramStorageResolver) throws KeyResolverException {
    return this._resolverSpi.engineLookupAndResolveSecretKey(paramElement, paramString, paramStorageResolver);
  }
  
  public void setProperty(String paramString1, String paramString2) {
    this._resolverSpi.engineSetProperty(paramString1, paramString2);
  }
  
  public String getProperty(String paramString) {
    return this._resolverSpi.engineGetProperty(paramString);
  }
  
  public boolean understandsProperty(String paramString) {
    return this._resolverSpi.understandsProperty(paramString);
  }
  
  public String resolverClassName() {
    return this._resolverSpi.getClass().getName();
  }
  
  public static Iterator iterator() {
    return new ResolverIterator(_resolverVector);
  }
  
  static class ResolverIterator implements Iterator {
    List res;
    
    Iterator it;
    
    int i;
    
    public ResolverIterator(List param1List) {
      this.res = param1List;
      this.it = this.res.iterator();
    }
    
    public boolean hasNext() {
      return this.it.hasNext();
    }
    
    public Object next() {
      this.i++;
      KeyResolver keyResolver = this.it.next();
      if (keyResolver == null)
        throw new RuntimeException("utils.resolver.noClass"); 
      return keyResolver._resolverSpi;
    }
    
    public void remove() {}
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\keyresolver\KeyResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */