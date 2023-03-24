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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeeOutputStream
/*    */   extends OutputStream
/*    */ {
/* 58 */   OutputStream tee = null;
/* 59 */   OutputStream out = null;
/*    */ 
/*    */   
/*    */   public TeeOutputStream(OutputStream chainedStream, OutputStream teeStream) {
/* 63 */     this.out = chainedStream;
/* 64 */     this.tee = teeStream;
/*    */   }
/*    */   
/*    */   public void write(int b) throws IOException {
/* 68 */     this.out.write(b);
/* 69 */     this.tee.write(b);
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 73 */     flush();
/* 74 */     this.out.close();
/* 75 */     this.tee.close();
/*    */   }
/*    */   
/*    */   public void flush() throws IOException {
/* 79 */     this.out.flush();
/* 80 */     this.tee.flush();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\TeeOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */