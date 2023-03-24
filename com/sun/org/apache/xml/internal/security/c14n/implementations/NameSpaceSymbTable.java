package com.sun.org.apache.xml.internal.security.c14n.implementations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

public class NameSpaceSymbTable {
  SymbMap symb = (SymbMap)initialMap.clone();
  
  int nameSpaces = 0;
  
  List level = new ArrayList(10);
  
  boolean cloned = true;
  
  static final String XMLNS = "xmlns";
  
  static final SymbMap initialMap = new SymbMap();
  
  public void getUnrenderedNodes(Collection paramCollection) {
    for (NameSpaceSymbEntry nameSpaceSymbEntry : this.symb.entrySet()) {
      if (!nameSpaceSymbEntry.rendered && nameSpaceSymbEntry.n != null) {
        nameSpaceSymbEntry = (NameSpaceSymbEntry)nameSpaceSymbEntry.clone();
        needsClone();
        this.symb.put(nameSpaceSymbEntry.prefix, nameSpaceSymbEntry);
        nameSpaceSymbEntry.lastrendered = nameSpaceSymbEntry.uri;
        nameSpaceSymbEntry.rendered = true;
        paramCollection.add(nameSpaceSymbEntry.n);
      } 
    } 
  }
  
  public void outputNodePush() {
    this.nameSpaces++;
    push();
  }
  
  public void outputNodePop() {
    this.nameSpaces--;
    pop();
  }
  
  public void push() {
    this.level.add(null);
    this.cloned = false;
  }
  
  public void pop() {
    int i = this.level.size() - 1;
    SymbMap symbMap = (SymbMap)this.level.remove(i);
    if (symbMap != null) {
      this.symb = symbMap;
      if (i == 0) {
        this.cloned = false;
      } else {
        this.cloned = (this.level.get(i - 1) != this.symb);
      } 
    } else {
      this.cloned = false;
    } 
  }
  
  final void needsClone() {
    if (!this.cloned) {
      this.level.set(this.level.size() - 1, this.symb);
      this.symb = (SymbMap)this.symb.clone();
      this.cloned = true;
    } 
  }
  
  public Attr getMapping(String paramString) {
    NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
    if (nameSpaceSymbEntry == null)
      return null; 
    if (nameSpaceSymbEntry.rendered)
      return null; 
    nameSpaceSymbEntry = (NameSpaceSymbEntry)nameSpaceSymbEntry.clone();
    needsClone();
    this.symb.put(paramString, nameSpaceSymbEntry);
    nameSpaceSymbEntry.rendered = true;
    nameSpaceSymbEntry.level = this.nameSpaces;
    nameSpaceSymbEntry.lastrendered = nameSpaceSymbEntry.uri;
    return nameSpaceSymbEntry.n;
  }
  
  public Attr getMappingWithoutRendered(String paramString) {
    NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
    return (nameSpaceSymbEntry == null) ? null : (nameSpaceSymbEntry.rendered ? null : nameSpaceSymbEntry.n);
  }
  
  public boolean addMapping(String paramString1, String paramString2, Attr paramAttr) {
    NameSpaceSymbEntry nameSpaceSymbEntry1 = this.symb.get(paramString1);
    if (nameSpaceSymbEntry1 != null && paramString2.equals(nameSpaceSymbEntry1.uri))
      return false; 
    NameSpaceSymbEntry nameSpaceSymbEntry2 = new NameSpaceSymbEntry(paramString2, paramAttr, false, paramString1);
    needsClone();
    this.symb.put(paramString1, nameSpaceSymbEntry2);
    if (nameSpaceSymbEntry1 != null) {
      nameSpaceSymbEntry2.lastrendered = nameSpaceSymbEntry1.lastrendered;
      if (nameSpaceSymbEntry1.lastrendered != null && nameSpaceSymbEntry1.lastrendered.equals(paramString2))
        nameSpaceSymbEntry2.rendered = true; 
    } 
    return true;
  }
  
  public Node addMappingAndRender(String paramString1, String paramString2, Attr paramAttr) {
    NameSpaceSymbEntry nameSpaceSymbEntry1 = this.symb.get(paramString1);
    if (nameSpaceSymbEntry1 != null && paramString2.equals(nameSpaceSymbEntry1.uri)) {
      if (!nameSpaceSymbEntry1.rendered) {
        nameSpaceSymbEntry1 = (NameSpaceSymbEntry)nameSpaceSymbEntry1.clone();
        needsClone();
        this.symb.put(paramString1, nameSpaceSymbEntry1);
        nameSpaceSymbEntry1.lastrendered = paramString2;
        nameSpaceSymbEntry1.rendered = true;
        return nameSpaceSymbEntry1.n;
      } 
      return null;
    } 
    NameSpaceSymbEntry nameSpaceSymbEntry2 = new NameSpaceSymbEntry(paramString2, paramAttr, true, paramString1);
    nameSpaceSymbEntry2.lastrendered = paramString2;
    needsClone();
    this.symb.put(paramString1, nameSpaceSymbEntry2);
    if (nameSpaceSymbEntry1 != null && nameSpaceSymbEntry1.lastrendered != null && nameSpaceSymbEntry1.lastrendered.equals(paramString2)) {
      nameSpaceSymbEntry2.rendered = true;
      return null;
    } 
    return nameSpaceSymbEntry2.n;
  }
  
  public int getLevel() {
    return this.level.size();
  }
  
  public void removeMapping(String paramString) {
    NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
    if (nameSpaceSymbEntry != null) {
      needsClone();
      this.symb.put(paramString, null);
    } 
  }
  
  public void removeMappingIfNotRender(String paramString) {
    NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
    if (nameSpaceSymbEntry != null && !nameSpaceSymbEntry.rendered) {
      needsClone();
      this.symb.put(paramString, null);
    } 
  }
  
  public boolean removeMappingIfRender(String paramString) {
    NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
    if (nameSpaceSymbEntry != null && nameSpaceSymbEntry.rendered) {
      needsClone();
      this.symb.put(paramString, null);
    } 
    return false;
  }
  
  static {
    NameSpaceSymbEntry nameSpaceSymbEntry = new NameSpaceSymbEntry("", null, true, "xmlns");
    nameSpaceSymbEntry.lastrendered = "";
    initialMap.put("xmlns", nameSpaceSymbEntry);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\NameSpaceSymbTable.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */