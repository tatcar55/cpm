package com.sun.xml.wss.saml;

import java.util.List;

public interface AuthorizationDecisionStatement {
  List<Action> getActionList();
  
  Evidence getEvidence();
  
  String getDecisionValue();
  
  String getResource();
  
  Subject getSubject();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\AuthorizationDecisionStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */