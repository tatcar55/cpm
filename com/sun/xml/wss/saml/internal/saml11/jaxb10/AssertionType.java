package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

public interface AssertionType {
  AdviceType getAdvice();
  
  void setAdvice(AdviceType paramAdviceType);
  
  SignatureType getSignature();
  
  void setSignature(SignatureType paramSignatureType);
  
  List getStatementOrSubjectStatementOrAuthenticationStatement();
  
  ConditionsType getConditions();
  
  void setConditions(ConditionsType paramConditionsType);
  
  String getIssuer();
  
  void setIssuer(String paramString);
  
  BigInteger getMajorVersion();
  
  void setMajorVersion(BigInteger paramBigInteger);
  
  Calendar getIssueInstant();
  
  void setIssueInstant(Calendar paramCalendar);
  
  BigInteger getMinorVersion();
  
  void setMinorVersion(BigInteger paramBigInteger);
  
  String getAssertionID();
  
  void setAssertionID(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\AssertionType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */