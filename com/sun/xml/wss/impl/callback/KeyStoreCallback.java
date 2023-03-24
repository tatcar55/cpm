/*    */ package com.sun.xml.wss.impl.callback;
/*    */ 
/*    */ import java.security.KeyStore;
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
/*    */ public class KeyStoreCallback
/*    */   extends XWSSCallback
/*    */   implements Callback
/*    */ {
/*    */   private KeyStore keystore;
/*    */   
/*    */   public KeyStore getKeystore() {
/* 57 */     return this.keystore;
/*    */   }
/*    */   
/*    */   public void setKeystore(KeyStore keystore) {
/* 61 */     this.keystore = keystore;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\KeyStoreCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */