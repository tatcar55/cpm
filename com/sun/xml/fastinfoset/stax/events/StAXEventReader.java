/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class StAXEventReader
/*     */   implements XMLEventReader
/*     */ {
/*     */   protected XMLStreamReader _streamReader;
/*     */   protected XMLEventAllocator _eventAllocator;
/*     */   private XMLEvent _currentEvent;
/*  35 */   private XMLEvent[] events = new XMLEvent[3];
/*  36 */   private int size = 3;
/*  37 */   private int currentIndex = 0;
/*     */   
/*     */   private boolean hasEvent = false;
/*     */   
/*     */   public StAXEventReader(XMLStreamReader reader) throws XMLStreamException {
/*  42 */     this._streamReader = reader;
/*  43 */     this._eventAllocator = (XMLEventAllocator)reader.getProperty("javax.xml.stream.allocator");
/*  44 */     if (this._eventAllocator == null) {
/*  45 */       this._eventAllocator = new StAXEventAllocatorBase();
/*     */     }
/*     */     
/*  48 */     if (this._streamReader.hasNext()) {
/*     */       
/*  50 */       this._streamReader.next();
/*  51 */       this._currentEvent = this._eventAllocator.allocate(this._streamReader);
/*  52 */       this.events[0] = this._currentEvent;
/*  53 */       this.hasEvent = true;
/*     */     } else {
/*  55 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.noElement"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/*  60 */     return this.hasEvent;
/*     */   }
/*     */   
/*     */   public XMLEvent nextEvent() throws XMLStreamException {
/*  64 */     XMLEvent event = null;
/*  65 */     XMLEvent nextEvent = null;
/*  66 */     if (this.hasEvent) {
/*     */       
/*  68 */       event = this.events[this.currentIndex];
/*  69 */       this.events[this.currentIndex] = null;
/*  70 */       if (this._streamReader.hasNext()) {
/*     */ 
/*     */         
/*  73 */         this._streamReader.next();
/*  74 */         nextEvent = this._eventAllocator.allocate(this._streamReader);
/*  75 */         if (++this.currentIndex == this.size)
/*  76 */           this.currentIndex = 0; 
/*  77 */         this.events[this.currentIndex] = nextEvent;
/*  78 */         this.hasEvent = true;
/*     */       } else {
/*  80 */         this._currentEvent = null;
/*  81 */         this.hasEvent = false;
/*     */       } 
/*  83 */       return event;
/*     */     } 
/*     */     
/*  86 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/*  92 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  97 */     this._streamReader.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 107 */     if (!this.hasEvent) {
/* 108 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/* 111 */     if (!this._currentEvent.isStartElement()) {
/* 112 */       StAXDocumentParser parser = (StAXDocumentParser)this._streamReader;
/* 113 */       return parser.getElementText(true);
/*     */     } 
/* 115 */     return this._streamReader.getElementText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 125 */     return this._streamReader.getProperty(name);
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
/*     */   public XMLEvent nextTag() throws XMLStreamException {
/* 137 */     if (!this.hasEvent) {
/* 138 */       throw new NoSuchElementException();
/*     */     }
/* 140 */     StAXDocumentParser parser = (StAXDocumentParser)this._streamReader;
/* 141 */     parser.nextTag(true);
/* 142 */     return this._eventAllocator.allocate(this._streamReader);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object next() {
/*     */     try {
/* 148 */       return nextEvent();
/* 149 */     } catch (XMLStreamException streamException) {
/* 150 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public XMLEvent peek() throws XMLStreamException {
/* 155 */     if (!this.hasEvent)
/* 156 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.noElement")); 
/* 157 */     this._currentEvent = this.events[this.currentIndex];
/* 158 */     return this._currentEvent;
/*     */   }
/*     */   
/*     */   public void setAllocator(XMLEventAllocator allocator) {
/* 162 */     if (allocator == null) {
/* 163 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.nullXMLEventAllocator"));
/*     */     }
/* 165 */     this._eventAllocator = allocator;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\StAXEventReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */