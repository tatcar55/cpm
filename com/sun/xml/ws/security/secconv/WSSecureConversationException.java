/*    */ package com.sun.xml.ws.security.secconv;
/*    */ 
/*    */ import com.sun.xml.ws.api.security.trust.WSTrustException;
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
/*    */ public class WSSecureConversationException
/*    */   extends WSTrustException
/*    */ {
/*    */   public WSSecureConversationException(String msg, Throwable cause) {
/* 51 */     super(msg, cause);
/*    */   }
/*    */   
/*    */   public WSSecureConversationException(String msg) {
/* 55 */     super(msg);
/*    */   }
/*    */   
/*    */   public WSSecureConversationException(WSTrustException tex) {
/* 59 */     super(tex.getMessage(), tex.getCause());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSecureConversationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */