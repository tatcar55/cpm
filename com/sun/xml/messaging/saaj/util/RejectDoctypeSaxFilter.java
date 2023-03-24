/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.AttributesImpl;
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
/*     */ public class RejectDoctypeSaxFilter
/*     */   extends XMLFilterImpl
/*     */   implements XMLReader, LexicalHandler
/*     */ {
/*  67 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.util", "com.sun.xml.messaging.saaj.util.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   static final String LEXICAL_HANDLER_PROP = "http://xml.org/sax/properties/lexical-handler";
/*     */ 
/*     */ 
/*     */   
/*  75 */   static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".intern();
/*  76 */   static final String SIGNATURE_LNAME = "Signature".intern();
/*  77 */   static final String ENCRYPTED_DATA_LNAME = "EncryptedData".intern();
/*  78 */   static final String DSIG_NS = "http://www.w3.org/2000/09/xmldsig#".intern();
/*  79 */   static final String XENC_NS = "http://www.w3.org/2001/04/xmlenc#".intern();
/*  80 */   static final String ID_NAME = "ID".intern();
/*     */   
/*     */   private LexicalHandler lexicalHandler;
/*     */ 
/*     */   
/*     */   public RejectDoctypeSaxFilter(SAXParser saxParser) throws SOAPException {
/*     */     XMLReader xmlReader;
/*     */     try {
/*  88 */       xmlReader = saxParser.getXMLReader();
/*  89 */     } catch (Exception e) {
/*  90 */       log.severe("SAAJ0602.util.getXMLReader.exception");
/*  91 */       throw new SOAPExceptionImpl("Couldn't get an XMLReader while constructing a RejectDoctypeSaxFilter", e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  98 */       xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", this);
/*  99 */     } catch (Exception e) {
/* 100 */       log.severe("SAAJ0603.util.setProperty.exception");
/* 101 */       throw new SOAPExceptionImpl("Couldn't set the lexical handler property while constructing a RejectDoctypeSaxFilter", e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     setParent(xmlReader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 116 */     if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
/* 117 */       this.lexicalHandler = (LexicalHandler)value;
/*     */     } else {
/* 119 */       super.setProperty(name, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 129 */     throw new SAXException("Document Type Declaration is not allowed");
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */   
/*     */   public void startEntity(String name) throws SAXException {
/* 136 */     if (this.lexicalHandler != null) {
/* 137 */       this.lexicalHandler.startEntity(name);
/*     */     }
/*     */   }
/*     */   
/*     */   public void endEntity(String name) throws SAXException {
/* 142 */     if (this.lexicalHandler != null) {
/* 143 */       this.lexicalHandler.endEntity(name);
/*     */     }
/*     */   }
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 148 */     if (this.lexicalHandler != null) {
/* 149 */       this.lexicalHandler.startCDATA();
/*     */     }
/*     */   }
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 154 */     if (this.lexicalHandler != null) {
/* 155 */       this.lexicalHandler.endCDATA();
/*     */     }
/*     */   }
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 160 */     if (this.lexicalHandler != null) {
/* 161 */       this.lexicalHandler.comment(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/* 171 */     if (atts != null) {
/* 172 */       boolean eos = false;
/* 173 */       if (namespaceURI == DSIG_NS || XENC_NS == namespaceURI) {
/* 174 */         eos = true;
/*     */       }
/* 176 */       int length = atts.getLength();
/* 177 */       AttributesImpl attrImpl = new AttributesImpl();
/* 178 */       for (int i = 0; i < length; i++) {
/* 179 */         String name = atts.getLocalName(i);
/* 180 */         if (name != null && name.equals("Id")) {
/* 181 */           if (eos || atts.getURI(i) == WSU_NS) {
/* 182 */             attrImpl.addAttribute(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), ID_NAME, atts.getValue(i));
/*     */           } else {
/*     */             
/* 185 */             attrImpl.addAttribute(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), atts.getType(i), atts.getValue(i));
/*     */           } 
/*     */         } else {
/* 188 */           attrImpl.addAttribute(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), atts.getType(i), atts.getValue(i));
/*     */         } 
/*     */       } 
/*     */       
/* 192 */       super.startElement(namespaceURI, localName, qName, attrImpl);
/*     */     } else {
/* 194 */       super.startElement(namespaceURI, localName, qName, null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\RejectDoctypeSaxFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */