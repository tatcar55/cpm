/*    */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
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
/*    */ public class ErrorHandlerRI
/*    */   implements ErrorHandler
/*    */ {
/*    */   public void error(SAXParseException ex) throws SAXException {
/* 49 */     throw ex;
/*    */   }
/*    */   
/*    */   public void fatalError(SAXParseException ex) throws SAXException {
/* 53 */     throw ex;
/*    */   }
/*    */   
/*    */   public void warning(SAXParseException ex) throws SAXException {
/* 57 */     System.out.println("warning: " + ex.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\ErrorHandlerRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */