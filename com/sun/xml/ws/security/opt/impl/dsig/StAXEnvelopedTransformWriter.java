/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.JAXBData;
/*     */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.OctectStreamData;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXEnvelopedTransformWriter
/*     */   implements XMLStreamWriter, StreamWriterData
/*     */ {
/*  64 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */   
/*  67 */   private XMLStreamWriter nextWriter = null;
/*     */   private boolean ignore = false;
/*  69 */   private Data data = null;
/*  70 */   private int index = 0;
/*  71 */   private NamespaceContextEx ns = null;
/*     */ 
/*     */   
/*     */   public StAXEnvelopedTransformWriter(XMLStreamWriter writer, Data data) {
/*  75 */     this.nextWriter = writer;
/*  76 */     this.data = data;
/*  77 */     if (data instanceof JAXBData) {
/*  78 */       this.ns = ((JAXBData)data).getNamespaceContext();
/*  79 */     } else if (data instanceof StreamWriterData) {
/*  80 */       this.ns = ((StreamWriterData)data).getNamespaceContext();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public StAXEnvelopedTransformWriter(Data data) {
/*  86 */     this.data = data;
/*  87 */     if (data instanceof JAXBData) {
/*  88 */       this.ns = ((JAXBData)data).getNamespaceContext();
/*  89 */     } else if (data instanceof StreamWriterData) {
/*  90 */       this.ns = ((StreamWriterData)data).getNamespaceContext();
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/*  95 */     return this.ns;
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  99 */     this.nextWriter.close();
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/* 103 */     this.nextWriter.flush();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 107 */     if (this.index > 0) {
/* 108 */       int size = this.index;
/* 109 */       for (int i = 0; i < size; i++) {
/* 110 */         writeEndElement();
/*     */       }
/*     */     } 
/* 113 */     this.nextWriter.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 117 */     if (this.index > 0) {
/* 118 */       this.index--;
/*     */     }
/* 120 */     if (!this.ignore) {
/* 121 */       this.nextWriter.writeEndElement();
/*     */     }
/* 123 */     if (this.index == 0) {
/* 124 */       this.ignore = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 129 */     if (!this.ignore) {
/* 130 */       this.nextWriter.writeStartDocument();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] c, int index, int len) throws XMLStreamException {
/* 135 */     if (!this.ignore) {
/* 136 */       this.nextWriter.writeCharacters(c, index, len);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String string) throws XMLStreamException {
/* 141 */     if (!this.ignore) {
/* 142 */       this.nextWriter.setDefaultNamespace(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCData(String string) throws XMLStreamException {
/* 147 */     if (!this.ignore) {
/* 148 */       this.nextWriter.writeCData(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCharacters(String string) throws XMLStreamException {
/* 153 */     if (!this.ignore) {
/* 154 */       this.nextWriter.writeCharacters(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeComment(String string) throws XMLStreamException {
/* 159 */     if (!this.ignore) {
/* 160 */       this.nextWriter.writeComment(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeDTD(String string) throws XMLStreamException {
/* 165 */     if (!this.ignore) {
/* 166 */       this.nextWriter.writeDTD(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String string) throws XMLStreamException {
/* 171 */     if (!this.ignore) {
/* 172 */       this.nextWriter.writeDefaultNamespace(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string) throws XMLStreamException {
/* 177 */     if (!this.ignore) {
/* 178 */       this.nextWriter.writeEmptyElement(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String string) throws XMLStreamException {
/* 183 */     if (!this.ignore) {
/* 184 */       this.nextWriter.writeEntityRef(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string) throws XMLStreamException {
/* 189 */     if (!this.ignore) {
/* 190 */       this.nextWriter.writeProcessingInstruction(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string) throws XMLStreamException {
/* 195 */     if (!this.ignore) {
/* 196 */       this.nextWriter.writeStartDocument(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string) throws XMLStreamException {
/* 201 */     if (!this.ignore) {
/* 202 */       this.nextWriter.writeStartElement(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 207 */     if (!this.ignore) {
/* 208 */       this.nextWriter.setNamespaceContext(namespaceContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getProperty(String string) throws IllegalArgumentException {
/* 213 */     return this.nextWriter.getProperty(string);
/*     */   }
/*     */   
/*     */   public String getPrefix(String string) throws XMLStreamException {
/* 217 */     return this.nextWriter.getPrefix(string);
/*     */   }
/*     */   
/*     */   public void setPrefix(String string, String string0) throws XMLStreamException {
/* 221 */     if (!this.ignore) {
/* 222 */       this.nextWriter.setPrefix(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 227 */     if (!this.ignore) {
/* 228 */       this.nextWriter.writeAttribute(localName, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0) throws XMLStreamException {
/* 233 */     if (!this.ignore) {
/* 234 */       this.nextWriter.writeEmptyElement(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeNamespace(String string, String string0) throws XMLStreamException {
/* 239 */     if (!this.ignore) {
/* 240 */       this.nextWriter.writeNamespace(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string, String string0) throws XMLStreamException {
/* 245 */     if (!this.ignore) {
/* 246 */       this.nextWriter.writeProcessingInstruction(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string, String string0) throws XMLStreamException {
/* 251 */     if (!this.ignore) {
/* 252 */       this.nextWriter.writeStartDocument(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 257 */     if (!this.ignore) {
/* 258 */       if (localName == "Signature" && namespaceURI == "http://www.w3.org/2000/09/xmldsig#" && 
/* 259 */         !((StAXEXC14nCanonicalizerImpl)this.nextWriter).isParentToParentAdvice()) {
/* 260 */         this.ignore = true;
/* 261 */         this.index++;
/*     */         
/*     */         return;
/*     */       } 
/* 265 */       this.nextWriter.writeStartElement(namespaceURI, localName);
/*     */     } else {
/* 267 */       this.index++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 272 */     if (!this.ignore) {
/* 273 */       this.nextWriter.writeAttribute(prefix, namespaceURI, localName, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0, String string1) throws XMLStreamException {
/* 278 */     if (!this.ignore) {
/* 279 */       this.nextWriter.writeEmptyElement(string, string0, string1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 284 */     if (!this.ignore) {
/* 285 */       if (localName == "Signature" && namespaceURI == "http://www.w3.org/2000/09/xmldsig#" && 
/* 286 */         !((StAXEXC14nCanonicalizerImpl)this.nextWriter).isParentToParentAdvice()) {
/* 287 */         this.ignore = true;
/* 288 */         this.index++;
/*     */         
/*     */         return;
/*     */       } 
/* 292 */       this.nextWriter.writeStartElement(prefix, localName, namespaceURI);
/*     */     } else {
/* 294 */       this.index++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 299 */     if (!this.ignore) {
/* 300 */       this.nextWriter.writeAttribute(namespaceURI, localName, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/* 309 */     this.nextWriter = writer;
/* 310 */     if (this.data instanceof JAXBData) {
/*     */       try {
/* 312 */         ((JAXBData)this.data).writeTo(this);
/* 313 */       } catch (XWSSecurityException ex) {
/* 314 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1706_ERROR_ENVELOPED_SIGNATURE(), (Throwable)ex);
/* 315 */         throw new XMLStreamException("Error occurred while performing Enveloped Signature");
/*     */       } 
/* 317 */     } else if (this.data instanceof StreamWriterData) {
/* 318 */       ((StreamWriterData)this.data).write(this);
/* 319 */     } else if (this.data instanceof OctectStreamData) {
/* 320 */       ((OctectStreamData)this.data).write(this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\StAXEnvelopedTransformWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */