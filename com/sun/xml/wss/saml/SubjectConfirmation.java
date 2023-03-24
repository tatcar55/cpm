package com.sun.xml.wss.saml;

import java.util.List;

public interface SubjectConfirmation {
  List<String> getConfirmationMethod();
  
  Object getSubjectConfirmationDataForSAML11();
  
  SubjectConfirmationData getSubjectConfirmationDataForSAML20();
  
  NameID getNameId();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\SubjectConfirmation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */