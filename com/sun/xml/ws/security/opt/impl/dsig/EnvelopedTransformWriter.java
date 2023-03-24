/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.DigesterOutputStream;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
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
/*     */ public class EnvelopedTransformWriter
/*     */   implements XMLStreamWriter
/*     */ {
/*  57 */   private StAXEXC14nCanonicalizerImpl stAXC14n = null;
/*  58 */   private JAXBSignatureHeaderElement signature = null;
/*  59 */   private Reference ref = null;
/*  60 */   private DigesterOutputStream dos = null;
/*  61 */   private int index = 0;
/*  62 */   private XMLStreamWriter writer = null;
/*     */   
/*     */   public EnvelopedTransformWriter(XMLStreamWriter writer, StAXEXC14nCanonicalizerImpl stAXC14n, Reference ref, JAXBSignatureHeaderElement signature, DigesterOutputStream dos) {
/*  65 */     this.stAXC14n = stAXC14n;
/*  66 */     this.writer = writer;
/*  67 */     this.ref = ref;
/*  68 */     this.signature = signature;
/*  69 */     this.dos = dos;
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/*  73 */     return this.writer.getNamespaceContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {
/*  81 */     this.writer.flush();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/*  85 */     for (int i = 0; i < this.index; i++) {
/*  86 */       this.stAXC14n.writeEndElement();
/*  87 */       this.writer.writeEndElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/*  92 */     if (this.index == 0) {
/*     */       return;
/*     */     }
/*  95 */     this.index--;
/*  96 */     this.stAXC14n.writeEndElement();
/*  97 */     this.ref.setDigestValue(this.dos.getDigestValue());
/*     */     
/*  99 */     this.signature.sign();
/*     */     
/* 101 */     this.signature.writeTo(this.writer);
/* 102 */     this.writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 106 */     this.stAXC14n.writeStartDocument();
/* 107 */     this.writer.writeStartDocument();
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] c, int index, int len) throws XMLStreamException {
/* 111 */     this.stAXC14n.writeCharacters(c, index, len);
/* 112 */     this.writer.writeCharacters(c, index, len);
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String string) throws XMLStreamException {
/* 116 */     this.writer.setDefaultNamespace(string);
/* 117 */     this.stAXC14n.setDefaultNamespace(string);
/*     */   }
/*     */   
/*     */   public void writeCData(String string) throws XMLStreamException {
/* 121 */     this.stAXC14n.writeCData(string);
/* 122 */     this.writer.writeCData(string);
/*     */   }
/*     */   
/*     */   public void writeCharacters(String string) throws XMLStreamException {
/* 126 */     this.stAXC14n.writeCharacters(string);
/* 127 */     this.writer.writeCharacters(string);
/*     */   }
/*     */   
/*     */   public void writeComment(String string) throws XMLStreamException {
/* 131 */     this.stAXC14n.writeComment(string);
/* 132 */     this.writer.writeComment(string);
/*     */   }
/*     */   
/*     */   public void writeDTD(String string) throws XMLStreamException {
/* 136 */     this.stAXC14n.writeDTD(string);
/* 137 */     this.writer.writeDTD(string);
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String string) throws XMLStreamException {
/* 141 */     this.stAXC14n.writeDefaultNamespace(string);
/* 142 */     this.writer.writeDefaultNamespace(string);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string) throws XMLStreamException {
/* 146 */     this.stAXC14n.writeEmptyElement(string);
/* 147 */     this.writer.writeEmptyElement(string);
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String string) throws XMLStreamException {
/* 151 */     this.stAXC14n.writeEntityRef(string);
/* 152 */     this.writer.writeEntityRef(string);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string) throws XMLStreamException {
/* 156 */     this.stAXC14n.writeProcessingInstruction(string);
/* 157 */     this.writer.writeProcessingInstruction(string);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string) throws XMLStreamException {
/* 161 */     this.stAXC14n.writeStartDocument(string);
/* 162 */     this.writer.writeStartDocument(string);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string) throws XMLStreamException {
/* 166 */     this.index++;
/* 167 */     this.stAXC14n.writeStartElement(string);
/* 168 */     this.writer.writeStartElement(string);
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 172 */     this.writer.setNamespaceContext(namespaceContext);
/*     */   }
/*     */   
/*     */   public Object getProperty(String string) throws IllegalArgumentException {
/* 176 */     return this.writer.getProperty(string);
/*     */   }
/*     */   
/*     */   public String getPrefix(String string) throws XMLStreamException {
/* 180 */     return this.writer.getPrefix(string);
/*     */   }
/*     */   
/*     */   public void setPrefix(String string, String string0) throws XMLStreamException {
/* 184 */     this.stAXC14n.setPrefix(string, string0);
/* 185 */     this.writer.setPrefix(string, string0);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0) throws XMLStreamException {
/* 189 */     this.stAXC14n.writeAttribute(string, string0);
/* 190 */     this.writer.writeAttribute(string, string0);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0) throws XMLStreamException {
/* 194 */     this.stAXC14n.writeEmptyElement(string, string0);
/* 195 */     this.writer.writeEmptyElement(string, string0);
/*     */   }
/*     */   
/*     */   public void writeNamespace(String string, String string0) throws XMLStreamException {
/* 199 */     this.stAXC14n.writeNamespace(string, string0);
/* 200 */     this.writer.writeNamespace(string, string0);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string, String string0) throws XMLStreamException {
/* 204 */     this.stAXC14n.writeProcessingInstruction(string, string0);
/* 205 */     this.writer.writeProcessingInstruction(string, string0);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string, String string0) throws XMLStreamException {
/* 209 */     this.stAXC14n.writeStartDocument(string, string0);
/* 210 */     this.writer.writeStartDocument(string, string0);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string, String string0) throws XMLStreamException {
/* 214 */     this.index++;
/* 215 */     this.stAXC14n.writeStartElement(string, string0);
/* 216 */     this.writer.writeStartElement(string, string0);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0, String string1) throws XMLStreamException {
/* 220 */     this.stAXC14n.writeAttribute(string, string0, string1);
/* 221 */     this.writer.writeAttribute(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0, String string1) throws XMLStreamException {
/* 225 */     this.stAXC14n.writeEmptyElement(string, string0, string1);
/* 226 */     this.writer.writeEmptyElement(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string, String string0, String string1) throws XMLStreamException {
/* 230 */     this.index++;
/* 231 */     this.stAXC14n.writeStartElement(string, string0, string1);
/* 232 */     this.writer.writeStartElement(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0, String string1, String string2) throws XMLStreamException {
/* 236 */     this.stAXC14n.writeAttribute(string, string0, string1, string2);
/* 237 */     this.writer.writeAttribute(string, string0, string1, string2);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\EnvelopedTransformWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */