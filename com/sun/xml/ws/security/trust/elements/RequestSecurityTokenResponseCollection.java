package com.sun.xml.ws.security.trust.elements;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface RequestSecurityTokenResponseCollection extends BaseSTSResponse {
  Map<QName, String> getOtherAttributes();
  
  List<RequestSecurityTokenResponse> getRequestSecurityTokenResponses();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\RequestSecurityTokenResponseCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */