/*     */ package com.sun.xml.fastinfoset.stax.factory;
/*     */ import com.sun.xml.fastinfoset.stax.events.AttributeBase;
/*     */ import com.sun.xml.fastinfoset.stax.events.CharactersEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.CommentEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.DTDEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.EndDocumentEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.EndElementEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.EntityReferenceEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.NamespaceBase;
/*     */ import com.sun.xml.fastinfoset.stax.events.ProcessingInstructionEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.StartDocumentEvent;
/*     */ import com.sun.xml.fastinfoset.stax.events.StartElementEvent;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.DTD;
/*     */ import javax.xml.stream.events.EndDocument;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.EntityDeclaration;
/*     */ import javax.xml.stream.events.EntityReference;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ 
/*     */ public class StAXEventFactory extends XMLEventFactory {
/*  30 */   Location location = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(Location location) {
/*  43 */     this.location = location;
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
/*     */   public Attribute createAttribute(String prefix, String namespaceURI, String localName, String value) {
/*  55 */     AttributeBase attr = new AttributeBase(prefix, namespaceURI, localName, value, null);
/*  56 */     if (this.location != null) attr.setLocation(this.location); 
/*  57 */     return (Attribute)attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(String localName, String value) {
/*  67 */     AttributeBase attr = new AttributeBase(localName, value);
/*  68 */     if (this.location != null) attr.setLocation(this.location); 
/*  69 */     return (Attribute)attr;
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(QName name, String value) {
/*  73 */     AttributeBase attr = new AttributeBase(name, value);
/*  74 */     if (this.location != null) attr.setLocation(this.location); 
/*  75 */     return (Attribute)attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Namespace createNamespace(String namespaceURI) {
/*  84 */     NamespaceBase event = new NamespaceBase(namespaceURI);
/*  85 */     if (this.location != null) event.setLocation(this.location); 
/*  86 */     return (Namespace)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Namespace createNamespace(String prefix, String namespaceURI) {
/*  96 */     NamespaceBase event = new NamespaceBase(prefix, namespaceURI);
/*  97 */     if (this.location != null) event.setLocation(this.location); 
/*  98 */     return (Namespace)event;
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
/*     */   public StartElement createStartElement(QName name, Iterator attributes, Iterator namespaces) {
/* 111 */     return createStartElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attributes, namespaces);
/*     */   }
/*     */   
/*     */   public StartElement createStartElement(String prefix, String namespaceUri, String localName) {
/* 115 */     StartElementEvent event = new StartElementEvent(prefix, namespaceUri, localName);
/* 116 */     if (this.location != null) event.setLocation(this.location); 
/* 117 */     return (StartElement)event;
/*     */   }
/*     */   
/*     */   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces) {
/* 121 */     return createStartElement(prefix, namespaceUri, localName, attributes, namespaces, null);
/*     */   }
/*     */   
/*     */   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces, NamespaceContext context) {
/* 125 */     StartElementEvent elem = new StartElementEvent(prefix, namespaceUri, localName);
/* 126 */     elem.addAttributes(attributes);
/* 127 */     elem.addNamespaces(namespaces);
/* 128 */     elem.setNamespaceContext(context);
/* 129 */     if (this.location != null) elem.setLocation(this.location); 
/* 130 */     return (StartElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement createEndElement(QName name, Iterator namespaces) {
/* 141 */     return createEndElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), namespaces);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement createEndElement(String prefix, String namespaceUri, String localName) {
/* 152 */     EndElementEvent event = new EndElementEvent(prefix, namespaceUri, localName);
/* 153 */     if (this.location != null) event.setLocation(this.location); 
/* 154 */     return (EndElement)event;
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
/*     */   public EndElement createEndElement(String prefix, String namespaceUri, String localName, Iterator<Namespace> namespaces) {
/* 168 */     EndElementEvent event = new EndElementEvent(prefix, namespaceUri, localName);
/* 169 */     if (namespaces != null)
/* 170 */       while (namespaces.hasNext()) {
/* 171 */         event.addNamespace(namespaces.next());
/*     */       } 
/* 173 */     if (this.location != null) event.setLocation(this.location); 
/* 174 */     return (EndElement)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createCharacters(String content) {
/* 184 */     CharactersEvent charEvent = new CharactersEvent(content);
/* 185 */     if (this.location != null) charEvent.setLocation(this.location); 
/* 186 */     return (Characters)charEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createCData(String content) {
/* 195 */     CharactersEvent charEvent = new CharactersEvent(content, true);
/* 196 */     if (this.location != null) charEvent.setLocation(this.location); 
/* 197 */     return (Characters)charEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createSpace(String content) {
/* 206 */     CharactersEvent event = new CharactersEvent(content);
/* 207 */     event.setSpace(true);
/* 208 */     if (this.location != null) event.setLocation(this.location); 
/* 209 */     return (Characters)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createIgnorableSpace(String content) {
/* 217 */     CharactersEvent event = new CharactersEvent(content, false);
/* 218 */     event.setSpace(true);
/* 219 */     event.setIgnorable(true);
/* 220 */     if (this.location != null) event.setLocation(this.location); 
/* 221 */     return (Characters)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument() {
/* 228 */     StartDocumentEvent event = new StartDocumentEvent();
/* 229 */     if (this.location != null) event.setLocation(this.location); 
/* 230 */     return (StartDocument)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument(String encoding) {
/* 240 */     StartDocumentEvent event = new StartDocumentEvent(encoding);
/* 241 */     if (this.location != null) event.setLocation(this.location); 
/* 242 */     return (StartDocument)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument(String encoding, String version) {
/* 253 */     StartDocumentEvent event = new StartDocumentEvent(encoding, version);
/* 254 */     if (this.location != null) event.setLocation(this.location); 
/* 255 */     return (StartDocument)event;
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
/*     */   public StartDocument createStartDocument(String encoding, String version, boolean standalone) {
/* 267 */     StartDocumentEvent event = new StartDocumentEvent(encoding, version);
/* 268 */     event.setStandalone(standalone);
/* 269 */     if (this.location != null) event.setLocation(this.location); 
/* 270 */     return (StartDocument)event;
/*     */   }
/*     */   
/*     */   public EndDocument createEndDocument() {
/* 274 */     EndDocumentEvent event = new EndDocumentEvent();
/* 275 */     if (this.location != null) event.setLocation(this.location); 
/* 276 */     return (EndDocument)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityReference createEntityReference(String name, EntityDeclaration entityDeclaration) {
/* 286 */     EntityReferenceEvent event = new EntityReferenceEvent(name, entityDeclaration);
/* 287 */     if (this.location != null) event.setLocation(this.location); 
/* 288 */     return (EntityReference)event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comment createComment(String text) {
/* 297 */     CommentEvent charEvent = new CommentEvent(text);
/* 298 */     if (this.location != null) charEvent.setLocation(this.location); 
/* 299 */     return (Comment)charEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTD createDTD(String dtd) {
/* 310 */     DTDEvent dtdEvent = new DTDEvent(dtd);
/* 311 */     if (this.location != null) dtdEvent.setLocation(this.location); 
/* 312 */     return (DTD)dtdEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction createProcessingInstruction(String target, String data) {
/* 323 */     ProcessingInstructionEvent event = new ProcessingInstructionEvent(target, data);
/* 324 */     if (this.location != null) event.setLocation(this.location); 
/* 325 */     return (ProcessingInstruction)event;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\factory\StAXEventFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */