/*    */ package com.sun.xml.wss.impl.callback;
/*    */ 
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SAMLAssertionValidator
/*    */ {
/*    */   void validate(Element paramElement) throws SAMLValidationException;
/*    */   
/*    */   void validate(XMLStreamReader paramXMLStreamReader) throws SAMLValidationException;
/*    */   
/*    */   public static class SAMLValidationException
/*    */     extends Exception
/*    */   {
/*    */     public SAMLValidationException(String message) {
/* 78 */       super(message);
/*    */     }
/*    */     
/*    */     public SAMLValidationException(String message, Throwable cause) {
/* 82 */       super(message, cause);
/*    */     }
/*    */     
/*    */     public SAMLValidationException(Throwable cause) {
/* 86 */       super(cause);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\SAMLAssertionValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */