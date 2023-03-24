package com.sun.xml.rpc.sp;

import org.xml.sax.Attributes;

public interface AttributesEx extends Attributes {
  boolean isSpecified(int paramInt);
  
  String getDefault(int paramInt);
  
  String getIdAttributeName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\AttributesEx.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */