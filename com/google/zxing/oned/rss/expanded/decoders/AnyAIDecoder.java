/*    */ package com.google.zxing.oned.rss.expanded.decoders;
/*    */ 
/*    */ import com.google.zxing.FormatException;
/*    */ import com.google.zxing.NotFoundException;
/*    */ import com.google.zxing.common.BitArray;
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
/*    */ final class AnyAIDecoder
/*    */   extends AbstractExpandedDecoder
/*    */ {
/*    */   private static final int HEADER_SIZE = 5;
/*    */   
/*    */   AnyAIDecoder(BitArray information) {
/* 42 */     super(information);
/*    */   }
/*    */ 
/*    */   
/*    */   public String parseInformation() throws NotFoundException, FormatException {
/* 47 */     StringBuilder buf = new StringBuilder();
/* 48 */     return getGeneralDecoder().decodeAllCodes(buf, 5);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\decoders\AnyAIDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */