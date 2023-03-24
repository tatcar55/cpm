/*     */ package com.sun.xml.ws.security.impl;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.HmacSHA1;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SignatureException;
/*     */ import java.util.Random;
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
/*     */ 
/*     */ 
/*     */ public class PasswordDerivedKey
/*     */ {
/*  69 */   private byte[] salt = null;
/*  70 */   private final int keylength = 160;
/*  71 */   private byte[] sign = null;
/*     */ 
/*     */   
/*     */   private byte[] generateRandomSaltof15Bytes() {
/*  75 */     Random random = new Random();
/*  76 */     byte[] randomSalt = new byte[15];
/*  77 */     random.nextBytes(randomSalt);
/*  78 */     return randomSalt;
/*     */   }
/*     */   
/*     */   private void generate16ByteSalt() {
/*  82 */     this.salt = new byte[16];
/*  83 */     this.salt[0] = 0;
/*  84 */     byte[] temp = generateRandomSaltof15Bytes();
/*  85 */     for (int i = 1; i < 16; i++) {
/*  86 */       this.salt[i] = temp[i - 1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generate160BitKey(String password, int iteration, byte[] reqsalt) throws UnsupportedEncodingException {
/*  93 */     String saltencode = Base64.encode(reqsalt);
/*     */     
/*  95 */     byte[] keyof160bits = new byte[20];
/*  96 */     byte[] temp = password.getBytes();
/*  97 */     byte[] temp1 = saltencode.getBytes();
/*  98 */     byte[] input = new byte[temp1.length + temp.length];
/*     */     
/* 100 */     System.arraycopy(temp, 0, input, 0, temp.length);
/* 101 */     System.arraycopy(temp1, 0, input, temp.length, temp1.length);
/*     */     
/* 103 */     MessageDigest md = null;
/*     */     
/*     */     try {
/* 106 */       md = MessageDigest.getInstance("SHA1");
/* 107 */     } catch (NoSuchAlgorithmException ex) {
/* 108 */       throw new RuntimeException(ex);
/*     */     } 
/* 110 */     md.reset();
/* 111 */     md.update(input);
/* 112 */     keyof160bits = md.digest();
/*     */     
/* 114 */     for (int i = 2; i <= iteration; i++) {
/* 115 */       md.reset();
/* 116 */       md.update(keyof160bits);
/* 117 */       keyof160bits = md.digest();
/*     */     } 
/*     */     
/* 120 */     return keyof160bits;
/*     */   }
/*     */   
/*     */   public SecretKey generate16ByteKeyforEncryption(byte[] keyof20Bytes) {
/* 124 */     byte[] keyof16Bytes = new byte[16];
/* 125 */     for (int i = 0; i < 16; i++)
/* 126 */       keyof16Bytes[i] = keyof20Bytes[i]; 
/* 127 */     AuthenticationTokenPolicy.UsernameTokenBinding untBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/* 128 */     untBinding.setSecretKey(keyof16Bytes);
/* 129 */     SecretKey sKey = untBinding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm("http://www.w3.org/2001/04/xmlenc#aes128-cbc"));
/*     */     
/* 131 */     return sKey;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey generateDerivedKeyforEncryption(String password, String algorithm, int iteration) throws UnsupportedEncodingException {
/* 137 */     SecretKey keySpec = null;
/* 138 */     byte[] reqsalt = new byte[16];
/* 139 */     byte[] keyof128length = new byte[16];
/* 140 */     byte[] keyof160bits = new byte[20];
/* 141 */     if (this.salt == null) {
/* 142 */       this.salt = new byte[16];
/* 143 */       generate16ByteSalt();
/*     */     } 
/*     */     
/* 146 */     reqsalt[0] = 2; int i;
/* 147 */     for (i = 1; i < 16; i++) {
/* 148 */       reqsalt[i] = this.salt[i];
/*     */     }
/*     */     
/* 151 */     keyof160bits = generate160BitKey(password, iteration, reqsalt);
/* 152 */     for (i = 0; i < 16; i++) {
/* 153 */       keyof128length[i] = keyof160bits[i];
/*     */     }
/*     */     
/* 156 */     if (testAlgorithm(algorithm)) {
/* 157 */       keySpec = new SecretKeySpec(keyof128length, algorithm);
/*     */     } else {
/* 159 */       throw new RuntimeException("This Derived Key procedure doesnot support " + algorithm);
/*     */     } 
/* 161 */     return keySpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generateMAC(byte[] data, String password, int iteration) throws InvalidKeyException, SignatureException, UnsupportedEncodingException {
/* 169 */     SecretKey keySpec = null;
/* 170 */     byte[] reqsalt = new byte[16];
/* 171 */     byte[] keyof160bits = new byte[20];
/* 172 */     if (this.salt == null) {
/* 173 */       this.salt = new byte[16];
/* 174 */       generate16ByteSalt();
/*     */     } 
/* 176 */     reqsalt[0] = 1;
/* 177 */     for (int i = 1; i < 16; i++) {
/* 178 */       reqsalt[i] = this.salt[i];
/*     */     }
/* 180 */     keyof160bits = generate160BitKey(password, iteration, reqsalt);
/* 181 */     keySpec = new SecretKeySpec(keyof160bits, "AES");
/*     */     
/* 183 */     HmacSHA1 mac = new HmacSHA1();
/* 184 */     mac.init(keySpec, 160);
/* 185 */     mac.update(data);
/*     */     
/* 187 */     byte[] signature = mac.sign();
/*     */     
/* 189 */     return signature;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get16ByteSalt() {
/* 194 */     generate16ByteSalt();
/* 195 */     return this.salt;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecretKey verifyEncryptionKey(String password, int iterate, byte[] receivedSalt) throws UnsupportedEncodingException {
/* 200 */     byte[] keyof160bits = new byte[20];
/* 201 */     receivedSalt[0] = 2;
/* 202 */     keyof160bits = generate160BitKey(password, iterate, receivedSalt);
/* 203 */     byte[] keyof128length = new byte[16];
/* 204 */     for (int i = 0; i < 16; i++) {
/* 205 */       keyof128length[i] = keyof160bits[i];
/*     */     }
/* 207 */     SecretKey keySpec = new SecretKeySpec(keyof128length, "AES");
/* 208 */     return keySpec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyMACSignature(byte[] receivedSignature, byte[] data, String password, int iterate, byte[] receivedsalt) throws UnsupportedEncodingException, InvalidKeyException, SignatureException {
/* 214 */     receivedsalt[0] = 1;
/* 215 */     byte[] keyof160bits = generate160BitKey(password, iterate, receivedsalt);
/* 216 */     SecretKey keySpec = new SecretKeySpec(keyof160bits, "AES");
/*     */     
/* 218 */     HmacSHA1 mac = new HmacSHA1();
/* 219 */     mac.init(keySpec, 160);
/* 220 */     mac.update(data);
/*     */     
/* 222 */     byte[] signature = mac.sign();
/* 223 */     return MessageDigest.isEqual(receivedSignature, signature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean testAlgorithm(String algo) {
/* 230 */     if (algo.equalsIgnoreCase("AES") || algo.equalsIgnoreCase("Aes128") || algo.startsWith("A") || algo.startsWith("a")) {
/* 231 */       return true;
/*     */     }
/*     */     
/* 234 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\PasswordDerivedKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */