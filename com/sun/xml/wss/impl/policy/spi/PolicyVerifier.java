package com.sun.xml.wss.impl.policy.spi;

import com.sun.xml.wss.impl.PolicyViolationException;
import com.sun.xml.wss.impl.policy.SecurityPolicy;

public interface PolicyVerifier {
  public static final String POLICY_ALTERNATIVE_ID = "policy-alternative-id";
  
  void verifyPolicy(SecurityPolicy paramSecurityPolicy1, SecurityPolicy paramSecurityPolicy2) throws PolicyViolationException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\spi\PolicyVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */