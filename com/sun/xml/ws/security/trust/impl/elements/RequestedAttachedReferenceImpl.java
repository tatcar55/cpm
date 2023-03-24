/*    */ package com.sun.xml.ws.security.trust.impl.elements;
/*    */ 
/*    */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*    */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*    */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*    */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedReferenceType;
/*    */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
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
/*    */ public class RequestedAttachedReferenceImpl
/*    */   extends RequestedReferenceType
/*    */   implements RequestedAttachedReference
/*    */ {
/* 61 */   SecurityTokenReference str = null;
/*    */ 
/*    */   
/*    */   public RequestedAttachedReferenceImpl() {}
/*    */ 
/*    */   
/*    */   public RequestedAttachedReferenceImpl(SecurityTokenReference str) {
/* 68 */     setSTR(str);
/*    */   }
/*    */   
/*    */   public RequestedAttachedReferenceImpl(RequestedReferenceType rrType) {
/* 72 */     this((SecurityTokenReference)new SecurityTokenReferenceImpl(rrType.getSecurityTokenReference()));
/*    */   }
/*    */   
/*    */   public SecurityTokenReference getSTR() {
/* 76 */     return this.str;
/*    */   }
/*    */   
/*    */   public final void setSTR(SecurityTokenReference str) {
/* 80 */     if (str != null) {
/* 81 */       setSecurityTokenReference((SecurityTokenReferenceType)str);
/*    */     }
/* 83 */     this.str = str;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RequestedAttachedReferenceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */