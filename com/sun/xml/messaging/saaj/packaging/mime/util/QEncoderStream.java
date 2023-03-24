/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class QEncoderStream
/*     */   extends QPEncoderStream
/*     */ {
/*     */   private String specials;
/*  62 */   private static String WORD_SPECIALS = "=_?\"#$%&'(),.:;<>@[\\]^`{|}~";
/*  63 */   private static String TEXT_SPECIALS = "=_?";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QEncoderStream(OutputStream out, boolean encodingWord) {
/*  72 */     super(out, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.specials = encodingWord ? WORD_SPECIALS : TEXT_SPECIALS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/*  88 */     c &= 0xFF;
/*  89 */     if (c == 32) {
/*  90 */       output(95, false);
/*  91 */     } else if (c < 32 || c >= 127 || this.specials.indexOf(c) >= 0) {
/*     */       
/*  93 */       output(c, true);
/*     */     } else {
/*  95 */       output(c, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int encodedLength(byte[] b, boolean encodingWord) {
/* 102 */     int len = 0;
/* 103 */     String specials = encodingWord ? WORD_SPECIALS : TEXT_SPECIALS;
/* 104 */     for (int i = 0; i < b.length; i++) {
/* 105 */       int c = b[i] & 0xFF;
/* 106 */       if (c < 32 || c >= 127 || specials.indexOf(c) >= 0) {
/*     */         
/* 108 */         len += 3;
/*     */       } else {
/* 110 */         len++;
/*     */       } 
/* 112 */     }  return len;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\QEncoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */