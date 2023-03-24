/*    */ package com.sun.xml.ws.message;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
/*    */ import org.xml.sax.helpers.XMLFilterImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class XMLReaderImpl
/*    */   extends XMLFilterImpl
/*    */ {
/*    */   private final Message msg;
/*    */   
/*    */   XMLReaderImpl(Message msg) {
/* 60 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public void parse(String systemId) {
/* 64 */     reportError();
/*    */   }
/*    */ 
/*    */   
/*    */   private void reportError() {
/* 69 */     throw new IllegalStateException("This is a special XMLReader implementation that only works with the InputSource given in SAXSource.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void parse(InputSource input) throws SAXException {
/* 74 */     if (input != THE_SOURCE)
/* 75 */       reportError(); 
/* 76 */     this.msg.writeTo(this, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContentHandler getContentHandler() {
/* 81 */     if (super.getContentHandler() == DUMMY) return null; 
/* 82 */     return super.getContentHandler();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setContentHandler(ContentHandler contentHandler) {
/* 87 */     if (contentHandler == null) contentHandler = DUMMY; 
/* 88 */     super.setContentHandler(contentHandler);
/*    */   }
/*    */   
/* 91 */   private static final ContentHandler DUMMY = new DefaultHandler();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 96 */   protected static final InputSource THE_SOURCE = new InputSource();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\XMLReaderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */