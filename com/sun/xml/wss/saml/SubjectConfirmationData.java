package com.sun.xml.wss.saml;

import java.util.Date;

public interface SubjectConfirmationData {
  String getAddress();
  
  String getInResponseTo();
  
  Date getNotBeforeDate();
  
  Date getNotOnOrAfterDate();
  
  String getRecipient();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\SubjectConfirmationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */