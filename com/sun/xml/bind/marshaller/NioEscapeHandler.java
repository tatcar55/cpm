/*     */ package com.sun.xml.bind.marshaller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NioEscapeHandler
/*     */   implements CharacterEscapeHandler
/*     */ {
/*     */   private final CharsetEncoder encoder;
/*     */   
/*     */   public NioEscapeHandler(String charsetName) {
/*  75 */     this.encoder = Charset.forName(charsetName).newEncoder();
/*     */   }
/*     */   
/*     */   public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
/*  79 */     int limit = start + length;
/*  80 */     for (int i = start; i < limit; i++) {
/*  81 */       switch (ch[i]) {
/*     */         case '&':
/*  83 */           out.write("&amp;");
/*     */           break;
/*     */         case '<':
/*  86 */           out.write("&lt;");
/*     */           break;
/*     */         case '>':
/*  89 */           out.write("&gt;");
/*     */           break;
/*     */         case '"':
/*  92 */           if (isAttVal) {
/*  93 */             out.write("&quot;"); break;
/*     */           } 
/*  95 */           out.write(34);
/*     */           break;
/*     */         
/*     */         default:
/*  99 */           if (this.encoder.canEncode(ch[i])) {
/* 100 */             out.write(ch[i]); break;
/*     */           } 
/* 102 */           out.write("&#");
/* 103 */           out.write(Integer.toString(ch[i]));
/* 104 */           out.write(59);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\marshaller\NioEscapeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */