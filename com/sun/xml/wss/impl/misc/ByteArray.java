/*    */ package com.sun.xml.wss.impl.misc;
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
/*    */ public class ByteArray
/*    */ {
/* 58 */   byte[] iv = null;
/* 59 */   byte[] ed = null;
/* 60 */   int length = 0;
/*    */   
/*    */   public ByteArray(byte[] iv, byte[] ed) {
/* 63 */     this.iv = iv;
/* 64 */     this.ed = ed;
/* 65 */     if (this.iv != null) {
/* 66 */       this.length = iv.length;
/*    */     }
/* 68 */     if (this.ed != null) {
/* 69 */       this.length += ed.length;
/*    */     }
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 74 */     return this.length;
/*    */   }
/*    */   
/*    */   public byte byteAt(int i) {
/* 78 */     if (i < 0 || i > this.length) {
/* 79 */       throw new ArrayIndexOutOfBoundsException("Index " + i + " is out of range");
/*    */     }
/* 81 */     if (this.iv != null && i < this.iv.length)
/* 82 */       return this.iv[i]; 
/* 83 */     if (this.iv == null || this.iv.length == 0) {
/* 84 */       return this.ed[i];
/*    */     }
/* 86 */     int index = i - this.iv.length;
/* 87 */     return this.ed[index];
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\ByteArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */