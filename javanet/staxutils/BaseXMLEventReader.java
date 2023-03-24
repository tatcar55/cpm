/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public abstract class BaseXMLEventReader
/*     */   implements XMLEventReader
/*     */ {
/*     */   protected boolean closed;
/*     */   
/*     */   public synchronized String getElementText() throws XMLStreamException {
/*     */     XMLEvent event;
/*  56 */     if (this.closed)
/*     */     {
/*  58 */       throw new XMLStreamException("Stream has been closed");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     StringBuffer buffer = new StringBuffer();
/*     */     
/*     */     while (true) {
/*  67 */       event = nextEvent();
/*  68 */       if (event.isCharacters()) {
/*     */ 
/*     */         
/*  71 */         if (event.getEventType() != 6)
/*     */         {
/*  73 */           buffer.append(event.asCharacters().getData()); }  continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*  77 */     if (event.isEndElement())
/*     */     {
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
/*  91 */       return buffer.toString(); } 
/*     */     throw new XMLStreamException("Non-text event encountered in getElementText(): " + event);
/*     */   }
/*     */   
/*     */   public XMLEvent nextTag() throws XMLStreamException {
/*     */     XMLEvent event;
/*  97 */     if (this.closed)
/*     */     {
/*  99 */       throw new XMLStreamException("Stream has been closed");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 106 */       if (hasNext())
/*     */       {
/* 108 */         event = nextEvent();
/* 109 */         if (event.isStartElement() || event.isEndElement())
/*     */         {
/* 111 */           return event;
/*     */         }
/* 113 */         if (event.isCharacters())
/*     */         {
/* 115 */           if (!event.asCharacters().isWhiteSpace())
/*     */           {
/* 117 */             throw new XMLStreamException("Non-ignorable space encountered");
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 122 */         else if (!(event instanceof javax.xml.stream.events.Comment))
/*     */         {
/* 124 */           throw new XMLStreamException("Non-ignorable event encountered: " + event);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 131 */         throw new XMLStreamException("Ran out of events in nextTag()");
/*     */       }
/*     */     
/*     */     }
/* 135 */     while (!event.isStartElement() && !event.isEndElement());
/*     */     
/* 137 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 143 */     throw new IllegalArgumentException("Property not supported: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws XMLStreamException {
/* 149 */     if (!this.closed)
/*     */     {
/* 151 */       this.closed = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object next() {
/*     */     try {
/* 161 */       return nextEvent();
/*     */     }
/* 163 */     catch (XMLStreamException e) {
/*     */       
/* 165 */       NoSuchElementException ex = new NoSuchElementException("Error getting next event");
/*     */       
/* 167 */       ex.initCause(e);
/* 168 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 176 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\BaseXMLEventReader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */