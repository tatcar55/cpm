/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ParsedResult
/*    */ {
/*    */   private final ParsedResultType type;
/*    */   
/*    */   protected ParsedResult(ParsedResultType type) {
/* 35 */     this.type = type;
/*    */   }
/*    */   
/*    */   public final ParsedResultType getType() {
/* 39 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract String getDisplayResult();
/*    */   
/*    */   public final String toString() {
/* 46 */     return getDisplayResult();
/*    */   }
/*    */   
/*    */   public static void maybeAppend(String value, StringBuilder result) {
/* 50 */     if (value != null && !value.isEmpty()) {
/*    */       
/* 52 */       if (result.length() > 0) {
/* 53 */         result.append('\n');
/*    */       }
/* 55 */       result.append(value);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void maybeAppend(String[] values, StringBuilder result) {
/* 60 */     if (values != null)
/* 61 */       for (String value : values)
/* 62 */         maybeAppend(value, result);  
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */