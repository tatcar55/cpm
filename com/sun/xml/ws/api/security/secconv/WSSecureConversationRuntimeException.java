/*    */ package com.sun.xml.ws.api.security.secconv;
/*    */ 
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class WSSecureConversationRuntimeException
/*    */   extends RuntimeException
/*    */ {
/*    */   private QName faultcode;
/*    */   
/*    */   public WSSecureConversationRuntimeException(QName faultcode, String faultstring) {
/* 52 */     super(faultstring);
/* 53 */     this.faultcode = faultcode;
/*    */   }
/*    */   
/*    */   public QName getFaultCode() {
/* 57 */     return this.faultcode;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\secconv\WSSecureConversationRuntimeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */