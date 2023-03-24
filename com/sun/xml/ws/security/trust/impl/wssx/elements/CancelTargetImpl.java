/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.CancelTargetType;
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
/*     */ public class CancelTargetImpl
/*     */   extends CancelTargetType
/*     */   implements CancelTarget
/*     */ {
/*  62 */   private String targetType = null;
/*     */ 
/*     */ 
/*     */   
/*  66 */   private SecurityTokenReference str = null;
/*  67 */   private Token token = null;
/*     */   
/*     */   public CancelTargetImpl(SecurityTokenReference str) {
/*  70 */     setSecurityTokenReference(str);
/*  71 */     setTargetType("SecurityTokenReference");
/*     */   }
/*     */   
/*     */   public CancelTargetImpl(Token token) {
/*  75 */     setToken(token);
/*  76 */     setTargetType("Custom");
/*     */   }
/*     */   
/*     */   public CancelTargetImpl(CancelTargetType ctType) throws Exception {
/*  80 */     JAXBElement<SecurityTokenReferenceType> obj = (JAXBElement)ctType.getAny();
/*  81 */     String local = obj.getName().getLocalPart();
/*  82 */     if ("SecurityTokenReference".equals(local)) {
/*  83 */       SecurityTokenReferenceImpl securityTokenReferenceImpl = new SecurityTokenReferenceImpl(obj.getValue());
/*     */       
/*  85 */       setSecurityTokenReference((SecurityTokenReference)securityTokenReferenceImpl);
/*  86 */       setTargetType("SecurityTokenReference");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetType() {
/*  93 */     return this.targetType;
/*     */   }
/*     */   
/*     */   public void setTargetType(String ttype) {
/*  97 */     this.targetType = ttype;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReference ref) {
/* 101 */     if (ref != null) {
/* 102 */       this.str = ref;
/* 103 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)ref);
/*     */       
/* 105 */       setAny(strElement);
/*     */     } 
/* 107 */     setTargetType("SecurityTokenReference");
/* 108 */     this.token = null;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 112 */     return this.str;
/*     */   }
/*     */   
/*     */   public void setToken(Token token) {
/* 116 */     if (token != null) {
/* 117 */       this.token = token;
/* 118 */       setAny(token);
/*     */     } 
/* 120 */     setTargetType("Custom");
/* 121 */     this.str = null;
/*     */   }
/*     */   
/*     */   public Token getToken() {
/* 125 */     return this.token;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\CancelTargetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */