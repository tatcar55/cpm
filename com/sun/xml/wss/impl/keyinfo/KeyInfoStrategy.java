/*    */ package com.sun.xml.wss.impl.keyinfo;
/*    */ 
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*    */ import com.sun.xml.wss.core.SecurityTokenReference;
/*    */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*    */ import java.security.cert.X509Certificate;
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
/*    */ public abstract class KeyInfoStrategy
/*    */ {
/*    */   public static KeyInfoStrategy getInstance(String strategy) {
/* 65 */     if ("Identifier" == strategy || "Identifier".equals(strategy))
/* 66 */       return new KeyIdentifierStrategy(); 
/* 67 */     if ("Thumbprint" == strategy || "Thumbprint".equals(strategy))
/* 68 */       return new KeyIdentifierStrategy(0); 
/* 69 */     if ("EncryptedKeySHA1" == strategy || "EncryptedKeySHA1".equals(strategy))
/* 70 */       return new KeyIdentifierStrategy(1); 
/* 71 */     if ("KeyName" == strategy || "KeyName".equals(strategy))
/* 72 */       return new KeyNameStrategy(); 
/* 73 */     if ("Direct" == strategy || "Direct".equals(strategy))
/* 74 */       return new DirectReferenceStrategy(); 
/* 75 */     if ("IssuerSerialNumber" == strategy || "IssuerSerialNumber".equals(strategy))
/* 76 */       return new X509IssuerSerialStrategy(); 
/* 77 */     if ("BinarySecret" == strategy || "BinarySecret".equals(strategy)) {
/* 78 */       return new BinarySecretStrategy();
/*    */     }
/* 80 */     return null;
/*    */   }
/*    */   
/*    */   public abstract void insertKey(KeyInfoHeaderBlock paramKeyInfoHeaderBlock, SecurableSoapMessage paramSecurableSoapMessage, String paramString) throws XWSSecurityException;
/*    */   
/*    */   public abstract void insertKey(SecurityTokenReference paramSecurityTokenReference, SecurableSoapMessage paramSecurableSoapMessage) throws XWSSecurityException;
/*    */   
/*    */   public abstract void setCertificate(X509Certificate paramX509Certificate);
/*    */   
/*    */   public abstract String getAlias();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\keyinfo\KeyInfoStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */