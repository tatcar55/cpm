/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.impl.policy.Constants;
/*     */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
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
/*     */ public class AsymmetricBindingProcessor
/*     */   extends BindingProcessor
/*     */ {
/*     */   private final AsymmetricBinding binding;
/*     */   
/*     */   public AsymmetricBindingProcessor(AsymmetricBinding asBinding, XWSSPolicyContainer container, boolean isServer, boolean isIncoming, Vector<SignedParts> signedParts, Vector<EncryptedParts> encryptedParts, Vector<SignedElements> signedElements, Vector<EncryptedElements> encryptedElements) {
/*  70 */     this.binding = asBinding;
/*  71 */     this.container = container;
/*  72 */     this.isServer = isServer;
/*  73 */     this.isIncoming = isIncoming;
/*  74 */     this.protectionOrder = this.binding.getProtectionOrder();
/*  75 */     this.tokenProcessor = new TokenProcessor(isServer, isIncoming, this.pid);
/*  76 */     this.iAP = new IntegrityAssertionProcessor(this.binding.getAlgorithmSuite(), this.binding.isSignContent());
/*  77 */     this.eAP = new EncryptionAssertionProcessor(this.binding.getAlgorithmSuite(), false);
/*  78 */     this.signedParts = signedParts;
/*  79 */     this.signedElements = signedElements;
/*  80 */     this.encryptedElements = encryptedElements;
/*  81 */     this.encryptedParts = encryptedParts;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process() throws PolicyException {
/*  87 */     Token st = getSignatureToken();
/*  88 */     Token et = getEncryptionToken();
/*  89 */     if (st != null) {
/*  90 */       this.primarySP = new SignaturePolicy();
/*  91 */       this.primarySP.setUUID(this.pid.generateID());
/*  92 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/*  93 */         Constants.logger.log(Level.FINEST, "ID of Primary signature policy is " + this.primarySP.getUUID());
/*     */       }
/*  95 */       this.tokenProcessor.addKeyBinding((Binding)this.binding, (WSSPolicy)this.primarySP, st, true);
/*  96 */       SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/*     */       
/*  98 */       SecurityPolicyUtil.setCanonicalizationMethod(spFB, this.binding.getAlgorithmSuite());
/*  99 */       spFB.isPrimarySignature(true);
/*     */     } 
/* 101 */     if (et != null) {
/* 102 */       this.primaryEP = new EncryptionPolicy();
/* 103 */       this.primaryEP.setUUID(this.pid.generateID());
/* 104 */       this.tokenProcessor.addKeyBinding((Binding)this.binding, (WSSPolicy)this.primaryEP, et, false);
/* 105 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/* 106 */         Constants.logger.log(Level.FINEST, "ID of Encryption policy is " + this.primaryEP.getUUID());
/*     */       }
/*     */     } 
/* 109 */     if (this.protectionOrder == "SignBeforeEncrypting") {
/* 110 */       this.container.insert((SecurityPolicy)this.primarySP);
/*     */     } else {
/* 112 */       this.container.insert((SecurityPolicy)this.primaryEP);
/* 113 */       this.container.insert((SecurityPolicy)this.primarySP);
/*     */     } 
/*     */     
/* 116 */     addPrimaryTargets();
/* 117 */     if (this.foundEncryptTargets && this.binding.getSignatureProtection()) {
/* 118 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/* 119 */         Constants.logger.log(Level.FINEST, "PrimarySignature will be Encrypted");
/*     */       }
/* 121 */       protectPrimarySignature();
/*     */     } 
/* 123 */     if (this.binding.isIncludeTimeStamp()) {
/* 124 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/* 125 */         Constants.logger.log(Level.FINEST, "Timestamp header will be added to the message and will be Integrity protected ");
/*     */       }
/* 127 */       TimestampPolicy tp = new TimestampPolicy();
/* 128 */       tp.setUUID(this.pid.generateID());
/* 129 */       this.container.insert((SecurityPolicy)tp);
/* 130 */       if (!this.binding.isDisableTimestampSigning()) {
/* 131 */         protectTimestamp(tp);
/*     */       }
/*     */     } 
/* 134 */     if (this.binding.getTokenProtection()) {
/* 135 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/* 136 */         Constants.logger.log(Level.FINEST, "Token reference by primary signature with ID " + this.primarySP.getUUID() + " will be Integrity protected");
/*     */       }
/* 138 */       if (this.primarySP != null) {
/* 139 */         protectToken((WSSPolicy)this.primarySP.getKeyBinding());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Token getEncryptionToken() {
/* 146 */     if ((this.isServer ^ this.isIncoming) != 0) {
/* 147 */       Token token1 = this.binding.getInitiatorToken();
/* 148 */       if (token1 == null) {
/* 149 */         token1 = this.binding.getRecipientEncryptionToken();
/*     */       }
/* 151 */       return token1;
/*     */     } 
/* 153 */     Token token = this.binding.getRecipientToken();
/* 154 */     if (token == null) {
/* 155 */       token = this.binding.getInitiatorEncryptionToken();
/*     */     }
/*     */     
/* 158 */     return token;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Token getSignatureToken() {
/* 163 */     if ((this.isServer ^ this.isIncoming) != 0) {
/* 164 */       Token token1 = this.binding.getRecipientToken();
/* 165 */       if (token1 == null) {
/* 166 */         token1 = this.binding.getRecipientSignatureToken();
/*     */       }
/*     */       
/* 169 */       return token1;
/*     */     } 
/* 171 */     Token token = this.binding.getInitiatorToken();
/* 172 */     if (token == null) {
/* 173 */       token = this.binding.getInitiatorSignatureToken();
/*     */     }
/*     */     
/* 176 */     return token;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Binding getBinding() {
/* 181 */     return (Binding)this.binding;
/*     */   }
/*     */   
/*     */   protected EncryptionPolicy getSecondaryEncryptionPolicy() throws PolicyException {
/* 185 */     if (this.sEncPolicy == null) {
/* 186 */       this.sEncPolicy = new EncryptionPolicy();
/* 187 */       this.sEncPolicy.setUUID(this.pid.generateID());
/* 188 */       Token token = getEncryptionToken();
/* 189 */       this.tokenProcessor.addKeyBinding((Binding)this.binding, (WSSPolicy)this.sEncPolicy, token, false);
/* 190 */       this.container.insert((SecurityPolicy)this.sEncPolicy);
/*     */     } 
/* 192 */     return this.sEncPolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void close() {
/* 197 */     if (this.protectionOrder == "SignBeforeEncrypting")
/* 198 */       this.container.insert((SecurityPolicy)this.primaryEP); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\AsymmetricBindingProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */