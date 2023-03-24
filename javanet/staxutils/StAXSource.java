/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.helpers.XMLFilterImplEx;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXSource
/*     */   extends SAXSource
/*     */ {
/*     */   private final StAXReaderToContentHandler reader;
/*  94 */   private XMLFilterImplEx repeater = new XMLFilterImplEx();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final XMLReader pseudoParser = new XMLReader(this) { private EntityResolver entityResolver; private DTDHandler dtdHandler;
/*     */       public boolean getFeature(String name) throws SAXNotRecognizedException {
/* 101 */         if ("http://xml.org/sax/features/namespace-prefixes".equals(name))
/* 102 */           return this.this$0.repeater.getNamespacePrefixes(); 
/* 103 */         if ("http://xml.org/sax/features/external-general-entities".equals(name))
/* 104 */           return true; 
/* 105 */         if ("http://xml.org/sax/features/external-parameter-entities".equals(name)) {
/* 106 */           return true;
/*     */         }
/*     */         
/* 109 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       private ErrorHandler errorHandler; private final StAXSource this$0;
/*     */       public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/* 113 */         if ("http://xml.org/sax/features/namespace-prefixes".equals(name)) {
/* 114 */           this.this$0.repeater.setNamespacePrefixes(value);
/* 115 */         } else if (!"http://xml.org/sax/features/external-general-entities".equals(name)) {
/*     */           
/* 117 */           if (!"http://xml.org/sax/features/external-parameter-entities".equals(name))
/*     */           {
/*     */             
/* 120 */             throw new SAXNotRecognizedException(name); } 
/*     */         } 
/*     */       }
/*     */       
/*     */       public Object getProperty(String name) throws SAXNotRecognizedException {
/* 125 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 126 */           return this.this$0.repeater.getLexicalHandler();
/*     */         }
/*     */         
/* 129 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       
/*     */       public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/* 133 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 134 */           this.this$0.repeater.setLexicalHandler((LexicalHandler)value);
/*     */         } else {
/* 136 */           throw new SAXNotRecognizedException(name);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void setEntityResolver(EntityResolver resolver) {
/* 143 */         this.entityResolver = resolver;
/*     */       }
/*     */       public EntityResolver getEntityResolver() {
/* 146 */         return this.entityResolver;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setDTDHandler(DTDHandler handler) {
/* 151 */         this.dtdHandler = handler;
/*     */       }
/*     */       public DTDHandler getDTDHandler() {
/* 154 */         return this.dtdHandler;
/*     */       }
/*     */       
/*     */       public void setContentHandler(ContentHandler handler) {
/* 158 */         this.this$0.repeater.setContentHandler(handler);
/*     */       }
/*     */       public ContentHandler getContentHandler() {
/* 161 */         return this.this$0.repeater.getContentHandler();
/*     */       }
/*     */ 
/*     */       
/*     */       public void setErrorHandler(ErrorHandler handler) {
/* 166 */         this.errorHandler = handler;
/*     */       }
/*     */       public ErrorHandler getErrorHandler() {
/* 169 */         return this.errorHandler;
/*     */       }
/*     */       
/*     */       public void parse(InputSource input) throws SAXException {
/* 173 */         parse();
/*     */       }
/*     */       
/*     */       public void parse(String systemId) throws SAXException {
/* 177 */         parse();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void parse() throws SAXException {
/*     */         try {
/* 185 */           this.this$0.reader.bridge();
/* 186 */         } catch (XMLStreamException e) {
/*     */           
/* 188 */           SAXParseException se = new SAXParseException(e.getMessage(), null, null, e.getLocation().getLineNumber(), e.getLocation().getColumnNumber(), e);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 199 */           if (this.errorHandler != null) {
/* 200 */             this.errorHandler.fatalError(se);
/*     */           }
/*     */ 
/*     */           
/* 204 */           throw se;
/*     */         } 
/*     */       } }
/*     */   ;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXSource(XMLStreamReader reader) {
/* 224 */     if (reader == null) {
/* 225 */       throw new IllegalArgumentException();
/*     */     }
/* 227 */     int eventType = reader.getEventType();
/* 228 */     if (eventType != 7 && eventType != 1)
/*     */     {
/* 230 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 233 */     this.reader = new XMLStreamReaderToContentHandler(reader, this.repeater);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     setXMLReader(this.pseudoParser);
/*     */     
/* 240 */     setInputSource(new InputSource());
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
/*     */   public StAXSource(XMLEventReader reader) {
/* 257 */     if (reader == null) {
/* 258 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     this.reader = new XMLEventReaderToContentHandler(reader, this.repeater);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     setXMLReader(this.pseudoParser);
/*     */     
/* 271 */     setInputSource(new InputSource());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\StAXSource.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */