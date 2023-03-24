/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSSKeyBindingExtension
/*     */   extends WSSPolicy
/*     */ {
/*     */   public MLSPolicy newX509CertificateKeyBinding() {
/*  74 */     if (isReadOnly()) {
/*  75 */       throw new RuntimeException("Can not create X509CertificateKeyBinding : Policy is Readonly");
/*     */     }
/*  77 */     this._keyBinding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  78 */     return this._keyBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy newSAMLAssertionKeyBinding() {
/*  89 */     if (isReadOnly()) {
/*  90 */       throw new RuntimeException("Can not create SAMLAssertionKeyBinding : Policy is Readonly");
/*     */     }
/*     */     
/*  93 */     this._keyBinding = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/*  94 */     return this._keyBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy newSymmetricKeyBinding() {
/* 105 */     if (isReadOnly()) {
/* 106 */       throw new RuntimeException("Can not create SymmetricKeyBinding : Policy is Readonly");
/*     */     }
/*     */     
/* 109 */     this._keyBinding = new SymmetricKeyBinding();
/* 110 */     return this._keyBinding;
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
/*     */   public MLSPolicy newDerivedTokenKeyBinding() {
/* 122 */     if (isReadOnly()) {
/* 123 */       throw new RuntimeException("Can not create DerivedTokenKeyBinding : Policy is Readonly");
/*     */     }
/*     */     
/* 126 */     this._keyBinding = new DerivedTokenKeyBinding();
/* 127 */     return this._keyBinding;
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
/*     */   public MLSPolicy newIssuedTokenKeyBinding() {
/* 139 */     if (isReadOnly()) {
/* 140 */       throw new RuntimeException("Can not create IssuedTokenKeyBinding : Policy is Readonly");
/*     */     }
/*     */     
/* 143 */     this._keyBinding = new IssuedTokenKeyBinding();
/* 144 */     return this._keyBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy newSecureConversationTokenKeyBinding() {
/* 155 */     if (isReadOnly()) {
/* 156 */       throw new RuntimeException("Can not create SecureConversationKeyBinding : Policy is Readonly");
/*     */     }
/*     */     
/* 159 */     this._keyBinding = new SecureConversationTokenKeyBinding();
/* 160 */     return this._keyBinding;
/*     */   }
/*     */   
/*     */   public MLSPolicy newUsernameTokenBindingKeyBinding() {
/* 164 */     if (isReadOnly()) {
/* 165 */       throw new RuntimeException("Can not create SAMLAssertionKeyBinding : Policy is Readonly");
/*     */     }
/* 167 */     this._keyBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/* 168 */     return this._keyBinding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\WSSKeyBindingExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */