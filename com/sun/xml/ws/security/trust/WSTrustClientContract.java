package com.sun.xml.ws.security.trust;

import com.sun.xml.ws.api.security.trust.WSTrustException;
import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
import com.sun.xml.ws.security.IssuedTokenContext;
import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
import java.net.URI;

public interface WSTrustClientContract {
  void handleRSTR(BaseSTSRequest paramBaseSTSRequest, BaseSTSResponse paramBaseSTSResponse, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  BaseSTSResponse handleRSTRForNegotiatedExchange(BaseSTSRequest paramBaseSTSRequest, BaseSTSResponse paramBaseSTSResponse, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  BaseSTSResponse createRSTRForClientInitiatedIssuedTokenContext(AppliesTo paramAppliesTo, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  boolean containsChallenge(RequestSecurityTokenResponse paramRequestSecurityTokenResponse);
  
  URI getComputedKeyAlgorithmFromProofToken(RequestSecurityTokenResponse paramRequestSecurityTokenResponse);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\WSTrustClientContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */