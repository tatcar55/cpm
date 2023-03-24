/*    */ package com.sun.istack;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import javax.activation.DataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ByteArrayDataSource
/*    */   implements DataSource
/*    */ {
/*    */   private final String contentType;
/*    */   private final byte[] buf;
/*    */   private final int len;
/*    */   
/*    */   public ByteArrayDataSource(byte[] buf, String contentType) {
/* 60 */     this(buf, buf.length, contentType);
/*    */   }
/*    */   public ByteArrayDataSource(byte[] buf, int length, String contentType) {
/* 63 */     this.buf = buf;
/* 64 */     this.len = length;
/* 65 */     this.contentType = contentType;
/*    */   }
/*    */   
/*    */   public String getContentType() {
/* 69 */     if (this.contentType == null)
/* 70 */       return "application/octet-stream"; 
/* 71 */     return this.contentType;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() {
/* 75 */     return new ByteArrayInputStream(this.buf, 0, this.len);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   public OutputStream getOutputStream() {
/* 83 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\ByteArrayDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */