package com.sun.org.apache.xml.internal.security.c14n.implementations;

import org.w3c.dom.Attr;

class NameSpaceSymbEntry implements Cloneable {
  int level = 0;
  
  String prefix;
  
  String uri;
  
  String lastrendered = null;
  
  boolean rendered = false;
  
  Attr n;
  
  NameSpaceSymbEntry(String paramString1, Attr paramAttr, boolean paramBoolean, String paramString2) {
    this.uri = paramString1;
    this.rendered = paramBoolean;
    this.n = paramAttr;
    this.prefix = paramString2;
  }
  
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\NameSpaceSymbEntry.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */