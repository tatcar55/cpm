/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*    */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*    */ import com.sun.xml.ws.security.trust.elements.Authenticator;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.AuthenticatorType;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthenticatorImpl
/*    */   extends AuthenticatorType
/*    */   implements Authenticator
/*    */ {
/*    */   public AuthenticatorImpl() {}
/*    */   
/*    */   public AuthenticatorImpl(AuthenticatorType aType) throws Exception {}
/*    */   
/*    */   public AuthenticatorImpl(byte[] hash) {
/* 71 */     setRawCombinedHash(hash);
/*    */   }
/*    */   
/*    */   public List<Object> getAny() {
/* 75 */     return super.getAny();
/*    */   }
/*    */   
/*    */   public byte[] getRawCombinedHash() {
/* 79 */     return getCombinedHash();
/*    */   }
/*    */   
/*    */   public void setRawCombinedHash(byte[] rawCombinedHash) {
/* 83 */     setCombinedHash(rawCombinedHash);
/*    */   }
/*    */   
/*    */   public String getTextCombinedHash() {
/* 87 */     return Base64.encode(getRawCombinedHash());
/*    */   }
/*    */   
/*    */   public void setTextCombinedHash(String encodedCombinedHash) {
/*    */     try {
/* 92 */       setRawCombinedHash(Base64.decode(encodedCombinedHash));
/* 93 */     } catch (Base64DecodingException de) {
/* 94 */       throw new RuntimeException("Error while decoding " + de.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\AuthenticatorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */