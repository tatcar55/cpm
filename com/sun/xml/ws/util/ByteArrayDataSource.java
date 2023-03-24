/*    */ package com.sun.xml.ws.util;
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
/*    */ 
/*    */ public final class ByteArrayDataSource
/*    */   implements DataSource
/*    */ {
/*    */   private final String contentType;
/*    */   private final byte[] buf;
/*    */   private final int start;
/*    */   private final int len;
/*    */   
/*    */   public ByteArrayDataSource(byte[] buf, String contentType) {
/* 62 */     this(buf, 0, buf.length, contentType);
/*    */   }
/*    */   public ByteArrayDataSource(byte[] buf, int length, String contentType) {
/* 65 */     this(buf, 0, length, contentType);
/*    */   }
/*    */   public ByteArrayDataSource(byte[] buf, int start, int length, String contentType) {
/* 68 */     this.buf = buf;
/* 69 */     this.start = start;
/* 70 */     this.len = length;
/* 71 */     this.contentType = contentType;
/*    */   }
/*    */   
/*    */   public String getContentType() {
/* 75 */     if (this.contentType == null)
/* 76 */       return "application/octet-stream"; 
/* 77 */     return this.contentType;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() {
/* 81 */     return new ByteArrayInputStream(this.buf, this.start, this.len);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 85 */     return null;
/*    */   }
/*    */   
/*    */   public OutputStream getOutputStream() {
/* 89 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\ByteArrayDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */