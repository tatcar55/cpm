/*     */ package com.sun.xml.wss.impl.policy.verifier;
/*     */ 
/*     */ import com.sun.xml.ws.security.spi.AlternativeSelector;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.List;
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
/*     */ public class UsernameOrX509AlternativeSelector
/*     */   implements AlternativeSelector
/*     */ {
/*     */   private enum SupportingTokenType
/*     */   {
/*  60 */     USERNAME, X509, UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessagePolicy selectAlternative(ProcessingContext ctx, List<MessagePolicy> alternatives, SecurityPolicy recvdPolicy) {
/*  65 */     SupportingTokenType reqMsgTokenType = determineTokenType(recvdPolicy);
/*  66 */     for (MessagePolicy mp : alternatives) {
/*  67 */       SupportingTokenType alternativeTokenType = determineTokenType((SecurityPolicy)mp);
/*  68 */       if (reqMsgTokenType != SupportingTokenType.UNKNOWN && reqMsgTokenType.equals(alternativeTokenType)) {
/*  69 */         return mp;
/*     */       }
/*     */     } 
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsAlternatives(List<MessagePolicy> alternatives) {
/*  77 */     if (alternatives.size() != 2) {
/*  78 */       return false;
/*     */     }
/*  80 */     SupportingTokenType firstAlternativeType = determineTokenType((SecurityPolicy)alternatives.get(0));
/*     */     
/*  82 */     if (firstAlternativeType == SupportingTokenType.UNKNOWN) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     SupportingTokenType secondAlternativeType = determineTokenType((SecurityPolicy)alternatives.get(1));
/*     */     
/*  88 */     if (secondAlternativeType == SupportingTokenType.UNKNOWN) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     if (firstAlternativeType == secondAlternativeType) {
/*  93 */       return false;
/*     */     }
/*     */     
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   private SupportingTokenType determineTokenType(SecurityPolicy recvdPolicy) {
/* 100 */     SupportingTokenType ret = SupportingTokenType.UNKNOWN;
/* 101 */     if (recvdPolicy instanceof MessagePolicy) {
/* 102 */       MessagePolicy pol = (MessagePolicy)recvdPolicy;
/* 103 */       for (int i = 0; i < pol.size(); i++) {
/*     */         try {
/* 105 */           WSSPolicy p = (WSSPolicy)pol.get(i);
/* 106 */           if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)p) || PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)p.getFeatureBinding())) {
/* 107 */             ret = SupportingTokenType.USERNAME; break;
/*     */           } 
/* 109 */           if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)p) || PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)p.getFeatureBinding())) {
/* 110 */             ret = SupportingTokenType.X509;
/*     */             break;
/*     */           } 
/* 113 */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\UsernameOrX509AlternativeSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */