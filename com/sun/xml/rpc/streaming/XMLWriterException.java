/*    */ package com.sun.xml.rpc.streaming;
/*    */ 
/*    */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*    */ import com.sun.xml.rpc.util.localization.Localizable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLWriterException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public XMLWriterException(String key) {
/* 44 */     super(key);
/*    */   }
/*    */   
/*    */   public XMLWriterException(String key, String arg) {
/* 48 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public XMLWriterException(String key, Object[] args) {
/* 52 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLWriterException(String key, Localizable arg) {
/* 56 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public XMLWriterException(Localizable arg) {
/* 60 */     super("xmlwriter.nestedError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 64 */     return "com.sun.xml.rpc.resources.streaming";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLWriterException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */