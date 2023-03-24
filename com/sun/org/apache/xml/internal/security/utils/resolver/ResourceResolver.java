package com.sun.org.apache.xml.internal.security.utils.resolver;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;

public class ResourceResolver {
  static Logger log = Logger.getLogger(ResourceResolver.class.getName());
  
  static boolean _alreadyInitialized = false;
  
  static List _resolverVector = null;
  
  static boolean allThreadSafeInList = true;
  
  protected ResourceResolverSpi _resolverSpi = null;
  
  private ResourceResolver(String paramString) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    this._resolverSpi = (ResourceResolverSpi)Class.forName(paramString).newInstance();
  }
  
  public ResourceResolver(ResourceResolverSpi paramResourceResolverSpi) {
    this._resolverSpi = paramResourceResolverSpi;
  }
  
  public static final ResourceResolver getInstance(Attr paramAttr, String paramString) throws ResourceResolverException {
    int i = _resolverVector.size();
    for (byte b = 0; b < i; b++) {
      ResourceResolver resourceResolver1 = _resolverVector.get(b);
      ResourceResolver resourceResolver2 = null;
      try {
        resourceResolver2 = (allThreadSafeInList || resourceResolver1._resolverSpi.engineIsThreadSafe()) ? resourceResolver1 : new ResourceResolver((ResourceResolverSpi)resourceResolver1._resolverSpi.getClass().newInstance());
      } catch (InstantiationException instantiationException) {
        throw new ResourceResolverException("", instantiationException, paramAttr, paramString);
      } catch (IllegalAccessException illegalAccessException) {
        throw new ResourceResolverException("", illegalAccessException, paramAttr, paramString);
      } 
      log.log(Level.FINE, "check resolvability by class " + resourceResolver1._resolverSpi.getClass().getName());
      if (resourceResolver1 != null && resourceResolver2.canResolve(paramAttr, paramString)) {
        if (b != 0) {
          List list = (List)((ArrayList)_resolverVector).clone();
          list.remove(b);
          list.add(0, resourceResolver1);
          _resolverVector = list;
        } 
        return resourceResolver2;
      } 
    } 
    Object[] arrayOfObject = { (paramAttr != null) ? paramAttr.getNodeValue() : "null", paramString };
    throw new ResourceResolverException("utils.resolver.noClass", arrayOfObject, paramAttr, paramString);
  }
  
  public static final ResourceResolver getInstance(Attr paramAttr, String paramString, List paramList) throws ResourceResolverException {
    log.log(Level.FINE, "I was asked to create a ResourceResolver and got " + ((paramList == null) ? 0 : paramList.size()));
    log.log(Level.FINE, " extra resolvers to my existing " + _resolverVector.size() + " system-wide resolvers");
    int i = 0;
    if (paramList != null && (i = paramList.size()) > 0)
      for (byte b = 0; b < i; b++) {
        ResourceResolver resourceResolver = paramList.get(b);
        if (resourceResolver != null) {
          String str = resourceResolver._resolverSpi.getClass().getName();
          log.log(Level.FINE, "check resolvability by class " + str);
          if (resourceResolver.canResolve(paramAttr, paramString))
            return resourceResolver; 
        } 
      }  
    return getInstance(paramAttr, paramString);
  }
  
  public static void init() {
    if (!_alreadyInitialized) {
      _resolverVector = new ArrayList(10);
      _alreadyInitialized = true;
    } 
  }
  
  public static void register(String paramString) {
    register(paramString, false);
  }
  
  public static void registerAtStart(String paramString) {
    register(paramString, true);
  }
  
  private static void register(String paramString, boolean paramBoolean) {
    try {
      ResourceResolver resourceResolver = new ResourceResolver(paramString);
      if (paramBoolean) {
        _resolverVector.add(0, resourceResolver);
        log.log(Level.FINE, "registered resolver");
      } else {
        _resolverVector.add(resourceResolver);
      } 
      if (!resourceResolver._resolverSpi.engineIsThreadSafe())
        allThreadSafeInList = false; 
    } catch (Exception exception) {
      log.log(Level.WARNING, "Error loading resolver " + paramString + " disabling it");
    } catch (NoClassDefFoundError noClassDefFoundError) {
      log.log(Level.WARNING, "Error loading resolver " + paramString + " disabling it");
    } 
  }
  
  public static XMLSignatureInput resolveStatic(Attr paramAttr, String paramString) throws ResourceResolverException {
    ResourceResolver resourceResolver = getInstance(paramAttr, paramString);
    return resourceResolver.resolve(paramAttr, paramString);
  }
  
  public XMLSignatureInput resolve(Attr paramAttr, String paramString) throws ResourceResolverException {
    return this._resolverSpi.engineResolve(paramAttr, paramString);
  }
  
  public void setProperty(String paramString1, String paramString2) {
    this._resolverSpi.engineSetProperty(paramString1, paramString2);
  }
  
  public String getProperty(String paramString) {
    return this._resolverSpi.engineGetProperty(paramString);
  }
  
  public void addProperties(Map paramMap) {
    this._resolverSpi.engineAddProperies(paramMap);
  }
  
  public String[] getPropertyKeys() {
    return this._resolverSpi.engineGetPropertyKeys();
  }
  
  public boolean understandsProperty(String paramString) {
    return this._resolverSpi.understandsProperty(paramString);
  }
  
  private boolean canResolve(Attr paramAttr, String paramString) {
    return this._resolverSpi.engineCanResolve(paramAttr, paramString);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\ResourceResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */