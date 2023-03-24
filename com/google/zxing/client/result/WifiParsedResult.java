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
/*    */ public final class WifiParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final String ssid;
/*    */   private final String networkEncryption;
/*    */   private final String password;
/*    */   private final boolean hidden;
/*    */   
/*    */   public WifiParsedResult(String networkEncryption, String ssid, String password) {
/* 30 */     this(networkEncryption, ssid, password, false);
/*    */   }
/*    */   
/*    */   public WifiParsedResult(String networkEncryption, String ssid, String password, boolean hidden) {
/* 34 */     super(ParsedResultType.WIFI);
/* 35 */     this.ssid = ssid;
/* 36 */     this.networkEncryption = networkEncryption;
/* 37 */     this.password = password;
/* 38 */     this.hidden = hidden;
/*    */   }
/*    */   
/*    */   public String getSsid() {
/* 42 */     return this.ssid;
/*    */   }
/*    */   
/*    */   public String getNetworkEncryption() {
/* 46 */     return this.networkEncryption;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 50 */     return this.password;
/*    */   }
/*    */   
/*    */   public boolean isHidden() {
/* 54 */     return this.hidden;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 59 */     StringBuilder result = new StringBuilder(80);
/* 60 */     maybeAppend(this.ssid, result);
/* 61 */     maybeAppend(this.networkEncryption, result);
/* 62 */     maybeAppend(this.password, result);
/* 63 */     maybeAppend(Boolean.toString(this.hidden), result);
/* 64 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\WifiParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */