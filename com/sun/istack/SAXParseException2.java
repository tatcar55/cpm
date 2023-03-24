/*    */ package com.sun.istack;
/*    */ 
/*    */ import org.xml.sax.Locator;
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
/*    */ 
/*    */ public class SAXParseException2
/*    */   extends SAXParseException
/*    */ {
/*    */   public SAXParseException2(String message, Locator locator) {
/* 54 */     super(message, locator);
/*    */   }
/*    */   
/*    */   public SAXParseException2(String message, Locator locator, Exception e) {
/* 58 */     super(message, locator, e);
/*    */   }
/*    */   
/*    */   public SAXParseException2(String message, String publicId, String systemId, int lineNumber, int columnNumber) {
/* 62 */     super(message, publicId, systemId, lineNumber, columnNumber);
/*    */   }
/*    */   
/*    */   public SAXParseException2(String message, String publicId, String systemId, int lineNumber, int columnNumber, Exception e) {
/* 66 */     super(message, publicId, systemId, lineNumber, columnNumber, e);
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 70 */     return getException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\SAXParseException2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */