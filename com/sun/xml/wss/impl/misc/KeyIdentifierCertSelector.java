/*    */ package com.sun.xml.wss.impl.misc;
/*    */ 
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*    */ import java.security.cert.CertSelector;
/*    */ import java.security.cert.Certificate;
/*    */ import java.security.cert.X509Certificate;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ public class KeyIdentifierCertSelector
/*    */   implements CertSelector
/*    */ {
/*    */   private final byte[] keyId;
/*    */   
/*    */   public KeyIdentifierCertSelector(byte[] keyIdValue) {
/* 70 */     this.keyId = keyIdValue;
/*    */   }
/*    */   
/*    */   public boolean match(Certificate cert) {
/* 74 */     if (cert instanceof X509Certificate) {
/* 75 */       byte[] keyIdtoMatch = null;
/*    */       try {
/* 77 */         keyIdtoMatch = X509SubjectKeyIdentifier.getSubjectKeyIdentifier((X509Certificate)cert);
/*    */       }
/* 79 */       catch (XWSSecurityException ex) {}
/*    */ 
/*    */       
/* 82 */       if (Arrays.equals(keyIdtoMatch, this.keyId)) {
/* 83 */         return true;
/*    */       }
/*    */     } 
/* 86 */     return false;
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 90 */     return new KeyIdentifierCertSelector(this.keyId);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\KeyIdentifierCertSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */