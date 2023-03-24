/*    */ package com.sun.xml.ws.security.trust.impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.security.trust.STSAuthorizationProvider;
/*    */ import javax.security.auth.Subject;
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
/*    */ public class DefaultSTSAuthorizationProvider
/*    */   implements STSAuthorizationProvider
/*    */ {
/*    */   public boolean isAuthorized(Subject subject, String appliesTo, String tokenType, String keyType) {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\DefaultSTSAuthorizationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */