/*    */ package com.sun.xml.txw2.output;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DumbEscapeHandler
/*    */   implements CharacterEscapeHandler
/*    */ {
/* 60 */   public static final CharacterEscapeHandler theInstance = new DumbEscapeHandler();
/*    */   
/*    */   public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
/* 63 */     int limit = start + length;
/* 64 */     for (int i = start; i < limit; i++) {
/* 65 */       switch (ch[i]) {
/*    */         case '&':
/* 67 */           out.write("&amp;");
/*    */           break;
/*    */         case '<':
/* 70 */           out.write("&lt;");
/*    */           break;
/*    */         case '>':
/* 73 */           out.write("&gt;");
/*    */           break;
/*    */         case '"':
/* 76 */           if (isAttVal) {
/* 77 */             out.write("&quot;"); break;
/*    */           } 
/* 79 */           out.write(34);
/*    */           break;
/*    */         
/*    */         default:
/* 83 */           if (ch[i] > '') {
/* 84 */             out.write("&#");
/* 85 */             out.write(Integer.toString(ch[i]));
/* 86 */             out.write(59); break;
/*    */           } 
/* 88 */           out.write(ch[i]);
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\DumbEscapeHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */