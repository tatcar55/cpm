package com.sun.xml.ws.security.policy;

import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.security.addressing.policy.Address;
import org.w3c.dom.Element;

public interface Issuer {
  Address getAddress();
  
  String getPortType();
  
  PolicyAssertion getServiceName();
  
  PolicyAssertion getReferenceParameters();
  
  PolicyAssertion getReferenceProperties();
  
  Element getIdentity();
  
  Address getMetadataAddress();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\Issuer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */