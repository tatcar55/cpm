/*     */ package com.sun.xml.stream.buffer;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.sax.SAXBufferCreator;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamBufferResult
/*     */   extends SAXResult
/*     */ {
/*     */   protected MutableXMLStreamBuffer _buffer;
/*     */   protected SAXBufferCreator _bufferCreator;
/*     */   
/*     */   public XMLStreamBufferResult() {
/*  81 */     setXMLStreamBuffer(new MutableXMLStreamBuffer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamBufferResult(MutableXMLStreamBuffer buffer) {
/*  90 */     setXMLStreamBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableXMLStreamBuffer getXMLStreamBuffer() {
/*  99 */     return this._buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(MutableXMLStreamBuffer buffer) {
/* 108 */     if (buffer == null) {
/* 109 */       throw new NullPointerException("buffer cannot be null");
/*     */     }
/* 111 */     this._buffer = buffer;
/* 112 */     setSystemId(this._buffer.getSystemId());
/*     */     
/* 114 */     if (this._bufferCreator != null) {
/* 115 */       this._bufferCreator.setXMLStreamBuffer(this._buffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public ContentHandler getHandler() {
/* 120 */     if (this._bufferCreator == null) {
/* 121 */       this._bufferCreator = new SAXBufferCreator(this._buffer);
/* 122 */       setHandler((ContentHandler)this._bufferCreator);
/* 123 */     } else if (super.getHandler() == null) {
/* 124 */       setHandler((ContentHandler)this._bufferCreator);
/*     */     } 
/*     */     
/* 127 */     return (ContentHandler)this._bufferCreator;
/*     */   }
/*     */   
/*     */   public LexicalHandler getLexicalHandler() {
/* 131 */     return (LexicalHandler)getHandler();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\XMLStreamBufferResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */