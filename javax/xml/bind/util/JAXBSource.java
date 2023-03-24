/*     */ package javax.xml.bind.util;
/*     */ 
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBSource
/*     */   extends SAXSource
/*     */ {
/*     */   private final Marshaller marshaller;
/*     */   private final Object contentObject;
/*     */   
/*     */   public JAXBSource(JAXBContext context, Object contentObject) throws JAXBException {
/* 124 */     this((context == null) ? assertionFailed(Messages.format("JAXBSource.NullContext")) : context.createMarshaller(), (contentObject == null) ? assertionFailed(Messages.format("JAXBSource.NullContent")) : contentObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBSource(Marshaller marshaller, Object contentObject) throws JAXBException {
/* 152 */     if (marshaller == null) {
/* 153 */       throw new JAXBException(Messages.format("JAXBSource.NullMarshaller"));
/*     */     }
/*     */     
/* 156 */     if (contentObject == null) {
/* 157 */       throw new JAXBException(Messages.format("JAXBSource.NullContent"));
/*     */     }
/*     */     
/* 160 */     this.marshaller = marshaller;
/* 161 */     this.contentObject = contentObject;
/*     */     
/* 163 */     setXMLReader(this.pseudoParser);
/*     */     
/* 165 */     setInputSource(new InputSource());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   private final XMLReader pseudoParser = new XMLReader() { private LexicalHandler lexicalHandler;
/*     */       public boolean getFeature(String name) throws SAXNotRecognizedException {
/* 176 */         if (name.equals("http://xml.org/sax/features/namespaces"))
/* 177 */           return true; 
/* 178 */         if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/* 179 */           return false; 
/* 180 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       private EntityResolver entityResolver; private DTDHandler dtdHandler;
/*     */       public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/* 184 */         if (name.equals("http://xml.org/sax/features/namespaces") && value)
/*     */           return; 
/* 186 */         if (name.equals("http://xml.org/sax/features/namespace-prefixes") && !value)
/*     */           return; 
/* 188 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       
/*     */       public Object getProperty(String name) throws SAXNotRecognizedException {
/* 192 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 193 */           return this.lexicalHandler;
/*     */         }
/* 195 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */       
/*     */       public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/* 199 */         if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 200 */           this.lexicalHandler = (LexicalHandler)value;
/*     */           return;
/*     */         } 
/* 203 */         throw new SAXNotRecognizedException(name);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void setEntityResolver(EntityResolver resolver) {
/* 211 */         this.entityResolver = resolver;
/*     */       }
/*     */       public EntityResolver getEntityResolver() {
/* 214 */         return this.entityResolver;
/*     */       }
/*     */ 
/*     */       
/*     */       public void setDTDHandler(DTDHandler handler) {
/* 219 */         this.dtdHandler = handler;
/*     */       }
/*     */       public DTDHandler getDTDHandler() {
/* 222 */         return this.dtdHandler;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       private XMLFilterImpl repeater = new XMLFilterImpl(); private ErrorHandler errorHandler;
/*     */       
/*     */       public void setContentHandler(ContentHandler handler) {
/* 231 */         this.repeater.setContentHandler(handler);
/*     */       }
/*     */       public ContentHandler getContentHandler() {
/* 234 */         return this.repeater.getContentHandler();
/*     */       }
/*     */ 
/*     */       
/*     */       public void setErrorHandler(ErrorHandler handler) {
/* 239 */         this.errorHandler = handler;
/*     */       }
/*     */       public ErrorHandler getErrorHandler() {
/* 242 */         return this.errorHandler;
/*     */       }
/*     */       
/*     */       public void parse(InputSource input) throws SAXException {
/* 246 */         parse();
/*     */       }
/*     */       
/*     */       public void parse(String systemId) throws SAXException {
/* 250 */         parse();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void parse() throws SAXException {
/*     */         try {
/* 258 */           JAXBSource.this.marshaller.marshal(JAXBSource.this.contentObject, this.repeater);
/* 259 */         } catch (JAXBException e) {
/*     */           
/* 261 */           SAXParseException se = new SAXParseException(e.getMessage(), null, null, -1, -1, e);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 267 */           if (this.errorHandler != null) {
/* 268 */             this.errorHandler.fatalError(se);
/*     */           }
/*     */ 
/*     */           
/* 272 */           throw se;
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
/*     */   private static Marshaller assertionFailed(String message) throws JAXBException {
/* 284 */     throw new JAXBException(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bin\\util\JAXBSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */