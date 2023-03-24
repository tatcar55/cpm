/*    */ package com.sun.xml.wss.impl.callback;
/*    */ 
/*    */ import java.security.KeyStore;
/*    */ import java.security.PrivateKey;
/*    */ import javax.security.auth.callback.Callback;
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
/*    */ public class PrivateKeyCallback
/*    */   extends XWSSCallback
/*    */   implements Callback
/*    */ {
/*    */   private PrivateKey key;
/*    */   private KeyStore keystore;
/*    */   private String alias;
/*    */   
/*    */   public PrivateKey getKey() {
/* 67 */     return this.key;
/*    */   }
/*    */   
/*    */   public void setKey(PrivateKey key) {
/* 71 */     this.key = key;
/*    */   }
/*    */   
/*    */   public KeyStore getKeystore() {
/* 75 */     return this.keystore;
/*    */   }
/*    */   
/*    */   public void setKeystore(KeyStore keystore) {
/* 79 */     this.keystore = keystore;
/*    */   }
/*    */   
/*    */   public void setAlias(String alias) {
/* 83 */     this.alias = alias;
/*    */   }
/*    */   
/*    */   public String getAlias() {
/* 87 */     return this.alias;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\PrivateKeyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */