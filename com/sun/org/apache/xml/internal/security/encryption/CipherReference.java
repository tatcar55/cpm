package com.sun.org.apache.xml.internal.security.encryption;

import org.w3c.dom.Attr;

public interface CipherReference {
  String getURI();
  
  Attr getURIAsAttr();
  
  Transforms getTransforms();
  
  void setTransforms(Transforms paramTransforms);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\CipherReference.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */