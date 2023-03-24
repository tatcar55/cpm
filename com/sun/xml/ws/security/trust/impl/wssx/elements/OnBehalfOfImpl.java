/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.OnBehalfOfType;
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
/*     */ public class OnBehalfOfImpl
/*     */   extends OnBehalfOfType
/*     */   implements OnBehalfOf
/*     */ {
/*  61 */   private EndpointReference epr = null;
/*  62 */   private SecurityTokenReference str = null;
/*     */   
/*     */   public OnBehalfOfImpl(Token oboToken) {
/*  65 */     setAny(oboToken.getTokenValue());
/*     */   }
/*     */   
/*     */   public OnBehalfOfImpl(OnBehalfOfType oboType) {
/*  69 */     Object ob = oboType.getAny();
/*  70 */     if (ob != null)
/*  71 */       setAny(ob); 
/*     */   }
/*     */   
/*     */   public EndpointReference getEndpointReference() {
/*  75 */     return this.epr;
/*     */   }
/*     */   
/*     */   public void setEndpointReference(EndpointReference endpointReference) {
/*  79 */     this.epr = endpointReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.str = null;
/*     */   }
/*     */   
/*     */   public void setSecurityTokenReference(SecurityTokenReference ref) {
/*  90 */     this.str = ref;
/*  91 */     if (ref != null) {
/*  92 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)ref);
/*     */       
/*  94 */       setAny(strElement);
/*     */     } 
/*  96 */     this.epr = null;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getSecurityTokenReference() {
/* 100 */     return this.str;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\OnBehalfOfImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */