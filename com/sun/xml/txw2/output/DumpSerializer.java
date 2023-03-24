/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DumpSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final PrintStream out;
/*     */   
/*     */   public DumpSerializer(PrintStream out) {
/*  56 */     this.out = out;
/*     */   }
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/*  60 */     this.out.println('<' + prefix + ':' + localName);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/*  64 */     this.out.println('@' + prefix + ':' + localName + '=' + value);
/*     */   }
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/*  68 */     this.out.println("xmlns:" + prefix + '=' + uri);
/*     */   }
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {
/*  72 */     this.out.println('>');
/*     */   }
/*     */   
/*     */   public void endTag() {
/*  76 */     this.out.println("</  >");
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/*  80 */     this.out.println(text);
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/*  84 */     this.out.println("<![CDATA[");
/*  85 */     this.out.println(text);
/*  86 */     this.out.println("]]>");
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/*  90 */     this.out.println("<!--");
/*  91 */     this.out.println(comment);
/*  92 */     this.out.println("-->");
/*     */   }
/*     */   
/*     */   public void startDocument() {
/*  96 */     this.out.println("<?xml?>");
/*     */   }
/*     */   
/*     */   public void endDocument() {
/* 100 */     this.out.println("done");
/*     */   }
/*     */   
/*     */   public void flush() {
/* 104 */     this.out.println("flush");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\DumpSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */