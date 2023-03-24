package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.api.security.trust.Status;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface RequestSecurityTokenResponse extends WSTrustElementBase, BaseSTSResponse {
  List<Object> getAny();
  
  String getContext();
  
  Map<QName, String> getOtherAttributes();
  
  void setSignChallengeResponse(SignChallengeResponse paramSignChallengeResponse);
  
  SignChallengeResponse getSignChallengeResponse();
  
  void setAuthenticator(Authenticator paramAuthenticator);
  
  Authenticator getAuthenticator();
  
  void setRequestedProofToken(RequestedProofToken paramRequestedProofToken);
  
  RequestedProofToken getRequestedProofToken();
  
  void setRequestedSecurityToken(RequestedSecurityToken paramRequestedSecurityToken);
  
  RequestedSecurityToken getRequestedSecurityToken();
  
  void setRequestedAttachedReference(RequestedAttachedReference paramRequestedAttachedReference);
  
  RequestedAttachedReference getRequestedAttachedReference();
  
  void setRequestedUnattachedReference(RequestedUnattachedReference paramRequestedUnattachedReference);
  
  RequestedUnattachedReference getRequestedUnattachedReference();
  
  void setRequestedTokenCancelled(RequestedTokenCancelled paramRequestedTokenCancelled);
  
  RequestedTokenCancelled getRequestedTokenCancelled();
  
  Status getStatus();
  
  void setStatus(Status paramStatus);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\RequestSecurityTokenResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */