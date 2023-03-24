/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import javax.xml.stream.EventFilter;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Characters;
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
/*     */ public class StAXFilteredEvent
/*     */   implements XMLEventReader
/*     */ {
/*     */   private XMLEventReader eventReader;
/*     */   private EventFilter _filter;
/*     */   
/*     */   public StAXFilteredEvent() {}
/*     */   
/*     */   public StAXFilteredEvent(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
/*  37 */     this.eventReader = reader;
/*  38 */     this._filter = filter;
/*     */   }
/*     */   
/*     */   public void setEventReader(XMLEventReader reader) {
/*  42 */     this.eventReader = reader;
/*     */   }
/*     */   
/*     */   public void setFilter(EventFilter filter) {
/*  46 */     this._filter = filter;
/*     */   }
/*     */   
/*     */   public Object next() {
/*     */     try {
/*  51 */       return nextEvent();
/*  52 */     } catch (XMLStreamException e) {
/*  53 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLEvent nextEvent() throws XMLStreamException {
/*  59 */     if (hasNext())
/*  60 */       return this.eventReader.nextEvent(); 
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/*  66 */     StringBuffer buffer = new StringBuffer();
/*  67 */     XMLEvent e = nextEvent();
/*  68 */     if (!e.isStartElement()) {
/*  69 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.mustBeOnSTART_ELEMENT"));
/*     */     }
/*     */     
/*  72 */     while (hasNext()) {
/*  73 */       e = nextEvent();
/*  74 */       if (e.isStartElement()) {
/*  75 */         throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.getElementTextExpectTextOnly"));
/*     */       }
/*  77 */       if (e.isCharacters())
/*  78 */         buffer.append(((Characters)e).getData()); 
/*  79 */       if (e.isEndElement())
/*  80 */         return buffer.toString(); 
/*     */     } 
/*  82 */     throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.END_ELEMENTnotFound"));
/*     */   }
/*     */   
/*     */   public XMLEvent nextTag() throws XMLStreamException {
/*  86 */     while (hasNext()) {
/*  87 */       XMLEvent e = nextEvent();
/*  88 */       if (e.isStartElement() || e.isEndElement())
/*  89 */         return e; 
/*     */     } 
/*  91 */     throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.startOrEndNotFound"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*     */     try {
/*  98 */       while (this.eventReader.hasNext()) {
/*  99 */         if (this._filter.accept(this.eventReader.peek())) return true; 
/* 100 */         this.eventReader.nextEvent();
/*     */       } 
/* 102 */       return false;
/* 103 */     } catch (XMLStreamException e) {
/* 104 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove() {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLEvent peek() throws XMLStreamException {
/* 114 */     if (hasNext())
/* 115 */       return this.eventReader.peek(); 
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 121 */     this.eventReader.close();
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) {
/* 125 */     return this.eventReader.getProperty(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\StAXFilteredEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */