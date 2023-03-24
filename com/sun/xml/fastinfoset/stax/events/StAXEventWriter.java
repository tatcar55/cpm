/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.Comment;
/*     */ import javax.xml.stream.events.DTD;
/*     */ import javax.xml.stream.events.EntityReference;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
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
/*     */ public class StAXEventWriter
/*     */   implements XMLEventWriter
/*     */ {
/*     */   private XMLStreamWriter _streamWriter;
/*     */   
/*     */   public StAXEventWriter(XMLStreamWriter streamWriter) {
/*  38 */     this._streamWriter = streamWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {
/*  46 */     this._streamWriter.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  53 */     this._streamWriter.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLEventReader eventReader) throws XMLStreamException {
/*  62 */     if (eventReader == null) throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.nullEventReader")); 
/*  63 */     while (eventReader.hasNext())
/*  64 */       add(eventReader.nextEvent());  } public void add(XMLEvent event) throws XMLStreamException { DTD dtd;
/*     */     StartDocument startDocument;
/*     */     StartElement startElement;
/*     */     Namespace namespace;
/*     */     Comment comment;
/*     */     ProcessingInstruction processingInstruction;
/*     */     Characters characters1;
/*     */     EntityReference entityReference;
/*     */     Attribute attribute;
/*     */     Characters characters;
/*     */     QName qname;
/*     */     Iterator<Namespace> iterator;
/*     */     Iterator<Attribute> attributes;
/*  77 */     int type = event.getEventType();
/*  78 */     switch (type) {
/*     */       case 11:
/*  80 */         dtd = (DTD)event;
/*  81 */         this._streamWriter.writeDTD(dtd.getDocumentTypeDeclaration());
/*     */         return;
/*     */       
/*     */       case 7:
/*  85 */         startDocument = (StartDocument)event;
/*  86 */         this._streamWriter.writeStartDocument(startDocument.getCharacterEncodingScheme(), startDocument.getVersion());
/*     */         return;
/*     */       
/*     */       case 1:
/*  90 */         startElement = event.asStartElement();
/*  91 */         qname = startElement.getName();
/*  92 */         this._streamWriter.writeStartElement(qname.getPrefix(), qname.getLocalPart(), qname.getNamespaceURI());
/*     */         
/*  94 */         iterator = startElement.getNamespaces();
/*  95 */         while (iterator.hasNext()) {
/*  96 */           Namespace namespace1 = iterator.next();
/*  97 */           this._streamWriter.writeNamespace(namespace1.getPrefix(), namespace1.getNamespaceURI());
/*     */         } 
/*     */         
/* 100 */         attributes = startElement.getAttributes();
/* 101 */         while (attributes.hasNext()) {
/* 102 */           Attribute attribute1 = attributes.next();
/* 103 */           QName name = attribute1.getName();
/* 104 */           this._streamWriter.writeAttribute(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attribute1.getValue());
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case 13:
/* 110 */         namespace = (Namespace)event;
/* 111 */         this._streamWriter.writeNamespace(namespace.getPrefix(), namespace.getNamespaceURI());
/*     */         return;
/*     */       
/*     */       case 5:
/* 115 */         comment = (Comment)event;
/* 116 */         this._streamWriter.writeComment(comment.getText());
/*     */         return;
/*     */       
/*     */       case 3:
/* 120 */         processingInstruction = (ProcessingInstruction)event;
/* 121 */         this._streamWriter.writeProcessingInstruction(processingInstruction.getTarget(), processingInstruction.getData());
/*     */         return;
/*     */       
/*     */       case 4:
/* 125 */         characters1 = event.asCharacters();
/*     */         
/* 127 */         if (characters1.isCData()) {
/* 128 */           this._streamWriter.writeCData(characters1.getData());
/*     */         } else {
/*     */           
/* 131 */           this._streamWriter.writeCharacters(characters1.getData());
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 9:
/* 136 */         entityReference = (EntityReference)event;
/* 137 */         this._streamWriter.writeEntityRef(entityReference.getName());
/*     */         return;
/*     */       
/*     */       case 10:
/* 141 */         attribute = (Attribute)event;
/* 142 */         qname = attribute.getName();
/* 143 */         this._streamWriter.writeAttribute(qname.getPrefix(), qname.getNamespaceURI(), qname.getLocalPart(), attribute.getValue());
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 12:
/* 149 */         characters = (Characters)event;
/* 150 */         if (characters.isCData()) {
/* 151 */           this._streamWriter.writeCData(characters.getData());
/*     */         }
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/* 157 */         this._streamWriter.writeEndElement();
/*     */         return;
/*     */       
/*     */       case 8:
/* 161 */         this._streamWriter.writeEndDocument();
/*     */         return;
/*     */     } 
/*     */     
/* 165 */     throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.eventTypeNotSupported", new Object[] { Util.getEventTypeString(type) })); }
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
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 177 */     return this._streamWriter.getPrefix(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 186 */     return this._streamWriter.getNamespaceContext();
/*     */   }
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
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 200 */     this._streamWriter.setDefaultNamespace(uri);
/*     */   }
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
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 214 */     this._streamWriter.setNamespaceContext(namespaceContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 226 */     this._streamWriter.setPrefix(prefix, uri);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\StAXEventWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */