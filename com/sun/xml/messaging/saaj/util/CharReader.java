/*    */ package com.sun.xml.messaging.saaj.util;
/*    */ 
/*    */ import java.io.CharArrayReader;
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
/*    */ public class CharReader
/*    */   extends CharArrayReader
/*    */ {
/*    */   public CharReader(char[] buf, int length) {
/* 49 */     super(buf, 0, length);
/*    */   }
/*    */   
/*    */   public CharReader(char[] buf, int offset, int length) {
/* 53 */     super(buf, offset, length);
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\CharReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */