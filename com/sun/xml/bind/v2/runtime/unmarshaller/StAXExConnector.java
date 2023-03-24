/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.jvnet.staxex.Base64Data;
/*    */ import org.jvnet.staxex.XMLStreamReaderEx;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class StAXExConnector
/*    */   extends StAXStreamConnector
/*    */ {
/*    */   private final XMLStreamReaderEx in;
/*    */   
/*    */   public StAXExConnector(XMLStreamReaderEx in, XmlVisitor visitor) {
/* 63 */     super((XMLStreamReader)in, visitor);
/* 64 */     this.in = in;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void handleCharacters() throws XMLStreamException, SAXException {
/* 69 */     if (this.predictor.expectText()) {
/* 70 */       CharSequence pcdata = this.in.getPCDATA();
/* 71 */       if (pcdata instanceof Base64Data) {
/* 72 */         Base64Data bd = (Base64Data)pcdata;
/* 73 */         Base64Data binary = new Base64Data();
/* 74 */         if (!bd.hasData()) {
/* 75 */           binary.set(bd.getDataHandler());
/*    */         } else {
/* 77 */           binary.set(bd.get(), bd.getDataLen(), bd.getMimeType());
/*    */         } 
/*    */         
/* 80 */         this.visitor.text((CharSequence)binary);
/* 81 */         this.textReported = true;
/*    */       } else {
/* 83 */         this.buffer.append(pcdata);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\StAXExConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */