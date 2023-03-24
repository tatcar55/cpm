/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import javax.xml.namespace.NamespaceContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamFilterWithId
/*     */   extends XMLStreamFilter
/*     */ {
/*  55 */   String id = null;
/*     */   
/*     */   boolean wroteId = false;
/*     */   
/*     */   public XMLStreamFilterWithId(XMLStreamWriter writer, NamespaceContextEx nce, String id) throws XMLStreamException {
/*  60 */     super(writer, nce);
/*  61 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String string) throws XMLStreamException {
/*  65 */     this.writer.setDefaultNamespace(string);
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/*  69 */     if (!this.wroteId && this.count == 1) {
/*  70 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/*  72 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/*  73 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/*  75 */       this.wroteId = true;
/*     */     } 
/*  77 */     this.writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string) throws XMLStreamException {
/*  81 */     if (!this.wroteId && this.count == 1) {
/*  82 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/*  84 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/*  85 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/*  87 */       this.wroteId = true;
/*     */     } 
/*  89 */     if (!this.seenFirstElement) {
/*  90 */       this.seenFirstElement = true;
/*     */     }
/*  92 */     this.writer.writeStartElement(string);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.count++;
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0) throws XMLStreamException {
/* 104 */     this.writer.writeAttribute(string, string0);
/*     */   }
/*     */   
/*     */   public void writeNamespace(String string, String string0) throws XMLStreamException {
/* 108 */     this.writer.writeNamespace(string, string0);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string, String string0) throws XMLStreamException {
/* 112 */     if (!this.wroteId && this.count == 1) {
/* 113 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 115 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 116 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 118 */       this.wroteId = true;
/*     */     } 
/* 120 */     if (!this.seenFirstElement) {
/* 121 */       this.seenFirstElement = true;
/*     */     }
/* 123 */     this.writer.writeStartElement(string, string0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String string, String string0, String string1) throws XMLStreamException {
/* 136 */     this.writer.writeAttribute(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string, String string0, String string1) throws XMLStreamException {
/* 140 */     if (!this.wroteId && this.count == 1) {
/* 141 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 143 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 144 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 146 */       this.wroteId = true;
/*     */     } 
/* 148 */     if (!this.seenFirstElement) {
/* 149 */       this.seenFirstElement = true;
/*     */     }
/*     */     
/* 152 */     this.writer.writeStartElement(string, string0, string1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String string, String string0, String string1, String string2) throws XMLStreamException {
/* 165 */     this.writer.writeAttribute(string, string0, string1, string2);
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] c, int index, int len) throws XMLStreamException {
/* 169 */     if (!this.wroteId && this.count == 1) {
/* 170 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 172 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 173 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 175 */       this.wroteId = true;
/*     */     } 
/* 177 */     this.writer.writeCharacters(c, index, len);
/*     */   }
/*     */   
/*     */   public void writeCharacters(String string) throws XMLStreamException {
/* 181 */     if (!this.wroteId && this.count == 1) {
/* 182 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 184 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 185 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 187 */       this.wroteId = true;
/*     */     } 
/* 189 */     this.writer.writeCharacters(string);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string) throws XMLStreamException {
/* 193 */     if (this.count == 0) {
/* 194 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 196 */     if (!this.wroteId && this.count == 1) {
/* 197 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 199 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 200 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 202 */       this.wroteId = true;
/*     */     } 
/* 204 */     this.writer.writeEmptyElement(string);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String string, String string0, String string1) throws XMLStreamException {
/* 209 */     if (this.count == 0) {
/* 210 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 212 */     if (!this.wroteId && this.count == 1) {
/* 213 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 215 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 216 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 218 */       this.wroteId = true;
/*     */     } 
/* 220 */     this.writer.writeEmptyElement(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0) throws XMLStreamException {
/* 224 */     if (this.count == 0) {
/* 225 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 227 */     if (!this.wroteId && this.count == 1) {
/* 228 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 230 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 231 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 233 */       this.wroteId = true;
/*     */     } 
/* 235 */     this.writer.writeEmptyElement(string, string0);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string, String string0) throws XMLStreamException {
/* 239 */     if (!this.wroteId && this.count == 1) {
/* 240 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 242 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 243 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 245 */       this.wroteId = true;
/*     */     } 
/* 247 */     this.writer.writeProcessingInstruction(string, string0);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string) throws XMLStreamException {
/* 251 */     if (!this.wroteId && this.count == 1) {
/* 252 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 254 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 255 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 257 */       this.wroteId = true;
/*     */     } 
/* 259 */     this.writer.writeProcessingInstruction(string);
/*     */   }
/*     */   
/*     */   public void writeCData(String string) throws XMLStreamException {
/* 263 */     if (!this.wroteId && this.count == 1) {
/* 264 */       this.writer.writeAttribute("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", this.id);
/*     */       
/* 266 */       if (this.writer instanceof com.sun.xml.wss.impl.c14n.BaseCanonicalizer) {
/* 267 */         this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */       }
/* 269 */       this.wroteId = true;
/*     */     } 
/* 271 */     this.writer.writeCData(string);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\XMLStreamFilterWithId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */