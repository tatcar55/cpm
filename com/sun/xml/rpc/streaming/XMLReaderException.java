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
/*    */ public class XMLReaderException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public XMLReaderException(String key) {
/* 44 */     super(key);
/*    */   }
/*    */   
/*    */   public XMLReaderException(String key, String arg) {
/* 48 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public XMLReaderException(String key, Object[] args) {
/* 52 */     super(key, args);
/*    */   }
/*    */   
/*    */   public XMLReaderException(String key, Localizable arg) {
/* 56 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public XMLReaderException(Localizable arg) {
/* 60 */     super("xmlreader.nestedError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 64 */     return "com.sun.xml.rpc.resources.streaming";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReaderException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */