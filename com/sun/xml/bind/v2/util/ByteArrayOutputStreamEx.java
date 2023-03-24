/*    */ package com.sun.xml.bind.v2.util;
/*    */ 
/*    */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ByteArrayOutputStreamEx
/*    */   extends ByteArrayOutputStream
/*    */ {
/*    */   public ByteArrayOutputStreamEx() {}
/*    */   
/*    */   public ByteArrayOutputStreamEx(int size) {
/* 57 */     super(size);
/*    */   }
/*    */   
/*    */   public void set(Base64Data dt, String mimeType) {
/* 61 */     dt.set(this.buf, this.count, mimeType);
/*    */   }
/*    */   
/*    */   public byte[] getBuffer() {
/* 65 */     return this.buf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readFrom(InputStream is) throws IOException {
/*    */     while (true) {
/* 73 */       if (this.count == this.buf.length) {
/*    */         
/* 75 */         byte[] data = new byte[this.buf.length * 2];
/* 76 */         System.arraycopy(this.buf, 0, data, 0, this.buf.length);
/* 77 */         this.buf = data;
/*    */       } 
/*    */       
/* 80 */       int sz = is.read(this.buf, this.count, this.buf.length - this.count);
/* 81 */       if (sz < 0)
/* 82 */         return;  this.count += sz;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\ByteArrayOutputStreamEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */