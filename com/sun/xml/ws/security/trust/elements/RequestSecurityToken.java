package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.api.security.trust.Claims;
import java.net.URI;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface RequestSecurityToken extends WSTrustElementBase, BaseSTSRequest {
  List<Object> getAny();
  
  String getContext();
  
  Map<QName, String> getOtherAttributes();
  
  URI getRequestType();
  
  void setRequestType(URI paramURI);
  
  void setClaims(Claims paramClaims);
  
  Claims getClaims();
  
  void setParticipants(Participants paramParticipants);
  
  void setValidateTarget(ValidateTarget paramValidateTarget);
  
  void setRenewTarget(RenewTarget paramRenewTarget);
  
  void setCancelTarget(CancelTarget paramCancelTarget);
  
  Participants getParticipants();
  
  RenewTarget getRenewTarget();
  
  CancelTarget getCancelTarget();
  
  ValidateTarget getValidateTarget();
  
  void setSecondaryParameters(SecondaryParameters paramSecondaryParameters);
  
  SecondaryParameters getSecondaryParameters();
  
  List<Object> getExtensionElements();
  
  void setActAs(ActAs paramActAs);
  
  ActAs getActAs();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\RequestSecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */