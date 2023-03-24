/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import java.util.Stack;
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
/*     */ public class IndentingXMLStreamWriter
/*     */   extends DelegatingXMLStreamWriter
/*     */ {
/*  51 */   private static final Object SEEN_NOTHING = new Object();
/*  52 */   private static final Object SEEN_ELEMENT = new Object();
/*  53 */   private static final Object SEEN_DATA = new Object();
/*     */   
/*  55 */   private Object state = SEEN_NOTHING;
/*  56 */   private Stack<Object> stateStack = new Stack();
/*     */   
/*  58 */   private String indentStep = "  ";
/*  59 */   private int depth = 0;
/*     */   
/*     */   public IndentingXMLStreamWriter(XMLStreamWriter writer) {
/*  62 */     super(writer);
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
/*     */ 
/*     */   
/*     */   public int getIndentStep() {
/*  80 */     return this.indentStep.length();
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
/*     */   public void setIndentStep(int indentStep) {
/*  95 */     StringBuilder s = new StringBuilder();
/*  96 */     while (indentStep > 0) { s.append(' '); indentStep--; }
/*  97 */      setIndentStep(s.toString());
/*     */   }
/*     */   
/*     */   public void setIndentStep(String s) {
/* 101 */     this.indentStep = s;
/*     */   }
/*     */   
/*     */   private void onStartElement() throws XMLStreamException {
/* 105 */     this.stateStack.push(SEEN_ELEMENT);
/* 106 */     this.state = SEEN_NOTHING;
/* 107 */     if (this.depth > 0) {
/* 108 */       super.writeCharacters("\n");
/*     */     }
/* 110 */     doIndent();
/* 111 */     this.depth++;
/*     */   }
/*     */   
/*     */   private void onEndElement() throws XMLStreamException {
/* 115 */     this.depth--;
/* 116 */     if (this.state == SEEN_ELEMENT) {
/* 117 */       super.writeCharacters("\n");
/* 118 */       doIndent();
/*     */     } 
/* 120 */     this.state = this.stateStack.pop();
/*     */   }
/*     */   
/*     */   private void onEmptyElement() throws XMLStreamException {
/* 124 */     this.state = SEEN_ELEMENT;
/* 125 */     if (this.depth > 0) {
/* 126 */       super.writeCharacters("\n");
/*     */     }
/* 128 */     doIndent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doIndent() throws XMLStreamException {
/* 139 */     if (this.depth > 0) {
/* 140 */       for (int i = 0; i < this.depth; i++) {
/* 141 */         super.writeCharacters(this.indentStep);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 147 */     super.writeStartDocument();
/* 148 */     super.writeCharacters("\n");
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 152 */     super.writeStartDocument(version);
/* 153 */     super.writeCharacters("\n");
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 157 */     super.writeStartDocument(encoding, version);
/* 158 */     super.writeCharacters("\n");
/*     */   }
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 162 */     onStartElement();
/* 163 */     super.writeStartElement(localName);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 167 */     onStartElement();
/* 168 */     super.writeStartElement(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 172 */     onStartElement();
/* 173 */     super.writeStartElement(prefix, localName, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 177 */     onEmptyElement();
/* 178 */     super.writeEmptyElement(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 182 */     onEmptyElement();
/* 183 */     super.writeEmptyElement(prefix, localName, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 187 */     onEmptyElement();
/* 188 */     super.writeEmptyElement(localName);
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 192 */     onEndElement();
/* 193 */     super.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/* 197 */     this.state = SEEN_DATA;
/* 198 */     super.writeCharacters(text);
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 202 */     this.state = SEEN_DATA;
/* 203 */     super.writeCharacters(text, start, len);
/*     */   }
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 207 */     this.state = SEEN_DATA;
/* 208 */     super.writeCData(data);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\IndentingXMLStreamWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */