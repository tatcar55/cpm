/*    */ package com.sun.xml.bind.marshaller;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinimumEscapeHandler
/*    */   implements CharacterEscapeHandler
/*    */ {
/* 57 */   public static final CharacterEscapeHandler theInstance = new MinimumEscapeHandler();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
/* 63 */     int limit = start + length;
/* 64 */     for (int i = start; i < limit; i++) {
/* 65 */       char c = ch[i];
/* 66 */       if (c == '&' || c == '<' || c == '>' || c == '\r' || (c == '"' && isAttVal)) {
/* 67 */         if (i != start)
/* 68 */           out.write(ch, start, i - start); 
/* 69 */         start = i + 1;
/* 70 */         switch (ch[i]) {
/*    */           case '&':
/* 72 */             out.write("&amp;");
/*    */             break;
/*    */           case '<':
/* 75 */             out.write("&lt;");
/*    */             break;
/*    */           case '>':
/* 78 */             out.write("&gt;");
/*    */             break;
/*    */           case '"':
/* 81 */             out.write("&quot;");
/*    */             break;
/*    */         } 
/*    */       
/*    */       } 
/*    */     } 
/* 87 */     if (start != limit)
/* 88 */       out.write(ch, start, limit - start); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\marshaller\MinimumEscapeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */