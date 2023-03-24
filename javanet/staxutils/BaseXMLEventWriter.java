/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
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
/*     */ public abstract class BaseXMLEventWriter
/*     */   implements XMLEventWriter
/*     */ {
/*     */   protected XMLEventFactory factory;
/*  69 */   protected List nsStack = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StartElement lastStart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected Map attrBuff = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   protected Map nsBuff = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closed;
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseXMLEventWriter() {
/*  99 */     this(null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseXMLEventWriter(XMLEventFactory eventFactory, NamespaceContext nsCtx) {
/* 106 */     if (nsCtx != null) {
/*     */       
/* 108 */       this.nsStack.add(new SimpleNamespaceContext(nsCtx));
/*     */     }
/*     */     else {
/*     */       
/* 112 */       this.nsStack.add(new SimpleNamespaceContext());
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (eventFactory != null) {
/*     */       
/* 118 */       this.factory = eventFactory;
/*     */     }
/*     */     else {
/*     */       
/* 122 */       this.factory = XMLEventFactory.newInstance();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() throws XMLStreamException {
/* 130 */     if (!this.closed)
/*     */     {
/* 132 */       sendCachedEvents();
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
/*     */   private void sendCachedEvents() throws XMLStreamException {
/* 145 */     if (this.lastStart != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       SimpleNamespaceContext nsCtx = pushNamespaceStack();
/*     */ 
/*     */       
/* 155 */       List namespaces = new ArrayList();
/*     */ 
/*     */       
/* 158 */       mergeNamespaces(this.lastStart.getNamespaces(), namespaces);
/* 159 */       mergeNamespaces(this.nsBuff.values().iterator(), namespaces);
/* 160 */       this.nsBuff.clear();
/*     */ 
/*     */       
/* 163 */       List attributes = new ArrayList();
/* 164 */       mergeAttributes(this.lastStart.getAttributes(), namespaces, attributes);
/* 165 */       mergeAttributes(this.attrBuff.values().iterator(), namespaces, attributes);
/*     */       
/* 167 */       this.attrBuff.clear();
/*     */ 
/*     */       
/* 170 */       QName tagName = this.lastStart.getName();
/* 171 */       QName newName = processQName(tagName, namespaces);
/*     */ 
/*     */       
/* 174 */       StartElement newStart = this.factory.createStartElement(newName.getPrefix(), newName.getNamespaceURI(), newName.getLocalPart(), attributes.iterator(), namespaces.iterator(), nsCtx);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       this.lastStart = null;
/* 180 */       sendEvent(newStart);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 187 */       for (Iterator iterator1 = this.nsBuff.values().iterator(); iterator1.hasNext(); ) {
/*     */         
/* 189 */         XMLEvent evt = iterator1.next();
/* 190 */         sendEvent(evt);
/*     */       } 
/*     */       
/* 193 */       this.nsBuff.clear();
/*     */       
/* 195 */       for (Iterator i = this.attrBuff.values().iterator(); i.hasNext(); ) {
/*     */         
/* 197 */         XMLEvent evt = i.next();
/* 198 */         sendEvent(evt);
/*     */       } 
/*     */       
/* 201 */       this.attrBuff.clear();
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
/*     */ 
/*     */   
/*     */   private void mergeAttributes(Iterator iter, List namespaces, List attributes) {
/* 220 */     while (iter.hasNext()) {
/*     */       
/* 222 */       Attribute attr = iter.next();
/*     */ 
/*     */       
/* 225 */       QName attrName = attr.getName();
/* 226 */       QName newName = processQName(attrName, namespaces);
/* 227 */       if (!attrName.equals(newName)) {
/*     */ 
/*     */         
/* 230 */         Attribute newAttr = this.factory.createAttribute(newName, attr.getValue());
/*     */         
/* 232 */         attributes.add(newAttr);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 237 */       attributes.add(attr);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeNamespaces(Iterator iter, List namespaces) throws XMLStreamException {
/* 258 */     while (iter.hasNext()) {
/*     */       
/* 260 */       Namespace ns = iter.next();
/* 261 */       String prefix = ns.getPrefix();
/* 262 */       String nsURI = ns.getNamespaceURI();
/* 263 */       SimpleNamespaceContext nsCtx = peekNamespaceStack();
/*     */       
/* 265 */       if (!nsCtx.isPrefixDeclared(prefix)) {
/*     */ 
/*     */         
/* 268 */         if (prefix == null || prefix.length() == 0) {
/*     */           
/* 270 */           nsCtx.setDefaultNamespace(nsURI);
/*     */         }
/*     */         else {
/*     */           
/* 274 */           nsCtx.setPrefix(prefix, nsURI);
/*     */         } 
/*     */ 
/*     */         
/* 278 */         namespaces.add(ns); continue;
/*     */       } 
/* 280 */       if (!nsCtx.getNamespaceURI(prefix).equals(nsURI))
/*     */       {
/* 282 */         throw new XMLStreamException("Prefix already declared: " + ns, ns.getLocation());
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QName processQName(QName name, List namespaces) {
/* 308 */     SimpleNamespaceContext nsCtx = peekNamespaceStack();
/*     */ 
/*     */     
/* 311 */     String nsURI = name.getNamespaceURI();
/* 312 */     String prefix = name.getPrefix();
/* 313 */     if (prefix != null && prefix.length() > 0) {
/*     */ 
/*     */ 
/*     */       
/* 317 */       String resolvedNS = nsCtx.getNamespaceURI(prefix);
/* 318 */       if (resolvedNS != null) {
/*     */         
/* 320 */         if (!resolvedNS.equals(nsURI))
/*     */         {
/*     */ 
/*     */           
/* 324 */           String newPrefix = nsCtx.getPrefix(nsURI);
/* 325 */           if (newPrefix == null)
/*     */           {
/*     */             
/* 328 */             newPrefix = generatePrefix(nsURI, nsCtx, namespaces);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 333 */           return new QName(nsURI, name.getLocalPart(), newPrefix);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 341 */       else if (nsURI != null && nsURI.length() > 0) {
/*     */ 
/*     */         
/* 344 */         nsCtx.setPrefix(prefix, nsURI);
/* 345 */         namespaces.add(this.factory.createNamespace(prefix, nsURI));
/*     */       } 
/*     */ 
/*     */       
/* 349 */       return name;
/*     */     } 
/* 351 */     if (nsURI != null && nsURI.length() > 0) {
/*     */ 
/*     */ 
/*     */       
/* 355 */       String newPrefix = nsCtx.getPrefix(nsURI);
/* 356 */       if (newPrefix == null)
/*     */       {
/*     */         
/* 359 */         newPrefix = generatePrefix(nsURI, nsCtx, namespaces);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 364 */       return new QName(nsURI, name.getLocalPart(), newPrefix);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 369 */     return name;
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
/*     */   
/*     */   private String generatePrefix(String nsURI, SimpleNamespaceContext nsCtx, List namespaces) {
/*     */     String newPrefix;
/* 389 */     int nsCount = 0;
/*     */     
/*     */     do {
/* 392 */       newPrefix = "ns" + nsCount;
/* 393 */       nsCount++;
/*     */     }
/* 395 */     while (nsCtx.getNamespaceURI(newPrefix) != null);
/*     */     
/* 397 */     nsCtx.setPrefix(newPrefix, nsURI);
/* 398 */     namespaces.add(this.factory.createNamespace(newPrefix, nsURI));
/* 399 */     return newPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws XMLStreamException {
/* 405 */     if (this.closed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 413 */       flush();
/*     */     }
/*     */     finally {
/*     */       
/* 417 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void add(XMLEvent event) throws XMLStreamException {
/* 425 */     if (this.closed)
/*     */     {
/* 427 */       throw new XMLStreamException("Writer has been closed");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     switch (event.getEventType()) {
/*     */       
/*     */       case 13:
/* 436 */         cacheNamespace((Namespace)event);
/*     */         return;
/*     */       
/*     */       case 10:
/* 440 */         cacheAttribute((Attribute)event);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 445 */     sendCachedEvents();
/*     */ 
/*     */ 
/*     */     
/* 449 */     if (event.isStartElement()) {
/*     */ 
/*     */ 
/*     */       
/* 453 */       this.lastStart = event.asStartElement();
/*     */       return;
/*     */     } 
/* 456 */     if (event.isEndElement()) {
/*     */       
/* 458 */       if (this.nsStack.isEmpty())
/*     */       {
/* 460 */         throw new XMLStreamException("Mismatched end element event: " + event);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 465 */       SimpleNamespaceContext nsCtx = peekNamespaceStack();
/* 466 */       EndElement endTag = event.asEndElement();
/* 467 */       QName endElemName = endTag.getName();
/*     */       
/* 469 */       String prefix = endElemName.getPrefix();
/* 470 */       String nsURI = endElemName.getNamespaceURI();
/* 471 */       if (nsURI != null && nsURI.length() > 0) {
/*     */ 
/*     */ 
/*     */         
/* 475 */         String boundURI = nsCtx.getNamespaceURI(prefix);
/* 476 */         if (!nsURI.equals(boundURI))
/*     */         {
/*     */ 
/*     */           
/* 480 */           String newPrefix = nsCtx.getPrefix(nsURI);
/* 481 */           if (newPrefix != null)
/*     */           {
/* 483 */             QName newName = new QName(nsURI, endElemName.getLocalPart(), newPrefix);
/*     */             
/* 485 */             event = this.factory.createEndElement(newName, endTag.getNamespaces());
/*     */           
/*     */           }
/*     */           else
/*     */           {
/*     */             
/* 491 */             throw new XMLStreamException("EndElement namespace (" + nsURI + ") isn't bound [" + endTag + "]");
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 501 */         String defaultURI = nsCtx.getNamespaceURI("");
/* 502 */         if (defaultURI != null && defaultURI.length() > 0)
/*     */         {
/* 504 */           throw new XMLStreamException("Unable to write " + event + " because default namespace is occluded by " + defaultURI);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 515 */       popNamespaceStack();
/*     */     } 
/*     */ 
/*     */     
/* 519 */     sendEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XMLEventReader reader) throws XMLStreamException {
/* 525 */     while (reader.hasNext())
/*     */     {
/* 527 */       add(reader.nextEvent());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getPrefix(String nsURI) throws XMLStreamException {
/* 536 */     return getNamespaceContext().getPrefix(nsURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setPrefix(String prefix, String nsURI) throws XMLStreamException {
/* 543 */     peekNamespaceStack().setPrefix(prefix, nsURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setDefaultNamespace(String nsURI) throws XMLStreamException {
/* 550 */     peekNamespaceStack().setDefaultNamespace(nsURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setNamespaceContext(NamespaceContext root) throws XMLStreamException {
/* 557 */     SimpleNamespaceContext parent = this.nsStack.get(0);
/* 558 */     parent.setParent(root);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized NamespaceContext getNamespaceContext() {
/* 564 */     return peekNamespaceStack();
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
/*     */   protected SimpleNamespaceContext popNamespaceStack() {
/* 577 */     return this.nsStack.remove(this.nsStack.size() - 1);
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
/*     */   protected SimpleNamespaceContext peekNamespaceStack() {
/* 590 */     return this.nsStack.get(this.nsStack.size() - 1);
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
/*     */   protected SimpleNamespaceContext pushNamespaceStack() {
/* 604 */     SimpleNamespaceContext nsCtx, parent = peekNamespaceStack();
/* 605 */     if (parent != null) {
/*     */       
/* 607 */       nsCtx = new SimpleNamespaceContext(parent);
/*     */     }
/*     */     else {
/*     */       
/* 611 */       nsCtx = new SimpleNamespaceContext();
/*     */     } 
/*     */ 
/*     */     
/* 615 */     this.nsStack.add(nsCtx);
/* 616 */     return nsCtx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cacheAttribute(Attribute attr) {
/* 627 */     this.attrBuff.put(attr.getName(), attr);
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
/*     */   protected void cacheNamespace(Namespace ns) {
/* 639 */     this.nsBuff.put(ns.getPrefix(), ns);
/*     */   }
/*     */   
/*     */   protected abstract void sendEvent(XMLEvent paramXMLEvent) throws XMLStreamException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\BaseXMLEventWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */