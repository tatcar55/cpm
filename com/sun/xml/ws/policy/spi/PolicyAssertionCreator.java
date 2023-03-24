package com.sun.xml.ws.policy.spi;

import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import java.util.Collection;

public interface PolicyAssertionCreator {
  String[] getSupportedDomainNamespaceURIs();
  
  PolicyAssertion createAssertion(AssertionData paramAssertionData, Collection<PolicyAssertion> paramCollection, AssertionSet paramAssertionSet, PolicyAssertionCreator paramPolicyAssertionCreator) throws AssertionCreationException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\spi\PolicyAssertionCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */