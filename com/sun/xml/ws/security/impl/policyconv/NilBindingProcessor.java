/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.EndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.ws.security.policy.SignedEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedEndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
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
/*     */ public class NilBindingProcessor
/*     */   extends BindingProcessor
/*     */ {
/*     */   public NilBindingProcessor(boolean isServer, boolean isIncoming, XWSSPolicyContainer container) {
/*  64 */     this.container = container;
/*  65 */     this.isIncoming = isIncoming;
/*  66 */     this.isServer = isServer;
/*  67 */     this.tokenProcessor = new TokenProcessor(isServer, isIncoming, this.pid);
/*     */   }
/*     */   
/*     */   public void process() throws PolicyException {
/*  71 */     this.container.setPolicyContainerMode(MessageLayout.Strict);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void protectPrimarySignature() throws PolicyException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void protectTimestamp(TimestampPolicy tp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void protectToken(WSSPolicy token) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void protectToken(WSSPolicy token, boolean ignoreSTR) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addPrimaryTargets() throws PolicyException {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean requireSC() {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EncryptionPolicy getSecondaryEncryptionPolicy() throws PolicyException {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(SupportingTokens st) throws PolicyException {
/* 112 */     SupportingTokensProcessor stp = new SupportingTokensProcessor(st, this.tokenProcessor, getBinding(), this.container, this.primarySP, this.primaryEP, this.pid);
/*     */     
/* 114 */     stp.process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(SignedSupportingTokens st) throws PolicyException {
/* 119 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(EndorsingSupportingTokens est) throws PolicyException {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(SignedEndorsingSupportingTokens est) throws PolicyException {
/* 129 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(SignedEncryptedSupportingTokens sest) throws PolicyException {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(EncryptedSupportingTokens est) throws PolicyException {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SignaturePolicy getSignaturePolicy() {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Binding getBinding() {
/* 149 */     return null;
/*     */   }
/*     */   
/*     */   protected void close() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\NilBindingProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */