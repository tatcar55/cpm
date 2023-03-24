/*    */ package com.sun.xml.messaging.saaj.util;
/*    */ 
/*    */ import java.io.CharArrayWriter;
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
/*    */ public class CharWriter
/*    */   extends CharArrayWriter
/*    */ {
/*    */   public CharWriter() {}
/*    */   
/*    */   public CharWriter(int size) {
/* 53 */     super(size);
/*    */   }
/*    */   
/*    */   public char[] getChars() {
/* 57 */     return this.buf;
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 61 */     return this.count;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\CharWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */