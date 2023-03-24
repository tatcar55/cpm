package com.sun.xml.wss.saml;

import java.util.Date;
import java.util.List;

public interface AuthenticationStatement {
  String getSubjectLocalityIPAddress();
  
  String getSubjectLocalityDNSAddress();
  
  List<AuthorityBinding> getAuthorityBindingList();
  
  Date getAuthenticationInstantDate();
  
  String getAuthenticationMethod();
  
  Subject getSubject();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\AuthenticationStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */