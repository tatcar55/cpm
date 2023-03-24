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
/*    */ public final class WifiResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public WifiParsedResult parse(Result result) {
/* 35 */     String rawText = getMassagedText(result);
/* 36 */     if (!rawText.startsWith("WIFI:")) {
/* 37 */       return null;
/*    */     }
/* 39 */     String ssid = matchSinglePrefixedField("S:", rawText, ';', false);
/* 40 */     if (ssid == null || ssid.isEmpty()) {
/* 41 */       return null;
/*    */     }
/* 43 */     String pass = matchSinglePrefixedField("P:", rawText, ';', false);
/* 44 */     String type = matchSinglePrefixedField("T:", rawText, ';', false);
/* 45 */     if (type == null) {
/* 46 */       type = "nopass";
/*    */     }
/* 48 */     boolean hidden = Boolean.parseBoolean(matchSinglePrefixedField("H:", rawText, ';', false));
/* 49 */     return new WifiParsedResult(type, ssid, pass, hidden);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\WifiResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */