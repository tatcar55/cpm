/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class LifetimeImpl
/*     */   extends LifetimeType
/*     */   implements Lifetime
/*     */ {
/*  70 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LifetimeImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public LifetimeImpl(AttributedDateTime created, AttributedDateTime expires) {
/*  80 */     if (created != null) {
/*  81 */       setCreated(created);
/*     */     }
/*  83 */     if (expires != null) {
/*  84 */       setExpires(expires);
/*     */     }
/*     */   }
/*     */   
/*     */   public LifetimeImpl(@NotNull LifetimeType ltType) {
/*  89 */     this(ltType.getCreated(), ltType.getExpires());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LifetimeType fromElement(@NotNull Element element) throws WSTrustException {
/*     */     try {
/* 106 */       Unmarshaller unmarshaller = WSTrustElementFactory.getContext().createUnmarshaller();
/* 107 */       return (LifetimeType)unmarshaller.unmarshal(element);
/* 108 */     } catch (JAXBException ex) {
/* 109 */       log.log(Level.SEVERE, LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT());
/*     */       
/* 111 */       throw new WSTrustException(LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\LifetimeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */