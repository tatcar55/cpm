/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventFactory
/*     */   extends BaseXMLEventFactory
/*     */ {
/*     */   public Attribute createAttribute(QName name, String value, Location location, QName schemaType) {
/*  65 */     return new AttributeEvent(name, value, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createCData(String content, Location location, QName schemaType) {
/*  72 */     return new CDataEvent(content, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createCharacters(String content, Location location, QName schemaType) {
/*  79 */     return new CharactersEvent(content, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Comment createComment(String text, Location location) {
/*  85 */     return new CommentEvent(text, location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DTD createDTD(String dtd, Location location) {
/*  91 */     return new DTDEvent(dtd, location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EndDocument createEndDocument(Location location) {
/*  97 */     return new EndDocumentEvent(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement createEndElement(QName name, Iterator namespaces, Location location, QName schemaType) {
/* 104 */     return new EndElementEvent(name, namespaces, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityReference createEntityReference(String name, EntityDeclaration declaration, Location location) {
/* 111 */     return new EntityReferenceEvent(name, declaration, location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createIgnorableSpace(String content, Location location) {
/* 117 */     return new IgnorableSpaceEvent(content, location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Namespace createNamespace(String prefix, String namespaceUri, Location location) {
/* 124 */     return new NamespaceEvent(prefix, namespaceUri, location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction createProcessingInstruction(String target, String data, Location location) {
/* 131 */     return new ProcessingInstructionEvent(target, data, location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Characters createSpace(String content, Location location) {
/* 137 */     return new IgnorableSpaceEvent(content, location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument(String encoding, String version, Boolean standalone, Location location, QName schemaType) {
/* 144 */     return new StartDocumentEvent(encoding, standalone, version, location, schemaType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement createStartElement(QName name, Iterator attributes, Iterator namespaces, NamespaceContext namespaceCtx, Location location, QName schemaType) {
/* 153 */     return new StartElementEvent(name, attributes, namespaces, namespaceCtx, location, schemaType);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\EventFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */