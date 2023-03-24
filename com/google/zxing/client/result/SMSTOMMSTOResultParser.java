/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.Result;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SMSTOMMSTOResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public SMSParsedResult parse(Result result) {
/* 35 */     String rawText = getMassagedText(result);
/* 36 */     if (!rawText.startsWith("smsto:") && !rawText.startsWith("SMSTO:") && 
/* 37 */       !rawText.startsWith("mmsto:") && !rawText.startsWith("MMSTO:")) {
/* 38 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 42 */     String number = rawText.substring(6);
/* 43 */     String body = null;
/* 44 */     int bodyStart = number.indexOf(':');
/* 45 */     if (bodyStart >= 0) {
/* 46 */       body = number.substring(bodyStart + 1);
/* 47 */       number = number.substring(0, bodyStart);
/*    */     } 
/* 49 */     return new SMSParsedResult(number, null, null, body);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\SMSTOMMSTOResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */