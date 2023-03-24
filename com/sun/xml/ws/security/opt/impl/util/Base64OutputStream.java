/*    */ package com.sun.xml.ws.security.opt.impl.util;
/*    */ 
/*    */ import com.sun.xml.wss.impl.misc.Base64;
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
/*    */ public class Base64OutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   private OutputStream os;
/*    */   
/*    */   public Base64OutputStream(OutputStream os) {
/* 55 */     this.os = os;
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 59 */     Base64.encodeToStream(b, off, len, this.os);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] b) throws IOException {
/* 64 */     String data = Base64.encode(b);
/* 65 */     this.os.write(data.getBytes());
/*    */   }
/*    */   
/*    */   public void write(int b) throws IOException {
/* 69 */     this.os.write(b);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\Base64OutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */