/*    */ package com.sun.xml.ws.security.opt.crypto.dsig.internal;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.security.Signature;
/*    */ import java.security.SignatureException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SignerOutputStream
/*    */   extends ByteArrayOutputStream
/*    */ {
/*    */   private final Signature sig;
/*    */   
/*    */   public SignerOutputStream(Signature sig) {
/* 40 */     this.sig = sig;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] arg0) {
/* 45 */     super.write(arg0, 0, arg0.length);
/*    */     try {
/* 47 */       this.sig.update(arg0);
/* 48 */     } catch (SignatureException e) {
/* 49 */       throw new RuntimeException("" + e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int arg0) {
/* 55 */     super.write(arg0);
/*    */     try {
/* 57 */       this.sig.update((byte)arg0);
/* 58 */     } catch (SignatureException e) {
/* 59 */       throw new RuntimeException("" + e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] arg0, int arg1, int arg2) {
/* 65 */     super.write(arg0, arg1, arg2);
/*    */     try {
/* 67 */       this.sig.update(arg0, arg1, arg2);
/* 68 */     } catch (SignatureException e) {
/* 69 */       throw new RuntimeException("" + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\internal\SignerOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */