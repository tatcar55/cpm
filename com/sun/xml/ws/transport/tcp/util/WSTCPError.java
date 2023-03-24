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
/*    */ public class WSTCPError
/*    */ {
/*    */   private final int code;
/*    */   private final int subCode;
/*    */   private final String description;
/*    */   
/*    */   public static WSTCPError createCriticalError(int subCode, String description) {
/* 52 */     return new WSTCPError(0, subCode, description);
/*    */   }
/*    */   
/*    */   public static WSTCPError createNonCriticalError(int subCode, String description) {
/* 56 */     return new WSTCPError(1, subCode, description);
/*    */   }
/*    */   
/*    */   public static WSTCPError createError(int code, int subCode, String description) {
/* 60 */     return new WSTCPError(code, subCode, description);
/*    */   }
/*    */   
/*    */   private WSTCPError(int code, int subCode, String description) {
/* 64 */     this.code = code;
/* 65 */     this.subCode = subCode;
/* 66 */     this.description = description;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 70 */     return this.code;
/*    */   }
/*    */   
/*    */   public int getSubCode() {
/* 74 */     return this.subCode;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 78 */     return this.description;
/*    */   }
/*    */   
/*    */   public boolean isCritical() {
/* 82 */     return (this.code == 0);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 86 */     StringBuffer sb = new StringBuffer(100);
/* 87 */     sb.append("Code: ");
/* 88 */     sb.append(this.code);
/* 89 */     sb.append(" SubCode: ");
/* 90 */     sb.append(this.subCode);
/* 91 */     sb.append(" Description: ");
/* 92 */     sb.append(this.description);
/* 93 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\WSTCPError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */