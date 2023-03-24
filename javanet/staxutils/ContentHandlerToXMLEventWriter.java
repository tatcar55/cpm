/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Stack;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContentHandlerToXMLEventWriter
/*     */   extends DefaultHandler
/*     */ {
/*     */   private final XMLEventWriter staxWriter;
/*     */   private final XMLEventFactory staxEventFactory;
/*  70 */   private Locator locator = null;
/*     */ 
/*     */   
/*  73 */   private Location location = null;
/*     */ 
/*     */   
/*     */   private final Stack prefixBindings;
/*     */ 
/*     */   
/*     */   private final HashMap entityMap;
/*     */ 
/*     */   
/*     */   public ContentHandlerToXMLEventWriter(XMLEventWriter staxCore) {
/*  83 */     this.staxWriter = staxCore;
/*  84 */     this.staxEventFactory = XMLEventFactory.newInstance();
/*     */     
/*  86 */     this.prefixBindings = new Stack();
/*     */     
/*  88 */     this.entityMap = new HashMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  98 */       this.staxWriter.add(this.staxEventFactory.createEndDocument());
/*  99 */       this.staxWriter.flush();
/* 100 */     } catch (XMLStreamException e) {
/* 101 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/* 112 */       this.staxWriter.add(this.staxEventFactory.createStartDocument());
/* 113 */     } catch (XMLStreamException e) {
/* 114 */       throw new SAXException(e);
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 128 */       Characters event = this.staxEventFactory.createCharacters(new String(ch, start, length));
/*     */       
/* 130 */       this.staxWriter.add(event);
/* 131 */     } catch (XMLStreamException e) {
/* 132 */       throw new SAXException(e);
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
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 145 */     characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
/* 162 */     this.entityMap.put(name, new EntityDeclarationImpl(this.location, name, publicId, systemId, notationName, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {
/*     */     try {
/* 174 */       this.staxWriter.add(this.staxEventFactory.createEntityReference(name, (EntityDeclarationImpl)this.entityMap.get(name)));
/*     */     }
/* 176 */     catch (XMLStreamException e) {
/* 177 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 187 */     this.locator = locator;
/* 188 */     this.staxEventFactory.setLocation(new Location(this, locator) {
/* 189 */           private final Locator val$locator; public int getLineNumber() { return this.val$locator.getLineNumber(); } private final ContentHandlerToXMLEventWriter this$0; public int getColumnNumber() {
/* 190 */             return this.val$locator.getColumnNumber(); }
/* 191 */           public int getCharacterOffset() { return -1; }
/* 192 */           public String getPublicId() { return this.val$locator.getPublicId(); } public String getSystemId() {
/* 193 */             return this.val$locator.getSystemId();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 207 */       this.staxWriter.add(this.staxEventFactory.createProcessingInstruction(target, data));
/*     */     }
/* 209 */     catch (XMLStreamException e) {
/* 210 */       throw new SAXException(e);
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
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 224 */     if (prefix.equals("xml")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 229 */     if (prefix == null) {
/* 230 */       prefix = "";
/*     */     }
/*     */     
/* 233 */     this.prefixBindings.add(prefix);
/* 234 */     this.prefixBindings.add(uri);
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
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 248 */       this.staxWriter.add(this.staxEventFactory.createEndElement(getPrefix(qName), namespaceURI, localName));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 254 */     catch (XMLStreamException e) {
/* 255 */       throw new SAXException(e);
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
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*     */     try {
/* 273 */       this.staxWriter.add(this.staxEventFactory.createStartElement(getPrefix(qName), namespaceURI, localName));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 280 */       while (this.prefixBindings.size() != 0) {
/* 281 */         String uri = this.prefixBindings.pop();
/* 282 */         String prefix = this.prefixBindings.pop();
/* 283 */         if (prefix.length() == 0) {
/* 284 */           this.staxWriter.setDefaultNamespace(uri);
/*     */         } else {
/* 286 */           this.staxWriter.setPrefix(prefix, uri);
/*     */         } 
/*     */ 
/*     */         
/* 290 */         if (prefix == null || "".equals(prefix) || "xmlns".equals(prefix)) {
/* 291 */           this.staxWriter.add(this.staxEventFactory.createNamespace(uri)); continue;
/*     */         } 
/* 293 */         this.staxWriter.add(this.staxEventFactory.createNamespace(prefix, uri));
/*     */       } 
/*     */ 
/*     */       
/* 297 */       writeAttributes(atts);
/* 298 */     } catch (XMLStreamException e) {
/* 299 */       throw new SAXException(e);
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
/*     */   private void writeAttributes(Attributes atts) throws XMLStreamException {
/* 311 */     for (int i = 0; i < atts.getLength(); i++) {
/* 312 */       String prefix = getPrefix(atts.getQName(i));
/* 313 */       if (!prefix.equals("xmlns")) {
/* 314 */         this.staxWriter.add(this.staxEventFactory.createAttribute(prefix, atts.getURI(i), atts.getLocalName(i), atts.getValue(i)));
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
/*     */   private String getPrefix(String qName) {
/* 332 */     int idx = qName.indexOf(':');
/* 333 */     if (idx == -1) {
/* 334 */       return "";
/*     */     }
/* 336 */     return qName.substring(0, idx);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\ContentHandlerToXMLEventWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */