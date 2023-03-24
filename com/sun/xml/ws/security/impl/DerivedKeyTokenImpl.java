/*     */ package com.sun.xml.ws.security.impl;
/*     */ 
/*     */ import com.sun.xml.ws.security.DerivedKeyToken;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ public class DerivedKeyTokenImpl
/*     */   implements DerivedKeyToken
/*     */ {
/*  66 */   private long length = 32L;
/*  67 */   private long offset = 0L; private String label; private byte[] secret;
/*  68 */   private long generation = 0L; private byte[] nonce; public DerivedKeyTokenImpl(long offset, long length, byte[] secret) {
/*  69 */     this; this.label = "WS-SecureConversationWS-SecureConversation";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.offset = offset;
/*  75 */     this.length = length;
/*  76 */     this.secret = secret;
/*     */     try {
/*  78 */       this.nonce = new byte[18];
/*  79 */       SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
/*  80 */       random.nextBytes(this.nonce);
/*  81 */     } catch (NoSuchAlgorithmException e) {
/*  82 */       throw new RuntimeException("No such algorithm found" + e.getMessage());
/*     */     } 
/*     */   }
/*     */   public DerivedKeyTokenImpl(long offset, long length, byte[] secret, byte[] nonce) {
/*     */     this;
/*     */     this.label = "WS-SecureConversationWS-SecureConversation";
/*  88 */     this.offset = offset;
/*  89 */     this.length = length;
/*  90 */     this.secret = secret;
/*  91 */     this.nonce = nonce;
/*     */   } public DerivedKeyTokenImpl(long offset, long length, byte[] secret, byte[] nonce, String label) {
/*     */     this;
/*     */     this.label = "WS-SecureConversationWS-SecureConversation";
/*  95 */     this.offset = offset;
/*  96 */     this.length = length;
/*  97 */     this.secret = secret;
/*  98 */     this.nonce = nonce;
/*  99 */     if (label != null)
/* 100 */       this.label = label; 
/*     */   }
/*     */   public DerivedKeyTokenImpl(long generation, byte[] secret) {
/*     */     this;
/*     */     this.label = "WS-SecureConversationWS-SecureConversation";
/* 105 */     this.generation = generation;
/* 106 */     this.secret = secret;
/*     */     try {
/* 108 */       this.nonce = new byte[18];
/* 109 */       SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
/* 110 */       random.nextBytes(this.nonce);
/* 111 */     } catch (NoSuchAlgorithmException e) {
/* 112 */       throw new RuntimeException("No such algorithm found" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getAlgorithm() {
/*     */     try {
/* 119 */       this; return new URI("http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1");
/* 120 */     } catch (URISyntaxException ex) {
/*     */ 
/*     */       
/* 123 */       return null;
/*     */     } 
/*     */   }
/*     */   public long getLength() {
/* 127 */     return this.length;
/*     */   }
/*     */   
/*     */   public long getOffset() {
/* 131 */     return this.offset;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 135 */     this; return "http://schemas.xmlsoap.org/ws/2005/02/sc/dk";
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getTokenValue() {
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   public long getGeneration() {
/* 144 */     return this.generation;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 148 */     return this.label;
/*     */   }
/*     */   
/*     */   public byte[] getNonce() {
/* 152 */     return this.nonce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey generateSymmetricKey(String algorithm) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
/* 159 */     byte[] temp = this.label.getBytes("UTF-8");
/* 160 */     byte[] seed = new byte[temp.length + this.nonce.length];
/* 161 */     System.arraycopy(temp, 0, seed, 0, temp.length);
/* 162 */     System.arraycopy(this.nonce, 0, seed, temp.length, this.nonce.length);
/*     */     
/* 164 */     byte[] tempBytes = SecurityUtil.P_SHA1(this.secret, seed, (int)(this.offset + this.length));
/* 165 */     byte[] key = new byte[(int)this.length];
/*     */     
/* 167 */     for (int i = 0; i < key.length; i++) {
/* 168 */       key[i] = tempBytes[i + (int)this.offset];
/*     */     }
/* 170 */     SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
/* 171 */     return keySpec;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\DerivedKeyTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */