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
/*    */ public class SerializationException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public SerializationException(String key, Object... args) {
/* 57 */     super(key, args);
/*    */   }
/*    */   
/*    */   public SerializationException(Localizable arg) {
/* 61 */     super("nestedSerializationError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public SerializationException(Throwable throwable) {
/* 65 */     super(throwable);
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 69 */     return "com.sun.xml.ws.resources.encoding";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\soap\SerializationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */