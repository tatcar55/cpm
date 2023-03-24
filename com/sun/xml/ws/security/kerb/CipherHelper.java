/*      */ package com.sun.xml.ws.security.kerb;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.CipherInputStream;
/*      */ import javax.crypto.CipherOutputStream;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.spec.IvParameterSpec;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import org.ietf.jgss.GSSException;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.internal.crypto.Aes128;
/*      */ import sun.security.krb5.internal.crypto.Aes256;
/*      */ import sun.security.krb5.internal.crypto.ArcFourHmac;
/*      */ import sun.security.krb5.internal.crypto.Des3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class CipherHelper
/*      */ {
/*      */   private static final int KG_USAGE_SEAL = 22;
/*      */   private static final int KG_USAGE_SIGN = 23;
/*      */   private static final int KG_USAGE_SEQ = 24;
/*      */   private static final int DES_CHECKSUM_SIZE = 8;
/*      */   private static final int DES_IV_SIZE = 8;
/*      */   private static final int AES_IV_SIZE = 16;
/*      */   private static final int HMAC_CHECKSUM_SIZE = 8;
/*      */   private static final int KG_USAGE_SIGN_MS = 15;
/*   49 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   private static final byte[] ZERO_IV = new byte[8];
/*   56 */   private static final byte[] ZERO_IV_AES = new byte[16];
/*      */   
/*      */   private int etype;
/*      */   
/*      */   private int sgnAlg;
/*      */   
/*      */   private int sealAlg;
/*      */   private byte[] keybytes;
/*   64 */   private int proto = 0;
/*      */   
/*      */   CipherHelper(EncryptionKey key) throws GSSException {
/*   67 */     this.etype = key.getEType();
/*   68 */     this.keybytes = key.getBytes();
/*      */     
/*   70 */     switch (this.etype) {
/*      */       case 1:
/*      */       case 3:
/*   73 */         this.sgnAlg = 0;
/*   74 */         this.sealAlg = 0;
/*      */         return;
/*      */       
/*      */       case 16:
/*   78 */         this.sgnAlg = 1024;
/*   79 */         this.sealAlg = 512;
/*      */         return;
/*      */       
/*      */       case 23:
/*   83 */         this.sgnAlg = 4352;
/*   84 */         this.sealAlg = 4096;
/*      */         return;
/*      */       
/*      */       case 17:
/*      */       case 18:
/*   89 */         this.sgnAlg = -1;
/*   90 */         this.sealAlg = -1;
/*   91 */         this.proto = 1;
/*      */         return;
/*      */     } 
/*      */     
/*   95 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getSgnAlg() {
/*  101 */     return this.sgnAlg;
/*      */   }
/*      */   
/*      */   int getSealAlg() {
/*  105 */     return this.sealAlg;
/*      */   }
/*      */   
/*      */   int getProto() {
/*  109 */     return this.proto;
/*      */   }
/*      */   
/*      */   int getEType() {
/*  113 */     return this.etype;
/*      */   }
/*      */   
/*      */   boolean isArcFour() {
/*  117 */     boolean flag = false;
/*  118 */     if (this.etype == 23) {
/*  119 */       flag = true;
/*      */     }
/*  121 */     return flag; } byte[] calculateChecksum(int alg, byte[] header, byte[] trailer, byte[] data, int start, int len, int tokenId) throws GSSException { byte[] buf;
/*      */     int offset;
/*      */     int total;
/*      */     byte[] buffer;
/*      */     int off;
/*      */     int tot;
/*  127 */     switch (alg) {
/*      */       
/*      */       case 0:
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  134 */           MessageDigest md5 = MessageDigest.getInstance("MD5");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  140 */           md5.update(header);
/*      */ 
/*      */           
/*  143 */           md5.update(data, start, len);
/*      */           
/*  145 */           if (trailer != null)
/*      */           {
/*      */ 
/*      */             
/*  149 */             md5.update(trailer);
/*      */           }
/*      */ 
/*      */           
/*  153 */           data = md5.digest();
/*  154 */           start = 0;
/*  155 */           len = data.length;
/*      */ 
/*      */           
/*  158 */           header = null;
/*  159 */           trailer = null;
/*  160 */         } catch (NoSuchAlgorithmException e) {
/*  161 */           GSSException ge = new GSSException(11, -1, "Could not get MD5 Message Digest - " + e.getMessage());
/*      */           
/*  163 */           ge.initCause(e);
/*  164 */           throw ge;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 512:
/*  169 */         return getDesCbcChecksum(this.keybytes, header, data, start, len);
/*      */ 
/*      */ 
/*      */       
/*      */       case 1024:
/*  174 */         if (header == null && trailer == null) {
/*  175 */           buf = data;
/*  176 */           total = len;
/*  177 */           offset = start;
/*      */         } else {
/*  179 */           total = ((header != null) ? header.length : 0) + len + ((trailer != null) ? trailer.length : 0);
/*      */ 
/*      */           
/*  182 */           buf = new byte[total];
/*  183 */           int pos = 0;
/*  184 */           if (header != null) {
/*  185 */             System.arraycopy(header, 0, buf, 0, header.length);
/*  186 */             pos = header.length;
/*      */           } 
/*  188 */           System.arraycopy(data, start, buf, pos, len);
/*  189 */           pos += len;
/*  190 */           if (trailer != null) {
/*  191 */             System.arraycopy(trailer, 0, buf, pos, trailer.length);
/*      */           }
/*      */           
/*  194 */           offset = 0;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  212 */           byte[] answer = Des3.calculateChecksum(this.keybytes, 23, buf, offset, total);
/*      */ 
/*      */ 
/*      */           
/*  216 */           return answer;
/*  217 */         } catch (GeneralSecurityException e) {
/*  218 */           GSSException ge = new GSSException(11, -1, "Could not use HMAC-SHA1-DES3-KD signing algorithm - " + e.getMessage());
/*      */ 
/*      */           
/*  221 */           ge.initCause(e);
/*  222 */           throw ge;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  228 */         if (header == null && trailer == null) {
/*  229 */           buffer = data;
/*  230 */           tot = len;
/*  231 */           off = start;
/*      */         } else {
/*  233 */           tot = ((header != null) ? header.length : 0) + len + ((trailer != null) ? trailer.length : 0);
/*      */ 
/*      */           
/*  236 */           buffer = new byte[tot];
/*  237 */           int pos = 0;
/*      */           
/*  239 */           if (header != null) {
/*  240 */             System.arraycopy(header, 0, buffer, 0, header.length);
/*  241 */             pos = header.length;
/*      */           } 
/*  243 */           System.arraycopy(data, start, buffer, pos, len);
/*  244 */           pos += len;
/*  245 */           if (trailer != null) {
/*  246 */             System.arraycopy(trailer, 0, buffer, pos, trailer.length);
/*      */           }
/*      */           
/*  249 */           off = 0;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  271 */           int key_usage = 23;
/*  272 */           if (tokenId == 257) {
/*  273 */             key_usage = 15;
/*      */           }
/*  275 */           byte[] answer = ArcFourHmac.calculateChecksum(this.keybytes, key_usage, buffer, off, tot);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  281 */           byte[] output = new byte[getChecksumLength()];
/*  282 */           System.arraycopy(answer, 0, output, 0, output.length);
/*      */ 
/*      */           
/*  285 */           return output;
/*  286 */         } catch (GeneralSecurityException e) {
/*  287 */           GSSException ge = new GSSException(11, -1, "Could not use HMAC_MD5_ARCFOUR signing algorithm - " + e.getMessage());
/*      */ 
/*      */           
/*  290 */           ge.initCause(e);
/*  291 */           throw ge;
/*      */         } 
/*      */     } 
/*      */     
/*  295 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] calculateChecksum(byte[] header, byte[] data, int start, int len, int key_usage) throws GSSException {
/*  305 */     int total = ((header != null) ? header.length : 0) + len;
/*      */ 
/*      */     
/*  308 */     byte[] buf = new byte[total];
/*      */ 
/*      */     
/*  311 */     System.arraycopy(data, start, buf, 0, len);
/*      */ 
/*      */     
/*  314 */     if (header != null) {
/*  315 */       System.arraycopy(header, 0, buf, len, header.length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  320 */     switch (this.etype) {
/*      */       case 17:
/*      */         try {
/*  323 */           byte[] answer = Aes128.calculateChecksum(this.keybytes, key_usage, buf, 0, total);
/*      */ 
/*      */ 
/*      */           
/*  327 */           return answer;
/*  328 */         } catch (GeneralSecurityException e) {
/*  329 */           GSSException ge = new GSSException(11, -1, "Could not use AES128 signing algorithm - " + e.getMessage());
/*      */ 
/*      */           
/*  332 */           ge.initCause(e);
/*  333 */           throw ge;
/*      */         } 
/*      */       
/*      */       case 18:
/*      */         try {
/*  338 */           byte[] answer = Aes256.calculateChecksum(this.keybytes, key_usage, buf, 0, total);
/*      */ 
/*      */ 
/*      */           
/*  342 */           return answer;
/*  343 */         } catch (GeneralSecurityException e) {
/*  344 */           GSSException ge = new GSSException(11, -1, "Could not use AES256 signing algorithm - " + e.getMessage());
/*      */ 
/*      */           
/*  347 */           ge.initCause(e);
/*  348 */           throw ge;
/*      */         } 
/*      */     } 
/*      */     
/*  352 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] encryptSeq(byte[] ivec, byte[] plaintext, int start, int len) throws GSSException {
/*      */     byte[] iv;
/*      */     byte[] checksum;
/*  360 */     switch (this.sgnAlg) {
/*      */       case 0:
/*      */       case 512:
/*      */         try {
/*  364 */           Cipher des = getInitializedDes(true, this.keybytes, ivec);
/*  365 */           return des.doFinal(plaintext, start, len);
/*      */         }
/*  367 */         catch (GeneralSecurityException e) {
/*  368 */           GSSException ge = new GSSException(11, -1, "Could not encrypt sequence number using DES - " + e.getMessage());
/*      */ 
/*      */           
/*  371 */           ge.initCause(e);
/*  372 */           throw ge;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 1024:
/*  377 */         if (ivec.length == 8) {
/*  378 */           iv = ivec;
/*      */         } else {
/*  380 */           iv = new byte[8];
/*  381 */           System.arraycopy(ivec, 0, iv, 0, 8);
/*      */         } 
/*      */         try {
/*  384 */           return Des3.encryptRaw(this.keybytes, 24, iv, plaintext, start, len);
/*      */         }
/*  386 */         catch (Exception e) {
/*      */           
/*  388 */           GSSException ge = new GSSException(11, -1, "Could not encrypt sequence number using DES3-KD - " + e.getMessage());
/*      */ 
/*      */           
/*  391 */           ge.initCause(e);
/*  392 */           throw ge;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  398 */         if (ivec.length == 8) {
/*  399 */           checksum = ivec;
/*      */         } else {
/*  401 */           checksum = new byte[8];
/*  402 */           System.arraycopy(ivec, 0, checksum, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  406 */           return ArcFourHmac.encryptSeq(this.keybytes, 24, checksum, plaintext, start, len);
/*      */         }
/*  408 */         catch (Exception e) {
/*      */           
/*  410 */           GSSException ge = new GSSException(11, -1, "Could not encrypt sequence number using RC4-HMAC - " + e.getMessage());
/*      */ 
/*      */           
/*  413 */           ge.initCause(e);
/*  414 */           throw ge;
/*      */         } 
/*      */     } 
/*      */     
/*  418 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] decryptSeq(byte[] ivec, byte[] ciphertext, int start, int len) throws GSSException {
/*      */     byte[] iv;
/*      */     byte[] checksum;
/*  426 */     switch (this.sgnAlg) {
/*      */       case 0:
/*      */       case 512:
/*      */         try {
/*  430 */           Cipher des = getInitializedDes(false, this.keybytes, ivec);
/*  431 */           return des.doFinal(ciphertext, start, len);
/*  432 */         } catch (GeneralSecurityException e) {
/*  433 */           GSSException ge = new GSSException(11, -1, "Could not decrypt sequence number using DES - " + e.getMessage());
/*      */ 
/*      */           
/*  436 */           ge.initCause(e);
/*  437 */           throw ge;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 1024:
/*  442 */         if (ivec.length == 8) {
/*  443 */           iv = ivec;
/*      */         } else {
/*  445 */           iv = new byte[8];
/*  446 */           System.arraycopy(ivec, 0, iv, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  450 */           return Des3.decryptRaw(this.keybytes, 24, iv, ciphertext, start, len);
/*      */         }
/*  452 */         catch (Exception e) {
/*      */           
/*  454 */           GSSException ge = new GSSException(11, -1, "Could not decrypt sequence number using DES3-KD - " + e.getMessage());
/*      */ 
/*      */           
/*  457 */           ge.initCause(e);
/*  458 */           throw ge;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  464 */         if (ivec.length == 8) {
/*  465 */           checksum = ivec;
/*      */         } else {
/*  467 */           checksum = new byte[8];
/*  468 */           System.arraycopy(ivec, 0, checksum, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  472 */           return ArcFourHmac.decryptSeq(this.keybytes, 24, checksum, ciphertext, start, len);
/*      */         }
/*  474 */         catch (Exception e) {
/*      */           
/*  476 */           GSSException ge = new GSSException(11, -1, "Could not decrypt sequence number using RC4-HMAC - " + e.getMessage());
/*      */ 
/*      */           
/*  479 */           ge.initCause(e);
/*  480 */           throw ge;
/*      */         } 
/*      */     } 
/*      */     
/*  484 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getChecksumLength() throws GSSException {
/*  490 */     switch (this.etype) {
/*      */       case 1:
/*      */       case 3:
/*  493 */         return 8;
/*      */       
/*      */       case 16:
/*  496 */         return Des3.getChecksumLength();
/*      */       
/*      */       case 17:
/*  499 */         return Aes128.getChecksumLength();
/*      */       case 18:
/*  501 */         return Aes256.getChecksumLength();
/*      */ 
/*      */       
/*      */       case 23:
/*  505 */         return 8;
/*      */     } 
/*      */     
/*  508 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken token, byte[] ciphertext, int cStart, int cLen, byte[] plaintext, int pStart) throws GSSException {
/*  521 */     switch (this.sealAlg) {
/*      */       case 0:
/*  523 */         desCbcDecrypt(token, getDesEncryptionKey(this.keybytes), ciphertext, cStart, cLen, plaintext, pStart);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 512:
/*  528 */         des3KdDecrypt(token, ciphertext, cStart, cLen, plaintext, pStart);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  532 */         arcFourDecrypt(token, ciphertext, cStart, cLen, plaintext, pStart);
/*      */         return;
/*      */     } 
/*      */     
/*  536 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken_v2 token, byte[] ciphertext, int cStart, int cLen, byte[] plaintext, int pStart, int key_usage) throws GSSException {
/*  551 */     switch (this.etype) {
/*      */       case 17:
/*  553 */         aes128Decrypt(token, ciphertext, cStart, cLen, plaintext, pStart, key_usage);
/*      */         return;
/*      */       
/*      */       case 18:
/*  557 */         aes256Decrypt(token, ciphertext, cStart, cLen, plaintext, pStart, key_usage);
/*      */         return;
/*      */     } 
/*      */     
/*  561 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken token, InputStream cipherStream, int cLen, byte[] plaintext, int pStart) throws GSSException, IOException {
/*      */     byte[] ciphertext;
/*      */     byte[] ctext;
/*  570 */     switch (this.sealAlg) {
/*      */       case 0:
/*  572 */         desCbcDecrypt(token, getDesEncryptionKey(this.keybytes), cipherStream, cLen, plaintext, pStart);
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 512:
/*  579 */         ciphertext = new byte[cLen];
/*      */         try {
/*  581 */           Krb5Token.readFully(cipherStream, ciphertext, 0, cLen);
/*  582 */         } catch (IOException e) {
/*  583 */           GSSException ge = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */           
/*  586 */           ge.initCause(e);
/*  587 */           throw ge;
/*      */         } 
/*      */         
/*  590 */         des3KdDecrypt(token, ciphertext, 0, cLen, plaintext, pStart);
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4096:
/*  596 */         ctext = new byte[cLen];
/*      */         try {
/*  598 */           Krb5Token.readFully(cipherStream, ctext, 0, cLen);
/*  599 */         } catch (IOException e) {
/*  600 */           GSSException ge = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */           
/*  603 */           ge.initCause(e);
/*  604 */           throw ge;
/*      */         } 
/*      */         
/*  607 */         arcFourDecrypt(token, ctext, 0, cLen, plaintext, pStart);
/*      */         return;
/*      */     } 
/*      */     
/*  611 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken_v2 token, InputStream cipherStream, int cLen, byte[] plaintext, int pStart, int key_usage) throws GSSException, IOException {
/*  621 */     byte[] ciphertext = new byte[cLen];
/*      */     try {
/*  623 */       Krb5Token.readFully(cipherStream, ciphertext, 0, cLen);
/*  624 */     } catch (IOException e) {
/*  625 */       GSSException ge = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */       
/*  628 */       ge.initCause(e);
/*  629 */       throw ge;
/*      */     } 
/*  631 */     switch (this.etype) {
/*      */       case 17:
/*  633 */         aes128Decrypt(token, ciphertext, 0, cLen, plaintext, pStart, key_usage);
/*      */         return;
/*      */       
/*      */       case 18:
/*  637 */         aes256Decrypt(token, ciphertext, 0, cLen, plaintext, pStart, key_usage);
/*      */         return;
/*      */     } 
/*      */     
/*  641 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */   
/*      */   void encryptData(WrapToken token, byte[] confounder, byte[] plaintext, int start, int len, byte[] padding, OutputStream os) throws GSSException, IOException {
/*      */     Cipher des;
/*      */     CipherOutputStream cos;
/*      */     byte[] ctext;
/*      */     byte[] ciphertext;
/*  650 */     switch (this.sealAlg) {
/*      */       
/*      */       case 0:
/*  653 */         des = getInitializedDes(true, getDesEncryptionKey(this.keybytes), ZERO_IV);
/*      */         
/*  655 */         cos = new CipherOutputStream(os, des);
/*      */         
/*  657 */         cos.write(confounder);
/*      */         
/*  659 */         cos.write(plaintext, start, len);
/*      */         
/*  661 */         cos.write(padding);
/*      */         return;
/*      */       
/*      */       case 512:
/*  665 */         ctext = des3KdEncrypt(confounder, plaintext, start, len, padding);
/*      */ 
/*      */ 
/*      */         
/*  669 */         os.write(ctext);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  673 */         ciphertext = arcFourEncrypt(token, confounder, plaintext, start, len, padding);
/*      */ 
/*      */ 
/*      */         
/*  677 */         os.write(ciphertext);
/*      */         return;
/*      */     } 
/*      */     
/*  681 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void encryptData(WrapToken_v2 token, byte[] confounder, byte[] tokenHeader, byte[] plaintext, int start, int len, int key_usage, OutputStream os) throws GSSException, IOException {
/*  699 */     byte[] ctext = null;
/*  700 */     switch (this.etype) {
/*      */       case 17:
/*  702 */         ctext = aes128Encrypt(confounder, tokenHeader, plaintext, start, len, key_usage);
/*      */         break;
/*      */       
/*      */       case 18:
/*  706 */         ctext = aes256Encrypt(confounder, tokenHeader, plaintext, start, len, key_usage);
/*      */         break;
/*      */       
/*      */       default:
/*  710 */         throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  717 */     os.write(ctext);
/*      */   }
/*      */   void encryptData(WrapToken token, byte[] confounder, byte[] plaintext, int pStart, int pLen, byte[] padding, byte[] ciphertext, int cStart) throws GSSException {
/*      */     int pos;
/*      */     Cipher des;
/*      */     byte[] ctext;
/*      */     byte[] ctext2;
/*  724 */     switch (this.sealAlg) {
/*      */       case 0:
/*  726 */         pos = cStart;
/*      */         
/*  728 */         des = getInitializedDes(true, getDesEncryptionKey(this.keybytes), ZERO_IV);
/*      */ 
/*      */         
/*      */         try {
/*  732 */           pos += des.update(confounder, 0, confounder.length, ciphertext, pos);
/*      */ 
/*      */           
/*  735 */           pos += des.update(plaintext, pStart, pLen, ciphertext, pos);
/*      */ 
/*      */           
/*  738 */           des.update(padding, 0, padding.length, ciphertext, pos);
/*      */           
/*  740 */           des.doFinal();
/*  741 */         } catch (GeneralSecurityException e) {
/*  742 */           GSSException ge = new GSSException(11, -1, "Could not use DES Cipher - " + e.getMessage());
/*      */           
/*  744 */           ge.initCause(e);
/*  745 */           throw ge;
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 512:
/*  750 */         ctext = des3KdEncrypt(confounder, plaintext, pStart, pLen, padding);
/*      */         
/*  752 */         System.arraycopy(ctext, 0, ciphertext, cStart, ctext.length);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  756 */         ctext2 = arcFourEncrypt(token, confounder, plaintext, pStart, pLen, padding);
/*      */         
/*  758 */         System.arraycopy(ctext2, 0, ciphertext, cStart, ctext2.length);
/*      */         return;
/*      */     } 
/*      */     
/*  762 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int encryptData(WrapToken_v2 token, byte[] confounder, byte[] tokenHeader, byte[] plaintext, int pStart, int pLen, byte[] ciphertext, int cStart, int key_usage) throws GSSException {
/*  780 */     byte[] ctext = null;
/*  781 */     switch (this.etype) {
/*      */       case 17:
/*  783 */         ctext = aes128Encrypt(confounder, tokenHeader, plaintext, pStart, pLen, key_usage);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  794 */         System.arraycopy(ctext, 0, ciphertext, cStart, ctext.length);
/*  795 */         return ctext.length;case 18: ctext = aes256Encrypt(confounder, tokenHeader, plaintext, pStart, pLen, key_usage); System.arraycopy(ctext, 0, ciphertext, cStart, ctext.length); return ctext.length;
/*      */     } 
/*      */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getDesCbcChecksum(byte[] key, byte[] header, byte[] data, int offset, int len) throws GSSException {
/*  819 */     Cipher des = getInitializedDes(true, key, ZERO_IV);
/*      */     
/*  821 */     int blockSize = des.getBlockSize();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  829 */     byte[] finalBlock = new byte[blockSize];
/*      */     
/*  831 */     int numBlocks = len / blockSize;
/*  832 */     int lastBytes = len % blockSize;
/*  833 */     if (lastBytes == 0) {
/*      */       
/*  835 */       numBlocks--;
/*  836 */       System.arraycopy(data, offset + numBlocks * blockSize, finalBlock, 0, blockSize);
/*      */     } else {
/*      */       
/*  839 */       System.arraycopy(data, offset + numBlocks * blockSize, finalBlock, 0, lastBytes);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  845 */       byte[] temp = new byte[Math.max(blockSize, (header == null) ? blockSize : header.length)];
/*      */ 
/*      */       
/*  848 */       if (header != null)
/*      */       {
/*  850 */         des.update(header, 0, header.length, temp, 0);
/*      */       }
/*      */ 
/*      */       
/*  854 */       for (int i = 0; i < numBlocks; i++) {
/*  855 */         des.update(data, offset, blockSize, temp, 0);
/*      */         
/*  857 */         offset += blockSize;
/*      */       } 
/*      */ 
/*      */       
/*  861 */       byte[] retVal = new byte[blockSize];
/*  862 */       des.update(finalBlock, 0, blockSize, retVal, 0);
/*  863 */       des.doFinal();
/*      */       
/*  865 */       return retVal;
/*  866 */     } catch (GeneralSecurityException e) {
/*  867 */       GSSException ge = new GSSException(11, -1, "Could not use DES Cipher - " + e.getMessage());
/*      */       
/*  869 */       ge.initCause(e);
/*  870 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Cipher getInitializedDes(boolean encryptMode, byte[] key, byte[] ivBytes) throws GSSException {
/*      */     try {
/*  888 */       IvParameterSpec iv = new IvParameterSpec(ivBytes);
/*  889 */       SecretKey jceKey = new SecretKeySpec(key, "DES");
/*      */       
/*  891 */       Cipher desCipher = Cipher.getInstance("DES/CBC/NoPadding");
/*  892 */       desCipher.init(encryptMode ? 1 : 2, jceKey, iv);
/*      */ 
/*      */       
/*  895 */       return desCipher;
/*  896 */     } catch (GeneralSecurityException e) {
/*  897 */       GSSException ge = new GSSException(11, -1, e.getMessage());
/*      */       
/*  899 */       ge.initCause(e);
/*  900 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void desCbcDecrypt(WrapToken token, byte[] key, byte[] cipherText, int offset, int len, byte[] dataOutBuf, int dataOffset) throws GSSException {
/*      */     try {
/*  926 */       int temp = 0;
/*      */       
/*  928 */       Cipher des = getInitializedDes(false, key, ZERO_IV);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  934 */       temp = des.update(cipherText, offset, 8, token.confounder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  940 */       offset += 8;
/*  941 */       len -= 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  950 */       int blockSize = des.getBlockSize();
/*  951 */       int numBlocks = len / blockSize - 1;
/*      */ 
/*      */       
/*  954 */       for (int i = 0; i < numBlocks; i++) {
/*  955 */         temp = des.update(cipherText, offset, blockSize, dataOutBuf, dataOffset);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  961 */         offset += blockSize;
/*  962 */         dataOffset += blockSize;
/*      */       } 
/*      */ 
/*      */       
/*  966 */       byte[] finalBlock = new byte[blockSize];
/*  967 */       des.update(cipherText, offset, blockSize, finalBlock);
/*      */       
/*  969 */       des.doFinal();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  976 */       int padSize = finalBlock[blockSize - 1];
/*  977 */       if (padSize < 1 || padSize > 8) {
/*  978 */         throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */       }
/*  980 */       token.padding = WrapToken.pads[padSize];
/*  981 */       blockSize -= padSize;
/*      */ 
/*      */       
/*  984 */       System.arraycopy(finalBlock, 0, dataOutBuf, dataOffset, blockSize);
/*      */     
/*      */     }
/*  987 */     catch (GeneralSecurityException e) {
/*  988 */       GSSException ge = new GSSException(11, -1, "Could not use DES cipher - " + e.getMessage());
/*      */       
/*  990 */       ge.initCause(e);
/*  991 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void desCbcDecrypt(WrapToken token, byte[] key, InputStream is, int len, byte[] dataOutBuf, int dataOffset) throws GSSException, IOException {
/* 1015 */     int temp = 0;
/*      */     
/* 1017 */     Cipher des = getInitializedDes(false, key, ZERO_IV);
/*      */     
/* 1019 */     WrapTokenInputStream truncatedInputStream = new WrapTokenInputStream(is, len);
/*      */     
/* 1021 */     CipherInputStream cis = new CipherInputStream(truncatedInputStream, des);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1027 */     temp = cis.read(token.confounder);
/*      */     
/* 1029 */     len -= temp;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     int blockSize = des.getBlockSize();
/* 1044 */     int numBlocks = len / blockSize - 1;
/*      */ 
/*      */     
/* 1047 */     for (int i = 0; i < numBlocks; i++) {
/*      */       
/* 1049 */       temp = cis.read(dataOutBuf, dataOffset, blockSize);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1056 */       dataOffset += blockSize;
/*      */     } 
/*      */ 
/*      */     
/* 1060 */     byte[] finalBlock = new byte[blockSize];
/*      */     
/* 1062 */     temp = cis.read(finalBlock);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1072 */       des.doFinal();
/* 1073 */     } catch (GeneralSecurityException e) {
/* 1074 */       GSSException ge = new GSSException(11, -1, "Could not use DES cipher - " + e.getMessage());
/*      */       
/* 1076 */       ge.initCause(e);
/* 1077 */       throw ge;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1085 */     int padSize = finalBlock[blockSize - 1];
/* 1086 */     if (padSize < 1 || padSize > 8) {
/* 1087 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/* 1089 */     token.padding = WrapToken.pads[padSize];
/* 1090 */     blockSize -= padSize;
/*      */ 
/*      */     
/* 1093 */     System.arraycopy(finalBlock, 0, dataOutBuf, dataOffset, blockSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] getDesEncryptionKey(byte[] key) throws GSSException {
/* 1109 */     if (key.length > 8) {
/* 1110 */       throw new GSSException(11, -100, "Invalid DES Key!");
/*      */     }
/*      */     
/* 1113 */     byte[] retVal = new byte[key.length];
/* 1114 */     for (int i = 0; i < key.length; i++)
/* 1115 */       retVal[i] = (byte)(key[i] ^ 0xF0); 
/* 1116 */     return retVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void des3KdDecrypt(WrapToken token, byte[] ciphertext, int cStart, int cLen, byte[] plaintext, int pStart) throws GSSException {
/*      */     byte[] ptext;
/*      */     try {
/* 1125 */       ptext = Des3.decryptRaw(this.keybytes, 22, ZERO_IV, ciphertext, cStart, cLen);
/*      */     }
/* 1127 */     catch (GeneralSecurityException e) {
/* 1128 */       GSSException ge = new GSSException(11, -1, "Could not use DES3-KD Cipher - " + e.getMessage());
/*      */       
/* 1130 */       ge.initCause(e);
/* 1131 */       throw ge;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     int padSize = ptext[ptext.length - 1];
/* 1147 */     if (padSize < 1 || padSize > 8) {
/* 1148 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/*      */     
/* 1151 */     token.padding = WrapToken.pads[padSize];
/* 1152 */     int len = ptext.length - 8 - padSize;
/*      */     
/* 1154 */     System.arraycopy(ptext, 8, plaintext, pStart, len);
/*      */ 
/*      */ 
/*      */     
/* 1158 */     System.arraycopy(ptext, 0, token.confounder, 0, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] des3KdEncrypt(byte[] confounder, byte[] plaintext, int start, int len, byte[] padding) throws GSSException {
/* 1167 */     byte[] all = new byte[confounder.length + len + padding.length];
/* 1168 */     System.arraycopy(confounder, 0, all, 0, confounder.length);
/* 1169 */     System.arraycopy(plaintext, start, all, confounder.length, len);
/* 1170 */     System.arraycopy(padding, 0, all, confounder.length + len, padding.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1177 */       byte[] answer = Des3.encryptRaw(this.keybytes, 22, ZERO_IV, all, 0, all.length);
/*      */ 
/*      */ 
/*      */       
/* 1181 */       return answer;
/* 1182 */     } catch (Exception e) {
/*      */       
/* 1184 */       GSSException ge = new GSSException(11, -1, "Could not use DES3-KD Cipher - " + e.getMessage());
/*      */       
/* 1186 */       ge.initCause(e);
/* 1187 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void arcFourDecrypt(WrapToken token, byte[] ciphertext, int cStart, int cLen, byte[] plaintext, int pStart) throws GSSException {
/* 1198 */     byte[] ptext, seqNum = decryptSeq(token.getChecksum(), token.getEncSeqNumber(), 0, 8);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1203 */       ptext = ArcFourHmac.decryptRaw(this.keybytes, 22, ZERO_IV, ciphertext, cStart, cLen, seqNum);
/*      */     }
/* 1205 */     catch (GeneralSecurityException e) {
/* 1206 */       GSSException ge = new GSSException(11, -1, "Could not use ArcFour Cipher - " + e.getMessage());
/*      */       
/* 1208 */       ge.initCause(e);
/* 1209 */       throw ge;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1224 */     int padSize = ptext[ptext.length - 1];
/* 1225 */     if (padSize < 1) {
/* 1226 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/*      */     
/* 1229 */     token.padding = WrapToken.pads[padSize];
/* 1230 */     int len = ptext.length - 8 - padSize;
/*      */     
/* 1232 */     System.arraycopy(ptext, 8, plaintext, pStart, len);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     System.arraycopy(ptext, 0, token.confounder, 0, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] arcFourEncrypt(WrapToken token, byte[] confounder, byte[] plaintext, int start, int len, byte[] padding) throws GSSException {
/* 1248 */     byte[] all = new byte[confounder.length + len + padding.length];
/* 1249 */     System.arraycopy(confounder, 0, all, 0, confounder.length);
/* 1250 */     System.arraycopy(plaintext, start, all, confounder.length, len);
/* 1251 */     System.arraycopy(padding, 0, all, confounder.length + len, padding.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1257 */     byte[] seqNum = new byte[4];
/* 1258 */     WrapToken.writeBigEndian(token.getSequenceNumber(), seqNum);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1264 */       byte[] answer = ArcFourHmac.encryptRaw(this.keybytes, 22, seqNum, all, 0, all.length);
/*      */ 
/*      */ 
/*      */       
/* 1268 */       return answer;
/* 1269 */     } catch (Exception e) {
/*      */       
/* 1271 */       GSSException ge = new GSSException(11, -1, "Could not use ArcFour Cipher - " + e.getMessage());
/*      */       
/* 1273 */       ge.initCause(e);
/* 1274 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] aes128Encrypt(byte[] confounder, byte[] tokenHeader, byte[] plaintext, int start, int len, int key_usage) throws GSSException {
/* 1288 */     byte[] all = new byte[confounder.length + len + tokenHeader.length];
/* 1289 */     System.arraycopy(confounder, 0, all, 0, confounder.length);
/* 1290 */     System.arraycopy(plaintext, start, all, confounder.length, len);
/* 1291 */     System.arraycopy(tokenHeader, 0, all, confounder.length + len, tokenHeader.length);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1296 */       byte[] answer = Aes128.encryptRaw(this.keybytes, key_usage, ZERO_IV_AES, all, 0, all.length);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1301 */       return answer;
/* 1302 */     } catch (Exception e) {
/*      */       
/* 1304 */       GSSException ge = new GSSException(11, -1, "Could not use AES128 Cipher - " + e.getMessage());
/*      */       
/* 1306 */       ge.initCause(e);
/* 1307 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void aes128Decrypt(WrapToken_v2 token, byte[] ciphertext, int cStart, int cLen, byte[] plaintext, int pStart, int key_usage) throws GSSException {
/* 1315 */     byte[] ptext = null;
/*      */     
/*      */     try {
/* 1318 */       ptext = Aes128.decryptRaw(this.keybytes, key_usage, ZERO_IV_AES, ciphertext, cStart, cLen);
/*      */     }
/* 1320 */     catch (GeneralSecurityException e) {
/* 1321 */       GSSException ge = new GSSException(11, -1, "Could not use AES128 Cipher - " + e.getMessage());
/*      */       
/* 1323 */       ge.initCause(e);
/* 1324 */       throw ge;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1337 */     int len = ptext.length - 16 - 16;
/*      */     
/* 1339 */     System.arraycopy(ptext, 16, plaintext, pStart, len);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] aes256Encrypt(byte[] confounder, byte[] tokenHeader, byte[] plaintext, int start, int len, int key_usage) throws GSSException {
/* 1357 */     byte[] all = new byte[confounder.length + len + tokenHeader.length];
/* 1358 */     System.arraycopy(confounder, 0, all, 0, confounder.length);
/* 1359 */     System.arraycopy(plaintext, start, all, confounder.length, len);
/* 1360 */     System.arraycopy(tokenHeader, 0, all, confounder.length + len, tokenHeader.length);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1366 */       byte[] answer = Aes256.encryptRaw(this.keybytes, key_usage, ZERO_IV_AES, all, 0, all.length);
/*      */ 
/*      */ 
/*      */       
/* 1370 */       return answer;
/* 1371 */     } catch (Exception e) {
/*      */       
/* 1373 */       GSSException ge = new GSSException(11, -1, "Could not use AES256 Cipher - " + e.getMessage());
/*      */       
/* 1375 */       ge.initCause(e);
/* 1376 */       throw ge;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void aes256Decrypt(WrapToken_v2 token, byte[] ciphertext, int cStart, int cLen, byte[] plaintext, int pStart, int key_usage) throws GSSException {
/*      */     byte[] ptext;
/*      */     try {
/* 1386 */       ptext = Aes256.decryptRaw(this.keybytes, key_usage, ZERO_IV_AES, ciphertext, cStart, cLen);
/*      */     }
/* 1388 */     catch (GeneralSecurityException e) {
/* 1389 */       GSSException ge = new GSSException(11, -1, "Could not use AES128 Cipher - " + e.getMessage());
/*      */       
/* 1391 */       ge.initCause(e);
/* 1392 */       throw ge;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1405 */     int len = ptext.length - 16 - 16;
/*      */     
/* 1407 */     System.arraycopy(ptext, 16, plaintext, pStart, len);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class WrapTokenInputStream
/*      */     extends InputStream
/*      */   {
/*      */     private InputStream is;
/*      */ 
/*      */ 
/*      */     
/*      */     private int length;
/*      */ 
/*      */ 
/*      */     
/*      */     private int remaining;
/*      */ 
/*      */     
/*      */     private int temp;
/*      */ 
/*      */ 
/*      */     
/*      */     public WrapTokenInputStream(InputStream is, int length) {
/* 1432 */       this.is = is;
/* 1433 */       this.length = length;
/* 1434 */       this.remaining = length;
/*      */     }
/*      */     
/*      */     public final int read() throws IOException {
/* 1438 */       if (this.remaining == 0) {
/* 1439 */         return -1;
/*      */       }
/* 1441 */       this.temp = this.is.read();
/* 1442 */       if (this.temp != -1)
/* 1443 */         this.remaining -= this.temp; 
/* 1444 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int read(byte[] b) throws IOException {
/* 1449 */       if (this.remaining == 0) {
/* 1450 */         return -1;
/*      */       }
/* 1452 */       this.temp = Math.min(this.remaining, b.length);
/* 1453 */       this.temp = this.is.read(b, 0, this.temp);
/* 1454 */       if (this.temp != -1)
/* 1455 */         this.remaining -= this.temp; 
/* 1456 */       return this.temp;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int read(byte[] b, int off, int len) throws IOException {
/* 1463 */       if (this.remaining == 0) {
/* 1464 */         return -1;
/*      */       }
/* 1466 */       this.temp = Math.min(this.remaining, len);
/* 1467 */       this.temp = this.is.read(b, off, this.temp);
/* 1468 */       if (this.temp != -1)
/* 1469 */         this.remaining -= this.temp; 
/* 1470 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final long skip(long n) throws IOException {
/* 1475 */       if (this.remaining == 0) {
/* 1476 */         return 0L;
/*      */       }
/* 1478 */       this.temp = (int)Math.min(this.remaining, n);
/* 1479 */       this.temp = (int)this.is.skip(this.temp);
/* 1480 */       this.remaining -= this.temp;
/* 1481 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int available() throws IOException {
/* 1486 */       return Math.min(this.remaining, this.is.available());
/*      */     }
/*      */     
/*      */     public final void close() throws IOException {
/* 1490 */       this.remaining = 0;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\CipherHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */