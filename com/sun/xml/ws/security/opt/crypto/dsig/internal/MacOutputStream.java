/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.internal;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/* 34 */   private static final byte[] none = "error".getBytes();
/*    */   private final HmacSHA1 mac;
/*    */   
/*    */   public MacOutputStream(HmacSHA1 mac) {
/* 38 */     this.mac = mac;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] toByteArray() {
/* 43 */     return none;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] arg0) {
/* 48 */     this.mac.update(arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int arg0) {
/* 53 */     this.mac.update((byte)arg0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] arg0, int arg1, int arg2) {
/* 58 */     this.mac.update(arg0, arg1, arg2);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\internal\MacOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */