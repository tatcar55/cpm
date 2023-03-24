package com.sun.xml.wss.saml;

import javax.xml.namespace.QName;

public interface AuthorityBinding {
  QName getAuthorityKind();
  
  String getBinding();
  
  String getLocation();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\AuthorityBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */