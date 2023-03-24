/*    */ package com.sun.xml.wss.impl.callback;
/*    */ 
/*    */ import java.security.cert.CertStore;
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
/*    */ public class CertStoreCallback
/*    */   extends XWSSCallback
/*    */   implements Callback
/*    */ {
/*    */   private CertStore certStore;
/*    */   
/*    */   public CertStore getCertStore() {
/* 58 */     return this.certStore;
/*    */   }
/*    */   
/*    */   public void setCertStore(CertStore store) {
/* 62 */     this.certStore = store;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\CertStoreCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */