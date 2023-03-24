/*     */ package com.google.zxing.datamatrix.encoder;
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
/*     */ final class EdifactEncoder
/*     */   implements Encoder
/*     */ {
/*     */   public int getEncodingMode() {
/*  23 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(EncoderContext context) {
/*  29 */     StringBuilder buffer = new StringBuilder();
/*  30 */     while (context.hasMoreCharacters()) {
/*  31 */       char c = context.getCurrentChar();
/*  32 */       encodeChar(c, buffer);
/*  33 */       context.pos++;
/*     */       
/*  35 */       int count = buffer.length();
/*  36 */       if (count >= 4) {
/*  37 */         context.writeCodewords(encodeToCodewords(buffer, 0));
/*  38 */         buffer.delete(0, 4);
/*     */         
/*  40 */         int newMode = HighLevelEncoder.lookAheadTest(context.getMessage(), context.pos, getEncodingMode());
/*  41 */         if (newMode != getEncodingMode()) {
/*  42 */           context.signalEncoderChange(0);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  47 */     buffer.append('\037');
/*  48 */     handleEOD(context, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleEOD(EncoderContext context, CharSequence buffer) {
/*     */     try {
/*  59 */       int count = buffer.length();
/*  60 */       if (count == 0) {
/*     */         return;
/*     */       }
/*  63 */       if (count == 1) {
/*     */         
/*  65 */         context.updateSymbolInfo();
/*  66 */         int available = context.getSymbolInfo().getDataCapacity() - context.getCodewordCount();
/*  67 */         int remaining = context.getRemainingCharacters();
/*  68 */         if (remaining == 0 && available <= 2) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/*  73 */       if (count > 4) {
/*  74 */         throw new IllegalStateException("Count must not exceed 4");
/*     */       }
/*  76 */       int restChars = count - 1;
/*  77 */       String encoded = encodeToCodewords(buffer, 0);
/*  78 */       boolean endOfSymbolReached = !context.hasMoreCharacters();
/*  79 */       boolean restInAscii = (endOfSymbolReached && restChars <= 2);
/*     */       
/*  81 */       if (restChars <= 2) {
/*  82 */         context.updateSymbolInfo(context.getCodewordCount() + restChars);
/*  83 */         int available = context.getSymbolInfo().getDataCapacity() - context.getCodewordCount();
/*  84 */         if (available >= 3) {
/*  85 */           restInAscii = false;
/*  86 */           context.updateSymbolInfo(context.getCodewordCount() + encoded.length());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  91 */       if (restInAscii) {
/*  92 */         context.resetSymbolInfo();
/*  93 */         context.pos -= restChars;
/*     */       } else {
/*  95 */         context.writeCodewords(encoded);
/*     */       } 
/*     */     } finally {
/*  98 */       context.signalEncoderChange(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void encodeChar(char c, StringBuilder sb) {
/* 103 */     if (c >= ' ' && c <= '?') {
/* 104 */       sb.append(c);
/* 105 */     } else if (c >= '@' && c <= '^') {
/* 106 */       sb.append((char)(c - 64));
/*     */     } else {
/* 108 */       HighLevelEncoder.illegalCharacter(c);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String encodeToCodewords(CharSequence sb, int startPos) {
/* 113 */     int len = sb.length() - startPos;
/* 114 */     if (len == 0) {
/* 115 */       throw new IllegalStateException("StringBuilder must not be empty");
/*     */     }
/* 117 */     char c1 = sb.charAt(startPos);
/* 118 */     char c2 = (len >= 2) ? sb.charAt(startPos + 1) : Character.MIN_VALUE;
/* 119 */     char c3 = (len >= 3) ? sb.charAt(startPos + 2) : Character.MIN_VALUE;
/* 120 */     char c4 = (len >= 4) ? sb.charAt(startPos + 3) : Character.MIN_VALUE;
/*     */     
/* 122 */     int v = (c1 << 18) + (c2 << 12) + (c3 << 6) + c4;
/* 123 */     char cw1 = (char)(v >> 16 & 0xFF);
/* 124 */     char cw2 = (char)(v >> 8 & 0xFF);
/* 125 */     char cw3 = (char)(v & 0xFF);
/* 126 */     StringBuilder res = new StringBuilder(3);
/* 127 */     res.append(cw1);
/* 128 */     if (len >= 2) {
/* 129 */       res.append(cw2);
/*     */     }
/* 131 */     if (len >= 3) {
/* 132 */       res.append(cw3);
/*     */     }
/* 134 */     return res.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\encoder\EdifactEncoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */