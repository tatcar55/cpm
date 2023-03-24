/*     */ package ec.gob.cnt.ciberpasswordgestor.utils;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.MultiFormatWriter;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.client.j2se.MatrixToImageWriter;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import de.taimos.totp.TOTP;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Base64;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.commons.codec.binary.Base32;
/*     */ import org.apache.commons.codec.binary.Hex;
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
/*     */ public class Utils
/*     */ {
/*     */   public static final String COMPANY = "CNT";
/*  37 */   public static String KEY = "";
/*  38 */   public static String USER = "";
/*  39 */   public static String PASS = "";
/*  40 */   public static String IDPASS = "";
/*  41 */   public static String EKEY = "";
/*     */ 
/*     */   
/*     */   public static String createNewPass(String texto) {
/*  45 */     String result = "";
/*  46 */     char[] caracteres = texto.toCharArray();
/*     */ 
/*     */     
/*  49 */     for (char c : caracteres) {
/*  50 */       int i = c - 15;
/*  51 */       int j = c + 10;
/*  52 */       System.out.println("i:" + i);
/*  53 */       System.out.println("j:" + j);
/*  54 */       char ch = (char)i;
/*  55 */       result = result + ch;
/*  56 */       result = result + c;
/*  57 */       char l = (char)j;
/*  58 */       result = result + l;
/*  59 */       System.out.println("resul:" + result);
/*     */     } 
/*     */     
/*  62 */     System.out.println("new pass:" + result);
/*  63 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sKey(String pass) {
/*     */     try {
/*  69 */       DBConect dBConect = new DBConect();
/*  70 */       return Encrypt.decryptText(pass, dBConect.selectCuenta(Encrypt.encryptText(pass, "root")).getPassword());
/*  71 */     } catch (Exception ex) {
/*     */       
/*  73 */       System.out.println("Error skey");
/*     */       
/*  75 */       return "";
/*     */     } 
/*     */   }
/*     */   public static String otpKey(String pass) {
/*     */     try {
/*  80 */       DBConect dBConect = new DBConect();
/*  81 */       return Encrypt.decryptText(pass, dBConect.selectCuenta(Encrypt.encryptText(pass, "otp*")).getPassword());
/*  82 */     } catch (Exception ex) {
/*  83 */       Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */       
/*  85 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String generateSecretKey() {
/*  90 */     SecureRandom random = new SecureRandom();
/*  91 */     byte[] bytes = new byte[20];
/*  92 */     random.nextBytes(bytes);
/*  93 */     Base32 base32 = new Base32();
/*  94 */     return base32.encodeToString(bytes);
/*     */   }
/*     */   
/*     */   public static String generateKey() {
/*  98 */     String encodedKey = null;
/*     */     
/*     */     try {
/* 101 */       KeyGenerator keyGen = KeyGenerator.getInstance("AES");
/* 102 */       keyGen.init(256, new SecureRandom());
/* 103 */       SecretKey secretKey = keyGen.generateKey();
/*     */ 
/*     */       
/* 106 */       encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
/* 107 */       System.out.println(encodedKey);
/*     */     }
/* 109 */     catch (NoSuchAlgorithmException ex) {
/* 110 */       Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */     
/* 113 */     return encodedKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTOTPCode(String secretKey) {
/* 118 */     Base32 base32 = new Base32();
/* 119 */     byte[] bytes = base32.decode(secretKey);
/* 120 */     String hexKey = Hex.encodeHexString(bytes);
/* 121 */     return TOTP.getOTP(hexKey);
/*     */   }
/*     */   
/*     */   public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
/*     */     try {
/* 126 */       return "otpauth://totp/" + 
/* 127 */         URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20") + "?secret=" + 
/* 128 */         URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20") + "&issuer=" + 
/* 129 */         URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
/* 130 */     } catch (UnsupportedEncodingException e) {
/* 131 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void createQRCode(String barCodeData, String filePath, int height, int width) throws WriterException, IOException {
/* 137 */     BitMatrix matrix = (new MultiFormatWriter()).encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
/*     */     
/* 139 */     try (FileOutputStream out = new FileOutputStream(filePath)) {
/* 140 */       MatrixToImageWriter.writeToStream(matrix, "png", out);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void infinityGeneratingCodes(String secretKey) {
/* 145 */     String lastCode = null;
/*     */     while (true) {
/* 147 */       String code = getTOTPCode(secretKey);
/* 148 */       if (!code.equals(lastCode)) {
/* 149 */         System.out.println(code);
/*     */       }
/* 151 */       lastCode = code;
/*     */       try {
/* 153 */         Thread.sleep(1000L);
/* 154 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generatePassword() {
/* 160 */     String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&+=*!()-_,";
/* 161 */     String pwd = "";
/*     */     
/*     */     do {
/* 164 */       pwd = generateRandomString(characters, 13);
/* 165 */       System.out.println(pwd);
/*     */     }
/* 167 */     while (!validaPassword(pwd));
/*     */     
/* 169 */     System.out.println(pwd);
/* 170 */     return pwd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateRandomString(String validChars, int length) {
/* 176 */     SecureRandom random = new SecureRandom();
/* 177 */     StringBuilder sb = new StringBuilder(length);
/*     */     
/* 179 */     for (int i = 0; i < length; i++) {
/* 180 */       int randomIndex = random.nextInt(validChars.length());
/* 181 */       char randomChar = validChars.charAt(randomIndex);
/* 182 */       sb.append(randomChar);
/*     */     } 
/*     */     
/* 185 */     return sb.toString();
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
/*     */   public static boolean validaUserName(String userName) {
/* 228 */     String NAME_REGEX = "^[a-zA-Z0-9]{5,16}$";
/* 229 */     return userName.matches("^[a-zA-Z0-9]{5,16}$");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validaPassword(String password) {
/* 235 */     String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!()-_,])(?=\\S+$).{12,16}$";
/* 236 */     return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!()-_,])(?=\\S+$).{12,16}$");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\ec\gob\cnt\ciberpasswordgesto\\utils\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */