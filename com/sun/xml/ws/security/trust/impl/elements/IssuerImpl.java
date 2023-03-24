/*    */ package com.sun.xml.ws.security.trust.impl.elements;
/*    */ 
/*    */ import com.sun.xml.ws.security.trust.elements.Issuer;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IssuerImpl
/*    */   implements Issuer
/*    */ {
/* 59 */   EndpointReference epr = null;
/*    */ 
/*    */   
/*    */   public IssuerImpl() {}
/*    */ 
/*    */   
/*    */   public IssuerImpl(EndpointReference epr) {
/* 66 */     setEndpointReference(epr);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EndpointReference getEndpointReference() {
/* 74 */     return this.epr;
/*    */   }
/*    */   
/*    */   public final void setEndpointReference(EndpointReference endpointReference) {
/* 78 */     this.epr = endpointReference;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\IssuerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */