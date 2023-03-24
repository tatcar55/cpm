/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.util.XMLEventConsumer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXEventContentHandler
/*     */   extends StAXContentHandler
/*     */ {
/*     */   private XMLEventConsumer consumer;
/*     */   private XMLEventFactory eventFactory;
/*  71 */   private List namespaceStack = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXEventContentHandler() {
/*  80 */     this.eventFactory = XMLEventFactory.newInstance();
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
/*     */   public StAXEventContentHandler(XMLEventConsumer consumer) {
/*  94 */     this.consumer = consumer;
/*  95 */     this.eventFactory = XMLEventFactory.newInstance();
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
/*     */   public StAXEventContentHandler(XMLEventConsumer consumer, XMLEventFactory factory) {
/* 111 */     this.consumer = consumer;
/* 112 */     if (factory != null) {
/*     */       
/* 114 */       this.eventFactory = factory;
/*     */     }
/*     */     else {
/*     */       
/* 118 */       this.eventFactory = XMLEventFactory.newInstance();
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
/*     */   public XMLEventConsumer getEventConsumer() {
/* 132 */     return this.consumer;
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
/*     */   public void setEventConsumer(XMLEventConsumer consumer) {
/* 144 */     this.consumer = consumer;
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
/* 156 */     return this.eventFactory;
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
/* 167 */     this.eventFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 173 */     super.startDocument();
/*     */ 
/*     */     
/* 176 */     this.namespaceStack.clear();
/*     */     
/* 178 */     this.eventFactory.setLocation(getCurrentLocation());
/*     */     
/*     */     try {
/* 181 */       this.consumer.add(this.eventFactory.createStartDocument());
/*     */     }
/* 183 */     catch (XMLStreamException e) {
/*     */       
/* 185 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 193 */     this.eventFactory.setLocation(getCurrentLocation());
/*     */ 
/*     */     
/*     */     try {
/* 197 */       this.consumer.add(this.eventFactory.createEndDocument());
/*     */     }
/* 199 */     catch (XMLStreamException e) {
/*     */       
/* 201 */       throw new SAXException(e);
/*     */     } 
/*     */ 
/*     */     
/* 205 */     super.endDocument();
/*     */ 
/*     */     
/* 208 */     this.namespaceStack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 216 */     this.eventFactory.setLocation(getCurrentLocation());
/*     */ 
/*     */     
/* 219 */     Collection[] events = { null, null };
/* 220 */     createStartEvents(attributes, events);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     this.namespaceStack.add(events[0]);
/*     */ 
/*     */     
/*     */     try {
/* 229 */       String[] qname = { null, null };
/* 230 */       parseQName(qName, qname);
/*     */       
/* 232 */       this.consumer.add(this.eventFactory.createStartElement(qname[0], uri, qname[1], events[1].iterator(), events[0].iterator()));
/*     */     
/*     */     }
/* 235 */     catch (XMLStreamException e) {
/*     */       
/* 237 */       throw new SAXException(e);
/*     */     }
/*     */     finally {
/*     */       
/* 241 */       super.startElement(uri, localName, qName, attributes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 250 */     super.endElement(uri, localName, qName);
/*     */     
/* 252 */     this.eventFactory.setLocation(getCurrentLocation());
/*     */ 
/*     */     
/* 255 */     String[] qname = { null, null };
/* 256 */     parseQName(qName, qname);
/*     */ 
/*     */     
/* 259 */     Collection nsList = this.namespaceStack.remove(this.namespaceStack.size() - 1);
/* 260 */     Iterator nsIter = nsList.iterator();
/*     */ 
/*     */     
/*     */     try {
/* 264 */       this.consumer.add(this.eventFactory.createEndElement(qname[0], uri, qname[1], nsIter));
/*     */     
/*     */     }
/* 267 */     catch (XMLStreamException e) {
/*     */       
/* 269 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 277 */     super.comment(ch, start, length);
/*     */     
/* 279 */     this.eventFactory.setLocation(getCurrentLocation());
/*     */     
/*     */     try {
/* 282 */       this.consumer.add(this.eventFactory.createComment(new String(ch, start, length)));
/*     */     
/*     */     }
/* 285 */     catch (XMLStreamException e) {
/*     */       
/* 287 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 296 */     super.characters(ch, start, length);
/*     */ 
/*     */     
/*     */     try {
/* 300 */       if (!this.isCDATA)
/*     */       {
/* 302 */         this.eventFactory.setLocation(getCurrentLocation());
/* 303 */         this.consumer.add(this.eventFactory.createCharacters(new String(ch, start, length)));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 308 */     catch (XMLStreamException e) {
/*     */       
/* 310 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 319 */     super.ignorableWhitespace(ch, start, length);
/* 320 */     characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 327 */     super.processingInstruction(target, data);
/*     */     
/*     */     try {
/* 330 */       this.consumer.add(this.eventFactory.createProcessingInstruction(target, data));
/*     */     }
/* 332 */     catch (XMLStreamException e) {
/*     */       
/* 334 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 342 */     this.eventFactory.setLocation(getCurrentLocation());
/*     */     
/*     */     try {
/* 345 */       this.consumer.add(this.eventFactory.createCData(this.CDATABuffer.toString()));
/*     */     }
/* 347 */     catch (XMLStreamException e) {
/*     */       
/* 349 */       throw new SAXException(e);
/*     */     } 
/*     */ 
/*     */     
/* 353 */     super.endCDATA();
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
/*     */   protected void createStartEvents(Attributes attributes, Collection[] events) {
/* 369 */     Map nsMap = null;
/* 370 */     List attrs = null;
/*     */ 
/*     */     
/* 373 */     if (this.namespaces != null) {
/*     */       
/* 375 */       Iterator prefixes = this.namespaces.getDeclaredPrefixes();
/* 376 */       while (prefixes.hasNext()) {
/*     */         
/* 378 */         String prefix = prefixes.next();
/* 379 */         String uri = this.namespaces.getNamespaceURI(prefix);
/*     */         
/* 381 */         Namespace ns = createNamespace(prefix, uri);
/* 382 */         if (nsMap == null)
/*     */         {
/* 384 */           nsMap = new HashMap();
/*     */         }
/*     */         
/* 387 */         nsMap.put(prefix, ns);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 394 */     String[] qname = { null, null };
/* 395 */     for (int i = 0, s = attributes.getLength(); i < s; i++) {
/*     */       
/* 397 */       parseQName(attributes.getQName(i), qname);
/*     */       
/* 399 */       String attrPrefix = qname[0];
/* 400 */       String attrLocal = qname[1];
/*     */       
/* 402 */       String attrQName = attributes.getQName(i);
/* 403 */       String attrValue = attributes.getValue(i);
/* 404 */       String attrURI = attributes.getURI(i);
/*     */       
/* 406 */       if ("xmlns".equals(attrQName) || "xmlns".equals(attrPrefix)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 412 */         if (!nsMap.containsKey(attrPrefix)) {
/*     */           
/* 414 */           Namespace ns = createNamespace(attrPrefix, attrValue);
/* 415 */           if (nsMap == null)
/*     */           {
/* 417 */             nsMap = new HashMap();
/*     */           }
/*     */           
/* 420 */           nsMap.put(attrPrefix, ns);
/*     */         } 
/*     */       } else {
/*     */         Attribute attribute;
/*     */ 
/*     */ 
/*     */         
/* 427 */         if (attrPrefix.length() > 0) {
/*     */           
/* 429 */           attribute = this.eventFactory.createAttribute(attrPrefix, attrURI, attrLocal, attrValue);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 434 */           attribute = this.eventFactory.createAttribute(attrLocal, attrValue);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 439 */         if (attrs == null)
/*     */         {
/* 441 */           attrs = new ArrayList();
/*     */         }
/*     */         
/* 444 */         attrs.add(attribute);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 450 */     events[0] = (nsMap == null) ? Collections.EMPTY_LIST : nsMap.values();
/* 451 */     events[1] = (attrs == null) ? Collections.EMPTY_LIST : attrs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Namespace createNamespace(String prefix, String uri) {
/* 457 */     if (prefix == null || prefix.length() == 0)
/*     */     {
/* 459 */       return this.eventFactory.createNamespace(uri);
/*     */     }
/*     */ 
/*     */     
/* 463 */     return this.eventFactory.createNamespace(prefix, uri);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\StAXEventContentHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */