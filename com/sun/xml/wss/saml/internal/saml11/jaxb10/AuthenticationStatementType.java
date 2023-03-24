package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.Calendar;
import java.util.List;

public interface AuthenticationStatementType extends SubjectStatementAbstractType {
  List getAuthorityBinding();
  
  Calendar getAuthenticationInstant();
  
  void setAuthenticationInstant(Calendar paramCalendar);
  
  SubjectLocalityType getSubjectLocality();
  
  void setSubjectLocality(SubjectLocalityType paramSubjectLocalityType);
  
  String getAuthenticationMethod();
  
  void setAuthenticationMethod(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\AuthenticationStatementType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */