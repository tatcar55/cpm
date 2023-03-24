/*    */ package com.sun.xml.ws.encoding.soap;
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
/*    */ public class DeserializationException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public DeserializationException(String key, Object... args) {
/* 57 */     super(key, args);
/*    */   }
/*    */   
/*    */   public DeserializationException(Throwable throwable) {
/* 61 */     super(throwable);
/*    */   }
/*    */   
/*    */   public DeserializationException(Localizable arg) {
/* 65 */     super("nestedDeserializationError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 69 */     return "com.sun.xml.ws.resources.encoding";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\soap\DeserializationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */