/*    */ package com.sun.xml.ws.rx.mc.api;
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
/*    */ public class MakeConnectionSupportedFeatureBuilder
/*    */ {
/*    */   public static MakeConnectionSupportedFeatureBuilder getBuilder() {
/* 50 */     return new MakeConnectionSupportedFeatureBuilder();
/*    */   }
/*    */   
/* 53 */   private long mcRequestBaseInterval = 2000L;
/* 54 */   private long responseRetrievalTimeout = 600000L;
/*    */   
/*    */   public MakeConnectionSupportedFeatureBuilder mcRequestBaseInterval(long value) {
/* 57 */     this.mcRequestBaseInterval = value;
/*    */     
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public MakeConnectionSupportedFeatureBuilder responseRetrievalTimeout(long value) {
/* 63 */     this.responseRetrievalTimeout = value;
/*    */     
/* 65 */     return this;
/*    */   }
/*    */   
/*    */   public MakeConnectionSupportedFeature build() {
/* 69 */     return new MakeConnectionSupportedFeature(true, this.mcRequestBaseInterval, this.responseRetrievalTimeout);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\api\MakeConnectionSupportedFeatureBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */