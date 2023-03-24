/*     */ package com.sun.xml.ws.security.trust;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GenericToken
/*     */   implements Token
/*     */ {
/*  68 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   private Object token;
/*     */ 
/*     */   
/*     */   private String tokenType;
/*     */ 
/*     */   
/*  78 */   private SecurityHeaderElement she = null;
/*     */   
/*     */   private String id;
/*     */   
/*     */   public GenericToken(Element token) {
/*  83 */     this.token = token;
/*  84 */     this.id = token.getAttributeNS(null, "AssertionID");
/*  85 */     if (this.id == null || this.id.length() == 0) {
/*  86 */       this.id = token.getAttributeNS(null, "ID");
/*     */     }
/*  88 */     if (this.id == null || this.id.length() == 0) {
/*  89 */       this.id = token.getAttributeNS(null, "Id");
/*     */     }
/*  91 */     if (this.id == null || this.id.length() == 0) {
/*  92 */       this.id = UUID.randomUUID().toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public GenericToken(JAXBElement token) {
/*  97 */     this.token = token;
/*     */   }
/*     */   
/*     */   public GenericToken(Element token, String tokenType) {
/* 101 */     this(token);
/*     */     
/* 103 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public GenericToken(SecurityHeaderElement headerElement) {
/* 107 */     this.she = headerElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 113 */     if (this.tokenType != null) {
/* 114 */       if (log.isLoggable(Level.FINE)) {
/* 115 */         log.log(Level.FINE, LogStringsMessages.WST_1001_TOKEN_TYPE(this.tokenType));
/*     */       }
/*     */       
/* 118 */       return this.tokenType;
/*     */     } 
/* 120 */     return "opaque";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 124 */     return this.token;
/*     */   }
/*     */   
/*     */   public SecurityHeaderElement getElement() {
/* 128 */     return this.she;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 132 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 136 */     this.id = id;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\GenericToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */