/*    */ package com.sun.xml.ws.security.impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*    */ import java.security.Principal;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ public class WebServiceContextSecurityDelegate
/*    */   implements WebServiceContextDelegate
/*    */ {
/* 59 */   private WebServiceContextDelegate delegate = null;
/*    */   
/*    */   public WebServiceContextSecurityDelegate(WebServiceContextDelegate delegate) {
/* 62 */     this.delegate = delegate;
/*    */   }
/*    */   public Principal getUserPrincipal(Packet packet) {
/* 65 */     Subject subject = (Subject)packet.invocationProperties.get("javax.security.auth.Subject");
/* 66 */     if (subject == null)
/*    */     {
/* 68 */       return null;
/*    */     }
/* 70 */     Set<Principal> set = subject.getPrincipals(Principal.class);
/* 71 */     if (set.isEmpty()) {
/* 72 */       return null;
/*    */     }
/*    */     
/* 75 */     return set.iterator().next();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isUserInRole(Packet arg0, String role) {
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   public String getEPRAddress(Packet arg0, WSEndpoint arg1) {
/* 84 */     return this.delegate.getEPRAddress(arg0, arg1);
/*    */   }
/*    */   
/*    */   public String getWSDLAddress(Packet arg0, WSEndpoint arg1) {
/* 88 */     return this.delegate.getWSDLAddress(arg0, arg1);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\WebServiceContextSecurityDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */