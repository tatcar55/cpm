package com.sun.org.apache.xml.internal.security.utils.resolver;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;

public abstract class ResourceResolverSpi {
  static Logger log = Logger.getLogger(ResourceResolverSpi.class.getName());
  
  protected Map _properties = null;
  
  public abstract XMLSignatureInput engineResolve(Attr paramAttr, String paramString) throws ResourceResolverException;
  
  public void engineSetProperty(String paramString1, String paramString2) {
    if (this._properties == null)
      this._properties = new HashMap(); 
    this._properties.put(paramString1, paramString2);
  }
  
  public String engineGetProperty(String paramString) {
    return (this._properties == null) ? null : (String)this._properties.get(paramString);
  }
  
  public void engineAddProperies(Map paramMap) {
    if (paramMap != null) {
      if (this._properties == null)
        this._properties = new HashMap(); 
      this._properties.putAll(paramMap);
    } 
  }
  
  public boolean engineIsThreadSafe() {
    return false;
  }
  
  public abstract boolean engineCanResolve(Attr paramAttr, String paramString);
  
  public String[] engineGetPropertyKeys() {
    return new String[0];
  }
  
  public boolean understandsProperty(String paramString) {
    String[] arrayOfString = engineGetPropertyKeys();
    if (arrayOfString != null)
      for (byte b = 0; b < arrayOfString.length; b++) {
        if (arrayOfString[b].equals(paramString))
          return true; 
      }  
    return false;
  }
  
  public static String fixURI(String paramString) {
    paramString = paramString.replace(File.separatorChar, '/');
    if (paramString.length() >= 4) {
      char c1 = Character.toUpperCase(paramString.charAt(0));
      char c2 = paramString.charAt(1);
      char c3 = paramString.charAt(2);
      char c4 = paramString.charAt(3);
      boolean bool = ('A' <= c1 && c1 <= 'Z' && c2 == ':' && c3 == '/' && c4 != '/') ? true : false;
      if (bool)
        log.log(Level.FINE, "Found DOS filename: " + paramString); 
    } 
    if (paramString.length() >= 2) {
      char c = paramString.charAt(1);
      if (c == ':') {
        char c1 = Character.toUpperCase(paramString.charAt(0));
        if ('A' <= c1 && c1 <= 'Z')
          paramString = "/" + paramString; 
      } 
    } 
    return paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\ResourceResolverSpi.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */