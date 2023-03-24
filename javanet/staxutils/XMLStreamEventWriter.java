/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.events.ExtendedXMLEvent;
/*     */ import javanet.staxutils.io.XMLWriterUtils;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class XMLStreamEventWriter
/*     */   extends BaseXMLEventWriter
/*     */ {
/*     */   private XMLStreamWriter writer;
/*     */   private StartElement savedStart;
/*     */   
/*     */   public XMLStreamEventWriter(XMLStreamWriter writer) {
/*  65 */     super(null, writer.getNamespaceContext());
/*  66 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() throws XMLStreamException {
/*  72 */     super.flush();
/*     */ 
/*     */     
/*  75 */     if (this.savedStart != null)
/*     */     {
/*  77 */       XMLWriterUtils.writeStartElement(this.savedStart, false, this.writer);
/*     */     }
/*     */ 
/*     */     
/*  81 */     this.writer.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws XMLStreamException {
/*  87 */     super.close();
/*  88 */     this.writer.close();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void sendEvent(XMLEvent event) throws XMLStreamException {
/* 106 */     if (this.savedStart != null) {
/*     */       
/* 108 */       StartElement start = this.savedStart;
/* 109 */       this.savedStart = null;
/*     */       
/* 111 */       if (event.getEventType() == 2) {
/*     */ 
/*     */ 
/*     */         
/* 115 */         XMLWriterUtils.writeStartElement(start, true, this.writer);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 121 */       XMLWriterUtils.writeStartElement(start, false, this.writer);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (event.isStartElement()) {
/*     */ 
/*     */       
/* 130 */       this.savedStart = event.asStartElement();
/*     */     }
/* 132 */     else if (event instanceof ExtendedXMLEvent) {
/*     */ 
/*     */       
/* 135 */       ((ExtendedXMLEvent)event).writeEvent(this.writer);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 140 */       XMLWriterUtils.writeEvent(event, this.writer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLStreamEventWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */