/*    */ package com.sun.xml.bind.v2.util;
/*    */ 
/*    */ import org.xml.sax.ErrorHandler;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.SAXParseException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FatalAdapter
/*    */   implements ErrorHandler
/*    */ {
/*    */   private final ErrorHandler core;
/*    */   
/*    */   public FatalAdapter(ErrorHandler handler) {
/* 56 */     this.core = handler;
/*    */   }
/*    */   
/*    */   public void warning(SAXParseException exception) throws SAXException {
/* 60 */     this.core.warning(exception);
/*    */   }
/*    */   
/*    */   public void error(SAXParseException exception) throws SAXException {
/* 64 */     this.core.fatalError(exception);
/*    */   }
/*    */   
/*    */   public void fatalError(SAXParseException exception) throws SAXException {
/* 68 */     this.core.fatalError(exception);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\FatalAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */