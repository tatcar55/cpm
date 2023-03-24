/*    */ package com.sun.xml.ws.streaming;
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
/*    */ public class XMLStreamWriterException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public XMLStreamWriterException(String key, Object... args) {
/* 57 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLStreamWriterException(Throwable throwable) {
/* 61 */     super(throwable);
/*    */   }
/*    */   
/*    */   public XMLStreamWriterException(Localizable arg) {
/* 65 */     super("xmlwriter.nestedError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 69 */     return "com.sun.xml.ws.resources.streaming";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\XMLStreamWriterException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */