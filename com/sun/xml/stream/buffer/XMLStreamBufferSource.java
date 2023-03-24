/*     */ package com.sun.xml.stream.buffer;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.sax.SAXBufferProcessor;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamBufferSource
/*     */   extends SAXSource
/*     */ {
/*     */   protected XMLStreamBuffer _buffer;
/*     */   protected SAXBufferProcessor _bufferProcessor;
/*     */   
/*     */   public XMLStreamBufferSource(XMLStreamBuffer buffer) {
/*  78 */     super(new InputSource(new ByteArrayInputStream(new byte[0])));
/*     */     
/*  80 */     setXMLStreamBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamBuffer getXMLStreamBuffer() {
/*  89 */     return this._buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) {
/*  98 */     if (buffer == null) {
/*  99 */       throw new NullPointerException("buffer cannot be null");
/*     */     }
/* 101 */     this._buffer = buffer;
/*     */     
/* 103 */     if (this._bufferProcessor != null) {
/* 104 */       this._bufferProcessor.setBuffer(this._buffer, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public XMLReader getXMLReader() {
/* 109 */     if (this._bufferProcessor == null) {
/* 110 */       this._bufferProcessor = new SAXBufferProcessor(this._buffer, false);
/* 111 */       setXMLReader((XMLReader)this._bufferProcessor);
/* 112 */     } else if (super.getXMLReader() == null) {
/* 113 */       setXMLReader((XMLReader)this._bufferProcessor);
/*     */     } 
/*     */     
/* 116 */     return (XMLReader)this._bufferProcessor;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\XMLStreamBufferSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */