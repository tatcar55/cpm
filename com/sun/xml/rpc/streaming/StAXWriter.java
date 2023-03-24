/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.xml.CDATA;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXWriter
/*     */   extends XMLWriterBase
/*     */ {
/*     */   private static final String XML_VERSION = "1.0";
/*     */   private XMLStreamWriter writer;
/*     */   private PrefixFactory prefixFactory;
/*     */   private boolean documentEnded = false;
/*  59 */   private static XMLOutputFactory outputFactory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXWriter(OutputStream out, String enc, boolean declare) {
/*     */     try {
/*  67 */       if (outputFactory == null) {
/*  68 */         outputFactory = XMLOutputFactory.newInstance();
/*     */       }
/*  70 */       this.writer = outputFactory.createXMLStreamWriter(out, enc);
/*  71 */       if (declare) {
/*  72 */         this.writer.writeStartDocument(enc, "1.0");
/*     */       }
/*     */     }
/*  75 */     catch (XMLStreamException e) {
/*  76 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXWriter(OutputStream out, String enc, boolean declare, XMLStreamWriter writer) {
/*     */     try {
/*  84 */       this.writer = writer;
/*  85 */       if (declare) {
/*  86 */         writer.writeStartDocument(enc, "1.0");
/*     */       }
/*     */     }
/*  89 */     catch (XMLStreamException e) {
/*  90 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriter getXMLStreamWriter() {
/*  98 */     return this.writer;
/*     */   }
/*     */   
/*     */   public void startElement(String localName, String uri) {
/*     */     try {
/* 103 */       if (uri.equals("")) {
/* 104 */         this.writer.writeStartElement(localName);
/*     */       } else {
/* 106 */         boolean mustDeclarePrefix = false;
/* 107 */         String aPrefix = getPrefix(uri);
/* 108 */         if (aPrefix == null) {
/* 109 */           aPrefix = this.prefixFactory.getPrefix(uri);
/* 110 */           mustDeclarePrefix = true;
/*     */         } 
/* 112 */         this.writer.writeStartElement(aPrefix, localName, uri);
/* 113 */         if (mustDeclarePrefix) {
/* 114 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } 
/* 117 */     } catch (XMLStreamException e) {
/* 118 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(String localName, String uri, String prefix) {
/*     */     try {
/* 124 */       if (uri.equals("")) {
/* 125 */         this.writer.writeStartElement(localName);
/*     */       } else {
/* 127 */         boolean mustDeclarePrefix = false;
/* 128 */         String other = this.writer.getPrefix(uri);
/* 129 */         if (other == null) {
/* 130 */           mustDeclarePrefix = true;
/*     */         }
/* 132 */         else if (!other.equals(prefix)) {
/* 133 */           mustDeclarePrefix = true;
/* 134 */           this.writer.setPrefix(prefix, uri);
/*     */         } 
/* 136 */         this.writer.writeStartElement(prefix, localName, uri);
/* 137 */         if (mustDeclarePrefix) {
/* 138 */           this.writer.writeNamespace(prefix, uri);
/*     */         }
/*     */       } 
/* 141 */     } catch (XMLStreamException e) {
/* 142 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String uri, String value) {
/*     */     try {
/* 148 */       if (uri.equals("")) {
/* 149 */         this.writer.writeAttribute(localName, value);
/*     */       } else {
/* 151 */         boolean mustDeclarePrefix = false;
/* 152 */         String aPrefix = getPrefix(uri);
/* 153 */         if (aPrefix == null) {
/* 154 */           mustDeclarePrefix = true;
/*     */           
/* 156 */           if (this.prefixFactory != null) {
/* 157 */             aPrefix = this.prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/* 160 */           if (aPrefix == null) {
/* 161 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 166 */         this.writer.writeAttribute(aPrefix, uri, localName, value);
/* 167 */         if (mustDeclarePrefix) {
/* 168 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       } 
/* 171 */     } catch (XMLStreamException e) {
/* 172 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttributeUnquoted(String localName, String uri, String value) {
/* 182 */     writeAttribute(localName, uri, value);
/*     */   }
/*     */   
/*     */   public void writeChars(String chars) {
/*     */     try {
/* 187 */       this.writer.writeCharacters(chars);
/* 188 */     } catch (XMLStreamException e) {
/* 189 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeChars(CDATA chars) {
/* 194 */     writeChars(chars.getText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharsUnquoted(String chars) {
/*     */     try {
/* 206 */       if (chars.charAt(0) == '<') {
/* 207 */         parseAndWriteXML(chars);
/*     */         return;
/*     */       } 
/* 210 */       this.writer.writeCharacters(chars);
/* 211 */     } catch (XMLStreamException e) {
/* 212 */       throw wrapException(e);
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
/*     */   public void writeCharsUnquoted(char[] buf, int offset, int len) {
/*     */     try {
/* 225 */       if (buf[offset] == '<') {
/* 226 */         parseAndWriteXML(new String(buf, offset, len));
/*     */         return;
/*     */       } 
/* 229 */       this.writer.writeCharacters(buf, offset, len);
/* 230 */     } catch (XMLStreamException e) {
/* 231 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeComment(String comment) {
/*     */     try {
/* 237 */       this.writer.writeComment(comment);
/* 238 */     } catch (XMLStreamException e) {
/* 239 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String prefix, String uri) {
/*     */     try {
/* 249 */       this.writer.writeNamespace(prefix, uri);
/*     */       
/* 251 */       if (prefix == null || prefix.length() == 0 || prefix.equals("xmlns")) {
/* 252 */         this.writer.setDefaultNamespace(uri);
/*     */       } else {
/*     */         
/* 255 */         this.writer.setPrefix(prefix, uri);
/*     */       } 
/* 257 */     } catch (XMLStreamException e) {
/* 258 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String uri) {
/*     */     try {
/* 269 */       String aPrefix = this.writer.getPrefix(uri);
/* 270 */       if (aPrefix == null) {
/* 271 */         if (this.prefixFactory == null) {
/* 272 */           throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */         }
/* 274 */         aPrefix = this.prefixFactory.getPrefix(uri);
/*     */       } 
/*     */       
/* 277 */       writeNamespaceDeclaration(aPrefix, uri);
/*     */     }
/* 279 */     catch (XMLStreamException e) {
/* 280 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endElement() {
/*     */     try {
/* 286 */       this.writer.writeEndElement();
/* 287 */     } catch (XMLStreamException e) {
/* 288 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 294 */       if (!this.documentEnded) {
/* 295 */         this.writer.writeEndDocument();
/*     */       }
/* 297 */       this.writer.close();
/* 298 */     } catch (XMLStreamException e) {
/* 299 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flush() {
/*     */     try {
/* 305 */       this.writer.flush();
/* 306 */     } catch (XMLStreamException e) {
/* 307 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) {
/*     */     try {
/* 313 */       return this.writer.getPrefix(uri);
/* 314 */     } catch (XMLStreamException e) {
/* 315 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 320 */     return this.writer.getNamespaceContext().getNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public PrefixFactory getPrefixFactory() {
/* 324 */     return this.prefixFactory;
/*     */   }
/*     */   
/*     */   public void setPrefixFactory(PrefixFactory factory) {
/* 328 */     this.prefixFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseAndWriteXML(String xml) throws XMLStreamException {
/* 336 */     XMLReader reader = new StAXReader(new StringReader(xml), true);
/* 337 */     int state = 0; do {
/*     */       QName elementName;
/* 339 */       state = reader.next();
/* 340 */       switch (state) {
/*     */         case 1:
/* 342 */           elementName = reader.getName();
/* 343 */           startElement(elementName.getLocalPart(), elementName.getNamespaceURI(), elementName.getPrefix());
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 348 */           endElement();
/*     */           break;
/*     */         case 3:
/* 351 */           writeChars(reader.getValue()); break;
/*     */       } 
/* 353 */     } while (state != 5);
/*     */   }
/*     */   
/*     */   private XMLWriterException wrapException(XMLStreamException e) {
/* 357 */     return new XMLWriterException("xmlwriter.ioException", (Localizable)new LocalizableExceptionAdapter(e));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\StAXWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */