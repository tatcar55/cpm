package com.sun.xml.ws.policy;

interface PolicyMapKeyHandler {
  boolean areEqual(PolicyMapKey paramPolicyMapKey1, PolicyMapKey paramPolicyMapKey2);
  
  int generateHashCode(PolicyMapKey paramPolicyMapKey);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyMapKeyHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */