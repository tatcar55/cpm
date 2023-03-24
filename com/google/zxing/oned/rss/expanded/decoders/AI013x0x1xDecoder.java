/*     */ package com.google.zxing.oned.rss.expanded.decoders;
/*     */ 
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.common.BitArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AI013x0x1xDecoder
/*     */   extends AI01weightDecoder
/*     */ {
/*     */   private static final int HEADER_SIZE = 8;
/*     */   private static final int WEIGHT_SIZE = 20;
/*     */   private static final int DATE_SIZE = 16;
/*     */   private final String dateCode;
/*     */   private final String firstAIdigits;
/*     */   
/*     */   AI013x0x1xDecoder(BitArray information, String firstAIdigits, String dateCode) {
/*  46 */     super(information);
/*  47 */     this.dateCode = dateCode;
/*  48 */     this.firstAIdigits = firstAIdigits;
/*     */   }
/*     */ 
/*     */   
/*     */   public String parseInformation() throws NotFoundException {
/*  53 */     if (getInformation().getSize() != 84) {
/*  54 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/*  57 */     StringBuilder buf = new StringBuilder();
/*     */     
/*  59 */     encodeCompressedGtin(buf, 8);
/*  60 */     encodeCompressedWeight(buf, 48, 20);
/*  61 */     encodeCompressedDate(buf, 68);
/*     */     
/*  63 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void encodeCompressedDate(StringBuilder buf, int currentPos) {
/*  67 */     int numericDate = getGeneralDecoder().extractNumericValueFromBitArray(currentPos, 16);
/*  68 */     if (numericDate == 38400) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     buf.append('(');
/*  73 */     buf.append(this.dateCode);
/*  74 */     buf.append(')');
/*     */     
/*  76 */     int day = numericDate % 32;
/*  77 */     numericDate /= 32;
/*  78 */     int month = numericDate % 12 + 1;
/*  79 */     numericDate /= 12;
/*  80 */     int year = numericDate;
/*     */     
/*  82 */     if (year / 10 == 0) {
/*  83 */       buf.append('0');
/*     */     }
/*  85 */     buf.append(year);
/*  86 */     if (month / 10 == 0) {
/*  87 */       buf.append('0');
/*     */     }
/*  89 */     buf.append(month);
/*  90 */     if (day / 10 == 0) {
/*  91 */       buf.append('0');
/*     */     }
/*  93 */     buf.append(day);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addWeightCode(StringBuilder buf, int weight) {
/*  98 */     int lastAI = weight / 100000;
/*  99 */     buf.append('(');
/* 100 */     buf.append(this.firstAIdigits);
/* 101 */     buf.append(lastAI);
/* 102 */     buf.append(')');
/*     */   }
/*     */ 
/*     */   
/*     */   protected int checkWeight(int weight) {
/* 107 */     return weight % 100000;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\decoders\AI013x0x1xDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */