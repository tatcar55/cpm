/*     */ package com.sun.xml.ws.security.opt.crypto.dsig.internal;
/*     */ 
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SignatureException;
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
/*     */ public class HmacSHA1
/*     */ {
/*     */   private static final int SHA1_BLOCK = 64;
/*     */   private byte[] key_opad;
/*     */   private MessageDigest digest;
/*     */   private int byte_length;
/*     */   
/*     */   public void init(Key key, int length) throws InvalidKeyException {
/*  77 */     if (key == null) {
/*  78 */       throw new InvalidKeyException("The key should not be null");
/*     */     }
/*     */     try {
/*  81 */       this.digest = MessageDigest.getInstance("SHA1");
/*  82 */       initialize(key);
/*  83 */     } catch (NoSuchAlgorithmException nsae) {
/*     */ 
/*     */       
/*  86 */       throw new InvalidKeyException("SHA1 not supported");
/*     */     } 
/*  88 */     if (length > 0) {
/*  89 */       this.byte_length = length / 8;
/*     */     } else {
/*     */       
/*  92 */       this.byte_length = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(byte[] data) {
/* 103 */     this.digest.update(data);
/*     */   }
/*     */   public void update(byte data) {
/* 106 */     this.digest.update(data);
/*     */   }
/*     */   public void update(byte[] data, int offset, int len) {
/* 109 */     this.digest.update(data, offset, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] sign() throws SignatureException {
/* 117 */     if (this.byte_length == 0) {
/* 118 */       throw new SignatureException("length should be -1 or greater than zero, but is " + this.byte_length);
/*     */     }
/*     */ 
/*     */     
/* 122 */     byte[] value = this.digest.digest();
/*     */     
/* 124 */     this.digest.reset();
/* 125 */     this.digest.update(this.key_opad);
/* 126 */     this.digest.update(value);
/* 127 */     byte[] result = this.digest.digest();
/*     */     
/* 129 */     if (this.byte_length > 0 && result.length > this.byte_length) {
/* 130 */       byte[] truncated = new byte[this.byte_length];
/* 131 */       System.arraycopy(result, 0, truncated, 0, this.byte_length);
/* 132 */       result = truncated;
/*     */     } 
/* 134 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verify(byte[] signature) throws SignatureException {
/* 143 */     return MessageDigest.isEqual(signature, sign());
/*     */   }
/*     */   
/*     */   private void initialize(Key key) {
/* 147 */     byte[] rawKey = key.getEncoded();
/* 148 */     byte[] normalizedKey = new byte[64];
/* 149 */     if (rawKey.length > 64) {
/* 150 */       this.digest.reset();
/* 151 */       rawKey = this.digest.digest(rawKey);
/*     */     } 
/* 153 */     System.arraycopy(rawKey, 0, normalizedKey, 0, rawKey.length);
/* 154 */     for (int i = rawKey.length; i < 64; i++) {
/* 155 */       normalizedKey[i] = 0;
/*     */     }
/* 157 */     byte[] key_ipad = new byte[64];
/* 158 */     this.key_opad = new byte[64];
/* 159 */     for (int j = 0; j < 64; j++) {
/* 160 */       key_ipad[j] = (byte)(normalizedKey[j] ^ 0x36);
/* 161 */       this.key_opad[j] = (byte)(normalizedKey[j] ^ 0x5C);
/*     */     } 
/*     */     
/* 164 */     this.digest.reset();
/* 165 */     this.digest.update(key_ipad);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\internal\HmacSHA1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */