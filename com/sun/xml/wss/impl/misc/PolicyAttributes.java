/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyAttributes
/*     */ {
/*     */   private boolean issuedTokens = false;
/*     */   private boolean secureConversation = false;
/*     */   private boolean reliableMessaging = false;
/*     */   private boolean supportingTokens = false;
/*     */   private boolean endorsingST = false;
/*     */   private boolean signedEndorsingST = false;
/*     */   private boolean signedST = false;
/*     */   private boolean protectSignature = false;
/*     */   private boolean protectTokens = false;
/*     */   
/*     */   public boolean isProtectTokens() {
/*  65 */     return this.protectTokens;
/*     */   }
/*     */   
/*     */   public void setProtectTokens(boolean protectTokens) {
/*  69 */     this.protectTokens = protectTokens;
/*     */   }
/*     */   
/*     */   public boolean isIssuedTokens() {
/*  73 */     return this.issuedTokens;
/*     */   }
/*     */   
/*     */   public void setIssuedTokens(boolean issuedTokens) {
/*  77 */     this.issuedTokens = issuedTokens;
/*     */   }
/*     */   
/*     */   public boolean isSecureConversation() {
/*  81 */     return this.secureConversation;
/*     */   }
/*     */   
/*     */   public void setSecureConversation(boolean secureConversation) {
/*  85 */     this.secureConversation = secureConversation;
/*     */   }
/*     */   
/*     */   public boolean isReliableMessaging() {
/*  89 */     return this.reliableMessaging;
/*     */   }
/*     */   
/*     */   public void setReliableMessaging(boolean reliableMessaging) {
/*  93 */     this.reliableMessaging = reliableMessaging;
/*     */   }
/*     */   
/*     */   public boolean isSupportingTokens() {
/*  97 */     return this.supportingTokens;
/*     */   }
/*     */   
/*     */   public void setSupportingTokens(boolean supportingTokens) {
/* 101 */     this.supportingTokens = supportingTokens;
/*     */   }
/*     */   
/*     */   public boolean isEndorsingST() {
/* 105 */     return this.endorsingST;
/*     */   }
/*     */   
/*     */   public void setEndorsingST(boolean endorsingST) {
/* 109 */     this.endorsingST = endorsingST;
/*     */   }
/*     */   
/*     */   public boolean isSignedEndorsingST() {
/* 113 */     return this.signedEndorsingST;
/*     */   }
/*     */   
/*     */   public void setSignedEndorsingST(boolean signedEndorsingST) {
/* 117 */     this.signedEndorsingST = signedEndorsingST;
/*     */   }
/*     */   
/*     */   public boolean isSignedST() {
/* 121 */     return this.signedST;
/*     */   }
/*     */   
/*     */   public void setSignedST(boolean signedST) {
/* 125 */     this.signedST = signedST;
/*     */   }
/*     */   
/*     */   public boolean isProtectSignature() {
/* 129 */     return this.protectSignature;
/*     */   }
/*     */   
/*     */   public void setProtectSignature(boolean protectSignature) {
/* 133 */     this.protectSignature = protectSignature;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\PolicyAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */