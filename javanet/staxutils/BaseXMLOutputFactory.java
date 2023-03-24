/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseXMLOutputFactory
/*     */   extends XMLOutputFactory
/*     */ {
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream stream, String encoding) throws XMLStreamException {
/*     */     try {
/*  62 */       return createXMLEventWriter(new OutputStreamWriter(stream, encoding));
/*     */     }
/*  64 */     catch (UnsupportedEncodingException e) {
/*     */       
/*  66 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream stream) throws XMLStreamException {
/*  75 */     return createXMLEventWriter(new OutputStreamWriter(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException {
/*     */     try {
/*  84 */       return createXMLStreamWriter(new OutputStreamWriter(stream, encoding));
/*     */     
/*     */     }
/*  87 */     catch (UnsupportedEncodingException e) {
/*     */       
/*  89 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException {
/*  98 */     return createXMLStreamWriter(new OutputStreamWriter(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
/* 105 */     return createXMLEventWriter(createXMLStreamWriter(result));
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
/*     */   public XMLEventWriter createXMLEventWriter(XMLStreamWriter writer) {
/* 119 */     return new XMLStreamEventWriter(writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Writer stream) throws XMLStreamException {
/* 126 */     return createXMLEventWriter(createXMLStreamWriter(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
/* 134 */     throw new UnsupportedOperationException("TrAX result not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 140 */     throw new IllegalArgumentException(name + " property isn't supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/* 153 */     throw new IllegalArgumentException(name + " property isn't supported");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\BaseXMLOutputFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */