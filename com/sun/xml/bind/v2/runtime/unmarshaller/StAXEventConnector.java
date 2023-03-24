/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StAXEventConnector
/*     */   extends StAXConnector
/*     */ {
/*     */   private final XMLEventReader staxEventReader;
/*     */   private XMLEvent event;
/*  81 */   private final AttributesImpl attrs = new AttributesImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private final StringBuilder buffer = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean seenText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXEventConnector(XMLEventReader staxCore, XmlVisitor visitor) {
/* 101 */     super(visitor);
/* 102 */     this.staxEventReader = staxCore;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bridge() throws XMLStreamException {
/*     */     try {
/* 109 */       int depth = 0;
/*     */       
/* 111 */       this.event = this.staxEventReader.peek();
/*     */       
/* 113 */       if (!this.event.isStartDocument() && !this.event.isStartElement()) {
/* 114 */         throw new IllegalStateException();
/*     */       }
/*     */       
/*     */       do {
/* 118 */         this.event = this.staxEventReader.nextEvent();
/* 119 */       } while (!this.event.isStartElement());
/*     */       
/* 121 */       handleStartDocument(this.event.asStartElement().getNamespaceContext());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 128 */         switch (this.event.getEventType()) {
/*     */           case 1:
/* 130 */             handleStartElement(this.event.asStartElement());
/* 131 */             depth++;
/*     */             break;
/*     */           case 2:
/* 134 */             depth--;
/* 135 */             handleEndElement(this.event.asEndElement());
/* 136 */             if (depth == 0)
/*     */               break;  break;
/*     */           case 4:
/*     */           case 6:
/*     */           case 12:
/* 141 */             handleCharacters(this.event.asCharacters());
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 146 */         this.event = this.staxEventReader.nextEvent();
/*     */       } 
/*     */       
/* 149 */       handleEndDocument();
/* 150 */       this.event = null;
/* 151 */     } catch (SAXException e) {
/* 152 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Location getCurrentLocation() {
/* 157 */     return this.event.getLocation();
/*     */   }
/*     */   
/*     */   protected String getCurrentQName() {
/*     */     QName qName;
/* 162 */     if (this.event.isEndElement()) {
/* 163 */       qName = this.event.asEndElement().getName();
/*     */     } else {
/* 165 */       qName = this.event.asStartElement().getName();
/* 166 */     }  return getQName(qName.getPrefix(), qName.getLocalPart());
/*     */   }
/*     */   
/*     */   private void handleCharacters(Characters event) throws SAXException, XMLStreamException {
/*     */     XMLEvent next;
/* 171 */     if (!this.predictor.expectText()) {
/*     */       return;
/*     */     }
/* 174 */     this.seenText = true;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 179 */       next = this.staxEventReader.peek();
/* 180 */       if (!isIgnorable(next))
/*     */         break; 
/* 182 */       this.staxEventReader.nextEvent();
/*     */     } 
/*     */     
/* 185 */     if (isTag(next)) {
/*     */       
/* 187 */       this.visitor.text(event.getData());
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 193 */     this.buffer.append(event.getData());
/*     */ 
/*     */     
/*     */     while (true) {
/* 197 */       next = this.staxEventReader.peek();
/* 198 */       if (!isIgnorable(next)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 203 */         if (isTag(next)) {
/*     */           
/* 205 */           this.visitor.text(this.buffer);
/* 206 */           this.buffer.setLength(0);
/*     */           
/*     */           return;
/*     */         } 
/* 210 */         this.buffer.append(next.asCharacters().getData());
/* 211 */         this.staxEventReader.nextEvent();
/*     */         continue;
/*     */       } 
/*     */       this.staxEventReader.nextEvent();
/*     */     }  } private boolean isTag(XMLEvent event) {
/* 216 */     int eventType = event.getEventType();
/* 217 */     return (eventType == 1 || eventType == 2);
/*     */   }
/*     */   
/*     */   private boolean isIgnorable(XMLEvent event) {
/* 221 */     int eventType = event.getEventType();
/* 222 */     return (eventType == 5 || eventType == 3);
/*     */   }
/*     */   
/*     */   private void handleEndElement(EndElement event) throws SAXException {
/* 226 */     if (!this.seenText && this.predictor.expectText()) {
/* 227 */       this.visitor.text("");
/*     */     }
/*     */ 
/*     */     
/* 231 */     QName qName = event.getName();
/* 232 */     this.tagName.uri = fixNull(qName.getNamespaceURI());
/* 233 */     this.tagName.local = qName.getLocalPart();
/* 234 */     this.visitor.endElement(this.tagName);
/*     */ 
/*     */     
/* 237 */     for (Iterator<Namespace> i = event.getNamespaces(); i.hasNext(); ) {
/* 238 */       String prefix = fixNull(((Namespace)i.next()).getPrefix());
/* 239 */       this.visitor.endPrefixMapping(prefix);
/*     */     } 
/*     */     
/* 242 */     this.seenText = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement(StartElement event) throws SAXException {
/* 247 */     for (Iterator<Namespace> i = event.getNamespaces(); i.hasNext(); ) {
/* 248 */       Namespace ns = i.next();
/* 249 */       this.visitor.startPrefixMapping(fixNull(ns.getPrefix()), fixNull(ns.getNamespaceURI()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     QName qName = event.getName();
/* 256 */     this.tagName.uri = fixNull(qName.getNamespaceURI());
/* 257 */     String localName = qName.getLocalPart();
/* 258 */     this.tagName.uri = fixNull(qName.getNamespaceURI());
/* 259 */     this.tagName.local = localName;
/* 260 */     this.tagName.atts = getAttributes(event);
/* 261 */     this.visitor.startElement(this.tagName);
/*     */     
/* 263 */     this.seenText = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes(StartElement event) {
/* 274 */     this.attrs.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     for (Iterator<Attribute> i = event.getAttributes(); i.hasNext(); ) {
/* 282 */       String qName; Attribute staxAttr = i.next();
/*     */       
/* 284 */       QName name = staxAttr.getName();
/* 285 */       String uri = fixNull(name.getNamespaceURI());
/* 286 */       String localName = name.getLocalPart();
/* 287 */       String prefix = name.getPrefix();
/*     */       
/* 289 */       if (prefix == null || prefix.length() == 0) {
/* 290 */         qName = localName;
/*     */       } else {
/* 292 */         qName = prefix + ':' + localName;
/* 293 */       }  String type = staxAttr.getDTDType();
/* 294 */       String value = staxAttr.getValue();
/*     */       
/* 296 */       this.attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 299 */     return this.attrs;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\StAXEventConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */