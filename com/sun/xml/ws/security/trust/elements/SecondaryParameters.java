package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.api.security.trust.Claims;
import java.util.List;

public interface SecondaryParameters extends WSTrustElementBase {
  List<Object> getAny();
  
  void setClaims(Claims paramClaims);
  
  Claims getClaims();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\SecondaryParameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */