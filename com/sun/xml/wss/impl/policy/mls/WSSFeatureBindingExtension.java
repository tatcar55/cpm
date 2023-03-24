/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSSFeatureBindingExtension
/*     */   extends WSSPolicy
/*     */ {
/*     */   public MLSPolicy newUsernameTokenFeatureBinding() throws PolicyGenerationException {
/*  76 */     if (isReadOnly()) {
/*  77 */       throw new RuntimeException("Can not create a feature binding of UsernameToken type for ReadOnly " + this._policyIdentifier);
/*     */     }
/*     */ 
/*     */     
/*  81 */     if ("SignaturePolicy" == this._policyIdentifier || "EncryptionPolicy" == this._policyIdentifier)
/*     */     {
/*  83 */       throw new PolicyGenerationException("Can not create a feature binding of UsernameToken type for " + this._policyIdentifier);
/*     */     }
/*     */ 
/*     */     
/*  87 */     this._featureBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/*  88 */     return this._featureBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy newX509CertificateFeatureBinding() throws PolicyGenerationException {
/* 101 */     if (isReadOnly()) {
/* 102 */       throw new RuntimeException("Can not create a feature binding of X509Certificate type for ReadOnly " + this._policyIdentifier);
/*     */     }
/*     */ 
/*     */     
/* 106 */     if ("SignaturePolicy" == this._policyIdentifier || "EncryptionPolicy" == this._policyIdentifier)
/*     */     {
/* 108 */       throw new PolicyGenerationException("Can not create a feature binding of X509Certificate type for " + this._policyIdentifier);
/*     */     }
/*     */ 
/*     */     
/* 112 */     this._featureBinding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 113 */     return this._featureBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy newSAMLAssertionFeatureBinding() throws PolicyGenerationException {
/* 125 */     if (isReadOnly()) {
/* 126 */       throw new RuntimeException("Can not create a feature binding of SAMLAssertion type for ReadOnly " + this._policyIdentifier);
/*     */     }
/*     */ 
/*     */     
/* 130 */     if ("SignaturePolicy" == this._policyIdentifier || "EncryptionPolicy" == this._policyIdentifier)
/*     */     {
/* 132 */       throw new PolicyGenerationException("Can not create a feature binding of SAMLAssertion type for " + this._policyIdentifier);
/*     */     }
/*     */ 
/*     */     
/* 136 */     this._featureBinding = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/* 137 */     return this._featureBinding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\WSSFeatureBindingExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */