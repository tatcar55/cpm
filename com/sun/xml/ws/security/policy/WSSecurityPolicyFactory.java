/*    */ package com.sun.xml.ws.security.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WSSecurityPolicyFactory
/*    */ {
/*    */   public static WSSecurityPolicyFactory getInstance() {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public static WSSecurityPolicyFactory getInstance(String namespaceURI) {
/* 62 */     throw new UnsupportedOperationException("This method is not supported");
/*    */   }
/*    */   
/*    */   public abstract EncryptedParts createEncryptedParts();
/*    */   
/*    */   public abstract SignedParts createSignedParts();
/*    */   
/*    */   public abstract SpnegoContextToken createSpnegoContextToken();
/*    */   
/*    */   public abstract TransportBinding createTransportBinding();
/*    */   
/*    */   public abstract TransportToken createTransportToken();
/*    */   
/*    */   public abstract AlgorithmSuite createAlgorithmSuite();
/*    */   
/*    */   public abstract UserNameToken createUsernameToken();
/*    */   
/*    */   public abstract SymmetricBinding createSymmetricBinding();
/*    */   
/*    */   public abstract AsymmetricBinding createASymmetricBinding();
/*    */   
/*    */   public abstract X509Token createX509Token();
/*    */   
/*    */   public abstract EndorsingSupportingTokens createEndorsingSupportingToken();
/*    */   
/*    */   public abstract IssuedToken createIssuedToken();
/*    */   
/*    */   public abstract PolicyAssertion createSecurityAssertion(QName paramQName);
/*    */   
/*    */   public abstract PolicyAssertion createSecurityAssertion(QName paramQName, ClassLoader paramClassLoader);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\WSSecurityPolicyFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */