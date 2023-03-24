/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class ContentHandlerToXMLStreamWriter
/*     */   extends DefaultHandler
/*     */ {
/*     */   private final XMLStreamWriter staxWriter;
/*     */   private final Stack prefixBindings;
/*     */   
/*     */   public ContentHandlerToXMLStreamWriter(XMLStreamWriter staxCore) {
/*  65 */     this.staxWriter = staxCore;
/*  66 */     this.prefixBindings = new Stack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  76 */       this.staxWriter.writeEndDocument();
/*  77 */       this.staxWriter.flush();
/*  78 */     } catch (XMLStreamException e) {
/*  79 */       throw new SAXException(e);
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
/*  90 */       this.staxWriter.writeStartDocument();
/*  91 */     } catch (XMLStreamException e) {
/*  92 */       throw new SAXException(e);
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 105 */       this.staxWriter.writeCharacters(ch, start, length);
/* 106 */     } catch (XMLStreamException e) {
/* 107 */       throw new SAXException(e);
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
/* 120 */     characters(ch, start, length);
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
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
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
/* 142 */       this.staxWriter.writeEntityRef(name);
/* 143 */     } catch (XMLStreamException e) {
/* 144 */       throw new SAXException(e);
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
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */ 
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
/* 170 */       this.staxWriter.writeProcessingInstruction(target, data);
/* 171 */     } catch (XMLStreamException e) {
/* 172 */       throw new SAXException(e);
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
/* 186 */     if (prefix.equals("xml")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (prefix == null) {
/* 192 */       prefix = "";
/*     */     }
/*     */     
/* 195 */     this.prefixBindings.add(prefix);
/* 196 */     this.prefixBindings.add(uri);
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
/* 210 */       this.staxWriter.writeEndElement();
/* 211 */     } catch (XMLStreamException e) {
/* 212 */       throw new SAXException(e);
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
/* 230 */       this.staxWriter.writeStartElement(getPrefix(qName), localName, namespaceURI);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       while (this.prefixBindings.size() != 0) {
/* 237 */         String uri = this.prefixBindings.pop();
/* 238 */         String prefix = this.prefixBindings.pop();
/* 239 */         if (prefix.length() == 0) {
/* 240 */           this.staxWriter.setDefaultNamespace(uri);
/*     */         } else {
/* 242 */           this.staxWriter.setPrefix(prefix, uri);
/*     */         } 
/*     */ 
/*     */         
/* 246 */         this.staxWriter.writeNamespace(prefix, uri);
/*     */       } 
/*     */       
/* 249 */       writeAttributes(atts);
/* 250 */     } catch (XMLStreamException e) {
/* 251 */       throw new SAXException(e);
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
/* 263 */     for (int i = 0; i < atts.getLength(); i++) {
/* 264 */       String prefix = getPrefix(atts.getQName(i));
/* 265 */       if (!prefix.equals("xmlns")) {
/* 266 */         this.staxWriter.writeAttribute(prefix, atts.getURI(i), atts.getLocalName(i), atts.getValue(i));
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
/*     */   private String getPrefix(String qName) {
/* 283 */     int idx = qName.indexOf(':');
/* 284 */     if (idx == -1) {
/* 285 */       return "";
/*     */     }
/* 287 */     return qName.substring(0, idx);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\ContentHandlerToXMLStreamWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */