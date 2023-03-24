/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.events.EventAllocator;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.stream.util.XMLEventAllocator;
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
/*     */ public class XMLStreamEventReader
/*     */   implements XMLEventReader
/*     */ {
/*     */   private XMLStreamReader reader;
/*     */   private XMLEventAllocator allocator;
/*     */   private XMLEvent nextEvent;
/*     */   private boolean closed;
/*     */   
/*     */   public XMLStreamEventReader(XMLStreamReader reader) {
/*  67 */     this.reader = reader;
/*  68 */     this.allocator = (XMLEventAllocator)new EventAllocator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamEventReader(XMLStreamReader reader, XMLEventAllocator allocator) {
/*  75 */     this.reader = reader;
/*  76 */     this.allocator = (allocator == null) ? (XMLEventAllocator)new EventAllocator() : allocator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/*  86 */     throw new IllegalArgumentException("Unknown property: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean hasNext() {
/*  92 */     if (this.closed)
/*     */     {
/*  94 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 100 */       return this.reader.hasNext();
/*     */     }
/* 102 */     catch (XMLStreamException e) {
/*     */ 
/*     */       
/* 105 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized XMLEvent nextTag() throws XMLStreamException {
/* 113 */     if (this.closed)
/*     */     {
/* 115 */       throw new XMLStreamException("Stream has been closed");
/*     */     }
/*     */ 
/*     */     
/* 119 */     this.nextEvent = null;
/* 120 */     this.reader.nextTag();
/* 121 */     return nextEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getElementText() throws XMLStreamException {
/* 127 */     if (this.closed)
/*     */     {
/* 129 */       throw new XMLStreamException("Stream has been closed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.nextEvent = null;
/* 135 */     return this.reader.getElementText();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized XMLEvent nextEvent() throws XMLStreamException {
/*     */     XMLEvent event;
/* 141 */     if (this.closed)
/*     */     {
/* 143 */       throw new XMLStreamException("Stream has been closed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (this.nextEvent != null) {
/*     */       
/* 150 */       event = this.nextEvent;
/* 151 */       this.nextEvent = null;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       event = allocateEvent();
/* 156 */       this.reader.next();
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized XMLEvent peek() throws XMLStreamException {
/* 166 */     if (this.closed)
/*     */     {
/* 168 */       throw new XMLStreamException("Stream has been closed");
/*     */     }
/*     */ 
/*     */     
/* 172 */     if (this.nextEvent == null) {
/*     */       
/* 174 */       this.nextEvent = allocateEvent();
/* 175 */       this.reader.next();
/*     */     } 
/*     */ 
/*     */     
/* 179 */     return this.nextEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object next() {
/*     */     try {
/* 187 */       return nextEvent();
/*     */     }
/* 189 */     catch (XMLStreamException e) {
/*     */ 
/*     */       
/* 192 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 200 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws XMLStreamException {
/* 206 */     if (!this.closed) {
/*     */       
/* 208 */       this.reader.close();
/* 209 */       this.closed = true;
/* 210 */       this.nextEvent = null;
/* 211 */       this.reader = null;
/* 212 */       this.allocator = null;
/*     */     } 
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
/*     */   protected XMLEvent allocateEvent() throws XMLStreamException {
/* 226 */     return this.allocator.allocate(this.reader);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLStreamEventReader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */