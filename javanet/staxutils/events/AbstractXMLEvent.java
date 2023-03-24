/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import javanet.staxutils.helpers.EventMatcher;
/*     */ import javanet.staxutils.helpers.UnknownLocation;
/*     */ import javanet.staxutils.io.XMLWriterUtils;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractXMLEvent
/*     */   implements ExtendedXMLEvent, Serializable, Cloneable
/*     */ {
/*     */   protected Location location;
/*     */   protected QName schemaType;
/*     */   
/*     */   public AbstractXMLEvent() {}
/*     */   
/*     */   public AbstractXMLEvent(Location location) {
/*  78 */     this.location = location;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractXMLEvent(Location location, QName schemaType) {
/*  84 */     this.location = location;
/*  85 */     this.schemaType = schemaType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractXMLEvent(XMLEvent that) {
/*  91 */     this.location = that.getLocation();
/*  92 */     this.schemaType = that.getSchemaType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getLocation() {
/*  98 */     return (this.location == null) ? (Location)UnknownLocation.INSTANCE : this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getSchemaType() {
/* 104 */     return this.schemaType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters asCharacters() {
/* 110 */     return (Characters)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement asEndElement() {
/* 116 */     return (EndElement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement asStartElement() {
/* 122 */     return (StartElement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttribute() {
/* 128 */     return (getEventType() == 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCharacters() {
/* 134 */     switch (getEventType()) {
/*     */       
/*     */       case 4:
/*     */       case 6:
/*     */       case 12:
/* 139 */         return true;
/*     */     } 
/*     */     
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndDocument() {
/* 150 */     return (getEventType() == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndElement() {
/* 156 */     return (getEventType() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntityReference() {
/* 162 */     return (getEventType() == 9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNamespace() {
/* 168 */     return (getEventType() == 13);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProcessingInstruction() {
/* 174 */     return (getEventType() == 3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartDocument() {
/* 180 */     return (getEventType() == 7);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartElement() {
/* 186 */     return (getEventType() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 194 */       return super.clone();
/*     */     }
/* 196 */     catch (CloneNotSupportedException e) {
/*     */ 
/*     */       
/* 199 */       throw new RuntimeException("Unexpected exception cloning XMLEvent", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(XMLEvent event) {
/* 208 */     return EventMatcher.eventsMatch(this, event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEvent(XMLStreamWriter writer) throws XMLStreamException {
/* 214 */     XMLWriterUtils.writeEvent(this, writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
/*     */     try {
/* 222 */       XMLWriterUtils.writeEvent(this, writer);
/*     */     }
/* 224 */     catch (IOException e) {
/*     */       
/* 226 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 237 */       writeAsEncodedUnicode(writer);
/*     */     }
/* 239 */     catch (XMLStreamException e) {}
/*     */ 
/*     */ 
/*     */     
/* 243 */     return writer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\AbstractXMLEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */