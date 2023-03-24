/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javanet.staxutils.helpers.XMLFilterImplEx;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.Comment;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.ProcessingInstruction;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
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
/*     */ public class XMLEventReaderToContentHandler
/*     */   implements StAXReaderToContentHandler
/*     */ {
/*     */   private final XMLEventReader staxEventReader;
/*     */   private XMLFilterImplEx filter;
/*     */   
/*     */   public XMLEventReaderToContentHandler(XMLEventReader staxCore, XMLFilterImplEx filter) {
/*  86 */     this.staxEventReader = staxCore;
/*     */     
/*  88 */     this.filter = filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bridge() throws XMLStreamException {
/*     */     try {
/*  98 */       int depth = 0;
/*     */       
/* 100 */       XMLEvent event = this.staxEventReader.peek();
/*     */       
/* 102 */       boolean readWhole = false;
/*     */       
/* 104 */       if (event.isStartDocument()) {
/* 105 */         readWhole = true;
/*     */       }
/* 107 */       else if (!event.isStartElement()) {
/* 108 */         throw new IllegalStateException();
/*     */       } 
/*     */       
/*     */       do {
/* 112 */         event = this.staxEventReader.nextEvent();
/* 113 */       } while (!event.isStartElement());
/*     */       
/* 115 */       handleStartDocument(event);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 122 */         switch (event.getEventType()) {
/*     */           case 1:
/* 124 */             depth++;
/* 125 */             handleStartElement(event.asStartElement());
/*     */             break;
/*     */           case 2:
/* 128 */             handleEndElement(event.asEndElement());
/* 129 */             depth--;
/* 130 */             if (depth == 0)
/*     */               break; 
/*     */             break;
/*     */           case 4:
/* 134 */             handleCharacters(event.asCharacters());
/*     */             break;
/*     */           case 9:
/* 137 */             handleEntityReference();
/*     */             break;
/*     */           case 3:
/* 140 */             handlePI((ProcessingInstruction)event);
/*     */             break;
/*     */           case 5:
/* 143 */             handleComment((Comment)event);
/*     */             break;
/*     */           case 11:
/* 146 */             handleDTD();
/*     */             break;
/*     */           case 10:
/* 149 */             handleAttribute();
/*     */             break;
/*     */           case 13:
/* 152 */             handleNamespace();
/*     */             break;
/*     */           case 12:
/* 155 */             handleCDATA();
/*     */             break;
/*     */           case 15:
/* 158 */             handleEntityDecl();
/*     */             break;
/*     */           case 14:
/* 161 */             handleNotationDecl();
/*     */             break;
/*     */           case 6:
/* 164 */             handleSpace();
/*     */             break;
/*     */           default:
/* 167 */             throw new InternalError("processing event: " + event);
/*     */         } 
/*     */         
/* 170 */         event = this.staxEventReader.nextEvent();
/*     */       } 
/*     */       
/* 173 */       handleEndDocument();
/*     */       
/* 175 */       if (readWhole)
/*     */       {
/* 177 */         while (this.staxEventReader.hasNext())
/* 178 */           this.staxEventReader.nextEvent(); 
/*     */       }
/* 180 */     } catch (SAXException e) {
/* 181 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndDocument() throws SAXException {
/* 186 */     this.filter.endDocument();
/*     */   }
/*     */   
/*     */   private void handleStartDocument(XMLEvent event) throws SAXException {
/* 190 */     Location location = event.getLocation();
/* 191 */     if (location != null) {
/* 192 */       this.filter.setDocumentLocator(new Locator(this, location) { private final Location val$location;
/*     */             public int getColumnNumber() {
/* 194 */               return this.val$location.getColumnNumber();
/*     */             } private final XMLEventReaderToContentHandler this$0;
/*     */             public int getLineNumber() {
/* 197 */               return this.val$location.getLineNumber();
/*     */             }
/*     */             public String getPublicId() {
/* 200 */               return this.val$location.getPublicId();
/*     */             }
/*     */             public String getSystemId() {
/* 203 */               return this.val$location.getSystemId();
/*     */             } }
/*     */         );
/*     */     } else {
/* 207 */       this.filter.setDocumentLocator(new DummyLocator());
/*     */     } 
/* 209 */     this.filter.startDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   private void handlePI(ProcessingInstruction event) throws XMLStreamException {
/*     */     try {
/* 215 */       this.filter.processingInstruction(event.getTarget(), event.getData());
/*     */     
/*     */     }
/* 218 */     catch (SAXException e) {
/* 219 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleCharacters(Characters event) throws XMLStreamException {
/*     */     try {
/* 225 */       this.filter.characters(event.getData().toCharArray(), 0, event.getData().length());
/*     */ 
/*     */     
/*     */     }
/* 229 */     catch (SAXException e) {
/* 230 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndElement(EndElement event) throws XMLStreamException {
/* 235 */     QName qName = event.getName();
/*     */ 
/*     */     
/*     */     try {
/* 239 */       String rawname, prefix = qName.getPrefix();
/*     */       
/* 241 */       if (prefix == null || prefix.length() == 0) {
/* 242 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 244 */         rawname = prefix + ':' + qName.getLocalPart();
/*     */       } 
/* 246 */       this.filter.endElement(qName.getNamespaceURI(), qName.getLocalPart(), rawname);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 252 */       for (Iterator i = event.getNamespaces(); i.hasNext(); ) {
/* 253 */         String nsprefix = ((Namespace)i.next()).getPrefix();
/* 254 */         if (nsprefix == null) {
/* 255 */           nsprefix = "";
/*     */         }
/* 257 */         this.filter.endPrefixMapping(nsprefix);
/*     */       } 
/* 259 */     } catch (SAXException e) {
/* 260 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement(StartElement event) throws XMLStreamException {
/*     */     try {
/*     */       String rawname;
/* 268 */       for (Iterator i = event.getNamespaces(); i.hasNext(); ) {
/* 269 */         String str = ((Namespace)i.next()).getPrefix();
/* 270 */         if (str == null) {
/* 271 */           str = "";
/*     */         }
/* 273 */         this.filter.startPrefixMapping(str, event.getNamespaceURI(str));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 279 */       QName qName = event.getName();
/* 280 */       String prefix = qName.getPrefix();
/*     */       
/* 282 */       if (prefix == null || prefix.length() == 0) {
/* 283 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 285 */         rawname = prefix + ':' + qName.getLocalPart();
/* 286 */       }  Attributes saxAttrs = getAttributes(event);
/* 287 */       this.filter.startElement(qName.getNamespaceURI(), qName.getLocalPart(), rawname, saxAttrs);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 292 */     catch (SAXException e) {
/* 293 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes(StartElement event) {
/* 303 */     AttributesImpl attrs = new AttributesImpl();
/*     */     
/* 305 */     if (!event.isStartElement()) {
/* 306 */       throw new InternalError("getAttributes() attempting to process: " + event);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 311 */     if (this.filter.getNamespacePrefixes()) {
/* 312 */       for (Iterator iterator = event.getNamespaces(); iterator.hasNext(); ) {
/* 313 */         Namespace staxNamespace = iterator.next();
/* 314 */         String uri = staxNamespace.getNamespaceURI();
/* 315 */         if (uri == null) uri = "";
/*     */         
/* 317 */         String prefix = staxNamespace.getPrefix();
/* 318 */         if (prefix == null) prefix = "";
/*     */         
/* 320 */         String qName = "xmlns";
/* 321 */         if (prefix.length() == 0) {
/* 322 */           prefix = qName;
/*     */         } else {
/* 324 */           qName = qName + ':' + prefix;
/*     */         } 
/* 326 */         attrs.addAttribute("http://www.w3.org/2000/xmlns/", prefix, qName, "CDATA", uri);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 331 */     for (Iterator i = event.getAttributes(); i.hasNext(); ) {
/* 332 */       String qName; Attribute staxAttr = i.next();
/*     */       
/* 334 */       String uri = staxAttr.getName().getNamespaceURI();
/* 335 */       if (uri == null)
/* 336 */         uri = ""; 
/* 337 */       String localName = staxAttr.getName().getLocalPart();
/* 338 */       String prefix = staxAttr.getName().getPrefix();
/*     */       
/* 340 */       if (prefix == null || prefix.length() == 0) {
/* 341 */         qName = localName;
/*     */       } else {
/* 343 */         qName = prefix + ':' + localName;
/* 344 */       }  String type = staxAttr.getDTDType();
/* 345 */       String value = staxAttr.getValue();
/*     */       
/* 347 */       attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 350 */     return attrs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleNamespace() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleAttribute() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleDTD() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleComment(Comment comment) throws XMLStreamException {
/*     */     try {
/* 372 */       String text = comment.getText();
/* 373 */       this.filter.comment(text.toCharArray(), 0, text.length());
/*     */ 
/*     */     
/*     */     }
/* 377 */     catch (SAXException e) {
/* 378 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEntityReference() {}
/*     */   
/*     */   private void handleSpace() {}
/*     */   
/*     */   private void handleNotationDecl() {}
/*     */   
/*     */   private void handleEntityDecl() {}
/*     */   
/*     */   private void handleCDATA() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLEventReaderToContentHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */