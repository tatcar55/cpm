/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.xml.CDATA;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastInfosetWriter
/*     */   extends StAXDocumentSerializer
/*     */   implements XMLWriter
/*     */ {
/*     */   PrefixFactory _prefixFactory;
/*     */   
/*     */   public FastInfosetWriter(OutputStream os, String encoding) {
/*  50 */     setOutputStream(os);
/*  51 */     setEncoding(encoding);
/*     */   }
/*     */   
/*     */   public void reset() {
/*  55 */     super.reset();
/*  56 */     this._prefixFactory = null;
/*     */   }
/*     */   
/*     */   public void writeStartDocument() {
/*     */     try {
/*  61 */       writeStartDocument("1.0");
/*  62 */     } catch (XMLStreamException e) {
/*  63 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String value) {
/*     */     try {
/*  69 */       super.writeAttribute(localName, value);
/*     */     }
/*  71 */     catch (XMLStreamException e) {
/*  72 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(QName name) {
/*  77 */     startElement(name.getLocalPart(), name.getNamespaceURI());
/*     */   }
/*     */   
/*     */   public void startElement(String localName) {
/*  81 */     startElement(localName, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String localName, String uri) {
/*     */     try {
/*  89 */       if (uri.length() == 0) {
/*  90 */         writeStartElement(localName);
/*     */       } else {
/*     */         
/*  93 */         String aPrefix = null;
/*  94 */         boolean mustDeclarePrefix = false;
/*     */         
/*  96 */         aPrefix = getPrefix(uri);
/*  97 */         if (aPrefix == null) {
/*  98 */           mustDeclarePrefix = true;
/*  99 */           if (this._prefixFactory != null) {
/* 100 */             aPrefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/* 102 */           if (aPrefix == null) {
/* 103 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */         
/* 107 */         writeStartElement(aPrefix, localName, uri);
/* 108 */         if (mustDeclarePrefix) {
/* 109 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       }
/*     */     
/* 113 */     } catch (XMLStreamException e) {
/* 114 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String localName, String uri, String prefix) {
/*     */     try {
/* 123 */       if (uri.length() == 0) {
/* 124 */         writeStartElement(localName);
/*     */       } else {
/*     */         
/* 127 */         String aPrefix = null;
/* 128 */         boolean mustDeclarePrefix = false;
/*     */         
/* 130 */         aPrefix = getPrefix(uri);
/* 131 */         if (aPrefix == null) {
/* 132 */           mustDeclarePrefix = true;
/* 133 */           aPrefix = prefix;
/* 134 */           if (aPrefix == null) {
/* 135 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */         
/* 139 */         writeStartElement(aPrefix, localName, uri);
/* 140 */         if (mustDeclarePrefix) {
/* 141 */           writeNamespaceDeclaration(aPrefix, uri);
/*     */         }
/*     */       }
/*     */     
/* 145 */     } catch (XMLStreamException e) {
/* 146 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String localName, String uri, String value) {
/*     */     try {
/* 155 */       if (uri.length() == 0) {
/* 156 */         writeAttribute(localName, value);
/*     */       } else {
/*     */         
/* 159 */         boolean mustDeclarePrefix = false;
/* 160 */         String prefix = getPrefix(uri);
/* 161 */         if (prefix == null) {
/* 162 */           mustDeclarePrefix = true;
/*     */           
/* 164 */           if (this._prefixFactory != null) {
/* 165 */             prefix = this._prefixFactory.getPrefix(uri);
/*     */           }
/*     */           
/* 168 */           if (prefix == null) {
/* 169 */             throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 174 */         writeAttribute(prefix, uri, localName, value);
/* 175 */         if (mustDeclarePrefix) {
/* 176 */           writeNamespaceDeclaration(prefix, uri);
/*     */         }
/*     */       } 
/* 179 */     } catch (XMLStreamException e) {
/* 180 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startElement(QName name, String prefix) {
/* 185 */     startElement(name.getLocalPart(), name.getNamespaceURI(), prefix);
/*     */   }
/*     */   
/*     */   public void writeAttribute(QName name, String value) {
/* 189 */     writeAttribute(name.getLocalPart(), name.getNamespaceURI(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttributeUnquoted(String localName, String value) {
/* 196 */     writeAttribute(localName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttributeUnquoted(String localName, String uri, String value) {
/* 205 */     writeAttribute(localName, uri, value);
/*     */   }
/*     */   
/*     */   public void writeAttributeUnquoted(QName name, String value) {
/* 209 */     writeAttributeUnquoted(name.getLocalPart(), name.getNamespaceURI(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String prefix, String uri) {
/*     */     try {
/* 220 */       setPrefix(prefix, uri);
/* 221 */       writeNamespace(prefix, uri);
/*     */     }
/* 223 */     catch (XMLStreamException e) {
/* 224 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespaceDeclaration(String uri) {
/* 234 */     if (this._prefixFactory == null) {
/* 235 */       throw new XMLWriterException("xmlwriter.noPrefixForURI", uri);
/*     */     }
/* 237 */     String aPrefix = this._prefixFactory.getPrefix(uri);
/* 238 */     writeNamespaceDeclaration(aPrefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(String chars) {
/*     */     try {
/* 246 */       writeCharacters(chars);
/*     */     }
/* 248 */     catch (XMLStreamException e) {
/* 249 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(CDATA chars) {
/* 257 */     writeChars(chars.getText());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharsUnquoted(String chars) {
/* 264 */     writeChars(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharsUnquoted(char[] buf, int offset, int len) {
/*     */     try {
/* 272 */       writeCharacters(buf, offset, len);
/*     */     }
/* 274 */     catch (XMLStreamException e) {
/* 275 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeComment(String comment) {
/*     */     try {
/* 284 */       super.writeComment(comment);
/*     */     }
/* 286 */     catch (XMLStreamException e) {
/* 287 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement() {
/*     */     try {
/* 296 */       writeEndElement();
/*     */     }
/* 298 */     catch (XMLStreamException e) {
/* 299 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefixFactory getPrefixFactory() {
/* 307 */     return this._prefixFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefixFactory(PrefixFactory factory) {
/* 314 */     this._prefixFactory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI(String prefix) {
/* 323 */     return getNamespaceContext().getNamespaceURI(prefix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) {
/*     */     try {
/* 333 */       return super.getPrefix(uri);
/*     */     }
/* 335 */     catch (XMLStreamException e) {
/* 336 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/*     */     try {
/* 345 */       super.flush();
/*     */     }
/* 347 */     catch (XMLStreamException e) {
/* 348 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 357 */       writeEndDocument();
/* 358 */       super.close();
/*     */     }
/* 360 */     catch (XMLStreamException e) {
/* 361 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeBytes(byte[] b, int start, int length) {
/*     */     try {
/* 373 */       encodeTerminationAndCurrentElement(true);
/* 374 */       encodeCIIOctetAlgorithmData(1, b, start, length);
/*     */     }
/* 376 */     catch (Exception e) {
/* 377 */       wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLWriterException wrapException(Exception e) {
/* 384 */     return new XMLWriterException("xmlwriter.ioException", (Localizable)new LocalizableExceptionAdapter(e));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\FastInfosetWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */