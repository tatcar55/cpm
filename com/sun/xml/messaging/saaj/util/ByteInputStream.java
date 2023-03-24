/*    */ package com.sun.xml.messaging.saaj.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
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
/*    */ public class ByteInputStream
/*    */   extends ByteArrayInputStream
/*    */ {
/* 49 */   private static final byte[] EMPTY_ARRAY = new byte[0];
/*    */   
/*    */   public ByteInputStream() {
/* 52 */     this(EMPTY_ARRAY, 0);
/*    */   }
/*    */   
/*    */   public ByteInputStream(byte[] buf, int length) {
/* 56 */     super(buf, 0, length);
/*    */   }
/*    */   
/*    */   public ByteInputStream(byte[] buf, int offset, int length) {
/* 60 */     super(buf, offset, length);
/*    */   }
/*    */   
/*    */   public byte[] getBytes() {
/* 64 */     return this.buf;
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 68 */     return this.count;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 72 */     reset();
/*    */   }
/*    */   
/*    */   public void setBuf(byte[] buf) {
/* 76 */     this.buf = buf;
/* 77 */     this.pos = 0;
/* 78 */     this.count = buf.length;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\ByteInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */