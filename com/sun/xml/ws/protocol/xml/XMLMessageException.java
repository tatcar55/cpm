/*    */ package com.sun.xml.ws.protocol.xml;
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
/*    */ public class XMLMessageException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public XMLMessageException(String key, Object... args) {
/* 52 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLMessageException(Throwable throwable) {
/* 56 */     super(throwable);
/*    */   }
/*    */   
/*    */   public XMLMessageException(Localizable arg) {
/* 60 */     super("server.rt.err", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 64 */     return "com.sun.xml.ws.resources.xmlmessage";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\protocol\xml\XMLMessageException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */