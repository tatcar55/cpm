package com.sun.xml.wss.core;

import com.sun.xml.wss.XWSSecurityException;
import javax.xml.soap.SOAPElement;

public interface SecurityHeaderBlock extends SOAPElement {
  String getId();
  
  SOAPElement getAsSoapElement() throws XWSSecurityException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SecurityHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */