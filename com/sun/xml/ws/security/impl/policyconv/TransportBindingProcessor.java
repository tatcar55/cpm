/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedEndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.SignedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.policy.TransportBinding;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.Iterator;
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
/*     */ public class TransportBindingProcessor
/*     */   extends BindingProcessor
/*     */ {
/*  69 */   private TransportBinding binding = null;
/*  70 */   private TimestampPolicy tp = null;
/*     */   
/*     */   private boolean buildSP = false;
/*     */   private boolean buildEP = false;
/*     */   
/*     */   public TransportBindingProcessor(TransportBinding binding, boolean isServer, boolean isIncoming, XWSSPolicyContainer container) {
/*  76 */     this.binding = binding;
/*  77 */     this.container = container;
/*  78 */     this.isIncoming = isIncoming;
/*  79 */     this.isServer = isServer;
/*  80 */     this.iAP = new IntegrityAssertionProcessor(binding.getAlgorithmSuite(), false);
/*  81 */     this.eAP = new EncryptionAssertionProcessor(binding.getAlgorithmSuite(), false);
/*  82 */     this.tokenProcessor = new TokenProcessor(isServer, isIncoming, this.pid);
/*     */   }
/*     */   
/*     */   public void process() throws PolicyException {
/*  86 */     this.container.setPolicyContainerMode(this.binding.getLayout());
/*  87 */     if (this.binding.isIncludeTimeStamp()) {
/*  88 */       this.tp = new TimestampPolicy();
/*  89 */       this.tp.setUUID(this.pid.generateID());
/*  90 */       this.container.insert((SecurityPolicy)this.tp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(SupportingTokens tokens) throws PolicyException {
/*  95 */     Iterator<Token> itr = tokens.getTokens();
/*  96 */     while (itr.hasNext()) {
/*  97 */       Token token = itr.next();
/*  98 */       WSSPolicy policy = this.tokenProcessor.getWSSToken(token);
/*  99 */       if (policy instanceof IssuedTokenKeyBinding) {
/* 100 */         ((IssuedTokenKeyBinding)policy).setSTRID(null);
/* 101 */       } else if (policy instanceof AuthenticationTokenPolicy.SAMLAssertionBinding) {
/* 102 */         ((AuthenticationTokenPolicy.SAMLAssertionBinding)policy).setSTRID(null);
/*     */       } 
/* 104 */       AuthenticationTokenPolicy atp = new AuthenticationTokenPolicy();
/* 105 */       atp.setFeatureBinding((MLSPolicy)policy);
/* 106 */       this.container.insert((SecurityPolicy)atp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(SignedSupportingTokens sst) throws PolicyException {
/* 111 */     Iterator<Token> itr = sst.getTokens();
/* 112 */     while (itr.hasNext()) {
/* 113 */       Token token = itr.next();
/* 114 */       WSSPolicy policy = this.tokenProcessor.getWSSToken(token);
/* 115 */       if (policy instanceof IssuedTokenKeyBinding) {
/* 116 */         ((IssuedTokenKeyBinding)policy).setSTRID(null);
/* 117 */       } else if (policy instanceof AuthenticationTokenPolicy.SAMLAssertionBinding) {
/* 118 */         ((AuthenticationTokenPolicy.SAMLAssertionBinding)policy).setSTRID(null);
/*     */       } 
/* 120 */       AuthenticationTokenPolicy atp = new AuthenticationTokenPolicy();
/* 121 */       atp.setFeatureBinding((MLSPolicy)policy);
/* 122 */       this.container.insert((SecurityPolicy)atp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(EndorsingSupportingTokens est) throws PolicyException {
/* 127 */     Iterator<Token> itr = est.getTokens();
/* 128 */     if (est.getSignedElements().hasNext() || est.getSignedParts().hasNext()) {
/* 129 */       this.buildSP = true;
/*     */     }
/* 131 */     while (itr.hasNext()) {
/* 132 */       Token token = itr.next();
/* 133 */       SignaturePolicy sp = new SignaturePolicy();
/* 134 */       SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/*     */       
/* 136 */       SecurityPolicyUtil.setCanonicalizationMethod(spFB, this.binding.getAlgorithmSuite());
/* 137 */       sp.setUUID(this.pid.generateID());
/* 138 */       this.tokenProcessor.addKeyBinding((Binding)this.binding, (WSSPolicy)sp, token, false);
/*     */ 
/*     */       
/* 141 */       if (this.tp != null) {
/* 142 */         SignatureTarget target = this.iAP.getTargetCreator().newURISignatureTarget(this.tp.getUUID());
/* 143 */         this.iAP.getTargetCreator().addTransform(target);
/* 144 */         SecurityPolicyUtil.setName((Target)target, (WSSPolicy)this.tp);
/*     */ 
/*     */         
/* 147 */         spFB.addTargetBinding(target);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 152 */       if (this.buildSP) {
/* 153 */         Iterator<SignedParts> itr_sp = est.getSignedParts();
/* 154 */         while (itr_sp.hasNext()) {
/* 155 */           SignedParts target = itr_sp.next();
/* 156 */           this.iAP.process(target, spFB);
/*     */         } 
/* 158 */         Iterator<SignedElements> itr_se = est.getSignedElements();
/* 159 */         while (itr_se.hasNext()) {
/* 160 */           SignedElements target = itr_se.next();
/* 161 */           this.iAP.process(target, spFB);
/*     */         } 
/*     */       } 
/*     */       
/* 165 */       this.container.insert((SecurityPolicy)sp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(SignedEndorsingSupportingTokens set) throws PolicyException {
/* 170 */     Iterator<Token> itr = set.getTokens();
/* 171 */     while (itr.hasNext()) {
/* 172 */       Token token = itr.next();
/* 173 */       SignaturePolicy sp = new SignaturePolicy();
/* 174 */       sp.setUUID(this.pid.generateID());
/* 175 */       SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/*     */       
/* 177 */       SecurityPolicyUtil.setCanonicalizationMethod(spFB, this.binding.getAlgorithmSuite());
/* 178 */       this.tokenProcessor.addKeyBinding((Binding)this.binding, (WSSPolicy)sp, token, false);
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (this.tp != null) {
/* 183 */         SignatureTarget target = this.iAP.getTargetCreator().newURISignatureTarget(this.tp.getUUID());
/* 184 */         this.iAP.getTargetCreator().addTransform(target);
/* 185 */         SecurityPolicyUtil.setName((Target)target, (WSSPolicy)this.tp);
/*     */ 
/*     */         
/* 188 */         spFB.addTargetBinding(target);
/* 189 */         this.container.insert((SecurityPolicy)sp);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(SignedEncryptedSupportingTokens sest) throws PolicyException {
/* 195 */     Iterator<Token> itr = sest.getTokens();
/* 196 */     while (itr.hasNext()) {
/* 197 */       Token token = itr.next();
/* 198 */       WSSPolicy policy = this.tokenProcessor.getWSSToken(token);
/* 199 */       if (policy instanceof IssuedTokenKeyBinding) {
/* 200 */         ((IssuedTokenKeyBinding)policy).setSTRID(null);
/* 201 */       } else if (policy instanceof AuthenticationTokenPolicy.SAMLAssertionBinding) {
/* 202 */         ((AuthenticationTokenPolicy.SAMLAssertionBinding)policy).setSTRID(null);
/*     */       } 
/* 204 */       AuthenticationTokenPolicy atp = new AuthenticationTokenPolicy();
/* 205 */       atp.setFeatureBinding((MLSPolicy)policy);
/* 206 */       this.container.insert((SecurityPolicy)atp);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected EncryptionPolicy getSecondaryEncryptionPolicy() throws PolicyException {
/* 211 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected Binding getBinding() {
/* 215 */     return (Binding)this.binding;
/*     */   }
/*     */   
/*     */   protected void close() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\TransportBindingProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */