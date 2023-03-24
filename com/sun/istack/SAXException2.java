/*    */ package com.sun.istack;
/*    */ 
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SAXException2
/*    */   extends SAXException
/*    */ {
/*    */   public SAXException2(String message) {
/* 53 */     super(message);
/*    */   }
/*    */   
/*    */   public SAXException2(Exception e) {
/* 57 */     super(e);
/*    */   }
/*    */   
/*    */   public SAXException2(String message, Exception e) {
/* 61 */     super(message, e);
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 65 */     return getException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\SAXException2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */