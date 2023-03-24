/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ClaimsType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ 
/*     */ public class ClaimsImpl
/*     */   extends ClaimsType
/*     */   implements Claims
/*     */ {
/*  72 */   List<Object> supportingInfo = new ArrayList();
/*  73 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClaimsImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClaimsImpl(String dialect) {
/*  84 */     setDialect(dialect);
/*     */   }
/*     */   
/*     */   public ClaimsImpl(ClaimsType clType) throws WSTrustException {
/*  88 */     setDialect(clType.getDialect());
/*  89 */     getAny().addAll(clType.getAny());
/*  90 */     getOtherAttributes().putAll(clType.getOtherAttributes());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClaimsType fromElement(Element element) throws WSTrustException {
/*     */     try {
/*  96 */       Unmarshaller unmarshaller = WSTrustElementFactory.getContext().createUnmarshaller();
/*  97 */       return ((JAXBElement<ClaimsType>)unmarshaller.unmarshal(element)).getValue();
/*  98 */     } catch (JAXBException ex) {
/*  99 */       log.log(Level.SEVERE, LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */       
/* 101 */       throw new WSTrustException(LogStringsMessages.WST_0021_ERROR_UNMARSHAL_DOM_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Object> getSupportingProperties() {
/* 106 */     return this.supportingInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\ClaimsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */