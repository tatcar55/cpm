package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;

public interface ReferenceList {
  public static final int DATA_REFERENCE = 1;
  
  public static final int KEY_REFERENCE = 2;
  
  void add(Reference paramReference);
  
  void remove(Reference paramReference);
  
  int size();
  
  boolean isEmpty();
  
  Iterator getReferences();
  
  Reference newDataReference(String paramString);
  
  Reference newKeyReference(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\ReferenceList.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */