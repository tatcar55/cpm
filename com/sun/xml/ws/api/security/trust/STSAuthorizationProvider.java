package com.sun.xml.ws.api.security.trust;

import javax.security.auth.Subject;

public interface STSAuthorizationProvider {
  boolean isAuthorized(Subject paramSubject, String paramString1, String paramString2, String paramString3);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\STSAuthorizationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */