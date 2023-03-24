/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.stream.util.XMLEventConsumer;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamUtils
/*     */ {
/*  65 */   private static XMLInputFactory inputFactory = XMLInputFactory.newInstance();
/*     */   
/*  67 */   private static XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
/*     */   
/*  69 */   private static final String[] EVENT_NAMES = new String[16];
/*     */   
/*     */   static {
/*  72 */     EVENT_NAMES[0] = "";
/*  73 */     EVENT_NAMES[10] = "ATTRIBUTE";
/*  74 */     EVENT_NAMES[12] = "CDATA";
/*  75 */     EVENT_NAMES[4] = "CHARACTERS";
/*  76 */     EVENT_NAMES[5] = "COMMENT";
/*  77 */     EVENT_NAMES[11] = "DTD";
/*  78 */     EVENT_NAMES[8] = "END_DOCUMENT";
/*  79 */     EVENT_NAMES[2] = "END_ELEMENT";
/*  80 */     EVENT_NAMES[15] = "ENTITY_DECLARATION";
/*  81 */     EVENT_NAMES[9] = "ENTITY_REFERENCE";
/*  82 */     EVENT_NAMES[13] = "NAMESPACE";
/*  83 */     EVENT_NAMES[14] = "NOTATION_DECLARATION";
/*  84 */     EVENT_NAMES[3] = "PROCESSING_INSTRUCTION";
/*  85 */     EVENT_NAMES[6] = "SPACE";
/*  86 */     EVENT_NAMES[7] = "START_DOCUMENT";
/*  87 */     EVENT_NAMES[1] = "START_ELEMENT";
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
/*     */   public static final String getEventTypeName(int eventType) {
/* 101 */     if (eventType > 0 || eventType < EVENT_NAMES.length)
/*     */     {
/* 103 */       return EVENT_NAMES[eventType];
/*     */     }
/*     */ 
/*     */     
/* 107 */     return "UNKNOWN";
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
/*     */   public static final String attributeValue(XMLStreamReader reader, String name) {
/* 124 */     return reader.getAttributeValue("", name);
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
/*     */   public static final String attributeValue(XMLStreamReader reader, QName name) {
/* 138 */     return reader.getAttributeValue(name.getNamespaceURI(), name.getLocalPart());
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
/*     */   public static final void skipElement(XMLEventReader reader) throws XMLStreamException {
/* 156 */     copyElement(reader, null);
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
/*     */   public static final void copyElement(XMLEventReader reader, XMLEventConsumer consumer) throws XMLStreamException {
/* 174 */     if (!reader.hasNext()) {
/*     */       return;
/*     */     }
/* 177 */     XMLEvent event = reader.peek();
/* 178 */     if (!event.isStartElement()) {
/*     */       return;
/*     */     }
/* 181 */     int depth = 0;
/*     */     
/*     */     do {
/* 184 */       XMLEvent currEvt = reader.nextEvent();
/* 185 */       if (currEvt.isStartElement()) {
/*     */         
/* 187 */         depth++;
/*     */       }
/* 189 */       else if (currEvt.isEndElement()) {
/*     */         
/* 191 */         depth--;
/*     */       } 
/*     */ 
/*     */       
/* 195 */       if (consumer == null)
/*     */         continue; 
/* 197 */       consumer.add(currEvt);
/*     */ 
/*     */     
/*     */     }
/* 201 */     while (depth > 0 && reader.hasNext());
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
/*     */   public static final void skipElementContent(XMLEventReader reader) throws XMLStreamException {
/* 219 */     copyElementContent(reader, null);
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
/*     */   public static final void copyElementContent(XMLEventReader reader, XMLEventConsumer consumer) throws XMLStreamException {
/* 239 */     if (!reader.hasNext()) {
/*     */       return;
/*     */     }
/* 242 */     int depth = 1;
/*     */     
/*     */     while (true) {
/* 245 */       XMLEvent currEvt = reader.peek();
/* 246 */       if (currEvt.isEndElement()) {
/*     */         
/* 248 */         depth--;
/* 249 */         if (depth == 0)
/*     */         {
/*     */           break;
/*     */         
/*     */         }
/*     */       }
/* 255 */       else if (currEvt.isStartElement()) {
/*     */         
/* 257 */         depth++;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 262 */       currEvt = reader.nextEvent();
/*     */       
/* 264 */       if (consumer != null)
/*     */       {
/* 266 */         consumer.add(currEvt);
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
/*     */   public static final void skipElement(XMLStreamReader reader) throws XMLStreamException {
/* 285 */     if (reader.isStartElement())
/*     */     {
/* 287 */       skipElementContent(reader);
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
/*     */   public static final void skipElementContent(XMLStreamReader reader) throws XMLStreamException {
/* 304 */     int depth = 0;
/* 305 */     while (depth >= 0) {
/*     */       
/* 307 */       reader.next();
/* 308 */       if (reader.isStartElement()) {
/*     */         
/* 310 */         depth++; continue;
/*     */       } 
/* 312 */       if (reader.isEndElement())
/*     */       {
/* 314 */         depth--;
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
/*     */   public static final void requireElement(XMLStreamReader reader, QName name) throws XMLStreamException {
/* 334 */     reader.require(1, name.getNamespaceURI(), name.getLocalPart());
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
/*     */   public static final void copy(XMLEventReader reader, XMLEventConsumer consumer) throws XMLStreamException {
/* 353 */     if (consumer instanceof XMLEventWriter) {
/*     */       
/* 355 */       copy(reader, (XMLEventWriter)consumer);
/*     */     }
/*     */     else {
/*     */       
/* 359 */       while (reader.hasNext())
/*     */       {
/* 361 */         consumer.add(reader.nextEvent());
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
/*     */   public static final void copy(XMLEventReader reader, XMLEventWriter writer) throws XMLStreamException {
/* 383 */     writer.add(reader);
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
/*     */   public static final void copy(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
/* 399 */     XMLEventReader r = inputFactory.createXMLEventReader(reader);
/* 400 */     XMLEventWriter w = new XMLStreamEventWriter(writer);
/*     */ 
/*     */     
/*     */     try {
/* 404 */       w.add(r);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 409 */       w.flush();
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
/*     */   public static final void copy(XMLStreamReader reader, XMLStreamWriter writer, XMLInputFactory factory) throws XMLStreamException {
/* 430 */     if (factory == null)
/*     */     {
/* 432 */       factory = inputFactory;
/*     */     }
/*     */ 
/*     */     
/* 436 */     XMLEventReader r = factory.createXMLEventReader(reader);
/* 437 */     XMLEventWriter w = new XMLStreamEventWriter(writer);
/*     */ 
/*     */     
/*     */     try {
/* 441 */       w.add(r);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 446 */       w.flush();
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
/*     */   public static final void copy(Source source, XMLStreamWriter writer) throws XMLStreamException {
/* 464 */     XMLStreamReader reader = inputFactory.createXMLStreamReader(source);
/* 465 */     copy(reader, writer);
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
/*     */   public static final void copy(Source source, XMLEventWriter writer) throws XMLStreamException {
/* 481 */     XMLEventReader reader = inputFactory.createXMLEventReader(source);
/* 482 */     copy(reader, writer);
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
/*     */   public static final void copy(XMLEventReader reader, Result result) throws XMLStreamException {
/* 498 */     XMLEventWriter writer = outputFactory.createXMLEventWriter(result);
/*     */     
/* 500 */     copy(reader, writer);
/*     */ 
/*     */     
/* 503 */     writer.flush();
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
/*     */   public static final void copy(XMLStreamReader reader, Result result) throws XMLStreamException {
/* 519 */     XMLStreamWriter writer = outputFactory.createXMLStreamWriter(result);
/*     */     
/* 521 */     copy(reader, writer);
/*     */ 
/*     */     
/* 524 */     writer.flush();
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
/*     */   public static final void requireStartElement(XMLEventReader reader, QName qname) throws XMLStreamException {
/* 540 */     if (reader.hasNext()) {
/*     */       
/* 542 */       XMLEvent nextEvent = reader.peek();
/* 543 */       if (nextEvent.isStartElement())
/*     */       {
/* 545 */         if (qname != null)
/*     */         {
/* 547 */           StartElement start = nextEvent.asStartElement();
/* 548 */           QName name = start.getName();
/* 549 */           if (!name.equals(qname))
/*     */           {
/* 551 */             throw new XMLStreamException("Encountered unexpected element; expected " + qname + ", but found " + name);
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 561 */         throw new XMLStreamException("Encountered unexpected event; expected " + qname + " start-tag, but found event " + nextEvent);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 569 */       throw new XMLStreamException("Encountered unexpected end of stream; expected element " + qname);
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
/*     */   public static StartElement mergeAttributes(StartElement tag, Iterator attrs, XMLEventFactory factory) {
/* 592 */     Map attributes = new HashMap();
/*     */ 
/*     */     
/* 595 */     for (Iterator i = tag.getAttributes(); i.hasNext(); ) {
/*     */       
/* 597 */       Attribute attr = i.next();
/* 598 */       attributes.put(attr.getName(), attr);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 603 */     while (attrs.hasNext()) {
/*     */       
/* 605 */       Attribute attr = attrs.next();
/* 606 */       attributes.put(attr.getName(), attr);
/*     */     } 
/*     */ 
/*     */     
/* 610 */     factory.setLocation(tag.getLocation());
/*     */     
/* 612 */     QName tagName = tag.getName();
/* 613 */     return factory.createStartElement(tagName.getPrefix(), tagName.getNamespaceURI(), tagName.getLocalPart(), attributes.values().iterator(), tag.getNamespaces(), tag.getNamespaceContext());
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
/*     */   public static final String readTextElement(XMLEventReader reader, QName elemName) throws XMLStreamException {
/* 636 */     if (elemName != null)
/*     */     {
/* 638 */       requireStartElement(reader, elemName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 643 */     String text = reader.getElementText();
/*     */ 
/*     */     
/* 646 */     reader.nextEvent();
/*     */     
/* 648 */     return text;
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
/*     */   public static final XMLEvent nextTag(XMLEventReader reader) throws XMLStreamException {
/* 665 */     while (reader.hasNext()) {
/*     */       
/* 667 */       XMLEvent nextEvent = reader.peek();
/* 668 */       if (nextEvent.isStartElement() || nextEvent.isEndElement())
/*     */       {
/* 670 */         return nextEvent;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 675 */       reader.nextEvent();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 681 */     return null;
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
/*     */   public static final StartElement nextElement(XMLEventReader reader) throws XMLStreamException {
/* 702 */     return nextElement(reader, null);
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
/*     */   public static final StartElement nextElement(XMLEventReader reader, QName name) throws XMLStreamException {
/* 726 */     while (reader.hasNext()) {
/*     */       
/* 728 */       XMLEvent nextEvent = reader.peek();
/* 729 */       if (nextEvent.isStartElement()) {
/*     */         
/* 731 */         StartElement start = nextEvent.asStartElement();
/* 732 */         if (name == null || start.getName().equals(name))
/*     */         {
/* 734 */           return start;
/*     */         }
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 742 */       if (nextEvent.isEndElement()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 749 */       reader.nextEvent();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 755 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLStreamUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */