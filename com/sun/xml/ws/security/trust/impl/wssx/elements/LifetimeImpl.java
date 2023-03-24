/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import javax.xml.bind.JAXBContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LifetimeImpl
/*     */   extends LifetimeType
/*     */   implements Lifetime
/*     */ {
/*     */   public LifetimeImpl() {}
/*     */   
/*     */   public LifetimeImpl(AttributedDateTime created, AttributedDateTime expires) {
/*  71 */     if (created != null) {
/*  72 */       setCreated(created);
/*     */     }
/*  74 */     if (expires != null) {
/*  75 */       setExpires(expires);
/*     */     }
/*     */   }
/*     */   
/*     */   public LifetimeImpl(LifetimeType ltType) {
/*  80 */     this(ltType.getCreated(), ltType.getExpires());
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
/*     */   public static LifetimeType fromElement(Element element) throws WSTrustException {
/*     */     try {
/*  97 */       JAXBContext jc = JAXBContext.newInstance("com.sun.xml.ws.security.trust.impl.wssx.elements");
/*     */       
/*  99 */       Unmarshaller u = jc.createUnmarshaller();
/* 100 */       return (LifetimeType)u.unmarshal(element);
/* 101 */     } catch (Exception ex) {
/* 102 */       throw new WSTrustException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\LifetimeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */