/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ 
/*     */ 
/*     */ public class StAXStreamContentHandler
/*     */   extends StAXContentHandler
/*     */ {
/*     */   private XMLStreamWriter writer;
/*     */   
/*     */   public StAXStreamContentHandler() {}
/*     */   
/*     */   public StAXStreamContentHandler(XMLStreamWriter writer) {
/*  67 */     this.writer = writer;
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
/*     */   public XMLStreamWriter getStreamWriter() {
/*  79 */     return this.writer;
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
/*     */   public void setStreamWriter(XMLStreamWriter writer) {
/*  91 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*  97 */     super.startDocument();
/*     */     
/*     */     try {
/* 100 */       this.writer.writeStartDocument();
/*     */     }
/* 102 */     catch (XMLStreamException e) {
/*     */       
/* 104 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/* 114 */       this.writer.writeEndDocument();
/*     */     }
/* 116 */     catch (XMLStreamException e) {
/*     */       
/* 118 */       throw new SAXException(e);
/*     */     } 
/*     */ 
/*     */     
/* 122 */     super.endDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/*     */     try {
/* 131 */       String[] qname = { null, null };
/* 132 */       parseQName(qName, qname);
/*     */       
/* 134 */       this.writer.writeStartElement(qname[0], qname[1], uri);
/*     */ 
/*     */       
/* 137 */       if (this.namespaces != null) {
/*     */         
/* 139 */         Iterator prefixes = this.namespaces.getDeclaredPrefixes();
/* 140 */         while (prefixes.hasNext()) {
/*     */           
/* 142 */           String prefix = prefixes.next();
/* 143 */           String nsURI = this.namespaces.getNamespaceURI(prefix);
/*     */           
/* 145 */           if (prefix.length() == 0) {
/*     */             
/* 147 */             this.writer.setDefaultNamespace(nsURI);
/*     */           }
/*     */           else {
/*     */             
/* 151 */             this.writer.setPrefix(prefix, nsURI);
/*     */           } 
/*     */ 
/*     */           
/* 155 */           this.writer.writeNamespace(prefix, nsURI);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       for (int i = 0, s = attributes.getLength(); i < s; i++)
/*     */       {
/* 164 */         parseQName(attributes.getQName(i), qname);
/*     */         
/* 166 */         String attrPrefix = qname[0];
/* 167 */         String attrLocal = qname[1];
/*     */         
/* 169 */         String attrQName = attributes.getQName(i);
/* 170 */         String attrValue = attributes.getValue(i);
/* 171 */         String attrURI = attributes.getURI(i);
/*     */         
/* 173 */         if ("xmlns".equals(attrQName) || "xmlns".equals(attrPrefix))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 179 */           String nsURI = this.namespaces.getNamespaceURI(attrPrefix);
/* 180 */           if (nsURI == null)
/*     */           {
/* 182 */             if (attrPrefix.length() == 0) {
/*     */               
/* 184 */               this.writer.setDefaultNamespace(attrValue);
/*     */             }
/*     */             else {
/*     */               
/* 188 */               this.writer.setPrefix(attrPrefix, attrValue);
/*     */             } 
/*     */ 
/*     */             
/* 192 */             this.writer.writeNamespace(attrPrefix, attrValue);
/*     */           }
/*     */         
/*     */         }
/* 196 */         else if (attrPrefix.length() > 0)
/*     */         {
/* 198 */           this.writer.writeAttribute(attrPrefix, attrURI, attrLocal, attrValue);
/*     */         
/*     */         }
/*     */         else
/*     */         {
/* 203 */           this.writer.writeAttribute(attrQName, attrValue);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 209 */     catch (XMLStreamException e) {
/*     */       
/* 211 */       throw new SAXException(e);
/*     */     }
/*     */     finally {
/*     */       
/* 215 */       super.startElement(uri, localName, qName, attributes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/*     */     try {
/* 226 */       this.writer.writeEndElement();
/*     */     }
/* 228 */     catch (XMLStreamException e) {
/*     */       
/* 230 */       throw new SAXException(e);
/*     */     }
/*     */     finally {
/*     */       
/* 234 */       super.endElement(uri, localName, qName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 242 */     super.comment(ch, start, length);
/*     */     
/*     */     try {
/* 245 */       this.writer.writeComment(new String(ch, start, length));
/*     */     }
/* 247 */     catch (XMLStreamException e) {
/*     */       
/* 249 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 258 */     super.characters(ch, start, length);
/*     */     
/*     */     try {
/* 261 */       if (!this.isCDATA)
/*     */       {
/* 263 */         this.writer.writeCharacters(ch, start, length);
/*     */       
/*     */       }
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
/*     */   
/*     */   public void endCDATA() throws SAXException {
/*     */     try {
/* 279 */       this.writer.writeCData(this.CDATABuffer.toString());
/*     */     }
/* 281 */     catch (XMLStreamException e) {
/*     */       
/* 283 */       throw new SAXException(e);
/*     */     } 
/*     */ 
/*     */     
/* 287 */     super.endCDATA();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 294 */     super.ignorableWhitespace(ch, start, length);
/*     */     
/*     */     try {
/* 297 */       this.writer.writeCharacters(ch, start, length);
/*     */     }
/* 299 */     catch (XMLStreamException e) {
/*     */       
/* 301 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 310 */     super.processingInstruction(target, data);
/*     */     
/*     */     try {
/* 313 */       this.writer.writeProcessingInstruction(target, data);
/*     */     }
/* 315 */     catch (XMLStreamException e) {
/*     */       
/* 317 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\StAXStreamContentHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */