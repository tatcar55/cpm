/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.elements.ProofEncryption;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ProofEncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProofEncryptionImpl
/*     */   extends ProofEncryptionType
/*     */   implements ProofEncryption
/*     */ {
/*  61 */   private String targetType = null;
/*     */   
/*  63 */   private SecurityTokenReference str = null;
/*  64 */   private Token token = null;
/*     */   
/*     */   public ProofEncryptionImpl(SecurityTokenReference str) {
/*  67 */     setSecurityTokenReference(str);
/*  68 */     setTargetType("SecurityTokenReference");
/*     */   }
/*     */   
/*     */   public ProofEncryptionImpl(Token token) {
/*  72 */     setToken(token);
/*  73 */     setTargetType("Token");
/*     */   }
/*     */   
/*     */   public ProofEncryptionImpl(ProofEncryptionType peType) {
/*  77 */     JAXBElement<SecurityTokenReferenceType> obj = (JAXBElement)peType.getAny();
/*  78 */     String local = obj.getName().getLocalPart();
/*  79 */     if ("SecurityTokenReference".equals(local)) {
/*  80 */       SecurityTokenReferenceImpl securityTokenReferenceImpl = new SecurityTokenReferenceImpl(obj.getValue());
/*     */       
/*  82 */       setSecurityTokenReference((SecurityTokenReference)securityTokenReferenceImpl);
/*  83 */       setTargetType("SecurityTokenReference");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetType() {
/*  90 */     return this.targetType;
/*     */   }
/*     */   
/*     */   public final void setTargetType(String ttype) {
/*  94 */     this.targetType = ttype;
/*     */   }
/*     */   
/*     */   public final void setSecurityTokenReference(SecurityTokenReference ref) {
/*  98 */     if (ref != null) {
/*  99 */       this.str = ref;
/* 100 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)ref);
/*     */       
/* 102 */       setAny(strElement);
/*     */     } 
/* 104 */     setTargetType("SecurityTokenReference");
/* 105 */     this.token = null;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 109 */     return this.str;
/*     */   }
/*     */   
/*     */   public final void setToken(Token token) {
/* 113 */     if (token != null) {
/* 114 */       this.token = token;
/* 115 */       setAny(token);
/*     */     } 
/* 117 */     setTargetType("Token");
/* 118 */     this.str = null;
/*     */   }
/*     */   
/*     */   public Token getToken() {
/* 122 */     return this.token;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\ProofEncryptionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */