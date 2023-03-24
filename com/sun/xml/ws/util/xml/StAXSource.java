/*     */ package com.sun.xml.ws.util.xml;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.SAXParseException2;
/*     */ import com.sun.istack.XMLStreamReaderToContentHandler;
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
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private final XMLStreamReaderToContentHandler reader;
/*     */   private final XMLStreamReader staxReader;
/* 105 */   private XMLFilterImpl repeater = new XMLFilterImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private final XMLReader pseudoParser = new XMLReader() { private LexicalHandler lexicalHandler; private EntityResolver entityResolver;
/*     */       public boolean getFeature(String name) throws SAXNotRecognizedException {
/* 112 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       private DTDHandler dtdHandler; private ErrorHandler errorHandler;
/*     */       
/*     */       public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/* 117 */         if (!name.equals("http://xml.org/sax/features/namespaces") || !value)
/*     */         {
/* 119 */           if (!name.equals("http://xml.org/sax/features/namespace-prefixes") || value)
/*     */           {
/*     */             
/* 122 */             throw new SAXNotRecognizedException(name); } 
/*     */         }
/*     */       }
/*     */       
/*     */       public Object getProperty(String name) throws SAXNotRecognizedException {
/* 127 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 128 */           return this.lexicalHandler;
/*     */         }
/* 130 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       
/*     */       public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/* 134 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 135 */           this.lexicalHandler = (LexicalHandler)value;
/*     */           return;
/*     */         } 
/* 138 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setEntityResolver(EntityResolver resolver) {
/* 146 */         this.entityResolver = resolver;
/*     */       }
/*     */       public EntityResolver getEntityResolver() {
/* 149 */         return this.entityResolver;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setDTDHandler(DTDHandler handler) {
/* 154 */         this.dtdHandler = handler;
/*     */       }
/*     */       public DTDHandler getDTDHandler() {
/* 157 */         return this.dtdHandler;
/*     */       }
/*     */       
/*     */       public void setContentHandler(ContentHandler handler) {
/* 161 */         StAXSource.this.repeater.setContentHandler(handler);
/*     */       }
/*     */       public ContentHandler getContentHandler() {
/* 164 */         return StAXSource.this.repeater.getContentHandler();
/*     */       }
/*     */ 
/*     */       
/*     */       public void setErrorHandler(ErrorHandler handler) {
/* 169 */         this.errorHandler = handler;
/*     */       }
/*     */       public ErrorHandler getErrorHandler() {
/* 172 */         return this.errorHandler;
/*     */       }
/*     */       
/*     */       public void parse(InputSource input) throws SAXException {
/* 176 */         parse();
/*     */       }
/*     */       
/*     */       public void parse(String systemId) throws SAXException {
/* 180 */         parse();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void parse() throws SAXException {
/*     */         try {
/* 188 */           StAXSource.this.reader.bridge();
/* 189 */         } catch (XMLStreamException e) {
/*     */           
/* 191 */           SAXParseException2 sAXParseException2 = new SAXParseException2(e.getMessage(), null, null, (e.getLocation() == null) ? -1 : e.getLocation().getLineNumber(), (e.getLocation() == null) ? -1 : e.getLocation().getColumnNumber(), e);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 202 */           if (this.errorHandler != null) {
/* 203 */             this.errorHandler.fatalError((SAXParseException)sAXParseException2);
/*     */           }
/*     */ 
/*     */           
/* 207 */           throw sAXParseException2;
/*     */         } finally {
/*     */           
/*     */           try {
/* 211 */             StAXSource.this.staxReader.close();
/* 212 */           } catch (XMLStreamException xe) {}
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
/*     */   public StAXSource(XMLStreamReader reader, boolean eagerQuit) {
/* 229 */     this(reader, eagerQuit, new String[0]);
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
/*     */   public StAXSource(XMLStreamReader reader, boolean eagerQuit, @NotNull String[] inscope) {
/* 250 */     if (reader == null)
/* 251 */       throw new IllegalArgumentException(); 
/* 252 */     this.staxReader = reader;
/*     */     
/* 254 */     int eventType = reader.getEventType();
/* 255 */     if (eventType != 7 && eventType != 1)
/*     */     {
/* 257 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 260 */     this.reader = new XMLStreamReaderToContentHandler(reader, this.repeater, eagerQuit, false, inscope);
/*     */     
/* 262 */     setXMLReader(this.pseudoParser);
/*     */     
/* 264 */     setInputSource(new InputSource());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\StAXSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */