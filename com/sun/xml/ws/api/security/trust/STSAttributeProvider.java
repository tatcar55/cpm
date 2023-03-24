package com.sun.xml.ws.api.security.trust;

import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;

public interface STSAttributeProvider {
  public static final String NAME_IDENTIFIER = "NameID";
  
  Map<QName, List<String>> getClaimedAttributes(Subject paramSubject, String paramString1, String paramString2, Claims paramClaims);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\STSAttributeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */