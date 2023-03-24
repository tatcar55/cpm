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
/*    */ public final class GeoParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final double latitude;
/*    */   private final double longitude;
/*    */   private final double altitude;
/*    */   private final String query;
/*    */   
/*    */   GeoParsedResult(double latitude, double longitude, double altitude, String query) {
/* 30 */     super(ParsedResultType.GEO);
/* 31 */     this.latitude = latitude;
/* 32 */     this.longitude = longitude;
/* 33 */     this.altitude = altitude;
/* 34 */     this.query = query;
/*    */   }
/*    */   
/*    */   public String getGeoURI() {
/* 38 */     StringBuilder result = new StringBuilder();
/* 39 */     result.append("geo:");
/* 40 */     result.append(this.latitude);
/* 41 */     result.append(',');
/* 42 */     result.append(this.longitude);
/* 43 */     if (this.altitude > 0.0D) {
/* 44 */       result.append(',');
/* 45 */       result.append(this.altitude);
/*    */     } 
/* 47 */     if (this.query != null) {
/* 48 */       result.append('?');
/* 49 */       result.append(this.query);
/*    */     } 
/* 51 */     return result.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getLatitude() {
/* 58 */     return this.latitude;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getLongitude() {
/* 65 */     return this.longitude;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getAltitude() {
/* 72 */     return this.altitude;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getQuery() {
/* 79 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 84 */     StringBuilder result = new StringBuilder(20);
/* 85 */     result.append(this.latitude);
/* 86 */     result.append(", ");
/* 87 */     result.append(this.longitude);
/* 88 */     if (this.altitude > 0.0D) {
/* 89 */       result.append(", ");
/* 90 */       result.append(this.altitude);
/* 91 */       result.append('m');
/*    */     } 
/* 93 */     if (this.query != null) {
/* 94 */       result.append(" (");
/* 95 */       result.append(this.query);
/* 96 */       result.append(')');
/*    */     } 
/* 98 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\GeoParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */