/*    */ package com.google.zxing.oned.rss.expanded.decoders;
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
/*    */ final class BlockParsedResult
/*    */ {
/*    */   private final DecodedInformation decodedInformation;
/*    */   private final boolean finished;
/*    */   
/*    */   BlockParsedResult(boolean finished) {
/* 39 */     this(null, finished);
/*    */   }
/*    */   
/*    */   BlockParsedResult(DecodedInformation information, boolean finished) {
/* 43 */     this.finished = finished;
/* 44 */     this.decodedInformation = information;
/*    */   }
/*    */   
/*    */   DecodedInformation getDecodedInformation() {
/* 48 */     return this.decodedInformation;
/*    */   }
/*    */   
/*    */   boolean isFinished() {
/* 52 */     return this.finished;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\decoders\BlockParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */