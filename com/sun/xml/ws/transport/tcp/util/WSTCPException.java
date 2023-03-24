/*    */ package com.sun.xml.ws.transport.tcp.util;
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
/*    */ public class WSTCPException
/*    */   extends Exception
/*    */ {
/*    */   private WSTCPError error;
/*    */   
/*    */   public WSTCPException(WSTCPError error) {
/* 50 */     this.error = error;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 54 */     return toString();
/*    */   }
/*    */   
/*    */   public WSTCPError getError() {
/* 58 */     return this.error;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     if (this.error != null) {
/* 63 */       return this.error.toString();
/*    */     }
/*    */     
/* 66 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\WSTCPException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */