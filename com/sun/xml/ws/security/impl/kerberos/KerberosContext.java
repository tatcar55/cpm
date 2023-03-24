/*     */ package com.sun.xml.ws.security.impl.kerberos;
/*     */ 
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.ietf.jgss.GSSContext;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
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
/*     */ public class KerberosContext
/*     */ {
/*  55 */   private GSSContext context = null;
/*  56 */   private byte[] kerberosToken = null;
/*  57 */   private byte[] secretKey = null;
/*  58 */   private SecretKey sKey = null;
/*     */ 
/*     */   
/*     */   private boolean setOnce = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnce(boolean setOnce) {
/*  66 */     this.setOnce = setOnce;
/*     */   }
/*     */   
/*     */   public boolean setOnce() {
/*  70 */     return this.setOnce;
/*     */   }
/*     */   
/*     */   public void setGSSContext(GSSContext context) {
/*  74 */     this.context = context;
/*     */   }
/*     */   
/*     */   public GSSContext getGSSContext() {
/*  78 */     return this.context;
/*     */   }
/*     */   
/*     */   public void setKerberosToken(byte[] token) {
/*  82 */     this.kerberosToken = token;
/*     */   }
/*     */   
/*     */   public byte[] getKerberosToken() {
/*  86 */     return this.kerberosToken;
/*     */   }
/*     */   
/*     */   public void setSecretKey(byte[] secretKey) {
/*  90 */     this.secretKey = secretKey;
/*     */   }
/*     */   
/*     */   public SecretKey getSecretKey(String algorithm) {
/*  94 */     if (this.sKey == null) {
/*  95 */       this.sKey = new SecretKeySpec(this.secretKey, algorithm);
/*     */     }
/*  97 */     return this.sKey;
/*     */   }
/*     */   
/*     */   public GSSCredential getDelegatedCredentials() throws GSSException {
/* 101 */     if (this.context != null && this.context.getCredDelegState()) {
/* 102 */       return this.context.getDelegCred();
/*     */     }
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\kerberos\KerberosContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */