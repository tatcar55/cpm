/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.UseKeyType;
/*     */ import java.net.URI;
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
/*     */ 
/*     */ 
/*     */ public class UseKeyImpl
/*     */   extends UseKeyType
/*     */   implements UseKey
/*     */ {
/*  72 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UseKeyImpl(Token token) {
/*  78 */     setToken(token);
/*     */   }
/*     */   
/*     */   public UseKeyImpl(@NotNull UseKeyType ukType) {
/*  82 */     setAny(ukType.getAny());
/*  83 */     setSig(ukType.getSig());
/*     */   }
/*     */   
/*     */   public void setToken(@NotNull Token token) {
/*  87 */     setAny(token.getTokenValue());
/*     */   }
/*     */   
/*     */   public Token getToken() {
/*  91 */     Object value = getAny();
/*  92 */     if (value instanceof Element)
/*  93 */       return (Token)new GenericToken((Element)value); 
/*  94 */     if (value instanceof JAXBElement) {
/*  95 */       return (Token)new GenericToken((JAXBElement)value);
/*     */     }
/*     */ 
/*     */     
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public void setSignatureID(@NotNull URI sigID) {
/* 103 */     setSig(sigID.toString());
/*     */   }
/*     */   
/*     */   public URI getSignatureID() {
/* 107 */     return URI.create(getSig());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\UseKeyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */