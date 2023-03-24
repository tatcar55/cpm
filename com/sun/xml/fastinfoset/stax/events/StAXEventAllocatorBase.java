/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.stream.util.XMLEventAllocator;
/*     */ import javax.xml.stream.util.XMLEventConsumer;
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
/*     */ public class StAXEventAllocatorBase
/*     */   implements XMLEventAllocator
/*     */ {
/*     */   XMLEventFactory factory;
/*     */   
/*     */   public StAXEventAllocatorBase() {
/*  50 */     if (System.getProperty("javax.xml.stream.XMLEventFactory") == null) {
/*  51 */       System.setProperty("javax.xml.stream.XMLEventFactory", "com.sun.xml.fastinfoset.stax.factory.StAXEventFactory");
/*     */     }
/*     */     
/*  54 */     this.factory = XMLEventFactory.newInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventAllocator newInstance() {
/*  64 */     return new StAXEventAllocatorBase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent allocate(XMLStreamReader streamReader) throws XMLStreamException {
/*  75 */     if (streamReader == null)
/*  76 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.nullReader")); 
/*  77 */     return getXMLEvent(streamReader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allocate(XMLStreamReader streamReader, XMLEventConsumer consumer) throws XMLStreamException {
/*  88 */     consumer.add(getXMLEvent(streamReader));
/*     */   }
/*     */   XMLEvent getXMLEvent(XMLStreamReader reader) {
/*     */     StartElementEvent startElement;
/*     */     EndElementEvent endElement;
/*     */     StartDocumentEvent docEvent;
/*     */     EndDocumentEvent endDocumentEvent;
/*  95 */     XMLEvent event = null;
/*     */     
/*  97 */     int eventType = reader.getEventType();
/*     */     
/*  99 */     this.factory.setLocation(reader.getLocation());
/* 100 */     switch (eventType) {
/*     */ 
/*     */       
/*     */       case 1:
/* 104 */         startElement = (StartElementEvent)this.factory.createStartElement(reader.getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/*     */ 
/*     */         
/* 107 */         addAttributes(startElement, reader);
/* 108 */         addNamespaces(startElement, reader);
/*     */ 
/*     */         
/* 111 */         event = startElement;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 116 */         endElement = (EndElementEvent)this.factory.createEndElement(reader.getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/*     */         
/* 118 */         addNamespaces(endElement, reader);
/* 119 */         event = endElement;
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 124 */         event = this.factory.createProcessingInstruction(reader.getPITarget(), reader.getPIData());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 129 */         if (reader.isWhiteSpace()) {
/* 130 */           event = this.factory.createSpace(reader.getText()); break;
/*     */         } 
/* 132 */         event = this.factory.createCharacters(reader.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 138 */         event = this.factory.createComment(reader.getText());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 143 */         docEvent = (StartDocumentEvent)this.factory.createStartDocument(reader.getVersion(), reader.getEncoding(), reader.isStandalone());
/*     */         
/* 145 */         if (reader.getCharacterEncodingScheme() != null) {
/* 146 */           docEvent.setDeclaredEncoding(true);
/*     */         } else {
/* 148 */           docEvent.setDeclaredEncoding(false);
/*     */         } 
/* 150 */         event = docEvent;
/*     */         break;
/*     */       
/*     */       case 8:
/* 154 */         endDocumentEvent = new EndDocumentEvent();
/* 155 */         event = endDocumentEvent;
/*     */         break;
/*     */       
/*     */       case 9:
/* 159 */         event = this.factory.createEntityReference(reader.getLocalName(), new EntityDeclarationImpl(reader.getLocalName(), reader.getText()));
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 10:
/* 165 */         event = null;
/*     */         break;
/*     */       
/*     */       case 11:
/* 169 */         event = this.factory.createDTD(reader.getText());
/*     */         break;
/*     */       
/*     */       case 12:
/* 173 */         event = this.factory.createCData(reader.getText());
/*     */         break;
/*     */       
/*     */       case 6:
/* 177 */         event = this.factory.createSpace(reader.getText());
/*     */         break;
/*     */     } 
/*     */     
/* 181 */     return event;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAttributes(StartElementEvent event, XMLStreamReader streamReader) {
/* 186 */     AttributeBase attr = null;
/* 187 */     for (int i = 0; i < streamReader.getAttributeCount(); i++) {
/* 188 */       attr = (AttributeBase)this.factory.createAttribute(streamReader.getAttributeName(i), streamReader.getAttributeValue(i));
/*     */       
/* 190 */       attr.setAttributeType(streamReader.getAttributeType(i));
/* 191 */       attr.setSpecified(streamReader.isAttributeSpecified(i));
/* 192 */       event.addAttribute(attr);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addNamespaces(StartElementEvent event, XMLStreamReader streamReader) {
/* 198 */     Namespace namespace = null;
/* 199 */     for (int i = 0; i < streamReader.getNamespaceCount(); i++) {
/* 200 */       namespace = this.factory.createNamespace(streamReader.getNamespacePrefix(i), streamReader.getNamespaceURI(i));
/*     */       
/* 202 */       event.addNamespace(namespace);
/*     */     } 
/*     */   }
/*     */   protected void addNamespaces(EndElementEvent event, XMLStreamReader streamReader) {
/* 206 */     Namespace namespace = null;
/* 207 */     for (int i = 0; i < streamReader.getNamespaceCount(); i++) {
/* 208 */       namespace = this.factory.createNamespace(streamReader.getNamespacePrefix(i), streamReader.getNamespaceURI(i));
/*     */       
/* 210 */       event.addNamespace(namespace);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\StAXEventAllocatorBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */