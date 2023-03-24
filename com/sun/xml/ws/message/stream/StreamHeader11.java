/*    */ package com.sun.xml.ws.message.stream;
/*    */ 
/*    */ import com.sun.istack.FinalArrayList;
/*    */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.ws.message.Util;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamHeader11
/*    */   extends StreamHeader
/*    */ {
/*    */   protected static final String SOAP_1_1_MUST_UNDERSTAND = "mustUnderstand";
/*    */   protected static final String SOAP_1_1_ROLE = "actor";
/*    */   
/*    */   public StreamHeader11(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 63 */     super(reader, mark);
/*    */   }
/*    */   
/*    */   public StreamHeader11(XMLStreamReader reader) throws XMLStreamException {
/* 67 */     super(reader);
/*    */   }
/*    */   
/*    */   protected final FinalArrayList<StreamHeader.Attribute> processHeaderAttributes(XMLStreamReader reader) {
/* 71 */     FinalArrayList<StreamHeader.Attribute> atts = null;
/*    */     
/* 73 */     this._role = "http://schemas.xmlsoap.org/soap/actor/next";
/*    */     
/* 75 */     for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 76 */       String localName = reader.getAttributeLocalName(i);
/* 77 */       String namespaceURI = reader.getAttributeNamespace(i);
/* 78 */       String value = reader.getAttributeValue(i);
/*    */       
/* 80 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceURI)) {
/* 81 */         if ("mustUnderstand".equals(localName)) {
/* 82 */           this._isMustUnderstand = Util.parseBool(value);
/* 83 */         } else if ("actor".equals(localName) && 
/* 84 */           value != null && value.length() > 0) {
/* 85 */           this._role = value;
/*    */         } 
/*    */       }
/*    */ 
/*    */       
/* 90 */       if (atts == null) {
/* 91 */         atts = new FinalArrayList();
/*    */       }
/* 93 */       atts.add(new StreamHeader.Attribute(namespaceURI, localName, value));
/*    */     } 
/*    */     
/* 96 */     return atts;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\StreamHeader11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */