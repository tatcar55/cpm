/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.io.Writer;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.EndElement;
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
/*     */ abstract class EventHelper
/*     */   implements XMLEvent
/*     */ {
/*     */   private final Location location;
/*     */   
/*     */   protected EventHelper(Location location) {
/*  56 */     this.location = location;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/*  60 */     return this.location;
/*     */   }
/*     */   
/*     */   public boolean isStartElement() {
/*  64 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAttribute() {
/*  68 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isNamespace() {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEntityReference() {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isProcessingInstruction() {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCharacters() {
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isStartDocument() {
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEndDocument() {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public StartElement asStartElement() {
/* 100 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public EndElement asEndElement() {
/* 104 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Characters asCharacters() {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public QName getSchemaType() {
/* 112 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public abstract void writeAsEncodedUnicode(Writer paramWriter) throws XMLStreamException;
/*     */   
/*     */   public int getEventType() {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\EventHelper.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */