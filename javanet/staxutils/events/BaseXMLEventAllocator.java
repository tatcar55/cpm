/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.Comment;
/*     */ import javax.xml.stream.events.DTD;
/*     */ import javax.xml.stream.events.EndDocument;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.EntityReference;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
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
/*     */ public abstract class BaseXMLEventAllocator
/*     */   implements XMLEventAllocator
/*     */ {
/*     */   public void allocate(XMLStreamReader reader, XMLEventConsumer consumer) throws XMLStreamException {
/*  55 */     consumer.add(allocate(reader));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent allocate(XMLStreamReader reader) throws XMLStreamException {
/*  64 */     int eventType = reader.getEventType();
/*  65 */     switch (eventType) {
/*     */       
/*     */       case 1:
/*  68 */         return allocateStartElement(reader);
/*     */       
/*     */       case 2:
/*  71 */         return allocateEndElement(reader);
/*     */       
/*     */       case 4:
/*  74 */         return allocateCharacters(reader);
/*     */       
/*     */       case 12:
/*  77 */         return allocateCData(reader);
/*     */       
/*     */       case 6:
/*  80 */         return allocateIgnorableSpace(reader);
/*     */       
/*     */       case 5:
/*  83 */         return allocateComment(reader);
/*     */       
/*     */       case 11:
/*  86 */         return allocateDTD(reader);
/*     */       
/*     */       case 9:
/*  89 */         return allocateEntityReference(reader);
/*     */       
/*     */       case 3:
/*  92 */         return allocateProcessingInstruction(reader);
/*     */       
/*     */       case 7:
/*  95 */         return allocateStartDocument(reader);
/*     */       
/*     */       case 8:
/*  98 */         return allocateEndDocument(reader);
/*     */     } 
/*     */     
/* 101 */     throw new XMLStreamException("Unexpected reader state: " + eventType);
/*     */   }
/*     */   
/*     */   public abstract StartElement allocateStartElement(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract EndElement allocateEndElement(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract Characters allocateCharacters(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract Characters allocateCData(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract Characters allocateIgnorableSpace(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract EntityReference allocateEntityReference(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract Comment allocateComment(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract DTD allocateDTD(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract StartDocument allocateStartDocument(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract EndDocument allocateEndDocument(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract ProcessingInstruction allocateProcessingInstruction(XMLStreamReader paramXMLStreamReader) throws XMLStreamException;
/*     */   
/*     */   public abstract NamespaceContext createStableNamespaceContext(XMLStreamReader paramXMLStreamReader);
/*     */   
/*     */   public abstract Location createStableLocation(XMLStreamReader paramXMLStreamReader);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\BaseXMLEventAllocator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */