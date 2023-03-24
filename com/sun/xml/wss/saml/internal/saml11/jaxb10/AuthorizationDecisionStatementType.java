package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;

public interface AuthorizationDecisionStatementType extends SubjectStatementAbstractType {
  String getResource();
  
  void setResource(String paramString);
  
  String getDecision();
  
  void setDecision(String paramString);
  
  List getAction();
  
  EvidenceType getEvidence();
  
  void setEvidence(EvidenceType paramEvidenceType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\AuthorizationDecisionStatementType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */