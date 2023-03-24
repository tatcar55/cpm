/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndentingXMLFilter
/*     */   extends XMLFilterImpl
/*     */   implements LexicalHandler
/*     */ {
/*     */   private LexicalHandler lexical;
/*     */   
/*     */   public IndentingXMLFilter() {}
/*     */   
/*     */   public IndentingXMLFilter(ContentHandler handler) {
/*  63 */     setContentHandler(handler);
/*     */   }
/*     */   
/*     */   public IndentingXMLFilter(ContentHandler handler, LexicalHandler lexical) {
/*  67 */     setContentHandler(handler);
/*  68 */     setLexicalHandler(lexical);
/*     */   }
/*     */   
/*     */   public LexicalHandler getLexicalHandler() {
/*  72 */     return this.lexical;
/*     */   }
/*     */   
/*     */   public void setLexicalHandler(LexicalHandler lexical) {
/*  76 */     this.lexical = lexical;
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
/*     */ 
/*     */   
/*     */   public int getIndentStep() {
/*  96 */     return this.indentStep.length();
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
/*     */   public void setIndentStep(int indentStep) {
/* 112 */     StringBuilder s = new StringBuilder();
/* 113 */     while (indentStep > 0) { s.append(' '); indentStep--; }
/* 114 */      setIndentStep(s.toString());
/*     */   }
/*     */   
/*     */   public void setIndentStep(String s) {
/* 118 */     this.indentStep = s;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/* 149 */     this.stateStack.push(SEEN_ELEMENT);
/* 150 */     this.state = SEEN_NOTHING;
/* 151 */     if (this.depth > 0) {
/* 152 */       writeNewLine();
/*     */     }
/* 154 */     doIndent();
/* 155 */     super.startElement(uri, localName, qName, atts);
/* 156 */     this.depth++;
/*     */   }
/*     */   
/*     */   private void writeNewLine() throws SAXException {
/* 160 */     super.characters(NEWLINE, 0, NEWLINE.length);
/*     */   }
/*     */   
/* 163 */   private static final char[] NEWLINE = new char[] { '\n' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 187 */     this.depth--;
/* 188 */     if (this.state == SEEN_ELEMENT) {
/* 189 */       writeNewLine();
/* 190 */       doIndent();
/*     */     } 
/* 192 */     super.endElement(uri, localName, qName);
/* 193 */     this.state = this.stateStack.pop();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 243 */     this.state = SEEN_DATA;
/* 244 */     super.characters(ch, start, length);
/*     */   }
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 248 */     if (this.depth > 0) {
/* 249 */       writeNewLine();
/*     */     }
/* 251 */     doIndent();
/* 252 */     if (this.lexical != null)
/* 253 */       this.lexical.comment(ch, start, length); 
/*     */   }
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 257 */     if (this.lexical != null)
/* 258 */       this.lexical.startDTD(name, publicId, systemId); 
/*     */   }
/*     */   
/*     */   public void endDTD() throws SAXException {
/* 262 */     if (this.lexical != null)
/* 263 */       this.lexical.endDTD(); 
/*     */   }
/*     */   
/*     */   public void startEntity(String name) throws SAXException {
/* 267 */     if (this.lexical != null)
/* 268 */       this.lexical.startEntity(name); 
/*     */   }
/*     */   
/*     */   public void endEntity(String name) throws SAXException {
/* 272 */     if (this.lexical != null)
/* 273 */       this.lexical.endEntity(name); 
/*     */   }
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 277 */     if (this.lexical != null)
/* 278 */       this.lexical.startCDATA(); 
/*     */   }
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 282 */     if (this.lexical != null) {
/* 283 */       this.lexical.endCDATA();
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
/*     */   
/*     */   private void doIndent() throws SAXException {
/* 301 */     if (this.depth > 0) {
/* 302 */       char[] ch = this.indentStep.toCharArray();
/* 303 */       for (int i = 0; i < this.depth; i++) {
/* 304 */         characters(ch, 0, ch.length);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 313 */   private static final Object SEEN_NOTHING = new Object();
/* 314 */   private static final Object SEEN_ELEMENT = new Object();
/* 315 */   private static final Object SEEN_DATA = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 322 */   private Object state = SEEN_NOTHING;
/* 323 */   private Stack<Object> stateStack = new Stack();
/*     */   
/* 325 */   private String indentStep = "";
/* 326 */   private int depth = 0;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\IndentingXMLFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */