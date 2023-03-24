/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.elements.RenewTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RenewTargetType;
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
/*     */ 
/*     */ 
/*     */ public class RenewTargetImpl
/*     */   extends RenewTargetType
/*     */   implements RenewTarget
/*     */ {
/*  63 */   private String targetType = null;
/*     */   
/*  65 */   private SecurityTokenReference str = null;
/*  66 */   private Token token = null;
/*     */   
/*     */   public RenewTargetImpl(SecurityTokenReference str) {
/*  69 */     setSecurityTokenReference(str);
/*  70 */     setTargetType("SecurityTokenReference");
/*     */   }
/*     */   
/*     */   public RenewTargetImpl(Token token) {
/*  74 */     setToken(token);
/*  75 */     setTargetType("Token");
/*     */   }
/*     */   
/*     */   public RenewTargetImpl(RenewTargetType rnType) {
/*  79 */     JAXBElement<SecurityTokenReferenceType> obj = (JAXBElement)rnType.getAny();
/*  80 */     String local = obj.getName().getLocalPart();
/*  81 */     if ("SecurityTokenReference".equals(local)) {
/*  82 */       SecurityTokenReferenceImpl securityTokenReferenceImpl = new SecurityTokenReferenceImpl(obj.getValue());
/*     */       
/*  84 */       setSecurityTokenReference((SecurityTokenReference)securityTokenReferenceImpl);
/*  85 */       setTargetType("SecurityTokenReference");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetType() {
/*  92 */     return this.targetType;
/*     */   }
/*     */   
/*     */   public final void setTargetType(String ttype) {
/*  96 */     this.targetType = ttype;
/*     */   }
/*     */   
/*     */   public final void setSecurityTokenReference(SecurityTokenReference ref) {
/* 100 */     if (ref != null) {
/* 101 */       this.str = ref;
/* 102 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)ref);
/*     */       
/* 104 */       setAny(strElement);
/*     */     } 
/* 106 */     setTargetType("SecurityTokenReference");
/* 107 */     this.token = null;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 111 */     return this.str;
/*     */   }
/*     */   
/*     */   public final void setToken(Token token) {
/* 115 */     if (token != null) {
/* 116 */       this.token = token;
/* 117 */       setAny(token);
/*     */     } 
/* 119 */     setTargetType("Token");
/* 120 */     this.str = null;
/*     */   }
/*     */   
/*     */   public Token getToken() {
/* 124 */     return this.token;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RenewTargetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */