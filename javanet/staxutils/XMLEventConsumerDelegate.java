/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.stream.util.XMLEventConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLEventConsumerDelegate
/*     */   implements XMLEventConsumer
/*     */ {
/*     */   private XMLEventConsumer consumer;
/*     */   private XMLEventFactory factory;
/*     */   
/*     */   public XMLEventConsumerDelegate(XMLEventConsumer consumer) {
/*  66 */     this.consumer = consumer;
/*  67 */     this.factory = XMLEventFactory.newInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventConsumerDelegate(XMLEventConsumer consumer, XMLEventFactory factory) {
/*  74 */     this.consumer = consumer;
/*  75 */     this.factory = (factory == null) ? XMLEventFactory.newInstance() : factory;
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
/*     */   
/*     */   public XMLEventConsumer getConsumer() {
/*  90 */     return this.consumer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConsumer(XMLEventConsumer consumer) {
/* 101 */     this.consumer = consumer;
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
/*     */   public XMLEventFactory getEventFactory() {
/* 113 */     return this.factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEventFactory(XMLEventFactory factory) {
/* 124 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLEvent event) throws XMLStreamException {
/* 130 */     this.consumer.add(event);
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
/*     */   public void addDTD(String dtd) throws XMLStreamException {
/* 143 */     add(this.factory.createDTD(dtd));
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
/*     */   public void addCData(String content) throws XMLStreamException {
/* 156 */     add(this.factory.createCData(content));
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
/*     */   public void addText(String content) throws XMLStreamException {
/* 169 */     add(this.factory.createCharacters(content));
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
/*     */   public void addIgnorableSpace(String content) throws XMLStreamException {
/* 182 */     add(this.factory.createIgnorableSpace(content));
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
/*     */   public void addSpace(String content) throws XMLStreamException {
/* 195 */     add(this.factory.createSpace(content));
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
/*     */   public void addComment(String comment) throws XMLStreamException {
/* 208 */     add(this.factory.createComment(comment));
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
/*     */   public void addStartDocument() throws XMLStreamException {
/* 220 */     add(this.factory.createStartDocument());
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
/*     */   public void addStartDocument(String encoding) throws XMLStreamException {
/* 233 */     add(this.factory.createStartDocument(encoding));
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
/*     */   
/*     */   public void addStartDocument(String encoding, String version) throws XMLStreamException {
/* 248 */     add(this.factory.createStartDocument(encoding, version));
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
/*     */ 
/*     */   
/*     */   public void addStartDocument(String encoding, String version, boolean standalone) throws XMLStreamException {
/* 264 */     add(this.factory.createStartDocument(encoding, version, standalone));
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
/*     */   public void addEndDocument() throws XMLStreamException {
/* 276 */     add(this.factory.createEndDocument());
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
/*     */   public void addStartElement(String localName, NamespaceContext context) throws XMLStreamException {
/* 290 */     addStartElement(localName, (Iterator)null, (Iterator)null, context);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStartElement(String localName, Iterator attributes, Iterator namespaces, NamespaceContext context) throws XMLStreamException {
/* 307 */     add(this.factory.createStartElement("", "", localName, attributes, namespaces, context));
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
/*     */ 
/*     */   
/*     */   public void addStartElement(String ns, String localName, NamespaceContext context) throws XMLStreamException {
/* 323 */     addStartElement(ns, localName, null, null, context);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStartElement(String ns, String localName, Iterator attributes, Iterator namespaces, NamespaceContext context) throws XMLStreamException {
/* 341 */     add(this.factory.createStartElement("", ns, localName, attributes, namespaces, context));
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
/*     */   
/*     */   public void addStartElement(QName name, NamespaceContext context) throws XMLStreamException {
/* 356 */     addStartElement(name, (Iterator)null, (Iterator)null, context);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStartElement(QName name, Iterator attributes, Iterator namespaces, NamespaceContext context) throws XMLStreamException {
/* 373 */     add(this.factory.createStartElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attributes, namespaces, context));
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
/*     */   public void addEndElement(String localName) throws XMLStreamException {
/* 387 */     addEndElement(localName, (Iterator)null);
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
/*     */   
/*     */   public void addEndElement(String localName, Iterator namespaces) throws XMLStreamException {
/* 402 */     add(this.factory.createEndElement(null, null, localName, namespaces));
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
/*     */   public void addEndElement(String ns, String localName) throws XMLStreamException {
/* 416 */     addEndElement(ns, localName, (Iterator)null);
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
/*     */ 
/*     */   
/*     */   public void addEndElement(String ns, String localName, Iterator namespaces) throws XMLStreamException {
/* 432 */     add(this.factory.createEndElement(null, ns, localName, namespaces));
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
/*     */   public void addEndElement(QName name) throws XMLStreamException {
/* 445 */     addEndElement(name, (Iterator)null);
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
/*     */ 
/*     */   
/*     */   public void addEndElement(QName name, Iterator namespaces) throws XMLStreamException {
/* 461 */     add(this.factory.createEndElement(name, namespaces));
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
/*     */   
/*     */   public void addTextElement(String name, String text, NamespaceContext context) throws XMLStreamException {
/* 476 */     addStartElement(name, context);
/* 477 */     if (text != null)
/*     */     {
/* 479 */       addText(text);
/*     */     }
/*     */     
/* 482 */     addEndElement(name);
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
/*     */   
/*     */   public void addTextElement(QName name, String text, NamespaceContext context) throws XMLStreamException {
/* 497 */     addStartElement(name, context);
/* 498 */     if (text != null)
/*     */     {
/* 500 */       addText(text);
/*     */     }
/*     */     
/* 503 */     addEndElement(name);
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
/*     */   
/*     */   public void addTextElement(String name, boolean text, NamespaceContext context) throws XMLStreamException {
/* 518 */     addTextElement(name, Boolean.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(QName name, boolean text, NamespaceContext context) throws XMLStreamException {
/* 533 */     addTextElement(name, Boolean.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(String name, int text, NamespaceContext context) throws XMLStreamException {
/* 548 */     addTextElement(name, Integer.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(QName name, int text, NamespaceContext context) throws XMLStreamException {
/* 563 */     addTextElement(name, Integer.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(String name, long text, NamespaceContext context) throws XMLStreamException {
/* 578 */     addTextElement(name, Long.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(QName name, long text, NamespaceContext context) throws XMLStreamException {
/* 593 */     addTextElement(name, Long.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(String name, float text, NamespaceContext context) throws XMLStreamException {
/* 608 */     addTextElement(name, Float.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(QName name, float text, NamespaceContext context) throws XMLStreamException {
/* 623 */     addTextElement(name, Float.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(String name, double text, NamespaceContext context) throws XMLStreamException {
/* 638 */     addTextElement(name, Double.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(QName name, double text, NamespaceContext context) throws XMLStreamException {
/* 653 */     addTextElement(name, Double.toString(text), context);
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
/*     */   
/*     */   public void addTextElement(String name, Number text, NamespaceContext context) throws XMLStreamException {
/* 668 */     if (text != null) {
/*     */       
/* 670 */       addTextElement(name, text.toString(), context);
/*     */     }
/*     */     else {
/*     */       
/* 674 */       addTextElement(name, (String)null, context);
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public void addTextElement(QName name, Number text, NamespaceContext context) throws XMLStreamException {
/* 691 */     if (text != null) {
/*     */       
/* 693 */       addTextElement(name, text.toString(), context);
/*     */     }
/*     */     else {
/*     */       
/* 697 */       addTextElement(name, (String)null, context);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLEventConsumerDelegate.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */