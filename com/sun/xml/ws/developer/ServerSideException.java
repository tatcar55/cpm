/*    */ package com.sun.xml.ws.developer;
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
/*    */ public class ServerSideException
/*    */   extends Exception
/*    */ {
/*    */   private final String className;
/*    */   
/*    */   public ServerSideException(String className, String message) {
/* 58 */     super(message);
/* 59 */     this.className = className;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 63 */     return "Client received an exception from server: " + super.getMessage() + " Please see the server log to find more detail regarding exact cause of the failure.";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     String s = this.className;
/* 70 */     String message = getLocalizedMessage();
/* 71 */     return (message != null) ? (s + ": " + message) : s;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\ServerSideException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */