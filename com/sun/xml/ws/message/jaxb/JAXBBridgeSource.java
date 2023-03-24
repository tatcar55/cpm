/*     */ package com.sun.xml.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.transform.sax.SAXSource;
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
/*     */ final class JAXBBridgeSource
/*     */   extends SAXSource
/*     */ {
/*     */   private final XMLBridge bridge;
/*     */   private final Object contentObject;
/*     */   private final XMLReader pseudoParser;
/*     */   
/*     */   public JAXBBridgeSource(XMLBridge bridge, Object contentObject) {
/*  74 */     this.pseudoParser = new XMLFilterImpl() {
/*     */         public boolean getFeature(String name) throws SAXNotRecognizedException {
/*  76 */           if (name.equals("http://xml.org/sax/features/namespaces"))
/*  77 */             return true; 
/*  78 */           if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/*  79 */             return false; 
/*  80 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */         private LexicalHandler lexicalHandler;
/*     */         public void setFeature(String name, boolean value) throws SAXNotRecognizedException {
/*  84 */           if (name.equals("http://xml.org/sax/features/namespaces") && value)
/*     */             return; 
/*  86 */           if (name.equals("http://xml.org/sax/features/namespace-prefixes") && !value)
/*     */             return; 
/*  88 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */         
/*     */         public Object getProperty(String name) throws SAXNotRecognizedException {
/*  92 */           if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/*  93 */             return this.lexicalHandler;
/*     */           }
/*  95 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */         
/*     */         public void setProperty(String name, Object value) throws SAXNotRecognizedException {
/*  99 */           if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 100 */             this.lexicalHandler = (LexicalHandler)value;
/*     */             return;
/*     */           } 
/* 103 */           throw new SAXNotRecognizedException(name);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void parse(InputSource input) throws SAXException {
/* 109 */           parse();
/*     */         }
/*     */         
/*     */         public void parse(String systemId) throws SAXException {
/* 113 */           parse();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void parse() throws SAXException {
/*     */           try {
/* 121 */             startDocument();
/*     */             
/* 123 */             JAXBBridgeSource.this.bridge.marshal(JAXBBridgeSource.this.contentObject, this, null);
/* 124 */             endDocument();
/* 125 */           } catch (JAXBException e) {
/*     */             
/* 127 */             SAXParseException se = new SAXParseException(e.getMessage(), null, null, -1, -1, e);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 133 */             fatalError(se);
/*     */ 
/*     */ 
/*     */             
/* 137 */             throw se;
/*     */           } 
/*     */         }
/*     */       };
/*     */     this.bridge = bridge;
/*     */     this.contentObject = contentObject;
/*     */     setXMLReader(this.pseudoParser);
/*     */     setInputSource(new InputSource());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\jaxb\JAXBBridgeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */