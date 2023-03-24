/*     */ package com.sun.xml.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.message.Util;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamHeader12
/*     */   extends StreamHeader
/*     */ {
/*     */   protected static final String SOAP_1_2_MUST_UNDERSTAND = "mustUnderstand";
/*     */   protected static final String SOAP_1_2_ROLE = "role";
/*     */   protected static final String SOAP_1_2_RELAY = "relay";
/*     */   
/*     */   public StreamHeader12(XMLStreamReader reader, XMLStreamBuffer mark) {
/*  65 */     super(reader, mark);
/*     */   }
/*     */   
/*     */   public StreamHeader12(XMLStreamReader reader) throws XMLStreamException {
/*  69 */     super(reader);
/*     */   }
/*     */   
/*     */   protected final FinalArrayList<StreamHeader.Attribute> processHeaderAttributes(XMLStreamReader reader) {
/*  73 */     FinalArrayList<StreamHeader.Attribute> atts = null;
/*     */     
/*  75 */     this._role = "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver";
/*     */     
/*  77 */     for (int i = 0; i < reader.getAttributeCount(); i++) {
/*  78 */       String localName = reader.getAttributeLocalName(i);
/*  79 */       String namespaceURI = reader.getAttributeNamespace(i);
/*  80 */       String value = reader.getAttributeValue(i);
/*     */       
/*  82 */       if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI)) {
/*  83 */         if ("mustUnderstand".equals(localName)) {
/*  84 */           this._isMustUnderstand = Util.parseBool(value);
/*  85 */         } else if ("role".equals(localName)) {
/*  86 */           if (value != null && value.length() > 0) {
/*  87 */             this._role = value;
/*     */           }
/*  89 */         } else if ("relay".equals(localName)) {
/*  90 */           this._isRelay = Util.parseBool(value);
/*     */         } 
/*     */       }
/*     */       
/*  94 */       if (atts == null) {
/*  95 */         atts = new FinalArrayList();
/*     */       }
/*  97 */       atts.add(new StreamHeader.Attribute(namespaceURI, localName, value));
/*     */     } 
/*     */     
/* 100 */     return atts;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\StreamHeader12.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */