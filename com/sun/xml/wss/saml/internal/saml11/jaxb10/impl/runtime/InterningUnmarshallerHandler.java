/*    */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.unmarshaller.InterningXMLReader;
/*    */ import javax.xml.bind.JAXBException;
/*    */ import javax.xml.bind.ValidationEvent;
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
/*    */ final class InterningUnmarshallerHandler
/*    */   extends InterningXMLReader
/*    */   implements SAXUnmarshallerHandler
/*    */ {
/*    */   private final SAXUnmarshallerHandler core;
/*    */   
/*    */   InterningUnmarshallerHandler(SAXUnmarshallerHandler core) {
/* 30 */     setContentHandler(core);
/* 31 */     this.core = core;
/*    */   }
/*    */   
/*    */   public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException {
/* 35 */     this.core.handleEvent(event, canRecover);
/*    */   }
/*    */   
/*    */   public Object getResult() throws JAXBException, IllegalStateException {
/* 39 */     return this.core.getResult();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\InterningUnmarshallerHandler.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */