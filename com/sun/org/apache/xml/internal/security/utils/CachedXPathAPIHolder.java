package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xpath.internal.CachedXPathAPI;
import org.w3c.dom.Document;

public class CachedXPathAPIHolder {
  static ThreadLocal local = new ThreadLocal();
  
  static ThreadLocal localDoc = new ThreadLocal();
  
  public static void setDoc(Document paramDocument) {
    if (localDoc.get() != paramDocument) {
      CachedXPathAPI cachedXPathAPI = local.get();
      if (cachedXPathAPI == null) {
        cachedXPathAPI = new CachedXPathAPI();
        local.set(cachedXPathAPI);
        localDoc.set(paramDocument);
        return;
      } 
      cachedXPathAPI.getXPathContext().reset();
      localDoc.set(paramDocument);
    } 
  }
  
  public static CachedXPathAPI getCachedXPathAPI() {
    CachedXPathAPI cachedXPathAPI = local.get();
    if (cachedXPathAPI == null) {
      cachedXPathAPI = new CachedXPathAPI();
      local.set(cachedXPathAPI);
      localDoc.set(null);
    } 
    return cachedXPathAPI;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\CachedXPathAPIHolder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */