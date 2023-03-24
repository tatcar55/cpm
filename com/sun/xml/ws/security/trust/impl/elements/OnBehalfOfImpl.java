/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.OnBehalfOfType;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OnBehalfOfImpl
/*     */   extends OnBehalfOfType
/*     */   implements OnBehalfOf
/*     */ {
/*  63 */   private EndpointReference epr = null;
/*  64 */   private SecurityTokenReference str = null;
/*     */   
/*     */   public OnBehalfOfImpl(Token oboToken) {
/*  67 */     oboToken.getTokenValue();
/*  68 */     setAny(oboToken.getTokenValue());
/*     */   }
/*     */   
/*     */   public OnBehalfOfImpl(OnBehalfOfType oboType) {
/*  72 */     Object ob = oboType.getAny();
/*  73 */     if (ob != null)
/*  74 */       setAny(ob); 
/*     */   }
/*     */   
/*     */   public EndpointReference getEndpointReference() {
/*  78 */     return this.epr;
/*     */   }
/*     */   
/*     */   public void setEndpointReference(EndpointReference endpointReference) {
/*  82 */     this.epr = endpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.str = null;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReference ref) {
/*  93 */     this.str = ref;
/*  94 */     if (ref != null) {
/*  95 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)ref);
/*     */       
/*  97 */       setAny(strElement);
/*     */     } 
/*  99 */     this.epr = null;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 103 */     return this.str;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\OnBehalfOfImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */