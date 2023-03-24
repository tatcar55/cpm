/*     */ package com.sun.xml.rpc.util.xml;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlWriter
/*     */ {
/*     */   private static final boolean shouldPrettyprint = false;
/*     */   private BufferedWriter out;
/*     */   private char quoteChar;
/*     */   private int depth;
/*     */   private boolean inStart;
/*     */   private boolean needNewline;
/*     */   private boolean writtenChars;
/*     */   private boolean inAttribute;
/*     */   private boolean inAttributeValue;
/*     */   
/*     */   private XmlWriter(OutputStreamWriter w, boolean declare) throws IOException {
/* 140 */     this.quoteChar = '"';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     this.depth = 0;
/* 230 */     this.inStart = false;
/* 231 */     this.needNewline = false;
/* 232 */     this.writtenChars = false;
/* 233 */     this.inAttribute = false;
/* 234 */     this.inAttributeValue = false; this.out = new BufferedWriter(w, 1024); String enc = w.getEncoding(); if (enc.equals("UTF8")) { enc = "UTF-8"; }
/*     */     else if (enc.equals("ASCII")) { enc = "US-ASCII"; }
/*     */      if (declare) { this.out.write("<?xml version=\"1.0\" encoding=\"" + enc + "\"?>"); this.out.newLine(); this.needNewline = true; }
/*     */   
/*     */   } public XmlWriter(OutputStream out, String enc, boolean declare) throws UnsupportedEncodingException, IOException { this(new OutputStreamWriter(out, enc), declare); } public XmlWriter(OutputStream out, String enc) throws UnsupportedEncodingException, IOException { this(new OutputStreamWriter(out, enc), true); }
/*     */   public XmlWriter(OutputStream out) throws IOException { this(new OutputStreamWriter(out, "UTF-8"), true); }
/*     */   public void setQuote(char quote) { if (quote != '"' && quote != '\'')
/*     */       throw new IllegalArgumentException("Illegal quote character: " + quote);  this.quoteChar = quote; }
/*     */   private void quote(char c) throws IOException { switch (c) { case '&':
/*     */         this.out.write("&amp;"); return;
/*     */       case '<':
/*     */         this.out.write("&lt;"); return;
/*     */       case '>':
/*     */         this.out.write("&gt;"); return; }
/*     */      this.out.write(c); }
/* 249 */   public void doctype(String root, String dtd) throws IOException { this.needNewline = true;
/* 250 */     this.out.write("<!DOCTYPE " + root + " SYSTEM " + this.quoteChar);
/* 251 */     quote(dtd);
/* 252 */     this.out.write(this.quoteChar + ">"); }
/*     */   private void nonQuote(char c) throws IOException { this.out.write(c); }
/*     */   private void aquote(char c) throws IOException { switch (c) { case '\'': if (this.quoteChar == c) { this.out.write("&apos;"); } else { this.out.write(c); }  return;
/*     */       case '"':
/*     */         if (this.quoteChar == c) { this.out.write("&quot;"); } else { this.out.write(c); }  return; }  quote(c); }
/*     */   private void quote(String s) throws IOException { for (int i = 0; i < s.length(); i++)
/* 258 */       quote(s.charAt(i));  } private void start0(String name) throws IOException { finishStart();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     this.out.write(60);
/* 264 */     this.out.write(name);
/* 265 */     this.inStart = true;
/* 266 */     this.writtenChars = false;
/* 267 */     this.depth++; }
/*     */   private void nonQuote(String s) throws IOException { for (int i = 0; i < s.length(); i++) nonQuote(s.charAt(i));  }
/*     */   private void aquote(String s) throws IOException { for (int i = 0; i < s.length(); i++) aquote(s.charAt(i));  }
/*     */   private void indent(int depth) throws IOException { for (int i = 0; i < depth; i++)
/* 271 */       this.out.write("  ");  } private void start1(String name) throws IOException { finishStart();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     this.out.write(60);
/* 279 */     this.out.write(name);
/* 280 */     this.inStart = true;
/* 281 */     this.writtenChars = false;
/* 282 */     this.depth++; }
/*     */ 
/*     */   
/*     */   private void finishStart() throws IOException {
/* 286 */     if (this.inStart) {
/* 287 */       if (this.inAttribute)
/* 288 */         this.out.write(this.quoteChar); 
/* 289 */       this.out.write(62);
/* 290 */       this.inStart = false;
/* 291 */       this.inAttribute = false;
/* 292 */       this.inAttributeValue = false;
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
/*     */   public void start(String name) throws IOException {
/* 305 */     start1(name);
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
/*     */   public void attribute(String name, String value) throws IOException {
/* 323 */     attributeName(name);
/* 324 */     attributeValue(value);
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
/*     */   public void attributeUnquoted(String name, String value) throws IOException {
/* 343 */     attributeName(name);
/* 344 */     attributeValueUnquoted(value);
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
/*     */   public void attribute(String prefix, String name, String value) throws IOException {
/* 365 */     attributeName(prefix, name);
/* 366 */     attributeValue(value);
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
/*     */   public void attributeUnquoted(String prefix, String name, String value) throws IOException {
/* 387 */     attributeName(prefix, name);
/* 388 */     attributeValueUnquoted(value);
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
/*     */   public void attributeName(String name) throws IOException {
/* 405 */     if (!this.inStart)
/* 406 */       throw new IllegalStateException(); 
/* 407 */     if (this.inAttribute) {
/* 408 */       this.out.write(this.quoteChar);
/* 409 */       this.inAttribute = false;
/* 410 */       this.inAttributeValue = false;
/*     */     } 
/* 412 */     this.out.write(32);
/* 413 */     this.out.write(name);
/* 414 */     this.out.write(61);
/* 415 */     this.out.write(this.quoteChar);
/* 416 */     this.inAttribute = true;
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
/*     */   public void attributeName(String prefix, String name) throws IOException {
/* 434 */     if (!this.inStart)
/* 435 */       throw new IllegalStateException(); 
/* 436 */     if (this.inAttribute) {
/* 437 */       this.out.write(this.quoteChar);
/* 438 */       this.inAttribute = false;
/* 439 */       this.inAttributeValue = false;
/*     */     } 
/* 441 */     this.out.write(32);
/* 442 */     this.out.write(prefix);
/* 443 */     this.out.write(58);
/* 444 */     this.out.write(name);
/* 445 */     this.out.write(61);
/* 446 */     this.out.write(this.quoteChar);
/* 447 */     this.inAttribute = true;
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
/*     */   public void attributeValue(String value) throws IOException {
/* 460 */     if (!this.inAttribute || this.inAttributeValue)
/* 461 */       throw new IllegalStateException(); 
/* 462 */     aquote(value);
/* 463 */     this.out.write(this.quoteChar);
/* 464 */     this.inAttribute = false;
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
/*     */   public void attributeValueUnquoted(String value) throws IOException {
/* 477 */     if (!this.inAttribute || this.inAttributeValue)
/* 478 */       throw new IllegalStateException(); 
/* 479 */     this.out.write(value, 0, value.length());
/* 480 */     this.out.write(this.quoteChar);
/* 481 */     this.inAttribute = false;
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
/*     */   public void attributeValueToken(String token) throws IOException {
/* 496 */     if (!this.inAttribute)
/* 497 */       throw new IllegalStateException(); 
/* 498 */     if (this.inAttributeValue)
/* 499 */       this.out.write(32); 
/* 500 */     aquote(token);
/* 501 */     this.inAttributeValue = true;
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
/*     */   public void end(String name) throws IOException {
/* 513 */     if (this.inStart) {
/* 514 */       if (this.inAttribute)
/* 515 */         this.out.write(this.quoteChar); 
/* 516 */       this.out.write("/>");
/* 517 */       this.inStart = false;
/* 518 */       this.inAttribute = false;
/* 519 */       this.inAttributeValue = false;
/*     */     } else {
/* 521 */       this.out.write("</");
/* 522 */       this.out.write(name);
/* 523 */       this.out.write(62);
/*     */     } 
/* 525 */     this.depth--;
/* 526 */     this.writtenChars = false;
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
/*     */   public void chars(String chars) throws IOException {
/* 538 */     finishStart();
/* 539 */     quote(chars);
/* 540 */     this.writtenChars = true;
/*     */   }
/*     */   
/*     */   public void chars(CDATA chars) throws IOException {
/* 544 */     finishStart();
/* 545 */     nonQuote(chars.getText());
/* 546 */     this.writtenChars = true;
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
/*     */   public void charsUnquoted(String chars) throws IOException {
/* 558 */     finishStart();
/* 559 */     this.out.write(chars, 0, chars.length());
/* 560 */     this.writtenChars = true;
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
/*     */   public void charsUnquoted(char[] buf, int off, int len) throws IOException {
/* 575 */     finishStart();
/* 576 */     this.out.write(buf, off, len);
/* 577 */     this.writtenChars = true;
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
/*     */   public void leaf(String name, String chars) throws IOException {
/* 596 */     start1(name);
/* 597 */     if (chars != null && chars.length() != 0)
/* 598 */       chars(chars); 
/* 599 */     end(name);
/*     */   }
/*     */   
/*     */   public void inlineLeaf(String name, String chars) throws IOException {
/* 603 */     start0(name);
/* 604 */     if (chars != null && chars.length() != 0)
/* 605 */       chars(chars); 
/* 606 */     end(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void leaf(String name) throws IOException {
/* 615 */     leaf(name, null);
/*     */   }
/*     */   
/*     */   public void inlineLeaf(String name) throws IOException {
/* 619 */     inlineLeaf(name, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 629 */     if (this.depth != 0)
/*     */     {
/*     */       
/* 632 */       this.out.newLine(); } 
/* 633 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 643 */     flush();
/* 644 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\XmlWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */