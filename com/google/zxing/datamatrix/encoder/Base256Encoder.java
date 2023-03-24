/*    */ package com.google.zxing.datamatrix.encoder;
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
/*    */ final class Base256Encoder
/*    */   implements Encoder
/*    */ {
/*    */   public int getEncodingMode() {
/* 23 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(EncoderContext context) {
/* 28 */     StringBuilder buffer = new StringBuilder();
/* 29 */     buffer.append(false);
/* 30 */     while (context.hasMoreCharacters()) {
/* 31 */       char c1 = context.getCurrentChar();
/* 32 */       buffer.append(c1);
/*    */       
/* 34 */       context.pos++;
/*    */       
/* 36 */       int newMode = HighLevelEncoder.lookAheadTest(context.getMessage(), context.pos, getEncodingMode());
/* 37 */       if (newMode != getEncodingMode()) {
/* 38 */         context.signalEncoderChange(newMode);
/*    */         break;
/*    */       } 
/*    */     } 
/* 42 */     int dataCount = buffer.length() - 1;
/* 43 */     int lengthFieldSize = 1;
/* 44 */     int currentSize = context.getCodewordCount() + dataCount + lengthFieldSize;
/* 45 */     context.updateSymbolInfo(currentSize);
/* 46 */     boolean mustPad = (context.getSymbolInfo().getDataCapacity() - currentSize > 0);
/* 47 */     if (context.hasMoreCharacters() || mustPad) {
/* 48 */       if (dataCount <= 249) {
/* 49 */         buffer.setCharAt(0, (char)dataCount);
/* 50 */       } else if (dataCount > 249 && dataCount <= 1555) {
/* 51 */         buffer.setCharAt(0, (char)(dataCount / 250 + 249));
/* 52 */         buffer.insert(1, (char)(dataCount % 250));
/*    */       } else {
/* 54 */         throw new IllegalStateException("Message length not in valid ranges: " + dataCount);
/*    */       } 
/*    */     }
/*    */     
/* 58 */     for (int i = 0, c = buffer.length(); i < c; i++) {
/* 59 */       context.writeCodeword(randomize255State(buffer
/* 60 */             .charAt(i), context.getCodewordCount() + 1));
/*    */     }
/*    */   }
/*    */   
/*    */   private static char randomize255State(char ch, int codewordPosition) {
/* 65 */     int pseudoRandom = 149 * codewordPosition % 255 + 1;
/* 66 */     int tempVariable = ch + pseudoRandom;
/* 67 */     if (tempVariable <= 255) {
/* 68 */       return (char)tempVariable;
/*    */     }
/* 70 */     return (char)(tempVariable - 256);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\encoder\Base256Encoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */