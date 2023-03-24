/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.xml.stream.EventFilter;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLReporter;
/*     */ import javax.xml.stream.XMLResolver;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.util.XMLEventAllocator;
/*     */ import javax.xml.transform.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseXMLInputFactory
/*     */   extends XMLInputFactory
/*     */ {
/*     */   protected XMLEventAllocator eventAllocator;
/*     */   protected XMLReporter xmlReporter;
/*     */   protected XMLResolver xmlResolver;
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/*  70 */     throw new IllegalArgumentException(name + " property not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/*  83 */     throw new IllegalArgumentException(name + " property not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventAllocator getEventAllocator() {
/*  89 */     return this.eventAllocator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEventAllocator(XMLEventAllocator eventAllocator) {
/*  95 */     this.eventAllocator = eventAllocator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLReporter getXMLReporter() {
/* 101 */     return this.xmlReporter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLReporter(XMLReporter xmlReporter) {
/* 107 */     this.xmlReporter = xmlReporter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLResolver getXMLResolver() {
/* 113 */     return this.xmlResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLResolver(XMLResolver xmlResolver) {
/* 119 */     this.xmlResolver = xmlResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(InputStream stream, String encoding) throws XMLStreamException {
/*     */     try {
/* 128 */       if (encoding != null)
/*     */       {
/* 130 */         return createXMLEventReader(new InputStreamReader(stream, encoding), encoding);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 135 */       return createXMLEventReader(new InputStreamReader(stream));
/*     */ 
/*     */     
/*     */     }
/* 139 */     catch (UnsupportedEncodingException e) {
/*     */       
/* 141 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(InputStream stream) throws XMLStreamException {
/* 150 */     return createXMLEventReader(new InputStreamReader(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(String systemId, InputStream stream) throws XMLStreamException {
/* 157 */     return createXMLEventReader(systemId, new InputStreamReader(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(XMLStreamReader reader) throws XMLStreamException {
/* 164 */     return new XMLStreamEventReader(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(InputStream stream, String encoding) throws XMLStreamException {
/*     */     try {
/* 173 */       if (encoding != null)
/*     */       {
/* 175 */         return createXMLStreamReader(new InputStreamReader(stream, encoding), encoding);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 180 */       return createXMLStreamReader(new InputStreamReader(stream));
/*     */ 
/*     */     
/*     */     }
/* 184 */     catch (UnsupportedEncodingException e) {
/*     */       
/* 186 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(InputStream stream) throws XMLStreamException {
/* 195 */     return createXMLStreamReader(new InputStreamReader(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(String systemId, InputStream stream) throws XMLStreamException {
/* 202 */     return createXMLStreamReader(systemId, new InputStreamReader(stream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
/* 209 */     return createXMLEventReader(createXMLStreamReader(reader));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(Reader reader, String encoding) throws XMLStreamException {
/* 216 */     return createXMLEventReader(createXMLStreamReader(reader, encoding));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
/* 223 */     return createXMLEventReader(createXMLStreamReader(source));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
/* 230 */     return createXMLEventReader(createXMLStreamReader(systemId, reader));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(String systemId, Reader reader, String encoding) throws XMLStreamException {
/* 237 */     return createXMLEventReader(createXMLStreamReader(systemId, reader, encoding));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(Source source) throws XMLStreamException {
/* 246 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(Reader reader) throws XMLStreamException {
/* 253 */     return createXMLStreamReader(null, reader, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(Reader reader, String encoding) throws XMLStreamException {
/* 260 */     return createXMLStreamReader(null, reader, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(String systemId, Reader reader) throws XMLStreamException {
/* 267 */     String encoding = null;
/* 268 */     if (reader instanceof InputStreamReader)
/*     */     {
/* 270 */       encoding = ((InputStreamReader)reader).getEncoding();
/*     */     }
/*     */ 
/*     */     
/* 274 */     return createXMLStreamReader(systemId, reader, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
/* 282 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) throws XMLStreamException {
/* 290 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public abstract XMLStreamReader createXMLStreamReader(String paramString1, Reader paramReader, String paramString2) throws XMLStreamException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\BaseXMLInputFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */