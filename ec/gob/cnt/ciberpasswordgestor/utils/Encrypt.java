/*    */ package ec.gob.cnt.ciberpasswordgestor.utils;
/*    */ 
/*    */ import java.util.Base64;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ import org.apache.commons.lang3.StringUtils;
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
/*    */ public class Encrypt
/*    */ {
/*    */   private static final String ALG = "AES";
/*    */   private static final String CI = "AES/CBC/PKCS5Padding";
/*    */   
/*    */   public static String encrypt(String key, String iv, String cleartext) throws Exception {
/* 40 */     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/* 41 */     SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
/* 42 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
/* 43 */     cipher.init(1, skeySpec, ivParameterSpec);
/* 44 */     byte[] encrypted = cipher.doFinal(cleartext.getBytes());
/* 45 */     return new String(Base64.encodeBase64(encrypted));
/*    */   }
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
/*    */   public static String decrypt(String key, String iv, String encrypted) throws Exception {
/* 58 */     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/* 59 */     SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
/* 60 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
/* 61 */     byte[] enc = Base64.decodeBase64(encrypted);
/* 62 */     cipher.init(2, skeySpec, ivParameterSpec);
/* 63 */     byte[] decrypted = cipher.doFinal(enc);
/* 64 */     return new String(decrypted);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String encryptText(String key, String cleartext) throws Exception {
/* 70 */     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/*    */     
/* 72 */     byte[] decodedKeyBytes = Base64.getDecoder().decode(key);
/* 73 */     SecretKey decodedKey = new SecretKeySpec(decodedKeyBytes, "AES");
/*    */     
/* 75 */     cipher.init(1, decodedKey, new IvParameterSpec(new byte[16]));
/* 76 */     byte[] encrypted = cipher.doFinal(cleartext.getBytes());
/* 77 */     return new String(Base64.encodeBase64(encrypted));
/*    */   }
/*    */   
/*    */   public static String decryptText(String key, String encrypted) throws Exception {
/* 81 */     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/*    */     
/* 83 */     byte[] decodedKeyBytes = Base64.getDecoder().decode(key);
/* 84 */     SecretKey decodedKey = new SecretKeySpec(decodedKeyBytes, "AES");
/*    */     
/* 86 */     byte[] enc = Base64.decodeBase64(encrypted);
/* 87 */     cipher.init(2, decodedKey, new IvParameterSpec(new byte[16]));
/* 88 */     byte[] decrypted = cipher.doFinal(enc);
/* 89 */     return new String(decrypted);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String keyPass(String pass) {
/* 95 */     String llavePass = pass + pass;
/* 96 */     String truncated = StringUtils.substring(llavePass, 1, 17);
/* 97 */     return Base64.getEncoder().encodeToString(truncated.getBytes());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgesto\\utils\Encrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */