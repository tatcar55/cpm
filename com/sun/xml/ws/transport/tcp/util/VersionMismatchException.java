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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VersionMismatchException
/*    */   extends Exception
/*    */ {
/*    */   private Version expectedFramingVersion;
/*    */   private Version expectedConnectionManagementVersion;
/*    */   
/*    */   public VersionMismatchException() {}
/*    */   
/*    */   public VersionMismatchException(String message, Version expectedFramingVersion, Version expectedConnectionManagementVersion) {
/* 58 */     super(message);
/* 59 */     this.expectedFramingVersion = expectedFramingVersion;
/* 60 */     this.expectedConnectionManagementVersion = expectedConnectionManagementVersion;
/*    */   }
/*    */   
/*    */   public Version getExpectedFramingVersion() {
/* 64 */     return this.expectedFramingVersion;
/*    */   }
/*    */   
/*    */   public Version getExpectedConnectionManagementVersion() {
/* 68 */     return this.expectedConnectionManagementVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\VersionMismatchException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */