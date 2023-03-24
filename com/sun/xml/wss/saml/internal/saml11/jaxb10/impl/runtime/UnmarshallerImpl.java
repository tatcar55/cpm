/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.bind.unmarshaller.InterningXMLReader;
/*     */ import com.sun.xml.bind.validator.DOMLocator;
/*     */ import com.sun.xml.bind.validator.Locator;
/*     */ import com.sun.xml.bind.validator.SAXLocator;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.UnmarshallerHandler;
/*     */ import javax.xml.bind.helpers.AbstractUnmarshallerImpl;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnmarshallerImpl
/*     */   extends AbstractUnmarshallerImpl
/*     */ {
/*  45 */   private DefaultJAXBContextImpl context = null;
/*     */   
/*     */   private final GrammarInfo grammarInfo;
/*     */ 
/*     */   
/*     */   public UnmarshallerImpl(DefaultJAXBContextImpl context, GrammarInfo gi) {
/*  51 */     this.context = context;
/*  52 */     this.grammarInfo = gi;
/*     */ 
/*     */     
/*  55 */     DatatypeConverter.setDatatypeConverter(DatatypeConverterImpl.theInstance);
/*     */   }
/*     */   
/*     */   public void setValidating(boolean validating) throws JAXBException {
/*  59 */     super.setValidating(validating);
/*  60 */     if (validating == true)
/*     */     {
/*     */       
/*  63 */       this.context.getGrammar();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmarshallerHandler getUnmarshallerHandler() {
/*  74 */     return new InterningUnmarshallerHandler(createUnmarshallerHandler((Locator)new SAXLocator()));
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
/*     */   private SAXUnmarshallerHandler createUnmarshallerHandler(Locator locator) {
/*  96 */     SAXUnmarshallerHandler unmarshaller = new SAXUnmarshallerHandlerImpl(this, this.grammarInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 102 */       if (isValidating())
/*     */       {
/*     */         
/* 105 */         unmarshaller = ValidatingUnmarshaller.create(this.context.getGrammar(), unmarshaller, locator);
/*     */       }
/*     */     }
/* 108 */     catch (JAXBException e) {
/*     */       
/* 110 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 113 */     return unmarshaller;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object unmarshal(XMLReader reader, InputSource source) throws JAXBException {
/* 119 */     SAXLocator locator = new SAXLocator();
/* 120 */     SAXUnmarshallerHandler handler = createUnmarshallerHandler((Locator)locator);
/*     */     
/* 122 */     reader = InterningXMLReader.adapt(reader);
/*     */     
/* 124 */     reader.setContentHandler(handler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     reader.setErrorHandler(new ErrorHandlerAdaptor(handler, (Locator)locator));
/*     */ 
/*     */     
/*     */     try {
/* 140 */       reader.parse(source);
/* 141 */     } catch (IOException e) {
/* 142 */       throw new JAXBException(e);
/* 143 */     } catch (SAXException e) {
/* 144 */       throw createUnmarshalException(e);
/*     */     } 
/*     */     
/* 147 */     Object result = handler.getResult();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     reader.setContentHandler(dummyHandler);
/* 153 */     reader.setErrorHandler(dummyHandler);
/*     */     
/* 155 */     return result;
/*     */   }
/*     */   
/*     */   public final Object unmarshal(Node node) throws JAXBException {
/*     */     try {
/* 160 */       DOMScanner scanner = new DOMScanner();
/* 161 */       UnmarshallerHandler handler = new InterningUnmarshallerHandler(createUnmarshallerHandler((Locator)new DOMLocator(scanner)));
/*     */ 
/*     */       
/* 164 */       if (node instanceof Element) {
/* 165 */         scanner.parse((Element)node, handler);
/*     */       }
/* 167 */       else if (node instanceof Document) {
/* 168 */         scanner.parse(((Document)node).getDocumentElement(), handler);
/*     */       } else {
/*     */         
/* 171 */         throw new IllegalArgumentException();
/*     */       } 
/* 173 */       return handler.getResult();
/* 174 */     } catch (SAXException e) {
/* 175 */       throw createUnmarshalException(e);
/*     */     } 
/*     */   }
/*     */   
/* 179 */   private static final DefaultHandler dummyHandler = new DefaultHandler();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\UnmarshallerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */