/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.xml.ws.security.trust.elements.Authenticator;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.AuthenticatorType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class AuthenticatorImpl
/*     */   extends AuthenticatorType
/*     */   implements Authenticator
/*     */ {
/*  67 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticatorImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticatorImpl(AuthenticatorType aType) throws RuntimeException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticatorImpl(byte[] hash) {
/*  81 */     setRawCombinedHash(hash);
/*     */   }
/*     */   
/*     */   public byte[] getRawCombinedHash() {
/*  85 */     return getCombinedHash();
/*     */   }
/*     */   
/*     */   public final void setRawCombinedHash(byte[] rawCombinedHash) {
/*  89 */     setCombinedHash(rawCombinedHash);
/*     */   }
/*     */   
/*     */   public String getTextCombinedHash() {
/*  93 */     return Base64.encode(getRawCombinedHash());
/*     */   }
/*     */   
/*     */   public void setTextCombinedHash(String encodedCombinedHash) {
/*     */     try {
/*  98 */       setRawCombinedHash(Base64.decode(encodedCombinedHash));
/*  99 */     } catch (Base64DecodingException de) {
/* 100 */       log.log(Level.SEVERE, LogStringsMessages.WST_0020_ERROR_DECODING(encodedCombinedHash), de);
/*     */       
/* 102 */       throw new RuntimeException(LogStringsMessages.WST_0020_ERROR_DECODING(encodedCombinedHash), de);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\AuthenticatorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */