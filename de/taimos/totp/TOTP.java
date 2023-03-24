/*     */ package de.taimos.totp;
/*     */ 
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.crypto.Mac;
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
/*     */ public final class TOTP
/*     */ {
/*     */   public static String getOTP(String key) {
/*  26 */     return getOTP(getStep(), key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validate(String key, String otp) {
/*  35 */     return validate(getStep(), key, otp);
/*     */   }
/*     */   
/*     */   private static boolean validate(long step, String key, String otp) {
/*  39 */     return (getOTP(step, key).equals(otp) || getOTP(step - 1L, key).equals(otp));
/*     */   }
/*     */ 
/*     */   
/*     */   private static long getStep() {
/*  44 */     return System.currentTimeMillis() / 30000L;
/*     */   }
/*     */   
/*     */   private static String getOTP(long step, String key) {
/*  48 */     String steps = Long.toHexString(step).toUpperCase();
/*  49 */     while (steps.length() < 16) {
/*  50 */       steps = "0" + steps;
/*     */     }
/*     */ 
/*     */     
/*  54 */     byte[] msg = hexStr2Bytes(steps);
/*  55 */     byte[] k = hexStr2Bytes(key);
/*     */     
/*  57 */     byte[] hash = hmac_sha1(k, msg);
/*     */ 
/*     */     
/*  60 */     int offset = hash[hash.length - 1] & 0xF;
/*  61 */     int binary = (hash[offset] & Byte.MAX_VALUE) << 24 | (hash[offset + 1] & 0xFF) << 16 | (hash[offset + 2] & 0xFF) << 8 | hash[offset + 3] & 0xFF;
/*  62 */     int otp = binary % 1000000;
/*     */     
/*  64 */     String result = Integer.toString(otp);
/*  65 */     while (result.length() < 6) {
/*  66 */       result = "0" + result;
/*     */     }
/*  68 */     return result;
/*     */   }
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
/*     */   private static byte[] hexStr2Bytes(String hex) {
/*  81 */     byte[] bArray = (new BigInteger("10" + hex, 16)).toByteArray();
/*  82 */     byte[] ret = new byte[bArray.length - 1];
/*     */ 
/*     */     
/*  85 */     System.arraycopy(bArray, 1, ret, 0, ret.length);
/*  86 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] hmac_sha1(byte[] keyBytes, byte[] text) {
/*     */     try {
/*  99 */       Mac hmac = Mac.getInstance("HmacSHA1");
/* 100 */       SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
/* 101 */       hmac.init(macKey);
/* 102 */       return hmac.doFinal(text);
/* 103 */     } catch (GeneralSecurityException gse) {
/* 104 */       throw new UndeclaredThrowableException(gse);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\de\taimos\totp\TOTP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */