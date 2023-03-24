/*    */ package com.sun.xml.ws.wsdl.writer;
/*    */ 
/*    */ import com.sun.xml.txw2.TypedXmlWriter;
/*    */ import java.util.Stack;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.Locator;
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
/*    */ public class TXWContentHandler
/*    */   implements ContentHandler
/*    */ {
/*    */   Stack<TypedXmlWriter> stack;
/*    */   
/*    */   public TXWContentHandler(TypedXmlWriter txw) {
/* 56 */     this.stack = new Stack<TypedXmlWriter>();
/* 57 */     this.stack.push(txw);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDocumentLocator(Locator locator) {}
/*    */ 
/*    */   
/*    */   public void startDocument() throws SAXException {}
/*    */ 
/*    */   
/*    */   public void endDocument() throws SAXException {}
/*    */ 
/*    */   
/*    */   public void startPrefixMapping(String prefix, String uri) throws SAXException {}
/*    */ 
/*    */   
/*    */   public void endPrefixMapping(String prefix) throws SAXException {}
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/* 76 */     TypedXmlWriter txw = ((TypedXmlWriter)this.stack.peek())._element(uri, localName, TypedXmlWriter.class);
/* 77 */     this.stack.push(txw);
/* 78 */     if (atts != null) {
/* 79 */       for (int i = 0; i < atts.getLength(); i++) {
/* 80 */         String auri = atts.getURI(i);
/* 81 */         if ("http://www.w3.org/2000/xmlns/".equals(auri)) {
/* 82 */           if ("xmlns".equals(atts.getLocalName(i))) {
/* 83 */             txw._namespace(atts.getValue(i), "");
/*    */           } else {
/* 85 */             txw._namespace(atts.getValue(i), atts.getLocalName(i));
/*    */           } 
/* 87 */         } else if (!"schemaLocation".equals(atts.getLocalName(i)) || !"".equals(atts.getValue(i))) {
/*    */ 
/*    */           
/* 90 */           txw._attribute(auri, atts.getLocalName(i), atts.getValue(i));
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 97 */     this.stack.pop();
/*    */   }
/*    */   
/*    */   public void characters(char[] ch, int start, int length) throws SAXException {}
/*    */   
/*    */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
/*    */   
/*    */   public void processingInstruction(String target, String data) throws SAXException {}
/*    */   
/*    */   public void skippedEntity(String name) throws SAXException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\TXWContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */