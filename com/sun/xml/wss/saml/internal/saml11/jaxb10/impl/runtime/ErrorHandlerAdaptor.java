/*    */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.validator.Locator;
/*    */ import javax.xml.bind.ValidationEventLocator;
/*    */ import javax.xml.bind.helpers.ValidationEventImpl;
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
/*    */ public class ErrorHandlerAdaptor
/*    */   implements ErrorHandler
/*    */ {
/*    */   private final SAXUnmarshallerHandler host;
/*    */   private final Locator locator;
/*    */   
/*    */   public ErrorHandlerAdaptor(SAXUnmarshallerHandler _host, Locator locator) {
/* 38 */     this.host = _host;
/* 39 */     this.locator = locator;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void error(SAXParseException exception) throws SAXException {
/* 45 */     propagateEvent(1, exception);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void warning(SAXParseException exception) throws SAXException {
/* 51 */     propagateEvent(0, exception);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fatalError(SAXParseException exception) throws SAXException {
/* 57 */     propagateEvent(2, exception);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void propagateEvent(int severity, SAXParseException saxException) throws SAXException {
/* 67 */     ValidationEventLocator vel = this.locator.getLocation(saxException);
/*    */ 
/*    */     
/* 70 */     ValidationEventImpl ve = new ValidationEventImpl(severity, saxException.getMessage(), vel);
/*    */ 
/*    */     
/* 73 */     Exception e = saxException.getException();
/* 74 */     if (e != null) {
/* 75 */       ve.setLinkedException(e);
/*    */     } else {
/* 77 */       ve.setLinkedException(saxException);
/*    */     } 
/*    */ 
/*    */     
/* 81 */     this.host.handleEvent(ve, (severity != 2));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\ErrorHandlerAdaptor.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */