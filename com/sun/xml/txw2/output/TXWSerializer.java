/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TXWSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   public final TypedXmlWriter txw;
/*     */   
/*     */   public TXWSerializer(TypedXmlWriter txw) {
/*  56 */     this.txw = txw;
/*     */   }
/*     */   
/*     */   public void startDocument() {
/*  60 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void endDocument() {
/*  64 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/*  68 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/*  72 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/*  76 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {
/*  80 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void endTag() {
/*  84 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/*  88 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/*  92 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void flush() {
/* 100 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\TXWSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */