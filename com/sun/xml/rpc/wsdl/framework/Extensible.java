package com.sun.xml.rpc.wsdl.framework;

import java.util.Iterator;

public interface Extensible extends Elemental {
  void addExtension(Extension paramExtension);
  
  Iterator extensions();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\Extensible.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */