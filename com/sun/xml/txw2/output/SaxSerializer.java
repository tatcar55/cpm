/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import com.sun.xml.txw2.TxwException;
/*     */ import java.util.Stack;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SaxSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final ContentHandler writer;
/*     */   private final LexicalHandler lexical;
/*     */   
/*     */   public SaxSerializer(ContentHandler handler) {
/*  63 */     this(handler, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SaxSerializer(ContentHandler handler, LexicalHandler lex) {
/*  74 */     this(handler, lex, true);
/*     */   }
/*     */   
/*     */   public SaxSerializer(ContentHandler handler, LexicalHandler lex, boolean indenting) {
/*  78 */     if (!indenting) {
/*  79 */       this.writer = handler;
/*  80 */       this.lexical = lex;
/*     */     } else {
/*  82 */       IndentingXMLFilter indenter = new IndentingXMLFilter(handler, lex);
/*  83 */       this.writer = indenter;
/*  84 */       this.lexical = indenter;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SaxSerializer(SAXResult result) {
/*  89 */     this(result.getHandler(), result.getLexicalHandler());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() {
/*     */     try {
/*  97 */       this.writer.startDocument();
/*  98 */     } catch (SAXException e) {
/*  99 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   private final Stack<String> prefixBindings = new Stack<String>();
/*     */ 
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/* 109 */     if (prefix == null) {
/* 110 */       prefix = "";
/*     */     }
/*     */     
/* 113 */     if (prefix.equals("xml")) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     this.prefixBindings.add(uri);
/* 118 */     this.prefixBindings.add(prefix);
/*     */   }
/*     */ 
/*     */   
/* 122 */   private final Stack<String> elementBindings = new Stack<String>();
/*     */ 
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/* 126 */     this.elementBindings.add(getQName(prefix, localName));
/* 127 */     this.elementBindings.add(localName);
/* 128 */     this.elementBindings.add(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private final AttributesImpl attrs = new AttributesImpl();
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/* 137 */     this.attrs.addAttribute(uri, localName, getQName(prefix, localName), "CDATA", value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {
/*     */     try {
/* 146 */       while (this.prefixBindings.size() != 0) {
/* 147 */         this.writer.startPrefixMapping(this.prefixBindings.pop(), this.prefixBindings.pop());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 152 */       this.writer.startElement(uri, localName, getQName(prefix, localName), this.attrs);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       this.attrs.clear();
/* 158 */     } catch (SAXException e) {
/* 159 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endTag() {
/*     */     try {
/* 165 */       this.writer.endElement(this.elementBindings.pop(), this.elementBindings.pop(), this.elementBindings.pop());
/*     */ 
/*     */     
/*     */     }
/* 169 */     catch (SAXException e) {
/* 170 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/*     */     try {
/* 176 */       this.writer.characters(text.toString().toCharArray(), 0, text.length());
/* 177 */     } catch (SAXException e) {
/* 178 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/* 183 */     if (this.lexical == null) {
/* 184 */       throw new UnsupportedOperationException("LexicalHandler is needed to write PCDATA");
/*     */     }
/*     */     try {
/* 187 */       this.lexical.startCDATA();
/* 188 */       text(text);
/* 189 */       this.lexical.endCDATA();
/* 190 */     } catch (SAXException e) {
/* 191 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/*     */     try {
/* 197 */       if (this.lexical == null) {
/* 198 */         throw new UnsupportedOperationException("LexicalHandler is needed to write comments");
/*     */       }
/* 200 */       this.lexical.comment(comment.toString().toCharArray(), 0, comment.length());
/* 201 */     } catch (SAXException e) {
/* 202 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() {
/*     */     try {
/* 208 */       this.writer.endDocument();
/* 209 */     } catch (SAXException e) {
/* 210 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */   
/*     */   private static String getQName(String prefix, String localName) {
/*     */     String qName;
/* 221 */     if (prefix == null || prefix.length() == 0) {
/* 222 */       qName = localName;
/*     */     } else {
/* 224 */       qName = prefix + ':' + localName;
/*     */     } 
/* 226 */     return qName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\SaxSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */