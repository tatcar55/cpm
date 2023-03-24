/*     */ package javanet.staxutils.helpers;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import javanet.staxutils.BaseXMLEventReader;
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
/*     */ public class ListEventReader
/*     */   extends BaseXMLEventReader
/*     */ {
/*  54 */   private int nextEvent = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List events;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListEventReader(List events) {
/*  67 */     this.events = events;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent nextEvent() throws XMLStreamException {
/*  73 */     if (hasNext()) {
/*     */       
/*  75 */       XMLEvent event = this.events.get(this.nextEvent);
/*  76 */       this.nextEvent++;
/*  77 */       return event;
/*     */     } 
/*     */ 
/*     */     
/*  81 */     throw new NoSuchElementException("End of stream reached");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/*  89 */     return (this.nextEvent < this.events.size());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEvent peek() throws XMLStreamException {
/*  95 */     if (hasNext())
/*     */     {
/*  97 */       return this.events.get(this.nextEvent);
/*     */     }
/*     */ 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\ListEventReader.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */