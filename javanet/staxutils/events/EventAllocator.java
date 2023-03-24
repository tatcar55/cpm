/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.Comment;
/*     */ import javax.xml.stream.events.DTD;
/*     */ import javax.xml.stream.events.EndDocument;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.EntityDeclaration;
/*     */ import javax.xml.stream.events.EntityReference;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartDocument;
/*     */ import javax.xml.stream.events.StartElement;
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
/*     */ public class EventAllocator
/*     */   extends BaseXMLEventAllocator
/*     */ {
/*     */   public XMLEventAllocator newInstance() {
/*  69 */     return new EventAllocator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement allocateStartElement(XMLStreamReader reader) throws XMLStreamException {
/*  76 */     Location location = createStableLocation(reader);
/*  77 */     QName name = reader.getName();
/*  78 */     List attributes = allocateAttributes(location, reader);
/*  79 */     List namespaces = allocateNamespaces(location, reader);
/*  80 */     NamespaceContext nsCtx = createStableNamespaceContext(reader);
/*  81 */     QName schemaType = determineSchemaType(reader);
/*     */     
/*  83 */     return new StartElementEvent(name, attributes.iterator(), namespaces.iterator(), nsCtx, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement allocateEndElement(XMLStreamReader reader) throws XMLStreamException {
/*  91 */     Location location = createStableLocation(reader);
/*  92 */     QName name = reader.getName();
/*  93 */     List namespaces = allocateNamespaces(location, reader);
/*  94 */     QName schemaType = determineSchemaType(reader);
/*     */     
/*  96 */     return new EndElementEvent(name, namespaces.iterator(), location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List allocateAttributes(Location location, XMLStreamReader reader) throws XMLStreamException {
/* 104 */     List attributes = null;
/* 105 */     for (int i = 0, s = reader.getAttributeCount(); i < s; i++) {
/*     */       
/* 107 */       QName name = reader.getAttributeName(i);
/* 108 */       String value = reader.getAttributeValue(i);
/* 109 */       String dtdType = reader.getAttributeType(i);
/* 110 */       boolean specified = reader.isAttributeSpecified(i);
/* 111 */       QName schemaType = determineAttributeSchemaType(reader, i);
/*     */       
/* 113 */       Attribute attr = new AttributeEvent(name, value, specified, dtdType, location, schemaType);
/*     */       
/* 115 */       if (attributes == null)
/*     */       {
/* 117 */         attributes = new ArrayList();
/*     */       }
/*     */       
/* 120 */       attributes.add(attr);
/*     */     } 
/*     */ 
/*     */     
/* 124 */     return (attributes != null) ? attributes : Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List allocateNamespaces(Location location, XMLStreamReader reader) throws XMLStreamException {
/* 131 */     List namespaces = null;
/* 132 */     for (int i = 0, s = reader.getNamespaceCount(); i < s; i++) {
/*     */       
/* 134 */       String prefix = reader.getNamespacePrefix(i);
/* 135 */       String nsURI = reader.getNamespaceURI(i);
/*     */       
/* 137 */       Namespace ns = new NamespaceEvent(prefix, nsURI, location);
/* 138 */       if (namespaces == null)
/*     */       {
/* 140 */         namespaces = new ArrayList();
/*     */       }
/*     */       
/* 143 */       namespaces.add(ns);
/*     */     } 
/*     */ 
/*     */     
/* 147 */     return (namespaces != null) ? namespaces : Collections.EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters allocateCData(XMLStreamReader reader) throws XMLStreamException {
/* 154 */     Location location = createStableLocation(reader);
/* 155 */     String text = reader.getText();
/* 156 */     QName schemaType = determineSchemaType(reader);
/* 157 */     return new CDataEvent(text, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters allocateCharacters(XMLStreamReader reader) throws XMLStreamException {
/* 164 */     Location location = createStableLocation(reader);
/* 165 */     String text = reader.getText();
/* 166 */     QName schemaType = determineSchemaType(reader);
/* 167 */     return new CharactersEvent(text, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters allocateIgnorableSpace(XMLStreamReader reader) throws XMLStreamException {
/* 174 */     Location location = createStableLocation(reader);
/* 175 */     String text = reader.getText();
/* 176 */     QName schemaType = determineSchemaType(reader);
/* 177 */     return new IgnorableSpaceEvent(text, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comment allocateComment(XMLStreamReader reader) throws XMLStreamException {
/* 184 */     Location location = createStableLocation(reader);
/* 185 */     String text = reader.getText();
/* 186 */     return new CommentEvent(text, location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTD allocateDTD(XMLStreamReader reader) throws XMLStreamException {
/* 192 */     Location location = createStableLocation(reader);
/* 193 */     List entities = (List)reader.getProperty("javax.xml.stream.entities");
/* 194 */     List notations = (List)reader.getProperty("javax.xml.stream.notations");
/* 195 */     String text = reader.getText();
/*     */     
/* 197 */     return new DTDEvent(text, entities, notations, location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument allocateStartDocument(XMLStreamReader reader) throws XMLStreamException {
/* 204 */     Location location = createStableLocation(reader);
/* 205 */     String encoding = reader.getCharacterEncodingScheme();
/* 206 */     String version = reader.getVersion();
/* 207 */     Boolean standalone = reader.standaloneSet() ? Boolean.valueOf(reader.isStandalone()) : null;
/*     */ 
/*     */     
/* 210 */     QName schemaType = determineSchemaType(reader);
/*     */     
/* 212 */     return new StartDocumentEvent(encoding, standalone, version, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndDocument allocateEndDocument(XMLStreamReader reader) throws XMLStreamException {
/* 220 */     Location location = createStableLocation(reader);
/* 221 */     QName schemaType = determineSchemaType(reader);
/* 222 */     return new EndDocumentEvent(location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityReference allocateEntityReference(XMLStreamReader reader) throws XMLStreamException {
/* 229 */     Location location = createStableLocation(reader);
/* 230 */     String name = reader.getLocalName();
/* 231 */     EntityDeclaration decl = determineEntityDeclaration(name, reader);
/*     */     
/* 233 */     return new EntityReferenceEvent(name, decl, location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction allocateProcessingInstruction(XMLStreamReader reader) throws XMLStreamException {
/* 240 */     Location location = createStableLocation(reader);
/* 241 */     String target = reader.getPITarget();
/* 242 */     String data = reader.getPIData();
/*     */     
/* 244 */     return new ProcessingInstructionEvent(target, data, location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName determineSchemaType(XMLStreamReader reader) {
/* 251 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName determineAttributeSchemaType(XMLStreamReader reader, int index) {
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDeclaration determineEntityDeclaration(String name, XMLStreamReader reader) {
/* 264 */     return new EntityDeclarationEvent(name, reader.getText(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location createStableLocation(XMLStreamReader reader) {
/* 271 */     return reader.getLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext createStableNamespaceContext(XMLStreamReader reader) {
/* 278 */     return reader.getNamespaceContext();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\EventAllocator.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */