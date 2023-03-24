/*    */ package com.sun.xml.ws.security.opt.impl.util;
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
/*    */ public class OutputStreamWrapper
/*    */   extends OutputStream
/*    */ {
/* 52 */   private OutputStream os = null;
/*    */   
/*    */   public OutputStreamWrapper(OutputStream stream) {
/* 55 */     this.os = stream;
/*    */   }
/*    */   
/*    */   public void write(int value) throws IOException {
/* 59 */     this.os.write(value);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/* 69 */     this.os.flush();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] value) throws IOException {
/* 74 */     this.os.write(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] value, int off, int len) throws IOException {
/* 79 */     this.os.write(value, off, len);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\OutputStreamWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */