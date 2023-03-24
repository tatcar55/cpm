package com.sun.org.apache.xml.internal.security.utils.resolver;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Attr;

public class ResourceResolverException extends XMLSecurityException {
  private static final long serialVersionUID = 1L;
  
  Attr _uri = null;
  
  String _BaseURI;
  
  public ResourceResolverException(String paramString1, Attr paramAttr, String paramString2) {
    super(paramString1);
    this._uri = paramAttr;
    this._BaseURI = paramString2;
  }
  
  public ResourceResolverException(String paramString1, Object[] paramArrayOfObject, Attr paramAttr, String paramString2) {
    super(paramString1, paramArrayOfObject);
    this._uri = paramAttr;
    this._BaseURI = paramString2;
  }
  
  public ResourceResolverException(String paramString1, Exception paramException, Attr paramAttr, String paramString2) {
    super(paramString1, paramException);
    this._uri = paramAttr;
    this._BaseURI = paramString2;
  }
  
  public ResourceResolverException(String paramString1, Object[] paramArrayOfObject, Exception paramException, Attr paramAttr, String paramString2) {
    super(paramString1, paramArrayOfObject, paramException);
    this._uri = paramAttr;
    this._BaseURI = paramString2;
  }
  
  public void setURI(Attr paramAttr) {
    this._uri = paramAttr;
  }
  
  public Attr getURI() {
    return this._uri;
  }
  
  public void setBaseURI(String paramString) {
    this._BaseURI = paramString;
  }
  
  public String getBaseURI() {
    return this._BaseURI;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\resolver\ResourceResolverException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */