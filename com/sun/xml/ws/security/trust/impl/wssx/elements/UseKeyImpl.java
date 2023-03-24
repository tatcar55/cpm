/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.UseKeyType;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UseKeyImpl
/*     */   extends UseKeyType
/*     */   implements UseKey
/*     */ {
/*  66 */   private String targetType = null;
/*     */   
/*  68 */   private SecurityTokenReference str = null;
/*  69 */   private Token token = null;
/*     */   
/*     */   public UseKeyImpl(Token token) {
/*  72 */     setToken(token);
/*     */   }
/*     */   
/*     */   public UseKeyImpl(SecurityTokenReference str) {
/*  76 */     setSecurityTokenReference(str);
/*  77 */     setTargetType("SecurityTokenReference");
/*     */   }
/*     */   
/*     */   public UseKeyImpl(UseKeyType ukType) throws Exception {
/*  81 */     Object obj = ukType.getAny();
/*  82 */     if (obj instanceof JAXBElement) {
/*  83 */       this.token = (Token)new GenericToken((JAXBElement)obj);
/*     */     } else {
/*  85 */       this.token = (Token)new GenericToken((Element)obj);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTargetType() {
/*  90 */     return this.targetType;
/*     */   }
/*     */   
/*     */   public void setTargetType(String ttype) {
/*  94 */     this.targetType = ttype;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReference ref) {
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
/*     */   public void setToken(Token token) {
/* 113 */     if (token != null) {
/* 114 */       this.token = token;
/* 115 */       setAny(token.getTokenValue());
/*     */     } 
/* 117 */     setTargetType("Token");
/* 118 */     this.str = null;
/*     */   }
/*     */   
/*     */   public Token getToken() {
/* 122 */     return this.token;
/*     */   }
/*     */   
/*     */   public void setSignatureID(URI sigID) {
/* 126 */     setSig(sigID.toString());
/*     */   }
/*     */   
/*     */   public URI getSignatureID() {
/*     */     try {
/* 131 */       return new URI(getSig());
/* 132 */     } catch (URISyntaxException ue) {
/* 133 */       throw new RuntimeException("URI syntax invalid ", ue);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\UseKeyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */