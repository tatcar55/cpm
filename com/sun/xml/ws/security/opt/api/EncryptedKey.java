package com.sun.xml.ws.security.opt.api;

import com.sun.xml.security.core.xenc.ReferenceList;
import java.security.Key;

public interface EncryptedKey {
  ReferenceList getReferenceList();
  
  void setReferenceList(ReferenceList paramReferenceList);
  
  Key getKey();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\EncryptedKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */