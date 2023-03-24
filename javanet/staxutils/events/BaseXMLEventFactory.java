/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLEventFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseXMLEventFactory
/*     */   extends XMLEventFactory
/*     */ {
/*     */   protected Location location;
/*     */   
/*     */   public void setLocation(Location location) {
/*  71 */     this.location = location;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(QName name, String value) {
/*  77 */     return createAttribute(name, value, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(String prefix, String namespaceURI, String localName, String value) {
/*  84 */     return createAttribute(new QName(namespaceURI, localName, prefix), value, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(String localName, String value) {
/*  91 */     return createAttribute(new QName(localName), value, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Attribute createAttribute(QName paramQName1, String paramString, Location paramLocation, QName paramQName2);
/*     */ 
/*     */   
/*     */   public Characters createCData(String content) {
/* 100 */     return createCData(content, this.location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Characters createCData(String paramString, Location paramLocation, QName paramQName);
/*     */ 
/*     */   
/*     */   public Characters createCharacters(String content) {
/* 109 */     return createCharacters(content, this.location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Characters createCharacters(String paramString, Location paramLocation, QName paramQName);
/*     */ 
/*     */   
/*     */   public Comment createComment(String text) {
/* 118 */     return createComment(text, this.location);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Comment createComment(String paramString, Location paramLocation);
/*     */ 
/*     */   
/*     */   public DTD createDTD(String dtd) {
/* 126 */     return createDTD(dtd, this.location);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract DTD createDTD(String paramString, Location paramLocation);
/*     */ 
/*     */   
/*     */   public EndDocument createEndDocument() {
/* 134 */     return createEndDocument(this.location);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract EndDocument createEndDocument(Location paramLocation);
/*     */ 
/*     */   
/*     */   public EndElement createEndElement(QName name, Iterator namespaces) {
/* 142 */     return createEndElement(name, namespaces, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement createEndElement(String prefix, String namespaceUri, String localName, Iterator namespaces) {
/* 149 */     return createEndElement(new QName(namespaceUri, localName, prefix), namespaces, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndElement createEndElement(String prefix, String namespaceUri, String localName) {
/* 157 */     return createEndElement(new QName(namespaceUri, localName, prefix), (Iterator)null, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EndElement createEndElement(QName paramQName1, Iterator paramIterator, Location paramLocation, QName paramQName2);
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityReference createEntityReference(String name, EntityDeclaration declaration) {
/* 168 */     return createEntityReference(name, declaration, this.location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EntityReference createEntityReference(String paramString, EntityDeclaration paramEntityDeclaration, Location paramLocation);
/*     */ 
/*     */   
/*     */   public Characters createIgnorableSpace(String content) {
/* 177 */     return createIgnorableSpace(content, this.location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Characters createIgnorableSpace(String paramString, Location paramLocation);
/*     */ 
/*     */   
/*     */   public Namespace createNamespace(String prefix, String namespaceUri) {
/* 186 */     return createNamespace(prefix, namespaceUri, this.location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Namespace createNamespace(String namespaceUri) {
/* 192 */     return createNamespace("", namespaceUri, this.location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Namespace createNamespace(String paramString1, String paramString2, Location paramLocation);
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction createProcessingInstruction(String target, String data) {
/* 202 */     return createProcessingInstruction(target, data, this.location);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ProcessingInstruction createProcessingInstruction(String paramString1, String paramString2, Location paramLocation);
/*     */ 
/*     */   
/*     */   public Characters createSpace(String content) {
/* 211 */     return createSpace(content, this.location);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Characters createSpace(String paramString, Location paramLocation);
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument() {
/* 219 */     return createStartDocument(null, null, null, this.location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument(String encoding, String version, boolean standalone) {
/* 226 */     return createStartDocument(encoding, version, Boolean.valueOf(standalone), this.location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument(String encoding, String version) {
/* 233 */     return createStartDocument(encoding, version, null, this.location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StartDocument createStartDocument(String encoding) {
/* 239 */     return createStartDocument(encoding, null, null, this.location, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract StartDocument createStartDocument(String paramString1, String paramString2, Boolean paramBoolean, Location paramLocation, QName paramQName);
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement createStartElement(QName name, Iterator attributes, Iterator namespaces) {
/* 250 */     return createStartElement(name, attributes, namespaces, (NamespaceContext)null, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces, NamespaceContext context) {
/* 259 */     return createStartElement(new QName(namespaceUri, localName, prefix), attributes, namespaces, context, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement createStartElement(String prefix, String namespaceUri, String localName, Iterator attributes, Iterator namespaces) {
/* 267 */     return createStartElement(new QName(namespaceUri, localName, prefix), attributes, namespaces, (NamespaceContext)null, this.location, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartElement createStartElement(String prefix, String namespaceUri, String localName) {
/* 275 */     return createStartElement(new QName(namespaceUri, localName, prefix), (Iterator)null, (Iterator)null, (NamespaceContext)null, this.location, (QName)null);
/*     */   }
/*     */   
/*     */   public abstract StartElement createStartElement(QName paramQName1, Iterator paramIterator1, Iterator paramIterator2, NamespaceContext paramNamespaceContext, Location paramLocation, QName paramQName2);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\BaseXMLEventFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */