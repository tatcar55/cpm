/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLEventWriter;
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
/*     */ public class XMLEventPipe
/*     */ {
/*     */   public static final int QUEUE_CAPACITY = 16;
/*  85 */   private List eventQueue = new LinkedList();
/*     */ 
/*     */   
/*  88 */   private int capacity = 16;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readEndClosed;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean writeEndClosed;
/*     */ 
/*     */ 
/*     */   
/* 100 */   private PipedXMLEventReader readEnd = new PipedXMLEventReader(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private PipedXMLEventWriter writeEnd = new PipedXMLEventWriter(this);
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
/*     */   public XMLEventPipe(int capacity) {
/* 123 */     this.capacity = capacity;
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
/*     */   public synchronized XMLEventReader getReadEnd() {
/* 135 */     if (this.readEnd == null)
/*     */     {
/* 137 */       this.readEnd = new PipedXMLEventReader(this);
/*     */     }
/*     */ 
/*     */     
/* 141 */     return this.readEnd;
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
/*     */   public synchronized XMLEventWriter getWriteEnd() {
/* 153 */     if (this.writeEnd == null)
/*     */     {
/* 155 */       this.writeEnd = new PipedXMLEventWriter(this);
/*     */     }
/*     */ 
/*     */     
/* 159 */     return this.writeEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventPipe() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PipedXMLEventWriter
/*     */     extends BaseXMLEventWriter
/*     */   {
/*     */     private XMLEventPipe pipe;
/*     */ 
/*     */ 
/*     */     
/*     */     public PipedXMLEventWriter(XMLEventPipe pipe) {
/* 177 */       this.pipe = pipe;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void close() throws XMLStreamException {
/* 183 */       super.close();
/*     */       
/* 185 */       synchronized (this.pipe) {
/*     */         
/* 187 */         if (this.pipe.readEndClosed)
/*     */         {
/* 189 */           this.pipe.eventQueue.clear();
/*     */         }
/*     */ 
/*     */         
/* 193 */         this.pipe.writeEndClosed = true;
/* 194 */         this.pipe.notifyAll();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void sendEvent(XMLEvent event) throws XMLStreamException {
/* 202 */       synchronized (this.pipe) {
/*     */         
/* 204 */         if (this.pipe.readEndClosed) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 211 */         if (this.pipe.capacity > 0)
/*     */         {
/* 213 */           while (this.pipe.eventQueue.size() >= this.pipe.capacity) {
/*     */ 
/*     */             
/*     */             try {
/* 217 */               this.pipe.wait();
/*     */             }
/* 219 */             catch (InterruptedException e) {
/*     */               
/* 221 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 229 */         this.pipe.eventQueue.add(event);
/* 230 */         if (this.pipe.eventQueue.size() == 1)
/*     */         {
/* 232 */           this.pipe.notifyAll();
/*     */         }
/*     */ 
/*     */         
/* 236 */         if (event.isEndDocument())
/*     */         {
/* 238 */           close();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PipedXMLEventReader
/*     */     extends BaseXMLEventReader
/*     */   {
/*     */     private XMLEventPipe pipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PipedXMLEventReader(XMLEventPipe pipe) {
/* 262 */       this.pipe = pipe;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized XMLEvent nextEvent() throws XMLStreamException {
/* 268 */       if (this.closed)
/*     */       {
/* 270 */         throw new XMLStreamException("Stream has been closed");
/*     */       }
/*     */ 
/*     */       
/* 274 */       synchronized (this.pipe) {
/*     */         
/* 276 */         while (this.pipe.eventQueue.size() == 0) {
/*     */           
/* 278 */           if (this.pipe.writeEndClosed)
/*     */           {
/* 280 */             throw new NoSuchElementException("Stream has completed");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 286 */             this.pipe.wait();
/*     */           }
/* 288 */           catch (InterruptedException e) {
/*     */             
/* 290 */             e.printStackTrace();
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 296 */         boolean notify = (this.pipe.capacity > 0 && this.pipe.eventQueue.size() >= this.pipe.capacity);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 301 */         XMLEvent nextEvent = this.pipe.eventQueue.remove(0);
/* 302 */         if (notify)
/*     */         {
/* 304 */           this.pipe.notifyAll();
/*     */         }
/*     */         
/* 307 */         return nextEvent;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized boolean hasNext() {
/* 315 */       if (this.closed)
/*     */       {
/* 317 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 321 */       synchronized (this.pipe) {
/*     */         
/* 323 */         while (this.pipe.eventQueue.size() == 0) {
/*     */           
/* 325 */           if (this.pipe.writeEndClosed) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 333 */             this.pipe.wait();
/*     */           }
/* 335 */           catch (InterruptedException e) {}
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 340 */         return (this.pipe.eventQueue.size() > 0);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized XMLEvent peek() throws XMLStreamException {
/* 348 */       if (this.closed)
/*     */       {
/* 350 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 354 */       synchronized (this.pipe) {
/*     */ 
/*     */         
/* 357 */         while (this.pipe.eventQueue.size() == 0) {
/*     */           
/* 359 */           if (this.pipe.writeEndClosed)
/*     */           {
/* 361 */             return null;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 367 */             this.pipe.wait();
/*     */           }
/* 369 */           catch (InterruptedException e) {}
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 374 */         return this.pipe.eventQueue.get(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void close() throws XMLStreamException {
/* 382 */       if (this.closed) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 388 */       synchronized (this.pipe) {
/*     */         
/* 390 */         this.pipe.readEndClosed = true;
/* 391 */         this.pipe.notifyAll();
/*     */       } 
/*     */ 
/*     */       
/* 395 */       super.close();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void finalize() {
/* 401 */       if (!this.closed)
/*     */       {
/* 403 */         synchronized (this.pipe) {
/*     */           
/* 405 */           this.pipe.readEndClosed = true;
/* 406 */           this.pipe.notifyAll();
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLEventPipe.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */