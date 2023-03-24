/*    */ package com.sun.xml.wss.impl.misc;
/*    */ 
/*    */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*    */ import java.security.PublicKey;
/*    */ import java.security.cert.CertSelector;
/*    */ import java.security.cert.Certificate;
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
/*    */ public class PublicKeyCertSelector
/*    */   implements CertSelector
/*    */ {
/* 64 */   PublicKey key = null;
/*    */   
/*    */   public PublicKeyCertSelector(PublicKey pk) {
/* 67 */     this.key = pk;
/*    */   }
/*    */   
/*    */   public boolean match(Certificate cert) {
/* 71 */     if (cert == null) {
/* 72 */       return false;
/*    */     }
/* 74 */     if (this.key == null)
/*    */     {
/* 76 */       throw new XWSSecurityRuntimeException("PublicKeyCertSelector instantiated with Null Key");
/*    */     }
/* 78 */     if (cert instanceof X509Certificate) {
/* 79 */       X509Certificate x509Cert = (X509Certificate)cert;
/* 80 */       if (this.key.equals(x509Cert.getPublicKey())) {
/* 81 */         return true;
/*    */       }
/*    */     } 
/* 84 */     return false;
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 88 */     return new PublicKeyCertSelector(this.key);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\PublicKeyCertSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */