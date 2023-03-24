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
/*    */ abstract class AbstractDoCoMoResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   static String[] matchDoCoMoPrefixedField(String prefix, String rawText, boolean trim) {
/* 32 */     return matchPrefixedField(prefix, rawText, ';', trim);
/*    */   }
/*    */   
/*    */   static String matchSingleDoCoMoPrefixedField(String prefix, String rawText, boolean trim) {
/* 36 */     return matchSinglePrefixedField(prefix, rawText, ';', trim);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\AbstractDoCoMoResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */