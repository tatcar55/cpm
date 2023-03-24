/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.helpers.StreamWriterDelegate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndentingXMLStreamWriter
/*     */   extends StreamWriterDelegate
/*     */   implements Indentation
/*     */ {
/*     */   private int depth;
/*     */   private int[] stack;
/*     */   private static final int WROTE_MARKUP = 1;
/*     */   private static final int WROTE_DATA = 2;
/*     */   private String indent;
/*     */   private String newLine;
/*     */   private char[] linePrefix;
/*     */   
/*     */   public IndentingXMLStreamWriter(XMLStreamWriter out) {
/*  74 */     super(out);
/*     */ 
/*     */ 
/*     */     
/*  78 */     this.depth = 0;
/*     */ 
/*     */     
/*  81 */     this.stack = new int[] { 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     this.indent = "  ";
/*     */     
/*  89 */     this.newLine = "\n";
/*     */ 
/*     */     
/*  92 */     this.linePrefix = null;
/*     */   }
/*     */   public void setIndent(String indent) {
/*  95 */     if (!indent.equals(this.indent)) {
/*  96 */       this.indent = indent;
/*  97 */       this.linePrefix = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getIndent() {
/* 102 */     return this.indent;
/*     */   }
/*     */   
/*     */   public void setNewLine(String newLine) {
/* 106 */     if (!newLine.equals(this.newLine)) {
/* 107 */       this.newLine = newLine;
/* 108 */       this.linePrefix = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLineSeparator() {
/*     */     try {
/* 118 */       return System.getProperty("line.separator");
/* 119 */     } catch (SecurityException ignored) {
/*     */       
/* 121 */       return "\n";
/*     */     } 
/*     */   }
/*     */   public String getNewLine() {
/* 125 */     return this.newLine;
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 129 */     beforeMarkup();
/* 130 */     this.out.writeStartDocument();
/* 131 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 135 */     beforeMarkup();
/* 136 */     this.out.writeStartDocument(version);
/* 137 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 141 */     beforeMarkup();
/* 142 */     this.out.writeStartDocument(encoding, version);
/* 143 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {
/* 147 */     beforeMarkup();
/* 148 */     this.out.writeDTD(dtd);
/* 149 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 153 */     beforeMarkup();
/* 154 */     this.out.writeProcessingInstruction(target);
/* 155 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 159 */     beforeMarkup();
/* 160 */     this.out.writeProcessingInstruction(target, data);
/* 161 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/* 165 */     beforeMarkup();
/* 166 */     this.out.writeComment(data);
/* 167 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 171 */     beforeMarkup();
/* 172 */     this.out.writeEmptyElement(localName);
/* 173 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 177 */     beforeMarkup();
/* 178 */     this.out.writeEmptyElement(namespaceURI, localName);
/* 179 */     afterMarkup();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 184 */     beforeMarkup();
/* 185 */     this.out.writeEmptyElement(prefix, localName, namespaceURI);
/* 186 */     afterMarkup();
/*     */   }
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 190 */     beforeStartElement();
/* 191 */     this.out.writeStartElement(localName);
/* 192 */     afterStartElement();
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 196 */     beforeStartElement();
/* 197 */     this.out.writeStartElement(namespaceURI, localName);
/* 198 */     afterStartElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 203 */     beforeStartElement();
/* 204 */     this.out.writeStartElement(prefix, localName, namespaceURI);
/* 205 */     afterStartElement();
/*     */   }
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/* 209 */     this.out.writeCharacters(text);
/* 210 */     afterData();
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 214 */     this.out.writeCharacters(text, start, len);
/* 215 */     afterData();
/*     */   }
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 219 */     this.out.writeCData(data);
/* 220 */     afterData();
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 224 */     this.out.writeEntityRef(name);
/* 225 */     afterData();
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 229 */     beforeEndElement();
/* 230 */     this.out.writeEndElement();
/* 231 */     afterEndElement();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/*     */     try {
/* 236 */       while (this.depth > 0) {
/* 237 */         writeEndElement();
/*     */       }
/* 239 */     } catch (Exception ignored) {}
/*     */     
/* 241 */     this.out.writeEndDocument();
/* 242 */     afterEndDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeMarkup() {
/* 247 */     int soFar = this.stack[this.depth];
/* 248 */     if ((soFar & 0x2) == 0 && (this.depth > 0 || soFar != 0)) {
/*     */       
/*     */       try {
/*     */         
/* 252 */         writeNewLine(this.depth);
/* 253 */         if (this.depth > 0 && getIndent().length() > 0) {
/* 254 */           afterMarkup();
/*     */         }
/* 256 */       } catch (Exception e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void afterMarkup() {
/* 263 */     this.stack[this.depth] = this.stack[this.depth] | 0x1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterData() {
/* 268 */     this.stack[this.depth] = this.stack[this.depth] | 0x2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeStartElement() {
/* 273 */     beforeMarkup();
/* 274 */     if (this.stack.length <= this.depth + 1) {
/*     */       
/* 276 */       int[] newStack = new int[this.stack.length * 2];
/* 277 */       System.arraycopy(this.stack, 0, newStack, 0, this.stack.length);
/* 278 */       this.stack = newStack;
/*     */     } 
/* 280 */     this.stack[this.depth + 1] = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterStartElement() {
/* 285 */     afterMarkup();
/* 286 */     this.depth++;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void beforeEndElement() {
/* 291 */     if (this.depth > 0 && this.stack[this.depth] == 1) {
/*     */       try {
/* 293 */         writeNewLine(this.depth - 1);
/* 294 */       } catch (Exception ignored) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void afterEndElement() {
/* 301 */     if (this.depth > 0) {
/* 302 */       this.depth--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterEndDocument() {
/* 308 */     if (this.stack[this.depth = 0] == 1) {
/*     */       try {
/* 310 */         writeNewLine(0);
/* 311 */       } catch (Exception ignored) {}
/*     */     }
/*     */     
/* 314 */     this.stack[this.depth] = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeNewLine(int indentation) throws XMLStreamException {
/* 319 */     int newLineLength = getNewLine().length();
/* 320 */     int prefixLength = newLineLength + getIndent().length() * indentation;
/* 321 */     if (prefixLength > 0) {
/* 322 */       if (this.linePrefix == null) {
/* 323 */         this.linePrefix = (getNewLine() + getIndent()).toCharArray();
/*     */       }
/* 325 */       while (prefixLength > this.linePrefix.length) {
/*     */         
/* 327 */         char[] newPrefix = new char[newLineLength + (this.linePrefix.length - newLineLength) * 2];
/*     */         
/* 329 */         System.arraycopy(this.linePrefix, 0, newPrefix, 0, this.linePrefix.length);
/* 330 */         System.arraycopy(this.linePrefix, newLineLength, newPrefix, this.linePrefix.length, this.linePrefix.length - newLineLength);
/*     */         
/* 332 */         this.linePrefix = newPrefix;
/*     */       } 
/* 334 */       this.out.writeCharacters(this.linePrefix, 0, prefixLength);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\IndentingXMLStreamWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */