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
/*     */ 
/*     */ 
/*     */ public class UsernameOrSAMLAlternativeSelector
/*     */   implements AlternativeSelector
/*     */ {
/*     */   private enum SupportingTokenType
/*     */   {
/*  62 */     USERNAME, SAML, UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessagePolicy selectAlternative(ProcessingContext ctx, List<MessagePolicy> alternatives, SecurityPolicy recvdPolicy) {
/*  71 */     SupportingTokenType reqMsgTokenType = determineTokenType(recvdPolicy);
/*  72 */     for (MessagePolicy mp : alternatives) {
/*  73 */       SupportingTokenType alternativeTokenType = determineTokenType((SecurityPolicy)mp);
/*  74 */       if (reqMsgTokenType != SupportingTokenType.UNKNOWN && reqMsgTokenType.equals(alternativeTokenType)) {
/*  75 */         return mp;
/*     */       }
/*     */     } 
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsAlternatives(List<MessagePolicy> alternatives) {
/*  83 */     if (alternatives.size() != 2) {
/*  84 */       return false;
/*     */     }
/*  86 */     SupportingTokenType firstAlternativeType = determineTokenType((SecurityPolicy)alternatives.get(0));
/*     */     
/*  88 */     if (firstAlternativeType == SupportingTokenType.UNKNOWN) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     SupportingTokenType secondAlternativeType = determineTokenType((SecurityPolicy)alternatives.get(1));
/*     */     
/*  94 */     if (secondAlternativeType == SupportingTokenType.UNKNOWN) {
/*  95 */       return false;
/*     */     }
/*     */     
/*  98 */     if (firstAlternativeType == secondAlternativeType) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     return true;
/*     */   }
/*     */   
/*     */   private SupportingTokenType determineTokenType(SecurityPolicy recvdPolicy) {
/* 106 */     SupportingTokenType ret = SupportingTokenType.UNKNOWN;
/* 107 */     if (recvdPolicy instanceof MessagePolicy) {
/* 108 */       MessagePolicy pol = (MessagePolicy)recvdPolicy;
/* 109 */       for (int i = 0; i < pol.size(); i++) {
/*     */         try {
/* 111 */           WSSPolicy p = (WSSPolicy)pol.get(i);
/* 112 */           if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)p) || PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)p.getFeatureBinding())) {
/* 113 */             ret = SupportingTokenType.USERNAME; break;
/*     */           } 
/* 115 */           if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)p) || PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)p.getFeatureBinding())) {
/* 116 */             ret = SupportingTokenType.SAML;
/*     */             break;
/*     */           } 
/* 119 */         } catch (Exception e) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 124 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\UsernameOrSAMLAlternativeSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */