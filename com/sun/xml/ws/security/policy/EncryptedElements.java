package com.sun.xml.ws.security.policy;

import java.util.Iterator;

public interface EncryptedElements extends Target {
  String getXPathVersion();
  
  Iterator<String> getTargets();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\EncryptedElements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */