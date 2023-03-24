/*    */ package com.sun.xml.ws.security.opt.crypto.jaxb;
/*    */ 
/*    */ import java.security.Key;
/*    */ import javax.xml.crypto.KeySelector;
/*    */ import javax.xml.crypto.dsig.XMLSignContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JAXBSignContext
/*    */   extends JAXBCryptoContext
/*    */   implements XMLSignContext
/*    */ {
/*    */   public JAXBSignContext() {}
/*    */   
/*    */   public JAXBSignContext(Key signingKey) {
/* 74 */     if (signingKey == null) {
/* 75 */       throw new NullPointerException("signingKey cannot be null");
/*    */     }
/* 77 */     setKeySelector(KeySelector.singletonKeySelector(signingKey));
/*    */   }
/*    */   
/*    */   public JAXBSignContext(KeySelector ks) {
/* 81 */     if (ks == null) {
/* 82 */       throw new NullPointerException("key selector cannot be null");
/*    */     }
/* 84 */     setKeySelector(ks);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\jaxb\JAXBSignContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */