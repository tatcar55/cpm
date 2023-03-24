package com.sun.xml.wss.saml;

import java.util.Date;

public interface AuthnStatement {
  Date getAuthnInstantDate();
  
  String getSessionIndex();
  
  Date getSessionNotOnOrAfterDate();
  
  String getSubjectLocalityAddress();
  
  String getSubjectLocalityDNSName();
  
  String getAuthnContextClassRef();
  
  String getAuthenticatingAuthority();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\AuthnStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */