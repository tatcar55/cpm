package com.sun.xml.wss.saml;

public interface Subject {
  SubjectConfirmation getSubjectConfirmation();
  
  NameIdentifier getNameIdentifier();
  
  NameID getNameId();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\Subject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */