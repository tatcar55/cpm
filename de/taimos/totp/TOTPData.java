/*     */ package de.taimos.totp;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.apache.commons.codec.binary.Base32;
/*     */ 
/*     */ 
/*     */ public final class TOTPData
/*     */ {
/*   9 */   private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
/*  10 */   private static final Random rnd = new Random();
/*     */ 
/*     */   
/*     */   private final String issuer;
/*     */ 
/*     */   
/*     */   private final String user;
/*     */ 
/*     */   
/*     */   private final byte[] secret;
/*     */ 
/*     */   
/*     */   public TOTPData(String issuer, String user, byte[] secret) {
/*  23 */     this.issuer = issuer;
/*  24 */     this.user = user;
/*  25 */     this.secret = secret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TOTPData(byte[] secret) {
/*  32 */     this(null, null, secret);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssuer() {
/*  39 */     return this.issuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUser() {
/*  46 */     return this.user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getSecret() {
/*  53 */     return this.secret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecretAsHex() {
/*  62 */     char[] hexChars = new char[this.secret.length * 2];
/*  63 */     for (int j = 0; j < this.secret.length; j++) {
/*  64 */       int v = this.secret[j] & 0xFF;
/*  65 */       hexChars[j * 2] = hexArray[v >>> 4];
/*  66 */       hexChars[j * 2 + 1] = hexArray[v & 0xF];
/*     */     } 
/*  68 */     return new String(hexChars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecretAsBase32() {
/*  77 */     Base32 base = new Base32();
/*  78 */     return base.encodeToString(this.secret);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/*  87 */     String secretString = getSecretAsBase32();
/*  88 */     return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", new Object[] { this.issuer, this.user, secretString, this.issuer });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSerial() {
/*  97 */     return String.format("otpauth://totp/%s:%s", new Object[] { this.issuer, this.user });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TOTPData create() {
/* 104 */     return new TOTPData(createSecret());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TOTPData create(String issuer, String user) {
/* 113 */     return new TOTPData(issuer, user, createSecret());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] createSecret() {
/* 122 */     byte[] secret = new byte[20];
/* 123 */     rnd.nextBytes(secret);
/* 124 */     return secret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\de\taimos\totp\TOTPData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */