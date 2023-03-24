/*     */ package javanet.staxutils.helpers;
/*     */ 
/*     */ import java.io.OutputStream;
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
/*     */ public abstract class FilterXMLOutputFactory
/*     */   extends XMLOutputFactory
/*     */ {
/*     */   protected XMLOutputFactory source;
/*     */   
/*     */   public FilterXMLOutputFactory() {
/*  48 */     this(XMLOutputFactory.newInstance());
/*     */   }
/*     */   
/*     */   public FilterXMLOutputFactory(XMLOutputFactory source) {
/*  52 */     this.source = source;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract XMLEventWriter filter(XMLEventWriter paramXMLEventWriter);
/*     */ 
/*     */   
/*     */   protected abstract XMLStreamWriter filter(XMLStreamWriter paramXMLStreamWriter);
/*     */ 
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/*  64 */     return this.source.isPropertySupported(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/*  69 */     this.source.setProperty(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/*  74 */     return this.source.getProperty(name);
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
/*  78 */     return filter(this.source.createXMLEventWriter(result));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Writer writer) throws XMLStreamException {
/*  82 */     return filter(this.source.createXMLEventWriter(writer));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream stream) throws XMLStreamException {
/*  86 */     return filter(this.source.createXMLEventWriter(stream));
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream stream, String encoding) throws XMLStreamException {
/*  91 */     return filter(this.source.createXMLEventWriter(stream, encoding));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
/*  95 */     return filter(this.source.createXMLStreamWriter(result));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Writer writer) throws XMLStreamException {
/*  99 */     return filter(this.source.createXMLStreamWriter(writer));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException {
/* 103 */     return filter(this.source.createXMLStreamWriter(stream));
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException {
/* 108 */     return filter(this.source.createXMLStreamWriter(stream, encoding));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 112 */     return hashCode(this.source);
/*     */   }
/*     */   
/*     */   protected static int hashCode(Object o) {
/* 116 */     return (o == null) ? 0 : o.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 120 */     if (!(o instanceof FilterXMLOutputFactory))
/* 121 */       return false; 
/* 122 */     FilterXMLOutputFactory that = (FilterXMLOutputFactory)o;
/* 123 */     return equals(this.source, that.source);
/*     */   }
/*     */   
/*     */   protected static boolean equals(Object x, Object y) {
/* 127 */     return (x == null) ? ((y == null)) : x.equals(y);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\FilterXMLOutputFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */