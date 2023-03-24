/*     */ package com.sun.xml.ws.security.opt.api.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import java.security.Key;
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
/*     */ public class BuilderResult
/*     */ {
/*  53 */   private Key dataProtectionKey = null;
/*  54 */   private Key keyProtectionKey = null;
/*  55 */   private KeyInfo keyInfo = null;
/*  56 */   private EncryptedKey encryptedKey = null;
/*  57 */   private String dpKID = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key getDataProtectionKey() {
/*  67 */     return this.dataProtectionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataProtectionKey(Key dataProtectionKey) {
/*  75 */     this.dataProtectionKey = dataProtectionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Key getKeyProtectionKey() {
/*  83 */     return this.keyProtectionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyProtectionKey(Key keyProtectionKey) {
/*  91 */     this.keyProtectionKey = keyProtectionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfo getKeyInfo() {
/*  99 */     return this.keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyInfo(KeyInfo keyInfo) {
/* 107 */     this.keyInfo = keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedKey getEncryptedKey() {
/* 115 */     return this.encryptedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncryptedKey(EncryptedKey encryptedKey) {
/* 123 */     this.encryptedKey = encryptedKey;
/*     */   }
/*     */   
/*     */   public void setDPTokenId(String id) {
/* 127 */     this.dpKID = id;
/*     */   }
/*     */   
/*     */   public String getDPTokenId() {
/* 131 */     return this.dpKID;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\keyinfo\BuilderResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */