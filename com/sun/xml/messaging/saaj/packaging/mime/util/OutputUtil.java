/*    */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class OutputUtil
/*    */ {
/* 65 */   private static byte[] newline = new byte[] { 13, 10 };
/*    */   
/*    */   public static void writeln(String s, OutputStream out) throws IOException {
/* 68 */     writeAsAscii(s, out);
/* 69 */     writeln(out);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void writeAsAscii(String s, OutputStream out) throws IOException {
/* 76 */     int len = s.length();
/* 77 */     for (int i = 0; i < len; i++)
/* 78 */       out.write((byte)s.charAt(i)); 
/*    */   }
/*    */   
/*    */   public static void writeln(OutputStream out) throws IOException {
/* 82 */     out.write(newline);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\OutputUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */