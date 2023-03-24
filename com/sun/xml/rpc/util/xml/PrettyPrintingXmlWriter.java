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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrettyPrintingXmlWriter
/*     */ {
/*     */   private static final boolean shouldPrettyprint = true;
/*     */   private BufferedWriter out;
/*     */   private char quoteChar;
/*     */   private int depth;
/*     */   private boolean inStart;
/*     */   private boolean needNewline;
/*     */   private boolean writtenChars;
/*     */   private boolean inAttribute;
/*     */   private boolean inAttributeValue;
/*     */   
/*     */   private PrettyPrintingXmlWriter(OutputStreamWriter w, boolean declare) throws IOException {
/* 143 */     this.quoteChar = '"';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     this.depth = 0;
/* 233 */     this.inStart = false;
/* 234 */     this.needNewline = false;
/* 235 */     this.writtenChars = false;
/* 236 */     this.inAttribute = false;
/* 237 */     this.inAttributeValue = false; this.out = new BufferedWriter(w, 1024); String enc = w.getEncoding(); if (enc.equals("UTF8")) { enc = "UTF-8"; } else if (enc.equals("ASCII")) { enc = "US-ASCII"; }  if (declare) { this.out.write("<?xml version=\"1.0\" encoding=\"" + enc + "\"?>"); this.out.newLine(); this.needNewline = true; }
/*     */   
/*     */   }
/*     */   public PrettyPrintingXmlWriter(OutputStream out, String enc, boolean declare) throws UnsupportedEncodingException, IOException { this(new OutputStreamWriter(out, enc), declare); }
/*     */   public PrettyPrintingXmlWriter(OutputStream out, String enc) throws UnsupportedEncodingException, IOException { this(new OutputStreamWriter(out, enc), true); }
/*     */   public PrettyPrintingXmlWriter(OutputStream out) throws IOException { this(new OutputStreamWriter(out, "UTF-8"), true); }
/*     */   public void setQuote(char quote) { if (quote != '"' && quote != '\'')
/*     */       throw new IllegalArgumentException("Illegal quote character: " + quote);  this.quoteChar = quote; } private void quote(char c) throws IOException { switch (c) { case '&':
/*     */         this.out.write("&amp;"); return;
/*     */       case '<':
/*     */         this.out.write("&lt;"); return;
/*     */       case '>':
/*     */         this.out.write("&gt;"); return; }
/* 250 */      this.out.write(c); } public void doctype(String root, String dtd) throws IOException { if (this.needNewline)
/* 251 */       this.out.newLine(); 
/* 252 */     this.needNewline = true;
/* 253 */     this.out.write("<!DOCTYPE " + root + " SYSTEM " + this.quoteChar);
/* 254 */     quote(dtd);
/* 255 */     this.out.write(this.quoteChar + ">");
/*     */     
/* 257 */     this.out.newLine(); }
/*     */   private void aquote(char c) throws IOException { switch (c) { case '\'': if (this.quoteChar == c) { this.out.write("&apos;"); } else { this.out.write(c); }  return;case '"': if (this.quoteChar == c) { this.out.write("&quot;"); } else { this.out.write(c); }  return; }  quote(c); }
/*     */   private void nonQuote(char c) throws IOException { this.out.write(c); }
/*     */   private void quote(String s) throws IOException { for (int i = 0; i < s.length(); i++) quote(s.charAt(i));  }
/* 261 */   private void nonQuote(String s) throws IOException { for (int i = 0; i < s.length(); i++) nonQuote(s.charAt(i));  } private void aquote(String s) throws IOException { for (int i = 0; i < s.length(); i++) aquote(s.charAt(i));  } private void indent(int depth) throws IOException { for (int i = 0; i < depth; i++) this.out.write("  ");  } private void start0(String name) throws IOException { finishStart();
/* 262 */     if (!this.writtenChars) {
/* 263 */       this.needNewline = true;
/* 264 */       indent(this.depth);
/*     */     } 
/* 266 */     this.out.write(60);
/* 267 */     this.out.write(name);
/* 268 */     this.inStart = true;
/* 269 */     this.writtenChars = false;
/* 270 */     this.depth++; }
/*     */ 
/*     */   
/*     */   private void start1(String name) throws IOException {
/* 274 */     finishStart();
/* 275 */     if (!this.writtenChars) {
/* 276 */       if (this.needNewline)
/* 277 */         this.out.newLine(); 
/* 278 */       this.needNewline = true;
/* 279 */       indent(this.depth);
/*     */     } 
/* 281 */     this.out.write(60);
/* 282 */     this.out.write(name);
/* 283 */     this.inStart = true;
/* 284 */     this.writtenChars = false;
/* 285 */     this.depth++;
/*     */   }
/*     */   
/*     */   private void finishStart() throws IOException {
/* 289 */     if (this.inStart) {
/* 290 */       if (this.inAttribute)
/* 291 */         this.out.write(this.quoteChar); 
/* 292 */       this.out.write(62);
/* 293 */       this.inStart = false;
/* 294 */       this.inAttribute = false;
/* 295 */       this.inAttributeValue = false;
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
/* 308 */     start1(name);
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
/* 326 */     attributeName(name);
/* 327 */     attributeValue(value);
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
/* 346 */     attributeName(name);
/* 347 */     attributeValueUnquoted(value);
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
/* 368 */     attributeName(prefix, name);
/* 369 */     attributeValue(value);
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
/* 390 */     attributeName(prefix, name);
/* 391 */     attributeValueUnquoted(value);
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
/* 408 */     if (!this.inStart)
/* 409 */       throw new IllegalStateException(); 
/* 410 */     if (this.inAttribute) {
/* 411 */       this.out.write(this.quoteChar);
/* 412 */       this.inAttribute = false;
/* 413 */       this.inAttributeValue = false;
/*     */     } 
/* 415 */     this.out.write(32);
/* 416 */     this.out.write(name);
/* 417 */     this.out.write(61);
/* 418 */     this.out.write(this.quoteChar);
/* 419 */     this.inAttribute = true;
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
/* 437 */     if (!this.inStart)
/* 438 */       throw new IllegalStateException(); 
/* 439 */     if (this.inAttribute) {
/* 440 */       this.out.write(this.quoteChar);
/* 441 */       this.inAttribute = false;
/* 442 */       this.inAttributeValue = false;
/*     */     } 
/* 444 */     this.out.write(32);
/* 445 */     this.out.write(prefix);
/* 446 */     this.out.write(58);
/* 447 */     this.out.write(name);
/* 448 */     this.out.write(61);
/* 449 */     this.out.write(this.quoteChar);
/* 450 */     this.inAttribute = true;
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
/* 463 */     if (!this.inAttribute || this.inAttributeValue)
/* 464 */       throw new IllegalStateException(); 
/* 465 */     aquote(value);
/* 466 */     this.out.write(this.quoteChar);
/* 467 */     this.inAttribute = false;
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
/* 480 */     if (!this.inAttribute || this.inAttributeValue)
/* 481 */       throw new IllegalStateException(); 
/* 482 */     this.out.write(value, 0, value.length());
/* 483 */     this.out.write(this.quoteChar);
/* 484 */     this.inAttribute = false;
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
/* 499 */     if (!this.inAttribute)
/* 500 */       throw new IllegalStateException(); 
/* 501 */     if (this.inAttributeValue)
/* 502 */       this.out.write(32); 
/* 503 */     aquote(token);
/* 504 */     this.inAttributeValue = true;
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
/* 516 */     if (this.inStart) {
/* 517 */       if (this.inAttribute)
/* 518 */         this.out.write(this.quoteChar); 
/* 519 */       this.out.write("/>");
/* 520 */       this.inStart = false;
/* 521 */       this.inAttribute = false;
/* 522 */       this.inAttributeValue = false;
/*     */     } else {
/* 524 */       this.out.write("</");
/* 525 */       this.out.write(name);
/* 526 */       this.out.write(62);
/*     */     } 
/* 528 */     this.depth--;
/* 529 */     this.writtenChars = false;
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
/* 541 */     finishStart();
/* 542 */     quote(chars);
/* 543 */     this.writtenChars = true;
/*     */   }
/*     */   
/*     */   public void chars(CDATA chars) throws IOException {
/* 547 */     finishStart();
/* 548 */     nonQuote(chars.getText());
/* 549 */     this.writtenChars = true;
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
/* 561 */     finishStart();
/* 562 */     this.out.write(chars, 0, chars.length());
/* 563 */     this.writtenChars = true;
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
/* 578 */     finishStart();
/* 579 */     this.out.write(buf, off, len);
/* 580 */     this.writtenChars = true;
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
/* 599 */     start1(name);
/* 600 */     if (chars != null && chars.length() != 0)
/* 601 */       chars(chars); 
/* 602 */     end(name);
/*     */   }
/*     */   
/*     */   public void inlineLeaf(String name, String chars) throws IOException {
/* 606 */     start0(name);
/* 607 */     if (chars != null && chars.length() != 0)
/* 608 */       chars(chars); 
/* 609 */     end(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void leaf(String name) throws IOException {
/* 618 */     leaf(name, null);
/*     */   }
/*     */   
/*     */   public void inlineLeaf(String name) throws IOException {
/* 622 */     inlineLeaf(name, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 632 */     if (this.depth != 0) {
/* 633 */       throw new IllegalStateException("Nonzero depth");
/*     */     }
/* 635 */     this.out.newLine();
/* 636 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 646 */     flush();
/* 647 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\PrettyPrintingXmlWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */