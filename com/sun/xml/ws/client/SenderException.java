/*    */ package com.sun.xml.ws.client;
/*    */ 
/*    */ import com.sun.istack.localization.Localizable;
/*    */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SenderException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public SenderException(String key, Object... args) {
/* 52 */     super(key, args);
/*    */   }
/*    */   
/*    */   public SenderException(Throwable throwable) {
/* 56 */     super(throwable);
/*    */   }
/*    */   
/*    */   public SenderException(Localizable arg) {
/* 60 */     super("sender.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 64 */     return "com.sun.xml.ws.resources.sender";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\SenderException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */