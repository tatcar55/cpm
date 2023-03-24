/*     */ package com.sun.xml.ws.security.opt.crypto.dsig.internal;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncByteArrayOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DigesterOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private boolean buffer = false;
/*     */   private UnsyncByteArrayOutputStream bos;
/*     */   private final MessageDigest md;
/*  48 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigesterOutputStream(MessageDigest md) {
/*  57 */     this(md, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigesterOutputStream(MessageDigest md, boolean buffer) {
/*  67 */     this.md = md;
/*  68 */     this.buffer = buffer;
/*  69 */     if (buffer) {
/*  70 */       this.bos = new UnsyncByteArrayOutputStream();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] input) {
/*  76 */     write(input, 0, input.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int input) {
/*  81 */     if (this.buffer) {
/*  82 */       this.bos.write(input);
/*     */     }
/*  84 */     this.md.update((byte)input);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] input, int offset, int len) {
/*  89 */     if (this.buffer) {
/*  90 */       this.bos.write(input, offset, len);
/*     */     }
/*     */     
/*  93 */     if (log.isLoggable(Level.FINER)) {
/*  94 */       log.log(Level.FINER, "Pre-digested input:");
/*  95 */       StringBuffer sb = new StringBuffer(len);
/*  96 */       for (int i = offset; i < offset + len; i++) {
/*  97 */         sb.append((char)input[i]);
/*     */       }
/*  99 */       log.log(Level.FINER, sb.toString());
/*     */     } 
/* 101 */     this.md.update(input, offset, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDigestValue() {
/* 108 */     return this.md.digest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/* 116 */     if (this.buffer) {
/* 117 */       return new ByteArrayInputStream(this.bos.toByteArray());
/*     */     }
/* 119 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\internal\DigesterOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */