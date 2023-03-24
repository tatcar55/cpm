/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IndentingUTF8XmlOutput
/*     */   extends UTF8XmlOutput
/*     */ {
/*     */   private final Encoded indent8;
/*     */   private final int unitLen;
/*  77 */   private int depth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean seenText = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IndentingUTF8XmlOutput(OutputStream out, String indentStr, Encoded[] localNames, CharacterEscapeHandler escapeHandler) {
/*  88 */     super(out, localNames, escapeHandler);
/*     */     
/*  90 */     if (indentStr != null) {
/*  91 */       Encoded e = new Encoded(indentStr);
/*  92 */       this.indent8 = new Encoded();
/*  93 */       this.indent8.ensureSize(e.len * 8);
/*  94 */       this.unitLen = e.len;
/*  95 */       for (int i = 0; i < 8; i++)
/*  96 */         System.arraycopy(e.buf, 0, this.indent8.buf, this.unitLen * i, this.unitLen); 
/*     */     } else {
/*  98 */       this.indent8 = null;
/*  99 */       this.unitLen = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException {
/* 105 */     indentStartTag();
/* 106 */     super.beginStartTag(prefix, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException {
/* 111 */     indentStartTag();
/* 112 */     super.beginStartTag(name);
/*     */   }
/*     */   
/*     */   private void indentStartTag() throws IOException {
/* 116 */     closeStartTag();
/* 117 */     if (!this.seenText)
/* 118 */       printIndent(); 
/* 119 */     this.depth++;
/* 120 */     this.seenText = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(Name name) throws IOException {
/* 125 */     indentEndTag();
/* 126 */     super.endTag(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException {
/* 131 */     indentEndTag();
/* 132 */     super.endTag(prefix, localName);
/*     */   }
/*     */   
/*     */   private void indentEndTag() throws IOException {
/* 136 */     this.depth--;
/* 137 */     if (!this.closeStartTagPending && !this.seenText)
/* 138 */       printIndent(); 
/* 139 */     this.seenText = false;
/*     */   }
/*     */   
/*     */   private void printIndent() throws IOException {
/* 143 */     write(10);
/* 144 */     int i = this.depth % 8;
/*     */     
/* 146 */     write(this.indent8.buf, 0, i * this.unitLen);
/*     */     
/* 148 */     i >>= 3;
/*     */     
/* 150 */     for (; i > 0; i--) {
/* 151 */       this.indent8.write(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void text(String value, boolean needSP) throws IOException {
/* 156 */     this.seenText = true;
/* 157 */     super.text(value, needSP);
/*     */   }
/*     */ 
/*     */   
/*     */   public void text(Pcdata value, boolean needSP) throws IOException {
/* 162 */     this.seenText = true;
/* 163 */     super.text(value, needSP);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/* 168 */     write(10);
/* 169 */     super.endDocument(fragment);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\IndentingUTF8XmlOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */