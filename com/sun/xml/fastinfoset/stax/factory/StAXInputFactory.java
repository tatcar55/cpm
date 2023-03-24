/*     */ package com.sun.xml.fastinfoset.stax.factory;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import com.sun.xml.fastinfoset.stax.StAXManager;
/*     */ import com.sun.xml.fastinfoset.stax.events.StAXEventReader;
/*     */ import com.sun.xml.fastinfoset.stax.events.StAXFilteredEvent;
/*     */ import com.sun.xml.fastinfoset.stax.util.StAXFilteredParser;
/*     */ import com.sun.xml.fastinfoset.tools.XML_SAX_FI;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
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
/*     */ public class StAXInputFactory
/*     */   extends XMLInputFactory
/*     */ {
/*  41 */   private StAXManager _manager = new StAXManager(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLInputFactory newInstance() {
/*  47 */     return XMLInputFactory.newInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(Reader xmlfile) throws XMLStreamException {
/*  56 */     return getXMLStreamReader(xmlfile);
/*     */   }
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(InputStream s) throws XMLStreamException {
/*  60 */     return (XMLStreamReader)new StAXDocumentParser(s, this._manager);
/*     */   }
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(String systemId, Reader xmlfile) throws XMLStreamException {
/*  64 */     return getXMLStreamReader(xmlfile);
/*     */   }
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(Source source) throws XMLStreamException {
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(String systemId, InputStream inputstream) throws XMLStreamException {
/*  72 */     return createXMLStreamReader(inputstream);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader createXMLStreamReader(InputStream inputstream, String encoding) throws XMLStreamException {
/*  77 */     return createXMLStreamReader(inputstream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   XMLStreamReader getXMLStreamReader(String systemId, InputStream inputstream, String encoding) throws XMLStreamException {
/*  83 */     return createXMLStreamReader(inputstream);
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
/*     */   XMLStreamReader getXMLStreamReader(Reader xmlfile) throws XMLStreamException {
/*  95 */     ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
/*  96 */     BufferedOutputStream bufferedStream = new BufferedOutputStream(byteStream);
/*  97 */     StAXDocumentParser sr = null;
/*     */     try {
/*  99 */       XML_SAX_FI convertor = new XML_SAX_FI();
/* 100 */       convertor.convert(xmlfile, bufferedStream);
/*     */       
/* 102 */       ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteStream.toByteArray());
/* 103 */       InputStream document = new BufferedInputStream(byteInputStream);
/* 104 */       sr = new StAXDocumentParser();
/* 105 */       sr.setInputStream(document);
/* 106 */       sr.setManager(this._manager);
/* 107 */       return (XMLStreamReader)sr;
/*     */     }
/* 109 */     catch (Exception e) {
/* 110 */       return null;
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
/*     */   public XMLEventReader createXMLEventReader(InputStream inputstream) throws XMLStreamException {
/* 122 */     return (XMLEventReader)new StAXEventReader(createXMLStreamReader(inputstream));
/*     */   }
/*     */   
/*     */   public XMLEventReader createXMLEventReader(Reader reader) throws XMLStreamException {
/* 126 */     return (XMLEventReader)new StAXEventReader(createXMLStreamReader(reader));
/*     */   }
/*     */   
/*     */   public XMLEventReader createXMLEventReader(Source source) throws XMLStreamException {
/* 130 */     return (XMLEventReader)new StAXEventReader(createXMLStreamReader(source));
/*     */   }
/*     */   
/*     */   public XMLEventReader createXMLEventReader(String systemId, InputStream inputstream) throws XMLStreamException {
/* 134 */     return (XMLEventReader)new StAXEventReader(createXMLStreamReader(systemId, inputstream));
/*     */   }
/*     */   
/*     */   public XMLEventReader createXMLEventReader(InputStream stream, String encoding) throws XMLStreamException {
/* 138 */     return (XMLEventReader)new StAXEventReader(createXMLStreamReader(stream, encoding));
/*     */   }
/*     */   
/*     */   public XMLEventReader createXMLEventReader(String systemId, Reader reader) throws XMLStreamException {
/* 142 */     return (XMLEventReader)new StAXEventReader(createXMLStreamReader(systemId, reader));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createXMLEventReader(XMLStreamReader streamReader) throws XMLStreamException {
/* 153 */     return (XMLEventReader)new StAXEventReader(streamReader);
/*     */   }
/*     */   
/*     */   public XMLEventAllocator getEventAllocator() {
/* 157 */     return (XMLEventAllocator)getProperty("javax.xml.stream.allocator");
/*     */   }
/*     */   
/*     */   public XMLReporter getXMLReporter() {
/* 161 */     return (XMLReporter)this._manager.getProperty("javax.xml.stream.reporter");
/*     */   }
/*     */   
/*     */   public XMLResolver getXMLResolver() {
/* 165 */     Object object = this._manager.getProperty("javax.xml.stream.resolver");
/* 166 */     return (XMLResolver)object;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXMLReporter(XMLReporter xmlreporter) {
/* 171 */     this._manager.setProperty("javax.xml.stream.reporter", xmlreporter);
/*     */   }
/*     */   
/*     */   public void setXMLResolver(XMLResolver xmlresolver) {
/* 175 */     this._manager.setProperty("javax.xml.stream.resolver", xmlresolver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter) throws XMLStreamException {
/* 184 */     return (XMLEventReader)new StAXFilteredEvent(reader, filter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter) throws XMLStreamException {
/* 194 */     if (reader != null && filter != null) {
/* 195 */       return (XMLStreamReader)new StAXFilteredParser(reader, filter);
/*     */     }
/* 197 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 208 */     if (name == null) {
/* 209 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.nullPropertyName"));
/*     */     }
/* 211 */     if (this._manager.containsProperty(name))
/* 212 */       return this._manager.getProperty(name); 
/* 213 */     throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[] { name }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/* 222 */     if (name == null) {
/* 223 */       return false;
/*     */     }
/* 225 */     return this._manager.containsProperty(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEventAllocator(XMLEventAllocator allocator) {
/* 232 */     this._manager.setProperty("javax.xml.stream.allocator", allocator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/* 243 */     this._manager.setProperty(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\factory\StAXInputFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */