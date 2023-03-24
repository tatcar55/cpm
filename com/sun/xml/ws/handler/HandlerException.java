/*    */ package com.sun.xml.ws.handler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HandlerException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public HandlerException(String key, Object... args) {
/* 57 */     super(key, args);
/*    */   }
/*    */   
/*    */   public HandlerException(Throwable throwable) {
/* 61 */     super(throwable);
/*    */   }
/*    */   
/*    */   public HandlerException(Localizable arg) {
/* 65 */     super("handler.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 69 */     return "com.sun.xml.ws.resources.handler";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\HandlerException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */