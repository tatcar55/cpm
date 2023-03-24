/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicyGenerator;
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
/*     */ public class WSSPolicyGenerator
/*     */   implements SecurityPolicyGenerator
/*     */ {
/*  62 */   MessagePolicy configuration = new MessagePolicy();
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
/*     */   public MLSPolicy newMLSPolicy() throws PolicyGenerationException {
/*  75 */     throw new PolicyGenerationException("Unsupported Operation");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimestampPolicy newTimestampPolicy() throws PolicyGenerationException {
/*  84 */     TimestampPolicy policy = new TimestampPolicy();
/*     */     
/*  86 */     this.configuration.append((SecurityPolicy)policy);
/*     */     
/*  88 */     return policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignaturePolicy newSignaturePolicy() throws PolicyGenerationException {
/*  97 */     SignaturePolicy policy = new SignaturePolicy();
/*     */     
/*  99 */     this.configuration.append((SecurityPolicy)policy);
/*     */     
/* 101 */     return policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionPolicy newEncryptionPolicy() throws PolicyGenerationException {
/* 110 */     EncryptionPolicy policy = new EncryptionPolicy();
/*     */     
/* 112 */     this.configuration.append((SecurityPolicy)policy);
/*     */     
/* 114 */     return policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationTokenPolicy newAuthenticationTokenPolicy() throws PolicyGenerationException {
/* 123 */     AuthenticationTokenPolicy policy = new AuthenticationTokenPolicy();
/*     */     
/* 125 */     this.configuration.append((SecurityPolicy)policy);
/*     */     
/* 127 */     return policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityPolicy configuration() throws PolicyGenerationException {
/* 136 */     return this.configuration;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\WSSPolicyGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */